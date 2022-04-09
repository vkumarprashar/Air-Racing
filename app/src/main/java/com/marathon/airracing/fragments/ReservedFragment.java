package com.marathon.airracing.fragments;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import okhttp3.internal.Util;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.marathon.airracing.R;
import com.marathon.airracing.Utils;
import com.marathon.airracing.database.DBHelper;
import com.marathon.airracing.models.RaceModel;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;


public class ReservedFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserved, container, false);
        RaceModel raceModel = Utils.raceModel;
        TextView raceName = view.findViewById(R.id.raceNameReserved);
        raceName.setText(raceModel.getRaceName());

        TextView raceInfo = view.findViewById(R.id.raceInfoReserved);
        raceInfo.setText(raceModel.getDate() + " @" + raceModel.getTime());

        TextView seats = view.findViewById(R.id.seatTypeReserved);
        seats.setText(raceModel.getTicketType() + "-" + raceModel.getTickets() + " persons");

        TextView price = view.findViewById(R.id.totalPriceReserved);
        price.setText("$" + raceModel.getPrice());

        DBHelper dbHelper = new DBHelper(getContext());
        List<RaceModel> list = dbHelper.fetchRecords();
        Log.d("FETCH RECORD DB", "onCreateView: " + list.size());
        createUri();
        return view;
    }

    //Saving screesnhot of the ticket
    private void createUri() {

        View mainView = getActivity().getWindow().getDecorView();
        mainView.setDrawingCacheEnabled(true);
        mainView.buildDrawingCache();
        Bitmap bitmap = mainView.getDrawingCache();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        String imagePath = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, UUID.randomUUID().toString(), ".jpg");
        if (imagePath!=null) {
            Snackbar.make(mainView, "Ticket saved into your gallery!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}