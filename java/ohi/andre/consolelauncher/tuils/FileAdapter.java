package ohi.andre.consolelauncher.tuils;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FileAdapter extends BaseAdapter {
	
	private ArrayList<String> files;
	private Context mContext;
	private ConsoleSkin mSkin;
	
	public FileAdapter(Context context, ConsoleSkin skin) {
		files = new ArrayList<>();
		
		mContext = context;
		mSkin = skin;
	}
	
	public void clear() {
		files.clear();
	}
	
	public void add(String string) {
		files.add(string);
	}
	
	@Override
	public int getCount() {
		return files.size();
	}

	@Override
	public Object getItem(int position) {
		return files.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = new TextView(mContext);
			mSkin.setupFileViews((TextView) convertView);
		}
		
		((TextView) convertView).setText((String) getItem(position));
		
		return convertView;
	}

}
