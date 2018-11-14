package scripts.API.BelvisGE;

import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

public class BelvisGE {
     public static final RSTile GELoc = new RSTile(3164, 3487, 0);

     public static boolean atGE(){
         return Player.getPosition().distanceTo(GELoc) < 10;
     }

     public static boolean isOpen(){
         return Interfaces.get(465) != null;
     }
}
