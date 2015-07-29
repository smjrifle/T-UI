package ohi.andre.consolelauncher.commands.raw.android;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import android.content.Intent;
import android.net.Uri;

public class share implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		if(info.args == null || ((List<Object>)info.args).size() <= 0)
			return info.res.getString(R.string.output_filenotfound);
		
		File f = ((ArrayList<File>) info.args).get(0);
		if(f.isDirectory())
			return info.res.getString(R.string.output_isdirectory);
		
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		Uri uri = Uri.fromFile(f);
		sharingIntent.setType("*/*");
		sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
		info.context.startActivity(Intent.createChooser(sharingIntent, 
				info.res.getString(R.string.share_label)));
		
		return info.res.getString(R.string.sharing) + " " + f.getName();
	}

	@Override
	public String getLabel() {
		return "share";
	}

	@Override
	public int helpRes() {
		return R.string.help_share;
	}

	@Override
	public int minArgs() {
		return 1;
	}

	@Override
	public int maxArgs() {
		return CommandAbstraction.UNDEFINIED;
	}

	@Override
	public boolean useRealTime() {
		return true;
	}

	@Override
	public int argType() {
		return CommandAbstraction.FILE;
	}

}
