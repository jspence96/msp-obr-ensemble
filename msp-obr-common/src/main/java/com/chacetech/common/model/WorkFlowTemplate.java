package com.chacetech.common.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(collection="WORKFLOW_TEMPLATE")
public class WorkFlowTemplate implements Serializable {

    private static final long serialVersionUID = 4744194715054672682L;

    private String workflowTemplateId;
    private List<WorkFlowTemplateStep> steps = new ArrayList();

    public WorkFlowTemplate() {}

    public String getWorkflowTemplateId() {
        return workflowTemplateId;
    }

    public void setWorkflowTemplateId(String workflowTemplateId) {
        this.workflowTemplateId = workflowTemplateId;
    }

    public List<WorkFlowTemplateStep> getSteps() {
        return steps;
    }

    public void setSteps(List<WorkFlowTemplateStep> steps) {
        this.steps = steps;
    }
}
