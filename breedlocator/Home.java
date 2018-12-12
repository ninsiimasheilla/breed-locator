package com.example.richardsenyange.breedlocator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.widget.NavigationView;

import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;


import com.example.richardsenyange.breedlocator.DrawerOptions.About;
import com.example.richardsenyange.breedlocator.DrawerOptions.BreedHistory;
import com.example.richardsenyange.breedlocator.DrawerOptions.Friends;
import com.example.richardsenyange.breedlocator.DrawerOptions.Help;
import com.example.richardsenyange.breedlocator.DrawerOptions.Settings;
import com.example.richardsenyange.breedlocator.DrawerOptions.Statistics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import  com.example.richardsenyange.breedlocator.Adapters.SectionsPagerAdapter;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    private CircleImageView prof_image;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TextView user_name,user_email;
private  FirebaseAuth mAuth;
private FirebaseUser mCurrentUser;
    private DatabaseReference mUserRef;

    private TabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try{
            mAuth=FirebaseAuth.getInstance();
            mCurrentUser=mAuth.getCurrentUser();
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            prof_image=(CircleImageView)findViewById(R.id.profile_image);
            user_name=(TextView)findViewById(R.id.prof_user_name);

            user_email=(TextView)findViewById(R.id.prog_user_email);
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.open, R.string.close);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            // NavigationView navigationViewRight = (NavigationView) findViewById(R.id.nav_view_right);
            // navigationViewRight.setNavigationItemSelectedListener(this);
            navigationView.setNavigationItemSelectedListener(this);

            //  FirebaseDatabase.getInstance().setPersistenceEnabled(true);

            mAuth = FirebaseAuth.getInstance();
            // getSupportActionBar().setTitle("Animal Breeder");

            if (mAuth.getCurrentUser() != null) {

                mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
                mUserRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String display_name = dataSnapshot.child("name").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        String image = dataSnapshot.child("image").getValue().toString();
                        //setting name and email in nav drawer
                        user_email.setText(email);
                        user_name.setText(display_name);
                        Picasso.with(getApplicationContext()).load(image).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.avatar).into(prof_image);}

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }


            //Tabs
            mViewPager = (ViewPager) findViewById(R.id.main_tabPager);
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            mViewPager.setAdapter(mSectionsPagerAdapter);

            mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
            mTabLayout.setupWithViewPager(mViewPager);

        }catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.history) {
            startActivity(new Intent(getApplicationContext(), BreedHistory.class));
        } else if (id == R.id.statistics) {
            startActivity(new Intent(getApplicationContext(), Statistics.class));
        } else if (id == R.id.friends) {
            startActivity(new Intent(getApplicationContext(), Friends.class));
        } else if (id == R.id.settings) {
            startActivity(new Intent(getApplicationContext(), Settings.class));
        } else if (id == R.id.about) {
            startActivity(new Intent(getApplicationContext(), About.class));
        } else if (id == R.id.help) {
            startActivity(new Intent(getApplicationContext(), Help.class));
        }else if(id==R.id.signout){ FirebaseAuth.getInstance().signOut(); startActivity(new Intent(getApplicationContext(),LoginPage.class));

        finish();}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
