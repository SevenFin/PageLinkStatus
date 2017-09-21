package com.status.service;

import com.status.model.User;
import com.status.model.Vendor;

import java.util.List;

/**
 * Created by SevenFin on 2015/9/11.
 */
public interface IUserService {

    public User selectUser(String username, String password);

    public List<String> selectVendor(Integer userId, boolean isAll);
}
