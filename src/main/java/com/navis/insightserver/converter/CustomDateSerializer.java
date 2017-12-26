package com.navis.insightserver.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by darrell-shofstall on 12/26/17.
 */
@Component
public class CustomDateSerializer extends StdSerializer<Date>{

    private SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyyy-MM-dd");

    public CustomDateSerializer(){
        this(null);
    }
    public CustomDateSerializer(Class<Date> t) {
        super(t);
    }

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(simpleDateFormat.format(date));
    }
}
