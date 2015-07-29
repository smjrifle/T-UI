package ohi.andre.consolelauncher.commands.raw.config;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import ohi.andre.consolelauncher.tuils.Storage;

public class hiddenfiles implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		String active = Storage.toggleShowHidden(info.prefs);
		return info.res.getString(R.string.output_hiddenfilechanged) + " " + active;
	}

	@Override
	public int helpRes() {
		return R.string.help_hiddenfiles;
	}

	@Override
	public String getLabel() {
		return "hiddenfiles";
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
