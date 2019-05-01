package com.sim.mundylingo.Questions;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.sim.mundylingo.Activities.LevelCompleActivity;
import com.sim.mundylingo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.sim.mundylingo.Activities.LoginActivity.IPadress;
import static com.sim.mundylingo.Activities.LoginActivity.currentUser;


public class Question1Activity extends AppCompatActivity {
    public String urlquetion = IPadress + "/miniProjetWebService/selectQuestion.php?level=";
    public String urlProposition = IPadress + "/miniProjetWebService/selectProposition.php?level=";
    public String urlReponse = IPadress + "/miniProjetWebService/selectReponsedeQuestion.php?level=";

    String urlPosteFR = IPadress + "/miniProjetWebService/updateUser.php?";
    String urlPosteAng = IPadress + "/miniProjetWebService/updateUserAng.php?";
    String urlPosteGerm = IPadress + "/miniProjetWebService/updateUserGerm.php?";
    String urlPosteEsp = IPadress + "/miniProjetWebService/updateUserEsp.php?";
    private TextView monQuestion;
    private TextView propo1;
    private TextView propo2;
    private TextView propo3;
    private TextView propo4;
    private Switch SW1;
    private Switch SW2;
    private Switch SW3;
    private Switch SW4;
    private String idQ = "";
    private String reponseVrai;
    private int BonnusReponse = 0;
    private int MyScore = 0;
    private String langue;
    private String level;
    private int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);
        monQuestion = findViewById(R.id.questionTextView);
        propo1 = findViewById(R.id.Prop1TextView);
        propo2 = findViewById(R.id.prop2TextView);
        propo3 = findViewById(R.id.prop3TextView);
        propo4 = findViewById(R.id.prop4TextView);
        SW1 = findViewById(R.id.switch1);
        SW2 = findViewById(R.id.switch2);
        SW3 = findViewById(R.id.switch3);
        SW4 = findViewById(R.id.switch4);
        langue = getIntent().getStringExtra("langue");
        level = getIntent().getStringExtra("level");
        score = getIntent().getIntExtra("score", 0);
        SW1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SW2.setChecked(false);
                    SW3.setChecked(false);
                    SW4.setChecked(false);
                }
            }
        });
        SW2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SW1.setChecked(false);
                    SW3.setChecked(false);
                    SW4.setChecked(false);
                }
            }
        });
        SW3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SW1.setChecked(false);
                    SW2.setChecked(false);
                    SW4.setChecked(false);
                }
            }
        });
        SW4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SW1.setChecked(false);
                    SW3.setChecked(false);
                    SW2.setChecked(false);
                }
            }
        });


        urlquetion += level;
        urlquetion += "&langue=";
        urlquetion += langue;
        urlquetion += "&type=1";
        GetQuestion(urlquetion);


    }

    public void GetQuestion(final String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("response from Json", response.toString());
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        //Toast.makeText(Main2Activity.this,jsonObject.getString("contenu"),Toast.LENGTH_LONG).show();
                        monQuestion.setText(jsonObject.getString("contenu"));
                        idQ = jsonObject.getString("id");
                        urlProposition += level;
                        urlProposition += "&question=";
                        urlProposition += idQ;
                        urlProposition += "&langue=";
                        urlProposition += langue;
//
                        urlReponse += level;
                        urlReponse += "&question=";
                        urlReponse += idQ;
                        urlReponse += "&idLangue=";
                        urlReponse += langue;
                        GetPropositions(urlProposition);
                        GetReponseDeQuestion(urlReponse);

                    }
                } catch (JSONException e) {
                    Log.e("json exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley error", error.getMessage());
            }
        });
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(jsonArrayRequest);

    }


    public void GetPropositions(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("response from Json 2", response.toString());
                try {
                    JSONObject jsonObject1 = response.getJSONObject(0);
                    //propo1.setText(jsonObject1.getString("contenu"));
                    SW1.setText(jsonObject1.getString("contenu"));
                    JSONObject jsonObject2 = response.getJSONObject(1);
                    //propo2.setText(jsonObject2.getString("contenu"));
                    SW2.setText(jsonObject2.getString("contenu"));
                    JSONObject jsonObject3 = response.getJSONObject(2);
                    //propo3.setText(jsonObject3.getString("contenu"));
                    SW3.setText(jsonObject3.getString("contenu"));

                    JSONObject jsonObject4 = response.getJSONObject(3);
                    // propo4.setText(jsonObject4.getString("contenu"));
                    SW4.setText(jsonObject4.getString("contenu"));
                } catch (JSONException e) {
                    Log.e("json exception 2", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley error 2", error.getMessage());


            }
        });
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(jsonArrayRequest);

    }

    public void GetReponseDeQuestion(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("response from Json3", response.toString());
                try {

                    JSONObject jsonObject = response.getJSONObject(0);
                    reponseVrai = jsonObject.getString("text");

                    BonnusReponse = jsonObject.getInt("bonus");
                    System.out.println(BonnusReponse);
                    //Toast.makeText(Main2Activity.this,"reponse vrai="+reponseVrai+"bonnus"+BonnusReponse,Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    Log.e("json exception3", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley error3", error.getMessage());
            }
        });
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(jsonArrayRequest);
    }


    public void updateUser() {

        MyScore += BonnusReponse;
        score += MyScore;
        System.out.println("this is final score of level" + score);

        System.out.println(" mail " + currentUser.getEmail());

        if (langue.equals("2")) {
            // Toast.makeText(Question2Activity.this,"Anglais",Toast.LENGTH_LONG).show();
            if (Integer.valueOf(level) > Integer.valueOf(currentUser.getLevelEng())) {
                urlPosteAng += "email=" + currentUser.getEmail() + "&levelAng=" + level + "&scoreAng=" + score;
                System.out.println(urlPosteAng);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPosteAng, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        currentUser.setLevelEng(level);
                        currentUser.setScoreEng(String.valueOf(score));
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
            } else {
                int currentlevel = Integer.valueOf(currentUser.getLevelEng()) + 1;
                level = String.valueOf(currentlevel);
                urlPosteAng += "email=" + currentUser.getEmail() + "&levelAng=" + level + "&scoreAng=" + score;
                System.out.println(urlPosteAng);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPosteAng, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        currentUser.setLevelEng(level);
                        currentUser.setScoreEng(String.valueOf(score));
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
        if (langue.equals("1")) {
            if (Integer.valueOf(level) > Integer.valueOf(currentUser.getLevelFr())) {

            } else {
                int currentlevel = Integer.valueOf(currentUser.getLevelFr()) + 1;
                level = String.valueOf(currentlevel);
            }

            urlPosteFR += "email=" + currentUser.getEmail() + "&levelFr=" + level + "&scoreF=" + score;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPosteFR, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    currentUser.setLevelFr(level);
                    currentUser.setScoreFr(String.valueOf(score));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getCause().toString());

                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    String mail = currentUser.getEmail();
                    String score = String.valueOf(MyScore);
                    String levelF = level;

                    params.put("scoreF", score);
                    params.put("levelFr", levelF);
                    params.put("email", mail);
                    return params;


                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);


        }

        if (langue.equals("3")) {
            if (Integer.valueOf(level) > Integer.valueOf(currentUser.getLevelGer())) {

            } else {
                int currentlevel = Integer.valueOf(currentUser.getLevelGer()) + 1;
                level = String.valueOf(currentlevel);
            }

            // Toast.makeText(Question2Activity.this,"German",Toast.LENGTH_LONG).show();
            urlPosteGerm += "email=" + currentUser.getEmail() + "&levelGer=" + level + "&scoreGerm=" + score;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPosteGerm, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    currentUser.setLevelGer(level);
                    currentUser.setScoreGer(String.valueOf(score));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getCause().toString());

                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    String mail = currentUser.getEmail();
                    String score = String.valueOf(MyScore);
                    String levelF = level;

                    params.put("scoreGerm", score);
                    params.put("levelGer", levelF);
                    params.put("email", mail);
                    return params;


                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);


        }
        if (langue.equals("4")) {
            if (Integer.valueOf(level) > Integer.valueOf(currentUser.getLevelSpan())) {


                urlPosteEsp += "email=" + currentUser.getEmail() + "&levelEsp=" + level + "&scoreEsp=" + score;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPosteEsp, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        currentUser.setLevelSpan(level);
                        currentUser.setScoreSpan(String.valueOf(score));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getCause().toString());

                    }
                }

                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        String mail = currentUser.getEmail();
                        String score = String.valueOf(MyScore);
                        String levelF = level;

                        params.put("scoreEsp", score);
                        params.put("levelEsp", levelF);
                        params.put("email", mail);
                        return params;


                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            } else {
                int currentlevel = Integer.valueOf(currentUser.getLevelSpan()) + 1;
                level = String.valueOf(currentlevel);


                urlPosteEsp += "email=" + currentUser.getEmail() + "&levelEsp=" + level + "&scoreEsp=" + score;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPosteEsp, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        currentUser.setLevelSpan(level);
                        currentUser.setScoreSpan(String.valueOf(score));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getCause().toString());

                    }
                }

                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        String mail = currentUser.getEmail();
                        String score = String.valueOf(MyScore);
                        String levelF = level;

                        params.put("scoreEsp", score);
                        params.put("levelEsp", levelF);
                        params.put("email", mail);
                        return params;


                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }


        }
    }


    public void ValidateReponse(View view) {

        if (SW1.isChecked()) {
            if (SW1.getText().equals(reponseVrai)) {
                System.out.println("sw1 ");
                updateUser();
                Intent intent = new Intent(Question1Activity.this, LevelCompleActivity.class);

                startActivity(intent);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Try again !!").setCancelable(false).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                Toast.makeText(Question1Activity.this, "No Try Again !!!!", Toast.LENGTH_LONG).show();

            }

        }
        if (SW2.isChecked()) {
            if (SW2.getText().equals(reponseVrai)) {
                System.out.println("sw2 ");


                updateUser();
                Intent intent = new Intent(Question1Activity.this, LevelCompleActivity.class);

                startActivity(intent);
            } else {
                Toast.makeText(Question1Activity.this, "No Try Again !!!!", Toast.LENGTH_LONG).show();
            }

        }
        if (SW3.isChecked()) {
            if (SW3.getText().equals(reponseVrai)) {
                System.out.println("sw3");

                updateUser();
                Intent intent = new Intent(Question1Activity.this, LevelCompleActivity.class);

                startActivity(intent);
            } else {
                Toast.makeText(Question1Activity.this, "No Try Again !!!!", Toast.LENGTH_LONG).show();
            }

        }
        if (SW4.isChecked()) {
            if (SW4.getText().equals(reponseVrai)) {

                try {

                    System.out.println("sw4 ");
                    updateUser();
                    Intent intent = new Intent(Question1Activity.this, LevelCompleActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Intent intent = new Intent(Question1Activity.this, LevelCompleActivity.class);
                    startActivity(intent);
                }

            } else {
                Toast.makeText(Question1Activity.this, "No Try Again !!!!", Toast.LENGTH_LONG).show();
            }

        }
    }
}
