package com.example.nyannyanquiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class GenreAdapter extends BaseAdapter {

    private List<GenreModel> genreList;

    public GenreAdapter(List<GenreModel> genreList) {
        this.genreList = genreList;
    }

    @Override
    public int getCount() {
        return genreList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View myView;

        if (convertView == null) {
            myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gen_item_layout, parent, false);
        } else {
            myView = convertView;
        }

        TextView genName = myView.findViewById(R.id.genName);

        genName.setText(genreList.get(position).getName());

        return myView;
    }
}
