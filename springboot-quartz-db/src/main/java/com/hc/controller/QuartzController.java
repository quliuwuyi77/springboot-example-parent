package com.hc.controller;

import com.hc.model.ScheduleTrigger;
import com.hc.service.ScheduleTriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/quartz")
public class QuartzController {

    @Autowired
    private ScheduleTriggerService scheduleTriggerService;

    @RequestMapping("/list")
    public ModelAndView getAll(){
        ModelAndView modelAndView = new ModelAndView();
        List<ScheduleTrigger> list = scheduleTriggerService.queryScheduleTriggerList();
        modelAndView.addObject("quartzList",list);
        modelAndView.setViewName("/index");
        return modelAndView;
    }

    @RequestMapping("/edit")
    public String editStatus(ScheduleTrigger scheduleTrigger){
        int n = scheduleTriggerService.updateByPrimaryKeySelective(scheduleTrigger);
        return "redirect:/quartz/list";
    }

    @RequestMapping("/proSave/{id}")
    public ModelAndView proSave(@PathVariable(value = "id") Integer id){
        ModelAndView modelAndView=new ModelAndView();
        ScheduleTrigger scheduleTrigger = scheduleTriggerService.selectByPrimaryKey(id);
        modelAndView.addObject("schedule",scheduleTrigger);
        modelAndView.setViewName("/edit");
        return modelAndView;
    }

}
