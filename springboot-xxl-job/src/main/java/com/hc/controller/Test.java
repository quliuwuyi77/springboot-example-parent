package com.hc.controller;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;

public class Test {
    public static void main(String[] args) {

        AnnotatedElementUtils.findMergedAnnotation(ParentController.class, RequestMapping.class);
    }
}
