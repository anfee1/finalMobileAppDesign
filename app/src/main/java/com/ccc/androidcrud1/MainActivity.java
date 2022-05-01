package com.ccc.androidcrud1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonAddItem,buttonListItem,buttonThird,buttonLogout;
    TextView usernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String name = getIntent().getStringExtra("name_key");
        usernameText = findViewById(R.id.textView5);
        usernameText.setText(name);

        buttonAddItem = (Button)findViewById(R.id.btn_add);
        buttonListItem = (Button)findViewById(R.id.btn_view_items);
        buttonThird = (Button)findViewById(R.id.btn_third);
        buttonLogout = (Button)findViewById(R.id.btn_logout);
        buttonAddItem.setOnClickListener(this);
        buttonListItem.setOnClickListener(this);
        buttonThird.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v==buttonAddItem){
            Intent intent = new Intent(getApplicationContext(),AddItem.class);
            intent.putExtra("name_key", usernameText.getText().toString().trim());
            startActivity(intent);
        }

        if(v==buttonListItem){
            Intent intent = new Intent(getApplicationContext(),ListItem.class);
            intent.putExtra("name_key", usernameText.getText().toString().trim());
            startActivity(intent);
        }

        if(v==buttonThird){
            Intent intent = new Intent(getApplicationContext(),MovieAdder.class);
            intent.putExtra("name_key", usernameText.getText().toString().trim());
            startActivity(intent);
        }

        if(v==buttonLogout){
            Intent intent = new Intent(getApplicationContext(),LogIn.class);
            startActivity(intent);
        }

    }
}