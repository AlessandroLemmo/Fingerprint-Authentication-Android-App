package com.example.fingerprintauthentication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Vector;




public class Login extends AppCompatActivity {

    private String username;
    private String password;
    private EditText mUsername;
    private EditText mPassword;
    private TextView mError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mUsername = (EditText) findViewById(R.id.username_text);
        mPassword = (EditText) findViewById(R.id.password_text);
        mError = (TextView) findViewById(R.id.error);



        Button confirm = findViewById(R.id.confirm_button);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = mUsername.getText().toString();
                password = mPassword.getText().toString();

                Vector<String> usernameVector = Registration.getUsernameVector();
                Vector<String> passwordVector = Registration.getPasswordVector();
                boolean found = false;

                for(int i = 0; i < usernameVector.size(); i++) {
                    if (username.equals(usernameVector.get(i)) && password.equals(passwordVector.get(i))) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    startActivity(new Intent(Login.this, LoginSuccess.class));
                }
                else {
                    mError.setText("Username o password errati. Riprovare");
                    mError.setTextColor(getResources().getColor(R.color.colorError));
                }
            }
        });


    }
}
