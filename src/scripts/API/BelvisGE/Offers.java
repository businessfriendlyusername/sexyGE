package scripts.API.BelvisGE;

import java.util.LinkedList;

public class Offers {

    private static LinkedList<Offer> offers = new LinkedList<Offer>();

    public static Offer popNextOffer(){
        return offers.pop();
    }

    public static Offer getNextOffer(){
        return offers.getFirst();
    }

    public static void addOffer(Offer offer){
        offers.add(offer);
    }

    public static boolean isEmpty(){
        return offers.size() == 0;
    }
}
