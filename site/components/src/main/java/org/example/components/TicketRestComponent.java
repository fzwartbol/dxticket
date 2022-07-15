package org.example.components;

import java.util.regex.Pattern;

import static org.example.components.CustomListComponent.TICKETS_API_BASEPATH;

public class TicketRestComponent extends BaseRestComponent{
    final static String TICKET_COMPONENT_NAME="Ticket";

    @Override
    public String getComponentName() {
        return TICKET_COMPONENT_NAME;
    }

    public String getIdFromPathInfo(String path) {
        return path.replaceFirst(Pattern.quote("/"+"tickets"+"/"), "").replaceAll(".html","");
    }

    public Object retrieveObjectByID (String id) {
        return restTemplate.getForObject(TICKETS_API_BASEPATH+"/"+id,Object.class);
    }
}
