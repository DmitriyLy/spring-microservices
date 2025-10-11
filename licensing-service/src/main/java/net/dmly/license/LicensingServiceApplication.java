package net.dmly.license;

import net.dmly.license.service.client.ClientType;
import net.dmly.license.service.client.OrganizationClient;
import net.dmly.license.service.client.impl.OrganizationDiscoveryClient;
import net.dmly.license.service.client.impl.OrganizationFeignClient;
import net.dmly.license.service.client.impl.OrganizationRestTemplateClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@SpringBootApplication
@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients
public class LicensingServiceApplication {

    @Autowired
    private OrganizationDiscoveryClient organizationDiscoveryClient;

    @Autowired
    private OrganizationFeignClient organizationFeignClient;

    @Autowired
    private OrganizationRestTemplateClient organizationRestTemplateClient;

	public static void main(String[] args) {
		SpringApplication.run(LicensingServiceApplication.class, args);
	}

    @Bean
    public Map<ClientType, OrganizationClient> clientsMap() {
        return Map.of(
                ClientType.FEIGN, organizationFeignClient,
                ClientType.REST, organizationRestTemplateClient,
                ClientType.DISCOVERY, organizationDiscoveryClient
        );
    }

}
