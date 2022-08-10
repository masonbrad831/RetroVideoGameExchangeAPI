package bradford.mason.retrovideogameexchange.model;


import javax.persistence.*;
import java.util.List;

@Entity
public class Offer {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String tradeMaker;
    private String offerMaker;


    @ElementCollection
    private List<String> tradeGames;

    @ElementCollection
    private List<String> offerGames;


    @ManyToOne
    private User receiver;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Game> gameTrade;

    @OneToMany
    private List<Game> gameOffer;

    private String date;

    private boolean isAccepted;

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTradeMaker() {
        return tradeMaker;
    }

    public void setTradeMaker(String tradeMaker) {
        this.tradeMaker = tradeMaker;
    }

    public String getOfferMaker() {
        return offerMaker;
    }

    public void setOfferMaker(String offerMaker) {
        this.offerMaker = offerMaker;
    }

    public List<String> getTradeGames() {
        return tradeGames;
    }

    public void setTradeGames(List<String> tradeGames) {
        this.tradeGames = tradeGames;
    }

    public List<String> getOfferGames() {
        return offerGames;
    }

    public void setOfferGames(List<String> offerGames) {
        this.offerGames = offerGames;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Game> getGameTrade() {
        return gameTrade;
    }

    public void setGameTrade(List<Game> gameTrade) {
        this.gameTrade = gameTrade;
    }

    public List<Game> getGameOffer() {
        return gameOffer;
    }

    public void setGameOffer(List<Game> gameOffer) {
        this.gameOffer = gameOffer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
