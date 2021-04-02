package com.hc.service;

import com.hc.mapper.ScheduleTriggerParamMapper;
import com.hc.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleTriggerParamServiceImpl implements ScheduleTriggerParamService {

    @Autowired
    private ScheduleTriggerParamMapper scheduleTriggerParamMapper;

    @Override
    public List<ScheduleTriggerParam> queryScheduleParamList(Integer triggerId) {
        return scheduleTriggerParamMapper.queryScheduleParamList(triggerId);
    }
}
