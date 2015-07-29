package ohi.andre.consolelauncher.managers;

import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import ohi.andre.comparestring.Compare;
import ohi.andre.consolelauncher.tuils.DeviceTuils;

public class FileManager {
	
	public static final int FILE_NOTFOUND = 10;
	public static final int ISFILE = 11;
	public static final int ISDIRECTORY = 12;
	public static final int IOERROR = 13;
	
	public static int mv(File input, File output) {
		if(input == null || output == null)
			return FileManager.FILE_NOTFOUND;
		
		if(!output.isDirectory())
			return FileManager.ISFILE;
		
		String[] command = {
				"mv",
				input.getAbsolutePath(),
				output.getAbsolutePath()
		};
		
		Process p;
		int exit;
		try {
			p = Runtime.getRuntime().exec(command);
			exit = p.waitFor();
		} catch (IOException | InterruptedException e) {
			return FileManager.IOERROR;
		}
		
		if(exit == 0)
			return 0;
		
		return FileManager.IOERROR;
	}
	
	public static int rm(File file) {
		if(file == null)
			return FileManager.FILE_NOTFOUND;
		
		String[] command = {
				"rm",
				"-r",
				file.getAbsolutePath()
		};
		
		Process p;
		int exit;
		try {
			p = Runtime.getRuntime().exec(command);
			exit = p.waitFor();
		} catch (IOException | InterruptedException e) {
			return FileManager.IOERROR;
		}
		
		if(exit == 0)
			return 0;
		
		return FileManager.IOERROR;
	}
	
	public static int mkdir(File cd, String name) {
		String[] command = {
				"mkdir",
				cd.getAbsolutePath() + "/" + name
		};

		Process p;
		int exit;
		try {
			p = Runtime.getRuntime().exec(command);
			exit = p.waitFor();
		} catch (IOException | InterruptedException e) {
			return FileManager.IOERROR;
		}

		if(exit == 0)
			return 0;

		return FileManager.IOERROR;
	}
	
	public static String[] ls(File cd) {
		return FileManager.ls(cd, true);
	}
	
	public static String[] ls(File f, boolean showHidden) {
		File[] content;
        content = f.listFiles();

        Arrays.sort(content, new Comparator<File>() {
            @Override
            public int compare(File lhs, File rhs) {
                if(lhs.isDirectory() && !rhs.isDirectory())
                    return -1;
                if(rhs.isDirectory() && !lhs.isDirectory())
                    return 1;

                return Compare.alphabeticCompare(lhs.getName(), rhs.getName());
            }
        });

        ArrayList<String> files = new ArrayList<>();
		for(File file : content)
			if(!file.isHidden() || showHidden)
				files.add(file.getName());
		
		return files.toArray(new String[files.size()]);
	}
	
	public static int cp(File input, File output) {
		if(input == null || output == null)
			return FileManager.FILE_NOTFOUND;
		
		if(!output.isDirectory())
			return FileManager.ISFILE;

		String[] command = {
				"cp",
				input.getAbsolutePath(),
				output.getAbsolutePath()
		};

		Process p;
		int exit;
		try {
			p = Runtime.getRuntime().exec(command);
			exit = p.waitFor();
		} catch (IOException | InterruptedException e) {
			return FileManager.IOERROR;
		}

		if(exit == 0)
			return 0;
		
		return FileManager.IOERROR;
	}
	
	public static int openFile(Context c, File file) {
		if(file == null)
			return FileManager.FILE_NOTFOUND;
		if(file.isDirectory()) 
			return FileManager.ISDIRECTORY;
		
		Intent intent = DeviceTuils.openFile(file);

		c.startActivity(intent);
		return 0;
	}
	
}
