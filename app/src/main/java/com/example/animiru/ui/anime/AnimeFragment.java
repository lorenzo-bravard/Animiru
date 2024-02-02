package com.example.animiru.ui.anime;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.animiru.MainActivity;
import com.example.animiru.R;
import com.example.animiru.data.AnimeData;
import com.example.animiru.databinding.FragmentAnimeBinding;
import com.example.animiru.ui.JikanApi;
import com.example.animiru.ui.RetrofitClient;
import com.squareup.picasso.Picasso;

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
    private static final String ARG_ANIME_ID = "anime_id";

    private FragmentAnimeBinding binding;
    private int anime_id;

    // Constructeur vide requis par Fragment
    public AnimeFragment() {
    }

    // Mise à jour de la méthode newInstance pour accepter l'animeid
    public static AnimeFragment newInstance(int animeid) {
        AnimeFragment fragment = new AnimeFragment();
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
        binding = FragmentAnimeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Vérifiez si binding est null avant d'accéder à ses propriétés
        if (binding != null) {
            binding.menuInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Obtenez le contexte de l'activité actuelle
                    MainActivity mainActivity = (MainActivity) v.getContext();

                    // Appel de la méthode pour changer de fragment
                    mainActivity.pageinfo(anime_id);
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
                    String title = animeData.getData().getTitle();
                    binding.title.setText(title);



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
