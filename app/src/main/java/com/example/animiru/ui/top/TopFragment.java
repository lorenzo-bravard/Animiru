package com.example.animiru.ui.top;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.animiru.data.AnimeData;
import com.example.animiru.databinding.FragmentAnimeBinding;
import com.example.animiru.databinding.FragmentTopBinding;
import com.example.animiru.ui.JikanApi;
import com.example.animiru.ui.RetrofitClient;
import com.example.animiru.ui.anime.AnimeFragment;
import com.squareup.picasso.Picasso;


import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ANIME_ID = "anime_id";

    private FragmentTopBinding binding;

    private int anime_id;


    // TODO: Rename and change types of parameters


    public TopFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TopFragment newInstance(int animeid) {
        TopFragment fragment = new TopFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ANIME_ID, animeid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Récupérez la valeur de anime_id à partir des arguments du fragment
        if (getArguments() != null) {
            anime_id = getArguments().getInt(ARG_ANIME_ID, 0);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTopBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Vérifiez si binding est null avant d'accéder à ses propriétés
        if (binding != null) {
            binding.menuEp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Obtenez le contexte de l'activité actuelle
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

// Utilisez la méthode popBackStack pour revenir à l'ancien fragment
                    fragmentManager.popBackStack();
                }
            });
        } else {
            // Log si binding est null (peut aider à identifier le problème)
            Log.e("AnimeFragment", "binding is null");
        }
        JikanApi apiService = RetrofitClient.getClient("https://api.jikan.moe/v4/").create(JikanApi.class);

        Call<AnimeData> call = apiService.getAnimeDetails(anime_id);
        Log.e("AnimeFragment", "test"+ anime_id);

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

                    binding.totalStud.setText(stringBuilder.toString());
                    StringBuilder stringBuilder2 = new StringBuilder();

                    for (AnimeData.Data.Genres genre : Genres) {
                        stringBuilder.append(genre.getName()).append("\n");
                    }

                    binding.totalGen.setText(stringBuilder2.toString());
                    binding.title.setText(title);
                    binding.totalEp.setText(String.valueOf(episodes) + " épisodes");
                    binding.totalSyn.setText(syn);

                    AnimeData.Data.Images.Jpg jpg = animeData.getData().getImages().getJpg();
                    if (jpg != null) {
                        String url = jpg.getLarge_image_url();
                        Picasso.get().load(url).into(binding.manga);
                    } else {

                    }
                }
            }
            @Override
            public void onFailure(Call<AnimeData> call, Throwable t) {

            }
        });
    }
}