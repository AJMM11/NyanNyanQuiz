package com.example.nyannyanquiz;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaderboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderboardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    MyDatabaseHelper myDB;
    RecyclerView recyclerView;
    ArrayList<String> user_id, user_username, user_password;
    ArrayList<Integer> user_score;
    CustomAdapter customAdapter;

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeaderboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaderboardFragment newInstance(String param1, String param2) {
        LeaderboardFragment fragment = new LeaderboardFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void sortDataByScore() {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < user_score.size(); i++) {
            indices.add(i);
        }

        Collections.sort(indices, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return user_score.get(i2) - user_score.get(i1); // Descending order
            }
        });

        ArrayList<String> sortedUserId = new ArrayList<>();
        ArrayList<String> sortedUserUsername = new ArrayList<>();
        ArrayList<String> sortedUserPassword = new ArrayList<>();
        ArrayList<Integer> sortedUserScore = new ArrayList<>();

        for (int index : indices) {
            sortedUserId.add(user_id.get(index));
            sortedUserUsername.add(user_username.get(index));
            sortedUserPassword.add(user_password.get(index));
            sortedUserScore.add(user_score.get(index));
        }

        user_id = sortedUserId;
        user_username = sortedUserUsername;
        user_password = sortedUserPassword;
        user_score = sortedUserScore;
    }
    private void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                user_id.add(cursor.getString(0));
                user_username.add(cursor.getString(1));
                user_password.add(cursor.getString(2));
                user_score.add(cursor.getInt(3));
            }
        }else{
            Toast.makeText(this.getContext(),"No data.",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        recyclerView = view.findViewById(R.id.leaderboardRecyclerView);
        try {

            myDB = new MyDatabaseHelper(this.getContext());

            if (myDB.isDatabaseEmpty()) {
                myDB.insertSampleData();
            }

            user_id = new ArrayList<>();
            user_username = new ArrayList<>();
            user_password = new ArrayList<>();
            user_score = new ArrayList<>();

            storeDataInArrays();
            sortDataByScore();
        }catch(Exception e){
            e.printStackTrace();
        }
        recyclerView = view.findViewById(R.id.leaderboardRecyclerView);
        customAdapter = new CustomAdapter(this.getActivity(), this.getContext(), user_id, user_username, user_password, user_score);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }
}