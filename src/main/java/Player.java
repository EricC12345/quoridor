/* Player.java - CIS405 - teams
 * Last Edit: March 20, 2015
 * ____________________________________________________________________________
 *
 * represents the data of a player of the game, such as the player's name,
 *   number of walls, and the player's location on the gameboard
 * 
 * --------------------------------- METHODS ----------------------------------
 * 
 * Player(int,int)        --> constructs player with an ID and wall count
 * Player(int,string,int) --> constructs player with ID, name, and wall count
 * int getPlayerNo()      --> returns the player number
 * String getName()       --> returns the player's name
 * int getNumWalls()      --> returns number of walls
 * boolean mayPlaceWall() --> returns if a player has walls that they may place
 * boolean placeWall()    --> decrements numWalls if player can place a wall
 *
 * Considerations
 *     
 *      Is getNumWalls() needed if we're checking for the number of walls
 *      within method placeWall()? - Cavanagh, Walling (2/16)
 *     
 *      Depending on what the object that implements the game rules does,
 *      we may want placeWall() to be a boolean method and return false
 *      if the player cannot place any more walls - Walling (2/16)
 *
 *     oh yeah that's way better
 */

class Player {
    
    private String playerName = "";    // player's name
    private int numWalls;              // number of walls
    private int playerNo;              // unique player I.D. between 0 and 3

    //*************************************************************************

    /**
      * constructs a player object with a name based on pno
      * @param pno player number
      * @param numWalls number of walls the player is given
      */
    public Player(int pno, int numWalls) {
        this(pno, "player_" + pno, numWalls);
    } 

    //*************************************************************************

    /**
      * constructs a player object with a specfic name
      * @param pno player number
      * @param playerName name of player
      * @param numWalls number of walls the player is given
      */
    public Player(int pno, String playerName, int numWalls) {
        this.playerName = playerName;
        this.numWalls = numWalls;
        this.playerNo = pno;
    } 

    //*************************************************************************

    /**
      * returns the player's number
      * @return the players number
      */
    public int getPlayerNo() {
        return playerNo;
    }

    //*************************************************************************

    /**
      * returns the player's name
      * @return the name of the player
      */
    public String getName() {
        return playerName;
    }

    //*************************************************************************

    // Consider if this is needed when we start to implement wall placement
    // possibly useful for display if we wanna show walls in the hand
    /** 
      * returns the number of walls
      * @return the number of walls remaining
      */
    public int getNumWalls() {
        return numWalls;
    }

    //*************************************************************************

    /**
      * Checks to see if the player can place a wall
      * @return true if this player has any walls left to place     
      */    
    private boolean mayPlaceWall() {
        return(numWalls != 0);        
    }

    //*************************************************************************

    /**
      * Decrements the number of walls if possible
      * @return true if walls have been decremented, false otherwise
      */
    public boolean useWall() {
        if(mayPlaceWall()) {
            numWalls--;
            return true;
        }
        return false;
    } 
}
