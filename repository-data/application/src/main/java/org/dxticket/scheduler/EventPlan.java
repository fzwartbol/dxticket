package org.dxticket.scheduler;


import org.dxticket.configuration.EventConfiguration;
import org.onehippo.repository.scheduling.RepositoryJob;
import org.onehippo.repository.scheduling.RepositoryJobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventPlan implements RepositoryJob {
    private static final Logger log = LoggerFactory.getLogger(EventPlan.class);

    public EventPlan() {}

    @Override
    public void execute(RepositoryJobExecutionContext repositoryJobExecutionContext) {
        System.out.println("Executing matcher event config");
        EventConfiguration eventConfig = new EventConfiguration();
        ImporterJob eventConfigImporterJob = new ImporterJob(eventConfig);
        eventConfigImporterJob.execute(repositoryJobExecutionContext);
    }



}
