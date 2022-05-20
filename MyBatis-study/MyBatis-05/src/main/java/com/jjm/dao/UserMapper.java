package com.jjm.dao;

import com.jjm.pojo.User;

/**
 * @author jjm
 * @version 1.0
 */
public interface UserMapper {
    User querySingle(int id);
}
