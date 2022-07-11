package org.dxticket.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event extends Model implements Serializable {
    String id;
    String name;
    String venueId;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime dateTime;
}