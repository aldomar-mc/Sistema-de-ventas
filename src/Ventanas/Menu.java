/**
 * ** @author @ldomar.mc
 */
package Ventanas;

import Clases.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.text.*;
import javax.swing.*;

public class Menu extends javax.swing.JFrame {

    /**
     * *******************************Los Atributos***************************
     */
    private Rectangle R = null;
    Controlador control = new Controlador();
    InfoGeneral info = new InfoGeneral();
    private static Image imagen;
    String us = "", idUSuario = "";

    /**
     * ************************Los Atributos***********************************
     */
    /**
     * *************************Los metodos***********************************
     */
    private void setPosition(JInternalFrame internalFrame) {
        internalFrame.setLocation((R.width - internalFrame.getWidth()) / 2, 10);
    }

    public void setInternalFrame(JInternalFrame internalFrame) {
        jDesktopPane1.add(internalFrame, JLayeredPane.DEFAULT_LAYER);
        setPosition(internalFrame);
        internalFrame.setVisible(true);
    }

    public void CargarFormulario(int dto) {
        boolean b = false;
        JInternalFrame frame = null;
        switch (dto) {

            case 2: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Marcas) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Marcas ObjMarca = new Marcas();
                    setInternalFrame(ObjMarca);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 3: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Unidad) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Unidad ObjUnidad = new Unidad();
                    setInternalFrame(ObjUnidad);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 4: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Lineaproducto) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Lineaproducto ObjLinea = new Lineaproducto();
                    setInternalFrame(ObjLinea);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 5: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Proveedores) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    info.control = 0;
                    Proveedores ObjProveedores = new Proveedores();
                    setInternalFrame(ObjProveedores);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 7: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Catalogoproductos) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Catalogoproductos ObjCatalogoproductos = new Catalogoproductos();
                    setInternalFrame(ObjCatalogoproductos);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 8: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Cotizaciondeproductos) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Cotizaciondeproductos ObjCotizaciondeproductos = new Cotizaciondeproductos();
                    setInternalFrame(ObjCotizaciondeproductos);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 9: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof LosClientes) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    LosClientes ObjLosClientes = new LosClientes();
                    setInternalFrame(ObjLosClientes);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 10: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Tipocomprobante) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Tipocomprobante ObjTipocomprobante = new Tipocomprobante();
                    setInternalFrame(ObjTipocomprobante);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 11: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof IniciandoComprobantesICECompured) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    IniciandoComprobantesICECompured ObjLoscomprobantes = new IniciandoComprobantesICECompured();
                    setInternalFrame(ObjLoscomprobantes);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 12: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Vendedores) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Vendedores ObjVendedores = new Vendedores();
                    setInternalFrame(ObjVendedores);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 13: {
                /*for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                 if (jDesktopPane1.getAllFrames()[i] instanceof Ventas) {
                 b = true;
                 frame = jDesktopPane1.getAllFrames()[i];
                 break;
                 }
                 }
                 if (!b) {
                 Ventas ObjVentas = new Ventas();
                 setInternalFrame(ObjVentas);
                 } else {
                 frame.toFront();
                 }
                 */
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof MisVentas) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    MisVentas ObjVentas = new MisVentas();
                    setInternalFrame(ObjVentas);
                } else {
                    frame.toFront();
                }

                break;
            }
            case 14: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Modelos) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Modelos ObjModelos = new Modelos();
                    setInternalFrame(ObjModelos);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 15: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Ingresodeproductos) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Ingresodeproductos ObjIngresodeproductos = new Ingresodeproductos();
                    setInternalFrame(ObjIngresodeproductos);
                } else {
                    frame.toFront();
                }
                break;

            }
            case 16: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Productos) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Productos ObjPro = new Productos();
                    setInternalFrame(ObjPro);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 19: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof AnularComprobante) {
                        //if (jDesktopPane1.getAllFrames()[i] instanceof FrmAnulandoComprobantes) {    
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    AnularComprobante num = new AnularComprobante();
                    //FrmAnulandoComprobantes num = new FrmAnulandoComprobantes();
                    setInternalFrame(num);
                } else {
                    frame.toFront();
                }
                break;
            }

            case 21: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Prestador) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Prestador objPrestador = new Prestador();
                    setInternalFrame(objPrestador);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 22: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof PrestarProductos) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    PrestarProductos objPrestarProductos = new PrestarProductos();
                    setInternalFrame(objPrestarProductos);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 24: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof CaracteristicasProducto) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    CaracteristicasProducto objPrestarProductos = new CaracteristicasProducto();
                    setInternalFrame(objPrestarProductos);
                } else {
                    frame.toFront();
                }
                break;
            }

            case 25: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Usuarios) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Usuarios objPrestarProductos = new Usuarios();
                    setInternalFrame(objPrestarProductos);
                } else {
                    frame.toFront();
                }
                break;
            }

            case 26: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof CambiarClave) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    CambiarClave objPrestarProductos = new CambiarClave();
                    setInternalFrame(objPrestarProductos);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 27: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof CrearPresmiso) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    CrearPresmiso objPrestarProductos = new CrearPresmiso();
                    setInternalFrame(objPrestarProductos);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 28: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Permisos) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Permisos objPrestarProductos = new Permisos();
                    setInternalFrame(objPrestarProductos);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 31: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof GrabarDevolucion) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    GrabarDevolucion objPrestarProductos = new GrabarDevolucion();
                    setInternalFrame(objPrestarProductos);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 32: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Rpt_Ventas) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Rpt_Ventas objPrestarProductos = new Rpt_Ventas();
                    setInternalFrame(objPrestarProductos);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 33: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Rpt_Cotizacion) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Rpt_Cotizacion objPrestarProductos = new Rpt_Cotizacion();
                    setInternalFrame(objPrestarProductos);
                } else {
                    frame.toFront();
                }
                break;
            }

            case 34: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Rpt_Prestamos) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Rpt_Prestamos objPrestarProductos = new Rpt_Prestamos();
                    setInternalFrame(objPrestarProductos);
                } else {
                    frame.toFront();
                }
                break;
            }

            case 35: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof IngresoDeProductosPrestados) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    IngresoDeProductosPrestados objPrestarProductos = new IngresoDeProductosPrestados();
                    setInternalFrame(objPrestarProductos);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 36: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof MontosporVendedor) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    MontosporVendedor objPrestarProductos = new MontosporVendedor();
                    setInternalFrame(objPrestarProductos);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 37: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof IngresoSinDocumento) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    IngresoSinDocumento objPrestarProductos = new IngresoSinDocumento();
                    setInternalFrame(objPrestarProductos);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 38: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof ResibirProductoPrestado) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    ResibirProductoPrestado recibidos = new ResibirProductoPrestado();
                    setInternalFrame(recibidos);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 39: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Rpt_Devueltos) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Rpt_Devueltos devul = new Rpt_Devueltos();
                    setInternalFrame(devul);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 40: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Rpt_Deudores) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Rpt_Deudores deu = new Rpt_Deudores();
                    setInternalFrame(deu);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 41: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Rpt_DeudasCanceladas) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Rpt_DeudasCanceladas deuc = new Rpt_DeudasCanceladas();
                    setInternalFrame(deuc);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 42: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof GenerarGuiaDeRemision) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    GenerarGuiaDeRemision geuia = new GenerarGuiaDeRemision();
                    setInternalFrame(geuia);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 43: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Rpt_Guia) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Rpt_Guia geuia = new Rpt_Guia();
                    setInternalFrame(geuia);
                } else {
                    frame.toFront();
                }
                break;
            }

            case 44: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof VerGarantia) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    VerGarantia geuia = new VerGarantia();
                    setInternalFrame(geuia);
                } else {
                    frame.toFront();
                }
                break;
            }

            case 45: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof ace) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    ace geuia = new ace();
                    setInternalFrame(geuia);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 46: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof FrmComprobantesVenta) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    FrmComprobantesVenta geuia = new FrmComprobantesVenta();
                    setInternalFrame(geuia);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 47: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof igv) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    igv geuia = new igv();
                    setInternalFrame(geuia);
                } else {
                    frame.toFront();
                }
                break;
            }

            case 48: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Facturarservicios) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Facturarservicios geui = new Facturarservicios();
                    setInternalFrame(geui);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 49: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof FrmComprobantesServicio) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    FrmComprobantesServicio geui = new FrmComprobantesServicio();
                    setInternalFrame(geui);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 50: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof ParticionarProducto) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    ParticionarProducto geui = new ParticionarProducto();
                    setInternalFrame(geui);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 51: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof VentasEnsamblaje) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    VentasEnsamblaje geui = new VentasEnsamblaje();
                    setInternalFrame(geui);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 52: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof FacturarProdcutosEnsamblados) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    FacturarProdcutosEnsamblados geui = new FacturarProdcutosEnsamblados();
                    setInternalFrame(geui);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 53: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Rpt_Cotiza) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Rpt_Cotiza geui = new Rpt_Cotiza();
                    setInternalFrame(geui);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 54: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof StockMinimo) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    StockMinimo stock = new StockMinimo();
                    setInternalFrame(stock);
                    stock.setLocation(500, 180);
                }
                break;
            }
            case 55: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Tecnico) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Tecnico Tec = new Tecnico();
                    setInternalFrame(Tec);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 56: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof DistribuirProductos) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    DistribuirProductos Distr = new DistribuirProductos();
                    setInternalFrame(Distr);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 57: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    //if (jDesktopPane1.getAllFrames()[i] instanceof Rep_Distribuidos) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof RepComprobantesAnulados) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    RepComprobantesAnulados objRepComprobantesAnulados = new RepComprobantesAnulados();
                    setInternalFrame(objRepComprobantesAnulados);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 58: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof RepInventarioInicial) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    RepInventarioInicial Distr = new RepInventarioInicial();
                    setInternalFrame(Distr);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 60: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof CambiarDeEstadoProducto) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    CambiarDeEstadoProducto Distr = new CambiarDeEstadoProducto();
                    setInternalFrame(Distr);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 62: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Movimientodecaja) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Movimientodecaja objMovimientodecaja = new Movimientodecaja();
                    setInternalFrame(objMovimientodecaja);
                } else {
                    frame.toFront();
                }
                break;
            }

            case 63: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof RptCaja) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    RptCaja objRptCaja = new RptCaja();
                    setInternalFrame(objRptCaja);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 66: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof RepVentasxTipo) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    RepVentasxTipo objRepVentasxTipo = new RepVentasxTipo();
                    setInternalFrame(objRepVentasxTipo);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 68: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof VentasxVenedor) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    VentasxVenedor objVentasxVenedor = new VentasxVenedor();
                    setInternalFrame(objVentasxVenedor);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 69: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof RepPagosxDeudas) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    RepPagosxDeudas objRepPagosxDeudas = new RepPagosxDeudas();
                    setInternalFrame(objRepPagosxDeudas);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 70: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof ReporVentasxDocumento) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    ReporVentasxDocumento objReporVentasxDocumento = new ReporVentasxDocumento();
                    setInternalFrame(objReporVentasxDocumento);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 75: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Rpt_DepositosenCuenta) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Rpt_DepositosenCuenta objRpt_DepositosenCuenta = new Rpt_DepositosenCuenta();
                    setInternalFrame(objRpt_DepositosenCuenta);
                } else {
                    frame.toFront();
                }
                break;
            }

            case 76: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof FrmRetiros) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    FrmRetiros objRpt_FrmRetiros = new FrmRetiros();
                    setInternalFrame(objRpt_FrmRetiros);
                } else {
                    frame.toFront();
                }
                break;

            }

            case 77: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof FrmRepDefectuosos) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    FrmRepDefectuosos objFrmRepDefectuosos = new FrmRepDefectuosos();
                    setInternalFrame(objFrmRepDefectuosos);
                } else {
                    frame.toFront();
                }
                break;
            }

            case 78: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof Backupfrm) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    Backupfrm objBackupfrm = new Backupfrm();
                    setInternalFrame(objBackupfrm);
                } else {
                    frame.toFront();
                }
                break;
            }
            case 79: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof ListaProductosVendidos) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    ListaProductosVendidos objListaProductosVendidos = new ListaProductosVendidos();
                    setInternalFrame(objListaProductosVendidos);
                } else {
                    frame.toFront();
                }
                break;
            }

            case 80: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof DeudasenGeneral) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    DeudasenGeneral objDeudasenGeneral = new DeudasenGeneral();
                    setInternalFrame(objDeudasenGeneral);
                } else {
                    frame.toFront();
                }
                break;
            }

            case 81: {
                for (int i = 0; i < jDesktopPane1.getAllFrames().length; i++) {
                    if (jDesktopPane1.getAllFrames()[i] instanceof frmBalancearStock) {
                        b = true;
                        frame = jDesktopPane1.getAllFrames()[i];
                        break;
                    }
                }
                if (!b) {
                    frmBalancearStock objfrmBalancearStock = new frmBalancearStock();
                    setInternalFrame(objfrmBalancearStock);
                } else {
                    frame.toFront();
                }
                break;
            }

        }
    }

    private void ActualizaEsta() {
        control.Sql = "select idusuario from usuario where nomusr='" + info.getUsuario() + "'";
        String idUsuario = control.DevolverRegistroDto(control.Sql, 1); //Capturamos el id del usuario      
        control.Sql = "update producto set estdo='Disponible' where idproducto in(select idproducto from por_vender "
                + "where idusuario='" + idUsuario + "' and sede='" + Controlador.sede + "') and estdo='Por vender';";
        control.EditarRegistro(control.Sql);
        control.Sql = "delete from por_vender where idusuario='" + idUsuario + "' and sede='" + Controlador.sede + "';";
        control.EliminarRegistro(control.Sql);
    }

    public void SalirDelSistema() {
        if (JOptionPane.showConfirmDialog(null, "Desea Salir del Sistema!!", "", JOptionPane.YES_NO_OPTION) == 0) {
            if (JOptionPane.showConfirmDialog(null, "Deseas Hacer una Copia de Seguridad de La Base de Datos", "", JOptionPane.YES_NO_OPTION) == 0) {
                control.CrearBackupBD_Vrs1();
            }
            dispose();
        }
        ActualizaEsta();
    }

    public void ReiniciarElSistema() {
        this.dispose();
        new Acceso().setVisible(true);
    }

    public Menu() {
        Iniciar();
        jDesktopPane1.setBackground(new Color(195, 221, 240));
    }

    public void OcultarMenu() {
        jMenuItem44.setVisible(false);
        jMenuItem46.setVisible(false);
        jMenuItem47.setVisible(false);
        jMenuItem48.setVisible(false);
        jMenuItem49.setVisible(false);
        jMenuItem52.setVisible(false);
        jMenuItem51.setVisible(false);
        jMenuItem1.setVisible(false);
        jMenuItem33.setVisible(false);
        jMenuItem54.setVisible(false);
        jMenuItem36.setVisible(false);
        jMenuItem34.setVisible(false);
        jMenuItem18.setVisible(false);
        jMenuItem39.setVisible(false);
        jMenuItem41.setVisible(false);
        jMenuItem20.setVisible(false);
        jMenuItem59.setVisible(false);
        jMenuItem17.setVisible(false);
        jMenuItem35.setVisible(false);
        jMenuItem42.setVisible(false);
        jMenuItem45.setVisible(false);
        jMenuItem55.setVisible(false);
        //jMenuItem61.setVisible(false);
        jMenuItem68.setVisible(false);
    }

    public void Iniciar() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/icoChiqui.png")).getImage());
        imagen = new ImageIcon(getClass().getResource("/Imagenes/logo.png")).getImage();
        //Capturando el id del usuario
        control.Sql = "select idusuario from usuario where nomusr='" + info.getUsuario() + "'";
        us = control.DevolverRegistroDto(control.Sql, 1);
        //Capturando el id del usuario
        ActualizaEsta();
        //Actulizar_Anterior_Producto();
        capturarUsuario();
        OcultarMenu();
    }

    public void capturarUsuario() {
        String path = "/Imagenes/emotion_happy.png";
        URL url = this.getClass().getResource(path);
        ImageIcon icon = new ImageIcon(url);
        control.Sql = "select * from usuario where nomusr='" + info.getUsuario() + "'";
        idUSuario = control.DevolverRegistroDto(control.Sql, 1);
        control.Sql = "SELECT nombre FROM vendedores  where idusuario='" + idUSuario + "';";
        String usaior = control.DevolverRegistroDto(control.Sql, 1);
        setTitle("Sistema de Gestión de Ventas de Productos y Servicios Informáticos  Ver 1.0                                                      "
                + "                                                   " + "Usuario: " + usaior + "                   SEDE:    " + Controlador.sede);
    }

    private void Actulizar_Anterior_Producto() {
        /* control.Sql = "select idusuario from usuario where nomusr='" + info.getUsuario() + "'";
         us = control.DevolverRegistroDto(control.Sql, 1);        
         try {
         control.Base.st = control.Base.conec.createStatement();
         control.Base.rt = control.Base.st.executeQuery("select * from por_vender where "
         + "idusuario='" + us + "';");
         while (control.Base.rt.next()) {
         control.Sql = "update producto set estdo='Disponible' where idproducto='" + 
         control.Base.rt.getString(1) + "';";control.EditarRegistro(control.Sql);
         }
         control.Sql = "delete from por_vender where idusuario='" + us + "';";
         control.EliminarRegistro(control.Sql);
         control.Sql="update producto set estdo="
         + "'Disponible' where estdo='Por Distribuir'; ";control.EditarRegistro(control.Sql);       
         control.Sql="update producto set estdo="
         + "'Disponible' where estdo='Por Vender'; ";control.EditarRegistro(control.Sql);    
      
      
         }
         catch (Exception e) {
         e.printStackTrace();
         }
         try {
         control.Base.st = control.Base.conec.createStatement();
         control.Base.rt = control.Base.st.executeQuery("select idproducto from productoensamblaje "
         + "where id=0;");
         while (control.Base.rt.next()) {
         control.Sql = "update producto set estdo='Disponible' where idproducto='" 
         + control.Base.rt.getString(1) + "';"; control.EditarRegistro(control.Sql);
         }    
         control.Sql = "delete from productoensamblaje where id=0;";
         control.EliminarRegistro(control.Sql);
         }
         catch (Exception e) {
         e.printStackTrace();
         }
         */
    }

    public boolean verificarpermiso(String permiso) {
        boolean a = false;
        control.Sql = "SELECT idpermisos FROM permisos where Descripcion='" + permiso + "';";
        String codper = control.DevolverRegistroDto(control.Sql, 1);
        control.Sql = "select count(*) from permisosusuario where idusuario='" + us
                + "' and idpermisos='" + codper + "';";
        String ver = control.DevolverRegistroDto(control.Sql, 1);
        if (ver.compareTo("1") == 0) {
            a = true;
        }
        return a;
    }

    /**
     * *****************Los metodos***************
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jToolBar1 = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        btIngreso = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnGenerarComprobantes = new javax.swing.JMenuItem();
        jMenuItem29 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem37 = new javax.swing.JMenuItem();
        jMenuItem38 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem58 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem69 = new javax.swing.JMenuItem();
        jMenuItem59 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem44 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem48 = new javax.swing.JMenuItem();
        jMenuItem49 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem19 = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenuItem52 = new javax.swing.JMenuItem();
        jMenuItem46 = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jMenuItem50 = new javax.swing.JMenuItem();
        jMenuItem51 = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jMenuItem47 = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem33 = new javax.swing.JMenuItem();
        jMenuItem54 = new javax.swing.JMenuItem();
        jMenuItem63 = new javax.swing.JMenuItem();
        jMenuItem64 = new javax.swing.JMenuItem();
        jMenuItem68 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem36 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem61 = new javax.swing.JMenuItem();
        jMenuItem34 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem39 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem41 = new javax.swing.JMenuItem();
        jMenuItem55 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem30 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem82 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem56 = new javax.swing.JMenuItem();
        jMenuItem53 = new javax.swing.JMenuItem();
        jMenuItem45 = new javax.swing.JMenuItem();
        jMenu12 = new javax.swing.JMenu();
        jMenuItem40 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem72 = new javax.swing.JMenuItem();
        jMenuItem67 = new javax.swing.JMenuItem();
        jMenuItem70 = new javax.swing.JMenuItem();
        jMenu14 = new javax.swing.JMenu();
        jMenuItem42 = new javax.swing.JMenuItem();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenuItem83 = new javax.swing.JMenuItem();
        jMenuItem80 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jDesktopPane1.setBackground(new java.awt.Color(255, 255, 255));
        jDesktopPane1.setForeground(new java.awt.Color(255, 255, 255));
        jDesktopPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jDesktopPane1MouseMoved(evt);
            }
        });
        jDesktopPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDesktopPane1MouseClicked(evt);
            }
        });
        jDesktopPane1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jDesktopPane1ComponentShown(evt);
            }
        });

        jToolBar1.setBackground(new java.awt.Color(165, 223, 239));
        jToolBar1.setRollover(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btIngreso.setBackground(new java.awt.Color(255, 255, 102));
        btIngreso.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btIngreso.setForeground(new java.awt.Color(204, 0, 0));
        btIngreso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/vendermenu.png"))); // NOI18N
        btIngreso.setText("Ingreso Productos");
        btIngreso.setMargin(new java.awt.Insets(2, 1, 2, 1));
        btIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIngresoActionPerformed(evt);
            }
        });
        jPanel1.add(btIngreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 140, 40));

        jToolBar1.add(jPanel1);

        jDesktopPane1.add(jToolBar1);
        jToolBar1.setBounds(0, 0, 1370, 60);

        getContentPane().add(jDesktopPane1, java.awt.BorderLayout.CENTER);

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Key.png"))); // NOI18N
        jMenu1.setMnemonic('c');
        jMenu1.setText("Configuracion y Seguridad del Sistema");

        mnGenerarComprobantes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, java.awt.event.InputEvent.CTRL_MASK));
        mnGenerarComprobantes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/note_go.png"))); // NOI18N
        mnGenerarComprobantes.setText("Iniciar Comprobantes");
        mnGenerarComprobantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGenerarComprobantesActionPerformed(evt);
            }
        });
        jMenu1.add(mnGenerarComprobantes);

        jMenuItem29.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/page_copy.png"))); // NOI18N
        jMenuItem29.setText("Tipos de comprobante");
        jMenuItem29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem29ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem29);
        jMenu1.add(jSeparator1);

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/User.png"))); // NOI18N
        jMenu6.setText("Usuarios");

        jMenuItem23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user_add.png"))); // NOI18N
        jMenuItem23.setText("Crear y actualizar usuarios");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem23);

        jMenuItem24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/password_protect_directories.png"))); // NOI18N
        jMenuItem24.setText("Cambiar Password");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem24);

        jMenu1.add(jMenu6);
        jMenu1.add(jSeparator3);

        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Database.png"))); // NOI18N
        jMenu7.setText("Bases de datos");

        jMenuItem25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/backups.png"))); // NOI18N
        jMenuItem25.setText("Backup de la Base de datos");
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem25);

        jMenuItem26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/download_database.png"))); // NOI18N
        jMenuItem26.setText("Restaurar la Base de datos");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem26);

        jMenu1.add(jMenu7);

        jMenu8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/toolbar.png"))); // NOI18N
        jMenu8.setText("Permisos");

        jMenuItem37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/toolbar_add.png"))); // NOI18N
        jMenuItem37.setText("Agregar Permisos");
        jMenuItem37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem37ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem37);

        jMenuItem38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/toolbar_go.png"))); // NOI18N
        jMenuItem38.setText("Asignar Permisos");
        jMenuItem38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem38ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem38);

        jMenu1.add(jMenu8);
        jMenu1.add(jSeparator2);

        jMenuItem58.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Refresh-icon.png"))); // NOI18N
        jMenuItem58.setText("Reiniciar el Sistema");
        jMenuItem58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem58ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem58);

        jMenuItem22.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jMenuItem22.setText("Salir del Sistema");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem22);

        jMenuItem69.setText("Configuracion");
        jMenuItem69.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem69ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem69);

        jMenuItem59.setText("Generar Guia de Remision");
        jMenuItem59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem59ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem59);

        jMenuBar1.add(jMenu1);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ventasps.png"))); // NOI18N
        jMenu2.setMnemonic('v');
        jMenu2.setText("Ventas");

        jMenuItem12.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/parentesco.png"))); // NOI18N
        jMenuItem12.setText("Clientes");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem12);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/premium_support.png"))); // NOI18N
        jMenuItem6.setText("Proveedores");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);
        jMenu2.add(jSeparator5);

        jMenuItem13.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/coins_in_hand.png"))); // NOI18N
        jMenuItem13.setText("Cotizacion de productos");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem13);

        jMenuItem14.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/money_add.png"))); // NOI18N
        jMenuItem14.setText("Venta de productos");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem14);

        jMenuItem44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/document_copies.png"))); // NOI18N
        jMenuItem44.setText("Generar Guia Remision");
        jMenuItem44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem44ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem44);

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_9, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/document_editing.png"))); // NOI18N
        jMenuItem10.setText("Ver Comprobantes y Ventas");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem10);
        jMenu2.add(jSeparator4);

        jMenuItem48.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/toolbox.png"))); // NOI18N
        jMenuItem48.setText("Facturar Servicio");
        jMenuItem48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem48ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem48);

        jMenuItem49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/document_editing.png"))); // NOI18N
        jMenuItem49.setText("Ver Comprobantes y Servicio");
        jMenuItem49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem49ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem49);
        jMenu2.add(jSeparator6);

        jMenuItem19.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/font_red_delete.png"))); // NOI18N
        jMenuItem19.setText("Anular comprobante de venta");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem19);
        jMenu2.add(jSeparator7);

        jMenuItem52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/page_white_edit.png"))); // NOI18N
        jMenuItem52.setText("Facturar Productos Ensamblados");
        jMenuItem52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem52ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem52);

        jMenuItem46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/generate_ssl_certificate.png"))); // NOI18N
        jMenuItem46.setText("Ver Caducidad");
        jMenuItem46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem46ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem46);
        jMenu2.add(jSeparator8);

        jMenuItem50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/open_folder.png"))); // NOI18N
        jMenuItem50.setText("Particion de Producto");
        jMenuItem50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem50ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem50);

        jMenuItem51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/application_view_gallery.png"))); // NOI18N
        jMenuItem51.setText("Productos para Ensamblaje");
        jMenuItem51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem51ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem51);
        jMenu2.add(jSeparator9);

        jMenuItem47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/coins_in_hand.png"))); // NOI18N
        jMenuItem47.setText("IGV");
        jMenuItem47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem47ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem47);

        jMenuBar1.add(jMenu2);

        jMenu10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/vende.png"))); // NOI18N
        jMenu10.setMnemonic('j');
        jMenu10.setText("Caja");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/category.png"))); // NOI18N
        jMenuItem1.setText("Lineas de producto");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem1);

        jMenuItem33.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/disable_enable_demo_mode.png"))); // NOI18N
        jMenuItem33.setText("Prestador");
        jMenuItem33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem33ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem33);

        jMenuItem54.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/disable_enable_demo_mode.png"))); // NOI18N
        jMenuItem54.setText("Técnicos");
        jMenuItem54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem54ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem54);

        jMenuItem63.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Refresh-icon.png"))); // NOI18N
        jMenuItem63.setText("Registro de E/S a Caja");
        jMenuItem63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem63ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem63);

        jMenuItem64.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_5, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Search1.png"))); // NOI18N
        jMenuItem64.setText("Ver Movimientos de Caja");
        jMenuItem64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem64ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem64);

        jMenuItem68.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem68.setText("Prueba");
        jMenuItem68.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem68ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem68);

        jMenuBar1.add(jMenu10);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Box.png"))); // NOI18N
        jMenu4.setMnemonic('a');
        jMenu4.setText("Almacen");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/list_box.png"))); // NOI18N
        jMenuItem2.setText("Catalogo de producto");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem2);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cart_add.png"))); // NOI18N
        jMenuItem5.setText("Ingreso de productos");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        jMenuItem36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/word_count.png"))); // NOI18N
        jMenuItem36.setText("Agregar Especificaciones");
        jMenuItem36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem36ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem36);

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/basket_full.png"))); // NOI18N
        jMenuItem9.setText("Stock Productos en Almacen");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem9);

        jMenuItem61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/note_go.png"))); // NOI18N
        jMenuItem61.setText("Retirar Prodcutos Defectuosos");
        jMenuItem61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem61ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem61);

        jMenuItem34.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/receipt_share.png"))); // NOI18N
        jMenuItem34.setText("PrestarProductos");
        jMenuItem34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem34ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem34);

        jMenuItem18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/report_go.png"))); // NOI18N
        jMenuItem18.setText("Registar Devolucion");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem18);

        jMenuItem39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/basket_put.png"))); // NOI18N
        jMenuItem39.setText("Ingresar Productos Prestados");
        jMenuItem39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem39ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem39);

        jMenuItem20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/basket.png"))); // NOI18N
        jMenuItem20.setText("Ingresar Productos sin Documento");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem20);

        jMenuItem41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/basket_remove.png"))); // NOI18N
        jMenuItem41.setText("Devolver Prestamo");
        jMenuItem41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem41ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem41);

        jMenuItem55.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/basket_remove.png"))); // NOI18N
        jMenuItem55.setText("Distribuir productos");
        jMenuItem55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem55ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem55);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/odata.png"))); // NOI18N
        jMenuItem3.setText("Marcas");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem3);

        jMenuItem30.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/images.png"))); // NOI18N
        jMenuItem30.setText("Modelos");
        jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem30ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem30);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/group_box.png"))); // NOI18N
        jMenuItem4.setText("Unidad");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenuItem82.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Add to basket.png"))); // NOI18N
        jMenuItem82.setText("Balancear Stock");
        jMenuItem82.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem82ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem82);

        jMenuBar1.add(jMenu4);

        jMenu9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Diagram.png"))); // NOI18N
        jMenu9.setMnemonic('r');
        jMenu9.setText("Reportes");
        jMenu9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu9ActionPerformed(evt);
            }
        });

        jMenuItem56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/acroread.png"))); // NOI18N
        jMenuItem56.setText("Comprobantes Anulados");
        jMenuItem56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem56ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem56);

        jMenuItem53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/acroread.png"))); // NOI18N
        jMenuItem53.setText("Cotizaciones");
        jMenuItem53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem53ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem53);

        jMenuItem45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/acroread.png"))); // NOI18N
        jMenuItem45.setText("Guias de Remision");
        jMenuItem45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem45ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem45);

        jMenu12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/acroread.png"))); // NOI18N
        jMenu12.setText("Las Ventas");

        jMenuItem40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/acroread.png"))); // NOI18N
        jMenuItem40.setText("Monto por Vendedor ");
        jMenuItem40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem40ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem40);

        jMenuItem21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/acroread.png"))); // NOI18N
        jMenuItem21.setText("Ventas");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem21);

        jMenuItem72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/acroread.png"))); // NOI18N
        jMenuItem72.setText("Ventas x Documento");
        jMenuItem72.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem72ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem72);

        jMenuItem67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/acroread.png"))); // NOI18N
        jMenuItem67.setText("Ventas por Tipos");
        jMenuItem67.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem67ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem67);

        jMenuItem70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/acroread.png"))); // NOI18N
        jMenuItem70.setText("VentasxVendedor");
        jMenuItem70.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem70ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem70);

        jMenu9.add(jMenu12);

        jMenu14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/acroread.png"))); // NOI18N
        jMenu14.setText("Los Productos");

        jMenuItem42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/acroread.png"))); // NOI18N
        jMenuItem42.setText("Productos Devueltos");
        jMenuItem42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem42ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem42);

        jMenuItem35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/acroread.png"))); // NOI18N
        jMenuItem35.setText("productos Prestamos");
        jMenuItem35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem35ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem35);

        jMenuItem83.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/acroread.png"))); // NOI18N
        jMenuItem83.setText("Productos Defectuosos");
        jMenuItem83.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem83ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem83);

        jMenuItem80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/acroread.png"))); // NOI18N
        jMenuItem80.setText("Productos Vendidos");
        jMenuItem80.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem80ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem80);

        jMenu9.add(jMenu14);

        jMenuBar1.add(jMenu9);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Info.png"))); // NOI18N
        jMenu5.setMnemonic('y');
        jMenu5.setText("Ayuda");

        jMenuItem16.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem16.setText("Acerca de... ");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem16);

        jMenuItem17.setText("Manual de Usuario");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem17);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        R = jDesktopPane1.getBounds();
}//GEN-LAST:event_formComponentShown
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        if (verificarpermiso(jMenuItem3.getText())) {
            CargarFormulario(2);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }

}//GEN-LAST:event_jMenuItem3ActionPerformed
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        if (verificarpermiso(jMenuItem4.getText())) {
            CargarFormulario(3);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
}//GEN-LAST:event_jMenuItem4ActionPerformed
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if (verificarpermiso(jMenuItem1.getText())) {
            CargarFormulario(4);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
}//GEN-LAST:event_jMenuItem1ActionPerformed
    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
//CargarFormulario(5);
        if (verificarpermiso(jMenuItem6.getText())) {
            CargarFormulario(5);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }

}//GEN-LAST:event_jMenuItem6ActionPerformed
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
// CargarFormulario(7);
        if (verificarpermiso(jMenuItem2.getText())) {
            CargarFormulario(7);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }

}//GEN-LAST:event_jMenuItem2ActionPerformed
    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
// CargarFormulario(8);
        if (verificarpermiso(jMenuItem13.getText())) {
            CargarFormulario(8);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
}//GEN-LAST:event_jMenuItem13ActionPerformed
    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
// CargarFormulario(9);
        if (verificarpermiso(jMenuItem12.getText())) {
            info.control = 0;
            CargarFormulario(9);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
}//GEN-LAST:event_jMenuItem12ActionPerformed
    private void jMenuItem29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem29ActionPerformed
        if (verificarpermiso(jMenuItem29.getText())) {
            CargarFormulario(10);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
}//GEN-LAST:event_jMenuItem29ActionPerformed
    private void mnGenerarComprobantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGenerarComprobantesActionPerformed
        control.Sql = "SELECT count(*) FROM vta_tipocomprobante where Sede='" + Controlador.sede + "'";
        if (Integer.parseInt(control.DevolverRegistroDto(control.Sql, 1)) == 0) {
            JOptionPane.showMessageDialog(null, "No hay Comprobantes para Iniciar");
            return;
        }
        if (verificarpermiso(jMenuItem12.getText())) {
            InfoGeneral.pase = true;
            CargarFormulario(11);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
}//GEN-LAST:event_mnGenerarComprobantesActionPerformed
    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        if (verificarpermiso(jMenuItem14.getText())) {
            CargarFormulario(13);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
}//GEN-LAST:event_jMenuItem14ActionPerformed
    private void jMenuItem30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem30ActionPerformed
        if (verificarpermiso(jMenuItem30.getText())) {
            CargarFormulario(14);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
}//GEN-LAST:event_jMenuItem30ActionPerformed
    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        if (verificarpermiso(jMenuItem5.getText())) {
            String est = "";
            control.Sql = "SELECT estado FROM estado_sinicial";
            est = control.DevolverRegistroDto(control.Sql, 1).toString();
            control.bandera1 = true;
            if (est.equals("1")) {
                control.bandera1 = true;
            } else {
                control.bandera1 = false;
            }
            CargarFormulario(15);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
}//GEN-LAST:event_jMenuItem5ActionPerformed
    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        if (verificarpermiso(jMenuItem9.getText())) {
            CargarFormulario(16);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem9ActionPerformed
    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        if (verificarpermiso(jMenuItem19.getText())) {
            CargarFormulario(19);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem19ActionPerformed
    private void jMenuItem33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem33ActionPerformed
        if (verificarpermiso(jMenuItem33.getText())) {
            CargarFormulario(21);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem33ActionPerformed
    private void jMenuItem34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem34ActionPerformed
        if (verificarpermiso(jMenuItem34.getText())) {
            CargarFormulario(22);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }

    }//GEN-LAST:event_jMenuItem34ActionPerformed
    private void jMenuItem36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem36ActionPerformed

        if (verificarpermiso(jMenuItem36.getText())) {
            CargarFormulario(24);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }

    }//GEN-LAST:event_jMenuItem36ActionPerformed
    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        if (verificarpermiso(jMenuItem23.getText())) {
            CargarFormulario(25);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem23ActionPerformed
    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        if (verificarpermiso(jMenuItem24.getText())) {
            CargarFormulario(26);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem24ActionPerformed
    private void jMenuItem37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem37ActionPerformed
        if (verificarpermiso(jMenuItem37.getText())) {
            CargarFormulario(27);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }

    }//GEN-LAST:event_jMenuItem37ActionPerformed
    private void jMenuItem38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem38ActionPerformed
        if (verificarpermiso(jMenuItem38.getText())) {
            CargarFormulario(28);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem38ActionPerformed
    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        if (verificarpermiso(jMenuItem18.getText())) {
            CargarFormulario(31);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem18ActionPerformed
    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        if (verificarpermiso(jMenuItem21.getText())) {
            CargarFormulario(32);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem21ActionPerformed
    private void jMenuItem35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem35ActionPerformed
        if (verificarpermiso(jMenuItem35.getText())) {
            CargarFormulario(34);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem35ActionPerformed
    private void jMenuItem39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem39ActionPerformed
        if (verificarpermiso(jMenuItem39.getText())) {
            CargarFormulario(35);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem39ActionPerformed
    private void jMenuItem40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem40ActionPerformed
        if (verificarpermiso(jMenuItem40.getText())) {
            CargarFormulario(36);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem40ActionPerformed
    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        if (verificarpermiso(jMenuItem20.getText())) {
            CargarFormulario(37);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem20ActionPerformed
    private void jMenuItem41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem41ActionPerformed
        if (verificarpermiso(jMenuItem41.getText())) {
            CargarFormulario(38);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem41ActionPerformed
    private void jMenuItem42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem42ActionPerformed
        if (verificarpermiso(jMenuItem42.getText())) {
            CargarFormulario(39);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem42ActionPerformed
    private void jMenuItem44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem44ActionPerformed
        if (verificarpermiso(jMenuItem44.getText())) {
            CargarFormulario(42);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem44ActionPerformed
    private void jMenuItem45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem45ActionPerformed
        if (verificarpermiso(jMenuItem45.getText())) {
            CargarFormulario(43);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }

    }//GEN-LAST:event_jMenuItem45ActionPerformed
    private void jMenuItem46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem46ActionPerformed
        if (verificarpermiso(jMenuItem46.getText())) {
            CargarFormulario(44);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem46ActionPerformed
    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        control.CrearBackupBD_Vrs1();
    }//GEN-LAST:event_jMenuItem25ActionPerformed
    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        RestoreDialog restoreDialog = new RestoreDialog(this, true);
        restoreDialog.setLocationRelativeTo(this);
        restoreDialog.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem26ActionPerformed
    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        SalirDelSistema();
    }//GEN-LAST:event_jMenuItem22ActionPerformed
    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        CargarFormulario(45);
    }//GEN-LAST:event_jMenuItem16ActionPerformed
    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        if (verificarpermiso(jMenuItem10.getText())) {
            CargarFormulario(46);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem10ActionPerformed
    private void jMenuItem47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem47ActionPerformed
        if (verificarpermiso(jMenuItem47.getText())) {
            CargarFormulario(47);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem47ActionPerformed
    private void jMenuItem48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem48ActionPerformed
        if (verificarpermiso(jMenuItem48.getText())) {
            CargarFormulario(48);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem48ActionPerformed
    private void jMenuItem49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem49ActionPerformed
        if (verificarpermiso(jMenuItem49.getText())) {
            CargarFormulario(49);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem49ActionPerformed
    private void jMenuItem50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem50ActionPerformed
        if (verificarpermiso(jMenuItem50.getText())) {
            CargarFormulario(50);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem50ActionPerformed
    private void jMenuItem51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem51ActionPerformed
        if (verificarpermiso(jMenuItem51.getText())) {
            CargarFormulario(51);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem51ActionPerformed
    private void jMenuItem52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem52ActionPerformed
        if (verificarpermiso(jMenuItem52.getText())) {
            CargarFormulario(52);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }

    }//GEN-LAST:event_jMenuItem52ActionPerformed
    private void jMenuItem53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem53ActionPerformed
        CargarFormulario(53);
    }//GEN-LAST:event_jMenuItem53ActionPerformed
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

    }//GEN-LAST:event_formWindowActivated
    private void jMenuItem54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem54ActionPerformed
        CargarFormulario(55);
    }//GEN-LAST:event_jMenuItem54ActionPerformed
    private void jMenuItem55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem55ActionPerformed
        CargarFormulario(56);
    }//GEN-LAST:event_jMenuItem55ActionPerformed
    private void jMenuItem56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem56ActionPerformed
        CargarFormulario(57);
    }//GEN-LAST:event_jMenuItem56ActionPerformed
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened
    private void jMenuItem58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem58ActionPerformed
        ReiniciarElSistema();
    }//GEN-LAST:event_jMenuItem58ActionPerformed
    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed

    }//GEN-LAST:event_jMenuItem17ActionPerformed
    private void jMenuItem59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem59ActionPerformed
        FrmGenerarGuiaRemision fggr = new FrmGenerarGuiaRemision(this, true);
        fggr.setIdVenta("17");
        fggr.setLocationRelativeTo(null);
        fggr.setVisible(true);
    }//GEN-LAST:event_jMenuItem59ActionPerformed
    private void jMenuItem61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem61ActionPerformed
        if (verificarpermiso(jMenuItem61.getText())) {
            CargarFormulario(60);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem61ActionPerformed
    private void jMenuItem63ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem63ActionPerformed
        if (verificarpermiso(jMenuItem63.getText())) {
            CargarFormulario(62);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem63ActionPerformed
    private void jMenuItem64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem64ActionPerformed
        if (verificarpermiso(jMenuItem64.getText())) {
            CargarFormulario(63);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem64ActionPerformed
    private void jMenu9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu9ActionPerformed
    private void jMenuItem67ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem67ActionPerformed
        if (verificarpermiso(jMenuItem67.getText())) {
            CargarFormulario(66);

        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem67ActionPerformed
    private void jMenuItem68ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem68ActionPerformed
        CargarFormulario(67);
    }//GEN-LAST:event_jMenuItem68ActionPerformed
    private void jMenuItem69ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem69ActionPerformed
        ConfiguracionDialog configuracion = new ConfiguracionDialog(this, true);
        configuracion.setLocationRelativeTo(this);
        configuracion.setVisible(true);
    }//GEN-LAST:event_jMenuItem69ActionPerformed
    private void jMenuItem70ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem70ActionPerformed
        if (verificarpermiso(jMenuItem70.getText())) {
            CargarFormulario(68);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem70ActionPerformed
    private void jMenuItem72ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem72ActionPerformed
        if (verificarpermiso(jMenuItem72.getText())) {
            CargarFormulario(70);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem72ActionPerformed
    private void jMenuItem83ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem83ActionPerformed
        if (verificarpermiso(jMenuItem83.getText())) {
            CargarFormulario(77);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem83ActionPerformed
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        SalirDelSistema();
    }//GEN-LAST:event_formWindowClosing

    private void jDesktopPane1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jDesktopPane1ComponentShown

    }//GEN-LAST:event_jDesktopPane1ComponentShown

    private void jDesktopPane1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDesktopPane1MouseMoved

    }//GEN-LAST:event_jDesktopPane1MouseMoved

    private void jDesktopPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDesktopPane1MouseClicked
        if (evt.getClickCount() == 2) {
            if (InfoGeneral.usuario.equalsIgnoreCase("admin")) {
                //                if (verificarpermiso("Stock Minimo")) {
                CargarFormulario(54);
                //                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no autorizado");
            }
        }
    }//GEN-LAST:event_jDesktopPane1MouseClicked

    private void btIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIngresoActionPerformed
        // TODO add your handling code here:
         if (verificarpermiso(jMenuItem5.getText())) {
            String est = "";
            control.Sql = "SELECT estado FROM estado_sinicial";
            est = control.DevolverRegistroDto(control.Sql, 1).toString();
            control.bandera1 = true;
            if (est.equals("1")) {
                control.bandera1 = true;
            } else {
                control.bandera1 = false;
            }
            CargarFormulario(15);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_btIngresoActionPerformed

    private void jMenuItem80ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem80ActionPerformed
        if (verificarpermiso(jMenuItem80.getText())) {
            CargarFormulario(79);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem80ActionPerformed

    private void jMenuItem82ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem82ActionPerformed
        if (verificarpermiso(jMenuItem82.getText())) {
            CargarFormulario(81);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene Permiso para este Menu!!");
        }
    }//GEN-LAST:event_jMenuItem82ActionPerformed
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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                new Menu().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btIngreso;
    public static javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu14;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem29;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem30;
    private javax.swing.JMenuItem jMenuItem33;
    private javax.swing.JMenuItem jMenuItem34;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem36;
    private javax.swing.JMenuItem jMenuItem37;
    private javax.swing.JMenuItem jMenuItem38;
    private javax.swing.JMenuItem jMenuItem39;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem40;
    private javax.swing.JMenuItem jMenuItem41;
    private javax.swing.JMenuItem jMenuItem42;
    private javax.swing.JMenuItem jMenuItem44;
    private javax.swing.JMenuItem jMenuItem45;
    private javax.swing.JMenuItem jMenuItem46;
    private javax.swing.JMenuItem jMenuItem47;
    private javax.swing.JMenuItem jMenuItem48;
    private javax.swing.JMenuItem jMenuItem49;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem50;
    private javax.swing.JMenuItem jMenuItem51;
    private javax.swing.JMenuItem jMenuItem52;
    private javax.swing.JMenuItem jMenuItem53;
    private javax.swing.JMenuItem jMenuItem54;
    private javax.swing.JMenuItem jMenuItem55;
    private javax.swing.JMenuItem jMenuItem56;
    private javax.swing.JMenuItem jMenuItem58;
    private javax.swing.JMenuItem jMenuItem59;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem61;
    private javax.swing.JMenuItem jMenuItem63;
    private javax.swing.JMenuItem jMenuItem64;
    private javax.swing.JMenuItem jMenuItem67;
    private javax.swing.JMenuItem jMenuItem68;
    private javax.swing.JMenuItem jMenuItem69;
    private javax.swing.JMenuItem jMenuItem70;
    private javax.swing.JMenuItem jMenuItem72;
    private javax.swing.JMenuItem jMenuItem80;
    private javax.swing.JMenuItem jMenuItem82;
    private javax.swing.JMenuItem jMenuItem83;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem mnGenerarComprobantes;
    // End of variables declaration//GEN-END:variables

}
