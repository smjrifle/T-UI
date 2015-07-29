package ohi.andre.consolelauncher.commands.raw.android;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import ohi.andre.consolelauncher.managers.FileManager;

public class install implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		if(info.args == null || ((List<Object>) info.args).size() <= 0)
			return info.res.getString(R.string.output_filenotfound);
		
		File file = ((ArrayList<File>) info.args).get(0);
		
		if(file != null && !(file.getName().endsWith(".apk") || file.getName().endsWith(".APK")))
			return info.res.getString(R.string.output_invalidapk);
		
		int output = FileManager.openFile(info.context, file);
		if(output == FileManager.FILE_NOTFOUND)
			return info.res.getString(R.string.output_filenotfound);
		if(output == FileManager.ISDIRECTORY)
			return info.res.getString(R.string.output_isdirectory);
		
		return info.res.getString(R.string.output_installing) + " " + file.getName();
	}

	@Override
	public int helpRes() {
		return R.string.help_install;
	}

	@Override
	public String getLabel() {
		return "install";
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
