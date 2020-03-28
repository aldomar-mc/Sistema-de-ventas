package Ventanas;
/************* @author Ing. Miguel Angel Silva Zapata. *************************/

import Clases.Controlador;import Clases.Controladoromar;import Clases.CustomTableModel;
import Clases.FormatoTabla;import Clases.IMPRIMIR;import Clases.InfoGeneral;import Clases.Mimodelo;
import Clases.Numero_a_Letra;import java.awt.Event;import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;import java.awt.event.KeyEvent;import java.awt.event.MouseEvent;
import java.text.DecimalFormat;import java.util.ArrayList;import java.util.Date;
import javax.swing.ImageIcon;import javax.swing.JOptionPane;import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;import java.util.HashMap;import java.util.Map;

public class Ventas extends javax.swing.JInternalFrame {
    /*********************************************Atributos********************************************************/
    
    private Mimodelo modeloClientes = new Mimodelo();private Mimodelo modeloProductosAlmacen = new Mimodelo();    
    private Mimodelo modeloProductosAVender1 = new Mimodelo();private InfoGeneral info = new InfoGeneral();
    private DecimalFormat forma = new DecimalFormat("0.00");private Controlador control = new Controlador();
    private Numero_a_Letra nume = new Numero_a_Letra();private IMPRIMIR imprime = new IMPRIMIR();
    private double suma=0, valor=0, totalVenta = 0, precioGrabar,descontar = 0.0;
    private String codCliente = "",tipodeventa="", nombreProducto, marcaProducto,idVenta, idProductoGrabar, idProducto
   , idencte, sr = "", srt = "", stockProducto,idCatalogo, mostrar, igv, subTotal, text = "", serie = "", numcomp = "";
    private int cantidadagregar = 0, canfilas = 0, controlCliente = 0, TipoVenta = 0, ppp = 0, cantidadVender,cantidadmostrar, controlad = 0, 
    idComprobanteGenerado,contadordeproductos=0, cantidadstock = 0, cantidadanterior = 0, idtProductos = 0,idtProdaVender = 0, Libre=1; 
    private ArrayList<String> Lista = new ArrayList<String>();private String datosProducto[] = new String[10];
    public String comprobante="";public double montoreal=0.0,elprecio=0.0;public  boolean banderitas=false;     
     
     
    private CustomTableModel model = new CustomTableModel() {
        private DecimalFormat format = new DecimalFormat("0.00");
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 4 || column == 5 || column == 9;
        }
        @Override
        public void setValueAt(Object aValue, int row, int column) {
            if (column == 4) {
                try {
                    if (!chkLibre.isSelected()) {
                        double preciouni = Double.parseDouble(aValue.toString());
                        int cantidadun = Integer.parseInt(super.getValueAt(row, 5).toString());
                        super.setValueAt(format.format(preciouni).replace(",", "."), row, column);
                        super.setValueAt(format.format(preciouni * cantidadun).replace(",", "."), row, 7);
                    }
                } catch (Exception e) {
                    if (!chkLibre.isSelected()) {
                        int cantidadun = Integer.parseInt(super.getValueAt(row, 5).toString());
                        double importeto = Double.parseDouble(super.getValueAt(row, 7).toString());
                        super.setValueAt(format.format(importeto / cantidadun).replace(",", "."), row, column);
                        super.setValueAt(format.format(importeto).replace(",", "."), row, 7);
                    }
                }
            } else {
                if (column == 5) {
                    int cantianterior = Integer.parseInt(super.getValueAt(row, 5).toString());
                    int filaant = row;
                    try {
                        //String produc = super.getValueAt(row, 1).toString()+" "+super.getValueAt(row, 2).toString();
                        String produc = super.getValueAt(row, 2).toString()+" "+super.getValueAt(row, 3).toString();
                        //JOptionPane.showMessageDialog(null, produc);
                        int cantidnueva = Integer.parseInt(aValue.toString());
                        if (cantidnueva > 0) {
                            double precio = Double.parseDouble(super.getValueAt(row, 4).toString());
                            double importe = Double.parseDouble(super.getValueAt(row, 7).toString());
                            //boolean v = agregarmas((cantidnueva - cantianterior), produc, super.getValueAt(row, 0).toString());
                            boolean v = agregarmasnew((cantidnueva - cantianterior), produc, super.getValueAt(row, 0).toString());
                            if (v == true) {
                                filaant = row;String codigo = "";codigo = super.getValueAt(row, 0).toString();
                                
                                //JOptionPane.showMessageDialog(null, "La Cantidad Ingresadas  " + aValue.toString() + "\n Superado El Stock.");
                                Resetear(v, cantianterior);
                                montoreal = montoreal + (precio * cantianterior);
                                txmontodiferente.setText(Double.toString(montoreal));
                                buscarProducto();
                            } else {
                                super.setValueAt((int) (cantidnueva), row, column);
                                super.setValueAt(format.format(cantidnueva * precio).replace(",", "."), row, 7);
                                montoreal = precio * cantidnueva;
                                montoreal = montoreal + (precio * (cantidnueva - cantianterior));
                                txmontodiferente.setText(Double.toString(montoreal));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (column == 9) {
                        String barra = "", us = "", ID = (String) tProdaVender.getValueAt(row, 0);
                        int ct = Integer.parseInt(tProdaVender.getValueAt(row, 5).toString());

                        //*****Capturamos el codigo de barra***********************************
                        control.Sql = "SELECT * FROM producto WHERE idproducto='" + ID + "'; ";
                        barra = control.DevolverRegistroDto(control.Sql, 4).toString();
                        //*****Capturamos el codigo de barra***********************************         

                        //*****Capturamoms el identificar del usuario******************************************                  
                        control.Sql = "SELECT idusuario FROM usuario WHERE nomusr='" + info.getUsuario() + "'; ";
                        us = control.DevolverRegistroDto(control.Sql, 1);
                        //*****Capturamoms el identificar del usuario******************************************
                        if ((Boolean) tProdaVender.getValueAt(row, 9)) {
                            control.fila = 0;
                            while (control.fila <= ct) {
                                if (control.fila == 0) {
                                    control.Sql = "DELETE FROM por_vender WHERE idproducto='" + ID + "' AND idusuario='" + us + "' AND sede='" + Controlador.sede + "'; ";
                                    control.EliminarRegistro(control.Sql);
                                    control.Sql = "UPDATE producto SET estdo='Disponible' WHERE idproducto='" + ID + "'; ";
                                    control.EditarRegistro(control.Sql);
                                }
                                else {
                                    control.Sql = "SELECT * FROM producto WHERE codbrr='" + barra + "' AND estdo='Por Vender' ORDER BY idproducto ASC LIMIT 1; ";
                                    ID = control.DevolverRegistroDto(control.Sql, 1);
                                    control.Sql = "SELECT * FROM por_vender WHERE idproducto='" + ID + "' AND idusuario='" + us + "' AND sede='" + Controlador.sede + "'; ";
                                    if (control.Verificarconsulta(control.Sql)) {
                                        control.Sql = "DELETE FROM por_vender WHERE idproducto='" + ID + "' AND " + "idusuario='" + us + "' AND sede='" + Controlador.sede + "'; ";
                                        control.EliminarRegistro(control.Sql);
                                        control.Sql = "UPDATE producto SET estdo='Disponible' WHERE idproducto='" + ID + "'; ";
                                        control.EditarRegistro(control.Sql);
                                    }
                                }
                                control.fila++;
                            }
                            buscarProducto();
                        } else {
                            JOptionPane.showMessageDialog(null, "Ya No Es Posible Todo Vuelve A Cero.");
                            ActualizaEsta();
                            Cancelar();
                        }
                    }
                    try {
                        super.setValueAt(aValue, row, column);
                    } catch (Exception e) {}
                }
            }
            calcularmontototal();
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 9) {
                return Boolean.class;
            }
            return super.getColumnClass(columnIndex);
        }
        private boolean agregarmas(int d, String da, String codigo) {
         boolean veris = false;
         /*********************obtiene la cantidad de productos disponible**************************/
         /*control.Sql = String.format("SELECT COUNT(p.catalogoproducto_codctlg) AS STOCK "
         + "FROM producto p, catalogoproducto c, modelo l, marca m,unidad un, sede s "
         + "WHERE p.catalogoproducto_codctlg = c.codctlg AND p.idSede = s.idSede AND "
         + "c.idunidad = un.idunidad AND c.idmodelos = l.idmodelos AND c.idMarca = m.idMarca AND "
         + "p.estdo = 'Disponible' AND CONCAT(l.nommod,' ',c.nomctlg,' ',m.nommrc) = '%s' AND s.nomse = '" + Controlador.sede
         + "' AND NOT p.idProducto IN (SELECT venta_producto.producto_idProducto AS Producto_idproducto "
         + "FROM venta_producto where situ='N') GROUP BY s.nomse, c.codctlg; ", da);*/
         
         control.Sql="select count(*) from producto pd inner join catalogoproducto cp on cp.codctlg=pd.catalogoproducto_codctlg "
         + "inner join modelocatalogo mc on mc.codctlg=cp.codctlg inner join modelo md on md.idmodelos=mc.idmodelos inner "
         + "join marca mrc on mrc.idmarca=cp.idmarca where CONCAT(md.nommod,' ',cp.nomctlg,' ',mrc.nommrc) ='"
        +da+"' and pd.estdo='Disponible';";
         
    //     System.out.println("Ver "+control.Sql);
         int a = 0;
            if (control.Verificandoconsulta(control.Sql)) {
                a = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
            }
            /*** ******************obtiene la cantidad de productos disponibles***************************/
            if (d > a) {
                veris = true;
            } else {
                if (d > 0) {
                    control.fila = 0;
                    while (control.fila < d) {
                       //,marca mrc
                       control.Sql = String.format("SELECT idproducto "
                                    + "FROM producto p,catalogoproducto c,modelo l,unidad un,sede s,marca mrc "
                                    + "WHERE p.catalogoproducto_codctlg = c.codctlg AND p.idSede = s.idSede AND "
                                    + "c.idunidad = un.idunidad AND c.idmodelos = l.idmodelos AND  mrc.idmarca=c.idmarca and p.estdo ='Disponible' AND "
                                    + "CONCAT(l.nommod,' ',c.nomctlg,' ',mrc.nommrc) = '%s' AND s.nomse = '" + Controlador.sede + "' AND "
                                    + "NOT p.idProducto IN (select venta_producto.producto_idProducto AS "
                                    + "Producto_idproducto FROM venta_producto WHERE situ='N') GROUP BY s.nomse, c.codctlg; ", da); 
                       
                        /*control.Sql = String.format("SELECT idproducto "
                                    + "FROM producto p,catalogoproducto c,modelo l,unidad un,sede s "
                                    + "WHERE p.catalogoproducto_codctlg = c.codctlg AND p.idSede = s.idSede AND "
                                    + "c.idunidad = un.idunidad AND c.idmodelos = l.idmodelos AND p.estdo ='Disponible' AND "
                                    + "CONCAT(l.nommod,' ',c.nomctlg) = '%s' AND s.nomse = '" + Controlador.sede + "' AND "
                                    + "NOT p.idProducto IN (select venta_producto.producto_idProducto AS "
                                    + "Producto_idproducto FROM venta_producto WHERE situ='N') GROUP BY s.nomse, c.codctlg; ", da);*/
                        
                        if (!chkLibre.isSelected()) {
                            ListaProductoPorVendido(control.DevolverRegistroDto(control.Sql, 1), "Por Vender");
                        }
                        control.fila++;
                    }
                } else {
                    control.fila = 0;
                    d = d * -1;
                    control.Sql = "SELECT idusuario FROM usuario WHERE nomusr = '" + info.getUsuario() + "'; ";
                    String idus = control.DevolverRegistroDto(control.Sql, 1);
                    while (control.fila < d) {
                        control.Sql = String.format("SELECT id FROM vta_porvender v where v.Catalogo = '%s' AND " +
                        "Sede = '%s' AND Usuario = '%s' ORDER BY 1 ASC LIMIT 1;", da, Controlador.sede, info.getUsuario());
                        ListaProductoPorVendido(control.DevolverRegistroDto(control.Sql, 1), "Disponible");
                        control.ejecutar(String.format("DELETE FROM por_vender WHERE idproducto = '%s' AND " +
                                                       "idusuario = '%s' AND sede = '%s'; ", da, idus, Controlador.sede));
                        
                        control.fila++;
                    }
                }
                buscarProducto();
            }
            return veris;
        }

        private boolean agregarmasnew(int d, String da, String codigo) {
         boolean veris = false;         
         control.Sql="select count(*) from producto pd inner join catalogoproducto cp on cp.codctlg=pd.catalogoproducto_codctlg "
         + "inner join modelocatalogo mc on mc.codctlg=cp.codctlg inner join modelo md on md.idmodelos=mc.idmodelos inner "
         + "join marca mrc on mrc.idmarca=cp.idmarca where CONCAT(md.nommod,' ',cp.nomctlg,' ',mrc.nommrc) ='"
        +da+"' and pd.estdo='Disponible';";
         
         //System.out.println("Ver "+control.Sql);
         int a = 0;
            if (control.Verificandoconsulta(control.Sql)) {
                a = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
                //JOptionPane.showMessageDialog(null,"Ingreso y es "+a);
            }
            /*** ******************obtiene la cantidad de productos disponibles***************************/
            if (d > a) {
             veris = true;
            }
            else {
              if (d > 0) {
               control.fila = 0;
               while (control.fila < d) {
                control.Sql = String.format("SELECT idproducto FROM producto p,catalogoproducto c,modelo l,unidad un,sede s,"
                + "marca mrc WHERE p.catalogoproducto_codctlg = c.codctlg AND p.idSede = s.idSede AND c.idunidad = "
                + "un.idunidad AND c.idmodelos = l.idmodelos AND  mrc.idmarca=c.idmarca and p.estdo ='Disponible' AND "
                + "CONCAT(l.nommod,' ',c.nomctlg,' ',mrc.nommrc) = '%s' AND s.nomse = '" + Controlador.sede + "' AND "
                + "NOT p.idProducto IN (select venta_producto.producto_idProducto AS "
                + "Producto_idproducto FROM venta_producto WHERE situ='N') GROUP BY s.nomse, c.codctlg; ", da);                         
                if (!chkLibre.isSelected()) {
                   ListaProductoPorVendido(control.DevolverRegistroDto(control.Sql, 1), "Por Vender");
                }
                control.fila++;
               }
              }
              else {
                control.fila = 0;d = d * -1;
                control.Sql = "SELECT idusuario FROM usuario WHERE nomusr = '" + info.getUsuario() + "'; ";
                String idus = control.DevolverRegistroDto(control.Sql, 1);
                while (control.fila < d) {
                  control.Sql = String.format("SELECT id FROM vta_porvender v where v.Catalogo = '%s' AND " +
                  "Sede = '%s' AND Usuario = '%s' ORDER BY 1 ASC LIMIT 1;", da, Controlador.sede, info.getUsuario());
                  ListaProductoPorVendido(control.DevolverRegistroDto(control.Sql, 1), "Disponible");
                  control.ejecutar(String.format("DELETE FROM por_vender WHERE idproducto = '%s' AND " +
                  "idusuario = '%s' AND sede = '%s'; ", da, idus, Controlador.sede));
                  control.fila++;
                }
              }
              buscarProducto();
            }
            return veris;
        }
    };
    /*****************************************************Fin Atributos*********************************************/

    
    /*** *****************************************************Métodos************************************************/
    public Ventas() {
        initComponents();setTitle("Venta de productos");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        txtBucarProducto.grabFocus();txtBucarProducto.requestFocus();txmontodiferente.setVisible(true);
        tClientes.setModel(modeloClientes);modeloClientes.setColumnIdentifiers(new String[]{"Código", "Cliente", "Dirección", "Ruc/Dni"});
        control.hideTableColumn(tClientes, 0);control.setWidthTableColumn(tClientes, 250, 1);
        control.forma_table_ver(modeloClientes, tClientes);tProductos.setModel(modeloProductosAlmacen);
        modeloProductosAlmacen.setColumnIdentifiers(new String[]{"Código", "Codigo", "Producto", "Marca", "Unidad", "Costo", "PrecioMenor", "Stock"});
        control.hideTableColumn(tProductos, 0);control.setWidthTableColumn(tProductos, 150, 1);
        control.setWidthTableColumn(tProductos, 650, 2);control.setWidthTableColumn(tProductos, 250, 3);
        control.setWidthTableColumn(tProductos, 130, 4, 5, 6, 7);
        control.forma_table_ver(modeloProductosAlmacen, tProductos);
        tProdaVender.setModel(model);
        model.setColumnIdentifiers(new String[]{"Código", "N°", "Producto",
        "Marca", "Precio", "Cantidad", "Cnt. Descargar", "Importe", "Mostrar", "Chk"});
        
        control.hideTableColumn(tProdaVender, 6);control.hideTableColumn(tProdaVender, 8);
        control.setWidthTableColumn(tProdaVender, 300, 2);control.setWidthTableColumn(tProdaVender, 60, 3);
        control.setWidthTableColumn(tProdaVender, 20, 1, 4, 5, 7, 8);
        
        tProdaVender.getColumnModel().getColumn(9).setPreferredWidth(5);
        
        FormatoTabla ft = new FormatoTabla(2);
        tProdaVender.setDefaultRenderer(Object.class, ft);
        
        control.hideTableColumn(tProdaVender, 0);chAgrupar.setSelected(true);buscarCliente();
        buscarProducto();llenarTipoComprobantes();txtFechaPago.setDate(new Date());
        txtMontoInicial.setVisible(false);lblFechaPago.setVisible(false);txtFechaPago.setVisible(false);
        lblMontoInicial.setVisible(false);jLabel2.setVisible(false);txtreferencia.setVisible(false);
        TipoVenta = 1;txtNumComprobantes.setEditable(false);txtFechaPago.setVisible(false);
        jComboBox1.setVisible(false);chkLibre.setVisible(false);txmontodiferente.setVisible(false);
        jLabel3.setVisible(false);jLabel4.setVisible(false);jLabel1.setVisible(false);jLabel8.setVisible(false);
        jLabel6.setVisible(false);lbProducto.setVisible(false);jCheckBox1.setVisible(false);txtPrecio.setVisible(false);
        txtCantidad.setVisible(false);txtDescuento.setVisible(false);txtValorVentaFinal.setVisible(false);Bcotizacion.setVisible(false);
        
        cbTipoComprobante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comprobante = cbTipoComprobante.getSelectedItem().toString();                
                if (cbTipoComprobante.getSelectedItem().equals("Venta Libre")) {
                    jLabel2.setVisible(true);txtreferencia.setVisible(true);
                } else {
                    jLabel2.setVisible(false);txtreferencia.setVisible(false);
                }
                generarComprobanteVenta();
            }
        });
        
        tClientes.setVisible(false);jScrollPane2.setVisible(false);txmontodiferente.setEditable(false);
        jCheckBox1.setSelected(true);jLabel2.setVisible(false);txtCantidad1.setVisible(false);
        info.setRucc("");info.setPass("5");controlCliente++; lbCliente.setText("CLIENTE");idencte="";
        control.Sql="SELECT * FROM CLIENTE where nomclie='CLIENTE'";
        info.setPass(control.DevolverRegistroDto(control.Sql, 1));lbmontovuelto.setText("0.00");
        cbTipoComprobante.setSelectedIndex(0);chAgrupar.setVisible(false);control.bandera = false;
    }
    public void CalMontoDiferente() {
        control.fila = 0;double mt = 0.0, pr = 0.0;int ct = 0;control.bandera = false;montoreal = 0.0;
        while (control.fila < tProdaVender.getRowCount()) {
           control.Sql = "SELECT * FROM producto WHERE idproducto='" + tProdaVender.getValueAt(control.fila,
           0).toString() + "'; ";control.Sql = "SELECT * FROM catalogoproducto WHERE codctlg='" + control.DevolverRegistroDto(
           control.Sql, 3) + "'; ";pr = Double.parseDouble(control.DevolverRegistroDto(control.Sql, 9));ct = Integer.parseInt(
           tProdaVender.getValueAt(control.fila, 5).toString());
            if ((Boolean) tProdaVender.getValueAt(control.fila, 9)) {
             montoreal = montoreal + (pr * ct);
            }
            control.fila++;
        }
        txmontodiferente.setText(Double.toString(montoreal));
    }
    public void CalcularVuelto() {
        if (Double.parseDouble(jTextField1.getText()) > 0) {
            if (Double.parseDouble(txtDescuentoTotal.getText()) >= 0 && Double.parseDouble(txtDescuentoTotal.getText()) < Double.parseDouble(lbTotalVenta.getText())) {
                if (Double.parseDouble(jTextField1.getText()) >= (Double.parseDouble(lbTotalVenta.getText()) - Double.parseDouble(txtDescuentoTotal.getText()))) {
                    double totalapagar = Double.parseDouble(lbTotalVenta.getText());
                    double montoentregado = Double.parseDouble(jTextField1.getText());
                    double descuento = Double.parseDouble(txtDescuentoTotal.getText());
                    totalapagar = (montoentregado - (totalapagar - descuento));
                    lbmontovuelto.setText("" + String.valueOf(forma.format(totalapagar)).replace(",", "."));
                } else {
                    jTextField1.setText("");
                    lbmontovuelto.setText("0.00");
                    JOptionPane.showMessageDialog(rootPane, "El Monto Entregado No Puede Ser Menor Al Monto A Pagar.\n"
                    + "De Lo Contrario Seleccione La Modalidad De Pago Al Crédito.");
                }
            } else{
                jTextField1.setText("");lbmontovuelto.setText("0.00");txtDescuentoTotal.setText("0.00");
                JOptionPane.showMessageDialog(rootPane, "El Descuento A Realizar No Puede Ser Una Cantidad Negativa Ni Debe "
               + "Superar El Monto A Pagar.\nDe Lo Contrario Seleccione La Modalidad De Pago Al Crédito."); 
            }
        } else {
            jTextField1.setText("");
            lbmontovuelto.setText("0.00");
            JOptionPane.showMessageDialog(rootPane, "El Monto Entregado Tiene Que Ser Mayor A 0 Soles.\nDe Lo Contrario Seleccione La Modalidad De Pago Al Crédito.");
        }
    }
    private void generarComprobanteVenta() {
     if (cbTipoComprobante.getSelectedIndex() >= 0) {
      control.Sql = "CALL ElComprobante('0','" + Controlador.sede + "','" + comprobante + "','Productos','0','1')";
      if (control.DevolverRegistroDto(control.Sql, 1).equals("Se debe iniciar este tipo de comprobante")) {
       JOptionPane.showMessageDialog(cbTipoComprobante, "No Hay Documentos De Este Tipo De Comprobante, Por "
       + "Favor Primero Incie La Numeración De Este Tipo De Comprobante.", "Mensaje", JOptionPane.WARNING_MESSAGE);
       InfoGeneral.pase = false;IniciandoComprobantesICECompured inicdoc = new IniciandoComprobantesICECompured();
       Menu.jDesktopPane1.add(inicdoc);inicdoc.toFront();inicdoc.setLocation(250, 250);inicdoc.setVisible(true);
     }
     else {
      numcomp = control.DevolverRegistroDto(control.Sql, 1);txtNumComprobantes.setText(numcomp);
      serie = control.DevolverRegistroDto(control.Sql, 2);lbSerie.setText(serie);
      idComprobanteGenerado = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 3));
     }
   }
   else {
    JOptionPane.showMessageDialog(this, "Indique El Tipo De Comprobante A Generar", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
   }
 }
    public void ReiniciandoDatosCreadito(){
      TipoVenta = 1;txtMontoInicial.setVisible(false);lblFechaPago.setVisible(false);
      txtFechaPago.setVisible(false);txtMontoInicial.setText("0.00");jComboBox1.setSelectedIndex(-1);
      jComboBox1.setVisible(false);lblMontoInicial.setVisible(false);
    }
    
    public void ImprimirVersionUltima(String idventa) {
        //***************************CAPTURAMOS LOS DATOS GENERALES**********************
        Map map = new HashMap();  //Creamos el objeto de los parametros.   
        control.Sql = "SELECT * FROM vta_datosimpresion WHERE idventa='" + idventa + "'";
        String cliente="",direc="",dia="",mes ="",anio ="";
        idencte=control.DevolverRegistroDto(control.Sql, 1);
        cliente = control.DevolverRegistroDto(control.Sql, 2);direc = control.DevolverRegistroDto(control.Sql, 3);
        dia = control.DevolverRegistroDto(control.Sql, 4);mes = control.DevolverRegistroDto(control.Sql, 5);
        anio = control.DevolverRegistroDto("SELECT right('" +control.DevolverRegistroDto(control.Sql, 6) + "', 1);", 1);
        control.Sql="select * from identificacion where idcliente='"+idencte+"'";
        idencte=control.DevolverRegistroDto(control.Sql, 3);
        
        //anio = control.DevolverRegistroDto(control.Sql, 6);
        control.Sql = "SELECT idventa, ROUND(Total,2), ROUND(SubTotal,2), ROUND(Igv,2) FROM vta_importes "
        + "WHERE idventa='" + idventa + "'";String monto = control.DevolverRegistroDto(control.Sql, 2);
        igv = control.DevolverRegistroDto(control.Sql, 4);
        subTotal = control.DevolverRegistroDto(control.Sql, 3);String monlt = control.numlt.Convertir(monto, true);
        if (dia.length() == 1) {
            dia = "0" + dia;
        }
     //***************************CAPTURAMOS LOS DATOS GENERALES**********************   

        //**********************CAPTURAR EL COMPROBANTE SELECCIONADO********************
        String MiComprobante = cbTipoComprobante.getSelectedItem().toString();
     //**********************CAPTURAR EL COMPROBANTE SELECCIONADO********************

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
                    txtNumComprobantes.setText(null);lbSerie.setText(null);
                    control.impresor.ImpresionBoletaProductos("idventa", idventa, "dia", dia, "mes", mes, "anio", anio,
                    "cliente", cliente, "direc", direc, "Monto", monto, "BoletaICE", "totalnum", new Numero_a_Letra().Convertir(monto, true));
                }

                if (MiComprobante.equalsIgnoreCase("Ticket Boleta")) {
                 String guiaRemision = "";map.put("idVenta", idventa);map.put("dia", dia);
                 map.put("mes", mes);map.put("anio", anio);map.put("cliente", cliente);
                 map.put("dir", direc);map.put("identi", idencte);map.put("guia", guiaRemision);
                 map.put("monletras", monlt);map.put("vlrvta", subTotal);map.put("igv", igv);
                 map.put("total", monto);control.imprimirComprobanteVenta(idventa, "ticket_boleta", map);
                }
                if (cbTipoComprobante.getSelectedItem().toString().equals("Ticket")) {
                 String guiaRemision = "";map.put("idVenta", idventa);map.put("dia", dia);
                 map.put("mes", mes);map.put("anio", anio);map.put("cliente", cliente);
                 map.put("dir", direc);map.put("identi", idencte);map.put("guia", guiaRemision);
                 map.put("monletras", monlt);map.put("vlrvta", subTotal);map.put("igv", igv);
                 map.put("total", monto);control.imprimirComprobanteVenta(idventa, "ticket", map);
               }

               int fac = 0, tope = 5;String guiaRemision = "";
               if (MiComprobante.equalsIgnoreCase("Factura")) {
                //*******************Captura la cantidad de filas de la factura**************************
                control.Sql = "SELECT * FROM config WHERE idconfig='maxFilasFactura'";
                tope = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 2));
                control.impresor.ImpresionFacturaProductos("idventa", idventa, "dia", dia, "mes", mes, "anio",
                anio, "cliente", cliente, "dir", direc, "identi", idencte, "guia", guiaRemision, "monletras",
                monlt, "vlrvta", subTotal, "igv", igv, "total", monto, "FacturaIce", "1", "100");
                
                //*******************Captura la cantidad de filas de la factura**************************
                
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
                if (MiComprobante.equalsIgnoreCase("Ticket Factura")) {
                 map.put("idVenta", idventa);map.put("dia", dia);map.put("mes", mes);
                 map.put("anio", anio);map.put("cliente", cliente);map.put("dir", direc);
                 map.put("identi", idencte);map.put("guia", guiaRemision);map.put("monletras", monlt);
                 map.put("vlrvta", subTotal);map.put("igv", igv);map.put("total", monto);
                 control.imprimirComprobanteVenta(idventa, "ticket_factura", map);
                 //******************SOLICITA IMPRESION DE GUIA DE REMISION*************************
                 if (JOptionPane.showConfirmDialog(null, "Desea Imprimir Una Guía De Remición?",
                  "Systems Message", JOptionPane.YES_NO_OPTION) == 0) {
                  FrmGenerarGuiaRemision generarGuiaRemision = new FrmGenerarGuiaRemision(null, true);
                  generarGuiaRemision.setLocationRelativeTo(this);generarGuiaRemision.setIdVenta(idventa);
                  generarGuiaRemision.setVisible(true);String idGuiaRemisionGenerado = generarGuiaRemision.getIdGuiaRemision();
                 }
                 //******************SOLICITA IMPRESION DE GUIA DE REMISION**************************
                }
            }
    }
    public void NuevoVender(){
     idencte = info.getRucc();Libre = 0;               
     //********************************OBTENERMOS EL MOTO REAL DE LA VENTA*************************
     if (Double.parseDouble(lbTotalVenta.getText()) < montoreal) {
      montoreal = Double.parseDouble(lbTotalVenta.getText());
     }
     //********************************OBTENERMOS EL MOTO REAL DE LA VENTA*************************
     
     //***********Inserta la venta y devuelve el identificador de la venta********************************************      
            control.Sql = "SELECT InsertaVentas('Contado','" + info.getPass() + "','"+ info.getUsuario() + "','" 
            + lbTotalVenta.getText() + "','" + montoreal + "','"+ tipodeventa + "','" + txtDescuentoTotal.getText() + "'," + "'" 
            + txtreferencia.getText()+ "','" + lbmontovuelto.getText() + "');";
            idVenta = control.DevolverRegistroDto(control.Sql, 1);elprecio = 0.0;montoreal = 0.0;
     //***********Inserta la venta y devuelve el identificador de la venta********************************************
     
     //******************inserta o genera un nuevo comprobante************************************************
            control.Sql = "SELECT InsertaComprobante('" + idComprobanteGenerado + "','" + idVenta + "')";
            control.DevolverRegistroDto(control.Sql, 1);
     //******************inserta o genera un nuevo comprobante************************************************
     
     //**********************************Grabar los detalle de la venta*****************************************
      int cpf=0;      
      while(tProdaVender.getRowCount()>0){
        //**********************************Capturamos los datos del detalle*************************************  
        control.bandera = (Boolean) tProdaVender.getValueAt(control.fila, 9);  
        precioGrabar = Double.parseDouble(model.getValueAt(control.fila, 4).toString());
        if (control.bandera) {
          cantidadVender = Integer.parseInt(model.getValueAt(control.fila, 5).toString());
          cantidadmostrar = Integer.parseInt(model.getValueAt(control.fila, 5).toString());
        } 
        else {
         cantidadVender = Integer.parseInt(model.getValueAt(control.fila, 5).toString());
         cantidadmostrar = 0;//Integer.parseInt(model.getValueAt(control.fila, 5).toString());    
         }
         mostrar = model.getValueAt(control.fila, 8).toString();
        //**********************************Capturamos los datos del detalle*************************************
         
        //****************************Obtiene el identificador del catalogo del producto a vender**********************
          idCatalogo = control.DevolverRegistroDto("SELECT catalogoproducto_codctlg FROM producto where idproducto='"+
           tProdaVender.getValueAt(control.fila, 0) + "'",1);
        //**************************Obtiene el identificador del catalogo del producto a vender************************ 
        
        //*********Determinar si el catalogo del prodcuto se encuentra el la tabla DetVentaFacturar para esta venta***********
        boolean bandera=control.Verificarconsulta("select * from detventafacturar where idventa='"+idVenta
        +"' and codctlg='"+idCatalogo+"'"); 
        //*********Determinar si el catalogo del prodcuto se encuentra el la tabla DetVentaFacturar para esta venta***********
        
        //***************************Insertar registro en la tabla DetVentaFacturar para esta venta*********************
        
        if(bandera){
         control.Sql="insert into detventafacturar(codctlg,cant,prec,idVenta,cantreal,mostrar,bandera) values('"+idCatalogo+"','"
        +cantidadVender+"','"+precioGrabar+"','"+idVenta+"','"+cantidadmostrar+"','1',"+control.bandera+"')";
         control.CrearRegistro(control.Sql);
        }
        else{
        } 
        
         //*****************************INSERTA DIRECTO EL DETALLE DE VENTA********************** 
                    control.Sql = "INSERT INTO detventafacturar(codctlg,cant,prec,idVenta,cantreal, mostrar, bandera) VALUES('"
                    + idCatalogo + "','" + cantidadVender + "','" + precioGrabar + "','" + idVenta + "','" + cantidadmostrar
                    + "','" + (mostrar == "Si" ? 1 : 0) + "','" + "bdr" + "'); ";control.CrearRegistro(control.Sql);
                    //*****************************INSERTA DIRECTO EL DETALLE DE VENTA**********************  
                //}
                //else //**************************ACTUALIZA EL DETALLE DE VENTA******************     
                //{
                  control.ejecutar(String.format("UPDATE detventafacturar SET cant=(cant + " + cantidadmostrar + ") WHERE "
                  + "idventa='%s' AND codctlg='%s'; ", idVenta, idCatalogo));
                //}
                //**************************ACTUALIZA EL DETALLE DE VENTA***********************************
        
        //***************************Insertar registro en la tabla DetVentaFacturar para esta venta*********************
        
      }
     //**********************************Grabar los detalle de la venta*****************************************       
            
      
    }
    private void vender() {
        if (cbTipoComprobante.getSelectedIndex() != -1 && tProdaVender.getRowCount() > 0
            && TipoVenta > 0 && lbCliente.getText().trim().length() > 0) {
            
            idencte = info.getRucc();Libre = 0;            
            //*****************************PREPARA DATOS DE LA VENTA******************              
            if (Double.parseDouble(lbTotalVenta.getText()) < montoreal) {
                montoreal = Double.parseDouble(lbTotalVenta.getText());
            }
            //*****************************PREPARA DATOS DE LA VENTA**********************************

            //***********Inserta la venta y devuelve el identificador de la venta*****      
            control.Sql = "SELECT InsertaVentas('Contado','" + info.getPass() + "','"+ info.getUsuario() + "','" 
            + lbTotalVenta.getText() + "','" + montoreal + "','"+ tipodeventa + "','" + txtDescuentoTotal.getText() + "'," + "'" 
            + txtreferencia.getText()+ "','" + lbmontovuelto.getText() + "');";
            idVenta = control.DevolverRegistroDto(control.Sql, 1);elprecio = 0.0;montoreal = 0.0;
            //***********Inserta la venta y devuelve el identificador de la venta**************************************            

            //******************inserta o genera un nuevo comprobante******************************************
            control.Sql = "SELECT InsertaComprobante('" + idComprobanteGenerado + "','" + idVenta + "')";
            control.DevolverRegistroDto(control.Sql, 1);
            //******************inserta o genera un nuevo comprobante******************************************      

            control.fila = 0;int bdr = 1;canfilas = model.getRowCount();
            while (control.fila < model.getRowCount()) {
                //****************CAPTURAMOS LOS DATOS DE LA TABLA****************************
                precioGrabar = Double.parseDouble(model.getValueAt(control.fila, 4).toString());
                control.bandera = (Boolean) tProdaVender.getValueAt(control.fila, 9);
                if (control.bandera) {
                    cantidadVender = Integer.parseInt(model.getValueAt(control.fila, 5).toString());
                    cantidadmostrar = Integer.parseInt(model.getValueAt(control.fila, 5).toString());
                } else {
                    cantidadVender = Integer.parseInt(model.getValueAt(control.fila, 5).toString());
                    cantidadmostrar = 0;//Integer.parseInt(model.getValueAt(control.fila, 5).toString());    
                }
                mostrar = model.getValueAt(control.fila, 8).toString();
                //****************CAPTURAMOS LOS DATOS DE LA TABLA*****************************

                //**************Obtiene el identificador del catalogo del producto a vender*******
                idCatalogo = control.DevolverRegistroDto("SELECT * FROM catalogoproducto c, unidad un,modelo m where "
                + "c.idmodelos=m.idmodelos and c.idunidad=un.idunidad " +
                 "AND CONCAT(nommod,' ',nomctlg)='" + tProdaVender.getValueAt(control.fila, 2) + "'; ", 1);
                //**********Obtiene el identificador del catalogo del producto a vender************

                //*************Obtiene el identificador del catalogo del producto a vender*********************************
                int ver = Integer.parseInt(control.DevolverRegistroDto(String.format("SELECT COUNT(*) FROM  "
               + "detventafacturar WHERE codctlg='%s' AND idventa='%s'; ", idCatalogo, idVenta), 1));
                //**********Obtiene el identificador del catalogo del producto a vender******************        
                if (ver == 0) {
                    //*****************************INSERTA DIRECTO EL DETALLE DE VENTA********************** 
                    control.Sql = "INSERT INTO detventafacturar(codctlg,cant,prec,idVenta,cantreal, mostrar, bandera) VALUES('"
                    + idCatalogo + "','" + cantidadVender + "','" + precioGrabar + "','" + idVenta + "','" + cantidadmostrar
                    + "','" + (mostrar == "Si" ? 1 : 0) + "','" + bdr + "'); ";control.CrearRegistro(control.Sql);
                    //*****************************INSERTA DIRECTO EL DETALLE DE VENTA**********************  
                }
                else //**************************ACTUALIZA EL DETALLE DE VENTA******************     
                {
                  control.ejecutar(String.format("UPDATE detventafacturar SET cant=(cant + " + cantidadmostrar + ") WHERE "
                  + "idventa='%s' AND codctlg='%s'; ", idVenta, idCatalogo));
                }
                //**************************ACTUALIZA EL DETALLE DE VENTA***********************************
                
                //*************************Fin Registrar en la tabla para facturar**************************        
                control.bandera = (Boolean) tProdaVender.getValueAt(control.fila, 9);
                if (cantidadmostrar > 1) {
                    for (int f = 1; f <= cantidadmostrar; f++) {
                        control.Sql = "SELECT * FROM vta_porvender where estado='Por Vender' and "
                                    + "catalogo='" + tProdaVender.getValueAt(control.fila, 2) + "' and usuario='"
                                    + info.getUsuario() + "' and sede='" + Controlador.sede + "' order by ID limit 1";
                        idProductoGrabar = control.DevolverRegistroDto(control.Sql, 1);

                        if (control.bandera == false) {                            
                         //JOptionPane.showMessageDialog(null, "Lo va a poner como disponible");
                         control.Sql = "call venta_productos_venta ('" + precioGrabar + "','" + idVenta+ "','" + idProductoGrabar + "','1');";                         
                        } 
                        else {
                         //JOptionPane.showMessageDialog(null, "Confirma la venta");   
                          control.Sql = "call venta_productos_venta ('" + precioGrabar + "','" + idVenta+ "','" + idProductoGrabar + "','0');";
                        }                        
                        control.CrearRegistro(control.Sql);
                        
                        /**********************************Elimina el producto por vender*****************************/
                        control.Sql = "Delete from por_vender where idusuario='" + control.DevolverRegistroDto("select "
                        + "* from usuario where nomusr='" + info.getUsuario() + "'", 1) + "' and idproducto='"
                        + idProductoGrabar + "' and sede='" + Controlador.sede + "'";control.EliminarRegistro(control.Sql);
                        /**********************************Elimina el producto por vender*****************************/
                        
                    }
                } 
                else {
                    if (cantidadmostrar == 1) {
                        idProductoGrabar = model.getValueAt(control.fila, 0).toString();
                        if (control.bandera == false) {
                            //JOptionPane.showMessageDialog(null, "Lo va a poner como disponible");
                            control.Sql = "call venta_productos_venta ('" + precioGrabar + "','" + idVenta
                                        + "','" + idProductoGrabar + "','1');";
                        } else {
                            //JOptionPane.showMessageDialog(null, "Confirma la venta");
                            control.Sql = "call venta_productos_venta ('" + precioGrabar + "','" + idVenta
                                        + "','" + idProductoGrabar + "','0');";          
                        }
                        control.CrearRegistro(control.Sql);
                        ListaProductoPorVendido(idProductoGrabar, "Vendido");
                    }
                }
                //}       Bloqueo Provicional 
                suma = suma + precioGrabar;control.fila++;
            }
            
            control.ejecutar(String.format("INSERT INTO pagos VALUES(NULL,curdate(), curtime(),'%s','Venta','%s','%s','%s','%s','%s','%s');",
             idVenta, info.getUsuario(), (Double.parseDouble(lbTotalVenta.getText())),
             lbCliente.getText(), cbTipoComprobante.getSelectedItem().toString(), lbSerie.getText()
             + "-" + txtNumComprobantes.getText(), txtDescuentoTotal.getText()));
            
            control.LimTablaeditable(model);control.LimTabla(modeloClientes);
            control.LimTabla(modeloProductosAlmacen);buscarProducto();buscarCliente();
            txtNumComprobantes.setText(" ");txtBucarProducto.setText("");txtBucarProducto.grabFocus();
            rbContado.setSelected(true);control.fila = 0;controlCliente = 0;ppp = 0;ImprimirVersionUltima(idVenta);
        }
    }          
    private void venderfactura() {
      montoreal = 0.0;double totalitem = 0, mt = 0.0, preciop = 0.0;
      int ct = 0, cantpro = 11, cantfac = 1, contador = 0, cantven = 0;        
      for(int i = 0; i < tProdaVender.getRowCount(); i++){
        if(i > cantpro - 1){
          cantfac += 1;cantpro += 11;
        }
      }
      double[] tot = new double[cantfac];double[] porcentaje = new double[cantfac];
      double[] descuento = new double[cantfac];double[] vuelto = new double[cantfac];
      int[] idf = new int[cantfac];String[] serieg = new String[cantfac];
      String[] numerog = new String[cantfac];String[] idv = new String[cantfac];cantpro = 11;
      JOptionPane.showMessageDialog(rootPane, "Se Necesitarán Un Total De " + cantfac + 
      " Factura(s) Para Esta Venta.");
        
      for (int j = 0; j < tProdaVender.getRowCount(); j++) {
        if (j > cantpro - 1) {
         j = j - 1;control.CrearRegistro("UPDATE comprobantes SET esta = 'Emitido' WHERE idComprobantes = '" + 
         idComprobanteGenerado + "'; ");generarComprobanteVenta();cantpro += cantpro;totalitem = 0;contador += 1;
        }
            else {
                //Variable para obtener el total del importa de la venta por items
             totalitem += Double.parseDouble(model.getValueAt(j, 7).toString());
            }
            tot[contador] = totalitem;idf[contador] = idComprobanteGenerado;
            serieg[contador] = serie;numerog[contador] = numcomp;
        }
        //Calculamos el porcentaje de cada monto
        for (int idx = 0; idx < cantfac; idx++) {
          control.Sql = "SELECT ROUND((('" + tot[idx] + "' * 100) / '" + Double.parseDouble(lbTotalVenta.getText()) + "'), 1); ";
          porcentaje[idx] = Double.parseDouble(control.DevolverRegistroDto(control.Sql, 1));
        }
        //Calculamos el descuento de cada monto a razon de su porcentaje
        for (int idx = 0; idx < cantfac; idx++) {
         control.Sql = "SELECT ROUND(ROUND(('" + txtDescuentoTotal.getText() + "' * '" + porcentaje[idx] 
         + "') / 100, 2), 1); ";descuento[idx] = Double.parseDouble(control.DevolverRegistroDto(control.Sql, 1));
        }
        //Calculamos el vuelto de cada monto a razon de su porcentaje
        for (int idx = 0; idx < cantfac; idx++) {
            control.Sql = "SELECT ROUND(ROUND(('" + lbmontovuelto.getText() + "' * '" + porcentaje[idx] + "') / 100, 2), 1); ";
            vuelto[idx] = Double.parseDouble(control.DevolverRegistroDto(control.Sql, 1));
        }
        for (int idx = 0; idx < cantfac; idx++) {
            JOptionPane.showMessageDialog(rootPane, "ANALISIS DE RESULTADOS\n" +
                    "MONTO " + (idx + 1) + ": " + tot[idx] + ".\n" +
                    "DESCUENTO " + (idx + 1) + ": " + descuento[idx] + ".\n" +
                    "VUELTO " + (idx + 1) + ": " + vuelto[idx] + ".\n");
        }
        
        for (int k = 0; k < cantfac; k++) {
            if (cbTipoComprobante.getSelectedIndex() != -1 && tProdaVender.getRowCount() > 0 && TipoVenta > 0 && lbCliente.getText().trim().length() > 0) {
                idencte = info.getRucc();
                Libre = 0;
                //*****************************PREPARA DATOS DE LA VENTA******************              
                if (Double.parseDouble(lbTotalVenta.getText()) < montoreal) {
                    montoreal = Double.parseDouble(lbTotalVenta.getText());
                }
                //*****************************PREPARA DATOS DE LA VENTA******************
                
                //***********Inserta la venta y devuelve el identificador de la venta*****
                //Insertamos el monto total real de la venta como 0
                /*if(!VerificaFicticia())
                  tipodeventa="F";
                */
                control.Sql = "SELECT InsertaVentas('Contado','" + info.getPass() + "','" + info.getUsuario() + "','" 
                + tot[k] + "','" + 0 + "','" + tipodeventa + "','" + descuento[k] + "','" + txtreferencia.getText() + "','" + 
                vuelto[k] + "'); ";
                idv[k] = control.DevolverRegistroDto(control.Sql, 1);
                //***********Inserta la venta y devuelve el identificador de la venta************************
                //******************inserta o genera un nuevo comprobante*************************//
                control.Sql = "SELECT InsertaComprobante('" + idf[k] + "','" + idv[k] + "')";
                control.DevolverRegistroDto(control.Sql, 1);
                //******************inserta o genera un nuevo comprobante*************************//
            }
        }
        
        int cf = 11, n = 0;
        for (int l = 0; l < idv.length; l++) {
            for (int m = n; m < tProdaVender.getRowCount(); m++) {
                if (m > cf - 1) {
                    n = m;
                    m = tProdaVender.getRowCount();
                    cf += 11;
                } else {
                    int bdr = 1;
                    canfilas = 11;
                    //****************CAPTURAMOS LOS DATOS DE LA TABLA****************************//
                    precioGrabar = Double.parseDouble(model.getValueAt(m, 4).toString());
                    control.bandera = (Boolean) tProdaVender.getValueAt(m, 9);
                    if (control.bandera) {
                        cantidadVender = Integer.parseInt(model.getValueAt(m, 5).toString());
                        cantidadmostrar = Integer.parseInt(model.getValueAt(m, 5).toString());
                    } else {
                        cantidadVender = Integer.parseInt(model.getValueAt(m, 5).toString());
                        cantidadmostrar = 0;   
                    }
                    mostrar = model.getValueAt(m, 8).toString();
                    //****************CAPTURAMOS LOS DATOS DE LA TABLA*****************************//

                    //**************Obtiene el identificador del catalogo del producto a vender*******//
                    idCatalogo = control.DevolverRegistroDto("SELECT * FROM catalogoproducto c, unidad un,modelo m where c.idmodelos=m.idmodelos and c.idunidad=un.idunidad AND CONCAT(nommod,' ',nomctlg)='" + tProdaVender.getValueAt(m, 2) + "'; ", 1);
                    //**********Obtiene el identificador del catalogo del producto a vender***********//

                    //**********Obtiene el identificador del catalogo del producto a vender******************//
                    int ver = Integer.parseInt(control.DevolverRegistroDto(String.format("SELECT COUNT(*) FROM  detventafacturar WHERE codctlg='%s' AND idventa='%s'; ", idCatalogo, idv[l]), 1));
                    //**********Obtiene el identificador del catalogo del producto a vender******************//        
                    if (ver == 0) {
                        //*****************************INSERTA DIRECTO EL DETALLE DE VENTA*************  
                        control.Sql = "INSERT INTO detventafacturar(codctlg,cant,prec,idVenta,cantreal, mostrar, bandera) VALUES('" + idCatalogo + "','" + cantidadVender + "','" + precioGrabar + "','" + idv[l] + "','" + cantidadmostrar + "','" + (mostrar == "Si" ? 1 : 0) + "','" + bdr + "'); ";
                        control.CrearRegistro(control.Sql);
                        //*****************************INSERTA DIRECTO EL DETALLE DE VENTA*************  
                    } else {
                        //**************************ACTUALIZA EL DETALLE DE VENTA******************//
                        control.ejecutar(String.format("UPDATE detventafacturar SET cant=(cant + " + cantidadmostrar + ") WHERE idventa='%s' AND codctlg='%s'; ", idv[l], idCatalogo));
                    }   //**************************ACTUALIZA EL DETALLE DE VENTA******************//
                    control.bandera = (Boolean) tProdaVender.getValueAt(m, 9);
                    if (cantidadmostrar > 1) {
                        for (int f = 1; f <= cantidadmostrar; f++) {
                            //if(!chkLibre.isSelected()){  //chequear
                            control.Sql = "SELECT * FROM vta_porvender where estado='Por Vender' and catalogo='" + tProdaVender.getValueAt(m, 2) + "' and usuario='" + info.getUsuario() + "' and sede='" + Controlador.sede + "' order by ID limit 1";
                            idProductoGrabar = control.DevolverRegistroDto(control.Sql, 1);
                            if (control.bandera == false) {
                                control.Sql = "call venta_productos_venta ('" + precioGrabar + "','" + idv[l] + "','" + idProductoGrabar + "','1');";
                            } else {
                                control.Sql = "call venta_productos_venta ('" + precioGrabar + "','" + idv[l] + "','" + idProductoGrabar + "','0');";
                            }
                            control.CrearRegistro(control.Sql);
                            control.Sql = "Delete from por_vender where idusuario='" + control.DevolverRegistroDto("select * from usuario where nomusr='" + info.getUsuario() + "'", 1) + "' and idproducto='" + idProductoGrabar + "' and sede='" + Controlador.sede + "'";
                            control.EliminarRegistro(control.Sql);
                        }
                    } else {
                        if (cantidadmostrar == 1) {
                            idProductoGrabar = model.getValueAt(m, 0).toString();
                            if (control.bandera == false) {
                                control.Sql = "call venta_productos_venta ('" + precioGrabar + "','" + idv[l] + "','" + idProductoGrabar + "','1');";
                            } else {
                                control.Sql = "call venta_productos_venta ('" + precioGrabar + "','" + idv[l] + "','" + idProductoGrabar + "','0');";
                            }
                            control.CrearRegistro(control.Sql);
                            ListaProductoPorVendido(idProductoGrabar, "Vendido");
                        }
                    }
                    control.Sql = "SELECT * FROM producto WHERE idproducto='" + tProdaVender.getValueAt(m, 0).toString() + "'; ";
                    control.Sql = "SELECT * FROM catalogoproducto WHERE codctlg='" + control.DevolverRegistroDto(control.Sql, 3) + "'; ";
                    preciop = Double.parseDouble(control.DevolverRegistroDto(control.Sql, 9));
                    cantven = Integer.parseInt(tProdaVender.getValueAt(m, 5).toString());
                    if ((Boolean) tProdaVender.getValueAt(m, 9)) {
                        montoreal = montoreal + (preciop * cantven);
                    }
                    suma = suma + precioGrabar;
                }
            }
            control.CrearRegistro("UPDATE venta SET montreal = '" + montoreal + "' WHERE idVenta = '" + idv[l] + "'; ");
            montoreal = 0.0;
            control.ejecutar(String.format("INSERT INTO pagos VALUES(NULL,curdate(), curtime(),'%s','Venta','%s','%s','%s',"
            + "'%s','%s','%s');", idv[l], info.getUsuario(), tot[l], lbCliente.getText(), cbTipoComprobante.getSelectedItem().toString(),
            serieg[l] + "-" + numerog[l], descuento[l]));ImprimirVersionUltima(idv[l]);
        }
        elprecio = 0.0;montoreal = 0.0;control.LimTablaeditable(model);control.LimTabla(modeloClientes);
        control.LimTabla(modeloProductosAlmacen);buscarProducto();buscarCliente();
        txtNumComprobantes.setText(" ");txtBucarProducto.setText("");txtBucarProducto.grabFocus();
        rbContado.setSelected(true);control.fila = 0;controlCliente = 0;ppp = 0;
    }
    private void Actulizar_Anterior_Producto() {
        control.Sql = "select idusuario from usuario where nomusr='" + info.getUsuario() + "'";
        String us = control.DevolverRegistroDto(control.Sql, 1);
        try {
            control.Base.st = control.Base.conec.createStatement();
            control.Base.rt = control.Base.st.executeQuery("select * from por_vender where idusuario='" + us + "';");
            while (control.Base.rt.next()) {
                control.Sql = "update producto set estdo='Disponible' where idproducto='" + control.Base.rt.getString(1) + "';";
                control.EditarRegistro(control.Sql);
            }
            control.Sql = "delete from por_vender where idusuario='" + us + "';";
            control.EliminarRegistro(control.Sql);
            control.Base.st = control.Base.conec.createStatement();
            control.Base.rt = control.Base.st.executeQuery("select idproducto from productoensamblaje where id=0;");
            while (control.Base.rt.next()) {
                control.Sql = "update producto set estdo='Disponible' where idproducto='" + control.Base.rt.getString(1) + "';";
                control.EditarRegistro(control.Sql);
            }
            control.Sql = "delete from productoensamblaje where id=0;";
            control.EliminarRegistro(control.Sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void llenarTipoComprobantes() {
      control.Sql = "SELECT * FROM tipocomprobante where idSede='"
      + control.ObtenerDato("sede", "nomse", Controlador.sede, 1) + "' and facde='Productos' AND tipcompr<> 'Guia de Remision'"
       + " and tipcompr<> 'Recibo' and tipcompr<> 'Nota de Venta';";
      control.LlenarCombo(cbTipoComprobante, control.Sql, 2);
    }
    public void buscarCliente() {
      control.Sql = "SELECT idcliente, nomclie,dir, numident FROM vta_cliente v where "
      + "nomclie like'" + txtBucarCliente.getText() + "%' or numident like'"
      + txtBucarCliente.getText() + "%'";control.LlenarJTabla(modeloClientes, control.Sql, 4);
    }
    private void buscarProducto() {
      control.Sql = "SELECT " + " (SELECT p.`idProducto` FROM producto p WHERE "
      + "p.`Catalogoproducto_codctlg` " + "= c.`codctlg` AND p.`estdo`='Disponible' limit 1),"
      + " (SELECT p.`codbrr` FROM producto p WHERE p.`Catalogoproducto_codctlg` = "
      + "c.`codctlg` AND p.`estdo`='Disponible' limit 1), concat(mo.`nommod`,' ',c.`nomctlg`)AS"
      //+ " `nomctlg`, m.`nommrc`, u.`nomuni`,c.precsg,c.`prexmayor`, (SELECT count("
      + " `nomctlg`, m.`nommrc`, u.`nomuni`,c.precsg,c.`prexmenor`, (SELECT count("
      + "p.`Catalogoproducto_codctlg`) "
      + "FROM producto p WHERE p.`Catalogoproducto_codctlg` = c.`codctlg` AND p.`estdo`="
      + "'Disponible' limit 1) FROM catalogoproducto c, marca m, modelo mo, unidad u "
      + " WHERE (c.`idMarca` = m.`idMarca` AND c.`idModelos` = mo.`idModelos` AND c.`idUnidad`"
      + " = u.`idUnidad` and (SELECT p.`idSede` FROM producto p WHERE p.`Catalogoproducto_codctlg` "
      + "= c.`codctlg` AND p.`estdo`='Disponible' limit 1)='" + InfoGeneral.idSede + "') and(concat(`mo`.`nommod`"
      + ",' ',`c`.`nomctlg`) like '%" + txtBucarProducto.getText() + "%' or (SELECT p.`codbrr` FROM producto "
      + "p WHERE p.`Catalogoproducto_codctlg` = c.`codctlg` AND p.`estdo`='Disponible' limit 1) "
      + " like '%" + txtBucarProducto.getText() + "%' or `m`.`nommrc` like '%" + txtBucarProducto.getText()
      + "%' or nomctlg like'%" + txtBucarProducto.getText() + "%') AND c.`codctlg` in (SELECT p.`Catalogoproducto_codctlg` "
      + "FROM producto p WHERE p.`estdo`='Disponible' )";
      
      control.LlenarJTabla(modeloProductosAlmacen, control.Sql, 8);lblStock.setText("");txtBucarProducto.grabFocus();
    }
    private void Cogerelproducto() {
        if (jCheckBox1.isSelected()) {
            if (tProductos.getRowCount() == 1) {
                tProductos.selectAll();
                llenarenuna();
            }
        } else {
            mostrarStockProducto();
            int cntd = 0;
            if (txtBucarProducto.getText().trim().length() > 0) {
                cntd = Integer.parseInt(txtCantidad.getText());
                if (cntd == 1) {
                    nombreProducto = txtBucarProducto.toString();
                    control.fila = VolverID(txtBucarProducto.getText());
                    idProducto = tProductos.getValueAt(control.fila, 0).toString();
                    lbProducto.setText(tProductos.getValueAt(control.fila, 1).toString());
                    txtPrecio.setText(tProductos.getValueAt(control.fila, 5).toString());
                    txtValorVentaFinal.setText(tProductos.getValueAt(control.fila, 5).toString());
                    txtDescuento.grabFocus();
                }
            }
        }
    }
    private int VolverID(String cdbr) {
        int p = -1;
        for (int f = 0; f < tProductos.getRowCount(); f++) {
            if (tProductos.getValueAt(f, 4).toString().equals(cdbr)) {
                p = f;
                break;
            }
        }
        return p;
    }
    private void SelecionarCliente() {
        lbCliente.setText(modeloClientes.getValueAt(tClientes.getSelectedRow(), 1).toString());
        info.setRucc(tClientes.getValueAt(tClientes.getSelectedRow(), 3).toString());
        info.setPass(modeloClientes.getValueAt(tClientes.getSelectedRow(), 0).toString());
        controlCliente++;txtBucarProducto.grabFocus();
    }
    private boolean VerificaEstado(String cod) {
        boolean a = false;
        if (control.Verificarconsulta("select * from producto where idproducto='" + cod + "' and estdo='Disponible';")) {
            a = true;
        }
        return a;
    }
    private void mostrarStockProducto() {
        if (tProductos.getSelectedRow() >= 0) {
            stockProducto = tProductos.getValueAt(tProductos.getSelectedRow(), 7).toString();
        }
    }
    private void seleccionarProducto() {
        if (tProductos.getSelectedRowCount() >= 0) {
            mostrarStockProducto();
            idProducto = modeloProductosAlmacen.getValueAt(tProductos.getSelectedRow(), 0).toString();
            if (control.Verificarconsulta("SELECT * FROM producto WHERE idproducto='" + idProducto + "' AND estdo='Disponible'; ")) {
                control.Sql = "SELECT COUNT(*), cantidad, idproducto FROM productocantidad WHERE idproducto='" + idProducto + "'; ";
                int cantidad = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
                if (cantidad == 1) {
                    control.Sql = "SELECT cantidad,idproducto FROM productocantidad WHERE idproducto='" + idProducto + "'; ";
                    int cnt = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
                    lbCantidadMax.setText("La Cantidad Máxima A Vender Es De " + cnt);
                    controlad = cnt;
                }
                lbProducto.setText(modeloProductosAlmacen.getValueAt(tProductos.getSelectedRow(), 2).toString());
                elprecio = Double.parseDouble(modeloProductosAlmacen.getValueAt(tProductos.getSelectedRow(), 5).toString());
                txtPrecio.setText(modeloProductosAlmacen.getValueAt(tProductos.getSelectedRow(), 5).toString());
                txtValorVentaFinal.setText(modeloProductosAlmacen.getValueAt(tProductos.getSelectedRow(), 5).toString());
                marcaProducto = modeloProductosAlmacen.getValueAt(tProductos.getSelectedRow(), 3).toString();
                txtCantidad.grabFocus();txtCantidad.selectAll();txtBucarProducto.setText("");buscarProducto();
            } else {
                JOptionPane.showMessageDialog(null, "Producto No Disponible, Ya Fue Seleccionado En Otra Terminal !!!", "Mensaje", JOptionPane.WARNING_MESSAGE);
                txtBucarProducto.setText("");
                buscarProducto();
            }
        }
    }
    private void limpiar() {
     txtCantidad.setText("1");txtCantidad1.setText("1");txtDescuento.setText("0.00");
     txtValorVentaFinal.setText("0.00");txtPrecio.setText("0.00");lbProducto.setText(" ");
     txtBucarProducto.grabFocus();lbCantidadMax.setText("");jCheckBox1.setSelected(true);
    }
    private boolean verificarNumero() {
        boolean sss = false;
        if (txtNumComprobantes.getText().trim().length() > 0) {
            control.Sql = "call  GeneraComprobante('" + Controlador.sede + "','"
                        + cbTipoComprobante.getSelectedItem().toString() + "','Productos');";
            String codCom = control.DevolverRegistroDto(control.Sql, 1);
            control.Sql = "select * from tipocomprobante where tipcompr='" + cbTipoComprobante.getSelectedItem().toString() + "';";
            control.Sql = "select count(*) from comprobantes where nume='" + txtNumComprobantes.getText()
                        + "' and idtipocomprobante='" + control.DevolverRegistroDto(control.Sql, 1) + "' and idcomprobantes<>'"
                        + codCom + "';";
            String dds = control.DevolverRegistroDto(control.Sql, 1);
            control.Sql = "SELECT candig FROM tipocomprobante where tipcompr='" + cbTipoComprobante.getSelectedItem().toString() + "';";
            int cantid = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
            if (dds.compareTo("0") == 0) {
                if (txtNumComprobantes.getText().trim().length() == cantid) {
                    sss = true;
                }
            }
        }
        return sss;
    }
    private void verificarTipoPago() {
        if ((!rbContado.isSelected()) && (!rbCredito.isSelected())) {
         JOptionPane.showMessageDialog(this, "Tiene Que Seleccionar La Modalidad De Venta.", "Mensaje", 
         JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (rbContado.isSelected()) {
         if (cbTipoComprobante.getSelectedIndex() > -1 && lbSerie.getText().trim().length() > 0) {
           if(cbTipoComprobante.getSelectedItem().toString().equals("Factura")){
                    venderfactura();
                } else {
                    vender();
                }
             
            } else {
                JOptionPane.showMessageDialog(this, "Selecione Un Tipo De Comprobante Y Genere Uno!!!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
        } 
        else {
         if (lbCliente.getText().equals("CONSUMO")) {
                JOptionPane.showMessageDialog(null, "Venta Para Consumo No Puede Ser Al Crédito.");
                return;
            }
            if (rbCredito.isSelected()) {                
                if (cbTipoComprobante.getSelectedIndex() > -1 && lbSerie.getText().trim().length() > 0) {
                    tipodeventa="N";
                    if (tipodeventa.equalsIgnoreCase("N")) {
                        //if(!VerificaFicticia())
                         VentaCredito();
                        //else
                        // JOptionPane.showMessageDialog(null, "Una venta Ficticia\n no se Puede realizar al Crédito");   
                    } else {
                        JOptionPane.showMessageDialog(null, "Solo Se Pueden Hacer Ventas Normales Al Crédito.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Selecione Un Tipo De Comprobante Y Genere Uno!!!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione El Tipo De Venta Que Va A Realizar!!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    private void VentaCredito() {
     if(comprobante.equalsIgnoreCase("Venta Libre")){
      JOptionPane.showMessageDialog(null, "La venta al credito no puede ser libre \n Cambiar de Tipo de Comprobante");
      //rbCredito.setSelected(true);
      return;   
     }   
     if(comprobante.equalsIgnoreCase("Factura")){
       if(tProdaVender.getRowCount()>11){
        JOptionPane.showMessageDialog(null, "La venta no se puede realizar\n Para factura Fisica solo es 11 Items \n Elimine los restantes");
          return;
       }   
      }   
      if (tProdaVender.getRowCount() > 0 && TipoVenta > 0) {  
       control.Sql = "SELECT InsertaVentas('Credito','" + info.getPass() + "','" + info.getUsuario()
       + "','" + lbTotalVenta.getText() + "','" + montoreal + "','" + tipodeventa + "','" + txtDescuentoTotal.getText() + "',"
       + "'" + txtreferencia.getText() + "','" + lbmontovuelto.getText() + "')";elprecio = 0.0;
       montoreal = 0.0;idVenta = control.DevolverRegistroDto(control.Sql, 1);
       double total = Double.parseDouble(lbTotalVenta.getText());
       double montini = Double.parseDouble(txtMontoInicial.getText());
       
       
      
      control.Sql = "SELECT InsertaComprobante('" + idComprobanteGenerado + "','" + idVenta + "')";
      control.DevolverRegistroDto(control.Sql, 1);control.fila = 0;
      
      while (control.fila < tProdaVender.getRowCount()) {          
         precioGrabar = Double.parseDouble(model.getValueAt(control.fila, 4).toString());
         cantidadVender = Integer.parseInt(model.getValueAt(control.fila, 5).toString());
         cantidadmostrar = Integer.parseInt(model.getValueAt(control.fila, 5).toString());
         mostrar = model.getValueAt(control.fila, 8).toString();
         
         //************************************Registrar en la tabla para facturar***************************
         idCatalogo = control.DevolverRegistroDto("select * from catalogoproducto  c, modelo m where c.idmodelos=m.idmodelos "
         + "and  concat(nommod,' ',nomctlg)='" + tProdaVender.getValueAt(control.fila, 2).toString() + "'", 1);         
         
         control.Sql = "insert into detventafacturar(codctlg,cant,prec,idVenta,cantreal,mostrar,bandera) values('"
         + idCatalogo + "','" + cantidadVender + "','" + precioGrabar + "','" + idVenta + "','" + cantidadmostrar
         + "','" + (mostrar == "Si" ? 1 : 0) + "','1')";control.ejecutar(control.Sql);        
         
         for(int k=1;k<=cantidadVender;k++){          
          /*****************************Obtener el ID del producto a Vender*************************/   
          control.Sql = "SELECT * FROM vta_porvender where estado='Por Vender' and "
          + "catalogo='" + tProdaVender.getValueAt(control.fila, 2) + "' and usuario='"
          + info.getUsuario() + "' and sede='" + Controlador.sede + "' order by ID limit 1";                    
          idProductoGrabar = control.DevolverRegistroDto(control.Sql, 1);   
          /*****************************Obtener el ID del producto a Vender*************************/   
          
          
          control.Sql="insert into venta_producto(prc,Venta_idVenta,Producto_idProducto,situ) values('"+precioGrabar+"','"
          +idVenta+"','"+idProductoGrabar+"','N')";control.CrearRegistro( control.Sql);          
          
          control.Sql="update producto set estdo='Vendido' where idproducto='"+idProductoGrabar+"' and estdo='Por vender'";
          control.EditarRegistro(control.Sql);
          
          control.Sql="delete from por_vender where idproducto='"+idProductoGrabar+"'";
          control.EliminarRegistro(control.Sql);
          
         }
         
         control.fila++;ListaProductoPorVendido(idProductoGrabar,"Vendido");
       }
       control.LimTablaeditable(model);control.LimTabla(modeloClientes);
       control.LimTabla(modeloProductosAlmacen);buscarProducto();buscarCliente();
       txtBucarProducto.setText("");txtBucarProducto.grabFocus();control.fila = 0;
       controlCliente = 0;ppp = 0;
       //JOptionPane.showMessageDialog(this,"La Venta Es "+idVenta);
       ReiniciandoDatosCreadito();ImprimirVersionUltima(idVenta);
       //imprimirComprobante();
        }
    }
    private void VentaCreditonew() {
      if (tProdaVender.getRowCount() > 0 && TipoVenta > 0) {
       int cf=0;   
       //JOptionPane.showMessageDialog(null, "Division es "+(27/11)+" Residuo es "+(27%11));
       /************************DETERMINAR LA CANTIDAD DE FACTURAS****************************/
       if (tProdaVender.getRowCount() > 11){
        cf=tProdaVender.getRowCount()/11;
        if((tProdaVender.getRowCount()%11)>0){
         cf++;    
        }
       }
       else{
         cf=1;  
       }
       JOptionPane.showMessageDialog(null,"Necesitas "+cf+" Facturas");
       /************************DETERMINAR LA CANTIDAD DE FACTURAS****************************/
       
       control.Sql = "SELECT InsertaVentas('Credito','" + info.getPass() + "','" + info.getUsuario()
       + "','" + lbTotalVenta.getText() + "','" + montoreal + "','" + tipodeventa + "','" + txtDescuentoTotal.getText() + "',"
       + "'" + txtreferencia.getText() + "','" + lbmontovuelto.getText() + "')";elprecio = 0.0;
       
       montoreal = 0.0;idVenta = control.DevolverRegistroDto(control.Sql, 1);
       double total = Double.parseDouble(lbTotalVenta.getText());
       double montini = Double.parseDouble(txtMontoInicial.getText());
       control.Sql = "insert into deuda(montdeu,pgoinici,fecdeud,idVenta) values('" + (total - montini) + "','"
       + txtMontoInicial.getText() + "','" + control.DevolverRegistroDto("select adddate(curdate(),interval " + jComboBox1.getSelectedItem().toString() + " day)", 1) + "','" + idVenta + "');";
       control.CrearRegistro(control.Sql);
       control.Sql = "select iddeuda from deuda where idventa='" + idVenta + "' order by iddeuda desc limit 1;";
       String de = control.DevolverRegistroDto(control.Sql, 1);
       control.Sql = "insert into pagoxdeuda (fecpgxdeu, montpag, idDeuda) values(curdate(),'" + montini 
      + "','" + de + "');";control.CrearRegistro(control.Sql);
      control.Sql = "SELECT InsertaComprobante('" + idComprobanteGenerado + "','" + idVenta + "')";
      control.DevolverRegistroDto(control.Sql, 1);control.fila = 0;
      
      while (control.fila < tProdaVender.getRowCount()) {
         precioGrabar = Double.parseDouble(model.getValueAt(control.fila, 4).toString());
         cantidadVender = Integer.parseInt(model.getValueAt(control.fila, 5).toString());
         cantidadmostrar = Integer.parseInt(model.getValueAt(control.fila, 5).toString());
         mostrar = model.getValueAt(control.fila, 8).toString();
         //************************************Registrar en la tabla para facturar***************************
         idCatalogo = control.DevolverRegistroDto("select * from catalogoproducto  c, modelo m where c.idmodelos=m.idmodelos "
         + "and  concat(nommod,' ',nomctlg)='" + tProdaVender.getValueAt(control.fila, 2).toString() + "'", 1);
         control.Sql = "insert into detventafacturar(codctlg,cant,prec,idVenta,cantreal,mostrar,bandera) values('"
         + idCatalogo + "','" + cantidadVender + "','" + precioGrabar + "','" + idVenta + "','" + cantidadmostrar
         + "','" + (mostrar == "Si" ? 1 : 0) + "','1')";control.ejecutar(control.Sql);
         precioGrabar = Double.parseDouble(tProdaVender.getValueAt(control.fila, 7).toString());
         idProductoGrabar = tProdaVender.getValueAt(control.fila, 0).toString();
         control.Sql = "call venta_productos_venta ('" + precioGrabar + "','" + idVenta + "','" + idProductoGrabar + "','" + Libre + "');";
         control.CrearRegistro(control.Sql);control.fila++;ListaProductoPorVendido(idProductoGrabar,"Vendido");
       }
       control.LimTablaeditable(model);control.LimTabla(modeloClientes);
       control.LimTabla(modeloProductosAlmacen);buscarProducto();buscarCliente();
       txtBucarProducto.setText("");txtBucarProducto.grabFocus();control.fila = 0;
       controlCliente = 0;ppp = 0;
       JOptionPane.showMessageDialog(this,"La Venta Es "+idVenta);
       ImprimirVersionUltima(idVenta);
       //imprimirComprobante();
        }
    }
    private static boolean band() {
        if (Math.random() > .5) {
            return true;
        } else {
            return false;
        }
    }
    private String Num_Letra(String codv) {
        control.Sql = "SELECT mes FROM impri_comprobante_venta where idventa='" + codv + "' group by idventa;";
        int sumto = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
        switch (sumto) {
            case 1: {
                codv = "Enero";
                break;
            }
            case 2: {
                codv = "Febrero";
                break;
            }
            case 3: {
                codv = "Marzo";
                break;
            }
            case 4: {
                codv = "Abril";
                break;
            }
            case 5: {
                codv = "Mayo";
                break;
            }
            case 6: {
                codv = "Junio";
                break;
            }
            case 7: {
                codv = "Julio";
                break;
            }
            case 8: {
                codv = "Agosto";
                break;
            }
            case 9: {
                codv = "Setiembre";
                break;
            }
            case 10: {
                codv = "Octubre";
                break;
            }
            case 11: {
                codv = "Noviembre";
                break;
            }
            case 12: {
                codv = "Diciembre";
                break;
            }
        }
        return codv;
    }
    private String Num_Mes(String codven) {
        control.Sql = "SELECT sum(tot) FROM impri_comprobante_venta  where idventa='" + codven + "';";
        String sumto = control.DevolverRegistroDto(control.Sql, 1);
        String numer = nume.Convertir(sumto, band());
        return numer;
    }
    private void Cancelar() {
      chAgrupar.setSelected(true);lbCantidadMax.setText("");InfoGeneral.setControlCliente(0);
      txtNumComprobantes.setText("");lbCliente.setText(" ");ppp = 0;lbProducto.setText(" ");
      lblFechaPago.setVisible(false);txtBucarCliente.setText("");txtBucarProducto.setText("");
      txtCantidad.setText("1");txtCantidad1.setText("1");txtDescuento.setText("0.00");
      txtMontoInicial.setText("0.00");txtValorVentaFinal.setText("0.00");txtPrecio.setText("0.00");
      lbSerie.setText("");totalVenta = 0.00;controlCliente = 0;lbTotalVenta.setText("" + totalVenta);
      rbContado.setSelected(true);txtBucarProducto.grabFocus();control.LimTablaeditable(model);
      control.LimTabla(modeloProductosAlmacen);control.LimTabla(modeloClientes);
      buscarCliente();ActualizaEsta();
      txtMontoInicial.setVisible(false);txtFechaPago.setVisible(false);
      elprecio = 0.0;montoreal = 0.0;txmontodiferente.setText(null);chkLibre.setSelected(false);
      buscarProducto();contadordeproductos = 0;info.setRucc("");info.setPass("");
      controlCliente++;lbCliente.setText("CLIENTE");cbTipoComprobante.setSelectedIndex(0);
      lbmontovuelto.setText("0.00");jTextField1.setText("");txmontodiferente.setText("0.00");
      txtDescuentoTotal.setText("0.00");txtreferencia.setText("");
    }
    private void ActualizaEsta() {
        control.fila = 0;
        if (tProdaVender.getRowCount() > 0) {
            control.Sql = "select idusuario from usuario where nomusr='" + info.getUsuario() + "'";
            String idUsuario = control.DevolverRegistroDto(control.Sql, 1); //Capturamos el id del usuario      
            control.Sql="update producto set estdo='Disponible' where idproducto in(select idproducto from por_vender " +
            "where idusuario='"+idUsuario+"' and sede='"+Controlador.sede+"') and estdo='Por vender';";
            control.EditarRegistro(control.Sql);
            control.Sql="delete from por_vender where idusuario='"+idUsuario+"' and sede='"+Controlador.sede+"';";
            control.EliminarRegistro(control.Sql);
        }
    }
    private void ListaProductoPorVendido(String cod, String est) {
        //////////////***********GRABAR CAMBIO DE DATOS************///////////////////////////     
        control.Sql = "SELECT count(*) FROM productocantidad where idproducto='" + cod + "';";
        int pp = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
        if (pp > 0) {
            control.Sql = "SELECT cantidad,idproducto FROM productocantidad where idproducto='" + cod + "';";
            int cnt = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
            control.Sql = "update productocantidad set cantidad='" + (cnt - cantidadVender) + "' where idproducto='" + cod + "';";
            control.EditarRegistro(control.Sql);
            control.Sql = "update producto set estdo='Disponible' " + "where idproducto='" + cod + "';";
            control.EditarRegistro(control.Sql);
        } else {
            control.Sql = "update producto set estdo='" + est + "' where idproducto='" + cod + "';";
            control.EditarRegistro(control.Sql);
        }
        control.Sql = "select idusuario from usuario where nomusr='" + info.getUsuario() + "'";
        String us = control.DevolverRegistroDto(control.Sql, 1);
//     Controlador.sede
        if (est.compareTo("Por Vender") == 0) {
            control.Sql = "insert into por_vender values('" + cod + "','" + us + "','" + Controlador.sede + "');";
            control.CrearRegistro(control.Sql);
        } else {
            control.Sql = "delete from por_vender where idproducto='" + cod + "' and idusuario='" + us + "';";
            control.EliminarRegistro(control.Sql);
        }
        cantidadVender = 0;
    }
    private boolean verificarcomprobante(String tipo) {
        boolean a = false;
        String cont = control.DevolverRegistroDto("select count(*) from comprobantesvta where esta='Activo' and tipcompr='" + tipo + "' ", 1);
        int valo = Integer.parseInt(cont);
        if (valo > 0) {
            a = true;
        }
        return a;
    }
    private void comporbantesss() {
        try {
            control.Sql = "SELECT * FROM tipocomprobante;";
            control.Base.st = control.Base.conec.createStatement();
            control.Base.rt = control.Base.st.executeQuery(control.Sql);
            while (control.Base.rt.next()) {
                control.Sql = "call  GeneraComprobante('" + Controlador.sede + "','" + control.Base.rt.getString(2) + "','Productos');";
                control.CrearRegistro(control.Sql);
            }
        } catch (Exception e) {
        }
    }
    private void Comprobante_Grabar() {
        if (tProdaVender.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No Hay Nada Que Facturar.");
            return;
        }
        if (cbTipoComprobante.getSelectedIndex() >= 0) {
            control.Sql = "call GeneraComprobante('" + Controlador.sede + "','" + cbTipoComprobante.getSelectedItem().toString() + "','Productos')";
            txtNumComprobantes.setText(control.DevolverRegistroDto(control.Sql, 1));
            lbSerie.setText(control.DevolverRegistroDto(control.Sql, 2));
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione Un Tipo De Comprobante!!");
        }
    }
    private void GenerarNuevoComrpobante() {
        if (tProdaVender.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No Hay Nada Que Facturar.");
            return;
        }
        if (cbTipoComprobante.getSelectedIndex() >= 0) {
            control.Sql = "select * from vta_comprobantes where tipo='" + cbTipoComprobante.getSelectedItem().toString()
                        + "' and sede='" + Controlador.sede + "' and para='Productos'";
            if (control.Verificandoconsulta(control.Sql)) {
                control.Sql = "call GeneraComprobante('" + Controlador.sede + "','" + cbTipoComprobante.getSelectedItem().toString()
                            + "','Productos')";
                idComprobanteGenerado = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 3));
                numcomp = control.DevolverRegistroDto(control.Sql, 1);
                txtNumComprobantes.setText(numcomp);
                serie = control.DevolverRegistroDto(control.Sql, 2);
                lbSerie.setText(serie);
            } else {
                JOptionPane.showMessageDialog(cbTipoComprobante, "No Hay Documentos De Ese Tipo Debe De Iniciarlo.");
                IniciandoComprobantesICECompured inicdoc = new IniciandoComprobantesICECompured();
                Menu.jDesktopPane1.add(inicdoc);
                inicdoc.toFront();
                inicdoc.setLocation(250, 250);
                inicdoc.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione Un Tipo De Comprobante!!");
        }
    }
    private double numer(String num) {
        double ae = 0;
        String a = "", submun = "";
        int aae = num.length();
        for (int u = 0; u < aae; u++) {
            submun = num.substring(u, u + 1);
            if (submun.compareTo(",") == 0) {
                submun = ".";
            }
            a = a + submun;
        }
        ae = Double.parseDouble(a);
        return ae;
    }
    private void agregarListaProducto() {
        text = "";
        if (txtCantidad.getText().trim().length() > 0 &&
            txtDescuento.getText().trim().length() > 0 && 
            txtPrecio.getText().trim().length() > 0 && 
            txtValorVentaFinal.getText().trim().length() > 0 && 
            idProducto.trim().length() > 0) {
            nombreProducto = lbProducto.getText();
            int cantidadSolicitada = Integer.parseInt(txtCantidad.getText());
            if (cantidadSolicitada > Integer.parseInt(stockProducto)) {
             JOptionPane.showMessageDialog(this, "No Hay Suficientes Productos En Stock Para Cubrir La Cantidad Solicitada", 
             "Mensaje", JOptionPane.INFORMATION_MESSAGE);return;
            }
            calcularMontos();
            //***********************************Agrega el producto a vender   ******************************************
            int versihay[] = verificarentabla(lbProducto.getText());
            if (versihay[0] == 0) {
                double su = (Double.parseDouble(txtPrecio.getText())) - Double.parseDouble(txtDescuento.getText());
                datosProducto[0] = idProducto;datosProducto[1] = "" + (++contadordeproductos);
                datosProducto[2] = lbProducto.getText();datosProducto[3] = marcaProducto;
                datosProducto[4] = "" + forma.format(su).replace(",", ".");datosProducto[5] = txtCantidad.getText();
                datosProducto[6] = txtCantidad.getText();
                datosProducto[7] = "" + forma.format((Double.parseDouble(txtPrecio.getText()) - Double.parseDouble(
                txtDescuento.getText())) * Integer.parseInt(txtCantidad.getText())).replace(",", ".");datosProducto[8] = "Si";
                model.addRow(new Object[]{datosProducto[0], datosProducto[1], datosProducto[2],
                datosProducto[3], datosProducto[4], datosProducto[5], datosProducto[6],
                datosProducto[7], datosProducto[8], Boolean.TRUE});
                if (Double.parseDouble(txtCantidad.getText()) > 0) {
                    if (controlad == 0) {
                        if (cantidadSolicitada > 1) {
                            for (int fil = 1; fil <= cantidadSolicitada; fil++) {
                                control.Sql = "select * from producto_venta where nomctlg='" + nombreProducto
                                            + "' and estdo='Disponible' and codbrr like '%" + txtBucarProducto.getText()
                                            + "%' and idproducto not in(select Producto_idProducto from venta_producto)order by idproducto  limit 1";
                                idProducto = control.DevolverRegistroDto(control.Sql, 1);
                                ListaProductoPorVendido(idProducto, "Por Vender");
                            }
                        } else {
                            ListaProductoPorVendido(idProducto, "Por Vender");
                        }
                    } else {
                        if (cantidadSolicitada <= controlad) {
                            if (cantidadSolicitada > 1) {
                                for (int fil = 1; fil < cantidadSolicitada; fil++) {
                                    control.Sql = "select * from producto_venta where nomctlg='" + nombreProducto
                                                + "' and estdo='Disponible' and idproducto not in(select Producto_idProducto "
                                                + "from venta_producto)order by idproducto  limit 1";
                                    idProducto = control.DevolverRegistroDto(control.Sql, 1);
                                    ListaProductoPorVendido(idProducto, "Por Vender");
                                }
                            } else {
                                ListaProductoPorVendido(idProducto, "Por Vender");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No Puede Ingresar Una Cantidad Mayor A La Establecida.");
                        }
                    }
                }
                calcularmontototal();
            } else {
                int cntentbl = Integer.parseInt(tProdaVender.getValueAt(versihay[1], 5).toString());
                double precio = Double.parseDouble(tProdaVender.getValueAt(versihay[1], 4).toString());
                int nuevcnt = Integer.parseInt(txtCantidad.getText());//     
                tProdaVender.setValueAt("" + (int) (cntentbl + nuevcnt), versihay[1], 5);
                tProdaVender.setValueAt("" + (int) (cntentbl + nuevcnt), versihay[1], 6);
                tProdaVender.setValueAt("" + forma.format((cntentbl + nuevcnt) * precio).replace(",", "."), versihay[1], 7);//     
            }
            nombreProducto = "";limpiar();idProducto = "";txtBucarProducto.setText("");
            buscarProducto();txtBucarProducto.grabFocus();
        } else {
            JOptionPane.showMessageDialog(null, "Faltan Datos Para Agregar A La Lista De Venta.");
        }
    }
    public void llenarcontar() {
        control.fila = 0;
        while (control.fila < tProdaVender.getRowCount()) {
            tProdaVender.setValueAt((control.fila + 1), control.fila, 1);
            control.fila++;
        }
    }
    private void quitarProductoLista() {
        control.fila = tProdaVender.getSelectedRow();
        double importeProducto = Double.parseDouble(model.getValueAt(control.fila, 7).toString().replace(",", "."));
        contadordeproductos--;
        int cantidadEstablecida = Integer.parseInt(model.getValueAt(control.fila, 5).toString());
        if (cantidadEstablecida > 1) {
            nombreProducto = model.getValueAt(control.fila, 2).toString();
            control.Sql = "call LimpiaPorVender('" + info.getUsuario() + "','"
                        + Controlador.sede + "','" + nombreProducto + "');";
            control.EditarRegistro(control.Sql);
        } else {
            ListaProductoPorVendido(model.getValueAt(tProdaVender.getSelectedRow(), 0).toString(), "Disponible");
        }
        model.removeRow(control.fila);
        calcularmontototal();
        llenarcontar();
        buscarProducto();
    }
    private void calcularMontos() {
        if (idProducto.trim().length() > 0) {
            double pre = Double.parseDouble(txtPrecio.getText());
            double des = Double.parseDouble(txtDescuento.getText());
            int cant = Integer.parseInt(txtCantidad.getText());
            if ((cant > 1) && (cant > Integer.parseInt(stockProducto))) {
                JOptionPane.showMessageDialog(cbTipoComprobante, "La Cantidad A Facturar No Puede Ser Mayor A Lo Que Existe En Stock.");
                txtCantidad.grabFocus();
            } else {
                if (jCheckBox1.isSelected() == true) {
                    totalVenta = ((pre * cant) - des);
                }
                txtValorVentaFinal.setText("" + numer(forma.format(totalVenta)));
                txtCantidad1.setText(txtCantidad.getText());
                txtDescuento.grabFocus();
                txtDescuento.selectAll();
            }
        }
    }
    private void HacerDescuento(KeyEvent evt) {
     if (evt.getKeyChar() == 10 && idProducto.trim().length() > 0) {
       int cant = Integer.parseInt(txtCantidad.getText());
       if (Integer.parseInt(stockProducto) < cant) {
        JOptionPane.showMessageDialog(cbTipoComprobante, "La Cantidad A Facturar No Puede Ser Mayor A Lo Que Existe En Stock.");
        txtCantidad.grabFocus();
       } 
       else {
        double pre = Double.parseDouble(txtPrecio.getText());double des = Double.parseDouble(txtDescuento.getText());
        totalVenta = (pre - des) * cant;txtValorVentaFinal.setText("" + numer(forma.format(totalVenta)));
        txtValorVentaFinal.grabFocus();
       }
     }
    }
    public void MostrarCjaDiferente() {
        if (chkLibre.isSelected()) {
            txmontodiferente.setVisible(true);
            txmontodiferente.setText(Double.toString(montoreal));
            txmontodiferente.requestFocus();
        } else {
            txmontodiferente.setVisible(false);
        }
    }
    public void EstablecerTipodeVenta() {
        if (montoreal < Double.parseDouble(lbTotalVenta.getText())) {
            tipodeventa = "A";
        } else {
            if (chkLibre.isSelected()) {
                tipodeventa = "F";
            } else {
                tipodeventa = "N";
            }
        }
    }
    public void RealizaLaVenta() {
        if(lbCliente.getText().trim().length()==0){
         JOptionPane.showMessageDialog(null,"Tienes que seleccionar al cliente");
         return;
        }    
        if (jTextField1.getText().trim().length() > 0) 
            CalcularVuelto();
        
        if(String.valueOf(cbTipoComprobante.getSelectedItem()).equalsIgnoreCase("Factura")||
         String.valueOf(cbTipoComprobante.getSelectedItem()).equalsIgnoreCase("Ticket Factura") ){
         control.Sql="select cl.nomclie Cliente,idf.desident Identificación,idf.numident "
         + "Numero from cliente cl inner join identificacion idf on idf.idcliente=cl.idcliente " +
         "where cl.idcliente='"+info.getPass()+"'";    
         if(control.DevolverRegistroDto(control.Sql,3).length()<11){
           JOptionPane.showMessageDialog(null,"El Cliente "+control.DevolverRegistroDto(control.Sql, 
           1)+" No tiene Ruc\n No se puede realizar la venta"); 
           return;
         }         
        }        
        CalMontoDiferente();EstablecerTipodeVenta();verificarTipoPago();              
        /*****************************Restablecer cliente y Comprobante*****************************/
        lbCliente.setText("CLIENTE");idencte="";contadordeproductos=0;
        control.Sql="SELECT * FROM CLIENTE where nomclie='CLIENTE'";
        info.setPass(control.DevolverRegistroDto(control.Sql, 1));
        cbTipoComprobante.setSelectedIndex(0);generarComprobanteVenta();
        txtBucarProducto.grabFocus();
        /*****************************Restablecer Cliente y Comprobante*****************************/        
    }
    private int[] verificarentabla(String dato) {
      int cont = 0, sihay = 0;int t[] = new int[2];
      while (cont < tProdaVender.getRowCount()) {
        //if (dato.equals(tProdaVender.getValueAt(cont, 2).toString())) {
        if (dato.equals(tProductos.getValueAt(tProductos.getSelectedRow(), 0).toString()+" "+
         tProdaVender.getValueAt(cont, 2).toString()+" "+tProdaVender.getValueAt(cont, 3).toString())) {         
         t[0] = 1;t[1] = cont;break;
        }
        cont++;
      }
      return t;
    }
    private void llenarenuna() {
      int versihay[] = verificarentabla(tProductos.getValueAt(tProductos.getSelectedRow(), 0).toString()+" "+
              tProductos.getValueAt(tProductos.getSelectedRow(), 2).toString()
      +" "+tProductos.getValueAt(tProductos.getSelectedRow(), 3).toString());
      int filaselect = tProductos.getSelectedRow();
      if (versihay[0] == 0) {
       datosProducto[0] = tProductos.getValueAt(tProductos.getSelectedRow(), 0).toString();
       datosProducto[1] = "" + (++contadordeproductos);
       datosProducto[2] = tProductos.getValueAt(tProductos.getSelectedRow(), 2).toString();
       datosProducto[3] = tProductos.getValueAt(tProductos.getSelectedRow(), 3).toString();
       datosProducto[4] = "" + forma.format(Double.parseDouble(tProductos.getValueAt(tProductos.getSelectedRow(), 6).toString())).replace(",", ".");
       datosProducto[5] = "1";datosProducto[6] = "1";
       datosProducto[7] = "" + forma.format((Double.parseDouble(tProductos.getValueAt(
       tProductos.getSelectedRow(), 6).toString()))).replace(",", ".");
       datosProducto[8] = "Si";cantidadagregar = 1;
       elprecio = Double.parseDouble(forma.format(Double.parseDouble(
       tProductos.getValueAt(tProductos.getSelectedRow(), 6).toString())).replace(",", "."));
       montoreal = montoreal + (elprecio * Integer.parseInt(txtCantidad.getText()));
       elprecio = 0.0;txmontodiferente.setText(Double.toString(montoreal));
       model.addRow(new Object[]{datosProducto[0], datosProducto[1], datosProducto[2], datosProducto[3], datosProducto[4], datosProducto[5],
       datosProducto[6], datosProducto[7], datosProducto[8], Boolean.TRUE});
       ListaProductoPorVendido(tProductos.getValueAt(filaselect, 0).toString(), "Por Vender");
     } else {
            String idprodt = tProductos.getValueAt(tProductos.getSelectedRow(), 0).toString();
            double cntentbl = Double.parseDouble(tProdaVender.getValueAt(versihay[1], 5).toString());
            double precio = Double.parseDouble(tProdaVender.getValueAt(versihay[1], 4).toString());
            tProdaVender.setValueAt("" + (int) (cntentbl + 1), versihay[1], 5);
            tProdaVender.setValueAt("" + (int) (cntentbl + 1), versihay[1], 6);
            tProdaVender.setValueAt("" + forma.format((cntentbl + 1) * precio).replace(",", "."), versihay[1], 7);
            tProdaVender.setRowSelectionInterval(versihay[1], versihay[1]);elprecio = precio;
            if (chkLibre.isSelected()) {
             control.Sql = "update producto set estdo='Disponible' where idproducto='" + idprodt + "'";
             control.EditarRegistro(control.Sql);
            }
        }
        calcularmontototal();txtBucarProducto.setText("");lbProducto.setText("");buscarProducto();
    }
    public void calcularmontototal() {
        double total = 0;
        control.fila = 0;
        while (control.fila < tProdaVender.getRowCount()) {
            total += Double.parseDouble(tProdaVender.getValueAt(control.fila, 7).toString());
            control.fila++;
        }
        lbTotalVenta.setText("" + forma.format(total).replace(",", "."));
    }
    public void Verificar() {
        control.fila = 0;
        while (control.fila < tProdaVender.getRowCount()) {
            JOptionPane.showMessageDialog(null, tProdaVender.getValueAt(control.fila, 9));
            if ((Boolean) tProdaVender.getValueAt(control.fila, 9)) {
                JOptionPane.showMessageDialog(null, "Marcado.");
            } else {
                JOptionPane.showMessageDialog(null, "Sin Marcar.");
            }
            control.fila++;
        }
    }
    /*******************************************************Fin Metodos*********************************************/
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        lbProducto = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tClientes = new javax.swing.JTable();
        txtBucarCliente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        bClienete = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tProductos = new javax.swing.JTable();
        txtBucarProducto = new javax.swing.JTextField();
        lblStock = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tProdaVender = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Bcotizacion = new javax.swing.JButton();
        chAgrupar = new javax.swing.JCheckBox();
        bVenta = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        bSalir = new javax.swing.JButton();
        lbCantidadMax = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        txtValorVentaFinal = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        rbCredito = new javax.swing.JRadioButton();
        rbContado = new javax.swing.JRadioButton();
        txtFechaPago = new com.toedter.calendar.JDateChooser();
        txtMontoInicial = new javax.swing.JTextField();
        lblFechaPago = new javax.swing.JLabel();
        lblMontoInicial = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        cbTipoComprobante = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        txtNumComprobantes = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        lbSerie = new javax.swing.JLabel();
        chkLibre = new javax.swing.JCheckBox();
        txmontodiferente = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        lbCliente = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCantidad1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        lbmontovuelto = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lbTotalVenta = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtDescuentoTotal = new javax.swing.JTextField();
        lbmontovuelto1 = new javax.swing.JLabel();
        txtreferencia = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setNextFocusableComponent(txtBucarProducto);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbProducto.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        lbProducto.setForeground(new java.awt.Color(0, 51, 102));
        lbProducto.setText(" ");
        lbProducto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbProducto.setName("lbProducto"); // NOI18N
        getContentPane().add(lbProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, 1221, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Clientes", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tClientes.setForeground(new java.awt.Color(0, 51, 102));
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
        tClientes.setName("tClientes"); // NOI18N
        tClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tClientesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tClientes);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 53, 100, 14));

        txtBucarCliente.setName("txtBucarCliente"); // NOI18N
        txtBucarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBucarClienteKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBucarClienteKeyReleased(evt);
            }
        });
        jPanel2.add(txtBucarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 22, 380, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Buscar");
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 24, 56, -1));

        bClienete.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        bClienete.setForeground(new java.awt.Color(0, 51, 102));
        bClienete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Clien list.png"))); // NOI18N
        bClienete.setMnemonic('l');
        bClienete.setText("Agregar Cliente");
        bClienete.setName("bClienete"); // NOI18N
        bClienete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bClieneteActionPerformed(evt);
            }
        });
        jPanel2.add(bClienete, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 17, 150, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 620, 56));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Producto en stock", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

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
        tProductos.setName("tProductos"); // NOI18N
        tProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tProductosMouseClicked(evt);
            }
        });
        tProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tProductosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tProductos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 47, 980, 170));

        txtBucarProducto.setName("txtBucarProducto"); // NOI18N
        txtBucarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBucarProductoActionPerformed(evt);
            }
        });
        txtBucarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBucarProductoKeyReleased(evt);
            }
        });
        jPanel1.add(txtBucarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 17, 640, -1));
        txtBucarProducto.getAccessibleContext().setAccessibleParent(txtBucarProducto);

        lblStock.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblStock.setForeground(new java.awt.Color(0, 51, 102));
        lblStock.setText("Buscar");
        lblStock.setName("lblStock"); // NOI18N
        jPanel1.add(lblStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(725, 63, 140, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 102));
        jLabel7.setText("Buscar");
        jLabel7.setName("jLabel7"); // NOI18N
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 19, 55, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 183, 999, 230));

        txtPrecio.setBackground(new java.awt.Color(51, 153, 255));
        txtPrecio.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtPrecio.setText("0.00");
        txtPrecio.setName("txtPrecio"); // NOI18N
        txtPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioActionPerformed(evt);
            }
        });
        getContentPane().add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 380, 56, -1));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Cnt :");
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 390, -1, -1));

        txtCantidad.setBackground(new java.awt.Color(51, 153, 255));
        txtCantidad.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtCantidad.setText("1");
        txtCantidad.setName("txtCantidad"); // NOI18N
        txtCantidad.setNextFocusableComponent(txtDescuento);
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCantidadKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        getContentPane().add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 380, 58, -1));

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        tProdaVender.setForeground(new java.awt.Color(0, 51, 102));
        tProdaVender.setModel(new javax.swing.table.DefaultTableModel(
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
        tProdaVender.setName("tProdaVender"); // NOI18N
        tProdaVender.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tProdaVenderKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tProdaVender);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 1293, 200));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Valor venta:");
        jLabel6.setName("jLabel6"); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 390, 94, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Bcotizacion.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        Bcotizacion.setForeground(new java.awt.Color(0, 51, 102));
        Bcotizacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Add to basket.png"))); // NOI18N
        Bcotizacion.setMnemonic('z');
        Bcotizacion.setText("Cargar Cotizacion");
        Bcotizacion.setName("Bcotizacion"); // NOI18N
        Bcotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BcotizacionActionPerformed(evt);
            }
        });
        jPanel3.add(Bcotizacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 248, 40));

        chAgrupar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        chAgrupar.setMnemonic('A');
        chAgrupar.setText("Agrupar");
        chAgrupar.setName("chAgrupar"); // NOI18N
        chAgrupar.setOpaque(false);
        chAgrupar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chAgruparActionPerformed(evt);
            }
        });
        jPanel3.add(chAgrupar, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 70, 11, -1));

        bVenta.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        bVenta.setForeground(new java.awt.Color(0, 51, 102));
        bVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Notes.png"))); // NOI18N
        bVenta.setMnemonic('v');
        bVenta.setText("Registrar venta");
        bVenta.setName("bVenta"); // NOI18N
        bVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bVentaActionPerformed(evt);
            }
        });
        jPanel3.add(bVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 240, 50));

        bCancelar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 51, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setMnemonic('c');
        bCancelar.setText("Cancelar");
        bCancelar.setName("bCancelar"); // NOI18N
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel3.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, 40));

        bSalir.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
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
        jPanel3.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 160, 130, 40));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 186, 294, 250));

        lbCantidadMax.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbCantidadMax.setForeground(new java.awt.Color(0, 51, 102));
        lbCantidadMax.setText(" ");
        lbCantidadMax.setName("lbCantidadMax"); // NOI18N
        getContentPane().add(lbCantidadMax, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 486, 240, -1));

        txtDescuento.setBackground(new java.awt.Color(51, 153, 255));
        txtDescuento.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtDescuento.setText("0.00");
        txtDescuento.setName("txtDescuento"); // NOI18N
        txtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyTyped(evt);
            }
        });
        getContentPane().add(txtDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 380, 62, -1));

        txtValorVentaFinal.setBackground(new java.awt.Color(51, 153, 255));
        txtValorVentaFinal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtValorVentaFinal.setText("0.00");
        txtValorVentaFinal.setName("txtValorVentaFinal"); // NOI18N
        txtValorVentaFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValorVentaFinalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtValorVentaFinalKeyTyped(evt);
            }
        });
        getContentPane().add(txtValorVentaFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 380, 50, -1));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Modalidad de pago", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N

        buttonGroup1.add(rbCredito);
        rbCredito.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        rbCredito.setForeground(new java.awt.Color(0, 51, 102));
        rbCredito.setMnemonic('r');
        rbCredito.setText("Crédito");
        rbCredito.setName("rbCredito"); // NOI18N
        rbCredito.setOpaque(false);
        rbCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCreditoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbContado);
        rbContado.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        rbContado.setForeground(new java.awt.Color(0, 51, 102));
        rbContado.setMnemonic('n');
        rbContado.setSelected(true);
        rbContado.setText("Contado");
        rbContado.setName("rbContado"); // NOI18N
        rbContado.setOpaque(false);
        rbContado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbContadoActionPerformed(evt);
            }
        });

        txtFechaPago.setForeground(new java.awt.Color(0, 51, 102));
        txtFechaPago.setDateFormatString("yyyy-MM-dd");
        txtFechaPago.setName("txtFechaPago"); // NOI18N

        txtMontoInicial.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtMontoInicial.setText("0.00");
        txtMontoInicial.setName("txtMontoInicial"); // NOI18N
        txtMontoInicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoInicialKeyTyped(evt);
            }
        });

        lblFechaPago.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblFechaPago.setForeground(new java.awt.Color(0, 51, 102));
        lblFechaPago.setText("Dias de Pago");
        lblFechaPago.setName("lblFechaPago"); // NOI18N

        lblMontoInicial.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblMontoInicial.setForeground(new java.awt.Color(0, 51, 102));
        lblMontoInicial.setText("Mon. Inicial");
        lblMontoInicial.setName("lblMontoInicial"); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "7", "15", "30", "45" }));
        jComboBox1.setName("jComboBox1"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(rbContado, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                        .addGap(28, 28, 28)
                        .addComponent(rbCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblFechaPago, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                            .addComponent(lblMontoInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtMontoInicial, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(4, 4, 4))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbContado)
                    .addComponent(rbCredito))
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblMontoInicial))
                    .addComponent(txtMontoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFechaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1064, 56, -1, 130));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Dscto:  S/.");
        jLabel8.setName("jLabel8"); // NOI18N
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 390, -1, 21));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Facturación", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N
        jPanel6.setName("jPanel6"); // NOI18N

        cbTipoComprobante.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cbTipoComprobante.setForeground(new java.awt.Color(0, 51, 102));
        cbTipoComprobante.setName("cbTipoComprobante"); // NOI18N

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 102));
        jLabel11.setText("Tipo comprobante");
        jLabel11.setName("jLabel11"); // NOI18N

        txtNumComprobantes.setEditable(false);
        txtNumComprobantes.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtNumComprobantes.setName("txtNumComprobantes"); // NOI18N

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 102));
        jLabel12.setText("Número:");
        jLabel12.setName("jLabel12"); // NOI18N

        lbSerie.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbSerie.setForeground(new java.awt.Color(0, 51, 102));
        lbSerie.setText(" ");
        lbSerie.setName("lbSerie"); // NOI18N

        chkLibre.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        chkLibre.setForeground(new java.awt.Color(0, 51, 102));
        chkLibre.setMnemonic('p');
        chkLibre.setText("Solo para el Cliente");
        chkLibre.setName("chkLibre"); // NOI18N
        chkLibre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLibreActionPerformed(evt);
            }
        });

        txmontodiferente.setName("txmontodiferente"); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(chkLibre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNumComprobantes)
                    .addComponent(txmontodiferente)
                    .addComponent(cbTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(chkLibre)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                .addComponent(cbTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbSerie)
                        .addGap(31, 31, 31))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtNumComprobantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txmontodiferente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(714, 56, 350, 128));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente Seleccionado", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N
        jPanel7.setName("jPanel7"); // NOI18N

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 51, 102));
        jLabel16.setText("Cliente");
        jLabel16.setName("jLabel16"); // NOI18N

        lbCliente.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        lbCliente.setText(" ");
        lbCliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbCliente.setName("lbCliente"); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(lbCliente))
                .addGap(5, 5, 5))
        );

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(632, 0, 671, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Producto:");
        jLabel3.setName("jLabel3"); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 66, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Precio:");
        jLabel4.setName("jLabel4"); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, 51, -1));

        txtCantidad1.setBackground(new java.awt.Color(51, 153, 255));
        txtCantidad1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtCantidad1.setText("1");
        txtCantidad1.setName("txtCantidad1"); // NOI18N
        txtCantidad1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidad1KeyPressed(evt);
            }
        });
        getContentPane().add(txtCantidad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 380, -1, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Datos de Referencia");
        jLabel2.setName("jLabel2"); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 420, 120, -1));

        jCheckBox1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(0, 51, 102));
        jCheckBox1.setText("Aut.");
        jCheckBox1.setName("jCheckBox1"); // NOI18N
        getContentPane().add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 66, -1));

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbmontovuelto.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        lbmontovuelto.setForeground(new java.awt.Color(0, 51, 102));
        lbmontovuelto.setText("   ");
        lbmontovuelto.setName("lbmontovuelto"); // NOI18N
        jPanel5.add(lbmontovuelto, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 70, 80, -1));

        jTextField1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jTextField1.setToolTipText("Despues de ingresar el Monto entregado presione Enter");
        jTextField1.setName("jTextField1"); // NOI18N
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        jPanel5.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 130, -1));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 51, 102));
        jLabel14.setText("Monto Entregado");
        jLabel14.setName("jLabel14"); // NOI18N
        jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, -1, -1));

        jLabel18.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 51, 102));
        jLabel18.setText("Monto de Vuelto");
        jLabel18.setName("jLabel18"); // NOI18N
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 80, -1, -1));

        lbTotalVenta.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        lbTotalVenta.setForeground(new java.awt.Color(0, 51, 102));
        lbTotalVenta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTotalVenta.setText("0.00");
        lbTotalVenta.setName("lbTotalVenta"); // NOI18N
        jPanel5.add(lbTotalVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(74, 62, 154, -1));

        jLabel17.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 51, 102));
        jLabel17.setText("Total Venta:");
        jLabel17.setName("jLabel17"); // NOI18N
        jPanel5.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, 180, -1));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel15.setText("S/.");
        jLabel15.setName("jLabel15"); // NOI18N
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 62, -1, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 102));
        jLabel9.setText("Dscto Total:");
        jLabel9.setName("jLabel9"); // NOI18N
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 13, 180, -1));

        txtDescuentoTotal.setBackground(new java.awt.Color(51, 153, 255));
        txtDescuentoTotal.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        txtDescuentoTotal.setText("0.00");
        txtDescuentoTotal.setToolTipText("Despues de ingresar el descuento presione Enter");
        txtDescuentoTotal.setName("txtDescuentoTotal"); // NOI18N
        txtDescuentoTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescuentoTotalKeyTyped(evt);
            }
        });
        jPanel5.add(txtDescuentoTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 140, -1));

        lbmontovuelto1.setFont(new java.awt.Font("Arial", 1, 25)); // NOI18N
        lbmontovuelto1.setForeground(new java.awt.Color(0, 51, 102));
        lbmontovuelto1.setText("S/. ");
        lbmontovuelto1.setName("lbmontovuelto1"); // NOI18N
        jPanel5.add(lbmontovuelto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 70, 40, -1));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 56, 705, 128));

        txtreferencia.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtreferencia.setName("txtreferencia"); // NOI18N
        getContentPane().add(txtreferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(658, 415, 350, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

//****************************LOS EVENTOS***************************************
private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
    ActualizaEsta();
    InfoGeneral.setControlCliente(0);
    dispose();
}//GEN-LAST:event_bSalirActionPerformed
    private void tClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tClientesMouseClicked
        if (evt.getClickCount() >= 2) {
            SelecionarCliente();
            txmontodiferente.setVisible(false);
        }
    }//GEN-LAST:event_tClientesMouseClicked
    private void txtBucarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBucarClienteKeyReleased
        buscarCliente();
    }//GEN-LAST:event_txtBucarClienteKeyReleased
    private void txtBucarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBucarProductoKeyReleased
        buscarProducto();
    }//GEN-LAST:event_txtBucarProductoKeyReleased
    private void tProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProductosMouseClicked
        if (evt.getClickCount() >= 2) {
            mostrarStockProducto();
            
           // if (controlCliente > 0 || InfoGeneral.getControlCliente() == 1) {
            
                if (jCheckBox1.isSelected()) {
                    llenarenuna();
                } else {
                    seleccionarProducto();
                }
            /*}
            else {
                JOptionPane.showMessageDialog(null, "Seleccione Un Cliente Para Realizar La Venta.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }*/
        }
    }//GEN-LAST:event_tProductosMouseClicked
    private void txtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioActionPerformed
        txtCantidad.requestFocus();
        txtCantidad.selectAll();
    }//GEN-LAST:event_txtPrecioActionPerformed
    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
        if (evt.getKeyChar() == 10) {
            calcularMontos();
        }
    }//GEN-LAST:event_txtCantidadKeyPressed
    private void txtDescuentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyPressed
        HacerDescuento(evt);
    }//GEN-LAST:event_txtDescuentoKeyPressed
    private void txtValorVentaFinalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorVentaFinalKeyPressed
        if (KeyEvent.VK_ENTER == evt.getKeyCode()) {
            if (evt.getKeyChar() == 10) {
                agregarListaProducto();
            }
        } else {
            evt.consume();
        }
    }//GEN-LAST:event_txtValorVentaFinalKeyPressed
    private void bVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bVentaActionPerformed
      /***************************COMPROBAR SI EL COMPROBANTE ES FACTURA*****************************/
      if((cbTipoComprobante.getSelectedItem().toString().equalsIgnoreCase("Factura"))
        &&(rbContado.isSelected())){
       JOptionPane.showMessageDialog(null, "Solo se usa Factura Física para creditos \n No se puede realizar la Venta");
       cbTipoComprobante.grabFocus();return;     
     }
     /***************************COMPROBAR SI EL COMPROBANTE ES FACTURA*****************************/
      
     /***************************COMPROBAR QUE PRODUCTOS POR VENDER*****************************/ 
     if(tProdaVender.getRowCount()==0){
      JOptionPane.showMessageDialog(null, "No agrego ningún producto para vender \n No se puede realizar la Venta");
      txtBucarProducto.grabFocus();return;   
     } 
     /***************************COMPROBAR QUE PRODUCTOS POR VENDER*******************************/ 
     
     /***************************COMPROBAR QUE HAY UN COMPROBANTE GENERADO**********************/ 
     if(txtNumComprobantes.getText().trim().length()==0){
      JOptionPane.showMessageDialog(null, "Debes generar el nuevo comprobante \n No se puede realizar la Venta");
      cbTipoComprobante.grabFocus();
      return;
     }
     /***************************COMPROBAR QUE HAY UN COMPROBANTE GENERADO**********************/
     
     /***************************COMPROBAR QUE REALMENTE QUIERES HACER LA VENTA**********************/
     if (JOptionPane.showConfirmDialog(null, "Desea Realizar La Venta?", "System Message", JOptionPane.YES_NO_OPTION) == 0) {
      RealizaLaVenta();
     }
     /***************************COMPROBAR QUE REALMENTE QUIERES HACER LA VENTA**********************/
     
    }//GEN-LAST:event_bVentaActionPerformed
    private void txtCantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyReleased
        control.Solonumeros(evt);
    }//GEN-LAST:event_txtCantidadKeyReleased
    private void txtDescuentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyReleased
        control.decimal(evt, txtDescuento.getText());
    }//GEN-LAST:event_txtDescuentoKeyReleased
    private void rbContadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbContadoActionPerformed
     TipoVenta = 1;txtMontoInicial.setVisible(false);lblFechaPago.setVisible(false);
     txtFechaPago.setVisible(false);jComboBox1.setVisible(false);lblMontoInicial.setVisible(false);
    }//GEN-LAST:event_rbContadoActionPerformed
    private void rbCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCreditoActionPerformed
      TipoVenta = 2;txtMontoInicial.setVisible(true);lblMontoInicial.setVisible(true);
      jComboBox1.setVisible(true);lblFechaPago.setVisible(true);txtMontoInicial.grabFocus();
    }//GEN-LAST:event_rbCreditoActionPerformed
    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
     ActualizaEsta();Cancelar();
    }//GEN-LAST:event_bCancelarActionPerformed
    private void BcotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BcotizacionActionPerformed
      CargarCotizacion cc = new CargarCotizacion();Menu.jDesktopPane1.add(cc);
      cc.toFront();cc.setVisible(true);cc.setLocation(300, 120);
    }//GEN-LAST:event_BcotizacionActionPerformed
    private void bClieneteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bClieneteActionPerformed
     txtBucarCliente.setText("");buscarCliente();LosClientes cli = new LosClientes();
     Menu.jDesktopPane1.add(cli);LosClientes.txtCliente.grabFocus();info.setControl(1);
     cli.toFront();cli.setVisible(true);
    }//GEN-LAST:event_bClieneteActionPerformed
    private void txtBucarClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBucarClienteKeyPressed
      if (evt.getKeyChar() == 10) {
       if (tClientes.getRowCount() == 0) {
         bClienete.doClick();
       }
       else {
         if (tClientes.getRowCount() == 1) {
          tClientes.selectAll();SelecionarCliente();txtBucarProducto.grabFocus();
         }
       }
     }
    }//GEN-LAST:event_txtBucarClienteKeyPressed
    private void txtBucarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBucarProductoActionPerformed
        Cogerelproducto();
    }//GEN-LAST:event_txtBucarProductoActionPerformed
    private void tProductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tProductosKeyReleased
      mostrarStockProducto();
    }//GEN-LAST:event_tProductosKeyReleased
    private void chAgruparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chAgruparActionPerformed
        buscarProducto();
    }//GEN-LAST:event_chAgruparActionPerformed
    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        control.Solonumeros(evt);
    }//GEN-LAST:event_txtCantidadKeyTyped
    private void txtDescuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyTyped
        control.decimal(evt, txtDescuento.getText());
    }//GEN-LAST:event_txtDescuentoKeyTyped
    private void txtValorVentaFinalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorVentaFinalKeyTyped
        if (KeyEvent.VK_ENTER != evt.getKeyCode()) {
            evt.consume();
        }
    }//GEN-LAST:event_txtValorVentaFinalKeyTyped
    private void txtMontoInicialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoInicialKeyTyped
        control.decimal(evt, txtMontoInicial.getText());
    }//GEN-LAST:event_txtMontoInicialKeyTyped
    private void txtCantidad1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidad1KeyPressed
     if (evt.getKeyChar() == 10) {
      if (txtCantidad1.getText().trim().length() > 0) {
       txtDescuento.grabFocus();txtDescuento.selectAll();
      }
     }
    }//GEN-LAST:event_txtCantidad1KeyPressed
    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        if (evt.getKeyChar() == 10) {
            CalcularVuelto();
        }
    }//GEN-LAST:event_jTextField1KeyPressed
    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
      control.decimal(evt, jTextField1.getText());
    }//GEN-LAST:event_jTextField1KeyTyped
    private void txtDescuentoTotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoTotalKeyTyped

    }//GEN-LAST:event_txtDescuentoTotalKeyTyped
    private void tProdaVenderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tProdaVenderKeyPressed
     if (evt.getKeyChar() == 127) {
            if (JOptionPane.showConfirmDialog(null, "Desea Eliminar Este Producto De La Lista?", "System Message", JOptionPane.YES_NO_OPTION) == 0) {
                quitarProductoLista();
            }
        }
    }//GEN-LAST:event_tProdaVenderKeyPressed
    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        if (txtDescuentoTotal.getText().trim().length() == 0) {
            txtDescuentoTotal.setText("0.0");
        }
    }//GEN-LAST:event_jTextField1FocusGained
    private void chkLibreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkLibreActionPerformed
        MostrarCjaDiferente();
    }//GEN-LAST:event_chkLibreActionPerformed
//****************************LOS EVENTOS***************************************    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bcotizacion;
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bClienete;
    private javax.swing.JButton bSalir;
    private javax.swing.JButton bVenta;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbTipoComprobante;
    private javax.swing.JCheckBox chAgrupar;
    private javax.swing.JCheckBox chkLibre;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lbCantidadMax;
    public static javax.swing.JLabel lbCliente;
    private javax.swing.JLabel lbProducto;
    private javax.swing.JLabel lbSerie;
    public static javax.swing.JLabel lbTotalVenta;
    private javax.swing.JLabel lblFechaPago;
    private javax.swing.JLabel lblMontoInicial;
    private javax.swing.JLabel lblStock;
    private javax.swing.JLabel lbmontovuelto;
    private javax.swing.JLabel lbmontovuelto1;
    private javax.swing.JRadioButton rbContado;
    private javax.swing.JRadioButton rbCredito;
    private javax.swing.JTable tClientes;
    public static javax.swing.JTable tProdaVender;
    public static javax.swing.JTable tProductos;
    private javax.swing.JTextField txmontodiferente;
    public static javax.swing.JTextField txtBucarCliente;
    private javax.swing.JTextField txtBucarProducto;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCantidad1;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtDescuentoTotal;
    private com.toedter.calendar.JDateChooser txtFechaPago;
    private javax.swing.JTextField txtMontoInicial;
    private javax.swing.JTextField txtNumComprobantes;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtValorVentaFinal;
    private javax.swing.JTextField txtreferencia;
    // End of variables declaration//GEN-END:variables
   private void Resetear(boolean b, int can){
     if(b = true){
      if (tProdaVender.getSelectedRowCount() == 1) {
       control.Sql = "SELECT p.Catalogoproducto_codctlg FROM producto p, catalogoproducto c WHERE p.Catalogoproducto_codctlg "
       + "= c.codctlg AND p.idProducto ='" + tProdaVender.getValueAt(tProdaVender.getSelectedRow(), 0) + "';";
       idtProdaVender = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
       for (int a = 0; a < tProductos.getRowCount(); a++) {
        control.Sql = "SELECT p.Catalogoproducto_codctlg FROM producto p, catalogoproducto c WHERE p.Catalogoproducto_codctlg = "
        + "c.codctlg AND p.idProducto ='" + tProductos.getValueAt(a, 0) + "';";idtProductos = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
        if (idtProductos == idtProdaVender) {
         control.Sql = "SELECT COUNT(p.Catalogoproducto_codctlg) FROM producto p, catalogoproducto c WHERE p.Catalogoproducto_codctlg "
         + "= c.codctlg AND p.Catalogoproducto_codctlg = '" + idtProdaVender + "' AND p.estdo='Disponible';";
          int c = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
          if (Integer.parseInt(tProductos.getValueAt(a, 7).toString()) >= 1) {
           tProdaVender.setValueAt(can, tProdaVender.getSelectedRow(), 5);
          }
        }
       }
      }
     }
   }
}
