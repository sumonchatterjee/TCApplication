package testproject.com.tcapplication.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by sumon.chatterjee on 14/08/17.
 */

public interface RetroFitServices {


//    @GET("support/")
//    Call<ResponseBody> getInput();

    @GET("support/")
    Observable<ResponseBody> getInput();


}
