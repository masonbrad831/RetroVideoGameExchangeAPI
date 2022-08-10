package bradford.mason.retrovideogameexchange.repository;

import bradford.mason.retrovideogameexchange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface UserRepo extends JpaRepository<User, String> {

}
