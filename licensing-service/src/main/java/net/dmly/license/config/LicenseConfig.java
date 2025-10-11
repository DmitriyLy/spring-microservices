package net.dmly.license.config;

import lombok.RequiredArgsConstructor;
import net.dmly.license.service.client.ClientType;
import net.dmly.license.service.client.OrganizationClient;
import net.dmly.license.service.client.impl.OrganizationDiscoveryClient;
import net.dmly.license.service.client.impl.OrganizationFeignClient;
import net.dmly.license.service.client.impl.OrganizationRestTemplateClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class LicenseConfig {

    @Bean
    public LocaleResolver localeResolver() {
        var localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        var messageSource = new ResourceBundleMessageSource();
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setBasenames("localization/messages");
        return messageSource;
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
