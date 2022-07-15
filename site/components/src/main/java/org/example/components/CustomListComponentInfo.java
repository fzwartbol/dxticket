package org.example.components;

import org.hippoecm.hst.core.parameters.DropDownList;
import org.hippoecm.hst.core.parameters.FieldGroup;
import org.hippoecm.hst.core.parameters.FieldGroupList;
import org.hippoecm.hst.core.parameters.Parameter;
import org.onehippo.cms7.essentials.components.info.EssentialsListComponentInfo;

@FieldGroupList({
        @FieldGroup(value = { "documentTypes","componentTitle", "path", "includeSubtypes"},
                titleKey = "list.group")
})
public interface CustomListComponentInfo extends EssentialsListComponentInfo {
    /**
     * Boolean flag which indicates if events that happened in the past will not be shown.
     *
     * @return {@code true} if items should be hidden, {@code false} otherwise
     */

    @Parameter(name = "componentTitle")
    String getComponentTitle();

    @Override
    @Parameter(name = "documentTypes", required = true)
    @DropDownList(value = {"Events", "Venues","Users","Tickets"})
    String getDocumentTypes();

    //TODO determine component functionalities
}
