package com.example.richardsenyange.breedlocator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
private VideoView vidView;
private FirebaseAuth mAuth;
private FirebaseUser mCurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
            //            getSupportActionBar().hide();
                  mCurrentUser=FirebaseAuth.getInstance().getCurrentUser();
            vidView=findViewById(R.id.videoView);
        String uriPath = "android.resource://" + getPackageName() +"/" +R.raw.animation;
        Uri uri = Uri.parse(uriPath);
        vidView.setVideoURI(uri);
        vidView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    if(isFinishing()&&mCurrentUser!=null){

                        startActivity(new Intent(getApplicationContext(),Home.class));
                        finish();
                        return;
                    }else {

                        startActivity(new Intent(getApplicationContext(),LoginPage.class));
                        finish();
                    }


                }
            });
          vidView.requestFocus();
            vidView.start();

    }

}
