package Bank_Management_System;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import static Bank_Management_System.Login.*;

public class FastCash extends JFrame implements ActionListener {

    JButton b100, b500, b1000, b2000, b5000, b10000, back, customBtn;
    JTextField customAmount;
    String pinnumber;
    String formno;

    FastCash(String pinnumber) {
        this.pinnumber = pinnumber;
        this.formno = Deposite.getFormno(pinnumber);

        setTitle("SecureBank ATM - Fast Cash");
        setLayout(null);
        getContentPane().setBackground(ATM_FRAME);

        JPanel atmFrame = new JPanel(null);
        atmFrame.setBounds(50, 20, 560, 620);
        atmFrame.setBackground(ATM_FRAME);
        atmFrame.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 75), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        add(atmFrame);

        JLabel brand = new JLabel("[ATM]  SECURE BANK", SwingConstants.CENTER);
        brand.setFont(new Font("SansSerif", Font.BOLD, 18));
        brand.setForeground(new Color(255, 215, 0));
        brand.setBounds(0, 10, 560, 30);
        atmFrame.add(brand);

        JPanel screen = new JPanel(null);
        screen.setBounds(30, 50, 500, 380);
        screen.setBackground(SCREEN_BG);
        screen.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SCREEN_BORDER, 3),
            BorderFactory.createLineBorder(new Color(0, 100, 150), 1)
        ));
        atmFrame.add(screen);

        JLabel screenHeader = new JLabel("QUICK WITHDRAWAL", SwingConstants.CENTER);
        screenHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
        screenHeader.setForeground(ACCENT);
        screenHeader.setBounds(0, 15, 500, 25);
        screen.add(screenHeader);

        JLabel subHeader = new JLabel("Select a preset amount or enter custom amount", SwingConstants.CENTER);
        subHeader.setFont(new Font("SansSerif", Font.PLAIN, 11));
        subHeader.setForeground(MUTED);
        subHeader.setBounds(0, 42, 500, 18);
        screen.add(subHeader);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0, 100, 150));
        sep.setBounds(30, 68, 440, 1);
        screen.add(sep);

        // -- Preset amount buttons (3x2 grid, slightly smaller to make room below) --
        int[] amounts = {100, 500, 1000, 2000, 5000, 10000};
        JButton[] buttons = new JButton[6];
        int bw = 200, bh = 42, gx = 20, gy = 10, startX = 30, startY = 82;

        for (int i = 0; i < 6; i++) {
            int row = i / 2, col = i % 2;
            buttons[i] = makeCashButton("Rs." + String.format("%,d", amounts[i]));
            buttons[i].setBounds(startX + col * (bw + gx), startY + row * (bh + gy), bw, bh);
            buttons[i].addActionListener(this);
            screen.add(buttons[i]);
        }
        b100 = buttons[0]; b500 = buttons[1]; b1000 = buttons[2];
        b2000 = buttons[3]; b5000 = buttons[4]; b10000 = buttons[5];

        // -- Separator between preset and custom --
        JSeparator sep2 = new JSeparator();
        sep2.setForeground(new Color(0, 100, 150));
        sep2.setBounds(30, 248, 440, 1);
        screen.add(sep2);

        JLabel customLabel = new JLabel("OTHER AMOUNT (Rs.)", SwingConstants.LEFT);
        customLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        customLabel.setForeground(MUTED);
        customLabel.setBounds(30, 256, 200, 16);
        screen.add(customLabel);

        // -- Custom amount text field --
        customAmount = makeTextField("Enter amount");
        customAmount.setBounds(30, 276, 290, 36);
        screen.add(customAmount);

        // -- Custom withdraw button --
        customBtn = makeScreenButton("WITHDRAW");
        customBtn.setBounds(330, 276, 140, 36);
        customBtn.addActionListener(this);
        screen.add(customBtn);

        // -- Back button --
        back = makeScreenButton("BACK TO MENU");
        back.setBounds(150, 328, 200, 36);
        back.addActionListener(this);
        screen.add(back);

        // -- Keypad Area --
        JPanel keypadArea = new JPanel(null);
        keypadArea.setBounds(30, 445, 500, 130);
        keypadArea.setBackground(KEYPAD_BG);
        keypadArea.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 65), 1));
        atmFrame.add(keypadArea);

        for (int i = 0; i < 3; i++) {
            JButton sideBtn = makeSideButton();
            sideBtn.setBounds(15, 15 + i * 38, 50, 32);
            keypadArea.add(sideBtn);
        }
        String[] keys = {"1","2","3","4","5","6","7","8","9","CLR","0","ENTER"};
        for (int i = 0; i < 12; i++) {
            int row = i / 3, col = i % 3;
            JButton key = makeKey(keys[i]);
            key.setBounds(80 + col * 130, 15 + row * 38, 120, 32);
            keypadArea.add(key);
        }
        for (int i = 0; i < 3; i++) {
            JButton sideBtn = makeSideButton();
            sideBtn.setBounds(435, 15 + i * 38, 50, 32);
            keypadArea.add(sideBtn);
        }

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

        setSize(680, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JButton makeCashButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Monospaced", Font.BOLD, 15));
        b.setBackground(new Color(20, 45, 90));
        b.setForeground(TEXT);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(BUTTON_BORDER, 1));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                b.setBorder(BorderFactory.createLineBorder(ACCENT_GLOW, 2));
                b.setBackground(new Color(0, 100, 150));
                b.setForeground(ACCENT_GLOW);
            }
            public void mouseExited(MouseEvent e) {
                b.setBorder(BorderFactory.createLineBorder(BUTTON_BORDER, 1));
                b.setBackground(new Color(20, 45, 90));
                b.setForeground(TEXT);
            }
        });
        return b;
    }

    // Shared withdraw logic — avoids code duplication
    private void doWithdraw(int withdrawAmt) {
        try {
            Conn c = new Conn();
            PreparedStatement ps1 = c.connect.prepareStatement(
                "SELECT type, amount FROM bank WHERE formno = ?");
            ps1.setString(1, formno);
            ResultSet rs = ps1.executeQuery();
            int balance = 0;
            while (rs.next())
                balance += rs.getString("type").equals("Deposit")
                           ? rs.getInt("amount") : -rs.getInt("amount");

            if (balance < withdrawAmt) {
                showMsg("Insufficient balance. Available: Rs." + String.format("%,d", balance), "error");
                return;
            }

            PreparedStatement ps2 = c.connect.prepareStatement(
                "INSERT INTO bank (formno, pinnumber, date, type, amount) VALUES (?, ?, ?, ?, ?)");
            ps2.setString(1, formno);
            ps2.setString(2, pinnumber);
            ps2.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));
            ps2.setString(4, "Withdraw");
            ps2.setInt(5, withdrawAmt);
            ps2.executeUpdate();

            showMsg("Rs." + String.format("%,d", withdrawAmt) + " withdrawn successfully!", "info");
            setVisible(false);
            new Transaction(pinnumber).setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            setVisible(false);
            new Transaction(pinnumber).setVisible(true);
            return;
        }

        // Custom amount button
        if (e.getSource() == customBtn) {
            String text = customAmount.getText().trim();
            if (text.isEmpty() || text.equals("Enter amount")) {
                showMsg("Please enter an amount", "error");
                return;
            }
            try {
                int amt = Integer.parseInt(text);
                if (amt <= 0) {
                    showMsg("Enter a valid amount greater than 0", "error");
                    return;
                }
                doWithdraw(amt);
            } catch (NumberFormatException ex) {
                showMsg("Amount must be a valid number", "error");
            }
            return;
        }

        // Preset buttons
        int withdrawAmt = 0;
        if      (e.getSource() == b100)   withdrawAmt = 100;
        else if (e.getSource() == b500)   withdrawAmt = 500;
        else if (e.getSource() == b1000)  withdrawAmt = 1000;
        else if (e.getSource() == b2000)  withdrawAmt = 2000;
        else if (e.getSource() == b5000)  withdrawAmt = 5000;
        else if (e.getSource() == b10000) withdrawAmt = 10000;

        if (withdrawAmt > 0) doWithdraw(withdrawAmt);
    }

    public static void main(String[] args) { new FastCash(""); }
}