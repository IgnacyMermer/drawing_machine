package com.e.maszynalosujca;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class nowy extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowy);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        navigationView.setCheckedItem(R.id.nav_gallery);
        ImageButton buttonBar = findViewById(R.id.buttonBar2);
        buttonBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        final ListView lv= findViewById(R.id.listView);
        final ArrayList<Integer> lista = new ArrayList<>();
        final ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(nowy.this, android.R.layout.simple_list_item_multiple_choice, lista);
        Button button = findViewById(R.id.button);
        final EditText pole = findViewById(R.id.poleWielkosc);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String poleTekst = pole.getText().toString();
                int a = Integer.parseInt(poleTekst);
                lista.add(a);
                pole.setText("");
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
        });
        lv.setAdapter(adapter2);
        Button buttonUsun = findViewById(R.id.buttonUsun);
        buttonUsun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray positionChecked = lv.getCheckedItemPositions();
                for(int i=lv.getCount()-1;i>=0;i--){
                    if(positionChecked.get(i)){
                        adapter2.remove(lista.get(i));
                    }
                }
                positionChecked.clear();
                adapter2.notifyDataSetChanged();
                if((lista.size() > 6)&&Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    lv.setNestedScrollingEnabled(true);
                }
                else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        lv.setNestedScrollingEnabled(false);
                    }
                }
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.nav_gallery){
                    drawer.closeDrawer(Gravity.LEFT);
                }
                else if(item.getItemId()==R.id.nav_home){
                    Intent i = new Intent(nowy.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("lista", lista);
                    startActivity(i);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
