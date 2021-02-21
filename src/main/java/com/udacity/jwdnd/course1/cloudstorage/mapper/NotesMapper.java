package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NotesMapper {

    @Select("SELECT * FROM NOTES")
    List<Notes> allNotes();

    @Select("SELECT * FROM NOTES WHERE nodeId = #{noteId}")
    public Notes noteByNoteId(Integer noteId);

    @Select("SELECT * FROM NOTES WHERE userId = #{userid}")
    public List<Notes> notesByUserId(Integer userId);


    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    public int insertNotes(Notes note);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteid}")
    public int deleteNote(int noteId);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} " +
            "WHERE noteid = #{noteId}")
    public int updateNote(Notes notes);
}