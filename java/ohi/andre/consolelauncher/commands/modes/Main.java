package ohi.andre.consolelauncher.commands.modes;

import ohi.andre.consolelauncher.commands.CommandGroup;
import ohi.andre.consolelauncher.commands.raw.android.about;
import ohi.andre.consolelauncher.commands.raw.android.apps;
import ohi.andre.consolelauncher.commands.raw.android.assoc;
import ohi.andre.consolelauncher.commands.raw.android.call;
import ohi.andre.consolelauncher.commands.raw.android.config;
import ohi.andre.consolelauncher.commands.raw.android.data;
import ohi.andre.consolelauncher.commands.raw.android.flash;
import ohi.andre.consolelauncher.commands.raw.android.refresh;
import ohi.andre.consolelauncher.commands.raw.shared.help;
import ohi.andre.consolelauncher.commands.raw.android.install;
import ohi.andre.consolelauncher.commands.raw.android.next;
import ohi.andre.consolelauncher.commands.raw.android.play;
import ohi.andre.consolelauncher.commands.raw.android.previous;
import ohi.andre.consolelauncher.commands.raw.android.restart;
import ohi.andre.consolelauncher.commands.raw.android.search;
import ohi.andre.consolelauncher.commands.raw.android.share;
import ohi.andre.consolelauncher.commands.raw.android.stop;
import ohi.andre.consolelauncher.commands.raw.android.track;
import ohi.andre.consolelauncher.commands.raw.android.uninstall;
import ohi.andre.consolelauncher.commands.raw.android.wifi;
import ohi.andre.consolelauncher.commands.raw.linux.cd;
import ohi.andre.consolelauncher.commands.raw.linux.cp;
import ohi.andre.consolelauncher.commands.raw.linux.ls;
import ohi.andre.consolelauncher.commands.raw.linux.mkdir;
import ohi.andre.consolelauncher.commands.raw.linux.mv;
import ohi.andre.consolelauncher.commands.raw.linux.open;
import ohi.andre.consolelauncher.commands.raw.linux.pwd;
import ohi.andre.consolelauncher.commands.raw.linux.rm;

public class Main extends CommandGroup {
	
	public Main() {
		super();
		
//		android
		commands.add(new about());
		commands.add(new apps());
		commands.add(new assoc());
		commands.add(new call());
		commands.add(new data());
		commands.add(new config());
		commands.add(new flash());
		commands.add(new help());
		commands.add(new install());
		commands.add(new next());
		commands.add(new play());
		commands.add(new previous());
		commands.add(new refresh());
		commands.add(new restart());
		commands.add(new search());
		commands.add(new share());
		commands.add(new stop());
		commands.add(new track());
		commands.add(new uninstall());
		commands.add(new wifi());
		
//		linux
		commands.add(new cd());
		commands.add(new cp());
		commands.add(new ls());
		commands.add(new mkdir());
		commands.add(new mv());
		commands.add(new open());
		commands.add(new pwd());
		commands.add(new rm());
	}

    @Override
    public String getLabel() {
        return "Launcher";
    }
}
