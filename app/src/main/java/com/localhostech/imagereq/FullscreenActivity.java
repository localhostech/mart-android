package com.localhostech.imagereq;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class FullscreenActivity extends AppCompatActivity {
    Animation fadeout;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fullscreen);
        fadeout = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        image = (ImageView) findViewById(R.id.splashscreen);
        image.startAnimation(fadeout);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(FullscreenActivity.this,ScrollingActivity.class);
                FullscreenActivity.this.startActivity(mainIntent);
                FullscreenActivity.this.finish();
            }
        }, 1000);
    }


}
