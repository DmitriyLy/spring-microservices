package net.dmly.license.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@Builder
@ToString
public class Organization extends RepresentationModel<Organization> {
    String id;
    String name;
    String contactName;
    String contactEmail;
    String contactPhone;
}
