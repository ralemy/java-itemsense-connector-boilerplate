package com.impinj.rtls.auth;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ralemy on 8/9/16.
 * the responsibility of this class is to represent the user of the api.
 */

public class ApiUser implements Principal {
    private final int hash;
    public String username;
    private List<String> roles;

    public ApiUser(String username, int hashCode){
        this.username = username;
        this.hash = hashCode;
        roles = new ArrayList<String>(10);
        roles.add("user");
    }

    @Override
    public boolean equals(Object another) {
        if(another instanceof ApiUser)
            if(((ApiUser) another).username.equals(this.username))
                return true;
        return false;
    }

    @Override
    public String toString() {
        return "User: "+ this.username;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public String getName() {
        return username;
    }
}
