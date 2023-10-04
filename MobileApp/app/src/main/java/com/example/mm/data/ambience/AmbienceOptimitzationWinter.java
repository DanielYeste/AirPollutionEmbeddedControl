package com.example.mm.data.ambience;

public class AmbienceOptimitzationWinter implements AmbienceOptimitzation {
    @Override
    public int optimalTemperature(double temperature){
        return (temperature > 17.0 && temperature < 24.0)? 1 : 0;
    }

    @Override
    public int optimalHumidity(double humidity){
        return (humidity > 40.0 && humidity < 50.0)? 1 : 0;
    }
}
