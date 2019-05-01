package com.sim.mundylingo.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import com.sim.mundylingo.AboutUs.AboutUsActivity;
import com.sim.mundylingo.Helpers.DatabaseHelper;
import com.sim.mundylingo.Helpers.InternetConnectivityObserver;
import com.sim.mundylingo.Models.User;
import com.sim.mundylingo.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/*

made By Khaled and Raoudha

 */

public class LoginActivity extends AppCompatActivity {

    //layout
    ConstraintLayout login_actv;
    //***************************
    //UI
    private static final String EMAIL = "email";

    private Button about;

    private ImageView logoImgV;


    public static String IPadress = "http://41.226.11.252:1180/lingued";


    //public String url = IPadress + "/miniProjetWebService/selectAjouUser.php?email=";
    public String url2 = IPadress + "/miniProjetWebService/selectUser.php?email=";
    //public String url3 = IPadress + "/miniprojetwebservice/update_userImg.php?email=";
    public static User currentUser;
    public DatabaseHelper Mydb;

    Thread loginThread;

    static String LinkUri = "";
    static String image = "";

    //mediasounds

    RelativeLayout btn_login;
    TextView btn_register;
    EditText edit_password, edit_username ;


    // Instance of Facebook Class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isLoggedIn = false;
        Mydb = new DatabaseHelper(this);
        //  sharedPreferences = getPreferences(MODE _PRIVATE);
        //   get access token from facebook

        //  Log.e("isLoggeIn", String.valueOf(isLoggedIn));


        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.login_action);
        btn_register = findViewById(R.id.register);
        edit_password = findViewById(R.id.edit_password);
        edit_username = findViewById(R.id.edit_username);

        //controle de saisie et clear edit text :


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: check for mdp and username  , then if true bring the data from database of the user  if it's first time
                //TODO :save the username for our user / login tool



                int errorColor;
                final int version = Build.VERSION.SDK_INT;

                //Get the defined errorColor from color resource.

                errorColor = ContextCompat.getColor(getApplicationContext(), R.color.dark_red);


                String errorString1 = "This field cannot be empty";  // Your custom error message.
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString1);
                spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString1.length(), 0);

                String errorString2 = "user don't exist";  // Your custom error message.
                ForegroundColorSpan foregroundColorSpan1 = new ForegroundColorSpan(errorColor);
                SpannableStringBuilder spannableStringBuilder1 = new SpannableStringBuilder(errorString2);
                spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString2.length(), 0);
                String password = edit_password.getText().toString();
                String mail = edit_username.getText().toString();
                if (password.length() < 4) {
                    edit_password.setError("password length must be greater than 4 lettres");

                }
        if (!isValidEmail(mail)){
            edit_username.setError("not valid email");
        }
                if (edit_username.getText().toString().isEmpty() || edit_password.getText().toString().isEmpty()) {
                    edit_username.setError(spannableStringBuilder);
                    edit_password.setError(spannableStringBuilder);
                }else{
                    getUser(new User(edit_username.getText().toString() , edit_password.getText().toString()));
                    InternetConnectivityObserver.get().setConsumer(new InternetConnectivityObserver.Consumer() {
                        @Override
                        public void accept(boolean internet) {
                            if (internet) {
                                goToHomeOnline();//connected
                            } else {
                                Snackbar snackbar = Snackbar
                                        .make(v, "Try again!", Snackbar.LENGTH_LONG)
                                        .setAction("RETRY", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                            }
                                        });
                                snackbar.setActionTextColor(Color.RED);
                                View sbView = snackbar.getView();
                                TextView textView =  sbView.findViewById(android.support.design.R.id.snackbar_text);
                                textView.setTextColor(Color.YELLOW);
                                snackbar.show();
                                goToHomeOffline();
                            }
                        }

                    });
                    InternetConnectivityObserver.get().start();
                    Intent i = new Intent(LoginActivity.this , LoaderActivity.class);
                    startActivity(i);
                }



            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : redirect to register new user
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });


    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
//TODO : redirect
    private void goToHomeOnline() {

        try {
            //todo: update user image and image
            //todo: update the currentUser variable
            //todo: update the user in the sqlite



            Intent intent = new Intent(this, LoaderActivity.class); // send me to the main activity activity
            startActivity(intent);

            LoginActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("login without user info ");

        }


    }


    private void goToHomeOffline() {
        Intent intent = new Intent(LoginActivity.this, OfflineActivity.class);
        startActivity(intent);
        LoginActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }









    public void getUser(final User user) {
        url2 += user.getEmail()+"&password="+user.getPassword();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url2, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                try {


                    JSONObject jObj = jsonArray.getJSONObject(0);
                    if(jObj.equals("user doest exists[]")){
                        Toast.makeText(LoginActivity.this, "User don't exist Please createan account", Toast.LENGTH_SHORT).show();

                    }else{
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
                        System.out.println("getting levels and scores :  " + currentUser.getLevelEng());
                        System.out.println();
                        System.out.println();

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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);


    }




/*

        //initiate databse interne


//todo: when logged into the app after initially loggin in with fb  or gmail
        if (isLoggedIn) { //if user connected with facebook
            //set the curent user in database



        }

//todo: callback related to the fb button and in case of success of this action
//callback register facebook
        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));//  a chercher


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                try {


                    retrieveFbUserProfile(AccessToken.getCurrentAccessToken());

                    Intent intent = new Intent(LoginActivity.this, LoaderActivity.class); // send me to the main activity activity
                    startActivity(intent);
                    System.out.println(loginResult.getAccessToken());
                    System.out.println(loginResult.getRecentlyGrantedPermissions());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("try&catch user", "in login success ");
                }

            }

            @Override
            public void onCancel() {
                Log.d("canceled fb", "login canceled");
                ReloadLogin();

            }

            @Override
            public void onError(FacebookException exception) {
                exception.getCause().toString();

                ReloadLogin();


            }
        });
*/





/*
    private void ReloadLogin() {
        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
        startActivity(intent);
        LoginActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    //getting fb information
    //todo: retrieve facebook user information email , emage , name ...

    private void retrieveFbUserProfile(final AccessToken accessToken) {

        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if (object != null) {
                            Log.d("logging", "onCompleted: " + object.toString());
                            User user = new User();
                            try {

                                user.setUsername(object.getString("name"));
                                user.setEmail(object.getString("email"));

                                user.setImgUrl("http://41.226.11.252:1180/lingued/miniProjetRessources/userphoto.png");
                                Log.d("logging", "onCompleted: user: " + user.toString());


                                LinkUri = user.getEmail();
                                image = user.getImgUrl();
                                url += LinkUri;
                                url += "&image=" + image;
                                loginApp(LinkUri, image);
                                user.setImgUrl("http://41.226.11.252:1180/lingued/miniProjetRessources/userphoto.png");
                                checkUser(user);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                // hideProgressBar();
                                //showErrorAlert();
                            }
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    //todo: methode that gets user information from my externel database
    public void checkUser(final User user) {
        url2 += user.getEmail();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url2, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                try {


                    JSONObject jObj = jsonArray.getJSONObject(0);

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
                    System.out.println("getting levels and scores :  " + currentUser);
                    System.out.println();
                    System.out.println();
                    Mydb.DeleteAllUsers();
                    Mydb.InsertUserData(currentUser);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);


    }




    //todo: this is the offline button


//register a new account in the application
//todo: check user if exist or insert his information if he don't

    public void loginApp(final String strig, final String image) {

        System.out.println();
        System.out.println("this is url login");

        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("post response ", response);
                System.out.println();
                System.out.println();

                Log.e("post : response object ", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley insert  ", error.getCause().toString());
                Toast.makeText(LoginActivity.this, "error someweher in the request", Toast.LENGTH_LONG).show();

            }
        }) {


            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("email", strig);
                params2.put("image", image);

                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sr);
    }

    //todo: useful methode
    public int randomId(int min, int max) {

        Random r = new Random();
        int i = r.nextInt(max - min + 1) + min;
        return i;
    }

    //todo: func update data
    private void UpdateFbUserProfile(final AccessToken accessToken) {

        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if (object != null) {
                            Log.d("logging", "onCompleted: " + object.toString());
                            User user = new User();
                            try {

                                user.setUsername(object.getString("name"));
                                user.setEmail(object.getString("email"));

                                user.setImgUrl("http://41.226.11.252:1180/lingued/miniProjetRessources/userphoto.png");
                                Log.d("logging", "onCompleted: user: " + user.toString());


                                LinkUri = user.getEmail();
                                image = user.getImgUrl();
                                url3 += LinkUri;
                                url3 += "&image=" + image;
                                System.out.println("url of update " + url3);
                                System.out.println("email inside oncreate " + LinkUri);
                                System.out.println();
                                System.out.println();
                                System.out.println("image inside oncreate" + image);
                                System.out.println();
                                UpdatePhpJsonUser(LinkUri, image);
                                user.setImgUrl("http://41.226.11.252:1180/lingued/miniProjetRessources/userphoto.png");
                                System.out.println("modifiying profile image");
                                getUser(user);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                // hideProgressBar();
                                //showErrorAlert();
                            }
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }


    public void UpdatePhpJsonUser(final String email, final String image) {

        System.out.println();
        System.out.println("this is url login");

        StringRequest sr = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("post response ", response);
                System.out.println();
                System.out.println();

                Log.e("post : response object ", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley insert  ", error.getCause().toString());
                Toast.makeText(LoginActivity.this, "error someweher in the request", Toast.LENGTH_LONG).show();

            }
        }) {


            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("email", email);
                params2.put("image", image);

                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sr);
    }

*/
}
