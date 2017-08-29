package testproject.com.tcapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import okhttp3.ResponseBody;

import static testproject.com.tcapplication.ApiRequest.REQUEST_ID_1;
import static testproject.com.tcapplication.ApiRequest.REQUEST_ID_2;
import static testproject.com.tcapplication.ApiRequest.REQUEST_ID_3;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, ApiRequest.ApiRequestCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
    }


    private void initializeViews() {
        (findViewById(R.id.btn)).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                makeApiCallsOnButtonClick();
                break;
        }

    }

    private void makeApiCallsOnButtonClick() {
        // make first api call
        ApiRequest firstApiRequest = new ApiRequest(REQUEST_ID_1);
        firstApiRequest.setApiRequestCallback(this);
        firstApiRequest.makeApiCall();
        ((TextView) findViewById(R.id.first_result_tv)).setText("Fetch the first input...");
        findViewById(R.id.first_result_progress_bar).setVisibility(View.VISIBLE);

        // make second api call
        ApiRequest secondApiRequest = new ApiRequest(REQUEST_ID_2);
        secondApiRequest.setApiRequestCallback(this);
        secondApiRequest.makeApiCall();
        ((TextView) findViewById(R.id.second_result_tv)).setText("Fetch the second input...");
        findViewById(R.id.second_result_progress_bar).setVisibility(View.VISIBLE);

        // make third api call
        ApiRequest thirdApiRequest = new ApiRequest(REQUEST_ID_3);
        thirdApiRequest.setApiRequestCallback(this);
        thirdApiRequest.makeApiCall();
        ((TextView) findViewById(R.id.third_result_tv)).setText("Fetch the third input...");
        ((EditText) findViewById(R.id.input_user_et)).setText("");
        ((TextView) findViewById(R.id.layout_tv)).setText("");
        ((TextView) findViewById(R.id.count_tv)).setText("");
        findViewById(R.id.third_result_progress_bar).setVisibility(View.VISIBLE);
        findViewById(R.id.third_input_result_wrapper).setVisibility(View.GONE);
    }

    @Override
    public void onResponse(boolean isSuccess, int requestId, ResponseBody data) {
        switch (requestId) {
            case REQUEST_ID_1:
                // show the first 10th character
                findViewById(R.id.first_result_progress_bar).setVisibility(View.GONE);
                showTenthCharacter(isSuccess, data);
                break;

            case REQUEST_ID_2:
                // show every 10th character (10th, 20th, 30th and so on)
                findViewById(R.id.second_result_progress_bar).setVisibility(View.GONE);
                showEveryTenthCharacter(isSuccess, data);
                break;

            case REQUEST_ID_3:
                findViewById(R.id.third_result_progress_bar).setVisibility(View.GONE);
                findViewById(R.id.third_input_result_wrapper).setVisibility(View.VISIBLE);

                prepareDictionary(isSuccess, data);
                break;
        }
    }

    // show the first 10th character
    private void showTenthCharacter(boolean isSuccess, ResponseBody data) {
        String text = "N/A";
        if (isSuccess) {
            String result = "";
            try {
                result = new String(data.bytes());
            } catch (Exception e) {
                // Exception
            }
            if (!TextUtils.isEmpty(result) && result.length() > 9) {
                char c = result.charAt(9);

                text = String.valueOf(c);
            }
        }

        String firstInputDisplayText = getResources().getString(R.string.first_input_display_name);
        SpannableString spannableString = new SpannableString(firstInputDisplayText + text);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, firstInputDisplayText.length(), 0);
        ((TextView) findViewById(R.id.first_result_tv)).setText(spannableString);
    }

    // show every 10th character
    private void showEveryTenthCharacter(boolean isSuccess, ResponseBody data) {
        String text = "N/A";
        if (isSuccess) {
            String result = "";
            try {
                result = new String(data.bytes());
            } catch (Exception e) {
                // Exception
            }

            ArrayList<String> charResult = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                if (i % 10 == 0) {
                    charResult.add(String.valueOf(result.charAt(i)));

                }
            }
            StringBuilder res = new StringBuilder();
            for (int i = 0; i < charResult.size(); i++) {
                res.append(charResult.get(i));

                if (i < charResult.size() - 1) {
                    res.append(", ");
                }
            }

            text = res.toString();
        }

        String firstInputDisplayText = getResources().getString(R.string.second_input_display_name);
        SpannableString spannableString = new SpannableString(firstInputDisplayText + text);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, firstInputDisplayText.length(), 0);
        ((TextView) findViewById(R.id.second_result_tv)).setText(spannableString);
    }

    private void prepareDictionary(boolean isSuccess, ResponseBody data) {
        String text = "";
        if (isSuccess) {
            String result = "";
            try {
                result = new String(data.bytes());
            } catch (Exception e) {
                // Exception
            }

            String firstInputDisplayText = getResources().getString(R.string.third_input_display_name);
            SpannableString spannableString = new SpannableString(firstInputDisplayText);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, firstInputDisplayText.length(), 0);
            ((TextView) findViewById(R.id.third_result_tv)).setText(spannableString);

            ((TextView) findViewById(R.id.layout_tv)).setText(result);

            final TagCountDictionary dictionary = new TagCountDictionary(result);
            dictionary.prepareDictionary();
            System.out.println("xx");

            (findViewById(R.id.submit_query_btn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String text = ((EditText) findViewById(R.id.input_user_et)).getText().toString();
                    int count = dictionary.getCount(text);

                    ((TextView) findViewById(R.id.count_tv)).setText(text + ": " + count);
                }
            });
        }
    }
}


