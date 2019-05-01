package com.sim.mundylingo.Fragment;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sim.mundylingo.Activities.LoginActivity;
import com.sim.mundylingo.Helpers.DatabaseHelper;
import com.sim.mundylingo.Models.User;
import com.sim.mundylingo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static com.sim.mundylingo.Activities.LoginActivity.IPadress;
import static com.sim.mundylingo.Activities.LoginActivity.currentUser;


public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public String url  = IPadress + "/miniProjetWebService/selectUser.php?email="+currentUser.getEmail();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView thumbnail;
    private Context context;
    private TextView username_prof, score_eng, score_fr, score_Sp, score_Ger;
    DatabaseHelper Mydb;
    Typeface OrangeJuce, AgentOrange;


    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        thumbnail = view.findViewById(R.id.img_profile);
        username_prof = view.findViewById(R.id.username_prof);
        score_eng = view.findViewById(R.id.score_eng);
        score_fr = view.findViewById(R.id.score_fr);
        score_Sp = view.findViewById(R.id.score_Sp);
        score_Ger = view.findViewById(R.id.score_Ger);
        AgentOrange = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AgentOrange.ttf");
        OrangeJuce = Typeface.createFromAsset(getActivity().getAssets(), "fonts/orange juice 2.0.ttf");

        //considering the that the user can login when he is ofline so i decided to add the user ifo in sqlite and get the stored info
        Mydb = new DatabaseHelper(view.getContext());


        User u = currentUser ;
        System.out.println("   ");
        System.out.println("   ");
        System.out.println("   ");
        System.out.println( "user in profile"+u.toString());
        System.out.println("   ");
        System.out.println("   ");
        System.out.println(currentUser.getImgUrl());

        Glide.with(view.getContext())
                .load(currentUser.getImgUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(thumbnail);

        //username_prof.setText(user.getName());
        username_prof.setText(currentUser.getEmail());
        username_prof.setTypeface(AgentOrange);
        score_eng.setText(currentUser.getScoreEng());
        score_eng.setTypeface(OrangeJuce);
        score_fr.setText(currentUser.getScoreFr());
        score_fr.setTypeface(OrangeJuce);
        score_Sp.setText(currentUser.getScoreSpan());
        score_Sp.setTypeface(OrangeJuce);
        score_Ger.setText(currentUser.getScoreGer());
        score_Ger.setTypeface(OrangeJuce);

        /*
        other methode
         */

        return view;
    }



}
