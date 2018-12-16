package com.assignment.noteapp;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBAdapter {
	
	private static final String DATABASE_NAME = "NoteApp_DB";

	private static final int DATABASE_VERSION = 1;

	private final Context context;

	private DatabaseHelper DBHelper;
	private static SQLiteDatabase db;


	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	public static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
	
}
	// ---opens the database---
		public DBAdapter open() throws SQLException {
			db = DBHelper.getWritableDatabase();
			return this;
		}

		// ---closes the database---
		public void close() {
			DBHelper.close();
		}
		
		public Cursor getAllData(){
			//Cursor mCursor=db.rawQuery("select * from tbl_registration where uname='"+ uname+"';", null);
			
			Cursor mCursor=db.rawQuery("select * from tbl_time_note", null);
			
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
			return mCursor;
			
		}

		public void insert_note(String notename, String desc,String time,String date, String img_path,String audio_path, String lat, String longi)
		{
			// TODO Auto-generated method stub
			
			ContentValues initialValues = new ContentValues();
		    initialValues.put("note_name" , notename);
		    initialValues.put("note_desc", desc);
		    initialValues.put("time", time);
		    initialValues.put("date",date);
		    initialValues.put("img_path", img_path);
		    initialValues.put("audio_path", audio_path);
		    initialValues.put("lattitude", lat);
		    initialValues.put("longitude", longi);
		    
		    db.insert("tbl_note", null, initialValues);
		    System.out.println("recored inserted successfully");
		}

		public Cursor getAllNote() {
			// TODO Auto-generated method stub
			
			Cursor mCursor=db.rawQuery("select * from tbl_note", null);
			
			return mCursor;
		}
		
		public Cursor getAllNoteInOrder() {
			// TODO Auto-generated method stub
			
			Cursor mCursor=db.rawQuery("select * from tbl_note ORDER BY note_name ASC", null);
			
			return mCursor;
		}

		public Cursor getdata_onid(int _id2) {
			// TODO Auto-generated method stub
			
			Cursor c = db.rawQuery("SELECT * FROM tbl_note WHERE note_id ="+_id2, null);
			c.moveToFirst();
			return c;
		}

		public void edit_note(int _id, String notename, String desc,
				String time, String date, String img_path, String audio_path,String lat,
				String longi) {
			// TODO Auto-generated method stub
			ContentValues initialValues = new ContentValues();
		    initialValues.put("note_name" , notename);
		    initialValues.put("note_desc", desc);
		    initialValues.put("time", time);
		    initialValues.put("date",date);
		    initialValues.put("img_path", img_path);
		    initialValues.put("audio_path", audio_path);
		    initialValues.put("lattitude", lat);
		    initialValues.put("longitude", longi);
		   db.update("tbl_note", initialValues, "note_id="+_id, null);
		    System.out.println("recored updated successfully");
			
		}

		public void delete_note(int _id) {
			// TODO Auto-generated method stub
			db.delete("tbl_note", "note_id ="+_id, null);
		}

		

		
}
