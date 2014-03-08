package com.linchproject.framework.components;

import org.junit.Test;
import org.sql2o.Connection;

import javax.sql.DataSource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author Georg Schmidl
 */
public class TransactionalConnectionServiceTest {

    @Test
    public void testGetConnection() throws Exception {
        DataSource dataSource = mock(DataSource.class);
        java.sql.Connection jdbcConnection = mock(java.sql.Connection.class);
        when(dataSource.getConnection()).thenReturn(jdbcConnection);

        TransactionalConnectionService transactionalConnectionService =
                new TransactionalConnectionService();
        transactionalConnectionService.setDataSource(dataSource);

        Connection connection1 = transactionalConnectionService.getConnection();

        verify(dataSource).getConnection();
        verify(jdbcConnection, never()).setAutoCommit(false);

        Connection connection2 = transactionalConnectionService.getConnection();
        assertFalse(connection1 == connection2);
    }

    @Test
    public void testBegin() throws Exception {
        DataSource dataSource = mock(DataSource.class);
        java.sql.Connection jdbcConnection = mock(java.sql.Connection.class);
        when(dataSource.getConnection()).thenReturn(jdbcConnection);

        TransactionalConnectionService transactionalConnectionService =
                new TransactionalConnectionService();
        transactionalConnectionService.setDataSource(dataSource);

        transactionalConnectionService.begin();

        verify(dataSource).getConnection();
        verify(jdbcConnection).setAutoCommit(false);

        Connection connection1 = transactionalConnectionService.getConnection();
        Connection connection2 = transactionalConnectionService.getConnection();
        assertTrue(connection1 == connection2);

        transactionalConnectionService.commit();
    }

    @Test
    public void testCommit() throws Exception {
        DataSource dataSource = mock(DataSource.class);
        java.sql.Connection jdbcConnection = mock(java.sql.Connection.class);
        when(dataSource.getConnection()).thenReturn(jdbcConnection);

        TransactionalConnectionService transactionalConnectionService =
                new TransactionalConnectionService();
        transactionalConnectionService.setDataSource(dataSource);

        transactionalConnectionService.begin();
        Connection connection1 = transactionalConnectionService.getConnection();
        transactionalConnectionService.commit();

        verify(dataSource).getConnection();
        verify(jdbcConnection).commit();

        Connection connection2 = transactionalConnectionService.getConnection();
        assertFalse(connection1 == connection2);
    }

    @Test
    public void testRollback() throws Exception {
        DataSource dataSource = mock(DataSource.class);
        java.sql.Connection jdbcConnection = mock(java.sql.Connection.class);
        when(dataSource.getConnection()).thenReturn(jdbcConnection);

        TransactionalConnectionService transactionalConnectionService =
                new TransactionalConnectionService();
        transactionalConnectionService.setDataSource(dataSource);

        transactionalConnectionService.begin();
        Connection connection1 = transactionalConnectionService.getConnection();
        transactionalConnectionService.rollback();

        verify(dataSource).getConnection();
        verify(jdbcConnection).rollback();

        Connection connection2 = transactionalConnectionService.getConnection();
        assertFalse(connection1 == connection2);
    }
}
