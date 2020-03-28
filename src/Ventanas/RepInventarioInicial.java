/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

/**
 *
 * @author Miguel Silva
 */
import Clases.*;import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
public class RepInventarioInicial extends javax.swing.JInternalFrame {
    Controlador control=new Controlador(); Mimodelo modelo=new Mimodelo();
    String sed="";
    public RepInventarioInicial() {
     initComponents();setTitle("El Inventario Inicial");
     this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     tProductos.setModel(modelo);     
     modelo.setColumnIdentifiers(new String[]{"Código","Producto","Cantidad","Marca","Precio","F_Ingreso"});
     
     tProductos.getColumnModel().getColumn(1).setPreferredWidth(300);
     tProductos.getColumnModel().getColumn(2).setPreferredWidth(200);
     control.LlenarCombo(jComboBox1,"select * from sede order by nomse", 2);
     jComboBox1.addItem("Todos");
     jButton4.setVisible(false);
     //control.forma_table_ver(modelo, tProductos);
     FormatoTabla ft = new FormatoTabla(1);
     tProductos.setDefaultRenderer(Object.class, ft);
     tProductos.getColumnModel().getColumn(0).setPreferredWidth(125);
     jComboBox1.setSelectedItem("Todos");
        Ver1();
     jComboBox1.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
          Ver1();
         }
     });
    }
    public void Imprimir(){
     if(tProductos.getRowCount()>0){
      sed=jComboBox1.getSelectedItem().toString();
      if(sed.equalsIgnoreCase("Todos")){
  //     control.impresor.ImprGeneral("InventarioInicial.jasper","Reporte de Inventario Inicial");  
          try {
              control.impresor.ImprInveInixSede("InventarioInicial.jasper", "Reporte de Inventario Inicial", sed);
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
      else{
          try {
                control.impresor.ImprInveInixSede("InventarioInicialPorSedes.jasper", "Reporte de Inventario Inicial", sed);
          } catch (Exception e) {
              e.printStackTrace();
          }
        
//       control.impresor.ImprInveInixSede("InventarioInicialPorSedes.jasper","Reporte de Inventario Inicial por Sedes",sed);      
      }      
     }  
     else{
      JOptionPane.showMessageDialog(rootPane,"No hay nada que imprimir");
      jComboBox1.grabFocus();
     }
    }
    public void Ver1(){
     String cdna="";   
     if(jComboBox1.getSelectedIndex()>=0){
      cdna=jComboBox1.getSelectedItem().toString();
      if(cdna.equalsIgnoreCase("Todos")){ 
       control.Sql="SELECT `p`.`codbrr` AS `Codigo`, `c`.`nomctlg` AS `Descripcion`, COUNT(p.Catalogoproducto_codctlg) AS Cantidad, `m`.`nommrc`     AS `Marca`, `p`.`precVenta`  AS `Precio`, `p`.`fecingralm` AS `Ingreso`, `pv`.`nompro`    AS `nompro`, `s`.`nomse`      AS `Sede`\n" +
"FROM `producto` `p`, `catalogoproducto` `c`, `proveedor` `pv`, `sede` `s`, `inventarioinicial` `iv`, `marca` `m`, `modelo` `lp`\n" +
"WHERE `p`.`Catalogoproducto_codctlg` = `c`.`codctlg` AND (`iv`.`idProducto` = `p`.`idProducto`) AND (`iv`.`idSede` = `s`.`idSede`) AND (`iv`.`idProveedor` = `pv`.`idProveedor`) AND (`m`.`idMarca` = `c`.`idMarca`) AND (`lp`.`idModelos` = `c`.`idModelos`) AND (`pv`.`idProveedor` = 1)\n" +
"AND (c.`nomctlg` LIKE'"+jTextField1.getText()+"%' OR m.`nommrc` LIKE '"+jTextField1.getText()+"%')\n" +
"GROUP BY Descripcion";
      }
      else{
       control.Sql="SELECT `p`.`codbrr` AS `Codigo`, `c`.`nomctlg` AS `Descripcion`, COUNT(p.Catalogoproducto_codctlg) AS Cantidad, `m`.`nommrc`     AS `Marca`, `p`.`precVenta`  AS `Precio`, `p`.`fecingralm` AS `Ingreso`, `pv`.`nompro`    AS `nompro`, `s`.`nomse`      AS `Sede`\n" +
"FROM `producto` `p`, `catalogoproducto` `c`, `proveedor` `pv`, `sede` `s`, `inventarioinicial` `iv`, `marca` `m`, `modelo` `lp`\n" +
"WHERE `p`.`Catalogoproducto_codctlg` = `c`.`codctlg` AND (`iv`.`idProducto` = `p`.`idProducto`) AND (`iv`.`idSede` = `s`.`idSede`) AND (`iv`.`idProveedor` = `pv`.`idProveedor`) AND (`m`.`idMarca` = `c`.`idMarca`) AND (`lp`.`idModelos` = `c`.`idModelos`) AND (`pv`.`idProveedor` = 1)\n" +
"AND s.`nomse`='"+jComboBox1.getSelectedItem().toString()+"' AND (c.`nomctlg` LIKE'"+jTextField1.getText()+"%' OR m.`nommrc` LIKE '"+jTextField1.getText()+"%')\n" +
"GROUP BY Descripcion";
      }
      control.LlenarJTabla(modelo, control.Sql,6);
     } 
     else{
      JOptionPane.showMessageDialog(rootPane,"Seleccione primero el criterio");
      control.LimTabla(modelo);
     }         
    }
    public void Ver(){
     String cdna="";   
     if(jComboBox1.getSelectedIndex()>=0){
      cdna=jComboBox1.getSelectedItem().toString();
      if(cdna.equalsIgnoreCase("Todos")){
       control.Sql="SELECT `p`.`codbrr` AS `Codigo`, `c`.`nomctlg` AS `Descripcion`, COUNT(p.Catalogoproducto_codctlg) AS Cantidad, `m`.`nommrc`     AS `Marca`, `p`.`precVenta`  AS `Precio`, `p`.`fecingralm` AS `Ingreso`, `pv`.`nompro`    AS `nompro`, `s`.`nomse`      AS `Sede`\n" +
"FROM `producto` `p`, `catalogoproducto` `c`, `proveedor` `pv`, `sede` `s`, `inventarioinicial` `iv`, `marca` `m`, `modelo` `lp`\n" +
"WHERE `p`.`Catalogoproducto_codctlg` = `c`.`codctlg` AND (`iv`.`idProducto` = `p`.`idProducto`) AND (`iv`.`idSede` = `s`.`idSede`) AND (`iv`.`idProveedor` = `pv`.`idProveedor`) AND (`m`.`idMarca` = `c`.`idMarca`) AND (`lp`.`idModelos` = `c`.`idModelos`) AND (`pv`.`idProveedor` = 1) \n" +
"GROUP BY Descripcion";         
      }
      else{
       control.Sql="SELECT `p`.`codbrr` AS `Codigo`, `c`.`nomctlg` AS `Descripcion`, COUNT(p.Catalogoproducto_codctlg) AS Cantidad, `m`.`nommrc`     AS `Marca`, `p`.`precVenta`  AS `Precio`, `p`.`fecingralm` AS `Ingreso`, `pv`.`nompro`    AS `nompro`, `s`.`nomse`      AS `Sede`\n" +
"FROM `producto` `p`, `catalogoproducto` `c`, `proveedor` `pv`, `sede` `s`, `inventarioinicial` `iv`, `marca` `m`, `modelo` `lp`\n" +
"WHERE `p`.`Catalogoproducto_codctlg` = `c`.`codctlg` AND (`iv`.`idProducto` = `p`.`idProducto`) AND (`iv`.`idSede` = `s`.`idSede`) AND (`iv`.`idProveedor` = `pv`.`idProveedor`) AND (`m`.`idMarca` = `c`.`idMarca`) AND (`lp`.`idModelos` = `c`.`idModelos`) AND (`pv`.`idProveedor` = 1) AND s.`nomse` = '"+
       jComboBox1.getSelectedItem().toString()+"'\n" +
"GROUP BY Descripcion";      
      }    
      control.LlenarJTabla(modelo, control.Sql,6);
     } 
     else{
      JOptionPane.showMessageDialog(rootPane,"Seleccione primero el criterio");
      control.LimTabla(modelo);
     }         
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tProductos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Selección de Criterios", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("Criterio");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 10));

        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 25, 640, -1));

        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(51, 0, 153));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/emotion_happy.png"))); // NOI18N
        jButton4.setMnemonic('v');
        jButton4.setText("Ver");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 22, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 60));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Productos del Inventario Inicial", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(51, 0, 153))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tProductos);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 790, 290));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setText("Buscar");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 44, -1, 10));

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 37, 730, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 810, 370));

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
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 110, 40));

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
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 110, 40));

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(51, 0, 153));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        jButton3.setMnemonic('c');
        jButton3.setText("Cancelar");
        jButton3.setMargin(new java.awt.Insets(2, 1, 2, 14));
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 110, 40));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 810, 60));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
     Ver();
    }//GEN-LAST:event_jButton4ActionPerformed
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
     Imprimir();
    }//GEN-LAST:event_jButton2ActionPerformed
    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
     Ver1();
    }//GEN-LAST:event_jTextField1KeyReleased
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tProductos;
    // End of variables declaration//GEN-END:variables
}
