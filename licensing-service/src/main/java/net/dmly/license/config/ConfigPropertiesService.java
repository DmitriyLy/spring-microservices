package net.dmly.license.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "licensing.service")
@Getter
public class ConfigPropertiesService {
    private String defaultDescription;
}
