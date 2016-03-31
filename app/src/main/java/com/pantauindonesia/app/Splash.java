package com.pantauindonesia.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.widget.TextView;

public class Splash extends Activity {
    MyCounter myTimer;
    TextView a;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        myTimer = new MyCounter(2 * 1000, 100);
        myTimer.start();

    }

    protected void onPause() {
        super.onPause();
        myTimer.cancel();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            myTimer.cancel();
            Splash.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public class MyCounter extends CountDownTimer {

        public MyCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            Intent i = new Intent(Splash.this, GettingStarted.class);
            startActivity(i);
            Splash.this.finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

        }
    }
}