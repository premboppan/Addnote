package com.assignment.noteapp;


import java.io.File;
import java.io.InputStream;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class Splash extends Activity{
	
	private final int SPLASH_DISPLAY_LENGHT = 6000;
	private static final String NOTE_DB="NoteApp_DB";
	File f;
	DBAdapter db = new DBAdapter(this);
	//private Handler h;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		/*DBAdapter db = new DBAdapter(this);
			db.open();
			db.close();*/
		try {

			new Async().execute();
			/*h = new Handler();
			h.postDelayed(null, 5000);*/
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent mainIntent = new Intent(Splash.this,
							Note_Home.class);
					Splash.this.startActivity(mainIntent);
					
					Splash.this.finish();
				}
			}, SPLASH_DISPLAY_LENGHT);
		} catch (Exception e) {
			Toast t = Toast.makeText(this, "Error on Application Startup",
					Toast.LENGTH_LONG);
			t.show();
		}
		
	}
	
	private class Async extends AsyncTask<Void, Void, Void>
	{

		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try
			{
				
			//Toast.makeText(getApplicationContext(), "Async is running", Toast.LENGTH_LONG).show();
			System.out.println("Async executed...2341222..`!!!@#@$%^$^$&^&5");
			f=getApplicationContext().getDatabasePath(NOTE_DB);
			
			 //f= new File("/data/data/com.example.walk_reminder/databases/walk_reminder");
			if(!f.exists())
			{
				InputStream ins = getAssets().open(NOTE_DB);
				
				
				db.open();
				db.close();
				
				ImportDatabase idp = new ImportDatabase(ins);
				idp.copyDataBase();
				System.out.println("Database Copied");
				//Toast.makeText(getApplicationContext(), "Database copied", Toast.LENGTH_LONG).show();
			}
			else
			{
				System.out.println("Database Already exists");
				//Toast.makeText(getApplicationContext(), "Database Already exists", Toast.LENGTH_LONG).show();
			}
			}catch(Exception e)
			{
				System.out.println("ERROR AT ASYNC:-->"+e.toString());
			}
			return null;
		
			}
		
		
	}


}
