package ohi.andre.consolelauncher.commands.raw.android;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;

public class about implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		return info.res.getString(R.string.output_about);
	}

	@Override
	public int helpRes() {
		return R.string.help_about;
	}

	@Override
	public String getLabel() {
		return "about";
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
