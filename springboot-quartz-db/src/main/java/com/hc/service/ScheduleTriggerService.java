package com.hc.service;


import com.hc.model.*;

import java.util.List;

public interface ScheduleTriggerService {

    int deleteByPrimaryKey(Integer id);

    int insert(ScheduleTrigger record);

    int insertSelective(ScheduleTrigger record);

    ScheduleTrigger selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ScheduleTrigger record);

    int updateByPrimaryKey(ScheduleTrigger record);

    /**
     * 查询触发器中包含的所有任务
     * @return
     */
    List<ScheduleTrigger> queryScheduleTriggerList();

}
