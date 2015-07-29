package ohi.andre.consolelauncher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.File;

import ohi.andre.consolelauncher.TimeManager.PackageListener;
import ohi.andre.consolelauncher.TimeManager.RamUpdater;
import ohi.andre.consolelauncher.tuils.ConsoleSkin;
import ohi.andre.consolelauncher.tuils.interfaces.CommandExecuter;
import ohi.andre.consolelauncher.tuils.interfaces.FileOpener;
import ohi.andre.consolelauncher.tuils.interfaces.OnDirChangedListener;
import ohi.andre.consolelauncher.tuils.interfaces.Outputable;
import ohi.andre.consolelauncher.tuils.interfaces.Reloadable;

public class LauncherActivity extends Activity implements Reloadable {

	private UImanager ui;
	private TimeManager time;
	private MainManager main;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        if(getPreferences(0).getInt(ConsoleSkin.bgColorKey, 0) == ConsoleSkin.SYSTEM_WALLPAPER)
            setTheme(R.style.SystemWallpaperStyle);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		
		ui = new UImanager(this, findViewById(R.id.id_mainview), new CommandExecuter() {
			
			@Override
			public void exec(String input) {
				main.onCommand(input);
			}
		}, new FileOpener() {
			
			@Override
			public void onOpenRequest(String s) {
				main.onExternalInput(s, false);
			}
		});
		ui.initUI();
		
		main = new MainManager(this, new OnDirChangedListener() {
			
			@Override
			public void onChange(File file) {
				ui.updateDir(file);
			}
		}, new Outputable() {
			
			@Override
			public void onOutput(String output, boolean realTime) {
				ui.setOutput(output, realTime);
			}
		});
		main.init(false);
		
		time = new TimeManager(this, new PackageListener() {
			
			@Override
			public void onPackageRemoved(String rem) {
				main.onRemApp(rem);
			}
			
			@Override
			public void onPackageAdd(String add) {
				main.onAddApp(add);
			}
		}, new RamUpdater() {
			
			@Override
			public void onRamUpdate() {
				ui.updateRamDetails();
			}
		}, main.getTutorialTrigger(), main.getPlayerController());
		time.init();
	}

    private void restart() {
        Intent intent = getIntent();
        startActivity(intent);
        finish();
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		
		ui.requestVoidViewFocus();
		main.requestDirUpdate(true);
	}
	
	@Override
	protected void onPause() {
		super.onPause();

        main.store();
		main.dispose();
	}
	
	@Override
	protected void onDestroy() {
        super.onDestroy();
		time.dispose();
		main.dispose();
	}
	
	@Override
	public void onBackPressed() {
		main.backPressed();
	}
	
	@Override
	public void reload(int mode) {
		if(mode == MainManager.CONFIG_MODE)
			main.init(true);
        else
            restart();
	}

}
