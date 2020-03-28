
package Ventanas;
import Clases.*;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Vendedores extends javax.swing.JInternalFrame {
Controlador control = new Controlador();
Mimodelo  modelo= new Mimodelo();
String dato="", pass="", Codigo="";

    /** Creates new form Vendedores */
    public Vendedores() {
        initComponents();
        bloquear(false);
        setTitle("Los Vendedores");
     this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     tVendedores.setModel(modelo);
     modelo.setColumnIdentifiers(new String[] {"Código","Dni","Nombre","Dirección","Telefono","Login"});
     bCrear.setMnemonic('c');
     bModificar.setMnemonic('d');
     bEliminar.setMnemonic('e');
     bCancelar.setMnemonic('a');
     bSalir.setMnemonic('s');
     MostrarCliente();
    }


    
   public void bloquear(boolean  a){
       tNombre.setEnabled(a);
       txDireccion.setEnabled(a);
       txDni.setEnabled(a);
       txTelefono.setEditable(a);
       txUsuario.setEnabled(a);
       pwClave.setEnabled(a);
   }
   public void limpiar(){
       txDireccion.setText("");
       txDni.setText("");
       txTelefono.setText("");
       txUsuario.setText("");
       pwClave.setText("");
       tNombre.setText("");
   }
   public void Cancelar(){
       limpiar();
       bloquear(false);
       bCrear.setText("Crear");
       bModificar.setText("Editar");
       bCrear.setEnabled(true);
       bModificar.setEnabled(true);
       bEliminar.setEnabled(true);
   }
   
   public void MostrarCliente(){
     BuscarCliente();   
    }    
    public void BuscarCliente(){
     control.Sql="SELECT idusuario,dni,nom,dire,tel, nomusr FROM vendedores  where idtipousuario='2' and ( nomusr like'"+
     txtbucarVendedor.getText()+"%' or dni like'"+
     txtbucarVendedor.getText()+"%') ";
     control.LlenarJTabla(modelo,control.Sql,6); 
     
 //    JOptionPane.showMessageDialog(null,control.Sql);
    }
    public void AgregarVendedor(){
        if(bCrear.getText().compareTo("Crear")==0){
            bloquear(true);
            tNombre.grabFocus();
            bEliminar.setEnabled(false);
            bModificar.setEnabled(false);
            bCrear.setText("Grabar");
        }else{
            if(verfica()){
                if(control.Verificarconsulta("select * from usuario where nomusr='"+txUsuario.getText()+"';")==false){
                   control.Sql="select InsertaVendedor('"+txUsuario.getText()+"','"+pwClave.getText()+"','Vendedor');";
                    dato=control.DevolverRegistroDto(control.Sql, 1);
                    //System.out.print(control.Sql);
                    control.Sql="insert into datosusuarios(nom,dire,tel, dni, Usuario_idusuario)values"
                    + "('"+control.PriLtrasMayuscula(tNombre.getText())+"','"+txDireccion.getText()+"','"+txTelefono.getText()+"','"+txDni.getText()+"','"+dato+"');";
                    control.CrearRegistro(control.Sql);
                   // System.out.print(control.Sql);
                    bloquear(false);
                    limpiar();
                    bEliminar.setEnabled(true);
                    bModificar.setEnabled(true);
                    bCrear.setText("Crear");     
                    control.LimTabla(modelo);
                    MostrarCliente();
                }else{
                    JOptionPane.showMessageDialog(null,"El usuario ya Existe!!");
                }
                
            }else{
                JOptionPane.showMessageDialog(null,"Informacion Incompleta!!");
            }
            
        }
    }
    public boolean  verfica(){
        boolean a=false;
        if(tNombre.getText().trim().length()>0 && txDireccion.getText().trim().length()>0 && txDni.getText().trim().length()>0 && txTelefono.getText().trim().length()>0 && txUsuario.getText().trim().length()>0){
            a=true;
        }
        return a;
    }
    public void EliminarVendedor(){
        
     if(tVendedores.getSelectedRowCount()==1){
         if(JOptionPane.showConfirmDialog(null, "Desea eliminar este Vendedor!!","",JOptionPane.YES_NO_OPTION)==0){
             control.Sql="delete from usuario where idusuario='"+modelo.getValueAt(tVendedores.getSelectedRow(), 0) +"';";
                control.EliminarRegistro(control.Sql );
                limpiar();
                MostrarCliente();
         }
     }else{
         JOptionPane.showMessageDialog(null, "Tiene Que Seleccionar un Clinete para Eliminar","",2);
     }
 
    }
    public void EditarVendedor(){
        if(bModificar.getText().compareTo("Editar")==0){
            if(tVendedores.getSelectedRowCount()==1){
                bloquear(true);
                tNombre.grabFocus();
                bCrear.setEnabled(false);
                bEliminar.setEnabled(false);
                Codigo=modelo.getValueAt(tVendedores.getSelectedRow(), 0).toString();
                tNombre.setText(modelo.getValueAt(tVendedores.getSelectedRow(), 2).toString());
                txDireccion.setText(modelo.getValueAt(tVendedores.getSelectedRow(), 3).toString());
                txDni.setText(modelo.getValueAt(tVendedores.getSelectedRow(), 1).toString());
                txTelefono.setText(modelo.getValueAt(tVendedores.getSelectedRow(), 4).toString());
                txUsuario.setText(modelo.getValueAt(tVendedores.getSelectedRow(), 5).toString());
                pass=control.DevolverRegistroDto("select psw from usuario where idusuario='"+Codigo+"';", 1);
                pwClave.setText(pass);
                bModificar.setText("Grabar");                
            }else{
                JOptionPane.showMessageDialog(null, "Selecione un Vendedor!!");
            }
        }else{
            if(verfica()){
                control.Sql="call UpdateVendedor('"+Codigo+"','"+txUsuario.getText()+"','"+pwClave.getText()+"','"+control.PriLtrasMayuscula(tNombre.getText())+"','"+txDireccion.getText()+"','"+txTelefono.getText()+"','"+txDni.getText()+"');";
                control.EditarRegistro(control.Sql);
                //System.out.print(control.Sql);
                bloquear(false);
                limpiar();
                bCrear.setEnabled(true);
                bEliminar.setEnabled(true);
                bModificar.setText("Editar");   
                control.LimTabla(modelo);
                MostrarCliente();
            }else{
                JOptionPane.showMessageDialog(null, "Datos incompletos!!!");
            }
            
        }
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
        tNombre = new javax.swing.JTextField();
        txTelefono = new javax.swing.JTextField();
        txDni = new javax.swing.JTextField();
        txtbucarVendedor = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tVendedores = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txDireccion = new javax.swing.JTextField();
        pwClave = new javax.swing.JPasswordField();
        txUsuario = new javax.swing.JTextField();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel1.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 100, 30));

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
        jPanel1.add(bCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, 30));

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
        jPanel1.add(bModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 100, 30));

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
        jPanel1.add(bEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 110, 30));

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
        jPanel1.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, -1, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 630, 50));

        tNombre.setName("tNombre"); // NOI18N
        getContentPane().add(tNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 370, -1));

        txTelefono.setName("txTelefono"); // NOI18N
        getContentPane().add(txTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, 370, -1));

        txDni.setName("txDni"); // NOI18N
        getContentPane().add(txDni, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 210, -1));

        txtbucarVendedor.setName("txtbucarVendedor"); // NOI18N
        txtbucarVendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucarVendedorKeyReleased(evt);
            }
        });
        getContentPane().add(txtbucarVendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 350, -1));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tVendedores.setForeground(new java.awt.Color(0, 51, 102));
        tVendedores.setModel(new javax.swing.table.DefaultTableModel(
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
        tVendedores.setName("tVendedores"); // NOI18N
        jScrollPane1.setViewportView(tVendedores);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 840, 190));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 102));
        jLabel9.setText("Buscar");
        jLabel9.setName("jLabel9"); // NOI18N
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, 20));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Usuario");
        jLabel6.setName("jLabel6"); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 130, 60, 20));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Clave");
        jLabel3.setName("jLabel3"); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 180, -1, 20));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Dirección");
        jLabel5.setName("jLabel5"); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, 20));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 102));
        jLabel7.setText("Nombre");
        jLabel7.setName("jLabel7"); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 20));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Teléfono");
        jLabel8.setName("jLabel8"); // NOI18N
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, 20));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 102));
        jLabel10.setText("Dni");
        jLabel10.setName("jLabel10"); // NOI18N
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 20, 20));

        txDireccion.setName("txDireccion"); // NOI18N
        getContentPane().add(txDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 370, -1));

        pwClave.setName("pwClave"); // NOI18N
        getContentPane().add(pwClave, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 180, 210, -1));

        txUsuario.setName("txUsuario"); // NOI18N
        getContentPane().add(txUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 130, 210, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
    dispose();
}//GEN-LAST:event_bSalirActionPerformed

private void bCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCrearActionPerformed
    AgregarVendedor();
}//GEN-LAST:event_bCrearActionPerformed

private void bModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarActionPerformed
    EditarVendedor();
}//GEN-LAST:event_bModificarActionPerformed

private void bEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarActionPerformed
EliminarVendedor();    
}//GEN-LAST:event_bEliminarActionPerformed

private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
    Cancelar();
}//GEN-LAST:event_bCancelarActionPerformed

    private void txtbucarVendedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucarVendedorKeyReleased

        MostrarCliente();
        
    }//GEN-LAST:event_txtbucarVendedorKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bCrear;
    private javax.swing.JButton bEliminar;
    private javax.swing.JButton bModificar;
    private javax.swing.JButton bSalir;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPasswordField pwClave;
    private javax.swing.JTextField tNombre;
    private javax.swing.JTable tVendedores;
    private javax.swing.JTextField txDireccion;
    private javax.swing.JTextField txDni;
    private javax.swing.JTextField txTelefono;
    private javax.swing.JTextField txUsuario;
    private javax.swing.JTextField txtbucarVendedor;
    // End of variables declaration//GEN-END:variables
}
