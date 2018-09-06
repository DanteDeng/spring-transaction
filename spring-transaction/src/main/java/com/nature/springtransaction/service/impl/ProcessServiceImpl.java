package com.nature.springtransaction.service.impl;

import com.nature.springtransaction.model.User;
import com.nature.springtransaction.service.ProcessService;
import com.nature.springtransaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private UserService userService;

    @Transactional
    @Override
    public void saveUpdateDelete(User user, int exIndex) {
        System.err.println("============================================================================================");
        if (exIndex == 1) {
            throw new RuntimeException("rollback at exception index " + exIndex);
        }
        userService.save(user);
        if (exIndex == 2) {
            throw new RuntimeException("rollback at exception index " + exIndex);
        }
        for (int i = 0; i < 5; i++) {
            user.setAge(user.getAge() + 3);
            userService.update(user);
            if (exIndex == 3) {
                throw new RuntimeException("rollback at exception index " + exIndex);
            }
        }

        userService.delete(user.getId());
        if (exIndex == 4) {
            throw new RuntimeException("rollback at exception index " + exIndex);
        }
        System.err.println("============================================================================================");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void required(User user) {
        System.err.println("============================================================================================");
        userService.saveRequired(user);
        for (int i = 0; i < 5; i++) {
            user.setAge(user.getAge() + 3);
            userService.updateRequired(user);
        }
        userService.deleteRequired(user.getId());
        System.err.println("============================================================================================");
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void support(User user) {
        System.err.println("============================================================================================");
        userService.saveSupport(user);
        for (int i = 0; i < 5; i++) {
            user.setAge(user.getAge() + 3);
            userService.updateSupport(user);
        }
        userService.deleteSupport(user.getId());
        System.err.println("============================================================================================");
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void mandatory(User user) {
        System.err.println("============================================================================================");
        userService.saveMandatory(user);
        for (int i = 0; i < 5; i++) {
            user.setAge(user.getAge() + 3);
            userService.updateMandatory(user);
        }
        userService.deleteMandatory(user.getId());
        System.err.println("============================================================================================");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void requiresNew(User user) {
        System.err.println("============================================================================================");
        userService.saveRequiresNew(user);
        for (int i = 0; i < 5; i++) {
            user.setAge(user.getAge() + 3);
            userService.updateRequiresNew(user);
        }
        userService.deleteRequiresNew(user.getId());
        System.err.println("============================================================================================");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void notSupported(User user) {
        System.err.println("============================================================================================");
        userService.saveNotSupported(user);
        for (int i = 0; i < 5; i++) {
            user.setAge(user.getAge() + 3);
            userService.updateNotSupported(user);
        }
        userService.deleteNotSupported(user.getId());
        System.err.println("============================================================================================");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void never(User user) {
        System.err.println("============================================================================================");
        userService.saveNever(user);
        for (int i = 0; i < 5; i++) {
            user.setAge(user.getAge() + 3);
            userService.updateNever(user);
        }
        userService.deleteNever(user.getId());
        System.err.println("============================================================================================");
    }

    @Transactional(propagation = Propagation.NESTED)
    @Override
    public void nested(User user) {
        System.err.println("============================================================================================");
        userService.saveNested(user);
        for (int i = 0; i < 5; i++) {
            try {
                user.setAge(user.getAge() + 3);
                userService.updateNested(user);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        //userService.deleteNested(user.getId());
        System.err.println("============================================================================================");
    }
}