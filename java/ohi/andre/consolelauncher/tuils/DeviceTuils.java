package ohi.andre.consolelauncher.tuils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

public class DeviceTuils {

	public static String ramDetails(ActivityManager mgr, MemoryInfo info) {
		mgr.getMemoryInfo(info);
		long availableMegs = info.availMem / 1048576L;
		
		return availableMegs + " MB";
	}
	
	public static String getDeviceName() {
	    String manufacturer = Build.MANUFACTURER;
	    String model = Build.MODEL;
	    if (model.startsWith(manufacturer)) {
	        return capitalize(model);
	    }
	    if (manufacturer.equalsIgnoreCase("HTC")) {
	        return "HTC " + model;
	    }
	    return capitalize(manufacturer) + " " + model;
	}

	private static String capitalize(String str) {
	    if (TextUtils.isEmpty(str)) 
	        return str;
	    
	    char[] arr = str.toCharArray();
	    boolean capitalizeNext = true;
	    String phrase = "";
	    for (char c : arr) {
	        if (capitalizeNext && Character.isLetter(c)) {
	            phrase += Character.toUpperCase(c);
	            capitalizeNext = false;
	            continue;
	        } else if (Character.isWhitespace(c)) 
	            capitalizeNext = true;
	        
	        phrase += c;
	    }
	    return phrase;
	}

	public static String getStackTrace(final Throwable throwable) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}
	
	public static Intent openFile(File url) {
		Uri uri = Uri.fromFile(url);

		Intent intent = new Intent(Intent.ACTION_VIEW);
		if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
			// Word document
			intent.setDataAndType(uri, "application/msword");
		} else if (url.toString().contains(".apk")) {
			// apk
			intent.setDataAndType(uri,
					"application/vnd.android.package-archive");
		} else if (url.toString().contains(".pdf")) {
			// PDF file
			intent.setDataAndType(uri, "application/pdf");
		} else if (url.toString().contains(".ppt")
				|| url.toString().contains(".pptx")) {
			// Powerpoint file
			intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
		} else if (url.toString().contains(".xls")
				|| url.toString().contains(".xlsx")) {
			// Excel file
			intent.setDataAndType(uri, "application/vnd.ms-excel");
		} else if (url.toString().contains(".zip")
				|| url.toString().contains(".rar")) {
			// ZIP Files
			intent.setDataAndType(uri, "application/zip");
		} else if (url.toString().contains(".rtf")) {
			// RTF file
			intent.setDataAndType(uri, "application/rtf");
		} else if (url.toString().contains(".wav")
				|| url.toString().contains(".mp3")) {
			// WAV audio file
			intent.setDataAndType(uri, "audio/x-wav");
		} else if (url.toString().contains(".gif")) {
			// GIF file
			intent.setDataAndType(uri, "image/gif");
		} else if (url.toString().contains(".jpg")
				|| url.toString().contains(".jpeg")
				|| url.toString().contains(".png")) {
			// JPG file
			intent.setDataAndType(uri, "image/jpeg");
		} else if (url.toString().contains(".txt")) {
			// Text file
			intent.setDataAndType(uri, "text/plain");
		} else if (url.toString().contains(".3gp")
				|| url.toString().contains(".mpg")
				|| url.toString().contains(".mpeg")
				|| url.toString().contains(".mpe")
				|| url.toString().contains(".mp4")
				|| url.toString().contains(".avi")) {
			// Video files
			intent.setDataAndType(uri, "video/*");
		} else {
			intent.setDataAndType(uri, "*/*");
		}

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		return intent;
	}
	
	public static String getInternalDirectoryPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

}
