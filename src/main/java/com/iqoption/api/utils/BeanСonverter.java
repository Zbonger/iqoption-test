package com.iqoption.api.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class BeanСonverter {

    public static Map<String, String> toParams(Object bean) {
        Gson gson = new Gson();
        Type stringStringMap = new TypeToken<Map<String, String>>() {}.getType();
        return gson.fromJson(gson.toJson(bean), stringStringMap);
    }
}
