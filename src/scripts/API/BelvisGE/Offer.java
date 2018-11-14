package scripts.API.BelvisGE;

import org.tribot.api2007.GrandExchange;

public class Offer {
    public enum Type {
        SELL,
        BUY;
    }
    private Type type;
    private int quantity;
    private int price;
    private String itemName;

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

    public boolean executeOffer(){
        boolean sell;
        if(type == Type.BUY)
            sell = false;
        else
            sell = true;
        return GrandExchange.offer(itemName, price, quantity, sell);
    }
}
