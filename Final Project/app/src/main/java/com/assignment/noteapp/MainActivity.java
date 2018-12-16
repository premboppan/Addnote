package com.assignment.noteapp;

import java.io.File;
import java.io.InputStream;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends Activity implements OnItemSelectedListener {
	
	private Spinner spin_category;
	private static final String NOTE_DB="NoteApp_DB";
	
	
	File f;
	DBAdapter db = new DBAdapter(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category);
		
		
		new Async().execute();
		
		System.out.println("path"+Environment.getExternalStorageDirectory());
		
		spin_category =(Spinner)findViewById(R.id.spin_category);
		ArrayAdapter<CharSequence> sp_adpt = ArrayAdapter.createFromResource(getApplicationContext(), R.array.sp_category, R.layout.spinner_item);
		sp_adpt.setDropDownViewResource(R.layout.spinner_dropdown_item);
		spin_category.setAdapter(sp_adpt);
		spin_category.setOnItemSelectedListener(this);
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		try
		{
			int pos = spin_category.getSelectedItemPosition();
			if(pos == 1)
			{
				Intent i = new Intent(getApplicationContext(),Note_Home.class);
				startActivity(i);
			}
			
		}catch(Exception e)
		{
			System.out.println("Error At Spinner Category-->"+e.toString());
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
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
