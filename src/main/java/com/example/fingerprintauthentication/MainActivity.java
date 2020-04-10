package com.example.fingerprintauthentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;




public class MainActivity extends AppCompatActivity {

    private TextView mHeadingLabel;
    private ImageView mFingerprintImage;
    private TextView mParaLabel;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button login = findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

        Button registration = findViewById(R.id.registration_button);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Registration.class));
            }
        });




        mHeadingLabel = (TextView) findViewById(R.id.headingLabel);
        mFingerprintImage = (ImageView) findViewById(R.id.fingerprintImage);
        mParaLabel = (TextView) findViewById(R.id.paraLabel);

        // Check 1: android version should be greater or equal to Marshmallow
        // Check 2: device has fingerprint scanner
        // Chech 3: have permission to use fingerprint scanner in the app
        // Check 4: lock screen is secured with atleast 1 type of lock
        // Check 5: atleast 1 fingerprint is registered


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);


            if(fingerprintManager.isHardwareDetected() == false) {

                mParaLabel.setText("Lettore di impronte digitali non trovato. Usa il login sottostante");

            } else if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

                 mParaLabel.setText("Permesso negato all'uso del fingerprint scanner. Usa il login sottostante");

            }else if(!keyguardManager.isKeyguardSecure()) {

                mParaLabel.setText("Non si ha alcun tipo di blocco settato. Aggiungine uno o usa il login sottostante");

            } else if(!fingerprintManager.hasEnrolledFingerprints()) {

                mParaLabel.setText("Aggiungi almeno un'impronta digitale oppure usa il login sottostante");
            } else {

                mParaLabel.setText("Metti il dito sullo scanner per accedere all'app");

                FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                fingerprintHandler.startAuth(fingerprintManager, null);
            }
        }

    }
}





@TargetApi(Build.VERSION_CODES.M)
class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;
    public FingerprintHandler(Context context) {
        this.context = context;
    }


    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {

        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal,0, this, null);
    }


    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("Errore di autenticazione. " + errString, false);           //troppi tentativi, riprovate più tardi
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Autenticazione fallita", false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Errore: " + helpString, false);                        //tenete il dito sul sensore un po più a lungo
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        //this.update("Login effettuato con successo", true);
        Intent i = new Intent(context, LoginSuccess.class);
        context.startActivity(i);
    }

    private void update(String s, boolean b) {
        TextView paraLabel = (TextView) ((Activity)context).findViewById(R.id.paraLabel);
        ImageView imageView = (ImageView) ((Activity)context).findViewById(R.id.fingerprintImage);

        paraLabel.setText(s);

        if(b == false) {
            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorError));
        } else {
            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            imageView.setImageResource(R.mipmap.action_done);
        }
    }


}




