/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CrearPresmiso.java
 *
 * Created on 06-feb-2014, 17:23:44
 */
package Ventanas;

import javax.swing.JOptionPane;
import Clases.*;
import javax.swing.ImageIcon;
/**
 *
 * @author Silva
 */
public class CrearPresmiso extends javax.swing.JInternalFrame {
Controlador control = new Controlador();
Mimodelo modelo= new Mimodelo();
String codper="";
    /** Creates new form CrearPresmiso */
    public CrearPresmiso() {
        initComponents();this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        this.setTitle("Permisos");
        tbPermisos.setModel(modelo);
         modelo.setColumnIdentifiers(new String[] {"Item","Permiso"});
         tbPermisos.getColumnModel().getColumn(0).setMaxWidth(0);
         tbPermisos.getColumnModel().getColumn(0).setMinWidth(0);
         tbPermisos.getColumnModel().getColumn(0).setPreferredWidth(0);
         MostrarCliente();
         control.forma_table_ver(modelo, tbPermisos);
    }
    public void MostrarCliente(){
     BuscarCliente();   
    }    
    public void BuscarCliente(){
     control.Sql="SELECT * FROM permisos where Descripcion like'"+
     txtBuscarPermisos.getText()+"%';";
     control.LlenarJTabla(modelo,control.Sql,2); 
     
 //    JOptionPane.showMessageDialog(null,control.Sql);
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    public void Cancelar(){
        txtPermisos.setText("");
        txtBuscarPermisos.setText("");
        bCrear.setText("Crear");
        bModificar.setText("Editar");
        bCrear.setEnabled(true);
//        bModificar.setEnabled(false);
        bModificar.setEnabled(true);
        bEliminar.setEnabled(true);
    }
    public void CrearPermisos(){
        if(bCrear.getText().compareTo("Crear")==0){
            if(txtPermisos.getText().trim().length()>0){
                control.Sql="insert into permisos (Descripcion) values ('"+txtPermisos.getText()+"');";
                control.CrearRegistro(control.Sql);
                 txtPermisos.setText("");
                 txtPermisos.grabFocus();
                control.LimTabla(modelo);
                MostrarCliente();
            }else{
                JOptionPane.showMessageDialog(null, "Ingrese el Nuevo Permiso!!");
            }
        }else{
         JOptionPane.showMessageDialog(null,"fds");
        }
    }
    public void EditarPermiso(){
        if(tbPermisos.getSelectedRowCount()==1){
            if(bModificar.getText().compareTo("Editar")==0){
                txtPermisos.setText(modelo.getValueAt(tbPermisos.getSelectedRow(), 1).toString());
                codper=modelo.getValueAt(tbPermisos.getSelectedRow(), 0).toString();
                bCrear.setEnabled(false);
                bEliminar.setEnabled(false);
                bModificar.setText("Grabar");
            }else{
                if(txtPermisos.getText().trim().length()>0){
                    control.Sql="update permisos set Descripcion='"+txtPermisos.getText()+"' where idpermisos='"+codper+"';";
                    control.EditarRegistro(control.Sql);
                    control.LimTabla(modelo);
                    MostrarCliente();
                    bCrear.setEnabled(true);
                bEliminar.setEnabled(true);
                bModificar.setText("Editar");
                txtPermisos.setText("");
                txtPermisos.grabFocus();
                }else{JOptionPane.showMessageDialog(null,"Tiene que Ingresar el Permiso");}
            }
        }else{
             JOptionPane.showMessageDialog(null, "Selecione un permiso para Editarlo");
        }
    }
    public void EliminarPermiso(){
        if(tbPermisos.getSelectedRowCount()==1){
            if(JOptionPane.showConfirmDialog(null, "Desea  Eliminar este Permiso!!","",JOptionPane.YES_NO_OPTION)==0){
                control.Sql="delete from permisos where idpermisos='"+modelo.getValueAt(tbPermisos.getSelectedRow(), 0).toString()+"';";
                control.EliminarRegistro(control.Sql);
                control.LimTabla(modelo);
                MostrarCliente();
                txtPermisos.grabFocus();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Seleccione un permiso para eliminar!!");
        }
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        bCrear = new javax.swing.JButton();
        bModificar = new javax.swing.JButton();
        bEliminar = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        bSalir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtPermisos = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtBuscarPermisos = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPermisos = new javax.swing.JTable();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bCrear.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCrear.setForeground(new java.awt.Color(0, 51, 102));
        bCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        bCrear.setMnemonic('c');
        bCrear.setText("Crear");
        bCrear.setName("bCrear"); // NOI18N
        bCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCrearActionPerformed(evt);
            }
        });
        jPanel1.add(bCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 10, 110, 40));

        bModificar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bModificar.setForeground(new java.awt.Color(0, 51, 102));
        bModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Edit.png"))); // NOI18N
        bModificar.setMnemonic('e');
        bModificar.setText("Editar");
        bModificar.setName("bModificar"); // NOI18N
        bModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModificarActionPerformed(evt);
            }
        });
        jPanel1.add(bModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 10, 110, 40));

        bEliminar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bEliminar.setForeground(new java.awt.Color(0, 51, 102));
        bEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        bEliminar.setMnemonic('l');
        bEliminar.setText("Eliminar");
        bEliminar.setName("bEliminar"); // NOI18N
        bEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(bEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 10, 110, 40));

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 51, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setMnemonic('a');
        bCancelar.setText("Cancelar");
        bCancelar.setName("bCancelar"); // NOI18N
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(372, 10, 110, 40));

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setForeground(new java.awt.Color(0, 51, 102));
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setMnemonic('s');
        bSalir.setText("Salir");
        bSalir.setName("bSalir"); // NOI18N
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel1.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 10, 110, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 62, 610, 60));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Nuevo Permiso");
        jLabel2.setName("jLabel2"); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 28, -1, -1));

        txtPermisos.setName("txtPermisos"); // NOI18N
        getContentPane().add(txtPermisos, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 25, 490, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Buscar");
        jLabel3.setName("jLabel3"); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 150, -1, -1));

        txtBuscarPermisos.setName("txtBuscarPermisos"); // NOI18N
        txtBuscarPermisos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarPermisosKeyReleased(evt);
            }
        });
        getContentPane().add(txtBuscarPermisos, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 540, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agregar Permisos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 60));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Permisos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tbPermisos.setForeground(new java.awt.Color(0, 51, 102));
        tbPermisos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tbPermisos.setName("tbPermisos"); // NOI18N
        tbPermisos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPermisosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbPermisos);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 622, 270));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 640, 330));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
        dispose();
    }//GEN-LAST:event_bSalirActionPerformed

    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
            Cancelar();
    }//GEN-LAST:event_bCancelarActionPerformed

    private void bCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCrearActionPerformed
        CrearPermisos();
    }//GEN-LAST:event_bCrearActionPerformed

    private void bModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarActionPerformed
        EditarPermiso();
    }//GEN-LAST:event_bModificarActionPerformed

    private void bEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarActionPerformed
        
        EliminarPermiso();
        
    }//GEN-LAST:event_bEliminarActionPerformed

    private void tbPermisosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPermisosMouseClicked
        if(evt.getClickCount()==2){
            if(tbPermisos.getSelectedRowCount()==1){
                bModificar.setText("Editar");
                bModificar.doClick();
            }
        }
    }//GEN-LAST:event_tbPermisosMouseClicked

    private void txtBuscarPermisosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarPermisosKeyReleased
        BuscarCliente();
    }//GEN-LAST:event_txtBuscarPermisosKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bCrear;
    private javax.swing.JButton bEliminar;
    private javax.swing.JButton bModificar;
    private javax.swing.JButton bSalir;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbPermisos;
    private javax.swing.JTextField txtBuscarPermisos;
    private javax.swing.JTextField txtPermisos;
    // End of variables declaration//GEN-END:variables
}