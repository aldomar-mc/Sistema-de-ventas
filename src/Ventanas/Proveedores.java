/** Proveedores.java *
 * Created on 13-ene-2014, 18:24:45
 */
package Ventanas;

/**
 * ** @author Ing. Miguel Angel Silva Zapata.   *********
 */
import javax.swing.*;
import Clases.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Proveedores extends javax.swing.JInternalFrame {

    /**
     * ***********************Atributos***************************************
     */
    Mimodelo modelo = new Mimodelo();
    Controlador control = new Controlador();
    DefaultTableModel modelo1 = new DefaultTableModel();
    String idprov;
    InfoGeneral info = new InfoGeneral();

    /**
     * ***********************Fin Atributos***********************************
     */

    /**
     * ***********************Métodos*****************************************
     */
    public void Cancelar() {
        txBuscar.setText(null);
        txDireccion.setText(null);
        txLetras.setText(null);
        txProveedor.setText(null);
        txRuc.setText(null);
        txTelefono.setText(null);
        tProveedores.clearSelection();
        txProveedor.requestFocus();
        bCrear.setEnabled(true);
        bEliminar.setEnabled(false);
        bModificar.setEnabled(false);
    }

    public void CrearProveedores() {
        control.bandera = false;
        if (txProveedor.getText().trim().length() == 0 || txLetras.getText().trim().length() == 0
                || txRuc.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Faltan Datos");
            txRuc.requestFocus();
        } else {
            control.Sql = "select * from proveedor where ltrs='" + txLetras.getText() + "'";
            if (control.Verificarconsulta(control.Sql)) {
                JOptionPane.showMessageDialog(rootPane, "Letras repetidas");
                txLetras.setSelectionStart(0);
                txLetras.setSelectionEnd(txLetras.getText().length());
            } else {
                control.Sql = "select * from proveedor where nompro='" + txProveedor.getText() + "'";
                if (control.Verificarconsulta(control.Sql)) {
                    JOptionPane.showMessageDialog(rootPane, "El proveedor ya existe");
                    txProveedor.setSelectionStart(0);
                    txProveedor.setSelectionEnd(txProveedor.getText().length());
                } else {
                    if (txRuc.getText().trim().length() > 0) {
                        if (txRuc.getText().trim().length() == 11) {
                            control.bandera = true;
                            control.Sql = "select * from ruc where numruc='" + txRuc.getText() + "'";
                            if (control.Verificarconsulta(control.Sql)) {
                                JOptionPane.showMessageDialog(rootPane, "Ya existe un proveedor con ese ruc");
                                return;
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "El ruc debe tener 11 digito este tiene " + txRuc.getText().trim().length());
                            control.MarcarTexto(txRuc);
                            return;
                        }
                    }
                    control.Sql = "insert into proveedor(nompro,dir,fono,ltrs) values('" + txProveedor.getText() + "','"
                            + txDireccion.getText() + "','" + txTelefono.getText() + "','" + txLetras.getText() + "')";
                    control.CrearRegistro(control.Sql);
                    if (control.bandera) {
                        idprov = control.ObtenerDato("proveedor", "nompro", txProveedor.getText(), 1);
                        control.Sql = "insert into ruc(numruc,idProveedor) values('" + txRuc.getText()
                                + "','" + idprov + "')";
                        control.CrearRegistro(control.Sql);
                        txBuscar.setText(txRuc.getText());
                    }

                    if (info.control == 1) {
                        this.dispose();
                        Ingresodeproductos.lbProveedor.setText(txProveedor.getText());
                        Ingresodeproductos.txBuscarproveedores.setText("");
                        Ingresodeproductos.controlado = 1;
                        info.setControlCliente(1);
                    }
                    Cancelar();
                    MostrarProveedores();
                }
            }
        }
    }

    public void setInternalFrame(JInternalFrame internalFrame) {
        internalFrame.setVisible(true);
    }

    public void ModificarProveedores() {
        idprov = tProveedores.getValueAt(control.fila, 0).toString();
        control.bandera = false;
        if (txLetras.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Faltan datos");
            txLetras.requestFocus();
        } else {
            if (txProveedor.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Falta el nombre del proveedor");
                txProveedor.grabFocus();
                return;
            }
            if (txDireccion.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Falta la dirección del proveedor");
                txDireccion.grabFocus();
                return;
            }
            if (txLetras.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Faltan las letras del proveedor");
                txLetras.grabFocus();
                return;
            }
            if (txRuc.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Falta el ruc del proveedor");
                txRuc.grabFocus();
                return;
            } else {
                if (txRuc.getText().trim().length() != 11) {
                    JOptionPane.showMessageDialog(null, "El ruc debe tener 11 digitos y este tiene " + txRuc.getText().trim().length());
                    control.MarcarTexto(txRuc);
                    return;
                }
            }
            control.Sql = "select * from proveedor where nompro='" + txProveedor.getText()
                    + "' and idProveedor<>'" + idprov + "'";
            control.bandera = control.Verificandoconsulta(control.Sql);
            if (!control.bandera) {
                control.Sql = "select * from vta_producto1 where Proveedor='" + txProveedor.getText() + "'";
                if ((control.bandera) && (control.Verificarconsulta(control.Sql))) {
                    control.bandera = true;
                } else {
                    control.bandera = false;
                }

                if (control.bandera) {
                    control.Sql = "update proveedor set dir='" + txDireccion.getText()
                            + "',fono='" + txTelefono.getText() + "'  where idProveedor='" + idprov + "'";
                } else {
                    control.Sql = "update proveedor set nompro='" + txProveedor.getText() + "',dir='"
                            + txDireccion.getText() + "',fono='" + txTelefono.getText() + "',ltrs='"
                            + txLetras.getText() + "' where idProveedor='" + idprov + "'";
                }
                control.EditarRegistro(control.Sql);
                control.Sql = "update ruc set numruc='" + txRuc.getText()
                        + "' where idproveedor='" + idprov + "'";
                control.EditarRegistro(control.Sql);
                if (info.control == 1) {
                    this.dispose();
                    Ingresodeproductos.lbProveedor.setText(tProveedores.getValueAt(tProveedores.getSelectedRow(),
                             2).toString());
                    Ingresodeproductos.txBuscarproveedores.setText("");
                    Ingresodeproductos.controlado = 1;
                }
                Cancelar();
                MostrarProveedores();
                bCrear.setEnabled(true);
                bEliminar.setEnabled(false);
                bModificar.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Proveedor Modificado Correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Ese nombre ya esta registrado");
            }
        }
    }

    public void EliminarProveedores() {
        idprov = tProveedores.getValueAt(control.fila, 0).toString();
        control.Sql = "SELECT * FROM proveedor p, inventarioinicial i WHERE p.idProveedor=i.idProveedor "
                + "AND p.idProveedor='" + idprov + "' GROUP BY p.idProveedor";
        if (!control.Verificandoconsulta(control.Sql)) {
            control.Sql = "SELECT * FROM proveedor p, compra cm WHERE p.idProveedor=cm.idProveedor AND "
                    + "p.idProveedor='" + idprov + "' GROUP BY p.idProveedor";
            if (!control.Verificandoconsulta(control.Sql)) {
                control.Sql = "SELECT * FROM proveedor p, cuentaban c WHERE p.idProveedor=c.idProveedor AND "
                        + "p.idProveedor='" + idprov + "' GROUP BY p.idProveedor";
                if (!control.Verificandoconsulta(control.Sql)) {
                    control.Sql = "DELETE FROM ruc WHERE idProveedor='" + idprov + "'";
                    control.EliminarRegistro(control.Sql);
                    control.Sql = "DELETE FROM proveedor WHERE idProveedor='" + idprov + "'";
                    control.EliminarRegistro(control.Sql);
                    Cancelar();
                    MostrarProveedores();
                    JOptionPane.showMessageDialog(null, "El Registro fue eliminado Exitosamente");
                } else {
                    JOptionPane.showMessageDialog(null, "El Proveedor ya tiene registros,"
                            + " no se puede eliminar");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El Proveedor ya tiene registros, no se puede eliminar");
            }
        } else {
            JOptionPane.showMessageDialog(null, "El Proveedor ya tiene registros, no se puede eliminar");
        }
    }

    public void VerProveedores() {
        control.fila = tProveedores.getSelectedRow();
        if (control.fila >= 0) {
            idprov = tProveedores.getValueAt(control.fila, 0).toString();
            txRuc.setText(tProveedores.getValueAt(control.fila, 1).toString());
            txProveedor.setText(tProveedores.getValueAt(control.fila, 2).toString());
            txDireccion.setText(tProveedores.getValueAt(control.fila, 3).toString());
            txTelefono.setText(tProveedores.getValueAt(control.fila, 4).toString());
            txLetras.setText(tProveedores.getValueAt(control.fila, 5).toString());
            bCrear.setEnabled(false);
            bEliminar.setEnabled(true);
            bModificar.setEnabled(true);
        }
    }

    public void MostrarProveedores() {
        BuscarProveedores();
    }

    public void BuscarProveedores() {
        control.Sql = "SELECT * FROM vta_losproveedores where ruc>1 and (proveedor like'%"
                + txBuscar.getText() + "%' or ruc like'%" + txBuscar.getText() + "%')";
        control.LlenarJTabla(modelo, control.Sql, 6);
    }

    public Proveedores() {
        initComponents();
        setTitle("Los Proveedores");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tProveedores.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"Código", "Ruc", "Proveedor",
            "Dirección", "Telefono", "Letras"});
        control.setWidthTableColumn(tProveedores, 240, 2);
        control.setWidthTableColumn(tProveedores, 350, 3);
        control.setWidthTableColumn(tProveedores, 45, 0, 1, 5);
        control.setWidthTableColumn(tProveedores, 80, 4);
        MostrarProveedores();
        control.forma_table_ver(modelo, tProveedores);
        TableRowSorter<TableModel> elorden = new TableRowSorter<TableModel>(modelo);
        tProveedores.setRowSorter(elorden);
        bEliminar.setEnabled(false);
        bModificar.setEnabled(false);
    }

    /**
     * ***********************Fin Métodos**************************************
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txProveedor = new javax.swing.JTextField();
        txTelefono = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txDireccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txRuc = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txLetras = new javax.swing.JTextField();
        bCrear = new javax.swing.JButton();
        bModificar = new javax.swing.JButton();
        bEliminar = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        bSalir = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tProveedores = new javax.swing.JTable();
        txBuscar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del proveedor", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Teléfono");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 30, -1, 20));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Proveedor");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 20));

        txProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txProveedorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txProveedorKeyReleased(evt);
            }
        });
        jPanel2.add(txProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 380, -1));

        txTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txTelefonoKeyPressed(evt);
            }
        });
        jPanel2.add(txTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(655, 30, 310, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Dirección");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        txDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txDireccionKeyPressed(evt);
            }
        });
        jPanel2.add(txDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 380, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Ruc");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 20));

        txRuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txRucKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txRucKeyTyped(evt);
            }
        });
        jPanel2.add(txRuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 330, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Letras");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 81, -1, 20));

        txLetras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txLetrasKeyPressed(evt);
            }
        });
        jPanel2.add(txLetras, new org.netbeans.lib.awtextra.AbsoluteConstraints(655, 80, 310, -1));

        bCrear.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCrear.setForeground(new java.awt.Color(0, 51, 102));
        bCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        bCrear.setMnemonic('c');
        bCrear.setText("Crear");
        bCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCrearActionPerformed(evt);
            }
        });
        jPanel2.add(bCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 110, 40));

        bModificar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bModificar.setForeground(new java.awt.Color(0, 51, 102));
        bModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Edit.png"))); // NOI18N
        bModificar.setMnemonic('e');
        bModificar.setText("Editar");
        bModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModificarActionPerformed(evt);
            }
        });
        jPanel2.add(bModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 130, 110, 40));

        bEliminar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bEliminar.setForeground(new java.awt.Color(0, 51, 102));
        bEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        bEliminar.setMnemonic('l');
        bEliminar.setText("Eliminar");
        bEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEliminarActionPerformed(evt);
            }
        });
        jPanel2.add(bEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, 110, 40));

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 51, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setMnemonic('a');
        bCancelar.setText("Cancelar");
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 130, 110, 40));

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setForeground(new java.awt.Color(0, 51, 102));
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setMnemonic('s');
        bSalir.setText("Salir");
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel2.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 130, 110, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 190));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tProveedores.setForeground(new java.awt.Color(0, 51, 102));
        tProveedores.setModel(new javax.swing.table.DefaultTableModel(
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
        tProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tProveedoresMouseClicked(evt);
            }
        });
        tProveedores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tProveedoresKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tProveedores);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 960, 240));

        txBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txBuscarKeyReleased(evt);
            }
        });
        jPanel4.add(txBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 380, -1));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("Buscar");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 980, 290));

        pack();
    }// </editor-fold>//GEN-END:initComponents
private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
    dispose();
}//GEN-LAST:event_bSalirActionPerformed
private void bCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCrearActionPerformed
    CrearProveedores();
}//GEN-LAST:event_bCrearActionPerformed
private void bModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarActionPerformed
    ModificarProveedores();
}//GEN-LAST:event_bModificarActionPerformed
private void bEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarActionPerformed
    EliminarProveedores();
}//GEN-LAST:event_bEliminarActionPerformed
private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
    Cancelar();
}//GEN-LAST:event_bCancelarActionPerformed
private void txRucKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txRucKeyTyped
    control.Solonumeros(evt);
    control.Longitudtx(txRuc, evt, 11);
}//GEN-LAST:event_txRucKeyTyped
private void txBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarKeyReleased
    BuscarProveedores();
}//GEN-LAST:event_txBuscarKeyReleased
private void tProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProveedoresMouseClicked
    if (info.control == 1) {
        Ingresodeproductos.lbProveedor.setText(tProveedores.getValueAt(tProveedores.getSelectedRow(), 2).toString());
        Ingresodeproductos.txBuscarproveedores.setText("");
        Ingresodeproductos.controlado = 1;
        this.dispose();
    } else {
        VerProveedores();
    }
}//GEN-LAST:event_tProveedoresMouseClicked
private void tProveedoresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tProveedoresKeyReleased
    VerProveedores();
}//GEN-LAST:event_tProveedoresKeyReleased

    private void txProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txProveedorKeyReleased
        control.Mayusculas(txProveedor);        // TODO add your handling code here:
    }//GEN-LAST:event_txProveedorKeyReleased

    private void txProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txProveedorKeyPressed
        if (evt.getKeyChar() == 10) {
            txTelefono.grabFocus();
        }
    }//GEN-LAST:event_txProveedorKeyPressed

    private void txTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txTelefonoKeyPressed
        if (evt.getKeyChar() == 10) {
            txDireccion.grabFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txTelefonoKeyPressed

    private void txDireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txDireccionKeyPressed
        if (evt.getKeyChar() == 10) {
            txLetras.grabFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txDireccionKeyPressed

    private void txLetrasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txLetrasKeyPressed
        if (evt.getKeyChar() == 10) {
            txRuc.grabFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txLetrasKeyPressed

    private void txRucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txRucKeyPressed
        if (evt.getKeyChar() == 10) {
            bCrear.doClick();
        }
    }//GEN-LAST:event_txRucKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bCrear;
    private javax.swing.JButton bEliminar;
    private javax.swing.JButton bModificar;
    private javax.swing.JButton bSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tProveedores;
    private javax.swing.JTextField txBuscar;
    private javax.swing.JTextField txDireccion;
    private javax.swing.JTextField txLetras;
    public static javax.swing.JTextField txProveedor;
    private javax.swing.JTextField txRuc;
    private javax.swing.JTextField txTelefono;
    // End of variables declaration//GEN-END:variables
}
