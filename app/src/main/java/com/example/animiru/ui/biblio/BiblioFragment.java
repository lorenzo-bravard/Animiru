package com.example.animiru.ui.biblio;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.animiru.MainActivity;
import com.example.animiru.data.AnimeData.Data.Images;

import com.example.animiru.stockage.AnimeLibraryItem;
import com.example.animiru.stockage.AnimePreferencesManager;
import com.example.animiru.ui.JikanApi;
import com.example.animiru.ui.RetrofitClient;
import com.example.animiru.data.AnimeData;
import com.example.animiru.databinding.FragmentBibliothequeBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BiblioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BiblioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentBibliothequeBinding binding;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AnimePreferencesManager preferencesManager;

    public BiblioFragment() {
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
    public static BiblioFragment newInstance(String param1, String param2) {
        BiblioFragment fragment = new BiblioFragment();
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
        binding = FragmentBibliothequeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.anime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenez le contexte de l'activité actuelle
                MainActivity mainActivity = (MainActivity) v.getContext();

                // Appel de la méthode pour changer de fragment
                mainActivity.pageAnime();

            }
        });

        JikanApi apiService = RetrofitClient.getClient("https://api.jikan.moe/v4/").create(JikanApi.class);


        // Récupérez la liste mise à jour des animeIds à chaque appel

        List<Integer> animeIds = anime();
        int sizelist = animeIds.size() - 1;
        int element = animeIds.get(sizelist);
        Log.d("MainActivity", "element : " + element);

        Call<AnimeData> call = apiService.getAnimeDetails(element);

        call.enqueue(new Callback<AnimeData>() {

            @Override
            public void onResponse(Call<AnimeData> call, Response<AnimeData> response) {
                Log.d("SearchResults", "responsenumber: " + response);
                if (response.isSuccessful()) {
                    AnimeData AnimeData = response.body();
                    Object Episodes = AnimeData.getData().getEpisodes();
                    String title = AnimeData.getData().getTitle();
                    binding.titre.setText(title);
                    binding.nbep.setText(String.valueOf(Episodes) + " épisodes");
                    Images.Jpg jpg = AnimeData.getData().getImages().getJpg();
                    if (jpg != null) {
                        String url = jpg.getLarge_image_url();
                        Picasso.get().load(url).into(binding.banniere);
                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<AnimeData> call, Throwable t) {

            }
        });

    }

    public List<Integer> anime() {
        List<Integer> animeIds = new ArrayList<>();

        preferencesManager = new AnimePreferencesManager(getContext());
        List<AnimeLibraryItem> animeLibrary = preferencesManager.getAnimeLibrary();

        if (animeLibrary != null && !animeLibrary.isEmpty()) {
            Log.d("Biblio", "Size of animeLibrary: " + animeLibrary.size());
            for (AnimeLibraryItem item : animeLibrary) {
                // Faites quelque chose avec chaque objet AnimeLibraryItem
                int animeId = item.getAnimeId();
                int lastWatchedEpisode = item.getLastWatchedEpisode();

                // Par exemple, affichez les valeurs dans la console
                Log.d("test", "AnimeId: " + animeId + ", LastWatchedEpisode: " + lastWatchedEpisode);

                // Ajoutez l'animeId à la liste
                animeIds.add(animeId);
            }
        }

        return animeIds;
    }
}