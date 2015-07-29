package ohi.andre.consolelauncher.commands.raw.android;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecInfo;
import android.content.Context;
import android.net.wifi.WifiManager;

public class wifi implements CommandAbstraction {

	@Override
	public String exec(ExecInfo info) {
		if(info.wifi == null)
			info.wifi = (WifiManager) info.context.getSystemService(Context.WIFI_SERVICE); 
		boolean active = !info.wifi.isWifiEnabled();
		info.wifi.setWifiEnabled(active);
		return info.res.getString(R.string.output_wifi) + " " + Boolean.toString(active);
	}

	@Override
	public String getLabel() {
		return "wifi";
	}

	@Override
	public int helpRes() {
		return R.string.help_wifi;
	}

	@Override
	public int minArgs() {
		return 0;
	}

	@Override
	public int maxArgs() {
		return 0;
	}

	@Override
	public boolean useRealTime() {
		return true;
	}

	@Override
	public int argType() {
		return CommandAbstraction.NOTHING;
	}

}
