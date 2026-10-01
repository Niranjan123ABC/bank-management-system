package Bank_Management_System;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.border.*;

public class Login extends JFrame implements ActionListener {

    JButton login, clear, signup;
    JTextField cardtext;
    JPasswordField pintext;

    // -- ATM Screen Design Constants --
    static final Color ATM_FRAME     = new Color(45, 45, 58);
    static final Color SCREEN_BG     = new Color(15, 32, 75);
    static final Color SCREEN_BORDER = new Color(22, 50, 110);
    static final Color ACCENT        = new Color(0, 180, 220);
    static final Color ACCENT_GLOW   = new Color(0, 212, 255);
    static final Color TEXT          = new Color(220, 230, 245);
    static final Color MUTED         = new Color(100, 130, 180);
    static final Color BUTTON_BG     = new Color(25, 50, 100);
    static final Color BUTTON_BORDER = new Color(0, 150, 200);
    static final Color KEYPAD_BG     = new Color(35, 35, 48);
    static final Color KEY_BG        = new Color(60, 60, 75);
    static final Color BORDER_COL    = new Color(40, 55, 90);   // <-- ADDED
    static final Font  FONT_TITLE    = new Font("SansSerif", Font.BOLD, 26);
    static final Font  FONT_LABEL    = new Font("SansSerif", Font.BOLD, 12);
    static final Font  FONT_INPUT    = new Font("Monospaced", Font.PLAIN, 14);
    static final Font  FONT_BTN      = new Font("SansSerif", Font.BOLD, 13);
    static final Font  FONT_ATM      = new Font("SansSerif", Font.BOLD, 11);

    Login() {
        setTitle("SecureBank ATM - Login");
        setLayout(null);
        getContentPane().setBackground(ATM_FRAME);

        // -- ATM Machine Frame --
        JPanel atmFrame = new JPanel(null);
        atmFrame.setBounds(50, 20, 560, 620);
        atmFrame.setBackground(ATM_FRAME);
        atmFrame.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 75), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        add(atmFrame);

        // -- Bank Logo / Brand --
        JLabel brand = new JLabel("[ATM]  SECURE BANK", SwingConstants.CENTER);
        brand.setFont(new Font("SansSerif", Font.BOLD, 18));
        brand.setForeground(new Color(255, 215, 0));
        brand.setBounds(0, 10, 560, 30);
        atmFrame.add(brand);

        // -- ATM Screen --
        JPanel screen = new JPanel(null);
        screen.setBounds(30, 50, 500, 320);
        screen.setBackground(SCREEN_BG);
        screen.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SCREEN_BORDER, 3),
            BorderFactory.createLineBorder(new Color(0, 100, 150), 1)
        ));
        atmFrame.add(screen);

        // Screen header
        JLabel screenHeader = new JLabel("AUTOMATED TELLER MACHINE", SwingConstants.CENTER);
        screenHeader.setFont(new Font("SansSerif", Font.BOLD, 11));
        screenHeader.setForeground(ACCENT);
        screenHeader.setBounds(0, 15, 500, 20);
        screen.add(screenHeader);

        // Screen title
        JLabel title = new JLabel("SecureBank", SwingConstants.CENTER);
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT);
        title.setBounds(0, 45, 500, 36);
        screen.add(title);

        JLabel subtitle = new JLabel("Please Insert Your Card & Enter PIN", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(MUTED);
        subtitle.setBounds(0, 85, 500, 20);
        screen.add(subtitle);

        // -- Card Panel inside Screen --
        JPanel panel = new JPanel(null);
        panel.setBounds(50, 120, 400, 180);
        panel.setBackground(new Color(12, 28, 65));
        panel.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 150), 1));
        screen.add(panel);

        // Card No label + field
        JLabel cardLabel = makeLabel("CARD NUMBER");
        cardLabel.setBounds(25, 15, 350, 18);
        panel.add(cardLabel);

        cardtext = makeTextField("Enter 16-digit card number");
        cardtext.setBounds(25, 35, 350, 36);
        panel.add(cardtext);

        // PIN label + field
        JLabel pinLabel = makeLabel("PIN");
        pinLabel.setBounds(25, 80, 350, 18);
        panel.add(pinLabel);

        pintext = new JPasswordField();
        styleField(pintext, "Enter 4-digit PIN");
        pintext.setBounds(25, 100, 350, 36);
        panel.add(pintext);

        // Divider
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0, 100, 150));
        sep.setBounds(25, 148, 350, 1);
        panel.add(sep);

        // Buttons
        login = makeScreenButton("SIGN IN");
        login.setBounds(25, 155, 170, 32);
        login.addActionListener(this);
        panel.add(login);

        clear = makeScreenButton("CLEAR");
        clear.setBounds(205, 155, 170, 32);
        clear.addActionListener(this);
        panel.add(clear);

        // Signup link
        signup = makeOutlineButton("CREATE NEW ACCOUNT");
        signup.setBounds(130, 260, 240, 32);
        signup.addActionListener(this);
        screen.add(signup);

        // -- Keypad Area --
        JPanel keypadArea = new JPanel(null);
        keypadArea.setBounds(30, 385, 500, 180);
        keypadArea.setBackground(KEYPAD_BG);
        keypadArea.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 65), 1));
        atmFrame.add(keypadArea);

        // Side buttons (left)
        for (int i = 0; i < 4; i++) {
            JButton sideBtn = makeSideButton();
            sideBtn.setBounds(15, 15 + i * 42, 50, 35);
            keypadArea.add(sideBtn);
        }

        // Keypad
        String[] keys = {"1","2","3","4","5","6","7","8","9","CLR","0","ENTER"};
        for (int i = 0; i < 12; i++) {
            int row = i / 3;
            int col = i % 3;
            JButton key = makeKey(keys[i]);
            key.setBounds(80 + col * 130, 15 + row * 42, 120, 35);
            keypadArea.add(key);
        }

        // Side buttons (right)
        for (int i = 0; i < 4; i++) {
            JButton sideBtn = makeSideButton();
            sideBtn.setBounds(435, 15 + i * 42, 50, 35);
            keypadArea.add(sideBtn);
        }

        // -- Card Slot & Cash Dispenser --
        JPanel cardSlot = new JPanel();
        cardSlot.setBounds(30, 575, 120, 8);
        cardSlot.setBackground(new Color(80, 80, 90));
        cardSlot.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 1));
        atmFrame.add(cardSlot);
        JLabel cardLabel2 = new JLabel("CARD SLOT", SwingConstants.CENTER);
        cardLabel2.setFont(new Font("SansSerif", Font.PLAIN, 9));
        cardLabel2.setForeground(MUTED);
        cardLabel2.setBounds(30, 585, 120, 12);
        atmFrame.add(cardLabel2);

        JPanel cashSlot = new JPanel();
        cashSlot.setBounds(220, 575, 160, 8);
        cashSlot.setBackground(new Color(80, 80, 90));
        cashSlot.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 1));
        atmFrame.add(cashSlot);
        JLabel cashLabel = new JLabel("CASH DISPENSER", SwingConstants.CENTER);
        cashLabel.setFont(new Font("SansSerif", Font.PLAIN, 9));
        cashLabel.setForeground(MUTED);
        cashLabel.setBounds(220, 585, 160, 12);
        atmFrame.add(cashLabel);

        // -- Status bar --
        JLabel status = new JLabel("[LOCK]  256-bit SSL Secured  -  Please Shield Your PIN", SwingConstants.CENTER);
        status.setFont(new Font("SansSerif", Font.PLAIN, 10));
        status.setForeground(MUTED);
        status.setBounds(0, 600, 560, 18);
        atmFrame.add(status);

        setSize(680, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // -- Factory helpers --
    static JLabel makeLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 11));
        l.setForeground(MUTED);
        return l;
    }

    static JTextField makeTextField(String placeholder) {
        JTextField f = new JTextField(placeholder);
        styleField(f, placeholder);
        return f;
    }

    static void styleField(JTextField f, String placeholder) {
        f.setBackground(new Color(10, 20, 50));
        f.setForeground(MUTED);
        f.setFont(FONT_INPUT);
        f.setCaretColor(ACCENT);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 100, 150), 1),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        String ph = placeholder;
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (f.getText().equals(ph)) { f.setText(""); f.setForeground(TEXT); }
                f.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ACCENT, 1),
                    BorderFactory.createEmptyBorder(6, 12, 6, 12)
                ));
            }
            public void focusLost(FocusEvent e) {
                if (f.getText().isEmpty()) { f.setText(ph); f.setForeground(MUTED); }
                f.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 100, 150), 1),
                    BorderFactory.createEmptyBorder(6, 12, 6, 12)
                ));
            }
        });
    }

    static JButton makeScreenButton(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BTN);
        b.setBackground(BUTTON_BG);
        b.setForeground(TEXT);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(BUTTON_BORDER, 1));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                b.setBackground(new Color(0, 100, 150));
                b.setForeground(ACCENT_GLOW);
            }
            public void mouseExited(MouseEvent e) {
                b.setBackground(BUTTON_BG);
                b.setForeground(TEXT);
            }
        });
        return b;
    }

    static JButton makeOutlineButton(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BTN);
        b.setBackground(new Color(12, 28, 65));
        b.setForeground(ACCENT);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(BUTTON_BORDER, 1));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(new Color(0, 100, 150)); b.setForeground(TEXT); }
            public void mouseExited(MouseEvent e)  { b.setBackground(new Color(12, 28, 65)); b.setForeground(ACCENT); }
        });
        return b;
    }

    // -- ADDED: makeAccentButton --
    static JButton makeAccentButton(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BTN);
        b.setBackground(new Color(0, 130, 180));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(ACCENT, 1));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                b.setBackground(new Color(0, 160, 210));
                b.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                b.setBackground(new Color(0, 130, 180));
                b.setForeground(Color.WHITE);
            }
        });
        return b;
    }

    static JButton makeKey(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        if (text.equals("ENTER")) {
            b.setBackground(new Color(0, 150, 100));
            b.setForeground(Color.WHITE);
        } else if (text.equals("CLR")) {
            b.setBackground(new Color(180, 50, 50));
            b.setForeground(Color.WHITE);
        } else {
            b.setBackground(KEY_BG);
            b.setForeground(TEXT);
        }
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 95), 1));
        return b;
    }

    static JButton makeSideButton() {
        JButton b = new JButton();
        b.setBackground(new Color(70, 70, 85));
        b.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 105), 1));
        b.setFocusPainted(false);
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == clear) {
            cardtext.setText("Enter 16-digit card number");
            cardtext.setForeground(MUTED);
            pintext.setText("");
        } else if (ae.getSource() == login) {
            String cardnumber = cardtext.getText().trim();
            String pinnumber  = new String(pintext.getPassword()).trim();
            if (cardnumber.isEmpty() || pinnumber.isEmpty()) {
                showMsg("Please enter Card Number and PIN", "error");
                return;
            }
            Conn conn = new Conn();
            String query = "SELECT * FROM login WHERE cardnumber=? AND pinnumber=?";
            try {
                java.sql.PreparedStatement ps = conn.connect.prepareStatement(query);
                ps.setString(1, cardnumber);
                ps.setString(2, pinnumber);
                java.sql.ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    setVisible(false);
                    new Transaction(pinnumber).setVisible(true);
                } else {
                    showMsg("Incorrect Card Number or PIN", "error");
                }
            } catch (Exception e) { e.printStackTrace(); }
        } else if (ae.getSource() == signup) {
            setVisible(false);
            new SignUp().setVisible(true);
        }
    }

    static void showMsg(String msg, String type) {
        JOptionPane.showMessageDialog(null, msg,
            type.equals("error") ? "Error" : "Info",
            type.equals("error") ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
        catch (Exception ignored) {}
        new Login();
    }
}