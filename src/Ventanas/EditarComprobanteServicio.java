package Ventanas;

import Clases.Controlador;
import Clases.FormatoTabla;
import Clases.IMPRIMIR;
import Clases.InfoGeneral;
import Clases.Mimodelo;
import Clases.Numero_a_Letra;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Silva
 */
public class EditarComprobanteServicio extends javax.swing.JInternalFrame {

    private Controlador control = new Controlador();
    private Mimodelo modelo = new Mimodelo();
    private Numero_a_Letra nume = new Numero_a_Letra();
    private IMPRIMIR imprime = new IMPRIMIR();
    private DefaultTableModel mode = (DefaultTableModel) AnularComprobante.tComprobante.getModel();
    private InfoGeneral info = new InfoGeneral();

    private String datos[] = new String[3];
    private String dat[] = new String[5];
    private int ppp = 0;
    private String codVen = "", codcomp = "", codtipocom = "", cod = "", sr = "", srt = "";
    private Map mapComprobante = new HashMap();
    private int idComprobanteGenerado;

    /**
     * Creates new form VerCaracteristicas
     */
    public EditarComprobanteServicio() {
        initComponents();

        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tbproductos.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"Servicio", "Costo", "Fecha"});
        tbproductos.getColumnModel().getColumn(0).setPreferredWidth(350);
        tbproductos.getColumnModel().getColumn(1).setPreferredWidth(80);
        tbproductos.getColumnModel().getColumn(2).setPreferredWidth(80);
        control.fillComboBox(String.format("SELECT ID,Tipo as comprobantes FROM vta_tipocomprobante "
                + "WHERE `Sede`='%s' AND  Para<>'Productos' order by  tipo", Controlador.sede), cboTipoComprobante, mapComprobante);
        FormatoTabla ft= new FormatoTabla(1);
        tbproductos.setDefaultRenderer(Object.class, ft);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbproductos = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbCliente = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbTipoComprobante = new javax.swing.JLabel();
        lbSerieComproAn = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        cboTipoComprobante = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblSerieComproNuevo = new javax.swing.JLabel();
        txtNumComprobante = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        lbMensaje = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Modificar comprobante de servicio");
        setToolTipText("");

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tbproductos.setForeground(new java.awt.Color(0, 51, 102));
        tbproductos.setModel(new javax.swing.table.DefaultTableModel(
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
        tbproductos.setName("tbproductos"); // NOI18N
        tbproductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbproductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbproductos);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Servicios ofrecidos");
        jLabel3.setName("jLabel3"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        lbCliente.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbCliente.setForeground(new java.awt.Color(0, 51, 102));
        lbCliente.setText(" ");
        lbCliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbCliente.setName("lbCliente"); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Cliente:");
        jLabel2.setName("jLabel2"); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Comprobante Actual", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 11))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Comprobante:");
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Número:");
        jLabel5.setName("jLabel5"); // NOI18N

        lbTipoComprobante.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbTipoComprobante.setForeground(new java.awt.Color(0, 51, 102));
        lbTipoComprobante.setText(" ");
        lbTipoComprobante.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbTipoComprobante.setName("lbTipoComprobante"); // NOI18N

        lbSerieComproAn.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbSerieComproAn.setForeground(new java.awt.Color(0, 51, 102));
        lbSerieComproAn.setText(" ");
        lbSerieComproAn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbSerieComproAn.setName("lbSerieComproAn"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTipoComprobante, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                    .addComponent(lbSerieComproAn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTipoComprobante))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbSerieComproAn))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nuevo comprobante", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 11))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N

        cboTipoComprobante.setForeground(new java.awt.Color(0, 51, 102));
        cboTipoComprobante.setName("cboTipoComprobante"); // NOI18N
        cboTipoComprobante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboTipoComprobanteMouseClicked(evt);
            }
        });
        cboTipoComprobante.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTipoComprobanteItemStateChanged(evt);
            }
        });
        cboTipoComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoComprobanteActionPerformed(evt);
            }
        });
        cboTipoComprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cboTipoComprobanteKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 102));
        jLabel7.setText("Comprobante:");
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Número:");
        jLabel6.setName("jLabel6"); // NOI18N

        lblSerieComproNuevo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblSerieComproNuevo.setForeground(new java.awt.Color(0, 51, 102));
        lblSerieComproNuevo.setText(" ");
        lblSerieComproNuevo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblSerieComproNuevo.setName("lblSerieComproNuevo"); // NOI18N

        txtNumComprobante.setEditable(false);
        txtNumComprobante.setName("txtNumComprobante"); // NOI18N
        txtNumComprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNumComprobanteKeyReleased(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 51, 102));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Refresh-icon.png"))); // NOI18N
        jButton5.setText("Generar");
        jButton5.setName("jButton5"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboTipoComprobante, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(lblSerieComproNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                                .addComponent(txtNumComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSerieComproNuevo)
                    .addComponent(txtNumComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 11, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setName("jPanel3"); // NOI18N

        jButton1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Edit.png"))); // NOI18N
        jButton1.setMnemonic('N');
        jButton1.setText("Generar Nuevo Comprobante");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton2.setMnemonic('S');
        jButton2.setText("Salir");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 102));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        jButton3.setMnemonic('E');
        jButton3.setText("Eliminar servicio");
        jButton3.setName("jButton3"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbMensaje.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbMensaje.setText(" ");
        lbMensaje.setName("lbMensaje"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbMensaje)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboTipoComprobanteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTipoComprobanteItemStateChanged
//        if(ppp>0){
//   // GenerarNuevoComrpobante();
//    }
        txtNumComprobante.setText("");
        lblSerieComproNuevo.setText("");
    }//GEN-LAST:event_cboTipoComprobanteItemStateChanged

    private void cboTipoComprobanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboTipoComprobanteMouseClicked

//        ppp++;
//        if(ppp>0){
//        if(cbtipo.getSelectedIndex()>-1){
//            GenerarNuevoComrpobante();
//        }
//    }
        txtNumComprobante.setText("");
        lblSerieComproNuevo.setText(" ");

    }//GEN-LAST:event_cboTipoComprobanteMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        // llenar();
        //      AnularComprobante a= new AnularComprobante();
//        a.MostrarCliente();
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tbproductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbproductosMouseClicked
        if (evt.getClickCount() == 2) {
            EliminarProducto(modelo.getValueAt(tbproductos.getSelectedRow(), 0).toString());
        }
    }//GEN-LAST:event_tbproductosMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        generarNuevoComprobante();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cboTipoComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoComprobanteActionPerformed

        txtNumComprobante.setText("");
        lblSerieComproNuevo.setText(" ");
        //    if(ppp>0){
//        if(cbtipo.getSelectedIndex()>-1){
//            GenerarNuevoComrpobante();
//        }
//    }
    }//GEN-LAST:event_cboTipoComprobanteActionPerformed

    private void cboTipoComprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboTipoComprobanteKeyPressed
//        if(cbtipo.getSelectedIndex()>-1){
//            GenerarNuevoComrpobante();
//        }// TODO add your handling code here:

        txtNumComprobante.setText("");
        lblSerieComproNuevo.setText(" ");
    }//GEN-LAST:event_cboTipoComprobanteKeyPressed

    private void txtNumComprobanteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumComprobanteKeyReleased
        lblSerieComproNuevo.setText(sr + "-" + txtNumComprobante.getText());
    }//GEN-LAST:event_txtNumComprobanteKeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        generarComprobante();
    }//GEN-LAST:event_jButton5ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboTipoComprobante;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lbCliente;
    private javax.swing.JLabel lbMensaje;
    public static javax.swing.JLabel lbSerieComproAn;
    public static javax.swing.JLabel lbTipoComprobante;
    public static javax.swing.JLabel lblSerieComproNuevo;
    public static javax.swing.JTable tbproductos;
    private javax.swing.JTextField txtNumComprobante;
    // End of variables declaration//GEN-END:variables

    public void cargarDatosComprobante() {

        if (info.getSerie() > 0) {
            try {//System.out.print(info.getSerie());
                //control.LimTabla(mode);
                control.Sql = "SELECT nomclie, nume, tipcompr,idcomporbanteservicio FROM imprimircoprobanteservicio  where idcomprobantes ='" + info.getSerie() + "' limit 1;";
                control.Base.st = control.Base.conec.createStatement();
                control.Base.rt = control.Base.st.executeQuery(control.Sql);
//                System.out.print(control.Sql);
                while (control.Base.rt.next()) {
                    lbCliente.setText(control.Base.rt.getString(1).toString());
                    lbTipoComprobante.setText(control.Base.rt.getString(3).toString());
                    lbSerieComproAn.setText(control.Base.rt.getString(2).toString());
                    codVen = control.Base.rt.getString(4).toString();

                    //datos[0]=control.Base.rt.getString(1);
                    //modelo.addRow(datos);
                }

                llenarProductos(codVen);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void llenarProductos(String cod) {
        control.Sql = "SELECT descriser,cost,fecha FROM imprimircoprobanteservicio where idcomporbanteservicio='" + cod + "';";
//        System.out.print(control.Sql);
        // System.out.print(codVen);
        try {
            control.Base.st = control.Base.conec.createStatement();
            control.Base.rt = control.Base.st.executeQuery(control.Sql);
            while (control.Base.rt.next()) {
                datos[0] = control.Base.rt.getString(1);
                datos[1] = control.Base.rt.getString(2);
                datos[2] = control.Base.rt.getString(3);
                modelo.addRow(datos);
            }
        } catch (Exception e) {
        }

    }

    private void EliminarProducto(String codi) {
        control.Sql = "call EditarListaVendida('" + codi + "');";
        control.EditarRegistro(control.Sql);
        control.LimTabla(modelo);
        llenarProductos(codVen);
    }

    private void EliminarVentaTotal() {
        if ((JOptionPane.showConfirmDialog(null, "Desea Anular  la Venta", "", JOptionPane.YES_NO_OPTION)) == 0) {
            while (control.fila < tbproductos.getRowCount()) {
                control.Sql = "call EditarListaVendida('" + modelo.getValueAt(control.fila, 0).toString() + "');";
                control.EditarRegistro(control.Sql);
                control.fila++;
            }
            control.Sql = "update comprobantes set esta='Anulado' where idcomprobantes='" + info.getSerie() + "';";
            control.EliminarRegistro(control.Sql);

            control.Sql = "delete from compventa where idventa='" + codVen + "';";
            control.EliminarRegistro(control.Sql);
            control.Sql = "delete from venta where idventa='" + codVen + "';";
            control.EliminarRegistro(control.Sql);
            control.LimTabla(modelo);
            lbMensaje.setText("venta anulada Exitosamemte!!");
            llenar();
        }
    }

    private void generarNuevoComprobante() {
        if (cboTipoComprobante.getSelectedIndex() != -1) {
            if ((JOptionPane.showConfirmDialog(this, "¿Confirma que desea emitir un nuevo comprobante para este servicio?", "", JOptionPane.YES_NO_OPTION)) == 0) {
                // codcomp=control.DevolverRegistroDto("SELECT * FROM compventa c where idventa='"+codVen+"';", 1);
                codtipocom = control.DevolverRegistroDto("SELECT * FROM tipocomprobante where tipcompr='" + cboTipoComprobante.getSelectedItem().toString() + "'", 1);
                if (verificarNumero()) {
                    control.Sql = "update comprobantes set esta='Anulado' where idcomprobantes='" + info.getSerie() + "';";
                    control.EditarRegistro(control.Sql);

                    control.Sql = "call  GeneraComprobante('1','" + cboTipoComprobante.getSelectedItem().toString() + "');";
                    String cd = control.DevolverRegistroDto(control.Sql, 2);//lbSerie.setText(dato);
                    // System.out.println(control.Sql);
                    control.Sql = "update comporbanteservicio set idcomprobantes='" + cd + "' where idcomporbanteservicio='" + codVen + "';";
                    control.EditarRegistro(control.Sql);
                    //  System.out.println(control.Sql);
                    control.Sql = "update comprobantes set esta='Emitido' where idcomprobantes='" + cd + "';";
                    control.EditarRegistro(control.Sql);

                    control.Sql = "select idcomporbanteservicio from imprimircoprobanteservicio where idcomprobantes='" + cd + "';";
                    cod = control.DevolverRegistroDto(control.Sql, 1);

                    llenar();
                    lbMensaje.setText("Generado Nuevo Comprobante con Numero " + txtNumComprobante.getText());

                    //imprime.ImprimirusConFechas("Factura.jasper", cd);
                    control.Sql = "SELECT count(*) FROM comporbanteservicio where idcomporbanteservicio='" + cod + "';";
                    // System.out.println(control.Sql);

                    int dat = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
                    if (cboTipoComprobante.getSelectedItem().toString().compareTo("Factura") == 0) {
                        String dia = Num_Mes(cod);
                        switch (dat) {
                            case 1: {
                                imprime.ImprimirusConFechasBofact("Serviciof1.jasper", cod, dia);
                                break;
                            }
                            case 2: {
                                imprime.ImprimirusConFechasBofact("Serviciof2.jasper", cod, dia);
                                break;
                            }
                            case 3: {
                                imprime.ImprimirusConFechasBofact("Serviciof3.jasper", cod, dia);
                                break;
                            }
                            case 4: {
                                imprime.ImprimirusConFechasBofact("Serviciof4.jasper", cod, dia);
                                break;
                            }
                            case 5: {
                                imprime.ImprimirusConFechasBofact("Serviciof5.jasper", cod, dia);
                                break;
                            }
                            case 6: {
                                imprime.ImprimirusConFechasBofact("Serviciof6.jasper", cod, dia);
                                break;
                            }
                            case 7: {
                                imprime.ImprimirusConFechasBofact("Serviciof7.jasper", cod, dia);
                                break;
                            }
                            case 8: {
                                imprime.ImprimirusConFechasBofact("Serviciof8.jasper", cod, dia);
                                break;
                            }
                        }
                    } else {
                        if (cboTipoComprobante.getSelectedItem().toString().compareTo("Boleta") == 0) {

                            switch (dat) {
                                case 1: {
                                    imprime.ImprimirusConFechas("Serivicio1.jasper", cod);
                                    break;
                                }
                                case 2: {
                                    imprime.ImprimirusConFechas("Serivicio2.jasper", cod);
                                    break;
                                }
                                case 3: {
                                    imprime.ImprimirusConFechas("Serivicio3.jasper", cod);
                                    break;
                                }
                                case 4: {
                                    imprime.ImprimirusConFechas("Serivicio4.jasper", cod);
                                    break;
                                }
                                case 5: {
                                    imprime.ImprimirusConFechas("Serivicio5.jasper", cod);
                                    break;
                                }
                                case 6: {
                                    imprime.ImprimirusConFechas("Serivicio6.jasper", cod);
                                    break;
                                }
                                case 7: {
                                    imprime.ImprimirusConFechas("Serivicio7.jasper", cod);
                                    break;
                                }
                                case 8: {
                                    imprime.ImprimirusConFechas("Serivicio8.jasper", cod);
                                    break;
                                }
                            }
                        } else {
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El Numero del Comprobante se repite!! o \n No esta ingresando La Cantidad de Digitos del Comprobante \nIngrese todos los Numeros Incluido los Ceros!!!!!");
                }
            }//
        } else {
            JOptionPane.showMessageDialog(null, "Tiene que Selecionar un tipon de Comprobante!!");
        }
    }

    private String Num_Mes(String codven) {
        control.Sql = "SELECT sum(cost*cant) FROM imprimircoprobanteservicio where  idcomporbanteservicio='" + codven + "';";
        String sumto = control.DevolverRegistroDto(control.Sql, 1);
        // System.out.println(control.Sql);

        String numer = nume.Convertir(sumto, band());

        return numer;
    }

    private static boolean band() {
        if (Math.random() > .5) {
            return true;
        } else {
            return false;
        }
    }

    private void limpiar() {
        while (mode.getRowCount() > 0) {
            mode.removeRow(0);
        }
    }

    private void llenar() {
        control.Sql = "SELECT idcomprobantes, serie,nume,tipcompr,esta FROM comprobantesvta;";
        limpiar();
        try {
            control.Base.st = control.Base.conec.createStatement();
            control.Base.rt = control.Base.st.executeQuery(control.Sql);
            while (control.Base.rt.next()) {
                dat[0] = control.Base.rt.getString(1);
                dat[1] = control.Base.rt.getString(2);
                dat[2] = control.Base.rt.getString(3);
                dat[3] = control.Base.rt.getString(4);
                dat[4] = control.Base.rt.getString(5);
                mode.addRow(dat);

            }
            AnularComprobante.tComprobante.setModel(mode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //    JOptionPane.showMessageDialog(null,control.Sql);
    }

    private void generarComprobante() {

        if (cboTipoComprobante.getSelectedIndex() >= 0) {

            control.Sql = "call ElComprobante('0','" + Controlador.sede + "','"
                    + cboTipoComprobante.getSelectedItem().toString() + "','Servicios','0','1')";
            if (control.DevolverRegistroDto(control.Sql, 1).equals("Se debe iniciar este tipo de comprobante")) {
                JOptionPane.showMessageDialog(this, "No hay documentos de ese tipo, por favor primer incie la numeración del documento", "Mensaje", JOptionPane.WARNING_MESSAGE);

                IniciandoComprobantesICECompured inicdoc = new IniciandoComprobantesICECompured();
                Menu.jDesktopPane1.add(inicdoc);
                inicdoc.toFront();
                inicdoc.setLocation(250, 250);
                inicdoc.setVisible(true);
            } else {
                txtNumComprobante.setText(control.DevolverRegistroDto(control.Sql, 1));
                lblSerieComproNuevo.setText(control.DevolverRegistroDto(control.Sql, 2));
                idComprobanteGenerado = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 3));
            }
        } else {
            JOptionPane.showMessageDialog(this, "Indique el tipo de comprobante a generar", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private boolean verificarNumero() {
        boolean sss = false;
        if (txtNumComprobante.getText().trim().length() > 0) {
            control.Sql = "call  GeneraComprobante('1','" + cboTipoComprobante.getSelectedItem().toString() + "');";
            String codCom = control.DevolverRegistroDto(control.Sql, 2);
            control.Sql = "select * from tipocomprobante where tipcompr='" + cboTipoComprobante.getSelectedItem().toString() + "';";
            control.Sql = "select count(*) from comprobantes where nume='" + txtNumComprobante.getText() + "' and idtipocomprobante='" + control.DevolverRegistroDto(control.Sql, 1) + "' and idcomprobantes<>'" + codCom + "';";
            String dds = control.DevolverRegistroDto(control.Sql, 1);
            control.Sql = "SELECT candig FROM tipocomprobante where tipcompr='" + cboTipoComprobante.getSelectedItem().toString() + "';";
            int cantid = Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1));
            if (dds.compareTo("0") == 0) {
                if (txtNumComprobante.getText().trim().length() == cantid) {
                    sss = true;
                }
            }
        }
        return sss;
    }
}
