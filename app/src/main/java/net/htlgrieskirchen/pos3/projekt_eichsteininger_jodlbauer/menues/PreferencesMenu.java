package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.InflaterHelper;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Static_Access;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class PreferencesMenu extends AppCompatActivity {
    private SharedPreferences prefs;
    private LinearLayout linearLayout;
    private String path = "/sdcard/project_eichsteininger_jodlbauer/preferences.txt";
    //private SharedPreferences.OnSharedPreferenceChangeListener preferencesChangeListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_menu);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new MySettingsFragment())
                .commit();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        linearLayout=findViewById(R.id.ll_pref);
        //preferencesChangeListener = this::onSharedPreferenceChanged;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.headermenu, menu);
        ActionBar a = getSupportActionBar();
        InflaterHelper.inflateLayout(a,linearLayout);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showPrefs();
        savePreferences(Static_Access.mode);
        int id = item.getItemId();
        if (id == R.id.onestepback) {
            startActivity(new Intent(this, MainActivity.class));//return to the Intent you came from
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void showPrefs() {
        String mode = (prefs.getString("mode", "light"));
        Static_Access.mode=mode;
    }
//does not work
//    private void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        Toast.makeText(this, "Hello There", Toast.LENGTH_SHORT).show();
//        String sValue = sharedPreferences.getString(key, "");
//        Toast.makeText(this, key + " new Value: " + sValue, Toast.LENGTH_LONG).show();
//    }
    private void savePreferences(String toWrite)
    {
        try {
            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(path)));
            out.print(toWrite);
            out.flush();
            out.close();
        } catch (Exception e) {
        }
    }
}
