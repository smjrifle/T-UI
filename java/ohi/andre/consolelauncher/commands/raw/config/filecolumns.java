package ohi.andre.consolelauncher.commands.raw.config;

import android.content.SharedPreferences;
import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import ohi.andre.consolelauncher.tuils.Storage;

public class filecolumns implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		if(info.args == null)
			return info.res.getString(R.string.output_invalidarg);
		
		int value = (int) info.args;
		
		SharedPreferences.Editor edit = info.prefs.edit();
		Storage.writeColumns(edit, value);
		edit.commit();
		
		return info.res.getString(R.string.output_filecolumns) + " " + value;
	}

	@Override
	public String getLabel() {
		return "filecolumns";
	}

	@Override
	public int helpRes() {
		return R.string.help_filecolumns;
	}

	@Override
	public boolean useRealTime() {
		return true;
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
	public int argType() {
		return CommandAbstraction.INTEGER;
	}

}
