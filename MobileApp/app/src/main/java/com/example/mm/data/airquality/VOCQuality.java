package com.example.mm.data.airquality;

/**
 * Implementation of AirQualityOptimization for VOC pollution.
 */
public class VOCQuality implements AirQualityOptimization {
    private static final int[] VOC_RANGES = {50, 750, 6000};

    /**
     * {@inheritDoc}
     */
    @Override
    public int calculateAirQuality(int pollution) {
        if (pollution < VOC_RANGES[0]) {
            return 0; // Normal
        } else if (pollution >= VOC_RANGES[1] && pollution <= VOC_RANGES[2]) {
            return 1; // A little uncomfortable
        } else if (pollution >= VOC_RANGES[2]) {
            return 2; // Tired
        } else {
            return 3; // Unknown or out of range
        }
    }
}
