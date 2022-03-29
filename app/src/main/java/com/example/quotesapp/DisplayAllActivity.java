package com.example.quotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DisplayAllActivity extends AppCompatActivity {
    DatabaseAdapter databaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all);
        PreCreateDB.copyDB(this);
        databaseAdapter = new DatabaseAdapter(this);
        ListView lvContact = findViewById(R.id.lvContact);
        final SimpleCursorAdapter simpleCursorAdapter = databaseAdapter.populateListViewFromDB();
        lvContact.setAdapter(simpleCursorAdapter);
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) simpleCursorAdapter.getItem(position);
                String quote = cursor.getString(1);
                String author = cursor.getString(2);
                ImageView shareBtn = findViewById(R.id.shareBtn);
                shareBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, quote + "\n\n- " + author);
                        intent.setType("text/plain");
                        startActivity(intent);
                    }
                });
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Quote Copied", quote + "\n\n- " + author);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(DisplayAllActivity.this, "Quote Copied!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}