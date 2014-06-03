package com.example.megamovies.app.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.megamovies.app.R;
import com.example.megamovies.app.model.DB;
import com.example.megamovies.app.model.Provider;

/**
 * Created by helenep on 25/05/2014.
 */
public class DisplayMovieActivity extends Activity {

    public final static String ID = "com.example.movies.DisplayMovieActivity.ID";
    public final static int EDITING = 1;

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_movie);

        Intent intent = getIntent();
        id = intent.getLongExtra(MainActivity.ID, -1);

        if (id != -1)
            this.display();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == EDITING){
            if(resultCode == RESULT_OK){
                display();
                // Toast d'information
                Toast.makeText(DisplayMovieActivity.this, "Film modifié", Toast.LENGTH_SHORT).show();
            }
            else {
                // Toast d'information
                Toast.makeText(DisplayMovieActivity.this, "Vous avez annulé les modifications", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void display() {

        //On récupère le film
        Cursor c = getContentResolver().query(Uri.parse(Provider.uriMovieID + "/" + id), null, null, null, null);
        c.moveToFirst();

        // titre
        TextView title = (TextView) findViewById(R.id.movie_title);
        title.setText(c.getString(c.getColumnIndex(DB.Film.TITLE)));

        // faire de meme avec les autres champs


        // On ferme le cursor
        c.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setNegativeButton("Non", null)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DisplayMovieActivity.this.getContentResolver().delete(Uri.parse(Provider.uriMovieID + '/' + String.valueOf(getId())), null, null);
                            finish();
                        }
                    })
                    .setMessage("Voulez vous vraiment supprimer ce film ?")
                    .setTitle("Suppresion")
                    .show();
            return true;
        } else if (id == R.id.action_edit) {
            Intent intent = new Intent(DisplayMovieActivity.this, AddEditMovieActivity.class);
            intent.putExtra(ID, this.id);
            startActivityForResult(intent, EDITING);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private long getId() { return id; }

}
