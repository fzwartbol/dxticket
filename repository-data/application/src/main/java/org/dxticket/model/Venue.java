package org.dxticket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venue extends Model {
    private String id;
    private String name;
    private Long capacity;

}