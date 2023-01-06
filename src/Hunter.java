/**
 * Hunter Class<br /><br />
 * This class represents the treasure hunter character (the player) in the Treasure Hunt game.
 * This code has been adapted from Ivan Turner's original program -- thank you Mr. Turner!
 */
public class Hunter
{
    //Keeps the items in the kit separate
    private static final String KIT_DELIMITER = ";";
    private static  final String DELIMITER = ";";

    //instance variables
    private String hunterName;
    private String inventory;
    private String treasureCollection;
    private int gold;

    //Constructor
    /**
     * The base constructor of a Hunter assigns the name to the hunter and an empty kit.
     *
     * @param hunterName The hunter's name.
     */
    public Hunter(String hunterName, int startingGold)
    {
        this.hunterName = hunterName;
        inventory = "";
        gold = startingGold;
        treasureCollection = "";
    }

    //Accessors
    public String getHunterName()
    {
        return hunterName;
    }

    public String getInv()
    {
        return inventory;
    }

    public int getGold()
    {
        return gold;
    }
    public String getTreasureCollection(){
        return treasureCollection;
    }

    public void changeGold(int modifier)
    {
        gold += modifier;
        if (gold < 0)
        {
            gold = 0;
        }
    }

    /**
     * Buys an item from a shop.
     *
     * @param item The item the hunter is buying.
     * @param costOfItem  the cost of the item
     *
     * @return true if the item is successfully bought.
     */
    public boolean buyItem(String item, int costOfItem) {
        if (costOfItem == 0 || gold < costOfItem || hasItem(item)) {
            return false;
        }
        gold -= costOfItem;
        return addItem(item.substring(0, 1).toUpperCase() + item.substring(1).toLowerCase());
    }

    /**
     * The Hunter is selling an item to a shop for gold.<p>
     * This method checks to make sure that the seller has the item and that the seller is getting more than 0 gold.
     *
     * @param item The item being sold.
     * @param buyBackPrice  the amount of gold earned from selling the item
     * @return true if the item was successfully sold.
     */
    public boolean sellItem(String item, int buyBackPrice) {
        if (buyBackPrice <= 0 || !hasItem(item)) {
            return false;
        }
        gold += buyBackPrice;
        removeItemFromKit(item);
        return true;
    }

    /**
     *  Removes an item from the kit.
     *
     * @param item The item to be removed.
     */
    public void removeItemFromKit(String item) {
        // if item is found
        if (hasItem(item)) {
            int itmIdx = inventory.toLowerCase().indexOf(item.toLowerCase());
            int endIdx = inventory.indexOf(" ", itmIdx);
            String tmpKit = inventory.substring(0, itmIdx);
            tmpKit += inventory.substring(endIdx + 1);

            // update kit
            inventory = tmpKit;
        }
    }

    /**
     * Checks to make sure that the item is not already in the kit.
     * If not, it adds an item to the end of the String representing the hunter's kit.<br /><br />
     * A KIT_DELIMITER character is added to the end of the of String.
     *
     * @param item The item to be added to the kit.
     * @returns true if the item is not in the kit and has been added.
     */
    private boolean addItem(String item) {
        if (!hasItem(item)) {
            inventory += item + " ";
            return true;
        }
        return false;
    }
    /**
     * Checks to make sure that the treasure has not already been collected.
     * If not, it adds an item to the end of the String representing the hunter's treasure collection.<br /><br />
     * A DELIMITER character is added to the end of the of String.
     *
     * @param treasure The treasure to be added to the kit.
     * @returns true if the treasure is not in the treasure collection and has been added.
     */
    public boolean collectTreasure(Treasure treasure)
    {
        String item = treasure.getType();
        if (!hasItem(item))
        {
            treasureCollection += item + DELIMITER;
            return true;
        }

        return false;
    }

    /**
     * Searches the kit String for a specified item.
     *
     * @param item The search item
     *
     * @return true if the item is found.
     */
    public boolean hasItemInKit(String item) {
        int placeholder = 0;

        while (placeholder < inventory.length() - 1) {
            int endOfItem = inventory.indexOf(KIT_DELIMITER, placeholder);
            String tmpItem = inventory.substring(placeholder, endOfItem);
            placeholder = endOfItem + 1;
            if (tmpItem.equalsIgnoreCase(item)) {
                // early return
                return true;
            }
        }
        return false;
    }

    /**
     * Searches the inventory String for a specified item.
     *
     * @param item The search item
     *
     * @return true if the item is found.
     */
    public boolean hasItem(String item) {
        return inventory.toLowerCase().contains(item.toLowerCase());
    }


    /** Returns a printable representation of the inventory, which
     *  is a list of the items in kit, with the KIT_DELIMITER replaced with a space
     *
     * @return  The printable String representation of the inventory
     */
    public String getInventory()
    {
        return replaceDelimiter(inventory);
    }
    public String getTreasures(){
        return replaceDelimiter(treasureCollection);
    }
    private String replaceDelimiter(String str){
        String updatedStr = str;
        String space = " ";
        int index = 0;

        while (updatedStr.contains(DELIMITER)){
            index = updatedStr.indexOf(DELIMITER);
            updatedStr = updatedStr.substring(0, index)+space+updatedStr.substring(index+1);
        }
        return updatedStr;
    }

    /**
     * @return A string representation of the hunter.
     */
    public String toString()
    {
        String str = hunterName + " has " + gold + " gold";
        if (!inventory.equals(""))
        {
            str += " and a(n) " + getInventory();
        }else{
            str += " and nothing else";
        }

        str += "\nTreasures collected: ";
        if (!treasureCollection.equals("")){
            str += getTreasures();
        }else{
            str += "none";
        }
        return str;
    }
}