package com.sim.mundylingo.Activities;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import com.sim.mundylingo.AboutUs.AboutUsActivity;
import com.sim.mundylingo.Fragment.CoursFragment;
import com.sim.mundylingo.Fragment.HomeFragment;
import com.sim.mundylingo.Fragment.LeadFragment;
import com.sim.mundylingo.Fragment.ProfileFragment;
import com.sim.mundylingo.R;

import static com.sim.mundylingo.Activities.LoginActivity.IPadress;


public class MainActivity extends AppCompatActivity {
    //**************************declarations *******************************
    private Toolbar toolbar;
    private CoordinatorLayout mainlayout;
    Menu menu;

    //************************** Urls  **********************************************************

    private static final String URLi = IPadress + "/miniProjetWebService/Langue/cours/getAllCourses.php";

    //*********************** sounds **********************************************

    //*********************** animations  **********************************************
    Animation anim;
//TODO: getters and setters


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar); //to set each fragment title
        menu = findViewById(R.id.side_menu);
        mainlayout = findViewById(R.id.mainlayout);


        //animation effects

        BottomNavigationView navigation = findViewById(R.id.navigation); //to navigate to each fragment i nead a ref for the nav bottom view
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        // load the home fragment  :   fragment by default

        toolbar.setTitle("Home");

        loadFragment(new HomeFragment());
        setSupportActionBar(toolbar);


    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_shop:


                    toolbar.setTitle("Home");

                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_gifts:
                    toolbar.setTitle("Lessons");
                    fragment = new CoursFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_cart:

                    toolbar.setTitle("Ranking");

                    fragment = new LeadFragment();
                    loadFragment(fragment);

                    return true;
                case R.id.navigation_profile:

                    toolbar.setTitle("Profile");

                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    /**
     * loading fragment into FrameLayout
     *
     * @param fragment
     */
    //function 3adeya n3aytilha bech nloadi another fragment
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onSelect() {

        Intent intent = new Intent(MainActivity.this, CoursEnglishActivity.class);
        startActivity(intent);
    }
public void UpdateCurrentUser(){
        String url =  IPadress + "/miniProjetWebService/";
}

//todo: add menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.side_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.side_fav: {
                Intent intent = new Intent(MainActivity.this, CoursPrefActivity.class);
                MainActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(intent);
                return true;
            }
            case R.id.side_about_us:{
                Intent i = new Intent(MainActivity.this , AboutUsActivity.class);
                MainActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(i);
                return true;
            }


            case R.id.side_logout:
                logoutFacebook();
                return true;

        }
        return true;
    }


    public void logoutFacebook() {

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        MainActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(intent);
    }

}
