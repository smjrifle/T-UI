package ohi.andre.consolelauncher.commands.raw.config;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import ohi.andre.consolelauncher.tuils.Storage;

public class playrandom implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		boolean active = Storage.toggleRadom(info.prefs);
		return info.res.getString(R.string.output_togglerandom) + " " + Boolean.toString(active);
	}

	@Override
	public String getLabel() {
		return "playrandom";
	}

	@Override
	public int helpRes() {
		return R.string.help_togglerandom;
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
