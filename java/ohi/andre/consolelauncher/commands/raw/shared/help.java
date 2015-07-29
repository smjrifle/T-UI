package ohi.andre.consolelauncher.commands.raw.shared;

import java.util.ArrayList;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;

public class help implements CommandAbstraction {

	@SuppressWarnings("unchecked")
	@Override
	public String exec(ExecInfo info) {
		if(info.args == null || ((ArrayList<String>) info.args).size() == 0) 
			return info.active.printCommands();
		
		CommandAbstraction cmd = info.active.getCommandByName(((ArrayList<String>) info.args).get(0));
		if(cmd == null)
			return info.res.getString(R.string.output_commandnotfound);
		return info.res.getString(cmd.helpRes());
	}
	
	@Override
	public int helpRes() {
		return R.string.help_help;
	}

	@Override
	public String getLabel() {
		return "help";
	}

	@Override
	public int minArgs() {
		return 0;
	}

	@Override
	public int maxArgs() {
		return 1;
	}

	@Override
	public boolean useRealTime() {
		return false;
	}

	@Override
	public int argType() {
		return CommandAbstraction.TEXTLIST;
	}

}
