package ohi.andre.consolelauncher;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.view.accessibility.AccessibilityEvent;

import ohi.andre.consolelauncher.tuils.interfaces.Disposeable;
import ohi.andre.consolelauncher.tuils.interfaces.OnHeadsetStateChangedListener;

public class TimeManager implements Disposeable {
	
	private final int RAM_DELAY = 5000;
	private final int TUTORIAL_DELAY = 8000;

//	apps
	private BroadcastReceiver appsBroadcast = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			String data = intent.getData().getSchemeSpecificPart();
			if(action == Intent.ACTION_PACKAGE_ADDED)
				listener.onPackageAdd(data);
			else
				listener.onPackageRemoved(data);
		}
        };
    
    private Handler handler;
    
//  ram
    private Runnable ramRunnable = new Runnable() {
		@Override
		public void run() {
			ram.onRamUpdate();
			handler.postDelayed(this, RAM_DELAY);
		}
	};
	
//	tutorial
	private int index = 0;
	private Runnable tutorialRunnable = new Runnable() {
		
		@Override
		public void run() {
			if(tutorial.suggestNext(index++))
				handler.postDelayed(this, TUTORIAL_DELAY);
			else
				tutorial.endTutorial();
		}
	};
	
	
//	music
	private BroadcastReceiver headsetReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if(ctrl == null)
				return;
			if(intent.getIntExtra("state", 0) != 1)
				ctrl.onHeadsetUnplugged();
		}
	};


//	references
	private PackageListener listener;
	private RamUpdater ram;
	private Activity mContext;
	private TutorialTrigger tutorial;
	private OnHeadsetStateChangedListener ctrl;
	
	public TimeManager(Activity context, PackageListener list, RamUpdater r, TutorialTrigger t, 
			OnHeadsetStateChangedListener p) {
		listener = list;
		ram = r;
		mContext = context;
		
		IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        
        tutorial = t;
        ctrl = p;
        
        mContext.registerReceiver(appsBroadcast, intentFilter);
        mContext.registerReceiver(headsetReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));   
	}
	
	public void init() {
		ram.onRamUpdate();
		handler = new Handler();
		handler.postDelayed(ramRunnable, RAM_DELAY);
		
		if(tutorial != null) {
			tutorial.suggestNext(index++);
			handler.postDelayed(tutorialRunnable, TUTORIAL_DELAY);
		}
	}
	
	@Override
	public void dispose() {
		handler.removeCallbacks(ramRunnable);
		try {
			mContext.unregisterReceiver(appsBroadcast);
			mContext.unregisterReceiver(headsetReceiver);
		} catch (Exception exc) {}
	}
	
	
	public interface PackageListener {
		void onPackageAdd(String add);
		void onPackageRemoved(String rem);
	}
	
	public interface RamUpdater {
		void onRamUpdate();
	}
	
	public interface TutorialTrigger {
		boolean suggestNext(int i);
		void endTutorial();
	}
	
}
