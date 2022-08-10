package bradford.mason.retrovideogameexchange.repository;

import bradford.mason.retrovideogameexchange.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RestResource
public interface GameRepo extends JpaRepository<Game, Integer> {

    List<Game> findAllByTitle(String title);
    List<Game> findAllByPublisher(String publisher);
    List<Game> findAllByYear(String year);
    List<Game> findAllBySystemContaining(String system);

}
