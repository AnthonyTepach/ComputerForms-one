package com.anthonytepach.computerforms;

import com.google.firebase.auth.FirebaseUser;

public class GetSet {

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    private FirebaseUser user;


}
