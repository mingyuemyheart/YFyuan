package com.moft.xfapply.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangquan on 2016/11/22.
 */

public class GsonUtil {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public final static Gson create() {
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                Date date = null;
                if(json.getAsJsonPrimitive().isNumber()) {
                    date = new Date(json.getAsJsonPrimitive().getAsLong());
                } else if (json.getAsJsonPrimitive().isString()) {
                    String strDate = json.getAsJsonPrimitive().getAsString();
                    try {
                        SimpleDateFormat formatter = null;
                        if(strDate.length() <= 10) {
                            formatter = new SimpleDateFormat(DATE_FORMAT);
                        } else {
                            formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
                        }
                        date = formatter.parse(strDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return date;
            }
        });

        Gson gson = builder.setDateFormat(DATE_TIME_FORMAT).create();
        return gson;
    }
}
