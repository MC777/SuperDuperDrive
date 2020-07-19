package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url, username, password, userid) " +
            "VALUES(#{credential.url}, #{credential.userName}, #{credential.password}, #{credential.userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer save(@Param("credential") Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> findCredentialsByUserId(@Param("userid") Long userid);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    void deleteById(@Param("credentialId") Long credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{credential.url}, username = #{credential.userName}, password = #{credential.password} " +
            "WHERE credentialId = #{credential.credentialId}")
    void update(@Param("credential") Credential credential);
}
