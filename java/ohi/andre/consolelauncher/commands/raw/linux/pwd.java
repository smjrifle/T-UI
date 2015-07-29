package ohi.andre.consolelauncher.commands.raw.linux;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;

public class pwd implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		return info.currentDirectory.getAbsolutePath();
	}

	@Override
	public int helpRes() {
		return R.string.help_pwd;
	}

	@Override
	public String getLabel() {
		return "pwd";
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
