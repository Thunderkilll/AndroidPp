package com.dev.thunderkilll.mundylingo.Activities;

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
import com.dev.thunderkilll.mundylingo.Adapters.CoursAdapter;
import com.dev.thunderkilll.mundylingo.Adapters.UserAdapter;
import com.dev.thunderkilll.mundylingo.Models.Cour;
import com.dev.thunderkilll.mundylingo.Models.User;
import com.dev.thunderkilll.mundylingo.R;
import com.dev.thunderkilll.mundylingo.fragment.CoursFragment;
import com.dev.thunderkilll.mundylingo.fragment.HomeFragment;
import com.dev.thunderkilll.mundylingo.fragment.LeadFragment;
import com.dev.thunderkilll.mundylingo.fragment.ProfileFragment;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.dev.thunderkilll.mundylingo.Activities.LoginActivity.IPadress;

public class MainActivity extends AppCompatActivity {
    //**************************declarations *******************************
    private Toolbar toolbar;
    private CoordinatorLayout mainlayout;
    Menu menu;

    //************************** Urls  **********************************************************

    private static final String URLi = IPadress + "/miniProjetWebService/Langue/cours/getAllCourses.php";

    //*********************** sounds **********************************************
    MediaPlayer btn_sound;
    MediaPlayer btn_sound1;
    MediaPlayer btn_sound2;
    MediaPlayer btn_sound3;
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
        FacebookSdk.sdkInitialize(getApplicationContext());//initialize fb sdk

        //sound effects
        btn_sound = MediaPlayer.create(MainActivity.this, R.raw.bouncy_sound);
        btn_sound1 = MediaPlayer.create(MainActivity.this, R.raw.errorsound);
        btn_sound2 = MediaPlayer.create(MainActivity.this, R.raw.gigital_life1);
        btn_sound3 = MediaPlayer.create(MainActivity.this, R.raw.scifi);
        //animation effects

        BottomNavigationView navigation = findViewById(R.id.navigation); //to navigate to each fragment i nead a ref for the nav bottom view
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        // load the home fragment  :   fragment by default

        toolbar.setTitle("Home");
        toolbar.setLogo(R.drawable.toolbarlogo);
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
                    // toolbar.setTitle("Shop");
                    btn_sound.start();
                    toolbar.setTitle("Home");
                    toolbar.setLogo(R.drawable.toolbarlogo);
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_gifts:
                    //toolbar.setTitle("My Gifts");
                    btn_sound.start();
                    toolbar.setTitle("main courses");
                    toolbar.setLogo(R.drawable.toolbarlogo);
                    fragment = new CoursFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_cart:
                    // toolbar.setTitle("Cart");
                    btn_sound.start();
                    toolbar.setTitle("Leaderboard");
                    toolbar.setLogo(R.drawable.toolbarlogo);
                    fragment = new LeadFragment();
                    loadFragment(fragment);

                    return true;
                case R.id.navigation_profile:
                    btn_sound.start();
                    toolbar.setTitle("Profile");
                    toolbar.setLogo(R.drawable.toolbarlogo);
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

            case R.id.side_settings:

            case R.id.side_logout:
                logoutFacebook();
                return true;

        }
        return true;
    }


    public void logoutFacebook() {
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        MainActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(intent);
    }

}
