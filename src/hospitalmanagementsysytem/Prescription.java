/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsysytem;

import static hospitalmanagementsysytem.Doctor.lblRoom;
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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author navee
 */
public class Prescription extends javax.swing.JFrame {

    /**
     * Creates new form Prescription
     */
    public Prescription() {
        initComponents();
        LoadPatientName();
        LoadDoctorID();
        autoId();
        Connect();
        table_update();

        cmbDoc.setSelectedIndex(-1);
        lblDocNa.setText(null);
        cmbPa.setSelectedIndex(-1);
        lblPID.setText(null);
        lblPAge.setText(null);
        txtSEfect.setText(null);
        txtMed.setText(null);
        txtDoes.setText(null);

        lblPresNo.setText(lblPrNo.getText());

        //BC PICTURE
        ImageIcon myImage1 = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/SRC/BC.jpeg")));
        Image img10 = myImage1.getImage();
        Image img20 = img10.getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon ii = new ImageIcon(img20);
        jLabel1.setIcon(ii);

        //small panel BC PICTURE
        ImageIcon myImage = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/SRC/UcreationBC.png")));
        Image img1 = myImage.getImage();
        Image img2 = img1.getScaledInstance(jLabel17.getWidth(), jLabel17.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon i = new ImageIcon(img2);
        jLabel17.setIcon(i);

    }

    public class Patient {

        String id;
        String name;

        public Patient(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    public class Doctor {

        String id;
        String name;

        public Doctor(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    public void LoadPatientName() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gp_hms", "root", "");
            pst = con.prepareStatement("select * from patient");
            rs = pst.executeQuery();
            cmbPa.removeAllItems();

            while (rs.next()) {
                String id1 = rs.getString(1);

                cmbPa.addItem(new Patient(rs.getString(1), rs.getString(1)));
            }

        } catch (ClassNotFoundException ex) {
            //  Logger.getLogger(channel.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            //   Logger.getLogger(channel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void LoadDoctorID() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gp_hms", "root", "");
            pst = con.prepareStatement("select * from doctor");
            rs = pst.executeQuery();
            cmbDoc.removeAllItems();

            while (rs.next()) {
                String id1 = rs.getString(1);

                cmbDoc.addItem(new Doctor(rs.getString(1), rs.getString(1)));
            }

        } catch (ClassNotFoundException ex) {
            //  Logger.getLogger(channel.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            //   Logger.getLogger(channel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void autoId() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gp_hms", "root", "");
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT MAX(PreNo) FROM prescription");
            rs.next();
            rs.getString("MAX(PreNo)");
            if (rs.getString("MAX(PreNo)") == null) {
                lblPrNo.setText("PR001");
            } else {
                long id = Long.parseLong(rs.getString("MAX(PreNo)").substring(2, rs.getString("MAX(PreNo)").length()));
                id++;
                lblPrNo.setText("PR" + String.format("%03d", id));
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
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    private void table_update() {
        int CC;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gp_hms", "root", "");
            pst = con.prepareStatement("SELECT * FROM prescription");
            ResultSet Rs = pst.executeQuery();
            java.sql.ResultSetMetaData RSMD = Rs.getMetaData();
            CC = RSMD.getColumnCount();
            DefaultTableModel DFT = (DefaultTableModel) jTablePres.getModel();
            DFT.setRowCount(0);

            while (Rs.next()) {
                Vector v2 = new Vector();
                for (int ii = 1; ii <= CC; ii++) {
                    v2.add(Rs.getString("PreNo"));
                    v2.add(Rs.getString("DocID"));
                    v2.add(Rs.getString("PName"));
                    v2.add(Rs.getString("PID"));
                    v2.add(Rs.getString("SEfect"));

                }
                DFT.addRow(v2);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(viewDoctor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(viewDoctor.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnD = new javax.swing.JButton();
        txtDoes = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtSEfect = new javax.swing.JTextArea();
        lblPrNo = new javax.swing.JLabel();
        lblDocNa = new javax.swing.JLabel();
        lblPID = new javax.swing.JLabel();
        lblPAge = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablePres = new javax.swing.JTable();
        jPanelPreview = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        lblPresNo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMed = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        txtMed = new javax.swing.JTextField();
        btnPNOK = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        btnDIDOK = new javax.swing.JButton();
        cmbDoc = new javax.swing.JComboBox();
        cmbPa = new javax.swing.JComboBox();
        jButton8 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Prescription Number");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Name of Medicine");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 140, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Does(mg)");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 96, -1));

        btnD.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnD.setText("delete");
        btnD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDActionPerformed(evt);
            }
        });
        jPanel2.add(btnD, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 300, 90, -1));

        txtDoes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDoesActionPerformed(evt);
            }
        });
        txtDoes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDoesKeyTyped(evt);
            }
        });
        jPanel2.add(txtDoes, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 210, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Doctor Name");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Doctor ID");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Patient Name");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Patient Age");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Patient ID");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Side Effects");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, -1, -1));

        txtSEfect.setColumns(20);
        txtSEfect.setRows(5);
        jScrollPane2.setViewportView(txtSEfect);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 210, -1));

        lblPrNo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel2.add(lblPrNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 200, 20));

        lblDocNa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel2.add(lblDocNa, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 210, 20));

        lblPID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel2.add(lblPID, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 210, 20));

        lblPAge.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel2.add(lblPAge, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 210, 20));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setText("Prescription Preview");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 20, -1, -1));

        jTablePres.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Prescription Number", "Doctor ID", "Patient Name", "Patient ID", "Side Effects"
            }
        ));
        jTablePres.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePresMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTablePres);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, 1000, 170));

        jPanelPreview.setBackground(new java.awt.Color(255, 255, 255));
        jPanelPreview.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Prescription No");

        lblPresNo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jTableMed.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTableMed.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Does(mg)"
            }
        ));
        jScrollPane1.setViewportView(jTableMed);

        javax.swing.GroupLayout jPanelPreviewLayout = new javax.swing.GroupLayout(jPanelPreview);
        jPanelPreview.setLayout(jPanelPreviewLayout);
        jPanelPreviewLayout.setHorizontalGroup(
            jPanelPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPreviewLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblPresNo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPreviewLayout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanelPreviewLayout.setVerticalGroup(
            jPanelPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPreviewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12)
                    .addComponent(lblPresNo, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel2.add(jPanelPreview, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 60, 310, 410));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 60, 100, 30));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setText("Update");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 230, 100, 30));

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton7.setText("add");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 90, -1));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setText("Delete");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 170, 100, 30));

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton5.setText("Clear");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 110, 100, 30));
        jPanel2.add(txtMed, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 210, 30));

        btnPNOK.setText("OK");
        btnPNOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPNOKActionPerformed(evt);
            }
        });
        jPanel2.add(btnPNOK, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 120, -1, -1));

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton6.setText("Exit");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 370, 100, 30));

        btnDIDOK.setText("OK");
        btnDIDOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDIDOKActionPerformed(evt);
            }
        });
        jPanel2.add(btnDIDOK, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, -1, -1));

        cmbDoc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel2.add(cmbDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 150, -1));

        cmbPa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel2.add(cmbPa, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 150, -1));

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton8.setText("Print");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 300, 100, 30));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SRC/UcreationBC.png"))); // NOI18N
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 670));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 1040, 670));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Manage Prescription Details");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 30, 370, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SRC/BC.jpeg"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 1390, 800));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (cmbDoc.getSelectedItem() == null || lblDocNa.getText() == null || cmbPa.getSelectedItem() == null || lblPID.getText() == null || lblPAge.getText() == null) {

            JOptionPane.showMessageDialog(this, "Fill All Information");

        } else {
        
        String prno = lblPrNo.getText();
        String dID = cmbDoc.getSelectedItem().toString();
        String Pname = cmbPa.getSelectedItem().toString();
        String pID = lblPID.getText();
        String SE = txtSEfect.getText();

        try {
            pst = con.prepareStatement("UPDATE  prescription set DocID=?,PName=?,PID=?,SEfect=? where PreNo=?");
            pst.setString(1, dID);
            pst.setString(2, Pname);
            pst.setString(3, pID);
            pst.setString(4, SE);
            pst.setString(5, prno);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, " Prescription Details successfully Updated");

            autoId();
            cmbDoc.setSelectedIndex(-1);
            lblDocNa.setText(null);
            cmbPa.setSelectedIndex(-1);
            lblPID.setText(null);
            lblPAge.setText(null);
            txtSEfect.setText(null);

            table_update();

            jButton2.setEnabled(true);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, " Failed To Update ");
            Logger.getLogger(hospitalmanagementsysytem.Prescription.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        if (cmbDoc.getSelectedItem() == null || lblDocNa.getText() == null || cmbPa.getSelectedItem() == null || lblPID.getText() == null || lblPAge.getText() == null) {

            JOptionPane.showMessageDialog(this, "Fill All Information");

        } else {

            String prno = lblPrNo.getText();
            String dID = cmbDoc.getSelectedItem().toString();
            String Pname = cmbPa.getSelectedItem().toString();
            String pID = lblPID.getText();
            String SE = txtSEfect.getText();

            try {
                pst = con.prepareStatement("INSERT INTO prescription (PreNo,DocID,PName,PID,SEfect) VALUES (?,?,?,?,?)");
                pst.setString(1, prno);
                pst.setString(2, dID);
                pst.setString(3, pID);
                pst.setString(4, Pname);
                pst.setString(5, SE);
                pst.executeUpdate();
                table_update();
                autoId();

                JOptionPane.showMessageDialog(this, " Prescription Information successfully ADDED");
                autoId();
                table_update();
                cmbDoc.setSelectedIndex(-1);
                lblDocNa.setText(null);
                cmbPa.setSelectedIndex(-1);
                lblPID.setText(null);
                lblPAge.setText(null);
                txtSEfect.setText(null);

                table_update();
                autoId();

                //JOptionPane.showMessageDialog(this, " Patient successfully ADD");
            } catch (SQLException ex) {
                Logger.getLogger(Prescription.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("update not work");
            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTablePresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePresMouseClicked
        // TODO add your handling code here:
        LoadDoctorID();
        LoadPatientName();
        cmbDoc.setSelectedIndex(-1);
        cmbPa.setSelectedIndex(-1);
        
        DefaultTableModel d1 = (DefaultTableModel) jTablePres.getModel();
        int SelectIndex = jTablePres.getSelectedRow();

        lblPrNo.setText(d1.getValueAt(SelectIndex, 0).toString());
        cmbDoc.setSelectedItem(d1.getValueAt(SelectIndex, 1).toString());
        cmbPa.setSelectedItem(d1.getValueAt(SelectIndex, 3).toString());
        txtSEfect.setText(d1.getValueAt(SelectIndex, 4).toString());

        jButton2.setEnabled(false);


    }//GEN-LAST:event_jTablePresMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        int opt = JOptionPane.showConfirmDialog(null, "Are you Want To Delete ?", "Delete", JOptionPane.YES_NO_OPTION);
        if (opt == 0) {
            String dno = lblPrNo.getText();

            try {
                pst = con.prepareStatement("DELETE FROM prescription WHERE PreNo=?");

                pst.setString(1, dno);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, " Doctor Information DELETED successfully");

                table_update();

                jButton2.setEnabled(true);

            } catch (SQLException ex) {
                Logger.getLogger(Prescription.class.getName()).log(Level.SEVERE, null, ex);
            }
            table_update();
            cmbDoc.setSelectedIndex(-1);
            lblDocNa.setText(null);
            cmbPa.setSelectedIndex(-1);
            lblPID.setText(null);
            lblPAge.setText(null);
            txtSEfect.setText(null);
        }
        table_update();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnDIDOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDIDOKActionPerformed

        try {
            pst = con.prepareStatement("select DoctorName from doctor where DoctorNo=? ");
            String id = cmbDoc.getSelectedItem().toString();
            pst.setString(1, id);
            ResultSet rs1 = pst.executeQuery();
            if (rs1.next() == false) {
                JOptionPane.showMessageDialog(this, "Sorry Record Not Found");
                lblDocNa.setText("");

            } else {

                lblDocNa.setText(rs1.getString("DoctorName"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(Prescription.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDIDOKActionPerformed

    private void btnPNOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPNOKActionPerformed
        // TODO add your handling code here:
        try {
            pst = con.prepareStatement("select Name,Age from patient where PatientNo =? ");
            String id = cmbPa.getSelectedItem().toString();
            pst.setString(1, id);
            ResultSet rs1 = pst.executeQuery();
            if (rs1.next() == false) {
                JOptionPane.showMessageDialog(this, "Sorry Record Not Found");
                lblDocNa.setText("");

            } else {

                lblPID.setText(rs1.getString("Name"));
                lblPAge.setText(rs1.getString("Age"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(Prescription.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPNOKActionPerformed

    private void btnDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTableMed.getModel();

        if (jTableMed.getSelectedRowCount() == 1) {
            model.removeRow(jTableMed.getSelectedRow());
        } else {
            if (jTableMed.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "This table Is Empty");
            } else {
                JOptionPane.showMessageDialog(this, "Select Single Raw For DELETE");
            }
        }


    }//GEN-LAST:event_btnDActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
       
        if(txtMed.getText()== "" || txtDoes.getText()==""){
            JOptionPane.showMessageDialog(this, "Enter Mediciene Name and Does");
        }else{
            DefaultTableModel model = (DefaultTableModel) jTableMed.getModel();
            model.addRow(new Object[]{txtMed.getText(), txtDoes.getText()});
        }
        txtMed.setText(null);
        txtDoes.setText(null);
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
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

                jPanelPreview.print(g2);

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
        cmbDoc.setSelectedIndex(-1);
        lblDocNa.setText(null);
        cmbPa.setSelectedIndex(-1);
        lblPID.setText(null);
        lblPAge.setText(null);
        txtSEfect.setText(null);


    }//GEN-LAST:event_jButton8ActionPerformed

    private void txtDoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDoesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDoesActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        cmbDoc.setSelectedIndex(-1);
        lblDocNa.setText(null);
        cmbPa.setSelectedIndex(-1);
        lblPID.setText(null);
        lblPAge.setText(null);
        txtSEfect.setText(null);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtDoesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDoesKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDoesKeyTyped

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
            java.util.logging.Logger.getLogger(Prescription.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Prescription.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Prescription.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Prescription.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Prescription().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnD;
    private javax.swing.JButton btnDIDOK;
    private javax.swing.JButton btnPNOK;
    private javax.swing.JComboBox cmbDoc;
    private javax.swing.JComboBox cmbPa;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelPreview;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableMed;
    private javax.swing.JTable jTablePres;
    private javax.swing.JLabel lblDocNa;
    private javax.swing.JLabel lblPAge;
    private javax.swing.JLabel lblPID;
    private javax.swing.JLabel lblPrNo;
    private javax.swing.JLabel lblPresNo;
    private javax.swing.JTextField txtDoes;
    private javax.swing.JTextField txtMed;
    private javax.swing.JTextArea txtSEfect;
    // End of variables declaration//GEN-END:variables
}
