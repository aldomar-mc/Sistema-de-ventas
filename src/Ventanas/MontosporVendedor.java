/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MontosporVendedor.java
 *
 * Created on 17-feb-2014, 18:33:10
 */
package Ventanas;

/**
 *
 * @author usuario
 */
import Clases.*;
import com.toedter.calendar.*;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Field;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
public class MontosporVendedor extends javax.swing.JInternalFrame {
    Controlador control=new Controlador();Mimodelo modelo=new Mimodelo();
    String des="",has="";    
    public MontosporVendedor() {
     initComponents();setTitle("Montos por vendedor");
     this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     tMontos.setModel(modelo);Desde.setDate(new Date());Hasta.setDate(new Date());
     modelo.setColumnIdentifiers(new String[]{"Vendedor","Monto"});     
     tMontos.getColumnModel().getColumn(0).setPreferredWidth(400);
     bImprimir.setMnemonic('i');bCancelar.setMnemonic('c');bSalir.setMnemonic('s');
     CargarDatos();
     FormatoTabla ft= new FormatoTabla(1);
     tMontos.setDefaultRenderer(Object.class, ft);
     Desde.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

         @Override
         public void propertyChange(PropertyChangeEvent evt) {
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              if("date".equals(evt.getPropertyName())){
                    JDateChooser isse=(JDateChooser)evt.getSource();
                    boolean isdatasele=false;
                    try {
                        Field dateselfi=JDateChooser.class.getDeclaredField("dateSelect");
                        dateselfi.setAccessible(true);
                        isdatasele=dateselfi.getBoolean(isse);
                    } catch (Exception ignoreOrNot) {
                        //System.out.println("GG1");
                        //OptionPane.showMessageDialog(null, "Hoy es:"+control.Formato_Amd(fechaIni.getDate()));
                        CargarDatos();
                        
                    }
                }
         }
     });
     Hasta.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

         @Override
         public void propertyChange(PropertyChangeEvent evt) {
       if("date".equals(evt.getPropertyName())){
                    JDateChooser isse=(JDateChooser)evt.getSource();
                    boolean isdatasele=false;
                    try {
                        Field dateselfi=JDateChooser.class.getDeclaredField("dateSelect");
                        dateselfi.setAccessible(true);
                        isdatasele=dateselfi.getBoolean(isse);
                    } catch (Exception ignoreOrNot) {
                        //System.out.println("GG1");
                        //OptionPane.showMessageDialog(null, "Hoy es:"+control.Formato_Amd(fechaIni.getDate()));
                 CargarDatos();
                    }
                }
             
         }
     });
     
    }
    public void Cancelar(){
     tMontos.clearSelection();Desde.setDate(new Date());Hasta.setDate(new Date());
     CargarDatos();
    }
    public void CargarDatos(){
     mostrarMontos(); SumarMontos();   
    }
    public void SumarMontos(){
     double mto=0.00;   
     for(int i=0;i<tMontos.getRowCount();i++){
      mto=mto+Double.parseDouble(tMontos.getValueAt(i,1).toString());   
     }   
     jLabel1.setText("S/."+mto);
    }
    public void mostrarMontos(){
     des=control.Formato_Amd(Desde.getDate()); has=control.Formato_Amd(Hasta.getDate());   
     control.Sql="CALL DevolverMotoVentasporVendedor('"+des+"','"+has+"')";             
     control.LlenarJTabla(modelo,control.Sql,2);        
     //System.out.print(control.Sql);
    }    
    public void Imprimir(){
     CargarDatos();   
     control.impresor.ImprConFechas(des,has,"MontoVentasxVendedor.jasper",jLabel1.getText());   
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tMontos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        bImprimir = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        bSalir = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Desde = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        Hasta = new com.toedter.calendar.JDateChooser();

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tMontos.setAutoCreateRowSorter(true);
        tMontos.setForeground(new java.awt.Color(0, 51, 102));
        tMontos.setModel(new javax.swing.table.DefaultTableModel(
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
        tMontos.setName("tMontos"); // NOI18N
        jScrollPane1.setViewportView(tMontos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 570, 220));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Total");
        jLabel1.setName("jLabel1"); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 290, 100, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bImprimir.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        bImprimir.setForeground(new java.awt.Color(0, 51, 102));
        bImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Print.png"))); // NOI18N
        bImprimir.setText("Imprimir");
        bImprimir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bImprimir.setName("bImprimir"); // NOI18N
        bImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bImprimirActionPerformed(evt);
            }
        });
        jPanel1.add(bImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 120, 45));

        bCancelar.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 51, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setText("Cancelar");
        bCancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bCancelar.setMargin(new java.awt.Insets(2, 1, 2, 1));
        bCancelar.setName("bCancelar"); // NOI18N
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 75, 120, 45));

        bSalir.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        bSalir.setForeground(new java.awt.Color(0, 51, 102));
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setText("Salir");
        bSalir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bSalir.setInheritsPopupMenu(true);
        bSalir.setMargin(new java.awt.Insets(2, 1, 2, 14));
        bSalir.setName("bSalir"); // NOI18N
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel1.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 120, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, 142, 190));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Total");
        jLabel4.setName("jLabel4"); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 290, 30, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(" "));
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel2MouseMoved(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Desde");
        jLabel2.setName("jLabel2"); // NOI18N

        Desde.setDateFormatString("dd-MM-yyyy");
        Desde.setName("Desde"); // NOI18N
        Desde.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DesdeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DesdeMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DesdeMousePressed(evt);
            }
        });
        Desde.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                DesdeMouseDragged(evt);
            }
        });
        Desde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                DesdeKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Hasta");
        jLabel3.setName("jLabel3"); // NOI18N

        Hasta.setDateFormatString("dd-MM-yyyy");
        Hasta.setName("Hasta"); // NOI18N
        Hasta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HastaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                HastaMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                HastaMousePressed(evt);
            }
        });
        Hasta.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                HastaMouseDragged(evt);
            }
        });
        Hasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                HastaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(Desde, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(Hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 0, 580, 50));

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void DesdeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DesdeMouseClicked
 
}//GEN-LAST:event_DesdeMouseClicked

private void DesdeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DesdeMouseEntered

}//GEN-LAST:event_DesdeMouseEntered
private void DesdeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DesdeMousePressed

}//GEN-LAST:event_DesdeMousePressed
private void DesdeMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DesdeMouseDragged

}//GEN-LAST:event_DesdeMouseDragged
private void DesdeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DesdeKeyTyped
 
}//GEN-LAST:event_DesdeKeyTyped
private void HastaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HastaMouseClicked
 
}//GEN-LAST:event_HastaMouseClicked
private void HastaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HastaMouseEntered

}//GEN-LAST:event_HastaMouseEntered
private void HastaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HastaMousePressed

}//GEN-LAST:event_HastaMousePressed
private void HastaMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HastaMouseDragged
 
}//GEN-LAST:event_HastaMouseDragged
private void HastaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HastaKeyTyped
 
}//GEN-LAST:event_HastaKeyTyped
private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
 dispose();
}//GEN-LAST:event_bSalirActionPerformed
private void bImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bImprimirActionPerformed
 Imprimir();
}//GEN-LAST:event_bImprimirActionPerformed
private void jPanel2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseMoved
// CargarDatos();
}//GEN-LAST:event_jPanel2MouseMoved
private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
 CargarDatos(); 
}//GEN-LAST:event_formMouseMoved
private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
 Cancelar(); 
}//GEN-LAST:event_bCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Desde;
    private com.toedter.calendar.JDateChooser Hasta;
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bImprimir;
    private javax.swing.JButton bSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tMontos;
    // End of variables declaration//GEN-END:variables
}
