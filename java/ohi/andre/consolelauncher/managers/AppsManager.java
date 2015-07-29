package ohi.andre.consolelauncher.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;

import ohi.andre.comparestring.Compare;
import ohi.andre.consolelauncher.tuils.interfaces.AppExplorer;

public class AppsManager implements AppExplorer {
	
	public static final String APP_LABEL = "label";
	
	private PackageManager mgr;
	private HashMap<String, String> apps;
	
	public AppsManager(Context context) {
		mgr = context.getPackageManager();
		apps = AppsManager.getApps(mgr);
	}

//  -------------------------------------------- startup methods -------------------------------------------------------------
	
	public void add(String packageName) {
		try {
			ApplicationInfo info = mgr.getApplicationInfo(packageName, 0);
			apps.put(info.loadLabel(mgr).toString(), packageName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void remove(String packageName) {
		Set<String> keys = apps.keySet();
		for(String key : keys) {
			if(apps.get(key).equals(packageName)) {
				apps.remove(key);
				break;
			}
		}
	}

//  --------------------------------------------- add/remove --------------------------------------------------------------

    @Override
	public HashMap<String, String> getApps() {
		return apps;
	}

    @Override
    public String printApps() {
        List<String> list = new ArrayList<>(apps.keySet());

        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return Compare.alphabeticCompare(lhs, rhs);
            }
        });

        String output = "";
        for(String s : list)
            output = output.concat(s + "\n");
        return output;
    }

//  ------------------------------------------------- app explorer -------------------------------------------------------

//  find the Application intent
	public AppInfo getInfo(String input) {
		String app = Compare.compare(apps.keySet(), input);
		String packageName = apps.get(app);
		if(packageName == null)
			return null;
		
		Intent i = mgr.getLaunchIntentForPackage(packageName);
		i.putExtra(AppsManager.APP_LABEL, app);
		
		String[] p;
		try {
			p = mgr.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		return new AppInfo(i, p);
	}

//  retrieve a list of installed apps
	private static HashMap<String, String> getApps(PackageManager mgr) {
		HashMap<String, String> map = new HashMap<>();
		
		Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> infos =  mgr.queryIntentActivities(i, 0);
		
		for(ResolveInfo info : infos) {
			String app = info.loadLabel(mgr).toString();
			map.put(app, info.activityInfo.packageName);
		}
		
		return map;
	}

	
	public class AppInfo {
		public Intent launchIntent;
		public String[] permissions;
		
		public AppInfo(Intent i, String[] p) {
			this.launchIntent = i;
			this.permissions = p;
		}
	}
	
}
