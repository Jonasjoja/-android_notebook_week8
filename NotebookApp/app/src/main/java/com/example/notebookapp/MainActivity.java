package com.example.notebookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Arraylist of string
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    //Oncreate options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflate menu and assign create menu to menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    //On option item selected options, to create add
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        //To see if equal to id created in menu item
        if (item.getItemId() == R.id.add_note)
        {
            //If yes, get context and jump to noteeditor class
            Intent intent = new Intent(getApplicationContext(),NoteEditorActivity.class);
            //And start activity
            startActivity(intent);

            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Defining the listview
        ListView listView = findViewById(R.id.listView);
        //Example note to array
        notes.add("Example note");

        //ArrayAdapter. "This" for context, simple list as layout/format and notes as the list array
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, notes);
        //Sets listview's adapter
        listView.setAdapter(arrayAdapter);

            //Click listener for clicking items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent for getting context and letting know its noteeditoractivity class as location
                Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
                //Let new activity know which item is tapped
                intent.putExtra("noteId",position);
                startActivity(intent);
            }
        });
        //To enable deletion via long click
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //sets the dialog, title, message ect.
                //Set context to mainactivity
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Confirm deletion")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //delete and notify adapter if yes is pressed
                                notes.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }
                        )
                        //if no, do nothing
                        .setNegativeButton("No",null)
                        //Show the dialog
                        .show();

                //True to make sure it doesnt assume a shortclick is done too
                return true;
            }
        });
    }
}
