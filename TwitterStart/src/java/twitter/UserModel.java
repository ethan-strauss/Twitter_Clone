package twitter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author ethan
 */
public class UserModel {

    // Follow a user
    public static void FollowUser(int followerId, int followingId) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "INSERT INTO following (followed_by_user_id, following_user_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, followerId);
            statement.setInt(2, followingId);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    // Unfollow a user
    public static void UnfollowUser(int followerId, int followingId) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "DELETE FROM following WHERE followed_by_user_id = ? AND following_user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, followerId);
            statement.setInt(2, followingId);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    // Login a user
    public static boolean login(User user) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT id, username, password FROM user WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            ResultSet results = statement.executeQuery();

            String password = "";
            if (results.next()) {
                password = results.getString("password");
            }

            results.close();
            statement.close();
            connection.close();

            return !password.isEmpty() && user.getPassword().equals(password);
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    // Get user by username
    public static User getUser(String username) {
        User user = null;
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT id, username, password, filename FROM user WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                int id = results.getInt("id");
                String password = results.getString("password");
                String filename = results.getString("filename");
                user = new User(id, username, password, filename);
            }

            results.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return user;
    }

    // Get user ID by username
    public static int getUserIdByUsername(String username) {
        int userId = -1;
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT id FROM user WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getInt("id");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return userId;
    }

    // Get all users
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM user");

            while (results.next()) {
                int id = results.getInt("id");
                String username = results.getString("username");
                String password = results.getString("password");
                String filename = results.getString("filename");

                User user = new User(id, username, password, filename);
                users.add(user);
            }

            results.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return users;
    }

    // Add a new user
    public static void addUser(User user) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "INSERT INTO user (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.execute();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    // Update an existing user
    public static void updateUser(User user) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "UPDATE user SET username = ?, password = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getId());
            statement.execute();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    // Get user by ID
    public static User getUserById(int id) {
        User user = null;
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT id, username, password, filename FROM user WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                int userId = results.getInt("id");
                String username = results.getString("username");
                String password = results.getString("password");
                String filename = results.getString("filename");
                user = new User(userId, username, password, filename);
            }

            results.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return user;
    }

    // Get user by username (alternative method)
    public static User getUsername(String username) {
        User user = null;
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM user WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("filename"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return user;
    }
}
