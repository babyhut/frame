package com.frame.project.sharedpreferences;

import android.content.Context;

/**
 * 
 * @author HDD
 * @data 2015年12月17日
 * @time 下午11:main_work_normal:41
 * @email please click here
 * @tel 有问题请联系13915996320 @ classname SharedPreferences工具类
 */
public interface ISharedPreferencesDao {
	/**
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public void setStringParam(Context context, String key, String value);

	/**
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public void setBooleanParam(Context context, String key, boolean value);

	/**
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public void setIntParam(Context context, String key, int value);

	/**
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public void setLongParam(Context context, String key, long value);

	public String getStringParam(Context context, String key);

	public boolean getBooleanParam(Context context, String key);

	public int getIntParam(Context context, String key);

	public long getLongParam(Context context, String key);

	public void cleanSharePreferences(Context context);

	public boolean isContains(Context context, String key);
}
