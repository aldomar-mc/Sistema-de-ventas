/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;
import Clases.*;
import static Ventanas.Ventas.lbCliente;
import static Ventanas.Ventas.lbTotalVenta;
import static Ventanas.Ventas.txtBucarCliente;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Silva
 */
public class Ensamblaje extends javax.swing.JInternalFrame {
    
    InfoGeneral info= new InfoGeneral();
    Controlador control = new Controlador();
    Mimodelo modelo = new Mimodelo();
    Mimodelo modelo1 = new Mimodelo();
    public Ensamblaje() {
        initComponents();this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
    }

//public void llenarcbo(){
//    control.LlenarCombo(cbTipoComprobante, "SELECT * FROM tipocomprobante t", 2);
//}
     public void MostrarCliente(){
     BuscarCliente();   
    }    
    public void BuscarCliente(){
     control.Sql="SELECT idcliente, nomclie,dir, numident FROM vta_cliente v where nomclie like'"+
     txtBucarCliente.getText()+"%' or numident like'"+
     txtBucarCliente.getText()+"%'";
     control.LlenarJTabla(modelo,control.Sql,4); 
     
 //    JOptionPane.showMessageDialog(null,control.Sql);
    }
    public void MostrarProducto(){
     BuscarProducto();   
    }    
    public void BuscarProducto(){
    //SELECT idproducto, nomctlg,seri,codbrr, nommrc,precVenta  FROM producto_venta
     control.Sql="select idproducto, nomctlg, seri, codbrr, nommrc, precVenta  FROM producto_venta where estdo='Disponible' and (nomctlg like'"+
     txtBucarProducto.getText()+"%' or seri like '"+txtBucarProducto.getText()+
     "%' or codbrr like '"+txtBucarProducto.getText()+"%') and idproducto not in(select Producto_idProducto from venta_producto);";
     
     control.LlenarJTabla(modelo1,control.Sql,6); 
    
 //    JOptionPane.showMessageDialog(null,control.Sql);
    }
       public void SelecionarCliente(){
        //Cancelar();
//        control.LimTabla(modelo2);
//        lbCliente.setText("");
//        lbProducto.setText(" ");
//        lbTotalVenta.setText("0.00");
//        totalCompra=0;
//        //montototal=0.00;
//        //lbMontoFinal.setText(""+montototal);
//        lbCliente.setText(modelo.getValueAt(tClientes.getSelectedRow(), 1).toString());
//        info.setPass(modelo.getValueAt(tClientes.getSelectedRow(), 0).toString());
//       // codCliente=info.getPass();
//        controlCliente++;
//        txtBucarProducto.grabFocus();
//        //cbTipoComprobante.setSelectedIndex(-1);
        //ppp=0;
        //lbSerie.setText("");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tClientes = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtBucarCliente = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tProductos = new javax.swing.JTable();
        txtBucarProducto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbCliente = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbCliente1 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Selecionar Cliente"));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscar Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N

        tClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tClientes);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Buscar Cliente:");

        txtBucarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBucarClienteActionPerformed(evt);
            }
        });
        txtBucarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBucarClienteKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBucarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBucarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleccionar producto con stock", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tProductos.setForeground(new java.awt.Color(0, 51, 102));
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
        tProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tProductosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tProductosMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(tProductos);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 660, 110));

        txtBucarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBucarProductoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBucarProductoKeyReleased(evt);
            }
        });
        jPanel2.add(txtBucarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 25, 480, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Buscar");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Cliente");

        lbCliente.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbCliente.setForeground(new java.awt.Color(0, 51, 102));
        lbCliente.setText(" ");
        lbCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Cliente");

        lbCliente1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbCliente1.setForeground(new java.awt.Color(0, 51, 102));
        lbCliente1.setText(" ");
        lbCliente1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(lbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(lbCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(lbCliente))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(lbCliente1)))
                .addGap(0, 63, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBucarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBucarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBucarClienteActionPerformed

    private void txtBucarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBucarClienteKeyReleased
        MostrarCliente();
    }//GEN-LAST:event_txtBucarClienteKeyReleased

    private void tProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProductosMouseClicked
//        if(evt.getClickCount()>=2){
//            if( controlCliente>0 || info.getControlCliente()==1 ){
//                // System.out.println(controlCliente+"ver ver");
//                SelecionarProducto();
//            }else{
//                // System.out.println(controlCliente);
//                JOptionPane.showMessageDialog(null, "Tiene que Selecionar un Cliente");
//            }
//        }
    }//GEN-LAST:event_tProductosMouseClicked

    private void tProductosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProductosMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tProductosMouseEntered

    private void txtBucarProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBucarProductoKeyPressed

//        if(evt.getKeyChar()==10){
//
//            if(tProductos.getRowCount()==1){
//                tProductos.selectAll();
//                SelecionarProducto();
//            }
//        }

    }//GEN-LAST:event_txtBucarProductoKeyPressed

    private void txtBucarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBucarProductoKeyReleased

        MostrarProducto();

    }//GEN-LAST:event_txtBucarProductoKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbCliente;
    private javax.swing.JLabel lbCliente1;
    private javax.swing.JTable tClientes;
    public static javax.swing.JTable tProductos;
    public static javax.swing.JTextField txtBucarCliente;
    private javax.swing.JTextField txtBucarProducto;
    // End of variables declaration//GEN-END:variables
}
