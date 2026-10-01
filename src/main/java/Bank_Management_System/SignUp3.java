package Bank_Management_System;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.util.*;

import javax.swing.*;
import static Bank_Management_System.Login.*;

public class SignUp3 extends JFrame implements ActionListener {

    JLabel formaccount, accounttype, card, cnumber, pin, pinnum, cdetails, pindetails, service;
    JRadioButton save, fixed, current, deposit;
    JCheckBox c1, c2, c3, c4, c5, c6, c7;
    JButton submit, cancel;
    String formno;

    SignUp3(String formno) {
        this.formno = formno;
        setTitle("SecureBank ATM - Account Details");
        setLayout(null);
        getContentPane().setBackground(ATM_FRAME);

        // -- Header --
        JPanel headerPanel = new JPanel(null);
        headerPanel.setBounds(0, 0, 660, 70);
        headerPanel.setBackground(new Color(15, 22, 40));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COL));
        add(headerPanel);

        JLabel title = new JLabel("Create Account  -  Step 3 of 3: Account Details");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        title.setForeground(TEXT);
        title.setBounds(25, 14, 500, 20);
        headerPanel.add(title);

        JLabel formNoLbl = new JLabel("Form #" + formno);
        formNoLbl.setFont(new Font("Monospaced", Font.PLAIN, 11));
        formNoLbl.setForeground(MUTED);
        formNoLbl.setBounds(25, 38, 300, 16);
        headerPanel.add(formNoLbl);

        SignUp.drawStepBar(headerPanel, 3);

        formaccount = new JLabel("Page 3: Account Details");
        formaccount.setFont(new Font("SansSerif", Font.BOLD, 16));
        formaccount.setForeground(TEXT);
        formaccount.setBounds(100, 80, 500, 30);
        add(formaccount);

        accounttype = new JLabel("Account Type");
        accounttype.setFont(new Font("SansSerif", Font.BOLD, 14));
        accounttype.setForeground(ACCENT);
        accounttype.setBounds(100, 120, 500, 30);
        add(accounttype);

        save = new JRadioButton("Saving Account");
        save.setBounds(150, 160, 150, 25);
        save.setBackground(ATM_FRAME);
        save.setForeground(TEXT);
        add(save);

        fixed = new JRadioButton("Fixed Account");
        fixed.setBounds(320, 160, 150, 25);
        fixed.setBackground(ATM_FRAME);
        fixed.setForeground(TEXT);
        add(fixed);

        current = new JRadioButton("Current Account");
        current.setBounds(150, 195, 150, 25);
        current.setBackground(ATM_FRAME);
        current.setForeground(TEXT);
        add(current);

        deposit = new JRadioButton("Recurring Deposit");
        deposit.setBounds(320, 195, 150, 25);
        deposit.setBackground(ATM_FRAME);
        deposit.setForeground(TEXT);
        add(deposit);

        ButtonGroup groupaccount = new ButtonGroup();
        groupaccount.add(save);
        groupaccount.add(fixed);
        groupaccount.add(current);
        groupaccount.add(deposit);

        card = new JLabel("Card Number");
        card.setFont(new Font("SansSerif", Font.BOLD, 14));
        card.setForeground(ACCENT);
        card.setBounds(100, 240, 200, 30);
        add(card);

        cnumber = new JLabel("XXXX-XXXX-XXXX-XXXX");
        cnumber.setFont(new Font("Monospaced", Font.BOLD, 16));
        cnumber.setForeground(TEXT);
        cnumber.setBounds(250, 240, 300, 30);
        add(cnumber);

        cdetails = new JLabel("Your 16 digit card number");
        cdetails.setFont(new Font("SansSerif", Font.PLAIN, 11));
        cdetails.setForeground(MUTED);
        cdetails.setBounds(100, 270, 500, 20);
        add(cdetails);

        pin = new JLabel("PIN:");
        pin.setFont(new Font("SansSerif", Font.BOLD, 14));
        pin.setForeground(ACCENT);
        pin.setBounds(100, 300, 100, 30);
        add(pin);

        pinnum = new JLabel("XXXX");
        pinnum.setFont(new Font("Monospaced", Font.BOLD, 16));
        pinnum.setForeground(TEXT);
        pinnum.setBounds(250, 300, 100, 30);
        add(pinnum);

        pindetails = new JLabel("Your 4 digit PIN");
        pindetails.setFont(new Font("SansSerif", Font.PLAIN, 11));
        pindetails.setForeground(MUTED);
        pindetails.setBounds(100, 330, 500, 20);
        add(pindetails);

        service = new JLabel("Services Required:");
        service.setFont(new Font("SansSerif", Font.BOLD, 14));
        service.setForeground(ACCENT);
        service.setBounds(100, 370, 300, 25);
        add(service);

        c1 = new JCheckBox("ATM Card");
        c1.setFont(new Font("SansSerif", Font.PLAIN, 13));
        c1.setBackground(ATM_FRAME); c1.setForeground(TEXT);
        c1.setBounds(100, 405, 120, 25);
        add(c1);

        c2 = new JCheckBox("Internet Banking");
        c2.setFont(new Font("SansSerif", Font.PLAIN, 13));
        c2.setBackground(ATM_FRAME); c2.setForeground(TEXT);
        c2.setBounds(250, 405, 150, 25);
        add(c2);

        c3 = new JCheckBox("Mobile Banking");
        c3.setFont(new Font("SansSerif", Font.PLAIN, 13));
        c3.setBackground(ATM_FRAME); c3.setForeground(TEXT);
        c3.setBounds(100, 440, 150, 25);
        add(c3);

        c4 = new JCheckBox("Email & SMS Alert");
        c4.setFont(new Font("SansSerif", Font.PLAIN, 13));
        c4.setBackground(ATM_FRAME); c4.setForeground(TEXT);
        c4.setBounds(250, 440, 150, 25);
        add(c4);

        c5 = new JCheckBox("Cheque Book");
        c5.setFont(new Font("SansSerif", Font.PLAIN, 13));
        c5.setBackground(ATM_FRAME); c5.setForeground(TEXT);
        c5.setBounds(100, 475, 150, 25);
        add(c5);

        c6 = new JCheckBox("E-Statement");
        c6.setFont(new Font("SansSerif", Font.PLAIN, 13));
        c6.setBackground(ATM_FRAME); c6.setForeground(TEXT);
        c6.setBounds(250, 475, 150, 25);
        add(c6);

        c7 = new JCheckBox("I accept all terms and conditions");
        c7.setFont(new Font("SansSerif", Font.PLAIN, 13));
        c7.setBackground(ATM_FRAME); c7.setForeground(TEXT);
        c7.setBounds(100, 520, 400, 25);
        add(c7);

        submit = makeAccentButton("Submit");
        submit.setBounds(100, 560, 120, 35);
        submit.addActionListener(this);
        add(submit);

        cancel = makeOutlineButton("Cancel");
        cancel.setBounds(250, 560, 120, 35);
        cancel.addActionListener(this);
        add(cancel);

        setSize(660, 660);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {

            String accountType = null;
            if      (save.isSelected())    accountType = "Saving Account";
            else if (fixed.isSelected())   accountType = "Fixed Deposit Account";
            else if (current.isSelected()) accountType = "Current Account";
            else if (deposit.isSelected()) accountType = "Recurring Deposit Account";

            if (!c7.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please accept the terms and conditions");
                return;
            }
            if (accountType == null) {
                JOptionPane.showMessageDialog(null, "Account type is Required");
                return;
            }

            // Generate card number and PIN
            Random r = new Random();
            String cardnumber = "4";
            for (int i = 0; i < 15; i++) cardnumber += r.nextInt(10);
            String pinnumber = String.format("%04d", r.nextInt(10000));

            // Build facility string
            String facility = "";
            if (c1.isSelected()) facility += " ATM Card";
            if (c2.isSelected()) facility += " Internet Banking";
            if (c3.isSelected()) facility += " Mobile Banking";
            if (c4.isSelected()) facility += " Email & SMS Alert";
            if (c5.isSelected()) facility += " Cheque Book";
            if (c6.isSelected()) facility += " E-Statement";

            try {
                Conn conn = new Conn();

                // UPDATE the same row in signup_combined with Step 3 fields.
                PreparedStatement ps1 = conn.connect.prepareStatement(
                    "UPDATE signup_combined SET " +
                    "accounttype=?, cardnumber=?, pinnumber=?, facility=? " +
                    "WHERE formno=?");
                ps1.setString(1, accountType);
                ps1.setString(2, cardnumber);
                ps1.setString(3, pinnumber);
                ps1.setString(4, facility);
                ps1.setString(5, formno);   // WHERE clause
                ps1.executeUpdate();

                // login table stays separate — used for authentication
                PreparedStatement ps2 = conn.connect.prepareStatement(
                    "INSERT INTO login (formno, cardnumber, pinnumber) VALUES (?, ?, ?)");
                ps2.setString(1, formno);
                ps2.setString(2, cardnumber);
                ps2.setString(3, pinnumber);
                ps2.executeUpdate();

                JOptionPane.showMessageDialog(null,
                    "Account created successfully!\n\n" +
                    "Card Number: " + cardnumber + "\nPIN: " + pinnumber +
                    "\n\nNOTE: Please save your Card & PIN Number");

                setVisible(false);
                new Transaction(pinnumber).setVisible(true);

            } catch (Exception ex) {
                ex.printStackTrace();
                showMsg(ex.getMessage(), "error");
            }

        } else if (e.getSource() == cancel) {
            setVisible(false);
            new Login().setVisible(true);
        }
    }

    public static void main(String[] args) { new SignUp3(""); }
}