package com.example.animiru;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.animiru.databinding.FragmentAddAnimeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addAnime#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addAnime extends Fragment {

    public addAnime() {
        // Required empty public constructor
    }
    private FragmentAddAnimeBinding binding;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addAnime.
     */
    // TODO: Rename and change types and number of parameters
    public static addAnime newInstance(String param1, String param2) {
        addAnime fragment = new addAnime();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_anime, container, false);
    }
}