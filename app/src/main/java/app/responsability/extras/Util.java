package app.responsability.extras;

import android.support.v4.app.FragmentManager;

import retrofit2.Call;
import retrofit2.Response;

public class Util {

    public static <T> Boolean treatGenericErrors(Call<T> call, Response<T> response, FragmentManager fragmentManager) {
        return true;
    }
}
