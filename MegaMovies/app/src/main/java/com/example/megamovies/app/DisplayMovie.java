package com.example.megamovies.app;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by helenep on 25/05/2014.
 */
public class DisplayMovie extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_movie);

        Intent intent = getIntent();
        final long id = intent.getLongExtra(MainActivity.ID, -1);

        this.display(id);
    }

    private void display(long id) {

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
        //getMenuInflater().inflate(R.menu.main, menu);
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

}
