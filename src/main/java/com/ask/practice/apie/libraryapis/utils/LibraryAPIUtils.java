package com.ask.practice.apie.libraryapis.utils;

import org.springframework.util.StringUtils;

public class LibraryAPIUtils {
    public static boolean doesStringValueExists(String str) {
        if(StringUtils.isEmpty(str)){
            return true;
        }
        return false;
    }
}
