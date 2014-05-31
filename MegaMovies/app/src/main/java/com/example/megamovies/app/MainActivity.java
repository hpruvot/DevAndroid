package com.example.megamovies.app;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>{

    public final static String ID = "com.example.movies.MainActivity.ID";

    private int i = 0;
    private GridView listMovies;

    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On desactive la sortie du clavier
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Récupération de la gridview créée dans le fichier main.xml
        listMovies = (GridView) findViewById(R.id.listofmovies);

        this.addMovies();

        // Listener pour la liste
        AdapterView.OnItemClickListener moviesListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DisplayMovie.class);
                intent.putExtra(ID, id);
                startActivity(intent);
            }
        };

        adapter = new SimpleCursorAdapter (this, R.layout.item_movie_list, null,
                new String[] {DB.Film.IMAGE, DB.Film.TITLE, DB.Film.GENDER},
                new int[] {R.id.img, R.id.title, R.id.gender}, 0);
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return getContentResolver().query(Provider.CONTENT_URI_SEARCH, null, constraint.toString(), null, null);
            }
        });
        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder(){
            public boolean setViewValue(View view, Cursor cursor, int columnIndex){
                if(view.getId() == R.id.img){
                    if (cursor.getInt(columnIndex) != -1){
                        ((ImageView)view).setImageResource(cursor.getInt(columnIndex));
                    }
                    else
                        // TODO remplacer l'image par defaut
                        ((ImageView)view).setImageResource(R.drawable.list_avatar);
                    return true;
                }

                return false;
            }
        });


        getLoaderManager().initLoader(0, null, this);


        //On attribue à notre listView l'adapter que l'on vient de créer
        listMovies.setAdapter(adapter);


        //On écoute la liste
        listMovies.setOnItemClickListener(moviesListener);


        //Reecherche

        // On récupère la barre de recherche
        EditText search = (EditText) findViewById(R.id.search);

        // On créé un ecouteur pour la recherche
        TextWatcher searchTextWatcher = new TextWatcher(){

            public void afterTextChanged(Editable arg0) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // On met à jour la liste d'item
                adapter.getFilter().filter(s.toString());
            }

        };

        // On lie la recherche au lister
        search.addTextChangedListener(searchTextWatcher);
    }


    private void addMovies() {
        /*
        map.put("titre", "Avatar");
        map.put("genre", "Science-fiction");
        map.put("img", String.valueOf(R.drawable.list_avatar));

        map.put("titre", "Django");
        map.put("genre", "Western");
        map.put("img", String.valueOf(R.drawable.list_django));

        map.put("titre", "Forrest Gump");
        map.put("genre", "Comédie, Romance");
        map.put("img", String.valueOf(R.drawable.list_forrestgump));

        map.put("titre", "Gran Torino");
        map.put("genre", "Drame, Thriller");
        map.put("img", String.valueOf(R.drawable.list_grantorino));

        map.put("titre", "Intouchables");
        map.put("genre", "Comédie");
        map.put("img", String.valueOf(R.drawable.list_intouchables));

        map.put("titre", "The dark knight rises");
        map.put("genre", "Action, Thriller");
        map.put("img", String.valueOf(R.drawable.list_thedarkknightrises));

        map.put("titre", "Pulp Fiction");
        map.put("genre", "Policier, Thriller");
        map.put("img", String.valueOf(R.drawable.list_pulpfiction));
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("titre", "Fight Club");
        map.put("genre", "Thriller, Drame");
        map.put("img", String.valueOf(R.drawable.list_fightclub));*/

        ContentValues movie = new ContentValues();
        movie.put(DB.Film.TITLE, "Le seigneur des anneaux");
        movie.put(DB.Film.GENDER, "Fantastique, Action");
        movie.put(DB.Film.IMAGE, R.drawable.list_leseigneurdesanneaux);
        this.getContentResolver().insert(Provider.CONTENT_URI_MOVIE_ADD, movie);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Provider.CONTENT_URI_MOVIES, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
