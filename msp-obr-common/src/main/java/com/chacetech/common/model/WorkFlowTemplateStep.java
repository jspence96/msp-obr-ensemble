package com.chacetech.common.model;

import java.io.Serializable;

public class WorkFlowTemplateStep implements Serializable {

    private static final long serialVersionUID = -1893370021469242001L;

    private int order;
    private String stepName;
    private String stepDescription;

    public WorkFlowTemplateStep() {}

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }
}
