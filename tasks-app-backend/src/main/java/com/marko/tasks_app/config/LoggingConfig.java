package com.marko.tasks_app.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class LoggingConfig {

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludePayload(true);
        filter.setIncludeHeaders(false);
        filter.setIncludeQueryString(true);
        filter.setIncludeClientInfo(true);
        filter.setMaxPayloadLength(10000);
        return filter;
    }
}
