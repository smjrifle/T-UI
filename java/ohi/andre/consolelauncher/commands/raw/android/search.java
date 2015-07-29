package ohi.andre.consolelauncher.commands.raw.android;

import java.util.ArrayList;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import android.content.Intent;
import android.net.Uri;

public class search implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		@SuppressWarnings("unchecked")
		ArrayList<String> args = (ArrayList<String>) info.args;
		
		String toSearch = "";
		for(int count = 0; count < args.size(); count++) {
			toSearch = toSearch.concat(args.get(count));
			if(count < args.size() - 1)
				toSearch = toSearch.concat("+");
		}
		
		String flat = "";
		for(String s : args)
			flat = flat.concat(s + " ");

		Uri uri = Uri.parse("http://www.google.com/#q=" + toSearch);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		info.context.startActivity(intent);
		
		return info.res.getString(R.string.searching) + " " + flat;
	}

	@Override
	public int helpRes() {
		return R.string.help_search;
	}

	@Override
	public String getLabel() {
		return "search";
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
		return CommandAbstraction.TEXTLIST;
	}

}
