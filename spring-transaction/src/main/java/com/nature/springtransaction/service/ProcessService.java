package com.nature.springtransaction.service;

import com.nature.springtransaction.model.User;

public interface ProcessService {

    void saveUpdateDelete(User user, int exIndex);

    void required(User user);

    void support(User user);

    void mandatory(User user);

    void requiresNew(User user);

    void notSupported(User user);

    void never(User user);

    void nested(User user);
}
