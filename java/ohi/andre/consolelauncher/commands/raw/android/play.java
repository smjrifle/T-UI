package ohi.andre.consolelauncher.commands.raw.android;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import ohi.andre.consolelauncher.managers.MusicManager.Status;

public class play implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		if(!info.playerStarted) {
			if(info.player.initPlayer())
				info.playerStarted = true;
			else
				return info.res.getString(R.string.output_musicfoldererror);
		}
		
		Status s = info.player.play();
		String output;
		if(s.playing)
			output = info.res.getString(R.string.output_playing) + " ";
		else
			output = info.res.getString(R.string.output_paused) + " ";
		
		output = output.concat(s.song);
		
		return output;
	}

	@Override
	public String getLabel() {
		return "play";
	}

	@Override
	public int helpRes() {
		return R.string.help_play;
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
