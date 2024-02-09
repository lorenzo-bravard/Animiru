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
import java.util.Objects;

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
    private static final String ARG_ep = "ep";
    private static final String ARG_images = "images";
    private static final String ARG_title = "title";
    private static final String ARG_syn = "syn";

    private static final String ARG_studio = "studio";

    private static final String ARG_genre = "genre";



    private String ep;
    private String images;
    private String title;
    private String syn;
    private String studio;
    private String genre;

    private FragmentTopBinding binding;


    // TODO: Rename and change types of parameters


    public TopFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TopFragment newInstance(String syn, String ep, String studio, String genres, String images, String title) {
        TopFragment fragment = new TopFragment();
        Bundle args = new Bundle();
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
        // Récupérez la valeur de anime_id à partir des arguments du fragment
        if (getArguments() != null) {
            ep = getArguments().getString(ARG_ep,"Information non trouvé");
            studio = getArguments().getString(ARG_studio,"Information non trouvé");
            genre = getArguments().getString(ARG_genre,"Information non trouvé");
            syn = getArguments().getString(ARG_syn,"Information non trouvé");
            images = getArguments().getString(ARG_images,"Information non trouvé");
            title = getArguments().getString(ARG_title,"Information non trouvé");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTopBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
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
        binding.totalGen.setText(genre);
        binding.totalStud.setText(studio);
        binding.title.setText(title);
        if(!Objects.equals(ep, "null")){
            binding.totalEp.setText(ep + " épisodes");
        }else{
            binding.totalEp.setText("Nous n'avons pas d'information pour l'instant.");
        }
        binding.totalSyn.setText(syn);
        Picasso.get().load(images).into(binding.manga);
    }
}