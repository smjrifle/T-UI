package ohi.andre.consolelauncher.commands.raw.linux;

import java.io.File;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import ohi.andre.consolelauncher.managers.FileManager;

public class ls implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		File file = info.currentDirectory;
		String[] names;
		try {
			names = FileManager.ls(file);
		} catch (NullPointerException exc) {
			names = new String[0];
		}
		
		String output = "";
		for(String s : names) 
			output = output.concat(s + "\n");
		
		return output;
	}

	@Override
	public int helpRes() {
		return R.string.help_ls;
	}

	@Override
	public String getLabel() {
		return "ls";
	}

	@Override
	public int minArgs() {
		return 0;
	}

	@Override
	public int maxArgs() {
		return 0;
	}

	@Override
	public boolean useRealTime() {
		return true;
	}

	@Override
	public int argType() {
		return CommandAbstraction.NOTHING;
	}

}
