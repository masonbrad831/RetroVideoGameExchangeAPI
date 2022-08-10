package bradford.mason.retrovideogameexchange.controller;

import bradford.mason.retrovideogameexchange.model.Game;
import bradford.mason.retrovideogameexchange.model.User;
import bradford.mason.retrovideogameexchange.repository.GameRepo;
import bradford.mason.retrovideogameexchange.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/games")
public class VideoGameRestController {

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo users;

    @GetMapping(path = "/title/{title}")
    public List<Game> findByTitle(@PathVariable("title") String title) {
        return gameRepo.findAllByTitle(title);
    }

    @GetMapping(path = "/year/{year}")
    public List<Game> findByYear(@PathVariable("year") String year) {
        return gameRepo.findAllByYear(year);
    }

    @GetMapping(path = "/publisher/{publisher}")
    public List<Game> findByPublisher(@PathVariable("publisher") String publisher) {
        return gameRepo.findAllByPublisher(publisher);
    }


    @GetMapping(path = "/system/{system}")
    public List<Game> findBySystem(@PathVariable("system") String system) {
        return gameRepo.findAllBySystemContaining(system);
    }

    @PostMapping("/create")
    public void createGame(@AuthenticationPrincipal User user, @RequestBody Game game ) {
        List<Game> games = new ArrayList<>();
        User theUser = user;
        game.setOwner(user);
        gameRepo.save(game);
        games.add(game);

        theUser.setGames(games);
        users.save(theUser);
    }


    @DeleteMapping(path = "/{id}")
    public String deleteGame(@AuthenticationPrincipal User user, @PathVariable("id") int id) {
        User currentGameUser = gameRepo.getById(id).getOwner();
        if (user.getUsername().equals(currentGameUser.getUsername())) {
            gameRepo.deleteById(id);
            return "Successfully Deleted";
        }

        return "Not Allowed";
    }





}
