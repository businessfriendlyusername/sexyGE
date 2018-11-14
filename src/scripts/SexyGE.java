package scripts;

import org.tribot.api.General;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSGEOffer;
import org.tribot.script.Script;
import scripts.API.BelvisGE.PriceLookup;

public class SexyGE extends Script {

    @Override
    public void run() {

//        LinkedList<Node> nodes = new LinkedList<Node>();
//        Collections.addAll(nodes, new WalkToGE(), new MakeOffer());
//        Offers.addOffer(new Offer(Offer.Type.SELL, 1, 75, "Bones"));
//        while(true){
//            for(Node node : nodes){
//                if(node.validate())
//                    node.execute();
//            }
//        }
//    }
//        while (true) {
//            General.sleep(1000);
//            for (RSGEOffer offer : GrandExchange.getOffers()) {
//                System.out.println(offer.getItemName() + " " + offer.getPrice() + " " + offer.getStatus());
//            }
//        }

        while(true){
            General.sleep(1000);
            System.out.println(PriceLookup.getPrice(Inventory.find("Feather")[0].getID()));
        }
    }
}
