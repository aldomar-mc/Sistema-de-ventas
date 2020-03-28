package Ventanas;

/**
 * ** @author Ing. Miguel Angel Silva Zapata.   *********
 */
import javax.swing.*;
import Clases.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class Catalogoproductos extends javax.swing.JInternalFrame {

    /**
     * ******************************Atributos*****************************
     */
    Mimodelo modelo = new Mimodelo();
    Controlador control = new Controlador();
    String idcat = "", iduni = "", idlin = "", idmar = "", idmod = "", producto = "", codcatalogored = "",
            codcatalogorededit = "";
    InfoGeneral info = new InfoGeneral();
    private String codcatal = "";
    String punipr = "";

    /**
     * ******************************Atributos*****************************
     */

    /**
     * ****************************Métodos********************************
     */
    public void BuscarCatalogo() {
        /*control.Sql=" select `cp`.`codctlg` AS `Codigo`,concat(`mo`.`nommod`,' ',`m`.`nommrc`"
     + ",' ',`cp`.`nomctlg`) AS `Producto`,`cp`.`precsg` AS `Precio`, " +
     " `u`.`nomuni` AS `abre`,`cp`.`stockmin` AS `StockMinimo`, " +
     " `cp`.`prexmenor` AS `prexmenor`,`cp`.`prexmayor` AS `prexmayor`,codbarra " +
     " from ((((`marca` `m` join `modelo` `mo`) join `modelocatalogo` `mc`) join"
     + " `catalogoproducto` `cp`) join `unidad` `u`) where ((`cp`.`idMarca` = "
     + "`m`.`idMarca`) and (`cp`.`idUnidad` = `u`.`idUnidad`) and (`mo`.`idModelos` "
     + "= `mc`.`idModelos`) and (`cp`.`codctlg` = `mc`.`codctlg`)) and (concat(`mo`.`nommod`"
     + ",' ',`m`.`nommrc`,' ',`cp`.`nomctlg`) like '%"+txBuscar.getText()+"%' or "
     + "`mo`.`nommod` like '%"+txBuscar.getText()+"%' or `m`.`nommrc` like '%"+
     txBuscar.getText()+"%' or codbarra like '%"+txBuscar.getText()+"%' );";     */
        control.Sql = "select * from loscatalogos where producto like'%" + txBuscar.getText() + "%' or "
                + "codbarra like '%" + txBuscar.getText() + "%'";
        control.LlenarJTabla(modelo, control.Sql, 8);
    }

    public void MostrarCatalogo() {
        BuscarCatalogo();
    }

    public void Cancelar() {
        bCrear.setEnabled(true);
        bEliminar.setEnabled(true);
        bModificar.setEnabled(false);
        txtDescripcion.setText(null);
        control.MarcarTexto(txBuscar);
        producto = "";
        txtCodBarra.setText("");
        codcatalogored = "";
        codcatalogorededit = "";
        txtCantidad.setText("0");
        txtCosto.setText("0");
        txtDescripcion.setText("");
        txtPrecioxMayor.setText("");
        txtPrecioxMenor.setText("");
        txtProductoContenido.setText("");
        txtStockMinimo.setText("0");
        txtCosto.setText("");
        cbLineas.setSelectedIndex(-1);
        cboMarcas.setSelectedIndex(-1);
        cboModelos.setSelectedIndex(-1);
        cboUnidades.setSelectedIndex(-1);
        codcatal = "";
        idcat = "";
        tCatalogoproductos.clearSelection();
        cbLineas.requestFocus();
    }

    public void CrearProducto() {
        String mrca = cboMarcas.getSelectedItem().toString().trim(), modl = cboUnidades.getSelectedItem().toString().trim();
        producto = txtDescripcion.getText();
        if (mrca.equalsIgnoreCase("Sin Marca")) {
            producto = producto + cbLineas.getSelectedItem().toString();
        } else {
            mrca = cboMarcas.getSelectedItem().toString();
            producto = producto + cbLineas.getSelectedItem().toString() + " " + mrca;
        }
        if (modl.equalsIgnoreCase("Sin Linea")) {
            modl = "";
        } else {
            modl = cboUnidades.getSelectedItem().toString();
            producto = producto + cbLineas.getSelectedItem().toString() + " " + mrca + " " + modl;
        }
    }

    public void CrearCatalogo() {
        if (cboUnidades.getSelectedIndex() == -1 || cbLineas.getSelectedIndex() == -1
                || cboMarcas.getSelectedIndex() == -1 || txtDescripcion.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Faltan datos o son Erróneos");
            return;
        } else {
            CrearProducto();
            control.Sql = "select * from catalogoproducto where nomctlg='" + producto + "'";
            if (control.Verificandoconsulta(control.Sql)) {
                JOptionPane.showMessageDialog(rootPane, "El producto esta repetido");
            } else {
                control.Sql = "select * from catalogoproducto where codbarra='" + txtCodBarra.getText() + "'";
                if (!control.Verificandoconsulta(control.Sql)) {
                    idmar = control.ObtenerDato("marca", "nommrc", cboMarcas.getSelectedItem().toString(), 1);
                    idlin = control.ObtenerDato("modelo", "nommod", cbLineas.getSelectedItem().toString(), 1);
                    iduni = control.ObtenerDato("unidad", "nomuni", cboUnidades.getSelectedItem().toString(), 1);
                    control.Sql = String.format("insert into catalogoproducto values(null,'%s',"
                            + "'%s','%s','%s','%s','%s','%s','%s','%s','%s'); ", txtDescripcion.getText(),
                             txtCosto.getText(), idmar, idlin, iduni, txtStockMinimo.getText(),
                            txtPrecioxMenor.getText(), txtPrecioxMayor.getText(), control.Formato_Amd(jDateChooser1.getDate()),
                            txtCodBarra.getText());
                    control.CrearRegistro(control.Sql);
                    idmod = control.ObtenerDato("modelo", "nommod", cbLineas.getSelectedItem().toString(), 1);
                    idcat = control.DevolverRegistroDto("select codctlg from catalogoproducto "
                            + "where idmarca='" + idmar + "' and idmodelos='" + idmod + "' and idunidad='" + iduni
                            + "' and nomctlg='" + txtDescripcion.getText() + "'", 1);
                    control.Sql = "insert into modelocatalogo(codctlg,idModelos) values('" + idcat
                            + "','" + idmod + "')";
                    control.CrearRegistro(control.Sql);
                    lbMensaje.setText("Se ha Ingresado Correctamente");
                    if (txtProductoContenido.getText().trim().length() > 0) {
                        control.Sql = String.format("insert into detalleproducto values(null,'%s',"
                                + "'%s','%s');", txtCantidad.getText(), idcat, codcatalogored);
                        control.ejecutar(control.Sql);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ya existe un catalogo con codigo de barra " + txtCodBarra.getText());
                }
            }
            //*******************LIMPIA TODOS LOS DATOS**********************
            txtDescripcion.setText(null);
            control.MarcarTexto(txBuscar);
            txBuscar.setText(txtCodBarra.getText());
            producto = "";
            txtPrecioxMayor.setText("");
            txtPrecioxMenor.setText("");
            txtCosto.setText("");
            txtProductoContenido.setText("");
            txtCodBarra.setText("");
            txtCantidad.setText("0");
            txtStockMinimo.setText("0");
            cbLineas.setSelectedIndex(-1);
            cboMarcas.setSelectedIndex(-1);
            cboModelos.setSelectedIndex(-1);
            cboUnidades.setSelectedIndex(-1);
            tCatalogoproductos.clearSelection();
            MostrarCatalogo();
            cbLineas.requestFocus();
            //*******************LIMPIA TODOS LOS DATOS**********************
        }
    }

    public Catalogoproductos() {
        initComponents();
        setTitle("El catalogo de productos");
        this.setFrameIcon(new ImageIcon(this.getClass().getResource("/Imagenes/icoChiqui.png")));
        tCatalogoproductos.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"Código", "Producto", "Costo", "Unidad", "Stock_Min.", "PrecxMen.", "PrecxMay", "Codigo Barra"});
        tCatalogoproductos.getColumnModel().getColumn(1).setPreferredWidth(510);
        tCatalogoproductos.getColumnModel().getColumn(2).setPreferredWidth(30);
        tCatalogoproductos.getColumnModel().getColumn(3).setPreferredWidth(50);
        tCatalogoproductos.getColumnModel().getColumn(4).setPreferredWidth(30);
        tCatalogoproductos.getColumnModel().getColumn(5).setPreferredWidth(40);
        tCatalogoproductos.getColumnModel().getColumn(6).setPreferredWidth(40);
        micb.setVisible(false);
        LlenaMarca_Tipo_Unidades();
        MostrarCatalogo();
        info.setGg(5);
        control.forma_table_ver(modelo, tCatalogoproductos);
        jDateChooser1.setDate(new Date());
        jDateChooser1.setVisible(false);
        jLabel12.setVisible(false);
        cboModelos.setVisible(false);
        bModificar.setEnabled(false);

    }

    public void cbo(boolean b) {
        cbLineas.setEnabled(b);
        cboMarcas.setEnabled(b);
        cboModelos.setEnabled(b);
        cboUnidades.setEnabled(b);
    }

    public void VerdatosCatalogo() {
        control.fila = tCatalogoproductos.getSelectedRow();
        if (control.fila >= 0) {
            bCrear.setEnabled(false);
            bEliminar.setEnabled(false);
            bModificar.setEnabled(true);
            idcat = tCatalogoproductos.getValueAt(control.fila, 0).toString();
            txtDescripcion.setText(control.DevolverRegistroDto("select nomctlg from catalogoproducto where codctlg='" + tCatalogoproductos.getValueAt(control.fila, 0).toString() + "';", 1));
            cbLineas.setSelectedItem(control.DevolverRegistroDto("select modelo from vta_catalogo where codigo='" + tCatalogoproductos.getValueAt(control.fila, 0).toString() + "';", 1));
            cboMarcas.setSelectedItem(control.DevolverRegistroDto("select marca from vta_catalogo where codigo='" + tCatalogoproductos.getValueAt(control.fila, 0).toString() + "';", 1));
            txtStockMinimo.setText(tCatalogoproductos.getValueAt(control.fila, 4).toString());
            txtPrecioxMenor.setText(tCatalogoproductos.getValueAt(control.fila, 5).toString());
            txtPrecioxMayor.setText(tCatalogoproductos.getValueAt(control.fila, 6).toString());
            cboUnidades.setSelectedItem(control.DevolverRegistroDto("select abre from vta_catalogo where codigo='" + tCatalogoproductos.getValueAt(control.fila, 0).toString() + "';", 1));
            txtCosto.setText(tCatalogoproductos.getValueAt(control.fila, 2).toString());
            control.Sql = "select idcatalogo2 from detalleproducto where idcatalogo1='" + tCatalogoproductos.getValueAt(control.fila, 0).toString() + "';";
            txtProductoContenido.setText(control.DevolverRegistroDto("select nomctlg from catalogoproducto where codctlg='" + control.DevolverRegistroDto(control.Sql, 1) + "';", 1));
            txtCantidad.setText(control.DevolverRegistroDto("select cant from detalleproducto where idcatalogo1='" + tCatalogoproductos.getValueAt(control.fila, 0).toString() + "';", 1));
            codcatalogored = control.DevolverRegistroDto("select idcatalogo2 from detalleproducto where idcatalogo1='" + tCatalogoproductos.getValueAt(control.fila, 0).toString() + "';", 1);
            codcatalogorededit = control.DevolverRegistroDto("select idcatalogo2 from detalleproducto where idcatalogo1='" + tCatalogoproductos.getValueAt(control.fila, 0).toString() + "';", 1);
            txtCodBarra.setText(tCatalogoproductos.getValueAt(control.fila, 7).toString());
        }
    }

    public void EditarCatalogo() {
        if (cboUnidades.getSelectedIndex() >= 0) {
            //***********************VALIDANDO EL CODIGO DE BARRA**********************
            control.Sql = "select * from catalogoproducto where codbrr='" + txtCodBarra.getText()
                    + "' and codctlg<>'" + idcat + "'";
            if (control.Verificandoconsulta(control.Sql)) {
                JOptionPane.showMessageDialog(null, "El codigo de barra " + txtCodBarra.getText()
                        + " Corresponde a otro catalogo");
                return;
            }
            //***********************VALIDANDO EL CODIGO DE BARRA**********************   
            control.Sql = "select * from catalogoproducto where nomctlg='" + txtDescripcion.getText() + "' and codctlg<>'" + idcat + "'";
            if (!control.verifnum(control.Sql)) {
                idmar = control.ObtenerDato("marca", "nommrc", cboMarcas.getSelectedItem().toString(), 1);
                idlin = control.ObtenerDato("modelo", "nommod", cbLineas.getSelectedItem().toString(), 1);
                iduni = control.ObtenerDato("unidad", "nomuni", cboUnidades.getSelectedItem().toString(), 1);
                control.Sql = String.format("update catalogoproducto set nomctlg='%s', idmarca='%s'"
                        + ", idmodelos='%s',idunidad='%s', stockmin='%s', prexmenor='%s', prexmayor='%s'"
                        + ",fechacad='%s',codbarra='%s',precsg='%s' where codctlg='%s';", txtDescripcion.getText(),
                        idmar, idlin, iduni, txtStockMinimo.getText(), txtPrecioxMenor.getText(),
                        txtPrecioxMayor.getText(), control.Formato_Amd(jDateChooser1.getDate()),
                        txtCodBarra.getText(), txtCosto.getText(), idcat);
                control.EditarRegistro(control.Sql);
            }
            idmod = control.ObtenerDato("modelo", "nommod", cbLineas.getSelectedItem().toString(), 1);
            control.Sql = "update modelocatalogo set idModelos='" + idmod + "' where codctlg='" + idcat + "'";
            control.EditarRegistro(control.Sql);

            //******************EDITAR PRODUCTOS EXISTENTES********************
            control.Sql = "update producto set codbrr='" + txtCodBarra.getText() + "' where " + "catalogoproducto_codctlg='" + idcat + "'";
            control.EditarRegistro(control.Sql);
            //******************EDITAR PRODUCTOS EXISTENTES********************

            if (txtProductoContenido.getText().trim().length() > 0) {
                control.Sql = "SELECT idDetalleProducto FROM detalleproducto WHERE idcatalogo1<>'" + idcat + "' and idcatalogo2='" + codcatal + "'";
                String dat = control.DevolverRegistroDto(control.Sql, 1);
                if (dat.equals("")) {
                    if (codcatalogorededit.equals("")) {
                        control.Sql = String.format("insert into detalleproducto values(null,'%s','%s','%s');", txtCantidad.getText(), idcat, codcatalogored);
                        control.ejecutar(control.Sql);
                    } else {
                        if (codcatalogored.equals(codcatalogorededit)) {
                            control.Sql = "update detalleproducto set idcatalogo2='" + codcatalogorededit + "', cant='" + txtCantidad.getText() + "' where idcatalogo1='" + idcat + "';";
                            control.ejecutar(control.Sql);
                        } else {
                            control.Sql = "update detalleproducto set idcatalogo2='" + codcatal + "', cant='" + txtCantidad.getText() + "' where idcatalogo1='" + idcat + "';";
                            control.ejecutar(control.Sql);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El Producto en Catálogo ya esta asignado a otro");
                }
            }
            MostrarCatalogo();
            Cancelar();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Faltan datos");
        }
    }

    public void Desvinvular() {
        if (idcat.trim().length() > 0) {
            control.Sql = "select count(*) from detalleproducto where idcatalogo1='" + idcat + "';";
            if (Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1)) > 0) {
                if (JOptionPane.showConfirmDialog(null, "Seguro que quieres quitar el producto contenido",
                        "Confirmar", 0) == 0) {
                    control.Sql = "delete from detalleproducto where idcatalogo1='" + idcat + "';";
                    control.EliminarRegistro(control.Sql);
                }
            } else {
                JOptionPane.showMessageDialog(null, "El Producto Seleccionado no contiene ningun otro producto");
            }
            Cancelar();
        } else {
            JOptionPane.showMessageDialog(null, "Tienes que seleccionar el Código del catalogo");
        }
    }

    public void EliminarCatalogo() {
        control.fila = tCatalogoproductos.getSelectedRow();
        if (control.fila >= 0) {
            idcat = tCatalogoproductos.getValueAt(control.fila, 0).toString();
            producto = tCatalogoproductos.getValueAt(control.fila, 1).toString();
            if (JOptionPane.showConfirmDialog(rootPane, "Deseas eliminar el producto \n" + producto, "Confirmar", 0) == 0) {
                control.Sql = "select * from Producto where Catalogoproducto_codctlg='" + idcat + "'";
                if (control.Verificandoconsulta(control.Sql)) {
                    JOptionPane.showMessageDialog(rootPane, "No se puede eliminar porque hay \n productos existentes de este catalogo");
                    Cancelar();
                } else {
                    control.Sql = "select * from detallecotizacion where codctlg='" + idcat + "'";
                    if (control.Verificandoconsulta(control.Sql)) {
                        JOptionPane.showMessageDialog(rootPane, "No se puede eliminar porque hay \n Cotizaciones de este catalogo");
                        Cancelar();
                    } else {
                        control.Sql = "delete from modelocatalogo where codctlg='" + idcat + "'";
                        control.EliminarRegistro(control.Sql);
                        control.Sql = "delete from catalogoproducto where codctlg='" + idcat + "'";
                        control.EliminarRegistro(control.Sql);
                        Cancelar();
                        MostrarCatalogo();
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Seleccione el elemento a eliminar");
            tCatalogoproductos.requestFocus();
        }
    }

    public void LlenaMarca_Tipo_Unidades() {
        control.LlenarCombo(cboMarcas, "select * from marca order by nommrc asc", 2);
        control.LlenarCombo(cboUnidades, "select * from unidad order by nomuni asc", 2);
        control.LlenarCombo(cbLineas, "select * from modelo order by nommod asc", 2);
    }

    public void Refrescar() {
        idmar = "";
        iduni = "";
        idlin = "";
        idmod = "";
        if (cboMarcas.getSelectedIndex() > -1) {
            idmar = cboMarcas.getSelectedItem().toString();
        }
        if (cboUnidades.getSelectedIndex() > -1) {
            iduni = cboUnidades.getSelectedItem().toString();
        }
        if (cbLineas.getSelectedIndex() > -1) {
            idlin = cbLineas.getSelectedItem().toString();
        }
        if (cboModelos.getSelectedIndex() > -1) {
            idmod = cboModelos.getSelectedItem().toString();
        }

        LlenaMarca_Tipo_Unidades();
        if (idmar.length() > 0) {
            control.MostrarEnCombo(idmar, cboMarcas);
        }
        if (iduni.length() > 0) {
            control.MostrarEnCombo(iduni, cboUnidades);
        }
        if (idlin.length() > 0) {
            control.MostrarEnCombo(idlin, cbLineas);
        }
        if (idmod.length() > 0) {
            control.MostrarEnCombo(idmod, cboModelos);
        }
    }

    /**
     * ****************************Métodos********************************
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbLineas = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        cboUnidades = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtStockMinimo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtProductoContenido = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtPrecioxMenor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtPrecioxMayor = new javax.swing.JTextField();
        txtCosto = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        txtCodBarra = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tCatalogoproductos = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txBuscar = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        bSalir = new javax.swing.JButton();
        bCrear = new javax.swing.JButton();
        bModificar = new javax.swing.JButton();
        bEliminar = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        micb = new javax.swing.JComboBox();
        cboModelos = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        bDesvincular = new javax.swing.JButton();
        lbMensaje = new javax.swing.JLabel();

        jMenuItem1.setText("Cantidad Mimina");
        jMenuItem1.setComponentPopupMenu(jPopupMenu1);
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de los productos", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(0, 51, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Tipo de Producto");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        cbLineas.setEditable(true);
        cbLineas.setNextFocusableComponent(cboMarcas);
        cbLineas.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cbLineasPopupMenuWillBecomeVisible(evt);
            }
        });
        jPanel1.add(cbLineas, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 350, -1));

        cboMarcas.setEditable(true);
        cboMarcas.setNextFocusableComponent(txtDescripcion);
        cboMarcas.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cboMarcasPopupMenuWillBecomeVisible(evt);
            }
        });
        jPanel1.add(cboMarcas, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 20, 300, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Marcas");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, -1, -1));

        txtDescripcion.setNextFocusableComponent(txtStockMinimo);
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyTyped(evt);
            }
        });
        jPanel1.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 350, -1));

        cboUnidades.setEditable(true);
        cboUnidades.setForeground(new java.awt.Color(0, 51, 102));
        cboUnidades.setNextFocusableComponent(txtCosto);
        cboUnidades.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cboUnidadesPopupMenuWillBecomeVisible(evt);
            }
        });
        jPanel1.add(cboUnidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, -1, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Unidad");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, -1, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 102));
        jLabel7.setText("Detalle");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, -1, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Stock Minimo");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 50, -1, -1));

        txtStockMinimo.setText("0");
        txtStockMinimo.setNextFocusableComponent(txtPrecioxMenor);
        txtStockMinimo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStockMinimoKeyTyped(evt);
            }
        });
        jPanel1.add(txtStockMinimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 50, 300, -1));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Producto Contenido");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 110, -1));

        txtProductoContenido.setEditable(false);
        txtProductoContenido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProductoContenidoKeyTyped(evt);
            }
        });
        jPanel1.add(txtProductoContenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 350, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 102));
        jLabel9.setText("Precio x Menor:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 90, -1));

        txtPrecioxMenor.setNextFocusableComponent(txtPrecioxMayor);
        txtPrecioxMenor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioxMenorKeyTyped(evt);
            }
        });
        jPanel1.add(txtPrecioxMenor, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 350, -1));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 102));
        jLabel10.setText("Cantidad");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 80, -1, -1));

        txtCantidad.setText("0");
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        jPanel1.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 80, 300, -1));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 102));
        jLabel11.setText("Precio x Mayor:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 110, -1, -1));

        txtPrecioxMayor.setNextFocusableComponent(cboUnidades);
        txtPrecioxMayor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioxMayorKeyTyped(evt);
            }
        });
        jPanel1.add(txtPrecioxMayor, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 110, 300, -1));

        txtCosto.setNextFocusableComponent(txtCodBarra);
        txtCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostoKeyTyped(evt);
            }
        });
        jPanel1.add(txtCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 140, 80, -1));
        jPanel1.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 140, 50, -1));

        txtCodBarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodBarraKeyTyped(evt);
            }
        });
        jPanel1.add(txtCodBarra, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 140, 180, -1));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 51, 102));
        jLabel14.setText("Codigo Barra");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 140, 80, -1));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 51, 102));
        jLabel15.setText("Costo Compra");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 140, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 170));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de productos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 153))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tCatalogoproductos.setAutoCreateRowSorter(true);
        tCatalogoproductos.setForeground(new java.awt.Color(0, 51, 102));
        tCatalogoproductos.setModel(new javax.swing.table.DefaultTableModel(
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
        tCatalogoproductos.setComponentPopupMenu(jPopupMenu1);
        tCatalogoproductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tCatalogoproductosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tCatalogoproductosMouseEntered(evt);
            }
        });
        tCatalogoproductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tCatalogoproductosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tCatalogoproductos);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1060, 310));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Buscar");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 27, -1, -1));

        txBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txBuscarKeyReleased(evt);
            }
        });
        jPanel3.add(txBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 25, 1010, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 231, 1080, 370));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(" "));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bSalir.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bSalir.setForeground(new java.awt.Color(0, 51, 102));
        bSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bSalir.setMnemonic('s');
        bSalir.setText("Salir");
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        jPanel2.add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 10, 110, 40));

        bCrear.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCrear.setForeground(new java.awt.Color(0, 51, 102));
        bCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Accept.png"))); // NOI18N
        bCrear.setMnemonic('c');
        bCrear.setText("Crear");
        bCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCrearActionPerformed(evt);
            }
        });
        jPanel2.add(bCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 110, 40));

        bModificar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bModificar.setForeground(new java.awt.Color(0, 51, 102));
        bModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Edit.png"))); // NOI18N
        bModificar.setMnemonic('e');
        bModificar.setText("Editar");
        bModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModificarActionPerformed(evt);
            }
        });
        jPanel2.add(bModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 110, 40));

        bEliminar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bEliminar.setForeground(new java.awt.Color(0, 51, 102));
        bEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        bEliminar.setMnemonic('l');
        bEliminar.setText("Eliminar");
        bEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEliminarActionPerformed(evt);
            }
        });
        jPanel2.add(bEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, 110, 40));

        bCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bCancelar.setForeground(new java.awt.Color(0, 51, 102));
        bCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        bCancelar.setMnemonic('a');
        bCancelar.setText("Cancelar");
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(bCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 110, 40));

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/note_go.png"))); // NOI18N
        jButton1.setMnemonic('r');
        jButton1.setText("Refrescar");
        jButton1.setToolTipText("");
        jButton1.setMargin(new java.awt.Insets(2, 1, 2, 14));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(625, 10, 110, 40));

        jPanel2.add(micb, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, -1, -1));

        cboModelos.setEditable(true);
        cboModelos.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cboModelosPopupMenuWillBecomeVisible(evt);
            }
        });
        jPanel2.add(cboModelos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 30, -1));

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 102));
        jLabel12.setText("Fecha Caducidad");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 30, -1, -1));

        bDesvincular.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bDesvincular.setForeground(new java.awt.Color(0, 51, 102));
        bDesvincular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        bDesvincular.setMnemonic('d');
        bDesvincular.setText("Desvincular");
        bDesvincular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDesvincularActionPerformed(evt);
            }
        });
        jPanel2.add(bDesvincular, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 10, 125, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 1080, 60));

        lbMensaje.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbMensaje.setForeground(new java.awt.Color(0, 51, 102));
        lbMensaje.setText(" ");
        getContentPane().add(lbMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 20, 30, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents
private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
    info.setGg(0);
    dispose();
}//GEN-LAST:event_bSalirActionPerformed
private void bCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCrearActionPerformed
    CrearCatalogo();
}//GEN-LAST:event_bCrearActionPerformed
private void bModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarActionPerformed
    EditarCatalogo();
}//GEN-LAST:event_bModificarActionPerformed
private void bEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarActionPerformed
    EliminarCatalogo();
}//GEN-LAST:event_bEliminarActionPerformed
private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
    txBuscar.setText(null);
    Cancelar();
    MostrarCatalogo();
}//GEN-LAST:event_bCancelarActionPerformed
private void txBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txBuscarKeyReleased
    BuscarCatalogo();
}//GEN-LAST:event_txBuscarKeyReleased
private void cboModelosPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cboModelosPopupMenuWillBecomeVisible

}//GEN-LAST:event_cboModelosPopupMenuWillBecomeVisible
private void tCatalogoproductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tCatalogoproductosMouseClicked
    if (evt.getClickCount() == 2) {
        if (tCatalogoproductos.getSelectedRowCount() == 1) {
            if (JOptionPane.showConfirmDialog(null, "Desea Editar el Catalgo?", "System Message", JOptionPane.YES_NO_OPTION) == 0) {
                VerdatosCatalogo();
            } else {
                codcatalogored = tCatalogoproductos.getValueAt(tCatalogoproductos.getSelectedRow(), 0).toString();
                txtProductoContenido.setText(tCatalogoproductos.getValueAt(tCatalogoproductos.getSelectedRow(), 1).toString());
                txtCantidad.grabFocus();
                codcatal = tCatalogoproductos.getValueAt(tCatalogoproductos.getSelectedRow(), 0).toString();
            }
        }
    }
}//GEN-LAST:event_tCatalogoproductosMouseClicked
private void tCatalogoproductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tCatalogoproductosKeyReleased
    VerdatosCatalogo();
}//GEN-LAST:event_tCatalogoproductosKeyReleased
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Refrescar();
    }//GEN-LAST:event_jButton1ActionPerformed
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
//VerdatosCatalogo();
        if (tCatalogoproductos.getSelectedRow() == 1) {
            for (int i = 1; i < 11; i++) {
                micb.addItem(i);
            }
            try {
                String nuestok = JOptionPane.showInputDialog(micb, "Ingresa el nuevo Stock");
                control.fila = tCatalogoproductos.getSelectedRow();
                control.Sql = "update catalogoproducto set stockmin='" + Integer.parseInt(nuestok) + "' where codctlg='"
                        + tCatalogoproductos.getValueAt(control.fila, 0).toString() + "'";
                control.EditarRegistro(control.Sql);
                MostrarCatalogo();
            } catch (Exception e) {
            }
        }


    }//GEN-LAST:event_jMenuItem1ActionPerformed
    private void tCatalogoproductosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tCatalogoproductosMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tCatalogoproductosMouseEntered

    private void txtCodBarraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodBarraKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodBarraKeyTyped

    private void txtCostoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoKeyTyped
        control.decimal(evt, txtCosto.getText());         // TODO add your handling code here:
    }//GEN-LAST:event_txtCostoKeyTyped

    private void txtPrecioxMayorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioxMayorKeyTyped
        control.decimal(evt, txtPrecioxMayor.getText());         // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioxMayorKeyTyped

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtPrecioxMenorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioxMenorKeyTyped
        control.decimal(evt, txtPrecioxMenor.getText());         // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioxMenorKeyTyped

    private void txtProductoContenidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductoContenidoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductoContenidoKeyTyped

    private void txtStockMinimoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockMinimoKeyTyped
        control.Solonumeros(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_txtStockMinimoKeyTyped

    private void cboUnidadesPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cboUnidadesPopupMenuWillBecomeVisible

    }//GEN-LAST:event_cboUnidadesPopupMenuWillBecomeVisible

    private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyTyped

    }//GEN-LAST:event_txtDescripcionKeyTyped

    private void cboMarcasPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cboMarcasPopupMenuWillBecomeVisible

    }//GEN-LAST:event_cboMarcasPopupMenuWillBecomeVisible
    private void cbLineasPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbLineasPopupMenuWillBecomeVisible

    }//GEN-LAST:event_cbLineasPopupMenuWillBecomeVisible
    private void bDesvincularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDesvincularActionPerformed
        Desvinvular();
    }//GEN-LAST:event_bDesvincularActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bCrear;
    private javax.swing.JButton bDesvincular;
    private javax.swing.JButton bEliminar;
    private javax.swing.JButton bModificar;
    private javax.swing.JButton bSalir;
    public static javax.swing.JComboBox cbLineas;
    public static final javax.swing.JComboBox cboMarcas = new javax.swing.JComboBox();
    public static javax.swing.JComboBox cboModelos;
    public static javax.swing.JComboBox cboUnidades;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbMensaje;
    private javax.swing.JComboBox micb;
    private javax.swing.JTable tCatalogoproductos;
    private javax.swing.JTextField txBuscar;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodBarra;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtPrecioxMayor;
    private javax.swing.JTextField txtPrecioxMenor;
    private javax.swing.JTextField txtProductoContenido;
    private javax.swing.JTextField txtStockMinimo;
    // End of variables declaration//GEN-END:variables
}
