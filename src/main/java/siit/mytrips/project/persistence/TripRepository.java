package siit.mytrips.project.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import siit.mytrips.project.model.Trip;


@Repository
public interface TripRepository extends CrudRepository<Trip, Long> {
}
