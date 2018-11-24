package app.responsability.services;

import app.responsability.models.UserProfile;
import app.responsability.models.UserReg;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsersService {

    @POST("/user")
    Call<Long> createUser(@Body UserReg user);

    @GET("/user")
    Call<UserProfile> getUserProfile();

    @PUT("/user/{userId}")
    Call<Long> updateUserProfile(@Path("userId") Long id, UserProfile userProfile);

}
