package com.impinj.rtls.connector.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ralemy on 8/9/16.
 * The responsibility of this object is to convert strings that represent date in itemsense to date objects.
 * itemsense dates have a Z[ETC/UTC] at the end of them that we have to strip.
 */
public class DateDeserializer extends StdDeserializer<Date> {
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public DateDeserializer(Class<?> vc) {
        super(vc);
    }

    public DateDeserializer(){
        this(null);
    }

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            String date = jsonParser.getText().substring(0, 24);
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }
}
