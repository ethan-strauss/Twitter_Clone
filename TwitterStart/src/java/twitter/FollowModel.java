
package twitter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ethan
 */
public class FollowModel {
    public static void addFollow(int followerId, int followedId) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO follow (follower_id, followed_id ) VALUES (?, ?)";
        
        try(Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)){
                    
                statement.setInt(1, followerId);
                statement.setInt(2, followedId);
                
                statement.executeUpdate();
                }
    }
    
    public static void removeFollow(int followerId, int followedId) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM follow WHERE follower_id = ? AND followed_id = ? ";
        
         try(Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)){
                    
                statement.setInt(1, followerId);
                statement.setInt(2, followedId);
                
                statement.executeUpdate();
                }
    }
    
    public static boolean isFollowing(int followerId, int followedId) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM follow WHERE follower_id = ? AND followed_id = ? ";
        
        try(Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)){
                    
                statement.setInt(1, followerId);
                statement.setInt(2, followedId);
                
                ResultSet resultSet = statement.executeQuery();
                return resultSet.next();
                }
    }
    //list of users user is following
    public static ArrayList<User> getFollowing(int userId) throws SQLException, ClassNotFoundException {
        String query = "SELECT u.* FROM user u INNER JOIN follow f ON u.id = f.followed_id WHERE f.follower_id = ? ";
        ArrayList<User> followingList = new ArrayList<>();
        
        try(Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, userId);
            
            ResultSet resultSet = statement.executeQuery();{
            
            while(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setFilename(resultSet.getString("filename"));

                followingList.add(user);
            }
        }
        return followingList;
            }
        }
    //list of users who follow user 
    public static ArrayList<User> getFollowers(int userId) throws SQLException, ClassNotFoundException {
        String query = "SELECT u.* FROM user u INNER JOIN follow f ON u.id = f.follower_id WHERE f.followed_id = ?";
        ArrayList<User> followersList = new ArrayList<>();
        
        try(Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)){
            
             statement.setInt(1, userId);
             ResultSet resultSet = statement.executeQuery();
              while(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                //user.setImage(resultSet.getBytes("image"));
                user.setFilename(resultSet.getString("filename"));

                followersList.add(user);
            }
              return followersList;
        }
    }
}
