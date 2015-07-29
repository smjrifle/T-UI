package ohi.andre.consolelauncher.commands.raw.android;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import android.content.Intent;
import android.net.Uri;

public class call implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		if(info.args == null) 
			return info.res.getString(R.string.output_numbernotfound);
		
		Uri uri = Uri.parse("tel:" + info.args);
		Intent intent = new Intent(Intent.ACTION_CALL, uri);
		info.context.startActivity(intent);
		
		return info.res.getString(R.string.calling) + " " + info.args;
	}

	@Override
	public String getLabel() {
		return "call";
	}

	@Override
	public int helpRes() {
		return R.string.help_call;
	}

	@Override
	public int minArgs() {
		return 1;
	}

	@Override
	public int maxArgs() {
		return 1;
	}

	@Override
	public boolean useRealTime() {
		return true;
	}

	@Override
	public int argType() {
		return CommandAbstraction.CONTACTNUMBER;
	}

}
