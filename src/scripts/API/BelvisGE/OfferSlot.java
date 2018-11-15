package scripts.API.BelvisGE;

public class OfferSlot {

    public OfferSlot(int index){
        slotIndex = index;
        slotInterfaceChildID = index + 7;
    }

    //the index of the GE offer slot starting at 0 (0-7)
    private int slotIndex;

    //the interface id of the offer slot
    private int slotInterfaceChildID;

    //the id of the buy offer component
    private final int BuyID = 3;

    //the id of the sell offer component
    private final int SellID = 4;

    //the id of the view offer component
    private final int ViewID = 2;

    public int getViewID() {
        return ViewID;
    }

    public int getSlotIndex() {
        return slotIndex;
    }

    public int getSlotInterfaceChildID() {
        return slotInterfaceChildID;
    }

    public int getBuyID() {
        return BuyID;
    }

    public int getSellID() {
        return SellID;
    }


}
