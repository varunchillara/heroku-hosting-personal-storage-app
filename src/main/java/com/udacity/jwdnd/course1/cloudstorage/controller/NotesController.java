package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.customexceptions.ExceedsLengthException;
import com.udacity.jwdnd.course1.cloudstorage.customexceptions.RepeatedNoteException;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class NotesController {

    @Autowired
    NotesService notesService;
    @Autowired
    UsersService usersService;

    public boolean isRepeatNote(String title, String description, List<Notes> notesList) {

        for(Notes note:notesList) {
            if(title.equals(note.getNoteTitle()) && description.equals(note.getNoteDescription())) {
                return true;
            }
        }
        return false;
    }

    @PostMapping("/notes")
    public String addNote(Authentication authentication, Notes note, RedirectAttributes redirectAttributes) {

        try {

            String username = (String) authentication.getPrincipal();
            Users user = usersService.getUser(username);

            List<Notes> notesList = notesService.getAllNotes(user.getUserId());

            if(note.getNoteDescription().length() > 999 || note.getNoteTitle().length() > 20) {
                throw new ExceedsLengthException();
            }

            if(isRepeatNote(note.getNoteTitle(), note.getNoteDescription(), notesList) == true) {
                throw new RepeatedNoteException();
            }

            if (note.getNoteId() != null) {
                notesService.updateNote(note);
                redirectAttributes.addFlashAttribute("successUpdateNote", "your note has been successfully updated!");
            } else {
                notesService.addNote(note, user.getUserId());
                redirectAttributes.addFlashAttribute("successAddNote", "your note has been successfully added!");
            }

        }catch(ExceedsLengthException e) {
            redirectAttributes.addFlashAttribute("failedToAddNoteOrEdit", "length of note title and/or note description are too long! note title must be less than 20 characters and note description must be less than 999 characters");
            return "redirect:/home";

        } catch (RepeatedNoteException f) {
            redirectAttributes.addFlashAttribute("noteAlreadyExists", "note already exists");
            return "redirect:/home";

        } catch(Exception g) {
            redirectAttributes.addFlashAttribute("failedToAddNoteOrEdit", "note could not be added or edited due to unknown reason. try again. if it still fails please contact our website admins");
            return "redirect:/home";
        }

        return "redirect:/home";
    }

    @GetMapping("/notes/delete/{id}")
    public String deleteNotes(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        Integer noteId = Integer.parseInt(id);
        notesService.deleteNote(noteId);
        redirectAttributes.addFlashAttribute("successDeleteNote", "your note has been successfully deleted!");

        return "redirect:/home";
    }
}
