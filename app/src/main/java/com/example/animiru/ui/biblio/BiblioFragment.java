package com.example.animiru.ui.biblio;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.animiru.MainActivity;
import com.example.animiru.R;
import com.example.animiru.data.AnimeData.Data.Images;

import com.example.animiru.stockage.AnimeLibraryItem;
import com.example.animiru.stockage.AnimePreferencesManager;
import com.example.animiru.ui.JikanApi;
import com.example.animiru.ui.RetrofitClient;
import com.example.animiru.data.AnimeData;
import com.example.animiru.databinding.FragmentBibliothequeBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

// Récupérez la Map mise à jour des animeIds et lastEpisodes à chaque appel
        Map<String, List<Object>> animeMap = anime();
        List<Object> animeIdsObjects = animeMap.get("animeIds");
        List<Object> lastEpisodesObjects = animeMap.get("lastEpisodes");
        List<Object> synListObjects = animeMap.get("synList");
        List<Object> studioListObjects = animeMap.get("studioList");
        List<Object> genreListObjects = animeMap.get("genreList");
        List<Object> epListObjects = animeMap.get("epList");
        List<Object> imagesListObjects = animeMap.get("imagesList");
        List<Object> titleListObjects = animeMap.get("titleList");

        List<String> studioList = convertListToString(studioListObjects);
        List<String> genreList = convertListToString(genreListObjects);
        List<String> synList = convertListToString(synListObjects);
        List<String> epList = convertListToString(epListObjects);
        List<String> imagesList = convertListToString(imagesListObjects);
        List<String> titleList = convertListToString(titleListObjects);
        List<Integer> animeIds = convertListToInteger(animeIdsObjects);
        List<Integer> lastEpisodes = convertListToInteger(lastEpisodesObjects);



        LinearLayout relativeAnimes = view.findViewById(R.id.animes);


        if (!animeIds.isEmpty()) {
            for (int i = 0; i < animeIds.size(); i++) {
                int element = animeIds.get(i);
                int lastWatchedEpisode = lastEpisodes.get(i);
                String syn;
                if(!synList.isEmpty()){
                    syn = synList.get(i);
                }else{
                    syn = "Nous n'avons pas d'information.";
                }
                String ep = epList.get(i);
                String images = imagesList.get(i);
                String title;
                if(!titleList.isEmpty()){
                    title = titleList.get(i);
                }else{
                    title = "Nous n'avons pas d'information.";
                }
                String studio = studioList.get(i);
                String genres = genreList.get(i);


                RelativeLayout animeLayout = new RelativeLayout(requireContext());

                // Définir le fond à partir du drawable défini
                animeLayout.setBackgroundResource(R.drawable.rectangle);


                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        getResources().getDimensionPixelSize(R.dimen.anime_layout_height)
                );
                layoutParams.setMargins(0, 100, 0, 0);

                animeLayout.setLayoutParams(layoutParams);
                relativeAnimes.addView(animeLayout);

                animeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Obtenez le contexte de l'activité actuelle
                        MainActivity mainActivity = (MainActivity) v.getContext();



                        // Appel de la méthode pour changer de fragment
                        mainActivity.pageAnime(lastWatchedEpisode, ep, images, title, syn, studio, genres, element);

                    }
                });

                // Création de l'ImageView pour 'suppr'
                ImageView supprImageView = new ImageView(requireContext());
                supprImageView.setId(View.generateViewId());
                supprImageView.setImageResource(R.drawable.ann);
                RelativeLayout.LayoutParams supprParams = new RelativeLayout.LayoutParams(
                        getResources().getDimensionPixelSize(R.dimen.suppr_width),
                        getResources().getDimensionPixelSize(R.dimen.suppr_height)
                );
                supprParams.addRule(RelativeLayout.END_OF, R.id.banniere);
                supprParams.addRule(RelativeLayout.ALIGN_TOP, R.id.banniere);
                supprParams.setMarginStart(getResources().getDimensionPixelSize(R.dimen.suppr_margin_start));
                supprImageView.setLayoutParams(supprParams);
                animeLayout.addView(supprImageView);

                supprImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeAnimeFromLibrary(element);
                        MainActivity mainActivity = (MainActivity) v.getContext();



                        // Appel de la méthode pour changer de fragment
                        mainActivity.pageAcceuil(false);
                    }
                });

// Création de l'ImageView pour 'banniere'
                ImageView banniereImageView = new ImageView(requireContext());
                banniereImageView.setId(View.generateViewId());
                RelativeLayout.LayoutParams banniereParams = new RelativeLayout.LayoutParams(
                        getResources().getDimensionPixelSize(R.dimen.banniere_width),
                        ViewGroup.LayoutParams.MATCH_PARENT
                );
                banniereParams.addRule(RelativeLayout.ALIGN_PARENT_START);
                banniereParams.addRule(RelativeLayout.CENTER_VERTICAL);
                banniereParams.setMarginStart(getResources().getDimensionPixelSize(R.dimen.banniere_margin_start));
                banniereImageView.setLayoutParams(banniereParams);
                animeLayout.addView(banniereImageView);

                TextView titleTextView = new TextView(requireContext());
                titleTextView.setId(View.generateViewId());
                titleTextView.setText(title);
                titleTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                titleParams.addRule(RelativeLayout.END_OF, banniereImageView.getId());
                titleParams.addRule(RelativeLayout.ALIGN_TOP, banniereImageView.getId());
                titleParams.setMarginStart(getResources().getDimensionPixelSize(R.dimen.title_margin_start));
                titleParams.setMargins(0, 20, 0, 0);

                titleTextView.setLayoutParams(titleParams);
                animeLayout.addView(titleTextView);

                // Création du TextView pour le nombre d'épisodes
                TextView episodesTextView = new TextView(requireContext());
                episodesTextView.setId(View.generateViewId());
                if(!Objects.equals(ep, "null")){
                    episodesTextView.setText(ep + " épisodes");
                }else{
                    episodesTextView.setText("Nous n'avons pas d'information pour l'instant.");
                }
                episodesTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
                episodesTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.gris));
                RelativeLayout.LayoutParams episodesParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                episodesParams.addRule(RelativeLayout.END_OF, banniereImageView.getId());
                episodesParams.addRule(RelativeLayout.BELOW, titleTextView.getId());
                episodesParams.setMarginStart(getResources().getDimensionPixelSize(R.dimen.episodes_margin_start));
                episodesTextView.setLayoutParams(episodesParams);
                animeLayout.addView(episodesTextView);

                // Création du TextView pour le synopsis
                TextView synopsisTextView = new TextView(requireContext());
                synopsisTextView.setId(View.generateViewId());

                synopsisTextView.setText(syn);
                // Limiter le texte à 3 lignes et ajouter des points de suspension (...) en cas de troncature
                synopsisTextView.setMaxLines(5);
                synopsisTextView.setEllipsize(TextUtils.TruncateAt.END);

                synopsisTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
                synopsisTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.gris));
                RelativeLayout.LayoutParams synopsisParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                synopsisParams.addRule(RelativeLayout.END_OF, banniereImageView.getId());
                synopsisParams.addRule(RelativeLayout.BELOW, episodesTextView.getId());
                synopsisParams.setMarginStart(getResources().getDimensionPixelSize(R.dimen.synopsis_margin_start));
                synopsisParams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.synopsis_margin_top), 0, 0);
                synopsisTextView.setLayoutParams(synopsisParams);
                animeLayout.addView(synopsisTextView);

// Création du TextView pour le nombre d'épisodes
                TextView epTextView = new TextView(requireContext());
                epTextView.setId(View.generateViewId());
                epTextView.setText("Le dernier épisode que vous avez vue : " + String.valueOf(lastWatchedEpisode));
                ;
                epTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
                epTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.gris));
                RelativeLayout.LayoutParams epParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                epParams.addRule(RelativeLayout.END_OF, banniereImageView.getId());
                epParams.addRule(RelativeLayout.BELOW, synopsisTextView.getId());
                epParams.setMarginStart(getResources().getDimensionPixelSize(R.dimen.ep_margin_start));
                epParams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.ep_margin_top), 0, 0);
                epTextView.setLayoutParams(epParams);
                animeLayout.addView(epTextView);

                // Création du TextView pour le "prog"
                TextView progTextView = new TextView(requireContext());
                progTextView.setId(View.generateViewId());
                progTextView.setText("Progression : ");
                progTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
                progTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.gris));

                RelativeLayout.LayoutParams progParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                progParams.addRule(RelativeLayout.END_OF, banniereImageView.getId());
                progParams.addRule(RelativeLayout.BELOW, epTextView.getId());
                progParams.setMarginStart(getResources().getDimensionPixelSize(R.dimen.prog_margin_start));
                progParams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.prog_margin_top), 0, 0);
                progTextView.setLayoutParams(progParams);
                animeLayout.addView(progTextView);

                ProgressBar progressBar = new ProgressBar(requireContext(), null, android.R.attr.progressBarStyleHorizontal);
                TextView progressTextView = new TextView(requireContext());
                progressTextView.setId(View.generateViewId());
                progressBar.setId(R.id.progbar);
                if(lastWatchedEpisode != 0){
                    double doubleValue = Double.parseDouble(ep);
                    int episodeInt = (int) doubleValue;
                    float calc = ((float) lastWatchedEpisode / episodeInt) * 100;
                    int porcent = (int) calc;

                    Log.d("Biblio", "pourcentage " + calc + "dernier episode " + lastWatchedEpisode + "episodeint " + episodeInt);

                    progressBar.setProgress(porcent);
                    progressTextView.setText(String.valueOf(porcent) + "%");

                }else{
                    progressBar.setProgress(0);
                    progressTextView.setText("0%");
                }


                progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#CE15C7")));

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        250,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                params.addRule(RelativeLayout.BELOW, progTextView.getId());
                params.addRule(RelativeLayout.END_OF, banniereImageView.getId());
                params.setMargins(50, 0, 0, 0);
                progressBar.setLayoutParams(params);

                animeLayout.addView(progressBar);


                progressTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
                progressTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.gris));
                RelativeLayout.LayoutParams progressParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                progressParams.addRule(RelativeLayout.END_OF, progressBar.getId());
                progressParams.addRule(RelativeLayout.BELOW, progTextView.getId());
                progressParams.setMarginStart(getResources().getDimensionPixelSize(R.dimen.episodes_margin_start));
                progressTextView.setLayoutParams(progressParams);
                animeLayout.addView(progressTextView);

                Picasso.get().load(images).into(banniereImageView);

        }
        } else {
            TextView textViewMessage = new TextView(requireContext());
            textViewMessage.setText("Votre bibliothèque d'anime est vide.");
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            textParams.setMargins(0, 300, 0, 0);
            textParams.gravity = Gravity.CENTER; // Centrer le TextView dans le LinearLayout
            textViewMessage.setLayoutParams(textParams);
            textViewMessage.setTextSize(18);
            int couleurTexte = ContextCompat.getColor(requireContext(), R.color.rose);
            textViewMessage.setTextColor(couleurTexte);

// Ajouter le TextView au conteneur
            relativeAnimes.addView(textViewMessage);
            relativeAnimes.setGravity(Gravity.CENTER);


            Log.d("Biblio", "TextView ajouté avec succès.");

        }
    }

    private void removeAnimeFromLibrary(int animeId) {
        // Récupérer la liste actuelle des animes dans la bibliothèque
        List<AnimeLibraryItem> animeLibrary = preferencesManager.getAnimeLibrary();
        Log.d("AnimeLibrary", "Nombre d'animes avant la suppression : " + animeLibrary.size());

        // Chercher l'anime avec l'animeId spécifié
        AnimeLibraryItem animeToRemove = null;
        for (AnimeLibraryItem anime : animeLibrary) {
            if (anime.getAnimeId() == animeId) {
                animeToRemove = anime;
                break;
            }
        }

        // Si l'anime est trouvé, le supprimer de la liste
        if (animeToRemove != null) {
            animeLibrary.remove(animeToRemove);
            Log.d("AnimeLibrary", "Anime supprimé : " + animeToRemove.getAnimeId());
            // Sauvegarder la liste mise à jour dans les préférences
            preferencesManager.saveAnimeLibrary(animeLibrary);
            Log.d("AnimeLibrary", "Nombre d'animes après la suppression : " + animeLibrary.size());
        } else {
            Log.d("AnimeLibrary", "Anime non trouvé avec l'ID : " + animeId);
        }
    }

    public Map<String, List<Object>> anime() {
        Map<String, List<Object>> result = new HashMap<>();
        List<Integer> animeIds = new ArrayList<>();
        List<Integer> lastEpisodes = new ArrayList<>();
        List<String> synList = new ArrayList<>();
        List<String> epList = new ArrayList<>();
        List<String> studioList = new ArrayList<>();
        List<String> genresList = new ArrayList<>();
        List<String> imagesList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();

        preferencesManager = new AnimePreferencesManager(getContext());
        List<AnimeLibraryItem> animeLibrary = preferencesManager.getAnimeLibrary();

        if (animeLibrary != null && !animeLibrary.isEmpty()) {
            Log.d("Biblio", "Size of animeLibrary: " + animeLibrary.size());
            for (AnimeLibraryItem item : animeLibrary) {
                int animeId = item.getAnimeId();
                int lastWatchedEpisode = item.getLastWatchedEpisode();
                String syn = item.syn();
                String ep = item.ep();
                String studio = item.studio();
                String genres = item.genres();
                String images = item.images();
                String title = item.title();

                Log.d("test", "AnimeId: " + animeId +
                        ", LastWatchedEpisode: " + lastWatchedEpisode +
                        ", Syn: " + syn +
                        ", Episodes: " + ep +
                        ", Studio: " + studio +
                        ", Genres: " + genres +
                        ", Titles: " + title +
                        ", Images: " + images);

                animeIds.add(animeId);
                lastEpisodes.add(lastWatchedEpisode);
                synList.add(syn);
                epList.add(ep);
                studioList.add(studio);
                genresList.add(genres);
                imagesList.add(images);
                titleList.add(title);
            }
            result.put("animeIds", (List<Object>) (List<?>) animeIds);
            result.put("lastEpisodes", (List<Object>) (List<?>) lastEpisodes);
            result.put("synList", (List<Object>) (List<?>) synList);
            result.put("studioList", (List<Object>) (List<?>) studioList);
            result.put("genreList", (List<Object>) (List<?>) genresList);
            result.put("epList", (List<Object>) (List<?>) epList);
            result.put("imagesList", (List<Object>) (List<?>) imagesList);
            result.put("titleList", (List<Object>) (List<?>) titleList);
        } else {
            // Handle the case where animeLibrary is null or empty
        }
        return result;
    }

    private List<Integer> convertListToInteger(List<Object> objectList) {
        List<Integer> IntegerList = new ArrayList<>();

        if (objectList != null) {
            for (Object obj : objectList) {
                if (obj instanceof Integer) {
                    IntegerList.add((Integer) obj);
                } else {
                    // Handle the case where the object is not an Integer (optional)
                }
            }
        } else {
            // Gérer le cas où la liste est null
        }



        return IntegerList;
    }

    private List<String> convertListToString(List<Object> objectList) {
        List<String> stringList = new ArrayList<>();

        if (objectList != null) {
            for (Object obj : objectList) {
                if (obj instanceof String) {
                    stringList.add((String) obj);
                } else {
                    // Handle the case where the object is not a String (optional)
                }
            }
        } else {

        }
        return stringList;
    }
}