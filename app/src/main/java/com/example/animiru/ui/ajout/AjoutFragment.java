package com.example.animiru.ui.ajout;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentAjoutBinding binding;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAjoutBinding.inflate(inflater, container, false);
        return binding.getRoot();    }

    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        JikanApi apiService = RetrofitClient.getClient("https://api.jikan.moe/v4/").create(JikanApi.class);
        int animeid = 346;
        binding.selecAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<AnimeData> call = apiService.getAnimeDetails(animeid);
                call.enqueue(new Callback<AnimeData>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<AnimeData> call, Response<AnimeData> response) {
                        if (response.isSuccessful()) {
                            AnimeData animeData = response.body();
                            Object episodes = animeData.getData().getEpisodes();
                            String title = animeData.getData().getTitle();
                            String syn = animeData.getData().getSynopsis();
                            List<AnimeData.Data.Genres> studios = animeData.getData().getStudios();
                            List<AnimeData.Data.Genres> Genres = animeData.getData().getGenres();

                            StringBuilder stringBuilder = new StringBuilder();

                            for (AnimeData.Data.Genres studio : studios) {
                                stringBuilder.append(studio.getName()).append("\n");
                            }

                            StringBuilder stringBuilder2 = new StringBuilder();

                            for (AnimeData.Data.Genres genre : Genres) {
                                stringBuilder2.append(genre.getName()).append("\n");
                            }
                            AnimeData.Data.Images.Jpg jpg = animeData.getData().getImages().getJpg();
                            String url = jpg.getLarge_image_url();

                            // Exemple d'utilisation : Ajouter un anime à la bibliothèque
                            addAnimeToLibrary(animeid, 0, syn, String.valueOf(episodes),stringBuilder.toString(), stringBuilder2.toString(),url,title);
                        }
                    }
                    @Override
                    public void onFailure(Call<AnimeData> call, Throwable t) {

                    }
                });
            }
        });
    }
}