package com.example.user.book.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.user.book.R;
import com.example.user.book.Volley.AppConfig;
import com.example.user.book.Volley.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Insert extends AppCompatActivity {

    ImageView imageView;
    TextView insertname, insetdescription;
    private static int RESULT_LOAD_IMG = 1;
    private String fileName;
    private String encodedString;
    Button save;
    String TAG;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        imageView = (ImageView) findViewById(R.id.insertimage);
        insertname = (TextView) findViewById(R.id.inserttext);
        insetdescription = (TextView) findViewById(R.id.insertdescription);
        save = (Button) findViewById(R.id.savebutton);
        pDialog=new ProgressDialog(this);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Insert.this);
                alertDialogBuilder.setMessage("Select Photo Via");

                alertDialogBuilder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, 0);
                    }
                });

                alertDialogBuilder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        // Start the Intent
                        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=insertname.getText().toString();
                String description=insetdescription.getText().toString();
                postAdd(encodedString,name,description);

            }
        });

    }

    // When Image is selected from Gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            String fileNameSegments[] = picturePath.split("/");
            fileName = fileNameSegments[fileNameSegments.length - 1];

            Bitmap myImg = BitmapFactory.decodeFile(picturePath);
            imageView.setImageBitmap(myImg);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Must compress the Image to reduce image size to make upload easy
            myImg.compress(Bitmap.CompressFormat.PNG, 50, stream);
            byte[] byte_arr = stream.toByteArray();
            // Encode Image to String
            encodedString = Base64.encodeToString(byte_arr, 0);

        } else if (requestCode == 0 && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap myImg = (Bitmap) extras.get("data");
            imageView.setImageBitmap(myImg);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Must compress the Image to reduce image size to make upload easy
            myImg.compress(Bitmap.CompressFormat.PNG, 50, stream);
            byte[] byte_arr = stream.toByteArray();
            // Encode Image to String
            encodedString = Base64.encodeToString(byte_arr, 0);

        }


    }


    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void postAdd( final String image,final String description,final String name) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        Log.e(TAG, "METHOD CALLED.......................................");
        //Toast.makeText(this,"jahdsahsdjklhas",Toast.LENGTH_LONG).show();
        pDialog.setMessage("Posting ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response)
            {
                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                Log.d(TAG, "Register Response: " + response.toString());
                List<String>nameList = new ArrayList<String>();
                List<String>imageList= new ArrayList<String>();
                List<String>descriptionList= new ArrayList<String>();
                try {
                    JSONArray newArray=new JSONArray(response);
                    for (int i=0;i<newArray.length();i++)
                    {
                        JSONObject jsonObject=newArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String image = jsonObject.getString("image");
                        String description = jsonObject.getString("description");
                        Log.d("JSON DATAS:" , name + "," + image + "," + description);
                        nameList.add(name);
                        imageList.add(image);
                        descriptionList.add(description);}

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent(Insert.this,Details.class);
                intent.putExtra("name",nameList.get(nameList.size()-1));
                intent.putExtra("image",imageList.get(imageList.size()-1));
                intent.putExtra("description",descriptionList.get(descriptionList.size()-1));
                startActivity(intent);
                hideDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                //
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("image", image);
                params.put("description", description);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}
