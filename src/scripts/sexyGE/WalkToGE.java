package scripts.sexyGE;

import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;
import scripts.API.BelvisGE.BelvisGE;
import scripts.API.Node;

import java.util.function.BooleanSupplier;

public class WalkToGE extends Node {

    @Override
    public void execute() {
        System.out.println("Walking to GE");
        WebWalking.walkTo(BelvisGE.GELoc, new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return Interfaces.get(595, 37) != null;//if we accidentaly opened the world map
            }
        }, 500);
        if(Interfaces.get(595, 37) != null)
            Interfaces.get(595, 37).click("Close");
    }

    @Override
    public boolean validate() {
        if(!BelvisGE.atGE())
            return true;
        else
            return false;
    }
}
