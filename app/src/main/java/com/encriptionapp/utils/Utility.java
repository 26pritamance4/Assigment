package com.encriptionapp.utils;

/**
 * Created by PRITAM on 4/14/2016.
 */
public class Utility {

    public static long hash (String s) {
        long h = 7L;
        String letters = "acdegilmnoprstuw";
        for(int i = 0; i < s.length(); i++) {
            h = (h * 37L + (long)letters.indexOf(s.charAt(i)));
        }
        return h;
    }

    public static String nextString(String s)
    {
        String letters = "acdegilmnoprstuw";
        char c = s.charAt(s.length() - 1);
        if (c == 'w')
        {
            if (s.length() > 1)
            {
                return (new StringBuilder()).append(nextString(s.substring(0, s.length() - 1))).append('a').toString();
            } else
            {
                return "wrong";
            }
        } else
        {
            return (new StringBuilder()).append(s.substring(0, s.length() - 1)).append(letters.charAt(letters.indexOf(c) + 1)).toString();
        }
    }
}
