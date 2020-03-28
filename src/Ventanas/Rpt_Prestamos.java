package Ventanas;
import java.util.Date;import Clases.*;
import javax.swing.ImageIcon;
public class Rpt_Prestamos extends javax.swing.JInternalFrame {
 Controlador control= new Controlador();Mimodelo modelo= new Mimodelo();
 IMPRIMIR imprime= new IMPRIMIR();int ControladorCombo=0;
 public Rpt_Prestamos() {
        initComponents();
        setTitle("Reporte de Productos Prestados y PrestadoDevueltos");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tbPrestamo.setModel(modelo);
        modelo.setColumnIdentifiers(new String[] {"Item","Producto","Codigo","Nombre","Responsable","Fec. Prestamo","Fec. Devolucion"});
        tbPrestamo.getColumnModel().getColumn(0).setPreferredWidth(50);
        tbPrestamo.getColumnModel().getColumn(1).setPreferredWidth(280);
        tbPrestamo.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbPrestamo.getColumnModel().getColumn(3).setPreferredWidth(150);
        tbPrestamo.getColumnModel().getColumn(4).setPreferredWidth(150);
        tbPrestamo.getColumnModel().getColumn(5).setPreferredWidth(80);
        tbPrestamo.getColumnModel().getColumn(6).setPreferredWidth(80);
        
        fechaFi.setDate( new Date());
        fechaIn.setDate( new Date());
        LlenarModo();
        MostrarCliente();
    }
 public void LlenarModo(){
      cbEstado.addItem("Prestado");cbEstado.addItem("Prestado Devuelto");
      cbEstado.setSelectedIndex(-1);
    }
 public void MostrarCliente(){
     BuscarCliente();   
    }    
 public void BuscarCliente(){
     control.Sql="SELECT idprestamo, nomctlg, codbrr ,nomprest, respon,fecini,fecdev FROM imprimir_prestados where tipopres='0';";
     control.LlenarJTabla(modelo,control.Sql,7); 
    }
 public void LlenarConCombo(){
     control.Sql="SELECT idprestamo, nomctlg, codbrr ,nomprest, respon,fecini,fecdev FROM imprimir_prestados  where tipopres='0' and estdo='"+cbEstado.getSelectedItem().toString()+"' ;";
     control.LlenarJTabla(modelo,control.Sql,7);      
    }
 public void Imprimir_Resporte(){
     if(cbEstado.getSelectedIndex()>=0)
      imprime.ImprimirusConFechasVariasUsuaModo("Reportes_Estado.jasper", control.Formato_Amd(fechaIn.getDate()),
      control.Formato_Amd(fechaFi.getDate()),cbEstado.getSelectedItem().toString(),"0");
     else
      imprime.ImprimirusConFechasVariasUsua("Reportes_Todo.jasper", control.Formato_Amd(fechaIn.getDate()),
      control.Formato_Amd(fechaFi.getDate()),"0");
    }    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        fechaIn = new com.toedter.calendar.JDateChooser();
        fechaFi = new com.toedter.calendar.JDateChooser();
        cbEstado = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPrestamo = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Fecha Inicio");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Fecha Final");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Estado");
        jLabel4.setName("jLabel4"); // NOI18N

        fechaIn.setDateFormatString("dd-MM-yyyy");
        fechaIn.setName("fechaIn"); // NOI18N

        fechaFi.setDateFormatString("dd-MM-yyyy");
        fechaFi.setName("fechaFi"); // NOI18N

        cbEstado.setName("cbEstado"); // NOI18N
        cbEstado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbEstadoMouseClicked(evt);
            }
        });
        cbEstado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbEstadoItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(217, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fechaIn, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fechaFi, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechaFi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fechaIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Reporte de Productos Prestados");
        jLabel1.setName("jLabel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tbPrestamo.setForeground(new java.awt.Color(0, 51, 102));
        tbPrestamo.setModel(new javax.swing.table.DefaultTableModel(
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
        tbPrestamo.setName("tbPrestamo"); // NOI18N
        jScrollPane1.setViewportView(tbPrestamo);

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Print.png"))); // NOI18N
        jButton1.setText("Imprimir");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton2.setText("Salir");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 887, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(265, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(263, 263, 263))
            .addGroup(layout.createSequentialGroup()
                .addGap(332, 332, 332)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(16, 16, 16)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed
    private void cbEstadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbEstadoMouseClicked
        ControladorCombo++;
    }//GEN-LAST:event_cbEstadoMouseClicked
    private void cbEstadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbEstadoItemStateChanged
        if(ControladorCombo>0){
            LlenarConCombo();
        }
    }//GEN-LAST:event_cbEstadoItemStateChanged
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Imprimir_Resporte();
    }//GEN-LAST:event_jButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbEstado;
    private com.toedter.calendar.JDateChooser fechaFi;
    private com.toedter.calendar.JDateChooser fechaIn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbPrestamo;
    // End of variables declaration//GEN-END:variables
}
