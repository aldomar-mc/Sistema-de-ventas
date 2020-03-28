/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VerCaracteristicas.java
 *
 * Created on 05-feb-2014, 16:06:30
 */
package Ventanas;
import Clases.*;
import javax.swing.ImageIcon;

/**
 *
 * @author Silva
 */
public class VerCaracteristicas extends javax.swing.JInternalFrame {
Controlador control=new Controlador();
Mimodelo modelo=new Mimodelo();
InfoGeneral info= new InfoGeneral();
String datos[]=new String[1];
    /** Creates new form VerCaracteristicas */
    public VerCaracteristicas() {
        initComponents(); 
        setTitle("Los Caracteristicas");
    this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     tbCaracteristicas.setModel(modelo);
     modelo.setColumnIdentifiers(new String[] {"Caracteristicas"});
     //control.forma_table_ver(modelo, tbCaracteristicas);
     FormatoTabla ft = new FormatoTabla(0);
     tbCaracteristicas.setDefaultRenderer(Object.class, ft);
    }
public void Mensaje(){
    //System.out.print(info.getCarac()
            
    if(info.getCarac()>0){
         try {
               //control.LimTabla(mode);
               control.Sql="SELECT descripcion FROM caracteristicas_productos  where idproducto ='"+info.getCarac()+"';";
               control.Base.st=control.Base.conec.createStatement();
               control.Base.rt=control.Base.st.executeQuery(control.Sql);
               while(control.Base.rt.next()){
                 datos[0]=control.Base.rt.getString(1);
                 modelo.addRow(datos);
               }
           //    VerCaracteristicas.tbCaracteristicas.setModel(mode);
 //              Ventas.tProdaVender.setModel(mode);
               
           } catch (Exception e) {
           }
    }
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        lbProducto = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCaracteristicas = new javax.swing.JTable();

        setBackground(new java.awt.Color(51, 153, 255));
        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Producto:");
        jLabel2.setName("jLabel2"); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 17));

        lbProducto.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbProducto.setForeground(new java.awt.Color(0, 51, 102));
        lbProducto.setText(" ");
        lbProducto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbProducto.setName("lbProducto"); // NOI18N
        getContentPane().add(lbProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 27, 530, -1));

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ver Caracteristicas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(51, 0, 153))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Caracteristicas Tecnicas");
        jLabel3.setName("jLabel3"); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, -1, 17));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tbCaracteristicas.setForeground(new java.awt.Color(0, 51, 102));
        tbCaracteristicas.setModel(new javax.swing.table.DefaultTableModel(
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
        tbCaracteristicas.setName("tbCaracteristicas"); // NOI18N
        jScrollPane1.setViewportView(tbCaracteristicas);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 590, 330));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lbProducto;
    public static javax.swing.JTable tbCaracteristicas;
    // End of variables declaration//GEN-END:variables
}
