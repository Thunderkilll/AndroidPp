package com.sim.mundylingo.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.sim.mundylingo.Helpers.DatabaseHelper;
import com.sim.mundylingo.Models.User;
import com.sim.mundylingo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static com.sim.mundylingo.Activities.LoaderActivity.UseritemsList;
import static com.sim.mundylingo.Activities.LoaderActivity.UseritemsListFr;
import static com.sim.mundylingo.Activities.LoaderActivity.UseritemsListGr;
import static com.sim.mundylingo.Activities.LoaderActivity.UseritemsListSp;
import static com.sim.mundylingo.Activities.LoaderActivity.getmAdapter;
import static com.sim.mundylingo.Activities.LoaderActivity.mAdapterFR;
import static com.sim.mundylingo.Activities.LoaderActivity.mAdapterGR;
import static com.sim.mundylingo.Activities.LoaderActivity.mAdapterSP;
import static com.sim.mundylingo.Activities.LoginActivity.IPadress;
import static com.sim.mundylingo.Activities.LoginActivity.currentUser;

public class LevelCompleActivity extends AppCompatActivity {

    private ImageView imageButton7, imageButton8;
    public String url = IPadress + "/miniProjetWebService/selectUser.php?email=" + currentUser.getEmail();
    public String url2 = "http://41.226.11.252:1180/lingued/miniProjetWebService/Langue/leaderboard/LeaderboardENG.php";
    public String url3 = "http://41.226.11.252:1180/lingued/miniProjetWebService/Langue/leaderboard/LeaderboardFR.php";
    public String url4 = "http://41.226.11.252:1180/lingued/miniProjetWebService/Langue/leaderboard/LeaderboardESP.php";
    public String url5 = "http://41.226.11.252:1180/lingued/miniProjetWebService/Langue/leaderboard/LeaderboardGER.php";
    DatabaseHelper Mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_comple);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageButton7 = findViewById(R.id.imageView6);
        imageButton8 = findViewById(R.id.imageView7);
        Mydb = new DatabaseHelper(this);
        imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("buttonPushed", "imagebutton 7 is pushed");
                getUser();

            }
        });
        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i1 = new Intent(LevelCompleActivity.this, CoursFrancais.class);
                Intent i2 = new Intent(LevelCompleActivity.this, CoursEnglishActivity.class);
                Intent i3 = new Intent(LevelCompleActivity.this, CoursGermanActivity.class);
                Intent i4 = new Intent(LevelCompleActivity.this, CoursSpanish.class);
                startActivity(i2);
            }
        });

    }

    public void getUser() {
        url += "&password=" + currentUser.getPassword();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                try {


                    JSONObject jObj = jsonArray.getJSONObject(0);
                    if (jObj.equals("user doest exists[]")) {


                    } else {
                        User user = new User();
                        user.setImgUrl(jObj.getString("image"));
                        user.setLevelFr(jObj.getString("levelFr"));
                        user.setLevelEng(jObj.getString("levelAng"));
                        user.setLevelSpan(jObj.getString("levelEsp"));
                        user.setLevelGer(jObj.getString("levelGer"));
                        user.setScoreFr(jObj.getString("scoreF"));
                        user.setScoreEng(jObj.getString("scoreAng"));
                        user.setScoreSpan(jObj.getString("scoreEsp"));
                        user.setScoreGer(jObj.getString("scoreGerm"));
                        System.out.println();
                        System.out.println();
                        System.out.println();
                        currentUser = user;

                        System.out.println();
                        System.out.println();
                        Mydb.DeleteAllUsers();
                        Mydb.InsertUserData(currentUser);
                        Mydb.close();


                        Intent i = new Intent(LevelCompleActivity.this, MainActivity.class);
                        startActivity(i);
                        LevelCompleActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(this));
        requestQueue.add(jsonArrayRequest);


    }


    //***********get leaderboard list of english language***********
    public void getListEnglish () {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url2, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("index>>>>>>", jsonArray.toString());


                try {


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jObj = jsonArray.getJSONObject(i);
                        User user = new User(jObj.getString("email"), jObj.getString("image"), jObj.getString("scoreAng"));
                        UseritemsList.add(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getmAdapter().leadersList = UseritemsList;
                //mAdapter = new UserAdapter(getActivity(), itemsList);

                //change status

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);


    }

    //***********get leaderboard list of french language***********
    public void getListFrench () {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url3, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("index>>>>>>", jsonArray.toString());


                try {


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jObj = jsonArray.getJSONObject(i);
                        User user = new User(jObj.getString("email"), jObj.getString("image"), jObj.getString("scoreF"));
                        UseritemsListFr.add(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAdapterFR.leadersList = UseritemsListFr;
                //mAdapter = new UserAdapter(getActivity(), itemsList);

                mAdapterFR.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
        System.out.println("french list from db");

    }

    //***********get leaderboard list of spanish language***********
    public void getListSpanish () {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url4, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
//TODO: fill in the list of 5 best user in spanish
                try {


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jObj = jsonArray.getJSONObject(i);
                        User user = new User(jObj.getString("email"), jObj.getString("image"), jObj.getString("scoreEsp"));
                        UseritemsListSp.add(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAdapterSP.leadersList = UseritemsListSp;
                mAdapterSP.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);


    }



    //***********get leaderboard list of german language***********
    public void getListGerman () {
//TODO: fill in the list of 5 best user in german

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url5, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                try {
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jObj = jsonArray.getJSONObject(i);
                        User user = new User(jObj.getString("email"), jObj.getString("image"), jObj.getString("scoreGerm"));
                        UseritemsListGr.add(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAdapterGR.leadersList = UseritemsListGr;
                mAdapterGR.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);


    }
}
