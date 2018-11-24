package app.responsability.services;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private String credentials;

    public AuthInterceptor(String email, String password) {
        this.credentials = Credentials.basic(email, password);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .addHeader("Authorization", credentials)
                .addHeader("Content-Type","application/json")
                .addHeader("Accept","application/json")
                .build();
        return chain.proceed(authenticatedRequest);
    }
}
