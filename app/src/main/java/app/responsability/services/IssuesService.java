package app.responsability.services;

import java.util.List;

import app.responsability.models.Issue;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IssuesService {

    @POST("/issue")
    Call<Long> createIssue(@Body Issue issue);

    @GET("/issue/{issueId}")
    Call<Issue> getIssueById(@Path("issueId") Long id);

    @GET("/issue")
    Call<List<Issue>> getIssues(@Query("lat") Double latitude, @Query("long") Double longitude, @Query("ic_radius") Double radius);
}
