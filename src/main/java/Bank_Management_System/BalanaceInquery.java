package Bank_Management_System;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import static Bank_Management_System.Login.*;

public class BalanaceInquery extends JFrame implements ActionListener {

    JButton back;
    String pinnumber;
    String formno;

    BalanaceInquery(String pinnumber) {
        this.pinnumber = pinnumber;
        this.formno = Deposite.getFormno(pinnumber);

        setTitle("SecureBank ATM - Balance Inquiry");
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

        JLabel screenHeader = new JLabel("BALANCE INQUIRY", SwingConstants.CENTER);
        screenHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
        screenHeader.setForeground(ACCENT);
        screenHeader.setBounds(0, 15, 500, 25);
        screen.add(screenHeader);

        JLabel subHeader = new JLabel("Your current account balance", SwingConstants.CENTER);
        subHeader.setFont(new Font("SansSerif", Font.PLAIN, 11));
        subHeader.setForeground(MUTED);
        subHeader.setBounds(0, 42, 500, 18);
        screen.add(subHeader);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0, 100, 150));
        sep.setBounds(30, 68, 440, 1);
        screen.add(sep);

        // Fetch balance using formno
        int balance = 0;
        try {
            Conn c = new Conn();
            PreparedStatement ps = c.connect.prepareStatement(
                "SELECT type, amount FROM bank WHERE formno = ?");
            ps.setString(1, formno);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                balance += rs.getString("type").equalsIgnoreCase("Deposit")
                           ? rs.getInt("amount") : -rs.getInt("amount");
        } catch (Exception e) { e.printStackTrace(); }

        JPanel balBox = new JPanel(null);
        balBox.setBounds(50, 100, 400, 160);
        balBox.setBackground(new Color(12, 28, 65));
        balBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 180, 220, 80), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        screen.add(balBox);

        JLabel balLabel = new JLabel("AVAILABLE BALANCE");
        balLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        balLabel.setForeground(MUTED);
        balLabel.setBounds(20, 15, 360, 18);
        balBox.add(balLabel);

        JLabel balAmount = new JLabel("Rs. " + String.format("%,d", balance));
        balAmount.setFont(new Font("Monospaced", Font.BOLD, 32));
        balAmount.setForeground(ACCENT);
        balAmount.setBounds(20, 40, 360, 45);
        balBox.add(balAmount);

        JLabel timeLabel = new JLabel("Updated: " + new java.util.Date());
        timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        timeLabel.setForeground(MUTED);
        timeLabel.setBounds(20, 95, 360, 16);
        balBox.add(timeLabel);

        JLabel note = new JLabel("This is your current account balance.");
        note.setFont(new Font("SansSerif", Font.PLAIN, 11));
        note.setForeground(MUTED);
        note.setBounds(50, 275, 400, 16);
        screen.add(note);

        back = makeScreenButton("BACK TO MENU");
        back.setBounds(150, 310, 200, 38);
        back.addActionListener(this);
        screen.add(back);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        new Transaction(pinnumber).setVisible(true);
    }

    public static void main(String[] args) { new BalanaceInquery(""); }
}