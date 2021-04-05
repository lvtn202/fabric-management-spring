package com.example.lvtn;

import com.example.lvtn.web.ApplicationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication
@Configuration
@ComponentScan(basePackageClasses = { LvtnApplication.class, ApplicationController.class})
@PropertySource({"classpath:/application.properties"})
public class LvtnApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(LvtnApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(LvtnApplication.class);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public static MessageSource messageSource() {
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("message");
		return resourceBundleMessageSource;
	}

	@Bean
    public DriverManagerDataSource getDataSource() {
		DriverManagerDataSource dataSourceBuilder = new DriverManagerDataSource();
        dataSourceBuilder.setDriverClassName("org.postgresql.Driver");
        dataSourceBuilder.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSourceBuilder.setUsername("postgres");
        dataSourceBuilder.setPassword("lvtn");
        return dataSourceBuilder;
    }

//    @Bean
//	@Lazy
//	public DriverManagerDataSource getNewDataSource() {
//		DriverManagerDataSource dataSourceBuilder = new DriverManagerDataSource();
//		dataSourceBuilder.setDriverClassName("org.postgresql.Driver");
//		dataSourceBuilder.setUrl("jdbc:postgresql://localhost:5432/postgres");
//		dataSourceBuilder.setUsername("abc");
//		dataSourceBuilder.setPassword("lvtn");
//		return dataSourceBuilder;
//	}

}
