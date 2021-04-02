package com.hc.service;

import com.hc.model.*;
import java.util.List;

public interface ScheduleTriggerParamService {

    /**
     * 查询出当前任务类对应所需的参数
     * @param triggerId
     * @return
     */
    List<ScheduleTriggerParam> queryScheduleParamList(Integer triggerId);
}
