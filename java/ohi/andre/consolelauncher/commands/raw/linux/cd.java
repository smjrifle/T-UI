package ohi.andre.consolelauncher.commands.raw.linux;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ohi.andre.consolelauncher.MainManager;
import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;

public class cd implements CommandAbstraction {

	@SuppressWarnings("unchecked")
	@Override
	public String exec(ExecInfo info) {
		if(info.args == null || ((List<Object>) info.args).size() <= 0)
			return info.res.getString(R.string.output_filenotfound);
		
		File folder = ((ArrayList<File>) info.args).get(0);
		if(folder == null)
			return info.res.getString(R.string.output_filenotfound);
		if(!folder.isDirectory())
			return info.res.getString(R.string.output_isfile);
		
		info.currentDirectory = folder;
		
		return MainManager.UPDATE_DIRECTORY;
	}

	@Override
	public int helpRes() {
		return R.string.help_cd;
	}

	@Override
	public String getLabel() {
		return "cd";
	}

	@Override
	public int minArgs() {
		return 1;
	}

	@Override
	public int maxArgs() {
		return 1;
	}

	@Override
	public boolean useRealTime() {
		return true;
	}

	@Override
	public int argType() {
		return CommandAbstraction.FILE;
	}

}
