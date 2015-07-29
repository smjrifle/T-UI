package ohi.andre.consolelauncher.commands.raw.android;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;

public class apps implements CommandAbstraction {
	
	@Override
	public String exec(ExecInfo info) {
		return info.appExpl.printApps();
	}

	@Override
	public int helpRes() {
		return R.string.help_apps;
	}

	@Override
	public String getLabel() {
		return "apps";
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
		return false;
	}

	@Override
	public int argType() {
		return CommandAbstraction.NOTHING;
	}

}
