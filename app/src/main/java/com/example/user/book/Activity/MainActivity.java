package com.example.user.book.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.book.R;

public class MainActivity extends AppCompatActivity {

    Button booklist,insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        booklist= (Button) findViewById(R.id.booklist);
        insert= (Button) findViewById(R.id.insertbutton);

        booklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ShowAll.class);
                startActivity(intent);
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Insert.class);
                startActivity(intent);
            }
        });
    }

}
