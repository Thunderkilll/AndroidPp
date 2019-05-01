package com.sim.mundylingo.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.sim.mundylingo.Questions.Question1Activity;
import com.sim.mundylingo.R;

import java.util.HashMap;
import java.util.Map;

import static com.sim.mundylingo.Activities.LoginActivity.IPadress;
import static com.sim.mundylingo.Activities.LoginActivity.currentUser;


public class Main2Activity extends AppCompatActivity {

    //declaration des variables
    public String urlquetion = IPadress+"/miniprojetwebservice/selectQuestion.php?level=1&langue=";
    public String urlProposition =IPadress+"/miniprojetwebservice/selectProposition.php?level=1&question=";
    public String urlReponse =IPadress+"/miniprojetwebservice/selectReponsedeQuestion.php?level=1&question=";
    public String urlPosteAng = IPadress + "/miniProjetWebService/updateUserAng.php?email=";
    private int BonnusReponse;
    private int MyScore;
    private String langue;
    private String level;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }

    public void updateUser() {
        MyScore = Integer.parseInt(score);
        MyScore += BonnusReponse;
        score = String.valueOf(MyScore);
        System.out.println(" mail " + currentUser.getEmail());
        urlPosteAng += currentUser.getEmail() + "&levelAng="+level+"&scoreAng="+score;
       if (langue.equals("2")) {
            // Toast.makeText(Question2Activity.this,"Anglais",Toast.LENGTH_LONG).show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPosteAng, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(Main2Activity.this, response, Toast.LENGTH_LONG).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Main2Activity.this, "erreur" + error, Toast.LENGTH_LONG).show();

                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    String mail = currentUser.getEmail();
                    String score = String.valueOf(MyScore);
                    String levelF = level;

                    params.put("scoreAng", score);
                    params.put("levelAng", levelF);
                    params.put("email", mail);
                    return params;


                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);


        }

    }




}


