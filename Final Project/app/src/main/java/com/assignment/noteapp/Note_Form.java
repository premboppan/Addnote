package com.assignment.noteapp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;



import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Note_Form extends Activity{
	
 private EditText edtxt_notename,edtxt_notedesc;
 private TableRow tblrw_notetime,tblrw_notedate;
 private Button btn_notemap,btn_notecamera,btn_notecancel,btn_notedelete,btn_noteok,btn_notegallery,btn_noteaudio;
 TextView txt_path,txt_notetime,txt_notedate,txt_audiopath;
 
 private static final int TAKE_PHOTO_CODE=111;
 private static final int PICK_FROM_CAMERA=222;
 private static final int UPLOAD_PHOTO_CODE = 200;
 //private static final int LAT_LONG_DATA = 300;
 int count=0;
 String trg,trg1;
 private static final int Time_PICKER_SID = 0; 
 private static final int DATE_DIALOG_ID=555;
 private Calendar mCalen;
 private int hourOfDay;
 private int minute;
 private int ampm;
 private int year;
	private int month;
	private int day,_id;
 
 DBAdapter db;
 File audiofile=null;
 Bundle b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_form);
		
		
		edtxt_notename = (EditText)findViewById(R.id.edtxt_notename);
		edtxt_notedesc = (EditText)findViewById(R.id.edtxt_notedesc);
		tblrw_notetime =(TableRow)findViewById(R.id.tblrw_notetime);
		tblrw_notedate=(TableRow)findViewById(R.id.tblrw_notedate);
		btn_notemap =(Button)findViewById(R.id.btn_notemap);
		btn_notecamera =(Button)findViewById(R.id.btn_notecamera);
		txt_path =(TextView)findViewById(R.id.txt_path);
		txt_audiopath =(TextView)findViewById(R.id.txt_audiopath);
		txt_notetime=(TextView)findViewById(R.id.txt_notetime);
		txt_notedate = (TextView)findViewById(R.id.txt_notedate);
		btn_notecancel =(Button)findViewById(R.id.btn_notecancel);
		btn_notedelete =(Button)findViewById(R.id.btn_notedelete);
		btn_noteok = (Button)findViewById(R.id.btn_noteok);
		btn_notegallery=(Button)findViewById(R.id.btn_notegallery);
		btn_noteaudio=(Button)findViewById(R.id.btn_noteaudio);
		btn_notemap=(Button)findViewById(R.id.btn_notemap);
		
		mCalen = Calendar.getInstance();
        hourOfDay = mCalen.get(Calendar.HOUR_OF_DAY);
        minute = mCalen.get(Calendar.MINUTE);
        ampm = mCalen.get(Calendar.AM_PM);
        setCurrentDateonView();
       db = new DBAdapter(this);
        
       b= getIntent().getExtras();
       if(b != null)
       {
    	   btn_noteok.setText("Update");
    	   _id = Integer.parseInt(b.getString("note_id"));
    	   call_fill(_id);
       }
       
       
       /*
        * Map button click...
        * 
        */
       
       btn_notemap.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		
			Constant.L_NOTE_FORM_ID=1;
			Intent i = new Intent(getApplicationContext(),LoadMap.class);
			startActivity(i);
			//startActivityForResult(i,LAT_LONG_DATA);
		}
	});
       
       
		/*
		 * camera button click..
		 */
		
		
		btn_notecamera.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent ii = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(ii, TAKE_PHOTO_CODE);
				
			}
		});
		
		btn_notegallery.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, UPLOAD_PHOTO_CODE);
			}
		});
		
		btn_noteaudio.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				 File sampleDir = new File(Environment.getExternalStorageDirectory(), "/NoteAppRecording");
                 if (!sampleDir.exists()) {
                     sampleDir.mkdirs();
                 }
                 String file_name = "Record";
                 try {
                     audiofile = File.createTempFile(file_name, ".amr", sampleDir);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
                 String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                 System.out.println("Path Of Recording -->/mnt/sdcard/TestRecordingData1Incoming/Record-291423644.amr"+audiofile.getAbsolutePath());
				recordAudio(audiofile);
				
				txt_audiopath.setText(audiofile.getAbsolutePath());
				
			}
		});
		
		
		btn_noteok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				if(b != null)
				{
					db.open();
					db.edit_note(_id,edtxt_notename.getText().toString(), edtxt_notedesc.getText().toString(),txt_notetime.getText().toString(),txt_notedate.getText().toString(), txt_path.getText().toString(),txt_audiopath.getText().toString(), "", "");
					db.close();
				}else
				{
				
				db.open();
				db.insert_note(edtxt_notename.getText().toString(), edtxt_notedesc.getText().toString(),txt_notetime.getText().toString(),txt_notedate.getText().toString(), txt_path.getText().toString(),txt_audiopath.getText().toString(), "", "");
				db.close();
				Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_LONG).show();
				
				}
				Intent i = new Intent(getApplicationContext(),Note_Home.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
        	
		btn_notedelete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			db.open();
			db.delete_note(_id);
			db.close();
		
			Intent i = new Intent(getApplicationContext(),Note_Home.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		
			}
		});
		
		btn_notecancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent i = new Intent(getApplicationContext(),Note_Home.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
		
		tblrw_notetime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				showDialog(Time_PICKER_SID);
			}
		});
		
		
		tblrw_notedate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);
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
		edtxt_notename.setText(c.getString(c.getColumnIndex("note_name")));
		edtxt_notedesc.setText(c.getString(c.getColumnIndex("note_desc")));
		txt_notedate.setText(c.getString(c.getColumnIndex("date")));
		txt_notetime.setText(c.getString(c.getColumnIndex("time")));
		txt_path.setText(c.getString(c.getColumnIndex("img_path")));
		txt_audiopath.setText(c.getString(c.getColumnIndex("audio_path")));
		}catch(Exception e)
		{
			System.out.println("ERROR AT CALL-FILL() method in NOTE_FROM class--->"+e.toString());
		}
	}


	public void recordAudio(File fileName) {
	    final MediaRecorder recorder = new MediaRecorder();
	   // ContentValues values = new ContentValues(3);
	   // values.put(MediaStore.MediaColumns.TITLE, fileName);
	    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	    recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
	    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
	    recorder.setOutputFile(fileName.getAbsolutePath());
	    //recorder.setOutputFile("/sdcard/sound/" + fileName);
	    try {
	      recorder.prepare();
	    } catch (Exception e){
	        e.printStackTrace();
	    }

	    final ProgressDialog mProgressDialog = new ProgressDialog(Note_Form.this);
	    mProgressDialog.setTitle("Recording");
	    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	   
	    mProgressDialog.setButton("Stop Recording", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, final int which) {
				// TODO Auto-generated method stub
				mProgressDialog.dismiss();
		        recorder.stop();
		        recorder.release();	
		}
		});
	    /*mProgressDialog.setButton("Stop recording", new DialogInterface.OnClickListener() {
	    public void onClick(DialogInterface dialog, int whichButton) {
	        mProgressDialog.dismiss();
	        recorder.stop();
	        recorder.release();
	        }
	    });*/

	    mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
	        public void onCancel(DialogInterface p1) {
	            recorder.stop();
	            recorder.release();
	        }
	    });
	    recorder.start();
	    mProgressDialog.show();
	}
	
	
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
        case Time_PICKER_SID:
            return new TimePickerDialog(this, TimePickerListener,
                    hourOfDay, minute, false);
        case DATE_DIALOG_ID:
			// set date picker as current date

			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
    }
    return null;
	}
	
	private TimePickerDialog.OnTimeSetListener TimePickerListener =
            new TimePickerDialog.OnTimeSetListener() {

                // while dialog box is closed, below method is called.
                public void onTimeSet(TimePicker view, int hour, int minute) {

                    mCalen.set(Calendar.HOUR_OF_DAY, hour);
                    mCalen.set(Calendar.MINUTE, minute);
                    
                    String timehour,timeminute;
                    int hour12format = mCalen.get(Calendar.HOUR);
                    hourOfDay = mCalen.get(Calendar.HOUR_OF_DAY);
                    minute = mCalen.get(Calendar.MINUTE);
                    ampm = mCalen.get(Calendar.AM_PM);
                    String ampmStr = (ampm == 0) ? "AM" : "PM";
                    // Set the Time String in Button
                    if(hour12format <=9 && hour12format >= 0 )
                    {
                    	timehour = "0" + hour12format;
                    	 //endTextView.setText("0" + hour12format + " : " + "0" + minute + " / " + ampmStr);
                    }
                    else
                    {
                    	timehour =  String.valueOf(hour12format);
                    }
                    if( minute <=9 && minute >= 0)
                    {
                    	timeminute = "0" + minute;
                    }
                    else
                    {
                    	timeminute = String.valueOf(minute);
                    }
                    txt_notetime.setText(timehour + " : " + timeminute + " / " + ampmStr);
                }
            };
            
            private void setCurrentDateonView() {
        		
        		final Calendar c = Calendar.getInstance();
        		year = c.get(Calendar.YEAR);
        		month = c.get(Calendar.MONTH);
        		day = c.get(Calendar.DAY_OF_MONTH);
        	}

        	
        	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        		// when dialog box is closed, below method will be called.
        		public void onDateSet(DatePicker view, int selectedYear,
        				int selectedMonth, int selectedDay) {
        			year = selectedYear;
        			month = selectedMonth;
        			day = selectedDay;

        			// set selected date into textview

        			if (month < 9 && day <= 9) {
        				txt_notedate.setText(new StringBuilder().append("0").append(day).append("-")
        						.append("0").append(month + 1).append("-")
        						.append(year));
        			} else if (month < 9 && day > 9) {
        				txt_notedate.setText(new StringBuilder().append(day).append("-")
        						.append("0").append(month + 1).append("-").append(year));
        			} else if (month >= 9 && day <= 9) {
        				txt_notedate.setText(new StringBuilder().append("0").append(day).append("-")
        						.append(month + 1).append("-").append(year));

        			} else {
        				txt_notedate.setText(new StringBuilder().append(day).append("-")
        						.append(month + 1).append("-").append(year));
        			}

        			// Toast.makeText(getApplicationContext(), year+"/"+month+"/"+day,
        			// 30).show();
        			// set selected date into datepicker also
        			// dpResult.init(year, month, day, null);

        		}
        	};
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	
    	//super.onActivityResult(requestCode, resultCode, data);
    	
    	
		try{
			//if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
			if(requestCode == TAKE_PHOTO_CODE)
			{
				try {
				 //2 we get the image bitmap from the camera..
	            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
	            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	            thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
	            //4
	            count++;
	            File file = new File(Environment.getExternalStorageDirectory()+File.separator + count+".jpeg");
	            
	                file.createNewFile();
	                FileOutputStream fo = new FileOutputStream(file);
	                //5
	                fo.write(bytes.toByteArray());
	                fo.close();
	            }
	            catch(IOException e)
	            {
	            	System.out.println("Error in Note_Form.java at camera onActivityResult"+e.toString());
	            }
	            if(resultCode ==RESULT_OK)
	            {
	            	startActivityForResult(new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), PICK_FROM_CAMERA);
	            	
	            }
			}
	            
			if(requestCode == PICK_FROM_CAMERA)
			{
				 Uri targeturi =data.getData();
				 System.out.println("uri:"+targeturi.toString());
	            	
	            	Cursor c = getContentResolver().query(targeturi, null, null, null, null);
	            	c.moveToFirst();
	            	trg1= c.getString(c.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
	            	txt_path.setText(trg1);
	            	System.out.println("Camera pic path1:"+trg1);
	            	Toast.makeText(getApplicationContext(), "Uri:"+trg1, Toast.LENGTH_SHORT).show();
			}
    	//String trg1 = null;
			
			
			if(requestCode == UPLOAD_PHOTO_CODE)
			{
				
			
    	if(resultCode==RESULT_OK)
    	{
    		Uri targeturi = data.getData();
    		 trg = targeturi.toString();
    		 //txtUri.setText(trg);
    		 System.out.println("targetUri:"+targeturi);
    		 Cursor c = getContentResolver().query(targeturi, null, null, null, null);
    		 c.moveToFirst();
    		  trg1= c.getString(c.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
    		 txt_path.setText(trg1);
    		 // String trg1 = targeturi.getPath();
    		 Toast.makeText(getApplicationContext(), trg1, 30).show();
    		
    		 //
    		
    	}
    	}
			
    	}catch (Exception e) {
			
    		 System.out.println("Error in Note_Form.java at camera,upload ActivityResult code:"+e.toString());
		}
    }
	
   }

