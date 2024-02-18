package com.domestack.omdb.client.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import java.io.IOException;

public class EmptyStringDeserializer extends StdScalarDeserializer<String> {
    public EmptyStringDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
        var val = parser.getValueAsString();
        return "N/A".equalsIgnoreCase(val) ? null : val;
    }
}