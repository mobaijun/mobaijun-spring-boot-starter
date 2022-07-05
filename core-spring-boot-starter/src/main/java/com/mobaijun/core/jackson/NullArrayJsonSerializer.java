package com.mobaijun.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.Serializable;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: NullArrayJsonSerializer
 * class description： 空数组序列化处理器 如果 Array 为 null，则序列化为 []
 *
 * @author MoBaiJun 2022/7/5 9:18
 */
public class NullArrayJsonSerializer extends JsonSerializer<Object> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (ObjectUtils.isEmpty(value)) {
            gen.writeStartArray();
            gen.writeEndArray();
        } else {
            gen.writeObject(value);
        }
    }
}
