package ohi.andre.consolelauncher.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ohi.andre.comparestring.Compare;

public abstract class CommandGroup {
	
	protected ArrayList<CommandAbstraction> commands;
    private boolean sorted = false;
	
	protected CommandGroup() {
		commands = new ArrayList<>();
	}
	
	public String printCommands() {
        if(!sorted) {
            sorted = true;
            sort();
        }

        String output = "";
        for(CommandAbstraction cmd : commands)
			output = output.concat(cmd.getLabel() + "\n");
		
		return output;
	}
	
	public CommandAbstraction getCommandByName(String name) {
		for(int count = 0; count < commands.size(); count++) {
			CommandAbstraction cmd = commands.get(count);
			if(name.equals(cmd.getLabel()))
				return cmd;
		}
		return null;
	}
	
    private void sort() {
        Collections.sort(commands, new Comparator<CommandAbstraction>() {
            @Override
            public int compare(CommandAbstraction lhs, CommandAbstraction rhs) {
                return Compare.alphabeticCompare(lhs.getLabel(), rhs.getLabel());
            }
        });
    }

    public abstract String getLabel();
	
}
