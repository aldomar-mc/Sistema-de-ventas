/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CaracteristicasProducto.java
 *
 * Created on 05-feb-2014, 9:12:41
 */
package Ventanas;

import Clases.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Silva
 */
public class CaracteristicasProducto extends javax.swing.JInternalFrame {

    Controlador control = new Controlador();
    Mimodelo modelo = new Mimodelo();
    Mimodelo modelo1 = new Mimodelo();
    Mimodelo modelo2 = new Mimodelo();
    String CodigoPro = "", codCara="",CodigoCaracteriticaRecuperada="";

    /** Creates new form CaracteristicasProducto */
    public CaracteristicasProducto() {
        initComponents();
        setTitle("Los Productos");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tbProducto.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"Item", "Nombre", "codigo", "Serie"});
        
        tbProducto.getColumnModel().getColumn(0).setPreferredWidth(50);
        tbProducto.getColumnModel().getColumn(1).setPreferredWidth(200);
        tbProducto.getColumnModel().getColumn(2).setPreferredWidth(70);
        tbProducto.getColumnModel().getColumn(3).setPreferredWidth(60);
        
        tbCaracteristicas.setModel(modelo1);
        modelo1.setColumnIdentifiers(new String[]{"Item", "Caracteristica"});
        tbCaracteristicas.getColumnModel().getColumn(0).setPreferredWidth(40);
        tbCaracteristicas.getColumnModel().getColumn(1).setPreferredWidth(250);
        
        tbCaracteristicasSimilares.setModel(modelo2);
        modelo2.setColumnIdentifiers(new String[]{"Item", "Caracteristica"});
        tbCaracteristicasSimilares.getColumnModel().getColumn(0).setPreferredWidth(40);
        tbCaracteristicasSimilares.getColumnModel().getColumn(1).setPreferredWidth(250);
        MostrarProductos();
        txtCarateristica.setEnabled(false);
        txtBuscar.grabFocus();
        FormatoTabla ft= new FormatoTabla(1);
        tbCaracteristicas.setDefaultRenderer(Object.class, ft);
        tbCaracteristicasSimilares.setDefaultRenderer(Object.class, ft);
        tbProducto.setDefaultRenderer(Object.class, ft);
    }

    public void MostrarProductos() {
     control.LimTabla(modelo1);control.LimTabla(modelo2);CodigoPro="";
     lbProducto.setText(" ");BuscarProductos();
    }

    public void BuscarProductos() {
     control.Sql = "select idProducto ,nomctlg,codbrr,seri from vtaprodcutostotal "
     + "where  (sede='"+Controlador.sede+"') and (nomctlg like'%"+ txtBuscar.getText() 
     + "%' or seri like'%"+txtBuscar.getText() + "%'"+ " or codbrr like'%" + txtBuscar.getText() 
     + "%')";control.LlenarJTabla(modelo, control.Sql, 4);
    }
    public void BuscarCaracteristica(String cod) {
     control.LimTabla(modelo1);
     control.Sql = "SELECT idCaracteriticas,descripcion FROM caracteristicas_productos "
     + "where idproducto='"+cod+"';";control.LlenarJTabla(modelo1, control.Sql, 2);
    }
    public void BuscarCaracteristicaSimilares(String nombre) {
     control.LimTabla(modelo2);
     control.Sql="SELECT nomlna FROM ver_linea_prodcuto  where nomctlg='"+nombre+"' limit 1;";
     String a=control.DevolverRegistroDto(control.Sql,1);
     control.Sql = "SELECT idcaracteriticas, descripcion FROM caracteristicas_productos "
     + "where nomlna like'%"+a+"%'  group by descripcion;"; control.LlenarJTabla(modelo2, control.Sql, 2);
    }
    public void SelecionarProducto() {
     txtCarateristica.setEnabled(true);
     CodigoPro=modelo.getValueAt(tbProducto.getSelectedRow(), 0  ).toString();
     lbProducto.setText(modelo.getValueAt(tbProducto.getSelectedRow(), 1).toString());
     BuscarCaracteristica(CodigoPro);BuscarCaracteristicaSimilares(modelo.getValueAt(tbProducto.getSelectedRow(),
     1).toString());txtCarateristica.grabFocus();
    }
    public void AgragarCaracteistica(String codi){
     if(txtCarateristica.getText().trim().length()>0){
      control.Sql="SELECT InsertaCaracteristica('"+txtCarateristica.getText()+"')";
      CodigoCaracteriticaRecuperada=control.DevolverRegistroDto(control.Sql,1);
      control.Sql="insert into carateristicasproducto (idProducto,idCaracteriticas) values('"+CodigoPro+"','"+CodigoCaracteriticaRecuperada+"');";
      control.CrearRegistro(control.Sql);control.LimTabla(modelo1);BuscarCaracteristica(codi);
      BuscarCaracteristicaSimilares(lbProducto.getText());
      txtCarateristica.setText("");txtCarateristica.grabFocus();
     }   
     else
      JOptionPane.showMessageDialog(null, "Ingrese una Caracteristica");     
    }
    public void EditarCaracteristica(){
     if(bModificar.getText().compareTo("Editar")==0){
      if(tbCaracteristicas.getSelectedRowCount()==1){
       codCara=modelo1.getValueAt(tbCaracteristicas.getSelectedRow(), 0).toString();
       txtCarateristica.setText(modelo1.getValueAt(tbCaracteristicas.getSelectedRow(), 
       1).toString());tbProducto.setEnabled(false);bAgregar.setEnabled(false);
       bEliminar.setEnabled(false);bModificar.setText("Grabar");txtCarateristica.grabFocus();
      }
      else{
       JOptionPane.showMessageDialog(null,"Seleccione una Caracteristica de este producto para editarlo!!");
      }
     }
     else{
      if(txtCarateristica.getText().trim().length()>0){
       control.Sql="update caracteriticas set descripcion ='"+txtCarateristica.getText()+
       "' where idCaracteriticas='"+codCara+"';";
       control.EditarRegistro(control.Sql);control.LimTabla(modelo1);
       BuscarCaracteristica(CodigoPro);control.LimTabla(modelo2);
       BuscarCaracteristicaSimilares(lbProducto.getText());
       bAgregar.setEnabled(true);bModificar.setText("Editar");
       tbProducto.setEnabled(true);bEliminar.setEnabled(true);
       txtCarateristica.setText("");txtCarateristica.grabFocus();codCara="";
      }
      else{
       JOptionPane.showMessageDialog(null, "Ingrese Una caracteristicas");
      }
     }
    }
    public void cancelar(){
     txtBuscar.setText("");control.LimTabla(modelo);MostrarProductos();
     txtBuscar.grabFocus();control.LimTabla(modelo2);control.LimTabla(modelo1);
     lbProducto.setText(" ");CodigoPro="";codCara="";CodigoCaracteriticaRecuperada="";
     tbProducto.setEnabled(true);bAgregar.setEnabled(true);bModificar.setText("Editar");
     txtCarateristica.setText("");bEliminar.setEnabled(true);txtCarateristica.setEnabled(false);
   }
    public void AgregarCaracteristica(){
        if(tbCaracteristicasSimilares.getSelectedRowCount()==1){
            if(veri(CodigoPro, modelo2.getValueAt(tbCaracteristicasSimilares.getSelectedRow(), 0).toString())){
                control.Sql="insert into carateristicasproducto (idProducto,idCaracteriticas) values('"+CodigoPro+"','"+modelo2.getValueAt(tbCaracteristicasSimilares.getSelectedRow(), 0).toString()+"');";
                control.CrearRegistro(control.Sql);
                BuscarCaracteristica(CodigoPro);
                BuscarCaracteristicaSimilares(lbProducto.getText());
            }else{
                JOptionPane.showMessageDialog(null, "El producto ya tiene esa caracteristica!!");
            }
        }
    }
    public boolean veri(String codpor, String codcar){
        boolean a= false;
        control.Sql="select count(*) from carateristicasproducto where idProducto='"+codpor+"' and idCaracteriticas='"+codcar+"';";
        if(Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1))==0){
            a=true;
        }
        return a;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbCaracteristicas = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbCaracteristicasSimilares = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCarateristica = new javax.swing.JTextField();
        bAgregar = new javax.swing.JButton();
        bModificar = new javax.swing.JButton();
        bEliminar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lbProducto = new javax.swing.JLabel();
        bSalir = new javax.swing.JButton();
        bSalir1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbProducto = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(51, 153, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tbCaracteristicas.setForeground(new java.awt.Color(0, 51, 102));
        tbCaracteristicas.setModel(new javax.swing.table.DefaultTableModel(
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
        tbCaracteristicas.setName("tbCaracteristicas"); // NOI18N
        tbCaracteristicas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCaracteristicasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbCaracteristicas);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 40, 460, 170));

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        tbCaracteristicasSimilares.setForeground(new java.awt.Color(0, 51, 102));
        tbCaracteristicasSimilares.setModel(new javax.swing.table.DefaultTableModel(
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
        tbCaracteristicasSimilares.setName("tbCaracteristicasSimilares"); // NOI18N
        tbCaracteristicasSimilares.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCaracteristicasSimilaresMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbCaracteristicasSimilares);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 490, 170));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Lista de Caracteristicas de este Productos");
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 290, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Lista de Caracteristicas de Productos de misma Linea");
        jLabel6.setName("jLabel6"); // NOI18N
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 290, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 1000, 222));

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Caracterisica:");
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 80, -1, -1));

        txtCarateristica.setName("txtCarateristica"); // NOI18N
        txtCarateristica.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCarateristicaKeyReleased(evt);
            }
        });
        jPanel2.add(txtCarateristica, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 76, 430, -1));

        bAgregar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bAgregar.setForeground(new java.awt.Color(0, 51, 102));
        bAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Add.png"))); // NOI18N
        bAgregar.setMnemonic('a');
        bAgregar.setText("Agregar");
        bAgregar.setName("bAgregar"); // NOI18N
        bAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAgregarActionPerformed(evt);
            }
        });
        jPanel2.add(bAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 60, 110, 40));

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
        jPanel2.add(bModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 70, 108, 40));

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
        jPanel2.add(bEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 70, 108, 40));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Producto");
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 23, -1, -1));

        lbProducto.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbProducto.setForeground(new java.awt.Color(0, 51, 102));
        lbProducto.setText(" ");
        lbProducto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbProducto.setName("lbProducto"); // NOI18N
        jPanel2.add(lbProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 18, 550, -1));

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setForeground(new java.awt.Color(0, 51, 102));
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bSalir.setMnemonic('c');
        bSalir.setText("Cancelar");
        bSalir.setName("bSalir"); // NOI18N
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel2.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 20, 108, 40));

        bSalir1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir1.setForeground(new java.awt.Color(0, 51, 102));
        bSalir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir1.setMnemonic('s');
        bSalir1.setText("Salir");
        bSalir1.setName("bSalir1"); // NOI18N
        bSalir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalir1ActionPerformed(evt);
            }
        });
        jPanel2.add(bSalir1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, 108, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 1000, 115));

        jPanel3.setBackground(new java.awt.Color(51, 153, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agregar caractersiticas a los Productos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tbProducto.setForeground(new java.awt.Color(0, 51, 102));
        tbProducto.setModel(new javax.swing.table.DefaultTableModel(
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
        tbProducto.setName("tbProducto"); // NOI18N
        tbProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProductoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbProducto);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 48, 990, 170));

        txtBuscar.setName("txtBuscar"); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel3.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 25, 867, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Buscar Producto:");
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 28, -1, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 230));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed

       cancelar();


    }//GEN-LAST:event_bSalirActionPerformed

    private void tbProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductoMouseClicked
        if (evt.getClickCount() == 2) {
            SelecionarProducto();
        }
    }//GEN-LAST:event_tbProductoMouseClicked

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        MostrarProductos();
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void bAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAgregarActionPerformed
        if(lbProducto.getText().trim().length()>0){
            AgragarCaracteistica(modelo.getValueAt(tbProducto.getSelectedRow(), 0).toString());
        }
    }//GEN-LAST:event_bAgregarActionPerformed

    private void bModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarActionPerformed
        EditarCaracteristica();
    }//GEN-LAST:event_bModificarActionPerformed

    private void bSalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalir1ActionPerformed
        dispose();
    }//GEN-LAST:event_bSalir1ActionPerformed

    private void tbCaracteristicasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCaracteristicasMouseClicked
        if(evt.getClickCount()==2){
            bModificar.setText("Editar");
            bModificar.doClick();
        }
    }//GEN-LAST:event_tbCaracteristicasMouseClicked

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
            if(evt.getKeyChar()==10){
                if(tbProducto.getRowCount()==1){
                    tbProducto.selectAll();
                    SelecionarProducto();
                }
            }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void bEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarActionPerformed
       if(tbCaracteristicas.getSelectedRowCount()==1){
           if(JOptionPane.showConfirmDialog(null, "Desea Eliminar esta Caracteristica!!","",JOptionPane.YES_NO_OPTION)==0){
               control.Sql="delete from carateristicasproducto where idCaracteriticas='"+modelo1.getValueAt(tbCaracteristicas.getSelectedRow(), 0).toString()+"' "
                       + "and idproducto='"+CodigoPro+"';";
               control.EliminarRegistro(control.Sql);
               control.LimTabla(modelo1);
              // System.out.print(control.Sql );
               BuscarCaracteristica(CodigoPro);
               BuscarCaracteristicaSimilares(lbProducto.getText());
           }else{
               JOptionPane.showMessageDialog(null, "Tiene que seleccionar una caracteristica de uhn producto seleccionado!!!");
           }
       }
    }//GEN-LAST:event_bEliminarActionPerformed

    private void tbCaracteristicasSimilaresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCaracteristicasSimilaresMouseClicked
       if(evt.getClickCount()==2){
            AgregarCaracteristica();
       }
    }//GEN-LAST:event_tbCaracteristicasSimilaresMouseClicked

    private void txtCarateristicaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCarateristicaKeyReleased
    control.Mayusculas(txtCarateristica);
    }//GEN-LAST:event_txtCarateristicaKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAgregar;
    private javax.swing.JButton bEliminar;
    private javax.swing.JButton bModificar;
    private javax.swing.JButton bSalir;
    private javax.swing.JButton bSalir1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbProducto;
    private javax.swing.JTable tbCaracteristicas;
    private javax.swing.JTable tbCaracteristicasSimilares;
    private javax.swing.JTable tbProducto;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCarateristica;
    // End of variables declaration//GEN-END:variables
}
