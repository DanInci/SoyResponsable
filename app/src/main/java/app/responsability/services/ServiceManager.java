package app.responsability.services;

import com.google.gson.GsonBuilder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceManager {

    private static final String baseURL = "http://18.197.19.50:8080/";
    private static final String GSON_SERIALIZER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private static Retrofit retrofit;

    private static String currentUsername;
    private static String currentPassword;

    private static CommentsService commentsService;
    private static IssuesService issuesService;
    private static UsersService usersService;

    public static Retrofit createSession(String username, String password) {
        currentUsername = username;
        currentPassword = password;

        Interceptor interceptor = new AuthInterceptor(username, password);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat(GSON_SERIALIZER_DATE_FORMAT).create()))
                .client(httpClient);
        retrofit = builder.build();
        return retrofit;
    }

    public static void  invalidateSession() {
        retrofit = null;
        commentsService = null;
        issuesService = null;
        usersService = null;

        currentUsername = "";
        currentPassword = "";
    }

    public static CommentsService getCommentsService() {
        if(commentsService == null) {
            commentsService = getRetrofitClient().create(CommentsService.class);
        }
        return commentsService;
    }

    public static IssuesService getIssuesService() {
        if(issuesService == null) {
            issuesService = getRetrofitClient().create(IssuesService.class);
        }
        return issuesService;
    }

    public static UsersService getUsersService() {
        if(usersService == null) {
            usersService = getRetrofitClient().create(UsersService.class);
        }
        return usersService;
    }


    private static Retrofit getRetrofitClient() {
        if(retrofit == null) {

        }
        return retrofit;
    }
}
