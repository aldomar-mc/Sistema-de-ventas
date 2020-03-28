package Ventanas;

/**
 * ** @author Ing. Miguel Angel Silva Zapata.   *********
 */
import javax.swing.*;
import Clases.*;
import static Ventanas.Ventas.tProductos;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle.Control;
import java.util.StringTokenizer;

public class VentasEnsamblaje extends javax.swing.JInternalFrame {

    /**
     * ***********************Atributos*********************
     */
    Mimodelo modelo = new Mimodelo();
    Mimodelo modelo1 = new Mimodelo();
    Mimodelo modelo2 = new Mimodelo();
    InfoGeneral info = new InfoGeneral();
private    String sr = "";
private    String srt = "",id="",codctl="";
    private String stockProducto,idProducto,nombreProducto,marcaProducto;
    Controlador control = new Controlador();
    Numero_a_Letra nume = new Numero_a_Letra();
    //  InfoGeneral info= new InfoGeneral();
    IMPRIMIR imprime = new IMPRIMIR();
    private DecimalFormat forma = new DecimalFormat("0.00");
    private double totalVenta = 0, precioGrabar;
    public String codCliente = "", codProducto, Marca, CodVenta, CodCompro, Modalidad, CodProductoGrabar;
    public int controlCliente = 0, TipoVenta = 0, ppp = 0,controlad=0;
    double totalCompra = 0, PrecioGrabar,preciosugerido;
    ArrayList<String> Lista = new ArrayList<String>();
    //   <String> nombreArrayList = new ArrayList<String>();
    String datos[] = new String[6];
    private String datosProducto[] = new String[6];

    /**
     * ***********************Atributos************************
     */
    public VentasEnsamblaje() {
        initComponents();
        setTitle("Enviar Productos Ensamblados");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tClientes.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"Código", "Cliente", "Dirección", "Ruc/Dni"});
        tClientes.getColumnModel().getColumn(0).setMaxWidth(0);
        tClientes.getColumnModel().getColumn(0).setMinWidth(0);
        tClientes.getColumnModel().getColumn(0).setPreferredWidth(0);
        tClientes.getColumnModel().getColumn(1).setPreferredWidth(250);//SELECT idproducto, nomctlg,seri,codbrr, nommrc,precVenta  FROM producto_venta
        tProductos.setModel(modelo1);
        modelo1.setColumnIdentifiers(new String[]{"Código", "Producto", "Serie", "Codigo", "Marca", "Precio Venta"});
        tProductos.getColumnModel().getColumn(1).setPreferredWidth(250);
        tProductos.getColumnModel().getColumn(2).setPreferredWidth(150);
        tProductos.getColumnModel().getColumn(3).setPreferredWidth(150);
        tProductos.getColumnModel().getColumn(4).setPreferredWidth(100);
        tProductos.getColumnModel().getColumn(5).setPreferredWidth(100);
        tProductos.getColumnModel().getColumn(0).setMaxWidth(0);
        tProductos.getColumnModel().getColumn(0).setMinWidth(0);
        tProductos.getColumnModel().getColumn(0).setPreferredWidth(0);
        tProdaVender.setModel(modelo2);
        modelo2.setColumnIdentifiers(new String[]{"Código", "Producto", "Marca", "Precio", "Cantidad", "Importe"});
        tProdaVender.getColumnModel().getColumn(0).setPreferredWidth(50);
        tProdaVender.getColumnModel().getColumn(1).setPreferredWidth(250);
        tProdaVender.getColumnModel().getColumn(2).setPreferredWidth(100);
        bSalir.setMnemonic('s');
        //Bcotizacion.setMnemonic('t');
        bVenta.setMnemonic('v');
        bCancelar.setMnemonic('c');
        //jButton1.setMnemonic('g');
        txtBucarCliente.grabFocus();
        lblStock.setText("");
        //comporbantesss();
        MostrarCliente();
        MostrarProducto();
        control.forma_table_ver(modelo, tClientes);
        control.forma_table_ver(modelo1, tProductos);
        control.forma_table_ver(modelo2, tProdaVender);
    }
 private void mostrarStockProducto() {
        if (tProductos.getSelectedRow() >= 0) {
            control.Sql = "SELECT * FROM vta_productosstock where nomctlg='"
                    + tProductos.getValueAt(tProductos.getSelectedRow(), 1).toString() + "'";
            stockProducto = control.DevolverRegistroDto(control.Sql, 1);
            lblStock.setText("Stock = " + control.DevolverRegistroDto(control.Sql, 1));
        }
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
        //control.Sql="update ";    
        //control.EditarRegistro(control.Sql);
            
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
//    public void llenarcbo(){
//    control.LlenarCombo(cbTipoComprobante, "SELECT * FROM tipocomprobante t", 2);
//}

    public void MostrarCliente() {
        BuscarCliente();
    }

    public void BuscarCliente() {
        control.Sql = "SELECT idcliente, nomclie,dir, numident FROM vta_cliente v where nomclie like'"
                + txtBucarCliente.getText() + "%' or numident like'"
                + txtBucarCliente.getText() + "%'";
        control.LlenarJTabla(modelo, control.Sql, 4);

        //    JOptionPane.showMessageDialog(null,control.Sql);
    }

    public void MostrarProducto() {
        BuscarProducto();
    }

    public void BuscarProducto() {
        //SELECT idproducto, nomctlg,seri,codbrr, nommrc,precVenta  FROM producto_venta
//        control.Sql = "select idproducto, nomctlg, seri, codbrr, nommrc, precVenta  FROM producto_venta where estdo='Disponible' and (nomctlg like'"
//                + txtBucarProducto.getText() + "%' or seri like '" + txtBucarProducto.getText()
//                + "%' or codbrr like '" + txtBucarProducto.getText() + "%') and idproducto not in(select Producto_idProducto from venta_producto);";
//
//        control.LlenarJTabla(modelo1, control.Sql, 6);
        
        
   control.Sql = "select idproducto, nomctlg, seri, codbrr, nommrc, precVenta  "
                + "FROM producto_venta where (estdo='Disponible' and Sede='" + Controlador.sede + "') "
                + "and idproducto not in(select Producto_idProducto from venta_producto) "
                + "and(nomctlg like '%" + txtBucarProducto.getText() + "%' or seri like '%" + txtBucarProducto.getText() + "%' "
                + "or codbrr like '%" + txtBucarProducto.getText() + "%' or nommrc like '%" + txtBucarProducto.getText() + "%') " + (chAgrupar.isSelected() ? " group by nomctlg " : "") + " order by nomctlg ;";
        control.LlenarJTabla(modelo1, control.Sql, 6);
        lblStock.setText("");

        //    JOptionPane.showMessageDialog(null,control.Sql);
    }
//    public void verficarcantifdad(){
//        if(Lista.size()<10){
//            if(tProdaVender.getRowCount()==0){
//            Lista.add(tProductos.getValueAt(tProductos.getSelectedRow(), 1).toString());
//            //System.out.print(tProductos.getValueAt(tProductos.getSelectedRow(), 1).toString());
//        }else{
//            String pro=tProductos.getValueAt(tProductos.getSelectedRow(), 1).toString();
//             boolean s=false;
//               for(int a=0; a<Lista.size();a++){
//                  if(Lista.get(a).compareTo(pro)==0){
//                     s=true;
//                    }
//                }
//                if(s==false){
//                  Lista.add(pro);
//              //    System.out.print(pro);
//                }
//            }
//        }else{
//            JOptionPane.showMessageDialog(null, "Ya tiene Productos para emitir un Comprobante!!");
//        }
//        
//    }

    public void SelecionarCliente() {
        //Cancelar();
        control.LimTabla(modelo2);
        lbCliente.setText("");
        lbProducto.setText(" ");
        lbTotalVenta.setText("0.00");
        totalCompra = 0;
        //montototal=0.00;
        //lbMontoFinal.setText(""+montototal);
        lbCliente.setText(modelo.getValueAt(tClientes.getSelectedRow(), 1).toString());
        info.setPass(modelo.getValueAt(tClientes.getSelectedRow(), 0).toString());
        // codCliente=info.getPass();
        controlCliente++;
        txtBucarProducto.grabFocus();
        //cbTipoComprobante.setSelectedIndex(-1);
        //ppp=0;
        //lbSerie.setText("");
    }
   
    public void llenarproducto(){
        control.Sql="select idproducto, nomctlg,  nommrc, precVenta ,count(nomctlg)as cantidad ,(precVenta*(count(nomctlg))) FROM producto_venta where idproducto in (select idproducto from productoensamblaje where idcliente='"+info.getPass()+"') group by nomctlg;";
        control.LlenarJTabla(modelo2, control.Sql, 6);
        control.Sql="select sum(precVenta) FROM producto_venta where idproducto in (select idproducto from productoensamblaje where idcliente='"+info.getPass()+"');";
        lbTotalVenta.setText(control.DevolverRegistroDto(control.Sql, 1));
        //control.Sql="SELECT monto FROM adelanto where idcliente='"+info.getPass()+"' and estado=0 order by idadelanto desc;";
//        lbAdelanto.setText(control.DevolverRegistroDto(control.Sql, 1));
    }
    
    public boolean VerificaEstado(String cod) {
        boolean a = false;
        if (control.Verificarconsulta("select * from producto where idproducto='" + cod + "' and estdo='Disponible';")) {
            a = true;
        }
        return a;
    }

    public void SelecionarProducto() {
//        if (tProductos.getSelectedRowCount() == 1) {
//            codProducto = tProductos.getValueAt(tProductos.getSelectedRow(), 0).toString();
//            //System.out.println(codProducto);
//            if (control.Verificarconsulta("select * from producto where idproducto='" + codProducto + "' and estdo='Disponible';")) {
//                //if(VerificaEstado(codProducto)==false){
//                lbProducto.setText(modelo1.getValueAt(tProductos.getSelectedRow(), 1).toString());
//                txtprecio.setText(modelo1.getValueAt(tProductos.getSelectedRow(), 5).toString());
//                txtValorVentaFinal.setText(modelo1.getValueAt(tProductos.getSelectedRow(), 5).toString());
//                Marca = modelo1.getValueAt(tProductos.getSelectedRow(), 4).toString();
//                //txtDescuento.grabFocus();
//                txtCantidad.grabFocus();
//                
//                //verficarcantifdad();
//            } else {
//                JOptionPane.showMessageDialog(null, "Producto No Disponible, ya Fue seleccionado en otro lugar!!");
//                control.LimTabla(modelo1);
//                MostrarProducto();
//            }
//        }
         if (tProductos.getSelectedRowCount() >= 0) {
            idProducto = tProductos.getValueAt(tProductos.getSelectedRow(), 0).toString();

            if (control.Verificarconsulta("select * from producto where idproducto='" + idProducto + "' and estdo='Disponible';")) {

                control.Sql = "SELECT count(*),cantidad,idproducto FROM productocantidad where idproducto='" + idProducto + "';";
                int cantidad = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
                if (cantidad == 1) {
                    control.Sql = "SELECT cantidad,idproducto FROM productocantidad where idproducto='" + idProducto + "';";
                    int cnt = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
                    lbCantidadMax.setText("La Cantidad Maxima a Vender es de " + cnt);
                    controlad = cnt;
                }
                lbProducto.setText(modelo1.getValueAt(tProductos.getSelectedRow(), 1).toString());
                txtprecio.setText(modelo1.getValueAt(tProductos.getSelectedRow(), 5).toString());
                txtValorVentaFinal.setText(modelo1.getValueAt(tProductos.getSelectedRow(), 5).toString());
                marcaProducto = modelo1.getValueAt(tProductos.getSelectedRow(), 4).toString();
                txtCantidad.grabFocus();
                txtCantidad.selectAll();
            } else {
                JOptionPane.showMessageDialog(null, "Producto no disponible, ya fue seleccionado en otra terminal!!", "Mensaje", JOptionPane.WARNING_MESSAGE);

                //buscarProducto();
                MostrarProducto();
            }
        }
    }

    public void limpiar() {
        txtCantidad.setText("1");
        txtDescuento.setText("0.00");
        txtValorVentaFinal.setText("0.00");
        txtprecio.setText("0.00");
        lbProducto.setText(" ");
        txtBucarProducto.grabFocus();
    }
//         public boolean verificarNumero(){
//         boolean sss=false;
//         if(txtNumComprobantes.getText().trim().length()>0){
//             control.Sql="call  GeneraComprobante('1','"+cbTipoComprobante.getSelectedItem().toString()+"');";
//             String codCom=control.DevolverRegistroDto(control.Sql, 2);
//             control.Sql="select * from tipocomprobante where tipcompr='"+cbTipoComprobante.getSelectedItem().toString()+"';";                      
//             control.Sql="select count(*) from comprobantes where nume='"+txtNumComprobantes.getText()+"' and idtipocomprobante='"+control.DevolverRegistroDto(control.Sql, 1)+"' and idcomprobantes<>'"+codCom+"';";
//             String dds = control.DevolverRegistroDto(control.Sql, 1);
//             control.Sql="SELECT candig FROM tipocomprobante where tipcompr='"+cbTipoComprobante.getSelectedItem().toString()+"';";
//             int cantid = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
//             if(dds.compareTo("0")==0){
//                 if(txtNumComprobantes.getText().trim().length()==cantid){
//                     sss=true;
//                 }
//             }
//         }
//         return sss;
//     }

    private static boolean band() {
        if (Math.random() > .5) {
            return true;
        } else {
            return false;
        }
    }

    

    public String Num_Mes(String codven) {
        control.Sql = "SELECT sum(tot) FROM impri_comprobante_venta  where idventa='" + codven + "';";
        String sumto = control.DevolverRegistroDto(control.Sql, 1);
        String numer = nume.Convertir(sumto, band());

        return numer;
    }

    public void Cancelar() {
        //   ActualizaEsta();

        info.setControlCliente(0);
//        for (int i = 0; i < Lista.size(); i++) {
//            Lista.remove(i);
//        }
        //   txtNumComprobantes.setText("");
        lbCliente.setText(" ");
        //    ppp=0;cbTipoComprobante.setSelectedIndex(-1);
        lbProducto.setText(" ");
        lbMontoInicial.setVisible(false);
        txtBucarCliente.setText("");
        txtBucarProducto.setText("");
        txtCantidad.setText("1");
        txtDescuento.setText("0.00");
        txtMontoInicial.setText("0.00");
        txtValorVentaFinal.setText("0.00");
        txtprecio.setText("0.00");
//        lbSerie.setText("");
        totalCompra = 0.00;
        controlCliente = 0;
        lbTotalVenta.setText("" + totalCompra);
//        rbVariable.setSelected(true);
        txtBucarCliente.grabFocus();
        control.LimTabla(modelo2);
        control.LimTabla(modelo1);
        control.LimTabla(modelo);
        MostrarCliente();
        MostrarProducto();
      //  txtMontoInicial.setVisible(false);
//        dtFechaPago.setVisible(false);
    }

    public void Mensaje(String us) {

        control.Sql = "SELECT codctlg, nomctlg, nommrc,prec,cnt, prec   FROM impri_cotizacion i where idcotizacion ='" + us + "';";
        control.LlenarJTabla(modelo, control.Sql, 6);
    }

    public void ListaProductoPorVendido(String cod, String est) {
        control.Sql = "update producto set estdo='" + est + "' where idproducto='" + cod + "';";
        System.out.println(control.Sql);
        control.EditarRegistro(control.Sql);
        
        control.Sql = "select idusuario from usuario where nomusr='" + info.getUsuario() + "'";
        System.out.println(control.Sql);
        String us = control.DevolverRegistroDto(control.Sql, 1);
        
        //control.CrearRegistro(control.Sql);
        if(est.compareTo("Disponible")==0){
            control.Sql="delete from productoensamblaje where idcliente='"+info.getPass()+"' and idproducto='"+cod+"';";
            control.EliminarRegistro(control.Sql);
            control.Sql="delete from por_vender where idproducto='"+cod+"' and idusuario='"+us+"';";
            control.EliminarRegistro(control.Sql);
        }else{if(est.compareTo("Por Vender")==0){
            control.Sql="insert into por_vender values('"+cod+"','"+us+"');";
            control.CrearRegistro(control.Sql);
        }
//            control.Sql="insert into productoensamblaje (idcliente, idproducto)values('"+info.getPass()+"','"+cod+"');";
//            control.CrearRegistro(control.Sql);

        }
    }

    public void ActualizaEsta() {
        String codi = "";
        //    control.fila=tProdaVender.getRowCount();
        control.fila = 0;
        if (tProdaVender.getRowCount() > 0) {
            control.Sql = "select idusuario from usuario where nomusr='" + info.getUsuario() + "'";
            String us = control.DevolverRegistroDto(control.Sql, 1);
            while (control.fila < tProdaVender.getRowCount()) {
                codi = modelo2.getValueAt(control.fila, 0).toString();
                control.Sql = "update producto set estdo='Disponible' where idproducto='" + codi + "';";
                control.EditarRegistro(control.Sql);
                control.Sql = "delete from por_vender where idproducto='" + codi + "' and idusuario='" + us + "';";
                control.EliminarRegistro(control.Sql);
                control.Sql="delete from productoensamblaje where idcliente='"+info.getPass()+"' and idproducto='"+codi+"';";
            control.EliminarRegistro(control.Sql);
                // System.out.print(control.Sql);
                control.fila++;
            }
        }
    }
    
  public void Actu() {
        String codi = "";
        //    control.fila=tProdaVender.getRowCount();
        control.fila = info.getControlEditarEnsamblaje();
        if (tProdaVender.getRowCount() > 0) {
            control.Sql = "select idusuario from usuario where nomusr='" + info.getUsuario() + "'";
            String us = control.DevolverRegistroDto(control.Sql, 1);
            while (control.fila < tProdaVender.getRowCount()) {
                codi = modelo2.getValueAt(control.fila, 0).toString();
                control.Sql = "update producto set estdo='Disponible' where idproducto='" + codi + "';";
                control.EditarRegistro(control.Sql);
                control.Sql = "delete from por_vender where idproducto='" + codi + "' and idusuario='" + us + "';";
                control.EliminarRegistro(control.Sql);
                control.Sql="delete from productoensamblaje where idcliente='"+info.getPass()+"' and idproducto='"+codi+"';";
            control.EliminarRegistro(control.Sql);
                // System.out.print(control.Sql);
                control.fila++;
            }
        }
    }  

    public boolean verificarcomprobante(String tipo) {
        boolean a = false;
        String cont = control.DevolverRegistroDto("select count(*) from comprobantesvta where esta='Activo' and tipcompr='" + tipo + "' ", 1);
        int valo = Integer.parseInt(cont);
        if (valo > 0) {
            a = true;
        }
        return a;
    }

    public void comporbantesss() {
        try {
            control.Base.Conectar();
            control.Sql = "SELECT * FROM tipocomprobante;";
            control.Base.st = control.Base.conec.createStatement();
            control.Base.rt = control.Base.st.executeQuery(control.Sql);
            while (control.Base.rt.next()) {
                control.Sql = "call  GeneraComprobante('1','" + control.Base.rt.getString(2) + "');";
                control.CrearRegistro(control.Sql);
                //   System.out.println(control.Sql);
            }
        } catch (Exception e) {
        }
    }
//    public void GenerarNuevoComrpobante(){
//        if(cbTipoComprobante.getSelectedIndex()>=0){
//        control.Sql="call  GeneraComprobante('1','"+cbTipoComprobante.getSelectedItem().toString()+"');";
//       System.out.print(control.Sql);
//        String da=control.DevolverRegistroDto(control.Sql, 1);
//        StringTokenizer tokenizer = new StringTokenizer(da, "-");
//        sr=tokenizer.nextToken();
//        srt=tokenizer.nextToken();
//      // lbSerie.setText(control.DevolverRegistroDto(control.Sql, 1));//lbSerie.setText(dato);
//       //txtNumComprobantes.setText(srt);
//       //System.out.print(control.Sql);
//        }else{
//            JOptionPane.showMessageDialog(null, "Seleccione un tipo de comprobante!!");
//        }
//    }
public void grabarProductosaEnsamblaje(){
    if(lbCliente.getText().trim().length()>0 && tProdaVender.getRowCount()>0){
        
        if(info.getControlEditarEnsamblaje()==0){
            control.Sql="insert into adelanto(monto,idcliente,estado) values('"+txtMontoInicial.getText()+"','"+info.getPass()+"','0');";
        control.CrearRegistro(control.Sql);
        lbMensaje.setText("Productos Enviados a Ensamblaje!!");
       control.Sql="update productoensamblaje set id=1 where idcliente='"+info.getPass()+"' and fecha=curdate();";
       control.EditarRegistro(control.Sql);
//            while(control.fila<tProdaVender.getRowCount()){
//                if(Integer.parseInt(tProdaVender.getValueAt(control.fila, 5).toString())>1){
//                    for(int a=0;a<Integer.parseInt(tProdaVender.getValueAt(control.fila, 5).toString());a++){
//                        codctl=control.DevolverRegistroDto("SELECT codctlg FROM catalogoproducto c "
//                                + "where nomctlg='"+tProdaVender.getValueAt(control.fila, 5).toString()+"' ;", 1);
//                        id=control.DevolverRegistroDto("select idproducto from producto where estdo='Disponible'"
//                                + " and Catalogoproducto_codctlg='"+codctl+"' limit 1;", 1);
//                        control.Sql="update producto set precventa='"+tProdaVender.getValueAt(control.fila, 5).toString()+"'"
//                                + " where idproducto='"+tProdaVender.getValueAt(control.fila, 0)+"';";
//                        control.EditarRegistro(control.Sql);   
//                        
//                    }
//                }else{
//                    control.Sql="update producto set precventa='"+tProdaVender.getValueAt(control.fila, 5)+"' where idproducto='"+tProdaVender.getValueAt(control.fila, 0)+"';";
//                    control.EditarRegistro(control.Sql);   
//                }
//                control.fila++;
//            }
        }else{
            control.fila=info.getControlEditarEnsamblaje();
            lbMensaje.setText("Productos Enviados a Ensamblaje!!");
            while(control.fila<tProdaVender.getRowCount()){
                
                control.Sql="update producto set precventa='"+tProdaVender.getValueAt(control.fila, 5)+"' "
                        + "where idproducto='"+tProdaVender.getValueAt(control.fila, 0)+"';";
                control.EditarRegistro(control.Sql);
                control.fila++;
            }
        }
        
        Cancelar();
    }else{
        JOptionPane.showMessageDialog(null,"Faltan Datos!!");
    }
}
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
        jLabel4 = new javax.swing.JLabel();
        lblStock = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtprecio = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tProdaVender = new javax.swing.JTable();
        lbTotalVenta = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bVenta = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        chAgrupar = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lbMensaje = new javax.swing.JLabel();
        lbCliente = new javax.swing.JLabel();
        lbMontoInicial = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        txtValorVentaFinal = new javax.swing.JTextField();
        txtMontoInicial = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lbCantidadMax = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbProducto.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbProducto.setForeground(new java.awt.Color(0, 51, 102));
        lbProducto.setText(" ");
        lbProducto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbProducto.setName("lbProducto"); // NOI18N
        getContentPane().add(lbProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 370, 1030, -1));

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleccionar producto con stock", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tProductosMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tProductos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 750, 110));

        txtBucarProducto.setName("txtBucarProducto"); // NOI18N
        txtBucarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBucarProductoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBucarProductoKeyReleased(evt);
            }
        });
        jPanel1.add(txtBucarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 25, 480, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Buscar");
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        lblStock.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblStock.setForeground(new java.awt.Color(0, 51, 102));
        lblStock.setText("Buscar");
        lblStock.setName("lblStock"); // NOI18N
        jPanel1.add(lblStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 180, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 780, 180));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Cantidad");
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 410, -1, -1));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Precio");
        jLabel2.setName("jLabel2"); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, -1, -1));

        txtprecio.setText("0.00");
        txtprecio.setName("txtprecio"); // NOI18N
        txtprecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtprecioActionPerformed(evt);
            }
        });
        getContentPane().add(txtprecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 410, 80, -1));

        txtCantidad.setText("1");
        txtCantidad.setName("txtCantidad"); // NOI18N
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCantidadKeyReleased(evt);
            }
        });
        getContentPane().add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 410, 70, -1));

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

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 1110, 170));

        lbTotalVenta.setForeground(new java.awt.Color(0, 51, 102));
        lbTotalVenta.setText("0.00");
        lbTotalVenta.setName("lbTotalVenta"); // NOI18N
        getContentPane().add(lbTotalVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 410, 50, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Valor venta");
        jLabel6.setName("jLabel6"); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 410, -1, -1));

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
        bVenta.setText("Registrar");
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

        chAgrupar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        chAgrupar.setMnemonic('A');
        chAgrupar.setText("Agrupar");
        chAgrupar.setName("chAgrupar"); // NOI18N
        chAgrupar.setOpaque(false);
        chAgrupar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chAgruparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(bCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(bSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(chAgrupar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chAgrupar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 190, 250, 170));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 102));
        jLabel10.setText("Producto:");
        jLabel10.setName("jLabel10"); // NOI18N
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, -1, -1));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 51, 102));
        jLabel14.setText("Total Venta");
        jLabel14.setName("jLabel14"); // NOI18N
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 410, -1, -1));

        jLabel15.setText("S/.");
        jLabel15.setName("jLabel15"); // NOI18N
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 410, 20, -1));

        lbMensaje.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbMensaje.setForeground(new java.awt.Color(0, 51, 102));
        lbMensaje.setText(" ");
        lbMensaje.setName("lbMensaje"); // NOI18N
        getContentPane().add(lbMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 84, 480, 20));

        lbCliente.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbCliente.setText(" ");
        lbCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbCliente.setName("lbCliente"); // NOI18N
        getContentPane().add(lbCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 30, 500, -1));

        lbMontoInicial.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbMontoInicial.setForeground(new java.awt.Color(0, 51, 102));
        lbMontoInicial.setText("Adelanto");
        lbMontoInicial.setName("lbMontoInicial"); // NOI18N
        getContentPane().add(lbMontoInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 130, -1, -1));

        txtDescuento.setText("0.00");
        txtDescuento.setName("txtDescuento"); // NOI18N
        txtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyReleased(evt);
            }
        });
        getContentPane().add(txtDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 410, 70, -1));

        txtValorVentaFinal.setText("0.00");
        txtValorVentaFinal.setName("txtValorVentaFinal"); // NOI18N
        txtValorVentaFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorVentaFinalActionPerformed(evt);
            }
        });
        txtValorVentaFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValorVentaFinalKeyPressed(evt);
            }
        });
        getContentPane().add(txtValorVentaFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 410, 70, -1));

        txtMontoInicial.setText("0.00");
        txtMontoInicial.setName("txtMontoInicial"); // NOI18N
        getContentPane().add(txtMontoInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 130, 70, -1));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Descuento");
        jLabel8.setName("jLabel8"); // NOI18N
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 410, -1, -1));

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 51, 102));
        jLabel17.setText("Cliente");
        jLabel17.setName("jLabel17"); // NOI18N
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, -1, -1));

        lbCantidadMax.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbCantidadMax.setForeground(new java.awt.Color(0, 51, 102));
        lbCantidadMax.setText(" ");
        lbCantidadMax.setName("lbCantidadMax"); // NOI18N
        getContentPane().add(lbCantidadMax, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
   if(info.getControlEditarEnsamblaje()==0){
       ActualizaEsta();
    info.setControlCliente(0);
    dispose();
   }else{
       Actu();
   dispose();
   }
}//GEN-LAST:event_bSalirActionPerformed

    private void tClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tClientesMouseClicked

        if (evt.getClickCount() >= 2) {
            SelecionarCliente();
        }

    }//GEN-LAST:event_tClientesMouseClicked

    private void txtBucarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBucarClienteKeyReleased
        MostrarCliente();
    }//GEN-LAST:event_txtBucarClienteKeyReleased

    private void txtBucarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBucarProductoKeyReleased

        MostrarProducto();

    }//GEN-LAST:event_txtBucarProductoKeyReleased

    private void tProductosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProductosMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tProductosMouseEntered

    private void tProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProductosMouseClicked
        if (evt.getClickCount() >= 2) {
            mostrarStockProducto();
            if (controlCliente > 0 || info.getControlCliente() == 1 || lbCliente.getText().trim().length()>0) {
                // System.out.println(controlCliente+"ver ver");
                
                SelecionarProducto();
            } else {
                // System.out.println(controlCliente);
                JOptionPane.showMessageDialog(null, "Tiene que Selecionar un Cliente");
            }
        }
    }//GEN-LAST:event_tProductosMouseClicked

    private void txtprecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtprecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtprecioActionPerformed

    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
//        if (evt.getKeyChar() == 10 && codProducto.trim().length() > 0) {
//
//            double pre = Double.parseDouble(txtprecio.getText());
//
//            double des = Double.parseDouble(txtDescuento.getText());
//            int cant = Integer.parseInt(txtCantidad.getText());
//            totalCompra = ((pre * cant) - des);
//            txtValorVentaFinal.setText("" + totalCompra);
//            //txtPrecioFinal.setText(""+((pre*cant)-(des)));
//            txtDescuento.grabFocus();
//        }
        calcularMontos(evt);

    }//GEN-LAST:event_txtCantidadKeyPressed

    private void txtDescuentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyPressed
//        if (evt.getKeyChar() == 10 && codProducto.trim().length() > 0) {
//            double pre = Double.parseDouble(txtprecio.getText());
//            double des = Double.parseDouble(txtDescuento.getText());
//            int cant = Integer.parseInt(txtCantidad.getText());
//            totalCompra = ((pre * cant) - des);
//            txtValorVentaFinal.setText("" + totalCompra);
//            txtValorVentaFinal.grabFocus();
//
//        }

HacerDescuento(evt);

    }//GEN-LAST:event_txtDescuentoKeyPressed

    private void txtBucarProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBucarProductoKeyPressed

        if (evt.getKeyChar() == 10) {

            if (tProductos.getRowCount() == 1) {
                tProductos.selectAll();
                SelecionarProducto();
            }
        }

    }//GEN-LAST:event_txtBucarProductoKeyPressed

    private void txtValorVentaFinalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorVentaFinalKeyPressed

//        if (evt.getKeyChar() == 10) {
//            if (txtCantidad.getText().trim().length() > 0 && txtDescuento.getText().trim().length() > 0
//                    && txtprecio.getText().trim().length() > 0 && txtValorVentaFinal.getText().trim().length() > 0 && codProducto.trim().length() > 0) {
//                datos[0] = codProducto;
//                datos[1] = lbProducto.getText();
//                datos[2] = Marca;
//                datos[3] = txtValorVentaFinal.getText();
//                datos[4] = txtCantidad.getText();
//                datos[5] = ""+Double.parseDouble(txtValorVentaFinal.getText())*Integer.parseInt(txtCantidad.getText());
//                modelo2.addRow(datos);
//                totalCompra = Double.parseDouble(lbTotalVenta.getText());
//                totalCompra = totalCompra + (Double.parseDouble(txtValorVentaFinal.getText())*Integer.parseInt(txtCantidad.getText()));
//                lbTotalVenta.setText("" + totalCompra);
//                lbProducto.setText(" ");
//                txtBucarProducto.setText("");
//                txtBucarProducto.grabFocus();
//                
//                ListaProductoPorVendido(codProducto, "Por Ensamblar");
//                codProducto = "";
//                limpiar();
//                //verficarcantifdad();
//            } else {
//                JOptionPane.showMessageDialog(null, "Faltan Datos para Agregar a la Lista de Venta");
//            }
//        }
 if (KeyEvent.VK_ENTER == evt.getKeyCode()) {
            agregarListaProducto(evt);
        } else {
            evt.consume();
        }

    }//GEN-LAST:event_txtValorVentaFinalKeyPressed

    private void tProdaVenderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProdaVenderMouseClicked
//        if (evt.getClickCount() >= 2) {
//            if(info.getControlEditarEnsamblaje()==0){
//                double dat = Double.parseDouble(lbTotalVenta.getText());
//                double cot = Double.parseDouble(modelo2.getValueAt(tProdaVender.getSelectedRow(), 5).toString());
//                totalCompra = totalCompra - cot;
//                lbTotalVenta.setText("" + totalCompra);
//                ListaProductoPorVendido(modelo2.getValueAt(tProdaVender.getSelectedRow(), 0).toString(), "Disponible");
//                modelo2.removeRow(tProdaVender.getSelectedRow());
//                control.LimTabla(modelo1);
//                MostrarProducto();
//        }else{
//                if(tProdaVender.getSelectedRow()>info.getControlEditarEnsamblaje()-1){
//                     double dat = Double.parseDouble(lbTotalVenta.getText());
//                double cot = Double.parseDouble(modelo2.getValueAt(tProdaVender.getSelectedRow(), 5).toString());
//                totalCompra = totalCompra - cot;
//                lbTotalVenta.setText("" + totalCompra);
//                ListaProductoPorVendido(modelo2.getValueAt(tProdaVender.getSelectedRow(), 0).toString(), "Disponible");
//                modelo2.removeRow(tProdaVender.getSelectedRow());
//                control.LimTabla(modelo1);
//                MostrarProducto();
//                }else{
//                    JOptionPane.showMessageDialog(null, "No Puede Eliminar producto ya enviado a almacen!!");
//                }
//            }
//        }
        if(info.getControlEditarEnsamblaje()==0){
            quitarProductoLista(evt);
        }else{
            if(tProdaVender.getSelectedRow()>info.getControlEditarEnsamblaje()-1){
                     double dat = Double.parseDouble(lbTotalVenta.getText());
                double cot = Double.parseDouble(modelo2.getValueAt(tProdaVender.getSelectedRow(), 5).toString());
                totalCompra = totalCompra - cot;
                lbTotalVenta.setText("" + totalCompra);
                ListaProductoPorVendido(modelo2.getValueAt(tProdaVender.getSelectedRow(), 0).toString(), "Disponible");
                modelo2.removeRow(tProdaVender.getSelectedRow());
                control.LimTabla(modelo1);
                MostrarProducto();
                }else{
                    JOptionPane.showMessageDialog(null, "No Puede Eliminar producto ya enviado a almacen!!");
                }
        }
         
    }//GEN-LAST:event_tProdaVenderMouseClicked

    private void bVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bVentaActionPerformed
        grabarProductosaEnsamblaje();
    }//GEN-LAST:event_bVentaActionPerformed

    private void txtCantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyReleased
        control.Solonumeros(evt);
    }//GEN-LAST:event_txtCantidadKeyReleased

    private void txtDescuentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyReleased
        control.Solonumeros(evt);// TODO add your handling code here:
    }//GEN-LAST:event_txtDescuentoKeyReleased

    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
        
        if(info.getControlEditarEnsamblaje()==0){
             ActualizaEsta();
            Cancelar();
        }else{
            Actu();
            Cancelar();
        }
    }//GEN-LAST:event_bCancelarActionPerformed

    private void bClieneteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bClieneteActionPerformed
        txtBucarCliente.setText("");
        MostrarCliente();
        LosClientes cli = new LosClientes();
        Menu.jDesktopPane1.add(cli);
        LosClientes.bCrear.doClick();
        LosClientes.txtCliente.grabFocus();
        info.setControl(1);
        cli.toFront();
        cli.setVisible(true);

    }//GEN-LAST:event_bClieneteActionPerformed

    private void txtBucarClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBucarClienteKeyPressed
        if (evt.getKeyChar() == 10) {
            if (tClientes.getRowCount() == 0) {
                bClienete.doClick();
            } else {
                if (tClientes.getRowCount() == 1) {
                    tClientes.selectAll();
                    SelecionarCliente();
                    txtBucarProducto.grabFocus();
                }
            }
        }
    }//GEN-LAST:event_txtBucarClienteKeyPressed

    private void txtValorVentaFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorVentaFinalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorVentaFinalActionPerformed

    private void chAgruparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chAgruparActionPerformed
        MostrarProducto();
    }//GEN-LAST:event_chAgruparActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bClienete;
    private javax.swing.JButton bSalir;
    private javax.swing.JButton bVenta;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chAgrupar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbCantidadMax;
    public static javax.swing.JLabel lbCliente;
    private javax.swing.JLabel lbMensaje;
    private javax.swing.JLabel lbMontoInicial;
    private javax.swing.JLabel lbProducto;
    public static javax.swing.JLabel lbTotalVenta;
    private javax.swing.JLabel lblStock;
    private javax.swing.JTable tClientes;
    public static javax.swing.JTable tProdaVender;
    public static javax.swing.JTable tProductos;
    public static javax.swing.JTextField txtBucarCliente;
    private javax.swing.JTextField txtBucarProducto;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtMontoInicial;
    private javax.swing.JTextField txtValorVentaFinal;
    private javax.swing.JTextField txtprecio;
    // End of variables declaration//GEN-END:variables

  private void quitarProductoLista(MouseEvent e) {
        if (e.getClickCount() >= 2) {
            control.fila = tProdaVender.getSelectedRow();

            double importeProducto = Double.parseDouble(modelo2.getValueAt(control.fila, 5).toString());
            totalVenta = totalVenta - importeProducto;
            lbTotalVenta.setText("" + totalVenta);
            int cantidadEstablecida = Integer.parseInt(modelo2.getValueAt(control.fila, 4).toString());
            if (cantidadEstablecida > 1) {
                nombreProducto = modelo2.getValueAt(control.fila, 1).toString();
                control.Sql = "call LimpiaPorVender('" + info.getUsuario() + "','" + Controlador.sede
                        + "','" + nombreProducto + "');";
                control.EditarRegistro(control.Sql);
                control.Sql="delete from productoensamblaje where idcliente='"+info.getPass()+"' and nomctlg='"+nombreProducto+"';";
                control.EditarRegistro(control.Sql);
            } else {
                ListaProductoPorVendido(modelo2.getValueAt(tProdaVender.getSelectedRow(), 0).toString(), "Disponible");
                control.Sql="delete from productoensamblaje where idcliente='"+info.getPass()+"' and nomctlg='"+nombreProducto+"';";
                control.EditarRegistro(control.Sql);
            }
            modelo2.removeRow(control.fila);
            //buscarProducto();
            MostrarProducto();
        }
    }    
    
private void calcularMontos(KeyEvent e) {
        if (e.getKeyChar() == 10 && idProducto.trim().length() > 0) {
            double pre = Double.parseDouble(txtprecio.getText());
            double des = Double.parseDouble(txtDescuento.getText());
            int cant = Integer.parseInt(txtCantidad.getText());
            if ((cant > 1) && (cant > Integer.parseInt(stockProducto))) {
                JOptionPane.showMessageDialog(null, "La cantidad a usar no puede ser mayor a lo que existe en Stock");
                txtCantidad.grabFocus();
            } else {
                totalVenta = ((pre * cant) - des);
                txtValorVentaFinal.setText("" + numer(forma.format(totalVenta)));
                txtDescuento.grabFocus();
                txtDescuento.selectAll();
            }
        }
    }

private void HacerDescuento(KeyEvent evt) {
        if (evt.getKeyChar() == 10 && idProducto.trim().length() > 0) {
            int cant = Integer.parseInt(txtCantidad.getText());
            if (Integer.parseInt(stockProducto) < cant) {
                JOptionPane.showMessageDialog(null, "La cantidad a facturar no puede ser mayor a lo que existe en Stock");
                txtCantidad.grabFocus();
            } else {
                double pre = Double.parseDouble(txtprecio.getText());
                double des = Double.parseDouble(txtDescuento.getText());
                totalVenta = ((pre * cant) - des);
                txtValorVentaFinal.setText("" + numer(forma.format(totalVenta)));
                txtValorVentaFinal.grabFocus();
            }
        }
    }
   private void agregarListaProducto(KeyEvent ev) {
        if (ev.getKeyChar() == 10) {
            if (txtCantidad.getText().trim().length() > 0 && txtDescuento.getText().trim().length() > 0
                    && txtprecio.getText().trim().length() > 0 && txtValorVentaFinal.getText().trim().length() > 0 && idProducto.trim().length() > 0) {

                nombreProducto = lbProducto.getText();
               preciosugerido=Double.parseDouble(txtprecio.getText());
                int cantidadSolicitada = Integer.parseInt(txtCantidad.getText());
                if (cantidadSolicitada > Integer.parseInt(stockProducto)) {
                    JOptionPane.showMessageDialog(this, "No hay suficientes productos en stock para cubrir la cantidad solicitada", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                calcularMontos(ev);
                //***********************************Agrega el producto a vender   ******************************************
                datosProducto[0] = idProducto;
                datosProducto[1] = lbProducto.getText();
                datosProducto[2] = marcaProducto;
                datosProducto[3] = txtprecio.getText();
                datosProducto[4] = txtCantidad.getText();
                datosProducto[5] = txtValorVentaFinal.getText();
                modelo2.addRow(datosProducto);
                //*********************************Fin Agrega el producto a vender   ***************************************

                //*******************************Limpia los controles   ****************************************************
                totalVenta = Double.parseDouble(lbTotalVenta.getText());
                totalVenta = totalVenta + (Double.parseDouble(txtValorVentaFinal.getText()));
                lbTotalVenta.setText("" + totalVenta);
                lbProducto.setText(" ");
                txtBucarProducto.setText("");
                txtBucarProducto.grabFocus();
                //********************* Fin Limpia los controles   *********************************************************

                if (controlad == 0) {
                    if (cantidadSolicitada > 1) {
                        for (int fil = 1; fil <= cantidadSolicitada; fil++) {
                            control.Sql = "select * from producto_venta where nomctlg='" + nombreProducto
                                    + "' and estdo='Disponible' and idproducto not in(select Producto_idProducto from venta_producto)order by idproducto  limit 1";
                            idProducto = control.DevolverRegistroDto(control.Sql, 1);
                            control.Sql="insert into productoensamblaje (idcliente, idproducto,nomctlg,precio,id,fecha)"
                                    + "values('"+info.getPass()+"','"+idProducto+"','"+nombreProducto+"','"+preciosugerido+"',0,curdate());";
                            System.out.println(control.Sql);
                            control.CrearRegistro(control.Sql);
                            ListaProductoPorVendido(idProducto, "Por Ensamblar");
                        }
                    } else {
                        control.Sql="insert into productoensamblaje (idcliente, idproducto,nomctlg,precio,id,fecha)"
                                    + "values('"+info.getPass()+"','"+idProducto+"','"+nombreProducto+"','"+preciosugerido+"',0,curdate());";
                            control.CrearRegistro(control.Sql);
                            System.out.println(control.Sql);
                        ListaProductoPorVendido(idProducto, "Por Ensamblar");
                    }
                    nombreProducto = "";
                    limpiar();
                } else {
                    if (cantidadSolicitada <= controlad) {
                        if (cantidadSolicitada > 1) {
                            for (int fil = 1; fil < cantidadSolicitada; fil++) {
                                control.Sql = "select * from producto_venta where nomctlg='" + nombreProducto
                                        + "' and estdo='Disponible' and idproducto not in(select Producto_idProducto from venta_producto)order by idproducto  limit 1";
                                idProducto = control.DevolverRegistroDto(control.Sql, 1);
                                
                                control.Sql="insert into productoensamblaje (idcliente, idproducto,nomctlg,precio,id,fecha)"
                                    + "values('"+info.getPass()+"','"+idProducto+"','"+nombreProducto+"','"+preciosugerido+"',0,curdate());";
                                System.out.println(control.Sql);
                                control.CrearRegistro(control.Sql);
                                ListaProductoPorVendido(idProducto, "Por Ensamblar");
                            }
                        } else {
                            control.Sql="insert into productoensamblaje (idcliente, idproducto,nomctlg,precio,id,fecha)"
                                    + "values('"+info.getPass()+"','"+idProducto+"','"+nombreProducto+"','"+preciosugerido+"',0,curdate());";
                            control.CrearRegistro(control.Sql);
                            System.out.println(control.Sql);
                            ListaProductoPorVendido(idProducto, "Por Ensamblar");
                        }
                        nombreProducto = "";
                        limpiar();
                    } else {
                        JOptionPane.showMessageDialog(null, "No Puede Ingresar una Cantidad mayor a la establecida");
                    }
                }
                idProducto = "";
                //buscarProducto();
                MostrarProducto();
            } else {
                JOptionPane.showMessageDialog(null, "Faltan datos para agregar a la lista de venta");
            }
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
 
}
