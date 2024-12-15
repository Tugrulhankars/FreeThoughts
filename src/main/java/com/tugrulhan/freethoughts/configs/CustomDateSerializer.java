package com.tugrulhan.freethoughts.configs;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomDateSerializer extends com.fasterxml.jackson.databind.ser.std.StdSerializer<LocalDateTime> {

    public CustomDateSerializer() {
        this(null);
    }

    public CustomDateSerializer(Class<LocalDateTime> t) {
        super(t);
    }

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeString(localDateTime.toString());
    }
}
