package com.status.service.impl;

import com.status.dao.IUserDao;
import com.status.model.User;
import com.status.model.Vendor;
import com.status.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SevenFin on 2015/9/11.
 */

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDao userDAO;

    public User selectUser(String username, String password) {

        return userDAO.selectUser(username,password);
    }

    public List<String> selectVendor(Integer userId, boolean isAll) {

        List<Vendor> list = null;
        if(isAll){
            list = userDAO.selectAllVendor();
        }else{
            list = userDAO.selectVendorByUser(userId);
        }
        List<String> vendorList = new ArrayList<String>();
        for (Vendor vendor : list) {
            vendorList.add(vendor.getVendor());
        }
        return vendorList;
    }
}
