package org.dxticket.scheduler;


import org.onehippo.repository.scheduling.RepositoryJob;
import org.onehippo.repository.scheduling.RepositoryJobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventMatcher implements RepositoryJob {
    private static final Logger logger = LoggerFactory.getLogger(EventMatcher.class);

    public EventMatcher() {}

    @Override
    public void execute(RepositoryJobExecutionContext repositoryJobExecutionContext) {
            logger.debug("Successfully started scheduled job for import of events");
            EventNodeMatcher eventNodeMatcher = new EventNodeMatcher();
            eventNodeMatcher.matchEvents();
    }
}
