package com.example.mm.ui.home;

import android.icu.util.Calendar;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mm.data.airquality.CO2Quality;
import com.example.mm.data.airquality.VOCQuality;
import com.example.mm.data.ambience.AmbienceOptimitzationSummer;
import com.example.mm.data.ambience.AmbienceOptimitzationWinter;
import com.example.mm.data.api.ApiCalls;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<Boolean> loadingLiveData;
    private final MutableLiveData<Boolean> classroom1Info;
    private final MutableLiveData<Boolean> classroom2Info;
    /**
     * Constructor for the SlideshowViewModel.
     * Initializes LiveData objects.
     */
    public HomeViewModel() {

        loadingLiveData = new MutableLiveData<>();
        classroom1Info = new MutableLiveData<>();
        classroom2Info = new MutableLiveData<>();
    }


    /**
     * Returns the LiveData object for loading state.
     *
     * @return LiveData object for loading state
     */
    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public MutableLiveData<Boolean> getClassroom1Info() {return classroom1Info;}

    public MutableLiveData<Boolean> getClassroom2Info() {return classroom2Info;}

    public void loadData() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        loadingLiveData.setValue(true); // Show the loading spinner

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background work here
                ApiCalls apiCalls = ApiCalls.getInstance();
                int[] measuredData = new int[4];
                int maxMeasuredValue = Integer.MIN_VALUE;

                for (int i = 1; i <= 2; i++) {
                    final String temperature = apiCalls.adafruitRequestTemperature(i);
                    final String humidity = apiCalls.adafruitRequestHumidity(i);
                    final String co = apiCalls.adafruitRequestCo(i);
                    final String voc = apiCalls.adafruitRequestVoc(i);

                    int[] currentMeasuredData = ambienceCalculation(Double.parseDouble(temperature),
                            Double.parseDouble(humidity),
                            Integer.parseInt(co),
                            Integer.parseInt(voc));

                    for (int j = 0; j < currentMeasuredData.length; j++) {
                        measuredData[j] += currentMeasuredData[j];
                        maxMeasuredValue = Math.max(maxMeasuredValue, measuredData[j]);
                    }

                    int finalI = i;
                    if (maxMeasuredValue >= 1) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                // UI Thread work here
                                if (finalI == 1) {
                                    classroom1Info.setValue(true);
                                } else {
                                    classroom2Info.setValue(true);
                                }
                            }
                        });
                    }else{
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                // UI Thread work here
                                if (finalI == 1) {
                                    classroom1Info.setValue(false);
                                } else {
                                    classroom2Info.setValue(false);
                                }
                            }
                        });
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // UI Thread work here
                            loadingLiveData.setValue(false); // Hide the loading spinner

                            // Use the measured data here
                            // maxMeasuredValue contains the highest value found
                        }
                    });
                }
            }
        });
    }




    private int[] ambienceCalculation(double temperature, double humidity, int co, int voc){
        int[] dataValues = new int[4];
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        CO2Quality co2Quality = new CO2Quality();
        VOCQuality vocQuality = new VOCQuality();
        if (currentMonth > 4 && currentMonth < 10) {
            AmbienceOptimitzationSummer ambience = new AmbienceOptimitzationSummer();
            dataValues[0] = ambience.optimalTemperature(temperature);
            dataValues[1] = ambience.optimalHumidity(humidity);
        } else {
            AmbienceOptimitzationWinter ambience = new AmbienceOptimitzationWinter();
            dataValues[0] = ambience.optimalTemperature(temperature);
            dataValues[1] = ambience.optimalHumidity(humidity);
        }
        dataValues[2] = co2Quality.calculateAirQuality(co);
        dataValues[3] = vocQuality.calculateAirQuality(voc);


        return dataValues;
    }

}