package ohi.andre.consolelauncher.tuils;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

public class ConsoleSkin {

    public static final int SYSTEM_WALLPAPER = -1;

//	color
	private int deviceInfoTextColor;
	
	private int inputTextColor;
	private int outputTextColor;
	
	private int ramTextColor;
	private int dirTextColor;
	
	private int filesTextColor;
	
	private int bgColor;
	
//	size
	private int deviceInfoTextSize;
	
	private int inputTextSize;
	private int outputTextSize;
	
	private int ramTextSize;
	private int dirTextSize;
	
	private int filesTextSize;
	
//	keys
		public static final String deviceInfoTextColorKey = "deviceInfoTextColor";
	
		public static final String inputTextColorKey = "inputTextColor";
		public static final String outputTextColorKey = "outputTextColor";
	
		public static final String ramTextColorKey = "ramTextColor";
		public static final String dirTextColorKey = "dirTextColor";
		
		public static final String filesTextColorKey = "filesTextColor";
		
		public static final String bgColorKey = "bgColor";
	
//	default
	class Default {
//		color
		public int deviceInfoTextColor = 0xff80deea;
		
		public int inputTextColor = Color.GREEN;
		public int outputTextColor = Color.WHITE;
		
		public int ramTextColor = 0xfff44336;
		public int dirTextColor = 0xffffc107;
		
		public int filesTextColor = Color.WHITE;
		
		public int bgColor = 0xff004d40;
		
//		size
		public int deviceInfoTextSize = 20;
		
		public int inputTextSize = 20;
		public int outputTextSize = 18;
		
		public int ramTextSize = 22;
		public int dirTextSize = 22;
		
		public int filesTextSize = 18;
		
	}
	
	public ConsoleSkin(SharedPreferences prefs) {
		Default def = new Default();
		
		deviceInfoTextColor = prefs.getInt(ConsoleSkin.deviceInfoTextColorKey, def.deviceInfoTextColor);
		
		inputTextColor = prefs.getInt(ConsoleSkin.inputTextColorKey, def.inputTextColor);
		outputTextColor = prefs.getInt(ConsoleSkin.outputTextColorKey, def.outputTextColor);
		
		ramTextColor = prefs.getInt(ConsoleSkin.ramTextColorKey, def.ramTextColor);
		dirTextColor = prefs.getInt(ConsoleSkin.dirTextColorKey, def.dirTextColor);
		
		filesTextColor = prefs.getInt(ConsoleSkin.filesTextColorKey, def.filesTextColor);
		
		bgColor = prefs.getInt(ConsoleSkin.bgColorKey, def.bgColor);
		
		deviceInfoTextSize = def.deviceInfoTextSize;

		inputTextSize = def.inputTextSize;
		outputTextSize = def.outputTextSize;
		
		ramTextSize = def.ramTextSize;
		dirTextSize = def.dirTextSize;
		
		filesTextSize = def.filesTextSize;
	}
	
	public void setupUI(View mainView, TextView devInfo, TextView input, TextView separator, TextView output, 
			TextView ram, TextView dir) {
		if(bgColor != ConsoleSkin.SYSTEM_WALLPAPER)
            mainView.setBackgroundColor(bgColor);

		devInfo.setTextColor(deviceInfoTextColor);
		devInfo.setTextSize(deviceInfoTextSize);
		
		input.setTextColor(inputTextColor);
		input.setTextSize(inputTextSize);
		
		separator.setTextColor(inputTextColor);
		separator.setTextSize(inputTextSize);
		
		output.setTextColor(outputTextColor);
		output.setTextSize(outputTextSize);
		
		ram.setTextColor(ramTextColor);
		ram.setTextSize(ramTextSize);
		
		dir.setTextColor(dirTextColor);
		dir.setTextSize(dirTextSize);
	}
	
	public void setupFileViews(TextView tv) {
		tv.setTextColor(filesTextColor);
		tv.setTextSize(filesTextSize);
		tv.setPadding(0, 0, 0, 0);
	}
	
}
