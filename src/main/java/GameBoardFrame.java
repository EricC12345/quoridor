import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameBoardFrame extends JFrame{

    private JFrame gameboard;

    //constructs JFrame
    public GameBoardFrame(GameBoard board){

        //initialize JFrame
        gameboard = new JFrame("Quoridor");
        gameboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameboard.setSize(1000,700);
        gameboard.setLocationRelativeTo(null);

        //creates the grid
        GridLayout game = new GridLayout(10,10);
        gameboard.setLayout(game);

        //draw the original board
        draw(board);
    }

    public void update(GameBoard board){
        gameboard.getContentPane().removeAll();
        draw(board);

    }

    private void draw(GameBoard board){
        topLayer();
        rows(board);
        gameboard.pack();
        gameboard.setVisible(true);
    }

    //creates rows A-I
    private void rows(GameBoard board){
        row("A", 1, board);
        row("B", 2, board);
        row("C", 3, board);
        row("D", 4, board);
        row("E", 5, board);
        row("F", 6, board);
        row("G", 7, board);
        row("H", 8, board);
        row("I", 9, board);
    }

    //creates a row
    private void row(String row, int rownum, GameBoard board){

        JLabel labelblank = new JLabel();
        labelblank.setOpaque(true);
        labelblank.setBackground(new Color(0, 0, 30));
        labelblank.setPreferredSize(new Dimension(100, 70));
        labelblank.setText("    " + row);
        gameboard.getContentPane().add(labelblank, BorderLayout.CENTER);

        for (int i = 1; i < 10; i++){
            if (!board.isOccupied(i-1,rownum-1)){ //Tylor Changed this
                JLabel labelblue = new JLabel();
                labelblue.setOpaque(true);
                labelblue.setBackground(new Color(0, 0, 150));
                labelblue.setPreferredSize(new Dimension(100, 70));
                labelblue.setBorder(BorderFactory.createLineBorder(Color.black));
                gameboard.getContentPane().add(labelblue, BorderLayout.CENTER);
            } else {
                printPlayerLabel(board.getPlayer(i-1,rownum-1));//And this.
            }
        }
    }

    private void printPlayerLabel(Player p){
        JLabel label = new JLabel();
        label.setOpaque(true);
        if(p.getPlayerNo() == 1)
            label.setBackground(new Color(150, 0, 0));
        else if(p.getPlayerNo() == 2)
            label.setBackground(new Color(0, 150, 0));
        else if(p.getPlayerNo() == 3)
            label.setBackground(new Color(150, 0, 150));
        else
            label.setBackground(new Color(0, 150, 150));
        label.setPreferredSize(new Dimension(100, 70));
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.setText("    " + p.getName());
        gameboard.getContentPane().add(label, BorderLayout.CENTER);
    }

    // prints out I-IX
    private void topLayer() {
        JLabel [] labels = new JLabel[10];
        // This bit will print a blank for the first spot
        labels[0] = new JLabel();
        labels[0].setOpaque(true);
        labels[0].setBackground(new Color(0, 0, 30));
        labels[0].setPreferredSize(new Dimension(100, 70));
        labels[0].setText("    ");
        gameboard.getContentPane().add(labels[0], BorderLayout.CENTER);
        // Index starts at 1, toNumerals starts at i-1
        for (int i = 1; i < 10; i++) {
            labels[i] = new JLabel();
            labels[i].setOpaque(true);
            labels[i].setBackground(new Color(0, 0, 30));
            labels[i].setPreferredSize(new Dimension(100, 70));
            labels[i].setText("    " + GameEngine.toNumerals(i-1));
            gameboard.getContentPane().add(labels[i], BorderLayout.CENTER);
        }
    }
}