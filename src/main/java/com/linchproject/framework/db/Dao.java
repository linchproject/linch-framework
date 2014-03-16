package com.linchproject.framework.db;

import org.sql2o.Query;

import java.util.List;

/**
 * @author Georg Schmidl
 */
public abstract class Dao<T extends Entity> {

    protected ConnectionService connectionService;

    public abstract T findById(Long id);

    public abstract List<T> findAll();

    public abstract void save(T entity);

    public abstract void delete(T entity);

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
