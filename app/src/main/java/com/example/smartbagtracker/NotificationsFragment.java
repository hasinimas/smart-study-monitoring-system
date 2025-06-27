package com.example.smartbagtracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class NotificationsFragment extends Fragment {

    private Switch switchPush, switchLCD, switchEnv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        switchPush = view.findViewById(R.id.switch_push);
        switchLCD = view.findViewById(R.id.switch_lcd);
        switchEnv = view.findViewById(R.id.switch_env);

        switchPush.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(getContext(), "Push Notifications: " + (isChecked ? "On" : "Off"), Toast.LENGTH_SHORT).show();
        });

        switchLCD.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(getContext(), "LCD Display: " + (isChecked ? "On" : "Off"), Toast.LENGTH_SHORT).show();
        });

        switchEnv.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(getContext(), "Environmental Alerts: " + (isChecked ? "On" : "Off"), Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}




