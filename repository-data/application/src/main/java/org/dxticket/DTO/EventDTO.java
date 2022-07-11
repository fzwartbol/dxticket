package org.dxticket.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.dxticket.model.Model;
import org.dxticket.model.Event;
import org.dxticket.model.Venue;

@NoArgsConstructor
@Data
public class EventDTO extends DTO {
    Event event;
    Venue venue;
    
    @Override
    public String getId() {
        return event.getId();
    }

    @Override
    public Model getModel() {
        return event;
    }


}
