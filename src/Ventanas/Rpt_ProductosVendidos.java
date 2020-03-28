package Ventanas;

import java.util.Date;
import Clases.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Rpt_ProductosVendidos extends javax.swing.JInternalFrame {

    Controlador control = new Controlador();
    Mimodelo modelo = new Mimodelo();
    IMPRIMIR imprime = new IMPRIMIR();
    int contadopr = 0, controladorModo = 0;

    /**
     * Creates new form Rpt_Ventas
     */
    
    /*SELECT idProveedor, Ruc, Proveedor,fec,fecingralm, nomctlg, codbrr,costo,
idVenta, fecvta, moda,
 prc,  estdo,
 nomclie, desident, numident,
dc.idDoc_Compra, serie, nume, dc.tipo, dc.idCompra
FROM venta v , venta_producto vp, producto pv, catalogoproducto ct,vta_cliente cl,doc_compra dc,compra co,vta_losproveedores prov
where v.idventa=vp.venta_idventa and vp.producto_idproducto=pv.idproducto and pv.catalogoproducto_codctlg=ct.codctlg
and v.idcliente=cl.idcliente and pv.iddoc_compra=dc.iddoc_compra and co.idcompra=dc.idcompra and co.idproveedor=prov.codigo;
* 
* 
* 
* SELECT idVenta, fecvta, moda, Usuario_idusuario,
 prc,
 fecingralm,  codbrr, costo, estdo,
  nomctlg,
 nomclie, desident, numident,
dc.idDoc_Compra, serie, nume, dc.tipo, dc.idCompra, fec, idProveedor, Ruc, Proveedor
FROM venta v , venta_producto vp, producto pv, catalogoproducto ct,vta_cliente cl,doc_compra dc,compra co,vta_losproveedores prov
where v.idventa=vp.venta_idventa and vp.producto_idproducto=pv.idproducto and pv.catalogoproducto_codctlg=ct.codctlg
and v.idcliente=cl.idcliente and pv.iddoc_compra=dc.iddoc_compra and co.idcompra=dc.idcompra and co.idproveedor=prov.codigo;

* 
* 
*/
    public Rpt_ProductosVendidos() {
        initComponents();
        setTitle("Reporte de Cotizaciones");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tbVentas.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"Item", "Cliente", "Fec. Cotizacion","Fec. Vencimiento"});
        tbVentas.getColumnModel().getColumn(0).setPreferredWidth(70);
        tbVentas.getColumnModel().getColumn(1).setPreferredWidth(280);
        tbVentas.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbVentas.getColumnModel().getColumn(3).setPreferredWidth(125);
       

//        fechaFin.setDate(new Date());
//        fechaIni.setDate(new Date());
//        control.LlenarCombo(cbusuario, "SELECT nomusr FROM vendedores v;", 1);
        MostrarCliente();
//        LlenarModo();
        llenattoal();
    }

    public void llenattoal(){
        control.Sql="SELECT sum(precventa) FROM producto p where estdo='Vendido';";
//        lbTotalenVEntas.setText("S/. "+control.DevolverRegistroDto(control.Sql, 1));
    }
   

    public void MostrarCliente() {
        BuscarCliente();
    }

    public void BuscarCliente() {
        control.Sql=" select c.idCotizacion,nomclie,fec_ctz, date_add(fec_ctz,  interval  diasdur day)ff " +
        " from cotizacion c, detallecotizacion d,cliente cl  where c.idcotizacion=d.idcotizacion and c.idcliente=cl.idcliente " +
        " and date_add(fec_ctz,  interval  diasdur day) >curdate() and nomclie like '"+txtBuscar.getText()+"%' group by idcotizacion; ";
        
//        control.Sql = "SELECT idventa,nomclie, fecvta, tipcompr, nume, moda, nom, total FROM ventas_imprimir_todo"
//                + " where fecvta>='" + control.Formato_Amd(fechaIni.getDate()) + "' and fecvta<='" + control.Formato_Amd(fechaFin.getDate()) + "'  " 
//                + " order by idventa asc;";
        control.LlenarJTabla(modelo, control.Sql, 4);
       // System.out.println(control.Sql);//admin

        //    JOptionPane.showMessageDialog(null,control.Sql);
    }

    public void BuscarPorUsuario() {
//        if (cbusuario.getSelectedItem().toString().trim().length() > 0) {
//            
//                control.Sql = "SELECT idventa,nomclie, fecvta, tipcompr, nume, moda, nom, total FROM ventas_imprimir_todo where fecvta>='" + control.Formato_Amd(fechaIni.getDate()) + "' and fecvta<='" + control.Formato_Amd(fechaFin.getDate()) + "' and nomusr='" + cbusuario.getSelectedItem() + "' order by idventa asc;";
//                control.LlenarJTabla(modelo, control.Sql, 8);
//            
//        }

    }

    

//    public void BuscarModos() {
//        if (cbusuario.getSelectedIndex() >= 0) {
//            control.Sql = "SELECT idventa,nomclie, fecvta, tipcompr, nume, moda, nom, total FROM ventas_imprimir_todo where fecvta>='" + control.Formato_Amd(fechaIni.getDate()) + "' and fecvta<='" + control.Formato_Amd(fechaFin.getDate()) + "' and nomusr='" + cbusuario.getSelectedItem().toString() + "' and moda='" + cbModo.getSelectedItem().toString() + "' order by idventa asc;";
//            control.LlenarJTabla(modelo, control.Sql, 8);
//        } else {
//            control.Sql = "SELECT idventa,nomclie, fecvta, tipcompr, nume, moda, nom, total FROM ventas_imprimir_todo where fecvta>='" + control.Formato_Amd(fechaIni.getDate()) + "' and fecvta<='" + control.Formato_Amd(fechaFin.getDate()) + "' and moda='" + cbModo.getSelectedItem().toString() + "' order by idventa asc;";
//            control.LlenarJTabla(modelo, control.Sql,8);
//        }
//        // System.out.print(control.Sql);
//    }
public void llen(){
    control.Sql="SELECT idventa,nomclie, fecvta, tipcompr, nume, moda, nom, total FROM ventas_imprimir_todo where nomclie like '"+txtBuscar.getText()+"%' or nume like '"+txtBuscar.getText()+"%';";
    control.LlenarJTabla(modelo, control.Sql, 8);
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbVentas = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Reporte de Cotizacion");
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, -1, -1));

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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 829, 370));

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
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 123, -1));

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
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 121, -1));

        txtBuscar.setName("txtBuscar"); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        getContentPane().add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 270, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Buscar");
        jLabel6.setName("jLabel6"); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
        if (tbVentas.getSelectedRowCount()==1) {
            control.Sql="select count(*) " +
"from cotizacion c, detallecotizacion d,cliente cl  where c.idcotizacion=d.idcotizacion and c.idcliente=cl.idcliente " +
"  and c.idcotizacion='"+tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString()+"';";
            int ggd= Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1).toString());
            
             if(ggd>1){
                imprime.ImprimirusConFechas("CotizacionVarios.jasper", tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString());
            }else{
                imprime.ImprimirusConFechas("Cotizacion.jasper", tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString());
            }
            
        }else{
            JOptionPane.showMessageDialog(null,"Selecione una cotizacion para Imprimir!!!");
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tbVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbVentasMouseClicked
        if(evt.getClickCount()==2){
            control.Sql="select count(*) " +
"from cotizacion c, detallecotizacion d,cliente cl  where c.idcotizacion=d.idcotizacion and c.idcliente=cl.idcliente " +
"  and c.idcotizacion='"+tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString()+"';";
            int ggd= Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1).toString());
            
             if(ggd>1){
                imprime.ImprimirusConFechas("CotizacionVarios.jasper", tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString());
            }else{
                imprime.ImprimirusConFechas("Cotizacion.jasper", tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString());
            }
//            imprime.ImprimirusConFechas("Detalle_venta.jasper", modelo.getValueAt(tbVentas.getSelectedRow(), 0).toString());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tbVentasMouseClicked

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
//       llen();
        
        MostrarCliente();
    }//GEN-LAST:event_txtBuscarKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbVentas;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
