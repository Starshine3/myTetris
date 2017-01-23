import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

import javax.swing.*;

public class Game extends JFrame {

    public Game(int row, int col) {
        super("myTetris");
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Board board = new Board(row, col);
        add(board);
        setVisible(true);
        board.start();
    }

    public Game() {
        super("myTetris");
        setSize(SETUP_WIDTH, SETUP_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel welcome = new JLabel("Welcome to myTetris!\n Please specify the number of rows and columns of the tetris board that you'd like to play.");
        JLabel rowMess = new JLabel("Rows");
        rowBox = new JTextField(5);
        JLabel colMess = new JLabel("Columns");
        colBox = new JTextField(5);
        initializeButtons();
        JPanel setup = new JPanel();
        setup.add(welcome);
        setup.add(rowMess);
        setup.add(rowBox);
        setup.add(colMess);
        setup.add(colBox);
        setup.add(start);
        setup.add(auto);
        setup.add(exit);
        add(setup);
        setVisible(true);
    }

    public void initializeButtons() {
        start = new JButton("Start");
        start.addActionListener(new ButtonListener());
        auto = new JButton("Default");
        auto.addActionListener(new ButtonListener());
        exit = new JButton("Exit");
        exit.addActionListener(new ButtonListener());
    }

    public static void main(String[] args) {
        game = new Game(20, 10);
        //setup = new Game();
    }

    private final int SETUP_WIDTH = 300;
    private final int SETUP_HEIGHT = 100;
    private final int GAME_WIDTH = 300;
    private final int GAME_HEIGHT = 620;
    private static Game setup;
    private static Game game;
    private JTextField rowBox;
    private JTextField colBox;
    private JButton start;
    private JButton auto;
    private JButton exit;

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String buttonPressed = e.getActionCommand();
            if (buttonPressed.equals("Start")) {
                if (!rowBox.getText().equals("") && !colBox.getText().equals("")) {
                    int row = Integer.parseInt(rowBox.getText());
                    int col = Integer.parseInt(colBox.getText());
                    setup.setVisible(false);
                    setup.dispose();
                    game = new Game(row, col);
                }
            } else if (buttonPressed.equals("Exit")) {
                System.exit(0);
            } else if (buttonPressed.equals("Default")) {
                setup.setVisible(false);
                setup.dispose();
                game = new Game(20, 10);
            }
        }
    }
}