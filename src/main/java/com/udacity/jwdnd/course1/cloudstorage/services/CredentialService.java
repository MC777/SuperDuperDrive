package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
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
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        EncryptionService encryptionService = new EncryptionService();
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        Credential newCredential = new Credential(
                url,
                username,
                encryptedPassword,
                userid,
                encodedKey
        );
        try {
            credentialMapper.save(newCredential);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newCredential;
    }

    public Credential updateCredential(Long credentialId,
                                        String url,
                                        String username,
                                        String password,
                                        Long userid) throws IOException {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        EncryptionService encryptionService = new EncryptionService();
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        Credential newCredential = new Credential(
                credentialId,
                url,
                username,
                encryptedPassword,
                userid,
                encodedKey
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

    public Credential decodePassword(Long credentialId){
        return credentialMapper.findByCredentialId(credentialId);
    }
}
