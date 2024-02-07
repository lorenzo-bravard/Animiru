package com.example.animiru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.animiru.stockage.AnimePreferencesManager;




import com.example.animiru.databinding.ActivityMainBinding;
import com.example.animiru.ui.anime.AnimeFragment;
import com.example.animiru.ui.biblio.BiblioFragment;
import com.example.animiru.ui.ajout.AjoutFragment;
import com.example.animiru.ui.top.TopFragment;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private static final String LAST_VERSION_CODE_PREF = "lastVersionCode";

    private  boolean pageAjoutVisible = false;

    private  boolean pageBiblio = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        BiblioFragment BiblioFragment = new BiblioFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, BiblioFragment)
                .commit();

        // Ajout d'un OnClickListener à l'ImageView
        binding.btnAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Appel de la méthode pour changer de fragment
                if (!pageAjoutVisible){
                    pageAjout();
                    binding.titrePage.setText(R.string.menu_ajout);
                    binding.biblio.setTextColor(getColor(R.color.gris));
                    binding.btnAjout.setImageResource(R.drawable.ann);
                    binding.header.setVisibility(View.VISIBLE);
                    pageAjoutVisible = true;
                }else{
                    pageAcceuil(true);
                    pageAjoutVisible = false;
                }
            }
        });
        binding.biblio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageBiblio){
                    pageAcceuil(true);
                    pageBiblio = false;
                }else{
                }
            }
        });
        //AnimePreferencesManager preferencesManager = new AnimePreferencesManager(this);
        //preferencesManager.resetAnimeLibraryAndJson();
    }


    // Méthode pour changer de fragment
    @SuppressLint("ResourceAsColor")
    public void pageAjout() {
        Log.d("MainActivity", "pageAjout() called");

        // Création d'une instance du deuxième fragment (Fragment2)
        AjoutFragment AjoutFragment = new AjoutFragment();

        // Obtention du gestionnaire de fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Début de la transaction de fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_right, R.anim.exit_to_left);


        // Remplacement du fragment actuel par le nouveau fragment (Fragment2)
        transaction.replace(R.id.fragment_container_view, AjoutFragment);

        // Ajout à la pile de retour arrière (retour possible avec le bouton physique "Retour")
        transaction.addToBackStack(null);

        // Validation de la transaction
        transaction.commit();
        pageBiblio = true;

    }
    // Méthode pour changer de fragment
    @SuppressLint("ResourceAsColor")
    public void pageAcceuil(boolean bool) {
        Log.d("MainActivity", "pageAcceuil() called");

        // Création d'une instance du deuxième fragment (Fragment2)
        BiblioFragment BiblioFragment = new BiblioFragment();

        // Obtention du gestionnaire de fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Début de la transaction de fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if(bool){
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        }else{

        }

        // Remplacement du fragment actuel par le nouveau fragment (Fragment2)
        transaction.replace(R.id.fragment_container_view, BiblioFragment);

        // Ajout à la pile de retour arrière (retour possible avec le bouton physique "Retour")
        transaction.addToBackStack(null);

        // Validation de la transaction
        transaction.commit();
        binding.titrePage.setText(R.string.menu_biblio);
        binding.biblio.setTextColor(getColor(R.color.rose));
        binding.btnAjout.setImageResource(R.drawable.ajout);
        binding.header.setVisibility(View.VISIBLE);
        pageAjoutVisible = false;

    }

    @SuppressLint("ResourceAsColor")
    public void pageAnime( int lastWatchedEpisode, String ep, String images, String title, String syn, String studio, String genres, int animeid) {
        Log.d("MainActivity", "pageAnime() called"+ title);

        // Création d'une instance du deuxième fragment (Fragment2)
        AnimeFragment animeFragment = AnimeFragment.newInstance(lastWatchedEpisode, ep, images, title, syn, studio, genres, animeid);

        // Obtention du gestionnaire de fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Début de la transaction de fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);

        // Remplacement du fragment actuel par le nouveau fragment (Fragment2)
        transaction.replace(R.id.fragment_container_view, animeFragment);

        // Ajout à la pile de retour arrière (retour possible avec le bouton physique "Retour")
        transaction.addToBackStack(null);

        // Validation de la transaction
        transaction.commit();
        binding.header.setVisibility(View.GONE);
        pageBiblio = true;
    }

    @SuppressLint("ResourceAsColor")
    public void pageinfo(String syn, String ep, String studio, String genres, String images, String title) {
        Log.d("MainActivity", "pageInfo() called");

        // Création d'une instance du deuxième fragment (Fragment2)
        TopFragment topFragment = TopFragment.newInstance(syn, ep, studio, genres, images, title);

        // Obtention du gestionnaire de fragments

        // Obtention du gestionnaire de fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Début de la transaction de fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Remplacement du fragment actuel par le nouveau fragment (Fragment2)
        transaction.replace(R.id.fragment_container_view, topFragment);

        // Ajout à la pile de retour arrière (retour possible avec le bouton physique "Retour")
        transaction.addToBackStack(null);

        // Validation de la transaction
        transaction.commit();
        binding.header.setVisibility(View.GONE);
        pageBiblio = true;
    }
}


