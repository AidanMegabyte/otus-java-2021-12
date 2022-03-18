package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.TextNode;
import ru.otus.model.Measurement;

import java.io.IOException;

public class MeasurementDeserializer extends StdDeserializer<Measurement> {

    public MeasurementDeserializer() {
        super(Measurement.class);
    }

    @Override
    public Measurement deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        var node = parser.getCodec().readTree(parser);
        var name = ((TextNode) node.get("name")).asText();
        var value = ((DoubleNode) node.get("value")).asDouble();
        return new Measurement(name, value);
    }
}
