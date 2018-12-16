package com.assignment.noteapp;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class Note_Adapter extends BaseAdapter {
	
	
	private static Activity activity;
	private ArrayList<String> id_note,note_name,note_desc,note_time,note_date,img_path;
	//private ArrayList<Integer> mail_status;
	
	private List<Note> notelist = null;
    private ArrayList<Note> notearraylist;
	private static LayoutInflater inflater;
	View vi;
	
	String[] sa_name,sa_date,sa_time,sa_imgpath;
	
	public Note_Adapter(Activity Note_Home,
			 ArrayList<String> _id,
			ArrayList<String> name,ArrayList<String> desc, ArrayList<String> date,ArrayList<String> time,ArrayList<String> path) {
//ArrayList<Integer> mail_st,
	
	activity = Note_Home;
	id_note=_id;
	note_name=name;
	note_desc=desc;
	note_date=date;
	note_time=time;
	img_path = path;
	
	
	/*sa_name = note_name.toArray(new String[note_name.size()]);
	sa_date = note_date.toArray(new String[note_date.size()]);
	sa_time = note_time.toArray(new String[note_time.size()]);
	sa_imgpath = img_path.toArray(new String[img_path.size()]);
	
	for(int i =0;i<sa_name.length;i++)
	{
		Note nt = new Note(sa_name[i], sa_date[i], sa_time[i], sa_imgpath[i]);
		notearraylist.add(nt);
		
		
	}*/
	
	inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	//inflater = (LayoutInflater)activity.getLayoutInflater();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return id_note.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
	
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertview, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		try{
			
			/*View vi = convertview;
			//vi = convertview;
			if(convertview == null)
				vi=inflater.inflate(R.layout., null);*/
			 vi = convertview;
			if(convertview == null)
				vi=inflater.inflate(R.layout.notelist, null);
			
			
		//1	
			TextView trsn = (TextView)vi.findViewById(R.id.txt_notename);
			String sdesc = note_name.get(position).toString();
			trsn.setText(sdesc);
			
			//trsn.setText(to_mail.get(position).toString());
			
			TextView msgto = (TextView)vi.findViewById(R.id.txt_notedate1);
			String sdesc1 = note_date.get(position).toString();
			msgto.setText(sdesc1);
			
			TextView tsdt = (TextView)vi.findViewById(R.id.txt_notetime1);
			String sdate = note_time.get(position).toString();
			tsdt.setText(sdate);
			//tsdt.setText(time_mail.get(position).toString());
			
			ImageView iv = (ImageView)vi.findViewById(R.id.imgview_note);
			String path = img_path.get(position).toString();
			
			Drawable drawable1 = Drawable.createFromPath(path);
			//Drawable drawable1 =LoadImageFromWebOperations(path);
			iv.setImageDrawable(drawable1);
			
			

			
		    
		//2	
		
			}catch(Exception e)
			{
				System.out.println("ERROR IN GETVIEW:"+e.toString());
			}
			return vi;
		
		
		
	}
	private Drawable LoadImageFromWebOperations(String url) {
		
		try{
 			InputStream is = (InputStream) new URL(url).getContent();
 			Drawable d = Drawable.createFromStream(is, "src name");
 			return d;
 		}catch (Exception e) {
 			System.out.println("Exc="+e);
 			return null;
 		}
 		
		
	}
	
	// Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        notelist.clear();
        if (charText.length() == 0) {
            notelist.addAll(notearraylist);
        } else {
            for (Note nt : notearraylist) {
                if (nt.getname().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    notelist.add(nt);
                }
            }
        }
        notifyDataSetChanged();
    }
	

	
}
