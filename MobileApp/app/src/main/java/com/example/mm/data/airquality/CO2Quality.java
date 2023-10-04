package com.example.mm.data.airquality;

/**
 * Implementation of AirQualityOptimization for CO2 pollution.
 */
public class CO2Quality implements AirQualityOptimization {
    private static final int[] CO2_RANGES = {500, 1000, 2500};

    /**
     * {@inheritDoc}
     */
    @Override
    public int calculateAirQuality(int pollution) {
        if (pollution < CO2_RANGES[0]) {
            return 0; // Normal
        } else if (pollution >= CO2_RANGES[1] && pollution <= CO2_RANGES[2]) {
            return 1; // A little uncomfortable
        } else if (pollution > CO2_RANGES[2]) {
            return 2; // Tired
        } else {
            return 3; // Unknown or out of range
        }
    }
}
