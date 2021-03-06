/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.hlj.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.hlj.domain.Users;
import com.hlj.service.inter.UsersServiceInter;
import com.hlj.struts.form.UserForm;
import com.sina.service.imp.UserServiceImp;

/** 
 * MyEclipse Struts
 * Creation date: 04-30-2015
 * 
 * XDoclet definition:
 * @struts.action parameter="flag"
 */
public class LoginAction extends DispatchAction {
	/*
	 * Generated Methods
	 */
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		UserForm userForm = (UserForm)form; 
		System.out.println(userForm.getUsername()+"这个表单中的用户名");
		Users user = new Users(); 
		user.setUsername(userForm.getUsername());
		user.setPassword(userForm.getPassword());
	//通过使用接口来进行调用
		UsersServiceInter userService = new UserServiceImp();  
		 System.out.println(user.getUsername()+userForm.getPassword()+"这就是用户名和密码"); 
		   
		if(userService.checkUser2(user)){  
			
		//	System.out.println(userService.checkUser(user)+"是答案"); 
			request.getSession().setAttribute("userinfo", user);
			 return mapping.findForward("loginok");
			  
		}else {
			
			return mapping.findForward("goLogin");
		}
		
		
	}
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		UserForm userForm = (UserForm)form;
		request.getSession().invalidate();
		return mapping.findForward("goLogin");
	
		
	}
	
}