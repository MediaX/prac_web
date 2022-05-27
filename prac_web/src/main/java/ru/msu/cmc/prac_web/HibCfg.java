package ru.msu.cmc.prac_web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class HibCfg {
    /*
     * Init dataSources
     */
    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/prac_web");
        dataSource.setUsername("postgres");
        dataSource.setPassword((""));

        return dataSource;
    }

    /*
     * Init hibernate properties
     */
    private Properties getHibernateProperties() {
        Properties properties = new Properties();

        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");

        return properties;
    }

    /*
     * Init session factory
     */
    @Bean
    public LocalSessionFactoryBean setSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setPackagesToScan(new String[] {"ru.msu.cmc.prac_web"});
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }
}