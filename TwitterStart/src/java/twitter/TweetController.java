package twitter;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@MultipartConfig(maxFileSize = 1000000)
public class TweetController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        if (action == null) {
            action = "list_tweets";
        }

        if (action.equalsIgnoreCase("createTweet")) {
            String tweetText = request.getParameter("tweetText");
            if (tweetText == null || tweetText.trim().isEmpty()) {
                String error = "Type something!";
                request.setAttribute("error", error);
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            int like_count = 0;
            String username = (String) session.getAttribute("username");  

           
            Tweet tweetObject = new Tweet(0, tweetText, currentTime, user_id, like_count, username);
            try {
                TweetModel.addTweet(tweetObject);

                // Redirect to list tweets after creating a new tweet
                response.sendRedirect("TweetController?action=list_tweets");
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("error", "An error occurred while creating the tweet.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else if (action.equalsIgnoreCase("list_tweets")) {
            try {
                // Get the logged-in user's own tweets
                ArrayList<Tweet> userTweets = TweetModel.getUserTweets(user_id);

                // Get the tweets from the users that the logged-in user follows
                ArrayList<Tweet> followedTweets = TweetModel.getTweetsFromFollowedUsers(user_id);

                // Combine both lists into one
                ArrayList<Tweet> allTweets = new ArrayList<>();
                allTweets.addAll(userTweets);
                allTweets.addAll(followedTweets);

                // Set the combined list of tweets in the request scope
                request.setAttribute("tweets", allTweets);

                request.getRequestDispatcher("/home.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("error", "An error occurred while retrieving the tweets.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else if (action.equalsIgnoreCase("likeTweet")) {
            int tweetId = Integer.parseInt(request.getParameter("tweetId"));

            try {
                // Increment the like count for the tweet
                TweetModel.incrementLikeCount(tweetId);

                // Redirect back to the tweet list or refresh the page
                response.sendRedirect("TweetController?action=list_tweets");
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("error", "An error occurred while liking the tweet.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
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
        return "Tweet Controller Servlet";
    }
}
