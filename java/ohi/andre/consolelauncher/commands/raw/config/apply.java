package ohi.andre.consolelauncher.commands.raw.config;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.raw.shared.switcher;

public class apply extends switcher {

	@Override
	public int helpRes() {
		return R.string.help_apply;
	}

	@Override
	public String getLabel() {
		return "apply";
	}

}
