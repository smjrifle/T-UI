package ohi.andre.consolelauncher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.method.ScrollingMovementMethod;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.io.File;

import ohi.andre.consolelauncher.managers.FileManager;
import ohi.andre.consolelauncher.tuils.ConsoleSkin;
import ohi.andre.consolelauncher.tuils.DeviceTuils;
import ohi.andre.consolelauncher.tuils.FileAdapter;
import ohi.andre.consolelauncher.tuils.Storage;
import ohi.andre.consolelauncher.tuils.interfaces.CommandExecuter;
import ohi.andre.consolelauncher.tuils.interfaces.FileOpener;
import ohi.andre.consolelauncher.tuils.listeners.PolicyReceiver;
import ohi.andre.consolelauncher.tuils.listeners.TrashInterfaces;
import ohi.andre.realtimetyping.TextViewManager;

public class UImanager implements OnItemClickListener, OnEditorActionListener, OnTouchListener, OnItemLongClickListener {
	
	private View mainView, focusableView;
	private EditText input;
	private TextView separator;
	private TextView dir, ram, output, deviceInfo;
	private GridView dirContent;
	
	private FileAdapter fileAdapter;
	private TextViewManager tvMgr;
	
	private MemoryInfo memory;
	private ActivityManager activityManager;
	
	private Activity mContext;
	
	private GestureDetector det;
	
	private boolean showHiddenFiles, canDelete = false;
	
	private CommandExecuter trigger;
	private FileOpener opener;

	private InputMethodManager imm;
	
	private DevicePolicyManager policy;
	private ComponentName component;

	private final int DIVIDER = 3;
	private final int MS = 5;
	
	public UImanager(Activity context, View rootView, CommandExecuter tri, FileOpener op) {
		mContext = context;
		
		mainView = rootView;
		focusableView = rootView.findViewById(R.id.id_focusableview);
		
		deviceInfo = (TextView) rootView.findViewById(R.id.id_deviceinfos_view);
		deviceInfo.setText(DeviceTuils.getDeviceName());

		input = (EditText) rootView.findViewById(R.id.id_input_view);
		input.setOnEditorActionListener(this);
		separator = (TextView) rootView.findViewById(R.id.id_separator_view);
		
		output = (TextView) rootView.findViewById(R.id.id_output_view);
		output.setMovementMethod(new ScrollingMovementMethod());
		tvMgr = new TextViewManager(output, MS);
		
		dir = (TextView) rootView.findViewById(R.id.id_device_dir);
		ram = (TextView) rootView.findViewById(R.id.id_device_ram);
		dirContent = (GridView) rootView.findViewById(R.id.id_details_dircontent);
		
		dirContent.setOnItemClickListener(this);
		dirContent.setOnItemLongClickListener(this);
		
		memory = new MemoryInfo();
		activityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
		
		trigger = tri;
		opener = op;
		
		input.setOnTouchListener(this);
		output.setOnTouchListener(this);
	}
	
	public void initUI() {
		SharedPreferences prefs = mContext.getPreferences(0);
		
		ConsoleSkin mSkin = new ConsoleSkin(prefs);
		mSkin.setupUI(mainView, deviceInfo, input, separator, output, ram, dir);
		
		fileAdapter = new FileAdapter(mContext, mSkin);
		dirContent.setAdapter(fileAdapter);
		dirContent.setNumColumns(Storage.readColumns(prefs));
		
		showHiddenFiles = Storage.showHiddenFiles(prefs);
		
		boolean closeOnDbTap = Storage.readDbTap(prefs);
		if(!closeOnDbTap) {
			policy = null;
			component = null;
			det = null;
			return;
		}
		
		if(policy == null) 
			initAdminUtilities();
		
		initDetector();
	}

//  ----------------------------------------------- end of startup methods -----------------------------------------------------
	
	private void initDetector() {
		det = new GestureDetector(mContext, TrashInterfaces.trashGestureListener);

		det.setOnDoubleTapListener(new OnDoubleTapListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                boolean admin = policy.isAdminActive(component);

                if (!admin)
                    requestAdmin();
                else
                    policy.lockNow();

                return true;
            }
        });
	}
	
	private void requestAdmin() {
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);  
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, component);  
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, 
				mContext.getString(R.string.adminrequest_label));  
		mContext.startActivityForResult(intent, 0);  
	}
	
	private void initAdminUtilities() {
		policy = (DevicePolicyManager) mContext.getSystemService(  
				Context.DEVICE_POLICY_SERVICE);  
		component = new ComponentName(mContext, PolicyReceiver.class);  
	}

//  -------------------------------------------- admin & db tap --------------------------------------------------------
	
	public void setOutput(String string, boolean realTime) {
		output.scrollTo(0, 0);
		output.setText("");
		if(realTime)
			tvMgr.newText(string);
		else
			output.setText(string);
	}
	
	public void updateRamDetails() {
		ram.setText("RAM: " + DeviceTuils.ramDetails(activityManager, memory));
	}
	
	public void updateDir(File file) {
		dir.setText(file.getAbsolutePath());
		
		fileAdapter.clear();
		fileAdapter.notifyDataSetChanged();
		
		String[] names;
		try {
			names = FileManager.ls(file, showHiddenFiles);
		} catch (NullPointerException exc) {
			output.setText(R.string.output_ioerror);
			return;
		}

		for(String s : names)
			fileAdapter.add(s);
		
		fileAdapter.notifyDataSetChanged();
	}

//  ------------------------------------------ ui updaters ---------------------------------------------------------------

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId != EditorInfo.IME_ACTION_GO)
            return false;

        if(input.getText().length() == 0)
            return true;

        canDelete = true;

        if(imm == null)
            imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);

        trigger.exec(input.getText().toString());

        return true;
    }

	private void processInputEvent(boolean openKeyboard, boolean focusEnd) {
		input.requestFocus();

		if(canDelete) {
			input.setText("");
			canDelete = false;
		} else if(focusEnd)
			input.setSelection(input.getText().length());

		if(openKeyboard) {
			if(imm == null)
				imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
		}
	}
	
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean b;

        float y = event.getY();

        if (v.getId() == input.getId()) {
            input.onTouchEvent(event);
            processInputEvent(false, false);
            b = true;
        } else if (v.getId() == output.getId()) {
            if (y > output.getHeight() / DIVIDER || event.getAction() != MotionEvent.ACTION_DOWN) {
                output.onTouchEvent(event);
                b = false;
            } else {
                processInputEvent(true, true);
                b = true;
            }
        } else
            b = false;

        return !(!b && det != null) || det.onTouchEvent(event);
    }

//  -------------------------------------------- input processors --------------------------------------------------------

	public void requestVoidViewFocus() {
		focusableView.requestFocus();
	}

//  ------------------------------------------- ui utils ----------------------------------------------------------------

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String newInput;
		String currentInput = input.getText().toString();

        String touched = (String) fileAdapter.getItem(position);

        newInput = currentInput.equals("") ? touched : currentInput + " " + touched;

		input.setText(newInput);
		processInputEvent(true, true);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		String file = (String) fileAdapter.getItem(position);

		opener.onOpenRequest(file);

		return true;
	}

//  ----------------------------------------------- file grid listeners ----------------------------------------------------------

}
