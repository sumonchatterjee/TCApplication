package testproject.com.tcapplication;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by sumon.chatterjee on 28/08/17.
 */

public class TagCountDictionary {
    private Map<String, Integer> mDictionary;
    private String mInputText;

    public TagCountDictionary(String stringText) {
        this.mInputText = stringText;
        mDictionary = new HashMap<>();
    }

    public void prepareDictionary() {
        if (!TextUtils.isEmpty(mInputText)) {
            Scanner in = new Scanner(mInputText);
            while (in.hasNext()) {
                String input = in.next();

                input = input.toLowerCase();
                if (mDictionary.containsKey(input)) {
                    mDictionary.put(input, mDictionary.get(input) + 1);
                } else {
                    mDictionary.put(input, 1);
                }
            }
        }
    }

    public int getCount(String key) {
        int returnValue = 0;

        if (!TextUtils.isEmpty(key) && mDictionary.containsKey(key)) {
            returnValue = mDictionary.get(key);
        }

        return returnValue;
    }
}
