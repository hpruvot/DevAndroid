package com.example.megamovies.app.controller;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import com.example.megamovies.app.R;
import com.example.megamovies.app.model.DB;
import com.example.megamovies.app.model.Provider;


public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>{

    public final static String ID = "com.example.movies.MainActivity.ID";

    private int i = 0;
    private GridView listMovies;

    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.checkFirstUse();

        // On desactive la sortie du clavier
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Récupération de la gridview créée dans le fichier display_menu.xmlmenu.xml
        listMovies = (GridView) findViewById(R.id.listofmovies);

        // Listener pour la liste
        AdapterView.OnItemClickListener moviesListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DisplayMovieActivity.class);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, AddEditMovieActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkFirstUse() {
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            this.addMovies();
            settings.edit().putBoolean("my_first_time", false).commit();
        }
    }


    public void addMovies () {
        ContentValues movie = new ContentValues();
        movie.put(DB.Film.TITLE, "Fight Club");
        movie.put(DB.Film.GENDER, "Thriller, Drame");
        movie.put(DB.Film.IMAGE, R.drawable.list_fightclub);
        this.getContentResolver().insert(Provider.CONTENT_URI_MOVIE_ADD, movie);

        movie.put(DB.Film.TITLE, "Pulp Fiction");
        movie.put(DB.Film.GENDER, "Policier, Thriller");
        movie.put(DB.Film.IMAGE, R.drawable.list_pulpfiction);
        this.getContentResolver().insert(Provider.CONTENT_URI_MOVIE_ADD, movie);

        movie.put(DB.Film.TITLE, "The dark knight rises");
        movie.put(DB.Film.GENDER, "Action, Thriller");
        movie.put(DB.Film.IMAGE, R.drawable.list_thedarkknightrises);
        this.getContentResolver().insert(Provider.CONTENT_URI_MOVIE_ADD, movie);

        movie.put(DB.Film.TITLE, "Intouchables");
        movie.put(DB.Film.GENDER, "Comédie");
        movie.put(DB.Film.IMAGE, R.drawable.list_intouchables);
        this.getContentResolver().insert(Provider.CONTENT_URI_MOVIE_ADD, movie);

        movie.put(DB.Film.TITLE, "Gran Torino");
        movie.put(DB.Film.GENDER, "Drame, Thriller");
        movie.put(DB.Film.IMAGE, R.drawable.list_grantorino);
        this.getContentResolver().insert(Provider.CONTENT_URI_MOVIE_ADD, movie);

        movie.put(DB.Film.TITLE, "Forrest Gump");
        movie.put(DB.Film.GENDER, "Fantastique, Action");
        movie.put(DB.Film.IMAGE, R.drawable.list_forrestgump);
        this.getContentResolver().insert(Provider.CONTENT_URI_MOVIE_ADD, movie);

        movie.put(DB.Film.TITLE, "Django");
        movie.put(DB.Film.GENDER, "Western");
        movie.put(DB.Film.IMAGE, R.drawable.list_django);
        this.getContentResolver().insert(Provider.CONTENT_URI_MOVIE_ADD, movie);

        movie.put(DB.Film.TITLE, "Avatar");
        movie.put(DB.Film.GENDER, "Science-fiction");
        movie.put(DB.Film.IMAGE, R.drawable.list_avatar);
        this.getContentResolver().insert(Provider.CONTENT_URI_MOVIE_ADD, movie);

        movie.put(DB.Film.TITLE, "Le seigneur des anneaux");
        movie.put(DB.Film.GENDER, "Comédie, Romance");
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
