package com.srit_nss_reports;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {


    EditText username,password;

    Button login,guest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        guest = (Button) findViewById(R.id.guest);
        login = (Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().equals("Admin") && password.getText().toString().equals("username") ) {
                    openAdmin();
                }else{
                    Toast.makeText(getApplicationContext(),"Invalid ",Toast.LENGTH_SHORT).show();
                }
            }
        });
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGuest();
            }
        });
    }
    public void openAdmin() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }
    public void openGuest() {
        Intent intent = new Intent(LoginActivity.this, GuestActivity.class);
        startActivity(intent);
    }


}