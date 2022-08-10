package bradford.mason.retrovideogameexchange.controller;

import bradford.mason.retrovideogameexchange.model.Game;
import bradford.mason.retrovideogameexchange.model.Offer;
import bradford.mason.retrovideogameexchange.model.User;
import bradford.mason.retrovideogameexchange.repository.GameRepo;
import bradford.mason.retrovideogameexchange.repository.OfferRepo;
import bradford.mason.retrovideogameexchange.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/offers")
public class OfferRestController {

    @Autowired
    GameRepo gameRepo;

    @Autowired
    OfferRepo offerRepo;

    @Autowired
    UserRepo users;

    public String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        return ("" + dtf.format(now));
    }

    @DeleteMapping(path = "/{id}")
    public String deleteGame(@AuthenticationPrincipal User user, @PathVariable("id") int id) {
        User currentGameUser = offerRepo.getById(id).getUser();
        if (user.getUsername().equals(currentGameUser.getUsername())) {
            gameRepo.deleteById(id);
            return "Successfully Deleted";
        }

        

        return "Not Allowed";
    }

    @PostMapping(path = "/add/{cartId}/{id}")
        public void addToCart(@PathVariable("id") int id, @PathVariable("cartId") int cartId)
        {
            Item item = repo.getById(id)
            list<Item> cartls = cartrepo.getById(cartId).getCart();
            cartls.add(item);
            Cart cart = cartrepo.getById(cartId)
            cart.setCart(cartls);
            cartrepo.save(cart);

        }

    @PostMapping("/create")
    public String createOffer(@AuthenticationPrincipal User user, @RequestBody Map<String, List<Integer>> map) {
        String offerGiver = user.getUsername();
        Offer offer = new Offer();

        List<Offer> offerList = new ArrayList<>();


        List<Integer> tradeGameId = map.get("trade");
        List<Integer> offerGameId = map.get("offer");

        List<String> tradeGameList = new ArrayList<>();
        List<String> offerGameList = new ArrayList<>();

        List<Game> offeredGames = new ArrayList<>();
        List<Game> tradedGames = new ArrayList<>();


        String checkOwner = gameRepo.getById(tradeGameId.get(0)).getOwner().getUsername();

        for (int i = 0; i < tradeGameId.size(); i++) {

            String tradeUser = gameRepo.getById(tradeGameId.get(0)).getOwner().getUsername();
            if(offerGiver.equals(tradeUser)) {
                return "Can not trade for your own game";
            }

            String diffOwner = gameRepo.getById(tradeGameId.get(i)).getOwner().getUsername();
            if(!checkOwner.equals(diffOwner)) {
                return "You can only make an offer to the same owner of all games";
            }

            tradeGameList.add(gameRepo.getById(tradeGameId.get(i)).getTitle());
        }

         for (int i : offerGameId) {

             String gamesUser = gameRepo.getById(i).getOwner().getUsername();
             if (!offerGiver.equals(gamesUser)) {
                 return "Not Allowed: Game is not yours to trade";
             }
             offeredGames.add(gameRepo.getById(i));
             offerGameList.add(gameRepo.getById(i).getTitle());
         }

//         Person who posted the game
         offer.setTradeMaker(gameRepo.getById(tradeGameId.get(0)).getOwner().getUsername());
//         Person who posted the offer
        offer.setOfferMaker(user.getUsername());
//        List of games that are wanted by the offer maker
        offer.setTradeGames(tradeGameList);
//        List of games that are being offered
        offer.setOfferGames(offerGameList);
//        Person who is making the trade
         offer.setReceiver(gameRepo.getById(tradeGameId.get(0)).getOwner());
//         Date of when offer was made
         offer.setDate(getDate());
//         List of games being traded for
         offer.setGameOffer(tradedGames);
//         List of games being offered
         offer.setGameTrade(offeredGames);
//         Person who is making the offer
         offer.setUser(user);

         offer.setAccepted(false);


         offerRepo.save(offer);
         offerList.add(offer);
         user.setOffers(offerList);
         users.save(user);

        //        TODO
//        LET USER KNOW OFFER WAS CREATED
        return "Offer create for " + tradeGameList + " for " + offerGameList +" at " + getDate();
    }

    @PostMapping("/accept")
    public String acceptOffer(@AuthenticationPrincipal User user, @RequestParam int id) {
        return acceptDeny(user,id,true);
    }

    @PostMapping("/reject")
    public String rejectOffer(@AuthenticationPrincipal User user, @RequestParam int id) {
        return acceptDeny(user,id,false);
        //        TODO
//        LET USER KNOW OFFER WAS REJECTED
    }

    public String acceptDeny(User user, int id, boolean isAccepted) {
        try {
            Offer offer = offerRepo.getById(id);

            if(offer.isAccepted() == true) {
                return "Offer has already been accepted!";
            }

            if (offer.getReceiver().getUsername().equals(user.getUsername())) {
                offer.setAccepted(isAccepted);
                offerRepo.save(offer);
                return "Offer accepted: " + isAccepted;
            }

        } catch (EntityNotFoundException e) {
            return "Offer of id + " + id;
        }
        return "Can not perform action";
    }
}
