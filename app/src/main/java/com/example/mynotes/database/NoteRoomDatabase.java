package com.example.mynotes.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//menambah anotasi database entiti dan database version
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteRoomDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    //agar noteroomdatabase bisa diakses dari luar
    public static volatile NoteRoomDatabase INSTANCE;

    public static NoteRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){ //biar gak harus define create database berulang kali
            synchronized (NoteRoomDatabase.class){
                //membuat database
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        NoteRoomDatabase.class, "note_database").build();
            }
        }
        return INSTANCE;
    }
}
