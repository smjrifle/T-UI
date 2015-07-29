package ohi.andre.consolelauncher.commands.raw.linux;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ohi.andre.consolelauncher.MainManager;
import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import ohi.andre.consolelauncher.managers.FileManager;

public class mv implements CommandAbstraction {

	@SuppressWarnings("unchecked")
	@Override
	public String exec(ExecInfo info) {
		if(info.args == null || ((List<Object>) info.args).size() <= 0)
			return info.res.getString(R.string.output_filenotfound);
		
		File input = ((ArrayList<File>) info.args).get(0);
		File output = ((ArrayList<File>) info.args).get(1);
		
		int result = FileManager.mv(input, output);
		
		if(result == FileManager.FILE_NOTFOUND)
			return info.res.getString(R.string.output_filenotfound);
		if(result == FileManager.IOERROR)
			return info.res.getString(R.string.output_ioerror);
		if(result == FileManager.ISFILE)
			return info.res.getString(R.string.output_isfile);
		
		return MainManager.UPDATE_DIRECTORY;
	}

	@Override
	public int helpRes() {
		return R.string.help_mv;
	}

	@Override
	public String getLabel() {
		return "mv";
	}

	@Override
	public int minArgs() {
		return 2;
	}

	@Override
	public int maxArgs() {
		return 2;
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
