package com.example.mm.data.airquality;

/**
 * Interface representing air quality optimization.
 */
public interface AirQualityOptimization {
    /**
     * Determines the air pollution quality based on the given pollution level.
     *
     * @param pollution The pollution level to evaluate.
     * @return An integer representing the air pollution quality.
     */
    int calculateAirQuality(int pollution);
}
