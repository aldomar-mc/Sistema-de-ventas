package Ventanas;

import java.util.Date;
import Clases.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class VerGarantia extends javax.swing.JInternalFrame {

    Controlador control = new Controlador();
    Mimodelo modelo = new Mimodelo();
    IMPRIMIR imprime = new IMPRIMIR();
    int contadopr = 0, controladorModo = 0;

    /**
     * Creates new form Rpt_Ventas
     */
    public VerGarantia() {
        initComponents();
        setTitle("Caducidad de Productos");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tbVentas.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"Item", "Nombre", "Codigo", "Serie", "Costo","Precio de Venta","Fecha de Caducidad","Estado"});
        tbVentas.getColumnModel().getColumn(0).setPreferredWidth(80);
        tbVentas.getColumnModel().getColumn(1).setPreferredWidth(300);
        tbVentas.getColumnModel().getColumn(2).setPreferredWidth(110);
        tbVentas.getColumnModel().getColumn(3).setPreferredWidth(110);
        tbVentas.getColumnModel().getColumn(4).setPreferredWidth(300);
        tbVentas.getColumnModel().getColumn(5).setPreferredWidth(80);
        tbVentas.getColumnModel().getColumn(6).setPreferredWidth(120);
        tbVentas.getColumnModel().getColumn(7).setPreferredWidth(120);
        
//        fechaFin.setDate(new Date());
//        fechaIni.setDate(new Date());
       // control.LlenarCombo(cbusuario, "SELECT nomusr FROM vendedores v;", 1);
        MostrarCliente();
        //LlenarModo();
        control.forma_table_ver(modelo, tbVentas);
    }

//    public void LlenarModo() {
//        cbModo.addItem("Contado");
//        cbModo.addItem("Credito");
//        cbModo.setSelectedIndex(-1);
//    }

    public void MostrarCliente() {
        BuscarCliente();
    }

    public void BuscarCliente() {
        //control.Sql = "SELECT idventa, nomclie, fecvta, fecfnl, nomctlg, prc,codbrr, seri,  tipcompr, nume FROM garantia "
                //+ "where fecfnl>=curdate() and (nomctlg like '"+txtBuscarGenrantia.getText()+"%' or codbrr like '"+txtBuscarGenrantia.getText()+"%' or seri like '"+txtBuscarGenrantia.getText()+"%' );";
/*        control.Sql = "SELECT `v`.`idVenta`, `cl`.`nomclie`, `v`.`fecvta`, `fcad`.`fecha_cad`, `ca`.`nomctlg`, `vp`.`prc`, `p`.`codbrr`, `se`.`seri`, `ti`.`tipcompr`, `cpr`.`nume`\n" +
"FROM `venta` `v`, `garantiaxventa` `g`, `venta_producto` `vp`, `producto` `p`, `catalogoproducto` `ca`, `compventa` `co`, `comprobantes` `cpr`, `cliente` `cl`, `tipocomprobante` `ti`, `serie` `se`, fecha_caducidad fcad\n" +
"WHERE (`v`.`idVenta` = `vp`.`Venta_idVenta`)\n" +
"       AND (`vp`.`idVenta_producto` = `g`.`idVenta_producto`)\n" +
"       AND (`vp`.`Producto_idProducto` = `p`.`idProducto`)\n" +
"       AND (p.`idProducto` = fcad.`id_Producto`)\n" +
"       AND (`p`.`Catalogoproducto_codctlg` = `ca`.`codctlg`)\n" +
"       AND (`v`.`idVenta` = `co`.`idVenta`)\n" +
"       AND (`co`.`idComprobantes` = `cpr`.`idComprobantes`)\n" +
"       AND (`v`.`idCliente` = `cl`.`idCliente`)\n" +
"       AND (`cpr`.`idTipoComprobante` = `ti`.`idTipoComprobante`)\n" +
"       AND (`p`.`idProducto` = `se`.`Producto_idProducto`) AND fecfnl>=CURDATE() AND (nomctlg LIKE '"+txtBuscarGenrantia.getText()+"%' OR codbrr LIKE '"+txtBuscarGenrantia.getText()+"%' OR seri LIKE '"+txtBuscarGenrantia.getText()+"%' );";
        */
        control.Sql="SELECT p.`idProducto`, c.nomctlg, p.codbrr, s.`seri`, p.costo, p.precVenta, fca.fecha_cad, p.estdo \n" +
"FROM producto p, serie s, catalogoproducto c, sede se, modelo mo, fecha_caducidad fca\n" +
"WHERE p.Catalogoproducto_codctlg=c.codctlg AND s.Producto_idProducto=p.idProducto AND se.idSede=p.idSede AND c.idModelos = mo.idModelos  AND fca.id_Producto=p.idProducto\n" +
                " AND curdate()>ADDDATE(fca.fecha_cad , interval-7 day ) AND estdo='Disponible' "+
"AND (c.`nomctlg` LIKE'%"+txtBuscarGenrantia.getText()+"%' OR s.`seri` LIKE'%"+txtBuscarGenrantia.getText()+"%' OR p.`codbrr` LIKE'%"+txtBuscarGenrantia.getText()+"%' OR p.`estdo` LIKE '%"+txtBuscarGenrantia.getText()+"%') AND (se.`nomse` ='"+Controlador.sede+"')";
        control.LlenarJTabla(modelo, control.Sql, 8);
System.out.println(control.Sql);
        //    JOptionPane.showMessageDialog(null,control.Sql);
    }

    public void ImprimirVentas() {
        //imprime.Impresiones("Lista de Productos con Garantia Activa", "VerGarantia");
        imprime.ImprimirCaducidad("VerGarantia.jasper", Controlador.sede,  txtBuscarGenrantia.getText());
//        if (cbusuario.getSelectedIndex() == -1) {
//            if (cbModo.getSelectedIndex() == -1) {
//                imprime.ImprimirusConFechasVarias("Ventas_General.jasper", control.Formato_Amd(fechaIni.getDate()), control.Formato_Amd(fechaFin.getDate()));
//            } else {
//                imprime.ImprimirusConFechasVariasUsua("Ventas_General_Todo.jasper", control.Formato_Amd(fechaIni.getDate()), control.Formato_Amd(fechaFin.getDate()), cbModo.getSelectedItem().toString());
//            }
//        } else {
//            if (cbModo.getSelectedIndex() == -1) {
//                imprime.ImprimirusConFechasVariasUsua("Ventas_General_Usuario.jasper", control.Formato_Amd(fechaIni.getDate()), control.Formato_Amd(fechaFin.getDate()), cbusuario.getSelectedItem().toString());
//            } else {
//                imprime.ImprimirusConFechasVariasUsuaModo("Ventas_General_Modo.jasper", control.Formato_Amd(fechaIni.getDate()), control.Formato_Amd(fechaFin.getDate()), cbusuario.getSelectedItem().toString(), cbModo.getSelectedItem().toString());
//            }
//        }
    }

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbVentas = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtBuscarGenrantia = new javax.swing.JTextField();

        setClosable(true);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tbVentas.setForeground(new java.awt.Color(0, 51, 102));
        tbVentas.setModel(new javax.swing.table.DefaultTableModel(
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
        tbVentas.setName("tbVentas"); // NOI18N
        tbVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbVentasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbVentas);

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

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Buscar:");
        jLabel6.setName("jLabel6"); // NOI18N

        txtBuscarGenrantia.setName("txtBuscarGenrantia"); // NOI18N
        txtBuscarGenrantia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarGenrantiaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1244, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscarGenrantia, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtBuscarGenrantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ImprimirVentas();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tbVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbVentasMouseClicked
        /*if(evt.getClickCount()==2){
            imprime.ImprimirusConFechas("Detalle_venta.jasper", modelo.getValueAt(tbVentas.getSelectedRow(), 0).toString());
        }*/        // TODO add your handling code here:
        if(evt.getClickCount()>=2 ){
            if(tbVentas.getValueAt(tbVentas.getSelectedRow(), 7).toString().compareTo("Disponible")==0){
                control.Sql="SELECT concat('PROVEEDOR: ',nompro ) ,concat('FECHA: ',fec ),  concat('SERIE: ',serie ) ,  concat('NUMERO: ',nume),"
                    + "  concat('TIPO DOCUMENTO: ',tipo) ,  concat(' PRODUCTO: ',nomctlg), concat('CODIGO: ',codbrr) FROM verproductospordocumentocompra where idproducto='"+tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString()+"';";
                String a1=control.DevolverRegistroDto(control.Sql, 1);
                String a2=control.DevolverRegistroDto(control.Sql, 2);
                String a3=control.DevolverRegistroDto(control.Sql, 3);
                String a4=control.DevolverRegistroDto(control.Sql, 4);
                String a5=control.DevolverRegistroDto(control.Sql, 5);
                String a6=control.DevolverRegistroDto(control.Sql, 6);
                String a7=control.DevolverRegistroDto(control.Sql, 7);
                System.out.println(control.Sql);
                String a8=tbVentas.getValueAt(tbVentas.getSelectedRow(), 3).toString();
                JOptionPane.showMessageDialog(null, "<html><body><font size = 6 >Datos del Producto Comprado:</font><br>"+a1+"<br>"+a2+"<br>"+a3+"<br>"+a4+"<br>"+a5+"<br>"+a6+"<br>"+a7+"<br>SERIE: "+a8+"</body></html>");
                
            }else{
                if(tbVentas.getValueAt(tbVentas.getSelectedRow(), 7).toString().compareTo("Distribuido")==0){
                    control.Sql="SELECT concat('PROVEEDOR: ',nompro ) ,concat('FECHA: ',fec ),  concat('SERIE: ',serie ) ,  concat('NUMERO: ',nume),"
                    + "  concat('TIPO DOCUMENTO: ',tipo) ,  concat(' PRODUCTO: ',nomctlg), concat('CODIGO: ',codbrr) FROM verproductospordocumentocompra where idproducto='"+tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString()+"';";
                System.out.println(control.Sql);
                String b1=control.DevolverRegistroDto(control.Sql, 1);
                String b2=control.DevolverRegistroDto(control.Sql, 2);
                String b3=control.DevolverRegistroDto(control.Sql, 3);
                String b4=control.DevolverRegistroDto(control.Sql, 4);
                String b5=control.DevolverRegistroDto(control.Sql, 5);
                String b6=control.DevolverRegistroDto(control.Sql, 6);
                String b7=control.DevolverRegistroDto(control.Sql, 7);
                String b8=tbVentas.getValueAt(tbVentas.getSelectedRow(), 3).toString();
                //JOptionPane.showMessageDialog(null, "<html><body><b><font size = 12 >Datos del Producto Comprado:</font></b><br>"+b1+"<br>"+b2+"<br>"+b3+"<br>"+b4+"<br>"+b5+"<br>"+b6+"<br>"+b7+"<br>SERIE: "+b8+"</body></html>");
                System.out.println(control.Sql);
                control.Sql="SELECT CONCAT('Sede Actual: ',s.nomse) FROM producto p, sede s, serie se WHERE s.idSede=p.idSede AND p.idProducto=se.Producto_idProducto AND p.estdo='Disponible' AND (p.codbrr='"+tbVentas.getValueAt(tbVentas.getSelectedRow(), 2).toString()+"' OR se.seri='"+tbVentas.getValueAt(tbVentas.getSelectedRow(), 3).toString()+"')";
                String sed = control.DevolverRegistroDto(control.Sql, 1);
                JOptionPane.showMessageDialog(null, "<html><font size = 6 >Datos del Producto Comprado:</font><br>"+b1+"<br>"+b2+"<br>"+b3+"<br>"+b4+"<br>"+b5+"<br>"+b6+"<br>"+b7+"<br>SERIE: "+b8+" <br>"
                        + "<font size=6 color=red>PRODUCTO DISTRIBUIDO</font><br><font size=4>"+sed+"</font><br></html>");
                }else{
                    if(tbVentas.getValueAt(tbVentas.getSelectedRow(), 7).toString().compareTo("Aperturado")==0){
                    control.Sql="SELECT concat('PROVEEDOR: ',nompro ) ,concat('FECHA: ',fec ),  concat('SERIE: ',serie ) ,  concat('NUMERO: ',nume),"
                    + "  concat('TIPO DOCUMENTO: ',tipo) ,  concat(' PRODUCTO: ',nomctlg), concat('CODIGO: ',codbrr) FROM verproductospordocumentocompra where idproducto='"+tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString()+"';";
                System.out.println(control.Sql);
                String b1=control.DevolverRegistroDto(control.Sql, 1);
                String b2=control.DevolverRegistroDto(control.Sql, 2);
                String b3=control.DevolverRegistroDto(control.Sql, 3);
                String b4=control.DevolverRegistroDto(control.Sql, 4);
                String b5=control.DevolverRegistroDto(control.Sql, 5);
                String b6=control.DevolverRegistroDto(control.Sql, 6);
                String b7=control.DevolverRegistroDto(control.Sql, 7);
                String b8=tbVentas.getValueAt(tbVentas.getSelectedRow(), 3).toString();
                //JOptionPane.showMessageDialog(null, "<html><body><b><font size = 12 >Datos del Producto Comprado:</font></b><br>"+b1+"<br>"+b2+"<br>"+b3+"<br>"+b4+"<br>"+b5+"<br>"+b6+"<br>"+b7+"<br>SERIE: "+b8+"</body></html>");
                System.out.println(control.Sql);
                JOptionPane.showMessageDialog(null, "<html><font size = 6 >Datos del Producto Comprado:</font><br>"+b1+"<br>"+b2+"<br>"+b3+"<br>"+b4+"<br>"+b5+"<br>"+b6+"<br>"+b7+"<br>SERIE: "+b8+" <br>"
                        + "<font size=6 color=red>PRODUCTO APERTURADO</font><br></html>");
                }else{
                        control.Sql="SELECT concat('PROVEEDOR: ',nompro ) ,concat('FECHA: ',fec ),  concat('SERIE: ',serie ) ,  concat('NUMERO: ',nume),"
                            + "  concat('TIPO DOCUMENTO: ',tipo) ,  concat(' PRODUCTO: ',nomctlg), concat('CODIGO: ',codbrr) FROM verproductospordocumentocompra where idproducto='"+tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString()+"';";
                        System.out.println(control.Sql);
                        String b1=control.DevolverRegistroDto(control.Sql, 1);
                        String b2=control.DevolverRegistroDto(control.Sql, 2);
                        String b3=control.DevolverRegistroDto(control.Sql, 3);
                        String b4=control.DevolverRegistroDto(control.Sql, 4);
                        String b5=control.DevolverRegistroDto(control.Sql, 5);
                        String b6=control.DevolverRegistroDto(control.Sql, 6);
                        String b7=control.DevolverRegistroDto(control.Sql, 7);
                        String b8=tbVentas.getValueAt(tbVentas.getSelectedRow(), 3).toString();
                        //JOptionPane.showMessageDialog(null, "<html><body><b><font size = 12 >Datos del Producto Comprado:</font></b><br>"+b1+"<br>"+b2+"<br>"+b3+"<br>"+b4+"<br>"+b5+"<br>"+b6+"<br>"+b7+"<br>SERIE: "+b8+"</body></html>");

                        control.Sql="SELECT CONCAT('Cliente: ',c.nomclie), CONCAT('Tipo Documento: ',tc.tipcompr), CONCAT('N° Documento: ',i.numident),CONCAT('Producto: ', cp.nomctlg), \n" +
        "CONCAT('Precio de Venta: ',vp.prc), CONCAT('Fecha de Venta: ',v.fecvta), CONCAT('Tipo Comprobante: ',tc.tipcompr), CONCAT('N° Comprobante: ',cbt.nume),\n" +
        "CONCAT('Codigo Barra: ',p.codbrr), CONCAT('Serie Producto: ',s.seri) \n" +
        "FROM venta_producto vp, venta v, producto p, cliente c, compventa cv  , comprobantes cbt, tipocomprobante tc, identificacion i, catalogoproducto cp, serie s\n" +
        "WHERE p.idProducto=vp.Producto_idProducto AND vp.Venta_idVenta=v.idVenta AND c.`idCliente`=v.idCliente AND i.idCliente=c.`idCliente` AND p.Catalogoproducto_codctlg=cp.codctlg\n" +
        "AND cv.idVenta=v.idVenta AND cv.idComprobantes=cbt.idComprobantes AND tc.idTipoComprobante=cbt.idTipoComprobante AND s.Producto_idProducto=p.idProducto AND p.idProducto='"+tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString()+"'";

                        /*control.Sql="SELECT concat('Cliente: ',nomclie), concat('Tipo Documento: ',desident), concat('N° Documento: ',numident),concat('Producto: ', nomctlg) " +
                        ", concat('Precio de Venta: ',prc), concat('Fecha de Venta: ',fec), concat('Tipo Comprobante: ',tipcompr), concat('N° Comprobante: ',serie), " +
                        "  concat('Codigo Barra: ',codbrr), concat('Serie Producto: ',seri) " +
                        " FROM venta_detalle v " +
                        " where codbrr='"+tbProducto.getValueAt(tbProducto.getSelectedRow(), 2).toString()+"' and seri ='"+tbProducto.getValueAt(tbProducto.getSelectedRow(), 3).toString()+"';";*/
                        String a1=control.DevolverRegistroDto(control.Sql, 1);
                        System.out.println(control.Sql);
                        String a2=control.DevolverRegistroDto(control.Sql, 2);
                        String a3=control.DevolverRegistroDto(control.Sql, 3);
                        String a4=control.DevolverRegistroDto(control.Sql, 4);
                        String a5=control.DevolverRegistroDto(control.Sql, 5);
                        String a6=control.DevolverRegistroDto(control.Sql, 6);
                        String a7=control.DevolverRegistroDto(control.Sql, 7);
                        String a8=control.DevolverRegistroDto(control.Sql, 8);
                        String a9=control.DevolverRegistroDto(control.Sql, 9);
                        String a10=control.DevolverRegistroDto(control.Sql, 10);
                        //String a8=tbProducto.getValueAt(tbProducto.getSelectedRow(), 3).toString();
                        System.out.println(control.Sql);
                        JOptionPane.showMessageDialog(null, "<html><font size = 6 >Datos del Producto Comprado:</font><br>"+b1+"<br>"+b2+"<br>"+b3+"<br>"+b4+"<br>"+b5+"<br>"+b6+"<br>"+b7+"<br>SERIE: "+b8+" <br>"
                                + "<font size=6 color=red>Datos del Producto Vendido</font> <br>"+a1+"<br>"+a2+"<br>"+a3+"<br>"+a4+"<br>"+a5+"<br>"+a6+"<br>"+a7+"<br> "+a8+"<br> "+a9+"<br> "+a10+"</html>"); 
                    }
                    
               
            }
            }
        }
        
    }//GEN-LAST:event_tbVentasMouseClicked

    private void txtBuscarGenrantiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarGenrantiaKeyReleased
        BuscarCliente();        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarGenrantiaKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbVentas;
    private javax.swing.JTextField txtBuscarGenrantia;
    // End of variables declaration//GEN-END:variables
}
