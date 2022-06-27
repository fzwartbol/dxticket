package training.standardfeatures.answer._01;
/*
 * Copyright 2020 Bloomreach B.V. (http://www.bloomreach.com)
 * Usage is prohibited except for people attending a training given or authorised by Bloomreach B.V., and only for that purpose.
 */

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.exceptions.FilterException;
import org.hippoecm.hst.content.beans.query.filter.BaseFilter;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.EssentialsNewsComponent;
import org.onehippo.cms7.essentials.components.info.EssentialsNewsComponentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ParametersInfo(type = EssentialsNewsComponentInfo.class)
public class NewsComponent extends EssentialsNewsComponent {

    private static final Logger log = LoggerFactory.getLogger(NewsComponent.class);

    @Override
    protected void contributeAndFilters(List<BaseFilter> filters, HstRequest request, HstQuery query) {
        super.contributeAndFilters(filters, request, query);
        try {
            Filter filter = query.createFilter();
            filter.addNotContains("dxticket:title", "Fake");
            filters.add(filter);
        } catch (FilterException e) {
            log.error("Error setting Filter", e.getMessage());
        }
    }
}
