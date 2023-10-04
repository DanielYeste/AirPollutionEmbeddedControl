package com.example.mm.data.api;

public class ApiInfo {
    private static ApiInfo instance;
    private String username = "Yestedan";
    private String feedKey = "aio_JvZJ70uIwNqGKkvqAgk22N00CVcp";

    private String baseApiFeedName = "aula";

    private String baseUrl = "https://io.adafruit.com/api/v2/ /feeds/ /data/last";

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private ApiInfo() {
        // Constructor privado para evitar instanciación directa
    }

    public static ApiInfo getInstance() {
        if (instance == null) {
            instance = new ApiInfo();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public String getFeedKey() {
        return feedKey;
    }

    public String getBaseApiFeed() {return baseApiFeedName;}
}

