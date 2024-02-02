package com.example.animiru.ui.ajout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.animiru.MainActivity;
import com.example.animiru.R;
import com.example.animiru.databinding.FragmentAjoutBinding;
import com.example.animiru.stockage.AnimeLibraryItem;
import com.example.animiru.stockage.AnimePreferencesManager;

import java.util.List;

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

    private void addAnimeToLibrary(int animeId, int lastWatchedEpisode) {
        // Récupérer la liste actuelle des animes dans la bibliothèque
        List<AnimeLibraryItem> animeLibrary = preferencesManager.getAnimeLibrary();
        Log.d("AnimeLibrary", "Nombre d'animes avant l'ajout : " + animeLibrary.size());

        // Ajouter le nouvel anime à la liste
        AnimeLibraryItem newAnime = new AnimeLibraryItem(animeId, lastWatchedEpisode);
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
        binding.selecAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Exemple d'utilisation : Ajouter un anime à la bibliothèque
                addAnimeToLibrary(367, 1);
            }
        });
    }
}