package com.ccc.androidcrud1;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ccc.androidcrud1.util.Utilities;

import java.util.HashMap;
import java.util.Map;





public class UpdateItem extends AppCompatActivity implements View.OnClickListener {


    EditText editTextItemName,editTextBrand,editTextPrice;
    Button buttonUpdateItem;
    String itemId, itemName, brand, price;
    TextView usernameText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.update_item);
        String name = getIntent().getStringExtra("name_key");
        usernameText = findViewById(R.id.textView5);
        usernameText.setText(name);


        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemId");
        itemName = intent.getStringExtra("itemName");
        brand = intent.getStringExtra("brand");
        price = intent.getStringExtra("price");



        editTextItemName = (EditText)findViewById(R.id.et_item_name);
        editTextBrand = (EditText)findViewById(R.id.et_brand);
        editTextPrice = (EditText)findViewById(R.id.et_price);


        editTextItemName.setText(itemName);
        editTextBrand.setText(brand);
        editTextPrice.setText(price);



        buttonUpdateItem = (Button)findViewById(R.id.btn_update_item);
        buttonUpdateItem.setOnClickListener(this);


    }

    //This is the part where data is transafeered from Your Android phone to Sheet by using HTTP Rest API calls

    private void   updateItemToSheet() {

        final ProgressDialog loading = ProgressDialog.show(this,"Updating Item","Please wait");
        final String name = editTextItemName.getText().toString().trim();
        final String brand = editTextBrand.getText().toString().trim();
        final String price = editTextPrice.getText().toString().trim();
        final String status = "Pending";




        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utilities.webAppUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(UpdateItem.this,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

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

                //here we pass params
                parmas.put("action","updateItem");
                parmas.put("itemId",itemId);
                parmas.put("itemName",name);
                parmas.put("brand",brand);
                parmas.put("status",status);
                parmas.put("price",price);


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

        if(v==buttonUpdateItem){
            updateItemToSheet();

            //Define what to do when button is clicked
        }
    }
}
