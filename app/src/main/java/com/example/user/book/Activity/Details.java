package com.example.user.book.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.book.R;
import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {
    ImageView imageView;
    TextView showname,showdescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle bdl = getIntent().getExtras();

        String name = bdl.getString("name");
        String image = bdl.getString("image");
        String description = bdl.getString("description");


        showname= (TextView) findViewById(R.id.showbook);
        showdescription= (TextView) findViewById(R.id.decription);
        imageView= (ImageView) findViewById(R.id.imageshow);

        showname.setText(name);
        showdescription.setText(description);
        Picasso.with(this).load(image).into(imageView);

    }
}
