/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hospitalmanagementsysytem;

//import com.mysql.cj.jdbc.result.ResultSetMetaData;
//import java.beans.Statement;
import static hospitalmanagementsysytem.Doctor.lblRoom;
import static hospitalmanagementsysytem.channel.lblRoom;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author navee
 */
public class Patient extends javax.swing.JFrame {

    /**
     * Creates new form Patient
     */
    public Patient() {
        initComponents();
        dt();
        times();
        Connect();
        AutoID();
        patient_table();

//        //small panel PICTURE
//        ImageIcon myImage = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/SRC/SmallPanel.jpg")));
//        Image img1 = myImage.getImage();
//        Image img2 = img1.getScaledInstance(jLabel9.getWidth(), jLabel9.getHeight(), Image.SCALE_SMOOTH);
//        ImageIcon i = new ImageIcon(img2);
//        jLabel9.setIcon(i);
        //BC PICTURE
        ImageIcon myImage1 = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/SRC/BC.jpeg")));
        Image img10 = myImage1.getImage();
        Image img20 = img10.getScaledInstance(lblBC.getWidth(), lblBC.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon ii = new ImageIcon(img20);
        lblBC.setIcon(ii);

    }

    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    public void Connect() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gp_hms", "root", "");
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Patient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Patient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public void dt() {

        java.util.Date d = new java.util.Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");

        String dd = sdf.format(d);
        lblDate.setText(dd);

    }

    public void times() {

        Timer t = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                java.util.Date dt = new java.util.Date();
                SimpleDateFormat st = new SimpleDateFormat("hh:mm:ss a");

                String tt = st.format(dt);
                lbltime.setText(tt);

            }
        });

        t.start();

    }

    public final void AutoID() {
        try {
            java.sql.Statement s = con.createStatement();
            rs = s.executeQuery("SELECT MAX(PatientNo) FROM patient");
            rs.next();
            String maxPatientNo = rs.getString("MAX(PatientNo)");
            if (maxPatientNo == null) {
                lblPNumber.setText("PS001");
            } else {
                int id = Integer.parseInt(maxPatientNo.substring(2));
                id++;
                String newPatientNo = String.format("PS%03d", id);
                lblPNumber.setText(newPatientNo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void patient_table() {
        try {
            pst = con.prepareStatement("SELECT * FROM patient");
            rs = pst.executeQuery();
            java.sql.ResultSetMetaData Rsm = rs.getMetaData();
            int c;
            c = Rsm.getColumnCount();

            DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();

                for (int i = 1; i <= c; i++) {
                    v2.add(rs.getString("PatientNo"));
                    v2.add(rs.getString("PRN"));
                    v2.add(rs.getString("Name"));
                    v2.add(rs.getString("Age"));
                    v2.add(rs.getString("Gender"));
                    v2.add(rs.getString("Phone"));
                    v2.add(rs.getString("AdmitDate"));
                    v2.add(rs.getString("Address"));
                    v2.add(rs.getString("MedicalHis"));

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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblPNumber = new javax.swing.JLabel();
        txtPName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtGender = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtMedicalHistory = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        txtPAge = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        lblRoom = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        lbltime = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblBC = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(102, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(11, 174, 197));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Patient Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(255, 255, 0))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Patient No");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Patient Name");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Phone");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Room Number");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        lblPNumber.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jPanel2.add(lblPNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 130, 30));

        txtPName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel2.add(txtPName, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 288, 30));

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        txtAddress.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jScrollPane1.setViewportView(txtAddress);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 320, 288, 110));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Age");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Gender");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        txtGender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female" }));
        txtGender.setSelectedIndex(-1);
        txtGender.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel2.add(txtGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 143, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Medical History");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, -1, -1));

        txtMedicalHistory.setColumns(20);
        txtMedicalHistory.setRows(5);
        txtMedicalHistory.setText("Existing medical conditions \n(e.g., diabetes, hypertension)");
        txtMedicalHistory.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txtMedicalHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMedicalHistoryMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(txtMedicalHistory);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 450, 288, 120));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Admit Date");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        txtPAge.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txtPAge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPAgeKeyTyped(evt);
            }
        });
        jPanel2.add(txtPAge, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 288, 30));

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setText("Room Details");
        jButton5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, 130, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Address");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, -1));

        txtPhone.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txtPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhoneKeyTyped(evt);
            }
        });
        jPanel2.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, 288, 30));

        lblRoom.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRoom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRoom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(lblRoom, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 120, 30));

        jDateChooser1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, 290, 30));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SRC/SmallPanel.jpg"))); // NOI18N
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 490, 590));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Update");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 700, 92, 46));

        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 700, 92, 46));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setText("Exit");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 700, 92, 46));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setText("Delete");
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 700, 92, 46));

        jTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Patient No", "Room Number", "Patient Name", "Age", "Gender", "Phone", "AdmitDate", "Address", "Medical History"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 220, 840, 440));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Patient Details Management");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 50, 650, -1));

        txtSearch.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        txtSearch.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtSearch.setText("Search With Patient ID.......");
        txtSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchMouseClicked(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 170, 240, 37));

        lbltime.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbltime.setForeground(new java.awt.Color(255, 255, 255));
        lbltime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbltime.setText("jLabel11");
        jPanel1.add(lbltime, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 200, 30));

        lblDate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setText("jLabel11");
        jPanel1.add(lblDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 200, 30));

        lblBC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SRC/BC.jpeg"))); // NOI18N
        jPanel1.add(lblBC, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, -1));

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
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        if (lblPNumber.getText() == null || lblRoom.getText() == null || txtPName.getText() == null || jDateChooser1.getDate() == null || txtPAge.getText() == null || txtPhone.getText() == null || txtGender.getSelectedItem() == null || txtPhone.getText() == null || txtAddress.getText() == null) {
            JOptionPane.showMessageDialog(this, "Fill All Informations");
        } else {

            String pno = lblPNumber.getText();
            String prn = lblRoom.getText();
            String pname = txtPName.getText();
            String page = txtPAge.getText();
            String pgender = (String) txtGender.getSelectedItem();
            String phone = txtPhone.getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-MM-dd");
            String date = Date_Format.format(jDateChooser1.getDate());
            String address = txtAddress.getText();
            String MedicalHis = txtMedicalHistory.getText();

            try {
                pst = con.prepareStatement("INSERT INTO patient (PatientNo,PRN,Name,Age,Gender,Phone,AdmitDate,Address,MedicalHis) VALUES (?,?,?,?,?,?,?,?,?)");
                pst.setString(1, pno);
                pst.setString(2, prn);
                pst.setString(3, pname);
                pst.setString(4, page);
                pst.setString(5, pgender);
                pst.setString(6, phone);
                pst.setString(7, date);
                pst.setString(8, address);
                pst.setString(9, MedicalHis);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, " Patient successfully ADDED");

                AutoID();
                lblRoom.setText(null);
                txtPAge.setText(null);
                txtGender.setSelectedIndex(-1);
                jDateChooser1.setDate(null);
                txtPName.setText("");
                txtPhone.setText("");
                txtAddress.setText("");
                txtPName.requestFocus();
                patient_table();

                //JOptionPane.showMessageDialog(this, " Patient successfully ADD");
            } catch (SQLException ex) {
                Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                String x = "Unavaliable";
                String rno = lblRoom.getText();
                pst = con.prepareStatement("UPDATE rooms set Statues=? WHERE Room_ID =?");
                pst.setString(1, x);
                pst.setString(2, rno);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, " Room Statues UPDATED successfully ");

                patient_table();
                patient_table();

                jButton5.setEnabled(true);

            } catch (SQLException ex) {
                Logger.getLogger(hospitalmanagementsysytem.Doctor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        DefaultTableModel d1 = (DefaultTableModel) jTable1.getModel();
        int SelectIndex = jTable1.getSelectedRow();

        lblPNumber.setText(d1.getValueAt(SelectIndex, 0).toString());
        lblRoom.setText(d1.getValueAt(SelectIndex, 1).toString());
        txtPName.setText(d1.getValueAt(SelectIndex, 2).toString());
        txtPAge.setText(d1.getValueAt(SelectIndex, 3).toString());
        txtGender.setSelectedItem(d1.getValueAt(SelectIndex, 4).toString());
        txtPhone.setText(d1.getValueAt(SelectIndex, 5).toString());
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String) d1.getValueAt(SelectIndex, 6));
            jDateChooser1.setDate(date);
        } catch (ParseException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtAddress.setText(d1.getValueAt(SelectIndex, 7).toString());
        txtMedicalHistory.setText(d1.getValueAt(SelectIndex, 8).toString());

        btnAdd.setEnabled(false);


    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        if (lblPNumber.getText() == null || lblRoom.getText() == null || txtPName.getText() == null || jDateChooser1.getDate() == null || txtPAge.getText() == null || txtPhone.getText() == null || txtGender.getSelectedItem() == null || txtPhone.getText() == null || txtAddress.getText() == null) {
            JOptionPane.showMessageDialog(this, "Fill All Informations");
        } else {

            String pno = lblPNumber.getText();
            String prn = lblRoom.getText();
            String pname = txtPName.getText();
            String page = txtPAge.getText();
            String pgender = (String) txtGender.getSelectedItem();
            String phone = txtPhone.getText();
            String address = txtAddress.getText();
            String MedicalHis = txtMedicalHistory.getText();

            try {
                pst = con.prepareStatement("UPDATE patient set PRN=?, Name=?,Age=?,Gender=?,Phone=?,Address=?,MedicalHis=? WHERE PatientNo=?");
                pst.setString(1, prn);
                pst.setString(2, pname);
                pst.setString(3, page);
                pst.setString(4, pgender);
                pst.setString(5, phone);
                pst.setString(6, address);
                pst.setString(7, MedicalHis);
                pst.setString(8, pno);

                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, " Patient successfully Updated");

                AutoID();
                lblRoom.setText(null);
                txtPAge.setText(null);
                txtGender.setSelectedIndex(-1);
                jDateChooser1.setDate(null);
                txtPName.setText("");
                txtPhone.setText("");
                txtAddress.setText("");
                txtPName.requestFocus();
                patient_table();

                btnAdd.setEnabled(true);

                //JOptionPane.showMessageDialog(this, " Patient successfully ADD");
            } catch (SQLException ex) {
                Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        int opt = JOptionPane.showConfirmDialog(null, "Are you Want To Delete ?", "Delete", JOptionPane.YES_NO_OPTION);
        if (opt == 0) {
            String pno = lblPNumber.getText();

            try {
                pst = con.prepareStatement("DELETE FROM patient WHERE PatientNo=?");

                pst.setString(1, pno);

                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, " Patient successfully Deleted");

                AutoID();
                lblRoom.setText(null);
                txtPAge.setText(null);
                txtGender.setSelectedIndex(-1);
                jDateChooser1.setDate(null);
                txtPName.setText("");
                txtPhone.setText("");
                txtAddress.setText("");
                txtPName.requestFocus();
                patient_table();

                btnAdd.setEnabled(true);

                //JOptionPane.showMessageDialog(this, " Patient successfully ADD");
            } catch (SQLException ex) {
                Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                String x = "Avaliable";
                String rno = lblRoom.getText();
                pst = con.prepareStatement("UPDATE rooms set Statues=? WHERE Room_ID =?");
                pst.setString(1, x);
                pst.setString(2, rno);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, " Room Statues UPDATED successfully ");

                patient_table();
                jButton5.setEnabled(true);

            } catch (SQLException ex) {
                Logger.getLogger(hospitalmanagementsysytem.Patient.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtPAgeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPAgeKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPAgeKeyTyped

    private void txtPhoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPhoneKeyTyped

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        ViewAvaliableRoomsPatient n = new ViewAvaliableRoomsPatient();
        n.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtMedicalHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMedicalHistoryMouseClicked
        // TODO add your handling code here:
        //txtMedicalHistory.setText(null);
    }//GEN-LAST:event_txtMedicalHistoryMouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        String tf = txtSearch.getText();
        try {
            if (tf.matches("^[0-9]+$")) {
                String query = "SELECT * FROM patient WHERE PatientNo  =" + tf;
                pst = con.prepareStatement(query);
                rs = pst.executeQuery();
                jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            } else {
                String query = "SELECT * FROM patient WHERE PatientNo  LIKE '%" + tf + "%'";

                pst = con.prepareStatement(query);

                rs = pst.executeQuery();
                jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked
        // TODO add your handling code here:
        txtSearch.setText(null);
    }//GEN-LAST:event_txtSearchMouseClicked

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
            java.util.logging.Logger.getLogger(Patient.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Patient.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Patient.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Patient.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Patient().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblBC;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblPNumber;
    public static javax.swing.JLabel lblRoom;
    private javax.swing.JLabel lbltime;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JComboBox txtGender;
    private javax.swing.JTextArea txtMedicalHistory;
    private javax.swing.JTextField txtPAge;
    private javax.swing.JTextField txtPName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

}
