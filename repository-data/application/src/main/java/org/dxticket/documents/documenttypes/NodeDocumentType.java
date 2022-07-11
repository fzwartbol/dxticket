package org.dxticket.documents.documenttypes;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class NodeDocumentType<Model> {
    private Model javaModelClass;
    protected String documentType;
    protected String documentIdentifier;
}
