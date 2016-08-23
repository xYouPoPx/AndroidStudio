package com.example.ycourteau.sqlite_developpezcom;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Tutorial_Android extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial__android);

        //Création d'une instance de ma classe LivresBDD
        LivresBDD livreBdd = new LivresBDD(this);

        //Création d'un livre
        Livre livre = new Livre("123456789", "Programmez pour Android");

        //On ouvre la base de données pour écrire dedans
        livreBdd.open();
        //On insère le livre que l'on vient de créer
        livreBdd.insertLivre(livre);

        //Pour vérifier que l'on a bien créé notre livre dans la BDD
        //on extrait le livre de la BDD grâce au titre du livre que l'on a créé précédemment
        Livre livreFromBdd = livreBdd.getLivreWithTitre(livre.getTitre());
        //Si un livre est retourné (donc si le livre à bien été ajouté à la BDD)
        if(livreFromBdd != null){
            //On affiche les infos du livre dans un Toast
            Toast.makeText(this, livreFromBdd.toString(), Toast.LENGTH_LONG).show();
            //On modifie le titre du livre
            livreFromBdd.setTitre("J'ai modifié le titre du livre");
            //Puis on met à jour la BDD
            livreBdd.updateLivre(livreFromBdd.getId(), livreFromBdd);
        }

        //On extrait le livre de la BDD grâce au nouveau titre
        livreFromBdd = livreBdd.getLivreWithTitre("J'ai modifié le titre du livre");
        //S'il existe un livre possédant ce titre dans la BDD
        if(livreFromBdd != null){
            //On affiche les nouvelles informations du livre pour vérifier que le titre du livre a bien été mis à jour
            Toast.makeText(this, livreFromBdd.toString(), Toast.LENGTH_LONG).show();
            //on supprime le livre de la BDD grâce à son ID
            livreBdd.removeLivreWithID(livreFromBdd.getId());
        }

        //On essaye d'extraire de nouveau le livre de la BDD toujours grâce à son nouveau titre
        livreFromBdd = livreBdd.getLivreWithTitre("J'ai modifié le titre du livre");
        //Si aucun livre n'est retourné
        if(livreFromBdd == null){
            //On affiche un message indiquant que le livre n'existe pas dans la BDD
            Toast.makeText(this, "Ce livre n'existe pas dans la BDD", Toast.LENGTH_LONG).show();
        }
        //Si le livre existe (mais normalement il ne devrait pas)
        else{
            //on affiche un message indiquant que le livre existe dans la BDD
            Toast.makeText(this, "Ce livre existe dans la BDD", Toast.LENGTH_LONG).show();
        }

        livreBdd.close();
    }
}