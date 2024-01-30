package com.example.animiru.ui.biblio;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        RelativeLayout RelativeAnimes = view.findViewById(R.id.animes);
            if (!animeIds.isEmpty()) {
                for (int element : animeIds) {
                    Call<AnimeData> call = apiService.getAnimeDetails(element);
                    call.enqueue(new Callback<AnimeData>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(Call<AnimeData> call, Response<AnimeData> response) {
                            if (response.isSuccessful()) {
                                AnimeData AnimeData = response.body();
                                Object Episodes = AnimeData.getData().getEpisodes();
                                String title = AnimeData.getData().getTitle();
                                String syn = AnimeData.getData().getSynopsis();


                                RelativeLayout relativeAnimes = view.findViewById(R.id.animes);
                                RelativeLayout animeLayout = new RelativeLayout(requireContext());

                                // Définir le fond à partir du drawable défini
                                animeLayout.setBackgroundResource(R.drawable.rectangle);

                                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        getResources().getDimensionPixelSize(R.dimen.anime_layout_height)
                                );
                                animeLayout.setLayoutParams(layoutParams);
                                relativeAnimes.addView(animeLayout);

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
                                episodesTextView.setText(String.valueOf(Episodes) + " épisodes");
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
                                synopsisTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
                                synopsisTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.gris));
                                RelativeLayout.LayoutParams synopsisParams = new RelativeLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                );
                                synopsisParams.addRule(RelativeLayout.END_OF,banniereImageView.getId());
                                synopsisParams.addRule(RelativeLayout.BELOW, episodesTextView.getId());
                                synopsisParams.setMarginStart(getResources().getDimensionPixelSize(R.dimen.synopsis_margin_start));
                                synopsisParams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.synopsis_margin_top), 0, 0);
                                synopsisTextView.setLayoutParams(synopsisParams);
                                animeLayout.addView(synopsisTextView);

// Création du TextView pour le nombre d'épisodes
                                TextView epTextView = new TextView(requireContext());
                                epTextView.setId(View.generateViewId());
                                epTextView.setText("ep");
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
                                Images.Jpg jpg = AnimeData.getData().getImages().getJpg();
                                if (jpg != null) {
                                    String url = jpg.getLarge_image_url();
                                    Picasso.get().load(url).into(banniereImageView);
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
            } else {
                TextView textViewMessage = new TextView(requireContext());
                textViewMessage.setText("Votre bibliothèque d'anime est vide.");
                textViewMessage.setTextSize(18);
                int couleurTexte = ContextCompat.getColor(requireContext(), R.color.rose);
                textViewMessage.setTextColor(couleurTexte);

                // Ajouter le TextView au conteneur
                RelativeAnimes.addView(textViewMessage);
                Log.d("Biblio", "TextView ajouté avec succès.");

            }
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
        }else{

        }
        return animeIds;
    }
}