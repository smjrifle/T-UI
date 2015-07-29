package ohi.andre.consolelauncher.managers;

import ohi.andre.consolelauncher.R;
import android.content.res.Resources;

public class TutorialManager {
	
	private String[] suggestSet;

	public TutorialManager(Resources res) {
		suggestSet = res.getStringArray(R.array.launcher_tutorial);
	}
	
	public String requestTutorial(int index) {
		if(index < suggestSet.length)
			return suggestSet[index];
		
		return null;
	}
}
