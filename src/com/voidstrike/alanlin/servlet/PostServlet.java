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
import com.voidstrike.alanlin.dbmgr.DBMgr;
import com.voidstrike.alanlin.dbmgr.Singleton;
import com.voidstrike.alanlin.logic.User;

import net.sf.json.JSONObject;

import com.voidstrike.alanlin.logic.Post;

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
		response.setContentType("application/json;charset=utf-8");
		JSONObject json = new JSONObject();
		Singleton sin = new Singleton();
		DBMgr tmp = sin.getInstance();
		
		// Get cookie
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("email");
		String uID = (String) session.getAttribute("id");
		tmp = new DBMgr();
		if (userID == null){
			json.put("flag", false);
			json.put("msg", "user doesn;t login");
			try{
				response.getWriter().write(json.toString());
				response.getWriter().flush();
				response.getWriter().close();
			} catch(Exception e){
				e.printStackTrace();
			}
			return;
		}
		User currentUser = tmp.getUser(userID);
		/*String context = request.getParameter("text");
		String imgPath = request.getParameter("img");
		
		if ((context == null || context.trim().isEmpty()) && (imgPath == null || imgPath.trim().isEmpty())){
			json.put("flag", false);
			json.put("msg", "cannot post without context and image");
			try{
				response.getWriter().write(json.toString());
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
		
		// code added for posting text
		String postText = request.getParameter("postText");
		//User currentUser = new User("", userID);
		String name = null;
		if(postText == null ){
			if(ServletFileUpload.isMultipartContent(request)){
				
	            try {
	                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
	                for(FileItem item : multiparts){
	                    if(!item.isFormField()){
	                        name = new File(item.getName()).getName();
	                        item.write( new File(ResourcePath.userDirPath + File.separator +uID + File.separator + name));
	                    }
	                }
	               //File uploaded successfully
	        		
	        		json.put("msg", "{\"flag\":\"true\",\"msg\":\"post success\"}");
	        		Post newPost = new Post(null, "Users/".concat(uID).concat("/"+name), currentDate, null, 0);
	        		currentUser.post(newPost, tmp);
	               request.setAttribute("message", "File Uploaded Successfully");
	               request.getRequestDispatcher("/index.jsp").forward(request, response);
	            } catch (Exception ex) {
	            	json.put("msg", "{\"flag\":\"true\",\"msg\":\"post failed\"}");
	               request.setAttribute("message", "File Upload Failed due to " + ex);
	            }
	        }else{
	            request.setAttribute("message",
	                                 "Sorry this Servlet only handles file upload request");
	            json.put("msg", "{\"flag\":\"true\",\"msg\":\"post only handles file upload\"}");
	        }
		}else if(postText != null && !postText.equals("") ){
			
        		//currentUser.postComment(userID, postText, currentDate, "Users/".concat(userID).concat("/"+name));
        		Post newPost = new Post(postText, null, currentDate, null, 0);
        		currentUser.post(newPost, tmp);
        		request.setAttribute("message","Text post success");
        		json.put("msg", "{\"flag\":\"true\",\"msg\":\"text post success\"}");
        		request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		//
		
		
		
		//Post newPost = new Post(context, imgPath, currentDate, null, 0);
		//currentUser.post(newPost, tmp);
		
		json.put("flag", true);
		json.put("msg", "post success");
		try{
			response.getWriter().write(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch(Exception e){
			e.printStackTrace();
		}
		tmp.closeAll();
	}

}
