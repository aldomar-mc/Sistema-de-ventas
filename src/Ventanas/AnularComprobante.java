package Ventanas;

/**
 * * ** @author Ing. Miguel Angel Silva Zapata. *********
 */
import Clases.Controlador;
import Clases.InfoGeneral;
import Clases.Mimodelo;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class AnularComprobante extends javax.swing.JInternalFrame {

    /**
     * ****************************Atributos***************************************
     */
    private static Mimodelo modelo = new Mimodelo();
    private static Controlador control = new Controlador();
    Mimodelo modelo1 = new Mimodelo();
    InfoGeneral info = new InfoGeneral();
    String codSer = "", sr = "", srt = "", idcom;
    int ppp = 0;
    public String codCliente = "", codProducto, Marca, CodVenta, CodCompro, Modalidad,
            CodProductoGrabar, identif, idencte, numdoc, stock, catalogo, gremi, igv, subtotal,
            serg, idguia, idusuario = "";

    /**
     * ****************************Atributos***************************************
     */

    /**
     * ****************************METODOS***************************************
     */
    public AnularComprobante() {
        initComponents();
        setTitle("Los comprobantes");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tComprobante.setModel(modelo);
        T_TiposComprobantes.setModel(modelo1);
        modelo.setColumnIdentifiers(new String[]{"Código", "Serie", "Número", "Tipo", "Estado", "Para"});
        modelo1.setColumnIdentifiers(new String[]{"ID", "Tipo", "Serie", "Cant_Digitos", "Para"});
        control.Sql = "SELECT ID,TIPO,SERIE,CANT_DIGITOS,PARA FROM vta_tipocomprobante where tipo like '%"
                + txBuscar.getText() + "%' and sede='" + Controlador.sede + "'";
        control.hideTableColumn(T_TiposComprobantes, 4);
        control.hideTableColumn(tComprobante, 5);
        control.LlenarJTablaSE(modelo1, control.Sql, 5);
        control.Sql = "select * from usuario where nomusr='" + info.getUsuario() + "'";
        idusuario = control.DevolverRegistroDto(control.Sql, 1);
        MostrarCommprobantes();
        control.forma_table_ver(modelo, tComprobante);
        control.forma_table_ver(modelo1, T_TiposComprobantes);
        jLabel2.setVisible(false);
    }

    private void MostrarCliente() {
        BuscarCliente();
    }

    private void BuscarCliente() {
        control.Sql = "SELECT idcomprobantes, serie,nume,tipcompr,esta FROM comprobantesvta "
                + "where serie like'" + txBuscar.getText() + "%'";
        control.LlenarJTabla(modelo, control.Sql, 5);
    }

    public static void MostrarCommprobantes() {
        int row = tComprobante.getSelectedRow();
        control.Sql = "SELECT * FROM vta_comprobantes where tipo<>'Venta Libre' and (Numero like'%"
                + txBuscar.getText() + "%' or tipo like'%" + txBuscar.getText() + "%') and (sede ='" + control.sede
                + "' and Estado='Emitido') order by 1 desc";
        control.LlenarJTabla(modelo, control.Sql, 6);
        if (row >= 0) {
            tComprobante.getSelectionModel().setSelectionInterval(row, row);
        }
    }

    public void Cancelar() {
        MostrarCliente();//txtNumComprobante.setText("");
        bModificar.setEnabled(true);
        bModificar.setText("Editar");
        bModificar.setEnabled(true);
        //cbTipoComrpo.setSelectedIndex(-1);
        bAnular.setText("Anular Com.");
    }

    public void EliminarComprobante() {
        if (tComprobante.getSelectedRowCount() == 1) {
            if (JOptionPane.showConfirmDialog(null, "Desea eliminar este registro!!", "Confirme",
                    JOptionPane.YES_NO_OPTION) == 0) {
                control.Sql = "delete from comprobantesvta where idcomprobantes='" + modelo.getValueAt(
                        tComprobante.getSelectedRow(), 0) + "';";
                control.EliminarRegistro(control.Sql);
                MostrarCliente();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tiene Que Seleccionar un Clinete para "
                    + "Eliminar", "", 2);
        }
    }

    public void GenerarNuevoComrpobante() {
        /*String dato = "";control.Sql = "call  GeneraComprobante('1','" 
     + cbTipoComrpo.getSelectedItem().toString() + "');";
     dato = control.DevolverRegistroDto(control.Sql, 1);
     StringTokenizer tokenizer = new StringTokenizer(dato, "-");sr = tokenizer.nextToken();
     srt = tokenizer.nextToken();txtNumComprobante.setText(srt);*/
    }

    public void AnulandocomprobanteUltimo() {
        String tipnewcom = "", Tipo = "", srie = "";
        control.fila = tComprobante.getSelectedRow();
        if (control.fila >= 0) {
            /**
             * *******************Capturamos el ID de la Venta*****************************************************
             */
            idcom = tComprobante.getValueAt(control.fila, 0).toString();
            control.Sql = "select idventa from compventa where idcomprobantes='" + idcom + "'";
            CodVenta = control.DevolverRegistroDto(control.Sql, 1);
            /**
             * *******************Capturamos el ID de la Venta*****************************************************
             */
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione el Comprobante a Eliminar");
            return;
        }
        /**
         * **************************Obtener el Nuevo Id de Tipo de Documento**********************************
         */
        if (T_TiposComprobantes.getSelectedRow() > -1) {
            control.fila = T_TiposComprobantes.getSelectedRow();
            tipnewcom = T_TiposComprobantes.getValueAt(control.fila, 0).toString();
            Tipo = T_TiposComprobantes.getValueAt(control.fila, 1).toString();
        } else {
            control.Sql = "select idtipocomprobante from comprobantes where idcomprobantes='" + idcom + "'";
            tipnewcom = control.DevolverRegistroDto(control.Sql, 1);
            Tipo = tComprobante.getValueAt(control.fila, 3).toString();
        }
        /**
         * **************************Obtener el Nuevo Id de Tipo de Documento**********************************
         */

        /**
         * ******************************************Anulamos el Anterior Comprobante*************************
         */
        control.Sql = "call AnulandoComprobante('" + idcom + "','" + tipnewcom + "','1')";
        control.CrearRegistro(control.Sql);
        /**
         * *****************************************Anulamos el Anterior Comprobante**************************
         */

        /**
         * ************************************Capturamos la serie y numero del
         * ultimo comprobante***************
         */
        control.Sql = "select concat(serie,'-',nume) Serie from comprobantes where esta='Emitido' and idtipocomprobante='"
                + tipnewcom + "' order by 1 " + " desc limit 1";
        srie = control.DevolverRegistroDto(control.Sql, 1);

        /**
         * ************************************Actualizamos la tabla pagos*************************************
         */
        control.Sql = "update pagos set numerocomprobante='" + srie + "',comprobante='" + Tipo + "' where Tipo='Venta' "
                + "and id='" + CodVenta + "'";
        System.out.println("Pagos " + control.Sql);
        control.EditarRegistro(control.Sql);
        /**
         * ************************************Actualizamos la tabla pagos*************************************
         */

        /**
         * ************************************Registrar en la tabla anulados************************************
         */
        control.Sql = "insert into anulados(idventa,fecha,hora,idusuario,motivo,idcomprobantes) values('" + CodVenta + "',curdate(),"
                + "curtime(),'" + idusuario + "','Por cambio de comprobante','" + idcom + "')";
        control.CrearRegistro(control.Sql);
        /**
         * ************************************Registrar en la tabla anulados************************************
         */

        MostrarCommprobantes();

    }

    public void ImprimeComprobante() {
        String cliente = "", dia = "", mes = "", ani = "", mont = "", direc = "", monlt = "";
        control.Sql = "select * from vta_datosimpresion where idventa='" + CodVenta + "'";
        dia = control.DevolverRegistroDto(control.Sql, 4);
        mes = control.DevolverRegistroDto(control.Sql, 5);
        ani = control.DevolverRegistroDto(control.Sql, 6).substring(3, 4);

    }

    public void Anulandocomprobante() {
        String tipo = "", para = "";
        control.fila = tComprobante.getSelectedRow();
        if (control.fila >= 0) {
            idcom = tComprobante.getValueAt(control.fila, 0).toString();///Obtenemos el id del comprobante
            tipo = control.DevolverRegistroDto("select * from comprobantes where idcomprobantes='"
                    + idcom + "'", 5); //obtenemos el tipo de comprobante
            para = tComprobante.getValueAt(control.fila, 5).toString();
            //Generar nuevo comprobante
            control.Sql = "call ElComprobante('0','" + Controlador.sede + "','" + tipo + "','" + para + "','0','1')";
            control.Sql = "update comprobantes set esta='Anulado' where idcomprobantes='" + idcom + "'";
            control.EditarRegistro(control.Sql); //anulamos el comprobante
//            System.out.println(control.Sql);
            if (control.DevolverRegistroDto(control.Sql, 1).equals("Se debe iniciar este tipo de comprobante")) {
                JOptionPane.showMessageDialog(null, "No hay documentos de ese tipo debes iniciarlo");
                IniciandoComprobantesICECompured inicdoc = new IniciandoComprobantesICECompured();
                Menu.jDesktopPane1.add(inicdoc);
                inicdoc.toFront();
                inicdoc.setLocation(250, 250);
                JOptionPane.showMessageDialog(null, "Aun no se anulo el comprobante");
                inicdoc.setVisible(true);
            } else {
                tipo = control.DevolverRegistroDto("select * from comprobantes where idTipoComprobante='"
                        + tipo + "' and esta='Activo'", 1);

                JOptionPane.showMessageDialog(tComprobante, "El tipo de comprobante es " + tipo);
                control.Sql = "update compventa set idComprobantes='" + tipo + "' where idComprobantes='"
                        + idcom + "'";
                control.EditarRegistro(control.Sql);
                control.Sql = "update comprobantes set esta='Emitido' where idComprobantes='" + tipo + "'";
                control.EditarRegistro(control.Sql);
                Cancelar();
                MostrarCommprobantes();
            }
        } else {
            JOptionPane.showMessageDialog(tComprobante, "Seleccione un comprobante");
        }
    }

    public void anularcomprobanteSIguiente() {
        /*if (bAnular.getText().compareTo("Anular Com.") == 0) {
           // cbTipoComrpo.setEnabled(true);
            bAnular.setText("Anular");
            bModificar.setEnabled(false);
            //txtNumComprobante.setEnabled(true);
        } else {
            if (cbTipoComrpo.getSelectedIndex() > -1) {
                String dato = "";
                control.Sql = "call  GeneraComprobante('1','" + cbTipoComrpo.getSelectedItem().toString()
                        + "');";
                dato = control.DevolverRegistroDto(control.Sql, 2);
                String a = "select * from tipocomprobante where tipcompr='" + cbTipoComrpo.getSelectedItem().toString() + "';";
                control.Sql = "select count(*) from comprobantes where nume='" + txtNumComprobante.getText()
                        + "' and idtipocomprobante='" + control.DevolverRegistroDto(a, 1) + "';";
                if (control.DevolverRegistroDto(control.Sql, 1).compareTo("0") == 0) {
                    control.Sql = "update comprobantes set esta='Anulado', nume='" + txtNumComprobante.getText()
                            + "' where idcomprobantes='" + dato + "';";
                    control.EditarRegistro(control.Sql);
                    bAnular.setText("Anular Com.");
                    MostrarCliente();
                    cbTipoComrpo.setSelectedIndex(-1);
                    cbTipoComrpo.setEnabled(false);
                    bModificar.setEnabled(true);
                    txtNumComprobante.setEnabled(false);
                } else {
                    control.Sql = "select esta from comprobantes where nume='" + txtNumComprobante.getText()
                            + "' and idtipocomprobante='" + control.DevolverRegistroDto(a, 1) + "';";
                    if (control.DevolverRegistroDto(control.Sql, 1).compareTo("Emitido") == 0) {
                        JOptionPane.showMessageDialog(null, "Comprobante ya fue emitido no se puede anular!!!");
                    } else {
                        if (control.DevolverRegistroDto(control.Sql, 1).compareTo("Anulado") == 0) {
                            JOptionPane.showMessageDialog(null, "Comprobante ya fue Anulado no se puede anular!!!");
                        } else {
                            control.Sql = "update comprobantes set esta='Anulado', nume='" + txtNumComprobante.getText() + "' where idcomprobantes='" + dato + "';";
                            control.EditarRegistro(control.Sql);
                            bAnular.setText("Anular Com.");
                            MostrarCliente();
                            cbTipoComrpo.setSelectedIndex(-1);
                            cbTipoComrpo.setEnabled(false);
                            bModificar.setEnabled(true);
                            txtNumComprobante.setEnabled(false);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un tipo de comprobante!!!");
            }
        }*/
    }

    public int vercuales() {
        int aa = 0;
        control.Sql = "SELECT count(*) FROM compventa where idcomprobantes='" + tComprobante.getValueAt(tComprobante.getSelectedRow(), 0).toString() + "';";
//        System.out.println(control.Sql);
        int sa = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
        if (sa == 1) {
            aa = 1;
        } else {
            aa = 2;
        }
        return aa;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        bAnular = new javax.swing.JButton();
        bModificar = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        bSalir1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        T_TiposComprobantes = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txSerie = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tComprobante = new javax.swing.JTable();
        txBuscar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        jPanel2.setName("jPanel2"); // NOI18N

        setClosable(true);
        setIconifiable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bAnular.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        bAnular.setForeground(new java.awt.Color(0, 51, 102));
        bAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        bAnular.setMnemonic('a');
        bAnular.setText("Anular Comprobante");
        bAnular.setName("bAnular"); // NOI18N
        bAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAnularActionPerformed(evt);
            }
        });
        jPanel1.add(bAnular, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 178, 40));

        bModificar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        bModificar.setForeground(new java.awt.Color(0, 51, 102));
        bModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Edit.png"))); // NOI18N
        bModificar.setMnemonic('e');
        bModificar.setText("Editar");
        bModificar.setName("bModificar"); // NOI18N
        bModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModificarActionPerformed(evt);
            }
        });
        jPanel1.add(bModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 130, 40));

        bCancelar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
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
        jPanel1.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 10, 130, 40));

        bSalir1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        bSalir1.setForeground(new java.awt.Color(0, 51, 102));
        bSalir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir1.setMnemonic('s');
        bSalir1.setText("Salir");
        bSalir1.setName("bSalir1"); // NOI18N
        bSalir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalir1ActionPerformed(evt);
            }
        });
        jPanel1.add(bSalir1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, 130, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 886, 58));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de tipos de comprobantes", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("Tipos de Comprobante");
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 160, 20));

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        T_TiposComprobantes.setModel(new javax.swing.table.DefaultTableModel(
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
        T_TiposComprobantes.setName("T_TiposComprobantes"); // NOI18N
        T_TiposComprobantes.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(T_TiposComprobantes);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 870, 170));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setText("Número Activo");
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 30, -1, 20));

        txSerie.setName("txSerie"); // NOI18N
        jPanel3.add(txSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 90, 90, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 405, 886, 200));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de  Comprobantes ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 51, 204))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tComprobante.setForeground(new java.awt.Color(0, 51, 102));
        tComprobante.setModel(new javax.swing.table.DefaultTableModel(
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
        tComprobante.setName("tComprobante"); // NOI18N
        tComprobante.getTableHeader().setReorderingAllowed(false);
        tComprobante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tComprobanteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tComprobante);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 870, 290));

        txBuscar.setName("txBuscar"); // NOI18N
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
        jPanel4.add(txBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 17, 820, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("Buscar");
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 17, -1, 20));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 69, 886, 340));

        pack();
    }// </editor-fold>//GEN-END:initComponents
private void bAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAnularActionPerformed
    if (JOptionPane.showConfirmDialog(null, "Desea anular el comprobante y Generar otro??", "", JOptionPane.YES_NO_OPTION) == 0) {
        AnulandocomprobanteUltimo();
    }
}//GEN-LAST:event_bAnularActionPerformed
private void bModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarActionPerformed
    if (tComprobante.getSelectedRow() < 0) {
        return;
    }
    if (modelo.getValueAt(tComprobante.getSelectedRow(), 4).toString().equalsIgnoreCase("Emitido")) {
        if (vercuales() == 1) {
            EditarComprobanteProducto comprobanteProducto = new EditarComprobanteProducto();
            Menu.jDesktopPane1.add(comprobanteProducto);
            InfoGeneral.setSerie(Integer.parseInt(modelo.getValueAt(tComprobante.getSelectedRow(), 0).toString()));
            comprobanteProducto.cargarDatosComprobante();
            comprobanteProducto.FilasArreglo = 0;
            comprobanteProducto.toFront();
            comprobanteProducto.setVisible(true);
            comprobanteProducto.setLocation(410, 90);
        } else {
//                EditarComprobanteServicio comprobanteServicio = new EditarComprobanteServicio();
//                Menu.jDesktopPane1.add(comprobanteServicio);
//                InfoGeneral.setSerie(Integer.parseInt(modelo.getValueAt(tComprobante.getSelectedRow(), 0).toString()));
//                comprobanteServicio.cargarDatosComprobante();
//                comprobanteServicio.toFront();
//                comprobanteServicio.setVisible(true);
//                comprobanteServicio.setLocation(300, 120);
            JOptionPane.showMessageDialog(null, "No se puede editar un Comprobante con Servicios");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Para modificar un comprobante debe de estar en estado emitido", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
    }
}//GEN-LAST:event_bModificarActionPerformed
private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
    Cancelar();
}//GEN-LAST:event_bCancelarActionPerformed
    private void txBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarKeyReleased
        MostrarCommprobantes();
    }//GEN-LAST:event_txBuscarKeyReleased
    private void tComprobanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tComprobanteMouseClicked
        if (evt.getClickCount() == 2) {
            if (modelo.getValueAt(tComprobante.getSelectedRow(), 4).toString().equalsIgnoreCase("Emitido")) {
                bModificar.doClick();
            } else {
                JOptionPane.showMessageDialog(null, "Solo se puede Modificar Comprobantes Emitidos");
            }
        }
    }//GEN-LAST:event_tComprobanteMouseClicked
    private void bSalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalir1ActionPerformed
        dispose();
    }//GEN-LAST:event_bSalir1ActionPerformed
    private void txBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txBuscarActionPerformed

    }//GEN-LAST:event_txBuscarActionPerformed
    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated

    }//GEN-LAST:event_formInternalFrameActivated

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown

    }//GEN-LAST:event_formComponentShown

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable T_TiposComprobantes;
    private javax.swing.JButton bAnular;
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bModificar;
    private javax.swing.JButton bSalir1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JTable tComprobante;
    private static javax.swing.JTextField txBuscar;
    private javax.swing.JTextField txSerie;
    // End of variables declaration//GEN-END:variables
}
