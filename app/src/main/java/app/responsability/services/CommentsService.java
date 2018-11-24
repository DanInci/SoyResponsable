package app.responsability.services;

import app.responsability.models.Comment;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CommentsService {

    @POST("/issue/{issueId}/comment")
    Call<Long> createComment(@Path("issueId") Long id, @Body Comment comment);

    @DELETE("/comment/{commentId}")
    Call<Void> deleteCommentById(@Path("commentId") Long id);

    @PUT("/comment/{commentId}")
    Call<Long> updateCommentById(@Path("commentId") Long id);
}
