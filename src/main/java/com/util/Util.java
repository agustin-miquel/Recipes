package com.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilities
 * @author Gines Miquel
 */
public class Util {
    
    /**
     * Identify elements of a String containing words that are separated by commas or spaces.
     * @param s
     * @return 
     */
    public static List<String> prepareList(String s) {
        String[] ss;
        if (s.contains(",")) {
            ss = s.split(",");
        } else {
            ss = s.split(" ");
        }
        
        List<String> ls = new ArrayList<>();
        for (String s2 : ss) {
            if (s2.trim().length() > 0) {
                ls.add(s2.trim());
            }
        }
        return ls;
    }

}
