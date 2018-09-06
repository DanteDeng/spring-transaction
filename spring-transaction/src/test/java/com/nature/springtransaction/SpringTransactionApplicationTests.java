package com.nature.springtransaction;

import com.nature.springtransaction.model.User;
import com.nature.springtransaction.service.ProcessService;
import com.nature.springtransaction.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-transaction.xml"})
//@Transactional
//@Rollback(value = false)
public class SpringTransactionApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private ProcessService processService;

    @Test
    public void saveByJdbcNormal() {
        User user = new User();
        user.setId("userId2");
        user.setName("userName");
        user.setSex("M");
        user.setAge(28);
        userService.saveByJdbcNormal(user);
    }

    @Test
    public void saveByJdbcTemplate() {
        User user = new User();
        user.setId("userId2");
        user.setName("userName");
        user.setSex("M");
        user.setAge(28);
        userService.saveByJdbcTemplate(user);
    }

    @Test
    public void process() {
        User user = new User();
        user.setId("userId2");
        user.setName("userName");
        user.setSex("M");
        user.setAge(28);
        processService.saveUpdateDelete(user, 0);

    }

    /**
     * 事务不存在则开启事务已存在则使用当前事务
     */
    @Test
    public void required() {
        User user = new User();
        user.setId("userId2");
        user.setName("userName");
        user.setSex("M");
        user.setAge(28);
        processService.required(user);
    }

    /**
     * 事务存在则使用，不存在则不开启事务，跟不加@Transactional是一样的效果
     */
    @Test
    public void support() {
        User user = new User();
        user.setId("userId2");
        user.setName("userName");
        user.setSex("M");
        user.setAge(28);
        processService.support(user);
    }

    /**
     * 必须在事务中运行如果没有事先开启事务则报错
     */
    @Test
    public void mandatory() {
        User user = new User();
        user.setId("userId2");
        user.setName("userName");
        user.setSex("M");
        user.setAge(28);
        processService.mandatory(user);
    }

    /**
     * 每次被调用都新开事务，原有事务在新事物执行期间挂起（这个情况的使用是有可能出现死锁的，所以需要慎用）
     */
    @Test
    public void requiresNew() {
        User user = new User();
        user.setId("userId2");
        user.setName("userName");
        user.setSex("M");
        user.setAge(28);
        processService.requiresNew(user);
    }

    /**
     * 不支持事务，每次被调用都是独自处理自己事务
     */
    @Test
    public void notSupported() {
        User user = new User();
        user.setId("userId2");
        user.setName("userName");
        user.setSex("M");
        user.setAge(28);
        processService.notSupported(user);
    }

    /**
     * 不支持事务，被嵌入到事务中就会报错
     */
    @Test
    public void never() {
        User user = new User();
        user.setId("userId2");
        user.setName("userName");
        user.setSex("M");
        user.setAge(28);
        processService.never(user);
    }

    /**
     * 可内嵌事务，在已经存在事务的情况下被调用会开启新的事务，独自提交与回滚，不影响旧事务
     */
    @Test
    public void nested() {
        User user = new User();
        user.setId("userId2");
        user.setName("userName");
        user.setSex("M");
        user.setAge(28);
        processService.nested(user);
    }
}
