package com.example.nyannyanquiz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nyannyanquiz.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList user_id, user_username, user_password, user_score;

    CustomAdapter(Activity activity, Context context, ArrayList user_id, ArrayList user_username, ArrayList user_password,
                  ArrayList user_score){
        this.activity = activity;
        this.context = context;
        this.user_id = user_id;
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_score = user_score;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.user_id_txt.setText(String.valueOf(position + 1));
        holder.user_username_txt.setText(String.valueOf(user_username.get(position)));
        holder.user_score_txt.setText(String.valueOf(user_score.get(position)));
        //Recyclerview onClickListener



    }

    @Override
    public int getItemCount() {
        return user_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView user_id_txt, user_username_txt, user_password_txt, user_score_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user_id_txt = itemView.findViewById(R.id.user_ranking_txt);
            user_username_txt = itemView.findViewById(R.id.user_username_txt);
            user_score_txt = itemView.findViewById(R.id.user_score_txt);

        }

    }

}
