package ohi.andre.consolelauncher.commands.raw.android;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import ohi.andre.consolelauncher.managers.ContactManager;

/**
 * Created by andre on 25/07/15.
 */
public class refresh implements CommandAbstraction {

    @Override
    public String exec(ExecInfo info) {
        info.player.init(info.prefs);

        info.contacts = new ContactManager(info.context);

        return info.res.getString(R.string.output_refresh);
    }

    @Override
    public String getLabel() {
        return "refresh";
    }

    @Override
    public int helpRes() {
        return R.string.help_refresh;
    }

    @Override
    public boolean useRealTime() {
        return true;
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
    public int argType() {
        return CommandAbstraction.NOTHING;
    }
}
