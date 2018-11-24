package app.responsability;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import app.responsability.services.ServiceManager;

public class AppSoyResponsable extends Application {

    private static final String SAVED_EMAIL = "soy.responsable.savedEmail";
    private static final String SAVED_PASSWORD = "soy.responsable.savedPassword";
    private static final String NOT_FOUND = "NOT_FOUND";

    private static Application appContext;
    public static Boolean isLoggedIn;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        loadSession();
    }

    public static void loadSession() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(appContext);
        String savedEmail = pref.getString(SAVED_EMAIL, NOT_FOUND);
        String savedPassword = pref.getString(SAVED_EMAIL, NOT_FOUND);
        if(!savedEmail.equals(NOT_FOUND) && !savedPassword.equals(NOT_FOUND)) {
            ServiceManager.createSession(savedEmail, savedPassword);
            isLoggedIn = true;
        }
    }

    public static void saveSession(String email, String password) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(appContext);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(SAVED_EMAIL, email);
        editor.putString(SAVED_PASSWORD, password);
        editor.apply();
    }
}
