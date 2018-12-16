package com.assignment.noteapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Note_Adapter1 extends BaseAdapter{
	Context nContext;
    LayoutInflater inflater;
    private List<Note> notelist = null;
    private ArrayList<Note> arraylist;
	
	 public Note_Adapter1(Context context,List<Note> notelist) {
		
        nContext = context;
        this.notelist = notelist;
        inflater = LayoutInflater.from(nContext);
        this.arraylist = new ArrayList<Note>();
        this.arraylist.addAll(notelist);
    }
	 public class ViewHolder {
	        TextView name;
	        TextView date;
	        TextView time;
	        ImageView imgpath;
	    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return notelist.size();
	}

	@Override
	public Note getItem(int position) {
		// TODO Auto-generated method stub
		return notelist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		
		final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.notelist, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.txt_notename);
            holder.date = (TextView) view.findViewById(R.id.txt_notedate1);
            holder.time = (TextView) view.findViewById(R.id.txt_notetime1);
            // Locate the ImageView in listview_item.xml
            holder.imgpath = (ImageView) view.findViewById(R.id.imgview_note);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(notelist.get(position).getname());
        holder.date.setText(notelist.get(position).getdate());
        holder.time.setText(notelist.get(position)
                .gettime());
        // Set the results into ImageView
        
        String path = notelist.get(position).getimgpath();
		
		Drawable drawable1 = Drawable.createFromPath(path);
        /*holder.imgpath.setImageResource(notelist.get(position)
                .getimgpath());*/
		holder.imgpath.setImageDrawable(drawable1);
		
		
		return view;
	}

	 public void filter(String charText) {
	        charText = charText.toLowerCase(Locale.getDefault());
	        notelist.clear();
	        if (charText.length() == 0) {
	            notelist.addAll(arraylist);
	        } else {
	            for (Note wp : arraylist) {
	                if (wp.getname().toLowerCase(Locale.getDefault())
	                        .contains(charText)) {
	                    notelist.add(wp);
	                }
	            }
	        }
	        notifyDataSetChanged();
	    }
	 
	
	
}
