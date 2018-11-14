package scripts;

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

        LinkedList<Node> nodes = new LinkedList<Node>();
        Collections.addAll(nodes, new WalkToGE(), new MakeOffer());
        Offers.addOffer(new Offer(Offer.Type.SELL, 10, -1, "Bones"));
        while(true){
            for(Node node : nodes){
                if(node.validate())
                    node.execute();
            }
        }
    }
}
