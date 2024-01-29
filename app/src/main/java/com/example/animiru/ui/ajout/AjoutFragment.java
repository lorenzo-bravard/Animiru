package com.example.animiru.ui.ajout;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.animiru.MainActivity;
import com.example.animiru.R;
import com.example.animiru.data.AnimeData;
import com.example.animiru.databinding.FragmentAjoutBinding;
import com.example.animiru.stockage.AnimeLibraryItem;
import com.example.animiru.stockage.AnimePreferencesManager;
import com.example.animiru.ui.JikanApi;
import com.example.animiru.ui.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.animiru.stockage.AnimeLibraryItem;
import com.example.animiru.stockage.AnimePreferencesManager;


import com.example.animiru.data.AnimeData;
import com.example.animiru.data.AnimeQuerry;
import com.example.animiru.databinding.FragmentAjoutBinding;
import com.example.animiru.ui.JikanApi;
import com.example.animiru.ui.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AjoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AjoutFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentAjoutBinding binding;
    private SearchView searchView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private AnimePreferencesManager preferencesManager;


    public AjoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AjoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AjoutFragment newInstance(String param1, String param2) {
        AjoutFragment fragment = new AjoutFragment();
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
        preferencesManager = new AnimePreferencesManager(getContext());



        // Exemple d'utilisation : Obtenir la liste des animes dans la bibliothèque
        List<AnimeLibraryItem> animeLibrary = preferencesManager.getAnimeLibrary();
        for (AnimeLibraryItem item : animeLibrary) {
            Log.d("AnimeLibrary", "Anime : " + item.getAnimeId() + ", Épisode : " + item.getLastWatchedEpisode());
        }

    }

    private void addAnimeToLibrary(int animeId, int lastWatchedEpisode, String syn, String ep, String studio, String genres, String images, String title) {
        // Récupérer la liste actuelle des animes dans la bibliothèque
        List<AnimeLibraryItem> animeLibrary = preferencesManager.getAnimeLibrary();
        Log.d("AnimeLibrary", "Nombre d'animes avant l'ajout : " + animeLibrary.size());

        // Ajouter le nouvel anime à la liste
        AnimeLibraryItem newAnime = new AnimeLibraryItem(animeId, lastWatchedEpisode, syn, ep, studio, genres, images, title);
        animeLibrary.add(newAnime);
        Log.d("AnimeLibrary", "Nouvel anime ajouté : " + newAnime.getAnimeId());

        // Sauvegarder la liste mise à jour dans les préférences
        preferencesManager.saveAnimeLibrary(animeLibrary);
        Log.d("AnimeLibrary", "Nombre d'animes après l'ajout : " + animeLibrary.size());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAjoutBinding.inflate(inflater, container, false);
        //return binding.getRoot();
        Log.d("SearchResults", "createview: ");
        // Inflate the layout for this fragment
        return binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean onQueryTextSubmit(String query) {
                Log.d("SearchResults", "Titlesubmit: ");
                // Call the API to search for anime
                JikanApi apiService = RetrofitClient.getClient("https://api.jikan.moe/v4/").create(JikanApi.class);

                Call<AnimeQuerry> call = apiService.doGetUserList(query, 1);

                call.enqueue(new Callback<AnimeQuerry>() {
                    @Override
                    public void onResponse(@NonNull Call<AnimeQuerry> call, @NonNull Response<AnimeQuerry> response) {
                        if (response.isSuccessful()) {
                            AnimeQuerry animeQuerry = response.body();


                            //String item = animeQuerry.getData();
                            List<AnimeQuerry.Data> Arrays = animeQuerry.getData();
                            Log.d("SearchResults", "Title: " + Arrays); // Afficher le titre dans la console
                            //binding.titre.setText(Arrays);
                        } else {
                            Log.d("SearchResults", "response: "); // Afficher le titre dans la console

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<AnimeQuerry> call, @NonNull Throwable t) {
                        Log.d("SearchResults", "failsubmit: ");
                    }
                });

                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                JikanApi apiService = RetrofitClient.getClient("https://api.jikan.moe/v4/").create(JikanApi.class);

                Call<AnimeQuerry> call = apiService.doGetUserList(newText,1);
                call.enqueue(new Callback<AnimeQuerry>() {
                    @Override
                    public void onResponse(@NonNull Call<AnimeQuerry> call, @NonNull Response<AnimeQuerry> response) {
                        Log.d("SearchResults", "response: " + response);
                        if (response.isSuccessful()) {
                            AnimeQuerry animeQuerry = response.body();
                            LinearLayout linearLayout = new LinearLayout(getContext());
                            linearLayout.setOrientation(LinearLayout.VERTICAL);

                            // Clear existing views in the ScrollView
                            binding.scrollQuerry.removeAllViews();

                            for (AnimeQuerry.Data animeData : animeQuerry.getData()) {
                                // Create a new RelativeLayout for each anime
                                RelativeLayout animeLayout = new RelativeLayout(getContext());
                                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                animeLayout.setLayoutParams(layoutParams);

                                // Create TextView for the anime's title
                                TextView titleTextView = new TextView(getContext());
                                titleTextView.setLayoutParams(new RelativeLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                titleTextView.setText(animeData.getTitle_english());

                                // Add titleTextView to animeLayout
                                animeLayout.addView(titleTextView);
                                ImageView imageView = new ImageView(getContext());
                                imageView.setLayoutParams(new RelativeLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                // Load the image using Picasso (adjust the URL based on your data model)
                                Picasso.get().load(animeData.getImages().getJpg().getImage_url()).into(imageView);

                                // Add imageView to animeLayout
                                animeLayout.addView(imageView);
                                // Add animeLayout to the LinearLayout
                                linearLayout.addView(animeLayout);
                            }

// Add the LinearLayout to the ScrollView
                            binding.scrollQuerry.addView(linearLayout);
                            //List<AnimeQuerry.Data> Arrays = animeQuerry.getTitle_english();
                           // Log.d("SearchResults", "Title: " + Arrays); // Afficher le titre dans la console
                            //binding.titre.setText(title);
                        } else {
                            Log.d("SearchResults", "TitleChange: "); // Afficher le titre dans la console
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<AnimeQuerry> call, @NonNull Throwable t) {
                        Log.d("SearchResults", "TitleChangeFailed: ");
                    }
                });
                return true;
            }

        });




    }
}