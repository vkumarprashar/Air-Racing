package com.marathon.airracing.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.marathon.airracing.R;
import com.marathon.airracing.adapters.CustomHistoryListViewAdapter;
import com.marathon.airracing.database.DBHelper;
import com.marathon.airracing.models.RaceModel;

import java.util.List;

public class HistoryFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        DBHelper dbHelper = new DBHelper(getContext());

        ListView listView = view.findViewById(R.id.history_list_view);
        List<RaceModel> raceModels = dbHelper.fetchRecords();
        CustomHistoryListViewAdapter adapter = new CustomHistoryListViewAdapter(getActivity(), raceModels);
        listView.setAdapter(adapter);
        return view;
    }
}