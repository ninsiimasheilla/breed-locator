package com.example.richardsenyange.breedlocator;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

public class Welocome extends AppCompatActivity {
    private CircleMenu circleMenu;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Intent intent;
    private Toast toast;
    String names[]={"Phone","Facebook","Google"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welocome);
        mAuth = FirebaseAuth.getInstance();
        circleMenu=(CircleMenu)findViewById(R.id.circlemenu);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"),R.drawable.login,R.drawable.back)
                .addSubMenu(Color.parseColor("#258CFF"),R.drawable.phn)
                .addSubMenu(Color.parseColor("#6D4CD4"),R.drawable.facebook)
                .addSubMenu(Color.parseColor("#FF0000"),R.drawable.google)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int i) {
                        try{
                            switch (i){
                                case 0:
                                    onStart();
                                    toast= Toast.makeText(getApplicationContext(),"selected "+names[0],Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.BOTTOM,0,0);
                                    toast.show();
                                    intent=new Intent(getApplicationContext(),phonesignup.class);
                                    intent.putExtra("value",i);
                                    startActivity(intent);
                                    break;
                                case 1:
                                    onStart();
                                    toast= Toast.makeText(getApplicationContext(),"selected "+names[1],Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.BOTTOM,0,0);
                                    toast.show();
                                    intent=new Intent(getApplicationContext(),phonesignup.class);
                                    intent.putExtra("value",i);
                                    startActivity(intent);
                                    break;
                                case 2:
                                    onStart();
                                    toast= Toast.makeText(getApplicationContext(),"selected "+names[2],Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.BOTTOM,0,0);
                                    toast.show();
                                    intent=new Intent(getApplicationContext(),phonesignup.class);
                                    intent.putExtra("value",i);
                                    startActivity(intent);
                                    break;
                                default:
                                    break;
                            }


                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser!=null){
            startActivity(new Intent(getApplicationContext(),phonesignup.class));
            finish();
        }

    }
}
