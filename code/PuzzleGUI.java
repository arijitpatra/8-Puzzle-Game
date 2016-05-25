package pkg8.puzzle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.xml.crypto.dsig.Manifest;

import javax.imageio.ImageIO;

//import com.puzzle.utils.Shuffle1; //(needed on Eclipse, commented for Net Beans)
import java.math.*;
import java.util.Arrays;
import java.util.Collections;

public class PuzzleGUI extends JFrame implements ActionListener {

    static Integer[] data = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    int inversions;
    int moveCount = 0;
    int arr[] = new int[9];
    JButton tiles[][] = new JButton[3][3];
    JButton shufflebtn = new JButton("SHUFFLE");
    JButton solutionbtn = new JButton("SOLUTION");
    JButton resultbtn = new JButton("MOVE COUNT");
    JButton viewGoalBtn = new JButton("* VIEW GOAL *");
    JTextArea dis = new JTextArea(100, 170);

    JScrollPane scroll = new JScrollPane(dis);
    int puz[][] = new int[3][3];
    int i, j, bx, by;
    JPanel p = new JPanel();
    Border bd = new LineBorder(Color.decode("#058e60"));
    Font font = new Font("Impact", Font.PLAIN, 55);
    Font shufflebtnFont = new Font("Lucida Sans", Font.PLAIN, 9);
    Font solutionbtnFont = new Font("Lucida Sans", Font.PLAIN, 9);
    Font resultbtnFont = new Font("Lucida Sans", Font.PLAIN, 9);
    Font viewGoalBtnFont = new Font("Lucida Sans", Font.PLAIN, 9);
    JRadioButton mismatchButton = new JRadioButton("Mismatch");
    JRadioButton manhattanButton = new JRadioButton("Manhattan");
    ButtonGroup bGroup = new ButtonGroup();
    JPanel buttonPanel = new JPanel();
    String heuristic;
    boolean clickedOnSolution;
    int c;
    MismatchAndManhattan m, bestMismatchAndManhattan;

    String initialConfig;
    String presentConfig;// stores present config of puzzle as string
    String goalConfig;

    public PuzzleGUI() {

        c = -1;
        clickedOnSolution = false;
        dis.setMargin(new Insets(5, 5, 5, 5));//top left bottom right
        heuristic = "Mismatch";
        goalConfig = "123456780";
        puz = Shuffle1.config(data);

        //puz = new int[][] { { 8, 6,7  }, { 2, 5, 4 }, { 3, 0, 1 } };//hardest.takes 31 steps
        // puz = new int[][] { { 6, 4, 7 }, { 8, 5, 0 }, { 3, 2, 1 } };//hardest.takes 31 steps
        // here
        // puz = new int[][] { { 3, 1, 6 }, { 0, 5, 4 }, { 2, 7, 8 } };//change
        // here
        // puz = new int[][] { { 2, 8, 0 }, { 5, 7, 3 }, { 4, 1, 6 } };//change
        // here
        presentConfig = "";

        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                presentConfig = presentConfig.concat(String.valueOf(puz[i][j]));
            }
        }
        System.out.println(presentConfig);
        initialConfig = presentConfig;

        moveCount = 0;
        int k = 0;
        for (Integer x : data) {
            arr[k++] = x.intValue();
        }

        inversions = Shuffle1.getinversions(arr);
        if (inversions % 2 != 0) {
            dis.setLineWrap(true);
            dis.setText("Unsolvable.Shuffle Again.");

            shufflebtn.setText("SHUFFLE");
            solutionbtn.setEnabled(false);
            resultbtn.setEnabled(false);

        } else {
            dis.setLineWrap(true);
            dis.setText("Solvable.Start Playing.");

            shufflebtn.setText("NEW GAME");
            solutionbtn.setEnabled(true);
            resultbtn.setEnabled(true);
        }

        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {

                tiles[i][j] = new JButton("" + puz[i][j]);
                tiles[i][j].setBorder(bd);
                tiles[i][j].setBackground(Color.decode("#0ed18f"));
                tiles[i][j].setForeground(Color.WHITE);
                tiles[i][j].setFont(font);
                tiles[i][j].setBounds(100 * j, 100 * i, 100, 100); // x,y,w,h
                tiles[i][j].addActionListener(this);
                tiles[i][j].setFocusPainted(false);
                p.add(tiles[i][j]);
            }
        }

        if (inversions % 2 != 0) {
            for (i = 0; i < 3; i++) {
                for (j = 0; j < 3; j++) {

                    tiles[i][j].setEnabled(false);
                }
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

        buttonPanel.setBorder(BorderFactory.createTitledBorder("Heuristic"));
        buttonPanel.setLayout(new GridLayout(2, 1));

        bGroup.add(mismatchButton);
        bGroup.add(manhattanButton);
        mismatchButton.addActionListener(this);
        manhattanButton.addActionListener(this);

        mismatchButton.setSelected(true);

        buttonPanel.add(mismatchButton);
        buttonPanel.add(manhattanButton);

        buttonPanel.setBounds(309, 65, 100, 60);

        add(p);

        p.add(buttonPanel);

        p.add(shufflebtn);
        p.add(solutionbtn);
        p.add(resultbtn);

        p.add(viewGoalBtn);

        dis.setEditable(false);

        viewGoalBtn.setBounds(309, 12, 100, 30);
        viewGoalBtn.setFont(viewGoalBtnFont);
        viewGoalBtn.setBackground(Color.decode("#FFC400"));
        viewGoalBtn.setForeground(Color.black);

        shufflebtn.setBounds(309, 192, 100, 30);
        shufflebtn.setFont(shufflebtnFont);
        shufflebtn.setBackground(Color.decode("#E53935"));
        shufflebtn.setForeground(Color.WHITE);
        solutionbtn.setBounds(309, 132, 100, 30);
        solutionbtn.setFont(solutionbtnFont);
        solutionbtn.setBackground(Color.decode("#469E46"));
        solutionbtn.setForeground(Color.WHITE);
        resultbtn.setBounds(309, 252, 100, 30);
        resultbtn.setFont(resultbtnFont);
        resultbtn.setForeground(Color.WHITE);
        resultbtn.setBackground(Color.decode("#006064"));

        solutionbtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                clickedOnSolution = true;

                System.out.println("present is" + presentConfig + " goal is "
                        + goalConfig);

                System.out.println("inversions " + inversions);
                m = new MismatchAndManhattan(presentConfig, goalConfig);

                if (mismatchButton.isSelected() == true) {
                    heuristic = "Mismatch";
                } else {
                    heuristic = "Manhattan";
                }

                m.doSearch(heuristic);
                dis.setText("States explored = " + m.unique + "\n-------------------------------------------\nSolution found in " + m.totalSteps + " steps\n-------------------------------------------\nSolution Steps :\n"
                        + m.fullSolutionString);
                dis.setCaretPosition(0);

                // disabling all butoons except the one to be clicked as per
                // solution
                c = 5;
                String ch = "";
                try {
                    ch = ch + m.fullSolutionString.charAt(c);

                } catch (Exception e2) {
                    // TODO: handle exception
                }
                for (i = 0; i < 3; i++) {
                    for (j = 0; j < 3; j++) {
                        if (tiles[i][j].getText().toString().equals(ch)) {
                            tiles[i][j].setEnabled(true);

                        } else {
                            tiles[i][j].setEnabled(false);
                        }

                    }
                }

            }
        });

        shufflebtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                c = -1;
                clickedOnSolution = false;
                for (i = 0; i < 3; i++) {
                    for (j = 0; j < 3; j++) {

                        tiles[i][j].setEnabled(true);

                    }
                }

                presentConfig = "";

                puz = Shuffle1.config(data);

                for (i = 0; i < 3; i++) {
                    for (j = 0; j < 3; j++) {
                        presentConfig = presentConfig.concat(String
                                .valueOf(puz[i][j]));
                    }
                }
                System.out.println(presentConfig);
                initialConfig = presentConfig;

                moveCount = 0;

                int k = 0;
                for (Integer x : data) {
                    arr[k++] = x.intValue();
                }

                inversions = Shuffle1.getinversions(arr);
                if (inversions % 2 != 0) {
                    dis.setLineWrap(true);
                    dis.setText("Unsolvable.Shuffle Again.");
                    solutionbtn.setEnabled(false);
                    resultbtn.setEnabled(false);

                    shufflebtn.setText("SHUFFLE");

                    for (i = 0; i < 3; i++) {
                        for (j = 0; j < 3; j++) {

                            tiles[i][j].setEnabled(false);

                        }
                    }

                } else {
                    dis.setLineWrap(true);
                    dis.setText("Solvable.Start Playing.");

                    shufflebtn.setText("NEW GAME");
                    solutionbtn.setEnabled(true);
                    resultbtn.setEnabled(true);
                }

                for (i = 0; i < 3; i++) {
                    for (j = 0; j < 3; j++) {

                        tiles[i][j].setText("" + puz[i][j]);
                        tiles[i][j].setBorder(bd);
                        tiles[i][j].setBackground(Color.decode("#0ed18f"));
                        tiles[i][j].setForeground(Color.WHITE);
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

                dis.setText("Move count = " + moveCount);

            }
        });

        viewGoalBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                dis.setText("Goal state is:\n\n\n\n 1\t 2\t 3 \n\n\n 4\t 5\t 6 \n\n\n 7\t 8\t -");

                //final ImageIcon icon = new ImageIcon("C:\\Users\\ari.clicks\\Desktop\\goalstate.jpg");
                //JOptionPane.showMessageDialog(null, null , "8 Puzzle Game Goal State", JOptionPane.INFORMATION_MESSAGE, icon);
                //JOptionPane.showMessageDialog(null, "Goal state is:\n\n 1 2 3 \n 4 5 6 \n 7 8 -", "Goal State", 1);                
            }
        });

        setSize(650, 328);
        //UIManager.put("ScrollBar.width",100);
        //UIManager.put("ScrollBar.width", (int) ((int) UIManager.get("ScrollBar.width") * 2.5));
        scroll.setBounds(418, 10, 220, 280);
        //scroll.setLayout(new GridLayout());
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		//scroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, Integer.MAX_VALUE));
        //scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        p.add(scroll);
        //scroll.

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

                    if ((x == bx && Math.abs(y - by) == 1)
                            || (y == by && Math.abs(x - bx) == 1)) {
                        tx = puz[x][y];
                        puz[x][y] = puz[bx][by];
                        puz[bx][by] = tx;
                        moveCount++;
                        tiles[bx][by].setText("" + puz[bx][by]);
                        tiles[bx][by].setBackground(Color.decode("#0ed18f"));
                        tiles[bx][by].setForeground(Color.WHITE);
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

                        // set present config string
                        presentConfig = "";
                        for (i = 0; i < 3; i++) {
                            for (j = 0; j < 3; j++) {
                                presentConfig = presentConfig.concat(String
                                        .valueOf(puz[i][j]));
                            }
                        }
                        System.out.println(presentConfig);

                        if (clickedOnSolution == true) {
                            c = c + 7;
                            System.out.println("now c is:" + c);
                            String ch = "";
                            try {
                                ch = m.fullSolutionString.charAt(c) + "";
                            } catch (Exception e) {
                                // TODO: handle exception
                            }

                            System.out.println("ch is:\n " + ch);

                            for (int ii = 0; ii < 3; ii++) {
                                for (int jj = 0; jj < 3; jj++) {
                                    if (tiles[ii][jj].getText().toString()
                                            .equals(ch)) {
                                        tiles[ii][jj].setEnabled(true);

                                    } else {
                                        tiles[ii][jj].setEnabled(false);
                                    }

                                }
                            }

                            if (presentConfig.equalsIgnoreCase("123456780"))// user
                            // reached
                            // goal
                            // by
                            // seeing
                            // solution
                            {
                                solutionbtn.setEnabled(false);

                                Object[] options = {"New game", "Exit"};

                                JOptionPane optionPane = new JOptionPane(
                                        "Goal reached! Play again?",
                                        JOptionPane.QUESTION_MESSAGE,
                                        JOptionPane.YES_NO_OPTION, null,
                                        options, options[0]);

                                JDialog dialog = optionPane.createDialog(
                                        getParent(), "Goal reached!");

                                dialog.setVisible(true);
                                if (optionPane.getValue() == options[0]) {
                                    shufflebtn.doClick();
                                } else if (optionPane.getValue() == options[1]) {
                                    dispose();
                                }

                            }

                        } else if (presentConfig.equalsIgnoreCase("123456780"))// user
                        // reached
                        // goal
                        // without
                        // seeing
                        // solution
                        {
                            for (int ii = 0; ii < 3; ii++) {
                                for (int jj = 0; jj < 3; jj++) {
                                    tiles[ii][jj].setEnabled(false);
                                }
                            }

                            solutionbtn.setEnabled(false);

                            bestMismatchAndManhattan = new MismatchAndManhattan(
                                    initialConfig, goalConfig);

                            // heuristic="Mismatch";
                            bestMismatchAndManhattan.doSearch("Mismatch");
                            int mismatchSteps = bestMismatchAndManhattan.totalSteps;
                            System.out.println("\n%%%%%%mismatchsteps "
                                    + mismatchSteps);

                            // heuristic="Manhattan";
                            bestMismatchAndManhattan = new MismatchAndManhattan(
                                    initialConfig, goalConfig);
                            bestMismatchAndManhattan.doSearch("Manhattan");
                            int manhattanSteps = bestMismatchAndManhattan.totalSteps;
                            System.out.println("\n%%%%%%manhattansteps "
                                    + manhattanSteps);

                            int minSteps = Math.min(mismatchSteps,
                                    manhattanSteps);

                            System.out.println("\nMinsteps is " + minSteps);
                            System.out.println("\nMoveCount is " + moveCount);
                            System.out.println("\nRatio of Minstep : MoveCount is " + (((double) minSteps) / ((double) moveCount)));
                            double score;
                            score = ((double) minSteps / (double) moveCount) * 100.0;
                            //if(score>100.0)
                            //score=100.0;
                            score = Math.ceil(score);

							//int finalScore=(int)score;
                            dis.setText("Game Over! \nCongratulations!\nYour score is: "
                                    + /* String.format("%.0f", score) */ +(int) score
                                    + "\nBest possible score: 100\n\nMinimum possible moves is " + minSteps + ".\nYou did in " + moveCount + " moves.");

                            // congo dialog box
                            Object[] options = {"New game", "Exit"};

                            JOptionPane optionPane = new JOptionPane(
                                    "Congratulations!\nYour score is: "
                                    + /* String.format("%.0f", score) */ +(int) score
                                    + "\nBest possible score: 100\n\nMinimum possible moves is " + minSteps + ".\nYou did in " + moveCount + " moves.\n\nPlay again?",
                                    JOptionPane.QUESTION_MESSAGE,
                                    JOptionPane.YES_NO_OPTION, null, options,
                                    options[0]);

                            JDialog dialog = optionPane.createDialog(
                                    getParent(), "Goal Reached!");

                            dialog.setVisible(true);
                            if (optionPane.getValue() == options[0]) {
                                shufflebtn.doClick();
                            } else if (optionPane.getValue() == options[1]) {
                                dispose();
                            }
                            return;
                        }

                    }
                }

            }

        }

    }

    public static void main(String[] args) {

        try {
            Thread.sleep(5000);                 //delay in milliseconds.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        new PuzzleGUI();
    }
}
