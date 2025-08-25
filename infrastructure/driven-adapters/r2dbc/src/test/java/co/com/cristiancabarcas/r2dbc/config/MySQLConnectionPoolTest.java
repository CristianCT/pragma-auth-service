package co.com.cristiancabarcas.r2dbc.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class MySQLConnectionPoolTest {

    @InjectMocks
    private MySQLConnectionPool connectionPool;

    @Mock
    private MySQLConnectionProperties properties;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(properties.getHost()).thenReturn("localhost");
        when(properties.getPort()).thenReturn(5432);
        when(properties.getDatabase()).thenReturn("dbName");
        when(properties.getUsername()).thenReturn("username");
        when(properties.getPassword()).thenReturn("password");
    }

    @Test
    void getConnectionConfigSuccess() {
        assertNotNull(connectionPool.getConnectionConfig(properties));
    }
}
