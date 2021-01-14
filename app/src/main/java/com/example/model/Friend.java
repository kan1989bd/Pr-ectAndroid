package com.example.model;

public class Friend {
    private String myId;
    private String friendId;
    private String status;

    public Friend(String myId, String friendId, String status) {
        this.myId = myId;
        this.friendId = friendId;
        this.status = status;
    }
    public Friend(){}

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
