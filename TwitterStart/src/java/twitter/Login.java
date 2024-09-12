package twitter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static twitter.Twitter.GFG2.getSHA;
import static twitter.Twitter.GFG2.toHexString;

public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (action.equalsIgnoreCase("login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (username == null || password == null) {
                request.setAttribute("error", "Username or password is missing.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            try {
                String hashedPassword = toHexString(getSHA(password));
                User user = new User(0, username, hashedPassword);

                if (UserModel.login(user)) {
                    HttpSession session = request.getSession();
                    int userId = UserModel.getUserIdByUsername(username);
                    session.setAttribute("user_id", userId);
                    session.setAttribute("username", username);

                    response.sendRedirect("TweetController?action=list_tweets");
                } else {
                    request.setAttribute("error", "Invalid username or password");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
            } catch (Exception ex) {
                exceptionPage(ex, request, response);
            }
        } else if (action.equalsIgnoreCase("register")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (username == null || password == null) {
                request.setAttribute("error", "Username or password is missing.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            try {
                String hashedPassword = toHexString(getSHA(password));
                User user = new User(0, username, hashedPassword);

                UserModel.addUser(user);

                HttpSession session = request.getSession();
                int userId = UserModel.getUserIdByUsername(username);
                session.setAttribute("user_id", userId);  // Consistent attribute name
                session.setAttribute("username", username);

                response.sendRedirect("TweetController?action=list_tweets");
            } catch (Exception ex) {
                exceptionPage(ex, request, response);
            }
        }
    }

    private void exceptionPage(Exception ex, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("error", ex.toString());
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }

    public static boolean ensureUserIsLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session.getAttribute("username") != null;
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
        return "Login Servlet";
    }
}
