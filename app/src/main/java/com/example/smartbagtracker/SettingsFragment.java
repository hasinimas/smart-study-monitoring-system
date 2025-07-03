package com.example.smartbagtracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private Switch wifiSwitch, bluetoothSwitch, switchPush, switchLCD, switchEnv;
    private SeekBar studyDurationSeekBar;
    private TextView durationText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        wifiSwitch = view.findViewById(R.id.switch_wifi);
        bluetoothSwitch = view.findViewById(R.id.switch_bluetooth);
        studyDurationSeekBar = view.findViewById(R.id.seek_study_duration);
        durationText = view.findViewById(R.id.text_duration_value);

        switchPush = view.findViewById(R.id.switch_push);
        switchLCD = view.findViewById(R.id.switch_lcd);
        switchEnv = view.findViewById(R.id.switch_env);

        // --- WiFi switch action ---
        wifiSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(getContext(), "WiFi: " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
        });

        // --- Bluetooth switch action ---
        bluetoothSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(getContext(), "Bluetooth: " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
        });

        // --- Study Duration Slider ---
        studyDurationSeekBar.setMax(120); // in minutes
        studyDurationSeekBar.setProgress(90); // default

        updateDurationText(90); // show 1.5 hours initially

        studyDurationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateDurationText(progress);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

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

    private void updateDurationText(int minutes) {
        float hours = minutes / 60f;
        durationText.setText(String.format("%.1f hours", hours));
    }
}



