package com.aplaline.rpi.sht21;

import org.h2.server.web.WebServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@SpringBootApplication
@EnableScheduling
public class Application implements SchedulingConfigurer {
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	private static final SHT21 sht21 = new SHT21();

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		log.info("Registering Tasks");
		taskRegistrar.addFixedRateTask(new TemperatureReaderTask(sht21, jdbcTemplate), 10000);
	}

	@Bean
	public ServletRegistrationBean h2ConsoleServletRegistration() {
		log.info("Registering H2 database console");
		WebServlet webServlet = new WebServlet();
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(webServlet);
		registrationBean.addUrlMappings("/db-console/*");
		return registrationBean;
	}
}
