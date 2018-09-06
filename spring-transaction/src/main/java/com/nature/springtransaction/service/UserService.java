package com.nature.springtransaction.service;

import com.nature.springtransaction.model.User;

public interface UserService {

    int saveByJdbcNormal(User user);

    int saveByJdbcTemplate(User user);

    int save(User user);

    int update(User user);

    int delete(String id);

    int saveRequired(User user);

    int updateRequired(User user);

    int deleteRequired(String id);

    int saveSupport(User user);

    int updateSupport(User user);

    int deleteSupport(String id);

    int saveMandatory(User user);

    int updateMandatory(User user);

    int deleteMandatory(String id);

    int saveRequiresNew(User user);

    int updateRequiresNew(User user);

    int deleteRequiresNew(String id);

    int saveNotSupported(User user);

    int updateNotSupported(User user);

    int deleteNotSupported(String id);

    int saveNever(User user);

    int updateNever(User user);

    int deleteNever(String id);

    int saveNested(User user);

    int updateNested(User user);

    int deleteNested(String id);
}
