/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ventanas;
import javax.swing.ImageIcon;import Clases.*;
import javax.swing.JOptionPane;
/**** @author Ing. Miguel Angel Silva Zapata */


public class ListaProductosVendidos extends javax.swing.JInternalFrame {
     Controlador control=new Controlador();Mimodelo modelo=new Mimodelo();
    /**************** Creates new form ListaProductosVendidos  ****************/
    public ListaProductosVendidos() {
        initComponents();setTitle("Los Productos Vendidos");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        modelo.setColumnIdentifiers(new String[] {"ID","Producto","Unidad","Cantidad"});
        T_Productos.setModel(modelo); control.hideTableColumn(T_Productos, 0);
        control.setWidthTableColumn(T_Productos, 350, 1);
        MostrarProductos();
    }
    public void MostrarProductos(){
     control.Sql="select cp.codctlg,concat(md.nommod,' ',mrc.nommrc,' ',cp.nomctlg) Producto,ud.nomuni Unidad, count(p.idproducto) Cantidad\n" +
    "from producto p inner join catalogoproducto cp on cp.codctlg=p.catalogoproducto_codctlg\n" +
    "inner join modelo md on md.idmodelos=cp.idmodelos inner join marca mrc on mrc.idmarca=cp.idmarca\n" +
    "inner join unidad ud on ud.idunidad=cp.idunidad where p.idproducto in (select producto_idproducto from venta_producto) and (md.nommod like'%"
     +txBuscar.getText()+"%' or  mrc.nommrc like'%"+txBuscar.getText()+"% ' or cp.nomctlg like'%"+txBuscar.getText()+"%')" +
    "group by cp.codctlg order by 4 desc;"  ;control.LlenarJTabla(modelo, control.Sql,4);
    }
    public void Imprimir(){
     if(T_Productos.getRowCount()>0){
      control.impresor.Imprimircon1Parametros("Lista de Productos Vendidos", "Rep_ProductosVendidos","Buscar",txBuscar.getText());
     }  
     else
         JOptionPane.showMessageDialog(null, "No hay datos para imprimir");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        T_Productos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txBuscar = new javax.swing.JTextField();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Productos Vendidos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton1.setMnemonic('s');
        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 110, 45));

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Print.png"))); // NOI18N
        jButton2.setMnemonic('i');
        jButton2.setText("Imprimir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 110, 45));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 400, 280, 65));

        T_Productos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(T_Productos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 870, 340));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setText("Buscar");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 40, -1, -1));

        txBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txBuscarKeyReleased(evt);
            }
        });
        jPanel1.add(txBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 36, 810, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 480));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
    private void txBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarKeyReleased
     MostrarProductos();
    }//GEN-LAST:event_txBuscarKeyReleased
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
     Imprimir();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable T_Productos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txBuscar;
    // End of variables declaration//GEN-END:variables
}