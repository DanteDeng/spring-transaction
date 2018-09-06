package com.nature.springtransaction.service.impl;

import com.nature.springtransaction.dao.UserDao;
import com.nature.springtransaction.model.User;
import com.nature.springtransaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public int saveByJdbcNormal(User user) {

        int save = userDao.saveByJdbcNormal(user);
        if (save == 1) {
            throw new RuntimeException("I want to roll back this transaction");
        }
        return save;
    }


    @Transactional
    @Override
    public int saveByJdbcTemplate(User user) {

        int save = userDao.save(user);
       /* if (save == 1) {
            throw new RuntimeException("I want to roll back this transaction");
        }*/
        return save;
    }

    @Transactional
    @Override
    public int save(User user) {
        return userDao.save(user);
    }

    @Transactional
    @Override
    public int update(User user) {
        return userDao.update(user);
    }

    @Transactional
    @Override
    public int delete(String id) {
        return userDao.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int saveRequired(User user) {
        return userDao.save(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateRequired(User user) {
        return userDao.update(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int deleteRequired(String id) {
        int delete = userDao.delete(id);
        if (delete == 1) {
            throw new RuntimeException("rollback all,because propagation is required");
        }
        return delete;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int saveSupport(User user) {
        return userDao.save(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int updateSupport(User user) {
        if (user.getAge() >= 32) {
            throw new RuntimeException("rollback none,because propagation is supports");
        }
        return userDao.update(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int deleteSupport(String id) {
        return userDao.delete(id);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public int saveMandatory(User user) {
        return userDao.save(user);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public int updateMandatory(User user) {
        if (user.getAge() >= 32) {
            throw new RuntimeException("rollback none,because propagation is supports");
        }
        return userDao.update(user);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public int deleteMandatory(String id) {
        return userDao.delete(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int saveRequiresNew(User user) {
        return userDao.save(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public int updateRequiresNew(User user) {
        if (user.getAge() >= 32) {
            throw new RuntimeException("rollback none,because propagation is supports");
        }
        return userDao.update(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public int deleteRequiresNew(String id) {
        return userDao.delete(id);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public int saveNotSupported(User user) {
        return userDao.save(user);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public int updateNotSupported(User user) {
        return userDao.update(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int deleteNotSupported(String id) {
        int delete = userDao.delete(id);
        if (delete == 1) {
            throw new RuntimeException("rollback this delete handle only,because propagation is not supported");
        }
        return delete;
    }

    @Transactional(propagation = Propagation.NEVER)
    @Override
    public int saveNever(User user) {
        return userDao.save(user);
    }

    @Transactional(propagation = Propagation.NEVER)
    @Override
    public int updateNever(User user) {
        return userDao.update(user);
    }

    @Transactional(propagation = Propagation.NEVER)
    @Override
    public int deleteNever(String id) {
        return userDao.delete(id);
    }

    @Transactional(propagation = Propagation.NESTED)
    @Override
    public int saveNested(User user) {
        return userDao.save(user);
    }

    @Transactional(propagation = Propagation.NESTED)
    @Override
    public int updateNested(User user) {
        if (user.getAge() >= 32 && user.getAge() < 38) {
            throw new RuntimeException("rollback this transaction,because propagation is supports");
        }
        return userDao.update(user);
    }

    @Transactional(propagation = Propagation.NESTED)
    @Override
    public int deleteNested(String id) {
        return userDao.delete(id);
    }
}
