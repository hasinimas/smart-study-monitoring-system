package com.example.smartbagtracker;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.firebase.database.*;


public class HomeFragment extends Fragment {

    private TextView temperatureTextView, humidityTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton btnProfile = view.findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = requireActivity().getSupportFragmentManager();
                ProfileDialogFragment dialog = new ProfileDialogFragment();
                dialog.show(fm, "profile_dialog");
            }
        });

        temperatureTextView = view.findViewById(R.id.temperatureTextView);  //temp-value
        humidityTextView  = view.findViewById(R.id.humidityTextView); //Humidity-value
        DatabaseReference tempRef = FirebaseDatabase.getInstance().getReference("sensor/temperature");
        DatabaseReference humidityRef = FirebaseDatabase.getInstance().getReference("sensor/humidity");

        // to Read Temperature from Firebase
        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Float temp = snapshot.getValue(Float.class);
                if (temp != null) {
                    temperatureTextView.setText(String.format("%.1f°C", temp));
                } else {
                    temperatureTextView.setText("--°C");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Firebase", "Error reading temperature", error.toException());
            }
        });

        // to Read Humidity sensor from Firebase
        humidityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Float humidity = snapshot.getValue(Float.class);
                if (humidity != null) {
                    humidityTextView.setText(String.format("%.1f%%", humidity));
                } else {
                    humidityTextView.setText("--%");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Firebase", "Error reading humidity", error.toException());
            }
        });


        return view;
    }

}


