package com.barchuk.springbootinfoscience.patterns;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AbstractFactory {
    private static final String FACTORY_CONFIG_RESOURCE_NAME = "/patterns/factory.config";
    enum FactoryTechnology {
        JDBC,
        JPA,
        HIBERNATE
    }
    static FactoryTechnology tech;

    public static void main(String[] args) throws Exception {
        final String property = "JPA"; //props.getProperty("dao_type")
        tech = FactoryTechnology.valueOf(property);
        final DaoFactory daoFactory = getDaoFactory();
        final Object Dao = daoFactory.getDao();
        System.out.println("Ця фабрика: " + daoFactory + " виробила таке з'єднання: " + Dao);
    }

    public static DaoFactory getDaoFactory() {
        switch(tech) {
            case JDBC: return new JdbcConnectionFactory();
            case JPA: return new JpaConnectionFactory();
            case HIBERNATE: return new HibernateConnectionFactory();
            default:
                throw new IllegalArgumentException("Unknown Factory Type");
        }
    }

    interface DaoFactory {
        Object getDao();
    }

    static class JdbcConnectionFactory implements DaoFactory {
        public Object getDao() {
            return "My Dummy JDBC Dao";
        }
    }
    static class HibernateConnectionFactory implements DaoFactory {
        public Object getDao() {
            return "My Fake Hibernate Dao";
        }
    }
    static class JpaConnectionFactory implements DaoFactory {
        public Object getDao() {
            return "This is a JPA Dao";
        }
    }

    static Properties props = new Properties();

 /*   static {
        try {
            InputStream stream =
                    AbstractFactory.class.getResourceAsStream(FACTORY_CONFIG_RESOURCE_NAME);
            if (stream == null) {
                throw new ExceptionInInitializerError("Can't load properties file from classpath: " + FACTORY_CONFIG_RESOURCE_NAME);
            }
            props.load(stream);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }*/
}
