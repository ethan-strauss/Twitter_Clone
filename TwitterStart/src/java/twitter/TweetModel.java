package twitter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class TweetModel {

    // Method to add a new tweet
    public static void addTweet(Tweet tweet) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "INSERT INTO tweet (user_id, text, timestamp, likeCount) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, tweet.getUser_id());
            statement.setString(2, tweet.getText());
            statement.setTimestamp(3, tweet.getTimestamp());
            statement.setInt(4, tweet.getLike_count());

            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println("Error adding tweet to database: " + ex.getMessage());
        }
    }

    // Method to retrieve tweets for a specific user
    public static ArrayList<Tweet> getUserTweets(int user_id) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT tweet.id, tweet.text, tweet.timestamp, tweet.likeCount, user.username " +
                           "FROM tweet INNER JOIN user ON tweet.user_id = user.id " +
                           "WHERE tweet.user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user_id);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                int id = results.getInt("id");
                String text = results.getString("text");
                Timestamp timestamp = results.getTimestamp("timestamp");
                int likeCount = results.getInt("likeCount");
                String username = results.getString("username");

                Tweet tweet = new Tweet(id, text, timestamp, user_id, likeCount, username);
                tweets.add(tweet);
            }

            results.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println("Error retrieving tweets: " + ex.getMessage());
        }
        return tweets;
    }

    // Method to retrieve tweets from followed users
    public static ArrayList<Tweet> getTweetsFromFollowedUsers(int userId) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT tweet.id, tweet.text, tweet.timestamp, tweet.likeCount, user.username, tweet.user_id " +
                           "FROM tweet " +
                           "INNER JOIN follow ON tweet.user_id = follow.followed_id " +
                           "INNER JOIN user ON tweet.user_id = user.id " +
                           "WHERE follow.follower_id = ? " +
                           "ORDER BY tweet.timestamp DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                int id = results.getInt("id");
                String text = results.getString("text");
                Timestamp timestamp = results.getTimestamp("timestamp");
                int likeCount = results.getInt("likeCount");
                String username = results.getString("username");
                int tweetUserId = results.getInt("user_id");

                Tweet tweet = new Tweet(id, text, timestamp, tweetUserId, likeCount, username);
                tweets.add(tweet);
            }

            results.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println("Error retrieving tweets from followed users: " + ex.getMessage());
        }
        return tweets;
    }

    // Increment like count for a tweet
    public static void incrementLikeCount(int tweetId) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "UPDATE tweet SET likeCount = likeCount + 1 WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, tweetId);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
