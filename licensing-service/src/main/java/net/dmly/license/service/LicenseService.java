package net.dmly.license.service;

import lombok.RequiredArgsConstructor;
import net.dmly.license.config.ConfigPropertiesService;
import net.dmly.license.model.License;
import net.dmly.license.model.Organization;
import net.dmly.license.repository.LicenseRepository;
import net.dmly.license.service.client.ClientType;
import net.dmly.license.service.client.OrganizationClient;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class LicenseService {

    private final LicenseRepository licenseRepository;
    private final ConfigPropertiesService configPropertiesService;
    private final MessageSource messageSource;
    private final Map<ClientType, OrganizationClient> clientsMap;

    public License getLicense(String organizationId,
                              String licenseId,
                              ClientType clientType,
                              Locale locale) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        if (license == null) {
            throw new IllegalArgumentException(
                    String.format(
                            messageSource.getMessage("license.search.error.message", null, locale),
                            licenseId,
                            organizationId
                    )
            );
        }

        license.setDescription(configPropertiesService.getDefaultDescription());

        if (clientType != null) {
            Optional<Organization> organization = getOrganization(organizationId, clientType);

            organization.ifPresentOrElse(
                    value -> {
                        license.setOrganizationName(value.getName());
                        license.setContactName(value.getContactName());
                        license.setContactEmail(value.getContactEmail());
                        license.setContactPhone(value.getContactPhone());
                    },
                    () -> {
                        throw new RuntimeException();
                    }
            );
        }

        return license;
    }

    private Optional<Organization> getOrganization(String organizationId, ClientType clientType) {
        var organizationClient = clientsMap.get(clientType);

        if (null == organizationClient) {
            return Optional.empty();
        }

        return Optional.ofNullable(organizationClient.getOrganization(organizationId));
    }

    public License createLicense(License license, Locale locale) {
        license.setLicenseId(UUID.randomUUID().toString());
        license.setDescription(Objects.toString(license.getDescription(), "") + " - " + configPropertiesService.getDefaultDescription());
        licenseRepository.save(license);
        return license;
    }

    public License updateLicense(License license, Locale locale) {
        licenseRepository.save(license);
        return license;
    }

    public String deleteLicense(String licenseId, String organizationId, Locale locale) {
        licenseRepository.deleteById(licenseId);

        return String.format(messageSource.getMessage("license.delete.message", null, locale), licenseId);
    }

    public List<License> findAllByOrganizationId(String organizationId, Locale locale) {
        return licenseRepository.findAllByOrganizationId(organizationId);
    }
}
