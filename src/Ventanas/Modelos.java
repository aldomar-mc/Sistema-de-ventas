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
import static Ventanas.Catalogoproductos.cboModelos;

public class Modelos extends javax.swing.JInternalFrame {

    /**
     * ***********************Atributos*********************
     */
    Mimodelo modelo = new Mimodelo();
    Controlador control = new Controlador();
    String idMdl;
    InfoGeneral info = new InfoGeneral();

    /**
     * ***********************Atributos*********************
     */
    public void CrearModelo() {
        if (txModelo.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Faltan datos");
            txModelo.requestFocus();
        } else {
            control.Sql = "select * from modelo where nommod='" + txModelo.getText() + "'";
            if (control.Verificandoconsulta(control.Sql)) {
                JOptionPane.showMessageDialog(rootPane, "El Modelo esta repetido");
            } else {
                control.Sql = "insert into modelo (nommod) values('" + control.PriLtrasMayuscula(txModelo.getText())
                        + "')";
                control.CrearRegistro(control.Sql);
                MostrarModelo();
            }
            if (info.getGg() == 5) {
                control.LlenarCombo(Catalogoproductos.cboModelos, "select * from modelo", 2);
            }
            Cancelar();
        }
    }

    public void ModificarModelo() {
        if (txModelo.getText().length() > 0) {
            control.Sql = "select * from modelo where nommod='" + txModelo.getText()
                    + "' and idModelos<>'" + idMdl + "'";
            if (!control.Verificarconsulta(control.Sql)) {
                control.Sql = "update modelo set nommod='" + txModelo.getText() + "' where idModelos='" + idMdl + "'";
                control.EditarRegistro(control.Sql);
                Cancelar();
                MostrarModelo();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Existe repeticion en nombre del modelo");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Faltan datos");
        }
    }

    public void EliminarModelo() {
        control.fila = tModelo.getSelectedRow();
        if (control.fila >= 0) {
            idMdl = tModelo.getValueAt(control.fila, 0).toString();
            control.Sql = "select * from catalogoproducto where idModelos='" + idMdl + "'";
            if (!control.Verificandoconsulta(control.Sql)) {
                if (JOptionPane.showConfirmDialog(rootPane, "Seguro deseas eliminar", "Confirmar", 0) == 0) {
                    control.Sql = "delete from modelo where idModelos='" + idMdl + "'";
                    control.EliminarRegistro(control.Sql);
                    Cancelar();
                    MostrarModelo();
                }
            } else {
                JOptionPane.showMessageDialog(null, "El modelo seleccionado esta siendo utilizado y no puede ser eliminado");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Seleccione la fila a eliminar");
        }
    }

    public void VerModelo() {
        control.fila = tModelo.getSelectedRow();
        if (control.fila >= 0) {
            bCrear.setEnabled(false);
            bEliminar.setEnabled(true);
            bModificar.setEnabled(true);
            idMdl = tModelo.getValueAt(control.fila, 0).toString();
            txModelo.setText(tModelo.getValueAt(control.fila, 1).toString());
        }
    }

    public void Cancelar() {
        txModelo.setText(null);
        bCrear.setEnabled(true);
        bEliminar.setEnabled(false);
        bModificar.setEnabled(false);
        tModelo.clearSelection();

//         txBuscar.setText(null);
        control.MarcarTexto(txBuscar);
        txModelo.requestFocus();

    }

    public void MostrarModelo() {
        BuscarModelo();
    }

    public void BuscarModelo() {
        control.Sql = "select * from modelo where nommod like'" + txBuscar.getText() + "%'";
        control.LlenarJTabla(modelo, control.Sql, 2);
    }

    public Modelos() {
        initComponents();
        setTitle("Los Modelos de productos");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tModelo.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"Código", "Modelo"});
        tModelo.getColumnModel().getColumn(1).setPreferredWidth(250);
        bCrear.setMnemonic('c');
        bCancelar.setMnemonic('a');
        bModificar.setMnemonic('d');
        bEliminar.setMnemonic('e');
        bSalir.setMnemonic('s');
        MostrarModelo();
        control.forma_table_ver(modelo, tModelo);
        bModificar.setEnabled(false);
        bEliminar.setEnabled(false);
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
        txModelo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tModelo = new javax.swing.JTable();
        txBuscar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel1.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, 100, 40));

        bCrear.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCrear.setForeground(new java.awt.Color(0, 51, 102));
        bCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        bCrear.setText("Crear");
        bCrear.setName("bCrear"); // NOI18N
        bCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCrearActionPerformed(evt);
            }
        });
        jPanel1.add(bCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, 40));

        bModificar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bModificar.setForeground(new java.awt.Color(0, 51, 102));
        bModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Edit.png"))); // NOI18N
        bModificar.setText("Editar");
        bModificar.setName("bModificar"); // NOI18N
        bModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModificarActionPerformed(evt);
            }
        });
        jPanel1.add(bModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 100, 40));

        bEliminar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bEliminar.setForeground(new java.awt.Color(0, 51, 102));
        bEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        bEliminar.setText("Eliminar");
        bEliminar.setName("bEliminar"); // NOI18N
        bEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(bEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 110, 40));

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
        jPanel1.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 110, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 610, 60));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Modelos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txModelo.setName("txModelo"); // NOI18N
        txModelo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txModeloKeyReleased(evt);
            }
        });
        jPanel2.add(txModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 520, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("Modelo");
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 50));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de modelos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(51, 0, 153))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tModelo.setAutoCreateRowSorter(true);
        tModelo.setForeground(new java.awt.Color(0, 51, 102));
        tModelo.setModel(new javax.swing.table.DefaultTableModel(
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
        tModelo.setName("tModelo"); // NOI18N
        tModelo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tModeloMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tModeloMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tModelo);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 590, 230));

        txBuscar.setName("txBuscar"); // NOI18N
        txBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txBuscarKeyReleased(evt);
            }
        });
        jPanel3.add(txBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 530, -1));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("Buscar");
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 20));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 610, 290));

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void txBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarKeyReleased
    BuscarModelo();
}//GEN-LAST:event_txBuscarKeyReleased

private void tModeloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tModeloMouseClicked
    VerModelo();
}//GEN-LAST:event_tModeloMouseClicked

private void tModeloMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tModeloMouseEntered

}//GEN-LAST:event_tModeloMouseEntered

private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
    dispose();
}//GEN-LAST:event_bSalirActionPerformed

private void bCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCrearActionPerformed
    CrearModelo();
}//GEN-LAST:event_bCrearActionPerformed

private void bModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarActionPerformed
    ModificarModelo();
}//GEN-LAST:event_bModificarActionPerformed

private void bEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarActionPerformed
    EliminarModelo();
}//GEN-LAST:event_bEliminarActionPerformed

private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
    txBuscar.setText(null);
    Cancelar();
    MostrarModelo();
    //txBuscar.setText("");
}//GEN-LAST:event_bCancelarActionPerformed

    private void txModeloKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txModeloKeyReleased
        control.Mayusculas(txModelo);        // TODO add your handling code here:
    }//GEN-LAST:event_txModeloKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bCrear;
    private javax.swing.JButton bEliminar;
    private javax.swing.JButton bModificar;
    private javax.swing.JButton bSalir;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tModelo;
    private javax.swing.JTextField txBuscar;
    private javax.swing.JTextField txModelo;
    // End of variables declaration//GEN-END:variables
}
