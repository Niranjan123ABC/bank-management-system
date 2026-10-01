package Bank_Management_System;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import static Bank_Management_System.Login.*;

public class Withdral extends JFrame implements ActionListener {

    JTextField amount;
    JButton withdra, back;
    String pinnumber;
    String formno;

    Withdral(String pinnumber) {
        this.pinnumber = pinnumber;
        this.formno = Deposite.getFormno(pinnumber);

        setTitle("SecureBank ATM - Withdrawal");
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

        JLabel screenHeader = new JLabel("CASH WITHDRAWAL", SwingConstants.CENTER);
        screenHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
        screenHeader.setForeground(ACCENT);
        screenHeader.setBounds(0, 15, 500, 25);
        screen.add(screenHeader);

        JLabel subHeader = new JLabel("Enter amount to withdraw", SwingConstants.CENTER);
        subHeader.setFont(new Font("SansSerif", Font.PLAIN, 11));
        subHeader.setForeground(MUTED);
        subHeader.setBounds(0, 42, 500, 18);
        screen.add(subHeader);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0, 100, 150));
        sep.setBounds(30, 68, 440, 1);
        screen.add(sep);

        JPanel inputPanel = new JPanel(null);
        inputPanel.setBounds(50, 100, 400, 200);
        inputPanel.setBackground(new Color(12, 28, 65));
        inputPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 150), 1));
        screen.add(inputPanel);

        JLabel lbl = makeLabel("AMOUNT TO WITHDRAW (Rs.)");
        lbl.setBounds(25, 20, 350, 18);
        inputPanel.add(lbl);

        amount = makeTextField("Enter amount");
        amount.setBounds(25, 45, 350, 40);
        inputPanel.add(amount);

        JLabel hint = new JLabel("Amount must not exceed your current balance");
        hint.setFont(new Font("SansSerif", Font.PLAIN, 11));
        hint.setForeground(MUTED);
        hint.setBounds(25, 95, 350, 16);
        inputPanel.add(hint);

        withdra = makeScreenButton("CONFIRM WITHDRAWAL");
        withdra.setBounds(25, 130, 170, 38);
        withdra.addActionListener(this);
        inputPanel.add(withdra);

        back = makeScreenButton("BACK");
        back.setBounds(205, 130, 170, 38);
        back.addActionListener(this);
        inputPanel.add(back);

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
        if (e.getSource() == withdra) {
            String number = amount.getText().trim();
            if (number.isEmpty() || number.equals("Enter amount")) {
                showMsg("Please enter an amount", "error"); return;
            }
            try {
                int amt = Integer.parseInt(number);
                if (amt <= 0) { showMsg("Enter a valid amount greater than 0", "error"); return; }

                // Check balance using formno
                Conn c = new Conn();
                PreparedStatement ps1 = c.connect.prepareStatement(
                    "SELECT type, amount FROM bank WHERE formno = ?");
                ps1.setString(1, formno);
                ResultSet rs = ps1.executeQuery();
                int balance = 0;
                while (rs.next())
                    balance += rs.getString("type").equals("Deposit")
                               ? rs.getInt("amount") : -rs.getInt("amount");

                if (amt > balance) {
                    showMsg("Insufficient balance. Available: Rs." + balance, "error"); return;
                }

                PreparedStatement ps2 = c.connect.prepareStatement(
                    "INSERT INTO bank (formno, pinnumber, date, type, amount) VALUES (?, ?, ?, ?, ?)");
                ps2.setString(1, formno);
                ps2.setString(2, pinnumber);
                ps2.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));
                ps2.setString(4, "Withdraw");
                ps2.setInt(5, amt);
                ps2.executeUpdate();

                showMsg("Rs." + amt + " withdrawn successfully!", "info");
                setVisible(false);
                new Transaction(pinnumber).setVisible(true);
            } catch (NumberFormatException ex) {
                showMsg("Amount must be a valid number", "error");
            } catch (Exception ex) { ex.printStackTrace(); }
        } else if (e.getSource() == back) {
            setVisible(false);
            new Transaction(pinnumber).setVisible(true);
        }
    }

    public static void main(String[] args) { new Withdral(""); }
}