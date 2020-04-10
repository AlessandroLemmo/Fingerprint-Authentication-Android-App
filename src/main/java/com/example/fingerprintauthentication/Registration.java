package com.example.fingerprintauthentication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Vector;




public class Registration extends AppCompatActivity {

    private String username;
    private String password;
    private EditText mUsername;
    private EditText mPassword;
    private TextView mError;

    private static Vector<String> usernameVector = new Vector<String>();
    private static Vector<String> passwordVector = new Vector<String>();

    public static Vector<String> getUsernameVector() {
        return usernameVector;
    }
    public static Vector<String> getPasswordVector() {
        return passwordVector;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mUsername = (EditText) findViewById(R.id.username_text);
        mPassword = (EditText) findViewById(R.id.password_text);
        mError = (TextView) findViewById(R.id.error);

        Button registration = findViewById(R.id.registration_button);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = mUsername.getText().toString();
                password = mPassword.getText().toString();

                boolean foundUsername = false;
                boolean foundPassword = false;

                for(int i = 0; i < usernameVector.size(); i++) {
                    if (username.equals(usernameVector.get(i))) {
                        foundUsername = true;
                        break;
                    }
                }

                for(int i = 0; i < passwordVector.size(); i++) {
                    if(password.equals(passwordVector.get(i))) {
                        foundPassword = true;
                        break;
                    }
                }


                if(foundUsername && !foundPassword) {
                    mError.setText("Username già in uso. Riprovare");
                    mError.setTextColor(getResources().getColor(R.color.colorError));

                }
                else if(!foundUsername && foundPassword) {
                    mError.setText("Password già in uso. Riprovare");
                    mError.setTextColor(getResources().getColor(R.color.colorError));

                }
                else if(foundUsername && foundPassword) {
                    mError.setText("Username e password già in uso. Riprovare");
                    mError.setTextColor(getResources().getColor(R.color.colorError));

                }
                else if(username.equals("") || password.equals("")) {
                    mError.setText("Username o password non inseriti correttamente. Riprovare");
                    mError.setTextColor(getResources().getColor(R.color.colorError));
                }
                else {
                    usernameVector.add(username);
                    passwordVector.add(password);
                    mError.setText("Registrazione effettuata con successo");
                }


            }
        });

    }
}
