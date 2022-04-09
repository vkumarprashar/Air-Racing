package com.marathon.airracing.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

import com.google.android.material.navigation.NavigationView;
import com.marathon.airracing.MainActivity;
import com.marathon.airracing.R;

public class LoginFragment extends Fragment {

    VideoView videoView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //set video view
        VideoView videoView = (VideoView) view.findViewById(R.id.video_view);
        String uriPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.video;
        Log.d("URI PATH", "onCreate: {}"+ uriPath);
        Uri uri2 = Uri.parse(uriPath);
        videoView.setVideoURI(uri2);
        videoView.requestFocus();
        videoView.start();

        Button btn = view.findViewById(R.id.loginBtn);
        btn.setOnClickListener(v -> {
            EditText phoneNumber = view.findViewById(R.id.phone_number);
            String phone = phoneNumber.getText().toString();

            EditText pwd = view.findViewById(R.id.password);
            if (TextUtils.isEmpty(phone)) {
                Log.d("Phone Number", "onCreateView: " + phone);
                phoneNumber.setError("Phone Number cannot be empty");
                phoneNumber.requestFocus();
                return;
            }
            if (phone.length()<10 || phone.length()>11) {
                phoneNumber.setError("Phone Number length does not match");
                phoneNumber.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(pwd.getText().toString())) {
                pwd.setError("Password cannot be empty");
                return;
            }
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("phoneNumber", phone);
            editor.commit();

            changeMenuItem();

            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame_layout, new HomeFragment());
            ft.commit();
        });

        return view;
    }

    //Changing Side nav menu items
    private void changeMenuItem() {
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem item = menu.findItem(R.id.nav_login);
        item.setVisible(false);
        MenuItem itemsignup = menu.findItem(R.id.nav_signup);
        itemsignup.setVisible(false);

        MenuItem itemreservation = menu.findItem(R.id.nav_my_reservations);
        itemreservation.setVisible(true);
        MenuItem itemLogout = menu.findItem(R.id.nav_logout);
        itemLogout.setVisible(true);
    }
}