package org.dxticket.documents.documentmodifier;

import org.dxticket.documents.documenttypes.NodeDocumentType;
import org.dxticket.model.Venue;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

public class VenueDocumentModifier extends DocumentModifier<Venue> {
    public VenueDocumentModifier(Session session, NodeDocumentType<Venue> documentType) {
        super(session, documentType);
    }

    @Override
    public void setProperties(Node node, Venue venue) throws RepositoryException {
        node.setProperty("dxticket:venueId", venue.getId());
        node.setProperty("dxticket:venueName", venue.getName());
        node.setProperty("dxticket:venueCapacity", venue.getCapacity());
    }


}
