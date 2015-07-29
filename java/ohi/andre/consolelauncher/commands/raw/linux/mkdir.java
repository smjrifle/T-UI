package ohi.andre.consolelauncher.commands.raw.linux;

import ohi.andre.consolelauncher.MainManager;
import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import ohi.andre.consolelauncher.managers.FileManager;

public class mkdir implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		if(info.args == null || ((String) info.args).contains("/"))
			return info.res.getString(R.string.output_invalidarg);
		
		int result = FileManager.mkdir(info.currentDirectory, (String) info.args);
		
		if(result == FileManager.IOERROR)
			return info.res.getString(R.string.output_ioerror);
		
		return MainManager.UPDATE_DIRECTORY;
	}

	@Override
	public int helpRes() {
		return R.string.help_mkdir;
	}

	@Override
	public String getLabel() {
		return "mkdir";
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
		return CommandAbstraction.PLAIN_TEXT;
	}

}
