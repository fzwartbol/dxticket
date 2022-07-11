package org.dxticket.scheduler;

import org.onehippo.cms7.services.HippoServiceRegistry;
import org.onehippo.repository.modules.DaemonModule;
import org.onehippo.repository.scheduling.RepositoryJobCronTrigger;
import org.onehippo.repository.scheduling.RepositoryJobInfo;
import org.onehippo.repository.scheduling.RepositoryJobTrigger;
import org.onehippo.repository.scheduling.RepositoryScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

public class EventScheduler implements DaemonModule {
    private static Logger log = LoggerFactory.getLogger(EventScheduler.class);

    @Override
    public void initialize(Session session) throws RepositoryException {
        System.out.println("Initializing EventScheduler");
        final RepositoryScheduler scheduler = HippoServiceRegistry.getService(RepositoryScheduler.class);
        final RepositoryJobInfo eventMatchScheduler = new RepositoryJobInfo("EventMatcherJob", EventPlan.class);
        final RepositoryJobTrigger timer = new RepositoryJobCronTrigger("minute","0 * * ? * *");
        scheduler.scheduleJob(eventMatchScheduler, timer);
    }

    @Override
    public void shutdown() {
    }

}
