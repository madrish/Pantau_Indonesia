package com.pantauindonesia.app;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Nailul on 12/6/2015.
 */

@ParseClassName("Comment")
public class Comment extends ParseObject {
    public String getUserId() {
        return getString("userId");
    }

    public String getBody() {
        return getString("commentList");
    }

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public void setBody(String comment) {
        put("commentList", comment);
    }
}