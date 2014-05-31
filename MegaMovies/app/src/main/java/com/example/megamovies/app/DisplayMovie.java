package com.example.megamovies.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by helenep on 25/05/2014.
 */
public class DisplayMovie extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_movie);
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
}
