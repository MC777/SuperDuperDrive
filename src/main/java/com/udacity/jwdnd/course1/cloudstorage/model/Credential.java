package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {
    private Long credentialId;
    private String url;
    private String userName;
    private String key;
    private String password;
    private String decryptedPassword;

    public Credential() { }

    public Credential(Long credentialId, String url, String userName, String key, String password) {
        this.credentialId = credentialId;
        this.url = url;
        this.userName = userName;
        this.key = key;
        this.password = password;
    }

    public Long getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Long credentialId) {
        this.credentialId = credentialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDecryptedPassword() {
        return decryptedPassword;
    }

    public void setDecryptedPassword(String decryptedPassword) {
        this.decryptedPassword = decryptedPassword;
    }
}
