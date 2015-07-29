package ohi.andre.consolelauncher.commands.raw.config;

import android.content.SharedPreferences;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import ohi.andre.consolelauncher.tuils.Storage;

public class ramcolor implements CommandAbstraction {

	@Override
	public int helpRes() {
		return R.string.help_ramcolor;
	}

    @Override
    public boolean useRealTime() {
        return true;
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
    public int argType() {
        return CommandAbstraction.COLOR;
    }

    @Override
    public String exec(ExecInfo info) {
        if(info.args == null)
            return info.res.getString(R.string.output_invalidarg);

        int color = (int) info.args;

        SharedPreferences.Editor editor = info.prefs.edit();
        Storage.setRamColor(editor, color);
        editor.commit();

        return info.res.getString(R.string.output_skinchanged) + " " + getLabel();
    }

    @Override
	public String getLabel() {
		return "ramcolor";
	}

}
