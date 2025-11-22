package net.dmly.organization.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import net.dmly.organization.model.Organization;
import net.dmly.organization.service.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @RolesAllowed({"ADMIN", "USER"})
    @GetMapping
    public ResponseEntity<List<Organization>> findAll(){
        return new ResponseEntity<>(organizationService.findAll(), HttpStatus.OK);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @GetMapping("/{organizationId}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable String organizationId) {
        return ResponseEntity.ok(organizationService.findById(organizationId));
    }

    @RolesAllowed({"ADMIN", "USER"})
    @PostMapping
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) {
        return new ResponseEntity<>(organizationService.create(organization), HttpStatus.CREATED);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @PutMapping("/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrganization(@PathVariable String organizationId,
                                   @RequestBody Organization organization) {
        organizationService.update(organizationId, organization);
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping("/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganizationById(@PathVariable String organizationId) {
        organizationService.delete(organizationId);
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/admin-only-endpoint")
    public ResponseEntity<Map<String, Object>> adminOnlyEndpoint() {
        Map<String, Object> response = new HashMap<>();
        response.put("type", "admin-only-endpoint");
        response.put("securityContext", SecurityContextHolder.getContext());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RolesAllowed("USER")
    @GetMapping("/user-only-endpoint")
    public ResponseEntity<Map<String, Object>> userOnlyEndpoint() {
        Map<String, Object> response = new HashMap<>();
        response.put("type", "user-only-endpoint");
        response.put("securityContext", SecurityContextHolder.getContext());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/security-debug-endpoint-public")
    public ResponseEntity<Map<String, Object>> securityDebugEndpointPublic() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Map<String, Object> response = new HashMap<>();
        response.put("type", "security-debug-endpoint-public");
        response.put("securityContext", securityContext);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/security-debug-endpoint-private")
    public ResponseEntity<Map<String, Object>> securityDebugEndpointPrivate() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Map<String, Object> response = new HashMap<>();
        response.put("type", "security-debug-endpoint-private");
        response.put("securityContext", securityContext);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
