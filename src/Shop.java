/**
 * The Shop class controls the cost of the items in the Treasure Hunt game.<p>
 * The Shop class also acts as a go between for the Hunter's buyItem() method.<p>
 * This code has been adapted from Ivan Turner's original program -- thank you Mr. Turner!
 */
import java.util.Locale;
import java.util.Scanner;

public class Shop
{
    // constants
    private static final int WATER_COST = 2;
    private static final int ROPE_COST = 4;
    private static final int MACHETE_COST = 6;
    private static  final int PARKA_COST = 8;
    private static final int HORSE_COST = 12;
    private static final int BOAT_COST = 20;

    // instance variables
    private double markdown;
    private double discount = 1;
    private Hunter customer;

    //Constructor
    public Shop(double markdown)
    {
        this.markdown = markdown;
        customer = null;
        if (TreasureHunter.isEasyMode()){
            discount = .6;
        }
    }

    /** method for entering the shop
     * @param hunter  the Hunter entering the shop
     * @param buyOrSell  String that determines if hunter is "B"uying or "S"elling
     */
    public void enter(Hunter hunter, String buyOrSell)
    {
        customer = hunter;

        Scanner scanner = new Scanner(System.in);
        if (buyOrSell.equalsIgnoreCase("b"))
        {
            System.out.println("Welcome to the shop! We have the finest wares in town.");
            System.out.println("Currently we have the following items:");
            System.out.println(inventory());
            System.out.print("What're you lookin' to buy? ");
            String item = scanner.nextLine();
            int cost = checkMarketPrice(item, true);
            if (cost == 0)
            {
                System.out.println("We ain't got none of those.");
            }
            else
            {
                System.out.print("It'll cost you " + cost + " gold. Buy it (y/n)? ");
                String option = scanner.nextLine();

                if (option.equalsIgnoreCase("y"))
                {
                    buyItem(item);
                }
            }
        }
        else
        {
            System.out.println("What're you lookin' to sell? ");
            System.out.print("You currently have the following items: " + customer.getInventory());
            String item = scanner.nextLine();
            int cost = checkMarketPrice(item, false);
            if (cost == 0)
            {
                System.out.println("We don't want none of those.");
            }
            else
            {
                System.out.print("It'll get you " + cost + " gold. Sell it (y/n)? ");
                String option = scanner.nextLine();

                if (option.equalsIgnoreCase("y"))
                {
                    sellItem(item);
                }
            }
        }
    }

    /** A method that returns a string showing the items available in the shop (all shops sell the same items)
     *
     * @return  the string representing the shop's items available for purchase and their prices
     */
    public String inventory()
    {
        String str;
        if (TreasureHunter.isCheatMode()){
            str = "Water: " + 1 + " gold\n";
            str += "Rope: " + 1 + " gold\n";
            str += "Machete: " + 1 + " gold\n";
            str += "Parka: " + 1 + " gold\n";
            str += "Horse: " + 1 + " gold\n";
            str += "Boat: " + 1 + " gold\n";
        }else {
            str = "Water: " + (int)(WATER_COST*discount) + " gold\n";
            str += "Rope: " + (int)(ROPE_COST*discount) + " gold\n";
            str += "Machete: " + (int)(MACHETE_COST*discount) + " gold\n";
            str += "Parka: " + (int)(PARKA_COST*discount) + " gold\n";
            str += "Horse: " + (int)(HORSE_COST*discount) + " gold\n";
            str += "Boat: " + (int)(BOAT_COST*discount) + " gold\n";

        }
        return str;
    }

    /**
     * A method that lets the customer (a Hunter) buy an item.
     * @param item The item being bought.
     */
    public void buyItem(String item)
    {
        int costOfItem = checkMarketPrice(item, true);
        if (customer.buyItem(item, costOfItem))
        {
            System.out.println("Ye' got yerself a " + item + ". Come again soon.");
        }
        else
        {
            System.out.println("Hmm, either you don't have enough gold or you've already got one of those!");
        }
    }

    /**
     * A pathway method that lets the Hunter sell an item.
     * @param item The item being sold.
     */
    public void sellItem(String item)
    {
        int buyBackPrice = checkMarketPrice(item, false);
        if (customer.sellItem(item, buyBackPrice))
        {
            System.out.println("Pleasure doin' business with you.");
        }
        else
        {
            System.out.println("Stop stringin' me along!");
        }
    }

    /**
     * Determines and returns the cost of buying or selling an item.
     * @param item The item in question.
     * @param isBuying Whether the item is being bought or sold.
     * @return The cost of buying or selling the item based on the isBuying parameter.
     */
    public int checkMarketPrice(String item, boolean isBuying)
    {
        if (isBuying)
        {
            return getCostOfItem(item);
        }
        else
        {
            return getBuyBackCost(item);
        }
    }

    /**
     * Checks the item entered against the costs listed in the static variables.
     *
     * @param item The item being checked for cost.
     * @return The cost of the item or 0 if the item is not found.
     */
    public int getCostOfItem(String item)
    {
        if (TreasureHunter.isCheatMode()){
            return switch (item.toLowerCase()) {
                case "water", "boat", "horse", "parka", "machete", "rope" -> 1;
                default -> 0;
            };
        }else {
            return switch (item.toLowerCase()) {
                case "water" -> (int)(WATER_COST*discount);
                case "rope" -> (int)(ROPE_COST*discount);
                case "machete" -> (int)(MACHETE_COST*discount);
                case "parka" -> (int)(PARKA_COST*discount);
                case "horse" -> (int)(HORSE_COST*discount);
                case "boat" -> (int)(BOAT_COST*discount);
                default -> 0;
            };
        }

    }

    /**
     * Checks the cost of an item and applies the markdown.
     *
     * @param item The item being sold.
     * @return The sell price of the item.
     */
    public int getBuyBackCost(String item)
    {
        return (int)(getCostOfItem(item) * markdown);
    }
}