package app.responsability;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import app.responsability.models.Location;
import app.responsability.models.UserProfile;
import app.responsability.services.ServiceManager;

public class AppSoyResponsable extends Application {

    public static final String SOY_RESPONSABLE = "SOY_RESPONSABLE";
    public static final String CURRENT_LOCATION_NAME = "Current Location";
    public static final Location CURRENT_LOCATION = new Location(CURRENT_LOCATION_NAME, 0d, 0d);

    private static final String SAVED_EMAIL = "soy.responsable.savedEmail";
    private static final String SAVED_PASSWORD = "soy.responsable.savedPassword";
    private static final String SAVED_LOCATION_NAME = "soy.responsable.savedLocationName";
    private static final String SAVED_LOCATION_LAT = "soy.responsable.savedLocationLat";
    private static final String SAVED_LOCATION_LNG = "soy.responsable.savedLocationLng";
    private static final String NOT_FOUND = "NOT_FOUND";

    private static Application appContext;

    public static boolean isLoggedIn;
    public static UserProfile loggedInUser;

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
        String savedLocationName = pref.getString(SAVED_LOCATION_NAME, NOT_FOUND);
        String savedLocationLat = pref.getString(SAVED_LOCATION_LAT, NOT_FOUND);
        String savedLocationLng = pref.getString(SAVED_LOCATION_LNG, NOT_FOUND);
        if(!savedEmail.equals(NOT_FOUND) && !savedPassword.equals(NOT_FOUND) &&
                !savedLocationName.equals(NOT_FOUND) && !savedLocationLat.equals(NOT_FOUND) && !savedLocationLng.equals(NOT_FOUND)) {
            ServiceManager.createSession(savedEmail, savedPassword);
            UserProfile user = new UserProfile();
            user.setLocationName(savedLocationName);
            user.setLatitude(Double.valueOf(savedLocationLat));
            user.setLongitude(Double.valueOf(savedLocationLng));
            setLoggedIn(user);
        }
    }

    public static void saveSession(String email, String password, Location currentLocation) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(appContext);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(SAVED_EMAIL, email);
        editor.putString(SAVED_PASSWORD, password);
        editor.putString(SAVED_LOCATION_NAME, currentLocation.getName());
        editor.putString(SAVED_LOCATION_LAT, currentLocation.getLat().toString());
        editor.putString(SAVED_LOCATION_LAT, currentLocation.getLng().toString());
        editor.apply();
    }

    public static void deleteSession() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(appContext);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(SAVED_EMAIL);
        editor.remove(SAVED_PASSWORD);
        editor.remove(SAVED_LOCATION_NAME);
        editor.remove(SAVED_LOCATION_LAT);
        editor.remove(SAVED_LOCATION_LAT);
        editor.apply();
    }

    public static void setLoggedIn(UserProfile user) {
        isLoggedIn = true;
        loggedInUser = user;
    }

    public static void setLoggedOut() {
        isLoggedIn = false;
        loggedInUser = null;
    }
}
