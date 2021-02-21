package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialsController {

    @Autowired
    CredentialsService credentialsService;
    @Autowired
    UsersService usersService;

    @PostMapping("/credentials")
    public String addOrUpdateCredential(Authentication authentication, Credentials credential, RedirectAttributes redirectAttributes) {

        String username  = (String) authentication.getPrincipal();
        Users user = usersService.getUser(username);

        if(credential.getCredentialId() != null) {
            credentialsService.updateCredential(credential);
            redirectAttributes.addFlashAttribute("successUpdateCredential", "your credential has been successfully updated!");

        }else {
            credentialsService.createCredential(credential, user.getUserId());
            redirectAttributes.addFlashAttribute("successAddCredential", "your credential has been successfully added!");
        }

        return "redirect:/home";
    }

    @GetMapping("/credentials/delete/{id}")
    public String deleteCredential(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        credentialsService.deleteCredential(id);

        redirectAttributes.addFlashAttribute("credentialDeleted", "your credential has been successfully deleted!");

        return "redirect:/home";
    }

}
