package com.example.notebookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class NoteEditorActivity extends AppCompatActivity {
    //NoteId as a global variable
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        //Display text from other activity

        //create edittext
        EditText editText = findViewById(R.id.editText);

        //To get the id from the other intent. -1 as def value to be safe
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        //Check if not -1, set text
        if (noteId != -1)
        {
            editText.setText(MainActivity.notes.get(noteId));
        } else {
            MainActivity.notes.add("");
            //Get the id, -1 cause of indexing
            noteId = MainActivity.notes.size() -1;
            //Update adapter to display changes
            MainActivity.arrayAdapter.notifyDataSetChanged();


        }
        //Save the text if its changed
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Set the changes in the right noteId with string val of charseq
                MainActivity.notes.set(noteId,String.valueOf(s));
                //Access arrayadapter and notify changes
                MainActivity.arrayAdapter.notifyDataSetChanged();


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
