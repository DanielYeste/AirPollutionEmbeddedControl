package com.example.mm.ui.classroom2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mm.R;
import com.example.mm.databinding.FragmentClassroom2Binding;

public class SecondClassroomFragment extends Fragment {

    private FragmentClassroom2Binding binding;
    private final int classroomId = 2;
    private SecondClassroomViewModel secondClassroomViewModel;
    private ProgressBar loadingSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentClassroom2Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        secondClassroomViewModel = new ViewModelProvider(this).get(SecondClassroomViewModel.class);
        loadingSpinner = root.findViewById(R.id.loading_spinner);
        ImageButton temperatureButton = root.findViewById(R.id.temperature_button);
        temperatureButton.setOnClickListener(v -> showTemperaturePopup(v));

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        secondClassroomViewModel = new ViewModelProvider(this).get(SecondClassroomViewModel.class);
        observeLiveData();
        secondClassroomViewModel.loadData(classroomId);
    }




    private void observeLiveData() {
        secondClassroomViewModel.getTemperatureLiveData().observe(getViewLifecycleOwner(), temperature -> {
            TextView temperatureTextView = getView().findViewById(R.id.aula2Temperature);
            temperatureTextView.setText(temperature);
        });

        secondClassroomViewModel.getHumidityLiveData().observe(getViewLifecycleOwner(), humidity -> {
            TextView humidityTextView = getView().findViewById(R.id.aula2Humidity);
            humidityTextView.setText(humidity);
        });

        secondClassroomViewModel.getCoLiveData().observe(getViewLifecycleOwner(), co -> {
            TextView coTextView = getView().findViewById(R.id.aula2Co);
            coTextView.setText(co);
        });

        secondClassroomViewModel.getVocLiveData().observe(getViewLifecycleOwner(), voc -> {
            TextView vocTextView = getView().findViewById(R.id.aula2Voc);
            vocTextView.setText(voc);
        });

        secondClassroomViewModel.getTemperatureExclamationLiveData().observe(getViewLifecycleOwner(), showExclamation -> {
            ImageView temperatureExclamation = getView().findViewById(R.id.temperature_exclamation);
            if (showExclamation) {
                temperatureExclamation.setVisibility(View.VISIBLE);
            } else {
                temperatureExclamation.setVisibility(View.GONE);
            }
        });

        secondClassroomViewModel.getHumidityExclamationLiveData().observe(getViewLifecycleOwner(), showExclamation -> {
            ImageView humidityExclamation = getView().findViewById(R.id.humidity_exclamation);
            if (showExclamation) {
                humidityExclamation.setVisibility(View.VISIBLE);
            } else {
                humidityExclamation.setVisibility(View.GONE);
            }
        });

        secondClassroomViewModel.getCoExclamationLiveData().observe(getViewLifecycleOwner(), showExclamation -> {
            ImageView coExclamation = getView().findViewById(R.id.co_exclamation);
            if (secondClassroomViewModel.getCoExclamationLiveData().getValue() >= 1) {
                coExclamation.setVisibility(View.VISIBLE);
                if  (secondClassroomViewModel.getCoExclamationLiveData().getValue() != 2) {
                    coExclamation.setColorFilter(Color.YELLOW);
                }
            } else {
                coExclamation.setVisibility(View.GONE);
            }
        });

        secondClassroomViewModel.getVocExclamationLiveData().observe(getViewLifecycleOwner(), showExclamation -> {
            ImageView vocExclamation = getView().findViewById(R.id.voc_exclamation);
            if (secondClassroomViewModel.getVocExclamationLiveData().getValue() >= 1) {
                vocExclamation.setVisibility(View.VISIBLE);
                if  (secondClassroomViewModel.getCoExclamationLiveData().getValue() != 2) {
                    vocExclamation.setColorFilter(Color.YELLOW);
                }
            } else {
                vocExclamation.setVisibility(View.GONE);
            }
        });

        secondClassroomViewModel.getLoadingLiveData().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                loadingSpinner.setVisibility(View.VISIBLE);
                // Hide other views if needed
                // Example: temperatureTextView.setVisibility(View.GONE);
            } else {
                loadingSpinner.setVisibility(View.GONE);
                // Show other views if needed
                // Example: temperatureTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showTemperaturePopup(View v) {
        /*
        * Not implemented at the end
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupLayout = inflater.inflate(R.layout.popup_temperature_info, null);
        PopupWindow popupWindow = new PopupWindow(
                popupLayout,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setElevation(20);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        */
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
