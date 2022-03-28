package com.example.mobileoperatormanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView login, password;
    private Button connectionBtn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login =  findViewById(R.id.LP_username);
        password = findViewById(R.id.LP_password);
        connectionBtn = findViewById(R.id.connexion);



        connectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( password.getText().toString().equals("123456") && login.getText().toString()!=null) {
                //correct
                    String username = login.getText().toString();
                    Intent intent = new Intent( MainActivity.this,UserProfile.class);
                    intent.putExtra("usernameValue",username);
                    startActivity(intent);
                }else
                    //incorrect
                    Toast.makeText(MainActivity.this,"LOGIN FAILED !!!",Toast.LENGTH_SHORT).show();
            }

        });
    }
}