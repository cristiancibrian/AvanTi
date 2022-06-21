package com.certuit.saml2.logic;

import com.certuit.saml2.entity.User;

public class UserManager {

    /**
     * Get an user
     *
     * @param userName
     * @param password
     * @return user
     */
    public User getUser(String userName, String password) {
        //TODO:Stub Implementation        
        return new User(userName, password, 56789);
    }
}
