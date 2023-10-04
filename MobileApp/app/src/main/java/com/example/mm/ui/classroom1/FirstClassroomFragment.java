package com.example.mm.ui.classroom1;

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
import com.example.mm.databinding.FragmentClassroom1Binding;
import com.example.mm.ui.classroom2.SecondClassroomViewModel;

public class FirstClassroomFragment extends Fragment {

    private FragmentClassroom1Binding binding;
    private final int classroomId = 1;
    private SecondClassroomViewModel slideshowViewModel;
    private ProgressBar loadingSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentClassroom1Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        slideshowViewModel = new ViewModelProvider(this).get(SecondClassroomViewModel.class);
        loadingSpinner = root.findViewById(R.id.loading_spinner);
        ImageButton temperatureButton = root.findViewById(R.id.temperature_button);
        temperatureButton.setOnClickListener(v -> showTemperaturePopup(v));

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        slideshowViewModel = new ViewModelProvider(this).get(SecondClassroomViewModel.class);
        observeLiveData();
        slideshowViewModel.loadData(classroomId);
    }




    private void observeLiveData() {
        slideshowViewModel.getTemperatureLiveData().observe(getViewLifecycleOwner(), temperature -> {
            TextView temperatureTextView = getView().findViewById(R.id.aula1Temperature);
            temperatureTextView.setText(temperature);
        });

        slideshowViewModel.getHumidityLiveData().observe(getViewLifecycleOwner(), humidity -> {
            TextView humidityTextView = getView().findViewById(R.id.aula1Humidity);
            humidityTextView.setText(humidity);
        });

        slideshowViewModel.getCoLiveData().observe(getViewLifecycleOwner(), co -> {
            TextView coTextView = getView().findViewById(R.id.aula1Co);
            coTextView.setText(co);
        });

        slideshowViewModel.getVocLiveData().observe(getViewLifecycleOwner(), voc -> {
            TextView vocTextView = getView().findViewById(R.id.aula1Voc);
            vocTextView.setText(voc);
        });

        slideshowViewModel.getTemperatureExclamationLiveData().observe(getViewLifecycleOwner(), showExclamation -> {
            ImageView temperatureExclamation = getView().findViewById(R.id.temperature_exclamation);
            if (showExclamation) {
                temperatureExclamation.setVisibility(View.VISIBLE);
            } else {
                temperatureExclamation.setVisibility(View.GONE);
            }
        });

        slideshowViewModel.getHumidityExclamationLiveData().observe(getViewLifecycleOwner(), showExclamation -> {
            ImageView humidityExclamation = getView().findViewById(R.id.humidity_exclamation);
            if (showExclamation) {
                humidityExclamation.setVisibility(View.VISIBLE);
            } else {
                humidityExclamation.setVisibility(View.GONE);
            }
        });

        slideshowViewModel.getCoExclamationLiveData().observe(getViewLifecycleOwner(), showExclamation -> {
            ImageView coExclamation = getView().findViewById(R.id.co_exclamation);
            if (slideshowViewModel.getCoExclamationLiveData().getValue() >= 1) {
                coExclamation.setVisibility(View.VISIBLE);
                if  (slideshowViewModel.getCoExclamationLiveData().getValue() != 2) {
                    coExclamation.setColorFilter(Color.YELLOW);
                }
            } else {
                coExclamation.setVisibility(View.GONE);
            }
        });

        slideshowViewModel.getVocExclamationLiveData().observe(getViewLifecycleOwner(), showExclamation -> {
            ImageView vocExclamation = getView().findViewById(R.id.voc_exclamation);
            if (slideshowViewModel.getVocExclamationLiveData().getValue() >= 1) {
                vocExclamation.setVisibility(View.VISIBLE);
                if  (slideshowViewModel.getCoExclamationLiveData().getValue() != 2) {
                    vocExclamation.setColorFilter(Color.YELLOW);
                }
            } else {
                vocExclamation.setVisibility(View.GONE);
            }
        });

        slideshowViewModel.getLoadingLiveData().observe(getViewLifecycleOwner(), isLoading -> {
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}