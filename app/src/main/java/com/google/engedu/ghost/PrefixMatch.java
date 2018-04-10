package com.google.engedu.ghost;


import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Shawn on 4/22/2016.
 */
public class PrefixMatch {


    public String findMatches(ArrayList<String> phrases, String prefix) {
        Log.i("WordFragment", Boolean.toString(Arrays.toString(phrases.toArray()).contains(prefix)));
        return Arrays.toString(phrases.toArray()).contains(prefix) ? phrases.get(this.find(phrases, prefix.toLowerCase())) : null;
    }

    public int find(ArrayList<String> phrases, String prefix) {
        int low = 0;
        int high = phrases.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (phrases.get(mid).startsWith(prefix) || phrases.get(mid).equals(prefix)) {
                if (mid == 0 || (!phrases.get(mid - 1).startsWith(prefix) || !phrases.get(mid).equals(prefix))) {
                    return mid;
                } else {
                    high = mid - 1;
                }
            } else if (prefix.compareTo(phrases.get(mid)) > 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }


}