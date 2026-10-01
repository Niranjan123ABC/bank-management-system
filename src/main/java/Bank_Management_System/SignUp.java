package Bank_Management_System;

import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.sql.PreparedStatement;
import java.util.Random;
import javax.swing.*;
import static Bank_Management_System.Login.*;

public class SignUp extends JFrame implements ActionListener {

    JTextField nametext, fathertext, emailtext, addresstext, pintext, statetext, citytext;
    JRadioButton male, female;
    JButton next;
    JDateChooser datechoose;
    JComboBox<String> religionbox;
    long random;

    SignUp() {
        setTitle("SecureBank ATM - New Account (Step 1/3)");
        setLayout(null);
        getContentPane().setBackground(ATM_FRAME);

        random = Math.abs(new Random().nextLong() % 9000L) + 1000L;

        // -- Header --
        JPanel headerPanel = new JPanel(null);
        headerPanel.setBounds(0, 0, 660, 70);
        headerPanel.setBackground(new Color(15, 22, 40));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COL));
        add(headerPanel);

        JLabel title = new JLabel("Create Account  -  Step 1 of 3: Personal Details");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        title.setForeground(TEXT);
        title.setBounds(25, 14, 500, 20);
        headerPanel.add(title);

        JLabel formNoLbl = new JLabel("Form #" + random);
        formNoLbl.setFont(new Font("Monospaced", Font.PLAIN, 11));
        formNoLbl.setForeground(MUTED);
        formNoLbl.setBounds(25, 38, 300, 16);
        headerPanel.add(formNoLbl);

        drawStepBar(headerPanel, 1);

        // -- Scrollable form --
        JPanel form = new JPanel(null);
        form.setBackground(ATM_FRAME);
        form.setPreferredSize(new Dimension(620, 480));

        int y = 14, lh = 18, fh = 36, gap = 52;

        form.add(makeLabel("FULL NAME"));           ((JLabel)form.getComponent(form.getComponentCount()-1)).setBounds(20, y, 280, lh);
        nametext = makeTextField("e.g. John Doe");  nametext.setBounds(20, y+lh+2, 280, fh); form.add(nametext);

        form.add(makeLabel("FATHER'S NAME"));       ((JLabel)form.getComponent(form.getComponentCount()-1)).setBounds(320, y, 280, lh);
        fathertext = makeTextField("Father's full name"); fathertext.setBounds(320, y+lh+2, 280, fh); form.add(fathertext);
        y += gap;

        form.add(makeLabel("RELIGION"));            ((JLabel)form.getComponent(form.getComponentCount()-1)).setBounds(20, y, 280, lh);
        String[] rels = {"Select Religion","Hindu","Muslim","Sikh","Christian","Other"};
        religionbox = new JComboBox<>(rels);
        styleCombo(religionbox);
        religionbox.setBounds(20, y+lh+2, 280, fh); form.add(religionbox);

        form.add(makeLabel("DATE OF BIRTH"));       ((JLabel)form.getComponent(form.getComponentCount()-1)).setBounds(320, y, 280, lh);
        datechoose = new JDateChooser();
        datechoose.setBounds(320, y+lh+2, 280, fh);
        datechoose.setBackground(new Color(10, 15, 28));
        datechoose.setForeground(TEXT);
        form.add(datechoose);
        y += gap;

        form.add(makeLabel("GENDER"));              ((JLabel)form.getComponent(form.getComponentCount()-1)).setBounds(20, y, 580, lh);
        male   = makeRadio("Male");   male.setBounds(20, y+lh+6, 100, 26);  form.add(male);
        female = makeRadio("Female"); female.setBounds(130, y+lh+6, 100, 26); form.add(female);
        ButtonGroup bg = new ButtonGroup(); bg.add(male); bg.add(female);
        y += gap;

        form.add(makeLabel("EMAIL ADDRESS"));       ((JLabel)form.getComponent(form.getComponentCount()-1)).setBounds(20, y, 580, lh);
        emailtext = makeTextField("you@example.com"); emailtext.setBounds(20, y+lh+2, 580, fh); form.add(emailtext);
        y += gap;

        form.add(makeLabel("ADDRESS"));             ((JLabel)form.getComponent(form.getComponentCount()-1)).setBounds(20, y, 580, lh);
        addresstext = makeTextField("Street / House No."); addresstext.setBounds(20, y+lh+2, 580, fh); form.add(addresstext);
        y += gap;

        form.add(makeLabel("CITY"));               ((JLabel)form.getComponent(form.getComponentCount()-1)).setBounds(20, y, 185, lh);
        citytext = makeTextField("City"); citytext.setBounds(20, y+lh+2, 185, fh); form.add(citytext);

        form.add(makeLabel("STATE"));              ((JLabel)form.getComponent(form.getComponentCount()-1)).setBounds(215, y, 185, lh);
        statetext = makeTextField("State"); statetext.setBounds(215, y+lh+2, 185, fh); form.add(statetext);

        form.add(makeLabel("PINCODE"));            ((JLabel)form.getComponent(form.getComponentCount()-1)).setBounds(415, y, 185, lh);
        pintext = makeTextField("000000"); pintext.setBounds(415, y+lh+2, 185, fh); form.add(pintext);

        JScrollPane scroll = new JScrollPane(form);
        scroll.setBounds(15, 85, 630, 490);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COL, 1));
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        scroll.setBackground(ATM_FRAME);
        scroll.getViewport().setBackground(ATM_FRAME);
        add(scroll);

        // -- Bottom buttons --
        JPanel bottom = new JPanel(null);
        bottom.setBounds(0, 588, 660, 58);
        bottom.setBackground(new Color(15, 22, 40));
        bottom.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COL));
        add(bottom);

        JButton back = makeOutlineButton("Cancel");
        back.setBounds(15, 10, 130, 36);
        back.addActionListener(ev -> { setVisible(false); new Login().setVisible(true); });
        bottom.add(back);

        next = makeAccentButton("Next ->");
        next.setBounds(510, 10, 135, 36);
        next.addActionListener(this);
        bottom.add(next);

        setSize(660, 660);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    static void drawStepBar(JPanel parent, int active) {
        int[] xPositions = {430, 530, 610};
        String[] labels = {"1", "2", "3"};
        for (int i = 0; i < 3; i++) {
            JLabel dot = new JLabel(labels[i], SwingConstants.CENTER);
            dot.setBounds(xPositions[i], 22, 24, 24);
            dot.setFont(new Font("SansSerif", Font.BOLD, 11));
            if (i + 1 < active) {
                dot.setBackground(new Color(0, 200, 140)); dot.setForeground(Color.BLACK);
            } else if (i + 1 == active) {
                dot.setBackground(ACCENT); dot.setForeground(Color.BLACK);
            } else {
                dot.setBackground(BORDER_COL); dot.setForeground(MUTED);
            }
            dot.setOpaque(true);
            dot.setBorder(BorderFactory.createLineBorder(dot.getBackground().darker(), 1));
            parent.add(dot);
        }
    }

    static void styleCombo(JComboBox<?> c) {
        c.setBackground(new Color(10, 15, 28));
        c.setForeground(TEXT);
        c.setFont(FONT_INPUT);
        c.setBorder(BorderFactory.createLineBorder(BORDER_COL, 1));
    }

    static JRadioButton makeRadio(String text) {
        JRadioButton r = new JRadioButton(text);
        r.setFont(new Font("SansSerif", Font.PLAIN, 13));
        r.setForeground(TEXT);
        r.setBackground(ATM_FRAME);
        r.setFocusPainted(false);
        return r;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name     = nametext.getText().trim();
        String father   = fathertext.getText().trim();
        String religion = (String) religionbox.getSelectedItem();
        String dob      = ((JTextField) datechoose.getDateEditor().getUiComponent()).getText();
        String gender   = male.isSelected() ? "Male" : female.isSelected() ? "Female" : null;
        String email    = emailtext.getText().trim();
        String address  = addresstext.getText().trim();
        String city     = citytext.getText().trim();
        String state    = statetext.getText().trim();
        String pin      = pintext.getText().trim();

        if (name.isEmpty() || father.isEmpty() || "Select Religion".equals(religion)
                || dob.isEmpty() || gender == null || email.isEmpty()
                || address.isEmpty() || city.isEmpty() || state.isEmpty() || pin.isEmpty()) {
            showMsg("Please fill all fields before proceeding", "error");
            return;
        }

        try {
            Conn c = new Conn();
            // INSERT a new row into signup_combined with Step 1 fields only.
            // Step 2 and Step 3 columns are left NULL and filled later via UPDATE.
            PreparedStatement ps = c.connect.prepareStatement(
                "INSERT INTO signup_combined " +
                "(formno, name, fathername, religion, dob, gender, email, address, city, state, pincode) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1,  "" + random);
            ps.setString(2,  name);
            ps.setString(3,  father);
            ps.setString(4,  religion);
            ps.setString(5,  dob);
            ps.setString(6,  gender);
            ps.setString(7,  email);
            ps.setString(8,  address);
            ps.setString(9,  city);
            ps.setString(10, state);
            ps.setString(11, pin);
            ps.executeUpdate();

            setVisible(false);
            new SignUp2("" + random).setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            showMsg(ex.getMessage(), "error");
        }
    }

    public static void main(String[] args) { new SignUp(); }
}