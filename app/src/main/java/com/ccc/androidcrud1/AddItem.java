package com.ccc.androidcrud1;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ccc.androidcrud1.util.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddItem extends AppCompatActivity implements View.OnClickListener {

    TextView usernameText, testing;
    EditText editTextItemName;
    Button buttonAddItem;
    ListView listView;
    SimpleAdapter adapter;
    ProgressDialog loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_item);
        String myname = getIntent().getStringExtra("name_key");

        usernameText = findViewById(R.id.textView5);
        usernameText.setText(myname);

        testing = findViewById(R.id.textTester);

        listView = findViewById(R.id.showSongs);

        editTextItemName = (EditText)findViewById(R.id.et_item_name);

        buttonAddItem = (Button)findViewById(R.id.btn_update_item);
        buttonAddItem.setOnClickListener(this);

        getSongs();
    }

    private void getSongs() {

        final ProgressDialog loading = ProgressDialog.show(this,"Adding Item","Please wait");
        final String name = editTextItemName.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(1, Utilities.musicUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Toast.makeText(AddItem.this,"Success",Toast.LENGTH_LONG).show();
                        testing.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> parmas = new HashMap<>();
                if (name.equals("")){
                    parmas.put("songQuery","New Music");
                } else{
                    parmas.put("songQuery",name);
                }
                return parmas;
            }
        };
        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

    @Override
    public void onClick(View v) {
        if(v==buttonAddItem){
            getSongs();
        }
    }

    private void parseItems(String response) {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try {
            JSONObject jobj = new JSONObject(response);
            JSONArray jarray = jobj.getJSONArray("items");


            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                String artistName = jo.getString("artistName");
                String duration = jo.getString("duration");
                String title = jo.getString("title");
                String videoId = jo.getString("videoId");
                String songLink = jo.getString("songLink");


                HashMap<String, String> item = new HashMap<>();
                item.put("artistName",artistName);
                item.put("duration", duration);
                item.put("title", title);
                item.put("videoId",videoId);
                item.put("songLink",songLink);

                list.add(item);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        adapter = new SimpleAdapter(this,list,R.layout.list_item_row,
                new String[]{"artistName","duration","title","videoId","songLink",},new int[]{R.id.tv_item_name,R.id.tv_brand,R.id.patientStatus});


        listView.setAdapter(adapter);
        loading.dismiss();
    }
}
