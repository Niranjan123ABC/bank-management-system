package Bank_Management_System;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import static Bank_Management_System.Login.*;

public class Pinchange extends JFrame implements ActionListener {

    JButton change, back;
    JTextField pin, repin;
    String pinnumber;

    Pinchange(String pinnumber) {
        this.pinnumber = pinnumber;
        setTitle("SecureBank ATM - PIN Change");
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
        JLabel screenHeader = new JLabel("CHANGE PIN", SwingConstants.CENTER);
        screenHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
        screenHeader.setForeground(ACCENT);
        screenHeader.setBounds(0, 15, 500, 25);
        screen.add(screenHeader);

        JLabel subHeader = new JLabel("Enter your new 4-digit PIN", SwingConstants.CENTER);
        subHeader.setFont(new Font("SansSerif", Font.PLAIN, 11));
        subHeader.setForeground(MUTED);
        subHeader.setBounds(0, 42, 500, 18);
        screen.add(subHeader);

        // Divider
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0, 100, 150));
        sep.setBounds(30, 68, 440, 1);
        screen.add(sep);

        // -- PIN Input Panel --
        JPanel inputPanel = new JPanel(null);
        inputPanel.setBounds(50, 90, 400, 230);
        inputPanel.setBackground(new Color(12, 28, 65));
        inputPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 150), 1));
        screen.add(inputPanel);

        JLabel l1 = makeLabel("NEW PIN (4 digits)");
        l1.setBounds(25, 15, 350, 18);
        inputPanel.add(l1);

        pin = makeTextField("Enter new PIN");
        pin.setBounds(25, 38, 350, 36);
        inputPanel.add(pin);

        JLabel l2 = makeLabel("CONFIRM NEW PIN");
        l2.setBounds(25, 85, 350, 18);
        inputPanel.add(l2);

        repin = makeTextField("Re-enter new PIN");
        repin.setBounds(25, 108, 350, 36);
        inputPanel.add(repin);

        // Buttons
        change = makeScreenButton("CHANGE PIN");
        change.setBounds(25, 160, 170, 38);
        change.addActionListener(this);
        inputPanel.add(change);

        back = makeScreenButton("BACK");
        back.setBounds(205, 160, 170, 38);
        back.addActionListener(this);
        inputPanel.add(back);

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
            int row = i / 3;
            int col = i % 3;
            JButton key = makeKey(keys[i]);
            key.setBounds(80 + col * 130, 15 + row * 38, 120, 32);
            keypadArea.add(key);
        }

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

        setSize(680, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == change) {
            String npin = pin.getText().trim();
            String rpin = repin.getText().trim();

            if (npin.isEmpty() || rpin.isEmpty()) {
                showMsg("Please fill both PIN fields", "error"); return;
            }
            if (!npin.equals(rpin)) {
                showMsg("PINs do not match", "error"); return;
            }
            if (npin.length() != 4 || !npin.matches("\\d+")) {
                showMsg("PIN must be exactly 4 digits", "error"); return;
            }

            try {
                Conn conn = new Conn();

                // signup3 drop ho gayi hai — ab signup_combined use ho rahi hai
                for (String table : new String[]{"bank", "login", "signup_combined"}) {
                    PreparedStatement ps = conn.connect.prepareStatement(
                        "UPDATE " + table + " SET pinnumber=? WHERE pinnumber=?");
                    ps.setString(1, rpin);
                    ps.setString(2, pinnumber);
                    ps.executeUpdate();
                }

                showMsg("PIN changed successfully!", "info");
                setVisible(false);
                new Transaction(rpin).setVisible(true);

            } catch (Exception ex) {
                ex.printStackTrace();
                showMsg(ex.getMessage(), "error");
            }

        } else if (e.getSource() == back) {
            setVisible(false);
            new Transaction(pinnumber).setVisible(true);
        }
    }

    public static void main(String[] args) { new Pinchange(""); }
}