/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

/**
 *
 * @author Miguel Silva
 */
import Clases.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
public class DistribuirProductos extends javax.swing.JInternalFrame {
    /***************************LOS ATRIBUTOS***************************************************/
    Controlador control=new Controlador();
    Mimodelo modelo1=new Mimodelo(); Mimodelo modelo2=new Mimodelo();
    /***************************FIN LOS ATRIBUTOS***********************************************/
    
    /***************************LOS METODOS***************************************************/
    public DistribuirProductos() {
      initComponents();setTitle("                                               Distribución de productos a las sedes");
      TProductosAlmacen.setModel(modelo1);TProductoDistribuido.setModel(modelo2);
      this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
      
      modelo1.setColumnIdentifiers(new String[] {"ID","Código","Producto","Marca","Serie"});
      TProductosAlmacen.getColumnModel().getColumn(1).setPreferredWidth(100);
      TProductosAlmacen.getColumnModel().getColumn(2).setPreferredWidth(350);
      TProductosAlmacen.getColumnModel().getColumn(3).setPreferredWidth(200);
      
      modelo2.setColumnIdentifiers(new String[] {"ID","Código","Producto","Marca","Serie","Sede"});
      TProductoDistribuido.getColumnModel().getColumn(1).setPreferredWidth(100);
      TProductoDistribuido.getColumnModel().getColumn(2).setPreferredWidth(350);
      TProductoDistribuido.getColumnModel().getColumn(3).setPreferredWidth(200);
      MostrarSedes();MostrarProductosenAlmacen();MostrarProductosenSede();
      control.forma_table_ver(modelo1, TProductosAlmacen);
      control.forma_table_ver(modelo2, TProductoDistribuido);
    }
    public void MostrarSedes(){
     control.Sql="select * from sede where idsede<>1";
     control.LlenarCombo(jComboBox1,control.Sql,2);
    }
    public void MostrarProductosenAlmacen(){
     control.Sql="select id,Codigo,Producto,Marca,Serie from vta_maestra_producto where "
     + "(Codigo like'"+txbuscar1.getText()+"%' or Producto like '"+txbuscar1.getText()+
     "%' or Marca like '"+txbuscar1.getText()+"%')"+ " and (estado='Disponible' and sede='Almacen')";   
     control.LlenarJTabla(modelo1,control.Sql,5);
    }
    public void MostrarProductosenSede(){
     control.Sql="select id,Codigo,Producto,Marca,Serie,Sede from vta_maestra_producto where "
     + "(Codigo like'"+txbuscar1.getText()+"%' or Producto like '"+txbuscar1.getText()+
     "%' or Marca like '"+txbuscar1.getText()+"%')"+ " and (estado='Disponible' and sede<>'Almacen')";        
     control.LlenarJTabla(modelo2,control.Sql,6);
    }
    public void DistribuiraSedes(){     
     if(jComboBox1.getSelectedIndex()==-1){
      JOptionPane.showMessageDialog(rootPane, "Seleccione una sede");
      return;
     }         
     control.fila=TProductosAlmacen.getSelectedRow();
     if(control.fila>=0){         
      control.Sql="call DistribuiralaSedes ('"+jComboBox1.getSelectedItem().toString()
      +"','"+TProductosAlmacen.getValueAt(control.fila,0).toString()+"')";      
      control.CrearRegistro(control.Sql);MostrarProductosenAlmacen();MostrarProductosenSede();
     }
    }
    public void VolverAlmacen(){
     control.fila=TProductoDistribuido.getSelectedRow();
     if(control.fila>=0){
      control.Sql="call DistribuiralaSedes ('Almacen','"+TProductoDistribuido.getValueAt(control.fila,0).toString()+"')";
      control.CrearRegistro(control.Sql);MostrarProductosenAlmacen();MostrarProductosenSede();
     }   
    }
    /***************************FIN LOS METODOS*************************************************/
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TProductoDistribuido = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txbuscar2 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TProductosAlmacen = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txbuscar1 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de las sedes", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setText("Las Sedes");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 780, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 60));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos en almacen", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TProductoDistribuido.setModel(new javax.swing.table.DefaultTableModel(
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
        TProductoDistribuido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TProductoDistribuidoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TProductoDistribuido);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 880, 220));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("Buscar");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));
        jPanel2.add(txbuscar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 25, 820, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 900, 280));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos en almacen", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TProductosAlmacen.setModel(new javax.swing.table.DefaultTableModel(
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
        TProductosAlmacen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TProductosAlmacenMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TProductosAlmacen);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 880, 170));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("Buscar");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        txbuscar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txbuscar1KeyReleased(evt);
            }
        });
        jPanel3.add(txbuscar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 25, 820, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 900, 230));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 153));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton1.setMnemonic('s');
        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 10, 130, 40));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 570, 900, 60));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txbuscar1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txbuscar1KeyReleased
     MostrarProductosenAlmacen();
    }//GEN-LAST:event_txbuscar1KeyReleased

    private void TProductosAlmacenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TProductosAlmacenMouseClicked
       DistribuiraSedes();
    }//GEN-LAST:event_TProductosAlmacenMouseClicked
    private void TProductoDistribuidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TProductoDistribuidoMouseClicked
       VolverAlmacen();     
    }//GEN-LAST:event_TProductoDistribuidoMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TProductoDistribuido;
    private javax.swing.JTable TProductosAlmacen;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txbuscar1;
    private javax.swing.JTextField txbuscar2;
    // End of variables declaration//GEN-END:variables
}
