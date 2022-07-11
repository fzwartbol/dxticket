package org.dxticket.configuration;

import org.dxticket.DTO.DTO;
import org.dxticket.documents.documentmodifier.DocumentModifier;
import org.dxticket.documents.documenttypes.NodeDocumentType;

import javax.jcr.Session;

public interface MatcherConfiguration {
    DTO getAPIModelDTO();
    String getContentDocumentBasepath();
    String getApiEndpoint();
    DocumentModifier getDocumentModifier(Session session);
    NodeDocumentType getDocumentType();
}
