package com.hololo.library.otpviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Toast;

import com.hololo.library.otpview.OTPListener;
import com.hololo.library.otpview.OTPView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OTPView otpView = (OTPView) findViewById(R.id.otpView);
        otpView.setTextColor(R.color.colorAccent);
        otpView.setHintColor(R.color.colorAccent);
        otpView.setCount(7);
        otpView.setInputType(InputType.TYPE_CLASS_NUMBER);
        otpView.setViewsPadding(16);
        otpView.setListener(new OTPListener() {
            @Override
            public void otpFinished(String otp) {
                Toast.makeText(MainActivity.this, "OTP finished, the otp is " + otp, Toast.LENGTH_SHORT).show();
            }
        });
        otpView.fillLayout();
    }
}
