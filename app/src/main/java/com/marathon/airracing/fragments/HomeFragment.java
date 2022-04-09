package com.marathon.airracing.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.android.material.snackbar.Snackbar;
import com.marathon.airracing.R;
import com.marathon.airracing.Utils;
import com.marathon.airracing.adapters.CustomHomeListViewAdapter;
import com.marathon.airracing.databinding.FragmentHomeBinding;
import com.marathon.airracing.models.RaceModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    List<RaceModel> list = new ArrayList<>();
    ListView listView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = getLayoutInflater().inflate(R.layout.fragment_home, container, false);

        listView = view.findViewById(R.id.list_view_home);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RaceModel raceModel = list.get(position);
                Utils.raceModel = raceModel;
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout, new TicketReservationFragment());
                ft.commit();
            }
        });

        getData(view);
        return view;
    }

    private void getData(View view) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url("http://ergast.com/api/f1/current.json")
                .method("GET", null)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Snackbar.make(view, "Cannot fetch Data", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONObject base = new JSONObject(response.body().string());
                    JSONObject mrData = base.getJSONObject("MRData");
                    JSONObject raceTable = mrData.getJSONObject("RaceTable");
                    JSONArray array = raceTable.getJSONArray("Races");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        list.add(new RaceModel(object.getString("raceName"),
                                object.getString("date"),
                                object.getString("time")));

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                CustomHomeListViewAdapter adapter = new CustomHomeListViewAdapter(getActivity(), list);
                                listView.setAdapter(adapter);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}