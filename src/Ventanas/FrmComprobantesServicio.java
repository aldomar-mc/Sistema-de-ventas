package Ventanas;

import Clases.Controlador;
import Clases.IMPRIMIR;
import Clases.InfoGeneral;
import Clases.Mimodelo;
import Clases.Numero_a_Letra;
import javax.swing.ImageIcon;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class FrmComprobantesServicio extends javax.swing.JInternalFrame {

    private Controlador control = new Controlador();
    private Mimodelo modelo = new Mimodelo();
    private IMPRIMIR imprime = new IMPRIMIR();
    private Numero_a_Letra nume = new Numero_a_Letra();
    private final int contadopr = 0, controladorModo;
    InfoGeneral info= new InfoGeneral();

    /**
     * Creates new form Rpt_Ventas
     */
    public FrmComprobantesServicio() {
        this.controladorModo = 0;
        initComponents();
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tablaServicios.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"Item", "Cliente", "Tipo de Documento", "Numero ", "Fec. Venta", "Comprobante", "Numero",  "Costo"});
        control.hideTableColumn(tablaServicios, 0);
        tablaServicios.getColumnModel().getColumn(1).setPreferredWidth(280);
        tablaServicios.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaServicios.getColumnModel().getColumn(3).setPreferredWidth(125);
        tablaServicios.getColumnModel().getColumn(4).setPreferredWidth(90);
        tablaServicios.getColumnModel().getColumn(5).setPreferredWidth(90);
        tablaServicios.getColumnModel().getColumn(6).setPreferredWidth(150);
        tablaServicios.getColumnModel().getColumn(7).setPreferredWidth(90);

//        fechaFin.setDate(new Date());
        //      fechaIni.setDate(new Date());
        //    control.LlenarCombo(cbusuario, "SELECT nomusr FROM vendedores v;", 1);
        MostrarCliente();
//        LlenarModo();
        control.forma_table_ver(modelo, tablaServicios);
        TableRowSorter<TableModel> elorden=new TableRowSorter<TableModel>(modelo);
        tablaServicios.setRowSorter(elorden);
    }

    public void MostrarCliente() {
        BuscarCliente();
    }

    public void BuscarCliente() {
        control.Sql = "SELECT i.idcomporbanteservicio,i.nomclie, i.desident, i.numident ,i.fecha,i.tipcompr, i.nume, sum(i.cost*i.cant), ti.tipcompr "
                + " FROM imprimircoprobanteservicio i, comprobantes c, tipocomprobante ti"
                + " where i.idcomprobantes=c.idcomprobantes and c.idtipocomprobante=ti.idtipocomprobante  and ti.idsede='"+info.getIdSede()+"'"
                + " and ( nomclie like '" + txtBuscar.getText() + "%' or numident like '" + txtBuscar.getText() + "%' or ti.tipcompr like '" + txtBuscar.getText() + "%')"
                + " group by i.idcomporbanteservicio order by idcomporbanteservicio desc;";
        System.out.println(control.Sql);
        control.LlenarJTabla(modelo, control.Sql, 8);
//        System.out.println(control.Sql);//admin

        //    JOptionPane.showMessageDialog(null,control.Sql);
    }

    public String Num_Mes(String codven) {
        control.Sql = "SELECT sum(cost*cant) FROM imprimircoprobanteservicio where  idcomporbanteservicio='" + codven + "';";
        String sumto = control.DevolverRegistroDto(control.Sql, 1);
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
 private void imprimir(String idComprobanteServicio, String  tipo,String total) {
     
        Map map = new HashMap();
        map.put("idServicioComprobante", idComprobanteServicio);
        map.put("totalnum", new Numero_a_Letra().Convertir(""+total, true));
        String idSede = InfoGeneral.getIdSede();
        if (idSede.equals("3")) {//Infotel
            if ("Factura".equals(tipo)) {
                
                control.showReportDialog("Factura de servicio", "facturaServicioInfotel", map);
            } else if ("Boleta".equals(tipo)) {
                control.showReportDialog("Factura de servicio", "boletaServicioInfotel", map);
            } else {
                JOptionPane.showMessageDialog(this, "No hay un diseño específico para este tipo de comprobante", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {//ICE
            if ("Factura".equals(tipo)) {
                control.showReportDialog("Factura de servicio", "facturaServicioICE", map);
            } else if ("Boleta".equals(tipo)) {
                control.showReportDialog("Factura de servicio", "boletaServicioICE", map);
            } else {
                JOptionPane.showMessageDialog(this, "No hay un diseño específico para este tipo de comprobante", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaServicios = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setTitle("Comprobantes por servicios");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Buscar Comprobante:");
        jLabel1.setName("jLabel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tablaServicios.setForeground(new java.awt.Color(0, 51, 102));
        tablaServicios.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaServicios.setName("tablaServicios"); // NOI18N
        tablaServicios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaServiciosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaServicios);

        jButton2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton2.setText("Salir");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtBuscar.setName("txtBuscar"); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 992, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tablaServiciosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaServiciosMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaServicios.getSelectedRowCount() == 1) {
                imprimir(tablaServicios.getValueAt(tablaServicios.getSelectedRow(), 0).toString(), 
                tablaServicios.getValueAt(tablaServicios.getSelectedRow(), 5).toString(), 
                tablaServicios.getValueAt(tablaServicios.getSelectedRow(), 7).toString());
            }
//            System.out.println(tablaServicios.getValueAt(tablaServicios.getSelectedRow(), 0).toString()+"---"+  tablaServicios.getValueAt(tablaServicios.getSelectedRow(), 5).toString()+"---"+ tablaServicios.getValueAt(tablaServicios.getSelectedRow(), 7).toString());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tablaServiciosMouseClicked

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        BuscarCliente();
    }//GEN-LAST:event_txtBuscarKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaServicios;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
