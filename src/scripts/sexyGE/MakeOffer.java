package scripts.sexyGE;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Camera;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSNPC;
import scripts.API.BelvisGE.BelvisGE;
import scripts.API.BelvisGE.Offer;
import scripts.API.BelvisGE.Offers;
import scripts.API.Node;

import java.util.function.BooleanSupplier;

public class MakeOffer extends Node {
    @Override
    public void execute() {
        if(!BelvisGE.isOpen()) {
            RSNPC[] clerks = NPCs.findNearest("Grand Exchange Clerk");
            if(clerks.length < 1)
                return;
            if(!DynamicClicking.clickRSNPC(clerks[0],"Exchange Grand Exchange Clerk")) {
                Camera.turnToTile(clerks[0]);
                DynamicClicking.clickRSNPC(clerks[0],"Exchange Grand Exchange Clerk");
            }
            Timing.waitCondition(new BooleanSupplier() {
                @Override
                public boolean getAsBoolean() {
                    return BelvisGE.isOpen();
                }
            }, General.random(4000, 6000));
        }


        System.out.println("Making an offer");
        Offer offer = Offers.getNextOffer();
        String predicate;
        String plural;
        String status;
        if(offer.executeOffer()){
            status = "SUCCEDED";
            Offers.popNextOffer();
        }

        else
            status = "FAILED";
        if(offer.getQuantity() == 1)
            plural = "";
        else plural = "s";
        if(offer.getType() == Offer.Type.BUY)
            predicate = "Buy";
        else
            predicate = "Sell";
        System.out.println("[GE] " + status + " " + predicate + " offer for " + offer.getQuantity()
                + " " + offer.getItemName() + plural + " at " + offer.getPrice());
    }

    @Override
    public boolean validate() {
        return !Offers.isEmpty() && BelvisGE.atGE();
    }
}
