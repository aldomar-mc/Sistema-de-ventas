/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/** *** Unidad.java Created on 13-ene-2014, 15:09:48 ********* */
package Ventanas;

/**
 * ** @author Ing. Miguel Angel Silva Zapata.   *********
 */
import javax.swing.*;
import Clases.*;
import static Ventanas.Catalogoproductos.cboUnidades;

public class Unidad extends javax.swing.JInternalFrame {

    /**
     * ***********************Atributos*********************
     */
    Mimodelo modelo = new Mimodelo();
    Controlador control = new Controlador();
    String idUni;
    InfoGeneral info = new InfoGeneral();

    /**
     * ***********************Atributos*********************
     */

    /**
     * ***********************Métodos***********************
     */
    public void CrearUnidad() {
        if (txAbreviatura.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Faltan datos");
            txAbreviatura.requestFocus();
        } else {
            control.Sql = "select * from unidad where nomuni='" + txUnidad.getText()
                    + "' or abre='" + txAbreviatura.getText() + "'";
            if (control.Verificandoconsulta(control.Sql)) {
                JOptionPane.showMessageDialog(rootPane, "La unidad o abreviatura esta repetida");
            } else {
                control.Sql = "insert into unidad (nomuni,abre) values('" + control.PriLtrasMayuscula(txUnidad.getText())
                        + "','" + control.PriLtrasMayuscula(txAbreviatura.getText()) + "')";
                control.CrearRegistro(control.Sql);
                MostrarUnidad();
            }
            if (info.getGg() == 5) {

                control.LlenarCombo(Catalogoproductos.cboUnidades, "select * from unidad", 2);
            }
            Cancelar();
        }
    }

    public void Cancelar() {
        txAbreviatura.setText(null);
        txUnidad.setText(null);
        tUnidades.clearSelection();
        txBuscar.setText(null);
        txUnidad.requestFocus();
    }

    public void ModificarUnidad() {
        if (txAbreviatura.getText().length() > 0) {
            control.Sql = "select * from unidad where nomuni='" + txUnidad.getText()
                    + "' and abre='" + txAbreviatura.getText() + "' and idUnidad<>'" + idUni + "'";
            if (!control.Verificarconsulta(control.Sql)) {
                control.Sql = "update unidad set nomuni='" + txUnidad.getText() + "',"
                        + "abre='" + txAbreviatura.getText() + "' where idUnidad='" + idUni + "'";
                control.EditarRegistro(control.Sql);
                Cancelar();
                MostrarUnidad();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Existe repeticion en la unidad \n o en la abreviatura");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Faltan datos");
        }
    }

    public void EliminarUnidad() {
        control.fila = tUnidades.getSelectedRow();
        if (control.fila >= 0) {
            idUni = tUnidades.getValueAt(control.fila, 0).toString();
            control.Sql = "select * from catalogoproducto where idUnidad='" + idUni + "'";
            if (!control.Verificandoconsulta(control.Sql)) {
                if (JOptionPane.showConfirmDialog(rootPane, "Seguro deseas eliminar", "Confirmar", 0) == 0) {
                    control.Sql = "delete from unidad where idUnidad='" + idUni + "'";
                    control.EliminarRegistro(control.Sql);
                    Cancelar();
                    MostrarUnidad();
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Seleccione la fila a eliminar");
        }
    }

    public void VerUnidad() {
        control.fila = tUnidades.getSelectedRow();
        if (control.fila >= 0) {
            idUni = tUnidades.getValueAt(control.fila, 0).toString();
            txUnidad.setText(tUnidades.getValueAt(control.fila, 1).toString());
            txAbreviatura.setText(tUnidades.getValueAt(control.fila, 2).toString());
        }
    }

    public void MostrarUnidad() {
        BuscarUnidad();
    }

    public void BuscarUnidad() {
        control.Sql = "select * from unidad where nomuni like'" + txBuscar.getText() + "%'";
        control.LlenarJTabla(modelo, control.Sql, 3);
    }

    public void ValidardatosUnidad() {

    }

    public Unidad() {
        initComponents();
        setTitle("Unidades de los productos");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tUnidades.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"Código", "Unidad", "Abreviatura"});
        tUnidades.getColumnModel().getColumn(1).setPreferredWidth(250);
        bCrear.setMnemonic('c');
        bCancelar.setMnemonic('a');
        bModificar.setMnemonic('d');
        bEliminar.setMnemonic('e');
        bSalir.setMnemonic('s');
        MostrarUnidad();
        control.forma_table_ver(modelo, tUnidades);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bCrear = new javax.swing.JButton();
        bModificar = new javax.swing.JButton();
        bEliminar = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txUnidad = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txAbreviatura = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tUnidades = new javax.swing.JTable();
        txBuscar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setForeground(new java.awt.Color(0, 51, 102));
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setText("Salir");
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel1.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 100, 40));

        bCrear.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCrear.setForeground(new java.awt.Color(0, 51, 102));
        bCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        bCrear.setText("Crear");
        bCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCrearActionPerformed(evt);
            }
        });
        jPanel1.add(bCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 100, 40));

        bModificar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bModificar.setForeground(new java.awt.Color(0, 51, 102));
        bModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Edit.png"))); // NOI18N
        bModificar.setText("Editar");
        bModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModificarActionPerformed(evt);
            }
        });
        jPanel1.add(bModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 100, 40));

        bEliminar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bEliminar.setForeground(new java.awt.Color(0, 51, 102));
        bEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        bEliminar.setText("Eliminar");
        bEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(bEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 110, 40));

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 51, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setText("Cancelar");
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 110, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 580, 60));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Unidades", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txUnidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txUnidadKeyReleased(evt);
            }
        });
        jPanel2.add(txUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 490, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Unidad");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Abreviatura");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, 20));

        txAbreviatura.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txAbreviaturaFocusGained(evt);
            }
        });
        jPanel2.add(txAbreviatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 490, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 580, 80));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Unidades", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tUnidades.setForeground(new java.awt.Color(0, 51, 102));
        tUnidades.setModel(new javax.swing.table.DefaultTableModel(
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
        tUnidades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tUnidadesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tUnidades);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 560, 220));

        txBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txBuscarKeyReleased(evt);
            }
        });
        jPanel3.add(txBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 490, -1));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Buscar");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 20));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 580, 290));

        pack();
    }// </editor-fold>//GEN-END:initComponents
private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
    dispose();
}//GEN-LAST:event_bSalirActionPerformed
private void bCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCrearActionPerformed
    CrearUnidad();
}//GEN-LAST:event_bCrearActionPerformed
private void bModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarActionPerformed
    ModificarUnidad();
}//GEN-LAST:event_bModificarActionPerformed
private void bEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarActionPerformed
    EliminarUnidad();
}//GEN-LAST:event_bEliminarActionPerformed
private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
    Cancelar();
}//GEN-LAST:event_bCancelarActionPerformed
private void txBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarKeyReleased
    BuscarUnidad();
}//GEN-LAST:event_txBuscarKeyReleased
private void tUnidadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tUnidadesMouseClicked
    VerUnidad();
}//GEN-LAST:event_tUnidadesMouseClicked
private void txUnidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txUnidadKeyReleased

}//GEN-LAST:event_txUnidadKeyReleased
private void txAbreviaturaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txAbreviaturaFocusGained
    control.VolverdeTxatx(txUnidad);
}//GEN-LAST:event_txAbreviaturaFocusGained
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bCrear;
    private javax.swing.JButton bEliminar;
    private javax.swing.JButton bModificar;
    private javax.swing.JButton bSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tUnidades;
    private javax.swing.JTextField txAbreviatura;
    private javax.swing.JTextField txBuscar;
    private javax.swing.JTextField txUnidad;
    // End of variables declaration//GEN-END:variables
}
