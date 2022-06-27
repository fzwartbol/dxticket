package training.standardfeatures.answer._02;
/*
 * Copyright 2020 Bloomreach B.V. (http://www.bloomreach.com)
 * Usage is prohibited except for people attending a training given or authorised by Bloomreach B.V., and only for that purpose.
 */

import org.hippoecm.hst.core.parameters.Parameter;
import org.onehippo.cms7.essentials.components.info.EssentialsNewsComponentInfo;

public interface NewsComponentInfo extends EssentialsNewsComponentInfo {

    @Parameter(name = "filterNews", defaultValue = "false", displayName = "Filter news")
    Boolean getFilterNews();

    @Parameter(name = "textToFilter", required = false, displayName = "Text to filter")
    String getTextToFilter();

}
