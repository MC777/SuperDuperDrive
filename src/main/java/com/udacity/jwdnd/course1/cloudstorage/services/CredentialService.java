package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public Credential uploadCredential(String url,
                                       String username,
                                       String password,
                                       Long userid) throws IOException {
        Credential newCredential = new Credential(
                url,
                username,
                password,
                userid
        );
        try {
            credentialMapper.save(newCredential);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newCredential;
    }

    public Credential updateCredential(Long credentialId,
                                       String credentialUrl,
                                       String credentialUsername,
                                       String credentialPassword,
                                       Long userid) throws IOException {
        Credential newCredential = new Credential(
                credentialId,
                credentialUrl,
                credentialUsername,
                credentialPassword,
                userid
        );
        try {
            credentialMapper.update(newCredential);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newCredential;
    }

    public List<Credential> getAllCredentials(Long userid) {
        return credentialMapper.findCredentialsByUserId(userid);
    }

    public void deleteCredential(Long credentialId) throws IOException {
        try {
            credentialMapper.deleteById(credentialId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
