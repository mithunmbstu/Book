package com.example.user.book.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.user.book.Adapter.ListViewAdapter;
import com.example.user.book.Pojo.ListRowItem;
import com.example.user.book.R;
import com.example.user.book.Volley.AppConfig;
import com.example.user.book.Volley.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowAll extends AppCompatActivity
{

    ListView list;
    private Context context;

    private ListViewAdapter listAdapter;

    public List<String> name = new ArrayList<String>();
    public List <String> description = new ArrayList<String>();
    public List<String> image = new ArrayList<String>();

    private static final String TAG = ShowAll.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        context = getApplicationContext();

        list = (ListView) findViewById(R.id.list);

        showAll();
    }

    private void showAll()
    {
        // Tag used to cancel the request
        String tag_array_req = "req_show";

        StringRequest strReq = new StringRequest(Request.Method.GET, AppConfig.show, new Response.Listener<String>()
        {
            String nam,img,des;

            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONArray array = new JSONArray(response);

                    for(int i=0; i<array.length(); i++)
                    {
                        JSONObject jobj1 = array.getJSONObject(i);
                        nam = jobj1.getString("name");
                        img = jobj1.getString("image");
                        des = jobj1.getString("description");

                        name.add(nam);
                        image.add(img);
                        description.add(des);
                    }


                    List<ListRowItem> eachItem = new ArrayList<ListRowItem>();

                    for (int i = 0; i < name.size(); i++)
                    {
                        eachItem.add(new ListRowItem(image.get(i), name.get(i), description.get(i)));
                    }

                    listAdapter = new ListViewAdapter(context, eachItem);
                    list.setAdapter(listAdapter);


                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                            Intent intent = new Intent(context, Details.class);
                            intent.putExtra("name", name.get(position));
                            intent.putExtra("image", image.get(position));
                            intent.putExtra("description", description.get(position));
                            startActivity(intent);
                        }
                    });

                } catch (JSONException e) {
                e.printStackTrace();
            }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Showing Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_array_req);
    }
}
