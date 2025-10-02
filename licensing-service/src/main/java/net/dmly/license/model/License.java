package net.dmly.license.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@ToString
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "licenses")
public class License extends RepresentationModel<License> {

    @Id
    @Column(name = "license_id",  nullable = false)
    private String licenseId;

    @Column(name = "description",   nullable = true)
    private String description;

    @Column(name = "organization_id",   nullable = false)
    private String organizationId;

    @Column(name = "product_name",  nullable = false)
    private String productName;

    @Column(name = "license_type",   nullable = false)
    @Enumerated(EnumType.STRING)
    private LicenseType licenseType;

    @Column(name = "comment", nullable = true)
    private String comment;

}
