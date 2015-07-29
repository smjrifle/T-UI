package ohi.andre.consolelauncher.commands.raw.config;

import java.io.File;
import java.util.List;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import ohi.andre.consolelauncher.tuils.Storage;
import android.content.SharedPreferences;

public class startupdir implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		if(info.args == null || ((List<Object>) info.args).size() <= 0)
			return info.res.getString(R.string.output_filenotfound);
		
		File folder = ((List<File>) info.args).get(0);
		if(!folder.isDirectory())
			return info.res.getString(R.string.output_isfile);
		
		SharedPreferences.Editor edit = info.prefs.edit();
		Storage.writeInitialPath(edit, folder.getAbsolutePath());
		edit.commit();
		
		return info.res.getString(R.string.output_startupdirmodified);
	}

	@Override
	public int helpRes() {
		return R.string.help_startupdir;
	}

	@Override
	public String getLabel() {
		return "startupdir";
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
