
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * GUI class for displaying CircuitTracer
 *
 * @author Daylen Mathews
 */
public class CircuitTracerGUI extends JFrame {

    private static final int BOARD_WIDTH = 600;
    private static final int BOARD_HEIGHT = 600;
    private static final int SOLUTIONS_WIDTH = 200;
    private static final int SOLUTION_CELL_HEIGHT = 25;

    private static final Color START_COLOR = new Color(0x57bcf2);
    private static final Color END_COLOR = new Color(0xe03a3a);
    private static final Color TRACE_COLOR = new Color(0xe45eeb);

    private static final Font BOARD_FONT = new Font("Arial", Font.BOLD, 50);

    private ArrayList<CircuitBoard> solvedBoards;
    private JLabel[][] boardSpots;
    private int viewingSolution = -1;

    public CircuitTracerGUI(CircuitBoard unsolvedBoard, ArrayList<CircuitBoard> solvedBoards) {
        super("Circuit Tracer");
        this.solvedBoards = solvedBoards;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        setJMenuBar(createMenuBar()); //Set up the menu bar
        JPanel boardPanel = createBoardPanel(unsolvedBoard);// Board Panel
        add(boardPanel, BorderLayout.CENTER);
        JPanel sidePanel = createSidePanel();// Sidebar
        add(sidePanel, BorderLayout.EAST);
        JPanel navigationPanel = createNavigationPanel(); // Navigation Buttons
        add(navigationPanel, BorderLayout.SOUTH);

        pack();
        centerFrame(this);
        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
        fileMenu.add(exitItem);

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Circuit Tracer GUI\nStart: Blue | End: Red | Trace: Purple",
                "About Circuit Tracer",
                JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private JPanel createBoardPanel(CircuitBoard board) {
        int rows = board.numRows();
        int cols = board.numCols();

        JPanel panel = new JPanel(new GridLayout(rows, cols));
        panel.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        boardSpots = new JLabel[rows][cols];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                JLabel spot = new JLabel(String.valueOf(board.charAt(y, x)), SwingConstants.CENTER);
                spot.setFont(BOARD_FONT);
                spot.setOpaque(true);
                spot.setBackground(getBackgroundColor(board.charAt(y, x)));
                spot.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                boardSpots[y][x] = spot;
                panel.add(spot);
            }
        }
        return panel;
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel(new BorderLayout());
        JLabel solutionsLabel = new JLabel(String.format("%d Solution%s", solvedBoards.size(), solvedBoards.size() != 1 ? "s" : ""));
        solutionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sidePanel.add(solutionsLabel, BorderLayout.NORTH);

        JList<String> solutionList = new JList<>(new DefaultListModel<>());
        DefaultListModel<String> listModel = (DefaultListModel<String>) solutionList.getModel();
        for (int i = 0; i < solvedBoards.size(); i++) {
            listModel.addElement("Solution " + (i + 1));
        }

        solutionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        solutionList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = solutionList.getSelectedIndex();
                if (index != viewingSolution) {
                    viewSolution(index);
                }
            }
        });

        sidePanel.add(new JScrollPane(solutionList), BorderLayout.CENTER);
        sidePanel.setPreferredSize(new Dimension(SOLUTIONS_WIDTH, BOARD_HEIGHT));
        return sidePanel;
    }

    private JPanel createNavigationPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        JButton prevButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");

        prevButton.addActionListener(e -> viewSolution(viewingSolution - 1));
        nextButton.addActionListener(e -> viewSolution(viewingSolution + 1));

        panel.add(prevButton);
        panel.add(nextButton);
        return panel;
    }

    private Color getBackgroundColor(char charAt) {
        switch (charAt) {
            case '1':
                return START_COLOR;  //Color for start
            case '2':
                return END_COLOR;    //Color for end
            case 'T':
                return TRACE_COLOR;  //Color for trace
            case 'O':
                return Color.WHITE;  //Open space
            case 'X':
                return Color.GRAY;   //Blocked space
            default:
                return Color.LIGHT_GRAY; //Default color
        }
    }

    private void viewSolution(int index) {
        if (index < 0 || index >= solvedBoards.size()) {
            return;
        }

        viewingSolution = index;
        CircuitBoard board = solvedBoards.get(index); //gets solved board
        for (int y = 0; y < board.numRows(); y++) {
            for (int x = 0; x < board.numCols(); x++) {
                char cell = board.charAt(y, x);
                boardSpots[y][x].setText(String.valueOf(cell));
                boardSpots[y][x].setBackground(getBackgroundColor(cell));
            }
        }
    }

    private void centerFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(
                (screenSize.width - frame.getWidth()) / 2,
                (screenSize.height - frame.getHeight()) / 2);
    }

    private static class solvedBoards {

        public solvedBoards() {
        }
    }

    private static class boardSpots {

        public boardSpots() {
        }
    }
}
