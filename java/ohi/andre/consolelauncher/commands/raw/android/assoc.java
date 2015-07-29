package ohi.andre.consolelauncher.commands.raw.android;

import java.util.ArrayList;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import ohi.andre.consolelauncher.managers.AssocManager;

public class assoc implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		@SuppressWarnings("unchecked")
		int result = info.assExpl.onNewAssoc((ArrayList<String>) info.args);

		if(result == 0)
			return info.res.getString(R.string.output_createdassoc);

		if(result == AssocManager.ASSOC_ARGS) {
			String output = info.assExpl.printAssocs();
			if(output.equals(""))
				return info.res.getString(helpRes());
			return output;
		}

		if(result == AssocManager.ASSOC_EXISTS)
			return info.res.getString(R.string.output_existingassoc);

		if(result == AssocManager.ASSOC_INVALID)
			return info.res.getString(R.string.output_invalidassoc);
		
		if(result == AssocManager.ASSOC_DELETE)
			return info.res.getString(R.string.output_assocdeleted);

		return null;
	}

	@Override
	public int helpRes() {
		return R.string.help_assoc;
	}

	@Override
	public String getLabel() {
		return "assoc";
	}

	@Override
	public int minArgs() {
		return 0;
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
