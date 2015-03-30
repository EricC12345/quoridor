/** SquareTest.java - CIS405 - teams
  * Last Edit: February 19, 2014
  * ___________________________________________________
  *
  * tests the Square object's methods
  */

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class SquareTest {
    
    Square square;
    Player player;
   
    
    @Before
    public void beef() throws Exception {
        // do whatever you want here
        // I always do what I want hehe

       square = new Square(3,5);
       player = new Player(0, 10);
               

    }

    @Test
    public void testSquareConstructor() throws Exception {
        assertNotNull("Square() returned null", square);
    }

    @Test
    public void testSquareGetPlayer() throws Exception {
       assertNull(square.getPlayer());
    }

    @Test
    public void testSquareAddPlayer() throws Exception {
       square.addPlayer(player);
       assertEquals(square.getPlayer(),player);
    }

    @Test
    public void testVacant() throws Exception {
        assertTrue(square.vacant());
        square.addPlayer(player);
        assertFalse(square.vacant());
    }

    @Test
    public void testSquareRemovePlayer() throws Exception {
       //Add the player to the board to remove it
       square.addPlayer(player);
       assertNotNull(square.getPlayer());

       // Remove the player and check 
       square.removePlayer();
       assertNull(square.getPlayer());
    }

    @Test
    public void testEquals() throws Exception {
        Square square2 = new Square(3,5);
        assertTrue(square.equals(square2));
    }

    @Test
    public void testSquarePlaceVertWall() throws Exception {
        square.placeWallBottom(true);
	assertTrue(square.hasWallBottom());

	square.placeWallBottom(false);
	assertTrue(square.hasWallBottom());
    }   

    @Test
    public void testSquarePlaceHorizWall() throws Exception {
	square.placeWallRight(true);
	assertTrue(square.hasWallRight());

	square.placeWallRight(false);
	assertTrue(square.hasWallRight());
    }
}
