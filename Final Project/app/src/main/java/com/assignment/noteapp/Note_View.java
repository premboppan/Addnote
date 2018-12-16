package com.assignment.noteapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Note_View extends Activity{

	private ImageView imgview_noteview;
	private TextView txt_noteviewname,txt_noteviewdesc,txt_noteviewdate,txt_noteviewtime;
	private Button btn_noteviewmap,btn_noteviewaudio;
	private Switch sw_update;
	DBAdapter db;
	Bundle b;
	int _id;
	String audio_path;
	String img_path;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_view);

		
		imgview_noteview = (ImageView)findViewById(R.id.imgview_noteview);
		txt_noteviewname =(TextView)findViewById(R.id.txt_noteviewname);
		txt_noteviewdesc = (TextView)findViewById(R.id.txt_noteviewdesc);
		txt_noteviewdate = (TextView)findViewById(R.id.txt_noteviewdate);
		txt_noteviewtime = (TextView)findViewById(R.id.txt_noteviewtime);
		btn_noteviewmap =(Button)findViewById(R.id.btn_noteviewmap);
		btn_noteviewaudio =(Button)findViewById(R.id.btn_noteviewaudio);
		sw_update = (Switch)findViewById(R.id.switchUpdate);
		db = new DBAdapter(this);
		b = getIntent().getExtras();
		if(b!=null)
		{
			_id = Integer.parseInt(b.getString("note_id"));
			call_fill(_id);
		}
		
		btn_noteviewaudio.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MediaPlayer mp = new MediaPlayer();
				try {
					if(audio_path != null)
					{
					mp.setDataSource(audio_path);
					mp.prepare();
					mp.start();
					System.out.println("Playing started of"+audio_path);
					}else
					{
						Toast.makeText(getApplicationContext(), "Audio is not exist", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("ERROR At Audio button in Note_view class-->"+e.toString());
					Toast.makeText(getApplicationContext(), "Audio is not exist", Toast.LENGTH_SHORT).show();
				} 
			
			}
		});
	
		
		/*
		 * Switch widget usage for sorting.......
		 */
		
		if(sw_update != null)
		{
			//switch1.setOnCheckedChangeListener(this);
			sw_update.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
				
					if(isChecked)
					{
						Intent i = new Intent(Note_View.this,Note_Form.class);
						i.putExtra("note_id",String.valueOf(_id));
						startActivity(i);
					}
					else
					{
						//ListViewUpdate();
					}
				}
			});
		}
		 final Drawable drawable1 = Drawable.createFromPath(img_path);	
		imgview_noteview.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			
    			final Dialog nagDialog = new Dialog(Note_View.this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
                nagDialog.setCancelable(false);
                nagDialog.setContentView(R.layout.preview_image);
                Button btnClose = (Button)nagDialog.findViewById(R.id.btnIvClose);
                ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.iv_preview_image);
                ivPreview.setBackgroundDrawable(drawable1);

                btnClose.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        nagDialog.dismiss();
                    }
                });
                nagDialog.show();
    		}
    	});
    
    }

	
	
	
	
	
	private void call_fill(int _id2) {
		// TODO Auto-generated method stub
		
		try{
			
			
			db.open();
			Cursor c = db.getdata_onid(_id2);
			db.close();
			c.moveToFirst();
			txt_noteviewname.setText(c.getString(c.getColumnIndex("note_name")));
			txt_noteviewdesc.setText(c.getString(c.getColumnIndex("note_desc")));
			txt_noteviewdate.setText(c.getString(c.getColumnIndex("date")));
			txt_noteviewtime.setText(c.getString(c.getColumnIndex("time")));
			
			 img_path =c.getString(c.getColumnIndex("img_path"));
			Drawable drawable1 = Drawable.createFromPath(img_path);
			//Drawable drawable1 =LoadImageFromWebOperations(path);
			imgview_noteview.setImageDrawable(drawable1);
			
			audio_path = c.getString(c.getColumnIndex("audio_path"));
			
			}catch(Exception e)
			{
				System.out.println("ERROR AT CALL-FILL() method in NOTE_View class--->"+e.toString());
			}
		
	}
}
