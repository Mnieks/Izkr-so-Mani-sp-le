package kautkadaprogramma6dec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class IzkrasoMani9 extends JFrame {
    private JPanel gridPanel;
    private Color[] colors = {
        Color.RED, Color.WHITE, Color.YELLOW, Color.GREEN, Color.GRAY, Color.CYAN, Color.BLUE, Color.BLACK, 
        new Color(173, 216, 230), //gaiši zils
        new Color(0, 0, 128), // zils 
        new Color(255, 165, 0), //oran
        new Color(255, 192, 203), // 
        new Color(255, 255, 0), // 
        new Color(0, 255, 0), // n
        new Color(128, 0, 0), // D
        new Color(255, 255, 255), 
    };
    
    private String[] countryNames = {
        "Austrija", "Lietuva", "Krievija", "Vācija", 
        "Ungārija","Jemena", "Indija", "Luksemburga",  
        "Gabona", "Armēnija", "Francija", "Beļģija", 
        "Nigērija", "Rumānija"
    };
    
    private Color[][] correctColors = {
        {Color.RED, Color.RED, Color.RED, Color.RED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.RED, Color.RED, Color.RED, Color.RED}, //austrija
        {Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.RED, Color.RED, Color.RED, Color.RED}, //lietuva
        {Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.RED, Color.RED, Color.RED, Color.RED},// krievija
        {Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.RED, Color.RED, Color.RED, Color.RED, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW}, // vācija 
        

        {Color.RED, Color.RED, Color.RED, Color.RED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN},// ungārija
        

        {Color.RED, Color.RED, Color.RED, Color.RED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK}, // Jemena
        

        {new Color(255, 165, 0), new Color(255, 165, 0), new Color(255, 165, 0), new Color(255, 165, 0),  Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN,},//indija
        

        {Color.RED, Color.RED, Color.RED, Color.RED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, new Color(173, 216, 230), new Color(173, 216, 230), new Color(173, 216, 230), new Color(173, 216, 230) }, // luksemburga

        {Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE,}, // gabona
        
        {Color.RED, Color.RED, Color.RED, Color.RED, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW,}, //Armenija
        
        {Color.BLUE, Color.WHITE, Color.RED, Color.BLUE, Color.WHITE, Color.RED, Color.BLUE, Color.WHITE, Color.RED, Color.BLUE, Color.WHITE, Color.RED}, // francija
        
        

        {Color.BLACK, Color.YELLOW, Color.RED, Color.BLACK, Color.YELLOW, Color.RED, Color.BLACK, Color.YELLOW, Color.RED, Color.BLACK, Color.YELLOW, Color.RED}, // beļgija
        

        {Color.GREEN, Color.WHITE, Color.GREEN, Color.GREEN, Color.WHITE, Color.GREEN, Color.GREEN, Color.WHITE, Color.GREEN, Color.GREEN, Color.WHITE, Color.GREEN}, // nigērija
        

        {Color.BLUE, Color.YELLOW, Color.RED, Color.BLUE, Color.YELLOW, Color.RED, Color.BLUE, Color.YELLOW, Color.RED, Color.BLUE, Color.YELLOW, Color.RED} //rumānija
    };
    
    private JLabel scoreLabel, countryLabel, timerLabel;
    private JButton startButton;
    private int punkti = 0, timeLeft = 30;
    private boolean isGameStarted = false;
    private javax.swing.Timer timer;
    private Color selectedColor = Color.WHITE;
    private JButton[] gridButtons = new JButton[12];
    private int currentCountryIndex = 0;
    private int correctFlagCount = 0;
    private int flagsCorrectlyColored = 0;
    private RoundButton playButton;

    public IzkrasoMani9() {
        setTitle("Izkrāso Mani");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        playButton = new RoundButton(Color.BLACK);
        playButton.setText("▶");
        playButton.setFont(new Font("Arial", Font.PLAIN, 30));
        playButton.addActionListener(e -> startGame());
        

        JPanel playButtonWrapper = new JPanel();
        playButtonWrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
        playButtonWrapper.setPreferredSize(new Dimension(getWidth(), 100));
        playButtonWrapper.add(playButton);
        add(playButtonWrapper, BorderLayout.NORTH);


        countryLabel = new JLabel("Valsts: " + countryNames[currentCountryIndex], JLabel.CENTER);
        countryLabel.setFont(new Font("Arial", Font.BOLD, 24));


        JPanel countryWrapper = new JPanel();
        countryWrapper.setLayout(new BorderLayout());
        countryWrapper.add(countryLabel, BorderLayout.CENTER);
        countryWrapper.setPreferredSize(new Dimension(getWidth(), 60));
        add(countryWrapper, BorderLayout.NORTH);


        gridPanel = new JPanel(new GridLayout(3, 43, 18, 19)); 
        createGridButtons(); 


        JPanel gridWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        gridWrapper.add(gridPanel);
        add(gridWrapper, BorderLayout.CENTER);




        int colorColumns = 2;
        int colorRows = (int) Math.ceil((double) colors.length / colorColumns);

        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new GridLayout(colorRows, colorColumns, 5, 5));


        JPanel colorPanelWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        colorPanelWrapper.setPreferredSize(new Dimension(100, 400));

        for (Color color : colors) {
            JButton colorButton = new RoundButton(color);
            colorButton.setBackground(color);
            colorButton.setBorder(BorderFactory.createEmptyBorder());
            colorButton.setPreferredSize(new Dimension(40, 40)); 
            colorButton.addActionListener(new ColorButtonListener(color));
            colorPanel.add(colorButton);
        }

        colorPanelWrapper.add(colorPanel);
        add(colorPanelWrapper, BorderLayout.EAST);



        JPanel bottomPanel = new JPanel(new BorderLayout());
        scoreLabel = new JLabel("Punkti: 0", JLabel.CENTER);
        bottomPanel.add(scoreLabel, BorderLayout.WEST);

        timerLabel = new JLabel("Atlikušais laiks: " + timeLeft + "s", JLabel.RIGHT);
        bottomPanel.add(timerLabel, BorderLayout.EAST);

        startButton = new JButton("Sākt spēli");
        startButton.addActionListener(e -> startGame());
        bottomPanel.add(startButton, BorderLayout.CENTER);
        bottomPanel.setPreferredSize(new Dimension(4, 50));

        add(bottomPanel, BorderLayout.SOUTH);
    }
    

	private void showRules() {
    JOptionPane.showMessageDialog(this, "Vadoties pēc valsts nosaukuma, iekrāso valsts karogu noteiktajā laikā.", "Spēles noteikumi", JOptionPane.INFORMATION_MESSAGE);
}
    
    
    

    
    

    private void startGame() {
        if (!isGameStarted) {
            isGameStarted = true;
            playButton.setEnabled(false);
            resetTimer();
            startTimer(); 
        }
    }

    private void resetTimer() {
        if (timer != null) {
            timer.stop(); 
        }
        timeLeft = 30; 
        timerLabel.setText("Atlikušais laiks: " + timeLeft + "s");
    }

    private void startTimer() {
        timer = new javax.swing.Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Atlikušais laiks: " + timeLeft + "s");
                if (timeLeft <= 0) {
                    timer.stop();
                    endGame();
                }
            }
        });
        timer.start();
    }

    private void createGridButtons() {
        gridPanel.removeAll(); 
        for (int i = 0; i < 12; i++) {
            JButton gridButton = new JButton();
            gridButton.setPreferredSize(new Dimension(180, 100)); 
            gridButton.setBackground(new Color(173, 216, 230)); 
            gridButton.addActionListener(new GridButtonListener(i));
            gridPanel.add(gridButton);
            gridButtons[i] = gridButton;
        }
        revalidate();
        repaint();
    }

    private class ColorButtonListener implements ActionListener {
        private Color color;

        public ColorButtonListener(Color color) {
            this.color = color;
        }

        public void actionPerformed(ActionEvent e) {
            if (isGameStarted) {
                selectedColor = color;
            }
        }
    }

    private class GridButtonListener implements ActionListener {
        private int index;

        public GridButtonListener(int index) {
            this.index = index;
        }

        public void actionPerformed(ActionEvent e) {
            if (isGameStarted) {
                JButton button = (JButton) e.getSource();
                button.setBackground(selectedColor);
                checkCompletion(); 
            }
        }
    }

    private void checkCompletion() {
        int correctFlags = 0; 
        Color[] currentCorrectColors = correctColors[currentCountryIndex];


        for (int i = 0; i < 12; i++) {
            if (gridButtons[i].getBackground().equals(currentCorrectColors[i])) {
                correctFlags++;
            }
        }


        if (correctFlags == 12) {
            flagsCorrectlyColored++;
            punkti += 5; 
            scoreLabel.setText("Punkti: " + punkti);

 
            if (flagsCorrectlyColored < countryNames.length) {
                currentCountryIndex++;
                countryLabel.setText("Valsts: " + countryNames[currentCountryIndex]);
                resetGrid();
            } else {
                endGame(); 
            }
        }
    }

    private void resetGrid() {

        for (JButton button : gridButtons) {
            button.setBackground(new Color(173, 216, 230)); 
        }
    }

    private void endGame() {
        JOptionPane.showMessageDialog(this, "Spēle beigusies! Jūs ieguvāt " + punkti + " punktus.");
        System.exit(0); 
    }

    private class RoundButton extends JButton {
        private Shape shape;

        public RoundButton(Color color) {
            setBackground(color);
            setContentAreaFilled(false);
        }


        protected void paintComponent(Graphics g) {
            if (getModel().isArmed()) {
                g.setColor(getBackground().darker());
            } else {
                g.setColor(getBackground());
            }
            g.fillOval(0, 0, getWidth(), getHeight()); 
            super.paintComponent(g);
        }

     
        public boolean contains(int x, int y) {
            return shape != null && shape.contains(x, y); 
        }


        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            shape = new Ellipse2D.Float(0, 0, width, height);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IzkrasoMani9 frame = new IzkrasoMani9();
            frame.setVisible(true);
        });
    }
}