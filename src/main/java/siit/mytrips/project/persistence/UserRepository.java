package siit.mytrips.project.persistence;

import org.springframework.data.repository.CrudRepository;
import siit.mytrips.project.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername (String username);
}
