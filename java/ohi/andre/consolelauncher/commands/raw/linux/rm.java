package ohi.andre.consolelauncher.commands.raw.linux;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ohi.andre.consolelauncher.MainManager;
import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import ohi.andre.consolelauncher.managers.FileManager;

public class rm implements CommandAbstraction {

	@SuppressWarnings("unchecked")
	@Override
	public String exec(ExecInfo info) {
		if(info.args == null || ((List<Object>) info.args).size() <= 0)
			return info.res.getString(R.string.output_filenotfound);
		
		File file = ((ArrayList<File>) info.args).get(0);
        Log.e("andre", file.getAbsolutePath());

		int result = FileManager.rm(file);
		if(result == FileManager.FILE_NOTFOUND)
			return info.res.getString(R.string.output_filenotfound);
		if(result == FileManager.IOERROR)
			return info.res.getString(R.string.output_ioerror);
		
		return MainManager.UPDATE_DIRECTORY;
	}

	@Override
	public int helpRes() {
		return R.string.help_rm;
	}

	@Override
	public String getLabel() {
		return "rm";
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
