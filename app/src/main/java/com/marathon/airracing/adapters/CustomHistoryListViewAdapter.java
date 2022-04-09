package com.marathon.airracing.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.marathon.airracing.R;
import com.marathon.airracing.models.RaceModel;

import java.util.List;

public class CustomHistoryListViewAdapter extends BaseAdapter {
    Activity activity;
    List<RaceModel> list;

    public CustomHistoryListViewAdapter(Activity activity, List<RaceModel> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public RaceModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.list_view_racing, null, false);
        RaceModel raceModel = getItem(position);
        TextView raceName = view.findViewById(R.id.raceNameTextView);
        raceName.setText(raceModel.getRaceName());

        TextView raceType = view.findViewById(R.id.raceType);
        raceType.setText("Qualifying - " +raceModel.getTicketType() + " - " + raceModel.getTickets() + " persons");

        TextView raceInfo = view.findViewById(R.id.raceInfo);
        raceInfo.setText(raceModel.getDate() + " @" + raceModel.getTime());

        return view;
    }
}
