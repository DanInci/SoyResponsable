package app.responsability.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.internal.bind.DateTypeAdapter;


import java.lang.reflect.Type;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceManager {

    private static final String baseURL = "http://18.197.19.50:8080/";
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    private static Gson gson;
    private static Retrofit retrofit;

    public static String currentEmail;
    public static String currentPassword;

    private static CommentsService commentsService;
    private static IssuesService issuesService;
    private static UsersService usersService;

    public static Retrofit createSession(String email, String password) {
        currentEmail = email;
        currentPassword = password;

        Interceptor interceptor = new AuthInterceptor(email, password);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(httpClient);
        retrofit = builder.build();
        return retrofit;
    }

    public static void  invalidateSession() {
        retrofit = null;
        commentsService = null;
        issuesService = null;
        usersService = null;

        currentEmail = "";
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

    private static Gson getGson() {
        if(gson == null) {
            gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Date.class, new DateTypeAdapter()).create();
        }
        return gson;
    }

    private static Retrofit getRetrofitClient() {
        if(retrofit == null) {
            OkHttpClient httpClient = new OkHttpClient.Builder().build();
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .client(httpClient);
            retrofit = builder.build();
        }
        return retrofit;
    }

    private static class DateTypeAdapter implements JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonElement js, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String date = js.getAsString();

            if (date==null || date.isEmpty()){
                return null;
            }

            try {
                return new Date(DATE_FORMATTER.parse(date).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }

        }
    }
}
