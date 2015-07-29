package ohi.andre.consolelauncher.tuils;

import android.annotation.SuppressLint;
import android.graphics.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import ohi.andre.comparestring.Compare;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import ohi.andre.consolelauncher.commands.raw.config.bgcolor;
import ohi.andre.consolelauncher.managers.ContactManager;

@SuppressLint("DefaultLocale")
public class CommandTuils {

	public static Command parse(String input, ExecInfo info) {
		input = CommandTuils.trimSpaces(input);
		String name = CommandTuils.findName(input);
		if(!CommandTuils.isAlpha(name))
			return null;
		
		CommandAbstraction cmd = info.active.getCommandByName(name);
		if(cmd == null)
			return null;

		input = input.substring(name.length());
		input = CommandTuils.trimSpaces(input);

        ArgInfo arg = null;
        if(input.length() > 0) {
            int type = cmd.argType();
            if (type == CommandAbstraction.FILE)
                arg = CommandTuils.file(input, info.currentDirectory, cmd.minArgs());
            else if (type == CommandAbstraction.CONTACTNUMBER)
                arg = CommandTuils.contactNumber(input, info.contacts);
            else if (type == CommandAbstraction.PLAIN_TEXT)
                arg = CommandTuils.plainText(input);
            else if (type == CommandAbstraction.PACKAGE)
                arg = CommandTuils.packageName(input, info.appExpl.getApps());
            else if (type == CommandAbstraction.COLOR)
                arg = CommandTuils.color(input);
            else if (type == CommandAbstraction.TEXTLIST)
                arg = CommandTuils.textList(input);
            else if (type == CommandAbstraction.INTEGER)
                arg = CommandTuils.integer(input);
        } else
            arg = new ArgInfo(null, 0);

        return new Command(cmd, arg);
	}
	
	private static String findName(String input) {
		int space = input.indexOf(" ");
		
		input = input.toLowerCase();
		
		if(space == -1)
			return input; 
		else
			return input.substring(0, space);
	}
	
	private static ArgInfo plainText(String input) {
		return new ArgInfo(input, 1);
	}
	
	private static ArgInfo textList(String input) {
		ArrayList<String> strings = new ArrayList<>();
		
		char[] chars = input.toCharArray();
		String arg = "";
        for (char c : chars) {
            if (c == ' ')
                if (arg.length() > 0) {
                    strings.add(arg);
                    arg = "";
                    continue;
                }

            arg = arg.concat(c + "");
        }
		
		if(arg.length() > 0) 
			strings.add(arg);
		
		return new ArgInfo(strings, strings.size());
	}
	
	private static ArgInfo integer(String input) {
		try {
			return new ArgInfo(Integer.parseInt(input), 1);
		} catch(NumberFormatException e) {
			return null;
		}
	}
	
	private static ArgInfo packageName(String input, HashMap<String, String> apps) {
		if(input.contains(".")) 
			if(apps.containsValue(input))
				return new ArgInfo(input, 1);
		
		String mostSuitable = Compare.compare(apps.keySet(), input);
		if(mostSuitable != null)
			return new ArgInfo(apps.get(mostSuitable), 1);
		return null;
	}
	
	private static ArgInfo contactNumber(String input, ContactManager contacts) {
		String number = null;
		
		if(CommandTuils.isNumber(input))
			number = input;
		else {
			String mostSuitable = Compare.compare(contacts.names(), input);
			if(mostSuitable != null)
				number = contacts.getNumber(mostSuitable);
		}
		return new ArgInfo(number, 1); 
	}
	
	private static ArgInfo color(String input) {
		try {
			int color = input.startsWith("#") ? Color.parseColor(input) : Color.parseColor("#" + input);
			return new ArgInfo(color, 1);
		} catch(IllegalArgumentException e) {
            if(input.equals(bgcolor.SYSTEM_WALLPAPER_ACTIVATOR))
                return new ArgInfo(ConsoleSkin.SYSTEM_WALLPAPER, 1);
            return null;
		}
	}
	
	private static ArgInfo file(String input, File cd, int def) {
		ArrayList<File> files = new ArrayList<>();
		
		char[] chars = input.toCharArray();


        String arg = "";
        for (char c : chars) {
            if (c != ' ') {
                arg = arg.concat(Character.toString(c));
                continue;
            }

            String path = CommandTuils.absolutePath(arg, cd);
            if (path != null) {
                files.add(new File(path));
                arg = "";
            } else
                arg = arg.concat(" ");
        }
		
		String path = CommandTuils.absolutePath(arg, cd);
		if(path != null) 
			files.add(new File(path));

        int size;
        if(files.size() <= 0)
            size = def;
        else
            size = files.size();

		return new ArgInfo(files, size);
	}

	private static String absolutePath(String path, File cd) {
		File f;
		
		if(path.startsWith("/"))
			f = new File(path);

		else {
			while(path.startsWith("..")) {
				cd = cd.getParentFile();

				try {
					path = path.substring(3);
				} catch (IndexOutOfBoundsException exc) {
					path = "";
					break;
				}
			}

			f = new File(cd.getAbsolutePath() + "/" + path);
		}
		
		if(f.exists())
			return f.getAbsolutePath();
		
		return null;
	}
	
	private static boolean isAlpha(String s) {
	    char[] chars = s.toCharArray();

	    for (char c : chars) 
	        if(!Character.isLetter(c)) 
	            return false;

	    return true;
	}
	
	private static boolean isNumber(String s) {
	    char[] chars = s.toCharArray();

	    for (char c : chars) 
	        if(Character.isLetter(c)) 
	            return false;

	    return true;
	}
	
	private static String trimSpaces(String s) {
		while(s.startsWith(" ")) 
			s = s.substring(1);
		while(s.endsWith(" ")) 
			s = s.substring(0, s.length() - 1);
		return s;
	}
	
	public static class ArgInfo {
		public Object arg;
		public int n;
		
		public ArgInfo(Object o, int i) {
			this.arg = o;
			this.n = i;
		}
	}
	
}
