package com.chacetech.migrations.converter;

import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;

@Component
@ReadingConverter
public class BasicDBObjectToBigDecimalConverter implements Converter<Object, BigDecimal> {

    public BasicDBObjectToBigDecimalConverter() {}

    @Nullable
    public BigDecimal convert(Object source) {
        Document basicDocument = (Document) source;
        return new BigDecimal(new BigInteger(basicDocument.get("value").toString()));
    }
}
