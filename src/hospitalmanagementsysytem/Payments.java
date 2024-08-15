package hospitalmanagementsysytem;

import static hospitalmanagementsysytem.Patient.lblRoom;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
//import net.proteanit.sql.DbUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kobinath
 */
public class Payments extends javax.swing.JFrame {

    /**
     * Creates new form employee
     */
    public Payments() {
        initComponents();
        autoId();
        patient_table();
        Payment_table();
        dt();
        BillClear();

        //BC PICTURE
        ImageIcon myImage1 = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/SRC/BC.jpeg")));
        Image img10 = myImage1.getImage();
        Image img20 = img10.getScaledInstance(jLabel2.getWidth(), jLabel2.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon ii = new ImageIcon(img20);
        jLabel2.setIcon(ii);

        //small panel BC PICTURE
        ImageIcon myImage1b = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/SRC/UcreationBC.png")));
        Image img10b = myImage1b.getImage();
        Image img20b = img10b.getScaledInstance(jLabel3.getWidth(), jLabel3.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon iib = new ImageIcon(img20b);
        jLabel3.setIcon(iib);

        ServiceCharge.setText("600.00");

    }

    public void autoId() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gp_hms", "root", "");
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT MAX(PayID) FROM payment");
            rs.next();
            rs.getString("MAX(PayID)");
            if (rs.getString("MAX(PayID)") == null) {
                lblPayID.setText("PA001");
            } else {
                long id = Long.parseLong(rs.getString("MAX(PayID)").substring(2, rs.getString("MAX(PayID)").length()));
                id++;
                lblPayID.setText("PA" + String.format("%03d", id));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    public void Connect() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gp_hms", "root", "");
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Prescription.Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Prescription.Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public void BillClear() {
        
        lblBPatientID.setText(null);
        lblBPatientName.setText(null);
        lblBAdmitDate.setText(null);
        lblBDisDate.setText(null);
        lblBDays.setText(null);
        lblBRoomNo.setText(null);
        lblBRoomCharge.setText(null);
        lblBServiceCharge.setText(null);
        lblBAmmount.setText(null);
        lblBDiscount.setText(null);
        lblBNetAmmount.setText(null);
        lblBPayMode.setText(null);
        lblBRecivedAmmount.setText(null);
        lblBBalance.setText(null);
    }

    public void dt() {

        java.util.Date d = new java.util.Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String dd = sdf.format(d);
        DISCD.setText(dd);

    }

    public void DayDuration() throws ParseException {

        String D1 = DISCD.getText();
        String D2 = AdmitD.getText();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Date d1 = sdf.parse(D1);
        Date d2 = sdf.parse(D2);

        long diffinm = Math.abs(d2.getTime() - d1.getTime());
        long diff = TimeUnit.DAYS.convert(diffinm, TimeUnit.MILLISECONDS);

        Days.setText(String.valueOf(diff));

    }

    public void Ammount() {
        Double day = Double.parseDouble(Days.getText());
        Double roomFee = Double.parseDouble(RoomCharge.getText());
        Double RoomCharge = day * roomFee;
        Double serviceCharge = Double.parseDouble(ServiceCharge.getText());
        Double ammount = serviceCharge + RoomCharge;
        Ammount.setText(String.valueOf(ammount));;

    }

    public void NetAmmount() {
        Double ammount = Double.parseDouble(Ammount.getText());
        String dis = cmbDISC.getSelectedItem().toString();

        switch (dis) {
            case "NO Discounts":
                NETAmmount.setText(Ammount.getText());
                break;
            case "Financial Assistance":
                double d = (ammount / 100) * 10;
                double netammount = ammount - d;
                NETAmmount.setText(String.valueOf(netammount));
                
                break;
            case "Sliding Scale Fee":
                double d1 = (ammount / 100) * 2;
                double netammount1 = ammount - d1;
                NETAmmount.setText(String.valueOf(netammount1));
                
                break;
            case "Prompt Payment Discounts":
                double d2 = (ammount / 100) * 3;
                double netammount2 = ammount - d2;
                NETAmmount.setText(String.valueOf(netammount2));
                
                break;
            case "Charity Care":
                double d3 = (ammount / 100) * 4;
                double netammount3 = ammount - d3;
                NETAmmount.setText(String.valueOf(netammount3));
                
                break;
            case "Negotiation or Payment Plans":
                double d4 = (ammount / 100) * 5;
                double netammount4 = ammount - d4;
                NETAmmount.setText(String.valueOf(netammount4));
                
                break;

            default:

        }

    }

    public void Balance() {
        double Receammount = Double.parseDouble(txtRicive.getText());
        double netammount = Double.parseDouble(NETAmmount.getText());
        double balance = Receammount - netammount;
        Balance.setText(String.valueOf(balance));
    }

    public void patient_table() {
        try {
            pst = con.prepareStatement("SELECT * FROM patient");
            rs = pst.executeQuery();
            java.sql.ResultSetMetaData Rsm = rs.getMetaData();
            int c;
            c = Rsm.getColumnCount();

            DefaultTableModel df = (DefaultTableModel) jTablePatient.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();

                for (int i = 1; i <= c; i++) {
                    v2.add(rs.getString("PatientNo"));
                    v2.add(rs.getString("Name"));
                    v2.add(rs.getString("Phone"));
                    v2.add(rs.getString("Address"));
                    v2.add(rs.getString("AdmitDate"));
                    v2.add(rs.getString("PRN"));
                }
                df.addRow(v2);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Payment_table() {
        try {
            pst = con.prepareStatement("SELECT * FROM payment");
            rs = pst.executeQuery();
            java.sql.ResultSetMetaData Rsm = rs.getMetaData();
            int c;
            c = Rsm.getColumnCount();

            DefaultTableModel df = (DefaultTableModel) jTablePayment.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();

                for (int i = 1; i <= c; i++) {
                    v2.add(rs.getString("PayID"));
                    v2.add(rs.getString("PatientName"));
                    v2.add(rs.getString("PatientID"));
                    v2.add(rs.getString("AdnitDate"));
                    v2.add(rs.getString("DisChargeDate"));
                    v2.add(rs.getString("Days"));
                    v2.add(rs.getString("RoomCharge"));
                    v2.add(rs.getString("ServiceCharge"));
                    v2.add(rs.getString("Ammount"));
                    v2.add(rs.getString("Discounts"));
                    v2.add(rs.getString("NetAmmount"));
                    v2.add(rs.getString("PaymentMethode"));
                    v2.add(rs.getString("RecivedAnnount"));
                    v2.add(rs.getString("Balance"));
                }
                df.addRow(v2);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BillPrint = new javax.swing.JFrame();
        jPanel4 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        Bill = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lblBPayID = new javax.swing.JLabel();
        lblBPatientID = new javax.swing.JLabel();
        lblBPatientName = new javax.swing.JLabel();
        lblBAdmitDate = new javax.swing.JLabel();
        lblBDisDate = new javax.swing.JLabel();
        lblBDays = new javax.swing.JLabel();
        lblBRoomNo = new javax.swing.JLabel();
        lblBRoomCharge = new javax.swing.JLabel();
        lblBServiceCharge = new javax.swing.JLabel();
        lblBAmmount = new javax.swing.JLabel();
        lblBDiscount = new javax.swing.JLabel();
        lblBNetAmmount = new javax.swing.JLabel();
        lblBPayMode = new javax.swing.JLabel();
        lblBRecivedAmmount = new javax.swing.JLabel();
        lblBBalance = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePayment = new javax.swing.JTable();
        cmbDISC = new javax.swing.JComboBox();
        cmbPayMode = new javax.swing.JComboBox();
        Balance = new javax.swing.JLabel();
        ServiceCharge = new javax.swing.JLabel();
        RoomCharge = new javax.swing.JLabel();
        DISCD = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        PID = new javax.swing.JLabel();
        AdmitD = new javax.swing.JLabel();
        Days = new javax.swing.JLabel();
        PName = new javax.swing.JLabel();
        txtRicive = new javax.swing.JTextField();
        NETAmmount = new javax.swing.JLabel();
        Ammount = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablePatient = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        RoomNO = new javax.swing.JLabel();
        lblPayID = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        BillPrint.setUndecorated(true);
        BillPrint.setSize(new java.awt.Dimension(1366, 710));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton5.setText("Print");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 130, 49));

        jButton2.setText("Exit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 130, 49));

        Bill.setBackground(new java.awt.Color(255, 255, 255));
        Bill.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Suwasewana Hospital");
        Bill.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 60));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Payment ID ");
        Bill.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 170, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("Patient Name ");
        Bill.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Patient ID ");
        Bill.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 160, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("Admit Date ");
        Bill.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, -1, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("Discharge Date ");
        Bill.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, -1, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Day Duration ");
        Bill.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, -1, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("Room Number ");
        Bill.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 310, -1, -1));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Room Charges ");
        Bill.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 340, -1, -1));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("Service Charges ");
        Bill.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 370, -1, -1));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Rs.");
        Bill.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 400, -1, -1));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText("Discount ");
        Bill.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 430, -1, -1));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("Net Ammount ");
        Bill.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 470, -1, -1));

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("Payment Mode");
        Bill.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 520, -1, -1));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("Recived Ammount");
        Bill.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 550, -1, -1));

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel38.setText("Balance");
        Bill.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 580, -1, -1));

        lblBPayID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBPayID.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBPayID.setText("Payment ID ");
        Bill.add(lblBPayID, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 170, -1));

        lblBPatientID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBPatientID.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBPatientID.setText("Patient ID ");
        Bill.add(lblBPatientID, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, 160, -1));

        lblBPatientName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBPatientName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBPatientName.setText("Patient Name ");
        Bill.add(lblBPatientName, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, -1, -1));

        lblBAdmitDate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBAdmitDate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBAdmitDate.setText("Admit Date ");
        Bill.add(lblBAdmitDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 220, -1, -1));

        lblBDisDate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBDisDate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBDisDate.setText("Discharge Date ");
        Bill.add(lblBDisDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 250, -1, -1));

        lblBDays.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBDays.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBDays.setText("Day Duration ");
        Bill.add(lblBDays, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 280, -1, -1));

        lblBRoomNo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBRoomNo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBRoomNo.setText("Room Number ");
        Bill.add(lblBRoomNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 310, -1, -1));

        lblBRoomCharge.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBRoomCharge.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBRoomCharge.setText("Room Charges ");
        Bill.add(lblBRoomCharge, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 340, -1, -1));

        lblBServiceCharge.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBServiceCharge.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBServiceCharge.setText("Service Charges ");
        Bill.add(lblBServiceCharge, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 370, -1, -1));

        lblBAmmount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBAmmount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBAmmount.setText("Ammount ");
        Bill.add(lblBAmmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 400, -1, -1));

        lblBDiscount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBDiscount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBDiscount.setText("Discount ");
        Bill.add(lblBDiscount, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 430, -1, -1));

        lblBNetAmmount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBNetAmmount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBNetAmmount.setText("Net Ammount ");
        Bill.add(lblBNetAmmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 470, -1, -1));

        lblBPayMode.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBPayMode.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBPayMode.setText("Payment Mode");
        Bill.add(lblBPayMode, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 520, -1, -1));

        lblBRecivedAmmount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBRecivedAmmount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBRecivedAmmount.setText("Recived Ammount");
        Bill.add(lblBRecivedAmmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 550, -1, -1));

        lblBBalance.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBBalance.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBBalance.setText("Balance");
        Bill.add(lblBBalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 580, -1, -1));

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Please Verify your Bill Before Leaving The Counter.....");
        Bill.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 610, 550, 20));

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Ammount ");
        Bill.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 400, -1, -1));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("Rs.");
        Bill.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 370, -1, -1));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Rs.");
        Bill.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 340, -1, -1));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Rs.");
        Bill.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 550, -1, -1));

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Rs.");
        Bill.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 470, -1, -1));

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("Rs.");
        Bill.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 580, -1, -1));

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setText("----------------------------------------------------------------------------------------------------------------------------------------");
        Bill.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 550, -1));

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("----------------------------------------------------------------------------------------------------------------------------------------");
        Bill.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 550, -1));

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("---------------------------------------------------------------------------------");
        Bill.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 490, 330, -1));

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("---------------------------------------------------------------------------------");
        Bill.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 500, 330, -1));

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("---------------------------------------------------------------------------------");
        Bill.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 450, 330, -1));

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("Thank you for your trust in Suvasevana Hospital");
        Bill.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 630, 510, 10));

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("Welcome to Suwasewana Hospital Payment Center");
        Bill.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 450, -1));

        jPanel4.add(Bill, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, 550, 650));

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SRC/BC New(1366x768).jpg"))); // NOI18N
        jPanel4.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1366, 710));

        javax.swing.GroupLayout BillPrintLayout = new javax.swing.GroupLayout(BillPrint.getContentPane());
        BillPrint.getContentPane().setLayout(BillPrintLayout);
        BillPrintLayout.setHorizontalGroup(
            BillPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        BillPrintLayout.setVerticalGroup(
            BillPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(51, 0, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Payments");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, -1, -1));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Patient Name");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Patient ID");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Admit Date");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Itemized Charges");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText(" Room Charges");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Services Charges");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 330, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Ammount");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Discounts or Adjustments");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 390, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Net Ammout");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Payment Methode");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 470, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Recived ammount");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 520, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Balance");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 560, -1, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setText("Time Duration(Days)");
        jPanel2.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, -1, -1));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setText("Dates and Duration");
        jPanel2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, -1));

        jTablePayment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "PayID", "PatientName", "PatientID", "AdnitDate", "DisChargeDate", "Days", "RoomCharge", "ServiceCharge", "Ammount", "Discounts", "NetAmmount", "PaymentMethode", "RecivedAnnount", "Balance"
            }
        ));
        jScrollPane1.setViewportView(jTablePayment);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 310, 820, 150));

        cmbDISC.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cmbDISC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NO Discounts", "Financial Assistance", "Sliding Scale Fee", "Prompt Payment Discounts", "Charity Care", "Negotiation or Payment Plans" }));
        cmbDISC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDISCActionPerformed(evt);
            }
        });
        jPanel2.add(cmbDISC, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 390, 140, -1));

        cmbPayMode.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cmbPayMode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cash", "Card" }));
        cmbPayMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPayModeActionPerformed(evt);
            }
        });
        jPanel2.add(cmbPayMode, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 470, 140, -1));

        Balance.setBackground(new java.awt.Color(102, 0, 204));
        Balance.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Balance.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Balance.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Balance.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(Balance, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 560, 140, 20));

        ServiceCharge.setBackground(new java.awt.Color(102, 0, 204));
        ServiceCharge.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ServiceCharge.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ServiceCharge.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ServiceCharge.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(ServiceCharge, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 330, 140, 20));

        RoomCharge.setBackground(new java.awt.Color(102, 0, 204));
        RoomCharge.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        RoomCharge.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RoomCharge.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        RoomCharge.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(RoomCharge, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 300, 140, 20));

        DISCD.setBackground(new java.awt.Color(102, 0, 204));
        DISCD.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        DISCD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DISCD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        DISCD.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(DISCD, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 180, 140, 20));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setText("Discharge Date");
        jPanel2.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, -1, -1));

        PID.setBackground(new java.awt.Color(102, 0, 204));
        PID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        PID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PID.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(PID, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 140, 20));

        AdmitD.setBackground(new java.awt.Color(102, 0, 204));
        AdmitD.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        AdmitD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AdmitD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        AdmitD.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(AdmitD, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 140, 20));

        Days.setBackground(new java.awt.Color(102, 0, 204));
        Days.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Days.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Days.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Days.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(Days, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 140, 20));

        PName.setBackground(new java.awt.Color(102, 0, 204));
        PName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        PName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(PName, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 140, 20));

        txtRicive.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtRicive.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRicive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRiciveActionPerformed(evt);
            }
        });
        txtRicive.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRiciveKeyTyped(evt);
            }
        });
        jPanel2.add(txtRicive, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 520, 140, -1));

        NETAmmount.setBackground(new java.awt.Color(102, 0, 204));
        NETAmmount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        NETAmmount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NETAmmount.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        NETAmmount.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(NETAmmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 430, 140, 20));

        Ammount.setBackground(new java.awt.Color(102, 0, 204));
        Ammount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Ammount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Ammount.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Ammount.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(Ammount, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 360, 140, 20));

        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField2.setText("    Search With ID........");
        jTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField2MouseClicked(evt);
            }
        });
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 40, 240, 40));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setText("Pay");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 510, 240, 40));

        btnPrint.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnPrint.setText("Print bill");
        btnPrint.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnPrint.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        jPanel2.add(btnPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 510, 240, 40));

        jTablePatient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "PatientID", "PatientName", "Phone", "Address", "AdmitDate", "RoomNo"
            }
        ));
        jTablePatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePatientMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTablePatient);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 100, 820, 150));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel24.setText("Payment Details");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 270, -1, -1));

        jButton4.setText(" Ammount");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 240, 140, 20));

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton8.setText("Exit");
        jButton8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 510, 240, 40));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setText("Payment ID");
        jPanel2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText(" Room No");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, -1, -1));

        jButton3.setText("Days Calculate");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, 140, 20));

        RoomNO.setBackground(new java.awt.Color(102, 0, 204));
        RoomNO.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        RoomNO.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RoomNO.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        RoomNO.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(RoomNO, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 270, 140, 20));

        lblPayID.setBackground(new java.awt.Color(102, 0, 204));
        lblPayID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPayID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPayID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblPayID.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(lblPayID, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 140, 20));

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel54.setText("Select Patient Details From This Table");
        jPanel2.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 50, 370, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SRC/UcreationBC.png"))); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1410, 610));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 1330, 610));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SRC/BC.jpeg"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -6, 1380, 790));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 778, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:

        this.dispose();

    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (lblPayID.getText() == null || PName.getText() == null || PID.getText() == null || AdmitD.getText() == null || DISCD.getText() == null || Days.getText() == null || RoomCharge.getText() == null || ServiceCharge.getText() == null || Ammount.getText() == null || cmbDISC.getSelectedItem() == null || txtRicive.getText() == null || Balance.getText() == null) {
            JOptionPane.showMessageDialog(this, "Fill all Informations");
        } else {
        
        Ammount();
        NetAmmount();
        Balance();
        

            int opt = JOptionPane.showConfirmDialog(null, "Are you Want To Pay ?", "Exit", JOptionPane.YES_NO_OPTION);
            if (opt == 0) {

                String paytd = lblPayID.getText();
                String pname = PName.getText();
                String pid = PID.getText();
                String admitd = AdmitD.getText();
                String discd = DISCD.getText();
                String days = Days.getText();
                String roomc = RoomCharge.getText();
                String service = ServiceCharge.getText();
                String ammount = Ammount.getText();
                String discount = (String) cmbDISC.getSelectedItem();
                String netammount = NETAmmount.getText();
                String paymode = (String) cmbPayMode.getSelectedItem();
                String ricive = txtRicive.getText();
                String balance = Balance.getText();

                try {
                    pst = con.prepareStatement("INSERT INTO payment (PayID,PatientName,PatientID,AdnitDate,DisChargeDate,Days,RoomCharge,ServiceCharge,Ammount,Discounts,NetAmmount,PaymentMethode,RecivedAnnount,Balance) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    pst.setString(1, paytd);
                    pst.setString(2, pname);
                    pst.setString(3, pid);
                    pst.setString(4, admitd);
                    pst.setString(5, discd);
                    pst.setString(6, days);
                    pst.setString(7, roomc);
                    pst.setString(8, service);
                    pst.setString(9, ammount);
                    pst.setString(10, discount);
                    pst.setString(11, netammount);
                    pst.setString(12, paymode);
                    pst.setString(13, ricive);
                    pst.setString(14, balance);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(this, " Payment Details successfully ADDED");
                  
                    Payment_table();
                    autoId();
                    txtRicive.requestFocus();
                     BillClear();

                    //JOptionPane.showMessageDialog(this, " Patient successfully ADD");
                } catch (SQLException ex) {
                    Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void txtRiciveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRiciveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRiciveActionPerformed

    private void jTablePatientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePatientMouseClicked
        try {

            // TODO add your handling code here:
            DefaultTableModel d1 = (DefaultTableModel) jTablePatient.getModel();
            int SelectIndex = jTablePatient.getSelectedRow();

            PID.setText(d1.getValueAt(SelectIndex, 0).toString());
            PName.setText(d1.getValueAt(SelectIndex, 1).toString());
            AdmitD.setText(d1.getValueAt(SelectIndex, 4).toString());
            RoomNO.setText(d1.getValueAt(SelectIndex, 5).toString());

            Class.forName("com.mysql.jdbc.Driver");
            Statement s = con.createStatement();
            String rn = RoomNO.getText().toString();
            ResultSet rs = s.executeQuery("SELECT * FROM rooms WHERE Room_ID ='" + rn + "'");
            rs.next();
            String x = rs.getString("RoomCharge");
            RoomCharge.setText(x);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Payments.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Payments.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jTablePatientMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            // TODO add your handling code here:
            DayDuration();

        } catch (ParseException ex) {
            Logger.getLogger(Payments.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        lblBPayID.setText(lblPayID.getText());
        lblBPatientID.setText(PID.getText());
        lblBPatientName.setText(PName.getText());
        lblBAdmitDate.setText(AdmitD.getText());
        lblBDisDate.setText(DISCD.getText());
        lblBDays.setText(Days.getText());
        lblBRoomNo.setText(RoomNO.getText());
        lblBRoomCharge.setText(RoomCharge.getText());
        lblBServiceCharge.setText(ServiceCharge.getText());
        lblBAmmount.setText(Ammount.getText());
        lblBDiscount.setText(cmbDISC.getSelectedItem().toString());
        lblBNetAmmount.setText(NETAmmount.getText());
        lblBPayMode.setText(cmbPayMode.getSelectedItem().toString());
        lblBRecivedAmmount.setText(txtRicive.getText());
        lblBBalance.setText(Balance.getText());
        BillPrint.setVisible(true);


    }//GEN-LAST:event_btnPrintActionPerformed

    private void cmbDISCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDISCActionPerformed
        // TODO add your handling code here:
        NetAmmount();
    }//GEN-LAST:event_cmbDISCActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Ammount();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void cmbPayModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPayModeActionPerformed
        // TODO add your handling code here:
        String dis = cmbPayMode.getSelectedItem().toString();

        switch (dis) {
            case "Card":
                txtRicive.setText(NETAmmount.getText());
                break;
            case "Cash":
                txtRicive.setText("");
                break;

        }
    }//GEN-LAST:event_cmbPayModeActionPerformed

    private void jTextField2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField2MouseClicked
        // TODO add your handling code here:
        jTextField2.setText(null);
    }//GEN-LAST:event_jTextField2MouseClicked

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        // TODO add your handling code here:
        String tf = jTextField2.getText();
        try {
            if (tf.matches("^[0-9]+$")) {
                String query = "SELECT * FROM patient WHERE PatientNo  =" + tf;
                pst = con.prepareStatement(query);
                rs = pst.executeQuery();
                jTablePatient.setModel(DbUtils.resultSetToTableModel(rs));
            } else {
                String query = "SELECT * FROM patient WHERE PatientNo  LIKE '%" + tf + "%'";

                pst = con.prepareStatement(query);
                rs = pst.executeQuery();
                jTablePatient.setModel(DbUtils.resultSetToTableModel(rs));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }


    }//GEN-LAST:event_jTextField2KeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        BillPrint.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Print Data");

        job.setPrintable(new Printable() {
            public int print(Graphics pg, PageFormat pf, int pageNum) {
                pf.setOrientation(PageFormat.LANDSCAPE);
                if (pageNum > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2 = (Graphics2D) pg;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                g2.scale(0.47, 0.47);

                Bill.print(g2);

                return Printable.PAGE_EXISTS;

            }
        });
        boolean ok = job.printDialog();
        if (ok) {
            try {

                job.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtRiciveKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRiciveKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtRiciveKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            //   java.util.logging.Logger.getLogger(employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            //  java.util.logging.Logger.getLogger(employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            //  java.util.logging.Logger.getLogger(employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Payments().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AdmitD;
    private javax.swing.JLabel Ammount;
    private javax.swing.JLabel Balance;
    private javax.swing.JPanel Bill;
    private javax.swing.JFrame BillPrint;
    private javax.swing.JLabel DISCD;
    private javax.swing.JLabel Days;
    private javax.swing.JLabel NETAmmount;
    private javax.swing.JLabel PID;
    private javax.swing.JLabel PName;
    private javax.swing.JLabel RoomCharge;
    private javax.swing.JLabel RoomNO;
    private javax.swing.JLabel ServiceCharge;
    private javax.swing.JButton btnPrint;
    private javax.swing.JComboBox cmbDISC;
    private javax.swing.JComboBox cmbPayMode;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTablePatient;
    private javax.swing.JTable jTablePayment;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lblBAdmitDate;
    private javax.swing.JLabel lblBAmmount;
    private javax.swing.JLabel lblBBalance;
    private javax.swing.JLabel lblBDays;
    private javax.swing.JLabel lblBDisDate;
    private javax.swing.JLabel lblBDiscount;
    private javax.swing.JLabel lblBNetAmmount;
    private javax.swing.JLabel lblBPatientID;
    private javax.swing.JLabel lblBPatientName;
    private javax.swing.JLabel lblBPayID;
    private javax.swing.JLabel lblBPayMode;
    private javax.swing.JLabel lblBRecivedAmmount;
    private javax.swing.JLabel lblBRoomCharge;
    private javax.swing.JLabel lblBRoomNo;
    private javax.swing.JLabel lblBServiceCharge;
    private javax.swing.JLabel lblPayID;
    private javax.swing.JTextField txtRicive;
    // End of variables declaration//GEN-END:variables
}
