package com.devghost.epstopikforbangladeshi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findIds();
        loadHomeFrag();

        //bottom nav
        bottomNavigationView.setOnItemSelectedListener(item -> {

            if(item.getItemId()==R.id.home_nav){
                FragmentManager fragment = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragment.beginTransaction();
                fragmentTransaction.replace(R.id.mainFrag,new HomeFrag());
                fragmentTransaction.commit();
            }
            else if(item.getItemId()==R.id.voca_nav){
                FragmentManager fragment = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragment.beginTransaction();
                fragmentTransaction.replace(R.id.mainFrag,new VocabularyFrag());
                fragmentTransaction.commit();
            }



            return true;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.hide_menu){
            Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId()==R.id.pp_menu){
            Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadHomeFrag() {
        FragmentManager fragment = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragment.beginTransaction();
        fragmentTransaction.add(R.id.mainFrag,new VocabularyFrag());
        fragmentTransaction.commit();
    }

    private void findIds() {
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        webView=findViewById(R.id.webview);
    }

    @Override
    public void onBackPressed() {


        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            //additional code
            AlertDialog.Builder alertdialogbuilder;
            alertdialogbuilder=new AlertDialog.Builder(MainActivity.this);
            alertdialogbuilder.setIcon(R.drawable.icon);
            alertdialogbuilder.setTitle("Quit?");
            alertdialogbuilder.setMessage("Do u want to Quit?");
            alertdialogbuilder.setCancelable(false);

            alertdialogbuilder.setPositiveButton("yes", (dialogInterface, i) -> finish());

            alertdialogbuilder.setNegativeButton("no", (dialogInterface, i) -> dialogInterface.cancel());

            AlertDialog alertDialog = alertdialogbuilder.create();
            alertDialog.show();
        } else {
            getSupportFragmentManager().popBackStack();
        }


    }
}