package twitter;

import java.sql.Timestamp;

public class Tweet {
    private int id;
    private String text;
    private Timestamp timestamp;
    private int user_id;
    private int like_count;
    private String username;

    // Constructor
    public Tweet(int id, String text, Timestamp timestamp, int user_id, int like_count, String username) {
        this.id = id;
        this.text = text;
        this.timestamp = timestamp;
        this.user_id = user_id;
        this.like_count = like_count;
        this.username = username;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
