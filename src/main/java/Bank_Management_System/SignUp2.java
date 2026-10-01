package Bank_Management_System;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import javax.swing.*;
import static Bank_Management_System.Login.*;
import static Bank_Management_System.SignUp.*;

public class SignUp2 extends JFrame implements ActionListener {

    JTextField occupationtext, pannumtext, addhartext;
    JRadioButton married, unmarried, yes, no;
    JButton next;
    JComboBox<String> incomebox, qualificationbox, categorybox;
    String formno;

    SignUp2(String formno) {
        this.formno = formno;
        setTitle("SecureBank ATM - New Account (Step 2/3)");
        setLayout(null);
        getContentPane().setBackground(ATM_FRAME);

        JPanel headerPanel = new JPanel(null);
        headerPanel.setBounds(0, 0, 660, 70);
        headerPanel.setBackground(new Color(15, 22, 40));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COL));
        add(headerPanel);

        JLabel title = new JLabel("Create Account  -  Step 2 of 3: Additional Details");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        title.setForeground(TEXT);
        title.setBounds(25, 14, 500, 20);
        headerPanel.add(title);

        JLabel formNoLbl = new JLabel("Form #" + formno);
        formNoLbl.setFont(new Font("Monospaced", Font.PLAIN, 11));
        formNoLbl.setForeground(MUTED);
        formNoLbl.setBounds(25, 38, 300, 16);
        headerPanel.add(formNoLbl);

        drawStepBar(headerPanel, 2);

        JPanel form = new JPanel(null);
        form.setBackground(ATM_FRAME);
        form.setPreferredSize(new Dimension(620, 440));

        int y = 14, lh = 18, fh = 36, gap = 52;

        form.add(makeLabel("OCCUPATION"));
        ((JLabel)form.getComponent(form.getComponentCount()-1)).setBounds(20, y, 580, lh);
        occupationtext = makeTextField("e.g. Engineer, Student, Business");
        occupationtext.setBounds(20, y+lh+2, 580, fh); form.add(occupationtext);
        y += gap;

        form.add(makeLabel("CATEGORY"));
        ((JLabel)form.getComponent(form.getComponentCount()-1)).setBounds(20, y, 280, lh);
        String[] cats = {"General","OBC","SC/ST","Hindu","Muslim","Sikh"};
        categorybox = new JComboBox<>(cats); styleCombo(categorybox);
        categorybox.setBounds(20, y+lh+2, 280, fh); form.add(categorybox);

        form.add(makeLabel("ANNUAL INCOME"));
        ((JLabel)form.getComponent(form.getComponentCount()-1)).setBounds(320, y, 280, lh);
        String[] incomes = {"Select","Below Rs.1 Lakh","Rs.1L - Rs.5L","Above Rs.5L"};
        incomebox = new JComboBox<>(incomes); styleCombo(incomebox);
        incomebox.setBounds(320, y+lh+2, 280, fh); form.add(incomebox);
        y += gap;

        form.add(makeLabel("EDUCATION QUALIFICATION"));
        ((JLabel)form.getComponent(form.getComponentCount()-1)).setBounds(20, y, 580, lh);
        String[] quals = {"10th","12th","Graduate","Post Graduate"};
        qualificationbox = new JComboBox<>(quals); styleCombo(qualificationbox);
        qualificationbox.setBounds(20, y+lh+2, 280, fh); form.add(qualificationbox);
        y += gap;

        form.add(makeLabel("MARITAL STATUS"));
        ((JLabel)form.getComponent(form.getComponentCount()-1)).setBounds(20, y, 580, lh);
        married   = makeRadio("Married");   married.setBounds(20, y+lh+6, 120, 26); form.add(married);
        unmarried = makeRadio("Unmarried"); unmarried.setBounds(150, y+lh+6, 120, 26); form.add(unmarried);
        ButtonGroup mg = new ButtonGroup(); mg.add(married); mg.add(unmarried);
        y += gap;

        form.add(makeLabel("AADHAAR NUMBER"));
        ((JLabel)form.getComponent(form.getComponentCount()-1)).setBounds(20, y, 280, lh);
        addhartext = makeTextField("XXXX XXXX XXXX");
        addhartext.setBounds(20, y+lh+2, 280, fh); form.add(addhartext);

        form.add(makeLabel("PAN NUMBER"));
        ((JLabel)form.getComponent(form.getComponentCount()-1)).setBounds(320, y, 280, lh);
        pannumtext = makeTextField("ABCDE1234F");
        pannumtext.setBounds(320, y+lh+2, 280, fh); form.add(pannumtext);
        y += gap;

        form.add(makeLabel("EXISTING BANK ACCOUNT?"));
        ((JLabel)form.getComponent(form.getComponentCount()-1)).setBounds(20, y, 580, lh);
        yes = makeRadio("Yes"); yes.setBounds(20, y+lh+6, 80, 26); form.add(yes);
        no  = makeRadio("No");  no.setBounds(110, y+lh+6, 80, 26); form.add(no);
        ButtonGroup ag = new ButtonGroup(); ag.add(yes); ag.add(no);

        JScrollPane scroll = new JScrollPane(form);
        scroll.setBounds(15, 85, 630, 490);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COL, 1));
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        scroll.setBackground(ATM_FRAME); scroll.getViewport().setBackground(ATM_FRAME);
        add(scroll);

        JPanel bottom = new JPanel(null);
        bottom.setBounds(0, 588, 660, 58);
        bottom.setBackground(new Color(15, 22, 40));
        bottom.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COL));
        add(bottom);

        JButton back = makeOutlineButton("Back");
        back.setBounds(15, 10, 130, 36);
        back.addActionListener(ev -> { setVisible(false); new SignUp().setVisible(true); });
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String occupation    = occupationtext.getText().trim();
        String category      = (String) categorybox.getSelectedItem();
        String income        = (String) incomebox.getSelectedItem();
        String qualification = (String) qualificationbox.getSelectedItem();
        String aadhar        = addhartext.getText().trim();
        String pan           = pannumtext.getText().trim();
        String marrite       = married.isSelected() ? "Married" : unmarried.isSelected() ? "Unmarried" : null;
        String account       = yes.isSelected() ? "Yes" : no.isSelected() ? "No" : null;

        if (occupation.isEmpty() || "Select".equals(income) || aadhar.isEmpty()
                || pan.isEmpty() || marrite == null || account == null) {
            showMsg("Please fill all fields before proceeding", "error");
            return;
        }

        try {
            Conn c = new Conn();
            // UPDATE the existing row created in Step 1 with Step 2 fields.
            PreparedStatement ps = c.connect.prepareStatement(
                "UPDATE signup_combined SET " +
                "occupation=?, category=?, income=?, qualification=?, marrite=?, addhar=?, pannum=?, account=? " +
                "WHERE formno=?");
            ps.setString(1, occupation);
            ps.setString(2, category);
            ps.setString(3, income);
            ps.setString(4, qualification);
            ps.setString(5, marrite);
            ps.setString(6, aadhar);
            ps.setString(7, pan);
            ps.setString(8, account);
            ps.setString(9, formno);   // WHERE clause — formno goes LAST
            ps.executeUpdate();

            setVisible(false);
            new SignUp3(formno).setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            showMsg(ex.getMessage(), "error");
        }
    }

    public static void main(String[] args) { new SignUp2(""); }
}