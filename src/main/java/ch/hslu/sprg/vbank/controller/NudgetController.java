package ch.hslu.sprg.vbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

//@RestController
public class NudgetController {

    @Autowired
    HttpServletRequest request;


//    @RequestMapping(path = "/nudget", produces = "text/plain")
    public String get() throws Exception {
        return new StringBuilder(request.getRemoteAddr()).reverse().toString();
    }

}
