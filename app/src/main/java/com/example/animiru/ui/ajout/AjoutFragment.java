package com.example.animiru.ui.ajout;


import android.annotation.SuppressLint;

import android.content.res.ColorStateList;
import android.graphics.Color;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.example.animiru.stockage.AnimeLibraryItem;
import com.example.animiru.stockage.AnimePreferencesManager;


import com.example.animiru.data.AnimeData;
import com.example.animiru.data.AnimeQuerry;
import com.example.animiru.databinding.FragmentAjoutBinding;
import com.example.animiru.databinding.ActivityMainBinding;
import com.example.animiru.ui.JikanApi;
import com.example.animiru.ui.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;


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
    private ActivityMainBinding b;
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

        b = ActivityMainBinding.inflate(inflater, container, false);
        //return binding.getRoot();
        Log.d("SearchResults", "createview: ");
        // Inflate the layout for this fragment

        binding.searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Désactiver le bouton au clic de l'icône
                b.footer1.setEnabled(false);
                // Masquer le footer1
                b.footer1.setVisibility(View.GONE);
            }

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

        // Définir un écouteur sur la fermeture du SearchView
        binding.searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // Réactiver le bouton en quittant le SearchView
                b.footer1.setEnabled(true);
                // Afficher à nouveau le footer1
                b.footer1.setVisibility(View.VISIBLE);
                return false;
            }
        });

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


                // Rendre le TextView invisible
                binding.textView2.setVisibility(View.INVISIBLE);

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
                                // Create RelativeLayout for each anime (animeLayout)
                                RelativeLayout animeLayout = new RelativeLayout(requireContext());

                                // Définir le fond à partir du drawable défini
                                final boolean[] isCrossVisible = {false};

                                animeLayout.setBackgroundResource(R.drawable.rectangle);
                                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        getResources().getDimensionPixelSize(R.dimen.anime_layout_height)
                                );
                                layoutParams.addRule(RelativeLayout.BELOW, R.id.searchView);
                                layoutParams.setMargins(0, 100, 0, 0);
                                animeLayout.setLayoutParams(layoutParams);

                                ImageView addImageView = new ImageView(requireContext());
                                addImageView.setId(View.generateViewId());


                                addImageView.setImageResource(R.drawable.ajout);
                                RelativeLayout.LayoutParams addParams = new RelativeLayout.LayoutParams(
                                        getResources().getDimensionPixelSize(R.dimen.suppr_width),
                                        getResources().getDimensionPixelSize(R.dimen.suppr_height)
                                );
                                addParams.addRule(RelativeLayout.END_OF, R.id.banniere);
                                addParams.addRule(RelativeLayout.ALIGN_TOP, R.id.banniere);
                                addParams.setMarginStart(getResources().getDimensionPixelSize(R.dimen.suppr_margin_start));
                                addImageView.setLayoutParams(addParams);




                                addImageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Code à exécuter lors du clic sur supprImageView

                                        Object episodes = animeData.getEpisodes();
                                        String title = animeData.getTitle_english();
                                        String syn = animeData.getSynopsis();
                                        List<AnimeQuerry.Data.Themes> studios = animeData.getStudios();
                                        List<AnimeQuerry.Data.Themes> Genres = animeData.getGenres();

                                        StringBuilder stringBuilder = new StringBuilder();

                                        for (AnimeQuerry.Data.Themes studio : studios) {
                                            stringBuilder.append(studio.getName()).append("\n");
                                        }

                                        StringBuilder stringBuilder2 = new StringBuilder();

                                        for (AnimeQuerry.Data.Themes genre : Genres) {
                                            stringBuilder2.append(genre.getName()).append("\n");
                                        }
                                        AnimeQuerry.Data.Images.Jpg jpg = animeData.getImages().getJpg();
                                        String url = jpg.getLarge_image_url();

                                        // Exemple d'utilisation : Ajouter un anime à la bibliothèque
                                        addAnimeToLibrary(animeData.getMal_id(), 0, syn, String.valueOf(episodes),stringBuilder.toString(), stringBuilder2.toString(),url,title);

                                        // Basculer entre les images de plus et de croix
                                        if (isCrossVisible[0]) {
                                            addImageView.setImageResource(R.drawable.ajout);
                                        } else {
                                            addImageView.setImageResource(R.drawable.ann);
                                        }

                                        isCrossVisible[0] = !isCrossVisible[0]; // Inverser l'état
                                    }
                                });
                                animeLayout.addView(addImageView);



                                ImageView banniereImageView = new ImageView(requireContext());
                                banniereImageView.setId(View.generateViewId());
                                RelativeLayout.LayoutParams banniereParams = new RelativeLayout.LayoutParams(
                                        getResources().getDimensionPixelSize(R.dimen.banniere_width),
                                        ViewGroup.LayoutParams.MATCH_PARENT
                                );
                                banniereParams.addRule(RelativeLayout.ALIGN_PARENT_START);
                                banniereParams.addRule(RelativeLayout.CENTER_VERTICAL);
                                banniereParams.setMarginStart(getResources().getDimensionPixelSize(R.dimen.banniere_margin_start));
                                Picasso.get().load(animeData.getImages().getJpg().getImage_url()).into(banniereImageView);

                                banniereImageView.setLayoutParams(banniereParams);
                                animeLayout.addView(banniereImageView);

                                TextView titleTextView = new TextView(requireContext());
                                titleTextView.setId(View.generateViewId());
                                titleTextView.setText(animeData.getTitle_english());
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
                                if(!Objects.equals(String.valueOf(animeData.getEpisodes()), "null")){
                                    episodesTextView.setText(String.valueOf(animeData.getEpisodes()) + " épisodes");
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
                                synopsisTextView.setText(animeData.getSynopsis());
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
                                progressBar.setProgress(0);
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
                                progressTextView.setText(String.valueOf(0) + "%");
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


// Add infoLayout to animeLayout


// Add animeLayout to the LinearLayout
                                linearLayout.addView(animeLayout);

                            }

// Ajoutez le LinearLayout à la ScrollView
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