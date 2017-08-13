package org.talkapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Budnikau Aliaksandr
 */
@RestController
@RequestMapping(LoginController.CONTROLLER_PATH)
public class LoginController {
    public static final String CONTROLLER_PATH = "/login";

    @RequestMapping(method = RequestMethod.POST)
    public String findAll() {
        return String.valueOf(true);
    }
}