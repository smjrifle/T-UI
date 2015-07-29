package ohi.andre.consolelauncher.commands.raw.android;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;

public class restart implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		if(!info.playerStarted) {
			if(info.player.initPlayer())
				info.playerStarted = true;
			else
				return info.res.getString(R.string.output_musicfoldererror);
		}
		
		String s = info.player.restart();
		return info.res.getString(R.string.output_playing) + " " + s;
	}

	@Override
	public String getLabel() {
		return "restart";
	}

	@Override
	public int helpRes() {
		return R.string.help_restart;
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
