/** To change this template, choose Tools | Templates
 and open the template in the editor. *********/
/* * Marcas.java ** Created on 13-ene-2014, 13:14:26
 */
package Ventanas;
/**** @author Ing. Miguel Angel Silva Zapata.   **********/
import javax.swing.*;import Clases.*;
import static Ventanas.Catalogoproductos.cboMarcas;
public class Marcas extends javax.swing.JInternalFrame {
    /*************************Atributos**********************/ 
    Mimodelo modelo=new Mimodelo();
    Controlador control=new Controlador();
    String idMrc;
    InfoGeneral info= new InfoGeneral();
    /*************************Atributos**********************/ 
    
    /*************************Métodos************************/ 
    public void CrearMarca(){   
     if(txMarca.getText().trim().length()==0){
      JOptionPane.showMessageDialog(rootPane,"Faltan datos");   
      txMarca.requestFocus();
     }    
     else{
      control.Sql="select * from marca where nommrc='"+txMarca.getText()+"'";   
      if(control.Verificandoconsulta(control.Sql)){
       JOptionPane.showMessageDialog(rootPane,"La marca esta repetida");          
      }
      else{       
       control.Sql="insert into marca (nommrc) values('"+control.PriLtrasMayuscula(txMarca.getText())+
       "')";control.CrearRegistro(control.Sql );MostrarMarca();       
      }
      if(info.getGg()==5){
          control.LlenarCombo(Catalogoproductos.cboMarcas,"select * from marca",2);
         // Catalogoproductos.cbLineas.
      }
      Cancelar();
     }    
    }
    public void ModificarMarca(){
     if(txMarca.getText().length()>0){
      control.Sql="select * from marca where nommrc='"+txMarca.getText()+
      "' and idMarca<>'"+idMrc+"'";        
      if(!control.Verificarconsulta(control.Sql)){
       control.Sql="update marca set nommrc='"+txMarca.getText()+"' where idMarca='"+idMrc+"'";        
       control.EditarRegistro(control.Sql);Cancelar();MostrarMarca();
       JOptionPane.showMessageDialog(null, "Modificado Correctamente");
      }
      else{
       JOptionPane.showMessageDialog(rootPane,"Existe repeticion en nombre de la marca");   
      }         
     } 
     else{
      JOptionPane.showMessageDialog(rootPane,"Faltan datos");   
     }    
    }
    public void EliminarMarca(){
     control.fila=tMarcas.getSelectedRow();   
     if(control.fila>=0){
      idMrc=tMarcas.getValueAt(control.fila,0).toString();   
      control.Sql="select * from catalogoproducto where idMarca='"+idMrc+"'";     
      if(!control.Verificandoconsulta(control.Sql)){
       if(JOptionPane.showConfirmDialog(rootPane,"Seguro deseas eliminar","Confirmar",0)==0){
        control.Sql="delete from marca where idMarca='"+idMrc+"'";    
        control.EliminarRegistro(control.Sql);Cancelar();MostrarMarca();    
       }          
      }else{
          JOptionPane.showMessageDialog(null, "La marca se esta utilizando y no se puede eliminar");
      }
     }     
     else{
      JOptionPane.showMessageDialog(rootPane,"Seleccione la fila a eliminar");   
     }         
    }
    public void VerMarca(){
     control.fila=tMarcas.getSelectedRow();
     if(control.fila>=0){
      bCrear.setEnabled(false);
      bModificar.setEnabled(true);
      bEliminar.setEnabled(true);
      idMrc=tMarcas.getValueAt(control.fila,0).toString();   
      txMarca.setText(tMarcas.getValueAt(control.fila,1).toString());         
     }    
    }
    public void Cancelar(){
        bCrear.setEnabled(true);
         bEliminar.setEnabled(false);
         bModificar.setEnabled(false);
     txMarca.setText(null);tMarcas.clearSelection();
     //txBuscar.setText(null);
     control.MarcarTexto(txMarca);
    // MostrarMarca();
     
     txMarca.requestFocus();
     
    }
    public void MostrarMarca(){
     BuscarMarca();   
    }
    public void BuscarMarca(){
     control.Sql="select * from marca where nommrc like'"+txBuscar.getText()+"%'";
     control.LlenarJTabla(modelo,control.Sql,2);     
    }
    public Marcas() {
     initComponents();setTitle("Las Marcas de productos");
     this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     tMarcas.setModel(modelo);
     modelo.setColumnIdentifiers(new String[] {"Código","Marca"});
     tMarcas.getColumnModel().getColumn(1).setPreferredWidth(250);
     MostrarMarca();
     control.forma_table_ver(modelo, tMarcas);
     bModificar.setEnabled(false);
     bEliminar.setEnabled(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bCrear = new javax.swing.JButton();
        bModificar = new javax.swing.JButton();
        bEliminar = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txMarca = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tMarcas = new javax.swing.JTable();
        txBuscar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setForeground(new java.awt.Color(0, 51, 102));
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setMnemonic('s');
        bSalir.setText("Salir");
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel1.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 100, 40));

        bCrear.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCrear.setForeground(new java.awt.Color(0, 51, 102));
        bCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        bCrear.setMnemonic('c');
        bCrear.setText("Crear");
        bCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCrearActionPerformed(evt);
            }
        });
        jPanel1.add(bCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 100, 40));

        bModificar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bModificar.setForeground(new java.awt.Color(0, 51, 102));
        bModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Edit.png"))); // NOI18N
        bModificar.setMnemonic('d');
        bModificar.setText("Editar");
        bModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModificarActionPerformed(evt);
            }
        });
        jPanel1.add(bModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 100, 40));

        bEliminar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bEliminar.setForeground(new java.awt.Color(0, 51, 102));
        bEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        bEliminar.setMnemonic('e');
        bEliminar.setText("Eliminar");
        bEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(bEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 110, 40));

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 51, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setMnemonic('a');
        bCancelar.setText("Cancelar");
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 110, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 580, 60));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Marcas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txMarcaKeyReleased(evt);
            }
        });
        jPanel2.add(txMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 240, 20));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("Marca");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 580, 47));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Marcas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 200));

        tMarcas.setAutoCreateRowSorter(true);
        tMarcas.setForeground(new java.awt.Color(0, 51, 102));
        tMarcas.setModel(new javax.swing.table.DefaultTableModel(
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
        tMarcas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tMarcasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tMarcasMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tMarcas);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 560, 210));

        txBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txBuscarKeyReleased(evt);
            }
        });
        jPanel3.add(txBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 510, -1));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("Buscar");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 580, 270));

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
 dispose();
}//GEN-LAST:event_bSalirActionPerformed

private void bCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCrearActionPerformed
 CrearMarca();
}//GEN-LAST:event_bCrearActionPerformed

private void bModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarActionPerformed
 ModificarMarca();
}//GEN-LAST:event_bModificarActionPerformed

private void bEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarActionPerformed
 EliminarMarca();
}//GEN-LAST:event_bEliminarActionPerformed
private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
 
 txBuscar.setText("");
 Cancelar();
 MostrarMarca();
}//GEN-LAST:event_bCancelarActionPerformed
private void txBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarKeyReleased
 BuscarMarca();
}//GEN-LAST:event_txBuscarKeyReleased
private void tMarcasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tMarcasMouseEntered
 
}//GEN-LAST:event_tMarcasMouseEntered
private void tMarcasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tMarcasMouseClicked
 VerMarca();
}//GEN-LAST:event_tMarcasMouseClicked

    private void txMarcaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txMarcaKeyReleased
        control.Mayusculas(txMarca);
    }//GEN-LAST:event_txMarcaKeyReleased

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
    private javax.swing.JTable tMarcas;
    private javax.swing.JTextField txBuscar;
    private javax.swing.JTextField txMarca;
    // End of variables declaration//GEN-END:variables
}
