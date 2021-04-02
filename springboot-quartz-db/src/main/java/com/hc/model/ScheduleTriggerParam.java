package com.hc.model;

public class ScheduleTriggerParam {

    private Integer paramId;

    private String name;

    private String value;

    private Integer scheduleTriggerId;

    public Integer getParamId() {
        return paramId;
    }

    public void setParamId(Integer paramId) {
        this.paramId = paramId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Integer getScheduleTriggerId() {
        return scheduleTriggerId;
    }

    public void setScheduleTriggerId(Integer scheduleTriggerId) {
        this.scheduleTriggerId = scheduleTriggerId;
    }
}