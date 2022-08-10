package bradford.mason.retrovideogameexchange.repository;

import bradford.mason.retrovideogameexchange.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface OfferRepo extends JpaRepository<Offer, Integer> {

}
