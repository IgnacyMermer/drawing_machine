package com.e.maszynalosujca;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        final ListView lv = findViewById(R.id.listView);
        final ArrayList<Integer> lista = new ArrayList<>();
        final ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(MainActivity.this, android.R.layout.simple_list_item_1, lista);
        lv.setAdapter(adapter2);
        final CheckBox checkBox = findViewById(R.id.opcja);
        ArrayList przyslanaLista = null;
        try {
            przyslanaLista = intent.getIntegerArrayListExtra("lista");
        }
        catch (Exception ex){
            przyslanaLista =null;
        }
        final EditText wielkosc = findViewById(R.id.poleWielkosc);
        Button kliknij = findViewById(R.id.button);
        final int[] ilosc = new int[1];
        final ArrayList<Integer> liczbyWylos = new ArrayList<>();
        final ArrayList finalPrzyslanaLista = przyslanaLista;
        kliknij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    lista.clear();
                    int wielkoscInt = 0;
                    liczbyWylos.clear();
                    if(!wielkosc.getText().toString().equals("")){
                        wielkoscInt = Integer.parseInt(wielkosc.getText().toString());
                    }
                    if(ilosc[0]<wielkoscInt){
                        throw new Exception("lala");
                    }
                    final int finalWielkoscInt = wielkoscInt;
                    if(finalPrzyslanaLista ==null||(!checkboks)){
                        for(int i = 0; i<= finalWielkoscInt-1; i++){
                            int liczba=(int)(Math.random()*ilosc[0]+1);
                            if((!liczbyWylos.contains(liczba))){
                                liczbyWylos.add(liczba);
                                lista.add(liczba);
                            }
                            else{
                                i--;
                            }
                            if(i==ilosc[0]){
                                liczbyWylos.clear();

                            }
                        }
                    }
                    else{
                        int y=finalWielkoscInt;
                        for(int i=0;i<=y-1;i++){
                            if(i<=finalPrzyslanaLista.size()-1){
                                int d = (Integer)finalPrzyslanaLista.get(i);
                                if(d<=ilosc[0]){
                                    lista.add(d);
                                    liczbyWylos.add(d);
                                }
                                else {
                                    y++;
                                }
                            }
                            else{
                                finalPrzyslanaLista.clear();
                                int liczba=(int)(Math.random()*ilosc[0]+1);
                                if((!liczbyWylos.contains(liczba))){
                                    liczbyWylos.add(liczba);
                                    lista.add(liczba);
                                }
                                else{
                                    i--;
                                }
                                if(i==ilosc[0]){
                                    liczbyWylos.clear();

                                }
                            }
                            if(i==finalPrzyslanaLista.size()-1){
                                finalPrzyslanaLista.clear();
                            }
                        }
                    }
                    adapter2.notifyDataSetChanged();
                    if((lista.size() > 6)&& Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                        lv.setNestedScrollingEnabled(true);
                    }
                    else{
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            lv.setNestedScrollingEnabled(false);
                        }
                    }
                }
                catch (Exception ex){
                    Log.i("wynik2", ex.getMessage().toString());
                }
            }
        });
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(this, R.array.liczby, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                ilosc[0] = Integer.parseInt(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        drawer = findViewById(R.id.drawerGlowne_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        /*mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();*/
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        ImageButton buttonBar = findViewById(R.id.buttonBar);
        buttonBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
    }
    Boolean checkboks=false;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nowy, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                drawer.closeDrawer(Gravity.LEFT);
                break;
            case R.id.nav_gallery:
                Intent i = new Intent(MainActivity.this, nowy.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
                break;
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.opcja){
            if(item.isChecked()){
                item.setChecked(false);
                checkboks = false;
            }
            else{
                item.setChecked(true);
                checkboks = true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
