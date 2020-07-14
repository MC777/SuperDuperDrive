package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoteService {

    private NotesMapper notesMapper;

    @Autowired
    public NoteService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public List<Note> getAll() {
        return notesMapper.findAll();
    }

    public List<Note> getAllByUserId(Long userid) {
        return notesMapper.findByUserId(userid);
    }

    public Note getById(Long id, Long userid) {
        return notesMapper.findById(id, userid);
    }

    public Boolean isNoteExists(Long id, Long userid) throws Exception {
        try {
            Note note = notesMapper.findById(id, userid);
            if (note == null) {
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        }  catch (Exception e) {
            throw e;
        }
    }

    public boolean create(Note note, Long userid) {
        return notesMapper.create(note, userid) > 0;
    }

    public boolean update(Note note, Long userid) {
        return notesMapper.update(note, userid) > 0;
    }

    public boolean delete(Long id, Long userid) {
        return notesMapper.delete(id, userid) > 0;
    }
}
