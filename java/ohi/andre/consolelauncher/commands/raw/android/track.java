package ohi.andre.consolelauncher.commands.raw.android;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;

public class track implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		if(info.player != null && info.playerStarted)
			return info.player.trackInfo();
		else
			return info.res.getString(R.string.output_nomusic);
	}

	@Override
	public String getLabel() {
		return "track";
	}

	@Override
	public int helpRes() {
		return R.string.help_track;
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
