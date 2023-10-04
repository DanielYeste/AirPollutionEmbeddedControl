package com.example.mm.data.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * The ApiCalls class handles API calls to retrieve data related to temperature, humidity, CO, and VOC.
 * It uses the Adafruit IO platform to fetch the data from specific feeds based on the classroom ID.
 * This class is implemented as a singleton to ensure only one instance is created.
 * To access the singleton instance, use the {@link #getInstance()} method.
 */
public class ApiCalls {
    private static ApiCalls instance;

    /**
     * Private constructor to prevent direct instantiation of the class.
     * Use {@link #getInstance()} to obtain the singleton instance.
     */
    private ApiCalls() {
        // Private constructor to prevent instantiation
    }

    /**
     * Returns the singleton instance of the ApiCalls class.
     *
     * @return The singleton instance.
     */
    public static ApiCalls getInstance() {
        if (instance == null) {
            instance = new ApiCalls();
        }
        return instance;
    }

    /**
     * Sends a request to Adafruit IO API to fetch the temperature for a specific classroom.
     *
     * @param classroomId The ID of the classroom.
     * @return The temperature value as a string if successful, or an error message.
     */
    public String adafruitRequestTemperature(int classroomId) {
        // Create the URL with the available information
        StringBuilder setUrl = new StringBuilder(ApiInfo.getInstance().getBaseUrl());

        int firstSpaceIndex = setUrl.indexOf(" ");
        if (firstSpaceIndex != -1) {
            setUrl.replace(firstSpaceIndex, firstSpaceIndex + 1, ApiInfo.getInstance().getUsername());

            int secondSpaceIndex = setUrl.indexOf(" ", firstSpaceIndex + ApiInfo.getInstance().getUsername().length());
            if (secondSpaceIndex != -1) {
                setUrl.replace(secondSpaceIndex, secondSpaceIndex + 1, ApiInfo.getInstance().getBaseApiFeed() + classroomId + ".temperature");
            }
        }
        String url = setUrl.toString();

        // Perform the request
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("X-AIO-Key", ApiInfo.getInstance().getFeedKey())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String responseData = responseBody.string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    return jsonObject.getString("value");
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return "Error en la obtención de datos";
    }

    /**
     * Sends a request to Adafruit IO API to fetch the humidity for a specific classroom.
     *
     * @param classroomId The ID of the classroom.
     * @return The humidity value as a string if successful, or an error message.
     */
    public String adafruitRequestHumidity(int classroomId) {
        // Create the URL with the available information
        StringBuilder setUrl = new StringBuilder(ApiInfo.getInstance().getBaseUrl());

        int firstSpaceIndex = setUrl.indexOf(" ");
        if (firstSpaceIndex != -1) {
            setUrl.replace(firstSpaceIndex, firstSpaceIndex + 1, ApiInfo.getInstance().getUsername());

            int secondSpaceIndex = setUrl.indexOf(" ", firstSpaceIndex + ApiInfo.getInstance().getUsername().length());
            if (secondSpaceIndex != -1) {
                setUrl.replace(secondSpaceIndex, secondSpaceIndex + 1, ApiInfo.getInstance().getBaseApiFeed() + classroomId + ".humidity");
            }
        }
        String url = setUrl.toString();

        // Perform the request
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("X-AIO-Key", ApiInfo.getInstance().getFeedKey())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String responseData = responseBody.string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    return jsonObject.getString("value");
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return "Error en la obtención de datos";
    }

    /**
     * Sends a request to Adafruit IO API to fetch the CO value for a specific classroom.
     *
     * @param classroomId The ID of the classroom.
     * @return The CO value as a string if successful, or an error message.
     */
    public String adafruitRequestCo(int classroomId) {
        // Create the URL with the available information
        StringBuilder setUrl = new StringBuilder(ApiInfo.getInstance().getBaseUrl());

        int firstSpaceIndex = setUrl.indexOf(" ");
        if (firstSpaceIndex != -1) {
            setUrl.replace(firstSpaceIndex, firstSpaceIndex + 1, ApiInfo.getInstance().getUsername());

            int secondSpaceIndex = setUrl.indexOf(" ", firstSpaceIndex + ApiInfo.getInstance().getUsername().length());
            if (secondSpaceIndex != -1) {
                setUrl.replace(secondSpaceIndex, secondSpaceIndex + 1, ApiInfo.getInstance().getBaseApiFeed() + classroomId + ".co2");
            }
        }
        String url = setUrl.toString();

        // Perform the request
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("X-AIO-Key", ApiInfo.getInstance().getFeedKey())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String responseData = responseBody.string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    return jsonObject.getString("value");
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return "Error en la obtención de datos";
    }

    /**
     * Sends a request to Adafruit IO API to fetch the VOC value for a specific classroom.
     *
     * @param classroomId The ID of the classroom.
     * @return The VOC value as a string if successful, or an error message.
     */
    public String adafruitRequestVoc(int classroomId) {
        // Create the URL with the available information
        StringBuilder setUrl = new StringBuilder(ApiInfo.getInstance().getBaseUrl());

        int firstSpaceIndex = setUrl.indexOf(" ");
        if (firstSpaceIndex != -1) {
            setUrl.replace(firstSpaceIndex, firstSpaceIndex + 1, ApiInfo.getInstance().getUsername());

            int secondSpaceIndex = setUrl.indexOf(" ", firstSpaceIndex + ApiInfo.getInstance().getUsername().length());
            if (secondSpaceIndex != -1) {
                setUrl.replace(secondSpaceIndex, secondSpaceIndex + 1, ApiInfo.getInstance().getBaseApiFeed() + classroomId + ".voc");
            }
        }
        String url = setUrl.toString();

        // Perform the request
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("X-AIO-Key", ApiInfo.getInstance().getFeedKey())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String responseData = responseBody.string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    return jsonObject.getString("value");
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return "Error en la obtención de datos";
    }
}

