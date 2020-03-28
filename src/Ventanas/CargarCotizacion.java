/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CargarCotizacion.java
 *
 * Created on 31-ene-2014, 10:50:29
 */
package Ventanas;
import Clases.*;
import java.util.ArrayList;
//import static Clases.Controlador.Base;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Silva
 */
public class CargarCotizacion extends javax.swing.JInternalFrame {
Mimodelo modelo=new Mimodelo();
Controlador control = new Controlador();
public static ConexionBD1 cn = ConexionBD1.getInstance();
InfoGeneral info=new InfoGeneral();
DefaultTableModel mode = (DefaultTableModel)Ventas.tProdaVender.getModel();
String Cliente;
    ArrayList<String> codss=new ArrayList<String>();
    ArrayList<Integer> cnts= new ArrayList<Integer>();
    ArrayList<Integer> stcpro= new ArrayList<Integer>();
    ArrayList<Double> pruni= new ArrayList<Double>();
    ArrayList<Double> prtod= new ArrayList<Double>();
double con=0;
String []datos=new String[8];
    /** Creates new form CargarCotizacion */
    public CargarCotizacion() {
        initComponents();
        setTitle("Las cotizaciones");
     this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
     tCotizacion.setModel(modelo);
     modelo.setColumnIdentifiers(new String[] {"Orden","Item","Fecha Cotizacion","Fecha Duracion Cotizacion","Cliente","Monto"});     
     tCotizacion.getColumnModel().getColumn(0).setMaxWidth(0);
     tCotizacion.getColumnModel().getColumn(0).setMinWidth(0);
     tCotizacion.getColumnModel().getColumn(0).setPreferredWidth(0);
     tCotizacion.getColumnModel().getColumn(1).setPreferredWidth(20);
     tCotizacion.getColumnModel().getColumn(2).setPreferredWidth(30);
     tCotizacion.getColumnModel().getColumn(3).setPreferredWidth(30);
     tCotizacion.getColumnModel().getColumn(4).setPreferredWidth(200);
     tCotizacion.getColumnModel().getColumn(5).setPreferredWidth(40);
     control.forma_table_ver(modelo, tCotizacion);
     MostrarProductos();
    }

    public void MostrarProductos(){
     BuscarProductos();   
    }    
    public void BuscarProductos(){
//     control.Sql="SELECT idcliente,idcotizacion, fec_ctz, ADDDATE(fec_ctz,diasdur) AS fec_fin ,nomclie  FROM impri_cotizacion i  "
//             + "WHERE nomclie LIKE'%"+txtBuscar.getText()+"%' OR fec_ctz LIKE CONCAT('%','"+txtBuscar.getText()+"','%') GROUP BY idcotizacion";
     control.Sql="select cl.idcliente,c.idCotizacion,fec_ctz, date_add(fec_ctz,  interval  diasdur day)ff,nomclie,sum(prec) as tot " +
            " from cotizacion c, detallecotizacion d,cliente cl  where c.idcotizacion=d.idcotizacion " +
            " and c.idcliente=cl.idcliente  and date_add(fec_ctz,  interval  diasdur day) >curdate() " +
            " and nomclie like '%"+txtBuscar.getText()+"%'  or c.idcotizacion  like '%"+txtBuscar.getText()+"%'  group by idcotizacion;";
     System.out.println(control.Sql);
     control.LlenarJTabla(modelo,control.Sql,6); 
     
 //    JOptionPane.showMessageDialog(null,control.Sql);
    }
    
    
    public void LimTabla() {
        while (mode.getRowCount() > 0) {
            mode.removeRow(0);
        }
    }
    public int stockprod(String id){
        int stock=0;
        String  dta="";
        dta=String.format("SELECT count(*) FROM producto p where p.`estdo`='Disponible' "+
                            " AND p.`Catalogoproducto_codctlg`= %s group by p.`Catalogoproducto_codctlg`;", id);
//                   datorecu=control.DevolverRegistroDto(chvezz,1);
                   stock=Integer.parseInt(control.DevolverRegistroDto(dta,1));
        return stock;
    }
    public String DevolverRegistroDto(String sq, int pos) {
        String rs = "";
        try {
            cn.st = cn.conec.createStatement();
            cn.rt = cn.st.executeQuery(sq);
            if (cn.rt.next()) {
                rs = cn.rt.getString(pos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
       public void Mensaje(String us){
           String codprodu="",nombpro="",marc="",codctlgp="", datorecu="",chvezz="",gen="";
                control.Sql = "select idusuario from usuario where nomusr='" + info.getUsuario() + "'";
                String usar = control.DevolverRegistroDto(control.Sql, 1);
           try {
               LimTabla();
                int stock=0, cant=0;
                double precuni=0,precto=0;
                
                
//                control.Sql="SELECT codctlg, nomctlg, nommrc,prec,cnt, prec, estdo   FROM impri_cotizacion i where idcotizacion ='"+us+"';";
               gen="SELECT codctlg,cnt,round((d.`prec`/d.`cnt` ),2) prc, d.`prec`,(SELECT count(*) FROM producto p where p.`estdo`='Disponible' AND p.`Catalogoproducto_codctlg`=codctlg group by p.`Catalogoproducto_codctlg`)as stock FROM detallecotizacion d where idcotizacion='"+us+"';";
//               System.out.println(control.Sql);
                cn.st=cn.conec.createStatement();
               cn.rt=cn.st.executeQuery(gen);
               while(cn.rt.next()){
                   codss.add(cn.rt.getString(1));
                   cnts.add(cn.rt.getInt(2));
                   pruni.add(cn.rt.getDouble(3));
                   prtod.add(cn.rt.getDouble(4));
                   stcpro.add(cn.rt.getInt(5));
               }
               control.fila=0;
               while (control.fila<codss.size()) {
                   
//                   System.out.println("se "+cn.rt.getString(2));
//                   codctlgp = cn.rt.getString(1);
//                   cant = cn.rt.getInt(2);
//                   precuni = cn.rt.getDouble(3);
//                   precto = cn.rt.getDouble(4);
//                   stock=stockprod(codctlgp);
                   stock=stcpro.get(control.fila);
                   cant=cnts.get(control.fila);
//                   chvezz=String.format("SELECT count(*) FROM producto p where p.`estdo`='Disponible' "+
//                            " AND p.`Catalogoproducto_codctlg`= %s group by p.`Catalogoproducto_codctlg`;", codctlgp);
//                   datorecu=control.DevolverRegistroDto(chvezz,1);
//                   stock=Integer.parseInt(control.DevolverRegistroDto(chvezz,1));
//                   System.out.println(chvezz);
//                   System.out.println(" stoc  "+stock+"   canti "+cant+"  canti"+datorecu);
                   
                   if(stock < cant){
                       break;
                   }else{
                       for(int r=1;r<=cant;r++){
                           chvezz=String.format("select idproducto,nomctlg, nommrc, seri, codbrr, prexmenor " +
                            " FROM producto_venta p where (estdo='Disponible' and Sede='" + Controlador.sede + "') " +
                            " and idproducto not in(select Producto_idProducto from venta_producto) " +
                            " AND p.`codctlg`='%s' " +
                            " order by nomctlg ",codss.get(control.fila));
//                           System.out.println(chvezz);
                            codprodu=DevolverRegistroDto(chvezz, 1);
                            nombpro =DevolverRegistroDto(chvezz, 2);
                            marc=DevolverRegistroDto(chvezz, 3);
                            control.CrearRegistro(String.format("INSERT INTO por_vender VALUES('%s','%s','%s');", codprodu,usar,Controlador.sede));
                            control.EditarRegistro(String.format("UPDATE producto p SET p.`estdo`='Por Vender' WHERE p.`idProducto`='%s'", codprodu));
//                            System.out.println(String.format("INSERT INTO por_vender VALUES('%s','%s','%s');", codprodu,usar,Controlador.sede));
                       }
                   }
//                   control.Sql="";
                   
                            datos[0]=codprodu;
                            datos[1]=nombpro;
                            datos[2]=marc;//codctlg,cnt,round((d.`prec`/d.`cnt` ),2) prc, d.`prec`
                            datos[3]=""+pruni.get(control.fila);
                            datos[4]=""+cant;
                            datos[5]=""+cant;
                            datos[6]=""+prtod.get(control.fila);
                            datos[7]="Si";
                            con=con+prtod.get(control.fila);
                            mode.addRow(datos);
                            control.fila++;
               }
               Ventas.tProdaVender.setModel(mode);
               Ventas.lbTotalVenta.setText(""+con);
           } catch (Exception e) {
               e.printStackTrace();
           }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tCotizacion = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        txtBuscar.setName("txtBuscar"); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Buscar:");
        jLabel2.setName("jLabel2"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tCotizacion.setForeground(new java.awt.Color(0, 51, 102));
        tCotizacion.setModel(new javax.swing.table.DefaultTableModel(
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
        tCotizacion.setName("tCotizacion"); // NOI18N
        tCotizacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tCotizacionMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tCotizacion);

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        jButton1.setMnemonic('l');
        jButton1.setText("Seleccionar");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton2.setMnemonic('s');
        jButton2.setText("Salir");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addGap(0, 86, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 2, 580, 380));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
if(tCotizacion.getSelectedRowCount()>0){
    //Ventas pal=new Ventas();
Cliente=modelo.getValueAt(tCotizacion.getSelectedRow(), 4).toString();
//System.out.print(Cliente);
Ventas.lbCliente.setText(Cliente); //Mensaje(Cliente);

//new Ventas().Mensaje(modelo.getValueAt(tCotizacion.getSelectedRow(), 0).toString());
//pal.lbCliente.setText(Cliente);
Mensaje(modelo.getValueAt(tCotizacion.getSelectedRow(), 1).toString());
         //pal.toFront();
         info.setPass(modelo.getValueAt(tCotizacion.getSelectedRow(), 0).toString());
        dispose();
}         
        

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        dispose();
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tCotizacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tCotizacionMouseClicked
    jButton1.doClick();
    }//GEN-LAST:event_tCotizacionMouseClicked

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        BuscarProductos();
    }//GEN-LAST:event_txtBuscarKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tCotizacion;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
