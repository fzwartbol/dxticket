package org.example.components;

import com.google.common.base.Strings;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.exceptions.FilterException;
import org.hippoecm.hst.content.beans.query.filter.BaseFilter;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.repository.util.DateTools;
import org.onehippo.cms7.essentials.components.EssentialsEventsComponent;
import org.onehippo.cms7.essentials.components.EssentialsListComponent;
import org.onehippo.cms7.essentials.components.utils.ComponentsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.List;

@ParametersInfo(type = EventComponentInfo.class)
public class EventComponent extends EssentialsListComponent {

    private static Logger log = LoggerFactory.getLogger(EssentialsEventsComponent.class);

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
        final EventComponentInfo paramInfo = getComponentParametersInfo(request);
        final String documentTypes = paramInfo.getDocumentTypes();

        ComponentsUtils.addCurrentDateStrings(request);
        if (Strings.isNullOrEmpty(documentTypes)) {
            setEditMode(request);
            return;
        }
        super.doBeforeRender(request, response);
    }

    @Override
    protected void contributeAndFilters(final List<BaseFilter> filters, final HstRequest request, final HstQuery query) {
        final EventComponentInfo paramInfo = getComponentParametersInfo(request);
        if (paramInfo.getHidePastEvents()) {
            final String dateField = paramInfo.getDocumentDateField();
            if (!Strings.isNullOrEmpty(dateField)) {
                try {
                    final Filter filter = query.createFilter();
                    filter.addGreaterOrEqualThan(dateField, Calendar.getInstance(), DateTools.Resolution.DAY);
                    filters.add(filter);
                } catch (FilterException e) {
                    log.error("Error while creating query filter to hide past events using date field {}", dateField, e);
                }
            }
        }
    }


}