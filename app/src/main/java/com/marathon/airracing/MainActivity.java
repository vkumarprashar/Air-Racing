package com.marathon.airracing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.marathon.airracing.databinding.ActivityMainBinding;
import com.marathon.airracing.fragments.HistoryFragment;
import com.marathon.airracing.fragments.HomeFragment;
import com.marathon.airracing.fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;
        toggle = new ActionBarDrawerToggle(this, drawer, binding.appBarMain.toolbar,R.string.open, R.string.close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_home: callFragment(new HomeFragment());
                        drawer.close();
                        break;

                    case R.id.nav_login: callFragment(new LoginFragment());
                        drawer.close();
                        break;

                    case R.id.nav_signup: callFragment(new LoginFragment());
                        drawer.close();
                        break;

                    case R.id.nav_my_reservations: callFragment(new HistoryFragment());
                        drawer.close();
                        break;

                    case R.id.nav_logout: logout();
                                            drawer.close();
                                            break;

                }
                return true;
            }
        });

        checkLogin();
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }
    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

        Menu menu = navigationView.getMenu();
        MenuItem itemreservation = menu.findItem(R.id.nav_my_reservations);
        itemreservation.setVisible(false);
        MenuItem itemLogout = menu.findItem(R.id.nav_logout);
        itemLogout.setVisible(false);

        MenuItem item = menu.findItem(R.id.nav_login);
        item.setVisible(true);
        MenuItem itemsignup = menu.findItem(R.id.nav_signup);
        itemsignup.setVisible(true);
        callFragment(new HomeFragment());
    }


    public void checkLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("phoneNumber", null);
        Menu menu = navigationView.getMenu();
        if (TextUtils.isEmpty(phoneNumber)) {
            MenuItem itemreservation = menu.findItem(R.id.nav_my_reservations);
            itemreservation.setVisible(false);
            MenuItem itemLogout = menu.findItem(R.id.nav_logout);
            itemLogout.setVisible(false);

            MenuItem item = menu.findItem(R.id.nav_login);
            item.setVisible(true);
            MenuItem itemsignup = menu.findItem(R.id.nav_signup);
            itemsignup.setVisible(true);
        } else {
            MenuItem item = menu.findItem(R.id.nav_login);
            item.setVisible(false);
            MenuItem itemsignup = menu.findItem(R.id.nav_signup);
            itemsignup.setVisible(false);

            MenuItem itemreservation = menu.findItem(R.id.nav_my_reservations);
            itemreservation.setVisible(true);
            MenuItem itemLogout = menu.findItem(R.id.nav_logout);
            itemLogout.setVisible(true);
        }

        callFragment(new HomeFragment());
    }

    private void callFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout, fragment);
        ft.commit();
    }
}