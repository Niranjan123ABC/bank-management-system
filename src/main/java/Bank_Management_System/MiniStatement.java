package Bank_Management_System;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import static Bank_Management_System.Login.*;

public class MiniStatement extends JFrame implements ActionListener {

    JButton backBtn, printBtn;
    String pinnumber;

    MiniStatement(String pinnumber) {
        this.pinnumber = pinnumber;
        String formno = Deposite.getFormno(pinnumber);

        setTitle("SecureBank ATM - Mini Statement");
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

        JLabel screenHeader = new JLabel("MINI STATEMENT", SwingConstants.CENTER);
        screenHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
        screenHeader.setForeground(ACCENT);
        screenHeader.setBounds(0, 10, 500, 25);
        screen.add(screenHeader);

        JLabel cardLbl = new JLabel("Card:  XXXX-XXXX-XXXX-????");
        cardLbl.setFont(new Font("Monospaced", Font.PLAIN, 11));
        cardLbl.setForeground(MUTED);
        cardLbl.setBounds(15, 40, 470, 18);
        screen.add(cardLbl);

        String[] cols = {"Date", "Type", "Amount (Rs.)", "Running Bal."};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = new JTable(model);
        table.setBackground(new Color(12, 28, 65));
        table.setForeground(TEXT);
        table.setFont(new Font("Monospaced", Font.PLAIN, 11));
        table.setRowHeight(24);
        table.setGridColor(new Color(0, 100, 150));
        table.setSelectionBackground(new Color(0, 180, 220, 40));
        table.getTableHeader().setBackground(new Color(15, 35, 80));
        table.getTableHeader().setForeground(ACCENT);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 10));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(15, 65, 470, 200);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 150), 1));
        scroll.getViewport().setBackground(new Color(12, 28, 65));
        screen.add(scroll);

        // Balance box
        JPanel balBox = new JPanel(null);
        balBox.setBounds(15, 272, 470, 55);
        balBox.setBackground(new Color(0, 180, 220, 18));
        balBox.setBorder(BorderFactory.createLineBorder(new Color(0, 180, 220, 60), 1));
        screen.add(balBox);

        JLabel balTitle = new JLabel("Current Balance");
        balTitle.setFont(new Font("SansSerif", Font.BOLD, 10));
        balTitle.setForeground(MUTED);
        balTitle.setBounds(14, 6, 200, 14);
        balBox.add(balTitle);

        JLabel balAmt = new JLabel("Rs. 0");
        balAmt.setFont(new Font("Monospaced", Font.BOLD, 20));
        balAmt.setForeground(ACCENT);
        balAmt.setBounds(14, 22, 442, 26);
        balBox.add(balAmt);

        // BACK button
        backBtn = makeScreenButton("BACK TO MENU");
        backBtn.setBounds(30, 335, 190, 35);
        backBtn.addActionListener(this);
        screen.add(backBtn);

        // PRINT / SAVE button
        printBtn = makeScreenButton("PRINT / SAVE");
        printBtn.setBounds(280, 335, 190, 35);
        printBtn.addActionListener(this);
        screen.add(printBtn);

        // Fetch data
        try {
            Conn conn = new Conn();

            PreparedStatement ps1 = conn.connect.prepareStatement(
                "SELECT cardnumber FROM login WHERE pinnumber = ?");
            ps1.setString(1, pinnumber);
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                String cn = rs1.getString("cardnumber");
                cardLbl.setText("Card:  XXXX-XXXX-XXXX-" + cn.substring(cn.length() - 4));
            }

            PreparedStatement ps3 = conn.connect.prepareStatement(
                "SELECT type, amount FROM bank WHERE formno = ?");
            ps3.setString(1, formno);
            ResultSet rs3 = ps3.executeQuery();
            int runBal = 0;
            while (rs3.next())
                runBal += rs3.getString("type").equals("Deposit")
                        ? rs3.getInt("amount") : -rs3.getInt("amount");
            balAmt.setText("Rs. " + String.format("%,d", runBal));

            PreparedStatement ps2 = conn.connect.prepareStatement(
                "SELECT date, type, amount FROM bank WHERE formno = ? ORDER BY date DESC LIMIT 10");
            ps2.setString(1, formno);
            ResultSet rs2 = ps2.executeQuery();

            int bal = runBal;
            while (rs2.next()) {
                String type = rs2.getString("type");
                int amt = rs2.getInt("amount");
                model.addRow(new Object[]{
                    rs2.getDate("date"),
                    type,
                    (type.equals("Deposit") ? "+" : "-") + String.format("%,d", amt),
                    "Rs. " + String.format("%,d", bal)
                });
                bal = type.equals("Deposit") ? bal - amt : bal + amt;
            }

        } catch (Exception e) { e.printStackTrace(); }

        // Color rows
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setHorizontalAlignment(SwingConstants.CENTER);
                setBackground(new Color(12, 28, 65));
                String type = (String) t.getValueAt(row, 1);
                if (col == 1 || col == 2)
                    setForeground("Deposit".equals(type) ? new Color(0, 200, 140) : new Color(255, 80, 100));
                else
                    setForeground(TEXT);
                if (sel) setBackground(new Color(0, 180, 220, 40));
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return this;
            }
        });

        // Keypad
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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            // Back — Transaction screen pe jao
            dispose();
            new Transaction(pinnumber).setVisible(true);

        } else if (e.getSource() == printBtn) {
            // Print / Save — system print dialog khulega
            printStatement();
        }
    }

    private void printStatement() {
        String formno = Deposite.getFormno(pinnumber);

        // Ek white panel banao jo statement draw kare
        JPanel printPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int y = 40;
                int pw = getWidth();

                // ---- Header ----
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("SansSerif", Font.BOLD, 22));
                String title = "SECURE BANK";
                g2.drawString(title, (pw - g2.getFontMetrics().stringWidth(title)) / 2, y);
                y += 28;

                g2.setFont(new Font("SansSerif", Font.PLAIN, 13));
                String sub = "Mini Statement";
                g2.drawString(sub, (pw - g2.getFontMetrics().stringWidth(sub)) / 2, y);
                y += 10;

                g2.setColor(Color.GRAY);
                g2.drawLine(30, y, pw - 30, y);
                y += 18;

                // ---- Date & Card ----
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
                g2.drawString("Date: " + new java.util.Date(), 30, y);
                y += 18;

                try {
                    Conn conn = new Conn();

                    PreparedStatement ps1 = conn.connect.prepareStatement(
                        "SELECT cardnumber FROM login WHERE pinnumber = ?");
                    ps1.setString(1, pinnumber);
                    ResultSet rs1 = ps1.executeQuery();
                    if (rs1.next()) {
                        String cn = rs1.getString("cardnumber");
                        g2.drawString("Card: XXXX-XXXX-XXXX-" + cn.substring(cn.length() - 4), 30, y);
                        y += 18;
                    }

                    // ---- Table Header ----
                    g2.setColor(Color.GRAY);
                    g2.drawLine(30, y, pw - 30, y);
                    y += 14;

                    g2.setColor(Color.BLACK);
                    g2.setFont(new Font("Monospaced", Font.BOLD, 11));
                    g2.drawString(String.format("%-20s %-12s %-14s %-14s",
                        "Date", "Type", "Amount", "Balance"), 30, y);
                    y += 6;

                    g2.setColor(Color.GRAY);
                    g2.drawLine(30, y, pw - 30, y);
                    y += 14;

                    // ---- Balance ----
                    PreparedStatement ps3 = conn.connect.prepareStatement(
                        "SELECT type, amount FROM bank WHERE formno = ?");
                    ps3.setString(1, formno);
                    ResultSet rs3 = ps3.executeQuery();
                    int runBal = 0;
                    while (rs3.next())
                        runBal += rs3.getString("type").equals("Deposit")
                                ? rs3.getInt("amount") : -rs3.getInt("amount");

                    // ---- Transactions ----
                    PreparedStatement ps2 = conn.connect.prepareStatement(
                        "SELECT date, type, amount FROM bank WHERE formno = ? ORDER BY date DESC LIMIT 10");
                    ps2.setString(1, formno);
                    ResultSet rs2 = ps2.executeQuery();

                    g2.setFont(new Font("Monospaced", Font.PLAIN, 10));
                    g2.setColor(Color.BLACK);
                    int bal = runBal;
                    while (rs2.next()) {
                        String type = rs2.getString("type");
                        int    amt  = rs2.getInt("amount");
                        String sign = type.equals("Deposit") ? "+" : "-";
                        g2.drawString(String.format("%-20s %-12s %-14s %-14s",
                            rs2.getDate("date"),
                            type,
                            sign + String.format("%,d", amt),
                            "Rs." + String.format("%,d", bal)), 30, y);
                        y += 18;
                        bal = type.equals("Deposit") ? bal - amt : bal + amt;
                    }

                    // ---- Current Balance ----
                    g2.setColor(Color.GRAY);
                    g2.drawLine(30, y, pw - 30, y);
                    y += 16;

                    g2.setColor(Color.BLACK);
                    g2.setFont(new Font("SansSerif", Font.BOLD, 13));
                    g2.drawString("Current Balance:  Rs. " + String.format("%,d", runBal), 30, y);
                    y += 24;

                    // ---- Footer ----
                    g2.setColor(Color.GRAY);
                    g2.drawLine(30, y, pw - 30, y);
                    y += 14;
                    g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
                    String footer = "Thank you for banking with SecureBank";
                    g2.drawString(footer, (pw - g2.getFontMetrics().stringWidth(footer)) / 2, y);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(600, 520);
            }
        };

        // PrinterJob — system print dialog
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Mini Statement - SecureBank");

        job.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) return Printable.NO_SUCH_PAGE;

            Graphics2D g2 = (Graphics2D) graphics;
            g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            double scaleX = pageFormat.getImageableWidth()  / printPanel.getPreferredSize().getWidth();
            double scaleY = pageFormat.getImageableHeight() / printPanel.getPreferredSize().getHeight();
            double scale  = Math.min(scaleX, scaleY);
            g2.scale(scale, scale);

            printPanel.setSize(printPanel.getPreferredSize());
            printPanel.setBackground(Color.WHITE);
            printPanel.printAll(graphics);

            return Printable.PAGE_EXISTS;
        });

        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
                showMsg("Statement printed / saved successfully!", "info");
            } catch (PrinterException ex) {
                showMsg("Print failed: " + ex.getMessage(), "error");
            }
        }
    }

    public static void main(String[] args) { new MiniStatement(""); }
}