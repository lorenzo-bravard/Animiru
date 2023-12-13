package com.example.animiru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import com.example.animiru.databinding.ActivityMainBinding;
import com.example.animiru.ui.biblio.BiblioFragment;
import com.example.animiru.ui.ajout.AjoutFragment;




public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private  boolean pageAjoutVisible = true;

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
        ImageView btn_ajout = findViewById(R.id.btn_ajout);

        // Ajout d'un OnClickListener à l'ImageView
        btn_ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Appel de la méthode pour changer de fragment
                if (pageAjoutVisible == false){
                    pageAjout();
                    ImageView imageView = findViewById(R.id.btn_ajout);
                    TextView textView = findViewById(R.id.titre_page);
                    TextView biblio = findViewById(R.id.biblio);
                    textView.setText(R.string.menu_ajout);
                    biblio.setTextColor(getColor(R.color.gris));
                    imageView.setImageResource(R.drawable.ann);
                    imageView.setId(R.id.btn_ajout);
                    pageAjoutVisible = true;
                }else{
                    getSupportFragmentManager().popBackStack();
                    ImageView imageView = findViewById(R.id.btn_ajout);
                    TextView textView = findViewById(R.id.titre_page);
                    TextView biblio = findViewById(R.id.biblio);
                    textView.setText(R.string.menu_biblio);
                    biblio.setTextColor(getColor(R.color.rose));
                    imageView.setImageResource(R.drawable.ajout);
                    pageAjoutVisible = false;
                }
            }
        });
        TextView btn_biblio = findViewById(R.id.biblio);
        btn_biblio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Appel de la méthode pour changer de fragment
                pageAcceuil();
            }
        });
    }

    // Méthode pour changer de fragment
    @SuppressLint("ResourceAsColor")
    public void pageAjout() {
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
        ImageView imageView = findViewById(R.id.btn_ajout);
        TextView textView = findViewById(R.id.titre_page);
        TextView biblio = findViewById(R.id.biblio);
        textView.setText(R.string.menu_biblio);
        biblio.setTextColor(getColor(R.color.rose));
        imageView.setImageResource(R.drawable.ajout);

    }
}


