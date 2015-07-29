package ohi.andre.consolelauncher.commands.raw.config;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import ohi.andre.consolelauncher.tuils.Storage;

public class doubletap implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		String active = Storage.toggleDbTap(info.prefs);
		return info.res.getString(R.string.output_doubletap) + " " + active;
	}

	@Override
	public String getLabel() {
		return "doubletap";
	}

	@Override
	public int helpRes() {
		return R.string.help_doubletap;
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
