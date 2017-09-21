package com.status.dao;

import com.status.model.User;
import com.status.model.Vendor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by SevenFin on 2015/9/11.
 */
public interface IUserDao {

    public User selectUser(@Param("username")String username, @Param("password")String password);

    public List<Vendor> selectAllVendor();

    public List<Vendor> selectVendorByUser(@Param("userId")int userId);
}
