package org.dxticket.configuration;

import org.dxticket.DTO.DTO;
import org.dxticket.DTO.EventDTO;
import org.dxticket.documents.documentmodifier.DocumentModifier;
import org.dxticket.documents.documentmodifier.EventDocumentModifier;
import org.dxticket.documents.documenttypes.NodeDocumentType;
import org.dxticket.model.Event;

import javax.jcr.Session;


public class VenueConfiguration implements MatcherConfiguration {
    public final static String API_EVENT_ENDPOINT = "http://localhost:9001/events";
    public final static String CONTENT_DOCUMENTS_EVENT_BASEPATH= "content/documents/dxticket/events/";
    public final static NodeDocumentType EVENT_DOCUMENTTYPE = new NodeDocumentType<>(new Event(),"dxticket:eventsdocument","dxticket:EventId");
    //TODO remove hardcoded endpoint

    @Override
    public DTO getAPIModelDTO() {
        return new EventDTO();
    }

    @Override
    public String getContentDocumentBasepath() {
        return CONTENT_DOCUMENTS_EVENT_BASEPATH;
    }

    @Override
    public String getApiEndpoint() {return API_EVENT_ENDPOINT;}

    @Override
    public DocumentModifier getDocumentModifier(Session session) {
        EventDocumentModifier eventDocumentModifier = new EventDocumentModifier(session,getDocumentType());
        return eventDocumentModifier;
    }

    @Override
    public NodeDocumentType getDocumentType() {
        return EVENT_DOCUMENTTYPE;
    }


}
