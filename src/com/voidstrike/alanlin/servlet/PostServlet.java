package com.voidstrike.alanlin.servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.voidstrike.alanlin.dao.ResourcePath;
import com.voidstrike.alanlin.dao.UserDao;
import com.voidstrike.alanlin.user.User;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 * Servlet implementation class PostServlet
 */
@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String json = null;
		// Get cookie
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("id");
		if (userID == null){
			json = "{\"flag\":\"false\",\"msg\":\"user doesn't login\"}";
			try{
				response.getWriter().print(json);
				response.getWriter().flush();
				response.getWriter().close();
			} catch(Exception e){
				e.printStackTrace();
			}
			return;
		}
		/*String context = request.getParameter("text");
		String imgPath = request.getParameter("img");
		if ((context == null || context.trim().isEmpty()) && (imgPath == null || imgPath.trim().isEmpty())){
			json = "{\"flag\":\"false\",\"msg\":\"cannot post without context and image\"}";
			try{
				response.getWriter().print(json);
				response.getWriter().flush();
				response.getWriter().close();
			} catch(Exception e){
				e.printStackTrace();
			}
			return;
		}*/
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = dateFormat.format(now);
		String postText = request.getParameter("postText");
		User currentUser = new User("", userID);
		String name = null;
		if(ServletFileUpload.isMultipartContent(request)){
			if(postText != null && !postText.equals("") )
    			currentUser.postComment(postText, currentDate, ResourcePath.userDirPath + File.separator +userID + File.separator + name);
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                        name = new File(item.getName()).getName();
                        item.write( new File(ResourcePath.userDirPath + File.separator +userID + File.separator + name));
                    }
                }
               //File uploaded successfully
        		
        		json = "{\"flag\":\"true\",\"msg\":\"post success\"}";
               request.setAttribute("message", "File Uploaded Successfully");
               request.getRequestDispatcher("/index.jsp").forward(request, response);
            } catch (Exception ex) {
            	json = "{\"flag\":\"true\",\"msg\":\"post failed\"}";
               request.setAttribute("message", "File Upload Failed due to " + ex);
            }
        }else{
            request.setAttribute("message",
                                 "Sorry this Servlet only handles file upload request");
            json = "{\"flag\":\"true\",\"msg\":\"post only handles file upload\"}";
        }
		try{
			response.getWriter().print(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
