/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hospitalmanagementsysytem;

//import com.mysql.cj.jdbc.result.ResultSetMetaData;
//import java.awt.Dimension;
import static hospitalmanagementsysytem.channel.lblRoom;
import java.awt.Image;
import java.awt.Toolkit;
//import java.beans.Statement;
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
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author navee
 */
public class Doctor extends javax.swing.JFrame {

    /**
     * Creates new form Patient
     */
    public Doctor() {

        initComponents();

        table_update();
        Connect();
        AutoID();
        table_update();
    }

    int id;
    String ustype;
    int newid;

    public Doctor(int id, String ustype) {
        initComponents();

        this.id = id;
        this.ustype = ustype;

        newid = id;

        //JOptionPane.showMessageDialog(this, newid);
        Connect();
        table_update();
        AutoID();
        table_update();

        //BC PICTURE
        ImageIcon myImage1 = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/SRC/BC.jpeg")));
        Image img10 = myImage1.getImage();
        Image img20 = img10.getScaledInstance(jLabel13.getWidth(), jLabel13.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon ii = new ImageIcon(img20);
        jLabel13.setIcon(ii);

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

    public final void AutoID() {
        try {
            java.sql.Statement s = con.createStatement();
            rs = s.executeQuery("SELECT MAX(DoctorNo) FROM doctor");
            rs.next();
            String maxPatientNo = rs.getString("MAX(DoctorNo)");
            if (maxPatientNo == null) {
                lblDNumber.setText("DC001");
            } else {
                int id = Integer.parseInt(maxPatientNo.substring(2));
                id++;
                String newPatientNo = String.format("DC%03d", id);
                lblDNumber.setText(newPatientNo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void table_update() {
        int CC;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gp_hms", "root", "");
            pst = con.prepareStatement("SELECT * FROM doctor");
            ResultSet Rs = pst.executeQuery();
            java.sql.ResultSetMetaData RSMD = Rs.getMetaData();
            CC = RSMD.getColumnCount();
            DefaultTableModel DFT = (DefaultTableModel) jTable1.getModel();
            DFT.setRowCount(0);

            while (Rs.next()) {
                Vector v2 = new Vector();
                for (int ii = 1; ii <= CC; ii++) {
                    v2.add(Rs.getString("DoctorNo"));
                    v2.add(Rs.getString("DoctorName"));
                    v2.add(Rs.getString("DoctorGender"));
                    v2.add(Rs.getString("DoctorDOB"));
                    v2.add(Rs.getString("DoctorAddress"));
                    v2.add(Rs.getInt("Phone"));
                    v2.add(Rs.getString("Specialization"));
                    v2.add(Rs.getString("Qualifications"));
                    v2.add(Rs.getString("Experience"));
                    v2.add(Rs.getInt("ChannelFee"));
                    v2.add(Rs.getString("RoomNo"));

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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblDNumber = new javax.swing.JLabel();
        txtDName = new javax.swing.JTextField();
        txtChannelFee = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtQualifi = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        txtSpecial = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDAddress = new javax.swing.JTextArea();
        txtExp = new javax.swing.JTextField();
        txtDGender = new javax.swing.JComboBox();
        lblRoom = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        BtnExit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(51, 0, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(102, 0, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Doctor No");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Doctor Name");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Specialization");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Qualifications");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, -1, -1));

        lblDNumber.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblDNumber.setText("jLabel5");
        jPanel2.add(lblDNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 64, -1));

        txtDName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel2.add(txtDName, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 249, -1));

        txtChannelFee.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtChannelFee.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtChannelFeeKeyTyped(evt);
            }
        });
        jPanel2.add(txtChannelFee, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 450, 249, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Channel fee");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, -1, -1));

        txtQualifi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtQualifi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQualifiActionPerformed(evt);
            }
        });
        jPanel2.add(txtQualifi, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 380, 249, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Phone");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Room No");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        txtPhone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhoneKeyTyped(evt);
            }
        });
        jPanel2.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 249, -1));

        txtSpecial.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSpecial.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cardiologist", "Dermatologist", "Neurologist", "Orthopedicsurgeon", "Pediatrician", "Pulmonologist" }));
        jPanel2.add(txtSpecial, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 340, 249, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Gender");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Date of Birth");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Address");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Experience");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, -1, -1));

        txtDAddress.setColumns(20);
        txtDAddress.setRows(5);
        jScrollPane1.setViewportView(txtDAddress);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 249, 84));

        txtExp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExpActionPerformed(evt);
            }
        });
        jPanel2.add(txtExp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 410, 249, -1));

        txtDGender.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDGender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female" }));
        jPanel2.add(txtDGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 249, -1));

        lblRoom.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRoom.setText("jLabel15");
        jPanel2.add(lblRoom, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 100, 20));

        jDateChooser1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel2.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 249, -1));

        jButton1.setText("Room Details");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 130, 30));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SRC/SmallPanel.jpg"))); // NOI18N
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 430, 540));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, 500));

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 690, 78, 40));

        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 690, 79, 40));

        BtnExit.setText("Exit");
        BtnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnExitActionPerformed(evt);
            }
        });
        jPanel1.add(BtnExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 690, 78, 40));

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 690, 91, 40));

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Doctor No", "Doctor Name", "Gender", "DOB", "Address", "Phone", "Specialization", "Qualification", "Experience", "Channel Fee", "Room No"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 200, 880, 450));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Doctor Details Management");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 650, -1));

        txtSearch.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSearch.setText("Search With Doctor ID......");
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
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 150, 234, 37));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SRC/BC New(1366x768).jpg"))); // NOI18N
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (lblDNumber.getText() == null || txtDName.getText() == null || txtDGender.getSelectedItem() == null || jDateChooser1.getDate() == null || txtDAddress.getText() == null || txtPhone.getText() == null || txtSpecial.getSelectedItem() == null || txtQualifi.getText() == null || txtExp.getText() == null || txtChannelFee.getText() == null || lblRoom.getText() == null) {
            JOptionPane.showMessageDialog(this, "Fill All Informations");
        } else {
        
        String dno = lblDNumber.getText();
        String dname = txtDName.getText();
        String dgender = (String) txtDGender.getSelectedItem();
        SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-MM-dd");
        String date = Date_Format.format(jDateChooser1.getDate());
        //String dob=jDateChooser1.getDateFormatString();
        String dAdd = txtDAddress.getText();
        String phone = txtPhone.getText();
        String special = (String) txtSpecial.getSelectedItem();
        String qualifi = txtQualifi.getText();
        String exp = txtExp.getText();
        String Cfee = txtChannelFee.getText();
        String room = lblRoom.getText();

        try {
            pst = con.prepareStatement("INSERT INTO doctor (DoctorNo,DoctorName,DoctorGender,DoctorDOB,DoctorAddress,Phone,Specialization,Qualifications,Experience,ChannelFee,RoomNO,Log_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, dno);
            pst.setString(2, dname);
            pst.setString(3, dgender);
            pst.setString(4, date);
            pst.setString(5, dAdd);
            pst.setString(6, phone);
            pst.setString(7, special);
            pst.setString(8, qualifi);
            pst.setString(9, exp);
            pst.setString(10, Cfee);
            pst.setString(11, room);
            pst.setInt(12, newid);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, " Doctor Information successfully ADDED");
            
            
            try {
                String x = "Unavaliable";
                String rno = lblRoom.getText();
                pst = con.prepareStatement("UPDATE rooms set Statues=? WHERE Room_ID =?");
                pst.setString(1, x);
                pst.setString(2, rno);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, " Room Statues UPDATED successfully ");

                table_update();

                table_update();

                jButton2.setEnabled(true);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Fill all Data");
                Logger.getLogger(hospitalmanagementsysytem.Doctor.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            
            

            AutoID();
            jDateChooser1.setDate(null);
            txtDAddress.setText(null);
            txtDName.setText("");
            txtSpecial.setSelectedIndex(-1);
            txtDGender.setSelectedIndex(-1);
            txtQualifi.setText("");
            txtExp.setText("");
            txtChannelFee.setText("");
            txtPhone.setText("");
            lblRoom.setText("");
            txtDName.requestFocus();
            table_update();

        } catch (SQLException ex) {
            Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("update not work");
        }
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        DefaultTableModel d1 = (DefaultTableModel) jTable1.getModel();
        int SelectIndex = jTable1.getSelectedRow();

        lblDNumber.setText(d1.getValueAt(SelectIndex, 0).toString());
        txtDName.setText(d1.getValueAt(SelectIndex, 1).toString());
        txtDGender.setSelectedItem(d1.getValueAt(SelectIndex, 2).toString());
        //jDateChooser1.setDate((Date)(d1.getValueAt(SelectIndex, 3)));
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String) d1.getValueAt(SelectIndex, 3));
            jDateChooser1.setDate(date);
        } catch (ParseException ex) {
            Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtDAddress.setText(d1.getValueAt(SelectIndex, 4).toString());
        txtPhone.setText(d1.getValueAt(SelectIndex, 5).toString());
        txtSpecial.setSelectedItem(d1.getValueAt(SelectIndex, 6).toString());
        txtQualifi.setText(d1.getValueAt(SelectIndex, 7).toString());
        txtExp.setText(d1.getValueAt(SelectIndex, 8).toString());
        txtChannelFee.setText(d1.getValueAt(SelectIndex, 9).toString());
        lblRoom.setText(d1.getValueAt(SelectIndex, 10).toString());

        jButton2.setEnabled(false);


    }//GEN-LAST:event_jTable1MouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if (lblDNumber.getText() == null || txtDName.getText() == null || txtDGender.getSelectedItem() == null || jDateChooser1.getDate() == null || txtDAddress.getText() == null || txtPhone.getText() == null || txtSpecial.getSelectedItem() == null || txtQualifi.getText() == null || txtExp.getText() == null || txtChannelFee.getText() == null || lblRoom.getText() == null) {
            JOptionPane.showMessageDialog(this, "Fill All Informations");
        } else {
        
        String dno = lblDNumber.getText();
        String dname = txtDName.getText();
        String dgender = (String) txtDGender.getSelectedItem();
        SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-MM-dd");
        String date = Date_Format.format(jDateChooser1.getDate());
        //String dob=jDateChooser1.getDateFormatString();

        String dAdd = txtDAddress.getText();
        String phone = txtPhone.getText();
        String special = (String) txtSpecial.getSelectedItem();
        String qualifi = txtQualifi.getText();
        String exp = txtExp.getText();
        String Cfee = txtChannelFee.getText();
        String room = lblRoom.getText().toString();

        try {
            pst = con.prepareStatement("UPDATE doctor set DoctorName=?,DoctorGender=?,DoctorDOB=?,DoctorAddress=?,Phone=?,Specialization=?,Qualifications=?,Experience=?,ChannelFee=?,RoomNO=? WHERE DoctorNo=?");
            pst.setString(1, dname);
            pst.setString(2, dgender);
            pst.setString(3, date);
            pst.setString(4, dAdd);
            pst.setString(5, phone);
            pst.setString(6, special);
            pst.setString(7, qualifi);
            pst.setString(8, exp);
            pst.setString(9, Cfee);
            pst.setString(10, room);
            pst.setString(11, dno);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, " Doctor Information UPDATED successfully ");
            
            try {
                String x = "Unavaliable";
                String rno = lblRoom.getText();
                pst = con.prepareStatement("UPDATE rooms set Statues=? WHERE Room_ID =?");
                pst.setString(1, x);
                pst.setString(2, rno);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, " Room Statues UPDATED successfully ");

                table_update();

                table_update();

                

            } catch (SQLException ex) {
                Logger.getLogger(hospitalmanagementsysytem.Doctor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            

            AutoID();
            jDateChooser1.setDate(null);
            txtDAddress.setText(null);
            txtDName.setText("");
            txtSpecial.setSelectedIndex(-1);
            txtDGender.setSelectedIndex(-1);
            txtQualifi.setText("");
            txtExp.setText("");
            txtChannelFee.setText("");
            txtPhone.setText("");
            lblRoom.setText("");
            txtDName.requestFocus();
            table_update();

            jButton2.setEnabled(true);

        } catch (SQLException ex) {
            Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int opt = JOptionPane.showConfirmDialog(null, "Are you Want To Delete ?", "Delete", JOptionPane.YES_NO_OPTION);
        if (opt == 0) {
            String dno = lblDNumber.getText();

            try {
                pst = con.prepareStatement("DELETE FROM doctor WHERE DoctorNo=?");

                pst.setString(1, dno);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, " Doctor Information DELETED successfully");
                
                try {
                String x = "Avaliable";
                String rno = lblRoom.getText();
                pst = con.prepareStatement("UPDATE rooms set Statues=? WHERE Room_ID =?");
                pst.setString(1, x);
                pst.setString(2, rno);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, " Room Statues UPDATED successfully ");


            } catch (SQLException ex) {
                Logger.getLogger(hospitalmanagementsysytem.Doctor.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                

            AutoID();
            jDateChooser1.setDate(null);
            txtDAddress.setText(null);
            txtDName.setText("");
            txtSpecial.setSelectedIndex(-1);
            txtDGender.setSelectedIndex(-1);
            txtQualifi.setText("");
            txtExp.setText("");
            txtChannelFee.setText("");
            txtPhone.setText("");
            lblRoom.setText("");
            txtDName.requestFocus();
            table_update();

                jButton2.setEnabled(true);

            } catch (SQLException ex) {
                Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void BtnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnExitActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_BtnExitActionPerformed

    private void txtQualifiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQualifiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQualifiActionPerformed

    private void txtExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExpActionPerformed

    private void txtPhoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
    }//GEN-LAST:event_txtPhoneKeyTyped
    }
    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked
        // TODO add your handling code here:
        txtSearch.setText("");

    }//GEN-LAST:event_txtSearchMouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        String tf = txtSearch.getText();
        try {
            if (tf.matches("^[0-9]+$")) {
                String query = "SELECT * FROM doctor WHERE id =" + tf;
                pst = con.prepareStatement(query);
                rs = pst.executeQuery();
                jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            } else {
                String query = "SELECT * FROM doctor WHERE DoctorName LIKE '%" + tf + "%'";
                String query2 = "SELECT * FROM doctor WHERE DoctorNo LIKE '%" + tf + "%'";
                pst = con.prepareStatement(query);
                pst = con.prepareStatement(query2);
                rs = pst.executeQuery();
                jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ViewAvaliableRoomsDoctor n = new ViewAvaliableRoomsDoctor();
        n.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtChannelFeeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtChannelFeeKeyTyped
        // TODO add your handling code here:
                char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
    } 
    }//GEN-LAST:event_txtChannelFeeKeyTyped

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
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Doctor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnExit;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblDNumber;
    public static javax.swing.JLabel lblRoom;
    private javax.swing.JTextField txtChannelFee;
    private javax.swing.JTextArea txtDAddress;
    private javax.swing.JComboBox txtDGender;
    private javax.swing.JTextField txtDName;
    private javax.swing.JTextField txtExp;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtQualifi;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JComboBox txtSpecial;
    // End of variables declaration//GEN-END:variables

}
