package com.frame.project.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.frame.project.constrants.Constants;


public class SharedPreferencesDaoImpl implements ISharedPreferencesDao {
	 private final String SharedPreferencesNAME = Constants.SHARED_PREFERNECES_NAME;
	 private SharedPreferences sp = null;
	 private Editor editor = null;
	@Override
	public void setStringParam(Context context, String key, String value) {
		initSpAndEd(context);
		editor.putString(key, value);
		editor.apply();
	}

	@Override
	public void setBooleanParam(Context context, String key, boolean value) {
		initSpAndEd(context);
		editor.putBoolean(key, value);
		editor.apply();
	}

	@Override
	public void setIntParam(Context context, String key, int value) {
		initSpAndEd(context);
		editor.putInt(key, value);
		editor.apply();
	}

	@Override
	public void setLongParam(Context context, String key, long value) {
		initSpAndEd(context);
		editor.putLong(key, value);
		editor.apply();
	}

	@Override
	public String getStringParam(Context context, String key) {
		initSp(context);
		return sp.getString(key, "");
	}

	@Override
	public boolean getBooleanParam(Context context, String key) {
		initSp(context);
		return sp.getBoolean(key, false);
	}

	@Override
	public int getIntParam(Context context, String key) {
		initSp(context);
		return sp.getInt(key, 0);
	}

	@Override
	public long getLongParam(Context context, String key) {
		initSp(context);
		return sp.getLong(key, 0l);
	}
	@Override
	public  boolean isContains(Context context,String string){
		initSp(context);
		return sp.contains(string);
	}
	@Override
	public void cleanSharePreferences(Context context) {
		initSpAndEd(context);
		editor.clear();
		editor.apply();
	}

	private void initSp(Context context){
		if(sp==null){
			sp = context.getSharedPreferences(SharedPreferencesNAME, Context.MODE_PRIVATE);
		}
	};
	private void initEditor(){
		if(editor==null){
			editor = sp.edit();
		}
	};
	private void initSpAndEd(Context context){
		initSp(context);
		initEditor();
	};
}
