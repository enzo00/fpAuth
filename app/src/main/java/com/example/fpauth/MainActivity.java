package com.example.fpauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    private FingerprintManager fingerprintManager;
    private FingerprintManager.AuthenticationCallback authenticationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lottieAnimationView =findViewById(R.id.fpanimation);
        fingerprintManager = (FingerprintManager)getSystemService(FINGERPRINT_SERVICE);
        authenticationCallback = new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                lottieAnimationView.setAnimation(R.raw.fail);
                lottieAnimationView.setSpeed(1);
                lottieAnimationView.playAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lottieAnimationView.setSpeed(-1);
                        lottieAnimationView.playAnimation();
                    }
                }, 2000);

            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                lottieAnimationView.setAnimation(R.raw.success);
                lottieAnimationView.setSpeed(1);
                lottieAnimationView.playAnimation();
               new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    startActivity(new Intent(MainActivity.this,DashboardActivity.class));
                    finish();
                    }
                }, 2000);

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                lottieAnimationView.setAnimation(R.raw.fail);
                lottieAnimationView.setSpeed(1);
                lottieAnimationView.playAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lottieAnimationView.setSpeed(-1);
                        lottieAnimationView.playAnimation();
                    }
                }, 2000);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        fingerprintManager.authenticate(null,null,0,authenticationCallback,null);
    }
}