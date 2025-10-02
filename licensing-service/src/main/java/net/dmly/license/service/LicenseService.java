package net.dmly.license.service;

import lombok.RequiredArgsConstructor;
import net.dmly.license.config.ConfigPropertiesService;
import net.dmly.license.model.License;
import net.dmly.license.repository.LicenseRepository;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LicenseService {

    private final LicenseRepository licenseRepository;
    private final ConfigPropertiesService  configPropertiesService;
    private final MessageSource messageSource;

    public License getLicense(String licenseId, String organizationId, Locale locale) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        if (null == license) {
            throw new IllegalArgumentException(
                    String.format(
                            messageSource.getMessage("license.search.error.message", null, locale),
                            licenseId,
                            organizationId
                    )
            );
        }

        license.setDescription(configPropertiesService.getDefaultDescription());

        return license;
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
