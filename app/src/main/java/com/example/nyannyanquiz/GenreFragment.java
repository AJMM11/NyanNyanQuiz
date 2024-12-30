package com.example.nyannyanquiz;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GenreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GenreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GenreFragment() {
        // Required empty public constructor
    }

    private GridView genreView;
    public static List<GenreModel> genreList = new ArrayList<>();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GenreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GenreFragment newInstance(String param1, String param2) {
        GenreFragment fragment = new GenreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_genre, container, false);
        genreView = view.findViewById(R.id.gen_Grid);

        loadGenres();

        GenreAdapter adapter = new GenreAdapter(genreList);
        genreView.setAdapter(adapter);

        // Agregar el listener para manejar los clics en los elementos del GridView
        genreView.setOnItemClickListener((adapterView, view1, position, id) -> {
            String selectedGenre = genreList.get(position).getName(); // Obtener el nombre del género seleccionado
            Fragment fragmentoDestino = new CategoryFragment();
            Bundle bundle = new Bundle();
            bundle.putString("genero", selectedGenre); // Cambia "clave" y "valor" según tus datos
            fragmentoDestino.setArguments(bundle);

            getFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, fragmentoDestino)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }


    private void loadGenres() {
        genreList.clear();
        genreList.add(new GenreModel("1", getString(R.string.anime)));
        genreList.add(new GenreModel("2", getString(R.string.history)));
        genreList.add(new GenreModel("3", getString(R.string.ordenadores)));
        genreList.add(new GenreModel("4", getString(R.string.generalknowledge)));
    }

    public interface FragmentCommunication {
        void goToQuiz(String data);
    }
}