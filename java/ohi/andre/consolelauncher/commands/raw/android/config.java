package ohi.andre.consolelauncher.commands.raw.android;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.raw.shared.switcher;

public class config extends switcher {

	@Override
	public int helpRes() {
		return R.string.help_config;
	}

	@Override
	public String getLabel() {
		return "config";
	}

}
