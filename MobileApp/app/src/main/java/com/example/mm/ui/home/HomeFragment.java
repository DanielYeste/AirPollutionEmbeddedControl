package com.example.mm.ui.home;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mm.R;
import com.example.mm.databinding.FragmentHomeBinding;

import java.util.Locale;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private ProgressBar loadingSpinner;

    private String currentLanguage = "default";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        loadingSpinner = root.findViewById(R.id.loading_spinner);

        binding.cataloniaFlag.setOnClickListener(this);
        binding.spainFlag.setOnClickListener(this);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        observeLiveData();
        homeViewModel.loadData();
    }

    private void observeLiveData() {
        homeViewModel.getClassroom1Info().observe(getViewLifecycleOwner(), showDanger -> {
            ImageView dangerInfo = getView().findViewById(R.id.classroomOneInfoIndicator);
            if (showDanger) {
                dangerInfo.setColorFilter(Color.RED);
            } else {
                dangerInfo.setColorFilter(Color.GREEN);
            }
        });

        homeViewModel.getClassroom2Info().observe(getViewLifecycleOwner(), showDanger -> {
            ImageView dangerInfo = getView().findViewById(R.id.classroomTwoInfoIndicator);
            if (showDanger) {
                dangerInfo.setColorFilter(Color.RED);
            } else {
                dangerInfo.setColorFilter(Color.GREEN);
            }
        });

        homeViewModel.getLoadingLiveData().observe(getViewLifecycleOwner(), isLoading -> {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cataloniaFlag) {
            if (!currentLanguage.equals("catalan")) {
                setLanguage("ca");
                currentLanguage = "catalan";
            }
        } else if (v.getId() == R.id.spainFlag) {
            if (!currentLanguage.equals("spanish")) {
                setLanguage("es");
                currentLanguage = "spanish";
            }
        }
    }

    private void setLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getResources().getConfiguration().setTo(configuration);

        requireActivity().recreate();
    }
}
