package org.dxticket.configuration;

import org.dxticket.DTO.DTO;
import org.dxticket.DTO.EventDTO;
import org.dxticket.consumer.ApiConsumer;
import org.dxticket.documents.documentmodifier.DocumentModifier;
import org.dxticket.documents.documentmodifier.EventDocumentModifier;
import org.dxticket.documents.documenttypes.NodeDocumentType;
import org.dxticket.model.Event;

import javax.jcr.Session;

public class EventConfiguration implements MatcherConfiguration {

    @Override
    public DTO getAPIModelDTO() {
        return new EventDTO();
    }

    @Override
    public String getContentDocumentBasepath() {
        return DocumentPaths.CONTENT_DOCUMENTS_EVENT_BASEPATH;
    }

    @Override
    public String getApiEndpoint() {
        return ApiConsumer.API_EVENT_ENDPOINT;
    }

    @Override
    public DocumentModifier getDocumentModifier(Session session) {
        EventDocumentModifier eventDocumentModifier = new EventDocumentModifier(session,getDocumentType());
        return eventDocumentModifier;
    }

    @Override
    public NodeDocumentType getDocumentType() {
        return new NodeDocumentType(new Event(),"dxticket:eventsdocument","dxticket:EventId");
    }


}
