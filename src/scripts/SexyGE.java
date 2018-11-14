package scripts;

import org.tribot.api.General;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.types.RSGEOffer;
import org.tribot.script.Script;
import scripts.API.BelvisGE.Offer;
import scripts.API.BelvisGE.Offers;
import scripts.API.Node;
import scripts.sexyGE.MakeOffer;
import scripts.sexyGE.WalkToGE;

import java.util.Collections;
import java.util.LinkedList;

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
        while (true) {
            General.sleep(1000);
            for (RSGEOffer offer : GrandExchange.getOffers()) {
                System.out.println(offer.getItemName() + " " + offer.getPrice() + " " + offer.getStatus());
            }
        }
    }
}
