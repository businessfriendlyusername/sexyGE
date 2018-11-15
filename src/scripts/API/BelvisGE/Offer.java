package scripts.API.BelvisGE;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.*;

import java.util.function.BooleanSupplier;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;

public class Offer {
    public enum Type {
        SELL,
        BUY;
    }
    private Type type;
    private int quantity;
    private int price;
    private String itemName;

    //Use a price of -1 to buy/sell at market price, -2 for instant buy/sell price
    public Offer(Type type, int quantity, int price, String itemName){
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.itemName = itemName;
    }

    public Type getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getItemName() {
        return itemName;
    }

    private boolean newOfferWindow(Type offerType){
        if(!BelvisGE.isOpen())//the GE window is open
            return false;
        GrandExchange.goToSelectionWindow(true);
        //~~~~~F2p test~~~~~~
        boolean f2p = false;
        RSInterfaceChild nullSlot = Interfaces.get(BelvisGE.GEInterfaceID, BelvisGE.offerSlot4ID);
        if(nullSlot.getChild(16).getText().equals("Empty") && nullSlot.getChild(3).getActions() == null)
            f2p = true;
        OfferSlot newOffer = BelvisGE.getOpenSlot(f2p);
        if(newOffer == null)
            return false;//we failed to get an open offer slot

        if(offerType == Type.BUY){
            RSInterface buyButton = Interfaces.get(BelvisGE.GEInterfaceID,
                    newOffer.getSlotInterfaceChildID(), BelvisGE.buyButtonComponentID);
            if(buyButton == null){
                return false;
            }
            Clicking.click(buyButton);
        }
        else{
            RSInterface sellButton = Interfaces.get(BelvisGE.GEInterfaceID,
                    newOffer.getSlotInterfaceChildID(), BelvisGE.sellButtonComponentID);
            if(sellButton == null){
                return false;
            }
            Clicking.click(sellButton);
        }
        Timing.waitCondition(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return GrandExchange.getWindowState() == GrandExchange.WINDOW_STATE.NEW_OFFER_WINDOW;
            }
        }, General.random(2000, 4000));
        if(GrandExchange.getWindowState() == GrandExchange.WINDOW_STATE.NEW_OFFER_WINDOW)
            return true;
        else
            return false;
    }

    private boolean selectItem(String itemName){
        Keyboard.typeString(itemName);
        General.sleep(400, 1000);
        RSInterface searchBox = Interfaces.get(162, 53);
        int i = 1;
        RSInterface itemText = searchBox.getChild(i);
        RSInterface itemButton = null;
        while(itemText != null){
            if(itemText.getText().equals(itemName)) {
                itemButton = searchBox.getChild(i - 1);
                //break;
            }
            i += 3;
            itemText = searchBox.getChild(i);
        }
        if(itemButton == null)
            return false;//we couldn't find the item we're trying to buy
        else
            itemButton.click("Select " + itemName);
        Timing.waitCondition(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return Interfaces.get(BelvisGE.GEInterfaceID, BelvisGE.buyScreenChildID, 25).getText().equals(itemName);
            }
        }, General.random(3000, 6000));
        if(Interfaces.get(BelvisGE.GEInterfaceID, BelvisGE.buyScreenChildID, 25).getText().equals(itemName))
            return true;
        else
            return false;
    }

    private void collectOffer(){
        for(OfferSlot slot : BelvisGE.getAllCompleteOffers()){
            RSInterface slotInterface = Interfaces.get(BelvisGE.GEInterfaceID, slot.getSlotInterfaceChildID(), slot.getViewID());
            if(slotInterface == null)
                return;
            slotInterface.click("View offer");
            Timing.waitCondition(new BooleanSupplier() {
                @Override
                public boolean getAsBoolean() {
                    return GrandExchange.getWindowState() == GrandExchange.WINDOW_STATE.OFFER_WINDOW;
                }
            }, General.random(2000, 4000));
            if(GrandExchange.getWindowState() != GrandExchange.WINDOW_STATE.OFFER_WINDOW)
                continue;
            RSItem[] items = GrandExchange.getCollectItems();
            GrandExchange.collectItems(GrandExchange.COLLECT_METHOD.BANK, items);
            GrandExchange.goToSelectionWindow(true);
            General.sleep(300, 700);
        }
    }

    public boolean executeOffer(){
        boolean sell;
        if(type == Type.BUY)
            sell = false;
        else
            sell = true;

        if(price == -2){//using instant buy/sell
            if(sell){
                RSItem[] item = Inventory.find(itemName);
                if(item.length < 1)
                    return false;
                RSItem itemToOffer = item[0];
                price = PriceLookup.getPrice(itemToOffer.getID());
                price *= 0.5;
                price -= 1;
                if(price == 0)
                    price = 1;
                if(!GrandExchange.offer(itemName, price, quantity, sell))
                    return false;
            }
            else{//buying an item
                if(!newOfferWindow(Type.BUY))//failed to open the new offer window
                    return false;
                General.sleep(300, 700);
                if(!selectItem(itemName))
                    return false;
                General.sleep(400, 800);
                RSInterface priceInterface = Interfaces.get(BelvisGE.GEInterfaceID, 26);
                if(priceInterface == null)
                    return false;
                try {
                    price = Integer.parseInt(priceInterface.getText());
                }
                catch (NumberFormatException e){
                    System.out.println("Failed to parse GE interface");
                    printStackTrace();
                }
                if(price == -2)//-2 is the original argument of executeOffer()
                    return false;
                price *= 1.5;
                price += 1;
                int coins = Inventory.getCount("Coins");
                if(coins == 0)
                    return false;//you have no money u dumb fuck
                if(price > coins)
                    price = coins;
                if(!GrandExchange.offer(itemName, price, quantity, sell))
                    return false;
            }
        }
        else {
            if (!GrandExchange.offer(itemName, price, quantity, sell))
                return false;
        }

        General.sleep(1000, 2000);
        collectOffer();
        return true;
    }
}
