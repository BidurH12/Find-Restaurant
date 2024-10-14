package com.example.findrestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashActivity extends AppCompatActivity {
    TextView textView;
    TextView textView2;
    CircleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textView=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);
        imageView=findViewById(R.id.imageView);
        Intent intent=new Intent(SplashActivity.this,Log_SignUp.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        },2500);




        Animation left= AnimationUtils.loadAnimation(this,R.anim.splash_anim);
        Animation right=AnimationUtils.loadAnimation(this,R.anim.splash_anim1);
         Animation blink=AnimationUtils.loadAnimation(this,R.anim.logo);
        textView.startAnimation(left);
        textView2.startAnimation(right);
        imageView.startAnimation(blink);
    }
}