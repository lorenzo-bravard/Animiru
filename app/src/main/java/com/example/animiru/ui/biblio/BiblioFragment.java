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


        JikanApi apiService = RetrofitClient.getClient("https://api.jikan.moe/v4/").create(JikanApi.class);

// Récupérez la Map mise à jour des animeIds et lastEpisodes à chaque appel
        Map<String, List<Integer>> animeMap = anime();
        List<Integer> animeIds = animeMap.get("animeIds");
        List<Integer> lastEpisodes = animeMap.get("lastEpisodes");

        LinearLayout relativeAnimes = view.findViewById(R.id.animes);

        if (animeIds != null && !animeIds.isEmpty()) {
            for (int i = 0; i < animeIds.size(); i++) {
                int element = animeIds.get(i);
                int lastWatchedEpisode = lastEpisodes.get(i);

                Call<AnimeData> call = apiService.getAnimeDetails(element);
                call.enqueue(new Callback<AnimeData>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<AnimeData> call, Response<AnimeData> response) {
                        if (isAdded() && getContext() != null) {

                            if (response.isSuccessful()) {
                                AnimeData animeData = response.body();
                                Object episodes = animeData.getData().getEpisodes();
                                String title = animeData.getData().getTitle();
                                String syn = animeData.getData().getSynopsis();


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
                                        mainActivity.pageAnime(element);

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
                                titleTextView.setLayoutParams(titleParams);
                                animeLayout.addView(titleTextView);

// Création du TextView pour le nombre d'épisodes
                                TextView episodesTextView = new TextView(requireContext());
                                episodesTextView.setId(View.generateViewId());
                                episodesTextView.setText(String.valueOf(episodes) + " épisodes");
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
                                epTextView.setText(String.valueOf(lastWatchedEpisode) + " épisodes");
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
                                progressBar.setId(R.id.progbar);
                                int episodeint = ((Number) episodes).intValue();
                                float calc = ((float) lastWatchedEpisode / episodeint) * 100;
                                int porcent = (int) calc;

                                Log.d("Biblio", "pourcentage " + calc + "dernier episode " + lastWatchedEpisode + "episodeint " + episodeint);

                                progressBar.setProgress(porcent);
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

                                TextView progressTextView = new TextView(requireContext());
                                progressTextView.setId(View.generateViewId());
                                progressTextView.setText(String.valueOf(porcent) + "%");
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

                                Images.Jpg jpg = animeData.getData().getImages().getJpg();
                                if (jpg != null) {
                                    String url = jpg.getLarge_image_url();
                                    Picasso.get().load(url).into(banniereImageView);
                                } else {

                                }
                            } else {

                            }
                        }else{

                        }
                    }

                        @Override
                        public void onFailure(Call<AnimeData> call, Throwable t) {

                        }
                    });
                }
            } else {
                TextView textViewMessage = new TextView(requireContext());
                textViewMessage.setText("Votre bibliothèque d'anime est vide.");
                textViewMessage.setTextSize(18);
                int couleurTexte = ContextCompat.getColor(requireContext(), R.color.rose);
                textViewMessage.setTextColor(couleurTexte);

                // Ajouter le TextView au conteneur
                relativeAnimes.addView(textViewMessage);
                Log.d("Biblio", "TextView ajouté avec succès.");

            }
        }

    public Map<String, List<Integer>> anime() {
        Map<String, List<Integer>> result = new HashMap<>();
        List<Integer> animeIds = new ArrayList<>();
        List<Integer> lastEpisodes = new ArrayList<>();

        preferencesManager = new AnimePreferencesManager(getContext());
        List<AnimeLibraryItem> animeLibrary = preferencesManager.getAnimeLibrary();

        if (animeLibrary != null && !animeLibrary.isEmpty()) {
            Log.d("Biblio", "Size of animeLibrary: " + animeLibrary.size());
            for (AnimeLibraryItem item : animeLibrary) {
                int animeId = item.getAnimeId();
                int lastWatchedEpisode = item.getLastWatchedEpisode();

                Log.d("test", "AnimeId: " + animeId + ", LastWatchedEpisode: " + lastWatchedEpisode);

                animeIds.add(animeId);
                lastEpisodes.add(lastWatchedEpisode);
            }
            result.put("animeIds", animeIds);
            result.put("lastEpisodes", lastEpisodes);
        } else {
            // Handle the case where animeLibrary is null or empty
        }
        return result;
    }
}