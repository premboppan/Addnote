package com.assignment.noteapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Stack;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.Toast;

public class Note_Home extends Activity implements OnItemClickListener, OnCheckedChangeListener{
	
	private TableRow tblrw_addnote;
	private ListView lv_note;
	private EditText edtxt_search;
	private Switch switch1;
	DBAdapter db ;
	int _id;
	private Note_Adapter nadpt;
	private Note_Adapter1 nadpt1;
	Cursor c;
	String[] sa_name,sa_date,sa_time,sa_imgpath;
	ArrayList<HashMap<String, Object>> searchResults;
	ArrayList<HashMap<String, Object>> originalValues;
	
	
	ArrayList<String> id_note = new ArrayList<String>();
	ArrayList<String> note_name = new ArrayList<String>();
	ArrayList<String> note_desc = new ArrayList<String>();
	ArrayList<String> note_time = new ArrayList<String>();
	ArrayList<String> note_date = new ArrayList<String>();
	ArrayList<String> img_path = new ArrayList<String>();
	ArrayList<String> note_lat = new ArrayList<String>();
	ArrayList<String> note_long = new ArrayList<String>();
	ArrayList<Note> notearrayList = new ArrayList<Note>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_home);
		try{
			
			lv_note = (ListView)findViewById(R.id.lv_Note);
			tblrw_addnote = (TableRow)findViewById(R.id.tblrw_addnote);
			edtxt_search = (EditText)findViewById(R.id.edtxt_search);
		switch1 = (Switch)findViewById(R.id.switch1);
		db = new DBAdapter(this);
		
		/*
		 * method for Listview..
		 */
		
		ListViewUpdate();
					
					/**
			         * Enabling Search Filter
			         * */
			        edtxt_search.addTextChangedListener(new TextWatcher() {
			             
			            @Override
			            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
			                // When user changed the Text
			               String text = edtxt_search.getText().toString().toLowerCase(Locale.getDefault());
			               nadpt1.filter(text);
			            }
			             
			            @Override
			            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			                    int arg3) {
			                // TODO Auto-generated method stub
			                 
			            }
			             
			            @Override
			            public void afterTextChanged(Editable arg0) {
			                // TODO Auto-generated method stub                          
			            }
			        });
		
		/*
		 * onclickListener on ListView.....
		 */
		
					lv_note.setOnItemClickListener(this);
		
		/*
		 * Contexmenu on Longpress to Listview..
		 */
		
					registerForContextMenu(lv_note);
		
		
		
		/*
		 * Switch widget usage for sorting.......
		 */
		
		if(switch1 != null)
		{
			//switch1.setOnCheckedChangeListener(this);
			switch1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
				
					if(isChecked)
					{
						ListViewUpdteInOrder();
					}
					else
					{
						ListViewUpdate();
					}
				}
			});
		}
		
		
				
		tblrw_addnote.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent i = new Intent(getApplicationContext(),Note_Form.class);
				startActivity(i);
			}
		});
		
	
		/*db.open();
		c = db.getAllNote();
		if(c!=null)
		{
		c.moveToFirst();
		System.out.println("Total rows-->"+c.getCount());
		Toast.makeText(getApplicationContext(), "Total"+c.getString(c.getColumnIndex("audio_path")), Toast.LENGTH_LONG).show();
		}*/
		}catch(Exception e)
		{
			System.out.println("Error at oncreate in Note_Home class-->"+e.toString());
		}
	}
	private void ListViewUpdate() {
		// TODO Auto-generated method stub
		
		
		
		clearAdapter();
		db.open();
		c = db.getAllNote();
		c.moveToFirst();
		if(c!=null)
			{
			
			
			System.out.println("TOTAL Column=-->>"+c.getCount());
			for(int i=0;i<c.getCount();i++)
			{
				id_note.add(c.getString(c.getColumnIndex("note_id")));
				note_name.add(c.getString(c.getColumnIndex("note_name")));
				note_desc.add(c.getString(c.getColumnIndex("note_desc")));
				note_time.add(c.getString(c.getColumnIndex("time")));
				note_date.add(c.getString(c.getColumnIndex("date")));
				img_path.add( c.getString(c.getColumnIndex("img_path")));
				//Toast.makeText(getApplicationContext(), c.getString(c.getColumnIndex("message_date")), Toast.LENGTH_LONG).show();
				c.moveToNext();
			}
		
			/*//Collections.sort(note_name, String.CASE_INSENSITIVE_ORDER);
		
			nadpt = new Note_Adapter(this, id_note, note_name, note_desc, note_date, note_time, img_path);
			//nadpt.notifyDataSetChanged();
			lv_note.setTextFilterEnabled(true);
			lv_note.setAdapter(nadpt);
			lv_note.setDividerHeight(2);
	*/		
			sa_name = note_name.toArray(new String[note_name.size()]);
			sa_date = note_date.toArray(new String[note_date.size()]);
			sa_time = note_time.toArray(new String[note_time.size()]);
			sa_imgpath = img_path.toArray(new String[img_path.size()]);
			
			for(int i =0;i<sa_name.length;i++)
			{
				Note nt = new Note(sa_name[i], sa_date[i], sa_time[i], sa_imgpath[i]);
				notearrayList.add(nt);
								
			}
			
			nadpt1 = new Note_Adapter1(this, notearrayList);
			lv_note.setAdapter(nadpt1);
			
			}

}
	private void clearAdapter() {
		// TODO Auto-generated method stub
		id_note.clear();
		note_name.clear();
		note_desc.clear();
		note_date.clear();
		note_time.clear();
		note_long.clear();
		note_lat.clear();
		img_path.clear();
		notearrayList.clear();
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		
		Intent i = new Intent (getApplicationContext(),Note_View.class);
		i.putExtra("note_id", id_note.get(position).toString());
		startActivity(i);
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu,View v,ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		menu.setHeaderTitle(note_name.get(info.position).toString());
		menu.add(0, 1, 0, "Edit");
		menu.add(0, 2, 0, "Delete");
		menu.add(0,3,0,"View");

		   
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int index = info.position;
		
		_id = Integer.parseInt(id_note.get(index));
		
		//Toast.makeText(getApplicationContext(), String.valueOf(_id), Toast.LENGTH_LONG).show();
		
		
		
		if (item.getTitle() == "Edit") 
		{
			/*yesNo = 1;
			callMainMethod(namee);*/
			Intent i = new Intent (getApplicationContext(),Note_Form.class);
			i.putExtra("note_id", String.valueOf(_id));
			startActivity(i);
			 
			return true;
		}
		if (item.getTitle() == "Delete") 
		{
			try 
			{
				AlertDialog.Builder builder = new Builder(this);
				builder.setTitle("Delete");
				builder.setIcon(android.R.drawable.ic_delete);
				builder.setMessage("Do you really want to Delete?");
				builder.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog, final int which) 
					{
						db.open();
						db.delete_note(_id);
						Toast.makeText(getApplicationContext(), "Note is deleted", Toast.LENGTH_LONG).show();
					    db.close();
						dialog.dismiss();
						ListViewUpdate();
					}
				});
				builder.setNegativeButton("Cancel", new AlertDialog.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog, final int which) {
						dialog.dismiss();
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
				
				
			 
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
			}
			return true;
		} 
		if(item.getTitle() == "View"){
			
			Intent i = new Intent (getApplicationContext(),Note_View.class);
			i.putExtra("note_id", String.valueOf(_id));
			startActivity(i);
			 
			
			return true;
			
		}
		
		else
			return false;
	}
	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
		// TODO Auto-generated method stub
		
		Toast.makeText(getApplicationContext(),"Monitored switch is " + (isChecked ? "on" : "off"),
	               Toast.LENGTH_SHORT).show();
		
		if(isChecked)
		{
			ListViewUpdteInOrder();
		}
		else
		{
			ListViewUpdate();
		}
	}
	private void ListViewUpdteInOrder() {
		// TODO Auto-generated method stub
		clearAdapter();
		db.open();
		c = db.getAllNoteInOrder();
		c.moveToFirst();
		if(c!=null)
			{
			
			
			System.out.println("TOTAL Column=-->>"+c.getCount());
			for(int i=0;i<c.getCount();i++)
			{
				id_note.add(c.getString(c.getColumnIndex("note_id")));
				note_name.add(c.getString(c.getColumnIndex("note_name")));
				note_desc.add(c.getString(c.getColumnIndex("note_desc")));
				note_time.add(c.getString(c.getColumnIndex("time")));
				note_date.add(c.getString(c.getColumnIndex("date")));
				img_path.add( c.getString(c.getColumnIndex("img_path")));
				//Toast.makeText(getApplicationContext(), c.getString(c.getColumnIndex("message_date")), Toast.LENGTH_LONG).show();
				c.moveToNext();
			}
		
			//Collections.sort(note_name, String.CASE_INSENSITIVE_ORDER);
		
		/*	nadpt = new Note_Adapter(this, id_note, note_name, note_desc, note_date, note_time, img_path);
			//nadpt.notifyDataSetChanged();
			lv_note.setAdapter(nadpt);
			lv_note.setDividerHeight(2);*/
			
			
			sa_name = note_name.toArray(new String[note_name.size()]);
			sa_date = note_date.toArray(new String[note_date.size()]);
			sa_time = note_time.toArray(new String[note_time.size()]);
			sa_imgpath = img_path.toArray(new String[img_path.size()]);
			
			for(int i =0;i<sa_name.length;i++)
			{
				Note nt = new Note(sa_name[i], sa_date[i], sa_time[i], sa_imgpath[i]);
				notearrayList.add(nt);
								
			}
			
			nadpt1 = new Note_Adapter1(this, notearrayList);
			lv_note.setAdapter(nadpt1);
	}

	}
	
	
}
