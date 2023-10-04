package com.example.mm.ui.classroom1;

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

public class FirstClassroomViewModel extends ViewModel {

    private final MutableLiveData<String> temperatureLiveData;
    private final MutableLiveData<String> humidityLiveData;
    private final MutableLiveData<String> coLiveData;
    private final MutableLiveData<String> vocLiveData;
    private final MutableLiveData<Boolean> loadingLiveData;
    private final MutableLiveData<Boolean> temperatureExclamationLiveData;

    private final MutableLiveData<Boolean> humidityExclamationLiveData;

    private final MutableLiveData<Integer> coExclamationLiveData;
    private final MutableLiveData<Integer> vocExclamationLiveData;

    /**
     * Constructor for the SlideshowViewModel.
     * Initializes LiveData objects.
     */
    public FirstClassroomViewModel() {
        temperatureLiveData = new MutableLiveData<>();
        humidityLiveData = new MutableLiveData<>();
        coLiveData = new MutableLiveData<>();
        vocLiveData = new MutableLiveData<>();
        loadingLiveData = new MutableLiveData<>();
        temperatureExclamationLiveData = new MutableLiveData<>();
        humidityExclamationLiveData = new MutableLiveData<>();
        coExclamationLiveData = new MutableLiveData<>();
        vocExclamationLiveData = new MutableLiveData<>();
    }

    /**
     * Returns the LiveData object for temperature data.
     *
     * @return LiveData object for temperature
     */
    public LiveData<String> getTemperatureLiveData() {
        return temperatureLiveData;
    }

    /**
     * Returns the LiveData object for humidity data.
     *
     * @return LiveData object for humidity
     */
    public LiveData<String> getHumidityLiveData() {
        return humidityLiveData;
    }

    /**
     * Returns the LiveData object for CO (Carbon Monoxide) data.
     *
     * @return LiveData object for CO
     */
    public LiveData<String> getCoLiveData() {
        return coLiveData;
    }

    /**
     * Returns the LiveData object for VOC (Volatile Organic Compounds) data.
     *
     * @return LiveData object for VOC
     */
    public LiveData<String> getVocLiveData() {
        return vocLiveData;
    }

    /**
     * Returns the LiveData object for loading state.
     *
     * @return LiveData object for loading state
     */
    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public MutableLiveData<Boolean> getTemperatureExclamationLiveData() {return temperatureExclamationLiveData;}

    public MutableLiveData<Boolean> getHumidityExclamationLiveData() {return humidityExclamationLiveData;}

    public MutableLiveData<Integer> getCoExclamationLiveData() {return coExclamationLiveData;}
    public MutableLiveData<Integer> getVocExclamationLiveData() {
        return vocExclamationLiveData;
    }

    /**
     * Loads data from the API based on the given classroom ID.
     *
     * @param classroomId the ID of the classroom
     */
    public void loadData(int classroomId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        loadingLiveData.setValue(true); // Show the loading spinner

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background work here
                ApiCalls apiCalls = ApiCalls.getInstance();
                final String temperature = apiCalls.adafruitRequestTemperature(classroomId);
                final String humidity = apiCalls.adafruitRequestHumidity(classroomId);
                final String co = apiCalls.adafruitRequestCo(classroomId);
                final String voc = apiCalls.adafruitRequestVoc(classroomId);
                int[] measuredData = ambienceCalculation(Double.parseDouble(temperature),
                        Double.parseDouble(humidity),
                        Integer.parseInt(co),
                        Integer.parseInt(voc));

                // Update UI on the main thread
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // UI Thread work here
                        temperatureLiveData.setValue(temperature);
                        humidityLiveData.setValue(humidity);
                        coLiveData.setValue(co);
                        vocLiveData.setValue(voc);
                        temperatureExclamationLiveData.setValue(measuredData[0] == 0);
                        humidityExclamationLiveData.setValue(measuredData[1] == 0);
                        coExclamationLiveData.setValue(measuredData[2]);
                        vocExclamationLiveData.setValue(measuredData[3]);

                        loadingLiveData.setValue(false); // Hide the loading spinner
                    }
                });
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