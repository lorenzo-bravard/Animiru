package com.example.animiru.ui.anime;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.animiru.MainActivity;
import com.example.animiru.R;

import com.example.animiru.data.AnimeData;
import com.example.animiru.databinding.FragmentAnimeBinding;
import com.example.animiru.stockage.AnimeLibraryItem;
import com.example.animiru.stockage.AnimePreferencesManager;
import com.example.animiru.ui.JikanApi;
import com.example.animiru.ui.RetrofitClient;
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
 * Use the {@link AnimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnimeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_lastWatchedEpisode = "lastWatchedEpisode";
    private static final String ARG_animeid = "animeid";


    private static final String ARG_ep = "ep";
    private static final String ARG_images = "images";
    private static final String ARG_title = "title";
    private static final String ARG_syn = "syn";

    private static final String ARG_studio = "studio";

    private static final String ARG_genre = "genre";

    private FragmentAnimeBinding binding;

    private int lastWatchedEpisode;
    private int animeid;
    private String ep;
    private String images;
    private String title;
    private String syn;
    private String studio;
    private String genre;


    private AnimePreferencesManager preferencesManager;


    public AnimeFragment() {
        // Required empty public constructor
    }
    public static AnimeFragment newInstance(int lastWatchedEpisode, String ep, String images, String title, String syn, String studio, String genres, int animeid) {
        AnimeFragment fragment = new AnimeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_lastWatchedEpisode, lastWatchedEpisode);
        args.putInt(ARG_animeid, animeid);
        args.putString(ARG_syn, syn);
        args.putString(ARG_studio, studio);
        args.putString(ARG_genre, genres);
        args.putString(ARG_ep, ep);
        args.putString(ARG_images, images);
        args.putString(ARG_title, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lastWatchedEpisode = getArguments().getInt(ARG_lastWatchedEpisode, 0);
            animeid = getArguments().getInt(ARG_animeid, 0);
            studio = getArguments().getString(ARG_studio,"Information non trouvé");
            genre = getArguments().getString(ARG_genre,"Information non trouvé");
            syn = getArguments().getString(ARG_syn,"Information non trouvé");
            ep = getArguments().getString(ARG_ep,"eee");
            images = getArguments().getString(ARG_images,"eee");
            title = getArguments().getString(ARG_title,"eee");
        }
        preferencesManager = new AnimePreferencesManager(requireContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAnimeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (binding != null) {
            binding.menuInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity mainActivity = (MainActivity) v.getContext();
                    // Appel de la méthode pour changer de fragment
                    mainActivity.pageinfo(syn, ep, studio, genre, images, title);
                }
            });
        } else {
            Log.e("AnimeFragment", "binding is null");
        }
        binding.title.setText(title);
        Picasso.get().load(images).into(binding.manga);

        RelativeLayout cateEp = view.findViewById(R.id.cate_ep);
        TextView textView = new TextView(requireContext());
        textView.setId(View.generateViewId());
        RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textLayoutParams.setMargins(10, 10, 10, 0);
        textView.setLayoutParams(textLayoutParams);
        textView.setText("Episodes :");
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
        cateEp.addView(textView);

        SeekBar seekBar = new SeekBar(requireContext());
        seekBar.setId(View.generateViewId());
        RelativeLayout.LayoutParams seekBarLayoutParams = new RelativeLayout.LayoutParams(
                900,
                40
        );
        seekBarLayoutParams.addRule(RelativeLayout.BELOW, R.id.titre);
        seekBarLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        seekBarLayoutParams.setMargins(0, 60, 0, 0);
        seekBarLayoutParams.addRule(RelativeLayout.BELOW, textView.getId());
        seekBar.setLayoutParams(seekBarLayoutParams);
        seekBar.setProgressDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.custom_seekbar_progress));
        seekBar.setThumb(ContextCompat.getDrawable(requireContext(), R.drawable.custom_seekbar_thumb));

        TextView textViewep = new TextView(requireContext());
        textViewep.setId(View.generateViewId());
        RelativeLayout.LayoutParams textLayoutParam = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textLayoutParam.setMargins(10, 10, 10, 0);
        textLayoutParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textLayoutParam.addRule(RelativeLayout.BELOW, seekBar.getId());
        textViewep.setText("Episode " + lastWatchedEpisode);

        textViewep.setLayoutParams(textLayoutParam);
        textViewep.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewep.setText("Episode " + progress);
                updateLastWatchedEpisode(animeid, progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        if(!Objects.equals(ep, "null")){
            double doubleEp = Double.parseDouble(ep);
            int intEp = (int) doubleEp;
            seekBar.setMax(intEp);
            seekBar.setProgress(lastWatchedEpisode);
        }else{
            seekBar.setMax(0);
            seekBar.setProgress(0);
        }
        cateEp.addView(seekBar);
        cateEp.addView(textViewep);

        TextView textViewExp = new TextView(requireContext());
        textViewExp.setId(View.generateViewId());
        RelativeLayout.LayoutParams textViewExpLayoutParam = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textViewExpLayoutParam.setMargins(70, 100, 10, 0);
        textViewExpLayoutParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textViewExpLayoutParam.addRule(RelativeLayout.BELOW, textViewep.getId());
        textViewExp.setText("Sélectionner le dernier épisode que vous avez vue !");
        textViewExp.setTextSize(18);
        textViewExp.setLayoutParams(textViewExpLayoutParam);
        textViewExp.setTextColor(ContextCompat.getColor(requireContext(), R.color.rose));
        cateEp.addView(textViewExp);


    }
    private void updateLastWatchedEpisode(int animeId, int newLastWatchedEpisode) {
        List<AnimeLibraryItem> animeLibrary = preferencesManager.getAnimeLibrary();
        Log.d("AnimeLibrary", "Nombre d'animes avant la mise à jour : " + animeLibrary.size());

        AnimeLibraryItem animeToUpdate = null;
        for (AnimeLibraryItem anime : animeLibrary) {
            if (anime.getAnimeId() == animeId) {
                animeToUpdate = anime;
                break;
            }
        }
        if (animeToUpdate != null) {
            animeToUpdate.setLastWatchedEpisode(newLastWatchedEpisode);
            Log.d("AnimeLibrary", "Dernier épisode vu mis à jour pour l'anime : " + animeToUpdate.getAnimeId());

            preferencesManager.saveAnimeLibrary(animeLibrary);
            Log.d("AnimeLibrary", "Nombre d'animes après la mise à jour : " + animeLibrary.size());
        } else {
            Log.d("AnimeLibrary", "Anime non trouvé avec l'ID : " + animeId);
        }
    }
}
