package Ventanas;

/**
 * ** @author Ing. Miguel Angel Silva Zapata.   *********
 */
import javax.swing.*;
import Clases.*;

public class Tipocomprobante extends javax.swing.JInternalFrame {

    /**
     * ***********************Atributos*********************
     */
    Mimodelo modelo = new Mimodelo();
    Controlador control = new Controlador();
    String codTipo = "";
    InfoGeneral info = new InfoGeneral();

    /**
     * ****************************Atributos**********************************
     */
    public Tipocomprobante() {
        initComponents();
        setTitle("Definiendo Comprobantes");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tTipocomprobante.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"ID", "Sede", "Ruc", "Tipo", "Serie", "Cant.Digitos", "Para"});
        control.LlenarCombo(cboSedes, "select * from sede where nomse<>'Almacen'", 2);
        control.MostrarEnCombo(Controlador.sede, cboSedes);
        cboSedes.setEnabled(false);
        MostrarTipoComprobante();
        control.forma_table_ver(modelo, tTipocomprobante);
        cboFacturacion.setSelectedItem("Productos");
        cboFacturacion.setVisible(false);
    }

    public void VolverRuc() {
        control.VolverdeTxatx(txruc);
        if (txruc.getText().trim().length() < 11) {
            txruc.grabFocus();
        }
    }

    public void MostrarTipoComprobante() {
        BuscarCliente();
    }

    public void BuscarCliente() {
        control.Sql = "SELECT * FROM vta_tipocomprobante  where (tipo like'%"
                + txBuscar.getText() + "%' or ruc like'" + txBuscar.getText() + "%' or sede like'%"
                + txBuscar.getText() + "%') and (Sede='" + Controlador.sede + "');";
        control.LlenarJTabla(modelo, control.Sql, 7);
    }

    public void Cancelar() {
        //txSerie.setEnabled(false); txtCantiDig.setEnabled(false);
        bEliminar.setEnabled(true);
        bCrear.setEnabled(true);
        bModificar.setEnabled(true);
        bCrear.setText("Crear");
        bModificar.setText("Editar");
        txruc.setText("");
        txSerie.setText("");
        txtCantiDig.setText("");
        cbotipo.setSelectedIndex(-1);
        cboFacturacion.setSelectedIndex(-1);
        MostrarTipoComprobante();
    }

    public void CrearTipo() {
        control.Sql = "SELECT count(*) FROM vta_tipocomprobante where sede='"
                + Controlador.sede.trim() + "' and ruc='" + txruc.getText() + "' and Tipo='"
                + cbotipo.getSelectedItem().toString() + "';";
        if (Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1)) == 0) {
            cboFacturacion.setSelectedIndex(0);
            control.Sql = "call CrearTipoComprobanteICECompured('0','" + cboSedes.getSelectedItem().toString()
                    + "','" + cbotipo.getSelectedItem().toString() + "','" + txSerie.getText() + "','" + txtCantiDig.getText()
                    + "','" + txruc.getText() + "','" + cboFacturacion.getSelectedItem().toString() + "','0')";
            JOptionPane.showMessageDialog(rootPane, control.CrearRegistroDev(control.Sql));
            LimpiaControles();
            MostrarTipoComprobante();
        } else {
            JOptionPane.showMessageDialog(null, "El Comprobante: " + cbotipo.getSelectedItem().toString()
                    + " ya fue creado para la Sede: " + Controlador.sede);
            cbotipo.grabFocus();
        }
    }

    public void LimpiaControles() {
        cbotipo.setSelectedIndex(-1);
        txBuscar.setText(null);
        txSerie.setText(null);
        txruc.setText(null);
        txtCantiDig.setText(null);
        tTipocomprobante.clearSelection();
        cboFacturacion.setSelectedIndex(-1);
        cboSedes.grabFocus();
    }

    public void CrearTipoComprobante() {
        if (bCrear.getText().compareTo("Crear") == 0) {
            txSerie.setEnabled(true);
            txtCantiDig.setEnabled(true);
            txSerie.grabFocus();
            bModificar.setEnabled(false);
            bEliminar.setEnabled(false);
            bCrear.setText("Grabar");
        } else {
            if (txSerie.getText().trim().length() > 0) {
                control.CrearRegistro(control.Sql);
                txSerie.setEnabled(false);
                txtCantiDig.setEnabled(false);
                txSerie.setText("");
                bModificar.setEnabled(true);
                bEliminar.setEnabled(true);
                bCrear.setText("Crear");
                control.LimTabla(modelo);
            } else {
                JOptionPane.showMessageDialog(null, "Tiene que llenar un Tipo de Comprobante");
            }
        }
    }

    public void EliminarTipoComprobante() {
        if (tTipocomprobante.getSelectedRowCount() == 1) {
            if (JOptionPane.showConfirmDialog(null, "Desea eliminar este registro!!", "", JOptionPane.YES_NO_OPTION) == 0) {
                control.Sql = "SELECT * FROM comprobantes WHERE idTipoComprobante='" + modelo.getValueAt(tTipocomprobante.getSelectedRow(), 0) + "'";
                if (!control.Verificandoconsulta(control.Sql)) {
                    control.Sql = "delete from tipocomprobante where idTipoComprobante='" + modelo.getValueAt(tTipocomprobante.getSelectedRow(), 0) + "';";
                    control.EliminarRegistro(control.Sql);
                    control.LimTabla(modelo);
                    BuscarCliente();
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede Eliminar el tipo pues ya se está Usando!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tiene Que Seleccionar un Clinete para Eliminar", "", 2);
        }
    }

    public void EditarTipoComprobante() {
        if (bModificar.getText().compareTo("Editar") == 0) {
            if (tTipocomprobante.getSelectedRowCount() > 0) {
                txruc.setText(modelo.getValueAt(tTipocomprobante.getSelectedRow(), 2).toString());
                txSerie.setText(modelo.getValueAt(tTipocomprobante.getSelectedRow(), 4).toString());
                cbotipo.setSelectedItem(modelo.getValueAt(tTipocomprobante.getSelectedRow(), 3).toString());
                txtCantiDig.setText(modelo.getValueAt(tTipocomprobante.getSelectedRow(), 5).toString());
                cboFacturacion.setSelectedItem(modelo.getValueAt(tTipocomprobante.getSelectedRow(), 6).toString());
                codTipo = modelo.getValueAt(tTipocomprobante.getSelectedRow(), 0).toString();
                bModificar.setText("Grabar");
                bEliminar.setEnabled(false);
                bCrear.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(null, "Selecione un Tipo de Comprobante");
            }
        } else {
            if (txSerie.getText().trim().length() > 0) {
                control.Sql = "update tipocomprobante set tipcompr='" + cbotipo.getSelectedItem().toString()
                        + "',candig='" + txtCantiDig.getText() + "',ruc='" + txruc.getText() + "',facde='"
                        + cboFacturacion.getSelectedItem().toString() + "' where idTipoComprobante='" + codTipo + "';";
                control.EditarRegistro(control.Sql);
                control.LimTabla(modelo);
                Cancelar();
                MostrarTipoComprobante();
                txSerie.setText("");
                bEliminar.setEnabled(true);
                txtCantiDig.setText("");
                bCrear.setEnabled(true);
                bModificar.setText("Editar");
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese Tipo de Comprobante");
            }
        }
    }

    /**
     * **************************Fin Atributos**********************************
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txSerie = new javax.swing.JTextField();
        txtCantiDig = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cboSedes = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        cbotipo = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txruc = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cboFacturacion = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        txBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tTipocomprobante = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bCrear = new javax.swing.JButton();
        bModificar = new javax.swing.JButton();
        bEliminar = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del tipo de comprobante", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setRequestFocusEnabled(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText(" ");
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, -1, 20));

        txSerie.setName("txSerie"); // NOI18N
        txSerie.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txSerieFocusGained(evt);
            }
        });
        txSerie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txSerieKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txSerieKeyTyped(evt);
            }
        });
        jPanel2.add(txSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 310, -1));

        txtCantiDig.setName("txtCantiDig"); // NOI18N
        txtCantiDig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantiDigActionPerformed(evt);
            }
        });
        txtCantiDig.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCantiDigFocusGained(evt);
            }
        });
        txtCantiDig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantiDigKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantiDigKeyTyped(evt);
            }
        });
        jPanel2.add(txtCantiDig, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, 270, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Cantidad de Digitos");
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, -1, 20));

        cboSedes.setName("cboSedes"); // NOI18N
        cboSedes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboSedesItemStateChanged(evt);
            }
        });
        jPanel2.add(cboSedes, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 29, 310, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Tipo");
        jLabel5.setName("jLabel5"); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, 20));

        cbotipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Boleta", "Factura", "Guia de Remision", "Nota de Venta", "Venta Libre", "Pedido", "Ticket", "Recibo", "Ticket Factura", "Ticket Boleta" }));
        cbotipo.setName("cbotipo"); // NOI18N
        cbotipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbotipoItemStateChanged(evt);
            }
        });
        cbotipo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbotipoFocusGained(evt);
            }
        });
        cbotipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbotipoKeyPressed(evt);
            }
        });
        jPanel2.add(cbotipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 310, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Sede");
        jLabel6.setName("jLabel6"); // NOI18N
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 20));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 102));
        jLabel7.setText("Serie");
        jLabel7.setName("jLabel7"); // NOI18N
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, 20));

        txruc.setName("txruc"); // NOI18N
        txruc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txrucFocusGained(evt);
            }
        });
        txruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txrucKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txrucKeyTyped(evt);
            }
        });
        jPanel2.add(txruc, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, 270, -1));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Ruc");
        jLabel8.setName("jLabel8"); // NOI18N
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 30, -1, 20));

        cboFacturacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Productos", "Servicios" }));
        cboFacturacion.setName("cboFacturacion"); // NOI18N
        cboFacturacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cboFacturacionFocusGained(evt);
            }
        });
        jPanel2.add(cboFacturacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, 270, -1));

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 130));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de tipos de comprobantes", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txBuscar.setName("txBuscar"); // NOI18N
        txBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txBuscarKeyReleased(evt);
            }
        });
        jPanel4.add(txBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 26, 430, -1));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tTipocomprobante.setForeground(new java.awt.Color(0, 51, 102));
        tTipocomprobante.setModel(new javax.swing.table.DefaultTableModel(
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
        tTipocomprobante.setName("tTipocomprobante"); // NOI18N
        jScrollPane1.setViewportView(tTipocomprobante);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 790, 210));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Buscar");
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 27, -1, 20));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 810, 270));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
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
        jPanel1.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 100, 40));

        bCrear.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCrear.setForeground(new java.awt.Color(0, 51, 102));
        bCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        bCrear.setMnemonic('c');
        bCrear.setText("Crear");
        bCrear.setName("bCrear"); // NOI18N
        bCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCrearActionPerformed(evt);
            }
        });
        jPanel1.add(bCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 100, 40));

        bModificar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
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
        jPanel1.add(bModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 10, 100, 40));

        bEliminar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bEliminar.setForeground(new java.awt.Color(0, 51, 102));
        bEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        bEliminar.setMnemonic('l');
        bEliminar.setText("Eliminar");
        bEliminar.setName("bEliminar"); // NOI18N
        bEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(bEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 10, 110, 40));

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 51, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setMnemonic('a');
        bCancelar.setText("Cancelar");
        bCancelar.setName("bCancelar"); // NOI18N
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(342, 10, 110, 40));

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, 570, 62));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents
private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
    dispose();
}//GEN-LAST:event_bSalirActionPerformed
private void bModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarActionPerformed
    EditarTipoComprobante();
}//GEN-LAST:event_bModificarActionPerformed
private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
    Cancelar();
}//GEN-LAST:event_bCancelarActionPerformed
    private void txBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarKeyReleased
        BuscarCliente();
    }//GEN-LAST:event_txBuscarKeyReleased
    private void bEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarActionPerformed
        EliminarTipoComprobante();
    }//GEN-LAST:event_bEliminarActionPerformed
    private void bCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCrearActionPerformed
        CrearTipo();
    }//GEN-LAST:event_bCrearActionPerformed
    private void txtCantiDigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantiDigActionPerformed

    }//GEN-LAST:event_txtCantiDigActionPerformed
    private void cboSedesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboSedesItemStateChanged
        txruc.grabFocus();
    }//GEN-LAST:event_cboSedesItemStateChanged
    private void txrucKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txrucKeyTyped
        control.Solonumeros(evt);
        control.Longitudtx(txruc, evt, 11);
    }//GEN-LAST:event_txrucKeyTyped
    private void txSerieKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txSerieKeyTyped
        control.Solonumeros(evt);
        control.Longitudtx(txSerie, evt, 5);
    }//GEN-LAST:event_txSerieKeyTyped
    private void txrucFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txrucFocusGained
        control.VolverDeCboaCbo(cboSedes);
    }//GEN-LAST:event_txrucFocusGained
    private void txSerieFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txSerieFocusGained
        VolverRuc();
    }//GEN-LAST:event_txSerieFocusGained
    private void cbotipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbotipoItemStateChanged
        //txtCantiDig.grabFocus();
    }//GEN-LAST:event_cbotipoItemStateChanged
    private void cbotipoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbotipoFocusGained
        control.VolverdeTxatx(txSerie);
    }//GEN-LAST:event_cbotipoFocusGained
    private void txtCantiDigFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantiDigFocusGained
        control.VolverDeCboaCbo(cbotipo);
    }//GEN-LAST:event_txtCantiDigFocusGained
    private void cboFacturacionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cboFacturacionFocusGained
        control.VolverdeTxatx(txtCantiDig);
    }//GEN-LAST:event_cboFacturacionFocusGained
    private void txtCantiDigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantiDigKeyTyped
        control.Solonumeros(evt);
        control.Longitudtx(txtCantiDig, evt, 1);
    }//GEN-LAST:event_txtCantiDigKeyTyped

    private void txrucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txrucKeyPressed
        if (evt.getKeyChar() == 10) {
            txSerie.grabFocus();
        }
    }//GEN-LAST:event_txrucKeyPressed

    private void txSerieKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txSerieKeyPressed
        if (evt.getKeyChar() == 10) {
            txtCantiDig.grabFocus();
        }
    }//GEN-LAST:event_txSerieKeyPressed

    private void txtCantiDigKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantiDigKeyPressed
        if (evt.getKeyChar() == 10) {
            cbotipo.grabFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantiDigKeyPressed

    private void cbotipoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbotipoKeyPressed
        if (evt.getKeyChar() == 10) {
            txtCantiDig.grabFocus();
        }
    }//GEN-LAST:event_cbotipoKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bCrear;
    private javax.swing.JButton bEliminar;
    private javax.swing.JButton bModificar;
    private javax.swing.JButton bSalir;
    private javax.swing.JComboBox cboFacturacion;
    private javax.swing.JComboBox cboSedes;
    private javax.swing.JComboBox cbotipo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tTipocomprobante;
    private javax.swing.JTextField txBuscar;
    private javax.swing.JTextField txSerie;
    private javax.swing.JTextField txruc;
    private javax.swing.JTextField txtCantiDig;
    // End of variables declaration//GEN-END:variables
}
