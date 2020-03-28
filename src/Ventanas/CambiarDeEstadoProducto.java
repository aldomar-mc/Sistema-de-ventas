/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

/**
 * ** @author Miguel Silva
 */
import Clases.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.*;

public class CambiarDeEstadoProducto extends javax.swing.JInternalFrame {

    /**
     * *************************LOS ATRIBUTOS***************************
     */
    Controlador control = new Controlador();
    InfoGeneral info = new InfoGeneral();
    Mimodelo modelo2 = new Mimodelo();
    Mimodelo modelo3 = new Mimodelo();
    private String cdbrr = "", idpro = "", idretiro = "";
    private int fila = 0, stock = 0;
    boolean b = false;

    /**
     * *************************FIN LOS ATRIBUTOS************************
     */

    /**
     * *************************LOS METODOS*****************************
     */
    public CambiarDeEstadoProducto() {
        initComponents();
        setTitle("Cambio de Estado de Productos!!");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        TProductosDisponibles.setModel(modelo2);
        modelo2.setColumnIdentifiers(new String[]{"ID", "Código", "Producto", "Marca",
             "Unidad", "Costo", "Precio", "Stock"});
        control.hideTableColumn(TProductosDisponibles, 0);
        control.setWidthTableColumn(TProductosDisponibles, 300, 2);
        MostrarProductosenDisponibles();
        tProductosaCambiar.setModel(modelo3);
        modelo3.setColumnIdentifiers(new String[]{"Código", "Producto", "Marca", "Unidad", "Cantidad",
            "Precio", "Costo"});
        control.setWidthTableColumn(tProductosaCambiar, 300, 1);
    }

    public void Agregar(int f) {
        control.Data[0] = TProductosDisponibles.getValueAt(fila, 1).toString();
        control.Data[1] = TProductosDisponibles.getValueAt(fila, 2).toString();
        control.Data[2] = TProductosDisponibles.getValueAt(fila, 3).toString();
        control.Data[3] = TProductosDisponibles.getValueAt(fila, 4).toString();
        control.Data[5] = TProductosDisponibles.getValueAt(fila, 6).toString();
        control.Data[6] = TProductosDisponibles.getValueAt(fila, 5).toString();
        if (f == -1) {
            control.Data[4] = txCantidad.getText();
            modelo3.addRow(control.Data);
        } else {
            control.Data[4] = Integer.toString(Integer.parseInt(tProductosaCambiar.getValueAt(f,
                    4).toString()) + Integer.parseInt(txCantidad.getText()));
            if (stock < Integer.parseInt(control.Data[4])) {
                JOptionPane.showMessageDialog(null, "La cantidad no puede superar el stock");
                control.MarcarTexto(txCantidad);
                return;
            } else {
                modelo3.setValueAt(control.Data[4], f, 4);
            }
        }

        limpiarcajas();
        txProducto.grabFocus();
    }

    private void cancelar() {
        stock = 0;
        cdbrr = "";
        idpro = "";
        txCantidad.setText(null);
        txProducto.setText(null);
        txtBucarProducto.setText("");
        TProductosDisponibles.clearSelection();
        control.LimTabla(modelo3);
        txObservacion.setText("");
    }

    public void Seleccionar() {
        if (TProductosDisponibles.getRowCount() > 0) {
            if (control.bandera) {
                fila = 0;
            } else {
                fila = TProductosDisponibles.getSelectedRow();
            }

            cdbrr = TProductosDisponibles.getValueAt(fila, 1).toString();
            stock = Integer.parseInt(TProductosDisponibles.getValueAt(fila, 7).toString());
            txProducto.setText(TProductosDisponibles.getValueAt(fila, 2).toString());
            txCantidad.grabFocus();
        }
    }

    public void CambiarEstado() {
        if (JOptionPane.showConfirmDialog(null, "Desea Grabar?",
                 "System Message", JOptionPane.YES_NO_OPTION) == 0) {
            /**
             * ******************CREAMOS EL RETIRO DE DEFECTUOSOS********************
             */
            int c = 0;
            control.fila = 0;
            control.Sql = "insert into RetiroDefectuosos(fecre,hra,obser,"
                    + "idusuario) values('" + control.Fecha() + "','" + control.Hora() + "','" + txObservacion.getText()
                    + "','" + InfoGeneral.idUsuario + "');";
            control.CrearRegistro(control.Sql);
            control.Sql = "select * from RetiroDefectuosos order by idRetiroDefectuosos desc limit 1";
            idretiro = control.DevolverRegistroDto(control.Sql, 1);
            /**
             * ******************CREAMOS EL RETIRO DE DEFECTUOSOS********************
             */

            /**
             * *********************Grabar el Detalle de los Productos Defectuosos*****************
             */
            while (control.fila < tProductosaCambiar.getRowCount()) {
                c = 1;
                cdbrr = tProductosaCambiar.getValueAt(control.fila, 0).toString();
                while (c <= Integer.parseInt(tProductosaCambiar.getValueAt(control.fila, 4).toString())) {
                    /**
                     * **********************Modifica estado del producto*****************************
                     */
                    control.Sql = "select * from producto where codbrr='" + cdbrr + "' and estdo='Disponible' and "
                            + " idsede='" + InfoGeneral.idSede + "' limit 1;";
                    idpro = control.DevolverRegistroDto(control.Sql, 1);
                    control.Sql = "update producto set estdo='Defectuoso' where idProducto='" + idpro
                            + "' and idSede='" + InfoGeneral.idSede + "';";
                    control.EditarRegistro(control.Sql);
                    /**
                     * **********************Modifica estado del producto*****************************
                     */

                    /**
                     * **********************Registrar el detalle del retiro*****************************
                     */
                    control.Sql = "insert into prodDefectuosos(idproducto,idRetiroDefectuosos,costo,precio) values('" + idpro + "','"
                            + idretiro + "','" + tProductosaCambiar.getValueAt(control.fila, 6).toString() + "','"
                            + tProductosaCambiar.getValueAt(control.fila, 5).toString() + "')";
                    control.CrearRegistro(control.Sql);
                    /**
                     * **********************Registrar el detalle del retiro*****************************
                     */
                    c++;
                }
                control.fila++;
            }
            cancelar();
            MostrarProductosenDisponibles();
            /**
             * *********************Grabar el Detalle de los Productos Defectuosos*****************
             */
        }

    }

    public void ValidarCantidad() {
        if (txCantidad.getText().equals("0")) {
            control.MarcarTexto(txCantidad);
            return;
        }
        if (Integer.parseInt(txCantidad.getText()) > stock) {
            JOptionPane.showMessageDialog(null, "No debe exceder la cantidad");
            control.MarcarTexto(txCantidad);
            return;
        } else {
            int fla = control.BuscarDatoenJtablePos(modelo3, cdbrr, 0);
            //JOptionPane.showMessageDialog(rootPane, "Fila encontrada "+fla);
            Agregar(fla);
        }
    }

    public void limpiarcajas() {
        txProducto.setText(null);
        txCantidad.setText(null);
    }

    public void MostrarProductosenDisponibles() {
        control.Sql = "SELECT " + " (SELECT p.`idProducto` FROM producto p WHERE "
                + "p.`Catalogoproducto_codctlg` " + "= c.`codctlg` AND p.`estdo`='Disponible' limit 1),"
                + " (SELECT p.`codbrr` FROM producto p WHERE p.`Catalogoproducto_codctlg` = "
                + "c.`codctlg` AND p.`estdo`='Disponible' limit 1), concat(mo.`nommod`,' ',c.`nomctlg`)AS `nomctlg`, "
                + "m.`nommrc`, u.`nomuni`, " + "c.precsg,c.`prexmayor`, (SELECT count(p.`Catalogoproducto_codctlg`) "
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
        control.LlenarJTabla(modelo2, control.Sql, 8);
    }

    public void EliminarDetalleProducto(KeyEvent evt) {
        if (evt.getKeyChar() == 127) {
            if (JOptionPane.showConfirmDialog(null, "Desea Eliminar este producto de la lista?",
                     "System Message", JOptionPane.YES_NO_OPTION) == 0) {
                modelo3.removeRow(tProductosaCambiar.getSelectedRow());
                MostrarProductosenDisponibles();
            }
        }
    }

    /**
     * *************************FIN LOS METODOS***************************
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        txProducto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Bgrabar = new javax.swing.JButton();
        Bsalir = new javax.swing.JButton();
        Bcancelar = new javax.swing.JButton();
        txCantidad = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tProductosaCambiar = new javax.swing.JTable();
        txObservacion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TProductosDisponibles = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtBucarProducto = new javax.swing.JTextField();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos para Cambio de Estado", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txProducto.setEditable(false);
        txProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txProductoActionPerformed(evt);
            }
        });
        jPanel2.add(txProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 25, 670, 20));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("Cantidad");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 27, -1, -1));

        Bgrabar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        Bgrabar.setForeground(new java.awt.Color(0, 0, 153));
        Bgrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save.png"))); // NOI18N
        Bgrabar.setMnemonic('g');
        Bgrabar.setText("Guardar");
        Bgrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BgrabarActionPerformed(evt);
            }
        });
        jPanel2.add(Bgrabar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 300, 130, 40));

        Bsalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        Bsalir.setForeground(new java.awt.Color(0, 0, 153));
        Bsalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        Bsalir.setMnemonic('s');
        Bsalir.setText("Salir");
        Bsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BsalirActionPerformed(evt);
            }
        });
        jPanel2.add(Bsalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 300, 130, 40));

        Bcancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        Bcancelar.setForeground(new java.awt.Color(0, 0, 153));
        Bcancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        Bcancelar.setMnemonic('c');
        Bcancelar.setText("Cancelar");
        Bcancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BcancelarActionPerformed(evt);
            }
        });
        jPanel2.add(Bcancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 300, 130, 40));

        txCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txCantidadActionPerformed(evt);
            }
        });
        txCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txCantidadKeyTyped(evt);
            }
        });
        jPanel2.add(txCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 25, 50, 20));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 153));
        jLabel7.setText("Producto:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 27, -1, -1));

        tProductosaCambiar.setModel(new javax.swing.table.DefaultTableModel(
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
        tProductosaCambiar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tProductosaCambiarKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tProductosaCambiar);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 950, 200));

        txObservacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txObservacionActionPerformed(evt);
            }
        });
        txObservacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txObservacionKeyTyped(evt);
            }
        });
        jPanel2.add(txObservacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, 880, 20));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setText("Observación");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 275, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 970, 350));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos en almacen", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 102))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TProductosDisponibles.setModel(new javax.swing.table.DefaultTableModel(
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
        TProductosDisponibles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TProductosDisponiblesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TProductosDisponibles);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 950, 210));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("Buscar");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

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
        jPanel3.add(txtBucarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 25, 470, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 270));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void BgrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BgrabarActionPerformed
        CambiarEstado();
    }//GEN-LAST:event_BgrabarActionPerformed
    private void txtBucarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBucarProductoKeyReleased
        MostrarProductosenDisponibles();
        limpiarcajas();
    }//GEN-LAST:event_txtBucarProductoKeyReleased
    private void TProductosDisponiblesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TProductosDisponiblesMouseClicked
        control.bandera = false;
        Seleccionar();
    }//GEN-LAST:event_TProductosDisponiblesMouseClicked
    private void txProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txProductoActionPerformed
    private void BsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BsalirActionPerformed
        dispose();
    }//GEN-LAST:event_BsalirActionPerformed
    private void BcancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BcancelarActionPerformed
        cancelar();
    }//GEN-LAST:event_BcancelarActionPerformed
    private void txtBucarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBucarProductoActionPerformed
        control.bandera = true;
        Seleccionar();
    }//GEN-LAST:event_txtBucarProductoActionPerformed
    private void txCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txCantidadActionPerformed
    private void txCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txCantidadKeyTyped
        control.Solonumeros(evt);
        if ((txCantidad.getText().trim().length() > 0) && (evt.getKeyChar() == 10)) {
            ValidarCantidad();
        }
    }//GEN-LAST:event_txCantidadKeyTyped

    private void txObservacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txObservacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txObservacionActionPerformed

    private void txObservacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txObservacionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txObservacionKeyTyped

    private void tProductosaCambiarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tProductosaCambiarKeyPressed
        EliminarDetalleProducto(evt);
    }//GEN-LAST:event_tProductosaCambiarKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bcancelar;
    private javax.swing.JButton Bgrabar;
    private javax.swing.JButton Bsalir;
    private javax.swing.JTable TProductosDisponibles;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tProductosaCambiar;
    private javax.swing.JTextField txCantidad;
    private javax.swing.JTextField txObservacion;
    private javax.swing.JTextField txProducto;
    private javax.swing.JTextField txtBucarProducto;
    // End of variables declaration//GEN-END:variables
}
