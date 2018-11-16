package com.jbsx.view.myinfo.data;

/**
 * 评论相关Event bus时间
 */
public class CommentEvent {
    private int type;
    int position;
    private UserComments mUserComments;

    public CommentEvent(int type, UserComments comments) {
        setType(type);
        setUserComments(comments);
    }

    public CommentEvent(int position, int type, UserComments comments) {
        this(type, comments);
        setPosition(position);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public UserComments getUserComments() {
        return mUserComments;
    }

    public void setUserComments(UserComments userComments) {
        mUserComments = userComments;
    }
}
