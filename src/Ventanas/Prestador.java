/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Prestador.java
 *
 * Created on 03-feb-2014, 18:04:28
 */
package Ventanas;

import javax.swing.*;
import Clases.*;
public class Prestador extends javax.swing.JInternalFrame {
    Controlador control =new Controlador();    
    InfoGeneral info=new InfoGeneral();
    String idpres="";Mimodelo modelo=new Mimodelo();
    
    public Prestador() {
     initComponents();setTitle("Los Prestadores");
     this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     tPrestador.setModel(modelo);modelo.setColumnIdentifiers(new String[] 
     {"Código","Prestador","Responsable","Dirección","Teléfono"});
     tPrestador.getColumnModel().getColumn(1).setPreferredWidth(250);
     tPrestador.getColumnModel().getColumn(2).setPreferredWidth(250);
     tPrestador.getColumnModel().getColumn(3).setPreferredWidth(250);
     tPrestador.getColumnModel().getColumn(4).setPreferredWidth(120);
     MostrarPrestador();
     control.forma_table_ver(modelo, tPrestador);
    // FormatoTabla ft= new FormatoTabla(0);
     
    }
    public void CrearPrestador(){
     if(txtDireccion.getText().trim().length()==0){
      JOptionPane.showMessageDialog(rootPane,"Faltan datos");   
      txtDireccion.grabFocus();
     }
     else{
      control.Sql="call CrearPrestador('"+txtNombre.getText()+"','"+
      txtResponsable.getText()+"','"+txtFono.getText()+"','"+
      txtDireccion.getText()+"')";control.CrearRegistro(control.Sql);  
      Cancelar();MostrarPrestador();
     }         
    }
    public void ModificarPrestador(){
     if(txtDireccion.getText().trim().length()==0){
      JOptionPane.showMessageDialog(rootPane,"Faltan datos");   
      txtDireccion.grabFocus();
     } 
     else{
      control.Sql="call MoficarPrestador('"+idpres+"','"+ txtNombre.getText()+"','"+
      txtResponsable.getText()+"','"+txtDireccion.getText()+"','"+txtFono.getText()+"')";
      control.EditarRegistro(control.Sql);Cancelar();MostrarPrestador();
     }
    }
    public void EliminarPrestador(){
     control.fila=tPrestador.getSelectedRow();
     if(control.fila>=0){
      if(JOptionPane.showConfirmDialog(rootPane,"Seguro deseas eliminar","Confirme",0)==0){
       control.Sql="call EliminarPrestador('"+idpres+"')";
       control.EliminarRegistro(control.Sql);
       Cancelar();MostrarPrestador();
      }    
     }
    }
    public void Cancelar(){
     txtBuscarPrestador.setText(null);txtDireccion.setText(null);txtFono.setText(null);   
     txtNombre.setText(null);txtResponsable.setText(null);tPrestador.clearSelection();
     txtNombre.grabFocus();
    }
    public void BuscarPrestador(){
     control.Sql="select * from vta_prestador where nomprest like'"+
     txtBuscarPrestador.getText()+"%' or respon like'"+txtBuscarPrestador.getText()+"%'";   
     control.LlenarJTabla(modelo,control.Sql,5);
    }
    public void MostrarPrestador(){
     BuscarPrestador();   
    }
    public void VerPrestador(){
     control.fila=tPrestador.getSelectedRow();
     if(control.fila>=0){
      idpres=tPrestador.getValueAt(control.fila,0).toString();   
      txtNombre.setText(tPrestador.getValueAt(control.fila,1).toString());
      txtResponsable.setText(tPrestador.getValueAt(control.fila,2).toString());
      txtDireccion.setText(tPrestador.getValueAt(control.fila,3).toString());
      txtFono.setText(tPrestador.getValueAt(control.fila,4).toString());
     }         
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jLabel9 = new javax.swing.JLabel();
        txtBuscarPrestador = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        txtFono = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtResponsable = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bCrear = new javax.swing.JButton();
        bModificar = new javax.swing.JButton();
        bEliminar = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tPrestador = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();

        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 153));
        jLabel9.setText("Buscar");
        jLabel9.setName("jLabel9"); // NOI18N
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 219, -1, 15));

        txtBuscarPrestador.setName("txtBuscarPrestador"); // NOI18N
        txtBuscarPrestador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarPrestadorKeyReleased(evt);
            }
        });
        getContentPane().add(txtBuscarPrestador, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 215, 300, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 10), java.awt.Color.white), "Datos del prestador", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtFono.setName("txtFono"); // NOI18N
        txtFono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFonoFocusGained(evt);
            }
        });
        jPanel2.add(txtFono, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 80, 240, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("Nombre");
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 20));

        txtNombre.setName("txtNombre"); // NOI18N
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        jPanel2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 250, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setText("Responsable");
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 20));

        txtResponsable.setName("txtResponsable"); // NOI18N
        txtResponsable.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtResponsableFocusGained(evt);
            }
        });
        txtResponsable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtResponsableKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtResponsableKeyTyped(evt);
            }
        });
        jPanel2.add(txtResponsable, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 250, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 153));
        jLabel6.setText("Fono");
        jLabel6.setName("jLabel6"); // NOI18N
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 50, 20));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 153));
        jLabel8.setText("Dirección");
        jLabel8.setName("jLabel8"); // NOI18N
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 30, 60, 20));

        txtDireccion.setName("txtDireccion"); // NOI18N
        txtDireccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDireccionFocusGained(evt);
            }
        });
        jPanel2.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, 240, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 63, 810, 120));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setForeground(new java.awt.Color(0, 51, 102));
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setText("Salir");
        bSalir.setName("bSalir"); // NOI18N
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel1.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 100, 40));

        bCrear.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCrear.setForeground(new java.awt.Color(0, 51, 102));
        bCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        bCrear.setText("Crear");
        bCrear.setName("bCrear"); // NOI18N
        bCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCrearActionPerformed(evt);
            }
        });
        jPanel1.add(bCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 100, 40));

        bModificar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bModificar.setForeground(new java.awt.Color(0, 51, 102));
        bModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Edit.png"))); // NOI18N
        bModificar.setText("Editar");
        bModificar.setName("bModificar"); // NOI18N
        bModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModificarActionPerformed(evt);
            }
        });
        jPanel1.add(bModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 100, 40));

        bEliminar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bEliminar.setForeground(new java.awt.Color(0, 51, 102));
        bEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        bEliminar.setText("Eliminar");
        bEliminar.setName("bEliminar"); // NOI18N
        bEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(bEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 110, 40));

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 51, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setText("Cancelar");
        bCancelar.setName("bCancelar"); // NOI18N
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 110, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 0, 583, 60));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tPrestador.setForeground(new java.awt.Color(0, 51, 102));
        tPrestador.setModel(new javax.swing.table.DefaultTableModel(
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
        tPrestador.setName("tPrestador"); // NOI18N
        tPrestador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tPrestadorMouseClicked(evt);
            }
        });
        tPrestador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tPrestadorKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tPrestador);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 790, 230));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de prestadores", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 184, 810, 290));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
      dispose();
    }//GEN-LAST:event_bSalirActionPerformed
    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
     Cancelar(); 
    }//GEN-LAST:event_bCancelarActionPerformed
    private void bCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCrearActionPerformed
     CrearPrestador();    
    }//GEN-LAST:event_bCrearActionPerformed
    private void bModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarActionPerformed
     ModificarPrestador();  
    }//GEN-LAST:event_bModificarActionPerformed
    private void bEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarActionPerformed
     EliminarPrestador();     
    }//GEN-LAST:event_bEliminarActionPerformed
    private void txtResponsableFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtResponsableFocusGained
     control.VolverdeTxatx(txtNombre);    
    }//GEN-LAST:event_txtResponsableFocusGained
    private void txtDireccionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDireccionFocusGained
     control.VolverdeTxatx(txtResponsable);    
    }//GEN-LAST:event_txtDireccionFocusGained
    private void txtFonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFonoFocusGained
     control.VolverdeTxatx(txtDireccion);         
    }//GEN-LAST:event_txtFonoFocusGained
    private void txtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyReleased
    control.Mayusculas(txtNombre);        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyReleased
    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
     control.SoloLetras(evt);
    }//GEN-LAST:event_txtNombreKeyTyped
    private void txtResponsableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtResponsableKeyTyped
     control.SoloLetras(evt);
    }//GEN-LAST:event_txtResponsableKeyTyped
    private void tPrestadorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tPrestadorMouseClicked
     VerPrestador();
    }//GEN-LAST:event_tPrestadorMouseClicked
    private void tPrestadorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tPrestadorKeyReleased
     VerPrestador();
    }//GEN-LAST:event_tPrestadorKeyReleased

    private void txtBuscarPrestadorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarPrestadorKeyReleased
     BuscarPrestador();
    }//GEN-LAST:event_txtBuscarPrestadorKeyReleased

    private void txtResponsableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtResponsableKeyReleased
            control.Mayusculas(txtResponsable);// TODO add your handling code here:
    }//GEN-LAST:event_txtResponsableKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    public static javax.swing.JButton bCrear;
    private javax.swing.JButton bEliminar;
    private javax.swing.JButton bModificar;
    private javax.swing.JButton bSalir;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tPrestador;
    private javax.swing.JTextField txtBuscarPrestador;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtFono;
    public static javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtResponsable;
    // End of variables declaration//GEN-END:variables

   
}
