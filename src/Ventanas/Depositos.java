/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Depositos.java
 *
 * Created on 11-feb-2014, 10:46:24
 */
package Ventanas;

import  Clases.*;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
public class Depositos extends javax.swing.JInternalFrame {
Mimodelo modelo=new Mimodelo();
Controlador control=new Controlador();
int controlacombo=0;
String idCuen="",idcompr="";
    
    public Depositos() {
        initComponents();
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
         tbCompras.setModel(modelo);modelo.setColumnIdentifiers(new String[] {"Compra","Proveedor","Serie","Numero","Fecha de Compra","Comprobnate"});
         tbCompras.getColumnModel().getColumn(0).setPreferredWidth(50);
         tbCompras.getColumnModel().getColumn(1).setPreferredWidth(200);
         tbCompras.getColumnModel().getColumn(2).setPreferredWidth(50);
         tbCompras.getColumnModel().getColumn(3).setPreferredWidth(50);
         tbCompras.getColumnModel().getColumn(4).setPreferredWidth(50);
         tbCompras.getColumnModel().getColumn(5).setPreferredWidth(50); 
         mostrarCuentaBancaria();
         fecha.setDate(new Date());
         control.forma_table_ver(modelo, tbCompras);    
    }

    public void mostrarCuentaBancaria(){
        control.Sql="SELECT idcompra, nompro, serie,nume,fec, tipo FROM compra_proveedor where (nompro like'"+
        txtBuscarCompra.getText()+"%' or serie like'"+
        txtBuscarCompra.getText()+"%') and idcompra not in (select idcompra from deposito) ";
        control.LlenarJTabla(modelo,control.Sql,6);
    }
    public void SeleccionarUsuario(){
        if(tbCompras.getSelectedRowCount()==1){
            controlacombo=0;
            lbBanco.setText(" ");
            lbProveedor.setText(modelo.getValueAt(tbCompras.getSelectedRow(), 1).toString());
            idcompr=modelo.getValueAt(tbCompras.getSelectedRow(), 0).toString();
            control.Sql="SELECT numcta FROM cuenta_bancaruia c where nompro='"+modelo.getValueAt(tbCompras.getSelectedRow(), 1).toString()+"';";
            control.LlenarCombo(cbCuentas, control.Sql, 1);
            //System.out.print(control.Sql);
        }
    }
    public void Cancelar(){
        mostrarCuentaBancaria();
        lbProveedor.setText(" ");
        lbBanco.setText(" ");
        txtBuscarCompra.setText("");
        txtBuscarCompra.grabFocus();
        controlacombo=0;
        cbCuentas.removeAllItems();
        txtMontoDeposito.setText("");
    }
    public void verifica(boolean a){
        if(a){
            control.Sql="SELECT nombco FROM cuenta_bancaruia c where nompro='"+lbProveedor.getText() +"' and numcta='"+cbCuentas.getSelectedItem().toString()+"';";
            lbBanco.setText(control.DevolverRegistroDto(control.Sql, 1));
            //System.out.print(control.Sql);
        }
    }
    public void Deporitar_Pago(){
        if(lbProveedor.getText().trim().length()>0 && cbCuentas.getSelectedIndex()>-1 && txtMontoDeposito.getText().trim().length()>0){
            control.Sql="SELECT idcuentaban FROM cuenta_bancaruia c where nompro='"+lbProveedor.getText() +"' and numcta='"+cbCuentas.getSelectedItem().toString()+"';";
            String cad = control.DevolverRegistroDto(control.Sql, 1);
            control.Sql="insert into deposito (numdpo, fechdep, idcompra, idcuentaban) values('"+txtMontoDeposito.getText()+"','"+control.Formato_Amd(fecha.getDate()) +"','"+idcompr+"','"+cad+"');";
            control.CrearRegistro(control.Sql);
            //System.out.print(control.Sql);
            Cancelar();
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel1 = new javax.swing.JPanel();
        lbProveedor = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbCuentas = new javax.swing.JComboBox();
        lbBanco = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMontoDeposito = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCompras = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtBuscarCompra = new javax.swing.JTextField();

        jInternalFrame1.setName("jInternalFrame1"); // NOI18N
        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Proveedor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 255))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(0, 51, 102));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbProveedor.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lbProveedor.setForeground(new java.awt.Color(0, 51, 102));
        lbProveedor.setText(" ");
        lbProveedor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbProveedor.setName("lbProveedor"); // NOI18N
        jPanel1.add(lbProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 16, 438, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Nº Cuenta");
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(51, 51, 70, 17));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Proveedor");
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(51, 20, 70, 17));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Banco:");
        jLabel8.setName("jLabel8"); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(51, 86, 70, 17));

        cbCuentas.setForeground(new java.awt.Color(0, 51, 102));
        cbCuentas.setName("cbCuentas"); // NOI18N
        cbCuentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbCuentasMouseClicked(evt);
            }
        });
        cbCuentas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbCuentasItemStateChanged(evt);
            }
        });
        jPanel1.add(cbCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 50, 187, -1));

        lbBanco.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lbBanco.setForeground(new java.awt.Color(0, 51, 102));
        lbBanco.setText(" ");
        lbBanco.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbBanco.setName("lbBanco"); // NOI18N
        jPanel1.add(lbBanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 81, 438, 24));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 624, 120));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        jButton1.setText("Pagar");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 116, 40));

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, 117, 40));

        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 51, 102));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton4.setText("Salir");
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 107, 40));

        fecha.setForeground(new java.awt.Color(0, 51, 102));
        fecha.setDateFormatString("dd-MM-yyyy");
        fecha.setName("fecha"); // NOI18N
        jPanel2.add(fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(447, 11, 133, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 102));
        jLabel10.setText("Fecha de Deposito");
        jLabel10.setName("jLabel10"); // NOI18N
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(324, 12, 113, 17));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 102));
        jLabel9.setText("Nº Comprobante");
        jLabel9.setName("jLabel9"); // NOI18N
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 12, 113, 17));

        txtMontoDeposito.setName("txtMontoDeposito"); // NOI18N
        jPanel2.add(txtMontoDeposito, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 11, 114, -1));

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 102));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Euro.png"))); // NOI18N
        jButton3.setText("Ver Depósitos");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 140, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 620, 90));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Listado Proveedores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 255))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tbCompras.setForeground(new java.awt.Color(0, 51, 102));
        tbCompras.setModel(new javax.swing.table.DefaultTableModel(
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
        tbCompras.setName("tbCompras"); // NOI18N
        tbCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbComprasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbCompras);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 72, 600, 130));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Buscar");
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 35, 51, 17));

        txtBuscarCompra.setName("txtBuscarCompra"); // NOI18N
        txtBuscarCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarCompraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarCompraKeyReleased(evt);
            }
        });
        jPanel3.add(txtBuscarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 34, 396, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 620, 210));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarCompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCompraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCompraKeyPressed

    private void txtBuscarCompraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCompraKeyReleased
        mostrarCuentaBancaria();
    }//GEN-LAST:event_txtBuscarCompraKeyReleased

    private void tbComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbComprasMouseClicked
        if(evt.getClickCount()==2){
            SeleccionarUsuario();
        }
    }//GEN-LAST:event_tbComprasMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Cancelar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cbCuentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbCuentasMouseClicked
        controlacombo++;
    }//GEN-LAST:event_cbCuentasMouseClicked

    private void cbCuentasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbCuentasItemStateChanged
        if(controlacombo>0 ){
            if(cbCuentas.getSelectedItem().toString().length()>0){
                verifica(true);
            }
        }
    }//GEN-LAST:event_cbCuentasItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Deporitar_Pago();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        VerDeposito dep = new VerDeposito();
        Menu.jDesktopPane1.add(dep);
        dep.toFront();
        dep.setVisible(true);
        dep.setLocation(300, 120);
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbCuentas;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbBanco;
    private javax.swing.JLabel lbProveedor;
    private javax.swing.JTable tbCompras;
    private javax.swing.JTextField txtBuscarCompra;
    private javax.swing.JTextField txtMontoDeposito;
    // End of variables declaration//GEN-END:variables
}
