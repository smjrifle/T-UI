package ohi.andre.consolelauncher.commands;

public interface CommandAbstraction {
	
//	undefinied n of arguments
	int UNDEFINIED = -1;

//	plain text (google search, ..)
	int PLAIN_TEXT = 10;
//	file arg
	int FILE = 11;
//	package
	int PACKAGE = 12;
//	contact
	int CONTACTNUMBER = 13;
//	color
	int COLOR = 14;
//	list of text
	int TEXTLIST = 15;
//	integer
	int INTEGER = 16;
//	nothing
	int NOTHING = CommandAbstraction.TEXTLIST;
	
	String exec(ExecInfo info);

	String getLabel();
	int helpRes();
	boolean useRealTime();
	
	int minArgs();
	int maxArgs();
	int argType();
}
