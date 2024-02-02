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
                    pageAcceuil();
                    pageAjoutVisible = false;
                }
            }
        });
        binding.biblio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Appel de la méthode pour changer de fragment
                pageAcceuil();
                pageAjoutVisible = false;

            }
        });

        // Obtenez les préférences partagées
        SharedPreferences preferences = getSharedPreferences("AnimePreferences", Context.MODE_PRIVATE);
        AnimePreferencesManager preferencesManager = new AnimePreferencesManager(this);

        // Vérifiez si l'application a été réinstallée ou mise à jour
        if (isAppUpdated(preferences)) {
            preferencesManager.resetAnimeLibraryAndJson();
        }
    }

    private boolean isAppUpdated(SharedPreferences preferences) {
        // Obtenir le numéro de version actuel de l'application
        int currentVersionCode = getVersionCode();

        // Obtenir la dernière version enregistrée
        int lastVersionCode = preferences.getInt(LAST_VERSION_CODE_PREF, -1);

        // Comparer les numéros de version
        return lastVersionCode != currentVersionCode;
    }

    private int getVersionCode() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }


    private void effacerSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("AnimePreferences", Context.MODE_PRIVATE);
        Log.d("MainActivity", "Contenu avant effacement : " + preferences.getString(AnimePreferencesManager.KEY_ANIME_LIBRARY, "Non trouvé"));
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(AnimePreferencesManager.KEY_ANIME_LIBRARY);
        editor.apply();
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

        // Remplacement du fragment actuel par le nouveau fragment (Fragment2)
        transaction.replace(R.id.fragment_container_view, AjoutFragment);

        // Ajout à la pile de retour arrière (retour possible avec le bouton physique "Retour")
        transaction.addToBackStack(null);

        // Validation de la transaction
        transaction.commit();

    }
    // Méthode pour changer de fragment
    @SuppressLint("ResourceAsColor")
    public void pageAcceuil() {
        Log.d("MainActivity", "pageAcceuil() called");

        // Création d'une instance du deuxième fragment (Fragment2)
        BiblioFragment BiblioFragment = new BiblioFragment();

        // Obtention du gestionnaire de fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Début de la transaction de fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();

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

    }

    @SuppressLint("ResourceAsColor")
    public void pageAnime(int element) {
        Log.d("MainActivity", "pageAnime() called"+ element);

        // Création d'une instance du deuxième fragment (Fragment2)
        AnimeFragment animeFragment = AnimeFragment.newInstance(element);

        // Obtention du gestionnaire de fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Début de la transaction de fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Remplacement du fragment actuel par le nouveau fragment (Fragment2)
        transaction.replace(R.id.fragment_container_view, animeFragment);

        // Ajout à la pile de retour arrière (retour possible avec le bouton physique "Retour")
        transaction.addToBackStack(null);

        // Validation de la transaction
        transaction.commit();
        binding.header.setVisibility(View.GONE);
    }

    @SuppressLint("ResourceAsColor")
    public void pageinfo(int element) {
        Log.d("MainActivity", "pageInfo() called");

        // Création d'une instance du deuxième fragment (Fragment2)
        TopFragment topFragment = TopFragment.newInstance(element);

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
    }
}


