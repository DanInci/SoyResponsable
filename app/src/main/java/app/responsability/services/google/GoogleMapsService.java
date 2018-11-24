package app.responsability.services.google;

import java.util.List;

import app.responsability.models.LocationSearchResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapsService {

    @GET("/maps/api/place/findplacefromtext/json")
    Call<LocationSearchResult> getLocationGeometry(@Query("key") String key,
                                                   @Query("input") String input,
                                                   @Query("inputtype") String inputType,
                                                   @Query("fields") String fields);
}
