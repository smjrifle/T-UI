package ohi.andre.consolelauncher.tuils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

public class Storage {
	
//	startupdir
	
	private static final String STARTUPDIR_KEY = "startupdir";

	public static String readInitialPath(SharedPreferences prefs) {
		return prefs.getString(Storage.STARTUPDIR_KEY, DeviceTuils.getInternalDirectoryPath());
	}
	
	public static void writeInitialPath(SharedPreferences.Editor edit, String path) {
		edit.putString(Storage.STARTUPDIR_KEY, path);
	}
	
//	show hidden files
	
	private static final String SHOWHIDDEN_KEY = "showHidden";
	
	public static boolean showHiddenFiles(SharedPreferences prefs) {
		return prefs.getBoolean(Storage.SHOWHIDDEN_KEY, false);
	}
	
	public static String toggleShowHidden(SharedPreferences prefs) {
		boolean active = !Storage.showHiddenFiles(prefs);
		
		SharedPreferences.Editor edit = prefs.edit();
		edit.putBoolean(Storage.SHOWHIDDEN_KEY, active);
		edit.commit();
		
		return Boolean.toString(active);
	}
	
//	assocs
	
	private static final String ASSOCNAME_KEY = "assocname_";
	private static final String ASSOCVALUE_KEY = "assocvalue_";
	private static final String ASSOC_N_KEY = "assoc_n";
	
	public static HashMap<String, String> assocMap(SharedPreferences prefs) {
		HashMap<String, String> assocs = new HashMap<>();
		
		int n = assocNumber(prefs);
		
		for(int count = 0; count < n; count++) 
			assocs.put(prefs.getString(Storage.ASSOCNAME_KEY + count, ""), 
					prefs.getString(Storage.ASSOCVALUE_KEY + count, ""));
		
		return assocs;
	}
	
	private static int assocNumber(SharedPreferences prefs) {
		return prefs.getInt(Storage.ASSOC_N_KEY, 0);
	}
	
	public static void saveAssocs(SharedPreferences.Editor edit, HashMap<String, String> assocs) {
		Iterator<Entry<String, String>> iterator = assocs.entrySet().iterator();
		
		Entry<String, String> next;
		int count = 0;
		while(iterator.hasNext()) {
			next = iterator.next();
			edit.putString(Storage.ASSOCNAME_KEY + count, next.getKey());
			edit.putString(Storage.ASSOCVALUE_KEY + count, next.getValue());
			count += 1;
		}
		edit.putInt(Storage.ASSOC_N_KEY, assocs.size());
	}

//	skin

    public static void setBg(SharedPreferences.Editor editor, int color) {
        editor.putInt(ConsoleSkin.bgColorKey, color);
    }

    public static void setDeviceColor(SharedPreferences.Editor editor, int color) {
        editor.putInt(ConsoleSkin.deviceInfoTextColorKey, color);
    }

    public static void setInputColor(SharedPreferences.Editor editor, int color) {
        editor.putInt(ConsoleSkin.inputTextColorKey, color);
    }

    public static void setOutputColor(SharedPreferences.Editor editor, int color) {
        editor.putInt(ConsoleSkin.outputTextColorKey, color);
    }

    public static void setRamColor(SharedPreferences.Editor editor, int color) {
        editor.putInt(ConsoleSkin.ramTextColorKey, color);
    }

    public static void setDirColor(SharedPreferences.Editor editor, int color) {
        editor.putInt(ConsoleSkin.dirTextColorKey, color);
    }

    public static void setFilesColor(SharedPreferences.Editor editor, int color) {
        editor.putInt(ConsoleSkin.filesTextColorKey, color);
    }
	
//	first time
	
	private static final String FIRSTTIME_KEY = "firstTime";
	
	public static boolean firstTime(SharedPreferences prefs) {
		boolean firstTime = prefs.getBoolean(Storage.FIRSTTIME_KEY, true);
		if(firstTime) {
			SharedPreferences.Editor edit = prefs.edit();
			edit.putBoolean(Storage.FIRSTTIME_KEY, false);
			edit.commit();
		}
		return firstTime;
	}
	
//	double tap
	
	private static final String DOUBLETAP_KEY = "dbTap";

	@SuppressLint("DefaultLocale")
	public static String toggleDbTap(SharedPreferences prefs) {
		boolean active = !Storage.readDbTap(prefs);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putBoolean(Storage.DOUBLETAP_KEY, active);
		edit.commit();
		
		return Boolean.toString(active);
	}
	
	public static boolean readDbTap(SharedPreferences prefs) {
		return prefs.getBoolean(Storage.DOUBLETAP_KEY, true);
	}
	
//	music
	
	private static final String RANDOMACTIVE_KEY = "randomActive";
	private static final String SONGFOLDER_KEY = "songFolder";
	
	public static boolean readRandom(SharedPreferences prefs) {
		return prefs.getBoolean(Storage.RANDOMACTIVE_KEY, false);
	}
	
	@SuppressLint("DefaultLocale")
	public static boolean toggleRadom(SharedPreferences prefs) 
			throws IllegalArgumentException {
		SharedPreferences.Editor edit = prefs.edit();
		
		boolean active = !Storage.readRandom(prefs);
		edit.putBoolean(Storage.RANDOMACTIVE_KEY, active);
		edit.commit();
		
		return active;
	}
	
	public static String readSongPath(SharedPreferences prefs) {
		return prefs.getString(Storage.SONGFOLDER_KEY, DeviceTuils.getInternalDirectoryPath() + "/Music");
	}
	
	public static void writeSongPath(SharedPreferences.Editor edit, String path) {
		edit.putString(Storage.SONGFOLDER_KEY, path);
	}
	
//	file columns
	
	private static final String FILECOLUMNS_KEY = "fileColumns";
	
	public static void writeColumns(SharedPreferences.Editor edit, int n) {
		edit.putInt(Storage.FILECOLUMNS_KEY, n);
	}
	
	public static int readColumns(SharedPreferences prefs) {
		return prefs.getInt(Storage.FILECOLUMNS_KEY, 3);
	}
	
}
