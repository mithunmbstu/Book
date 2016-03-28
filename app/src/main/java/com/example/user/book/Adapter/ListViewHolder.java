package com.example.user.book.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.book.R;

/**
 * Created by User on 3/3/2016.
 */
public class ListViewHolder
{
    ImageView image;
    TextView name;

    public ListViewHolder(View v)
    {
        super();
        image = (ImageView) v.findViewById(R.id.img);
        name = (TextView) v.findViewById(R.id.txt);

    }
}
