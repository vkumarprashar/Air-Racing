package com.marathon.airracing.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.marathon.airracing.R;
import com.marathon.airracing.Utils;
import com.marathon.airracing.database.DBHelper;
import com.marathon.airracing.models.RaceModel;

import java.util.ArrayList;
import java.util.List;

public class TicketReservationFragment extends Fragment {


    Spinner spinner;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket_reservation, container, false);
        RaceModel raceModel = Utils.raceModel;
        spinner = view.findViewById(R.id.seatTypeSpinner);
        List<String> stringList = new ArrayList<>();
        stringList.add("Standing: $20");
        stringList.add("Normal: $50");
        stringList.add("FirstClass: $100");
        stringList.add("VIP: $150");
        DBHelper dbHelper = new DBHelper(getContext());
        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, stringList);
        spinner.setAdapter(adapter);

        TextView raceName = view.findViewById(R.id.raceNameTC);
        raceName.setText(raceModel.getRaceName());
        TextView raceInfo = view.findViewById(R.id.raceInfoTC);
        raceInfo.setText(raceModel.getDate() + " @" + raceModel.getTime());

        EditText noOfSeats = view.findViewById(R.id.noOfSeats);
        Button button = view.findViewById(R.id.reserveBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seats = noOfSeats.getText().toString();
                raceModel.setTickets(seats);
                String price= "";
                long id = spinner.getSelectedItemId();
                if (id == 0) {
                    price = String.valueOf(Integer.valueOf(seats) * 20);
                    raceModel.setTicketType("Standing");
                }else if (id == 1) {
                    price = String.valueOf(Integer.valueOf(seats) * 50);
                    raceModel.setTicketType("Normal");
                } else if (id == 2) {
                    price = String.valueOf(Integer.valueOf(seats) * 100);
                    raceModel.setTicketType("Executive");
                } else if (id == 3) {
                    price = String.valueOf(Integer.valueOf(seats) * 150);
                    raceModel.setTicketType("VIP");
                }
                raceModel.setPrice(price);


                Utils.raceModel = raceModel;

                boolean check = dbHelper.insertRecord(raceModel);
                if (check) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frame_layout, new ReservedFragment());
                    ft.commit();
                } else {
                    Snackbar.make(view, "Data not saved, Try again!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frame_layout, new HomeFragment());
                    ft.commit();
                }

            }

        });

        return view;
    }


}