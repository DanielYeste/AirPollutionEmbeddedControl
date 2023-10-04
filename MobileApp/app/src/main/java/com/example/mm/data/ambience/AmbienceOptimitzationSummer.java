package com.example.mm.data.ambience;

public class AmbienceOptimitzationSummer implements AmbienceOptimitzation{
    @Override
    public int optimalTemperature(double temperature){
        return (temperature > 23.0 && temperature < 27.0)? 1 : 0;
    }

    @Override
    public int optimalHumidity(double humidity){
        return (humidity > 45.0 && humidity < 60.0)? 1 : 0;
    }
}
