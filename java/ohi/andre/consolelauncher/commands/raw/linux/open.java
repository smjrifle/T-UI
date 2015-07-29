package ohi.andre.consolelauncher.commands.raw.linux;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import ohi.andre.consolelauncher.managers.FileManager;

public class open implements CommandAbstraction {

	@SuppressWarnings("unchecked")
	@Override
	public String exec(ExecInfo info) {
		if(info.args == null || ((List<Object>) info.args).size() <= 0)
			return info.res.getString(R.string.output_filenotfound);
		
		File file = ((ArrayList<File>) info.args).get(0);
		
		int result = FileManager.openFile(info.context, file);
		
		if(result == FileManager.FILE_NOTFOUND)
			return info.res.getString(R.string.output_filenotfound);
		if(result == FileManager.ISDIRECTORY)
			return info.res.getString(R.string.output_isdirectory);
		if(result == FileManager.IOERROR)
			return info.res.getString(R.string.output_ioerror);
		
		return info.res.getString(R.string.output_opening) + " " + file.getName();
	}

	@Override
	public int helpRes() {
		return R.string.help_open;
	}

	@Override
	public String getLabel() {
		return "open";
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
