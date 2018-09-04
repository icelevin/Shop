package com.jyunmore.lib_ec.datebase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "user_profile")
public class UserProfile {
    @Id
    private String objectId;
    private String email;
    private String sessionToken;
    private String username;
    private String mobilePhoneNumber;
    @Generated(hash = 1817393973)
    public UserProfile(String objectId, String email, String sessionToken,
            String username, String mobilePhoneNumber) {
        this.objectId = objectId;
        this.email = email;
        this.sessionToken = sessionToken;
        this.username = username;
        this.mobilePhoneNumber = mobilePhoneNumber;
    }
    @Generated(hash = 968487393)
    public UserProfile() {
    }
    public String getObjectId() {
        return this.objectId;
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSessionToken() {
        return this.sessionToken;
    }
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getMobilePhoneNumber() {
        return this.mobilePhoneNumber;
    }
    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }



}
