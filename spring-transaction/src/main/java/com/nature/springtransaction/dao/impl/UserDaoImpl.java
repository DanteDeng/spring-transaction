package com.nature.springtransaction.dao.impl;

import com.nature.springtransaction.dao.UserDao;
import com.nature.springtransaction.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate template;

    @Override
    public int saveByJdbcNormal(User user) {
        try {
            Connection connection = DataSourceUtils.getConnection(dataSource);// 这里是最为关键的一步
            //Connection connection = dataSource.getConnection();   // 直接从数据源获取连接，事务无法生效，因为直接获取的方式不是获取的事务开启时候绑定的连接
            PreparedStatement preparedStatement = connection.prepareStatement("insert into user(id,name,sex,age) values(?,?,?,?)");
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSex());
            preparedStatement.setInt(4, user.getAge());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int save(User user) {
        // 使用jdbcTemplate，这时候连接实际是通过DataSourceUtils（TransactionSynchronizationUtils）工具类获取来的
        //TransactionInterceptor
        //DataSourceTransactionManager
        return template.update("insert into user(id,name,sex,age) values(?,?,?,?)", user.getId(), user.getName(), user.getSex(), user.getAge());
    }

    @Override
    public int update(User user) {
        return template.update("update user set name=?,sex=?,age=? where id=?", user.getName(), user.getSex(), user.getAge(), user.getId());
    }

    @Override
    public int delete(String id) {
        return template.update("delete from  user where id=?", id);
    }


}
