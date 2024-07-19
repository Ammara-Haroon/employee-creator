package com.projects.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  public void addCorsMappings(CorsRegistry registry) {
    String[] allowedOrigins = { "https://employee-dashboard-frontend-hf6q6wxeca-ts.a.run.app","https://employee-dashboard-frontend-hf6q6wxeca-ts.a.run.app/","http://localhost:5173", "http://localhost:5173/", "http://127.0.0.1:5173" };
    registry.addMapping("/**").allowedOrigins(allowedOrigins).allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
        .allowedHeaders("*").allowCredentials(true);
  }
}