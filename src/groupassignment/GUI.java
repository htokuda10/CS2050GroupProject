package groupassignment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The GUI class creates a window that allows a user to run a simulation with
 * the option to adjust 4 different parameters.
 * @author Hideyuki Tokuda, Jason Mathews, Jedediah Hernandez
 */
public class GUI {

    private JFrame frame;
    private JLabel contentPane;
    private JPanel northPanel;
    private JTextArea results;
    private JScrollPane resultsPane;
    private JPanel westPanel;
    private JPanel centerPanel;
    private JPanel eastPanel;

    private JSlider totalTimeSlider;
    private JLabel totalTimeLabel;
    private JLabel totalTimeValue;

    private JSlider questionMeanSlider;
    private JLabel questionMeanLabel;
    private JLabel questionMeanValue;
    private int questionSim;

    private JSlider walkInMeanSlider;
    private JLabel walkInMeanLabel;
    private JLabel walkInMeanValue;
    private int walkInSim;

    private JSlider callInMeanSlider;
    private JLabel callInMeanLabel;
    private JLabel callInMeanValue;
    private int callInSim;

    private JButton simulate;
    private JButton save;
    private JButton reset;
    private JButton exit;

    private Color transparent = new Color(0, 0, 0, 0);
    private Font large = new Font("Sans Serif", Font.PLAIN, 18);
    private ImageIcon bgImagePath = new ImageIcon(getClass().getClassLoader()
            .getResource("groupassignment/resources/dash.jpg"));
    private ImageIcon bgWest = new ImageIcon(getClass().getClassLoader()
            .getResource("groupassignment/resources/dash_west.jpg"));
    private ImageIcon bgCenter = new ImageIcon(getClass().getClassLoader()
            .getResource("groupassignment/resources/dash_center.jpg"));
    private ImageIcon bgEast = new ImageIcon(getClass().getClassLoader()
            .getResource("groupassignment/resources/dash_east.jpg"));

    // -------------------------------------------------------------------------
    /**
     * GUI constructor
     */
    public GUI () {
        ImageIcon simulateIcon = new ImageIcon(getClass().getClassLoader()
                .getResource("groupassignment/resources/btn_simulate.png"));
        ImageIcon saveIcon = new ImageIcon(getClass().getClassLoader()
                .getResource("groupassignment/resources/btn_save.png"));
        ImageIcon resetIcon = new ImageIcon(getClass().getClassLoader()
                .getResource("groupassignment/resources/btn_reset.png"));
        ImageIcon exitIcon = new ImageIcon(getClass().getClassLoader()
                .getResource("groupassignment/resources/btn_exit.png"));

        contentPane = new JLabel(bgImagePath);
        contentPane.setLayout(new BorderLayout());

        frame = new JFrame("The Secretary");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.add(contentPane);
        frame.add(contentPane);

        // North --------------------------------

        northPanel = new JPanel(new GridLayout(4, 2));
        northPanel.setBorder(new EmptyBorder(10, 20, 20, 0));
        northPanel.setPreferredSize(new Dimension(900, 342));
        northPanel.setBackground(transparent);

        // display value from totalTimeSlider
        totalTimeLabel = new JLabel("Total simulation time: ");
        totalTimeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        totalTimeLabel.setFont(large);
        totalTimeLabel.setForeground(Color.white);
        totalTimeValue = new JLabel();
        totalTimeValue.setFont(large);
        totalTimeValue.setForeground(Color.white);
        northPanel.add(totalTimeLabel);
        northPanel.add(totalTimeValue);

        // display value from questionMeanSlider
        questionMeanLabel = new JLabel("Average time to answer a question: ");
        questionMeanLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        questionMeanLabel.setFont(large);
        questionMeanLabel.setForeground(Color.white);
        questionMeanValue = new JLabel();
        questionMeanValue.setFont(large);
        questionMeanValue.setForeground(Color.white);
        northPanel.add(questionMeanLabel);
        northPanel.add(questionMeanValue);

        // display value from walkInMeanSlider
        walkInMeanLabel = new JLabel("Average time between walk-ins: ");
        walkInMeanLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        walkInMeanLabel.setFont(large);
        walkInMeanLabel.setForeground(Color.white);
        walkInMeanValue = new JLabel();
        walkInMeanValue.setFont(large);
        walkInMeanValue.setForeground(Color.white);
        northPanel.add(walkInMeanLabel);
        northPanel.add(walkInMeanValue);

        // display value from callInMeanSlider
        callInMeanLabel = new JLabel("Average time between phone calls: ");
        callInMeanLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        callInMeanLabel.setFont(large);
        callInMeanLabel.setForeground(Color.white);
        callInMeanValue = new JLabel();
        callInMeanValue.setFont(large);
        callInMeanValue.setForeground(Color.white);
        northPanel.add(callInMeanLabel);
        northPanel.add(callInMeanValue);

        contentPane.add(northPanel, BorderLayout.NORTH);

        // West ---------------------------------

        westPanel = new JPanel();
        westPanel.setPreferredSize(new Dimension(335, 359));
        westPanel.setLayout(new BorderLayout());
        JLabel westBG = new JLabel(bgWest);
        westPanel.add(westBG);
        contentPane.add(westPanel, BorderLayout.WEST);

        // Center -------------------------------

        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        JLabel centerBG = new JLabel(bgCenter);
        centerPanel.add(centerBG);
        centerBG.setLayout(new BoxLayout(centerBG, BoxLayout.Y_AXIS));
        centerBG.add(Box.createVerticalStrut(70));

        // slider for total time
        totalTimeSlider = new JSlider(0, 0, 100, 50);
        totalTimeSlider.setMaximumSize(new Dimension(320, 50));
        totalTimeSlider.setPaintLabels(true);
        totalTimeSlider.setMajorTickSpacing(20);
        totalTimeSlider.setPaintTicks(true);
        totalTimeSlider.setOpaque(false);
        totalTimeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                totalTimeValue.setText(totalTimeSlider.getValue() + " minutes");
                if (totalTimeSlider.getValue() == 0)
                    totalTimeValue.setText("Infinity");
                frame.repaint();
            }
        });
        centerBG.add(totalTimeSlider);
        totalTimeValue.setText(totalTimeSlider.getValue() + " minutes");

        // slider for average question time
        questionMeanSlider = new JSlider(0, 0, 100, 24);
        questionMeanSlider.setMaximumSize(new Dimension(320, 50));
        questionMeanSlider.setPaintLabels(true);
        questionMeanSlider.setMajorTickSpacing(20);
        questionMeanSlider.setPaintTicks(true);
        questionMeanSlider.setOpaque(false);
        questionMeanSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                questionMeanValue.setText(questionMeanSlider.getValue() + " seconds");
                if (questionMeanSlider.getValue() == 0)
                    questionMeanValue.setText("Infinity");
                frame.repaint();
            }
        });
        centerBG.add(questionMeanSlider);
        questionMeanValue.setText(questionMeanSlider.getValue() + " seconds");

        // slider for average time between walk-ins
        walkInMeanSlider = new JSlider(0, 0, 100, 45);
        walkInMeanSlider.setMaximumSize(new Dimension(320, 50));
        walkInMeanSlider.setPaintLabels(true);
        walkInMeanSlider.setMajorTickSpacing(20);
        walkInMeanSlider.setPaintTicks(true);
        walkInMeanSlider.setOpaque(false);
        walkInMeanSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                walkInMeanValue.setText(walkInMeanSlider.getValue() + " seconds");
                if (walkInMeanSlider.getValue() == 0)
                    walkInMeanValue.setText("Infinity");
                frame.repaint();
            }
        });
        centerBG.add(walkInMeanSlider);
        walkInMeanValue.setText(walkInMeanSlider.getValue() + " seconds");

        // slider for average time between phone calls
        callInMeanSlider = new JSlider(0, 0, 100, 55);
        callInMeanSlider.setMaximumSize(new Dimension(320, 50));
        callInMeanSlider.setPaintLabels(true);
        callInMeanSlider.setMajorTickSpacing(20);
        callInMeanSlider.setPaintTicks(true);
        callInMeanSlider.setOpaque(false);
        callInMeanSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                callInMeanValue.setText(callInMeanSlider.getValue() + " seconds");
                if (callInMeanSlider.getValue() == 0)
                    callInMeanValue.setText("Infinity");
                frame.repaint();
            }
        });
        centerBG.add(callInMeanSlider);
        callInMeanValue.setText(callInMeanSlider.getValue() + " seconds");

        contentPane.add(centerPanel, BorderLayout.CENTER);

        // East ---------------------------------

        eastPanel = new JPanel();
        eastPanel.setBackground(new Color(212, 214, 211));
        eastPanel.setLayout(new BorderLayout());
        eastPanel.setPreferredSize(new Dimension(200, 359));
        JLabel eastBG = new JLabel(bgEast);
        eastPanel.add(eastBG);
        eastBG.setLayout(new BoxLayout(eastBG, BoxLayout.Y_AXIS));

        // [simulate] button
        simulate = new JButton();
        simulate.setIcon(simulateIcon);
        simulate.setOpaque(false);
        simulate.setContentAreaFilled(false);
        simulate.setBorderPainted(false);
        simulate.setFocusPainted(false);
        simulate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (totalTimeSlider.getValue() == 0) {
                    JOptionPane.showMessageDialog(frame,
                            "Simulate for ZERO minutes?\nOk, done! No results.",
                            "Really?", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                displayResults();
                // no need for these to be enabled at this point
                simulate.setEnabled(false);
                totalTimeSlider.setEnabled(false);
                questionMeanSlider.setEnabled(false);
                walkInMeanSlider.setEnabled(false);
                callInMeanSlider.setEnabled(false);
                save.setEnabled(true);
            }
        });
        eastBG.add(simulate);

        // [save] button
        save = new JButton();
        save.setEnabled(false);
        save.setIcon(saveIcon);
        save.setOpaque(false);
        save.setContentAreaFilled(false);
        save.setBorderPainted(false);
        save.setFocusPainted(false);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int status = chooser.showSaveDialog(frame);
                if (status == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = chooser.getSelectedFile();
                    try {
                        FileWriter fw = new FileWriter(selectedFile + ".txt");
                        results.write(fw);
                    }
                    catch (IOException ioe) { /* nothing */ }
                }
            }
        });
        eastBG.add(save);

        // [reset] button
        reset = new JButton();
        reset.setIcon(resetIcon);
        reset.setOpaque(false);
        reset.setContentAreaFilled(false);
        reset.setBorderPainted(false);
        reset.setFocusPainted(false);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                String[] args = {};
                GroupAssignment.main(args);
            }
        });
        eastBG.add(reset);

        // [exit] button
        exit = new JButton();
        exit.setIcon(exitIcon);
        exit.setOpaque(false);
        exit.setContentAreaFilled(false);
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        eastBG.add(exit);

        contentPane.add(eastPanel, BorderLayout.EAST);

        // center the window and make visible
        Point center = GraphicsEnvironment
                .getLocalGraphicsEnvironment().getCenterPoint();
        frame.setBounds(center.x - 900 / 2, center.y - 720 / 2, 900, 720);
        frame.setVisible(true);

    }  // GUI constructor

    // -------------------------------------------------------------------------

    /**
     * Displays the results of the simulation in a scroll pane.
     */
    public void displayResults() {

        northPanel.removeAll();
        northPanel.setLayout(new BorderLayout());
        results = new JTextArea("Results from a(n) " +
                totalTimeSlider.getValue() + "-minute simulation with the" +
                " following settings: \n\n- " + questionMeanSlider.getValue() +
                " seconds, on average to answer a question\n- " +
                walkInMeanSlider.getValue() +
                " seconds, on average, between walk-in customers\n- " +
                callInMeanSlider.getValue() +
                " seconds, on average, between customers who called\n\n" +
                "------------------------------------------------------------" +
                "---------------------\nEvent Log\n-------------------------" +
                "--------------------------------------------------------\n\n");
        results.setForeground(Color.white);
        results.setOpaque(false);
        resultsPane = new JScrollPane(results);
        resultsPane.setOpaque(false);
        resultsPane.getViewport().setOpaque(false);
        JScrollBar scrollBar = resultsPane.getVerticalScrollBar();
        scrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                frame.repaint();
            }
        });
        results.setEditable(false);
        northPanel.add(resultsPane);
        northPanel.validate();

        if (questionMeanSlider.getValue() == 0) {
            questionSim = Integer.MAX_VALUE;
        } else {
            questionSim = questionMeanSlider.getValue();
        }
        if (walkInMeanSlider.getValue() == 0) {
            walkInSim = Integer.MAX_VALUE;
        } else {
            walkInSim = walkInMeanSlider.getValue();
        }
        if (callInMeanSlider.getValue() == 0) {
            callInSim = Integer.MAX_VALUE;
        } else {
            callInSim = callInMeanSlider.getValue();
        }

        // run the simulation with chosen parameters
        new Timer(totalTimeSlider.getValue() * 60, // convert to seconds
                questionSim, walkInSim, callInSim, results);
        results.append("\n\nEnd of Simulation.");
    }  // displayResults
}  // GUI class
