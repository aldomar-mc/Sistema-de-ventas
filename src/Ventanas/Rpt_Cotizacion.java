
package Ventanas;
import Clases.*;import com.toedter.calendar.JDateChooser;
import java.beans.PropertyChangeEvent;import java.lang.reflect.Field;
import java.util.Date;import javax.swing.ImageIcon;
public class Rpt_Cotizacion extends javax.swing.JInternalFrame {
    
    Controlador control = new Controlador();Mimodelo modelo= new Mimodelo();
    IMPRIMIR imprime=new IMPRIMIR();String datos[]= new String[12];
    
    /** Creates new form Rpt_Ventas */
    public Rpt_Cotizacion() {
        initComponents(); 
        setTitle("Movimiento de Productos");
     this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     tbMovimiento.setModel(modelo);
     modelo.setColumnIdentifiers(new String[] {"Codigo","Proveedor","Comprobante","Serie","Fecha Ingreso","Producto","Codigo Producto","Costo Compra","Fecha Venta","Comprobante","Numero","Precio Venta"});
     tbMovimiento.getColumnModel().getColumn(0).setMaxWidth(0);
     tbMovimiento.getColumnModel().getColumn(0).setMinWidth(0);
     tbMovimiento.getColumnModel().getColumn(0).setPreferredWidth(0);
     tbMovimiento.getColumnModel().getColumn(1).setPreferredWidth(200);
     tbMovimiento.getColumnModel().getColumn(2).setPreferredWidth(80);  
     tbMovimiento.getColumnModel().getColumn(3).setPreferredWidth(100); 
     tbMovimiento.getColumnModel().getColumn(4).setPreferredWidth(100); 
     tbMovimiento.getColumnModel().getColumn(5).setPreferredWidth(200); 
     tbMovimiento.getColumnModel().getColumn(6).setPreferredWidth(100); 
    tbMovimiento.getColumnModel().getColumn(7).setPreferredWidth(100); 
    tbMovimiento.getColumnModel().getColumn(8).setPreferredWidth(100); 
    tbMovimiento.getColumnModel().getColumn(9).setPreferredWidth(100); 
    tbMovimiento.getColumnModel().getColumn(10).setPreferredWidth(100); 
    tbMovimiento.getColumnModel().getColumn(11).setPreferredWidth(100); 
    // BuscarCliente();
    
     jButton3.setVisible(false);
     fechaFi.setDate(new Date());
     fechaIn.setDate(new Date());
     VerPorFechas();
     FormatoTabla ft= new FormatoTabla(1);
     tbMovimiento.setDefaultRenderer(Object.class, ft);
     fechaIn.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if("date".equals(evt.getPropertyName())){
                    JDateChooser isse=(JDateChooser)evt.getSource();
                    boolean isdatasele=false;
                    try {
                        Field dateselfi=JDateChooser.class.getDeclaredField("dateSelect");
                        dateselfi.setAccessible(true);
                        isdatasele=dateselfi.getBoolean(isse);
                    } catch (Exception ignoreOrNot) {
                        //OptionPane.showMessageDialog(null, "Hoy es:"+control.Formato_Amd(fechaIni.getDate()));
                        //CargarDatos();
                       // BuscarCliente();
                        VerPorFechas();
                        
                    }
                }
            }
        });
     fechaFi.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
         if("date".equals(evt.getPropertyName())){
                    JDateChooser isse=(JDateChooser)evt.getSource();
                    boolean isdatasele=false;
                    try {
                        Field dateselfi=JDateChooser.class.getDeclaredField("dateSelect");
                        dateselfi.setAccessible(true);
                        isdatasele=dateselfi.getBoolean(isse);
                    } catch (Exception ignoreOrNot) {
                        //OptionPane.showMessageDialog(null, "Hoy es:"+control.Formato_Amd(fechaIni.getDate()));
                 //       CargarDatos();
                        //BuscarCliente();
                        VerPorFechas();
                        
                    }
                }
            }
        });
    }
    public void BuscarCliente(){
     control.LimTabla(modelo);
     control.Sql=" select idproducto, nompro, tipo, serie, fecingralm, "
     +" nomctlg,seri, codbrr, costo, fecvta,tipcompr, serieventa, prc "
     +" from registro_movimiento where (nomctlg like '%"+txtBuscar.getText()
     +"%' or codbrr like '%"+txtBuscar.getText()+"%' or seri like '%"+txtBuscar.getText()+"%');";
     try {
      control.Base.st= control.Base.conec.createStatement();
      control.Base.rt=control.Base.st.executeQuery(control.Sql);
      while(control.Base.rt.next()){
       datos[0]=control.Base.rt.getString(1);datos[1]=control.Base.rt.getString(2);
       datos[2]=control.Base.rt.getString(3);datos[3]=control.Base.rt.getString(4);
       datos[4]=control.Base.rt.getString(5);datos[5]=control.Base.rt.getString(6);
       datos[6]=control.Base.rt.getString(7);datos[7]=control.Base.rt.getString(8);
       datos[8]=control.Base.rt.getString(9);datos[9]=control.Base.rt.getString(10);
       datos[10]=control.Base.rt.getString(11);datos[11]=control.Base.rt.getString(12);
       datos[12]=control.Base.rt.getString(13);modelo.addRow(datos);
      }
     } catch (Exception e) {
            e.printStackTrace();
        }
 //    JOptionPane.showMessageDialog(null,control.Sql);
    }
    public void VerPorFechas(){
     control.LimTabla(modelo);
     //         control.Sql="select nompro, tipo, serie, fecingralm, nomctlg, seri,codbrr, costo, fecvta,tipcompr, serieventa, prc "
        //       + "from registro_movimiento where fecvta>='"+control.Formato_Amd(fechaIn.getDate()) +"' and fecvta<='"+control.Formato_Amd(fechaFi.getDate()) +"';";
     //       
     control.Sql="select p.idProducto,  pr.nompro, dc.tipo, dc.serie, p.fecingralm, ct.nomctlg, "
     + "p.codbrr, p.costo , v.fecvta, tip.tipcompr ,  co.nume, vp.prc  "+
     "from doc_compra dc, compra c, proveedor pr,producto p, venta v, " +
     "venta_producto vp, compventa cv, comprobantes co, cliente cl, catalogoproducto ct, " +
     "tipocomprobante tip, serie ser " +
     "where c.idCompra = dc.idCompra and c.idProveedor = pr.idProveedor and " +
     "dc.idDoc_Compra = p.idDoc_Compra and p.idProducto = vp.Producto_idProducto and vp.Venta_idVenta = v.idVenta " +
     "and v.idVenta = cv.idVenta and cv.idComprobantes = co.idComprobantes and v.idCliente = cl.idCliente " +
     "and p.Catalogoproducto_codctlg = ct.codctlg and co.idTipoComprobante = tip.idTipoComprobante " +
     "and p.idProducto = ser.Producto_idProducto and dc.idsede='"+InfoGeneral.getIdSede()+
     "' and v.fecvta between '"+control.Formato_Amd(fechaIn.getDate()) +"' "+
     " and '"+control.Formato_Amd(fechaFi.getDate()) +"'  and "+
     " (ct.nomctlg like '%"+txtBuscar.getText()+"%' or p.codbrr like '%"+txtBuscar.getText()
     +"%' or dc.serie like '%"+txtBuscar.getText()+"%');";  
     try {
      control.Base.st= control.Base.conec.createStatement();
      control.Base.rt=control.Base.st.executeQuery(control.Sql);
      while(control.Base.rt.next()){
       datos[0]=control.Base.rt.getString(1);datos[1]=control.Base.rt.getString(2);
       datos[2]=control.Base.rt.getString(3);datos[3]=control.Base.rt.getString(4);
       datos[4]=control.Base.rt.getString(5);datos[5]=control.Base.rt.getString(6);
       datos[6]=control.Base.rt.getString(7);datos[7]=control.Base.rt.getString(8);
       datos[8]=control.Base.rt.getString(9);datos[9]=control.Base.rt.getString(10);
       datos[10]=control.Base.rt.getString(11);datos[11]=control.Base.rt.getString(12);
       modelo.addRow(datos);
      }
     }
     catch (Exception e) {
      e.printStackTrace();
     }
    }
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        fechaIn = new com.toedter.calendar.JDateChooser();
        fechaFi = new com.toedter.calendar.JDateChooser();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbMovimiento = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Fecha Inicio");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Fecha Final");
        jLabel3.setName("jLabel3"); // NOI18N

        fechaIn.setName("fechaIn"); // NOI18N

        fechaFi.setName("fechaFi"); // NOI18N

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 102));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Search1.png"))); // NOI18N
        jButton3.setText("Ver");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(10, 10, 10)
                .addComponent(fechaIn, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fechaFi, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(166, 166, 166))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3)
                        .addComponent(fechaFi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2)
                        .addComponent(fechaIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tbMovimiento.setForeground(new java.awt.Color(0, 51, 102));
        tbMovimiento.setModel(new javax.swing.table.DefaultTableModel(
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
        tbMovimiento.setName("tbMovimiento"); // NOI18N
        tbMovimiento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMovimientoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbMovimiento);

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Print.png"))); // NOI18N
        jButton1.setText("Imprimir");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton2.setText("Salir");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Buscar");
        jLabel4.setName("jLabel4"); // NOI18N

        txtBuscar.setName("txtBuscar"); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(372, 372, 372)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1147, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        VerPorFechas();
    }//GEN-LAST:event_jButton3ActionPerformed
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     imprime.ImprimirusConFechasVarias("Ventas_Ganancia_Total.jasper", control.Formato_Amd(fechaIn.getDate())
     , control.Formato_Amd(fechaFi.getDate()));
    }//GEN-LAST:event_jButton1ActionPerformed
    private void tbMovimientoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMovimientoMouseClicked
     if(evt.getClickCount()==2){
      imprime.ImprimirusConFechas("Reporte_Producto.jasper",
      tbMovimiento.getValueAt(tbMovimiento.getSelectedRow(), 0).toString());
     }
    }//GEN-LAST:event_tbMovimientoMouseClicked
    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
     VerPorFechas();//BuscarCliente();
    }//GEN-LAST:event_txtBuscarKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser fechaFi;
    private com.toedter.calendar.JDateChooser fechaIn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbMovimiento;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
