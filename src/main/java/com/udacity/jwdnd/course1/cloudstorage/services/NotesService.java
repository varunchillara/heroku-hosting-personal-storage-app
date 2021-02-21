package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    private NotesMapper notesMapper;

    public NotesService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public List<Notes> getAllNotes(Integer userId) throws Exception {
        List<Notes> notesList = notesMapper.notesByUserId(userId);
        return notesList;
    }

    public void addNote(Notes note, Integer userId) {
        note.setUserId(userId);
        notesMapper.insertNotes(note);
    }

    public Notes getNote(Integer noteId) {
        Notes note = notesMapper.noteByNoteId(noteId);
        return note;
    }

    public void updateNote(Notes note) {
        notesMapper.updateNote(note);
    }

    public void deleteNote(int noteId) {
        notesMapper.deleteNote(noteId);
    }

}
