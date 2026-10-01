package Bank_Management_System;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static Bank_Management_System.Login.*;

public class Transaction extends JFrame implements ActionListener {

    JButton deposite, withdral, fastcash, ministatement, balanceinquery, pinchange, exit;
    String pinnumber;

    Transaction(String pinnumber) {
        this.pinnumber = pinnumber;
        setTitle("SecureBank ATM - Transactions");
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

        // -- Bank Brand --
        JLabel brand = new JLabel("[ATM]  SECURE BANK", SwingConstants.CENTER);
        brand.setFont(new Font("SansSerif", Font.BOLD, 18));
        brand.setForeground(new Color(255, 215, 0));
        brand.setBounds(0, 10, 560, 30);
        atmFrame.add(brand);

        // -- ATM Screen --
        JPanel screen = new JPanel(null);
        screen.setBounds(30, 50, 500, 380);
        screen.setBackground(SCREEN_BG);
        screen.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SCREEN_BORDER, 3),
            BorderFactory.createLineBorder(new Color(0, 100, 150), 1)
        ));
        atmFrame.add(screen);

        // Screen header
        JLabel screenHeader = new JLabel("SELECT TRANSACTION", SwingConstants.CENTER);
        screenHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
        screenHeader.setForeground(ACCENT);
        screenHeader.setBounds(0, 15, 500, 25);
        screen.add(screenHeader);

        JLabel subHeader = new JLabel("Please choose your desired service", SwingConstants.CENTER);
        subHeader.setFont(new Font("SansSerif", Font.PLAIN, 11));
        subHeader.setForeground(MUTED);
        subHeader.setBounds(0, 42, 500, 18);
        screen.add(subHeader);

        // Divider
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0, 100, 150));
        sep.setBounds(30, 68, 440, 1);
        screen.add(sep);

        // -- Menu Buttons (ATM Style - Left & Right side buttons) --
        // Left side buttons
        String[] leftLabels = {"[1] Deposit", "[3] Fast Cash", "[5] Balance"};
        String[] rightLabels = {"[2] Withdraw", "[4] Statement", "[6] PIN Change"};

        JButton[] leftBtns = new JButton[3];
        JButton[] rightBtns = new JButton[3];

        for (int i = 0; i < 3; i++) {
            // Left buttons
            leftBtns[i] = makeATMScreenButton(leftLabels[i]);
            leftBtns[i].setBounds(30, 85 + i * 75, 200, 55);
            leftBtns[i].addActionListener(this);
            screen.add(leftBtns[i]);

            // Right buttons
            rightBtns[i] = makeATMScreenButton(rightLabels[i]);
            rightBtns[i].setBounds(270, 85 + i * 75, 200, 55);
            rightBtns[i].addActionListener(this);
            screen.add(rightBtns[i]);
        }

        deposite       = leftBtns[0];  // Deposit
        fastcash       = leftBtns[1];  // Fast Cash
        balanceinquery = leftBtns[2];  // Balance Inquiry

        withdral       = rightBtns[0]; // Withdrawal
        ministatement  = rightBtns[1]; // Mini Statement
        pinchange      = rightBtns[2]; // PIN Change

        // Exit button at bottom
        exit = makeExitButton("[X] EXIT / LOGOUT");
        exit.setBounds(150, 320, 200, 40);
        exit.addActionListener(this);
        screen.add(exit);

        // -- Keypad Area --
        JPanel keypadArea = new JPanel(null);
        keypadArea.setBounds(30, 445, 500, 130);
        keypadArea.setBackground(KEYPAD_BG);
        keypadArea.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 65), 1));
        atmFrame.add(keypadArea);

        // Side buttons (left)
        for (int i = 0; i < 3; i++) {
            JButton sideBtn = makeSideButton();
            sideBtn.setBounds(15, 15 + i * 38, 50, 32);
            keypadArea.add(sideBtn);
        }

        // Keypad
        String[] keys = {"1","2","3","4","5","6","7","8","9","CLR","0","ENTER"};
        for (int i = 0; i < 12; i++) {
            int row = i / 3;
            int col = i % 3;
            JButton key = makeKey(keys[i]);
            key.setBounds(80 + col * 130, 15 + row * 38, 120, 32);
            keypadArea.add(key);
        }

        // Side buttons (right)
        for (int i = 0; i < 3; i++) {
            JButton sideBtn = makeSideButton();
            sideBtn.setBounds(435, 15 + i * 38, 50, 32);
            keypadArea.add(sideBtn);
        }

        // -- Card Slot & Cash Dispenser --
        JPanel cardSlot = new JPanel();
        cardSlot.setBounds(30, 585, 120, 6);
        cardSlot.setBackground(new Color(80, 80, 90));
        cardSlot.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 1));
        atmFrame.add(cardSlot);
        JLabel cardLabel2 = new JLabel("CARD SLOT", SwingConstants.CENTER);
        cardLabel2.setFont(new Font("SansSerif", Font.PLAIN, 9));
        cardLabel2.setForeground(MUTED);
        cardLabel2.setBounds(30, 593, 120, 12);
        atmFrame.add(cardLabel2);

        JPanel cashSlot = new JPanel();
        cashSlot.setBounds(220, 585, 160, 6);
        cashSlot.setBackground(new Color(80, 80, 90));
        cashSlot.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 1));
        atmFrame.add(cashSlot);
        JLabel cashLabel = new JLabel("CASH DISPENSER", SwingConstants.CENTER);
        cashLabel.setFont(new Font("SansSerif", Font.PLAIN, 9));
        cashLabel.setForeground(MUTED);
        cashLabel.setBounds(220, 593, 160, 12);
        atmFrame.add(cashLabel);

        // -- Status bar --
        JLabel status = new JLabel("[LOCK] 256-bit SSL Secured  -  Please Shield Your PIN", SwingConstants.CENTER);
        status.setFont(new Font("SansSerif", Font.PLAIN, 10));
        status.setForeground(MUTED);
        status.setBounds(0, 610, 560, 18);
        atmFrame.add(status);

        setSize(680, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JButton makeATMScreenButton(String text) {
        JButton b = new JButton("<html><center>" + text + "</center></html>");
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setBackground(new Color(20, 45, 90));
        b.setForeground(TEXT);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BUTTON_BORDER, 1),
            BorderFactory.createEmptyBorder(8, 5, 8, 5)
        ));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                b.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ACCENT_GLOW, 2),
                    BorderFactory.createEmptyBorder(7, 4, 7, 4)
                ));
                b.setBackground(new Color(0, 100, 150));
                b.setForeground(ACCENT_GLOW);
            }
            public void mouseExited(MouseEvent e) {
                b.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BUTTON_BORDER, 1),
                    BorderFactory.createEmptyBorder(8, 5, 8, 5)
                ));
                b.setBackground(new Color(20, 45, 90));
                b.setForeground(TEXT);
            }
        });
        return b;
    }

    private JButton makeExitButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setBackground(new Color(80, 20, 20));
        b.setForeground(new Color(255, 100, 100));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(new Color(150, 50, 50), 1));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                b.setBackground(new Color(120, 30, 30));
                b.setForeground(new Color(255, 150, 150));
            }
            public void mouseExited(MouseEvent e) {
                b.setBackground(new Color(80, 20, 20));
                b.setForeground(new Color(255, 100, 100));
            }
        });
        return b;
    }

    private JButton makeKey(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 11));
        if (text.equals("ENTER")) {
            b.setBackground(new Color(0, 130, 90));
            b.setForeground(Color.WHITE);
        } else if (text.equals("CLR")) {
            b.setBackground(new Color(150, 40, 40));
            b.setForeground(Color.WHITE);
        } else {
            b.setBackground(KEY_BG);
            b.setForeground(TEXT);
        }
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 95), 1));
        return b;
    }

    private JButton makeSideButton() {
        JButton b = new JButton();
        b.setBackground(new Color(70, 70, 85));
        b.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 105), 1));
        b.setFocusPainted(false);
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if      (e.getSource() == exit)           { System.exit(0); }
        else if (e.getSource() == deposite)       { setVisible(false); new Deposite(pinnumber).setVisible(true); }
        else if (e.getSource() == withdral)       { setVisible(false); new Withdral(pinnumber).setVisible(true); }
        else if (e.getSource() == fastcash)       { setVisible(false); new FastCash(pinnumber).setVisible(true); }
        else if (e.getSource() == pinchange)      { setVisible(false); new Pinchange(pinnumber).setVisible(true); }
        else if (e.getSource() == balanceinquery) { setVisible(false); new BalanaceInquery(pinnumber).setVisible(true); }
        else if (e.getSource() == ministatement)  { new MiniStatement(pinnumber).setVisible(true); }
    }

    public static void main(String[] args) { new Transaction(""); }
}