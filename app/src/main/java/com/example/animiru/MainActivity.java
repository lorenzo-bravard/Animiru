package com.example.animiru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import com.example.animiru.databinding.ActivityMainBinding;
import com.example.animiru.ui.biblio.BiblioFragment;
import com.example.animiru.ui.ajout.AjoutFragment;




public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
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

        // Récupération de l'ImageView à partir de la mise en page
        ImageView imageView = findViewById(R.id.btn_ajout);

        // Ajout d'un OnClickListener à l'ImageView
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Appel de la méthode pour changer de fragment
                changerDeFragment();
            }
        });
    }

    // Méthode pour changer de fragment
    public void changerDeFragment() {
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
        ImageView imageView = findViewById(R.id.btn_ajout);
        TextView textView = findViewById(R.id.titre_page);
        textView.setText(R.string.menu_ajout);
        imageView.setImageResource(R.drawable.ann);

    }
    }
