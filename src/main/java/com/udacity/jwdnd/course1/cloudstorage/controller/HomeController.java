package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private NotesService notesService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private FilesService filesService;

    @GetMapping(value = {"/home", "/"})
    public ModelAndView getHome(Authentication authentication, Model model) throws Exception {

        try {

            String username= null;
            Users user = null;

            username = (String) authentication.getPrincipal();
            user = usersService.getUser(username);

            if(user == null) {
                return new ModelAndView(("login"));
            }

            ModelAndView modelAndView = new ModelAndView("home");
            modelAndView.addObject("notes", notesService.getAllNotes(user.getUserId()));
            modelAndView.addObject("credentials", credentialsService.getCredentials(user.getUserId()));
            modelAndView.addObject("encryptionService", encryptionService);
            modelAndView.addObject("files", filesService.getUserFiles(user.getUserId()));

            if(user == null) {
                throw new NullPointerException();
            }

            return modelAndView;

        }catch(NullPointerException e) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("errorMessage", true);

            return modelAndView;
        }

    }

}
