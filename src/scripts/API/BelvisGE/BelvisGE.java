package scripts.API.BelvisGE;

import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSGEOffer;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSTile;

public class BelvisGE {

    public static final RSTile GELoc = new RSTile(3164, 3487, 0);

    public static final int GEInterfaceID = 465;
    public static final int buyScreenChildID = 24;
    public static final int sellScreenChildID = 15;
    public static final int offerSlot1ID = 7;
    public static final int offerSlot2ID = 8;
    public static final int offerSlot3ID = 9;
    public static final int offerSlot4ID = 10;
    public static final int offerSlot5ID = 11;
    public static final int offerSlot6ID = 12;
    public static final int offerSlot7ID = 13;
    public static final int offerSlot8ID = 14;
    public static final int buyButtonComponentID = 3;
    public static final int sellButtonComponentID = 4;

    public static boolean atGE(){
         return Player.getPosition().distanceTo(GELoc) < 10;
    }

    public static boolean isOpen(){
         return Interfaces.get(465) != null;
    }

    public static OfferSlot getOpenSlot(boolean f2p){
         RSGEOffer[] offers = GrandExchange.getOffers();
         int length = 8;
         if(offers == null)
             return null;
         if(f2p)
             length = 3;
         for(int i = 0; i < length; i++){
             if(offers[i].getStatus() == RSGEOffer.STATUS.EMPTY){
                 return new OfferSlot(i);
             }
         }
         return null;
    }
}
