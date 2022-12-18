package com.example.mynotes.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//digunakan untuk mendeteksi object yang digunakan untuk room
@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    //digunakan untuk kolom
    @ColumnInfo(name="id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "date")
    private String date;

    //constructor tanpa id karena di generate auto increment
    public Note(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

//    @Ignore
//    public Note(){
//
//    }

    public Note(int id, String title, String desc, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    //membuat tostring agar tidak muncul object - object seperti PBO
    public String toString(){
        return this.title + "\n" + this.description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
