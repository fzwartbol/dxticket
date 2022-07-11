package org.dxticket.documents.documentmodifier;

import org.dxticket.documents.documenttypes.NodeDocumentType;
import org.dxticket.model.Event;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

public class EventDocumentModifier extends DocumentModifier<Event> {
    public EventDocumentModifier(Session session, NodeDocumentType<Event> documentType) {
        super(session, documentType);

    }

    @Override
    public void updateDocument(Node node, Event event) {
        try {
            node.setProperty("dxticket:EventId", event.getId());
            node.setProperty("dxticket:title", event.getName());
            node.setProperty("dxticket:venueId", event.getVenueId());

        } catch (RepositoryException e) {
            log.error(e.getMessage());
        }
    }

}
