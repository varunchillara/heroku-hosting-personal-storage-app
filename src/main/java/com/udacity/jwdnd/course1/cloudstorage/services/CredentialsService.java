package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {

    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public void createCredential(Credentials credential, Integer userId) {

        try {
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            //
            String keyOutput = Base64.getEncoder().encodeToString(key);

            credential.setUserid(userId);
            credential.setKey(keyOutput);
            String password = encryptionService.encryptValue(credential.getPassword(), keyOutput);
            credential.setPassword(password);
            credentialsMapper.insertCredential(credential);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public void updateCredential(Credentials credential) {

        try {
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            //

            String keyOutput = Base64.getEncoder().encodeToString(key);
            credential.setKey(keyOutput);
            String password = encryptionService.encryptValue(credential.getPassword(), keyOutput);
            credential.setPassword(password);
            credentialsMapper.updateNote(credential);

        }catch(Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public Credentials getCredential(Integer credentialId) {
        Credentials credentials = credentialsMapper.getCredentialByCredentialId(credentialId);
        String decryptedPass = encryptionService.decryptValue(credentials.getPassword(), credentials.getKey());
        credentials.setPassword(decryptedPass);
        return  credentials;
    }

    public void deleteCredential(Integer credentialId) {
        credentialsMapper.deleteCredential(credentialId);
    }

    public List<Credentials> getCredentials(Integer userId) {
        return credentialsMapper.getCredentials(userId);
    }

}
