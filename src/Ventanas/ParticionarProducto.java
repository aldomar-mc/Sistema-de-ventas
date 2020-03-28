package Ventanas;

import Clases.*;
import static Ventanas.Ventas.tProductos;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class ParticionarProducto extends javax.swing.JInternalFrame {

    /**
     * ********************ATRIBUTOS******************************
     */
    Mimodelo modelo = new Mimodelo();
    Mimodelo modelo1 = new Mimodelo();
    Mimodelo modelo2 = new Mimodelo();
    Mimodelo modelo3 = new Mimodelo();
    Mimodelo modelo4 = new Mimodelo();
    InfoGeneral info = new InfoGeneral();
    Controlador control = new Controlador();
    int CtdContenida = 0;
    String codig = "", codiNuev = "", codigoPadre, codigohijo, costoprod = "", precvent = "",
            fecing = "", docCmp = "", idUsuario = "";
    /**
     * ********************ATRIBUTOS******************************
     */

    /**
     * **********************METODOS******************************
     */
    private int valor = 0;
    private int cantidad = 0;
    String[] dat = new String[4];

    public ParticionarProducto() {
        initComponents();
        setTitle("Particion de Producto");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tProductos.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"Código",
            "Producto", "Codigo", "Precio Venta", "Stock"});
        control.setWidthTableColumn(tProductos, 50, 2, 3, 4);
        control.setWidthTableColumn(tProductos, 60, 2);
        control.setWidthTableColumn(tProductos, 20, 4);
        tProductos.getColumnModel().getColumn(1).setPreferredWidth(350);
        control.hideTableColumn(tProductos, 0);
        MostrarProducto();
        tSubProductos.setModel(modelo2);
        modelo2.setColumnIdentifiers(new String[]{"Producto", "Codigo Barras", "Precio"});
        control.hideTableColumn(tSubProductos, 0);
        control.setMaxWidthColumnTable(tSubProductos, 280, 1, 2);
        control.forma_table_ver(modelo, tProductos);
        lblserie.setVisible(false);
        txSeri.setVisible(false);
        idUsuario = control.DevolverRegistroDto("select * from usuario where nomusr='" + InfoGeneral.usuario + "'", 1);
    }

    public void MostrarProducto() {
        BuscarProducto();
    }

    public void SeleccionarProducto() {
        control.fila = tProductos.getSelectedRow();
        if (control.fila > -1) {
            /**
             * ******************TENEMOS EL CODIGO DEL
             * PRINCIPAL**********************
             */
            control.Sql = "select catalogoproducto_codctlg,fecingralm,idDoc_Compra from producto where "
                    + "idproducto='" + tProductos.getValueAt(control.fila, 0).toString() + "'";
            codigoPadre = control.DevolverRegistroDto(control.Sql, 1);
            fecing = control.DevolverRegistroDto(control.Sql, 2);
            docCmp = control.DevolverRegistroDto(control.Sql, 3);
            control.Sql = "select idcatalogo2,cant from detalleproducto where idcatalogo1='" + codigoPadre + "'";
            codigohijo = control.DevolverRegistroDto(control.Sql, 1);
            CtdContenida = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 2));
            /**
             * ******************TENEMOS EL CODIGO DEL
             * PRINCIPAL**********************
             */

            /**
             * ***********************Poner Datos en el
             * Formulario****************************
             */
            control.Sql = "select cp.Codbarra,concat(md.nommod,' ',mrc.nommrc,' ',cp.nomctlg) Producto,cp.precsg "
                    + "Costo,cp.prexmayor from catalogoproducto cp inner join marca mrc on mrc.idmarca=cp.idmarca "
                    + "inner join modelocatalogo mc on mc.codctlg=cp.codctlg inner join modelo md on md.idmodelos="
                    + "cp.idmodelos where cp.codctlg='" + codigohijo + "'";
            lbNombreProducto.setText(tProductos.getValueAt(control.fila, 1).toString());
            lbNombreNuevoProducto.setText(control.DevolverRegistroDto(control.Sql, 2));
            txtCantidadSubProductos.setText(Integer.toString(CtdContenida));
            costoprod = control.DevolverRegistroDto(control.Sql, 3);
            txtPrecioSubProductos.setText(control.DevolverRegistroDto(control.Sql, 4));
            txtCodigoBarr.setText(control.DevolverRegistroDto(control.Sql, 1));
            /**
             * ***********************Poner Datos en el
             * Formulario****************************
             */
        }
    }

    public void BuscarProducto() {
        /*control.Sql="SELECT p.`idProducto`, CONCAT(`mo`.`nommod`,' ',m.nommrc,' ',`c`.`nomctlg`), `p`.`codbrr`, `c`.`prexmenor`," +
         "count(p.`Catalogoproducto_codctlg`) FROM producto p, catalogoproducto c, marca m, sede se, modelo mo," +
         " modelocatalogo mc, detalleproducto dp WHERE p.`Catalogoproducto_codctlg` = c.`codctlg` AND m.idMarca"
         + " = c.idMarca AND p.`idSede` = se.`idSede` AND c.`idModelos` = mo.`idModelos` AND c.`codctlg`="
         + "mc.`codctlg` AND p.`estdo`='Disponible' AND (CONCAT(`mo`.`nommod`,' ',m.nommrc,' ',`c`.`nomctlg`) LIKE "
         + "'%"+txtBucarProducto.getText()+"%' OR p.`codbrr` LIKE '%"+txtBucarProducto.getText()+"%') " +
         "AND p.`idProducto` NOT IN(SELECT producto_idProducto FROM venta_producto) AND c.`codctlg`"
         + "=dp.`idcatalogo1` GROUP BY p.`Catalogoproducto_codctlg`;";*/
        control.Sql = "select p.idproducto,concat(md.nommod,' ',mrc.nommrc,' ',cp.nomctlg),p.codbrr,cp.prexmenor,count("
                + "p.catalogoproducto_codctlg) from producto p inner join catalogoproducto cp on cp.codctlg=p.catalogoproducto_codctlg "
                + "inner join modelocatalogo mc on mc.codctlg=cp.codctlg inner join modelo md on md.idmodelos=mc.idmodelos inner "
                + "join marca mrc on mrc.idmarca=cp.idmarca where p.estdo='Disponible' and cp.codctlg in (select idcatalogo1 from detalleproducto) "
                + "and (p.codbrr like'%" + txtBucarProducto.getText() + "%' or cp.nomctlg like'%" + txtBucarProducto.getText() + "%' or md.nommod like'%"
                + txtBucarProducto.getText() + "%' "
                + "or mrc.nommrc like'%" + txtBucarProducto.getText() + "%') group by cp.nomctlg;";
        control.LlenarJTabla(modelo, control.Sql, 5);
    }

    public void Generar() {
        if (txtCantidadSubProductos.getText().trim().length() == 0) {
            txtCantidadSubProductos.grabFocus();
            return;
        } else {
            control.LimTabla(modelo2);
            int contt = 1;
            int da = Integer.parseInt(txtCantidadSubProductos.getText().toString());
            while (contt <= da) {
                dat[0] = lbNombreNuevoProducto.getText();
                dat[1] = txtCodigoBarr.getText();
                //dat[2]=txSeri.getText();
                dat[2] = txtPrecioSubProductos.getText();
                modelo2.addRow(dat);
                contt++;
            }
        }
    }

    public void Cancelar() {
        control.LimTabla(modelo);
        control.LimTabla(modelo1);
        control.LimTabla(modelo2);
        lbNombreNuevoProducto.setText(" ");
        lbNombreProducto.setText(" ");
        txtBucarProducto.setText("");
        txtCantidadSubProductos.setText("");
        chbGenerar.setSelected(false);
        txtPrecioSubProductos.setText("");
        codig = "";
        codigoPadre = "";
        codigohijo = "";
        costoprod = "";
        precvent = "";
        CtdContenida = 0;
        fecing = "";
        docCmp = "";
        MostrarProducto();
    }

    public void verificar() {
        if (chbGenerar.isSelected()) {
            if (lbNombreNuevoProducto.getText().trim().length() > 0 && lbNombreProducto.getText().trim().length() > 0) {
                lblcodigo.setEnabled(false);
                lblserie.setEnabled(false);
                txSeri.setEnabled(false);
                txtCodigoBarr.setEnabled(false);
                Generar();
            } else {
                JOptionPane.showMessageDialog(null, "Datos incompletos");
            }
        } else {
            control.LimTabla(modelo2);
            txtCodigoBarr.grabFocus();
            lblcodigo.setEnabled(true);
            lblserie.setEnabled(true);
            txSeri.setEnabled(true);
            txtCodigoBarr.setEnabled(true);
        }
    }

    public void AgregarUnoAUno() {
        if (txtCodigoBarr.getText().trim().length() > 0) {
            if (valor < cantidad) {
                dat[0] = lbNombreNuevoProducto.getText();
                dat[1] = txtCodigoBarr.getText();
                dat[2] = txSeri.getText();
                dat[3] = txtPrecioSubProductos.getText();
                modelo2.addRow(dat);
                txtCodigoBarr.setText("");
                txSeri.setText("");
                txtCodigoBarr.requestFocus();
                valor++;
            } else {
                JOptionPane.showMessageDialog(null, "Ya no puede agregar más productos");
                txtCodigoBarr.requestFocus();
            }
        } else {
            txtCodigoBarr.grabFocus();
            return;
        }
    }

    public void Aceptar() {
        if (JOptionPane.showConfirmDialog(null, "Desea Crear esta división??", "", JOptionPane.YES_NO_OPTION) == 0) {
            /**
             * ************************ REGISTRAR PARTICION DE PRODUCTO******************************
             */
            String idApertura = "";
            control.Sql = "insert into aperturaproducto(fch,hra,idusuario) values (curdate(),curtime(),'"
                    + idUsuario + "')";
            control.CrearRegistro(control.Sql);
            control.Sql = "select * from aperturaproducto order by 1 desc limit 1";
            idApertura = control.DevolverRegistroDto(control.Sql, 1);
            /**
             * ************************ REGISTRAR PARTICION DE PRODUCTO******************************
             */

            for (control.fila = 1; control.fila <= CtdContenida; control.fila++) {
                control.Sql = "insert into producto(fecingralm,Catalogoproducto_codctlg,codbrr,idDoc_Compra,"
                        + "costo,estdo,precVenta,idSede) values('" + fecing + "','" + codigohijo + "','" + txtCodigoBarr.getText()
                        + "','" + docCmp + "','" + costoprod + "','Disponible','" + txtPrecioSubProductos.getText() + "','"
                        + info.getIdSede() + "')";
                control.CrearRegistro(control.Sql);

            }
            /**
             * *********************Actualizar el
             * producto****************************
             */
            control.Sql = "update producto set estdo='Aperturado' where idproducto='" + tProductos.getValueAt(
                    tProductos.getSelectedRow(), 0).toString() + "'";
            control.EditarRegistro(control.Sql);

            control.Sql = "insert into productoaperturado(idproducto,idaperturaproducto) values('" + tProductos.getValueAt(
                    tProductos.getSelectedRow(), 0).toString() + "','" + idApertura + "')";
            control.CrearRegistro(control.Sql);
            Cancelar();

        }
    }

    public void GenerarNuevosProductos() {
        if (JOptionPane.showConfirmDialog(null, "Desea Crear esta división??", "", JOptionPane.YES_NO_OPTION) == 0) {

            /**
             * ***********Capturamos datos del producto pricipal para los
             * nuevos productos*********************
             */
            String codctlgnew = "", fecing = "", docCompra = "", codbrr = "", costo = "", precio = "";
            control.Sql = "select catalogoproducto_codctlg,fecingralm,idDoc_compra from producto where idproducto='"
                    + codig + "' limit 1";
            codctlgnew = control.DevolverRegistroDto(control.Sql, 1);
            fecing = control.DevolverRegistroDto(control.Sql, 2);
            docCompra = control.DevolverRegistroDto(control.Sql, 3);
            /**
             * ***********Capturamos datos del producto pricipal para los
             * nuevos productos*********************
             */

            /**
             * *******************Capturamos datos del catalogo para los Nuevos
             * Produtos**********************
             */
            control.Sql = "select precsg,codbarra,prexmayor from catalogoproducto where codctlg='" + codctlgnew + "'";
            costo = control.DevolverRegistroDto(control.Sql, 1);
            codbrr = control.DevolverRegistroDto(control.Sql, 2);
            precio = control.DevolverRegistroDto(control.Sql, 3);
            /**
             * *******************Capturamos datos del catalogo para los Nuevos
             * Produtos**********************
             */

            /**
             * **********************Generar el Nuevo
             * Producto*******************************
             */
            control.fila = 0;
            while (control.fila < tSubProductos.getRowCount()) {
                control.Sql = "insert into producto(fecingralm,Catalogoproducto_codctlg,codbrr,idDoc_Compra,"
                        + "costo,estdo,precVenta,idSede) values('" + fecing + "','" + codctlgnew + "','" + codbrr + "','" + docCompra
                        + "','" + costo + "','Disponible','" + precio + "','" + info.getIdSede() + "')";
                control.CrearRegistro(control.Sql);
                control.fila++;
            }
            /**
             * **********************Generar el Nuevo
             * Producto*******************************
             */

            /**
             * ******************Aperturar el producto
             * Principal***************************
             */
            control.Sql = "update producto set estdo='Aperturado' where idproducto='" + codig + "';";
            control.EditarRegistro(control.Sql);
            Cancelar();
            dispose();
            /**
             * ******************Aperturar el producto
             * Principal***************************
             */

            /*control.Sql="SELECT fecingralm, catalogoproducto_codctlg, codbrr, idDoc_compra, costo, precventa "+
             "FROM producto where idproducto='"+codig+"';";
          
             String p1=control.DevolverRegistroDto(control.Sql, 1);
             String p2=control.DevolverRegistroDto(control.Sql, 2);
             String p3=control.DevolverRegistroDto(control.Sql, 3);
             String p4=control.DevolverRegistroDto(control.Sql, 4);
             String p5=control.DevolverRegistroDto(control.Sql, 5);
        
             control.Sql="SELECT fecinic,fecfin,periodo, cantiperio FROM garantiaxcompra where idproducto='"+codig+"'";
             String g1=control.DevolverRegistroDto(control.Sql, 1);
             String g2=control.DevolverRegistroDto(control.Sql, 2);
             String g3=control.DevolverRegistroDto(control.Sql, 3);
             String g4=control.DevolverRegistroDto(control.Sql, 4);
             control.Sql="SELECT fecha_cad FROM fecha_caducidad WHERE id_Producto='"+codig+"'";
             String fechacadu = control.DevolverRegistroDto(control.Sql, 1);
             while(control.fila<tSubProductos.getRowCount()){
             control.Sql=" insert into producto(fecingralm,catalogoproducto_codctlg, codbrr, idDoc_compra, "
             + "costo, precventa,estdo,idsede) values('"+p1+"','"+codiNuev+"','"+tSubProductos.getValueAt(control.fila,
             1).toString()+"',"+ " '"+p4+"','"+(Double.parseDouble(p5)/Integer.parseInt(txtCantidadSubProductos.getText()))+"',"
             + " '"+tSubProductos.getValueAt(control.fila, 2).toString()+"','Disponible','"+info.getIdSede()+"');";
             control.CrearRegistro(control.Sql);
             */
 /*control.Sql="select idproducto from producto order by idproducto desc limit 1;";
             String codnu=control.DevolverRegistroDto(control.Sql, 1);
             control.Sql="insert into garantiaxcompra (fecinic, fecfin, idproducto, periodo, cantiperio) "
             + "values('"+g1+"','"+g2+"','"+codnu+"','"+g3+"','"+g4+"');";
             control.CrearRegistro(control.Sql);
             control.Sql="insert into serie  "
             + "values(null,'"+tSubProductos.getValueAt(control.fila, 2).toString()+"','"+codnu+"');";
             control.CrearRegistro(control.Sql);
             control.Sql="INSERT INTO fecha_caducidad (fecha_cad, id_Producto) VALUES ('"+fechacadu+"','"+codnu+"');";
             control.CrearRegistro(control.Sql);
                
             /*    control.fila++;
             }
             control.Sql="update producto set estdo='Aperturado' where idproducto='"+codig+"';";
             control.EditarRegistro(control.Sql); Cancelar();*/
        }
    }

    public void cargarcantidadProducto() {
        control.Sql = "SELECT count(*) FROM productocantidad  where idproducto='" + codigohijo + "';";
        int cant = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
        control.Sql = "SELECT cantidad FROM productocantidad  where idproducto='" + codigohijo + "';";
    }

    public void GrabarNuevoProduto() {
        if (JOptionPane.showConfirmDialog(null, "Se va  a agregar una Nueva Cantidad de Productos", "", JOptionPane.YES_NO_OPTION) == 0) {
            control.Sql = "SELECT count(*) FROM productocantidad  where idproducto='" + codigohijo + "';";
            int cant = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
            if (cant == 0) {
//            control.Sql="insert into productocantidad (idproducto, cantidad) values ('"+codigohijo+"','"+txtMas.getText()+"');";

                control.CrearRegistro(control.Sql);
                control.Sql = "update producto set estdo='Aperturado' where idproducto='" + codigoPadre + "';";

                control.EditarRegistro(control.Sql);
                codigoPadre = "";
                codigohijo = "";

                BuscarProducto();
                valor = 0;
            } else {

                control.EditarRegistro(control.Sql);
                control.Sql = "update producto set estdo='Aperturado' where idproducto='" + codigoPadre + "';";

                control.EditarRegistro(control.Sql);

                codigoPadre = "";
                codigohijo = "";
                BuscarProducto();
                valor = 0;
            }
        }
    }

    /**
     * ***********************METODOS******************************
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tSubProductos = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtCantidadSubProductos = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lbNombreNuevoProducto = new javax.swing.JLabel();
        lblcodigo = new javax.swing.JLabel();
        txtCodigoBarr = new javax.swing.JTextField();
        txSeri = new javax.swing.JTextField();
        lblserie = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtPrecioSubProductos = new javax.swing.JTextField();
        txtBucarProducto = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tProductos = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lbNombreProducto = new javax.swing.JLabel();
        chbGenerar = new javax.swing.JCheckBox();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setForeground(new java.awt.Color(0, 51, 102));
        jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tSubProductos.setForeground(new java.awt.Color(0, 51, 102));
        tSubProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        tSubProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tSubProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tSubProductos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(597, 204, 560, 220));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("N° de SubProductos");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(608, 138, -1, -1));

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        jButton1.setMnemonic('a');
        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 440, 140, 46));

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        jButton2.setMnemonic('c');
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 440, 140, 46));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Producto Principal");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(608, 38, -1, -1));

        txtCantidadSubProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadSubProductosActionPerformed(evt);
            }
        });
        txtCantidadSubProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadSubProductosKeyPressed(evt);
            }
        });
        jPanel1.add(txtCantidadSubProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(755, 137, 180, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Producto Contenido");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(608, 71, -1, -1));

        lbNombreNuevoProducto.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbNombreNuevoProducto.setForeground(new java.awt.Color(0, 51, 102));
        lbNombreNuevoProducto.setText(" ");
        lbNombreNuevoProducto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(lbNombreNuevoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(755, 69, 380, -1));

        lblcodigo.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblcodigo.setForeground(new java.awt.Color(0, 51, 102));
        lblcodigo.setText("Codigo Barras");
        jPanel1.add(lblcodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 173, -1, -1));

        txtCodigoBarr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoBarrKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoBarrKeyReleased(evt);
            }
        });
        jPanel1.add(txtCodigoBarr, new org.netbeans.lib.awtextra.AbsoluteConstraints(755, 170, 180, -1));

        txSeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txSeriKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txSeriKeyReleased(evt);
            }
        });
        jPanel1.add(txSeri, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 210, 210, -1));

        lblserie.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblserie.setForeground(new java.awt.Color(0, 51, 102));
        lblserie.setText("Nueva Serie");
        jPanel1.add(lblserie, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 210, -1, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 102));
        jLabel7.setText("Precio de Venta");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(629, 108, -1, -1));

        txtPrecioSubProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrecioSubProductosKeyPressed(evt);
            }
        });
        jPanel1.add(txtPrecioSubProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(755, 107, 180, -1));

        txtBucarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBucarProductoKeyReleased(evt);
            }
        });
        jPanel1.add(txtBucarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 12, 281, -1));

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
        jScrollPane2.setViewportView(tProductos);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 46, 590, 375));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Buscar Producto");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

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
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 440, 140, 46));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de los Productos a Generar", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbNombreProducto.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbNombreProducto.setForeground(new java.awt.Color(0, 51, 102));
        lbNombreProducto.setText(" ");
        lbNombreProducto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.add(lbNombreProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 28, 380, -1));

        chbGenerar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        chbGenerar.setMnemonic('t');
        chbGenerar.setText("Generar Automaticamente");
        chbGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbGenerarActionPerformed(evt);
            }
        });
        jPanel2.add(chbGenerar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 130, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 560, 190));

        jTabbedPane1.addTab("Particion de Productos", jPanel1);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 520));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProductosMouseClicked
        if (evt.getClickCount() == 2) {
            SeleccionarProducto();
            /*if(tProductos.getSelectedRowCount()==1){
             chbGenerar.setSelected(false);
             codig=tProductos.getValueAt(tProductos.getSelectedRow(), 0).toString();
             lbNombreProducto.setText(tProductos.getValueAt(tProductos.getSelectedRow(), 1).toString());
             txtCantidadSubProductos.setText("");control.LimTabla(modelo2);
             control.Sql="select Catalogoproducto_codctlg from producto where idProducto='"+codig+"'";
             String codcatalogo1=control.DevolverRegistroDto(control.Sql,1);                
             control.Sql="select idcatalogo2 from detalleproducto where idcatalogo1='"+codcatalogo1+"'";
             String codcatalogo2=control.DevolverRegistroDto(control.Sql,1);
             codiNuev=codcatalogo2;
             if(codcatalogo2.equals("")){
             JOptionPane.showMessageDialog(null,"No existe ningun Producto a particionar "
             + "para este catalogo","System Message",3);
             }else{
             valor=0;
             control.Sql="select nomctlg from catalogoproducto where codctlg='"+codcatalogo2+"'";
             lbNombreNuevoProducto.setText(control.DevolverRegistroDto(control.Sql, 1));
             control.Sql="select prexmenor from catalogoproducto where codctlg='"+codcatalogo2+"'";
             txtPrecioSubProductos.setText(control.DevolverRegistroDto(control.Sql, 1));
                    
             control.Sql="select codbarra from catalogoproducto where codctlg='"+codcatalogo2+"'";
             txtCodigoBarr.setText(control.DevolverRegistroDto(control.Sql, 1));
                    
             control.Sql="select cant from detalleproducto where idcatalogo1='"+codcatalogo1+"'";
             txtCantidadSubProductos.setText(control.DevolverRegistroDto(control.Sql,1));
             cantidad=Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
             }
             }*/
        }
    }//GEN-LAST:event_tProductosMouseClicked

    private void txtBucarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBucarProductoKeyReleased
        MostrarProducto();
    }//GEN-LAST:event_txtBucarProductoKeyReleased

    private void txtPrecioSubProductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioSubProductosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioSubProductosKeyPressed

    private void txtCodigoBarrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoBarrKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoBarrKeyPressed

    private void chbGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbGenerarActionPerformed
        // JOptionPane.showMessageDialog(null,"El anterio "+codig+" El nuevo es "+codiNuev);
        verificar();
    }//GEN-LAST:event_chbGenerarActionPerformed

    private void txtCantidadSubProductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadSubProductosKeyPressed

    }//GEN-LAST:event_txtCantidadSubProductosKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Cancelar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //GenerarNuevosProductos();
        Aceptar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tSubProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tSubProductosMouseClicked

    }//GEN-LAST:event_tSubProductosMouseClicked

    private void txtCantidadSubProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadSubProductosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadSubProductosActionPerformed

    private void txtCodigoBarrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoBarrKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtCodigoBarr.getText().trim().length() > 0) {
                txSeri.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoBarrKeyReleased

    private void txSeriKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txSeriKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            AgregarUnoAUno();
        }

    }//GEN-LAST:event_txSeriKeyReleased

    private void txSeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txSeriKeyPressed
        //if(evt.getKeyChar()==10){
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            AgregarUnoAUno();
        }

        //}
    }//GEN-LAST:event_txSeriKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chbGenerar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbNombreNuevoProducto;
    private javax.swing.JLabel lbNombreProducto;
    private javax.swing.JLabel lblcodigo;
    private javax.swing.JLabel lblserie;
    private javax.swing.JTable tProductos;
    private javax.swing.JTable tSubProductos;
    private javax.swing.JTextField txSeri;
    private javax.swing.JTextField txtBucarProducto;
    private javax.swing.JTextField txtCantidadSubProductos;
    private javax.swing.JTextField txtCodigoBarr;
    private javax.swing.JTextField txtPrecioSubProductos;
    // End of variables declaration//GEN-END:variables
}
