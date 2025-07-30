package org.acme;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import org.acme.activerecord.Human;

@ResourceProperties(path = "/humans")
public interface HumanCrudResource extends PanacheEntityResource<Human, Long> {
}
