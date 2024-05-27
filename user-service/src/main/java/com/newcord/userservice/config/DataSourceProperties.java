package com.newcord.userservice.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceProperties {
    @Value("${spring.datasource.master.jdbc-url}")
    String masterJdbcUrl;
    @Value("${spring.datasource.master.username}")
    String masterUsername;
    @Value("${spring.datasource.master.password}")
    String masterPassword;
    @Value("${spring.datasource.master.driver-class-name}")
    String masterDriverClassName;
    @Value("${spring.datasource.master.hikari.maximum-pool-size}")
    int masterMaximumPoolSize;
    @Value("${spring.datasource.master.hikari.minimum-idle}")
    int masterMinimumIdle;
    @Value("${spring.datasource.master.hikari.idle-timeout}")
    Long masterIdleTimeout;
    @Value("${spring.datasource.master.hikari.connection-timeout}")
    Long masterConnectionTimeout;
    @Value("${spring.datasource.master.hikari.max-lifetime}")
    Long masterMaxLifetime;
    @Value("${spring.datasource.slave.jdbc-url}")
    String slaveJdbcUrl;
    @Value("${spring.datasource.slave.username}")
    String slaveUsername;
    @Value("${spring.datasource.slave.password}")
    String slavePassword;
    @Value("${spring.datasource.slave.driver-class-name}")
    String slaveDriverClassName;
    @Value("${spring.datasource.slave.hikari.maximum-pool-size}")
    int slaveMaximumPoolSize;
    @Value("${spring.datasource.slave.hikari.minimum-idle}")
    int slaveMinimumIdle;
    @Value("${spring.datasource.slave.hikari.idle-timeout}")
    Long slaveIdleTimeout;
    @Value("${spring.datasource.slave.hikari.connection-timeout}")
    Long slaveConnectionTimeout;
    @Value("${spring.datasource.slave.hikari.max-lifetime}")
    Long slaveMaxLifetime;

    @Bean
    @Primary
    HikariDataSource masterDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(masterJdbcUrl);
        config.setUsername(masterUsername);
        config.setPassword(masterPassword);
        config.setDriverClassName(masterDriverClassName);
        config.setMaximumPoolSize(masterMaximumPoolSize);
        config.setMinimumIdle(masterMinimumIdle);
        config.setIdleTimeout(masterIdleTimeout);
        config.setConnectionTimeout(masterConnectionTimeout);
        config.setMaxLifetime(masterMaxLifetime);
        return new HikariDataSource(config);
    }

    @Bean
    @Primary
    HikariDataSource slaveDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(slaveJdbcUrl);
        config.setUsername(slaveUsername);
        config.setPassword(slavePassword);
        config.setDriverClassName(slaveDriverClassName);
        config.setMaximumPoolSize(slaveMaximumPoolSize);
        config.setMinimumIdle(slaveMinimumIdle);
        config.setIdleTimeout(slaveIdleTimeout);
        config.setConnectionTimeout(slaveConnectionTimeout);
        config.setMaxLifetime(slaveMaxLifetime);
        return new HikariDataSource(config);
    }


}
