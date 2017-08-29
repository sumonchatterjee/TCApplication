package testproject.com.tcapplication;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import testproject.com.tcapplication.network.RetroFitClient;

/**
 * Created by sumon.chatterjee on 28/08/17.
 */

public class ApiRequest {
    public static final int REQUEST_ID_1 = 0;
    public static final int REQUEST_ID_2 = 1;
    public static final int REQUEST_ID_3 = 2;

    private int mRequestId;
    private ApiRequestCallback mApiRequestCallback;

    public ApiRequest(int requestId) {
        this.mRequestId = requestId;
    }

    public void makeApiCall() {
        Observable<ResponseBody> call = RetroFitClient.getServices().getInput();

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
//                        if (mApiRequestCallback != null) {
//                            mApiRequestCallback.onResponse(false, mRequestId, null);
//                        }
                    }

                    @Override
                    public void onNext(ResponseBody response) {
                        if (mApiRequestCallback != null) {
                            mApiRequestCallback.onResponse(true, mRequestId, response);
                        }
                    }
                });
    }




    public interface ApiRequestCallback {
        void onResponse(boolean isSuccess, int requestId, ResponseBody data);
    }

    public void setApiRequestCallback(ApiRequestCallback apiRequestCallback) {
        this.mApiRequestCallback = apiRequestCallback;
    }
}

