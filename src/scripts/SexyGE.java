package scripts;

import org.tribot.api.General;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSGEOffer;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.Script;
import scripts.API.BelvisGE.BelvisGE;
import scripts.API.BelvisGE.Offer;
import scripts.API.BelvisGE.Offers;
import scripts.API.BelvisGE.PriceLookup;
import scripts.API.Node;
import scripts.sexyGE.MakeOffer;
import scripts.sexyGE.WalkToGE;

import java.util.Collections;
import java.util.LinkedList;

public class SexyGE extends Script {

    @Override
    public void run() {

        LinkedList<Node> nodes = new LinkedList<Node>();
        Collections.addAll(nodes, new WalkToGE(), new MakeOffer());
        Offers.addOffer(new Offer(Offer.Type.BUY, 1, -2, "Bones"));
        while(true){
            for(Node node : nodes){
                if(node.validate())
                    node.execute();
            }
        }
    }
}
