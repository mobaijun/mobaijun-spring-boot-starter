package com.mobaijun.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.io.Serializable;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: NullStringJsonSerializer
 * class description：jackson NULL值序列化为 ""
 *
 * @author MoBaiJun 2022/7/5 9:22
 */
public class NullStringJsonSerializer extends JsonSerializer<Object> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString("");
    }
}
