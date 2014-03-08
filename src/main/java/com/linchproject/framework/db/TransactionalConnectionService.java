package com.linchproject.framework.db;

import com.linchproject.ioc.Transactional;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.sql2o.StatementRunnable;

import javax.sql.DataSource;

/**
 * @author Georg Schmidl
 */
public class TransactionalConnectionService implements ConnectionService, Transactional {

    private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();

    protected DataSource dataSource;
    protected Sql2o sql2o;

    @Override
    public Connection getConnection() {
        return connectionThreadLocal.get() != null? connectionThreadLocal.get(): new Connection(sql2o);
    }

    @Override
    public void begin() {
        Connection connection = connectionThreadLocal.get();
        if (connection == null) {
            connection = sql2o.beginTransaction();
            connectionThreadLocal.set(connection);
        }
    }

    @Override
    public void commit() {
        Connection connection = connectionThreadLocal.get();
        if (connection != null) {
            connection.commit();
        }
        connectionThreadLocal.remove();
    }

    @Override
    public void rollback() {
        Connection connection = connectionThreadLocal.get();
        if (connection != null) {
            connection.rollback();
        }
        connectionThreadLocal.remove();
    }

    @Override
    public Query createQuery(String sql) {
        return getConnection().createQuery(sql)
                .setAutoDeriveColumnNames(true)
                .setCaseSensitive(true);
    }

    @Override
    public Query createQuery(String sql, boolean returnGeneratedKeys) {
        return getConnection().createQuery(sql, returnGeneratedKeys)
                .setAutoDeriveColumnNames(true)
                .setCaseSensitive(true);
    }

    @Override
    public void runInTransaction(StatementRunnable runnable) {
        sql2o.runInTransaction(runnable);
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.sql2o = new Sql2o(dataSource);
    }
}
