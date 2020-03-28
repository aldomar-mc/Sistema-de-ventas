
package Ventanas;

import Clases.Controlador;
import Clases.IMPRIMIR;
import Clases.InfoGeneral;
import Clases.Mimodelo;
import Clases.Numero_a_Letra;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.omg.CORBA.INV_POLICY;

public class Facturarservicios extends javax.swing.JInternalFrame {

    private InfoGeneral info = new InfoGeneral();
    private Controlador control = new Controlador();
    private Mimodelo modeloClientes = new Mimodelo();
    private Mimodelo modeloServicios = new Mimodelo();
    private Mimodelo modeloServiciosFacturar = new Mimodelo();
    private String sr = "";
    private String srt = "";
    private Numero_a_Letra nume = new Numero_a_Letra();
    private IMPRIMIR imprime = new IMPRIMIR();
    private String idServicio = "", idCliente = null, idComprobanteServicio = null, dni;
    private int ppp = 0;
    private String idServicioEditar;
    private int idComprobanteGenerado = -1;
    private double suma=0;
    public Facturarservicios() {
        initComponents();
        setTitle("Facturación de servicios");
        tClientes.setModel(modeloClientes);
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        modeloClientes.setColumnIdentifiers(new String[]{"Código", "Cliente", "Dirección", "Ruc/Dni"});
        control.hideTableColumn(tClientes, 0);
        control.setWidthTableColumn(tClientes, 200, 1);
        control.setWidthTableColumn(tClientes, 100, 2, 3);

        tServicios.setModel(modeloServicios);
        modeloServicios.setColumnIdentifiers(new String[]{"Item", "Servicio", "Costo"});
        control.hideTableColumn(tServicios, 0);
        control.setMaxWidthColumnTable(tServicios, 60, 2);

        tListaServiciosVender.setModel(modeloServiciosFacturar);
        modeloServiciosFacturar.setColumnIdentifiers(new String[]{"Item", "Servicio", "Costo", "Cant."});
        control.hideTableColumn(tListaServiciosVender, 0);
        control.setMaxWidthColumnTable(tListaServiciosVender, 70, 2, 3);
        buscarServicio();
        buscarCliente();
        habilitarRegistroServicio(false);

        control.Sql = "SELECT * FROM tipocomprobante where idSede='"
                + control.ObtenerDato("sede", "nomse", Controlador.sede, 1) + "' and facde<>'Productos'";
        control.LlenarCombo(cbTipoComprobante, control.Sql, 2);
        control.forma_table_ver(modeloClientes, tClientes);
        control.forma_table_ver(modeloServicios, tServicios);
        control.forma_table_ver(modeloServiciosFacturar, tListaServiciosVender);
        

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tClientes = new javax.swing.JTable();
        txtBucarCliente = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        bClienete = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cbTipoComprobante = new javax.swing.JComboBox();
        txtNumComprobantes = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        checkTecnico = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lbNumeroSerie = new javax.swing.JLabel();
        ldident = new javax.swing.JLabel();
        lbSerNum = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        txtPrecioServicioFinal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        lbServicios = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbCliente = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txBuscarSer = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tServicios = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        panelServicios = new javax.swing.JPanel();
        lbNuevoSer = new javax.swing.JLabel();
        txtNombreServicio = new javax.swing.JTextField();
        lbCostoNuevoSer = new javax.swing.JLabel();
        txtCostoServicio = new javax.swing.JTextField();
        btnNuevoServicio = new javax.swing.JButton();
        btnEditarServicio = new javax.swing.JButton();
        btnCancelarServicio = new javax.swing.JButton();
        btnEliminarServicio = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tListaServiciosVender = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Clientes", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N

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
        jScrollPane1.setViewportView(tClientes);

        txtBucarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBucarClienteActionPerformed(evt);
            }
        });
        txtBucarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBucarClienteKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Buscar:");

        bClienete.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        bClienete.setForeground(new java.awt.Color(0, 51, 102));
        bClienete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Clien list.png"))); // NOI18N
        bClienete.setMnemonic('l');
        bClienete.setText("Agregar Cliente");
        bClienete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bClieneteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBucarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bClienete, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBucarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(bClienete, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del comprobante", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(0, 51, 204));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 102));
        jLabel7.setText("Tipo de Comprobante:");

        cbTipoComprobante.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cbTipoComprobante.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTipoComprobanteItemStateChanged(evt);
            }
        });
        cbTipoComprobante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbTipoComprobanteMouseClicked(evt);
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

        txtNumComprobantes.setEditable(false);
        txtNumComprobantes.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtNumComprobantes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNumComprobantesKeyReleased(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 51, 102));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Refresh-icon.png"))); // NOI18N
        jButton5.setText("Genera");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        checkTecnico.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        checkTecnico.setForeground(new java.awt.Color(0, 0, 102));
        checkTecnico.setMnemonic('t');
        checkTecnico.setText("Técnico");
        checkTecnico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkTecnicoActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 102));
        jLabel13.setText("Número Comprobante:");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 102));
        jLabel12.setText("  ");
        jLabel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbNumeroSerie.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbNumeroSerie.setForeground(new java.awt.Color(0, 51, 102));
        lbNumeroSerie.setText("   ");
        lbNumeroSerie.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        ldident.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ldident.setForeground(new java.awt.Color(0, 51, 102));
        ldident.setText("  ");
        ldident.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbSerNum.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbSerNum.setForeground(new java.awt.Color(0, 51, 102));
        lbSerNum.setText("   ");
        lbSerNum.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(336, 336, 336))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbNumeroSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumComprobantes, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cbTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbSerNum, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ldident, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(cbTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lbNumeroSerie)
                    .addComponent(txtNumComprobantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbSerNum, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ldident)
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkTecnico)
                    .addComponent(jLabel12)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        jButton2.setMnemonic('g');
        jButton2.setText("Grabar");
        jButton2.setPreferredSize(new java.awt.Dimension(110, 33));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        jButton3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 102));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        jButton3.setMnemonic('c');
        jButton3.setText("Cancelar");
        jButton3.setPreferredSize(new java.awt.Dimension(119, 33));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3);

        jButton1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton1.setMnemonic('s');
        jButton1.setText("Salir");
        jButton1.setPreferredSize(new java.awt.Dimension(97, 33));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cabecera de atencion", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(51, 0, 153))); // NOI18N

        txtPrecioServicioFinal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtPrecioServicioFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrecioServicioFinalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioServicioFinalKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 102));
        jLabel11.setText("Costo: ");

        lbServicios.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbServicios.setForeground(new java.awt.Color(0, 51, 102));
        lbServicios.setText("  ");
        lbServicios.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtCantidad.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtCantidad.setText("1");
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Cantidad:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 102));
        jLabel9.setText("Servicios:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Cliente:");

        lbCliente.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbCliente.setForeground(new java.awt.Color(0, 51, 102));
        lbCliente.setText("  ");
        lbCliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(137, 137, 137)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioServicioFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                    .addComponent(lbCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(lbServicios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbCliente)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lbServicios))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtPrecioServicioFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Servicios", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N

        txBuscarSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txBuscarSerKeyReleased(evt);
            }
        });

        tServicios.setForeground(new java.awt.Color(0, 51, 102));
        tServicios.setModel(new javax.swing.table.DefaultTableModel(
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
        tServicios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tServiciosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tServicios);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Buscar:");

        panelServicios.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbNuevoSer.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbNuevoSer.setForeground(new java.awt.Color(0, 51, 102));
        lbNuevoSer.setText("Servicio:");

        txtNombreServicio.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtNombreServicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreServicioKeyPressed(evt);
            }
        });

        lbCostoNuevoSer.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbCostoNuevoSer.setForeground(new java.awt.Color(0, 51, 102));
        lbCostoNuevoSer.setText("Costo:");

        txtCostoServicio.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtCostoServicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCostoServicioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostoServicioKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout panelServiciosLayout = new javax.swing.GroupLayout(panelServicios);
        panelServicios.setLayout(panelServiciosLayout);
        panelServiciosLayout.setHorizontalGroup(
            panelServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelServiciosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lbCostoNuevoSer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbNuevoSer, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelServiciosLayout.createSequentialGroup()
                        .addComponent(txtCostoServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtNombreServicio))
                .addContainerGap())
        );
        panelServiciosLayout.setVerticalGroup(
            panelServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelServiciosLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(panelServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNuevoSer)
                    .addComponent(txtNombreServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(panelServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCostoNuevoSer)
                    .addComponent(txtCostoServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        btnNuevoServicio.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnNuevoServicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add-icon.png"))); // NOI18N
        btnNuevoServicio.setMnemonic('N');
        btnNuevoServicio.setText("Nuevo");
        btnNuevoServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoServicioActionPerformed(evt);
            }
        });

        btnEditarServicio.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnEditarServicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pencil-icon16.png"))); // NOI18N
        btnEditarServicio.setMnemonic('M');
        btnEditarServicio.setText("Modificar");
        btnEditarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarServicioActionPerformed(evt);
            }
        });

        btnCancelarServicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Actions-edit-delete-icon.png"))); // NOI18N
        btnCancelarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarServicioActionPerformed(evt);
            }
        });

        btnEliminarServicio.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnEliminarServicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/trash-icon16.png"))); // NOI18N
        btnEliminarServicio.setMnemonic('E');
        btnEliminarServicio.setText("Eliminar");
        btnEliminarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarServicioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelServicios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txBuscarSer))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnEliminarServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNuevoServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelarServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(panelServicios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txBuscarSer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnNuevoServicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(btnEliminarServicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditarServicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelarServicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Servicios Prestados", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N

        tListaServiciosVender.setForeground(new java.awt.Color(0, 51, 102));
        tListaServiciosVender.setModel(new javax.swing.table.DefaultTableModel(
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
        tListaServiciosVender.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tListaServiciosVenderMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tListaServiciosVender);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jScrollPane3)
                .addGap(5, 5, 5))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void bClieneteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bClieneteActionPerformed
        txtBucarCliente.setText("");
        buscarCliente();
        LosClientes cli = new LosClientes();
        Menu.jDesktopPane1.add(cli);
        LosClientes.bCrear.doClick();
        LosClientes.txtCliente.grabFocus();
        info.setControl(3);
        cli.toFront();
        cli.setVisible(true);

    }//GEN-LAST:event_bClieneteActionPerformed
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        grabarServicio();
    }//GEN-LAST:event_jButton2ActionPerformed
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        cancelar();
    }//GEN-LAST:event_jButton3ActionPerformed
    private void btnNuevoServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoServicioActionPerformed
        nuevoServicio();
    }//GEN-LAST:event_btnNuevoServicioActionPerformed
    private void tClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tClientesMouseClicked
        if (evt.getClickCount() == 2) {
            SelecionarCliente();
        }
    }//GEN-LAST:event_tClientesMouseClicked
    private void txtPrecioServicioFinalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioServicioFinalKeyPressed
        if (evt.getKeyChar() == 10) {
            agregarServicoLista();
        }
    }//GEN-LAST:event_txtPrecioServicioFinalKeyPressed
    private void tServiciosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tServiciosMouseClicked
        if (evt.getClickCount() >= 2) {
            if (lbCliente.getText().trim().length() > 0) {
                SelecionarServicio();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_tServiciosMouseClicked
    private void cbTipoComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoComprobanteActionPerformed

        ldident.setText(" ");
        txtNumComprobantes.setText("");
        //        if (ppp > 0) {
//            if (cbTipoCompro.getSelectedIndex() > -1) {
//                GenerarNuevoComrpobante();
//            }
//        }        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoComprobanteActionPerformed
    private void cbTipoComprobanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbTipoComprobanteMouseClicked

        ldident.setText(" ");
        txtNumComprobantes.setText("");
        //        ppp++;
//        if (ppp > 0) {
//            if (cbTipoCompro.getSelectedIndex() > -1) {
//                GenerarNuevoComrpobante();
//            }
//        }        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoComprobanteMouseClicked
    private void cbTipoComprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbTipoComprobanteKeyPressed
        ldident.setText(" ");
        txtNumComprobantes.setText("");

        //        if (cbTipoCompro.getSelectedIndex() > -1) {
//            GenerarNuevoComrpobante();
//            //      }
//        }
    }//GEN-LAST:event_cbTipoComprobanteKeyPressed
    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
        if (evt.getKeyChar() == 10) {
            if (txtCantidad.getText().trim().length() > 0) {
                txtPrecioServicioFinal.setText("" + (Integer.parseInt(txtCantidad.getText().toString()) * Integer.parseInt(txtPrecioServicioFinal.getText().toString())));
                txtPrecioServicioFinal.grabFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese la Cantidad!!");
            }
        }
    }//GEN-LAST:event_txtCantidadKeyPressed
    private void txtBucarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBucarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBucarClienteActionPerformed
    private void txtBucarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBucarClienteKeyReleased
        buscarCliente();
    }//GEN-LAST:event_txtBucarClienteKeyReleased
    private void txtNumComprobantesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumComprobantesKeyReleased
        lbSerNum.setText(sr + "-" + txtNumComprobantes.getText());
    }//GEN-LAST:event_txtNumComprobantesKeyReleased
    private void tListaServiciosVenderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tListaServiciosVenderMouseClicked
        if (evt.getClickCount() == 2) {
            if (tListaServiciosVender.getSelectedRowCount() == 1) {
                modeloServiciosFacturar.removeRow(tListaServiciosVender.getSelectedRow());
            }
        }
    }//GEN-LAST:event_tListaServiciosVenderMouseClicked
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        generarComprobante();
    }//GEN-LAST:event_jButton5ActionPerformed
    private void txBuscarSerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarSerKeyReleased
        buscarServicio();
    }//GEN-LAST:event_txBuscarSerKeyReleased
    private void cbTipoComprobanteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTipoComprobanteItemStateChanged

    }//GEN-LAST:event_cbTipoComprobanteItemStateChanged
    private void checkTecnicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkTecnicoActionPerformed
        buscarTecnico();
    }//GEN-LAST:event_checkTecnicoActionPerformed

    private void txtCostoServicioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoServicioKeyTyped
        control.decimal(evt, txtCostoServicio.getText());
    }//GEN-LAST:event_txtCostoServicioKeyTyped

    private void btnCancelarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarServicioActionPerformed
        cancelarRegistroServicio();
    }//GEN-LAST:event_btnCancelarServicioActionPerformed

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        control.Solonumeros(evt);
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtPrecioServicioFinalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioServicioFinalKeyTyped
        control.decimal(evt, txtPrecioServicioFinal.getText());
    }//GEN-LAST:event_txtPrecioServicioFinalKeyTyped

    private void txtNombreServicioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreServicioKeyPressed
        if (control.isEnterKey(evt)) {
            txtCostoServicio.requestFocus();
        }
    }//GEN-LAST:event_txtNombreServicioKeyPressed

    private void txtCostoServicioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoServicioKeyPressed
        if (control.isEnterKey(evt)) {
            if (btnNuevoServicio.isEnabled()) {
                btnNuevoServicio.doClick();
            } else {
                btnEditarServicio.doClick();
            }
        }
    }//GEN-LAST:event_txtCostoServicioKeyPressed

    private void btnEditarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarServicioActionPerformed
        editarServicio();
    }//GEN-LAST:event_btnEditarServicioActionPerformed

    private void btnEliminarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarServicioActionPerformed
        eliminarServicio();
    }//GEN-LAST:event_btnEliminarServicioActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bClienete;
    private javax.swing.JButton btnCancelarServicio;
    private javax.swing.JButton btnEditarServicio;
    private javax.swing.JButton btnEliminarServicio;
    private javax.swing.JButton btnNuevoServicio;
    private javax.swing.JComboBox cbTipoComprobante;
    private javax.swing.JCheckBox checkTecnico;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    public static javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JLabel lbCliente;
    private javax.swing.JLabel lbCostoNuevoSer;
    private javax.swing.JLabel lbNuevoSer;
    private javax.swing.JLabel lbNumeroSerie;
    private javax.swing.JLabel lbSerNum;
    private javax.swing.JLabel lbServicios;
    public static javax.swing.JLabel ldident;
    private javax.swing.JPanel panelServicios;
    private javax.swing.JTable tClientes;
    private javax.swing.JTable tListaServiciosVender;
    private javax.swing.JTable tServicios;
    private javax.swing.JTextField txBuscarSer;
    public static javax.swing.JTextField txtBucarCliente;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCostoServicio;
    private javax.swing.JTextField txtNombreServicio;
    private javax.swing.JTextField txtNumComprobantes;
    private javax.swing.JTextField txtPrecioServicioFinal;
    // End of variables declaration//GEN-END:variables

    private void buscarTecnico() {
        String tec = "";
        if (txtNumComprobantes.getText().length() == 0 || idComprobanteGenerado == -1) {
            JOptionPane.showMessageDialog(this, "¿Debe generar el comprobante de pago?", "Mensaje", JOptionPane.WARNING_MESSAGE);
            checkTecnico.setSelected(false);
            return;
        }
        if (checkTecnico.isSelected()) {
            dni = JOptionPane.showInputDialog(this, "Ingrese el N° de DNI del técnico: ", "DNI", JOptionPane.QUESTION_MESSAGE);
            control.Sql = "select * from tecnico where dni='" + dni + "'";
            ldident.setText(control.DevolverRegistroDto(control.Sql, 1));
            tec = control.DevolverRegistroDto(control.Sql, 2);
            if (tec.trim().length() == 0) {
                if (JOptionPane.showConfirmDialog(rootPane, "No se ha encontrado el técnico con el DNI especificado.\n¿Desea continuar buscando?", "Confirme", 0) == 0) {
                    jLabel12.setText(" ");
                    Tecnico tecni = new Tecnico();
                    Menu.jDesktopPane1.add(tecni);// Tecnico.bCrear.doClick();
                    Tecnico.txtdni.grabFocus();
                    InfoGeneral.setControl(3);
                    tecni.toFront();
                    tecni.setVisible(true);
                }
            } else {
                jLabel12.setText(tec);
            }
        } else {
            jLabel12.setText(" ");
            ldident.setText(" ");
        }
    }

    private void habilitarRegistroServicio(boolean a) {
        panelServicios.setVisible(a);
        txtNombreServicio.setEnabled(a);
        txtCostoServicio.setEnabled(a);
        txtNombreServicio.setText("");
        txtCostoServicio.setText("0.00");
    }

    private void buscarCliente() {
        control.Sql = "SELECT idcliente, nomclie,dir, numident "
                + "FROM vta_cliente v where nomclie like '%" + txtBucarCliente.getText() + "%' "
                + "or numident like '%" + txtBucarCliente.getText() + "%'";
        control.LlenarJTabla(modeloClientes, control.Sql, 4);

        //    JOptionPane.showMessageDialog(null,control.Sql);
    }

    private void buscarServicio() {
        control.Sql = "SELECT * FROM servicio where descriser like '%" + txBuscarSer.getText() + "%' ";
        control.LlenarJTabla(modeloServicios, control.Sql, 3);
    }

    private void SelecionarCliente() {
        if (tClientes.getSelectedRowCount() == 1) {
            lbCliente.setText(tClientes.getValueAt(tClientes.getSelectedRow(), 1).toString());
            idCliente = tClientes.getValueAt(tClientes.getSelectedRow(), 0).toString();
            //info.setPass(idCliente);
        }
    }

    private void agregarServicoLista() {
        if (txtPrecioServicioFinal.getText().trim().length() > 0 && lbServicios.getText().trim().length() > 0) {
            String dd[] = new String[4];
            dd[0] = idServicio;
            dd[1] = lbServicios.getText();
            dd[2] = ""+Double.parseDouble(txtPrecioServicioFinal.getText())/Integer.parseInt(txtCantidad.getText());
            dd[3] = txtCantidad.getText();
            modeloServiciosFacturar.addRow(dd);
            lbServicios.setText(" ");
            txtPrecioServicioFinal.setText("");
            idServicio = "";
            txtCantidad.setText("1");
            buscarServicio();
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un Servicio para agregar a la lista!!");
        }
    }

    private void SelecionarServicio() {
        if (tServicios.getSelectedRowCount() == 1) {
            lbServicios.setText(tServicios.getValueAt(tServicios.getSelectedRow(), 1).toString());
            idServicio = tServicios.getValueAt(tServicios.getSelectedRow(), 0).toString();
            txtPrecioServicioFinal.setText(tServicios.getValueAt(tServicios.getSelectedRow(), 2).toString());
            txtCantidad.grabFocus();
        }
    }

    private void GenerarNuevoComrpobante() {
        if (tListaServiciosVender.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "No hay servicios que facturar");
            cbTipoComprobante.setSelectedIndex(-1);
            return;
        }
        if (cbTipoComprobante.getSelectedIndex() >= 0) {

            control.Sql = "call  GeneraComprobante('1','" + cbTipoComprobante.getSelectedItem().toString() + "');";
            String da = control.DevolverRegistroDto(control.Sql, 1);
            StringTokenizer tokenizer = new StringTokenizer(da, "-");
            sr = tokenizer.nextToken();
            srt = tokenizer.nextToken();
            lbSerNum.setText(control.DevolverRegistroDto(control.Sql, 1));//lbSerie.setText(dato);
            lbNumeroSerie.setText(sr);
            txtNumComprobantes.setText(srt);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un tipo de comprobante!!");
        }
    }

    private void cancelar() {
        habilitarRegistroServicio(false);
        lbCliente.setText(" ");
        lbServicios.setText(" ");
        txtPrecioServicioFinal.setText("");
        control.LimTabla(modeloServiciosFacturar);
        txtCantidad.setText("1");
        buscarCliente();
        txtNumComprobantes.setText("");
        buscarServicio();
        ppp = 0;
        cbTipoComprobante.setSelectedIndex(-1);
        ldident.setText(" ");
        lbSerNum.setText(" ");
        idComprobanteGenerado = -1;
        idComprobanteServicio = null;
        idCliente = null;
        lbNumeroSerie.setText(" ");
    }

    private void grabarServicio() {
        if (idCliente != null) {
            if (modeloServiciosFacturar.getRowCount() > 0) {
                if (idComprobanteGenerado != -1) {
                    generarComprobante();
                    double total = 0;
                    for (int i = 0; i < modeloServiciosFacturar.getRowCount(); i++) {
                        double costo = Double.parseDouble(modeloServiciosFacturar.getValueAt(i, 2).toString());
                        double cantidad = Double.parseDouble(modeloServiciosFacturar.getValueAt(i, 3).toString());
                        total += costo * cantidad;
                    }
                    control.Sql = "select InsertaServiciopre(curdate(),'" + idComprobanteGenerado + "','" + idCliente + "'," + total + ");";
                    idComprobanteServicio = control.getValueQuery(control.Sql);
                    if (idComprobanteServicio != null) {
                        control.fila = 0;
                        while (control.fila < tListaServiciosVender.getRowCount()) {
                            control.Sql = "insert into serviciodetalle (idservicio, cost, idcomporbanteServicio, cant)values('"
                                    + modeloServiciosFacturar.getValueAt(control.fila, 0).toString() + "','" + modeloServiciosFacturar.getValueAt(control.fila, 2).toString()
                                    + "','" + idComprobanteServicio + "','" + modeloServiciosFacturar.getValueAt(control.fila, 3).toString() + "');";

                            control.ejecutar(control.Sql);
                            suma=suma+(Double.parseDouble(modeloServiciosFacturar.getValueAt(control.fila, 2).toString())*Integer.parseInt(modeloServiciosFacturar.getValueAt(control.fila, 3).toString()));
                            control.fila++;
                            
                        }
                        control.Sql = "update comprobantes set esta='Emitido',nume='" + txtNumComprobantes.getText() + "' where idcomprobantes='" + idComprobanteGenerado + "';";
                        control.EditarRegistro(control.Sql);

                        imprimir();
                        cancelar();

                    } else {
                        JOptionPane.showMessageDialog(this, "Se ha generado un error interno al guardar los datos", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Debe generar el comprobante para guardar la prestación de servicio", "Mensaje", JOptionPane.INFORMATION_MESSAGE);

                }
            } else {
                JOptionPane.showMessageDialog(this, "La facturación del servicio no puede continuar, ya que no hay ningún servicio prestado en la lista de facturación", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar el cliente para facturar el servicio", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void imprimir() {
        Map map = new HashMap();
        map.put("idServicioComprobante", idComprobanteServicio);
        map.put("totalnum", new Numero_a_Letra().Convertir(""+suma, true));
        String idSede = InfoGeneral.getIdSede();
        if (idSede.equals("3")) {//Infotel
            if ("Factura".equals(cbTipoComprobante.getSelectedItem().toString())) {
                
                control.showReportDialog("Factura de servicio", "facturaServicioInfotel", map);
            } else if ("Boleta".equals(cbTipoComprobante.getSelectedItem().toString())) {
                control.showReportDialog("Factura de servicio", "boletaServicioInfotel", map);
            } else {
                JOptionPane.showMessageDialog(this, "No hay un diseño específico para este tipo de comprobante", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {//ICE
            if ("Factura".equals(cbTipoComprobante.getSelectedItem().toString())) {
                control.showReportDialog("Factura de servicio", "facturaServicioICE", map);
            } else if ("Boleta".equals(cbTipoComprobante.getSelectedItem().toString())) {
                control.showReportDialog("Factura de servicio", "boletaServicioICE", map);
            } else {
                JOptionPane.showMessageDialog(this, "No hay un diseño específico para este tipo de comprobante", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
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
        control.Sql = "SELECT month(fecha) FROM imprimircoprobanteservicio where  idcomporbanteservicio='" + codv + "' group by idcomporbanteservicio;";
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
        control.Sql = "SELECT sum(cost*cant) FROM imprimircoprobanteservicio where  idcomporbanteservicio='" + codven + "';";
        String sumto = control.DevolverRegistroDto(control.Sql, 1);
        String numer = nume.Convertir(sumto, band());
        return numer;
    }

    private void cancelarRegistroServicio() {
        habilitarRegistroServicio(false);
        btnNuevoServicio.setText("Nuevo");
        btnNuevoServicio.setMnemonic('N');
        btnEditarServicio.setText("Modificar");
        btnEditarServicio.setMnemonic('M');
        btnNuevoServicio.setEnabled(true);
        btnEditarServicio.setEnabled(true);
        btnEliminarServicio.setEnabled(true);
        tServicios.setEnabled(true);
        txBuscarSer.setEditable(true);

    }

    private void nuevoServicio() {
        if (btnNuevoServicio.getText().equals("Nuevo")) {
            habilitarRegistroServicio(true);
            txtNombreServicio.requestFocus();
            btnEditarServicio.setEnabled(false);
            btnEliminarServicio.setEnabled(false);
            tServicios.setEnabled(false);
            txBuscarSer.setEditable(false);
            btnNuevoServicio.setText("Grabar");
            btnNuevoServicio.setMnemonic('G');

        } else {
            double costo = control.toDouble(txtCostoServicio.getText());
            if (txtNombreServicio.getText().trim().length() > 0) {
                control.Sql = "call Servicios('0','" + txtNombreServicio.getText() + "','" + costo + "','0')";
                String valueQuery = control.getValueQuery(control.Sql);
                if (valueQuery.equals("1")) {
                    buscarServicio();
                    lbServicios.setText(txtNombreServicio.getText());
                    txtPrecioServicioFinal.setText(txtCostoServicio.getText());
                    control.Sql = "select idservicio from servicio where descriser='" + txtNombreServicio.getText() + "';";
                    idServicio = control.DevolverRegistroDto(control.Sql, 1);
                    txtCantidad.grabFocus();
                    txtCantidad.selectAll();
                    txtCantidad.setText("1");
                    cancelarRegistroServicio();
                    JOptionPane.showMessageDialog(this, "El servicio se ha registrado satisfactoriamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Ya existe un servicio con el nombre indicado, especifique otro e intente nuevamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Indique el nombre del servicio", "Mensaje", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void editarServicio() {
        int selectedRow = tServicios.getSelectedRow();
        if (selectedRow >= 0) {
            if (btnEditarServicio.getText().equals("Modificar")) {
                habilitarRegistroServicio(true);
                idServicioEditar = modeloServicios.getValueAt(selectedRow, 0).toString();
                txtNombreServicio.setText(modeloServicios.getValueAt(selectedRow, 1).toString());
                txtCostoServicio.setText(modeloServicios.getValueAt(selectedRow, 2).toString());
                btnEditarServicio.setText("Grabar");
                btnEditarServicio.setMnemonic('G');
                btnNuevoServicio.setEnabled(false);
                btnEliminarServicio.setEnabled(false);
                tServicios.setEnabled(false);
                txBuscarSer.setEditable(false);
            } else {
                double costo = control.toDouble(txtCostoServicio.getText());
                if (txtNombreServicio.getText().trim().length() > 0) {
                    control.Sql = "call Servicios('" + idServicioEditar + "','" + txtNombreServicio.getText() + "','" + costo + "','1')";
                    String valueQuery = control.getValueQuery(control.Sql);
                    if (valueQuery.equals("1")) {
                        buscarServicio();
                        cancelarRegistroServicio();
                        txBuscarSer.requestFocus();
                        JOptionPane.showMessageDialog(this, "El servicio se ha registrado satisfactoriamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Ya existe un servicio con el nombre indicado, especifique otro e intente nuevamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Indique el nombre del servicio", "Mensaje", JOptionPane.WARNING_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione el servicio que desea eliminar", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void eliminarServicio() {
        int row = tServicios.getSelectedRow();
        if (row >= 0) {
            boolean ejecutar = control.ejecutar(String.format("DELETE FROM servicio WHERE `idServicio`='%s';", modeloServicios.getValueAt(row, 0).toString()));
            if (ejecutar) {
                buscarServicio();
                JOptionPane.showMessageDialog(this, "El servicio seleccionado se ha eliminado correctamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "El servicio seleccionado no se ha podido eliminar, ya que actualmente está en uso", "Mensaje", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione el servicio que desea eliminar", "Mensaje", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void generarComprobante() {
        if (tListaServiciosVender.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay servicios en la lista para facturar", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (cbTipoComprobante.getSelectedIndex() >= 0) {
                if(cbTipoComprobante.getSelectedItem().equals("Factura")){
                     control.Sql = "call ElComprobante('0','" + Controlador.sede + "','"
                        + cbTipoComprobante.getSelectedItem().toString() + "','Productos','0','1')";
                }else{
                     control.Sql = "call ElComprobante('0','" + Controlador.sede + "','"
                        + cbTipoComprobante.getSelectedItem().toString() + "','Servicios','0','1')";
                }
               
                if (control.DevolverRegistroDto(control.Sql, 1).equals("Se debe iniciar este tipo de comprobante")) {
                    JOptionPane.showMessageDialog(cbTipoComprobante, "No hay documentos de ese tipo, por favor primer incie la numeración del documento", "Mensaje", JOptionPane.WARNING_MESSAGE);

                    IniciandoComprobantesICECompured inicdoc = new IniciandoComprobantesICECompured();
                    Menu.jDesktopPane1.add(inicdoc);
                    inicdoc.toFront();
                    inicdoc.setLocation(250, 250);
                    inicdoc.setVisible(true);
                } else {
                    txtNumComprobantes.setText(control.DevolverRegistroDto(control.Sql, 1));
                    lbNumeroSerie.setText(control.DevolverRegistroDto(control.Sql, 2));
                    idComprobanteGenerado = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 3));
                }
            } else {
                JOptionPane.showMessageDialog(this, "Indique el tipo de comprobante a generar", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

}
