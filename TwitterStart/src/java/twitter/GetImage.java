
package twitter;



import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ethan
 */
public class GetImage extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
           String userName = request.getParameter("username");
           
           try {
               Connection connection = DatabaseConnection.getConnection();
               String preparedSQL = "select image, filename from user where username = ?";
               PreparedStatement preparedStatement = connection.prepareStatement(preparedSQL);
               
               preparedStatement.setString(1, userName);
               
               ResultSet result = preparedStatement.executeQuery();
               
               Blob blob = null;
               String filename = "";
               
               while (result.next() ){
                   blob = result.getBlob("image");
                   filename = result.getString("filename");
               }
               byte[] imageBytes = blob.getBytes(1, (int)blob.length());
               
               preparedStatement.close();
               connection.close();
               
               String contentType = this.getServletContext().getMimeType(filename);
               
               response.setHeader("Content-Type", contentType);
               
               OutputStream os = response.getOutputStream();
               os.write(imageBytes);
               os.flush();
               os.close();
           }   catch (ClassNotFoundException exception) {
               System.out.println(exception);
           } catch (SQLException ex) {
            Logger.getLogger(GetImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        //save test
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
