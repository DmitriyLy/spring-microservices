package net.dmly.organization.converter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;

public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    public static final String CLIENT_NAME = "ostock";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        collectResourceAccessRoles(jwt, authorities);
        collectRealmAccessRoles(jwt, authorities);

        return authorities;
    }

    private void collectResourceAccessRoles(Jwt jwt, Set<GrantedAuthority> authorities) {
        Map<String, Object> resourceAccessClaim = jwt.getClaim("resource_access");
        Map<String, Object> realmClaim = (Map<String, Object>) resourceAccessClaim.get(CLIENT_NAME);
        collectRoles(realmClaim, authorities);
    }

    private void collectRealmAccessRoles(Jwt jwt, Set<GrantedAuthority> authorities) {
        Map<String, Object> resourceAccessClaim = jwt.getClaim("realm_access");
        collectRoles(resourceAccessClaim, authorities);
    }

    private void collectRoles(Map<String, Object> claim, Set<GrantedAuthority> authorities) {
        if (MapUtils.isEmpty(claim)) {
            return;
        }

        List<String> roles = (List<String>) claim.get("roles");

        if (CollectionUtils.isEmpty(roles)) {
            return;
        }

        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        });
    }
}
