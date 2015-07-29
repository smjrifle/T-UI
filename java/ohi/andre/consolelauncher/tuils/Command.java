package ohi.andre.consolelauncher.tuils;

import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import ohi.andre.consolelauncher.tuils.CommandTuils.ArgInfo;

public class Command {
	
	private CommandAbstraction cmd;
	private ArgInfo mArgs;
	
	public Command(CommandAbstraction cmdAbs, ArgInfo args) {
		mArgs = args;
		cmd = cmdAbs;
	}
	
	public String exec(ExecInfo info) {
		if(cmd.minArgs() > 0)
			if(mArgs == null || cmd.minArgs() > mArgs.n)
				return info.res.getString(cmd.helpRes());
		if(cmd.maxArgs() != CommandAbstraction.UNDEFINIED) 
			if(mArgs != null && mArgs.n > cmd.maxArgs()) 
				return null;
		
		info.args = mArgs.arg;
		
		String output = cmd.exec(info);
		
		info.clear();
		
		return output;
	}
	
	public boolean useRealTimeTyping() {
		return cmd.useRealTime();
	}
}
