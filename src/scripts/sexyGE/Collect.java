package scripts.sexyGE;

import org.tribot.api2007.Banking;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.types.RSGEOffer;
import scripts.API.Node;

public class Collect extends Node {
    @Override
    public void execute() {

    }

    @Override
    public boolean validate() {
        RSGEOffer[] offers = GrandExchange.getOffers();
        if(!Banking.isInBank())
            return false;
        for (RSGEOffer offer : offers) {
            if (offer.getStatus() == RSGEOffer.STATUS.COMPLETED || offer.getStatus() == RSGEOffer.STATUS.CANCELLED)
                return true;
        }
        return false;
    }
}
