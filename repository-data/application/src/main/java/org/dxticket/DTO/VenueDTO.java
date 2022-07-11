package org.dxticket.DTO;

import lombok.Data;
import org.dxticket.model.Model;
import org.dxticket.model.Venue;

@Data
public class VenueDTO extends DTO {
    Venue venue;

    @Override
    public String getId() {
        return venue.getId();
    }

    @Override
    public Model getModel() {
        return venue;
    }
}
