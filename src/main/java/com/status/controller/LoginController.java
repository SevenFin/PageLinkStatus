package com.status.controller;

import com.status.model.User;
import com.status.model.Vendor;
import com.status.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by SevenFin on 2015/9/17.
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private IUserService userService;

    /**
     * @author Lenovo
     * 登录请求
     */
    @RequestMapping("/login")
    public String login(Model model, @ModelAttribute User user, HttpSession session,HttpServletRequest request) {

        user = userService.selectUser(user.getUsername(),user.getPassword());
        if(user!=null){
            int roleId = user.getRoleid();
            session.setAttribute("user", user);
            session.setAttribute("role", roleId);
            List<String> list ;
            if(roleId==1){
                list = userService.selectVendor(null,true);
            }else{
                list = userService.selectVendor(user.getUserid(),false);
            }
            session.setAttribute("vendorList", list);

            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            int nowYear = Integer.parseInt(format.format(new Date()));
            List<Integer> yearList = new ArrayList<Integer>();
            for (int i = nowYear; i >= 2000; i--) {
                yearList.add(i);
            }
            session.setAttribute("yearList", yearList);
            session.setAttribute("ip", request.getRemoteAddr());
            return "user/menu";
        }else{
            model.addAttribute("message","用户名或密码错误");
            return "index";
        }
    }

}
