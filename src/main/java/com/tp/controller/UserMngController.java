package com.tp.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

/**
 * Created by HoangAnh on 8/31/2018.
 */
@ManagedBean
@Component
@Scope("view")
public class UserMngController extends BaseController {

    @PostConstruct
    @Override
    protected void init() throws Exception {

    }
}
