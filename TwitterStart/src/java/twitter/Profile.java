package twitter;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Profile extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Ensure the user is logged in
        if (!Login.ensureUserIsLoggedIn(request)) {
            request.setAttribute("message", "You must log in.");
            response.sendRedirect("Login");
            return;
        }

        HttpSession session = request.getSession();
        String currentUsername = (String) session.getAttribute("username");
        Integer currentUserId = (Integer) session.getAttribute("user_id");  // Retrieve the user's ID

        // If no username parameter is provided, assume the user is viewing their own profile
        String profileUsername = request.getParameter("username");
        if (profileUsername == null) {
            profileUsername = currentUsername;
        }

        User currentUser = UserModel.getUser(currentUsername);
        User profileUser = UserModel.getUser(profileUsername);
        
        if (profileUser == null) {
            response.sendRedirect("/error.jsp");
            return;
        }

        try {
            // Check if the current user is following the profile user
            boolean isFollowing = FollowModel.isFollowing(currentUser.getId(), profileUser.getId());

            // Get the list of users the profile user is following
            ArrayList<User> followingList = FollowModel.getFollowing(profileUser.getId());
            // Get the list of users who follow the profile user
            ArrayList<User> followersList = FollowModel.getFollowers(profileUser.getId());

            // Retrieve the profile user's tweets including photo tweets
            ArrayList<Tweet> profileUserTweets = TweetModel.getUserTweets(profileUser.getId());

            // Set attributes for the JSP page
            request.setAttribute("isFollowing", isFollowing);
            request.setAttribute("profileUser", profileUser);
            request.setAttribute("filename", profileUser.getFilename());
            request.setAttribute("followingList", followingList);
            request.setAttribute("followersList", followersList);
            request.setAttribute("tweets", profileUserTweets);  // Add the user's tweets

            // Forward to the profile.jsp page
            String url = "/profile.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect("/error.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles user profiles and follow/unfollow logic";
    }
}
