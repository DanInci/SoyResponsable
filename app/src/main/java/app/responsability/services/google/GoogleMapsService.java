package app.responsability.services.google;

import app.responsability.models.component.LocationSearchResult;
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
