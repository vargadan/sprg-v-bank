package com.dani.vbank.controller;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController extends AbstractErrorController {


    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        return "error";
    }
}
