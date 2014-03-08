package com.linchproject.framework.db;

import org.sql2o.Query;

import java.util.List;

/**
 * @author Georg Schmidl
 */
public abstract class Dao<T> {

    protected ConnectionService connectionService;

    public abstract T findById(Long id);

    public abstract List<T> findAll();

    public abstract void save(T object);

    public abstract void delete(T object);

    protected Query query(String sql) {
        return connectionService.createQuery(sql);
    }

    protected Query query(String sql, boolean returnGeneratedKeys) {
        return connectionService.createQuery(sql, returnGeneratedKeys);
    }

    public void setConnectionService(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }
}
