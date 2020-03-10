package com.chacetech.migrations;

import com.chacetech.common.model.WorkFlowTemplate;
import com.chacetech.common.model.WorkFlowTemplateStep;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.util.JSON;
import org.omg.PortableInterceptor.LOCATION_FORWARD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

@ChangeLog
public class Changelog {

    private static final Logger LOGGER  = LoggerFactory.getLogger("Changelog.class");
    private final String SCRIPT_FOLDER = "json/";

    @ChangeSet(order="001", id = "baseline", author="jspence")
    public void baseline(final DB db) {
        // do nothing for baseline
    }

    @ChangeSet(order="002", id = "create_workflow_template", author ="jspence", runAlways = true)
    public void create_workflow_template(final DB db) {

        final DBCollection workflowTemplateCollection = db.getCollection("WORKFLOW_TEMPLATE");

        Gson gson = new Gson();

        String fileName = "workflowTemplateScript.json";

        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(SCRIPT_FOLDER + fileName);
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
        WorkFlowTemplate[] data = gson.fromJson(reader, WorkFlowTemplate[].class);

        try {
            reader.close();
        } catch (IOException ioe) {
            LOGGER.error("Exception in JSON reader for " + fileName, ioe);
        }

        workflowTemplateCollection.drop();

        for (WorkFlowTemplate workFlowTemplate : data) {
            if (!workFlowTemplate.getSteps().stream().map(WorkFlowTemplateStep::getStepName)
                    .allMatch(new HashSet<>()::add)) {
                throw new RuntimeException("Duplicate workflow template steps found");
            }

            String json = gson.toJson(workFlowTemplate);
            BasicDBObject workflowTemplateObject = (BasicDBObject) JSON.parse(json);
            workflowTemplateCollection.insert(workflowTemplateObject);

        }
    }

}
