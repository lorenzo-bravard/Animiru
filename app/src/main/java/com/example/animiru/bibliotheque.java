package com.example.animiru;

import com.example.animiru.data.JikanAPI;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.animiru.databinding.FragmentBibliothequeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link bibliotheque#newInstance} factory method to
 * create an instance of this fragment.
 */
public class bibliotheque extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentBibliothequeBinding binding;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public bibliotheque() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment bibliotheque.
     */
    // TODO: Rename and change types and number of parameters
    public static bibliotheque newInstance(String param1, String param2) {
        bibliotheque fragment = new bibliotheque();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBibliothequeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        JikanAPI apiService = RetrofitClient.getClient("https://api.jikan.moe/v4/").create(JikanAPI.class);

        Call<Anime> call = apiService.getAnimeDetails(1); // Remplacez 1 par l'ID de l'anime que vous souhaitez obtenir

        call.enqueue(new Callback<Anime>() {
            @Override
            public void onResponse(Call<Anime> call, Response<Anime> response) {
                if (response.isSuccessful()) {
                    Anime anime = response.body();
                    // Faites quelque chose avec les données de l'anime
                } else {
                    // Gérez les erreurs ici
                }
            }

            @Override
            public void onFailure(Call<Anime> call, Throwable t) {
                // Gérez les erreurs ici
            }
        });

        binding.anime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                animePage animePage = new animePage();
                fragmentTransaction.replace(R.id.fragment_container_view, animePage);
                fragmentTransaction.commit();
            }
        });





    }
}