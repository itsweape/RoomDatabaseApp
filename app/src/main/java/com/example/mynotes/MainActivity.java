package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.mynotes.database.Note;
import com.example.mynotes.database.NoteDao;
import com.example.mynotes.database.NoteRoomDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private NoteDao mNotesDao;
    private ExecutorService executorService;
    private ListView listView;
    private Button btnAddNote, btnEditNote;
    private EditText edtTitle, edtDesc, edtDate;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lv_notes);
        btnAddNote = findViewById(R.id.btn_add_note);
        btnEditNote = findViewById(R.id.btn_update_note);

        edtTitle = findViewById(R.id.edt_title);
        edtDesc = findViewById(R.id.edt_desc);
        edtDate = findViewById(R.id.edt_date);

        //untuk menjalankan CUD di background layar
        executorService = Executors.newSingleThreadExecutor();

        //get database
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(this);
        mNotesDao = db.noteDao();

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                String desc = edtDesc.getText().toString();
                String date = edtDate.getText().toString();
                insertData(new Note(title, desc, date));
                setEmptyField();
            }
        });

        btnEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                String desc = edtDesc.getText().toString();
                String date = edtDate.getText().toString();
                updateData(new Note(id, title, desc, date));
                id = 0;
                Log.d("cekid", "onclick " + id);
                setEmptyField();
                getAllNotes();
            }
        });

        //ketika item listview di klik maka edit teks terisi data yang sudah diinput tadi
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note item = (Note) adapterView.getAdapter().getItem(i);
                id = item.getId();
                edtTitle.setText(item.getTitle());
                edtDesc.setText(item.getDescription());
                edtDate.setText(item.getDate());
            }
        });

        //Ketika item listview di klik/tekan lama maka item akan terhapus
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note item = (Note) adapterView.getAdapter().getItem(i);
                deleteData(item);
                return true;
            }
        });
        getAllNotes();
    }

    private void setEmptyField(){
        edtTitle.setText("");
        edtDesc.setText("");
        edtDate.setText("");
    }

    //function mendapatkan semua data notes di database
    private void getAllNotes(){
        mNotesDao.getAllNotes().observe(this, notes -> {
            ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(this,
                    android.R.layout.simple_list_item_1, notes);
            listView.setAdapter(adapter);
        });
    }

    //function insert data ke room
    private void insertData(Note note){
        executorService.execute(() -> mNotesDao.insert(note));

    }

    //function update data
    private void updateData(Note note){
        executorService.execute(() -> mNotesDao.update(note));

    }

    //function delete data
    private void deleteData(Note note){
        executorService.execute(() -> mNotesDao.delete(note));

    }
}