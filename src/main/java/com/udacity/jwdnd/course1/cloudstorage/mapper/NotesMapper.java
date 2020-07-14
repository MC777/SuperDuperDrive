package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NotesMapper {
    @Select("SELECT * FROM notes")
    List<Note> findAll();

    @Select("SELECT * FROM notes WHERE userid = #{userid}")
    List<Note> findByUserId(Long userId);

    @Select("SELECT * FROM notes WHERE userid = #{userid} AND noteid = #{noteid} ")
    public Note findById(Long noteid, Long userid);

    @Insert("INSERT INTO notes (notetitle, notedescription, userid) VALUES (#{note.noteTitle}, #{note.noteDescription}, #{userid})")
    Integer create(@Param("note") Note note, Long userid);

    @Update("UPDATE notes SET notetitle = #{note.noteTitle}, notedescription = #{note.noteDescription} WHERE noteid = #{note.noteId} AND userid = #{userid}")
    Integer update(@Param("note") Note note, Long userid);

    @Delete("DELETE FROM notes WHERE noteid = #{id} AND userid = #{userid}")
    Integer delete(Long id, Long userid);
}
