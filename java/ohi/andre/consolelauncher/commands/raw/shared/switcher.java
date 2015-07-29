package ohi.andre.consolelauncher.commands.raw.shared;

import ohi.andre.consolelauncher.MainManager;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;

public abstract class switcher implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		return MainManager.SWITCH;
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
