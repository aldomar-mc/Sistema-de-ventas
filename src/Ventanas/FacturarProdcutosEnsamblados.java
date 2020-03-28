package Ventanas;
/**** @author Ing. Miguel Angel Silva Zapata.   **********/
import javax.swing.*;import Clases.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle.Control;
import java.util.StringTokenizer;
public class FacturarProdcutosEnsamblados extends javax.swing.JInternalFrame {
    /*************************Atributos**********************/ 
    Mimodelo modelo=new Mimodelo();Mimodelo modelo1=new Mimodelo();
    Mimodelo modelo2=new Mimodelo();
    InfoGeneral info=new InfoGeneral();
    String sr="";
    private double suma=0;
    String srt="",idCatalogo="",idVenta,nompro;
    Controlador control = new Controlador();
    Numero_a_Letra nume= new Numero_a_Letra();
    //InfoGeneral info= new InfoGeneral();,
    IMPRIMIR imprime=new IMPRIMIR();
    private String igv;
    private String subTotal;
    private String idencte;
    public String codCliente="",codProducto, Marca,CodVenta, CodCompro,Modalidad,CodProductoGrabar,idProductoGrabar;
    public int controlCliente=0, TipoVenta=0,ppp=0,cantidadVender;
    double totalCompra=0,PrecioGrabar,precioGrabar;
    ArrayList <String> Lista=new ArrayList<String>();
    //<String> nombreArrayList = new ArrayList<String>();
    String datos[]=new String[6];
    private int idComprobanteGenerado=0, cantidadvender=0;
    /*************************Atributos*************************/ 
    public FacturarProdcutosEnsamblados() {
     initComponents();setTitle("Facturar Productos Ensamblados");
     this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     tClientes.setModel(modelo);modelo.setColumnIdentifiers(new String[] {"Código","Cliente","Dirección","Ruc/Dni"});
     tClientes.getColumnModel().getColumn(0).setMaxWidth(0);
     tClientes.getColumnModel().getColumn(0).setMinWidth(0);
     tClientes.getColumnModel().getColumn(0).setPreferredWidth(0);
     tClientes.getColumnModel().getColumn(1).setPreferredWidth(250);//SELECT idproducto, nomctlg,seri,codbrr, nommrc,precVenta  FROM producto_venta
     tProdaVender.setModel(modelo2);modelo2.setColumnIdentifiers(new String[] 
     {"Código","cantidad","Producto","Marca","Precio"});
     //idproducto,'1'as cantidad, nomctlg,  nommrc, precVenta
     tProdaVender.getColumnModel().getColumn(0).setPreferredWidth(50);
     tProdaVender.getColumnModel().getColumn(1).setPreferredWidth(50);
     tProdaVender.getColumnModel().getColumn(2).setPreferredWidth(250);
     tProdaVender.getColumnModel().getColumn(3).setPreferredWidth(100);
     tProdaVender.getColumnModel().getColumn(4).setPreferredWidth(100);
     bSalir.setMnemonic('s');
     //Bcotizacion.setMnemonic('t');
     bVenta.setMnemonic('v');
     bCancelar.setMnemonic('c');
     jButton1.setMnemonic('g');
     txtBucarCliente.grabFocus();
     jButton2.setVisible(false);
     comporbantesss();
     MostrarCliente();
    // MostrarProducto();
     //txtNumComprobantes.setVisible(false);
     //llenarcbo();
     dtFechaPago.setDate(new Date());
     txtMontoInicial.setVisible(false);
     lbMontoInicial.setVisible(false);
     dtFechaPago.setVisible(false);
     lbMontoInicial.setVisible(false);
     lbMontoInicial1.setVisible(false);
     rbVariable.setVisible(false);
     //Actulizar_Anterior_Producto();
     //lbSerie.setText(""+control.RecuperaSerie());
     control.forma_table_ver(modelo, tClientes);
     control.forma_table_ver(modelo2, tProdaVender);
    }
    
    
    public void Actulizar_Anterior_Producto(){
        control.Sql="select idusuario from usuario where nomusr='"+info.getUsuario()+"'";
        String us=control.DevolverRegistroDto(control.Sql, 1);
        
        //control.Sql="select * from por_vender where idusuario='"+us+"';";
        try {
            control.Base.st=control.Base.conec.createStatement();
            control.Base.rt=control.Base.st.executeQuery("select * from por_vender where idusuario='"+us+"';");
            while(control.Base.rt.next()){
                   control.Sql="update producto set estdo='Disponible' where idproducto='"+control.Base.rt.getString(1).toString()+"';";
                   control.EditarRegistro(control.Sql);       
            }
            control.Sql="delete from por_vender where idusuario='"+us+"';";
            control.EliminarRegistro(control.Sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void llenarcbo(){
    control.LlenarCombo(cbTipoComprobante, "SELECT * FROM tipocomprobante t", 2);
}
     public void MostrarCliente(){
     BuscarCliente();   
    }    
    public void BuscarCliente(){
     control.Sql="SELECT idcliente, nomclie,dir, numident FROM vta_cliente  where ( nomclie like'"+
     txtBucarCliente.getText()+"%' or numident like'"+
     txtBucarCliente.getText()+"%' )and idcliente in (select idcliente from adelanto where estado=0)";
     control.LlenarJTabla(modelo,control.Sql,4); 
//     System.out.println(control.Sql);
 //    JOptionPane.showMessageDialog(null,control.Sql);
    }
       
  
    public void SelecionarCliente(){
        //Cancelar();
        control.LimTabla(modelo2);
        lbCliente.setText("");
      //  lbProducto.setText(" ");
        lbTotalVenta.setText("0.00");
        totalCompra=0;
        //montototal=0.00;
        //lbMontoFinal.setText(""+montototal);
        lbCliente.setText(modelo.getValueAt(tClientes.getSelectedRow(), 1).toString());
        info.setPass(modelo.getValueAt(tClientes.getSelectedRow(), 0).toString());
        idencte = tClientes.getValueAt(tClientes.getSelectedRow(), 3).toString();
       // codCliente=info.getPass();
        controlCliente++;
        llenarproducto();
//        txtBucarProducto.grabFocus();
        //cbTipoComprobante.setSelectedIndex(-1);
        //ppp=0;
        //lbSerie.setText("");
    }
    
    public void llenarproducto(){
        control.Sql="select v.idproducto,count(pe.nomctlg)as cantidad, pe.nomctlg,  v.nommrc, pe.precio "
                + "FROM producto_venta v, productoensamblaje pe"
                + " where v.idproducto=pe.idproducto and "
                + " v.idproducto in  (select idproducto from productoensamblaje where idcliente='"+info.getPass()+"') group by pe.nomctlg; ";
//        System.out.println(control.Sql);
        control.LlenarJTabla(modelo2, control.Sql, 5);
        control.Sql="select sum(precVenta) FROM producto_venta where idproducto in (select idproducto from productoensamblaje where idcliente='"+info.getPass()+"');";
        lbTotalVenta.setText(control.DevolverRegistroDto(control.Sql, 1));
        control.Sql="SELECT monto FROM adelanto where idcliente='"+info.getPass()+"' and estado=0 order by idadelanto desc;";
        lbAdelanto.setText(control.DevolverRegistroDto(control.Sql, 1));
    }
    
    public boolean VerificaEstado(String cod){
        boolean a=false;
        if(control.Verificarconsulta("select * from producto where idproducto='"+cod+"' and estdo='Disponible';")){
            a=true;
        }
        return a;
    }

  
         public boolean verificarNumero(){
         boolean sss=false;
         if(txtNumComprobantes.getText().trim().length()>0){
             control.Sql="call  GeneraComprobante('1','"+cbTipoComprobante.getSelectedItem().toString()+"');";
             String codCom=control.DevolverRegistroDto(control.Sql, 2);
             control.Sql="select * from tipocomprobante where tipcompr='"+cbTipoComprobante.getSelectedItem().toString()+"';";                      
             control.Sql="select count(*) from comprobantes where nume='"+txtNumComprobantes.getText()+"' and idtipocomprobante='"+control.DevolverRegistroDto(control.Sql, 1)+"' and idcomprobantes<>'"+codCom+"';";
             String dds = control.DevolverRegistroDto(control.Sql, 1);
             control.Sql="SELECT candig FROM tipocomprobante where tipcompr='"+cbTipoComprobante.getSelectedItem().toString()+"';";
             int cantid = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
             if(dds.compareTo("0")==0){
                 if(txtNumComprobantes.getText().trim().length()==cantid){
                     sss=true;
                 }
             }
         }
         return sss;
     }
         
    private void vender() {
        if (cbTipoComprobante.getSelectedIndex() != -1 && tProdaVender.getRowCount() > 0 && TipoVenta > 0 && lbCliente.getText().trim().length() > 0) {

            control.Sql = "SELECT InsertaVentas('Contado','" + info.getPass() + "','" + info.getUsuario() + "')";

            idVenta = control.DevolverRegistroDto(control.Sql, 1);
            nompro=tProdaVender.getValueAt(control.fila, 2).toString();
            control.Sql = "SELECT InsertaComprobante('" + idComprobanteGenerado + "','" + idVenta + "')";  //Graba el comprobante de venta y devuelve su id
            control.DevolverRegistroDto(control.Sql, 1);
            
//            System.out.println(control.Sql);
            control.Sql = "call ElComprobante('0','" + Controlador.sede + "','" + cbTipoComprobante.getSelectedItem().toString()
                    + "','Productos','" + idComprobanteGenerado + "','2');"; 
            control.fila = 0;
            while (control.fila < tProdaVender.getRowCount()) {

                precioGrabar = Double.parseDouble(modelo2.getValueAt(control.fila, 4).toString());
                cantidadVender = Integer.parseInt(modelo2.getValueAt(control.fila, 1).toString());
                //************************************Registrar en la tabla para facturar***************************
                idCatalogo = control.DevolverRegistroDto("select * from catalogoproducto where nomctlg='" + tProdaVender.getValueAt(control.fila, 2) + "'", 1);

                control.Sql = "insert into detventafacturar(codctlg,cant,prec,idVenta) values('" + idCatalogo + "','" + cantidadVender + "','"
                        + precioGrabar + "','" + idVenta + "')";
                control.CrearRegistro(control.Sql);
                
                //************************************Fin Registrar en la tabla para facturar***********************
                if (cantidadVender > 1) {
                    for (int f = 1; f <= cantidadVender; f++) {
//                        control.Sql = "SELECT * FROM vta_porvender where estado='Por Vender' and usuario='"
//                                + info.getUsuario() + "' and sede='" + Controlador.sede + "' order by ID limit 1";
                        control.Sql="SELECT idproducto FROM productoensamblaje p where nomctlg='"+nompro+"' and idcliente='"+info.getPass()+"' limit 1;";
                        idProductoGrabar = control.DevolverRegistroDto(control.Sql, 1);
                        control.Sql = "call Venta_Productos_Venta ('" + precioGrabar + "','" + idVenta + "','" + idProductoGrabar + "');";
                        control.CrearRegistro(control.Sql);
////                        control.Sql = "Delete from por_vender where idusuario='" + control.DevolverRegistroDto("select * from usuario where nomusr='"
////                                + info.getUsuario() + "'", 1) + "' and idproducto='" + idProductoGrabar + "'";
                        control.Sql="delete from productoensamblaje where idproducto='"+idProductoGrabar+"';";
                        control.EliminarRegistro(control.Sql);
                    }
                } else {
                    idProductoGrabar = modelo2.getValueAt(control.fila, 0).toString();
                    control.Sql = "call Venta_Productos_Venta ('" + precioGrabar + "','" + idVenta + "','" + idProductoGrabar + "');";
                    control.CrearRegistro(control.Sql);
                    control.Sql="delete from productoensamblaje where idproducto='"+idProductoGrabar+"';";
                        control.EliminarRegistro(control.Sql);
                        ListaProductoPorVendido(idProductoGrabar, "Vendido");
                }
                suma=suma+precioGrabar;
//                System.out.println("La Suma es de: "+suma+"="+precioGrabar+"*"+cantidadVender);
                control.fila++;
            }
            control.Sql="update adelanto set estado=1 where idcliente='"+info.getPass()+"';";
            control.EditarRegistro(control.Sql);
            control.LimTabla(modelo2);
            control.LimTabla(modelo);
            //control.LimTabla(modeloProductosAlmacen);
            // buscarProducto();
            MostrarCliente();
            //buscarCliente();
            txtNumComprobantes.setText(" ");
            //txtBucarProduct.setText("");
            //txtBucarProducto.grabFocus();
            rbContado.setSelected(true);
            control.fila = 0;
            controlCliente = 0;
            ppp = 0;
            imprimirComprobante();
        }
    }
 private void imprimirComprobante() {

        control.Sql = "select * from vta_datosimpresion where idventa='" + idVenta + "'";
        String cliente = control.DevolverRegistroDto(control.Sql, 2);
        String direc = control.DevolverRegistroDto(control.Sql, 3);
        String dia = control.DevolverRegistroDto(control.Sql, 4);
        String mes = control.DevolverRegistroDto(control.Sql, 5);
        String anio = control.DevolverRegistroDto(control.Sql, 6).substring(3, 4);

        control.Sql = "select * from vta_importes where idventa='" + idVenta + "'";

        String monto = control.DevolverRegistroDto(control.Sql, 2);

        igv = control.DevolverRegistroDto(control.Sql, 4);
        subTotal = control.DevolverRegistroDto(control.Sql, 3);
        String monlt = control.numlt.Convertir(lbTotalVenta.getText(), true);

        if (dia.length() == 1) {
            dia = "0" + dia;
        }

        if (cbTipoComprobante.getSelectedItem().toString().equals("Boleta")) {
            cbTipoComprobante.setSelectedIndex(-1);
            txtNumComprobantes.setText(null);
            lbSerie.setText(null);
//            System.out.println(new Numero_a_Letra().Convertir(monto, true)+"--"+monto);
            if(Controlador.sede.equals("Infotel")){
                            control.impresor.ImpresionBoletaProductos("idventa", idVenta, "dia", dia, "mes", mes, "anio", anio,
                    "cliente", cliente, "direc", direc, "Monto", monto, "BoletaInfo","totalnum",new Numero_a_Letra().Convertir(monto, true));
//                            System.out.println("Infotel");
            }else{
                            control.impresor.ImpresionBoletaProductos("idventa", idVenta, "dia", dia, "mes", mes, "anio", anio,
                    "cliente", cliente, "direc", direc, "Monto", monto, "BoletaICE","totalnum",new Numero_a_Letra().Convertir(monto, true));
//                            System.out.println("ICe Computer");
            }

        } else {
            if (cbTipoComprobante.getSelectedItem().toString().equals("Factura")) {
                String guiaRemision = "";
                if (JOptionPane.showConfirmDialog(null, "Deseas imprimir Guia de Remisión", "Confirmar por favor", 0) == 0) {

                    control.Sql = "call ElComprobante('0','" + Controlador.sede + "','Guia de Remision','Productos','0','1')";
                    guiaRemision = control.DevolverRegistroDto(control.Sql, 1);
                    String idGuiaRemision = control.DevolverRegistroDto(control.Sql, 3);

                    if (guiaRemision.equalsIgnoreCase("Se debe iniciar este tipo de comprobante")) {
                        JOptionPane.showMessageDialog(this, "La numeración para la guía de remisión aún no ha sido inicializada, por lo tanto solo se mostrará la factura");
//                        control.impresor.ImpresionFacturaProductos("idventa", idVenta, "dia", dia, "mes", mes, "anio",
//                                anio, "cliente", cliente, "dir", direc, "identi", idencte, "guia", guiaRemision, "monletras", monlt, "vlrvta", subTotal, "igv",
//                                igv, "total", monto, "FacturaInfotel");//Imprimir la Guia de Remision
                        if(Controlador.sede.equals("Infotel")){
                            control.impresor.ImpresionFacturaProductos("idventa", idVenta, "dia", dia, "mes", mes, "anio",
                            anio, "cliente", cliente, "dir", direc, "identi", idencte, "guia", guiaRemision, "monletras", monlt, "vlrvta", subTotal, "igv",
                            igv, "total", monto, "FacturaInfotel","","");//imprime factura infotel
                        }else{
                            control.impresor.ImpresionFacturaProductos("idventa", idVenta, "dia", dia, "mes", mes, "anio",
                            anio, "cliente", cliente, "dir", direc, "identi", idencte, "guia", guiaRemision, "monletras", monlt, "vlrvta", subTotal, "igv",
                            igv, "total", monto, "FacturaIce","","");//Imprime factura de ICE
                        }
                    } else {

                        control.Sql = "update comprobantes set esta='Emitido' where idComprobantes='" + idGuiaRemision + "'";
                        control.EditarRegistro(control.Sql);
                        FrmGenerarGuiaRemision generarGuiaRemision = new FrmGenerarGuiaRemision(null, true);
                        generarGuiaRemision.setLocationRelativeTo(this);
                        generarGuiaRemision.setIdVenta(idVenta);
                        generarGuiaRemision.setVisible(true);
                        String idGuiaRemisionGenerado = generarGuiaRemision.getIdGuiaRemision();
                        if (idGuiaRemisionGenerado != null) {
//                            control.ejecutar(String.format("UPDATE venta v SET v.`idGuiaRemision`=%s WHERE v.`idVenta`=%s;", idGuiaRemisionGenerado, idVenta));
                        }
                    }

                } else {
                    if(Controlador.sede.equals("Infotel")){
                            control.impresor.ImpresionFacturaProductos("idventa", idVenta, "dia", dia, "mes", mes, "anio",
                            anio, "cliente", cliente, "dir", direc, "identi", idencte, "guia", guiaRemision, "monletras", monlt, "vlrvta", subTotal, "igv",
                            igv, "total", monto, "FacturaInfotel","","");//Imprimie factura infotel
                    }else{
                            control.impresor.ImpresionFacturaProductos("idventa", idVenta, "dia", dia, "mes", mes, "anio",
                            anio, "cliente", cliente, "dir", direc, "identi", idencte, "guia", guiaRemision, "monletras", monlt, "vlrvta", subTotal, "igv",
                            igv, "total", monto, "FacturaIce","","");//Imprime factura Ice
                    }
                    
                }

            }
        }
        cbTipoComprobante.setSelectedIndex(-1);
        idComprobanteGenerado = -1;
        lbSerie.setText("");
        lbTotalVenta.setText("0.00");
    }
/*
  public void Verificartipo() {
      if ((!rbContado.isSelected()) && (!rbCredito.isSelected())) {
            JOptionPane.showMessageDialog(this, "Tiene que seleccionar la modalidad de venta", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (rbContado.isSelected()) {
            if (cbTipoComprobante.getSelectedIndex() > -1 && lbSerie.getText().trim().length() > 0) {
                if (verificarNumero()) {
                    RegistrarVenta();
                } else {
                    JOptionPane.showMessageDialog(null, "El Numero del Comprobante se repite!! o \n No esta ingresando La Cantidad de Digitos del Comprobante \nIngrese todos los Numeros Incluido los Ceros!!!!!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione un tipo de comprobante y Genere Uno!!!");
            }
        } else {
            if (rbCredito.isSelected()) {
                if (cbTipoComprobante.getSelectedIndex() > -1 && lbSerie.getText().trim().length() > 0) {
                    if (verificarNumero()) {
                        VentaCredito();
                    } else {
                        JOptionPane.showMessageDialog(null, "El Numero del Comprobante se repite!! o \n No esta ingresando La Cantidad de Digitos del Comprobante \nIngrese todos los Numeros Incluido los Ceros!!!!!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione un Tipo de comprobante y Genere Uno!!!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione el tipo de venta que va a realizar!!");
            }
        }

    }
*/
   private void verificarTipoPago() {

        if ((!rbContado.isSelected()) && (!rbCredito.isSelected())) {
            JOptionPane.showMessageDialog(this, "Tiene que seleccionar la modalidad de venta", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (rbContado.isSelected()) {
            if (cbTipoComprobante.getSelectedIndex() > -1 && lbSerie.getText().trim().length() > 0) {
                vender();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione un tipo de comprobante y genere Uno!!!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            if (rbCredito.isSelected()) {
                if (cbTipoComprobante.getSelectedIndex() > -1 && lbSerie.getText().trim().length() > 0) {
                    if (verificarNumero()) {
                        VentaCredito();
                    } else {
                        JOptionPane.showMessageDialog(this, "El número del comprobante se repite!! o \n no está ingresando la cantidad de Dígitos del Comprobante \nIngrese todos los Numeros Incluido los Ceros!!!!!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Selecione un tipo de comprobante y genere uno!!!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione el tipo de venta que va a realizar!!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

  public void VentaCredito(){
         if(tProdaVender.getRowCount()>0 && TipoVenta>0){
                control.Sql="SELECT InsertaVentas('Credito','"+info.getPass()+"','"+info.getUsuario()+"')";
               CodVenta=control.DevolverRegistroDto(control.Sql,1);
              double total=Double.parseDouble(lbTotalVenta.getText());
              double montini=Double.parseDouble(txtMontoInicial.getText());
           control.Sql="insert into deuda(montdeu,pgoinici,fecdeud,idVenta) values('"+(total-montini)+"','"+txtMontoInicial.getText()+"','"+control.Formato_Amd(dtFechaPago.getDate()) +"','"+CodVenta+"');";
           control.CrearRegistro(control.Sql);
           control.Sql="select iddeuda from deuda where idventa='"+CodVenta+"' order by iddeuda desc limit 1;";
           String de=control.DevolverRegistroDto(control.Sql,1);
            control.Sql="insert into pagoxdeuda (fecpgxdeu, montpag, idDeuda) values(curdate(),'"+montini+"','"+de+"');";
                                control.CrearRegistro(control.Sql); 
           
           control.Sql="SELECT InsertaComprobante('"+cbTipoComprobante.getSelectedItem().toString()+"','"+CodVenta+"')";
         //  System.out.print(control.Sql);
           CodCompro=control.DevolverRegistroDto(control.Sql,1);
          
                while(control.fila<tProdaVender.getRowCount()){
       PrecioGrabar=Double.parseDouble(tProdaVender.getValueAt(control.fila,4).toString()); 
      CodProductoGrabar=tProdaVender.getValueAt(control.fila,0).toString(); 
       //PrecioP=Double.parseDouble(tProdaVender.getValueAt(control.fila,5).toString()); 
       
       control.Sql="call Venta_Productos_Venta ('"+PrecioGrabar+"','"+CodVenta+"','"+CodProductoGrabar+"');";
       control.CrearRegistro(control.Sql);
       control.fila++;
       ListaProductoPorVendido(CodProductoGrabar, "Vendido");
                }
                control.Sql="update adelanto set estado=1 where idcliente='"+info.getPass()+"';";
           control.EditarRegistro(control.Sql);
        control.LimTabla(modelo2);
            control.LimTabla(modelo);
            control.LimTabla(modelo1);
//            MostrarProducto();
            MostrarCliente();
     //       txtBucarProducto.setText("");
    //        txtBucarProducto.grabFocus();
            control.fila=0;
            controlCliente=0;
            ppp=0;
            //cbTipoComprobante.setSelectedIndex(-1);
     //       Cancelar();
            control.Sql="select count(*) from ddddd where idventa='"+CodVenta+"';";
                   int dat= Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
               if(cbTipoComprobante.getSelectedItem().toString().compareTo("Factura")==0){
                   String dia=Num_Mes(CodVenta);
                   String numero=Num_Letra(CodVenta);
                 //  System.out.print(numero);
                   
//                   control.Sql="select count(*) from impri_comprobante_venta where idventa='"+CodVenta+"';";
//                   int dat= Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
                   switch(dat){
                       case 1:{imprime.ImprimirusConFechasFactura("Factura1C.jasper", CodVenta,dia,numero); break;}
                       case 2:{imprime.ImprimirusConFechasFactura("Factura2C.jasper", CodVenta,dia,numero); break;}
                       case 3:{imprime.ImprimirusConFechasFactura("Factura3C.jasper", CodVenta,dia,numero); break;}
                       case 4:{imprime.ImprimirusConFechasFactura("Factura4C.jasper", CodVenta,dia,numero); break;}
                       case 5:{imprime.ImprimirusConFechasFactura("Factura5C.jasper", CodVenta,dia,numero); break;}
                       case 6:{imprime.ImprimirusConFechasFactura("Factura6C.jasper", CodVenta,dia,numero); break;}
                       case 7:{imprime.ImprimirusConFechasFactura("Factura7C.jasper", CodVenta,dia,numero); break;}
                       case 8:{imprime.ImprimirusConFechasFactura("Factura8C.jasper", CodVenta,dia,numero); break;}
                       case 9:{imprime.ImprimirusConFechasFactura("FacturaC.jasper", CodVenta,dia,numero); break;}
                   }
               }else{
                   if(cbTipoComprobante.getSelectedItem().toString().compareTo("Boleta")==0){
                       String numero=Num_Letra(CodVenta);
                       switch(dat){
                           case 1:{imprime.ImprimirusConFechasBoleta("Boleta1C.jasper", CodVenta,numero);break;}
                           case 2:{imprime.ImprimirusConFechasBoleta("Boleta2C.jasper", CodVenta,numero);break;}
                           case 3:{imprime.ImprimirusConFechasBoleta("Boleta3C.jasper", CodVenta,numero);break;}
                           case 4:{imprime.ImprimirusConFechasBoleta("Boleta4C.jasper", CodVenta,numero);break;}
                           case 5:{imprime.ImprimirusConFechasBoleta("Boleta5C.jasper", CodVenta,numero);break;}
                           case 6:{imprime.ImprimirusConFechasBoleta("Boleta6C.jasper", CodVenta,numero);break;}
                           case 7:{imprime.ImprimirusConFechasBoleta("Boleta7C.jasper", CodVenta,numero);break;}
                           case 8:{imprime.ImprimirusConFechasBoleta("Boleta8C.jasper", CodVenta,numero);break;}
                           case 9:{imprime.ImprimirusConFechasBoleta("BoletaC.jasper", CodVenta,numero);break;}
                       }
                       
                   }else{
                       JOptionPane.showMessageDialog(null, "Venta hecha Correctamente!!!");
                   }
               }
            ppp=0;
            cbTipoComprobante.setSelectedIndex(-1); 
           lbSerie.setText("");
           rbVariable.setSelected(true);
           //  imprime.ImprimirusConFechas("Credito.jasper", CodVenta);
      Cancelar();
                comporbantesss();
         }else{
             JOptionPane.showMessageDialog(null, "Para hacer este tipo de venta usted necesita ingresar: \nProducto \nIngresar el Monto inicial Puede ser:  0.00\nFecha en que va a Cancelar ");
         }
    }
    
        private static boolean band(){
        if ( Math.random() > .5) {
            return true;
        }else{
            return false;
        }
    }
    public String Num_Letra(String codv){
        control.Sql="SELECT mes FROM impri_comprobante_venta where idventa='"+codv+"' group by idventa;";
       int sumto=Integer.parseInt(control.DevolverRegistroDto(control.Sql,1));
        switch(sumto){
            case 1:{
                codv="Enero";break;
            }
            case 2:{codv="Febrero";break;}
            case 3:{codv="Marzo";break;}
            case 4:{codv="Abril";break;}
            case 5:{codv="Mayo";break;}
            case 6:{codv="Junio";break;}
            case 7:{codv="Julio";break;}
            case 8:{codv="Agosto";break;}
            case 9:{codv="Setiembre";break;}
            case 10:{codv="Octubre";break;}
            case 11:{codv="Noviembre";break;}
            case 12:{codv="Diciembre";break;}
                            
                            
        }
        
        return codv;
    }
    public String Num_Mes(String codven){
        control.Sql="SELECT sum(tot) FROM impri_comprobante_venta  where idventa='"+codven+"';";
        String sumto=control.DevolverRegistroDto(control.Sql,1);
        String numer= nume.Convertir(sumto, band());
        
           return numer;
    }
/*    public void RegistrarVenta(){
        
        if(cbTipoComprobante.getSelectedIndex()!=-1 && tProdaVender.getRowCount()>0 && TipoVenta>0 && lbCliente.getText().trim().length()>0){
           control.Sql="SELECT InsertaVentas('Contado','"+info.getPass()+"','"+info.getUsuario()+"')";
           //System.out.print(control.Sql);
           CodVenta=control.DevolverRegistroDto(control.Sql,1);
           
           control.Sql="SELECT InsertaComprobante('"+cbTipoComprobante.getSelectedItem().toString()+"','"+CodVenta+"')";
         //  System.out.print(control.Sql);
           CodCompro=control.DevolverRegistroDto(control.Sql,1);
         
           //System.out.println(control.Sql);
            control.Sql = "call ElComprobante('0','" + Controlador.sede + "','" + cbTipoComprobante.getSelectedItem().toString()
                    + "','Productos','" + idComprobanteGenerado + "','2');";
            
           control.fila = 0;
           while(control.fila<tProdaVender.getRowCount()){
       PrecioGrabar=Double.parseDouble(tProdaVender.getValueAt(control.fila,4).toString()); 
      CodProductoGrabar=tProdaVender.getValueAt(control.fila,0).toString(); 
      
       //PrecioP=Double.parseDouble(tProdaVender.getValueAt(control.fila,5).toString()); 
       
       control.Sql="call Venta_Productos_Venta ('"+PrecioGrabar+"','"+CodVenta+"','"+CodProductoGrabar+"');";
       control.CrearRegistro(control.Sql);
       control.fila++;
      // System.out.print(control.Sql);
       
     ListaProductoPorVendido(CodProductoGrabar, "Vendido");
      } 
           control.Sql="update adelanto set estado=1 where idcliente='"+info.getPass()+"';";
           control.EditarRegistro(control.Sql);
           control.Sql="SELECT idcomprobantes FROM compventa where idventa='"+CodVenta+"';";
           control.Sql="update comprobantes set nume='"+txtNumComprobantes.getText()+"' where idcomprobantes='"+control.DevolverRegistroDto(control.Sql,1)+"';";
           control.EditarRegistro(control.Sql);
           lbSerie.setText(""+control.RecuperaSerie(cbTipoComprobante.getSelectedItem().toString()));
            control.LimTabla(modelo2);
            control.LimTabla(modelo);
            control.LimTabla(modelo1);
//            MostrarProducto();
            MostrarCliente();
        //    txtBucarProducto.setText("");
        //    txtBucarProducto.grabFocus();
            rbVariable.setSelected(true);
            control.fila=0;
            controlCliente=0;
            ppp=0;
            //Cancelar();
            control.Sql="select count(*) from ddddd where idventa='"+CodVenta+"';";
                   int dat= Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
             if(TipoVenta==1){
               if(cbTipoComprobante.getSelectedItem().toString().compareTo("Factura")==0){
                 String dia=Num_Mes(CodVenta);
                   String numero=Num_Letra(CodVenta);
                   //System.out.print(numero);
                   //imprime.ImprimirusConFechasFactura("Factura.jasper", CodVenta,dia,numero);
                   
                   switch(dat){
                       case 1:{imprime.ImprimirusConFechasFactura("Factura1.jasper", CodVenta,dia,numero); break;}
                       case 2:{imprime.ImprimirusConFechasFactura("Factura2.jasper", CodVenta,dia,numero); break;}
                       case 3:{imprime.ImprimirusConFechasFactura("Factura3.jasper", CodVenta,dia,numero); break;}
                       case 4:{imprime.ImprimirusConFechasFactura("Factura4.jasper", CodVenta,dia,numero); break;}
                       case 5:{imprime.ImprimirusConFechasFactura("Factura5.jasper", CodVenta,dia,numero); break;}
                       case 6:{imprime.ImprimirusConFechasFactura("Factura6.jasper", CodVenta,dia,numero); break;}
                       case 7:{imprime.ImprimirusConFechasFactura("Factura7.jasper", CodVenta,dia,numero); break;}  
                       case 8:{imprime.ImprimirusConFechasFactura("Factura8.jasper", CodVenta,dia,numero); break;}
                       case 9:{imprime.ImprimirusConFechasFactura("Factura.jasper", CodVenta,dia,numero); break;}
                   }
               }else{
                   if(cbTipoComprobante.getSelectedItem().toString().compareTo("Boleta")==0){
                           String numero=Num_Letra(CodVenta);
                       switch(dat){
                           case 1:{imprime.ImprimirusConFechasBoleta("Boleta1.jasper", CodVenta,numero);break;}
                           case 2:{imprime.ImprimirusConFechasBoleta("Boleta2.jasper", CodVenta,numero);break;}   
                           case 3:{imprime.ImprimirusConFechasBoleta("Boleta3.jasper", CodVenta,numero);break;}
                           case 4:{imprime.ImprimirusConFechasBoleta("Boleta4.jasper", CodVenta,numero);break;}
                           case 5:{imprime.ImprimirusConFechasBoleta("Boleta5.jasper", CodVenta,numero);break;}
                           case 6:{imprime.ImprimirusConFechasBoleta("Boleta6.jasper", CodVenta,numero);break;}
                           case 7:{imprime.ImprimirusConFechasBoleta("Boleta7.jasper", CodVenta,numero);break;}
                           case 8:{imprime.ImprimirusConFechasBoleta("Boleta8.jasper", CodVenta,numero);break;}
                           case 9:{imprime.ImprimirusConFechasBoleta("Boleta.jasper", CodVenta,numero);break;}
                       }
                   }else{
                       JOptionPane.showMessageDialog(null, "Venta hecha Correctamente!!!");
                   }
               }
            ppp=0;
            cbTipoComprobante.setSelectedIndex(-1); 
           }
         comporbantesss();
         Cancelar();
        }else{
              JOptionPane.showMessageDialog(null, "Faltan datos para Grabar Venta");
        }
    }
  */
    public void RegistrarVenta(){
        
        if(cbTipoComprobante.getSelectedIndex()!=-1 && tProdaVender.getRowCount()>0 && TipoVenta>0 && lbCliente.getText().trim().length()>0){
           control.Sql="SELECT InsertaVentas('Contado','"+info.getPass()+"','"+info.getUsuario()+"')";
           //System.out.print(control.Sql);
           CodVenta=control.DevolverRegistroDto(control.Sql,1);
           
           control.Sql="SELECT InsertaComprobante('"+cbTipoComprobante.getSelectedItem().toString()+"','"+CodVenta+"')";
         //  System.out.print(control.Sql);
           CodCompro=control.DevolverRegistroDto(control.Sql,1);
         
           //System.out.println(control.Sql);
            control.Sql = "call ElComprobante('0','" + Controlador.sede + "','" + cbTipoComprobante.getSelectedItem().toString()
                    + "','Productos','" + idComprobanteGenerado + "','2');";
            
           control.fila = 0;
           while(control.fila<tProdaVender.getRowCount()){
       PrecioGrabar=Double.parseDouble(tProdaVender.getValueAt(control.fila,4).toString()); 
      CodProductoGrabar=tProdaVender.getValueAt(control.fila,0).toString(); 
      cantidadvender=Integer.parseInt(tProdaVender.getValueAt(control.fila,1).toString());
      idCatalogo = control.DevolverRegistroDto("select * from catalogoproducto where nomctlg='" + tProdaVender.getValueAt(control.fila, 2).toString() + "'", 1);

                control.Sql = "insert into detventafacturar(codctlg,cant,prec,idVenta) values('" + idCatalogo + "','" + cantidadvender + "','"
                        + PrecioGrabar + "','" + CodVenta + "')";
                control.CrearRegistro(control.Sql);
                
       //PrecioP=Double.parseDouble(tProdaVender.getValueAt(control.fila,5).toString()); 
       
       control.Sql="call Venta_Productos_Venta ('"+PrecioGrabar+"','"+CodVenta+"','"+CodProductoGrabar+"');";
       control.CrearRegistro(control.Sql);
       control.fila++;
      // System.out.print(control.Sql);
       
     ListaProductoPorVendido(CodProductoGrabar, "Vendido");
      } 
           control.Sql="update adelanto set estado=1 where idcliente='"+info.getPass()+"';";
           control.EditarRegistro(control.Sql);
           control.Sql="SELECT idcomprobantes FROM compventa where idventa='"+CodVenta+"';";
           control.Sql="update comprobantes set nume='"+txtNumComprobantes.getText()+"' where idcomprobantes='"+control.DevolverRegistroDto(control.Sql,1)+"';";
           control.EditarRegistro(control.Sql);
           lbSerie.setText(""+control.RecuperaSerie(cbTipoComprobante.getSelectedItem().toString()));
            control.LimTabla(modelo2);
            control.LimTabla(modelo);
            control.LimTabla(modelo1);
//            MostrarProducto();
            MostrarCliente();
        //    txtBucarProducto.setText("");
        //    txtBucarProducto.grabFocus();
            rbVariable.setSelected(true);
            control.fila=0;
            controlCliente=0;
            ppp=0;
            //Cancelar();
            control.Sql="select count(*) from ddddd where idventa='"+CodVenta+"';";
                   int dat= Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
             if(TipoVenta==1){
               if(cbTipoComprobante.getSelectedItem().toString().compareTo("Factura")==0){
                 String dia=Num_Mes(CodVenta);
                   String numero=Num_Letra(CodVenta);
                   //System.out.print(numero);
                   //imprime.ImprimirusConFechasFactura("Factura.jasper", CodVenta,dia,numero);
                   
                   switch(dat){
                       case 1:{imprime.ImprimirusConFechasFactura("Factura1.jasper", CodVenta,dia,numero); break;}
                       case 2:{imprime.ImprimirusConFechasFactura("Factura2.jasper", CodVenta,dia,numero); break;}
                       case 3:{imprime.ImprimirusConFechasFactura("Factura3.jasper", CodVenta,dia,numero); break;}
                       case 4:{imprime.ImprimirusConFechasFactura("Factura4.jasper", CodVenta,dia,numero); break;}
                       case 5:{imprime.ImprimirusConFechasFactura("Factura5.jasper", CodVenta,dia,numero); break;}
                       case 6:{imprime.ImprimirusConFechasFactura("Factura6.jasper", CodVenta,dia,numero); break;}
                       case 7:{imprime.ImprimirusConFechasFactura("Factura7.jasper", CodVenta,dia,numero); break;}  
                       case 8:{imprime.ImprimirusConFechasFactura("Factura8.jasper", CodVenta,dia,numero); break;}
                       case 9:{imprime.ImprimirusConFechasFactura("Factura.jasper", CodVenta,dia,numero); break;}
                   }
               }else{
                   if(cbTipoComprobante.getSelectedItem().toString().compareTo("Boleta")==0){
                           String numero=Num_Letra(CodVenta);
                       switch(dat){
                           case 1:{imprime.ImprimirusConFechasBoleta("Boleta1.jasper", CodVenta,numero);break;}
                           case 2:{imprime.ImprimirusConFechasBoleta("Boleta2.jasper", CodVenta,numero);break;}   
                           case 3:{imprime.ImprimirusConFechasBoleta("Boleta3.jasper", CodVenta,numero);break;}
                           case 4:{imprime.ImprimirusConFechasBoleta("Boleta4.jasper", CodVenta,numero);break;}
                           case 5:{imprime.ImprimirusConFechasBoleta("Boleta5.jasper", CodVenta,numero);break;}
                           case 6:{imprime.ImprimirusConFechasBoleta("Boleta6.jasper", CodVenta,numero);break;}
                           case 7:{imprime.ImprimirusConFechasBoleta("Boleta7.jasper", CodVenta,numero);break;}
                           case 8:{imprime.ImprimirusConFechasBoleta("Boleta8.jasper", CodVenta,numero);break;}
                           case 9:{imprime.ImprimirusConFechasBoleta("Boleta.jasper", CodVenta,numero);break;}
                       }
                   }else{
                       JOptionPane.showMessageDialog(null, "Venta hecha Correctamente!!!");
                   }
               }
            ppp=0;
            cbTipoComprobante.setSelectedIndex(-1); 
           }
         comporbantesss();
         Cancelar();
        }else{
              JOptionPane.showMessageDialog(null, "Faltan datos para Grabar Venta");
        }
    }
    public void Cancelar(){
     //   ActualizaEsta();
        
        info.setControlCliente(0);
//        for (int i = 0; i < Lista.size(); i++) {
//            Lista.remove(i);
//        }
        lbAdelanto.setText("0.00");
        txtNumComprobantes.setText("");
        lbCliente.setText(" ");
        ppp=0;cbTipoComprobante.setSelectedIndex(-1);
    //    lbProducto.setText(" ");
        lbMontoInicial.setVisible(false);
        txtBucarCliente.setText("");
   //     txtBucarProducto.setText("");
   //     txtCantidad.setText("1");
   //     txtDescuento.setText("0.00");
        txtMontoInicial.setText("0.00");
    //    txtValorVentaFinal.setText("0.00");
  //      txtprecio.setText("0.00");
        lbSerie.setText("");
        totalCompra=0.00;
        controlCliente=0;
        lbTotalVenta.setText(""+totalCompra);
        rbVariable.setSelected(true);
        txtBucarCliente.grabFocus();
        control.LimTabla(modelo2);
        control.LimTabla(modelo1);
        control.LimTabla(modelo);
        MostrarCliente();
   //     MostrarProducto();
        txtMontoInicial.setVisible(false);
        dtFechaPago.setVisible(false);
    }
    
    public void Mensaje(String us){

      control.Sql="SELECT codctlg, nomctlg, nommrc,prec,cnt, prec   FROM impri_cotizacion i where idcotizacion ='"+us+"';";
     control.LlenarJTabla(modelo,control.Sql,6); 
}
    public void ListaProductoPorVendido(String cod,String est){
        control.Sql="update producto set estdo='"+est+"' where idproducto='"+cod+"';";
        control.EditarRegistro(control.Sql);
        control.Sql="select idusuario from usuario where nomusr='"+info.getUsuario()+"'";
        String us=control.DevolverRegistroDto(control.Sql, 1);
        
        if(est.compareTo("Por Vender")==0){
            control.Sql="insert into por_vender values('"+cod+"','"+us+"');";
            control.CrearRegistro(control.Sql);
            
        }else{
            control.Sql="delete from por_vender where idproducto='"+cod+"' and idusuario='"+us+"';";
            control.EliminarRegistro(control.Sql);
        }
        
 //       System.out.print(control.Sql);
    }
    
    public void ActualizaEsta(){
        String codi="";
    //    control.fila=tProdaVender.getRowCount();
        control.fila=0;
       if(tProdaVender.getRowCount()>0){
           control.Sql="select idusuario from usuario where nomusr='"+info.getUsuario()+"'";
        String us=control.DevolverRegistroDto(control.Sql, 1);
       while(control.fila<tProdaVender.getRowCount()){
           codi=modelo2.getValueAt(control.fila, 0).toString();
           control.Sql="update producto set estdo='Disponible' where idproducto='"+codi+"';";
           control.EditarRegistro(control.Sql);
           control.Sql="delete from por_vender where idproducto='"+codi+"' and idusuario='"+us+"';";
            control.EliminarRegistro(control.Sql);
          // System.out.print(control.Sql);
           control.fila++;
        } 
    }
    }
    public boolean verificarcomprobante(String tipo){
        boolean a=false;
       String cont=control.DevolverRegistroDto("select count(*) from comprobantesvta where esta='Activo' and tipcompr='"+tipo+"' ",1);
       int valo=Integer.parseInt(cont);
       if(valo>0){
           a=true;
       }
        return a;
    }
    
    public void comporbantesss(){
       control.Sql = "SELECT * FROM tipocomprobante where idSede='"
                + control.ObtenerDato("Sede", "nomse", Controlador.sede, 1) + "' and facde='Productos'";
        control.LlenarCombo(cbTipoComprobante, control.Sql, 2);
    }
    public void GenerarNuevoComrpobante(){
        if(cbTipoComprobante.getSelectedIndex()>=0){
        control.Sql="call  GeneraComprobante('1','"+cbTipoComprobante.getSelectedItem().toString()+"');";
       //System.out.print(control.Sql);
        String da=control.DevolverRegistroDto(control.Sql, 1);
        StringTokenizer tokenizer = new StringTokenizer(da, "-");
        sr=tokenizer.nextToken();
        srt=tokenizer.nextToken();
       lbSerie.setText(control.DevolverRegistroDto(control.Sql, 1));//lbSerie.setText(dato);
       txtNumComprobantes.setText(srt);
       //System.out.print(control.Sql);
        }else{
            JOptionPane.showMessageDialog(null, "Seleccione un tipo de comprobante!!");
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tClientes = new javax.swing.JTable();
        txtBucarCliente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        bClienete = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tProdaVender = new javax.swing.JTable();
        lbTotalVenta = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bVenta = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        lbAdelanto = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lbCliente = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        rbCredito = new javax.swing.JRadioButton();
        rbContado = new javax.swing.JRadioButton();
        rbVariable = new javax.swing.JRadioButton();
        lbMontoInicial1 = new javax.swing.JLabel();
        lbMontoInicial = new javax.swing.JLabel();
        txtMontoInicial = new javax.swing.JTextField();
        dtFechaPago = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        cbTipoComprobante = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtNumComprobantes = new javax.swing.JTextField();
        lbSerie = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleccionar Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
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

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 600, 100));

        txtBucarCliente.setName("txtBucarCliente"); // NOI18N
        txtBucarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBucarClienteKeyReleased(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBucarClienteKeyPressed(evt);
            }
        });
        jPanel2.add(txtBucarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 25, 310, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Buscar");
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        bClienete.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bClienete.setForeground(new java.awt.Color(0, 51, 102));
        bClienete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Clien list.png"))); // NOI18N
        bClienete.setText("Agregar Cliente");
        bClienete.setName("bClienete"); // NOI18N
        bClienete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bClieneteActionPerformed(evt);
            }
        });
        jPanel2.add(bClienete, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 14, 150, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 160));

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
        tProdaVender.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tProdaVenderMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tProdaVender);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 890, 180));

        lbTotalVenta.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbTotalVenta.setForeground(new java.awt.Color(0, 51, 102));
        lbTotalVenta.setText("0.00");
        lbTotalVenta.setName("lbTotalVenta"); // NOI18N
        getContentPane().add(lbTotalVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 280, 50, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setName("jPanel3"); // NOI18N

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setForeground(new java.awt.Color(0, 51, 102));
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setText("Salir");
        bSalir.setName("bSalir"); // NOI18N
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });

        bVenta.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bVenta.setForeground(new java.awt.Color(0, 51, 102));
        bVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Notes.png"))); // NOI18N
        bVenta.setText("Registrar venta");
        bVenta.setName("bVenta"); // NOI18N
        bVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bVentaActionPerformed(evt);
            }
        });

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 51, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setText("Cancelar");
        bCancelar.setName("bCancelar"); // NOI18N
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 460, 70));

        lbAdelanto.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbAdelanto.setForeground(new java.awt.Color(0, 51, 102));
        lbAdelanto.setText("0.00");
        lbAdelanto.setName("lbAdelanto"); // NOI18N
        getContentPane().add(lbAdelanto, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 280, 100, 20));

        jLabel15.setText("S/.");
        jLabel15.setName("jLabel15"); // NOI18N
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 280, 20, -1));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 51, 102));
        jLabel16.setText("Cliente");
        jLabel16.setName("jLabel16"); // NOI18N
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, -1, -1));

        lbCliente.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbCliente.setText(" ");
        lbCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbCliente.setName("lbCliente"); // NOI18N
        getContentPane().add(lbCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 30, 360, -1));

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 51, 102));
        jLabel17.setText("Total Venta");
        jLabel17.setName("jLabel17"); // NOI18N
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 280, -1, -1));

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 51, 102));
        jLabel18.setText("Monto Adelanto");
        jLabel18.setName("jLabel18"); // NOI18N
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        jLabel19.setText("S/.");
        jLabel19.setName("jLabel19"); // NOI18N
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 280, 20, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setName("jPanel1"); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleccionar producto con stock", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonGroup1.add(rbCredito);
        rbCredito.setForeground(new java.awt.Color(0, 51, 102));
        rbCredito.setText("Credito");
        rbCredito.setName("rbCredito"); // NOI18N
        rbCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCreditoActionPerformed(evt);
            }
        });
        jPanel4.add(rbCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, -1, -1));

        buttonGroup1.add(rbContado);
        rbContado.setForeground(new java.awt.Color(0, 51, 102));
        rbContado.setText("Contado");
        rbContado.setName("rbContado"); // NOI18N
        rbContado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbContadoActionPerformed(evt);
            }
        });
        jPanel4.add(rbContado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        buttonGroup1.add(rbVariable);
        rbVariable.setForeground(new java.awt.Color(0, 51, 102));
        rbVariable.setText("f");
        rbVariable.setName("rbVariable"); // NOI18N
        jPanel4.add(rbVariable, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, -1, -1));

        lbMontoInicial1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbMontoInicial1.setForeground(new java.awt.Color(0, 51, 102));
        lbMontoInicial1.setText("Monto Inicial");
        lbMontoInicial1.setName("lbMontoInicial1"); // NOI18N

        lbMontoInicial.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbMontoInicial.setForeground(new java.awt.Color(0, 51, 102));
        lbMontoInicial.setText("Fecha de Pago");
        lbMontoInicial.setName("lbMontoInicial"); // NOI18N

        txtMontoInicial.setText("0.00");
        txtMontoInicial.setName("txtMontoInicial"); // NOI18N

        dtFechaPago.setForeground(new java.awt.Color(0, 51, 102));
        dtFechaPago.setDateFormatString("yyyy-MM-dd");
        dtFechaPago.setName("dtFechaPago"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbMontoInicial1)
                        .addGap(9, 9, 9)
                        .addComponent(txtMontoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbMontoInicial)
                        .addGap(6, 6, 6)
                        .addComponent(dtFechaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbMontoInicial1)
                    .addComponent(txtMontoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbMontoInicial)
                    .addComponent(dtFechaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 170, 230, 140));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel5.setName("jPanel5"); // NOI18N

        cbTipoComprobante.setForeground(new java.awt.Color(0, 51, 102));
        cbTipoComprobante.setName("cbTipoComprobante"); // NOI18N
        cbTipoComprobante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbTipoComprobanteMouseClicked(evt);
            }
        });
        cbTipoComprobante.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTipoComprobanteItemStateChanged(evt);
            }
        });
        cbTipoComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoComprobanteActionPerformed(evt);
            }
        });
        cbTipoComprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbTipoComprobanteKeyPressed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setText("Generar");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 102));
        jLabel11.setText("Tipo comprobante");
        jLabel11.setName("jLabel11"); // NOI18N

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 102));
        jLabel12.setText("Número");
        jLabel12.setName("jLabel12"); // NOI18N

        txtNumComprobantes.setName("txtNumComprobantes"); // NOI18N
        txtNumComprobantes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNumComprobantesKeyReleased(evt);
            }
        });

        lbSerie.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbSerie.setForeground(new java.awt.Color(0, 51, 102));
        lbSerie.setText(" ");
        lbSerie.setName("lbSerie"); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNumComprobantes)
                            .addComponent(lbSerie, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
                    .addComponent(jLabel11)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(cbTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(lbSerie))
                .addGap(6, 6, 6)
                .addComponent(txtNumComprobantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 170, 260, 140));

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Edit.png"))); // NOI18N
        jButton2.setText("Editar la Lista");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 80, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
ActualizaEsta();
info.setControlCliente(0);
    dispose();
}//GEN-LAST:event_bSalirActionPerformed

    private void tClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tClientesMouseClicked
        
         if(evt.getClickCount()>=2 ){
            SelecionarCliente();
        }
        
    }//GEN-LAST:event_tClientesMouseClicked

    private void txtBucarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBucarClienteKeyReleased
MostrarCliente(); 
    }//GEN-LAST:event_txtBucarClienteKeyReleased

    private void tProdaVenderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProdaVenderMouseClicked
        if(evt.getClickCount()>=2 ){
          double dat=Double.parseDouble(lbTotalVenta.getText());
          double cot=Double.parseDouble(modelo2.getValueAt(tProdaVender.getSelectedRow(), 5).toString());
          
          totalCompra=totalCompra-cot;
          lbTotalVenta.setText(""+totalCompra);
          ListaProductoPorVendido(modelo2.getValueAt(tProdaVender.getSelectedRow(), 0).toString(),"Disponible");
          modelo2.removeRow(tProdaVender.getSelectedRow());
          control.LimTabla(modelo1);
         // MostrarProducto();
          //modelo.getValueAt(tProductosingresar.getSelectedRow(), ctp)
      }
    }//GEN-LAST:event_tProdaVenderMouseClicked

    private void bVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bVentaActionPerformed

       verificarTipoPago();
        
    }//GEN-LAST:event_bVentaActionPerformed

    private void rbContadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbContadoActionPerformed
        TipoVenta=1;
        txtMontoInicial.setVisible(false);
        lbMontoInicial.setVisible(false);
        lbMontoInicial1.setVisible(false);
        dtFechaPago.setVisible(false);
    }//GEN-LAST:event_rbContadoActionPerformed

    private void rbCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCreditoActionPerformed
        TipoVenta=2;
        txtMontoInicial.setVisible(true);
        lbMontoInicial.setVisible(true);
        dtFechaPago.setVisible(true);
        txtMontoInicial.grabFocus();
    }//GEN-LAST:event_rbCreditoActionPerformed

    private void cbTipoComprobanteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTipoComprobanteItemStateChanged
lbSerie.setText(" ");
        txtNumComprobantes.setText("");
        
        if(ppp>0){
   // if(evt.getStateChange()== ItemEvent.SELECTED){
     //   if(cbTipoComprobante.getSelectedIndex()>-1){
//            GenerarNuevoComrpobante();
       // }
   }
        
    
    //    JOptionPane.showMessageDialog(null, "Debe de Inicializar este comprobante!!");
    
//}
   
    }//GEN-LAST:event_cbTipoComprobanteItemStateChanged

    private void cbTipoComprobanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbTipoComprobanteMouseClicked
lbSerie.setText(" ");
        txtNumComprobantes.setText("");
        
        //  ppp++; 
//   if(ppp>0){
//      if(cbTipoComprobante.getSelectedIndex()>-1){
//         GenerarNuevoComrpobante();
//     }}
  // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoComprobanteMouseClicked

    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
       ActualizaEsta();
        Cancelar();
       
    }//GEN-LAST:event_bCancelarActionPerformed

    private void bClieneteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bClieneteActionPerformed
        txtBucarCliente.setText("");
        MostrarCliente();
        LosClientes cli=new LosClientes();
       Menu.jDesktopPane1.add(cli);
       LosClientes.bCrear.doClick();
       LosClientes.txtCliente.grabFocus();
       info.setControl(1);
       cli.toFront();
       cli.setVisible(true);
       
    }//GEN-LAST:event_bClieneteActionPerformed

    private void txtBucarClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBucarClienteKeyPressed
        if(evt.getKeyChar()==10){
            if(tClientes.getRowCount()==0){
                bClienete.doClick();
            }else{
                if(tClientes.getRowCount()==1){
                    tClientes.selectAll();
                    SelecionarCliente();
                //    txtBucarProducto.grabFocus();
                }
            }
        }
    }//GEN-LAST:event_txtBucarClienteKeyPressed

    private void cbTipoComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoComprobanteActionPerformed

        lbSerie.setText(" ");
        txtNumComprobantes.setText("");
        
        //    if(ppp>0){
//      if(cbTipoComprobante.getSelectedIndex()>-1){
//         GenerarNuevoComrpobante();
//     }}
    }//GEN-LAST:event_cbTipoComprobanteActionPerformed

    private void cbTipoComprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbTipoComprobanteKeyPressed

        lbSerie.setText(" ");
        txtNumComprobantes.setText("");
//     if(evt.getKeyChar()==10){
//       if(ppp>0){
//      if(cbTipoComprobante.getSelectedIndex()>-1){
//         GenerarNuevoComrpobante();
//     }}
        
    }//GEN-LAST:event_cbTipoComprobanteKeyPressed

    private void txtNumComprobantesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumComprobantesKeyReleased
    
        //String sr=tokenizer.nextToken();
        //String srt=tokenizer.nextToken();
        lbSerie.setText(sr+"-"+txtNumComprobantes.getText());
        
    }//GEN-LAST:event_txtNumComprobantesKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       //
       // GenerarNuevoComrpobante();
        generarComprobanteVenta();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(lbCliente.getText().trim().length()==0){
            JOptionPane.showMessageDialog(null, "Seleccione un Cliente para editar los producto enviados a ensamblaje!!");
        }else{
            info.setControlEditarEnsamblaje(tProdaVender.getRowCount());
            VentasEnsamblaje venta= new VentasEnsamblaje();
            Menu.jDesktopPane1.add(venta);
            venta.setVisible(true);
            venta.setLocation(50, 50);
            VentasEnsamblaje.lbCliente.setText(lbCliente.getText());
            venta.llenarproducto();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bClienete;
    private javax.swing.JButton bSalir;
    private javax.swing.JButton bVenta;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbTipoComprobante;
    private com.toedter.calendar.JDateChooser dtFechaPago;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbAdelanto;
    public static javax.swing.JLabel lbCliente;
    private javax.swing.JLabel lbMontoInicial;
    private javax.swing.JLabel lbMontoInicial1;
    private javax.swing.JLabel lbSerie;
    public static javax.swing.JLabel lbTotalVenta;
    private javax.swing.JRadioButton rbContado;
    private javax.swing.JRadioButton rbCredito;
    private javax.swing.JRadioButton rbVariable;
    private javax.swing.JTable tClientes;
    public static javax.swing.JTable tProdaVender;
    public static javax.swing.JTextField txtBucarCliente;
    private javax.swing.JTextField txtMontoInicial;
    private javax.swing.JTextField txtNumComprobantes;
    // End of variables declaration//GEN-END:variables
 private void generarComprobanteVenta() {
        if (tProdaVender.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay productos en la lista para facturar", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (cbTipoComprobante.getSelectedIndex() >= 0) {

                control.Sql = "call ElComprobante('0','" + Controlador.sede + "','"
                        + cbTipoComprobante.getSelectedItem().toString() + "','Productos','0','1')";
                if (control.DevolverRegistroDto(control.Sql, 1).equals("Se debe iniciar este tipo de comprobante")) {
                    JOptionPane.showMessageDialog(cbTipoComprobante, "No hay documentos de ese tipo, por favor primer incie la numeración del documento", "Mensaje", JOptionPane.WARNING_MESSAGE);

                    IniciandoComprobantesICECompured inicdoc = new IniciandoComprobantesICECompured();
                    Menu.jDesktopPane1.add(inicdoc);
                    inicdoc.toFront();
                    inicdoc.setLocation(250, 250);
                    inicdoc.setVisible(true);
                } else {
                    txtNumComprobantes.setText(control.DevolverRegistroDto(control.Sql, 1));
                    lbSerie.setText(control.DevolverRegistroDto(control.Sql, 2));
                    idComprobanteGenerado = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 3));
                }
            } else {
                JOptionPane.showMessageDialog(this, "Indique el tipo de comprobante a generar", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
