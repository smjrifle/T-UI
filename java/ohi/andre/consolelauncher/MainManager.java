package ohi.andre.consolelauncher;

import android.app.Activity;
import android.content.SharedPreferences;

import java.io.File;

import ohi.andre.consolelauncher.TimeManager.TutorialTrigger;
import ohi.andre.consolelauncher.commands.CommandGroup;
import ohi.andre.consolelauncher.commands.ExecInfo;
import ohi.andre.consolelauncher.commands.modes.Config;
import ohi.andre.consolelauncher.commands.modes.Main;
import ohi.andre.consolelauncher.managers.AppsManager;
import ohi.andre.consolelauncher.managers.AppsManager.AppInfo;
import ohi.andre.consolelauncher.managers.AssocManager;
import ohi.andre.consolelauncher.managers.ContactManager;
import ohi.andre.consolelauncher.managers.MusicManager;
import ohi.andre.consolelauncher.managers.TutorialManager;
import ohi.andre.consolelauncher.tuils.Command;
import ohi.andre.consolelauncher.tuils.CommandTuils;
import ohi.andre.consolelauncher.tuils.DeviceTuils;
import ohi.andre.consolelauncher.tuils.Storage;
import ohi.andre.consolelauncher.tuils.interfaces.AppExplorer;
import ohi.andre.consolelauncher.tuils.interfaces.AssocExplorer;
import ohi.andre.consolelauncher.tuils.interfaces.CmdTrigger;
import ohi.andre.consolelauncher.tuils.interfaces.Disposeable;
import ohi.andre.consolelauncher.tuils.interfaces.OnDirChangedListener;
import ohi.andre.consolelauncher.tuils.interfaces.OnHeadsetStateChangedListener;
import ohi.andre.consolelauncher.tuils.interfaces.Outputable;
import ohi.andre.consolelauncher.tuils.interfaces.Reloadable;

public class MainManager implements Disposeable {

	public static int CONFIG_MODE = 10;
    public static int LAUNCHER_MODE = 11;

    public static final String SWITCH = "switch";
	public static final String UPDATE_DIRECTORY = "update_dir";
	
	private AppsManager appsMgr;
	private AssocManager assocMgr;

	private TutorialManager tutorialMgr;
	private TutorialTrigger tutorialTrigger;
	
	private CmdTrigger[] triggers;

	private ExecInfo info;
	
	private OnHeadsetStateChangedListener playerCtrl;
	
	private Activity mContext;
	private OnDirChangedListener dir;
	private Reloadable rel;
	private Outputable out;

	private CommandGroup group;

	private boolean refreshOnResume = false;
	
	public MainManager(LauncherActivity activity, OnDirChangedListener d, Outputable o) {
		SharedPreferences prefs = activity.getPreferences(0);
		
		appsMgr = new AppsManager(activity);
		assocMgr = new AssocManager(prefs);

		mContext = activity;
		this.dir = d;
		
		group = new Main();

		initLauncherMode();

		rel = activity;
		out = o;
		
//		tutorial
		boolean launcherTutorial = Storage.firstTime(prefs);
		if(!launcherTutorial)
			return;
		
		tutorialMgr = new TutorialManager(mContext.getResources());
		tutorialTrigger = new TutorialTrigger() {

			@Override
			public boolean suggestNext(int i) {
				String output = tutorialMgr.requestTutorial(i);
				if(output == null) {
					endTutorial();
					return false;
				}

				out.onOutput(tutorialMgr.requestTutorial(i), true);
				return true;
			}

			@Override
			public void endTutorial() {
				MainManager.this.endTutorial();
			}
		};
	}
	
	public void init(boolean config) {
		AssocExplorer ass = null;
		AppExplorer apps = null;
		MusicManager music = null;
		ContactManager cont = null;
		
		if(info != null) {
			info.dispose();
			if(info.player != null)
				info.player.dispose();
		}
		
		if(!config) {
			ass = assocMgr;
			
			apps = appsMgr;

			cont = new ContactManager(mContext);

			music = new MusicManager(mContext);

			this.playerCtrl = music;
		}

		info = new ExecInfo(mContext, group, ass, apps, music, cont);
		
		if(!config)
			requestDirUpdate();
	}

    private void initLauncherMode() {
        triggers = new CmdTrigger[3];
        triggers[0] = new AssocTrigger();
        triggers[1] = new CommandTrigger();
        triggers[2] = new AppsTrigger();
    }

    private void initConfigMode() {
        triggers = new CmdTrigger[1];
        triggers[0] = new CommandTrigger();
    }

    private void switchMode() {
        int mode;
        if(group instanceof Main) {
            group = new Config();
            initConfigMode();
            mode = MainManager.CONFIG_MODE;
        } else
            mode = MainManager.LAUNCHER_MODE;
        rel.reload(mode);
    }

//    ----------------------------------------- end of startup methods -----------------------------------------------------------

	public OnHeadsetStateChangedListener getPlayerController() {
		return playerCtrl;
	}

    public TutorialTrigger getTutorialTrigger() {
        return tutorialTrigger;
    }

//    ---------------------------------------- end of accessors -----------------------------------------------------------------
	
	public void requestDirUpdate(boolean isResume) {
		if((isResume && refreshOnResume) || !isResume)
			dir.onChange(info.currentDirectory);
	}
	
	private void requestDirUpdate() {
		requestDirUpdate(false);
	}

    //	this manages an external input (long press)
    public void onExternalInput(String path, boolean openParent) {
        File f;
        if(openParent)
            f = info.currentDirectory.getParentFile();
        else
            f = new File(info.currentDirectory, path);

        if(f == null)
            return;

        String command;
        if(f.isDirectory())
            command = "cd ";
        else
            command = "open ";
        command = command.concat(f.getAbsolutePath());

        onCommand(command);
    }

    public void backPressed() {
        if(group instanceof Main)
            onExternalInput(null, true);
    }

//    --------------------------------------- end of file managers accessors -------------------------------------------------------------------
	
	private void endTutorial() {
		tutorialMgr = null;
		tutorialTrigger = null;
	}
	
	public void onCommand(String input) {
		if(tutorialMgr != null)
			return;
		
		int r = -1;
        for (CmdTrigger trigger : triggers) {
            try {
                r = trigger.trigger(input);
            } catch (Exception e) {
                out.onOutput(DeviceTuils.getStackTrace(e), false);
            }
            if (r == 0)
                return;
        }

		out.onOutput(mContext.getString(R.string.output_commandnotfound), true);
	}

//  ----------------------------------------- end of outputters ----------------------------------------------------------------

	@Override
	public void dispose() {
		info.dispose();
	}

    public void store() {
        SharedPreferences.Editor edit = mContext.getPreferences(0).edit();
        assocMgr.save(edit);
        edit.commit();
    }

//  ----------------------------------------- save & close methods -----------------------------------------------

	public void onAddApp(String packageName) {
		appsMgr.add(packageName);
	}
	
	public void onRemApp(String packageName) {
		appsMgr.remove(packageName);
	}

//	------------------------------------ app manager ----------------------------------------------
	
	private class CommandTrigger implements CmdTrigger {
		@Override
		public int trigger(String input) {
			String output;
			boolean rt;

            Command command = CommandTuils.parse(input, info);
            if(command == null)
                return 1;
			else
				output = command.exec(info);


            if(output == null)
				return 1;
			else if(output.equals(MainManager.UPDATE_DIRECTORY)) {
				requestDirUpdate();
				output = mContext.getString(R.string.output_operationdone);
			} else if(output.equals(MainManager.SWITCH)) {
				switchMode();
				output = mContext.getString(R.string.mode_changed) + " " + group.getLabel();
			}
			
			rt = command.useRealTimeTyping();
				
			out.onOutput(output, rt);
			
			return 0;
		}

	}
	
	private class AppsTrigger implements CmdTrigger {
		@Override
		public int trigger(String input) {
			AppInfo app = appsMgr.getInfo(input);
			if(app == null)
				return 1;

			out.onOutput(
					mContext.getString(R.string.launching) + " " + app.launchIntent.getStringExtra(AppsManager.APP_LABEL), 
					true
			);
			
			if(app.permissions != null) {
				for(int count = 0; count < app.permissions.length; count++) {
					String p = app.permissions[count];
					if(p.equals("android.permission.WRITE_EXTERNAL_STORAGE") || 
							p.equals("android.permission.WRITE_INTERNAL_STORAGE") ||
							p.equals("android.permission.INTERNET"))
						refreshOnResume = true;
					else 
						if(count == app.permissions.length - 1)
							refreshOnResume = false;
				}
			}
			
			mContext.startActivity(app.launchIntent);

			return 0;
		}

	}
	
	private class AssocTrigger implements CmdTrigger {
		@Override
		public int trigger(String input) {
			String assoc = assocMgr.getAssoc(input);
			if(assoc == null)
				return 1;
			
			onCommand(assoc);
			
			return 0;
		}

	}

    //	------------------------------------------ triggers ---------------------------------------------
}
