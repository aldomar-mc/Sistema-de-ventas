package Ventanas;

import Clases.Controlador;
import Clases.IMPRIMIR;
import Clases.InfoGeneral;
import Clases.Mimodelo;
import Clases.Numero_a_Letra;
import com.toedter.calendar.JDateChooser;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class FrmComprobantesVenta extends javax.swing.JInternalFrame {

    /**
     * **************************ATRIBUTOS**************************************************
     */
    private Controlador control = new Controlador();
    private Mimodelo modelo = new Mimodelo();
    private DecimalFormat forma = new DecimalFormat("####.####");
    private IMPRIMIR imprime = new IMPRIMIR();
    private Numero_a_Letra nume = new Numero_a_Letra();
    private int contadopr = 0, controladorModo = 0;
    private InfoGeneral info = new InfoGeneral();
    private double totalVenta = 0;
    private double valor = 0, igv1 = 0, subtotal1 = 0;
    String idGuiaRemision = "";

    /**
     * **************************ATRIBUTOS**************************************************
     */

    /**
     * **********************************METODOS******************************************
     */
    /*public void PonerFechaActual(){
     Desde.setDate( new Date());Hasta.setDate( new Date());   
    }   */
    public FrmComprobantesVenta() {
        initComponents();
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tbVentas.setModel(modelo);
        modelo.setColumnIdentifiers(
                new String[]{"Item", "Fec. Venta", "Comprobante", "Numero", "Cliente",
                    "Modalidad", "Vendedor", "Total Venta", "Estado Comp", "N° Guia"});
        control.hideTableColumn(tbVentas, 0);
        control.setWidthTableColumn(tbVentas, 500, 4);
        control.setMaxWidthColumnTable(tbVentas, 80, 1, 2, 5, 7, 8);
        control.setMaxWidthColumnTable(tbVentas, 110, 3);
        control.hideTableColumn(tbVentas, 9);
        cargarComprobantes();
        control.forma_table_ver(modelo, tbVentas);//PonerFechaActual();
        TableRowSorter<TableModel> elorden = new TableRowSorter<TableModel>(modelo);
        tbVentas.setRowSorter(elorden);
        //****************************CONTROLAR EL CAMBIO DE FECHA***********************
        /* Desde.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
       if("date".equals(evt.getPropertyName())){
        JDateChooser isse=(JDateChooser)evt.getSource();
        boolean isdatasele=false;
        try {
         Field dateselfi=JDateChooser.class.getDeclaredField("dateSelect");
         dateselfi.setAccessible(true);isdatasele=dateselfi.getBoolean(isse);
        } 
        catch (Exception e) {    
          cargarComprobantes();  
        }
       }
      }
     });
     Hasta.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
       if("date".equals(evt.getPropertyName())){
        JDateChooser isse=(JDateChooser)evt.getSource();
        boolean isdatasele=false;
        try {
         Field dateselfi=JDateChooser.class.getDeclaredField("dateSelect");
         dateselfi.setAccessible(true);
         isdatasele=dateselfi.getBoolean(isse);
        } 
        catch (Exception e) {         
          cargarComprobantes();  
        }
       }
      }
     });*/
        //****************************CONTROLAR EL CAMBIO DE FECHA***************** 

    }

    private void generarguia() {
        if (JOptionPane.showConfirmDialog(null, "Desea imprimir una guia de remicion?",
                "Systems Message", JOptionPane.YES_NO_OPTION) == 0) {
            FrmGenerarGuiaRemision generarGuiaRemision = new FrmGenerarGuiaRemision(null, true);
            generarGuiaRemision.setLocationRelativeTo(this);
            generarGuiaRemision.setIdVenta(tbVentas.getValueAt(tbVentas.getSelectedRow(),
                    0).toString());
            generarGuiaRemision.setVisible(true);
            String idGuiaRemisionGenerado = generarGuiaRemision.getIdGuiaRemision();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItemImprimir = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemVerGuiaImprimir = new javax.swing.JMenuItem();
        jMenuItemVerGuiaVista = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbVentas = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        jMenuItemImprimir.setText("Imprimir comprobante");
        jMenuItemImprimir.setName("jMenuItemImprimir"); // NOI18N
        jMenuItemImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemImprimirActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemImprimir);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jPopupMenu1.add(jSeparator1);

        jMenuItemVerGuiaImprimir.setText("Imprimir Guia de Remisión");
        jMenuItemVerGuiaImprimir.setName("jMenuItemVerGuiaImprimir"); // NOI18N
        jMenuItemVerGuiaImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemVerGuiaImprimirActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemVerGuiaImprimir);

        jMenuItemVerGuiaVista.setText("Visualizar Guia de Remisión");
        jMenuItemVerGuiaVista.setName("jMenuItemVerGuiaVista"); // NOI18N
        jMenuItemVerGuiaVista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemVerGuiaVistaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemVerGuiaVista);

        setClosable(true);
        setIconifiable(true);
        setTitle("Comprobantes de Venta");
        setToolTipText("");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Buscar Comprobante");
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 13, 143, -1));

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
        tbVentas.setComponentPopupMenu(jPopupMenu1);
        tbVentas.setName("tbVentas"); // NOI18N
        tbVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbVentasMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbVentasMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbVentas);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 37, 1087, 470));

        jButton2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton2.setMnemonic('s');
        jButton2.setText("Salir");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 520, 116, 40));

        txtBuscar.setName("txtBuscar"); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        getContentPane().add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 11, 940, -1));

        jButton3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 102));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Print.png"))); // NOI18N
        jButton3.setMnemonic('i');
        jButton3.setText("Imprimir");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(239, 520, 116, 40));

        jButton4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 51, 102));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        jButton4.setMnemonic('a');
        jButton4.setText("Anular");
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(625, 520, 116, 40));

        jButton5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 51, 102));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/anular.png"))); // NOI18N
        jButton5.setMnemonic('e');
        jButton5.setText("Genrar Guia");
        jButton5.setName("jButton5"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(429, 520, 140, 40));

        jPanel1.setName("jPanel1"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 760, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 510, 760, 60));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed
    private void tbVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbVentasMouseClicked
        if (evt.getClickCount() == 2) {
            if (tbVentas.getValueAt(tbVentas.getSelectedRow(), 8).toString().equals("Anulado")) {
                control.Sql = String.format("SELECT `fecha`, `hora`, `usuario`, `autorizador` FROM `anulados` WHERE `idventa`=%s;", tbVentas.getValueAt(tbVentas.getSelectedRow(), 0).toString());
                JOptionPane.showMessageDialog(null, "Fecha  Anulación:  " + control.DevolverRegistroDto(control.Sql, 1) + "\nHora Anulación:  " + control.DevolverRegistroDto(control.Sql, 2) + "\nUsuario:  " + control.DevolverRegistroDto(control.Sql, 3) + "\nAutorzador y Motivo:  " + control.DevolverRegistroDto(control.Sql, 4));
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tbVentasMouseClicked
    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        cargarComprobantes();
    }//GEN-LAST:event_txtBuscarKeyReleased
    private void tbVentasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbVentasMousePressed
        Point point = evt.getPoint();
        int currentRow = this.tbVentas.rowAtPoint(point);
        this.tbVentas.setRowSelectionInterval(currentRow, currentRow);
        jMenuItemVerGuiaImprimir.setEnabled(modelo.getValueAt(currentRow, 2).toString().equalsIgnoreCase("Factura"));
        jMenuItemVerGuiaImprimir.setEnabled(modelo.getValueAt(currentRow, 2).toString().equalsIgnoreCase("Ticket Factura"));
        jMenuItemVerGuiaVista.setEnabled(modelo.getValueAt(currentRow, 2).toString().equalsIgnoreCase("Factura"));
        jMenuItemVerGuiaVista.setEnabled(modelo.getValueAt(currentRow, 2).toString().equalsIgnoreCase("Ticket Factura"));
    }//GEN-LAST:event_tbVentasMousePressed
    private void jMenuItemImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemImprimirActionPerformed
        imprimirComprobante();
    }//GEN-LAST:event_jMenuItemImprimirActionPerformed
    private void jMenuItemVerGuiaImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemVerGuiaImprimirActionPerformed
        visualizarGuia(true);
    }//GEN-LAST:event_jMenuItemVerGuiaImprimirActionPerformed
    private void jMenuItemVerGuiaVistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemVerGuiaVistaActionPerformed
        visualizarGuia(false);
    }//GEN-LAST:event_jMenuItemVerGuiaVistaActionPerformed
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (tbVentas.getSelectedRowCount() == 1) {
            imprimirComprobante();
        } else {
            JOptionPane.showMessageDialog(null, "No ha selecionado un comprobante para imprimir!!", "System Message", 3);
        }
    }//GEN-LAST:event_jButton3ActionPerformed
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (tbVentas.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Debes Seleccionar un comrobantes");
            return;
        }
        if (JOptionPane.showConfirmDialog(null, "Desea anular el comprobante selecionado?",
                "System Message", JOptionPane.YES_NO_OPTION) == 0) {
            int lengtresp = 0;
            String resp = "";
            boolean bamdera = true;
            int rows = tbVentas.getSelectedRow();
            while (lengtresp == 0) {
                resp = JOptionPane.showInputDialog("Ingrese el autorizador de la anulación");
                if (null == resp || resp == null) {
                    JOptionPane.showMessageDialog(null, "Cancelo la nota");
                    resp = "null";
                } else if (resp.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Ingrese un dato Correcto");
                }
                lengtresp = resp.length();
            }
            control.Sql = "SELECT nom FROM vendedores  where nomusr='" + info.getUsuario() + "';";
            String usaior = control.DevolverRegistroDto(control.Sql, 1);
            if (resp != "null") {
                control.Sql = String.format("INSERT INTO anulados VALUES(NULL, '%s',curdate(),"
                        + "curtime(),'%s','%s')", tbVentas.getValueAt(rows, 0).toString(), usaior, resp);
                control.ejecutar(control.Sql);
                control.ejecutar(String.format("UPDATE venta v SET v.`montfactu`=0, v.`montreal`=0 "
                        + "WHERE v.`idVenta`=%s;", tbVentas.getValueAt(rows, 0).toString()));

                control.ejecutar(String.format("update comprobantes set esta ='Anulado' "
                        + "where idcomprobantes = %s;", control.DevolverRegistroDto(String.format("SELECT "
                                + "c.`idComprobantes` FROM compventa c WHERE c.`idVenta`=%s;", tbVentas.getValueAt(rows,
                                        0).toString()), 1)));
                control.ejecutar(String.format("UPDATE producto p SET p.`estdo`="
                        + "'Disponible' " + " WHERE p.`idProducto` in (SELECT v.`Producto_idProducto` FROM"
                        + " venta_producto v" + " WHERE v.`Venta_idVenta`=%s);", tbVentas.getValueAt(rows,
                                0).toString()));

                control.ejecutar(String.format("DELETE FROM pagos WHERE `idpagos`=%s "
                        + "AND `tipo`='Venta';", tbVentas.getValueAt(rows, 0).toString()));
                tbVentas.setValueAt("Anulado", rows, 8);
            }
        } else {
        }
    }//GEN-LAST:event_jButton4ActionPerformed
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (tbVentas.getSelectedRowCount() == 1) {
            if (tbVentas.getValueAt(tbVentas.getSelectedRow(), 2).toString().endsWith("Factura") || tbVentas.getValueAt(tbVentas.getSelectedRow(),
                    2).toString().endsWith("Ticket Factura")) {
                generarguia();
            } else {
                JOptionPane.showMessageDialog(null, "El comprobante selecionado incorrecto "
                        + "para emitir una Guia");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione una Factura o Ticket Factura "
                    + "para emitir una Guia", "System Message", 1);
        }
    }//GEN-LAST:event_jButton5ActionPerformed
    public void ImprimirGuiaExistente() {

    }

    private void imprimirComprobante() {
        String text = "";
        String idVenta = modelo.getValueAt(tbVentas.getSelectedRow(), 0).toString();
        String tipo = modelo.getValueAt(tbVentas.getSelectedRow(), 2).toString();
        control.Sql = "select * from vta_datosimpresion where idventa='" + idVenta + "'";
        String cliente = control.DevolverRegistroDto(control.Sql, 2);
        String direc = control.DevolverRegistroDto(control.Sql, 3);
        String dia = control.DevolverRegistroDto(control.Sql, 4);
        String mes = control.DevolverRegistroDto(control.Sql, 5);
        String anio = control.DevolverRegistroDto(control.Sql, 6).substring(3, 4);

        String idencte = control.getValueQuery(String.format("SELECT `numident` FROM "
                + "`identificacion` WHERE `idCliente`=%s;", control.DevolverRegistroDto(control.Sql, 1)));

        control.Sql = "select idventa, round(Total,2), round(SubTotal,2), round(Igv,2)  from vta_importes where idventa='" + idVenta + "'";
        String monto = control.DevolverRegistroDto(control.Sql, 2);
        String igv = control.DevolverRegistroDto(control.Sql, 4);
        String subTotal = control.DevolverRegistroDto(control.Sql, 3);
        text = igv;
        try {
            Number numero = forma.parse(text);
            valor = numero.doubleValue();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
        igv1 = valor;
        text = subTotal;
        try {
            Number numero = forma.parse(text);
            valor = numero.doubleValue();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
        String monlt = control.numlt.Convertir("" + monto, true);
        subtotal1 = valor;
        if (dia.length() == 1) {
            dia = "0" + dia;
        }
        if (tipo.equalsIgnoreCase("Boleta")) {
            control.impresor.ImpresionBoletaProductos("idventa", idVenta, "dia", dia, "mes",
                    mes, "anio", anio, "cliente", cliente, "direc", direc, "Monto", monto, "BoletaICE", "totalnum", monlt);
        }
        if (tipo.equalsIgnoreCase("Factura")) {
            String guiaRemision = "";
            control.Sql = "SELECT g.`idGuiaRemision`,v.`idVenta`, (SELECT co.`nume` FROM `comprobantes` co\n"
                    + "WHERE co.`idComprobantes`=g.`idComprobante`) AS numeroGuia,c.`nume` as numeroFactura,\n"
                    + "cl.`nomclie`, i.`numident`, g.`fechaEmision`, g.`fechaInicioTraslado`, g.`puntoDePartida`,\n"
                    + "g.`puntoDeLlegada`, g.`marcaPlacaVehiculo`, g.`numeroLicenciaConducir`,\n"
                    + "g.`constanciaInscripcion`, g.`nombreTransportista`, g.`rucTransportista`, g.`motivoDeTraslado`,\n"
                    + "IF(POSITION('Otro:' IN g.`motivoDeTraslado`) = 0,'',SUBSTRING(g.`motivoDeTraslado`,\n"
                    + "POSITION('Otro:' IN g.`motivoDeTraslado`)+5)) AS otro\n"
                    + "FROM comprobantes c, compventa cv, cliente cl, identificacion i, venta v, guiaremision g\n"
                    + "WHERE c.`idComprobantes`=cv.`idComprobantes`  AND i.`idCliente`=cl.`idCliente`\n"
                    + "AND v.`idVenta`=cv.`idVenta` AND v.`idCliente`=cl.`idCliente` AND g.`idVenta`='" + idVenta + "'";

            guiaRemision = control.DevolverRegistroDto(control.Sql, 3);
            idGuiaRemision = control.DevolverRegistroDto(control.Sql, 1);

            control.impresor.ImpresionFacturaProductos("idventa", idVenta, "dia", dia,
                    "mes", mes, "anio", anio, "cliente", cliente, "dir", direc, "identi", idencte,
                    "guia", guiaRemision, "monletras", monlt, "vlrvta", subTotal, "igv",
                    igv, "total", monto, "FacturaIce", "1", "100");

            if (idGuiaRemision.trim().length() > 0) {
                JOptionPane.showMessageDialog(this, "La guía de remisión se ha generado "
                        + "correctamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                Map map = new HashMap();
                map.put("idGuia", String.valueOf(idGuiaRemision));
                map.put("codv", System.getProperties().getProperty("user.dir") + "\\src\\Reportes\\");
                String sede = InfoGeneral.getIdSede();
                control.showReportDialog("Guía de Remisión", "guiaDeRemisionICE", map);
            }
            /**
             * ****************Vamos a Imprimir la Guia de
             * Remisison***********************
             *
             * /*control.impresor.ImpresionFacturaProductos("idventa", idVenta,
             * "dia", dia, "mes", mes, "anio",anio, "cliente", cliente, "dir",
             * direc, "identi", idencte, "guia", guiaRemision,
             * "monletras",monlt, "vlrvta", subTotal , "igv",igv, "total",
             * monto,
             * "FacturaIce",Integer.toString(lmt1),Integer.toString(lmt2));
             */

        }
        if (tipo.equalsIgnoreCase("Ticket")) {
            String guiaRemision = "";
            control.impresor.ImpresionFacturaProductos("idVenta", idVenta, "dia", dia, "mes", mes, "anio", anio, "cliente", cliente, "dir", direc, "identi",
                    idencte, "guia", guiaRemision, "monletras", monlt, "vlrvta", subTotal, "igv", igv, "total", monto, "ticket", "", "");
        }
        if (tipo.equalsIgnoreCase("Ticket Factura")) {
            String guiaRemision = "";
            control.impresor.ImpresionFacturaProductos("idVenta", idVenta, "dia", dia,
                    "mes", mes, "anio", anio, "cliente", cliente, "dir", direc, "identi", idencte,
                    "guia", guiaRemision, "monletras", monlt, "vlrvta", subTotal, "igv",
                    igv, "total", monto, "ticket_factura", "", "");//Imprimie factura infotel
        }
        if (tipo.equalsIgnoreCase("Ticket Boleta")) {
            String guiaRemision = "";
            control.impresor.ImpresionFacturaProductos("idVenta", idVenta, "dia", dia,
                    "mes", mes, "anio", anio, "cliente", cliente, "dir", direc, "identi",
                    idencte, "guia", guiaRemision, "monletras", monlt, "vlrvta", subTotal, "igv",
                    igv, "total", monto, "ticket_boleta", "", "");
        }
    }

    private void cargarComprobantes() {
        /* control.Sql="select v.idVenta,v.fecvta,ti.tipcompr,cpr.nume,c.nomclie,v.moda,"
     + "d.nom,IF(cpr.esta='Anulado',0.00,(SELECT round(sum(d.`cant`* d.`prec`),2)"
     + " FROM detventafacturar d WHERE d.`idVenta`= v.`idVenta` AND d.`mostrar`=true)),"
     + "cpr.esta,ver_guia(v.idventa) from venta v,cliente c,usuario u,datosusuarios d,"
     + "venta_producto vp,compventa co, comprobantes cpr, tipocomprobante ti where v.idCliente"
     + "=c.idCliente and v.Usuario_idusuario=u.idusuario and u.idusuario=d.Usuario_idusuario "
     + "and v.idVenta=vp.Venta_idVenta and v.idVenta=co.idVenta and co.idComprobantes="
     + "cpr.idComprobantes and cpr.idTipoComprobante=ti.idTipoComprobante and ti.idsede='"
     +info.getIdSede()+"' and v.tipo<>'F' and (cpr.nume like'%" + txtBuscar.getText() + "%' or ti.tipcompr "
     + "like'%" + txtBuscar.getText()+ "%' or c.nomclie like'%" + txtBuscar.getText()              
     + "%')group by v.idVenta order by v.fecvta desc;";
         */

        control.Sql = "select v.idventa,v.fecvta,tc.tipcompr,cb.nume,cl.nomclie,v.moda,concat(dtu.nom,' ',dtu.ape),v.montfactu\n"
                + ",cb.esta,if((select c.nume from guiaremision gr inner join comprobantes c on c.idcomprobantes=gr.idcomprobante where "
                + "gr.idventa=v.idventa) is null,\"\",(select c.nume from guiaremision gr inner join comprobantes c on c.idcomprobantes="
                + "gr.idcomprobante where gr.idventa=v.idventa)) Guia from venta v inner join cliente cl on cl.idcliente=v.idcliente"
                + " inner join compventa cv on cv.idventa=v.idventa inner join comprobantes cb on cb.idcomprobantes=cv.idcomprobantes"
                + " inner join tipocomprobante tc on tc.idtipocomprobante=cb.idtipocomprobante inner join usuario us on us.idusuario="
                + "v.usuario_idusuario inner join datosusuarios dtu on dtu.usuario_idusuario=us.idusuario where v.tipo<>'F' and "
                + "cb.esta<>'Anulado' and (cb.nume like '%" + txtBuscar.getText() + "%' or cl.nomclie like '%" + txtBuscar.getText()
                + "%' or tc.tipcompr like '" + txtBuscar.getText() + "%')  "
                //"%' or tc.tipcompr like '"+txtBuscar.getText()+"%') and  (v.fecvta>='"+control.Formato_Amd(Desde.getDate())+
                //"' and v.fecvta<='"+control.Formato_Amd(Hasta.getDate())+"') order by 1 desc;";        
                + "order by 1 desc;";

        /*
          "%' or tc.tipcompr like '"+txtBuscar.getText()+"%') and  (v.fecvta>='"+control.Formato_Amd(f_Desde.getDate())+
     "' and v.fecvta<='"+control.Formato_Amd(F_Hasta.getDate())+"') order by 1 desc;";*/
        control.LlenarJTabla(modelo, control.Sql, 10);
    }

    private void visualizarGuia(boolean paraImprimir) {
        if (modelo.getValueAt(tbVentas.getSelectedRow(), 9) != null) {
            String idGuiaRemision = control.DevolverRegistroDto(String.format("SELECT `idGuiaRemision` "
                    + "FROM `guiaremision` WHERE `idVenta`=%s LIMIT 1;", modelo.getValueAt(tbVentas.getSelectedRow(),
                            0).toString()), 1);
            Map map = new HashMap();
            map.put("idGuia", idGuiaRemision);
            String sede
                    = InfoGeneral.getIdSede();
            if (paraImprimir) {
                control.showReportDialog("Guía de Remisión", "guiaDeRemisionICE", map);
            } else {
                control.showReportDialog("Guía de Remisión", "guiaDeRemisionICEVista", map);
            }
        } else {
            JOptionPane.showMessageDialog(this, "La factura seleccionada no tiene guia de remisión",
                    "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /**
     * **********************************METODOS******************************************
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuItem jMenuItemImprimir;
    private javax.swing.JMenuItem jMenuItemVerGuiaImprimir;
    private javax.swing.JMenuItem jMenuItemVerGuiaVista;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTable tbVentas;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables

}
