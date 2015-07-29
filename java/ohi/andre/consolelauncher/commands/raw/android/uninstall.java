package ohi.andre.consolelauncher.commands.raw.android;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import android.content.Intent;
import android.net.Uri;

public class uninstall implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		if(info.args == null)
			return info.res.getString(R.string.output_appnotfound);
		
		Uri packageURI = Uri.parse("package:" + info.args);
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		info.context.startActivity(uninstallIntent);
		
		return info.res.getString(R.string.output_uninstalling) + " " + info.args;
	}

	@Override
	public int helpRes() {
		return R.string.help_uninstall;
	}

	@Override
	public String getLabel() {
		return "uninstall";
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
		return CommandAbstraction.PACKAGE;
	}

}
