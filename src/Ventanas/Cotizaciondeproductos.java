/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

/**
 * ** @author Ing. Miguel Angel Silva Zapata.   *********
 */
import javax.swing.*;
import Clases.*;
//import com.sun.corba.se.spi.orb.OperationFactory;
import java.awt.Event;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableModel;
import org.codehaus.groovy.runtime.callsite.GetEffectivePogoFieldSite;
import org.jvnet.substance.button.StandardButtonShaper;

public class Cotizaciondeproductos extends javax.swing.JInternalFrame {

    private CustomTableModel model = new CustomTableModel() {
        private DecimalFormat format = new DecimalFormat("0.00");

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3 || column == 4;
        }

        @Override
        public void setValueAt(Object aValue, int row, int column) {
            double preuni;
            int cant;
            switch (column) {
                case 3: {
                    preuni = Double.parseDouble(aValue.toString());  //Captura el precio            
                    cant = Integer.parseInt(super.getValueAt(row, 4).toString()); //Captura la cantidad        
                    super.setValueAt((preuni), row, column);
                    super.setValueAt(forma.format((preuni * cant)).replace(",", "."), row, 5);
                    break;
                }
                case 4: {
                    cant = Integer.parseInt(aValue.toString()); //Captura la cantidad           
                    preuni = Double.parseDouble(super.getValueAt(row, 3).toString());  //Captura el precio                     
                    super.setValueAt((cant), row, column);
                    super.setValueAt((forma.format(preuni * cant).replace(",", ".")), row, 5);
                    break;
                }
                default: {
                    super.setValueAt(aValue, row, column);
                    break;
                }
            }
            lbMontoFinal.setText(control.CalcularMontodeJtableDefault(model, 5));
        }

        private void agregarmas(int d) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

    /**
     * ***********************Atributos****************************************
     */
    Mimodelo modelo = new Mimodelo();
    Mimodelo modelo1 = new Mimodelo();
    Mimodelo modelo2 = new Mimodelo();
    Controlador control = new Controlador();
    InfoGeneral info = new InfoGeneral();
    IMPRIMIR imprime = new IMPRIMIR();
    int controlCliente = 0, ContadorProdcuto = 0, CantiP = 0, codPr, ControlREporte = 0;
    private DecimalFormat forma = new DecimalFormat("0.00");
    DecimalFormat formato = new DecimalFormat("00.00");
    String valorFormateado = "";
    double montototal = 0, PrecioP = 0;
    String datos[] = new String[6];
    String dato[] = new String[1];
    double mont1 = 0;
    double mont2 = 0;
    String val1 = "", marca = "";
    private double valor = 0;
    private String stockProducto;
    String codCliente = "", codProducto = "", Marca = "", CodCotiz = "";

    /**
     * ***********************Atributos****************************************
     */

    public Cotizaciondeproductos() {
        initComponents();
        setTitle("Cotización de productos");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tClientes.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"Código",
            "Nombre", "Direccion", "Tipo Cliente", "Tipo Id", "Nª Id"});
        /**
         * ***********************ocultar controles**************************
         */
        jLabel1.setVisible(false);
        jLabel11.setVisible(false);
        jLabel2.setVisible(false);
        jLabel12.setVisible(false);
        txtPrecio.setVisible(false);
        txtDescuento.setVisible(false);
        txtCantidad.setVisible(false);
        txtPrecioFinal.setVisible(false);
        tClientes.getColumnModel().getColumn(1).setPreferredWidth(250);
        tProductos.setModel(modelo1);
        modelo1.setColumnIdentifiers(new String[]{"Código", "Producto", "Marca",
            "Presentación", "Pre_Mayor", "Pre_Mayor"});
        tProductos.getColumnModel().getColumn(2).setPreferredWidth(80);
        tProductos.getColumnModel().getColumn(1).setPreferredWidth(450);
        tProd_Cotizados.setModel(model);
        model.setColumnIdentifiers(new String[]{"Código",
            "Producto", "Marca", "Precio", "Cantidad", "Importe"});
        tProd_Cotizados.getColumnModel().getColumn(1).setPreferredWidth(450);

        jLabel6.setVisible(false);
        MostrarCliente();
        jButton1.setVisible(false);
        MostrarProdutco();
        control.forma_table_ver(modelo, tClientes);
        control.forma_table_ver1(modelo1, tProductos);
        //control.forma_table_ver(modelo2, tProd_Cotizados);jLabel3.setText("");
        control.forma_table_ver1(model, tProd_Cotizados);
        jLabel3.setText("");
    }

    public void MostrarCliente() {
        BuscarCliente();
    }

    public void BuscarCliente() {
        control.Sql = "SELECT idcliente, nomclie,dir,tipo, desident, numident FROM "
                + "vta_cliente v where nomclie like'%" + txtBuscarCliente.getText() + "%' or numident like'%"
                + txtBuscarCliente.getText() + "%'";
        control.LlenarJTabla(modelo, control.Sql, 6);
    }

    public void Cancelar() {
        //control.LimTabla(modelo2);
        control.LimTablaDefault(model);
        info.setControlCliente(0);
        lbCliente.setText(" ");
        lbProducto.setText(" ");
        lbMontoFinal.setText("0.0");
        txtDescuento.setText("0.0");
        txtPrecio.setText("0.00");
        txtCantidad.setText("1");
        txtBuscarCliente.setText("");
        txAtencion.setText("");
        txtBuscarProducto.setText("");
        txtBuscarCliente.grabFocus();
        MostrarCliente();
        MostrarProdutco();
        jLabel3.setText("");
    }

    public void MostrarProdutco() {
        BuscarProducto();
    }

    public void BuscarProducto() {
//   control.Sql="SELECT idproducto, nomctlg,seri,codbrr, nommrc,prexmenor  FROM "
//   + "producto_venta where (nomctlg like'"+txtBuscarProducto.getText()+"%' or seri like'"+
//   txtBuscarProducto.getText()+"%' or codbrr like'"+txtBuscarProducto.getText()+"%') group by nomctlg";

        control.Sql = "select Barra,Producto,Marca,abre,prexmenor,prexmayor "
                + "from vta_catalogo where Producto like'%" + txtBuscarProducto.getText()
                + "%' or Marca like'%" + txtBuscarProducto.getText() + "%' or abre like'%"
                + txtBuscarProducto.getText() + "%' or barra like'%" + txtBuscarProducto.getText()
                + "%'";
        control.LlenarJTabla(modelo1, control.Sql, 6);
    }

    private void mostrarStockProducto() {
        if (tProductos.getSelectedRow() >= 0) {
            control.Sql = " select count(p.catalogoproducto_codctlg) AS stock "
                    + " from producto p , catalogoproducto c , modelo l , marca m , sede s "
                    + " where p.catalogoproducto_codctlg = c.codctlg and p.idSede = s.idSede "
                    + "   and c.idmodelos = l.idmodelos and c.idMarca = m.idMarca and p.estdo = "
                    + "'Disponible' and concat(nommod,' ',c.nomctlg)='" + tProductos.getValueAt(tProductos.getSelectedRow(),
                             1).toString() + "' and s.nomse='" + Controlador.sede + "' and not p.idProducto "
                    + "in (select venta_producto.producto_idProducto AS Producto_idproducto from venta_producto) "
                    + " group by s.nomse ,c.codctlg;";
            stockProducto = control.DevolverRegistroDto(control.Sql, 1);
            jLabel3.setText("Stock = " + control.DevolverRegistroDto(control.Sql, 1));
        }
    }

    public void SelecionarCliente() {
        info.setPass(modelo.getValueAt(tClientes.getSelectedRow(), 0).toString());
        /*control.LimTabla(modelo2);lbCliente.setText(" ");
     
     lbProducto.setText(" ");montototal=0.00;lbMontoFinal.setText(""+montototal);
         */
        lbCliente.setText(modelo.getValueAt(tClientes.getSelectedRow(), 1).toString());
        codCliente = modelo.getValueAt(tClientes.getSelectedRow(), 0).toString();
        controlCliente++;
        txtBuscarProducto.grabFocus();
    }

    public void SelecionarProducto() {
        //if(lbCliente.getText().trim().length()>0){
        lbProducto.setText(modelo1.getValueAt(tProductos.getSelectedRow(), 1).toString());
        txtPrecio.setText(modelo1.getValueAt(tProductos.getSelectedRow(), 5).toString());
        codProducto = modelo1.getValueAt(tProductos.getSelectedRow(), 0).toString();
        marca = modelo1.getValueAt(tProductos.getSelectedRow(), 2).toString();
        Marca = modelo1.getValueAt(tProductos.getSelectedRow(), 4).toString();
        txtCantidad.grabFocus();
        txtCantidad.selectAll();
        //}
        //else{
        //JOptionPane.showMessageDialog(null, "Selecione un Cliente!!");
        MostrarProdutco();
        txtBuscarProducto.setText("");
        txtBuscarCliente.grabFocus();
        //}

    }

    public void Agregando() {
        int cant = 0;
        double impt = 0.0;
        codProducto = tProductos.getValueAt(tProductos.getSelectedRow(), 1).toString();
        int[] Res = control.VerificarenJtabla(codProducto, tProd_Cotizados, 1);
        if (Res[0] == 0) {
            codProducto = tProductos.getValueAt(tProductos.getSelectedRow(), 0).toString();
            control.Data[0] = "" + codProducto;
            control.Data[1] = tProductos.getValueAt(tProductos.getSelectedRow(), 1).toString();
            control.Data[2] = tProductos.getValueAt(tProductos.getSelectedRow(), 2).toString();
            control.Data[3] = tProductos.getValueAt(tProductos.getSelectedRow(), 4).toString();
            control.Data[4] = "1";
            control.Data[5] = forma.format((Integer.parseInt(control.Data[4])
                    * Double.parseDouble(control.Data[3]))).replace(",", ".");
            /*control.Data[5]=Double.toString(Integer.parseInt(control.Data[4])*
       Double.parseDouble(control.Data[3]));*/
            //modelo2.addRow(control.Data);       forma.format(
            model.addRow(control.Data);
        } else {
            cant = Integer.parseInt(tProd_Cotizados.getValueAt(Res[1], 4).toString()) + 1;
            //impt=Double.parseDouble(tProd_Cotizados.getValueAt(Res[1],3).toString().replace(",", "."))*cant;tProd_Cotizados.setValueAt(impt,Res[1],5);
            impt = Double.parseDouble(tProd_Cotizados.getValueAt(Res[1], 3).toString()) * cant;
            tProd_Cotizados.setValueAt(forma.format(impt).replace(",", "."), Res[1], 5);
            tProd_Cotizados.setValueAt(Integer.toString(cant), Res[1], 4);
        }
        //lbMontoFinal.setText(control.CalcularMontodeJtable(modelo2,5));
        lbMontoFinal.setText(control.CalcularMontodeJtableDefault(model, 5));
    }

    public void Pasardirecto() {
        if (control.bandera) {
            if (tProductos.getRowCount() == 1) {
                Agregando();
            }
        } else {
            Agregando();
        }
    }

    public void Pasar() {
        if (modelo1.getRowCount() == 1) {
            tProductos.selectAll();
            Pasardirecto();
            txtBuscarProducto.setText("");
        }
    }

    public void QuitarCotizados() {
        //modelo2.removeRow(tProd_Cotizados.getSelectedRow());
        model.removeRow(tProd_Cotizados.getSelectedRow());
        //lbMontoFinal.setText(control.CalcularMontodeJtable(modelo2,5));
        lbMontoFinal.setText(control.CalcularMontodeJtableDefault(model, 5));
        txtBuscarProducto.grabFocus();
    }

    public void GrabarCotizacion() {
        if (tProd_Cotizados.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "No hay ningun producto cotizado");
            return;
        }
        if (txtDiasHabiles.getText().trim().length() > 0 && lbCliente.getText().trim().length() > 0 && model.getRowCount() > -1) {
            control.Sql = "SELECT InsertaCotizacion('" + info.getPass() + "','"
                    + info.getUsuario() + "','" + txtDiasHabiles.getText() + "')";
            CodCotiz = control.DevolverRegistroDto(control.Sql, 1);
            control.fila = 0;
            while (control.fila < tProd_Cotizados.getRowCount()) {
                control.Sql = "select * from catalogoproducto where codbarra='"
                        + tProd_Cotizados.getValueAt(control.fila, 0).toString() + "'";
                codPr = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
                CantiP = Integer.parseInt(tProd_Cotizados.getValueAt(control.fila, 4).toString());
                PrecioP = Double.parseDouble(tProd_Cotizados.getValueAt(control.fila, 5).toString());
                control.Sql = "insert into detallecotizacion (cnt, prec, idcotizacion, codctlg)"
                        + "values('" + CantiP + "','" + PrecioP + "','" + CodCotiz + "','" + codPr + "');";
                control.CrearRegistro(control.Sql);

                /*control.Sql="select count(*) from carateristicasproducto where idproducto='"+codPr+"';";
        ControlREporte=Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
                 */
                control.fila++;
            }

            if (txAtencion.getText().trim().length() > 0) {
                control.Sql = "insert into atencion(nomaten,idCotizacion) values('" + txAtencion.getText()
                        + "','" + CodCotiz + "')";
                control.CrearRegistro(control.Sql);
            }
            /*control.impresor.ImprimirCotizacion("Cotización","LaCotizacion","Id","Monto",
       CodCotiz,lbMontoFinal.getText());*/
            double Igvs[] = new double[3];
            String F_caduc = "";
            F_caduc = control.DevolverRegistroDto("select date_format((fec_ctz + interval diasdur day),'%d/%m/%Y') "
                    + "F_final from cotizacion where idcotizacion=" + CodCotiz + " ;", 1);
            Igvs = control.CalcularIgv(Double.parseDouble(lbMontoFinal.getText()));
            control.impresor.Imprimircon4Parametros("La cotización", "LaCotizacion", "Monto", "Id", "atencion",
                    "fcadu", lbMontoFinal.getText(), CodCotiz, txAtencion.getText(), F_caduc);
            /*control.impresor.ImprimirCotizacion("Cotización de Productos","LaCotizacion","Id","Monto",
       CodCotiz,lbMontoFinal.getText(),Double.toString(Igvs[1]),Double.toString(Igvs[0]));      
             */
            Cancelar();
        } else {
            JOptionPane.showMessageDialog(null, "Tiene que Ingresar un Cliente y Productos Cotizados");
            txtBuscarCliente.grabFocus();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tProductos = new javax.swing.JTable();
        txtBuscarProducto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tClientes = new javax.swing.JTable();
        txtBuscarCliente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        bCliente = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        lbMontoFinal = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        lbProducto = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtPrecioFinal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lbCliente = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtDiasHabiles = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txAtencion = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tProd_Cotizados = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleccionar producto", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(0, 51, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        tProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tProductos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 910, 200));

        txtBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarProductoActionPerformed(evt);
            }
        });
        txtBuscarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarProductoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProductoKeyReleased(evt);
            }
        });
        jPanel1.add(txtBuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 15, 440, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Buscar");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, -1, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Stock");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 20, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 930, 260));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleccionar Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(0, 51, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        tClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tClientesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tClientes);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 0, 0));

        txtBuscarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarClienteKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarClienteKeyReleased(evt);
            }
        });
        jPanel2.add(txtBuscarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 440, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Buscar");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        bCliente.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCliente.setForeground(new java.awt.Color(0, 51, 102));
        bCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Clien list.png"))); // NOI18N
        bCliente.setMnemonic('l');
        bCliente.setText("Agregar Cliente");
        bCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bClienteActionPerformed(evt);
            }
        });
        jPanel2.add(bCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 110));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Descuento");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 425, -1, -1));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Precio");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 425, -1, -1));

        txtDescuento.setText("0.0");
        txtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyTyped(evt);
            }
        });
        getContentPane().add(txtDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 425, 100, -1));

        txtPrecio.setText("0.0");
        getContentPane().add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 425, 100, -1));

        lbMontoFinal.setText(" 0.00 ");
        lbMontoFinal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(lbMontoFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 610, 110, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setText("Monto Total");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 610, -1, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(" "));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/registrar.png"))); // NOI18N
        jButton2.setMnemonic('r');
        jButton2.setText("Registrar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 110, 50));

        jButton5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 51, 102));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        jButton5.setMnemonic('c');
        jButton5.setText("Cancelar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 110, 50));

        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 51, 102));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton4.setMnemonic('s');
        jButton4.setText("Salir");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 185, 110, 50));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 110, 150, 260));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setText("Monto Total");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 610, -1, -1));

        lbProducto.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbProducto.setForeground(new java.awt.Color(0, 51, 102));
        lbProducto.setText(" ");
        lbProducto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(lbProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 383, 910, -1));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 102));
        jLabel11.setText("Precio Final");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 425, -1, -1));

        txtPrecioFinal.setText("0.0");
        txtPrecioFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrecioFinalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioFinalKeyTyped(evt);
            }
        });
        getContentPane().add(txtPrecioFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 425, 110, -1));

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 102));
        jLabel12.setText("Cantidad");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 425, -1, -1));

        txtCantidad.setText("1");
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
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
        getContentPane().add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 425, 100, -1));

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/List.png"))); // NOI18N
        jButton1.setMnemonic('v');
        jButton1.setText("Ver Caracteristicas");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1004, 383, 30, -1));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbCliente.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbCliente.setForeground(new java.awt.Color(0, 51, 102));
        lbCliente.setText(" ");
        lbCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.add(lbCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 25, 470, -1));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Atención");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 85, -1, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 102));
        jLabel9.setText("El Cliente");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 5, -1, -1));

        txtDiasHabiles.setText("7");
        jPanel4.add(txtDiasHabiles, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 90, -1));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 102));
        jLabel13.setText("Validez");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, -1, -1));
        jPanel4.add(txAtencion, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 85, 380, -1));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 0, 510, 110));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos Cotizados", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tProd_Cotizados.setForeground(new java.awt.Color(0, 51, 102));
        tProd_Cotizados.setModel(new javax.swing.table.DefaultTableModel(
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
        tProd_Cotizados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tProd_CotizadosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tProd_Cotizados);

        jPanel5.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 1050, 210));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 102));
        jLabel7.setText("Producto:");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 368, 1070, 240));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (lbProducto.getText().trim().length() > 0 && codProducto != "") {
            VerCaracteristicas cli = new VerCaracteristicas();
            Menu.jDesktopPane1.add(cli);
            VerCaracteristicas.lbProducto.setText(lbProducto.getText().toString());
            info.setCarac(Integer.parseInt(codProducto));
            cli.Mensaje();
            cli.toFront();
            cli.setVisible(true);
            cli.setLocation(300, 120);
        }
}//GEN-LAST:event_jButton1ActionPerformed
    private void txtBuscarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProductoKeyReleased
        MostrarProdutco();
    }//GEN-LAST:event_txtBuscarProductoKeyReleased
    private void txtBuscarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarClienteKeyReleased
        MostrarCliente();
    }//GEN-LAST:event_txtBuscarClienteKeyReleased
    private void tClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tClientesMouseClicked

        //
    }//GEN-LAST:event_tClientesMouseClicked
    private void tProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProductosMouseClicked
        if (evt.getClickCount() >= 2) {
            control.bandera = 3 < 2;
            Pasardirecto();
        }
    }//GEN-LAST:event_tProductosMouseClicked
    private void txtDescuentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyPressed
        if (evt.getKeyChar() == 10 && codProducto.trim().length() > 0) {
            double pre = Double.parseDouble(txtPrecio.getText());
            double des = Double.parseDouble(txtDescuento.getText());
            int cant = Integer.parseInt(txtCantidad.getText());
            txtPrecioFinal.setText("" + ((pre * cant) - (des)));
            txtPrecioFinal.grabFocus();
            txtPrecioFinal.selectAll();
        }
    }//GEN-LAST:event_txtDescuentoKeyPressed
    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
        if (evt.getKeyChar() == 10 && codProducto.trim().length() > 0) {
            double pre = Double.parseDouble(txtPrecio.getText());
            double des = Double.parseDouble(txtDescuento.getText());
            int cant = Integer.parseInt(txtCantidad.getText());
            txtPrecioFinal.setText("" + ((pre * cant) - (des)));
            txtDescuento.grabFocus();
            txtDescuento.selectAll();
        }
    }//GEN-LAST:event_txtCantidadKeyPressed
    private void txtPrecioFinalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioFinalKeyPressed
        if (evt.getKeyChar() == 10 && codProducto.trim().length() > 0) {
            ContadorProdcuto++;
            mont1 = Double.parseDouble(txtPrecio.getText());
            mont2 = Double.parseDouble(txtPrecioFinal.getText());
            val1 = "" + forma.format(mont1);
            try {
                Number numero = forma.parse(val1);
                valor = numero.doubleValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            datos[0] = "" + codProducto;
            datos[1] = lbProducto.getText();
            datos[2] = marca;
            datos[3] = "" + valor;
            datos[4] = txtCantidad.getText();
            val1 = "" + forma.format(mont2);
            try {
                Number numero = forma.parse(val1);
                valor = numero.doubleValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            datos[5] = "" + valor;
            modelo2.addRow(datos);
            codProducto = "";
            Marca = "";
            marca = "";
            lbProducto.setText(" ");
            txtPrecio.setText(null);
            txtDescuento.setText("0.0");
            txtCantidad.setText("1");
            txtPrecio.setText("0.0");
            double mo = Double.parseDouble(txtPrecioFinal.getText());
            montototal = montototal + mo;
            val1 = "" + forma.format(montototal);
            try {
                Number numero = forma.parse(val1);
                valor = numero.doubleValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            lbMontoFinal.setText(control.decimalFormat(valor));
            txtPrecioFinal.setText("0.00");
            txtBuscarProducto.grabFocus();
            control.LimTabla(modelo1);
            MostrarProdutco();
            txtBuscarProducto.setText("");
            txtBuscarProducto.grabFocus();
        }
    }//GEN-LAST:event_txtPrecioFinalKeyPressed
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Cancelar();
    }//GEN-LAST:event_jButton5ActionPerformed
    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        GrabarCotizacion();

    }//GEN-LAST:event_jButton2ActionPerformed
    private void txtBuscarProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProductoKeyPressed
        //if(evt.getKeyChar()==10 ){
        //if( modelo1.getRowCount()==1){
        //tProductos.selectAll();
        //SelecionarProducto();
        //}
        //} 
        //Pasar();
    }//GEN-LAST:event_txtBuscarProductoKeyPressed
    private void tProd_CotizadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProd_CotizadosMouseClicked
        if (evt.getClickCount() >= 2) {
            QuitarCotizados();
        }
    }//GEN-LAST:event_tProd_CotizadosMouseClicked
    private void txtCantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadKeyReleased
    private void bClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bClienteActionPerformed
        MostrarCliente();
        LosClientes cli = new LosClientes();
        Menu.jDesktopPane1.add(cli);
        info.setControl(2);
        LosClientes.bCrear.doClick();
        if (tClientes.getRowCount() == 0) {
            LosClientes.txtCliente.setText(txtBuscarCliente.getText());
        }
        LosClientes.txtCliente.setText(txtBuscarCliente.getText());
        txtBuscarCliente.setText("");
        LosClientes.txtCliente.grabFocus();
        cli.toFront();
        cli.setVisible(true);
        cli.setLocation(300, 80);
    }//GEN-LAST:event_bClienteActionPerformed
    private void txtBuscarClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarClienteKeyPressed
        if (evt.getKeyChar() == 10) {
            if (tClientes.getRowCount() == 0) {
                bCliente.doClick();
            } else {
                if (tClientes.getRowCount() == 1) {
                    tClientes.selectAll();
                    SelecionarCliente();
                    txtBuscarProducto.grabFocus();
                } else {
                    bCliente.doClick();
                }
            }
        }
    }//GEN-LAST:event_txtBuscarClienteKeyPressed
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        info.setControlCliente(0);
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed
    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        control.Solonumeros(evt);
    }//GEN-LAST:event_txtCantidadKeyTyped
    private void txtDescuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyTyped
        control.decimal(evt, txtDescuento.getText());        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescuentoKeyTyped
    private void txtPrecioFinalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioFinalKeyTyped
        control.decimal(evt, txtPrecioFinal.getText());        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioFinalKeyTyped
    private void txtBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarProductoActionPerformed
        control.bandera = 3 > 2;
        Pasar();
    }//GEN-LAST:event_txtBuscarProductoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCliente;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    public static javax.swing.JLabel lbCliente;
    private javax.swing.JLabel lbMontoFinal;
    private javax.swing.JLabel lbProducto;
    private javax.swing.JTable tClientes;
    private javax.swing.JTable tProd_Cotizados;
    private javax.swing.JTable tProductos;
    private javax.swing.JTextField txAtencion;
    public static javax.swing.JTextField txtBuscarCliente;
    private javax.swing.JTextField txtBuscarProducto;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtDiasHabiles;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtPrecioFinal;
    // End of variables declaration//GEN-END:variables
}
