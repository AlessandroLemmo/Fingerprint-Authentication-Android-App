package com.example.fingerprintauthentication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class LoginSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);


        Button login = findViewById(R.id.login_button);
        Button registration = findViewById(R.id.registration_button);

        login.setEnabled(false);
        registration.setEnabled(false);
    }
}
