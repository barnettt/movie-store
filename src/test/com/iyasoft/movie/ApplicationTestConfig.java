package com.iyasoft.movie;

import javax.sql.DataSource;

import com.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = {com.iyasoft.movie.repository.MovieDetailRepository.class})
@EntityScan(basePackages = {"com.iyasoft.movie.entity"})
public class ApplicationTestConfig {
    public static void main(String[] args) {

        SpringApplication.run(Application.class);

    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUsername("sa");
        dataSource.setPassword("sa");
        dataSource.setUrl(
                "jdbc:h2:mem:movie-store;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");

        return dataSource;
    }

}
