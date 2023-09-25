package com.patri.plataforma.restapi.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Converter(autoApply = true)
public class OffSetDateTimeAttributeConverter implements AttributeConverter<OffsetDateTime, Timestamp>
{

    @Override
    public Timestamp convertToDatabaseColumn (OffsetDateTime attribute)
    {
        Instant instant = attribute.toInstant();
        return attribute == null ? null : Timestamp.from(instant);
    }



    @Override
    public OffsetDateTime convertToEntityAttribute (Timestamp dbData)
    {
        Instant instant = Instant.ofEpochMilli(dbData.getTime());
        return dbData == null ? null : OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}

