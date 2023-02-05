package ru.proj.authorization;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Application launch class
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 02.02.23
 */
@SpringBootApplication
public class Main {

    private Properties loadDbProperties() {
        Properties cfg = new Properties();
        try (InputStream io = new FileInputStream("db/liquibase.properties")) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("driver-class-name"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return cfg;
    }

    /**
     * Creates a unique instance of SessionFactory
     *
     * @return SessionFactory instance
     */
    @Bean(destroyMethod = "close")
    public SessionFactory sf() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    /**
     * Starts the application
     *
     * @param args app arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Go to http://localhost:8080/index");
    }

}


