
package Ventanas;
import Clases.*;import javax.swing.ImageIcon;
//import javax.swing.JOptionPane;

public class VerProductosComprados extends javax.swing.JInternalFrame {
 Controlador control= new Controlador();Mimodelo modelo=  new Mimodelo();
 String datos[]= new String[7];InfoGeneral info= new InfoGeneral();

    /** Creates new form VerProductosComprados */
public VerProductosComprados() {
  initComponents();setTitle("Los Productos Comprados");this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
  tbProductos.setModel(modelo);modelo.setColumnIdentifiers(new String[] {"Código","Producto", "Unidad", "Cantidad", "Costo"});
  control.setWidthTableColumn(tbProductos, 200, 1);Mostar();
}
public void Mostar(){
 control.Sql="select cpr.codbarra C_Barra,concat(md.nommod,' ',mrc.nommrc,' ',cpr.nomctlg) Producto,ud.nomuni Unidad,count(pd.idproducto) Cantidad,pd.Costo\n" +
"from compra cp inner join doc_compra dc on cp.idcompra=dc.idcompra inner join producto pd on\n" +
"pd.idDoc_compra=dc.idDoc_compra inner join catalogoproducto cpr on cpr.codctlg=pd.catalogoproducto_codctlg inner join\n" +
"modelocatalogo mdc on mdc.codctlg=cpr.codctlg inner join modelo md on md.idmodelos=mdc.idmodelos inner join marca mrc on\n" +
"mrc.idmarca=cpr.idmarca inner join unidad ud on ud.idunidad=cpr.idunidad " +
"where cp.idcompra='"+info.getComp().trim()+"' and (md.nommod like'%"+txtBuscar.getText()+"%' or mrc.nommrc like '%"+
 txtBuscar.getText()+"%' or cpr.nomctlg like '%"+txtBuscar.getText()+"' or cpr.codbarra like '%"+txtBuscar.getText()+"%') "
+ "group by 1 order by 1";control.LlenarJTabla(modelo,control.Sql,5);
}

public void Imprimir(){
 if(tbProductos.getRowCount()>0){
  control.impresor.Imprimircon6Parametros("Detalle de Compra","DetalleCompra","idCompra","proveedor","f_compra","t_comprobante",
  "monto_compra","numero",info.getComp(),lbProveedor.getText(),lbFechaCom.getText(),lbTipoCom.getText(),
  lbcosto.getText(), lbNumero.getText());
 }   
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbProductos = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbProveedor = new javax.swing.JLabel();
        lbFechaCom = new javax.swing.JLabel();
        lbTipoCom = new javax.swing.JLabel();
        lbNumero = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        lbcosto = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bImprimir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tbProductos.setForeground(new java.awt.Color(0, 51, 102));
        tbProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        tbProductos.setName("tbProductos"); // NOI18N
        tbProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbProductos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 890, 350));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("Proveedor:");
        jLabel2.setName("jLabel2"); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 25, -1, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setText("Fecha Compra:");
        jLabel3.setName("jLabel3"); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 57, -1, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setText("Tipo Comprobante:");
        jLabel4.setName("jLabel4"); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, -1, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setText("Numero:");
        jLabel6.setName("jLabel6"); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 92, -1, -1));

        lbProveedor.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbProveedor.setForeground(new java.awt.Color(0, 51, 102));
        lbProveedor.setText(" ");
        lbProveedor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbProveedor.setName("lbProveedor"); // NOI18N
        getContentPane().add(lbProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 600, -1));

        lbFechaCom.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbFechaCom.setForeground(new java.awt.Color(0, 51, 102));
        lbFechaCom.setText(" ");
        lbFechaCom.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbFechaCom.setName("lbFechaCom"); // NOI18N
        getContentPane().add(lbFechaCom, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 55, 110, -1));

        lbTipoCom.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbTipoCom.setForeground(new java.awt.Color(0, 51, 102));
        lbTipoCom.setText(" ");
        lbTipoCom.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbTipoCom.setName("lbTipoCom"); // NOI18N
        getContentPane().add(lbTipoCom, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 110, -1));

        lbNumero.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbNumero.setForeground(new java.awt.Color(0, 51, 102));
        lbNumero.setText(" ");
        lbNumero.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbNumero.setName("lbNumero"); // NOI18N
        getContentPane().add(lbNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 90, 110, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("Buscar");
        jLabel7.setName("jLabel7"); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 142, -1, -1));

        txtBuscar.setName("txtBuscar"); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        getContentPane().add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 138, 820, -1));

        lbcosto.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbcosto.setForeground(new java.awt.Color(0, 51, 102));
        lbcosto.setText(" ");
        lbcosto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbcosto.setName("lbcosto"); // NOI18N
        getContentPane().add(lbcosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 55, 110, 20));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setText("Costo Productos:");
        jLabel9.setName("jLabel9"); // NOI18N
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 57, -1, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de la Compra", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel1.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(765, 70, 115, 40));

        bImprimir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bImprimir.setForeground(new java.awt.Color(0, 51, 102));
        bImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Print.png"))); // NOI18N
        bImprimir.setMnemonic('i');
        bImprimir.setText("Imprimir");
        bImprimir.setName("bImprimir"); // NOI18N
        bImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bImprimirActionPerformed(evt);
            }
        });
        jPanel1.add(bImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(765, 20, 115, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 905, 120));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Productos Comprados", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(51, 0, 102))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 122, 907, 395));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
      Mostar();
    }//GEN-LAST:event_txtBuscarKeyReleased
    private void tbProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductosMouseClicked
        
    }//GEN-LAST:event_tbProductosMouseClicked
    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
      dispose();
    }//GEN-LAST:event_bSalirActionPerformed
    private void bImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bImprimirActionPerformed
      Imprimir();
    }//GEN-LAST:event_bImprimirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bImprimir;
    private javax.swing.JButton bSalir;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lbFechaCom;
    public javax.swing.JLabel lbNumero;
    public static javax.swing.JLabel lbProveedor;
    public static javax.swing.JLabel lbTipoCom;
    public javax.swing.JLabel lbcosto;
    public static javax.swing.JTable tbProductos;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
