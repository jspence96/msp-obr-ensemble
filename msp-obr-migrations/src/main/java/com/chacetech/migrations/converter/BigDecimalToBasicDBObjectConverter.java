package com.chacetech.migrations.converter;

import com.mongodb.BasicDBObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@WritingConverter
public class BigDecimalToBasicDBObjectConverter implements Converter<BigDecimal, BasicDBObject> {

    public BigDecimalToBasicDBObjectConverter() {}

    public BasicDBObject convert(BigDecimal source) {
        return (new BasicDBObject("value", source.unscaledValue().longValue())).append("scale", source.scale());
    }
}
