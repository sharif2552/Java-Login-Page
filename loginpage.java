import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LoginForm2 extends JFrame implements ActionListener {
    private JLabel labelUsername, labelPassword, messageLabel;
    private JTextField textFieldUsername;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel contentPanel;

    private boolean isBlown = false;
    private int blowRadius = 0;
    private Timer blowTimer;

    public LoginForm2() {
        contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

               
                try {
                    BufferedImage backgroundImage = ImageIO.read(new File("C:\\Users\\PC\\Documents\\NetBeansProjects\\mavenproject2\\src\\main\\java\\background.jpg"));

                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                
                if (isBlown) {
                    g.setColor(Color.GREEN); 
                    g.fillOval(getWidth() / 2 - blowRadius, getHeight() / 2 - blowRadius, 2 * blowRadius, 2 * blowRadius);
                }
            }
        };

        contentPanel.setLayout(new GridBagLayout());
        setContentPane(contentPanel);

        labelUsername = new JLabel("Username:");
        labelPassword = new JLabel("Password:");
        textFieldUsername = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        messageLabel = new JLabel();

        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.insets = new Insets(5, 5, 5, 5);
        labelConstraints.anchor = GridBagConstraints.WEST;

        GridBagConstraints inputConstraints = new GridBagConstraints();
        inputConstraints.insets = new Insets(5, 5, 5, 5);
        inputConstraints.fill = GridBagConstraints.HORIZONTAL;
        inputConstraints.weightx = 1.0;

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.insets = new Insets(10, 5, 5, 5);
        buttonConstraints.gridwidth = GridBagConstraints.REMAINDER;
        buttonConstraints.anchor = GridBagConstraints.CENTER;

        contentPanel.add(labelUsername, labelConstraints);
        contentPanel.add(textFieldUsername, inputConstraints);
        contentPanel.add(labelPassword, labelConstraints);
        contentPanel.add(passwordField, inputConstraints);
        contentPanel.add(loginButton, buttonConstraints);
        contentPanel.add(messageLabel, buttonConstraints);

        loginButton.addActionListener(this);

        // Initialize the blow effect Timer
        blowTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isBlown) {
                    // Increase the radius of the blow effect
                    blowRadius += 10;

                    // Repaint
                    contentPanel.repaint();

                    // Stop the animation 
                    if (blowRadius > 300) {
                        blowTimer.stop();
                        isBlown = false;
                    }
                }
            }
        });

        setTitle("Login Form");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = textFieldUsername.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);

            if (authenticate(username, password)) {
                startBlowEffect(); // Start the blow effect
                messageLabel.setText("Login successful!");
            } else {
                
                contentPanel.setBackground(Color.RED);
                contentPanel.repaint(); // repaint
                messageLabel.setText("Login failed. Please check your credentials.");
            }
        }
    }

    private void startBlowEffect() {
        isBlown = true;
        blowRadius = 0;
        blowTimer.start();
    }

    private boolean authenticate(String username, String password) {
        String validUsername = "admin";
        String validPassword = "admin";

        return username.equals(validUsername) && password.equals(validPassword);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginForm2();
        });
    }
}
