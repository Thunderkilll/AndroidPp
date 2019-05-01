package com.sim.mundylingo.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sim.mundylingo.Models.User;
import com.sim.mundylingo.R;
import org.json.JSONObject;

import java.util.HashMap;

import static com.sim.mundylingo.Activities.LoginActivity.IPadress;
import static com.sim.mundylingo.Activities.LoginActivity.currentUser;

public class RegisterActivity extends AppCompatActivity {

	public String url = IPadress + "/miniProjetWebService/selectAjouUser.php?email=";

    EditText username_text, password_text, confirm_password_text;
    Button loginButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginButton = findViewById(R.id.loginButton);
        username_text = findViewById(R.id.username_text);
        password_text = findViewById(R.id.password_text);  //Your EditText
        confirm_password_text = findViewById(R.id.confirm_password_text);  //Your EditText


        try {
            username_text.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    final int DRAWABLE_RIGHT = 2;


                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (username_text.getRight() - username_text.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                            username_text.setText("");

                            return true;
                        }
                    }
                    return false;
                }
            });

            password_text.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    final int DRAWABLE_RIGHT = 2;


                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (password_text.getRight() - password_text.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                            password_text.setText("");

                            return true;
                        }
                    }
                    return false;
                }
            });
            confirm_password_text.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    final int DRAWABLE_RIGHT = 2;


                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (confirm_password_text.getRight() - confirm_password_text.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                            confirm_password_text.setText("");

                            return true;
                        }
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void RegisterUser(View view) {


        int errorColor;
        final int version = Build.VERSION.SDK_INT;

        //Get the defined errorColor from color resource.

        errorColor = ContextCompat.getColor(getApplicationContext(), R.color.dark_red);


        String errorString1 = "This field cannot be empty";  // Your custom error message.
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString1);
        spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString1.length(), 0);

        String errorString2 = "No much ";  // Your custom error message.
        ForegroundColorSpan foregroundColorSpan1 = new ForegroundColorSpan(errorColor);
        SpannableStringBuilder spannableStringBuilder1 = new SpannableStringBuilder(errorString2);
        spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString2.length(), 0);
        String password = password_text.getText().toString();
        String mail = username_text.getText().toString();
        if (password.length() < 4) {
            password_text.setError("password length must be greater than 4 lettres");          username_text.setError(spannableStringBuilder);
        }else if (confirm_password_text.getText().toString().isEmpty()) {
            confirm_password_text.setError(spannableStringBuilder);
        }else if (password_text.getText().toString().isEmpty()) {

        }else if (!isValidEmail(mail)){
            username_text.setError("not valid email");
        }else if (username_text.getText().toString().isEmpty()) {

            password_text.setError(spannableStringBuilder);
        }else if (!password_text.getText().toString().equals(confirm_password_text.getText().toString())){
            password_text.setError(spannableStringBuilder1);
            confirm_password_text.setError(spannableStringBuilder1);
            password_text.setText(null);
            confirm_password_text.setText(null);
        }else{
            RegisterUser(username_text.getText().toString() ,
                    "http://41.226.11.252:1180/lingued/miniProjetRessources/userphoto.png" ,
                    password_text.getText().toString() );
        }

    }





    public void RegisterUser(final String email, final String image , final String password) {

        System.out.println();
        System.out.println("this is url login");
        url += email+"&password=" + password_text.getText().toString()+"&image=http://41.226.11.252:1180/lingued/miniProjetRessources/userphoto.png";

        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("post response ", response);
                System.out.println();
                System.out.println();
                int errorColor;
                final int version = Build.VERSION.SDK_INT;
                errorColor = ContextCompat.getColor(getApplicationContext(), R.color.dark_red);
                String errorString2 = "this email/user exist";  // Your custom error message.
                ForegroundColorSpan foregroundColorSpan1 = new ForegroundColorSpan(errorColor);
                SpannableStringBuilder spannableStringBuilder1 = new SpannableStringBuilder(errorString2);
                spannableStringBuilder1.setSpan(foregroundColorSpan1, 0, errorString2.length(), 0);

 if ( response.equals("[]")){
     Log.d("response","user exist");
     System.out.println("user exist");
     username_text.setError(spannableStringBuilder1);
     Toast.makeText(RegisterActivity.this, "user exist", Toast.LENGTH_SHORT).show();
 }else {

     System.out.println("user added");
     User user = new User(image , "0", "0", "0", "0", "1", "1", "1", "1",email ,password );//email, image,  "0", "0", "0", "0", "1", "5", "1", "1", password
     currentUser = user;
     Intent i = new Intent(RegisterActivity.this , LoaderActivity.class);
     startActivity(i);

 }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley insert  ", error.getCause().toString());
                Toast.makeText(RegisterActivity.this, "error someweher in the request", Toast.LENGTH_LONG).show();

            }
        }) {


            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params3 = new HashMap<String, String>();
                params3.put("email", email);
                params3.put("image", image);
                params3.put("password", password);

                return new JSONObject(params3).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sr);
    }



    public static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}