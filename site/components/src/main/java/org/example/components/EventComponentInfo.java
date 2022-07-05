package org.example.components;

import org.hippoecm.hst.core.parameters.FieldGroup;
import org.hippoecm.hst.core.parameters.FieldGroupList;
import org.hippoecm.hst.core.parameters.Parameter;
import org.onehippo.cms7.essentials.components.info.EssentialsListComponentInfo;

@FieldGroupList({
        @FieldGroup(value = {"hidePastEvents", "documentDateField", "path", "includeSubtypes", "documentTypes"},
                titleKey = "list.group")
})
public interface EventComponentInfo extends EssentialsListComponentInfo {
    /**
     * Boolean flag which indicates if events that happened in the past will not be shown.
     *
     * @return {@code true} if items should be hidden, {@code false} otherwise
     */
    @Parameter(name = "hidePastEvents", defaultValue = "true")
    Boolean getHidePastEvents();

    @Parameter(name = "documentDateField")
    String getDocumentDateField();

    @Override
    @Parameter(name = "documentTypes", required = true)
    String getDocumentTypes();

    //TODO determine component functionalities
}
