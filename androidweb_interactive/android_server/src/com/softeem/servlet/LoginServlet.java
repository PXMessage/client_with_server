package com.softeem.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.softeem.bean.LoginInfo;

public class LoginServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request,response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			String name = request.getParameter("username");
			String pass = request.getParameter("password");
			LoginInfo loginInfo = null;
			if("admin".equals(name)){
				if("123456".equals(pass)){
					loginInfo = new LoginInfo(1, "登录成功", true);
					JSONObject jsonObj = JSONObject.fromObject(loginInfo);
					out.println(jsonObj.toString());
				}else{
					loginInfo = new LoginInfo(0, "密码错误", false);
					JSONObject jsonObj = JSONObject.fromObject(loginInfo);
					out.println(jsonObj.toString());
				}
			}else{
				loginInfo = new LoginInfo(-1, "用户不存在", false);
				JSONObject jsonObj = JSONObject.fromObject(loginInfo);
				out.println(jsonObj.toString());
			}
			out.flush();
			out.close();
	}

}
