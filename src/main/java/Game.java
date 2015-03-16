/* Game.java (aka Quoridor) - CIS 405 - teams
 * Last Edit: March 14, 2015
 * ____________________________________________________________________________
 * 
 * implements the GameEngine and Protocols to create and run the game Quoridor
 */
import java.util.*;
import java.io.*;

// networking stuff... 
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Game {

    private static int numPlayers;     // how many players are in the game
    private static Player [] players ; // the players
    //private static PrintStream Deb.ug; // debugging outstream

    /*
     * prints a friendly message and exits
     * @param an int to return to the OS
     */
    private static void usage(int error) {
        System.err.println("usage: java Game <host> <port> <host> <port> [<host> <port> <host> <port>]");
        System.exit(error);
    }

    /*
     * parses command-line arguments
     * populates the inStreams and outStreams arrays in Protocol
     * counts the number of players, stores it in numPlayers
     * @param
     */
    public static void parseArgs (String[] args) {
        
        // ********* FIX ME *********************
        // Cannot start a a two player game with this in
        // need 4 arguments for a two player game
        if (args.length !=  4 && args.length != 8) {
            usage(1);
        }

        Protocol.ports = new int[args.length/2];
        Protocol.hosts = new String[args.length/2];

        //reconsider
        for (int i = 0; i < args.length; i++) { 
            Protocol.hosts[i/2] = args[i];
            i++;
            try {
                Protocol.ports[i/2] = Integer.parseInt(args[i]);
            } catch (Exception e) {
                usage(2);
            }
           
        }

        // Set the number of players
        numPlayers = args.length/2;
        
        Deb.ug.println("number of players: " + numPlayers);
        Deb.ug.println("hosts found: " + Arrays.toString(Protocol.hosts));
        Deb.ug.println("ports found: " + Arrays.toString(Protocol.ports));

        // Connect to players
        Protocol.outStreams = new PrintStream [Protocol.ports.length];
        Protocol.inStreams  = new Scanner     [Protocol.ports.length];
//         Protocol.inStreams = inStreams;
//         Protocol.outStreams = outStreams;
        for (int i = 0; i < Protocol.ports.length; i++) {
            try {
                Socket socket = new Socket(Protocol.hosts[i], Protocol.ports[i]);
                Protocol.outStreams[i] = new PrintStream(socket.getOutputStream());
                Protocol.inStreams[i] = new Scanner(socket.getInputStream());
            } catch (UnknownHostException uhe) {
                // the host name provided could not be resolved
                uhe.printStackTrace();
                System.exit(1);
            } catch (IOException ioe) {
                // there was a standard input/output error (lower-level)
                ioe.printStackTrace();
                System.exit(1);
            }
        }
        // test all connections -- not necessary, just for debugging
        for (int i = 0; i < Protocol.ports.length; i++) {
            if (Protocol.outStreams[i] == null) {
                System.out.println("outStreams[" + i + "] is null!");
            }
            if (Protocol.inStreams[i] == null) {
                System.out.println("inStreams[" + i + "] is null!");
            }
        }
    }

    public static void main (String[] args) {
        // initialize debug stream
        Deb.initialize("game");

        // Connect to players
        Deb.ug.println("args provided: " + Arrays.toString(args));
        parseArgs(args);

        // Instantiate GameBoard
        Deb.ug.println("instantiating GameBoard");
        GameBoard board = new GameBoard();

        // Instantiate Players array
        Deb.ug.println("instantiating Players array");
        players = new Player[numPlayers];

        board.setupInitialPosition(players);
        // tell all move servers who the players are
        Protocol.broadcastPlayers(players);

        // Start up the display
        Deb.ug.println("starting GameBoardFrame");
        GameBoardFrame f = new GameBoardFrame(board);

        // Initialize current player to player 0 (index 0)
        Player currentPlayer = players[0];

        // ***FIXME***
        // loop will need to check for a victory condition
        Deb.ug.println("beginning main loop");
        while (true) {
            // Get move from player
            Deb.ug.println("requesting move from player: " + currentPlayer.getName());
            String response = Protocol.requestMove(currentPlayer);
            Deb.ug.println("received: " + response);

            // Validate move
            boolean legal = GameEngine.validate(board, currentPlayer, 
                                                response);

            if (legal) {
                // Parse the move string to a square location
                Square destination = GameEngine.getSquare(board,response);
                // move player on board, broadcast move
                board.move(currentPlayer, destination);
                Protocol.broadcastWent(currentPlayer, response);
            } else {
                // if illegal, boot player & broadcast boot to other players
                Deb.ug.println("illegal move attempted");
                board.bootPlayer(currentPlayer);
                Protocol.broadcastBoot(currentPlayer);
                players[currentPlayer.getPlayerNo()] = null;
            }
        
            // Update the graphical board
            f.update(board);

            // Retrieve a possibly winning player
            Player winner = GameEngine.checkVictory(board, players);
            if (winner != null) {
                // If the retrieved player is a winner, display and exit loop
                Protocol.broadcastVictor(winner);
                break;
            }

            //...the game is still going, get the next player and continue
            
            // Get next player's turn 
            currentPlayer = 
                GameEngine.nextPlayer(currentPlayer.getPlayerNo(), players);
 
            // Sleepy time
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // ignore it
            }
        }

        Protocol.closeAllStreams(players);
        // maybe sleep here?
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // ignore it; just quit
        }

        System.exit(0);
    }
}
