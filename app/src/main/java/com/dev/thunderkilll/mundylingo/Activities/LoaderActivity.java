package com.dev.thunderkilll.mundylingo.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import steelkiwi.com.library.DotsLoaderView;

import static com.dev.thunderkilll.mundylingo.Activities.LoginActivity.IPadress;

public class LoaderActivity extends Activity {

    public static List<User> UseritemsList;
    public static List<User> UseritemsListFr;
    public static List<User> UseritemsListSp;
    public static List<User> UseritemsListGr;
    public static List<Cour> courList;
    public static UserAdapter mAdapter;
    public static UserAdapter mAdapterFR;
    public static UserAdapter mAdapterSP;
    public static UserAdapter mAdapterGR;
    public static CoursAdapter m2Adapter;
    static String status ="";
    private static final String URLi = IPadress + "/miniProjetWebService/Langue/cours/getAllCourses.php";
    Thread splashTread;
    DotsLoaderView dotsLoaderViewt;
    //************************** Urls  **********************************************************
    public String url2 = IPadress + "/miniProjetWebService/Langue/leaderboard/LeaderboardENG.php";
    public String url3 = IPadress + "/miniProjetWebService/Langue/leaderboard/LeaderboardFR.php";
    public String url4 = IPadress + "/miniProjetWebService/Langue/leaderboard/LeaderboardESP.php";
    public String url5 = IPadress + "/miniProjetWebService/Langue/leaderboard/LeaderboardGER.php";
// *******************************getters and setters *********************************************


    public static List<User> getUseritemsList() {
        return UseritemsList;
    }

    public static void setUseritemsList(List<User> useritemsList) {
        UseritemsList = useritemsList;
    }

    public static List<User> getUseritemsListFr() {
        return UseritemsListFr;
    }

    public static void setUseritemsListFr(List<User> useritemsListFr) {
        UseritemsListFr = useritemsListFr;
    }

    public static List<User> getUseritemsListSp() {
        return UseritemsListSp;
    }

    public static void setUseritemsListSp(List<User> useritemsListSp) {
        UseritemsListSp = useritemsListSp;
    }

    public static List<User> getUseritemsListGr() {
        return UseritemsListGr;
    }

    public static void setUseritemsListGr(List<User> useritemsListGr) {
        UseritemsListGr = useritemsListGr;
    }

    public static List<Cour> getCourList() {
        return courList;
    }

    public static void setCourList(List<Cour> courList) {
        LoaderActivity.courList = courList;
    }

    public static UserAdapter getmAdapter() {
        return mAdapter;
    }

    public static void setmAdapter(UserAdapter mAdapter) {
        LoaderActivity.mAdapter = mAdapter;
    }

    public static UserAdapter getmAdapterFR() {
        return mAdapterFR;
    }

    public static void setmAdapterFR(UserAdapter mAdapterFR) {
        LoaderActivity.mAdapterFR = mAdapterFR;
    }

    public static UserAdapter getmAdapterSP() {
        return mAdapterSP;
    }

    public static void setmAdapterSP(UserAdapter mAdapterSP) {
        LoaderActivity.mAdapterSP = mAdapterSP;
    }

    public static UserAdapter getmAdapterGR() {
        return mAdapterGR;
    }

    public static void setmAdapterGR(UserAdapter mAdapterGR) {
        LoaderActivity.mAdapterGR = mAdapterGR;
    }

    public static CoursAdapter getM2Adapter() {
        return m2Adapter;
    }

    public static void setM2Adapter(CoursAdapter m2Adapter) {
        LoaderActivity.m2Adapter = m2Adapter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        dotsLoaderViewt = findViewById(R.id.dotsLoaderView);
         status = "starting";
        //declaring the lists of objects
        dotsLoaderViewt.show();
        dotsLoaderViewt.hide();
        UseritemsList = new ArrayList<>();
        UseritemsListFr = new ArrayList<>();
        UseritemsListSp = new ArrayList<>();
        UseritemsListGr = new ArrayList<>();
        courList = new ArrayList<>();


        try {
            CallThemAll() ;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


// TODO : services

    public  void waitingThread(){

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited <6000) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(LoaderActivity.this,
                            MainActivity.class);//LoginActivity.class CoursEnglishActivity
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    LoaderActivity.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    LoaderActivity.this.finish();
                }

            }
        };
        splashTread.start();
    }

    public void CallThemAll() throws InterruptedException {
        dotsLoaderViewt.show();
        // Json response
        getListEnglish();
        getListFrench();
        getListSpanish();
        getListGerman();
        getData();
        //editing the adapter
        mAdapter = new UserAdapter(this, UseritemsList);
        mAdapterFR = new UserAdapter(this, UseritemsListFr);
        mAdapterSP = new UserAdapter(this, UseritemsListSp);
        mAdapterGR = new UserAdapter(this, UseritemsListGr);
        m2Adapter = new CoursAdapter(this, courList);
        if (status=="failed"){
            // Json response
            getListEnglish();
            getListFrench();
            getListSpanish();
            getListGerman();
            getData();
            //editing the adapter
            mAdapter = new UserAdapter(this, UseritemsList);
            mAdapterFR = new UserAdapter(this, UseritemsListFr);
            mAdapterSP = new UserAdapter(this, UseritemsListSp);
            mAdapterGR = new UserAdapter(this, UseritemsListGr);
            m2Adapter = new CoursAdapter(this, courList);
        }else{
            dotsLoaderViewt.hide();
            waitingThread();
            }

    }

    //***********get leaderboard list of english language***********
    public void getListEnglish() {



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
                mAdapter.leadersList = UseritemsList;
                //mAdapter = new UserAdapter(getActivity(), itemsList);
               mAdapter.notifyDataSetChanged();
              //change status
                status = "englishOk";
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                status = "failed";
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);


    }

    //***********get leaderboard list of french language***********
    public void getListFrench() {


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
                status = "frenchOk";
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                status = "failed";
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);


    }

    //***********get leaderboard list of spanish language***********
    public void getListSpanish() {

        dotsLoaderViewt.show();
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
                status = "spanishOk";
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                status = "failed";
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);


    }

    //***********get leaderboard list of german language***********
    public void getListGerman() {
//TODO: fill in the list of 5 best user in german

        dotsLoaderViewt.show();
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
                status = "germanOk";
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                status = "failed";
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);


    }


    public void getData() {

        dotsLoaderViewt.show();


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLi, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("index>>>>>>", jsonArray.toString());


                try {


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jObj = jsonArray.getJSONObject(i);

                        Cour c = new Cour(
                                String.valueOf(jObj.getInt("id")),
                                jObj.getString("idLevel"),
                                jObj.getString("grammaire"),
                                jObj.getString("conjugaison"),
                                jObj.getString("orthographe"),
                                String.valueOf(jObj.getInt("langue")));


                        Log.d("affichageCours", c.toString());

                        courList.add(c);
                        for (int j = 0; j < courList.size(); j++) {
                            if (courList.get(j).getLangue().equals("1"))
                                System.out.println("fr");
                            if (courList.get(j).getLangue().equals("2"))
                                System.out.println("en");
                            if (courList.get(j).getLangue().equals("3"))
                                System.out.println("ger");
                            if (courList.get(j).getLangue().equals("4"))
                                System.out.println("sp");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                m2Adapter.itemcourList = courList;
                m2Adapter.notifyDataSetChanged();
                status = "lastOk";
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                status = "failed";
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
