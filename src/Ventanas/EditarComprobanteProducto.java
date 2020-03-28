package Ventanas;

import Clases.Controlador;import Clases.IMPRIMIR;import Clases.InfoGeneral;
import Clases.Mimodelo;import Clases.Numero_a_Letra;import static Ventanas.AnularComprobante.MostrarCommprobantes;
import java.awt.HeadlessException;
import java.text.DecimalFormat;import javax.swing.ImageIcon;import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/******************** @author Silva  ****************/
public class EditarComprobanteProducto extends javax.swing.JInternalFrame {
   
    /**********************************LOS ATRIBUTOS*************************************/
    private Controlador control = new Controlador();private Mimodelo modeloProductos = new Mimodelo();
    private IMPRIMIR imprime = new IMPRIMIR();private Numero_a_Letra nume = new Numero_a_Letra();
    private Mimodelo modeloComprobante = (Mimodelo) AnularComprobante.tComprobante.getModel();
    private Mimodelo modeloTipocomprobante = new Mimodelo();
    private InfoGeneral info = new InfoGeneral();private String datos[] = new String[6];
    private String dat[] = new String[5];private int ppp = 0;private String idVenta = "";
    private String idComprobante = "";private String serie = "";private String srt = "";
    private DecimalFormat forma = new DecimalFormat("0.00");private String idNuevoComprobante;
    String[][] PorEliminar=new String[200][3]; int FilasArreglo=0;String idusuario="";
    DefaultTableModel  ModeloEliminar=new DefaultTableModel();
    /**********************************LOS ATRIBUTOS*************************************/

    /*******************************LOS MÉTODOS************************/
    public EditarComprobanteProducto() {
        initComponents();ModeloEliminar.setColumnIdentifiers(new String[]{"CodCtlg", "Cant", "idventa"});
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tablaProductos.setModel(modeloProductos);
        tablaTipoComprobante.setModel(modeloTipocomprobante);
        modeloProductos.setColumnIdentifiers(new String[]{"CodCtlg", "Producto", "Marca", "Unidad", 
          "Cantidad","Precio", "Importe","IdVenta"});
        modeloTipocomprobante.setColumnIdentifiers(new String[]{"ID", "Tipo", "Serie", "Para"});
        control.hideTableColumn(tablaProductos, 0,2,3,7);
        control.setWidthTableColumn(tablaProductos,90, 4,5,6);
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(350);
        
        control.Sql = "SELECT ID,Tipo,Serie,para FROM vta_tipocomprobante where sede='" 
        + Controlador.sede + "'";control.LlenarJTabla(modeloTipocomprobante, control.Sql, 4);
        cbtipo.setVisible(false);jButton5.setVisible(false);
        control.forma_table_ver(modeloProductos, tablaProductos);
        control.forma_table_ver(modeloTipocomprobante, tablaTipoComprobante);
        idComprobante=Integer.toString(info.getSerie());  
        control.LimTablaDefault(ModeloEliminar);
        control.Sql="select * from usuario where nomusr='"+info.getUsuario()+"'";
        idusuario=control.DevolverRegistroDto(control.Sql,1);
        btnGenerarNuevoComprobante.setVisible(false);jButton4.setVisible(false);
    }
    public void cargarDatosComprobante() {
     if (InfoGeneral.getSerie() > 0) {
       idComprobante=Integer.toString(InfoGeneral.getSerie());
      try {
       control.Sql = "SELECT nomclie, nume, tipcompr,idventa,serie FROM editar_compro "
       + "c where idComprobantes ='"+ InfoGeneral.getSerie() + "' limit 1;";
       lbCliente.setText(control.DevolverRegistroDto(control.Sql, 1));
       lbNumComproAn.setText(control.DevolverRegistroDto(control.Sql, 2));
       lbTipoComprobante.setText(control.DevolverRegistroDto(control.Sql, 3));
       idVenta = control.DevolverRegistroDto(control.Sql, 4);
       lbSeries.setText(control.DevolverRegistroDto(control.Sql, 5));
       //llenarProductos(idVenta);
       MostrarProductos();
      }
      catch (HeadlessException e) {   e.printStackTrace();}
     }
    }
    public void GrabarCambiosRealizados(){
      if(ModeloEliminar.getRowCount()>0){
        /********************Eliminar el detalle de la venta*********************/           
          for(int f=0;f<FilasArreglo;f++){
            /********************Eliminar el detventafactura*********************/
            control.Sql="delete from detventaproducto where idventa='"+idVenta+"' and codctlg='"
            +ModeloEliminar.getValueAt(f, 0).toString()+"'"; control.EliminarRegistro(control.Sql);  
            /********************Eliminar el detventafactura*********************/
            
            /****************Poner como disponibles los productos vendidos de ese item*********************/
            control.Sql="";
            /********************Poner como disponibles los productos vendidos*********************/
            
            /********************Eliminar el Venta_producto*********************/
            control.Sql="delete  from venta_producto where (venta_idventa='"+idVenta+" ' and producto_idproducto "
            + "in (select idproducto from producto where catalogoproducto_codctlg='"+ModeloEliminar.getValueAt(f,
            0).toString()+"' and estdo='vendido'));";control.EliminarRegistro(control.Sql);
            /********************Eliminar el Venta_producto*********************/
          }
        /********************Eliminar el detalle de la venta*********************/
        GenerarNuevoComprobante();
       }
       else{
        JOptionPane.showMessageDialog(null,"No se ha registrado Ningún Cambio");        
       }  
    }
    public void MostrarProductos(){
     control.Sql="select mc.codctlg IDCtlg,concat(md.nommod,' ',cp.nomctlg) Producto,mrc.nommrc Marca,"
     + "u.nomuni Unidad,dvf.cant Cantidad,dvf.prec Precio,round((dvf.cant*dvf.prec),2) Importe,v.Idventa "
     + "from venta v inner join compventa cv on cv.idventa=v.idventa inner join comprobantes cb on "
     + "cb.idcomprobantes=cv.idcomprobantes inner join detventafacturar dvf on dvf.idventa=v.idventa" 
     +" inner join catalogoproducto cp on cp.codctlg=dvf.codctlg inner join unidad u on u.idunidad="
     + "cp.idunidad inner join marca mrc on mrc.idmarca=cp.idmarca inner join modelocatalogo mc "
     + "on mc.codctlg=cp.codctlg inner join modelo md on md.idmodelos=mc.idmodelos inner join "
     + "tipocomprobante tc on tc.idtipocomprobante=cb.idtipocomprobante where cb.idcomprobantes='"+
     idComprobante+"' and cb.esta='Emitido' order by 1;";control.LlenarJTabla(modeloProductos,control.Sql, 8);     
    }
    private void llenarProductos(String cod) {
        control.Sql = "SELECT idproducto,nomctlg,codbrr,seri,prc,nommrc FROM ver_vendido where idVenta='" + cod + "';";
        control.LlenarJTablaSE(modeloProductos, control.Sql, 6);
    }
    private void EliminarProducto(String codi) {
        control.Sql = "call EditarListaVendida('" + codi + "');";
        control.EditarRegistro(control.Sql);
        control.LimTabla(modeloProductos);
        llenarProductos(idVenta);
    }
    public void EliminaVenta(){
     /*************************Capturar el ID de la Venta************************************/   
     control.Sql="select v.idventa ID from venta v inner join compventa cv on v.idventa="
     + "cv.idventa inner join comprobantes cb on cb.idcomprobantes=cv.idcomprobantes " +
     "where cb.idcomprobantes='"+idComprobante+"'"; idVenta=control.DevolverRegistroDto(control.Sql,1);
     /*************************Capturar el ID de la Venta************************************/
     
     /*************************Eliminar los pagos de la venta*********************************/
     control.Sql="delete from pagos where id='"+idVenta+"' and tipo='Venta';";
     control.EliminarRegistro(control.Sql);
     /*************************Eliminar los pagos de la venta*********************************/
     
     /************Eliminar Guia de remision de la venta cuyo Comprobante sea el Seleccionado***********/
     control.Sql="delete from guiaremision where idventa='"+idVenta+"' and idcomprobante='"+
      idComprobante+"';";control.EliminarRegistro(control.Sql);
     /************Eliminar Guia de remision de la venta cuyo Comprobante sea el Seleccionado***********/
     
     /*************Eliminar los detalles de venta facturar de la venta seleccionada*************/
     control.Sql="delete from detventafacturar where idventa='"+idVenta+"';";
     control.EliminarRegistro(control.Sql);
     /*************Eliminar los detalles de venta facturar de la venta seleccionada*************/
     
     /***************Actualizamos el estado de los productos de la venta********************/
     /*control.Sql="update producto set estdo='Disponible' where idproducto in" +
     "(select idproducto from (select Producto_idproducto from venta_producto where Venta_idventa='"+
     idVenta+"') as newtaba);";*/
     control.Sql="update producto set estdo='Disponible' where idproducto in(select producto_idproducto "
     + "from venta_producto where venta_idventa='"+idVenta+"') and estdo='Vendido';";     
     control.EditarRegistro(control.Sql);
     /***************Actualizamos el estado de los productos de la venta********************/
     
     /***********************Eliminar los Venta_producto de la venta*********************/
     control.Sql="delete from venta_producto where Venta_idventa='"+idVenta+"';";
     control.EliminarRegistro(control.Sql);
     /***********************Eliminar los Venta_producto de la venta*********************/
     
     /***********************Eliminar el comprobante de la venta*************************/
     /*
     control.Sql="delete from compventa where idventa='"+idVenta+"';";
     control.EliminarRegistro(control.Sql);
     */
     /***********************Eliminar el comprobante de la venta*************************/
     
     /*********************************Eliminar la venta******************************/
     /*
     control.Sql="delete from Venta where idventa='"+idVenta+"';";
     control.EliminarRegistro(control.Sql);
     */
     /*********************************Eliminar la venta******************************/
     
     /***************Actualizamos el estado del comprobante a Anulado**********************/
     control.Sql="update comprobantes set esta='Anulado' where idComprobantes='"+
     idComprobante+"';";control.EditarRegistro(control.Sql);
     /***************Actualizamos el estado del comprobante a Anulado**********************/
     
     /******************************************Guardar el comprobate Anulado*****************************/
     control.Sql="insert into anulados(idventa,fecha,hora,idusuario,motivo,idcomprobantes) values('"+idVenta+"',curdate(),"
      + "curtime(),'"+idusuario+"','Por venta anulada','"+idComprobante+"')";control.CrearRegistro(control.Sql);
     /******************************************Guardar el comprobate Anulado*****************************/
     dispose();
    }
    private void EliminarVentaTotal() {
        if ((JOptionPane.showConfirmDialog(null, "Desea Anular  la Venta",
                "", JOptionPane.YES_NO_OPTION)) == 0) {
            while (control.fila < tablaProductos.getRowCount()) {
                control.Sql = "call EditarListaVendida('"
                        + modeloProductos.getValueAt(control.fila, 0).toString() + "');";
                control.EditarRegistro(control.Sql);
                control.fila++;
            }
            control.Sql = "update comprobantes set esta='Anulado' where idcomprobantes='"
                    + info.getSerie() + "';";
//            System.out.println(control.Sql);
            control.EliminarRegistro(control.Sql);
            control.Sql = "delete from compventa where idventa='" + idVenta + "';";
//            System.out.println(control.Sql);
            control.EliminarRegistro(control.Sql);
            
            control.Sql = "delete from detventafacturar where idventa='" + idVenta + "';";
//            System.out.println(control.Sql);
            control.EliminarRegistro(control.Sql);
            
            control.Sql = "delete from venta where idventa='" + idVenta + "';";
//            System.out.println(control.Sql);
            control.EliminarRegistro(control.Sql);
            control.LimTabla(modeloProductos);
            lbMensaje.setText("Venta anulada Exitosamemte!!");
            this.dispose();
            llenar();
        }
    }
    public void QuitarItemDelaVenta(){
     control.fila=tablaProductos.getSelectedRow();
     if(control.fila>=0){
      if(JOptionPane.showConfirmDialog(this,"Estas Seguro que quieres eliminar "
       + "el detalle","Confirmar",0)==0){
       /***************Llenar los Items Seleccionados y Eliminar Fila**************/   
       control.Data[0]=tablaProductos.getValueAt(control.fila,0).toString(); // Codigo del Catalogo
       control.Data[1]=tablaProductos.getValueAt(control.fila,4).toString(); // LA Cantidad
       control.Data[2]=tablaProductos.getValueAt(control.fila,7).toString(); // Id de la Venta      
       modeloProductos.removeRow(control.fila);  
       /***************Llenar los Items Seleccionados y Eliminar Fila**************/
      }   
     } 
     else{
      JOptionPane.showMessageDialog(null, "Primero tienes que seleccionar el Item a quitar");
     }
    }
    private void GenerarNuevoComprobante() {
     if (tablaTipoComprobante.getSelectedRow() >= 0) {
      if (idNuevoComprobante != null) {
       if ((JOptionPane.showConfirmDialog(this, "¿Confirma que desea emitir un nuevo comprobante?", 
        "Nuevo comprobante", JOptionPane.YES_NO_OPTION)) == 0) {
        control.ejecutar(String.format("update comprobantes set esta ='Anulado' where idcomprobantes = %s;",
        InfoGeneral.getSerie()));
        control.ejecutar(String.format("update compventa set idcomprobantes =%s where idventa = %s;",
        idNuevoComprobante, idVenta));
        control.ejecutar(String.format("update comprobantes set esta ='Emitido' where idcomprobantes = %s;"
        , idNuevoComprobante));
        InfoGeneral.setSerie(Integer.parseInt(idNuevoComprobante));llenar();
        lbMensaje.setText("Nuevo comprobante generado: " + txtNumComprobante.getText());
        imprimirComprobante(modeloTipocomprobante.getValueAt(tablaTipoComprobante.getSelectedRow(),
        1).toString());tablaTipoComprobante.clearSelection();txtNumComprobante.setText("");
        lbSerieComproNuev.setText(" ");idNuevoComprobante = null;
       }//ver y checar
      } 
      else 
       JOptionPane.showMessageDialog(this, "Genere un  número de comprobante, dando doble clic en un tipo de comprobante", "Mensaje", JOptionPane.INFORMATION_MESSAGE);     
     }
     else 
      JOptionPane.showMessageDialog(this, "Seleccione el tipo de comprobante que desea emitir", "Mensaje", JOptionPane.INFORMATION_MESSAGE);     
    }
    private String Num_Mes(String codven) {
        control.Sql = "SELECT sum(tot) FROM impri_comprobante_venta  where idventa='" + codven + "';";
        String sumto = control.DevolverRegistroDto(control.Sql, 1);
        String numer = nume.Convertir(sumto, band());
        return numer;
    }
    private String Num_Letra(String codv) {
        control.Sql = "SELECT mes FROM impri_comprobante_venta where idventa='"
                + codv + "' group by idventa;";
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
    private static boolean band() {
        if (Math.random() > .5) {
            return true;
        } else {
            return false;
        }
    }
    private void limpiar() {
        while (modeloComprobante.getRowCount() > 0) {
            modeloComprobante.removeRow(0);
        }
    }
    private void llenar() {
     try {
            AnularComprobante.MostrarCommprobantes();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void GenerarComprobanteFirme() {
     control.fila = tablaTipoComprobante.getSelectedRow();
     control.Sql = "call ElComprobante('0','" + Controlador.sede + "','"
     + tablaTipoComprobante.getValueAt(control.fila, 1).toString() + "','"
     + tablaTipoComprobante.getValueAt(control.fila, 3).toString() + "','0','1')";

     if (control.DevolverRegistroDto(control.Sql, 1).equals("Se debe iniciar este tipo de comprobante")) {
      JOptionPane.showMessageDialog(this, "No hay documentos de ese tipo debes iniciarlo");
      IniciandoComprobantesICECompured inicdoc = new IniciandoComprobantesICECompured();
      Menu.jDesktopPane1.add(inicdoc);inicdoc.toFront();
      inicdoc.setLocation(250, 250);inicdoc.setVisible(true);
     }
     else {
      txtNumComprobante.setText(control.DevolverRegistroDto(control.Sql, 1));
      lbSerieComproNuev.setText(control.DevolverRegistroDto(control.Sql, 2));
      idNuevoComprobante = control.DevolverRegistroDto(control.Sql, 3);
     }
    }
    private void imprimirComprobante(String tipo) {
     control.Sql = "select * from vta_datosimpresion where idventa='" + idVenta + "'";
     String cliente = control.DevolverRegistroDto(control.Sql, 2);
     String direc = control.DevolverRegistroDto(control.Sql, 3);
     String dia = control.DevolverRegistroDto(control.Sql, 4);
     String mes = control.DevolverRegistroDto(control.Sql, 5);
     String anio = control.DevolverRegistroDto(control.Sql, 6).substring(3, 4);
     String idencte = control.getValueQuery(String.format("SELECT `numident` FROM `identificacion` WHERE `idCliente`=%s;", control.DevolverRegistroDto(control.Sql, 1)));
     control.Sql = "select * from vta_importes where idventa='" + idVenta + "'";
     String monto = forma.format(control.DevolverRegistroDto(control.Sql, 2)).replace(",", ".");
     String igv = forma.format(control.DevolverRegistroDto(control.Sql, 4)).replace(",", ".");
     String subTotal = forma.format(control.DevolverRegistroDto(control.Sql, 3)).replace(",", ".");
     String monlt = control.numlt.Convertir("" + (Double.parseDouble(igv) + Double.parseDouble(subTotal)), true);
     if (dia.length() == 1) {
      dia = "0" + dia;
     }

        if (tipo.equalsIgnoreCase("Boleta")) {
//            if(Controlador.sede.equals("Infotel")){
//            control.impresor.ImpresionBoletaProductos("idventa", idVenta, "dia", dia, "mes", mes, "anio", anio,
//                    "cliente", cliente, "direc", direc, "Monto", monto, "BoletaInfo",new Numero_a_Letra().Convertir(monto, true),"numtotal");                
//            }else{
            control.impresor.ImpresionBoletaProductos("idventa", idVenta, "dia", dia, "mes", mes, "anio", anio,
                    "cliente", cliente, "direc", direc, "Monto", monto, "BoletaICE",new Numero_a_Letra().Convertir(monto, true),"numtotal");                
//            }

        }
//        else {
            if (tipo.equalsIgnoreCase("Factura")) {
                String guiaRemision = "";
                if (JOptionPane.showConfirmDialog(null, "Deseas imprimir Guia de Remisión", "Confirmar por favor", 0) == 0) {

                    control.Sql = "call ElComprobante('0','" + Controlador.sede + "','Guia de Remision','Productos','0','1')";
                    guiaRemision = control.DevolverRegistroDto(control.Sql, 1);
                    String idGuiaRemision = control.DevolverRegistroDto(control.Sql, 3);

                    if (guiaRemision.equalsIgnoreCase("Se debe iniciar este tipo de comprobante")) {
                        JOptionPane.showMessageDialog(this, "La numeración para la guía de remisión aún no ha sido inicializada, por lo tanto solo se mostrará la factura");
//                         if(Controlador.sede.equals("Infotel")){
//                                control.impresor.ImpresionFacturaProductos("idventa", idVenta, "dia", dia, "mes", mes, "anio",
//                                anio, "cliente", cliente, "dir", direc, "identi", idencte, "guia", guiaRemision, "monletras", monlt, "vlrvta", subTotal, "igv",
//                                igv, "total", monto, "FacturaInfotel");//Imprimir la Guia de Remision
//                         }else{
                             control.impresor.ImpresionFacturaProductos("idventa", idVenta, "dia", dia, "mes", mes, "anio",
                            anio, "cliente", cliente, "dir", direc, "identi", idencte, "guia", guiaRemision, "monletras", monlt, "vlrvta", subTotal, "igv",
                            igv, "total", monto, "FacturaIce","","");//Imprimir la Guia de Remision
//                         }

                    } else {

                        control.Sql = "update comprobantes set esta='Emitido' where idComprobantes='" + idGuiaRemision + "'";
                        control.EditarRegistro(control.Sql);
                        JOptionPane.showMessageDialog(this, "Aquí la guia de remisión");
                    }

                } else {
//                     if(Controlador.sede.equals("Infotel")){
//                            control.impresor.ImpresionFacturaProductos("idventa", idVenta, "dia", dia, "mes", mes, "anio",
//                            anio, "cliente", cliente, "dir", direc, "identi", idencte, "guia", guiaRemision, "monletras", monlt, "vlrvta", subTotal, "igv",
//                            igv, "total", monto, "FacturaInfotel");//Imprimir la Guia de Remision
//                     }else{
                            control.impresor.ImpresionFacturaProductos("idventa", idVenta, "dia", dia, "mes", mes, "anio",
                            anio, "cliente", cliente, "dir", direc, "identi", idencte, "guia", guiaRemision, "monletras", monlt, "vlrvta", subTotal, "igv",
                            igv, "total", monto, "FacturaIce","","");//Imprimir la Guia de Remision
//                     }

                }

            }
            if (tipo.equalsIgnoreCase("Ticket")) {
        String guiaRemision = "";
//        if(Controlador.sede.equals("Local2")){
         control.impresor.ImpresionFacturaProductos("idVenta", idVenta, "dia", dia, "mes", mes, "anio",
         anio, "cliente", cliente, "dir", direc, "identi", idencte, "guia", guiaRemision, 
         "monletras", monlt, "vlrvta", subTotal, "igv",
         igv, "total", monto, "ticket","","");//Imprimie factura infotel
//        }
//        else{
//         control.impresor.ImpresionFacturaProductos("idventa", idVenta, "dia", dia, "mes", mes, "anio",
//         anio, "cliente", cliente, "dir", direc, "identi", idencte, "guia", guiaRemision, "monletras",
//         monlt, "vlrvta", subTotal, "igv",
//         igv, "total", monto, "FacturaIce");//Imprime factura Ice
//        }
       }
        if (tipo.equalsIgnoreCase("Factura Ticket")) {
        String guiaRemision = "";
//        if(Controlador.sede.equals("Local2")){
         control.impresor.ImpresionFacturaProductos("idVenta", idVenta, "dia", dia, "mes", mes, "anio",
         anio, "cliente", cliente, "dir", direc, "identi", idencte, "guia", guiaRemision, 
         "monletras", monlt, "vlrvta", subTotal, "igv",
         igv, "total", monto, "ticket_factura","","");//Imprimie factura infotel
//        }
//        else{
//         control.impresor.ImpresionFacturaProductos("idventa", idVenta, "dia", dia, "mes", mes, "anio",
//         anio, "cliente", cliente, "dir", direc, "identi", idencte, "guia", guiaRemision, "monletras",
//         monlt, "vlrvta", subTotal, "igv",
//         igv, "total", monto, "FacturaIce");//Imprime factura Ice
//        }
       }
       if (tipo.equalsIgnoreCase("Boleta Ticket")) {
        String guiaRemision = "";
//        if(Controlador.sede.equals("Local2")){
         control.impresor.ImpresionFacturaProductos("idVenta", idVenta, "dia", dia, "mes", mes, "anio",
         anio, "cliente", cliente, "dir", direc, "identi", idencte, "guia", guiaRemision, 
         "monletras", monlt, "vlrvta", subTotal, "igv",
         igv, "total", monto, "ticket_boleta","","");//Imprimie factura infotel
//        }
//        else{
//         control.impresor.ImpresionFacturaProductos("idventa", idVenta, "dia", dia, "mes", mes, "anio",
//         anio, "cliente", cliente, "dir", direc, "identi", idencte, "guia", guiaRemision, "monletras",
//         monlt, "vlrvta", subTotal, "igv",
//         igv, "total", monto, "FacturaIce");//Imprime factura Ice
//        }
       }
//        }
    }
    public void ActualizarComprobante(){
     if(modeloProductos.getRowCount()>0){
      int ctdadpro=0; 
      //modeloProductos
      idVenta=modeloProductos.getValueAt(control.fila,7).toString();  
      JOptionPane.showMessageDialog(null, "La venta es "+idVenta);
      for(control.fila=0;control.fila<modeloProductos.getRowCount();control.fila++){
        /*********************ELIMINAR EL DETALLEVENTAFACTURAR**********************/  
        control.Sql="delete from detventafacturar where codctlg='"+modeloProductos.getValueAt(control.fila,
         0).toString()+"' and idventa='" +idVenta+"' order by 1 desc;";control.EliminarRegistro(control.Sql);
        /*********************ELIMINAR EL DETALLEVENTAFACTURAR**********************/         
         
        /*************************ACTUALIZAR EL ESTADO DE LOS PRODUCTOS*****************************/ 
        control.Sql="update producto set estdo='Disponible' where idproducto in (select idproducto from "
        + "(select idproducto from producto where idproducto in (select Producto_idproducto from "
        + "venta_producto where Venta_idventa='"+idVenta+"') and catalogoproducto_codctlg='"
        +modeloProductos.getValueAt(control.fila,0).toString()+"') as Tabla);";control.EditarRegistro(control.Sql);
       /*************************ACTUALIZAR EL ESTADO DE LOS PRODUCTOS******************************/  
         
        /*************************ELIMINAR EL DE LA TABLA VENTA-PRODUCTO*************************/
        control.Sql="insert into temporal select idventa_producto,Producto_idProducto from venta_producto" +
        "  where Producto_idProducto in (select idproducto from producto where idproducto in (select "
        + "Producto_idproducto from venta_producto where Venta_idventa='" +idVenta+"') and catalogoproducto_codctlg='"
        +modeloProductos.getValueAt(control.fila,0).toString()+"');";control.CrearRegistro(control.Sql);
         
         control.Sql="delete from venta_producto where idventa_producto in (select campo1 from temporal);";
         control.EliminarRegistro(control.Sql);         
         control.Sql="delete from temporal;";control.EliminarRegistro(control.Sql);
        /*************************ELIMINAR EL DE LA TABLA VENTA-PRODUCTO*************************/
      }
      GenerarComprobanteFirme();
      //MostrarCommprobantes();
      imprimirComprobante(idVenta); dispose();
      AnularComprobante.MostrarCommprobantes();       
     }  
     else{
      JOptionPane.showMessageDialog(this,"No hay comprobantes");
     }
    }
    
    /*******************************LOS MÉTODOS************************/
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lbCliente = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lbTipoComprobante = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbSeries = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbNumComproAn = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cbtipo = new javax.swing.JComboBox();
        txtNumComprobante = new javax.swing.JTextField();
        lbSerieComproNuev = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaTipoComprobante = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnGenerarNuevoComprobante = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnEliminarVenta = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        lbMensaje = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("modificar comprobante de venta");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del comprobante", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbCliente.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbCliente.setForeground(new java.awt.Color(0, 51, 102));
        lbCliente.setText(" ");
        lbCliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbCliente.setName("lbCliente"); // NOI18N
        jPanel1.add(lbCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 19, 780, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Cliente:");
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 21, 50, 17));

        jButton5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 51, 102));
        jButton5.setMnemonic('G');
        jButton5.setText("Generar");
        jButton5.setName("jButton5"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, -1, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Comprobante anterior", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTipoComprobante.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbTipoComprobante.setForeground(new java.awt.Color(0, 51, 102));
        lbTipoComprobante.setText(" ");
        lbTipoComprobante.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbTipoComprobante.setName("lbTipoComprobante"); // NOI18N
        jPanel2.add(lbTipoComprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 160, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Tipo ");
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 31, 70, 17));

        lbSeries.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbSeries.setForeground(new java.awt.Color(0, 51, 102));
        lbSeries.setText(" ");
        lbSeries.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbSeries.setName("lbSeries"); // NOI18N
        jPanel2.add(lbSeries, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 88, 160, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 102));
        jLabel9.setText("Serie");
        jLabel9.setName("jLabel9"); // NOI18N
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 50, 17));

        lbNumComproAn.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbNumComproAn.setForeground(new java.awt.Color(0, 51, 102));
        lbNumComproAn.setText(" ");
        lbNumComproAn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbNumComproAn.setName("lbNumComproAn"); // NOI18N
        jPanel2.add(lbNumComproAn, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 58, 160, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Número");
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 70, 17));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 45, 225, 150));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nuevo comprobante", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Número");
        jLabel6.setName("jLabel6"); // NOI18N
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, -1, 17));

        cbtipo.setForeground(new java.awt.Color(0, 51, 102));
        cbtipo.setName("cbtipo"); // NOI18N
        cbtipo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbtipoMouseClicked(evt);
            }
        });
        cbtipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbtipoItemStateChanged(evt);
            }
        });
        cbtipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbtipoActionPerformed(evt);
            }
        });
        cbtipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbtipoKeyPressed(evt);
            }
        });
        jPanel4.add(cbtipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 160, -1));

        txtNumComprobante.setName("txtNumComprobante"); // NOI18N
        txtNumComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumComprobanteActionPerformed(evt);
            }
        });
        txtNumComprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumComprobanteKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNumComprobanteKeyReleased(evt);
            }
        });
        jPanel4.add(txtNumComprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(318, 117, 120, -1));

        lbSerieComproNuev.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbSerieComproNuev.setForeground(new java.awt.Color(0, 51, 102));
        lbSerieComproNuev.setText(" ");
        lbSerieComproNuev.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbSerieComproNuev.setName("lbSerieComproNuev"); // NOI18N
        jPanel4.add(lbSerieComproNuev, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 117, 110, -1));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Serie");
        jLabel8.setName("jLabel8"); // NOI18N
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 117, -1, 17));

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tablaTipoComprobante.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaTipoComprobante.setName("tablaTipoComprobante"); // NOI18N
        tablaTipoComprobante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaTipoComprobanteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaTipoComprobante);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 590, 90));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 45, 610, 150));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 857, 206));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGenerarNuevoComprobante.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnGenerarNuevoComprobante.setForeground(new java.awt.Color(0, 51, 102));
        btnGenerarNuevoComprobante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Edit.png"))); // NOI18N
        btnGenerarNuevoComprobante.setMnemonic('a');
        btnGenerarNuevoComprobante.setText("Actualizar Comprobante");
        btnGenerarNuevoComprobante.setName("btnGenerarNuevoComprobante"); // NOI18N
        btnGenerarNuevoComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarNuevoComprobanteActionPerformed(evt);
            }
        });
        jPanel3.add(btnGenerarNuevoComprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 200, 50));

        btnSalir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(0, 51, 102));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        btnSalir.setMnemonic('s');
        btnSalir.setText("Salir");
        btnSalir.setName("btnSalir"); // NOI18N
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel3.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, 200, 50));

        btnEliminarVenta.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnEliminarVenta.setForeground(new java.awt.Color(0, 51, 102));
        btnEliminarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        btnEliminarVenta.setMnemonic('l');
        btnEliminarVenta.setText("Eliminar la Venta");
        btnEliminarVenta.setName("btnEliminarVenta"); // NOI18N
        btnEliminarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarVentaActionPerformed(evt);
            }
        });
        jPanel3.add(btnEliminarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 10, 200, 50));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 218, 857, 75));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Productos Vendidos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N
        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tablaProductos.setForeground(new java.awt.Color(0, 51, 102));
        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaProductos.setName("tablaProductos"); // NOI18N
        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaProductos);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 63, 839, 170));

        jButton4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 51, 102));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        jButton4.setMnemonic('q');
        jButton4.setText("Quitar Producto");
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 13, 150, 45));

        lbMensaje.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbMensaje.setText(" ");
        lbMensaje.setName("lbMensaje"); // NOI18N
        jPanel5.add(lbMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 23, 618, -1));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 295, 857, 240));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**********************************LOS EVENTOS*************************************/
    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed
    private void tablaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductosMouseClicked
      /*  if (evt.getClickCount() == 2) {
            EliminarProducto(modeloProductos.getValueAt(tablaProductos.getSelectedRow(), 0).toString());
        }*/
    }//GEN-LAST:event_tablaProductosMouseClicked
    private void btnEliminarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarVentaActionPerformed
    //    EliminarVentaTotal();
        EliminaVenta();
        AnularComprobante.MostrarCommprobantes();
    }//GEN-LAST:event_btnEliminarVentaActionPerformed
    private void btnGenerarNuevoComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarNuevoComprobanteActionPerformed
      ActualizarComprobante();
    }//GEN-LAST:event_btnGenerarNuevoComprobanteActionPerformed
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      QuitarItemDelaVenta();
    }//GEN-LAST:event_jButton4ActionPerformed
    private void txtNumComprobanteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumComprobanteKeyReleased
        lbSerieComproNuev.setText(serie + "-" + txtNumComprobante.getText());
    }//GEN-LAST:event_txtNumComprobanteKeyReleased
    private void txtNumComprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumComprobanteKeyPressed
    }//GEN-LAST:event_txtNumComprobanteKeyPressed
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        GenerarComprobanteFirme();
    }//GEN-LAST:event_jButton5ActionPerformed
    private void txtNumComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumComprobanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumComprobanteActionPerformed
    private void tablaTipoComprobanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaTipoComprobanteMouseClicked
        if (evt.getClickCount() == 2) {
            GenerarComprobanteFirme();
        }
    }//GEN-LAST:event_tablaTipoComprobanteMouseClicked
    private void cbtipoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbtipoKeyPressed
        lbSerieComproNuev.setText(" ");
        txtNumComprobante.setText("");
    }//GEN-LAST:event_cbtipoKeyPressed
    private void cbtipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbtipoActionPerformed
        lbSerieComproNuev.setText(" ");
        txtNumComprobante.setText("");
    }//GEN-LAST:event_cbtipoActionPerformed
    private void cbtipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbtipoItemStateChanged
        lbSerieComproNuev.setText(" ");
        txtNumComprobante.setText("");
    }//GEN-LAST:event_cbtipoItemStateChanged
    private void cbtipoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbtipoMouseClicked
        lbSerieComproNuev.setText(" ");
        txtNumComprobante.setText("");
    }//GEN-LAST:event_cbtipoMouseClicked
   /**********************************LOS EVENTOS*************************************/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminarVenta;
    private javax.swing.JButton btnGenerarNuevoComprobante;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbtipo;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JLabel lbCliente;
    private javax.swing.JLabel lbMensaje;
    public static javax.swing.JLabel lbNumComproAn;
    public static javax.swing.JLabel lbSerieComproNuev;
    public static javax.swing.JLabel lbSeries;
    public static javax.swing.JLabel lbTipoComprobante;
    public static javax.swing.JTable tablaProductos;
    private javax.swing.JTable tablaTipoComprobante;
    private javax.swing.JTextField txtNumComprobante;
    // End of variables declaration//GEN-END:variables
}
