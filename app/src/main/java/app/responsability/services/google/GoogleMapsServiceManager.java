package app.responsability.services.google;

import com.google.gson.GsonBuilder;

import app.responsability.models.component.LocationSearchResult;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleMapsServiceManager {

    private static final String apiKey = "AIzaSyCcUEUaMQL0_6tvsTyPjwMc_gxeaDnAbjY";
    private static final String baseURL = "https://maps.googleapis.com/";
    private static final String GSON_SERIALIZER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private static Retrofit retrofit;
    private static GoogleMapsService googleMapsService;


    private static GoogleMapsService getGoogleMapsService() {
        if(googleMapsService == null) {
            googleMapsService = getRetrofitClient().create(GoogleMapsService.class);
        }
        return googleMapsService;
    }

    public static Call<LocationSearchResult> findLocationsByName(String name) {
        return getGoogleMapsService().getLocationGeometry(apiKey, name, "textquery", "ic_name,geometry");
    }


    private static Retrofit getRetrofitClient() {
        if(retrofit == null) {
            OkHttpClient httpClient = new OkHttpClient.Builder().build();
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat(GSON_SERIALIZER_DATE_FORMAT).create()))
                    .client(httpClient);
            retrofit = builder.build();
        }
        return retrofit;
    }
}
