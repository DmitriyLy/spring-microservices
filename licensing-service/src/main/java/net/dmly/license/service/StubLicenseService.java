package net.dmly.license.service;


import lombok.RequiredArgsConstructor;
import net.dmly.license.model.License;
import net.dmly.license.model.LicenseType;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Order(value = Ordered.LOWEST_PRECEDENCE)
public class StubLicenseService implements LicenseService {

    private final MessageSource messageSource;

    @Override
    public License getLicense(String licenseId, String organizationId) {
        return License.builder()
                .id(new Random().nextLong(1000))
                .licenseId(licenseId)
                .organizationId(organizationId)
                .description("Stub License")
                .productName("Stub Product")
                .licenseType(LicenseType.FULL)
                .build();
    }

    @Override
    public String createLicense(License license, String organizationId, Locale locale) {
        if (license == null) {
            throw new RuntimeException("License is null");
        }

        license.setOrganizationId(organizationId);

        return String.format(messageSource.getMessage("license.create.message",null, locale), license);
    }

    @Override
    public String updateLicense(License license, String organizationId) {
        if (license == null) {
            throw new RuntimeException("License is null");
        }

        license.setOrganizationId(organizationId);

        return String.format(messageSource.getMessage("license.update.message",null, null), license);
    }

    @Override
    public String deleteLicense(String licenseId, String organizationId) {
        return String.format(
                messageSource.getMessage("license.delete.message", null, null),
                licenseId,
                organizationId
        );
    }
}
