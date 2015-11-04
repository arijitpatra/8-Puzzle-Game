/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg8.puzzle;

/**
 *
 * @author ari.clicks
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;

//import com.puzzle.utils.Shuffle1; //(needed on Eclipse, commented for Net Beans)

import java.math.*;
import java.util.Arrays;
import java.util.Collections;

public class PuzzleGUI extends JFrame implements ActionListener {
    static Integer[] data = { 1, 2, 3, 4, 5, 6, 7, 8, 0 };
    int inversions;
    int moveCount = 0;
    int arr[] = new int[9];
    JButton tiles[][] = new JButton[3][3];
    JButton shufflebtn = new JButton("SHUFFLE");
    JButton solutionbtn = new JButton("SOLUTION");
    JButton resultbtn = new JButton("RESULT");
    JTextArea dis = new JTextArea(100, 170);
    JScrollPane scroll = new JScrollPane(dis);
    int puz[][] = new int[3][3];
    int i, j, bx, by;
    JPanel p = new JPanel();
    Border bd = new LineBorder(Color.DARK_GRAY);
    Font font = new Font("Serif", Font.BOLD, 70);
    Font shufflebtnFont = new Font("Serif", Font.PLAIN, 10);
    Font solutionbtnFont = new Font("Serif", Font.PLAIN, 10);
    Font resultbtnFont = new Font("Serif", Font.PLAIN, 10);

    public PuzzleGUI() {
        puz = Shuffle1.config(data);

        int k = 0;
        for (Integer x : data) {
            arr[k++] = x.intValue();
        }

        inversions = Shuffle1.getinversions(arr);
        if (inversions % 2 != 0) {
            dis.setLineWrap(true);
            dis.setText("Unsolvable. \nShuffle Again.");
        //shufflebtn.setEnabled(true);
            shufflebtn.setText("SHUFFLE");
        }

        else {
            dis.setLineWrap(true);
            dis.setText("Solvable. \nStart Playing.");
        //shufflebtn.setEnabled(false);
            shufflebtn.setText("NEW GAME");
        }

        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {

                tiles[i][j] = new JButton("" + puz[i][j]);
                tiles[i][j].setBorder(bd);
                tiles[i][j].setBackground(Color.YELLOW);
                tiles[i][j].setForeground(Color.BLUE);
                tiles[i][j].setFont(font);
                tiles[i][j].setBounds(100 * j, 100 * i, 100, 100); // x,y,w,h
                tiles[i][j].addActionListener(this);
                tiles[i][j].setFocusPainted(false);
                p.add(tiles[i][j]);
            }
        }

        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                if (puz[i][j] == 0) {
                    bx = i;
                    by = j;
                }
            }
        }
        tiles[bx][by].setBackground(Color.WHITE);
        tiles[bx][by].setText("");
        p.setLayout(null);

        add(p);
        p.add(shufflebtn);
        p.add(solutionbtn);
        p.add(resultbtn);
        // p.add(dis);

        dis.setEditable(false);
        // dis.setText("Hi");
        shufflebtn.setBounds(312, 182, 100, 30);
        shufflebtn.setFont(shufflebtnFont);
        shufflebtn.setBackground(Color.black);
        shufflebtn.setForeground(Color.WHITE);
        solutionbtn.setBounds(312, 222, 100, 30);
        solutionbtn.setFont(solutionbtnFont);
        solutionbtn.setBackground(Color.GREEN);
        resultbtn.setBounds(312, 262, 100, 30);
        resultbtn.setFont(resultbtnFont);
        resultbtn.setForeground(Color.WHITE);
        resultbtn.setBackground(Color.RED);

        shufflebtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                puz = Shuffle1.config(data);

                int k = 0;
                for (Integer x : data) {
                    arr[k++] = x.intValue();
                }

                inversions = Shuffle1.getinversions(arr);
                if (inversions % 2 != 0) {
                    dis.setLineWrap(true);
                    dis.setText("Unsolvable. \nShuffle Again.");
                //shufflebtn.setEnabled(true);
                    shufflebtn.setText("SHUFFLE");
                }
                else {
                    dis.setLineWrap(true);
                    dis.setText("Solvable. \nStart Playing.");
                //shufflebtn.setEnabled(false);
                shufflebtn.setText("NEW GAME");
                }

                for (i = 0; i < 3; i++) {
                    for (j = 0; j < 3; j++) {

                        tiles[i][j].setText("" + puz[i][j]);
                        tiles[i][j].setBorder(bd);
                        tiles[i][j].setBackground(Color.YELLOW);
                        tiles[i][j].setForeground(Color.BLUE);
                        tiles[i][j].setFont(font);
                        tiles[i][j].setBounds(100 * j, 100 * i, 100, 100); // x,y,w,h

                        tiles[i][j].setFocusPainted(false);
                        p.add(tiles[i][j]);
                    }
                }

                for (i = 0; i < 3; i++) {
                    for (j = 0; j < 3; j++) {
                        if (puz[i][j] == 0) {
                            bx = i;
                            by = j;
                        }
                    }
                }
                tiles[bx][by].setBackground(Color.WHITE);
                tiles[bx][by].setText("");

            }
        });

       resultbtn.addActionListener(new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            //activated only when game is complete
            dis.setText("");
            dis.setText("" + moveCount);
            moveCount=0;
        }
    });
        
        setSize(430, 328);

        scroll.setBounds(312, 5, 100, 170);
        p.add(scroll);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        setResizable(false);
        setTitle("8 Puzzle Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        int x = 0, y = 0, tx = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tiles[i][j] == ae.getSource()) {
                    x = i;
                    y = j;
                    System.out.println(x + " " + y);
                    System.out.println(bx + " " + by);
                    if ((x == bx && Math.abs(y - by) == 1)
                            || (y == by && Math.abs(x - bx) == 1)) {
                        tx = puz[x][y];
                        puz[x][y] = puz[bx][by];
                        puz[bx][by] = tx;
                        
                        moveCount++;
                        System.out.println(moveCount);

                        tiles[bx][by].setText("" + puz[bx][by]);
                        tiles[bx][by].setBackground(Color.YELLOW);
                        tiles[bx][by].setForeground(Color.BLUE);
                        tiles[x][y].setBackground(Color.WHITE);
                        tiles[x][y].setText("");
                        for (i = 0; i < 3; i++) {
                            for (j = 0; j < 3; j++) {
                                if (puz[i][j] == 0) {
                                    bx = i;
                                    by = j;
                                }
                            }
                        }

                        return;
                    }
                }

            }

        }
    }

    public static void main(String[] args) {

        new PuzzleGUI();
    }
}

