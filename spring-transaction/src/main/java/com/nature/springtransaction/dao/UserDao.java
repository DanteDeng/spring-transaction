package com.nature.springtransaction.dao;

import com.nature.springtransaction.model.User;

public interface UserDao {

    int saveByJdbcNormal(User user);

    int save(User user);

    int update(User user);

    int delete(String id);

}
