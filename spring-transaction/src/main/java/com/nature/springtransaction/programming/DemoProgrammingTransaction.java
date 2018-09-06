package com.nature.springtransaction.programming;

import com.nature.springtransaction.model.User;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * 编程式事务
 */
public class DemoProgrammingTransaction {

    /**
     * 数据源
     */
    private static DataSource dataSource;
    /**
     * 事务管理器
     */
    private static PlatformTransactionManager transactionManager;
    /**
     * jdbc模板
     */
    private static JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        init();
        //int i = testTransactionManager();
        int i = testTransactionTemplate();
        System.out.println("test transaction template result = " + i);


    }

    /**
     * 初始化(数据源、事务管理器、jdbc模板)
     */
    private static void init() {
        dataSource = new BasicDataSource(); // 数据源配置
        ((BasicDataSource) dataSource).setUrl("jdbc:mysql://localhost:3306/nature-test?useSSL=false&serverTimezone=GMT");
        ((BasicDataSource) dataSource).setUsername("root");
        ((BasicDataSource) dataSource).setPassword("dy051.");
        ((BasicDataSource) dataSource).setMaxActive(20);
        ((BasicDataSource) dataSource).setMaxIdle(5);
        transactionManager = new DataSourceTransactionManager();    // 事务管理器
        ((DataSourceTransactionManager) transactionManager).setDataSource(dataSource); // 设置数据源
        jdbcTemplate = new JdbcTemplate();  //jdbc模板
        jdbcTemplate.setDataSource(dataSource);
    }

    /**
     * 使用TransactionManager进行编程式事务管理
     * @return 这个结果不值得参考
     */
    private static int testTransactionManager() {
        DefaultTransactionDefinition transDef = new DefaultTransactionDefinition(); // 定义事务属性
        transDef.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED); // 设置传播行为属性
        TransactionStatus status = transactionManager.getTransaction(transDef); // 获得事务状态（开启事务）
        int update = 0;
        try {
            update = dataBaseOperation();
            if (update == 1) {
                throw new RuntimeException("roll back the transaction");
            }
            transactionManager.commit(status);// 提交

        } catch (Exception e) {
            System.err.println(e.getMessage());
            transactionManager.rollback(status);// 回滚
        }
        return update;
    }

    /**
     * 使用TransactionTemplate实现编程式事务
     * @return 这个结果不值得参考
     */
    private static int testTransactionTemplate() {
        TransactionTemplate tt = new TransactionTemplate(transactionManager); // 新建一个TransactionTemplate
        // 模板执行方法（这是一个非常值得借鉴的设计模式）
        Integer execute = tt.execute((status) -> {  // 事务开启提交与回滚等已经封装
            int update = dataBaseOperation();
            if (update == 1) {
                throw new RuntimeException("roll back the transaction");
            }
            return update;
        });// 执行execute方法进行事务管理

        return execute == null ? 0 : execute;
    }

    /**
     * 数据库操作
     * @return 操作结果
     */
    private static int dataBaseOperation() {
        User user = new User();
        user.setId("userId2");
        user.setName("userName");
        user.setSex("M");
        user.setAge(28);
        return jdbcTemplate.update("insert into user(id,name,sex,age) values(?,?,?,?)", user.getId(), user.getName(), user.getSex(), user.getAge());
    }
}
