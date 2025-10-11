package net.dmly.organization.controller;

import lombok.RequiredArgsConstructor;
import net.dmly.organization.model.Organization;
import net.dmly.organization.service.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping
    public ResponseEntity<List<Organization>> findAll(){
        return new ResponseEntity<>(organizationService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable String organizationId) {
        return ResponseEntity.ok(organizationService.findById(organizationId));
    }

    @PostMapping
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) {
        return new ResponseEntity<>(organizationService.create(organization), HttpStatus.CREATED);
    }

    @PutMapping("/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrganization(@PathVariable String organizationId,
                                   @RequestBody Organization organization) {
        organizationService.update(organizationId, organization);
    }

    @DeleteMapping("/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganizationById(@PathVariable String organizationId) {
        organizationService.delete(organizationId);
    }
}
