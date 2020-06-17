package com.test.sate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBUtils {

	private static DBUtils dbUtils;
	private SQLiteDatabase db;

	/**
	 *
	 * @return
	 */
	public static DBUtils getInstance() {
		if (dbUtils == null) {
			dbUtils = new DBUtils();
			return dbUtils;
		}
		return dbUtils;
	}

	/**
	 *
	 * 
	 * @param contenxt
	 *
	 */
	public void creads(Context contenxt) {
		String path = contenxt.getCacheDir().getPath() + "/sate.db";
		db = SQLiteDatabase.openOrCreateDatabase(path, null);
		String satesql = "create table if not exists sates"
				+ "(id integer primary key autoincrement,"
				+ "name text,date text,status text,username text)";

		String usersql = "create table if not exists users"
				+ "(id integer primary key autoincrement,"
				+ "name text,psw text)";

		String satesql1 = "create table if not exists sates1"
				+ "(id integer primary key autoincrement,"
				+ "name text,date text,status text,username text)";

		db.execSQL(satesql);//执行sql语句
		db.execSQL(usersql);//
		db.execSQL(satesql1);//
	}

	//添加 场地
	public long inisertSate(Sate sate){
			ContentValues contentValues = new ContentValues();
			contentValues.put("name", sate.getName());
			contentValues.put("date", sate.getDate());
		    contentValues.put("status", sate.getStatus());
			long dataSize = db.insert("sates", null, contentValues);
			return dataSize;
	}

	//添加 场地
	public long inisertSate1(Sate sate){
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", sate.getName());
		contentValues.put("date", sate.getDate());
		contentValues.put("status", sate.getStatus());
		long dataSize = db.insert("sates1", null, contentValues);
		return dataSize;
	}


	//添加学生
	public long inisertUser(User user){
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", user.getName());
		contentValues.put("psw", user.getPsw());
		long dataSize = db.insert("users", null, contentValues);
		return dataSize;
	}

	//登录判断
	public boolean ifLogin(String name,String psw){
		User user = queryUser(name);
		if (user.getName().equals(name) && user.getPsw().equals(psw)){
			return true;
		}
		return false;
	}

	//根据用户名来查询用户
	public User queryUser(String names){
		User user = new User();
		Cursor cursor = db.query("users", null, "name = ?",
				new String[] { names }, null, null, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String psw = cursor.getString(cursor.getColumnIndex("psw"));
			user.setName(name);
			user.setPsw(psw);
			user.setId(id);
		}
		if (cursor!=null){
			cursor.close();
		}
		return user;
	}


	//根据内容查询
	public List<Sate> selectUserData(String usernames) {
		Cursor cursor = db.query("sates", null, "username = ?",
				new String[] { usernames }, null, null, null);
		List<Sate> list = new ArrayList<>();
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String date = cursor.getString(cursor.getColumnIndex("date"));
			String status = cursor.getString(cursor.getColumnIndex("status"));
			String username = cursor.getString(cursor.getColumnIndex("username"));
			Sate bean = new Sate();
			bean.setDate(date);
			bean.setName(name);
			bean.setStatus(status);
			bean.setUsername(username);
			bean.setId(id);
			list.add(bean);
		}
		if (cursor!=null){
			cursor.close();
		}
		return list;
	}
	//根据内容查询
	public List<Sate> selectUserData1(String usernames) {
		Cursor cursor = db.query("sates1", null, "username = ?",
				new String[] { usernames }, null, null, null);
		List<Sate> list = new ArrayList<>();
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String date = cursor.getString(cursor.getColumnIndex("date"));
			String status = cursor.getString(cursor.getColumnIndex("status"));
			String username = cursor.getString(cursor.getColumnIndex("username"));
			Sate bean = new Sate();
			bean.setDate(date);
			bean.setName(name);
			bean.setStatus(status);
			bean.setUsername(username);
			bean.setId(id);
			list.add(bean);
		}
		if (cursor!=null){
			cursor.close();
		}
		return list;
	}

     //根据内容查询
	public Sate selectDataToContent(int ids) {
		Cursor cursor = db.query("sates", null, "id = ?",
				new String[] { String.valueOf(ids) }, null, null, null);
		Sate bean = new Sate();
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String date = cursor.getString(cursor.getColumnIndex("date"));
			String status = cursor.getString(cursor.getColumnIndex("status"));
		    bean.setDate(date);
		    bean.setName(name);
		    bean.setStatus(status);
			bean.setId(id);
		}
		if (cursor!=null){
			cursor.close();
		}
		return bean;
	}

	//根据内容查询
	public Sate selectDataToContent1(int ids) {
		Cursor cursor = db.query("sates1", null, "id = ?",
				new String[] { String.valueOf(ids) }, null, null, null);
		Sate bean = new Sate();
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String date = cursor.getString(cursor.getColumnIndex("date"));
			String status = cursor.getString(cursor.getColumnIndex("status"));
			bean.setDate(date);
			bean.setName(name);
			bean.setStatus(status);
			bean.setId(id);
		}
		if (cursor!=null){
			cursor.close();
		}
		return bean;
	}

    //根据id 修改
	public int modifyDataContent(String status,int id) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("status", status);
		contentValues.put("username", "");
		int index = db.update("sates", contentValues, "id = ?",
				new String[] { String.valueOf(id) });
		return index;
	}

	//根据id 修改
	public int modifyDataContent1(String status,int id) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("status", status);
		contentValues.put("username", "");
		int index = db.update("sates1", contentValues, "id = ?",
				new String[] { String.valueOf(id) });
		return index;
	}

	//根据username 修改
	public int modifyDataStatus(String status,String username,int id) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("status", status);
		contentValues.put("username", username);
		int index = db.update("sates", contentValues, "id = ?",
				new String[] { String.valueOf(id) });
		return index;
	}

	//根据username 修改
	public int modifyDataStatus1(String status,String username,int id) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("status", status);
		contentValues.put("username", username);
		int index = db.update("sates1", contentValues, "id = ?",
				new String[] { String.valueOf(id) });
		return index;
	}

	//
	public List<Sate> getAllData() {
		ArrayList<Sate> list = new ArrayList<>();
		Cursor cursor = db.query("sates", null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String date = cursor.getString(cursor.getColumnIndex("date"));
			String status = cursor.getString(cursor.getColumnIndex("status"));
			String username = cursor.getString(cursor.getColumnIndex("username"));
			Sate bean = new Sate();
			bean.setDate(date);
			bean.setName(name);
			bean.setStatus(status);
			bean.setUsername(username);
			bean.setId(id);
			list.add(bean);
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}

	//
	public List<Sate> getAllData1() {
		ArrayList<Sate> list = new ArrayList<>();
		Cursor cursor = db.query("sates1", null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String date = cursor.getString(cursor.getColumnIndex("date"));
			String status = cursor.getString(cursor.getColumnIndex("status"));
			String username = cursor.getString(cursor.getColumnIndex("username"));
			Sate bean = new Sate();
			bean.setDate(date);
			bean.setName(name);
			bean.setStatus(status);
			bean.setUsername(username);
			bean.setId(id);
			list.add(bean);
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}
    //删除
	public int delData(String content) {
		int inde = db.delete("sates", "name = ?",
				new String[] { content });
		return inde;
	}


	/**
	 * 查询
	 * @param names
	 * @return
	 */
	public ArrayList<Sate> selectListData(String names) {
		ArrayList<Sate> list = new ArrayList<>();
		//查询数据库
		Cursor cursor= db.rawQuery("select * from sates where name like '%"+names+"%'",null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String date = cursor.getString(cursor.getColumnIndex("date"));
			String status = cursor.getString(cursor.getColumnIndex("status"));
			String username = cursor.getString(cursor.getColumnIndex("username"));
			Sate bean = new Sate();
			bean.setDate(date);
			bean.setName(name);
			bean.setStatus(status);
			bean.setUsername(username);
			bean.setId(id);
			list.add(bean);
		}
		if (cursor != null) {
			cursor.close();
		}

		return list;
	}


	/**
	 * 查询1
	 * @param names
	 * @return
	 */
	public ArrayList<Sate> selectListData1(String names) {
		ArrayList<Sate> list = new ArrayList<>();
		//查询数据库
		Cursor cursor= db.rawQuery("select * from sates1 where name like '%"+names+"%'",null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String date = cursor.getString(cursor.getColumnIndex("date"));
			String status = cursor.getString(cursor.getColumnIndex("status"));
			String username = cursor.getString(cursor.getColumnIndex("username"));
			Sate bean = new Sate();
			bean.setDate(date);
			bean.setName(name);
			bean.setStatus(status);
			bean.setUsername(username);
			bean.setId(id);
			list.add(bean);
		}
		if (cursor != null) {
			cursor.close();
		}

		return list;
	}


}
