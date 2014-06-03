package com.example.megamovies.app;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Intent;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.GridView;
import android.widget.SimpleAdapter;


public class MainActivity extends Activity implements View.OnClickListener{

    private int i = 0;
    private GridView listMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Récupération de la listview créée dans le fichier main.xml
        listMovies = (GridView) findViewById(R.id.listofmovies);

        //Création de la ArrayList qui nous permettra de remplir la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        //On déclare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;

        //Création d'une HashMap pour insérer les informations du premier item de notre listView
        map = new HashMap<String, String>();
        map.put("titre", "Avatar");
        map.put("genre", "Science-fiction");
        map.put("img", String.valueOf(R.drawable.list_avatar));
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("titre", "Django");
        map.put("genre", "Western");
        map.put("img", String.valueOf(R.drawable.list_django));
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("titre", "Forrest Gump");
        map.put("genre", "Comédie, Romance");
        map.put("img", String.valueOf(R.drawable.list_forrestgump));
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("titre", "Gran Torino");
        map.put("genre", "Drame, Thriller");
        map.put("img", String.valueOf(R.drawable.list_grantorino));
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("titre", "Intouchables");
        map.put("genre", "Comédie");
        map.put("img", String.valueOf(R.drawable.list_intouchables));
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("titre", "The dark knight rises");
        map.put("genre", "Action, Thriller");
        map.put("img", String.valueOf(R.drawable.list_thedarkknightrises));
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("titre", "Pulp Fiction");
        map.put("genre", "Policier, Thriller");
        map.put("img", String.valueOf(R.drawable.list_pulpfiction));
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("titre", "Fight Club");
        map.put("genre", "Thriller, Drame");
        map.put("img", String.valueOf(R.drawable.list_fightclub));
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("titre", "Le seigneur des anneaux");
        map.put("genre", "Fantastique, Action");
        map.put("img", String.valueOf(R.drawable.list_leseigneurdesanneaux));
        listItem.add(map);

        //Création d'un SimpleAdapter qui se chargera de mettre les items présents dans notre list (listItem) dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.list_of_movies,
                new String[] {"img","titre", "genre"}, new int[] {R.id.img,R.id.titre, R.id.genre});

        //On attribue à notre listView l'adapter que l'on vient de créer
        listMovies.setAdapter(mSchedule);
/*
        //Enfin on met un écouteur d'évènement sur notre listView
        listMovies.setOnItemClickListener(new OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //on récupère la HashMap contenant les infos de notre item (titre, description, img)
                HashMap<String, String> map = (HashMap<String, String>) listMovies.getItemAtPosition(position);
                //on créé une boite de dialogue
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                //on attribue un titre à notre boite de dialogue
                adb.setTitle("Sélection Item");
                //on insère un message à notre boite de dialogue, et ici on affiche le titre de l'item cliqué
                adb.setMessage("Votre choix : "+map.get("titre"));
                //on indique que l'on veut le bouton ok à notre boite de dialogue
                adb.setPositiveButton("Ok", null);
                //on affiche la boite de dialogue
                adb.show();
            }
        });*/

        listMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent appInfo = new Intent(MainActivity.this, DisplayMovie.class);
                startActivity(appInfo);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause(){
        super.onPause();
        Toast toast = Toast.makeText(this,"onPause", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast toast = Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Toast toast = Toast.makeText(this,"onRestart", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onResume(){
        super.onResume();
        Toast toast = Toast.makeText(this, "onResume", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button:
                Toast.makeText(this,"Bouton cliqué",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
