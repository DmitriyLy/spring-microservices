package net.dmly.license.repository;

import net.dmly.license.model.License;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LicenseRepository extends CrudRepository<License, String> {
    License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);

    List<License> findAllByOrganizationId(String organizationId);
}
