/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Omr
 */
public class Configuracion {

    private static Configuracion configuracion;
    private Controladoromar controlador = new Controladoromar();
    private boolean vistaPreviaComprobantesVenta = true;
    private double igv = 18;
    private double tasaDeCambio = 1;
    private int maximoRenglonesFactura = 1;
    private int maximoRenglonesBoleta = 1;
    private int cantidadCopiasSinVistaPrevia = 1;
    private String impresoraFacturaBoleta = "";
    private String impresoraTicket = "";

    private Configuracion() {

    }

    public static Configuracion getInstance() {
        if (configuracion == null) {
            configuracion = new Configuracion();
            configuracion.load();
        }
        return configuracion;
    }

    public boolean isVistaPreviaComprobantesVenta() {
        return vistaPreviaComprobantesVenta;
    }

    public void setVistaPreviaComprobantesVenta(boolean vistaPreviaComprobantesVenta) {
        this.vistaPreviaComprobantesVenta = vistaPreviaComprobantesVenta;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public double getTasaDeCambio() {
        return tasaDeCambio;
    }

    public void setTasaDeCambio(double tasaDeCambio) {
        this.tasaDeCambio = tasaDeCambio;
    }

    public int getMaximoRenglonesFactura() {
        return maximoRenglonesFactura;
    }

    public void setMaximoRenglonesFactura(int maximoRenglonesFactura) {
        this.maximoRenglonesFactura = maximoRenglonesFactura;
    }

    public int getMaximoRenglonesBoleta() {
        return maximoRenglonesBoleta;
    }

    public void setMaximoRenglonesBoleta(int maximoRenglonesBoleta) {
        this.maximoRenglonesBoleta = maximoRenglonesBoleta;
    }

    public int getCantidadCopiasSinVistaPrevia() {
        return cantidadCopiasSinVistaPrevia;
    }

    public void setCantidadCopiasSinVistaPrevia(int cantidadCopiasSinVistaPrevia) {
        this.cantidadCopiasSinVistaPrevia = cantidadCopiasSinVistaPrevia;
    }

    public String getImpresoraFacturaBoleta() {
        return impresoraFacturaBoleta;
    }

    public void setImpresoraFacturaBoleta(String impresoraFacturaBoleta) {
        this.impresoraFacturaBoleta = impresoraFacturaBoleta;
    }

    public String getImpresoraTicket() {
        return impresoraTicket;
    }

    public void setImpresoraTicket(String impresoraTicket) {
        this.impresoraTicket = impresoraTicket;
    }

    public void load() {

        try {
            this.igv = Double.parseDouble(controlador.ejecutarMsg("SELECT `valor` FROM `config` WHERE `idconfig`='igv';"));
        } catch (Exception e) {
        }
         try {
            this.tasaDeCambio = Double.parseDouble(controlador.ejecutarMsg("SELECT `valor` FROM `config` WHERE `idconfig`='tasaDeCambio';"));
        } catch (Exception e) {
        }
        this.vistaPreviaComprobantesVenta = controlador.ejecutarMsg("SELECT `valor` FROM `config` WHERE `idconfig`='vistaPreviaComprobantes';").equals("true");
        try {
            this.maximoRenglonesFactura = Integer.parseInt(controlador.ejecutarMsg("SELECT `valor` FROM `config` WHERE `idconfig`='maxFilasFactura';"));
        } catch (Exception e) {
        }
        try {
            this.maximoRenglonesBoleta = Integer.parseInt(controlador.ejecutarMsg("SELECT `valor` FROM `config` WHERE `idconfig`='maxFilasBoleta';"));
        } catch (Exception e) {
        }
        try {
            this.cantidadCopiasSinVistaPrevia = Integer.parseInt(controlador.ejecutarMsg("SELECT `valor` FROM `config` WHERE `idconfig`='cantidadCopiasSinVistaPrevia';"));
        } catch (Exception e) {
        }
        this.impresoraFacturaBoleta = controlador.ejecutarMsg("SELECT `valor` FROM `config` WHERE `idconfig`='impresoraFacturaBoleta';");
        this.impresoraTicket = controlador.ejecutarMsg("SELECT `valor` FROM `config` WHERE `idconfig`='impresoraTicket';");
    }

    public void save() {
        update("igv", String.valueOf(this.igv));
        update("tasaDeCambio", String.valueOf(this.tasaDeCambio));
        update("vistaPreviaComprobantes", String.valueOf(this.vistaPreviaComprobantesVenta));
        update("maxFilasFactura", String.valueOf(this.maximoRenglonesFactura));
        update("maxFilasBoleta", String.valueOf(this.maximoRenglonesBoleta));
        update("cantidadCopiasSinVistaPrevia", String.valueOf(this.cantidadCopiasSinVistaPrevia));
        update("impresoraFacturaBoleta", this.impresoraFacturaBoleta);
        update("impresoraTicket", this.impresoraTicket);

    }

    private void update(String key, String value) {

        controlador.ejecutar(String.format("INSERT IGNORE INTO config VALUES('%s','%s');", key, value));
        controlador.ejecutar(String.format("UPDATE config c SET c.`valor`='%s' WHERE c.`idconfig`='%s';", value, key));
    }

}
