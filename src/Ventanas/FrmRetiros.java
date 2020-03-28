/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ventanas;
import Clases.*;import com.toedter.calendar.*;import java.awt.event.*;
import java.beans.*;import java.lang.reflect.*;import java.util.*;import javax.swing.*;

/*********************** @author User  MASZ*************************/
public class FrmRetiros extends javax.swing.JInternalFrame {
    /****************************ATRIBUTOS***********************************/
    Controlador control=new Controlador();Mimodelo modelo=new Mimodelo();String ctr="";   
    /****************************ATRIBUTOS***********************************/
    public FrmRetiros() {
        initComponents();setTitle("Los Retiros de  la Cuenta de la Empresa");
     this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     T_DepositosRetiros.setModel(modelo);modelo.setColumnIdentifiers(new String[] 
     {"ID","Descripción","Fecha","Monto","Cuenta","Nro.Baucher","Responsable","Usuario"});
     control.hideTableColumn(T_DepositosRetiros, 0);
     control.setWidthTableColumn(T_DepositosRetiros, 180, 4);PonerFechas();     
     control.setWidthTableColumn(T_DepositosRetiros, 280, 1,6);PonerFechas();     
     fdesde.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {       
       if("date".equals(evt.getPropertyName())){
        JDateChooser isse=(JDateChooser)evt.getSource();boolean isdatasele=false;
        try {
         Field dateselfi=JDateChooser.class.getDeclaredField("dateSelect");
         dateselfi.setAccessible(true);isdatasele=dateselfi.getBoolean(isse);
        }
        catch (Exception ignoreOrNot) {                                      
         Mostrar();   
        }
       }
      }
     });
     fhasta.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {       
       if("date".equals(evt.getPropertyName())){
        JDateChooser isse=(JDateChooser)evt.getSource();boolean isdatasele=false;
        try {
         Field dateselfi=JDateChooser.class.getDeclaredField("dateSelect");
         dateselfi.setAccessible(true);isdatasele=dateselfi.getBoolean(isse);
        }
        catch (Exception ignoreOrNot) {                 
         Mostrar();   
        }
       }
      }
     });
     control.bandera=false;control.LlenarCombo(jComboBox2, "select * from cuentaempresa",2);
     lbRetiros.setText("0.00");Mostrar();
     jComboBox2.addActionListener(new ActionListener(){
       @Override
       public void actionPerformed(ActionEvent e) {                        
         Mostrar();txBuscar.grabFocus();
       }       
      });           
     control.bandera=true; 
    }
    public void PonerFechas(){
      fdesde.setDate(new Date());fhasta.setDate(new Date());
    }
    public void Mostrar(){      
     if(control.bandera)
       ctr=jComboBox2.getSelectedItem().toString();     
     control.Sql="SELECT r.idRetiroCuenta ID,r.mtvo Motivo,date_format(r.fecret,'%d-%m-%Y') "   
      + "Fecha,r.mont Monto,c.numcta Cuenta,r.nrobaucher NroBaucher,r.respble Responsable,u.nomusr" +
      " FROM cuentaempresa c inner join RetiroCuenta r on c.idctaemprea=r.idctaemprea " +
      "inner join sede s on s.idsede=r.idsede inner join usuario u on u.idusuario=r.idusuario "+
      "where (r.fecret between '"+control.Formato_Amd(fdesde.getDate())+"' and '"+ 
      control.Formato_Amd(fhasta.getDate())+"') and (r.mtvo like '%"+txBuscar.getText()
      +"%' or r.nrobaucher like '%"+txBuscar.getText()+"%' or r.respble like '%"+txBuscar.getText()+"%') "
      + "and (r.idsede='"+InfoGeneral.idSede+"') and c.numcta='"+ctr+"'";
     control.LlenarJTabla(modelo,control.Sql,8); System.out.println(control.Sql);
      
      control.Sql="select if(sum(r.mont) is null,'0.00',sum(r.mont)) from cuentaempresa c inner join retirocuenta r on c.idctaemprea="
     + "r.idctaemprea inner join sede s on s.idsede=r.idsede inner join usuario u on u.idusuario=r.idusuario"
     + " where (r.fecret between '"+control.Formato_Amd(fdesde.getDate())+"' and '"+ control.Formato_Amd(
     fhasta.getDate())+"') and "+"s.idSede='"+InfoGeneral.idSede+"' and (r.mtvo like'%"+txBuscar.getText()
     +"%' or r.nrobaucher like'%"+txBuscar.getText()+"%' or r.respble like'%"+txBuscar.getText()+ "%') and "
     +" c.numcta='"+ctr+"';";lbRetiros.setText(control.DevolverRegistroDto(control.Sql,1));   
    }
    public void Imprimir(){
        
    }
    public void Cancelar(){
     jComboBox2.setSelectedIndex(-1);T_DepositosRetiros.clearSelection();
     PonerFechas();control.bandera=false;ctr="";txBuscar.setText(null);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lbRetiros = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txBuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        fhasta = new com.toedter.calendar.JDateChooser();
        fdesde = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        T_DepositosRetiros = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 0, 153));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton1.setMnemonic('s');
        jButton1.setText("Salir");
        jButton1.setMargin(new java.awt.Insets(2, 1, 2, 14));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 110, 40));

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 0, 153));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Print.png"))); // NOI18N
        jButton2.setMnemonic('i');
        jButton2.setText("Imprimir");
        jButton2.setMargin(new java.awt.Insets(2, 1, 2, 14));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 110, 40));

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(51, 0, 153));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        jButton3.setMnemonic('c');
        jButton3.setText("Cancelar");
        jButton3.setMargin(new java.awt.Insets(2, 1, 2, 14));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 110, 40));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 1020, 60));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Criterios de seleccion", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 13), new java.awt.Color(0, 0, 51)))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbRetiros.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        lbRetiros.setForeground(new java.awt.Color(0, 0, 102));
        lbRetiros.setText("M_Depositos");
        jPanel2.add(lbRetiros, new org.netbeans.lib.awtextra.AbsoluteConstraints(935, 60, -1, -1));

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 200, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("Cuenta bancaria");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        txBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txBuscarKeyReleased(evt);
            }
        });
        jPanel2.add(txBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 77, 920, -1));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("Hasta");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 30, -1, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("Buscar");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        fhasta.setDateFormatString("dd-MM-yyyy");
        jPanel2.add(fhasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 28, 150, -1));

        fdesde.setDateFormatString("dd-MM-yyyy");
        jPanel2.add(fdesde, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 28, 150, -1));

        T_DepositosRetiros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(T_DepositosRetiros);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 1000, 250));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("Desde");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, -1, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setText("Depositos:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 60, -1, -1));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("Cuenta bancaria");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 360));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Imprimir();
    }//GEN-LAST:event_jButton2ActionPerformed
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Cancelar();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        //if(jComboBox2.getSelectedIndex()>-1){
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void txBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarKeyReleased
        Mostrar();
    }//GEN-LAST:event_txBuscarKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable T_DepositosRetiros;
    private com.toedter.calendar.JDateChooser fdesde;
    private com.toedter.calendar.JDateChooser fhasta;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbRetiros;
    private javax.swing.JTextField txBuscar;
    // End of variables declaration//GEN-END:variables
}
