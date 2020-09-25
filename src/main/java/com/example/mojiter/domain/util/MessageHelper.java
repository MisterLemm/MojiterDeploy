package com.example.mojiter.domain.util;

import com.example.mojiter.domain.User;

public abstract class MessageHelper {
    public static String getAuthorName(User author){
        return author != null ? author.getUsername() : "<none>";
    }

}
