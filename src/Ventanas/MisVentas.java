/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import javax.swing.ImageIcon;

/**
 * ******************* @author @ldomar.mc https://zeytol.com  ************************
 */
import Clases.*;
import static Clases.Controlador.Base;
import static Ventanas.Ventas.lbCliente;
import static Ventanas.Ventas.lbTotalVenta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MisVentas extends javax.swing.JInternalFrame {

    /**
     * ************************************************* LOS ATRIBUTOS***************************************
     */
    Controlador control = new Controlador();
    Mimodelo modeloproductos = new Mimodelo();
    Mimodelo modelofactura = new Mimodelo();
    private DecimalFormat forma = new DecimalFormat("0.00");
    InfoGeneral info = new InfoGeneral();
    static public String idCliente = "", dni_ruc = "";
    String codctlg = "", idProducto = "", idSede = "", idUsuario = "", precio = "", tipoventa = "", comprobante = "", numcomp = "",
            idComprobanteGenerado = "", serie = "", ModaVenta = "", montoFacturar = "", idVenta = "", subTotal = "", igv = "";
    int item = 0, cantidad = 0, cantidadOld = 0, cantidadNew = 0, posi = -1;
    double total = 0.00;
    double mtReal = 0.00, adelanto = 0.00;
    boolean mibandera = false;

    private CustomTableModel model = new CustomTableModel() {
        private DecimalFormat format = new DecimalFormat("0.00");

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1 || column == 4 || column == 5 || column == 7;
        }

        @Override
        public void setValueAt(Object aValue, int row, int column) {
            switch (column) {
                case 1: {
                    super.setValueAt(aValue.toString(), row, column);
                    break;
                }
                case 4: {
                    cantidadOld = Integer.parseInt(super.getValueAt(row, 5).toString());
                    precio = super.getValueAt(row, 4).toString();
                    try {
                        super.setValueAt((String) RecalcularImporte(Integer.toString(cantidadOld), aValue.toString()), row, 6);
                        super.setValueAt(aValue.toString(), row, column);
                        Calcularmontototal();
                        CalcularVuelto();
                    } catch (Exception e) {
                    }
                    break;
                }
                case 5: {
                    cantidadOld = Integer.parseInt(super.getValueAt(row, 5).toString());
                    precio = super.getValueAt(row, 4).toString();
                    codctlg = super.getValueAt(row, 0).toString();
                    //JOptionPane.showMessageDialog(null,"Esta actuando Ahora la bandera es "+control.bandera);
                    try {
                        cantidadNew = Integer.parseInt(aValue.toString());
                        super.setValueAt((String) RecalcularImporte(aValue.toString(), precio), row, 6);
                        super.setValueAt((int) (cantidadNew), row, column);
                        if (cantidadNew == 0) {
                            JOptionPane.showMessageDialog(null, "Cero no es una cantidad Validad");
                            super.setValueAt((int) (cantidadOld), row, column);
                            return;
                        }
                        int diferencia = (cantidadNew - cantidadOld);
                        if ((Boolean) T_ProductosparaVender.getValueAt(row, 7)) {
                            if (diferencia > 0) {
                                control.Sql = "select count(*) from producto where estdo='Disponible' and catalogoproducto_codctlg='" + codctlg + "'";
                                int stk = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
                                if (stk >= diferencia) {
                                    if (control.bandera) {
                                        control.bandera = false;
                                        //    JOptionPane.showMessageDialog(null, "No Actualiza");
                                    } else {
                                        //     JOptionPane.showMessageDialog(null, "Si Actualiza");
                                        ActualizaProductoxVender(true, (cantidadNew - cantidadOld));
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Supera el Stock");
                                    super.setValueAt((int) (cantidadOld), row, column);
                                }
                            } else {
                                if ((cantidadNew - cantidadOld) != 0) {
                                    ActualizaProductoxVender(false, (cantidadOld - cantidadNew));
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Si el Check esta desactivado no se podra modificar");
                            super.setValueAt((int) (cantidadOld), row, column);
                        }
                        BuscarProducto();
                        Calcularmontototal();
                        CalcularVuelto();
                        txCliente.grabFocus();
                    } catch (Exception e) {//super.setValueAt((int)(cantidadOld), row, column);
                    }
//             super.setValueAt((int)(cantidadNew), row, column);
                    break;
                }
                case 7: {
                    cantidad = Integer.parseInt(super.getValueAt(row, 5).toString());
                    super.setValueAt(aValue, row, column);
                    codctlg = super.getValueAt(row, 0).toString();
                    if ((Boolean) T_ProductosparaVender.getValueAt(row, 7)) {
                        //JOptionPane.showMessageDialog(null,"Aumenta el inventario");
                        ActualizaProductoxVender(true, cantidad);
                        //BuscarProducto();txCliente.grabFocus(); 
                    } else {
                        // JOptionPane.showMessageDialog(null,"Disminuye el inventario "+codctlg);
                        ActualizaProductoxVender(false, cantidad);
                    }
                    BuscarProducto();
                    Calcularmontototal();
                    CalcularVuelto();
                    txCliente.grabFocus();
                    break;
                }
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 7) {
                return Boolean.class;
            }
            return super.getColumnClass(columnIndex);
        }
    };

    /**
     * ************************************************* LOS ATRIBUTOS***************************************
     */

    /**
     * ****************************************** LOS MÉTODOS****************************************
     */
    public void AgregarProductos(int evt) {
        if (T_Productos.getRowCount() > 0) {
            //*********************************Capturamos el codigo del catalogo**************************************  
            switch (evt) {
                case 0: {
                    control.fila = 0;
                    break;
                }
                case 1: {
                    control.fila = T_Productos.getSelectedRow();
                    break;
                }
            }
            codctlg = T_Productos.getValueAt(control.fila, 0).toString();
            control.Data[0] = codctlg;
            //*********************************Capturamos el codigo del catalogo**************************************

            posi = (int) BuscarDatoenJtablePos(codctlg, 0);
            if (posi > -1) {
                cantidad = 0;
                cantidad = Integer.parseInt(T_ProductosparaVender.getValueAt(posi, 5).toString()) + 1;
                precio = T_ProductosparaVender.getValueAt(posi, 5).toString();
                T_ProductosparaVender.setValueAt(cantidad, posi, 5);
                T_ProductosparaVender.setValueAt(RecalcularImporte(Integer.toString(cantidad), precio), posi, 6);
            } else {
                control.Data[1] = Integer.toString(T_ProductosparaVender.getRowCount() + 1);
                control.Data[2] = T_Productos.getValueAt(control.fila, 2).toString();
                control.Data[3] = T_Productos.getValueAt(control.fila, 3).toString();
                control.Data[4] = T_Productos.getValueAt(control.fila, 6).toString();
                control.Data[5] = "1";
                control.Data[6] = Double.toString(Integer.parseInt(control.Data[5]) * Double.parseDouble(control.Data[4]));
                model.addRow(new Object[]{control.Data[0], control.Data[1], control.Data[2], control.Data[3], control.Data[4], control.Data[5], control.Data[6], Boolean.TRUE});
            }
            //*********************************Obtenemos un producto disponible**************************************
            control.Sql = "select * from producto where catalogoproducto_codctlg='" + codctlg + "' and estdo='Disponible' and "
                    + "idSede='" + idSede + "' order by 1 limit 1 ";
            idProducto = control.DevolverRegistroDto(control.Sql, 1);
            //*********************************Obtenemos un producto disponible**************************************
            CambiarEstadoPorVender();
            txBuscar.setText("");
            Calcularmontototal();
            lbitems.setText(Integer.toString(T_ProductosparaVender.getRowCount()));
            BuscarProducto();
            txBuscar.grabFocus();
        }
    }

    public void ActualizaProductoxVender(boolean bdr, int cant) {
        if (bdr) {
            //****************************Incrementar la cantidad por vender******************************/   
            for (int f = 1; f <= cant; f++) {
                //*********************************Obtenemos un producto disponible**************************************
                control.Sql = "select * from producto where catalogoproducto_codctlg='" + codctlg + "' and estdo='Disponible' and "
                        + "idSede='" + idSede + "' order by 1 limit 1 ";
                idProducto = control.DevolverRegistroDto(control.Sql, 1);
                //*********************************Obtenemos un producto disponible**************************************
                CambiarEstadoPorVender();
            }
            //****************************Incrementar la cantidad por vender******************************/
        } else {
            //****************************Disminuir la cantidad por vender********************************/   
            for (int f = 1; f <= cant; f++) {
                //*********************************Obtenemos un producto disponible**************************************
                control.Sql = "select idProducto from por_vender where idproducto=(select idproducto from producto where estdo='Por Vender' "
                        + "and catalogoproducto_codctlg='" + codctlg + "' limit 1) and idUsuario='" + idUsuario + "' and sede='" + idSede + "';";
                idProducto = control.DevolverRegistroDto(control.Sql, 1);
                //*********************************Obtenemos un producto disponible**************************************

                //*********************************Actualizamos y eliminamos los productos por vender***************************
                control.Sql = "delete from por_vender where idproducto='" + idProducto + "' and idusuario='" + idUsuario + "' and sede='" + idSede + "'";
                control.EliminarRegistro(control.Sql);
                control.Sql = "update producto set estdo='Disponible' where idproducto='" + idProducto + "'";
                control.EditarRegistro(control.Sql);
                //*********************************Actualizamos y eliminamos los productos por vender****************************

            }
            //****************************Disminuir la cantidad por vender********************************/ 
        }
        BuscarProducto();
    }

    public void BuscaCliente(KeyEvent e) {
        //if((txCliente.getText().trim().length()>0)&&(e.getKeyChar()==10)){
        if ((e.getKeyChar() == 10)) {
            control.Sql = "select idCliente,nomclie,desident,numident from vta_cliente where numident='" + txCliente.getText() + "'";
            if (control.Verificarconsulta(control.Sql)) {
                idCliente = control.DevolverRegistroDto(control.Sql, 1);
                dni_ruc = txCliente.getText();
                lbCliente.setText(control.DevolverRegistroDto(control.Sql, 2));
                lbIdentificacion.setText(control.DevolverRegistroDto(control.Sql, 3) + ":  " + control.DevolverRegistroDto(control.Sql, 4));
                txBuscar.grabFocus();
            } else {
                LosClientes cli = new LosClientes();
                info.setRucc("");
                Menu.jDesktopPane1.add(cli);
                LosClientes.txtCliente.grabFocus();
                info.setControl(1);
                cli.toFront();
                cli.setVisible(true);
            }
        }
    }

    public void BuscarProducto() {
        control.Sql = "select cp.codctlg,cp.codbarra,concat(md.nommod,' ',cp.nomctlg) Producto,mrc.nommrc Marca,"
                + "u.nomuni Unidad,cp.precsg Costo,cp.prexmenor,(select count(catalogoproducto_codctlg) Cantidad from producto where "
                + "catalogoproducto_codctlg=cp.codctlg and estdo='Disponible' and idSede='" + idSede + "') Stock from catalogoproducto "
                + "cp inner join modelo md on cp.idmodelos=md.idmodelos inner join unidad u on u.idunidad=cp.idunidad inner join marca "
                + "mrc on mrc.idmarca=cp.idmarca where (select count(catalogoproducto_codctlg) Cantidad from producto where "
                + "catalogoproducto_codctlg=cp.codctlg and estdo='Disponible' and idSede='" + idSede + "')>0"
                + " and (cp.codbarra like'%" + txBuscar.getText() + "%' or md.nommod like'%" + txBuscar.getText() + "%' or cp.nomctlg "
                + " like'%" + txBuscar.getText() + "%' or mrc.nommrc like'%" + txBuscar.getText() + "%' or concat(md.nommod,' ',cp.nomctlg) "
                + "like'%" + txBuscar.getText() + "%') order by 3;";
        control.LlenarJTabla(modeloproductos, control.Sql, 8);
        //System.out.print("Daiel>>>>>"+control.Sql); 
    }

    public int BuscarDatoenJtablePos(String dto, int col) {
        int pos = -1;
        int fila = 0;
        if (model.getRowCount() > 0) {
            while (fila < model.getRowCount()) {
                if (model.getValueAt(fila, col).toString().equalsIgnoreCase(dto)) {
                    pos = fila;
                    fila = model.getRowCount();
                }
                fila++;
            }
        }
        return pos;
    }

    public void Calcularmontototal() {
        total = 0.00;
        control.fila = 0;
        while (control.fila < T_ProductosparaVender.getRowCount()) {
            total = total + Double.parseDouble(model.getValueAt(control.fila, 6).toString().replace(",", "."));
            control.fila++;
        }
        lbMontoTotal.setText(control.decimalFormat(total));
    }

    public void CalcularVuelto() {
        if (txMontoEntregado.getText().trim().length() == 0) {
            lbVuelto.setText("0.00");
        } else {
            total = ((Double.parseDouble(txMontoEntregado.getText().replace(",", ".")) - Double.parseDouble(lbMontoTotal.getText().replace(",", "."))));
//       if(total<0)
//        total=(-1)*total;  

            lbVuelto.setText(control.decimalFormat(total));
        }
    }

    public void CambiarEstadoPorVender() {
        //*********************************Ponemos por Vender al Producto***************************************
        control.Sql = "update producto set estdo='Por Vender' where catalogoproducto_codctlg='" + codctlg + "' and idproducto='"
                + idProducto + "'";
        control.EditarRegistro(control.Sql);
        //*********************************Ponemos por Vender al Producto***************************************

        //*********************************Insertamos el producto por vender**************************************
        control.Sql = "insert into por_vender(idproducto,idusuario,sede) values('" + idProducto + "','" + idUsuario + "','" + idSede + "');";
        control.CrearRegistro(control.Sql);
        //*********************************Insertamos el producto por vender**************************************     
    }

    public void Cancelar() {
        txBuscar.setText(null);
        PonerClienteGnrl();
        LimpiarProductoporVender();
        LimpiarElModel();
        Calcularmontototal();
        txMontoEntregado.setText(null);
        lbitems.setText(Integer.toString(T_ProductosparaVender.getRowCount()));
        CalcularVuelto();
        PonerClienteGnrl();
        PonerVentaLibre();
        ckTv.setSelected(false);
        SeleccionarContado();
        BuscarProducto();
    }

    public void CapturandoIdentificadores() {
        idSede = InfoGeneral.idSede;
        idUsuario = InfoGeneral.idUsuario;
    }

    public boolean DeterminasiFicticia() {
        boolean bandera = true;
        int ctdfal = 0;
        tipoventa = "N";
        for (int f = 0; f < T_ProductosparaVender.getRowCount(); f++) {
            if ((Boolean) T_ProductosparaVender.getValueAt(f, 7) == false) {
                bandera = false;
                ctdfal++;
            }
        }
        if (bandera == false) {
            if (T_ProductosparaVender.getRowCount() == ctdfal) {
                tipoventa = "F";
            } else {
                tipoventa = "P";
            }
        }
        return bandera;
    }

    public void DeterminaTipoVenta() {
        if (DeterminasiFicticia()) {
            if (ckTv.isSelected()) {
                tipoventa = "A";
            }
        }
    }

    public void DeterminaModalidadVenta() {
        if (rbContado.isSelected()) {
            ModaVenta = "Contado";
        }
        if (rbCredito.isSelected()) {
            ModaVenta = "Credito";
        }
    }

    private void generarComprobanteVenta() {
        if (cboComprobantes.getSelectedIndex() >= 0) {
            control.Sql = "CALL ElComprobante('0','" + Controlador.sede + "','" + comprobante + "','Productos','0','1')";

            if (control.DevolverRegistroDto(control.Sql, 1).equals("Se debe iniciar este tipo de comprobante")) {
                JOptionPane.showMessageDialog(cboComprobantes, "No hay documentos de este tipo de comprobante, por "
                        + "Favor primero incie la numeración de este tipo de comprobante.", "Mensaje", JOptionPane.WARNING_MESSAGE);
                InfoGeneral.pase = false;
                IniciandoComprobantesICECompured inicdoc = new IniciandoComprobantesICECompured();
                Menu.jDesktopPane1.add(inicdoc);
                inicdoc.toFront();
                inicdoc.setLocation(250, 250);
                inicdoc.setVisible(true);
            } else {
                numcomp = control.DevolverRegistroDto(control.Sql, 1);//txtNumComprobantes.setText(numcomp);
                serie = control.DevolverRegistroDto(control.Sql, 2);//lbSerie.setText(serie);
                lbNumeroComprobante.setText(serie + "_" + numcomp);
                idComprobanteGenerado = control.DevolverRegistroDto(control.Sql, 3);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Indique el tipo de comprobante a generar", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void ImprimirVersionUltima(String idventa) {
        //******************************************CAPTURAMOS LOS DATOS GENERALES***********************************
        Map map = new HashMap();  //Creamos el objeto de los parametros.   
        control.Sql = "SELECT * FROM vta_datosimpresion WHERE idventa='" + idventa + "'";
        String cliente = "", direc = "", dia = "", mes = "", anio = "";
        idCliente = control.DevolverRegistroDto(control.Sql, 1);
        cliente = control.DevolverRegistroDto(control.Sql, 2);
        direc = control.DevolverRegistroDto(control.Sql, 3);
        dia = control.DevolverRegistroDto(control.Sql, 4);
        mes = control.DevolverRegistroDto(
                control.Sql, 5);
        anio = control.DevolverRegistroDto("SELECT right('" + control.DevolverRegistroDto(control.Sql, 6) + "', 1);", 1);
        control.Sql = "select * from identificacion where idcliente='" + idCliente + "'";
        idCliente = control.DevolverRegistroDto(control.Sql, 3);

        //anio = control.DevolverRegistroDto(control.Sql, 6);
        control.Sql = "SELECT idventa, ROUND(Total,2), ROUND(SubTotal,2), ROUND(Igv,2) FROM vta_importes "
                + "WHERE idventa='" + idventa + "'";
        String monto = control.DevolverRegistroDto(control.Sql, 2);
        igv = control.DevolverRegistroDto(control.Sql, 4);
        subTotal = control.DevolverRegistroDto(control.Sql, 3);
        String monlt = control.numlt.Convertir(monto, true);

        if (dia.length() == 1) {
            dia = "0" + dia;
        }
        //*************************************CAPTURAMOS LOS DATOS GENERALES*****************************************   

        //**********************CAPTURAR EL COMPROBANTE SELECCIONADO********************
        String MiComprobante = cboComprobantes.getSelectedItem().toString();
        //**********************CAPTURAR EL COMPROBANTE SELECCIONADO**********************

        //********************Determinar El Tipo de COMPROBANTE SELECCIONADO*****************
        boolean banderaTC = false;
        if (MiComprobante.equalsIgnoreCase("Boleta") || MiComprobante.equalsIgnoreCase("Ticket Boleta")
                || MiComprobante.equalsIgnoreCase("Ticket") || MiComprobante.equalsIgnoreCase("Factura")
                || MiComprobante.equalsIgnoreCase("Ticket Factura")) {
            banderaTC = true;
        }
        //********************Determinar El Tipo de COMPROBANTE SELECCIONADO*****************

        //**************VENTA AL CONTADO***************************************************   
        //if (rbContado.isSelected()) {
        if (banderaTC) {    //*****************Comprobantes cotidianos**********         
            if (MiComprobante.equalsIgnoreCase("Boleta")) {
                lbNumeroComprobante.setText(null);//lbSerie.setText(null);
                control.impresor.ImpresionBoletaProductos("idventa", idventa, "dia", dia, "mes", mes, "anio", anio,
                        "cliente", cliente, "direc", direc, "Monto", monto, "BoletaICE", "totalnum", new Numero_a_Letra().Convertir(monto, true));
            }

            if (MiComprobante.equalsIgnoreCase("Ticket Boleta")) {
                /*
                 String guiaRemision = "";map.put("idVenta", idventa);map.put("dia", dia);
                 map.put("mes", mes);map.put("anio", anio);map.put("cliente", cliente);
                 map.put("dir", direc);map.put("identi", idCliente);map.put("guia", guiaRemision);
                 map.put("monletras", monlt);map.put("vlrvta", subTotal);map.put("igv", igv);
                 map.put("total", monto);
                 
                 control.imprimirComprobanteVenta(idventa, "ticket_boleta", map);*/
 /*   JOptionPane.showMessageDialog(null, "Imprimir un ticket boleta");
                 control.impresor.Imprimircon1Parametros("Ticket","ticket_boleta", "idVenta", idventa);*/

 /*String guiaRemision = "";
      control.impresor.ImpresionFacturaProductos("idVenta", idventa, "dia", dia, 
      "mes", mes, "anio",anio, "cliente", cliente, "dir", direc, "identi", idCliente, "guia", guiaRemision,"monletras", monlt, "vlrvta", subTotal, "igv",
      igv, "total", monto, "ticket_boleta","",""); Valido*/
                String guiaRemision = "";
                control.impresor.ImpresioLosticketts("idVenta", idventa, "dia", dia,
                        "mes", mes, "anio", anio, "cliente", cliente, "dir", direc, "identi", idCliente, "guia", guiaRemision, "monletras", monlt, "vlrvta", subTotal, "igv",
                        igv, "total", monto, "ticket_boleta", "", "");

            }
            if (cboComprobantes.getSelectedItem().toString().equals("Ticket")) {

                /*String guiaRemision = "";
                control.impresor.ImpresionFacturaProductos("idVenta", idVenta, "dia", dia, "mes"
              , mes, "anio",anio, "cliente", cliente, "dir", direc, "identi", idencte, "guia",
             guiaRemision,"monletras", monlt, "vlrvta", subTotal, "igv",igv, "total", monto, "ticket","","");
                 */
                String guiaRemision = "";
                map.put("idVenta", idventa);
                map.put("dia", dia);
                map.put("mes", mes);
                map.put("anio", anio);
                map.put("cliente", cliente);
                map.put("dir", direc);
                map.put("identi", idCliente);
                map.put("guia", guiaRemision);
                map.put("monletras", monlt);
                map.put("vlrvta", subTotal);
                map.put("igv", igv);
                map.put("total", monto);
                control.imprimirComprobanteVenta(idventa, "ticket", map);

            }

            int fac = 0, tope = 5;
            String guiaRemision = "";
            if (MiComprobante.equalsIgnoreCase("Factura")) {
                //*******************Captura la cantidad de filas de la factura**************************
                int lim1 = 0, lim2 = 10, toveces = 0, cant = 0;
                //*********************Obtenemos la cantidad maxima a imprimir************************ 
                control.Sql = "SELECT * FROM config WHERE idconfig='maxFilasFactura'";
                tope = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 2));
                //*********************Obtenemos la cantidad maxima a imprimir************************ 

                control.impresor.ImpresionFacturaProductos("idventa", idventa, "dia", dia, "mes", mes, "anio",
                        anio, "cliente", cliente, "dir", direc, "identi", idCliente, "guia", guiaRemision, "monletras",
                        monlt, "vlrvta", subTotal, "igv", igv, "total", monto, "FacturaIce", Integer.toString(lim1), Integer.toString(lim2));
                //******************SOLICITA IMPRESION DE GUIA DE REMISION**********************************
                if (JOptionPane.showConfirmDialog(null, "Desea Imprimir Una Guía De Remición?", "Systems Message",
                        JOptionPane.YES_NO_OPTION) == 0) {
                    FrmGenerarGuiaRemision generarGuiaRemision = new FrmGenerarGuiaRemision(null, true);
                    generarGuiaRemision.setLocationRelativeTo(this);
                    generarGuiaRemision.setIdVenta(idventa);
                    generarGuiaRemision.setVisible(true);
                    String idGuiaRemisionGenerado = generarGuiaRemision.getIdGuiaRemision();
                }
                //******************SOLICITA IMPRESION DE GUIA DE REMISION***********************************
            }
            if (MiComprobante.equalsIgnoreCase("Ticket Factura")) {
                map.put("idVenta", idventa);
                map.put("dia", dia);
                map.put("mes", mes);
                map.put("anio", anio);
                map.put("cliente", cliente);
                map.put("dir", direc);
                map.put("identi", idCliente);
                map.put("guia", guiaRemision);
                map.put("monletras", monlt);
                map.put("vlrvta", subTotal);
                map.put("igv", igv);
                map.put("total", monto);
                control.imprimirComprobanteVenta(idventa, "ticket_factura", map);
                //******************SOLICITA IMPRESION DE GUIA DE REMISION*************************
                if (JOptionPane.showConfirmDialog(null, "Desea Imprimir Una Guía De Remición?",
                        "Systems Message", JOptionPane.YES_NO_OPTION) == 0) {
                    FrmGenerarGuiaRemision generarGuiaRemision = new FrmGenerarGuiaRemision(null, true);
                    generarGuiaRemision.setLocationRelativeTo(this);
                    generarGuiaRemision.setIdVenta(idventa);
                    generarGuiaRemision.setVisible(true);
                    String idGuiaRemisionGenerado = generarGuiaRemision.getIdGuiaRemision();
                }
                //******************SOLICITA IMPRESION DE GUIA DE REMISION**************************
            }
        }
    }

    public void LimpiarElModel() {
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

    public void LimpiarProductoporVender() {
        int cantid = 0;
        control.Sql = "select count(*) from por_vender where idusuario='" + idUsuario + "' and sede='" + idSede + "'";
        cantid = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
        if (cantid > 0) {
            /**
             * **********Eliminamos los productos por vender y ponemos
             * dispnibles los productos por vender  ***********
             */
            for (int i = 1; i <= cantid; i++) {
                control.Sql = "select * from por_vender where idUsuario='" + idUsuario + "' and sede='" + idSede + "' limit 1";
                idProducto = control.DevolverRegistroDto(control.Sql, 1);
                control.Sql = "update producto set  estdo='Disponible' where idproducto='" + idProducto + "'";
                control.EditarRegistro(control.Sql);
                control.Sql = "delete from por_vender where idUsuario='" + idUsuario + "' and sede='" + idSede + "' and idProducto='" + idProducto + "'";
                control.EliminarRegistro(control.Sql);
            }
            /**
             * **********Eliminamos los productos por vender y ponemos
             * dispnibles los productos por vender  ***********
             */
        }
    }

    public void MarcarCheck() {
        if (T_ProductosparaVender.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No Hay Productos por Vender");
            ckTv.setSelected(false);
            return;
        }
        if (ckTv.isSelected()) {
            if (DeterminasiFicticia() == false) {
                JOptionPane.showMessageDialog(null, "No puedes Marcarlo");
                ckTv.setSelected(false);
            } else {
                tipoventa = "A";
            }
        } else {
            JOptionPane.showMessageDialog(null, "Lo Desmarcaste");
        }
    }

    public MisVentas() {
        initComponents();
        CapturandoIdentificadores();
        setTitle("Venta de productos");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        LimpiarProductoporVender();
        rbContado.setSelected(true);
        OcultarDatosCredito(false);
        PonerClienteGnrl();
        control.LlenarCombo(cboComprobantes, "select * from tipocomprobante where tipcompr not in ('Guia de Remisión','Nota de Venta','Recibo')", 2);
        T_Productos.setModel(modeloproductos);
        modeloproductos.setColumnIdentifiers(new String[]{"codctl", "Código", "Producto", "Marca", "Unidad", "Costo", "PrecioMenor", "Stock"});
        T_ProductosparaVender.setModel(model);
        modelofactura.setColumnIdentifiers(new String[]{"campo1", "campo2", "campo3", "campo4", "campo5"});
        model.setColumnIdentifiers(new String[]{"Código", "N°", "Producto", "Marca", "Precio", "Cantidad", "Importe", "Chk"});
        control.hideTableColumn(T_Productos, 0);
        control.hideTableColumn(T_ProductosparaVender, 0);
        control.setWidthTableColumn(T_Productos, 400, 2);
        control.setWidthTableColumn(T_ProductosparaVender, 400, 2);
        PonerClienteGnrl();
        PonerVentaLibre();
        BuscarProducto();
        control.bandera = false;
        lbMontoTotal.setText("0.00");
        lbVuelto.setText("0.00");
        lbitems.setText(Integer.toString(T_ProductosparaVender.getRowCount()));
        cboComprobantes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comprobante = cboComprobantes.getSelectedItem().toString();
                if (cboComprobantes.getSelectedItem().equals("Venta Libre")) {
                    //lbNumeroComprobante.setVisible(false);
                    txReferencia.setVisible(true);
                } else {
                    lbNumeroComprobante.setVisible(true);
                    txReferencia.setVisible(false);
                }
                generarComprobanteVenta();
            }
        });

    }

    public void OcultarDatosCredito(boolean b) {
        lbInicial.setVisible(b);
        txInicial.setVisible(b);
        lbdias.setVisible(b);
        cboDias.setVisible(b);
    }

    public void obtenerMontoReal() {
        if (tipoventa.equalsIgnoreCase("A")) {
            for (int f = 0; f < T_ProductosparaVender.getRowCount(); f++) {
                control.Sql = "select * from catalogoproducto where codctlg='" + T_ProductosparaVender.getValueAt(f, 0).toString() + "'";
                mtReal = mtReal + (Integer.parseInt(T_ProductosparaVender.getValueAt(f, 5).toString()) * Double.parseDouble(control.DevolverRegistroDto(control.Sql, 9)));
            }
        }
        if (tipoventa.equalsIgnoreCase("F")) {
            mtReal = 0.00;
        }

        if (tipoventa.equalsIgnoreCase("N")) {
            mtReal = Double.parseDouble(lbMontoTotal.getText());
        }

        if (tipoventa.equalsIgnoreCase("P")) {
            for (int f = 0; f < T_ProductosparaVender.getRowCount(); f++) {
                if ((Boolean) T_ProductosparaVender.getValueAt(f, 7)) {
                    control.Sql = "select * from catalogoproducto where codctlg='" + T_ProductosparaVender.getValueAt(f, 0).toString() + "'";
                    mtReal = mtReal + (Integer.parseInt(T_ProductosparaVender.getValueAt(f, 5).toString()) * Double.parseDouble(control.DevolverRegistroDto(control.Sql, 9)));
                }
            }
        }
    }

    public void obtenerMontoRealConRango(int ini, int fin) {
        if (tipoventa.equalsIgnoreCase("A")) {
            for (int f = ini; f <= fin; f++) {
                control.Sql = "select * from catalogoproducto where codctlg='" + T_ProductosparaVender.getValueAt(f, 0).toString() + "'";
                mtReal = mtReal + (Integer.parseInt(T_ProductosparaVender.getValueAt(f, 5).toString()) * Double.parseDouble(control.DevolverRegistroDto(control.Sql, 9)));
            }
        }
        if (tipoventa.equalsIgnoreCase("F")) {
            mtReal = 0.00;
        }

        if (tipoventa.equalsIgnoreCase("N")) {
            for (int f = ini; f <= fin; f++) {
                control.Sql = "select * from catalogoproducto where codctlg='" + T_ProductosparaVender.getValueAt(f, 0).toString() + "'";
                mtReal = mtReal + (Integer.parseInt(T_ProductosparaVender.getValueAt(f, 5).toString()) * Double.parseDouble(control.DevolverRegistroDto(control.Sql, 9)));
            }
            // mtReal=Double.parseDouble(lbMontoTotal.getText());
        }

        if (tipoventa.equalsIgnoreCase("P")) {
            for (int f = ini; f <= fin; f++) {
                if ((Boolean) T_ProductosparaVender.getValueAt(f, 7)) {
                    control.Sql = "select * from catalogoproducto where codctlg='" + T_ProductosparaVender.getValueAt(f, 0).toString() + "'";
                    mtReal = mtReal + (Integer.parseInt(T_ProductosparaVender.getValueAt(f, 5).toString()) * Double.parseDouble(control.DevolverRegistroDto(control.Sql, 9)));
                }
            }
        }
    }

    public void PonerClienteGnrl() {
        lbCliente.setText("CLIENTE");
        control.Sql = "select idcliente,desident,numident from vta_cliente where nomclie='CLIENTE'";
        idCliente = control.DevolverRegistroDto(control.Sql, 1);
        lbIdentificacion.setText(control.DevolverRegistroDto(control.Sql, 2) + ":  " + control.DevolverRegistroDto(control.Sql, 3));
    }

    public void PonerVentaLibre() {
        cboComprobantes.setSelectedItem("Venta Libre");
        comprobante = cboComprobantes.getSelectedItem().toString();
        generarComprobanteVenta();
    }

    public String RecalcularImporte(String ctd, String precio) {
        int cant = Integer.parseInt(ctd);
        double Importe = (Double) (cant * Double.parseDouble(precio));
        return Double.toString(Importe);
    }

    public void RenumerarJTable() {
        if (model.getRowCount() > 0) {
            for (int f = 0; f < model.getRowCount(); f++) {
                model.setValueAt((f + 1), f, 1);
            }
        }
    }

    public void SeleccionarContado() {
        OcultarDatosCredito(false);
        txInicial.setText("");
        cboDias.setSelectedIndex(-1);
        rbContado.setSelected(true);
    }

    public void SeleccionarCredito() {
        OcultarDatosCredito(true);
        cboDias.setSelectedIndex(0);
        txInicial.setText("0.00");
        txInicial.grabFocus();
    }

    public void Suprimir(KeyEvent ev) {
        if (ev.getKeyChar() == 127) {
            if (JOptionPane.showConfirmDialog(null, "Seguro quieres Quitar el Producto a Vender?", "System Message", JOptionPane.YES_NO_OPTION) == 0) {
                control.fila = T_ProductosparaVender.getSelectedRow();
                codctlg = T_ProductosparaVender.getValueAt(control.fila, 0).toString();
                cantidad = Integer.parseInt(T_ProductosparaVender.getValueAt(control.fila, 5).toString());
                ActualizaProductoxVender(false, cantidad);
                model.removeRow(control.fila);
                RenumerarJTable();
                Calcularmontototal();
                CalcularVuelto();
                BuscarProducto();
                txCliente.grabFocus();
                if (T_ProductosparaVender.getRowCount() == 0) {
                    ckTv.setSelected(false);
                }
            }
            lbitems.setText(Integer.toString(T_ProductosparaVender.getRowCount()));
        }

    }

    public void Vender() {
        mibandera = true;
        if (T_ProductosparaVender.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No Hay Ningun Producto Para Vender");
            mibandera = false;
            return;
        }
        if ((comprobante.equalsIgnoreCase("Factura")) || (comprobante.equalsIgnoreCase("Ticket Factura"))) {
            if (dni_ruc.trim().length() < 11) {
                JOptionPane.showMessageDialog(null, "No se le puede dar Factura al Cliente porque no tiene RUC");
                mibandera = false;
                return;
            }
        }
        DeterminaModalidadVenta();
        DeterminaTipoVenta();
        mtReal = 0.00;
        obtenerMontoReal();
        if (ModaVenta.equalsIgnoreCase("Contado") && (Double.parseDouble(lbVuelto.getText()) < 0)) {
            JOptionPane.showMessageDialog(null, "El Monto Entregado no puede ser menor que el Monto Total en una Venta al Contado \n"
                    + "La Venta no se realizara");
            mibandera = false;
            return;
        }

        if ((ModaVenta.equalsIgnoreCase("Credito")) && (comprobante.equalsIgnoreCase("Venta Libre"))) {
            JOptionPane.showMessageDialog(null, "Una Venta Libre no puede ser como venta libre");
            mibandera = false;
            return;
        }

        if (JOptionPane.showConfirmDialog(null, "Desea Realizar La Venta?", "System Message", JOptionPane.YES_NO_OPTION) == 0) {

            //*************************Grabar la Venta y Obtener su Identificador*************************************************************
//           JOptionPane.showMessageDialog(null, "El Cliente es: "+idCliente);
            control.Sql = "insert into venta(fecvta,moda,idCliente,Usuario_idusuario,montfactu,montreal,tipo,descuento,referencia,vuelto) values('" + control.Fecha()
                    + "','" + ModaVenta + "','" + idCliente + "','" + idUsuario + "','" + lbMontoTotal.getText() + "','" + control.decimalFormat(mtReal) + "','" + tipoventa + "','0.00','"
                    + txReferencia.getText() + "','" + lbVuelto.getText() + "')";
            control.CrearRegistro(control.Sql);
            control.Sql = "select * from venta order by 1 desc limit 1";
            idVenta = control.DevolverRegistroDto(control.Sql, 1);
            //*************************Grabar la Venta y Obtener su Identificador*************************************************************  

            //******************inserta o genera un nuevo comprobante************************************************
            control.Sql = "SELECT InsertaComprobante('" + idComprobanteGenerado + "','" + idVenta + "')";
            control.DevolverRegistroDto(control.Sql, 1);
            //******************inserta o genera un nuevo comprobante************************************************

            //****************************Llena los detalles de la venta**************************************************
            String cntReal = "";
            for (int f = 0; f < T_ProductosparaVender.getRowCount(); f++) {

                //***************************Obtener al cantidad Real**************************************************
                cantidad = 0;
                if ((Boolean) T_ProductosparaVender.getValueAt(f, 7)) {
                    cntReal = T_ProductosparaVender.getValueAt(f, 5).toString();
                    cantidad = Integer.parseInt(T_ProductosparaVender.getValueAt(f, 5).toString());
                } else {
                    cntReal = "0.00";
                }
                //***************************Obtener al cantidad Real**************************************************

                //***************************l************Crear un DetVentaFacturar****************************************************************
                codctlg = T_ProductosparaVender.getValueAt(f, 0).toString();
                control.Sql = "insert into detventafacturar(codctlg,cant,prec,idVenta,cantreal,mostrar,bandera) values('" + codctlg + "','" + T_ProductosparaVender.getValueAt(
                        f, 5).toString() + "','" + control.decimalFormat(Double.parseDouble(T_ProductosparaVender.getValueAt(f, 4).toString())) + "','"
                        + idVenta + "','" + cntReal + "','1','1')";
                control.CrearRegistro(control.Sql);
                //***************************l************Crear un DetVentaFacturar****************************************************************

                //***************************l************Crear los Venta_Producto****************************************************************
                for (int c = 1; c <= cantidad; c++) {
                    //***************************l************Capturamos el ID del Producto************************************************************  
                    control.Sql = "select pv.idproducto from por_vender pv inner join producto p on p.idproducto=pv.idproducto inner join catalogoproducto cp "
                            + "on cp.codctlg=p.catalogoproducto_codctlg where cp.codctlg='" + codctlg + "' and p.estdo='por vender' and pv.idUsuario='" + idUsuario
                            + "' and sede='" + idSede + "' limit 1;";
                    idProducto = control.DevolverRegistroDto(control.Sql, 1);
                    //***************************l************Capturamos el ID del Producto************************************************************

                    //***************************l************Creamos el producto venta****************************************************
                    control.Sql = "insert into venta_producto(prc,Venta_idVenta,Producto_idProducto,situ) values('" + control.decimalFormat(Double.parseDouble(
                            T_ProductosparaVender.getValueAt(f, 4).toString())) + "','" + idVenta + "','" + idProducto + "','N')";
                    control.CrearRegistro(control.Sql);
                    //***************************l************Creamos el producto venta****************************************************

                    //***************************l***********Actutlizamos el producto y eliminamos el por vender********************************
                    control.Sql = "delete from por_vender where idproducto='" + idProducto + "'";
                    control.EliminarRegistro(control.Sql);
                    control.Sql = "update producto set estdo='Vendido' where idproducto='" + idProducto + "'";
                    control.EditarRegistro(control.Sql);
                    //***************************l***********Actutlizamos el producto y eliminamos el por vender********************************
                }
            }

            if (ModaVenta.equalsIgnoreCase("Contado")) {
                //***************************l***********Grabando el pago realizado*******************************************************
                control.Sql = "insert into pagos(fecha,hora,id,tipo,usuario,monto,pagador,comprobante,numerocomprobante,descuento) values('" + control.Fecha()
                        + "','" + control.Hora() + "','" + idVenta + "','Venta','" + info.getUsuario() + "','" + control.decimalFormat(mtReal) + "','" + lbCliente.getText() + "','"
                        + comprobante + "','" + serie + "-" + numcomp + "','0.00')";
                control.CrearRegistro(control.Sql);
                //***************************l***********Grabando el pago realizado*******************************************************
            } else {
                double mt = Double.parseDouble(lbMontoTotal.getText()) - Double.parseDouble(txInicial.getText());
                control.Sql = "insert into deuda(montdeu,pgoinici,fecdeud,idVenta) values('" + control.decimalFormat(mt) + "','" + txInicial.getText()
                        + "','" + control.DevolverRegistroDto("select adddate(curdate(),interval " + cboDias.getSelectedItem().toString() + " day)", 1) + "','" + idVenta
                        + "');";
                control.CrearRegistro(control.Sql);
                control.Sql = "select iddeuda from deuda where idventa='" + idVenta + "' order by iddeuda desc limit 1;";
                String de = control.DevolverRegistroDto(control.Sql, 1);
                control.Sql = "insert into pagoxdeuda (fecpgxdeu, montpag, idDeuda) values(curdate(),'"
                        + txInicial.getText() + "','" + de + "');";
                control.CrearRegistro(control.Sql);
            }
            ImprimirVersionUltima(idVenta);
        } else {
            mibandera = false;
        }
        //}  
    }

    public void Vendiendo() {
        if (cboComprobantes.getSelectedItem().toString().equalsIgnoreCase("Factura")) {
            if (dni_ruc.trim().length() < 11) {
                JOptionPane.showMessageDialog(null, "No se le puede dar Factura al Cliente porque no tiene RUC");
                mibandera = false;
                return;
            }

            DeterminaModalidadVenta();
            DeterminaTipoVenta();
            if (ModaVenta.equalsIgnoreCase("Contado")) {
                if (Double.parseDouble(lbVuelto.getText()) < 0) {
                    JOptionPane.showMessageDialog(null, "El Monto Cancelado no puede ser menor que el Monto Total");
                    control.MarcarTexto(txMontoEntregado);
                    mibandera = false;
                    return;
                }
            }
            if ((ModaVenta.equalsIgnoreCase("Credito")) && (comprobante.equalsIgnoreCase("Venta Libre"))) {
                JOptionPane.showMessageDialog(null, "Una Venta Libre no puede ser como venta libre");
                mibandera = false;
                return;
            }

            int ctnd = 0, tope = 0, lim1 = 0, lim2 = 0, TotalFilas = 0;   //Variables a utilizar para determinar rangos
            //int  tope=0, lim1=0,lim2=0,TotalFilas=0;   //Variables a utilizar para determinar rangos
            tope = Integer.parseInt(control.DevolverRegistroDto("select * from config where idconfig='maxFilasFactura'", 2));//Obtener la cantidad máxima.
            TotalFilas = T_ProductosparaVender.getRowCount();
            String idclienteTmp = idCliente;
            if (ModaVenta.equalsIgnoreCase("Credito")) {
                adelanto = Double.parseDouble(txInicial.getText());
            }

            if (TotalFilas > tope) {

                try {
                    control.Sql = "select @re:=truncate((" + TotalFilas + "/" + tope + "),0) dato,if((mod(" + TotalFilas + "," + tope + "))>0,(@re+1),@re) nuevo;";//obtenemos las veces que se tiene que
                    String cadenanum = control.DevolverRegistroDto(control.Sql, 2);
                    ctnd = Integer.parseInt(cadenanum);//Capturamos el total de filas
                } catch (Exception e) {
                }

                JOptionPane.showMessageDialog(null, "Atención se van  Imprimir " + ctnd + " Facturas");
                DeterminaModalidadVenta();
                DeterminaTipoVenta();
                int fila = 0;
                lim1 = 0;
                lim2 = tope - 1;

                for (int i = 1; i <= ctnd; i++) {   // CONTROLAMOS LAS VENTAS QUE SE VAN  A REALIZAR
                    mtReal = 0.00;
                    obtenerMontoRealConRango(lim1, lim2);
                    JOptionPane.showMessageDialog(null, "el monto es " + mtReal);

                    //*************************Grabar la Venta y Obtener su Identificador******************************************************
                    //JOptionPane.showMessageDialog(null, "El Cliente es: "+idclienteTmp);
                    control.Sql = "insert into venta(fecvta,moda,idCliente,Usuario_idusuario,montfactu,montreal,tipo,descuento,referencia,vuelto) values('"
                            + control.Fecha() + "','" + ModaVenta + "','" + idclienteTmp + "','" + idUsuario + "','" + control.decimalFormat(mtReal) + "','" + control.decimalFormat(mtReal) + "','"
                            + tipoventa + "','0.00','" + txReferencia.getText() + "','" + lbVuelto.getText() + "')";
                    control.CrearRegistro(control.Sql);
                    control.Sql = "select * from venta order by 1 desc limit 1";
                    idVenta = control.DevolverRegistroDto(control.Sql, 1);
                    //*************************Grabar la Venta y Obtener su Identificador******************************************************

                    //******************inserta o genera un nuevo comprobante****************************************************************
                    if (i > 1) {
                        generarComprobanteVenta();
                    }

                    control.Sql = "SELECT InsertaComprobante('" + idComprobanteGenerado + "','" + idVenta + "')";
                    control.DevolverRegistroDto(control.Sql, 1);
                    //******************inserta o genera un nuevo comprobante****************************************************************

                    for (int cl = 1; cl <= tope; cl++) {   // el tope es 11 actualmente
                        if (fila < TotalFilas) {
                            //***************************Obtener al cantidad Real para la venta*******************************************************
                            cantidad = 0;
                            String cntReal = "";
                            if ((Boolean) T_ProductosparaVender.getValueAt(fila, 7)) {
                                cntReal = T_ProductosparaVender.getValueAt(fila, 5).toString();
                                cantidad = Integer.parseInt(T_ProductosparaVender.getValueAt(fila, 5).toString());
                            } else {
                                cntReal = "0.00";
                            }
                            //***************************Obtener al cantidad Real******************************************************************

                            //***************************l************Crear un DetVentaFacturar****************************************************************
                            codctlg = T_ProductosparaVender.getValueAt(fila, 0).toString();
                            control.Sql = "insert into detventafacturar(codctlg,cant,prec,idVenta,cantreal,mostrar,bandera) values('" + codctlg + "','" + T_ProductosparaVender.getValueAt(
                                    fila, 5).toString() + "','" + control.decimalFormat(Double.parseDouble(T_ProductosparaVender.getValueAt(fila, 4).toString())) + "','"
                                    + idVenta + "','" + cntReal + "','1','1')";
                            control.CrearRegistro(control.Sql);
                            //***************************l************Crear un DetVentaFacturar****************************************************************   

                            for (int c = 1; c <= cantidad; c++) {
                                //***************************l************Capturamos el ID del Producto*************************************************
                                control.Sql = "select pv.idproducto from por_vender pv inner join producto p on p.idproducto=pv.idproducto inner join catalogoproducto cp "
                                        + "on cp.codctlg=p.catalogoproducto_codctlg where cp.codctlg='" + codctlg + "' and p.estdo='por vender' and pv.idUsuario='" + idUsuario
                                        + "' and sede='" + idSede + "' limit 1;";
                                idProducto = control.DevolverRegistroDto(control.Sql, 1);
                                //***************************l************Capturamos el ID del Producto*************************************************

                                //***************************l************Creamos el producto venta****************************************************
                                control.Sql = "insert into venta_producto(prc,Venta_idVenta,Producto_idProducto,situ) values('" + control.decimalFormat(Double.parseDouble(
                                        T_ProductosparaVender.getValueAt(fila, 4).toString())) + "','" + idVenta + "','" + idProducto + "','N')";
                                control.CrearRegistro(control.Sql);
                                //***************************l************Creamos el producto venta****************************************************

                                //***************************l***********Actutlizamos el producto y eliminamos el por vender********************************
                                control.Sql = "delete from por_vender where idproducto='" + idProducto + "' and idusuario='" + idUsuario + "' and sede='" + idSede + "'";
                                control.EliminarRegistro(control.Sql);

                                control.Sql = "update producto set estdo='Vendido' where idproducto='" + idProducto + "'";
                                control.EditarRegistro(control.Sql);
                                //***************************l***********Actutlizamos el producto y eliminamos el por vender********************************
                            }
                        } else {
                            break;
                        }
                        fila++;    //Incrementa las filas incluidas
                    }
                    if (ModaVenta.equalsIgnoreCase("Contado")) {
                        //***************************l***********Grabando el pago realizado*******************************************************
                        control.Sql = "insert into pagos(fecha,hora,id,tipo,usuario,monto,pagador,comprobante,numerocomprobante,descuento) values('" + control.Fecha()
                                + "','" + control.Hora() + "','" + idVenta + "','Venta','" + info.getUsuario() + "','" + control.decimalFormat(mtReal) + "','" + lbCliente.getText() + "','"
                                + comprobante + "','" + serie + "-" + numcomp + "','0.00')";
                        control.CrearRegistro(control.Sql);
                        //***************************l***********Grabando el pago realizado*******************************************************
                    } else {
                        double deuda = 0.00;  //El Monto de la Deuda
                        if (mtReal > adelanto) {
                            deuda = mtReal - adelanto;
                        } else {
                            deuda = 0.00;
                        }

                        control.Sql = "insert into deuda(montdeu,pgoinici,fecdeud,idVenta) values('" + control.decimalFormat(deuda) + "','"
                                + control.decimalFormat(adelanto) + "','" + control.DevolverRegistroDto("select adddate(curdate(),interval "
                                + cboDias.getSelectedItem().toString() + " day)", 1) + "','" + idVenta + "');";

                        control.CrearRegistro(control.Sql);
                        control.Sql = "select iddeuda from deuda where idventa='" + idVenta + "' order by iddeuda desc limit 1;";

                        String de = control.DevolverRegistroDto(control.Sql, 1);
                        control.Sql = "insert into pagoxdeuda (fecpgxdeu, montpag, idDeuda) values(curdate(),'"
                                + control.decimalFormat(adelanto) + "','" + de + "');";
                        control.CrearRegistro(control.Sql);

                        if (mtReal > adelanto) {
                            deuda = mtReal - adelanto;
                            adelanto = 0.00;
                        } else {
                            adelanto = adelanto - mtReal;
                        }
                    }
                    ImprimirVersionUltima(idVenta);
                    lim1 = lim2 + 1; //0+10=10  10+10=20
                    if ((TotalFilas - fila) > tope) {
                        lim2 = lim2 + tope; //Calculamos los nuevos limites     
                    } else {
                        lim2 = lim2 + (TotalFilas - fila); //Calculamos los nuevos limites  
                    }
                    JOptionPane.showMessageDialog(null, "Final de la Venta " + i);
                }
                mibandera = true;
            } else {
                Vender();
            }
        } else {
            Vender();
        }

        //*************************************LIMPIAR EL FROMULARIO DE VENTAS*******************************************
        if (mibandera) {  //Ayuda a saber si se limpia o no la venta
            txBuscar.setText(null);
            PonerClienteGnrl();
            LimpiarProductoporVender();
            LimpiarElModel();
            Calcularmontototal();
            txMontoEntregado.setText(null);
            CalcularVuelto();
            PonerClienteGnrl();
            PonerVentaLibre();
            ckTv.setSelected(false);
            SeleccionarContado();
            txCliente.setText(null);
            BuscarProducto();
        }

        //*************************************LIMPIAR EL FROMULARIO DE VENTAS*******************************************
    }

    /**
     * ****************************************** LOS MÉTODOS*****************************************
     */

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lbCliente = new javax.swing.JLabel();
        txCliente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lbIdentificacion = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lbMontoTotal = new javax.swing.JLabel();
        txMontoEntregado = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbVuelto = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lbNumeroComprobante = new javax.swing.JLabel();
        cboComprobantes = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        rbContado = new javax.swing.JRadioButton();
        rbCredito = new javax.swing.JRadioButton();
        txInicial = new javax.swing.JTextField();
        lbInicial = new javax.swing.JLabel();
        lbdias = new javax.swing.JLabel();
        cboDias = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        T_ProductosparaVender = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        lbitems = new javax.swing.JLabel();
        txReferencia = new javax.swing.JTextField();
        ckTv = new javax.swing.JCheckBox();
        lbReferencia = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        T_Productos = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        txBuscar = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bVender = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(0, 51, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbCliente.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbCliente.setText("Miguel Angel Silva Zapata");
        jPanel1.add(lbCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 20, 410, -1));

        txCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txClienteKeyTyped(evt);
            }
        });
        jPanel1.add(txCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 300, -1));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("Buscar");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 24, -1, -1));

        lbIdentificacion.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbIdentificacion.setText("Cliente");
        jPanel1.add(lbIdentificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, 130, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1282, 55));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Los  Montos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbMontoTotal.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbMontoTotal.setText("50.00");
        jPanel3.add(lbMontoTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 20, 100, -1));

        txMontoEntregado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txMontoEntregadoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txMontoEntregadoKeyTyped(evt);
            }
        });
        jPanel3.add(txMontoEntregado, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 52, 130, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText("Monto Entregado");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 52, -1, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel6.setText("           Vuelto    :");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        lbVuelto.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbVuelto.setText("50.00");
        jPanel3.add(lbVuelto, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 90, 100, -1));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel8.setText("        Monto Total");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 100, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 3, 390, 110));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Facturación", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbNumeroComprobante.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbNumeroComprobante.setText("0001-355");
        jPanel4.add(lbNumeroComprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 110, -1));

        jPanel4.add(cboComprobantes, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 180, -1));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setText("Tipo Comprobante");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel11.setText("                 Número");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(398, 3, 435, 110));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Modalidad de Venta", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonGroup1.add(rbContado);
        rbContado.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbContado.setMnemonic('o');
        rbContado.setText("Contado");
        rbContado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbContadoActionPerformed(evt);
            }
        });
        jPanel5.add(rbContado, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, -1, -1));

        buttonGroup1.add(rbCredito);
        rbCredito.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        rbCredito.setMnemonic('r');
        rbCredito.setText("Crédito");
        rbCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCreditoActionPerformed(evt);
            }
        });
        jPanel5.add(rbCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, 20));

        txInicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txInicialKeyTyped(evt);
            }
        });
        jPanel5.add(txInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 125, -1));

        lbInicial.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbInicial.setText("                Inicial");
        jPanel5.add(lbInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, -1, -1));

        lbdias.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbdias.setText("                Dias");
        jPanel5.add(lbdias, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 85, -1, -1));

        cboDias.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "7", "15", "30", "45" }));
        cboDias.setSelectedIndex(-1);
        jPanel5.add(cboDias, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 125, -1));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(835, 3, 440, 110));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 53, 1281, 118));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos a Vender", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel6.setForeground(new java.awt.Color(0, 51, 51));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        T_ProductosparaVender.setModel(new javax.swing.table.DefaultTableModel(
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
        T_ProductosparaVender.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                T_ProductosparaVenderFocusGained(evt);
            }
        });
        T_ProductosparaVender.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                T_ProductosparaVenderKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(T_ProductosparaVender);

        jPanel6.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 40, 1110, 240));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos a Vender", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel9.setForeground(new java.awt.Color(0, 51, 51));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable3);

        jPanel9.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 50, 1080, 230));

        jPanel6.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 335, 1100, 290));

        lbitems.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbitems.setText("Referencia");
        jPanel6.add(lbitems, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 20, -1, -1));
        jPanel6.add(txReferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(662, 18, 420, -1));

        ckTv.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        ckTv.setText("A");
        ckTv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckTvActionPerformed(evt);
            }
        });
        jPanel6.add(ckTv, new org.netbeans.lib.awtextra.AbsoluteConstraints(1083, 16, 35, -1));

        lbReferencia.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbReferencia.setText("Referencia");
        jPanel6.add(lbReferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 24, -1, -1));

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 335, 1131, 290));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de productos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel7.setForeground(new java.awt.Color(0, 51, 51));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        T_Productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                T_ProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(T_Productos);

        jPanel7.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 50, 1110, 110));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel14.setText("Buscar");
        jPanel7.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 24, -1, -1));

        txBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txBuscarActionPerformed(evt);
            }
        });
        txBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txBuscarKeyReleased(evt);
            }
        });
        jPanel7.add(txBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 440, -1));

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 1131, 170));

        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setMnemonic('s');
        bSalir.setText("Salir");
        bSalir.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel8.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 320, 120, 50));

        bVender.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bVender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save.png"))); // NOI18N
        bVender.setMnemonic('v');
        bVender.setText("Vender");
        bVender.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bVender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bVenderActionPerformed(evt);
            }
        });
        jPanel8.add(bVender, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 80, 120, 50));

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setMnemonic('c');
        bCancelar.setText("Cancelar");
        bCancelar.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel8.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 190, 120, 50));

        getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1132, 170, 150, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void rbCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCreditoActionPerformed
        SeleccionarCredito();
    }//GEN-LAST:event_rbCreditoActionPerformed
    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
        LimpiarProductoporVender();
        dispose();
    }//GEN-LAST:event_bSalirActionPerformed
    private void rbContadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbContadoActionPerformed
        SeleccionarContado();
    }//GEN-LAST:event_rbContadoActionPerformed
    private void txClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txClienteKeyTyped
        BuscaCliente(evt);
    }//GEN-LAST:event_txClienteKeyTyped
    private void txBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarKeyReleased
        BuscarProducto();
    }//GEN-LAST:event_txBuscarKeyReleased
    private void txBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txBuscarActionPerformed
        control.bandera = true;
        AgregarProductos(0);
    }//GEN-LAST:event_txBuscarActionPerformed
    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
        Cancelar();
    }//GEN-LAST:event_bCancelarActionPerformed
    private void T_ProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_T_ProductosMouseClicked
        if (evt.getClickCount() == 2) {
            control.bandera = true;
            AgregarProductos(1);
        }
    }//GEN-LAST:event_T_ProductosMouseClicked
    private void T_ProductosparaVenderFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_T_ProductosparaVenderFocusGained
        control.bandera = false;  //Para que se pueda trabajar
    }//GEN-LAST:event_T_ProductosparaVenderFocusGained
    private void T_ProductosparaVenderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_T_ProductosparaVenderKeyPressed
        Suprimir(evt);
    }//GEN-LAST:event_T_ProductosparaVenderKeyPressed
    private void ckTvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckTvActionPerformed
        MarcarCheck();
    }//GEN-LAST:event_ckTvActionPerformed
    private void bVenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bVenderActionPerformed
        Vendiendo();
    }//GEN-LAST:event_bVenderActionPerformed
    private void txMontoEntregadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txMontoEntregadoKeyReleased
        CalcularVuelto();
    }//GEN-LAST:event_txMontoEntregadoKeyReleased
    private void txMontoEntregadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txMontoEntregadoKeyTyped
        control.decimal(evt, txMontoEntregado.getText());
        if (lbMontoTotal.getText().equalsIgnoreCase("0.00")) {
            evt.consume();
        }
    }//GEN-LAST:event_txMontoEntregadoKeyTyped
    private void txInicialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txInicialKeyTyped
        control.decimal(evt, txInicial.getText());
    }//GEN-LAST:event_txInicialKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable T_Productos;
    public static javax.swing.JTable T_ProductosparaVender;
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bSalir;
    private javax.swing.JButton bVender;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboComprobantes;
    private javax.swing.JComboBox cboDias;
    private javax.swing.JCheckBox ckTv;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable3;
    public static javax.swing.JLabel lbCliente;
    public static javax.swing.JLabel lbIdentificacion;
    private javax.swing.JLabel lbInicial;
    private javax.swing.JLabel lbMontoTotal;
    private javax.swing.JLabel lbNumeroComprobante;
    private javax.swing.JLabel lbReferencia;
    private javax.swing.JLabel lbVuelto;
    private javax.swing.JLabel lbdias;
    private javax.swing.JLabel lbitems;
    private javax.swing.JRadioButton rbContado;
    private javax.swing.JRadioButton rbCredito;
    private javax.swing.JTextField txBuscar;
    public static javax.swing.JTextField txCliente;
    private javax.swing.JTextField txInicial;
    private javax.swing.JTextField txMontoEntregado;
    private javax.swing.JTextField txReferencia;
    // End of variables declaration//GEN-END:variables
}
