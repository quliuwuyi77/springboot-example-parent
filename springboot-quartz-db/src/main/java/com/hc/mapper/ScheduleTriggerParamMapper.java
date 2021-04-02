package com.hc.mapper;

import com.hc.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleTriggerParamMapper {

    int deleteByPrimaryKey(Integer paramId);

    int insert(ScheduleTriggerParam record);

    int insertSelective(ScheduleTriggerParam record);

    ScheduleTriggerParam selectByPrimaryKey(Integer paramId);

    int updateByPrimaryKeySelective(ScheduleTriggerParam record);

    int updateByPrimaryKey(ScheduleTriggerParam record);

    /**
     * 查询出当前任务类对应所需的参数
     * @param triggerId
     * @return
     */
    List<ScheduleTriggerParam> queryScheduleParamList(Integer triggerId);
}