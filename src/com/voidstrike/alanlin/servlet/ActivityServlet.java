package com.voidstrike.alanlin.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.voidstrike.alanlin.dbmgr.DBMgr;
import com.voidstrike.alanlin.logic.Activity;
import com.voidstrike.alanlin.logic.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class PostServlet
 */
@WebServlet("/ActivityServlet")
public class ActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivityServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		JSONObject json = new JSONObject();
		
		DBMgr tmp = new DBMgr();
		// Get cookie
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("email");
		String workPattern = (String) request.getParameter("pattern");
		if (userID == null){
			json.put("flag", false);
			json.put("msg", "user doesn't login");
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
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = dateFormat.format(now);
		Map<Integer, LinkedList<Activity>> activities = new HashMap<Integer, LinkedList<Activity>>();
		
		if (workPattern == null){
			// Error workPattern
			json.put("flag", false);
			json.put("msg", "unsupported operation");
		}
		else if (workPattern.equals("join")){
			// Join Activity
			String activityId = (String) request.getParameter("activity_id");
			Activity targetAc = new Activity();
			targetAc.setActivityId(activityId);
			currentUser.joinActivity(targetAc, tmp);
			json.put("flag", true);
			json.put("msg", "join activity success");
		}
		else if (workPattern.equals("create")){
			// Create Activity;
			Activity targetAc = new Activity();
			String title = (String) request.getParameter("title");
			String location = (String) request.getParameter("location");
			String tag = (String) request.getParameter("tag");
			targetAc.setTitle(title);
			targetAc.setLocation(location);
			targetAc.setTag(tag);
			targetAc.setTime(currentDate);
			currentUser.createActivity(targetAc, tmp);
			json.put("flag", true);
			json.put("msg", "create activity success");
		}
		else if (workPattern.equals("view")){
			// Return List of Activities
			ResultSet rs = tmp.getActivityList();
			LinkedList<Activity> auxList = new LinkedList<>();
			Activity currentAc;
			json.put("flag", true);
			int i=0;
			try {
				while(rs.next()){
					currentAc = new Activity();
					currentAc.setActivityId(rs.getString("id"));
					currentAc.setTitle(rs.getString("title"));
					currentAc.setLocation(rs.getString("location"));
					currentAc.setTime(rs.getString("time"));
					currentAc.setTag(rs.getString("tag"));
					auxList.add(currentAc);
					activities.put(i, auxList);
					i++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			JSONArray tmpArray = new JSONArray();
			for(Activity each : auxList){
				tmpArray.add(each.getJSONObject());
			}
			json.put("activities", tmpArray);
			session.setAttribute("actmsg", auxList);
			RequestDispatcher rsd = request.getRequestDispatcher("index.jsp");
			rsd.forward(request, response);
		}
		else{
			// Error workPattern
			json.put("flag", false);
			json.put("msg", "unsupported operation");
		}
	
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
