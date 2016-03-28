package com.example.user.book.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.user.book.Pojo.ListRowItem;
import com.example.user.book.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 3/3/2016.
 */
public class ListViewAdapter extends BaseAdapter
{
    Context context;
    List<ListRowItem> eachArrayList;


    public ListViewAdapter(Context context, List<ListRowItem> eachArrayList)
    {
        this.context = context;
        this.eachArrayList = eachArrayList;
    }

    @Override
    public int getCount()
    {
        return eachArrayList.size();
    }

    @Override
    public Object getItem(int i)
    {
        return eachArrayList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View r=view;
        ListViewHolder listholder = null;

        if(r==null)
        {
            LayoutInflater inflt = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            r=inflt.inflate(R.layout.list_single, viewGroup,false);
            listholder=new ListViewHolder(r);
            r.setTag(listholder);
        }

        else
        {
            listholder=(ListViewHolder) r.getTag();
        }

        ListRowItem t=eachArrayList.get(i);

        Picasso.with(context)
                .load(t.getImage())
                .into(listholder.image);

        listholder.name.setText(t.getName()+"");

        return r;
    }
}