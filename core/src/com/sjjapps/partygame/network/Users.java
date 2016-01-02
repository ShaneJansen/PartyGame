package com.sjjapps.partygame.network;

import com.sjjapps.partygame.common.models.User;

import java.util.HashSet;

/**
 * Created by Shane Jansen on 1/1/16.
 */
public class Users {
    private HashSet<User> mUsers;

    public Users() {
        mUsers = new HashSet<User>();
    }

    public HashSet<User> getUsers() {
        return mUsers;
    }
}
