package com.hololo.library.otpviewexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.widget.Toast;

import com.hololo.library.otpview.OTPListener;
import com.hololo.library.otpview.OTPView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OTPView otpView = findViewById(R.id.otpView);
        otpView.setTextColor(R.color.colorAccent)
                .setHintColor(R.color.colorAccent)
                .setCount(7)
                .setInputType(InputType.TYPE_CLASS_NUMBER)
                .setViewsPadding(16)
                .setListener(new OTPListener() {
                    @Override
                    public void otpFinished(String otp) {
                        Toast.makeText(MainActivity.this, "OTP finished, the otp is " + otp, Toast.LENGTH_SHORT).show();
                    }
                })
                .fillLayout();
    }
}
