package com.assignment.noteapp;

public class Note {
	
	String name,desc,imgpath,date,time;
	int note_id;
	
	public Note(String name, String date, String time,
            String path) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.imgpath = path;
    }
 
    public String getname() {
        return this.name;
    }
 
    public String getdate() {
        return this.date;
    }
 
    public String gettime() {
        return this.time;
    }
 
    public String getimgpath() {
        return this.imgpath;
    }

}
