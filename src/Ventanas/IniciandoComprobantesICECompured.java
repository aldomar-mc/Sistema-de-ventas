package Ventanas;

/**
 * ** @author Miguel Silva *********
 */
import Clases.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class IniciandoComprobantesICECompured extends javax.swing.JInternalFrame {

    Controlador control = new Controlador();
    Mimodelo modelo = new Mimodelo();
    String iddoc = "";
    InfoGeneral info = new InfoGeneral();

    public void LimpiaControles() {
        tTipocomprobante.clearSelection();
        jLabel4.setText(null);
        txBuscar.setText(null);
        txInicio.setText(null);
    }

    public void IniciaComprobante() {
        if (txInicio.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Faltan datos");
            txInicio.grabFocus();
        } else {
            control.fila = tTipocomprobante.getSelectedRow();
            if (control.fila > -1) {
                control.Sql = "call ElComprobante('0','" + tTipocomprobante.getValueAt(control.fila, 1).toString() + "','"
                        + tTipocomprobante.getValueAt(control.fila, 3).toString() + "','" + tTipocomprobante.getValueAt(control.fila, 6).toString()
                        + "','" + txInicio.getText() + "','0')";
                JOptionPane.showMessageDialog(rootPane, control.CrearRegistroDev(control.Sql));
                LimpiaControles();
                if (!InfoGeneral.pase) {
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Debes seleccionar un tipo de documento");
            }
        }
    }

    public IniciandoComprobantesICECompured() {
        initComponents();
        tTipocomprobante.setModel(modelo);
        setTitle("Iniciando comprobantes de pago");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tTipocomprobante.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"ID",
            "Sede", "Ruc", "Tipo", "Serie", "Cant.Digitos", "Para"});
        jLabel4.setText(null);
        MostrarTipoComprobante();
        control.forma_table_ver(modelo,
                tTipocomprobante);
        tTipocomprobante.getColumnModel().getColumn(6).setPreferredWidth(0);
        tTipocomprobante.getColumnModel().getColumn(6).setMinWidth(0);
        tTipocomprobante.getColumnModel().getColumn(6).setMaxWidth(0);
    }

    public void Seleccionar() {
        control.fila = tTipocomprobante.getSelectedRow();
        if (control.fila >= 0) {
            iddoc = tTipocomprobante.getValueAt(control.fila, 0).toString();
            control.Sql = "select count(*) from comprobantes where idTipoComprobante='"
                    + iddoc + "'";
            if (Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1)) > 0) {
                JOptionPane.showMessageDialog(rootPane, "El Comprobante Seleccionado ya fue "
                        + "inicializado anteriormente");
                tTipocomprobante.clearSelection();
                return;
            }
            jLabel4.setText(tTipocomprobante.getValueAt(control.fila, 3).toString() + " Serie "
                    + tTipocomprobante.getValueAt(control.fila, 4).toString() + " Del Ruc "
                    + tTipocomprobante.getValueAt(control.fila, 2).toString());
            txInicio.grabFocus();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Debes Seleccionar");
        }
    }

    public void MostrarTipoComprobante() {
        BuscarCliente();
    }

    public void BuscarCliente() {
        control.Sql = "SELECT * FROM vta_tipocomprobante  where (tipo like'"
                + txBuscar.getText() + "%' or ruc like'" + txBuscar.getText() + "%' or sede like'"
                + txBuscar.getText() + "%') and (Sede='" + Controlador.sede + "');";
        control.LlenarJTabla(modelo, control.Sql, 7);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txInicio = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        txBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tTipocomprobante = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(" "));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton1.setMnemonic('s');
        jButton1.setText("Salir");
        jButton1.setMargin(new java.awt.Insets(2, 1, 2, 14));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        jButton2.setMnemonic('i');
        jButton2.setText("Iniciar");
        jButton2.setMargin(new java.awt.Insets(2, 1, 2, 14));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 102));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        jButton3.setMnemonic('c');
        jButton3.setText("Cancelar");
        jButton3.setMargin(new java.awt.Insets(2, 1, 2, 14));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 0, 300, 110));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de inicialización", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Tipo");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, -1, 20));

        jLabel4.setBackground(new java.awt.Color(51, 153, 255));
        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Buscar");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 370, 20));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Numero Inicio");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, 20));

        txInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txInicioKeyTyped(evt);
            }
        });
        jPanel3.add(txInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 380, 20));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 110));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de tipos de comprobantes", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txBuscarKeyReleased(evt);
            }
        });
        jPanel4.add(txBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 26, 240, -1));

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
        tTipocomprobante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tTipocomprobanteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tTipocomprobante);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 800, 200));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Buscar");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 27, -1, 20));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 820, 260));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 380));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void txBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarKeyReleased
        BuscarCliente();
    }//GEN-LAST:event_txBuscarKeyReleased
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        IniciaComprobante();
    }//GEN-LAST:event_jButton2ActionPerformed
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        control.Sql = "call ComprobantesComputec('0'" + tTipocomprobante.getValueAt(tTipocomprobante.getSelectedRow(), 1);
    }//GEN-LAST:event_jButton3ActionPerformed
    private void tTipocomprobanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tTipocomprobanteMouseClicked
        Seleccionar();
    }//GEN-LAST:event_tTipocomprobanteMouseClicked
    private void txInicioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txInicioKeyTyped
        control.Solonumeros(evt);
    }//GEN-LAST:event_txInicioKeyTyped
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tTipocomprobante;
    private javax.swing.JTextField txBuscar;
    private javax.swing.JTextField txInicio;
    // End of variables declaration//GEN-END:variables
}
