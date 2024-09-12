package twitter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FollowServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer followerId = (Integer) session.getAttribute("user_id");

        if (followerId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("unfollowById".equalsIgnoreCase(action)) {
                String unfollowIdStr = request.getParameter("unfollowId");
                if (unfollowIdStr != null && !unfollowIdStr.isEmpty()) {
                    int unfollowId = Integer.parseInt(unfollowIdStr);

                    // Ensure the user exists in the database
                    User unfollowUser = UserModel.getUserById(unfollowId);
                    if (unfollowUser == null) {
                        request.setAttribute("error", "User with ID " + unfollowId + " not found.");
                        request.getRequestDispatcher("/profile.jsp").forward(request, response);
                        return;
                    }

                    FollowModel.removeFollow(followerId, unfollowId);
                } else {
                    request.setAttribute("error", "Invalid user ID for unfollow.");
                    request.getRequestDispatcher("/profile.jsp").forward(request, response);
                    return;
                }
            } else if ("followById".equalsIgnoreCase(action)) {
                String followIdStr = request.getParameter("followId");
                if (followIdStr != null && !followIdStr.isEmpty()) {
                    int followId = Integer.parseInt(followIdStr);

                    // Ensure the user exists in the database
                    User followUser = UserModel.getUserById(followId);
                    if (followUser == null) {
                        request.setAttribute("error", "User with ID " + followId + " not found.");
                        request.getRequestDispatcher("/profile.jsp").forward(request, response);
                        return;
                    }

                    // Prevent the user from following themselves
                    if (followerId == followId) {
                        request.setAttribute("error", "You cannot follow yourself.");
                        request.getRequestDispatcher("/profile.jsp").forward(request, response);
                        return;
                    }

                    FollowModel.addFollow(followerId, followId);
                } else {
                    request.setAttribute("error", "Invalid user ID for follow.");
                    request.getRequestDispatcher("/profile.jsp").forward(request, response);
                    return;
                }
            } else {
                // Existing follow/unfollow logic for following by username
                String username = request.getParameter("username");
                if (username == null || action == null) {
                    response.sendRedirect("/error.jsp");
                    return;
                }

                User followedUser = UserModel.getUsername(username);
                if (followedUser == null) {
                    response.sendRedirect("/error.jsp");
                    return;
                }

                int followedId = followedUser.getId();

                // Prevent the user from following themselves
                if (followerId == followedId) {
                    request.setAttribute("error", "You cannot follow yourself.");
                    request.getRequestDispatcher("/profile.jsp").forward(request, response);
                    return;
                }

                if (action.equalsIgnoreCase("follow")) {
                    // Follow the user
                    FollowModel.addFollow(followerId, followedId);
                } else if (action.equalsIgnoreCase("unfollow")) {
                    // Unfollow the user
                    FollowModel.removeFollow(followerId, followedId);
                }
            }

            // Redirect back to the profile page to refresh the list
            response.sendRedirect("Profile?username=" + session.getAttribute("username"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "An unexpected error occurred: " + ex.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
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
        return "Follow and Unfollow Servlet";
    }
}
