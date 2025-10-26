package net.dmly.license.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dmly.license.model.License;
import net.dmly.license.service.LicenseService;
import net.dmly.license.service.client.ClientType;
import net.dmly.license.util.UserContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/organization/{organizationId}/license")
@RequiredArgsConstructor
@Slf4j
public class LicenseController {
    private final LicenseService licenseService;

    @GetMapping("/{licenseId}")
    public ResponseEntity<License> getLicense(@PathVariable("organizationId") String organizationId,
                                              @PathVariable("licenseId") String licenseId,
                                              @RequestHeader(value = "Accept-Language", required = false) Locale locale) {

        License license = licenseService.getLicense(organizationId, licenseId, null, locale);

        license.add(
                linkTo(methodOn(LicenseController.class).getLicense(organizationId, licenseId, locale)).withSelfRel(),
                linkTo(methodOn(LicenseController.class).createLicense(license, locale)).withRel("createLicense"),
                linkTo(methodOn(LicenseController.class).updateLicense(license, locale)).withRel("updateLicense"),
                linkTo(methodOn(LicenseController.class).deleteLicense(organizationId, licenseId, locale)).withRel("deleteLicense")
        );

        return ResponseEntity.ok(license);
    }

    @GetMapping("/{licenseId}/{clientType}")
    public ResponseEntity<License> getLicenseWithClient(@PathVariable("organizationId") String organizationId,
                                                        @PathVariable("licenseId") String licenseId,
                                                        @PathVariable("clientType") ClientType clientType,
                                                        @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        return ResponseEntity.ok(licenseService.getLicense(organizationId, licenseId, clientType, locale));
    }

    @PutMapping
    public ResponseEntity<License> updateLicense(@RequestBody License license,
                                                 @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        return ResponseEntity.ok(licenseService.updateLicense(license, locale));
    }

    @PostMapping
    public ResponseEntity<License> createLicense(@RequestBody License license,
                                                @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        return new ResponseEntity<>(licenseService.createLicense(license, locale), HttpStatus.CREATED);
    }

    @DeleteMapping("/{licenseId}")
    public ResponseEntity<String> deleteLicense(@PathVariable("organizationId") String organizationId,
                                                @PathVariable("licenseId") String licenseId,
                                                @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        return ResponseEntity.ok(licenseService.deleteLicense(organizationId, licenseId, locale));
    }

    @GetMapping
    public ResponseEntity<List<License>> findAllLicenseByOrganizationId(@PathVariable("organizationId") String organizationId,
                                                                        @RequestHeader(value = "Accept-Language", required = false) Locale locale) throws TimeoutException {
        log.debug("LicenseServiceController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return ResponseEntity.ok(licenseService.findAllByOrganizationId(organizationId, locale));
    }
}
