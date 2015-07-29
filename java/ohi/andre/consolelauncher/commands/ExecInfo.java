package ohi.andre.consolelauncher.commands;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.io.File;
import java.lang.reflect.Method;

import ohi.andre.consolelauncher.managers.ContactManager;
import ohi.andre.consolelauncher.managers.MusicManager;
import ohi.andre.consolelauncher.tuils.Storage;
import ohi.andre.consolelauncher.tuils.interfaces.AppExplorer;
import ohi.andre.consolelauncher.tuils.interfaces.AssocExplorer;
import ohi.andre.consolelauncher.tuils.interfaces.Disposeable;

@SuppressWarnings("deprecation")
public class ExecInfo implements Disposeable {

//	current directory
	public File currentDirectory;
	
//	current set of commands
	public CommandGroup active;
	
//	context for accessing methods
	public Context context;
	
//	resources references
	public SharedPreferences prefs;
	public Resources res;
	
//	flashlight
	public boolean isFlashOn = false, canUseFlash = false;
	public Camera camera;
	public Parameters parameters;
	
//	internet
	public WifiManager wifi;

//	3g/data
	public Method setMobileDataEnabledMethod;
	public ConnectivityManager connectivityMgr;
	public Object connectMgr;
	
//	contacts
	public ContactManager contacts;
	
//	music
	public MusicManager player;
	public boolean playerStarted = false;
	
//	exploring apps & assocs
	public AssocExplorer assExpl;
	public AppExplorer appExpl;
	
//	current set of args
	public Object args;
	
	public ExecInfo(Activity context, CommandGroup active, AssocExplorer asexp, 
			AppExplorer apexp, MusicManager p, ContactManager c) {
		this.prefs = context.getPreferences(0);
		this.res = context.getResources();
		this.active = active;
		
		if(asexp != null) {
			this.context = context;
			
			SharedPreferences prefs = context.getPreferences(0);
			
			this.currentDirectory = new File(Storage.readInitialPath(prefs));
			this.assExpl = asexp;
			this.appExpl = apexp;
			
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1)
				initFlash(context);
			
			this.player = p;
			this.contacts = c;
		} 
	}
	
	@TargetApi(Build.VERSION_CODES.ECLAIR_MR1)
	private void initFlash(Context context) {
		this.canUseFlash = context.getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
	}

	public void initCamera() {
		this.camera = Camera.open();
		this.parameters = this.camera.getParameters();
	}
	
	@Override
	public void dispose() {
		if(this.camera == null || this.isFlashOn)
			return;
		
		this.camera.stopPreview();
		this.camera.release();
		this.camera = null;
		this.parameters = null;
	}
	
	public void clear() {
		args = null;
	}
}
