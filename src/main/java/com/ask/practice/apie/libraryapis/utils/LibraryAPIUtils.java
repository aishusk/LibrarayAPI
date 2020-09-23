package com.ask.practice.apie.libraryapis.utils;

import org.springframework.util.StringUtils;

import java.util.UUID;

public class LibraryAPIUtils {
    public static boolean doesStringValueExists(String str) {
        if(StringUtils.isEmpty(str)){
            return false;
        }
        return true;
    }

    public static String getTraceIdIfNotSet(String traceId){
        if(!doesStringValueExists(traceId)){
            return UUID.randomUUID().toString();
        }
        return traceId;
    }
}
