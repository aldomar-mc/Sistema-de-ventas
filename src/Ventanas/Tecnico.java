
package Ventanas;

/**** @author Miguel Silva ****/
import Clases.*;import javax.swing.*;
public class Tecnico extends javax.swing.JInternalFrame {
   Controlador control=new Controlador();InfoGeneral info=new InfoGeneral();
   String idtec="",tecn,modo="";Mimodelo modelo=new Mimodelo();
     
    public Tecnico() {
      initComponents();setTitle("Los Técnicos Informáticos");setLocation(300,150);
      this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
      tTecnicos.setModel(modelo);modelo.setColumnIdentifiers(new String[] 
     {"ID","DNI","Tecnico","Dirección","Teléfono","Sede"});     
     tTecnicos.getColumnModel().getColumn(1).setPreferredWidth(60);
     tTecnicos.getColumnModel().getColumn(2).setPreferredWidth(250);
     tTecnicos.getColumnModel().getColumn(3).setPreferredWidth(300);
     tTecnicos.getColumnModel().getColumn(4).setPreferredWidth(120);     
     MostrarTecnico();control.LlenarCombo(cboSedes,"select * from sede where nomse<>'Almacen'",2);
    }
    public void Yaexiste(){
     if(txtdni.getText().trim().length()==8){
      if(control.Verificandoconsulta("select * from tecnico where dni='"+txtdni.getText()+"'"))
      {JOptionPane.showMessageDialog(rootPane,"El dni "+txtdni.getText()+" Ya Existe");
       Limpiacontroles();
      }    
     }   
    }
    public void Limpiacontroles(){
     txtBuscarTecnico.setText(null);txtDireccion.setText(null);txtdni.setText(null);
     cboSedes.setSelectedIndex(-1);txtFono.setText(null);txtTecnico.setText(null);txtdni.grabFocus();
    }
    public void CrearTecnico(){
     if(modo.equals("Edicion")){
      JOptionPane.showMessageDialog(rootPane,"Esta en modo de edicion");
      return;
     }             
     if(cboSedes.getSelectedItem().toString().trim().length()==0){
      JOptionPane.showMessageDialog(rootPane,"Faltan Datos");
      txtDireccion.grabFocus();
     }
     else{
      control.Sql="call Tecnicos('0','"+txtTecnico.getText()+"','"+txtDireccion.getText()
      +"','"+txtdni.getText()+"','"+txtFono.getText()+"','0','"+
      control.ObtenerDato("sede","nomse",cboSedes.getSelectedItem().toString(),1)+"');";         
      JOptionPane.showMessageDialog(rootPane, control.CrearRegistroDev(control.Sql));
      tecn=txtTecnico.getText();Limpiacontroles();MostrarTecnico();     
      info.setPass(idtec);     
      if(info.control==3){
       info.setControlCliente(1);
       Facturarservicios.jLabel12.setText(tecn);            
      }    
     }
    }
    public void ModificarTecnico(){
     if(txtDireccion.getText().trim().length()==0){
      JOptionPane.showMessageDialog(rootPane,"Faltan Datos");
      txtDireccion.grabFocus();
     }
     else{
      control.Sql="call Tecnicos('"+idtec+"','"+txtTecnico.getText()+"','"+txtDireccion.getText()
      +"','"+txtdni.getText()+"','"+txtFono.getText()+"','1','"+
      control.ObtenerDato("sede","nomse",cboSedes.getSelectedItem().toString(),1)+"');";         
      JOptionPane.showMessageDialog(rootPane, control.EditarRegistroDev(control.Sql));
      Limpiacontroles();modo="Crear";MostrarTecnico();
     }   
    }
    public void EliminarTecnico(){
     control.fila=tTecnicos.getSelectedRow();
     if(control.fila>=0){
      if(JOptionPane.showConfirmDialog(rootPane,"Seguro quieres eliminar","Confirmar",0)==0){
       control.Sql="call Tecnicos('"+idtec+"','"+txtTecnico.getText()+"','"+txtDireccion.getText()
       +"','"+txtdni.getText()+"','"+txtFono.getText()+"','2')";           
       JOptionPane.showMessageDialog(rootPane, control.EliminarRegistro(control.Sql));
       Limpiacontroles();modo="Crear";MostrarTecnico();   
      }   
     }
     else{
      JOptionPane.showMessageDialog(rootPane, "Debes seleccionar para eliminar");
     }
    }
    public void Cancelar(){
     tTecnicos.clearSelection();modo="Crear";Limpiacontroles();
    }
    public void VerTecnico(){
     control.fila=tTecnicos.getSelectedRow();
     if(control.fila>=0){
      idtec=tTecnicos.getValueAt(control.fila,0).toString();   
      txtTecnico.setText(tTecnicos.getValueAt(control.fila,2).toString());
      txtdni.setText(tTecnicos.getValueAt(control.fila,1).toString());
      txtDireccion.setText(tTecnicos.getValueAt(control.fila,3).toString());
      txtFono.setText(tTecnicos.getValueAt(control.fila,4).toString());
      control.MostrarEnCombo(tTecnicos.getValueAt(control.fila,5).toString(),cboSedes);
      
      modo="Edicion";
     }   
    }
    public void BuscarTecnico(){
      control.Sql="select idTecnico,dni,nomtec,dir,tel,sede from vta_tecnico where dni like'"+
     txtBuscarTecnico.getText()+"%' or nomtec like'"+txtBuscarTecnico.getText()+
     "%' or sede like'"+txtBuscarTecnico.getText()+"%'";control.LlenarJTabla(modelo,control.Sql,6);
    }
    public void Salir(){
     tecn=txtTecnico.getText();Limpiacontroles();MostrarTecnico();info.setPass(idtec);     
      if(info.control==3){          
       info.setControlCliente(1); Facturarservicios.jLabel12.setText(tecn);  
       Facturarservicios.ldident.setText(idtec);  
      }
      dispose();    
    }
    public void MostrarTecnico(){
     BuscarTecnico();   
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
        txtFono = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtdni = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTecnico = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cboSedes = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tTecnicos = new javax.swing.JTable();
        txtBuscarTecnico = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
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
        jPanel1.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 110, 40));

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
        bModificar.setMnemonic('e');
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
        bEliminar.setMnemonic('l');
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

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 590, 60));

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 10), java.awt.Color.white), "Datos del Técnico", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtFono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFonoFocusGained(evt);
            }
        });
        txtFono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFonoKeyTyped(evt);
            }
        });
        jPanel2.add(txtFono, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 80, 260, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("Dni");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 20));

        txtdni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtdniKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdniKeyTyped(evt);
            }
        });
        jPanel2.add(txtdni, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 250, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setText("Sede");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, 20));

        txtTecnico.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTecnicoFocusGained(evt);
            }
        });
        txtTecnico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTecnicoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTecnicoKeyTyped(evt);
            }
        });
        jPanel2.add(txtTecnico, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 250, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 153));
        jLabel6.setText("Fono");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 80, 50, 20));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 153));
        jLabel8.setText("Dirección");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 30, 60, 20));

        txtDireccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDireccionFocusGained(evt);
            }
        });
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDireccionKeyReleased(evt);
            }
        });
        jPanel2.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 30, 260, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 153));
        jLabel7.setText("Técnico");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 20));

        cboSedes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cboSedesFocusGained(evt);
            }
        });
        jPanel2.add(cboSedes, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 250, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 860, 150));

        jPanel3.setBackground(new java.awt.Color(51, 153, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Técnicos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tTecnicos.setForeground(new java.awt.Color(0, 51, 102));
        tTecnicos.setModel(new javax.swing.table.DefaultTableModel(
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
        tTecnicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tTecnicosMouseClicked(evt);
            }
        });
        tTecnicos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tTecnicosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tTecnicos);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 840, 250));

        txtBuscarTecnico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarTecnicoKeyReleased(evt);
            }
        });
        jPanel3.add(txtBuscarTecnico, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 770, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 153));
        jLabel9.setText("Buscar");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 15));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 215, 860, 310));

        jPanel4.setBackground(new java.awt.Color(51, 153, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 540));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
       Salir();
    }//GEN-LAST:event_bSalirActionPerformed

    private void bCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCrearActionPerformed
        CrearTecnico();
    }//GEN-LAST:event_bCrearActionPerformed

    private void bModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarActionPerformed
        ModificarTecnico();
    }//GEN-LAST:event_bModificarActionPerformed

    private void bEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarActionPerformed
        EliminarTecnico();
    }//GEN-LAST:event_bEliminarActionPerformed

    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
        Cancelar();
    }//GEN-LAST:event_bCancelarActionPerformed
    private void txtFonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFonoFocusGained
        control.VolverdeTxatx(txtDireccion);
    }//GEN-LAST:event_txtFonoFocusGained
    private void txtdniKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdniKeyReleased
     Yaexiste();
    }//GEN-LAST:event_txtdniKeyReleased
    private void txtdniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdniKeyTyped
     control.Solonumeros(evt);control.Longitudtx(txtdni, evt,8);
    }//GEN-LAST:event_txtdniKeyTyped
    private void txtTecnicoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTecnicoFocusGained
      control.VolverdeTxatx(txtdni);
      if(txtdni.getText().trim().length()<8){  
       //JOptionPane.showMessageDialog(rootPane,"El dni debe tener 8 digitos");
       control.MarcarTexto(txtdni);
      }    
    }//GEN-LAST:event_txtTecnicoFocusGained

    private void txtTecnicoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTecnicoKeyTyped
        control.SoloLetras(evt);
    }//GEN-LAST:event_txtTecnicoKeyTyped
    private void txtDireccionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDireccionFocusGained
        control.VolverdeTxatx(txtTecnico);
    }//GEN-LAST:event_txtDireccionFocusGained
    private void tTecnicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tTecnicosMouseClicked
        VerTecnico();
    }//GEN-LAST:event_tTecnicosMouseClicked
    private void tTecnicosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tTecnicosKeyReleased
        VerTecnico();
    }//GEN-LAST:event_tTecnicosKeyReleased
    private void txtBuscarTecnicoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarTecnicoKeyReleased
        BuscarTecnico();
    }//GEN-LAST:event_txtBuscarTecnicoKeyReleased
    private void txtTecnicoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTecnicoKeyReleased
     control.Mayusculas(txtTecnico);
    }//GEN-LAST:event_txtTecnicoKeyReleased
    private void txtDireccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyReleased
     control.Mayusculas(txtDireccion);
    }//GEN-LAST:event_txtDireccionKeyReleased
    private void txtFonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFonoKeyTyped
     control.Solonumeros(evt);
    }//GEN-LAST:event_txtFonoKeyTyped
    private void cboSedesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cboSedesFocusGained
     control.VolverdeTxatx(txtTecnico);
    }//GEN-LAST:event_cboSedesFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    public static javax.swing.JButton bCrear;
    private javax.swing.JButton bEliminar;
    private javax.swing.JButton bModificar;
    private javax.swing.JButton bSalir;
    private javax.swing.JComboBox cboSedes;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tTecnicos;
    private javax.swing.JTextField txtBuscarTecnico;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtFono;
    private javax.swing.JTextField txtTecnico;
    public static javax.swing.JTextField txtdni;
    // End of variables declaration//GEN-END:variables
}
