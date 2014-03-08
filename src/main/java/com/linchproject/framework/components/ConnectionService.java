package com.linchproject.framework.components;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.StatementRunnable;

/**
 * @author Georg Schmidl
 */
public interface ConnectionService {

    Connection getConnection();

    Query createQuery(String sql);

    Query createQuery(String sql, boolean returnGeneratedKeys);

    void runInTransaction(StatementRunnable runnable);
}
