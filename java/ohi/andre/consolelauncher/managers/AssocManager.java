package ohi.andre.consolelauncher.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import ohi.andre.consolelauncher.commands.modes.Main;
import ohi.andre.consolelauncher.tuils.Storage;
import ohi.andre.consolelauncher.tuils.interfaces.AssocExplorer;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

public class AssocManager implements AssocExplorer {

	private HashMap<String, String> assocs;
	
	public static final int ASSOC_EXISTS = 10;
	public static final int ASSOC_INVALID = 11;
	public static final int ASSOC_ARGS = 12;
	public static final int ASSOC_DELETE = 13;
	
	private static final String DELETE_MARKER = "-rm";
	
	public AssocManager(SharedPreferences prefs) {
		assocs = Storage.assocMap(prefs);
	}

//  ---------------------------------------- constructor ----------------------------------------------------------------------

    @SuppressLint("DefaultLocale")
    @Override
    public int onNewAssoc(ArrayList<String> strings) {
        if(strings == null || strings.size() == 0)
            return AssocManager.ASSOC_ARGS;

        String first = strings.get(0);
        if(first.toLowerCase().equals(AssocManager.DELETE_MARKER)) {
            String assDel = strings.get(1);
            assocs.remove(assDel);
            return AssocManager.ASSOC_DELETE;
        }

        if(assocs.containsKey(first))
            return AssocManager.ASSOC_EXISTS;

        Main main = new Main();
        if(main.getCommandByName(first) != null)
            return AssocManager.ASSOC_INVALID;

        String args = "";
        strings.remove(0);
        for(int count = 0; count < strings.size(); count++) {
            args = args.concat(strings.get(count) + " ");
        }

        assocs.put(first, args);

        return 0;
    }

    @Override
    public String printAssocs() {
        Iterator<Entry<String, String>> iterator = assocs.entrySet().iterator();

        String output = "";
        Entry<String, String> entry;
        while(iterator.hasNext()) {
            entry = iterator.next();
            output = output.concat(entry.getKey() + " = " + entry.getValue() + "\n");
        }

        return output;
    }

//  -------------------------------------------------- assoc explorer ------------------------------------------------------

	public String getAssoc(String assoc) {
		if(!assocs.containsKey(assoc))
			return null;
		
		return assocs.get(assoc);
	}
	
	public void save(SharedPreferences.Editor edit) {
		Storage.saveAssocs(edit, assocs);
	}

}
