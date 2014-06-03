package com.example.megamovies.app.controller;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.megamovies.app.R;
import com.example.megamovies.app.model.DB;
import com.example.megamovies.app.model.Provider;

public class AddEditMovieActivity extends ActionBarActivity {

    private EditText title;
    private EditText gender;

    private boolean isEditing = false;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_movie);


        Intent intent = getIntent();
        id = intent.getLongExtra(DisplayMovieActivity.ID, -1);

        title = (EditText) findViewById(R.id.movie_title_edit);
        gender = (EditText) findViewById(R.id.movie_gender_edit);
        
        if (id != -1)
            displayValues();

    }

    private void displayValues() {
        isEditing = true;

        //On récupère le film
        Cursor c = getContentResolver().query(Uri.parse(Provider.uriMovieID + "/" + id), null, null, null, null);
        c.moveToFirst();

        // titre
        title.setText(c.getString(c.getColumnIndex(DB.Film.TITLE)));
        // genre
        gender.setText(c.getString(c.getColumnIndex(DB.Film.GENDER)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_save) {

            ContentValues movie = new ContentValues();
            movie.put(DB.Film.TITLE, title.getText().toString());
            movie.put(DB.Film.GENDER, gender.getText().toString());

            if (isEditing) {
                this.getContentResolver().update(Uri.parse(Provider.uriMovieID + "/" + this.id), movie, null, null);
                Intent intent= new Intent(AddEditMovieActivity.this, DisplayMovieActivity.class);
                setResult(RESULT_OK, intent);
            }
            else
                this.getContentResolver().insert(Provider.CONTENT_URI_MOVIE_ADD, movie);

            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
