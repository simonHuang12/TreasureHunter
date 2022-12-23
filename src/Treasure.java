public class Treasure
{
    public static final String GEM = "Gems";
    public static final String TROPHY = "the Trophy";
    public static final String CROWN = "the Crown";
    // the "treasure" representing nothing public static final String DUST = "Dust";
    public static final String DUST = "Dust";
    private String type;
    public Treasure()
    {
        int random = (int) (Math.random() * 7) + 1;
        if (random == 1)
        {
            type = GEM;
        }
        else if (random == 2)
        {
            type = TROPHY;
        }
        else if (random == 3)
        {
            type = CROWN;
        }
        else if (random >= 4)
        {
            type = DUST;
        }
    }
    public static boolean collectionHasAllTreasures (String collection)
    {
        boolean hasGem = (collection.contains(GEM));
        boolean hasTrophy = (collection.contains(TROPHY));
        boolean hasCrown = (collection.contains(CROWN));
        return (hasGem && hasTrophy && hasCrown);
    }
    public String getType()
    {
        return type;
    }
}