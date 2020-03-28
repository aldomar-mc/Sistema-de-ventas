/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import java.awt.Color;
import java.text.DecimalFormat;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.ImageIcon;
import Clases.Configuracion;
import Clases.Controlador;

/**
 *
 * @author Ing Miguel Angel Silva Zapata
 */
public class ConfiguracionDialog extends javax.swing.JDialog {

    private Controlador control = new Controlador();
    private DecimalFormat format = new DecimalFormat("0.00");

    /**
     * Creates new form Configuracion
     */
    public ConfiguracionDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //getContentPane().setBackground(new Color(185, 204, 130));
//                setIconImage(new ImageIcon(getClass().getResource(Controlador.ICON_PATH)).getImage());
        cboImpresorasFacturas.addItem("Mostrar Ventana");

        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);

        for (PrintService printService : printServices) {
            cboImpresorasFacturas.addItem(printService.getName());
            cboImpresorasTicket.addItem(printService.getName());
        }
        txtIgv.setText(control.DevolverRegistroDto("SELECT c.`valor` FROM config c WHERE c.`idconfig`='igv';", 1));
        txtTasaDeCambio.setText(control.DevolverRegistroDto("SELECT c.`valor` FROM config c WHERE c.`idconfig`='tasaDeCambio';", 1));
        chkVistaPrevia.setSelected(control.DevolverRegistroDto("SELECT c.`valor` FROM config c WHERE c.`idconfig`='vistaPreviaComprobantes';", 1) == "true" ? true : false);
        spinnerMaxRenglonesFactura.setValue(Integer.parseInt(control.DevolverRegistroDto("SELECT c.`valor` FROM config c WHERE c.`idconfig`='maxFilasFactura';", 1)));
        spinnerMaxRenglonesBoleta.setValue(Integer.parseInt(control.DevolverRegistroDto("SELECT c.`valor` FROM config c WHERE c.`idconfig`='maxFilasBoleta';", 1)));
        spinnerCantidadDeCopiasSinVistaPropia.setValue(Integer.parseInt(control.DevolverRegistroDto("SELECT c.`valor` FROM config c WHERE c.`idconfig`='cantidadCopiasSinVistaPrevia';", 1)));
        cboImpresorasFacturas.setSelectedItem(control.DevolverRegistroDto("SELECT c.`valor` FROM config c WHERE c.`idconfig`='impresoraFacturaBoleta';", 1));
        cboImpresorasTicket.setSelectedItem(control.DevolverRegistroDto("SELECT c.`valor` FROM config c WHERE c.`idconfig`='impresoraTicket';", 1));

    }

    private void guardar() {
        control.ejecutar(String.format("UPDATE config c SET c.`valor`='%s' WHERE c.`idconfig`='cantidadCopiasSinVistaPrevia';", Integer.parseInt(
                spinnerCantidadDeCopiasSinVistaPropia.getValue().toString())));
        control.ejecutar(String.format("UPDATE config c SET c.`valor`='%s' WHERE c.`idconfig`='igv';", Double.parseDouble(txtIgv.getText())));
        control.ejecutar(String.format("UPDATE config c SET c.`valor`='%s' WHERE c.`idconfig`='impresoraFacturaBoleta';", cboImpresorasFacturas.getSelectedItem().toString()));
        control.ejecutar(String.format("UPDATE config c SET c.`valor`='%s' WHERE c.`idconfig`='impresoraPredeterminada';", Double.parseDouble(txtIgv.getText())));
        control.ejecutar(String.format("UPDATE config c SET c.`valor`='%s' WHERE c.`idconfig`='impresoraTicket';", cboImpresorasTicket.getSelectedItem().toString()));
        control.ejecutar(String.format("UPDATE config c SET c.`valor`='%s' WHERE c.`idconfig`='maxFilasBoleta';", Integer.parseInt(spinnerMaxRenglonesBoleta.getValue().toString())));
        control.ejecutar(String.format("UPDATE config c SET c.`valor`='%s' WHERE c.`idconfig`='maxFilasFactura';", Integer.parseInt(spinnerMaxRenglonesFactura.getValue().toString())));
        control.ejecutar(String.format("UPDATE config c SET c.`valor`='%s' WHERE c.`idconfig`='tasaDeCambio';", Double.parseDouble(txtTasaDeCambio.getText())));
        control.ejecutar(String.format("UPDATE config c SET c.`valor`='%s' WHERE c.`idconfig`='vistaPreviaComprobantes';", chkVistaPrevia.isSelected()));
//                try {
//                        Configuracion.getInstance().setIgv(Double.parseDouble(txtIgv.getText()));
//                } catch (NumberFormatException e) {
//                }
//                try {
//                        Configuracion.getInstance().setTasaDeCambio(Double.parseDouble(txtTasaDeCambio.getText()));
//                } catch (NumberFormatException e) {
//                }
//                Configuracion.getInstance().setVistaPreviaComprobantesVenta(chkVistaPrevia.isSelected());
//
//                try {
//                        Configuracion.getInstance().setMaximoRenglonesFactura(Integer.parseInt(spinnerMaxRenglonesFactura.getValue().toString()));
//                } catch (NumberFormatException e) {
//                }
//                try {
//                        Configuracion.getInstance().setMaximoRenglonesBoleta(Integer.parseInt(spinnerMaxRenglonesBoleta.getValue().toString()));
//                } catch (NumberFormatException e) {
//                }
//                try {
//                        Configuracion.getInstance().setCantidadCopiasSinVistaPrevia(Integer.parseInt(spinnerCantidadDeCopiasSinVistaPropia.getValue().toString()));
//                } catch (NumberFormatException e) {
//                }
//                Configuracion.getInstance().setImpresoraFacturaBoleta(cboImpresorasFacturas.getSelectedItem().toString());
//                Configuracion.getInstance().setImpresoraTicket(cboImpresorasTicket.getSelectedItem().toString());
//                Configuracion.getInstance().save();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        cboImpresorasFacturas = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        spinnerMaxRenglonesFactura = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        chkVistaPrevia = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        spinnerCantidadDeCopiasSinVistaPropia = new javax.swing.JSpinner();
        jLabel16 = new javax.swing.JLabel();
        spinnerMaxRenglonesBoleta = new javax.swing.JSpinner();
        jLabel17 = new javax.swing.JLabel();
        cboImpresorasTicket = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtIgv = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTasaDeCambio = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configuración General");

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Impresión"));
        jPanel7.setToolTipText("");
        jPanel7.setName("jPanel7"); // NOI18N

        cboImpresorasFacturas.setName("cboImpresorasFacturas"); // NOI18N

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Cantidad de Copias (sin Vista Previa)");
        jLabel14.setName("jLabel14"); // NOI18N

        spinnerMaxRenglonesFactura.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));
        spinnerMaxRenglonesFactura.setEditor(new javax.swing.JSpinner.NumberEditor(spinnerMaxRenglonesFactura, ""));
        spinnerMaxRenglonesFactura.setName("spinnerMaxRenglonesFactura"); // NOI18N

        jLabel15.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Impresora por Defecto Boletas - Facturas");
        jLabel15.setName("jLabel15"); // NOI18N

        chkVistaPrevia.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        chkVistaPrevia.setText("Vista previa de comprobantes de venta");
        chkVistaPrevia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkVistaPrevia.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkVistaPrevia.setName("chkVistaPrevia"); // NOI18N

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Máximo de renglones por Factura");
        jLabel13.setName("jLabel13"); // NOI18N

        spinnerCantidadDeCopiasSinVistaPropia.setModel(new javax.swing.SpinnerNumberModel(1, 0, 10, 1));
        spinnerCantidadDeCopiasSinVistaPropia.setEditor(new javax.swing.JSpinner.NumberEditor(spinnerCantidadDeCopiasSinVistaPropia, ""));
        spinnerCantidadDeCopiasSinVistaPropia.setName("spinnerCantidadDeCopiasSinVistaPropia"); // NOI18N

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Máximo de renglones por Boleta");
        jLabel16.setName("jLabel16"); // NOI18N

        spinnerMaxRenglonesBoleta.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));
        spinnerMaxRenglonesBoleta.setEditor(new javax.swing.JSpinner.NumberEditor(spinnerMaxRenglonesBoleta, ""));
        spinnerMaxRenglonesBoleta.setName("spinnerMaxRenglonesBoleta"); // NOI18N

        jLabel17.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Impresora por Defecto Ticket");
        jLabel17.setName("jLabel17"); // NOI18N

        cboImpresorasTicket.setName("cboImpresorasTicket"); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkVistaPrevia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(spinnerMaxRenglonesBoleta, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spinnerMaxRenglonesFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spinnerCantidadDeCopiasSinVistaPropia, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cboImpresorasTicket, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(cboImpresorasFacturas, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(chkVistaPrevia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(spinnerMaxRenglonesFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(spinnerMaxRenglonesBoleta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(spinnerCantidadDeCopiasSinVistaPropia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboImpresorasFacturas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboImpresorasTicket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("General"));
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("IGV (%): ");
        jLabel1.setName("jLabel1"); // NOI18N

        txtIgv.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        txtIgv.setName("txtIgv"); // NOI18N
        txtIgv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIgvKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Tasa de cambio: ");
        jLabel2.setName("jLabel2"); // NOI18N

        txtTasaDeCambio.setName("txtTasaDeCambio"); // NOI18N
        txtTasaDeCambio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTasaDeCambioKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtIgv)
                    .addComponent(txtTasaDeCambio, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIgv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTasaDeCambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setName("jPanel2"); // NOI18N

        jButton1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton1.setMnemonic('C');
        jButton1.setText("Cerrar");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save.png"))); // NOI18N
        jButton2.setMnemonic('G');
        jButton2.setText("Guardar");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtIgvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIgvKeyTyped
        control.decimal(evt, txtIgv.getText());
    }//GEN-LAST:event_txtIgvKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        guardar();
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtTasaDeCambioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTasaDeCambioKeyTyped
        control.decimal(evt, txtTasaDeCambio.getText());
    }//GEN-LAST:event_txtTasaDeCambioKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConfiguracionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfiguracionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfiguracionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfiguracionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ConfiguracionDialog dialog = new ConfiguracionDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboImpresorasFacturas;
    private javax.swing.JComboBox cboImpresorasTicket;
    private javax.swing.JCheckBox chkVistaPrevia;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSpinner spinnerCantidadDeCopiasSinVistaPropia;
    private javax.swing.JSpinner spinnerMaxRenglonesBoleta;
    private javax.swing.JSpinner spinnerMaxRenglonesFactura;
    private javax.swing.JTextField txtIgv;
    private javax.swing.JTextField txtTasaDeCambio;
    // End of variables declaration//GEN-END:variables

}
