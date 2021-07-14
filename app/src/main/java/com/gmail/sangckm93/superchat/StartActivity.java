package com.gmail.sangckm93.superchat;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.annotation.Annotation;


public class StartActivity extends AppCompatActivity {

    Button login, register;
    FirebaseUser firebaseUser;

    private static int SFLASH_TIME_OUT = 1800;
    Animation smalltobig;
    ImageView ivIcon;
    @Override
    protected void onStart() {
        super.onStart();
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        //Check if User is !null-> auto login
//        if (firebaseUser != null){
//            Intent intent = new Intent(StartActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ivIcon = findViewById(R.id.icon_view);
        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        ivIcon.startAnimation(smalltobig);

        login = findViewById(R.id.login);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                StartActivity.this.finish();
                //Check if User is !null-> auto login
                if (firebaseUser != null){
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                    finish();
                }else{
                    startActivity(new Intent(StartActivity.this, LoginActivity.class));
                    overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                }
            }
        },SFLASH_TIME_OUT);
//        login = findViewById(R.id.login);
//        register = findViewById(R.id.register);
//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(StartActivity.this, LoginActivity.class));
//            }
//        });
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
//            }
//        });

    }
}
