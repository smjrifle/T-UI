package ohi.andre.consolelauncher.commands.modes;

import ohi.andre.consolelauncher.commands.CommandGroup;
import ohi.andre.consolelauncher.commands.raw.shared.help;
import ohi.andre.consolelauncher.commands.raw.config.apply;
import ohi.andre.consolelauncher.commands.raw.config.bgcolor;
import ohi.andre.consolelauncher.commands.raw.config.devicecolor;
import ohi.andre.consolelauncher.commands.raw.config.dircolor;
import ohi.andre.consolelauncher.commands.raw.config.doubletap;
import ohi.andre.consolelauncher.commands.raw.config.filecolumns;
import ohi.andre.consolelauncher.commands.raw.config.filescolor;
import ohi.andre.consolelauncher.commands.raw.config.hiddenfiles;
import ohi.andre.consolelauncher.commands.raw.config.inputcolor;
import ohi.andre.consolelauncher.commands.raw.config.musicfolder;
import ohi.andre.consolelauncher.commands.raw.config.outputcolor;
import ohi.andre.consolelauncher.commands.raw.config.ramcolor;
import ohi.andre.consolelauncher.commands.raw.config.startupdir;
import ohi.andre.consolelauncher.commands.raw.config.playrandom;

public class Config extends CommandGroup {
	
	public Config() {
		super();
		
		commands.add(new apply());
		commands.add(new bgcolor());
		commands.add(new devicecolor());
		commands.add(new dircolor());
		commands.add(new doubletap());
		commands.add(new filecolumns());
		commands.add(new filescolor());
		commands.add(new hiddenfiles());
		commands.add(new inputcolor());
		commands.add(new musicfolder());
		commands.add(new outputcolor());
		commands.add(new ramcolor());
		commands.add(new startupdir());
		commands.add(new playrandom());

		commands.add(new help());
	}

    @Override
    public String getLabel() {
        return "Config";
    }
}
