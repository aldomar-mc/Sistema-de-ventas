-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.1-m2-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema dbventas
--

CREATE DATABASE IF NOT EXISTS dbventas;
USE dbventas;

--
-- Temporary table structure for view `compra_proveedor`
--
DROP TABLE IF EXISTS `compra_proveedor`;
DROP VIEW IF EXISTS `compra_proveedor`;
CREATE TABLE `compra_proveedor` (
  `idproveedor` int(11),
  `idDoc_Compra` int(11),
  `idcompra` int(11),
  `nompro` varchar(45),
  `fec` date,
  `serie` varchar(15),
  `nume` varchar(45),
  `tipo` varchar(45)
);

--
-- Temporary table structure for view `comprobantesvta`
--
DROP TABLE IF EXISTS `comprobantesvta`;
DROP VIEW IF EXISTS `comprobantesvta`;
CREATE TABLE `comprobantesvta` (
  `idComprobantes` int(11),
  `tipcompr` varchar(45),
  `nume` varchar(45),
  `serie` varchar(45),
  `esta` varchar(45)
);

--
-- Temporary table structure for view `credito`
--
DROP TABLE IF EXISTS `credito`;
DROP VIEW IF EXISTS `credito`;
CREATE TABLE `credito` (
  `idventa` int(11),
  `idcliente` int(11),
  `iddeuda` int(11),
  `montdeu` decimal(9,2),
  `pgoinici` decimal(9,2),
  `fecdeud` date,
  `total` decimal(10,2),
  `nomctlg` text,
  `nomclie` varchar(90),
  `fono` varchar(45),
  `nom` varchar(45),
  `dia` int(2),
  `mes` int(2),
  `an` int(4)
);

--
-- Temporary table structure for view `ddddd`
--
DROP TABLE IF EXISTS `ddddd`;
DROP VIEW IF EXISTS `ddddd`;
CREATE TABLE `ddddd` (
  `idventa` int(11),
  `nomclie` varchar(90),
  `nomctlg` text
);

--
-- Temporary table structure for view `deudores`
--
DROP TABLE IF EXISTS `deudores`;
DROP VIEW IF EXISTS `deudores`;
CREATE TABLE `deudores` (
  `idDeuda` int(11),
  `idventa` int(11),
  `idcliente` int(11),
  `montdeu` decimal(9,2),
  `pgoinici` decimal(9,2),
  `fecdeud` date,
  `fecvta` date,
  `moda` varchar(45),
  `nomclie` varchar(90),
  `fono` varchar(45),
  `dir` varchar(75),
  `numident` varchar(45),
  `tipcompr` varchar(45),
  `nume` varchar(45)
);

--
-- Temporary table structure for view `dtscotizacion`
--
DROP TABLE IF EXISTS `dtscotizacion`;
DROP VIEW IF EXISTS `dtscotizacion`;
CREATE TABLE `dtscotizacion` (
  `ID` int(11),
  `Fecha` date,
  `F_Caducidad` varchar(10),
  `Cliente` varchar(90),
  `Direccion` varchar(75),
  `Usuario` varchar(91),
  `codctlg` int(11),
  `nomctlg` longtext,
  `Cant` int(11),
  `Precio` decimal(10,2),
  `Importe` decimal(9,2)
);

--
-- Temporary table structure for view `editar_compro`
--
DROP TABLE IF EXISTS `editar_compro`;
DROP VIEW IF EXISTS `editar_compro`;
CREATE TABLE `editar_compro` (
  `idventa` int(11),
  `fecvta` date,
  `moda` varchar(45),
  `idcliente` int(11),
  `nomclie` varchar(90),
  `dir` varchar(75),
  `fono` varchar(45),
  `tipo` varchar(45),
  `idCompVenta` int(11),
  `fecemi` varchar(45),
  `idComprobantes` int(11),
  `serie` varchar(45),
  `nume` varchar(45),
  `esta` varchar(45),
  `tipcompr` varchar(45)
);

--
-- Temporary table structure for view `impri_comprobante_venta`
--
DROP TABLE IF EXISTS `impri_comprobante_venta`;
DROP VIEW IF EXISTS `impri_comprobante_venta`;
CREATE TABLE `impri_comprobante_venta` (
  `idVenta` int(11),
  `nomclie` varchar(90),
  `dir` varchar(75),
  `numident` varchar(45),
  `nomctlg` text,
  `prc` decimal(9,2),
  `Pretot` decimal(13,4),
  `dia` int(2),
  `mes` int(2),
  `an` int(4),
  `serie` varchar(45),
  `igv` decimal(12,4),
  `tot` decimal(9,2)
);

--
-- Temporary table structure for view `impri_comprobante_venta1`
--
DROP TABLE IF EXISTS `impri_comprobante_venta1`;
DROP VIEW IF EXISTS `impri_comprobante_venta1`;
CREATE TABLE `impri_comprobante_venta1` (
  `idVenta` int(11),
  `nomclie` varchar(90),
  `dir` varchar(75),
  `numident` varchar(45),
  `nomctlg` text,
  `prc` decimal(9,2),
  `Pretot` decimal(9,2),
  `dia` int(2),
  `mes` int(2),
  `an` int(4),
  `serie` varchar(45),
  `igv` decimal(18,4),
  `tot` decimal(19,4),
  `abre` varchar(5),
  `codbrr` varchar(45),
  `seri` varchar(45),
  `ver` varchar(45)
);

--
-- Temporary table structure for view `lavista1`
--
DROP TABLE IF EXISTS `lavista1`;
DROP VIEW IF EXISTS `lavista1`;
CREATE TABLE `lavista1` (
  `idVenta` int(11),
  `Comprobante` varchar(91),
  `Fecha` date,
  `Cliente` varchar(90),
  `Modalidad` varchar(45),
  `Usuario` varchar(91),
  `Monto` decimal(9,2),
  `Des` decimal(9,2),
  `MontDescuento` decimal(10,2),
  `Estado` varchar(45),
  `Tipo` int(11),
  `ElNumero` varchar(91),
  `esta` varchar(45)
);

--
-- Temporary table structure for view `lavista2`
--
DROP TABLE IF EXISTS `lavista2`;
DROP VIEW IF EXISTS `lavista2`;
CREATE TABLE `lavista2` (
  `idVenta` int(11),
  `Comprobante` varchar(91),
  `Fecha` date,
  `Cliente` varchar(90),
  `Modalidad` varchar(45),
  `Usuario` varchar(45),
  `MontoReal` decimal(9,2),
  `Des` decimal(9,2),
  `MontDescuento` decimal(10,2),
  `Estado` varchar(45),
  `Tipo` int(11),
  `ElNumero` varchar(91),
  `esta` varchar(45)
);

--
-- Temporary table structure for view `lavistavta`
--
DROP TABLE IF EXISTS `lavistavta`;
DROP VIEW IF EXISTS `lavistavta`;
CREATE TABLE `lavistavta` (
  `idVenta` int(11),
  `tipcompr` varchar(45),
  `Comprobante` varchar(91),
  `Fecha` date,
  `Cliente` varchar(90),
  `Modalidad` varchar(45),
  `Usuario` varchar(45),
  `MontoReal` decimal(9,2),
  `Des` decimal(9,2),
  `MontDescuento` decimal(10,2),
  `Estado` varchar(45),
  `Tipo` int(11),
  `ElNumero` varchar(91),
  `esta` varchar(45)
);

--
-- Temporary table structure for view `loscatalogos`
--
DROP TABLE IF EXISTS `loscatalogos`;
DROP VIEW IF EXISTS `loscatalogos`;
CREATE TABLE `loscatalogos` (
  `Codigo` int(11),
  `Producto` longtext,
  `Precio` decimal(9,2),
  `abre` varchar(90),
  `StockMinimo` int(11),
  `prexmenor` decimal(9,2),
  `prexmayor` decimal(9,2),
  `codbarra` varchar(45)
);

--
-- Temporary table structure for view `montoporvendedor`
--
DROP TABLE IF EXISTS `montoporvendedor`;
DROP VIEW IF EXISTS `montoporvendedor`;
CREATE TABLE `montoporvendedor` (
  `Vendedor` varchar(45),
  `monto` decimal(9,2),
  `fecha` date
);

--
-- Temporary table structure for view `producto_venta`
--
DROP TABLE IF EXISTS `producto_venta`;
DROP VIEW IF EXISTS `producto_venta`;
CREATE TABLE `producto_venta` (
  `idproducto` int(11),
  `codbrr` varchar(45),
  `estdo` varchar(45),
  `precVenta` decimal(9,2),
  `nomctlg` longtext,
  `seri` varchar(45),
  `nommrc` varchar(80),
  `Sede` varchar(45),
  `prexmenor` decimal(9,2),
  `prexmayor` decimal(9,2),
  `nommod` varchar(90),
  `codctlg` int(11)
);

--
-- Temporary table structure for view `registro_movimiento`
--
DROP TABLE IF EXISTS `registro_movimiento`;
DROP VIEW IF EXISTS `registro_movimiento`;
CREATE TABLE `registro_movimiento` (
  `idDoc_Compra` int(11),
  `serie` varchar(15),
  `tipo` varchar(45),
  `idCompra` int(11),
  `fec` date,
  `idProveedor` int(11),
  `nompro` varchar(45),
  `idproducto` int(11),
  `fecingralm` date,
  `codbrr` varchar(45),
  `costo` decimal(9,2),
  `estdo` varchar(45),
  `idventa` int(11),
  `fecvta` date,
  `moda` varchar(45),
  `idVenta_producto` int(11),
  `prc` decimal(9,2),
  `nomctlg` text,
  `serieventa` varchar(45),
  `tipcompr` varchar(45),
  `seri` varchar(45)
);

--
-- Temporary table structure for view `resporte_ventas`
--
DROP TABLE IF EXISTS `resporte_ventas`;
DROP VIEW IF EXISTS `resporte_ventas`;
CREATE TABLE `resporte_ventas` (
  `idventa` int(11),
  `nomclie` varchar(90),
  `numident` varchar(45),
  `nomctlg` text,
  `prc` decimal(9,2),
  `fecvta` date,
  `serie` varchar(45),
  `moda` varchar(45),
  `nom` varchar(45),
  `nomusr` varchar(45)
);

--
-- Temporary table structure for view `rpteventas`
--
DROP TABLE IF EXISTS `rpteventas`;
DROP VIEW IF EXISTS `rpteventas`;
CREATE TABLE `rpteventas` (
  `idVenta` int(11),
  `Comprobante` varchar(138),
  `Fecha` date,
  `Cliente` varchar(90),
  `Modalidad` varchar(45),
  `Usuario` varchar(45),
  `MontoReal` decimal(9,2),
  `Baseimponible` decimal(12,2),
  `IGV` decimal(13,2),
  `MontDescuento` decimal(10,2),
  `Estado` varchar(45),
  `Tipo` int(11),
  `ElNumero` varchar(91),
  `esta` varchar(45)
);

--
-- Temporary table structure for view `rpteventasfinal`
--
DROP TABLE IF EXISTS `rpteventasfinal`;
DROP VIEW IF EXISTS `rpteventasfinal`;
CREATE TABLE `rpteventasfinal` (
  `idVenta` int(11),
  `tipcompr` varchar(45),
  `ser` varchar(45),
  `Comprobante` varchar(45),
  `Fecha` date,
  `Cliente` varchar(90),
  `Modalidad` varchar(45),
  `Usuario` varchar(45),
  `MontoReal` decimal(9,2),
  `Baseimponible` decimal(12,2),
  `IGV` decimal(13,2),
  `MontDescuento` decimal(9,2),
  `Estado` varchar(45),
  `Tipo` int(11),
  `ElNumero` varchar(91),
  `esta` varchar(45)
);

--
-- Temporary table structure for view `vendedores`
--
DROP TABLE IF EXISTS `vendedores`;
DROP VIEW IF EXISTS `vendedores`;
CREATE TABLE `vendedores` (
  `idusuario` int(11),
  `idTipousuario` int(11),
  `idDatosUsuarios` int(11),
  `nombre` varchar(91),
  `psw` varchar(8),
  `nomusr` varchar(45),
  `dire` varchar(45),
  `tel` varchar(45),
  `dni` varchar(45),
  `nomtpus` varchar(45),
  `idSede` int(10) unsigned,
  `nom` varchar(45),
  `ape` varchar(45)
);

--
-- Temporary table structure for view `venta_detalle`
--
DROP TABLE IF EXISTS `venta_detalle`;
DROP VIEW IF EXISTS `venta_detalle`;
CREATE TABLE `venta_detalle` (
  `idventa` int(11),
  `nomclie` varchar(90),
  `dir` varchar(75),
  `numident` varchar(45),
  `nomctlg` text,
  `prc` decimal(9,2),
  `fec` date,
  `serie` varchar(45),
  `desident` varchar(45),
  `tipcompr` varchar(45),
  `codbrr` varchar(45),
  `seri` varchar(45)
);

--
-- Temporary table structure for view `ventas_imprimir_todo`
--
DROP TABLE IF EXISTS `ventas_imprimir_todo`;
DROP VIEW IF EXISTS `ventas_imprimir_todo`;
CREATE TABLE `ventas_imprimir_todo` (
  `idventa` int(11),
  `nomclie` varchar(90),
  `fecvta` date,
  `moda` varchar(45),
  `nom` varchar(45),
  `total` decimal(31,2),
  `nomusr` varchar(45),
  `nume` varchar(45),
  `tipcompr` varchar(45)
);

--
-- Temporary table structure for view `ver_compra_editar`
--
DROP TABLE IF EXISTS `ver_compra_editar`;
DROP VIEW IF EXISTS `ver_compra_editar`;
CREATE TABLE `ver_compra_editar` (
  `idcompra` int(11),
  `fec` date,
  `idproveedor` int(11),
  `iddoc_compra` int(11),
  `serie` varchar(15),
  `nume` varchar(45),
  `tipo` varchar(45),
  `idproducto` int(11),
  `codbrr` varchar(45),
  `costo` decimal(9,2),
  `seri` varchar(45),
  `periodo` varchar(45),
  `cantiperio` int(10) unsigned,
  `nompro` varchar(45),
  `nomctlg` text
);

--
-- Temporary table structure for view `ver_para_devolver`
--
DROP TABLE IF EXISTS `ver_para_devolver`;
DROP VIEW IF EXISTS `ver_para_devolver`;
CREATE TABLE `ver_para_devolver` (
  `idproducto` int(11),
  `nomctlg` text,
  `codbrr` varchar(45),
  `precVenta` decimal(9,2),
  `estdo` varchar(45),
  `nommod` varchar(90),
  `seri` varchar(45)
);

--
-- Temporary table structure for view `ver_vendido`
--
DROP TABLE IF EXISTS `ver_vendido`;
DROP VIEW IF EXISTS `ver_vendido`;
CREATE TABLE `ver_vendido` (
  `idventa` int(11),
  `idproducto` int(11),
  `codbrr` varchar(45),
  `prc` decimal(9,2),
  `nomctlg` text,
  `nommrc` varchar(80),
  `seri` varchar(45),
  `fecvta` date,
  `Usuario_idusuario` int(11)
);

--
-- Temporary table structure for view `ver_ventas_para_guia`
--
DROP TABLE IF EXISTS `ver_ventas_para_guia`;
DROP VIEW IF EXISTS `ver_ventas_para_guia`;
CREATE TABLE `ver_ventas_para_guia` (
  `idventa` int(11),
  `nomclie` varchar(90),
  `fecvta` date,
  `moda` varchar(45),
  `nom` varchar(45),
  `suma` decimal(31,2),
  `nomusr` varchar(45),
  `nume` varchar(45)
);

--
-- Temporary table structure for view `verproductospordocumentocompra`
--
DROP TABLE IF EXISTS `verproductospordocumentocompra`;
DROP VIEW IF EXISTS `verproductospordocumentocompra`;
CREATE TABLE `verproductospordocumentocompra` (
  `idcompra` int(11),
  `fec` date,
  `idproveedor` int(11),
  `idDoc_Compra` int(11),
  `serie` varchar(15),
  `nume` varchar(45),
  `tipo` varchar(45),
  `idproducto` int(11),
  `fecingralm` date,
  `codbrr` varchar(45),
  `costo` decimal(9,2),
  `estdo` varchar(45),
  `precVenta` decimal(9,2),
  `nomctlg` longtext,
  `nompro` varchar(45)
);

--
-- Temporary table structure for view `vta_catalogo`
--
DROP TABLE IF EXISTS `vta_catalogo`;
DROP VIEW IF EXISTS `vta_catalogo`;
CREATE TABLE `vta_catalogo` (
  `Codigo` int(11),
  `Producto` longtext,
  `Precio` decimal(9,2),
  `Modelo` varchar(90),
  `Marca` varchar(80),
  `abre` varchar(90),
  `StockMinimo` int(11),
  `prexmenor` decimal(9,2),
  `prexmayor` decimal(9,2),
  `Barra` varchar(45)
);

--
-- Temporary table structure for view `vta_cliente`
--
DROP TABLE IF EXISTS `vta_cliente`;
DROP VIEW IF EXISTS `vta_cliente`;
CREATE TABLE `vta_cliente` (
  `idcliente` int(11),
  `nomclie` varchar(90),
  `dir` varchar(75),
  `fono` varchar(45),
  `tipo` varchar(45),
  `idIdentificacion` int(11),
  `desident` varchar(45),
  `numident` varchar(45)
);

--
-- Temporary table structure for view `vta_comprobantes`
--
DROP TABLE IF EXISTS `vta_comprobantes`;
DROP VIEW IF EXISTS `vta_comprobantes`;
CREATE TABLE `vta_comprobantes` (
  `ID` int(11),
  `Serie` varchar(45),
  `Numero` varchar(45),
  `Tipo` varchar(45),
  `Estado` varchar(45),
  `Para` varchar(25),
  `Sede` varchar(45),
  `ruc` varchar(11)
);

--
-- Temporary table structure for view `vta_datosimpresion`
--
DROP TABLE IF EXISTS `vta_datosimpresion`;
DROP VIEW IF EXISTS `vta_datosimpresion`;
CREATE TABLE `vta_datosimpresion` (
  `idCliente` int(11),
  `nomclie` varchar(90),
  `dir` varchar(75),
  `dia` int(2),
  `mes` varchar(15),
  `anio` int(4),
  `Tipo` varchar(45),
  `idVenta` int(11)
);

--
-- Temporary table structure for view `vta_datossede`
--
DROP TABLE IF EXISTS `vta_datossede`;
DROP VIEW IF EXISTS `vta_datossede`;
CREATE TABLE `vta_datossede` (
  `idVenta` int(11),
  `idCompVenta` int(11),
  `idcomprobantes` int(11),
  `tipcompr` varchar(45),
  `serie` varchar(45),
  `nume` varchar(45),
  `fecemi` varchar(45),
  `fecvta` date,
  `nomclie` varchar(90),
  `desident` varchar(45),
  `numident` varchar(45),
  `esta` varchar(45),
  `nomse` varchar(45),
  `idSede` int(10) unsigned
);

--
-- Temporary table structure for view `vta_deuda`
--
DROP TABLE IF EXISTS `vta_deuda`;
DROP VIEW IF EXISTS `vta_deuda`;
CREATE TABLE `vta_deuda` (
  `idCompra` int(11),
  `nompro` varchar(45),
  `tipo` varchar(45),
  `nume` varchar(45),
  `fec` date,
  `Saldo` decimal(32,2),
  `situa` varchar(20),
  `idSede` int(10) unsigned
);

--
-- Temporary table structure for view `vta_importes`
--
DROP TABLE IF EXISTS `vta_importes`;
DROP VIEW IF EXISTS `vta_importes`;
CREATE TABLE `vta_importes` (
  `idventa` int(11),
  `Total` decimal(40,4),
  `SubTotal` decimal(41,2),
  `Igv` decimal(44,4)
);

--
-- Temporary table structure for view `vta_inventarioinicial`
--
DROP TABLE IF EXISTS `vta_inventarioinicial`;
DROP VIEW IF EXISTS `vta_inventarioinicial`;
CREATE TABLE `vta_inventarioinicial` (
  `Codigo` varchar(45),
  `Descripcion` text,
  `Marca` varchar(80),
  `Precio` decimal(9,2),
  `Ingreso` date,
  `nompro` varchar(45),
  `Sede` varchar(45)
);

--
-- Temporary table structure for view `vta_losproveedores`
--
DROP TABLE IF EXISTS `vta_losproveedores`;
DROP VIEW IF EXISTS `vta_losproveedores`;
CREATE TABLE `vta_losproveedores` (
  `Codigo` int(11),
  `Ruc` varchar(11),
  `Proveedor` varchar(45),
  `Direccion` varchar(100),
  `Telefono` varchar(45),
  `Letras` varchar(45)
);

--
-- Temporary table structure for view `vta_maestra_producto`
--
DROP TABLE IF EXISTS `vta_maestra_producto`;
DROP VIEW IF EXISTS `vta_maestra_producto`;
CREATE TABLE `vta_maestra_producto` (
  `ID` int(11),
  `Codigo` varchar(45),
  `Producto` text,
  `Marca` varchar(80),
  `P_Venta` decimal(9,2),
  `Serie` varchar(45),
  `Estado` varchar(45),
  `T_Documento` varchar(45),
  `N_Documento` varchar(45),
  `Sede` varchar(45),
  `Costo` decimal(9,2)
);

--
-- Temporary table structure for view `vta_movcaja`
--
DROP TABLE IF EXISTS `vta_movcaja`;
DROP VIEW IF EXISTS `vta_movcaja`;
CREATE TABLE `vta_movcaja` (
  `idMovimientoCaja` int(11) unsigned,
  `concepto` varchar(150),
  `fch` date,
  `comprobante` varchar(45),
  `tipo` varchar(25),
  `numcomprobante` varchar(91),
  `monto` decimal(9,2),
  `idSede` int(11) unsigned,
  `descuento` decimal(9,2)
);

--
-- Temporary table structure for view `vta_movcaja1`
--
DROP TABLE IF EXISTS `vta_movcaja1`;
DROP VIEW IF EXISTS `vta_movcaja1`;
CREATE TABLE `vta_movcaja1` (
  `concepto` mediumtext,
  `fch` date,
  `comprobante` varchar(45),
  `monto` decimal(9,2),
  `descuento` decimal(9,2),
  `importe` decimal(10,2),
  `numcomprobante` varchar(91),
  `tipo` varchar(25)
);

--
-- Temporary table structure for view `vta_numeroguiasegunventa`
--
DROP TABLE IF EXISTS `vta_numeroguiasegunventa`;
DROP VIEW IF EXISTS `vta_numeroguiasegunventa`;
CREATE TABLE `vta_numeroguiasegunventa` (
  `numeroGuia` varchar(45),
  `idVenta` int(11)
);

--
-- Temporary table structure for view `vta_parafacturar`
--
DROP TABLE IF EXISTS `vta_parafacturar`;
DROP VIEW IF EXISTS `vta_parafacturar`;
CREATE TABLE `vta_parafacturar` (
  `codctlg` int(11),
  `nomctlg` text,
  `cant` decimal(9,2),
  `prec` decimal(9,2),
  `Importe` decimal(18,4),
  `idVenta` int(11),
  `fecvta` date,
  `unidad` varchar(5)
);

--
-- Temporary table structure for view `vta_porvender`
--
DROP TABLE IF EXISTS `vta_porvender`;
DROP VIEW IF EXISTS `vta_porvender`;
CREATE TABLE `vta_porvender` (
  `ID` int(11),
  `Catalogo` longtext,
  `Estado` varchar(45),
  `Usuario` varchar(45),
  `Sede` varchar(45)
);

--
-- Temporary table structure for view `vta_producto1`
--
DROP TABLE IF EXISTS `vta_producto1`;
DROP VIEW IF EXISTS `vta_producto1`;
CREATE TABLE `vta_producto1` (
  `Item` int(11),
  `Codigo` varchar(45),
  `Producto` text,
  `Precio` decimal(9,2),
  `Costo` decimal(9,2),
  `Serie` varchar(45),
  `F_Ingreso` date,
  `Estado` varchar(45),
  `Proveedor` varchar(45)
);

--
-- Temporary table structure for view `vta_productoprestar`
--
DROP TABLE IF EXISTS `vta_productoprestar`;
DROP VIEW IF EXISTS `vta_productoprestar`;
CREATE TABLE `vta_productoprestar` (
  `Codigo` varchar(45),
  `Producto` text,
  `Serie` varchar(45),
  `costo` decimal(9,2),
  `estado` varchar(45)
);

--
-- Temporary table structure for view `vta_productosstock`
--
DROP TABLE IF EXISTS `vta_productosstock`;
DROP VIEW IF EXISTS `vta_productosstock`;
CREATE TABLE `vta_productosstock` (
  `stock` bigint(21),
  `idproducto` int(11),
  `fecingralm` date,
  `Catalogoproducto_codctlg` int(11),
  `idDoc_Compra` int(11),
  `nomctlg` text,
  `idmarca` int(11),
  `idmodelos` int(11),
  `idUnidad` int(11),
  `nommod` varchar(90),
  `nommrc` varchar(80),
  `Sede` varchar(45),
  `Precio` decimal(9,2)
);

--
-- Temporary table structure for view `vta_tipocomprobante`
--
DROP TABLE IF EXISTS `vta_tipocomprobante`;
DROP VIEW IF EXISTS `vta_tipocomprobante`;
CREATE TABLE `vta_tipocomprobante` (
  `ID` int(11),
  `Sede` varchar(45),
  `Ruc` varchar(11),
  `Tipo` varchar(45),
  `Serie` varchar(45),
  `Cant_Digitos` int(11),
  `Para` varchar(25)
);

--
-- Temporary table structure for view `vta_usuarios`
--
DROP TABLE IF EXISTS `vta_usuarios`;
DROP VIEW IF EXISTS `vta_usuarios`;
CREATE TABLE `vta_usuarios` (
  `Codigo` int(11),
  `Usuario` varchar(45),
  `Clave` varchar(8),
  `Tipo` varchar(45),
  `Sede` varchar(45)
);

--
-- Temporary table structure for view `vta_vistaejemplo`
--
DROP TABLE IF EXISTS `vta_vistaejemplo`;
DROP VIEW IF EXISTS `vta_vistaejemplo`;
CREATE TABLE `vta_vistaejemplo` (
  `codbarra` varchar(45),
  `Catalogo` text,
  `Marca` varchar(80),
  `Unidad` varchar(90)
);

--
-- Temporary table structure for view `vtaactvta`
--
DROP TABLE IF EXISTS `vtaactvta`;
DROP VIEW IF EXISTS `vtaactvta`;
CREATE TABLE `vtaactvta` (
  `Idproducto` int(11)
);

--
-- Temporary table structure for view `vtaconsulta`
--
DROP TABLE IF EXISTS `vtaconsulta`;
DROP VIEW IF EXISTS `vtaconsulta`;
CREATE TABLE `vtaconsulta` (
  `Name_exp_1` bigint(11),
  `Sede` bigint(11) unsigned,
  `Name_exp_3` varchar(45),
  `nomctlg` longtext,
  `nommrc` varchar(80),
  `nomuni` varchar(90),
  `Name_exp_7` decimal(9,2),
  `prexmayor` decimal(9,2),
  `Name_exp_9` bigint(21)
);

--
-- Temporary table structure for view `vtapermisosusuarios`
--
DROP TABLE IF EXISTS `vtapermisosusuarios`;
DROP VIEW IF EXISTS `vtapermisosusuarios`;
CREATE TABLE `vtapermisosusuarios` (
  `idpermisos` int(11),
  `descripcion` varchar(65),
  `idPermisosUsuario` int(11),
  `idusuario` int(11)
);

--
-- Temporary table structure for view `vtaprodcutostotal`
--
DROP TABLE IF EXISTS `vtaprodcutostotal`;
DROP VIEW IF EXISTS `vtaprodcutostotal`;
CREATE TABLE `vtaprodcutostotal` (
  `idProducto` int(11),
  `fecingralm` date,
  `Catalogoproducto_codctlg` int(11),
  `codbrr` varchar(45),
  `idDoc_Compra` int(11),
  `costo` decimal(9,2),
  `estdo` varchar(45),
  `precVenta` decimal(9,2),
  `idSerie` int(11),
  `seri` varchar(45),
  `Producto_idProducto` int(11),
  `codctlg` int(11),
  `nomctlg` longtext,
  `precsg` decimal(9,2),
  `idMarca` int(11),
  `idModelos` int(11),
  `idUnidad` int(11),
  `Sede` varchar(45)
);

--
-- Definition of table `anulados`
--

DROP TABLE IF EXISTS `anulados`;
CREATE TABLE `anulados` (
  `idanulados` int(11) NOT NULL AUTO_INCREMENT,
  `idventa` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `idusuario` int(10) unsigned NOT NULL,
  `motivo` varchar(120) NOT NULL,
  `idcomprobantes` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idanulados`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `anulados`
--

/*!40000 ALTER TABLE `anulados` DISABLE KEYS */;
/*!40000 ALTER TABLE `anulados` ENABLE KEYS */;


--
-- Definition of table `aperturaproducto`
--

DROP TABLE IF EXISTS `aperturaproducto`;
CREATE TABLE `aperturaproducto` (
  `idaperturaproducto` int(11) NOT NULL AUTO_INCREMENT,
  `fch` date NOT NULL,
  `hra` time NOT NULL,
  `idusuario` int(11) NOT NULL,
  PRIMARY KEY (`idaperturaproducto`),
  KEY `idusuario` (`idusuario`),
  CONSTRAINT `aperturaproducto_ibfk_1` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `aperturaproducto`
--

/*!40000 ALTER TABLE `aperturaproducto` DISABLE KEYS */;
/*!40000 ALTER TABLE `aperturaproducto` ENABLE KEYS */;


--
-- Definition of table `atencion`
--

DROP TABLE IF EXISTS `atencion`;
CREATE TABLE `atencion` (
  `idatencion` int(11) NOT NULL AUTO_INCREMENT,
  `nomaten` varchar(60) NOT NULL,
  `idCotizacion` int(11) NOT NULL,
  PRIMARY KEY (`idatencion`),
  KEY `idCotizacion` (`idCotizacion`),
  CONSTRAINT `atencion_ibfk_1` FOREIGN KEY (`idCotizacion`) REFERENCES `cotizacion` (`idCotizacion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `atencion`
--

/*!40000 ALTER TABLE `atencion` DISABLE KEYS */;
/*!40000 ALTER TABLE `atencion` ENABLE KEYS */;


--
-- Definition of table `banco`
--

DROP TABLE IF EXISTS `banco`;
CREATE TABLE `banco` (
  `idBanco` int(11) NOT NULL AUTO_INCREMENT,
  `nombco` varchar(45) DEFAULT NULL,
  `direc` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`idBanco`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `banco`
--

/*!40000 ALTER TABLE `banco` DISABLE KEYS */;
/*!40000 ALTER TABLE `banco` ENABLE KEYS */;


--
-- Definition of table `catalogoproducto`
--

DROP TABLE IF EXISTS `catalogoproducto`;
CREATE TABLE `catalogoproducto` (
  `codctlg` int(11) NOT NULL AUTO_INCREMENT,
  `nomctlg` text,
  `precsg` decimal(9,2) DEFAULT NULL,
  `idMarca` int(11) NOT NULL,
  `idModelos` int(11) NOT NULL,
  `idUnidad` int(11) NOT NULL,
  `stockmin` int(11) NOT NULL DEFAULT '0',
  `prexmenor` decimal(9,2) NOT NULL,
  `prexmayor` decimal(9,2) NOT NULL,
  `fechacad` date NOT NULL,
  `codbarra` varchar(45) NOT NULL,
  PRIMARY KEY (`codctlg`),
  KEY `fk_Lineas_Marca1` (`idMarca`),
  KEY `fk_Catalogoproducto_Unidad1` (`idUnidad`),
  KEY `fk_Catalogoproducto_Lineaproducto1` (`idModelos`) USING BTREE,
  CONSTRAINT `fk_Catalogoproducto_Lineaproducto1` FOREIGN KEY (`idModelos`) REFERENCES `modelo` (`idModelos`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Catalogoproducto_Unidad1` FOREIGN KEY (`idUnidad`) REFERENCES `unidad` (`idUnidad`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Lineas_Marca1` FOREIGN KEY (`idMarca`) REFERENCES `marca` (`idMarca`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `catalogoproducto`
--

/*!40000 ALTER TABLE `catalogoproducto` DISABLE KEYS */;
/*!40000 ALTER TABLE `catalogoproducto` ENABLE KEYS */;


--
-- Definition of table `cierreapertura`
--

DROP TABLE IF EXISTS `cierreapertura`;
CREATE TABLE `cierreapertura` (
  `idCierreApertura` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fec` date NOT NULL,
  `hra` time NOT NULL,
  `monto` decimal(9,2) NOT NULL,
  `estado` varchar(20) NOT NULL COMMENT 'Ingreso o Egreso',
  `idusuario` int(11) NOT NULL,
  `idSede` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idCierreApertura`),
  KEY `FK_CierreApertura_1` (`idusuario`),
  KEY `FK_cierreapertura_2` (`idSede`),
  CONSTRAINT `FK_CierreApertura_1` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`),
  CONSTRAINT `FK_cierreapertura_2` FOREIGN KEY (`idSede`) REFERENCES `sede` (`idSede`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cierreapertura`
--

/*!40000 ALTER TABLE `cierreapertura` DISABLE KEYS */;
/*!40000 ALTER TABLE `cierreapertura` ENABLE KEYS */;


--
-- Definition of table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `nomclie` varchar(90) DEFAULT NULL,
  `dir` varchar(75) DEFAULT NULL,
  `fono` varchar(45) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idCliente`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cliente`
--

/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` (`idCliente`,`nomclie`,`dir`,`fono`,`tipo`) VALUES 
 (1,'CLIENTE','AV. GENERAL','985421545','Natural');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;


--
-- Definition of table `comporbanteservicio`
--

DROP TABLE IF EXISTS `comporbanteservicio`;
CREATE TABLE `comporbanteservicio` (
  `idComporbanteServicio` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `idComprobantes` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `total` decimal(9,2) NOT NULL DEFAULT '0.00',
  `igv` decimal(9,2) NOT NULL DEFAULT '0.18',
  PRIMARY KEY (`idComporbanteServicio`),
  KEY `fk_ComporbanteServicio_Comprobantes1` (`idComprobantes`),
  KEY `fk_ComporbanteServicio_Cliente1` (`idCliente`),
  CONSTRAINT `fk_ComporbanteServicio_Cliente1` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ComporbanteServicio_Comprobantes1` FOREIGN KEY (`idComprobantes`) REFERENCES `comprobantes` (`idComprobantes`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comporbanteservicio`
--

/*!40000 ALTER TABLE `comporbanteservicio` DISABLE KEYS */;
/*!40000 ALTER TABLE `comporbanteservicio` ENABLE KEYS */;


--
-- Definition of table `compra`
--

DROP TABLE IF EXISTS `compra`;
CREATE TABLE `compra` (
  `idCompra` int(11) NOT NULL AUTO_INCREMENT,
  `fec` date DEFAULT NULL,
  `idProveedor` int(11) NOT NULL,
  PRIMARY KEY (`idCompra`),
  KEY `fk_Compra_Proveedor1` (`idProveedor`),
  CONSTRAINT `fk_Compra_Proveedor1` FOREIGN KEY (`idProveedor`) REFERENCES `proveedor` (`idProveedor`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `compra`
--

/*!40000 ALTER TABLE `compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `compra` ENABLE KEYS */;


--
-- Definition of table `comprobantes`
--

DROP TABLE IF EXISTS `comprobantes`;
CREATE TABLE `comprobantes` (
  `idComprobantes` int(11) NOT NULL AUTO_INCREMENT,
  `serie` varchar(45) DEFAULT NULL,
  `nume` varchar(45) DEFAULT NULL,
  `esta` varchar(45) DEFAULT NULL,
  `idTipoComprobante` int(11) NOT NULL,
  PRIMARY KEY (`idComprobantes`),
  KEY `fk_Comprobantes_TipoComprobante1` (`idTipoComprobante`),
  CONSTRAINT `FK_comprobantes_1` FOREIGN KEY (`idTipoComprobante`) REFERENCES `tipocomprobante` (`idTipoComprobante`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comprobantes`
--

/*!40000 ALTER TABLE `comprobantes` DISABLE KEYS */;
INSERT INTO `comprobantes` (`idComprobantes`,`serie`,`nume`,`esta`,`idTipoComprobante`) VALUES 
 (1,'001','000001','Activo',1);
/*!40000 ALTER TABLE `comprobantes` ENABLE KEYS */;


--
-- Definition of table `compventa`
--

DROP TABLE IF EXISTS `compventa`;
CREATE TABLE `compventa` (
  `idCompVenta` int(11) NOT NULL AUTO_INCREMENT,
  `fecemi` varchar(45) DEFAULT NULL,
  `idComprobantes` int(11) NOT NULL,
  `idVenta` int(11) NOT NULL,
  PRIMARY KEY (`idCompVenta`),
  KEY `fk_CompVenta_Comprobantes1` (`idComprobantes`),
  KEY `fk_CompVenta_Venta1` (`idVenta`),
  CONSTRAINT `fk_CompVenta_Comprobantes1` FOREIGN KEY (`idComprobantes`) REFERENCES `comprobantes` (`idComprobantes`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_CompVenta_Venta1` FOREIGN KEY (`idVenta`) REFERENCES `venta` (`idVenta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `compventa`
--

/*!40000 ALTER TABLE `compventa` DISABLE KEYS */;
/*!40000 ALTER TABLE `compventa` ENABLE KEYS */;


--
-- Definition of table `config`
--

DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `idconfig` varchar(100) NOT NULL,
  `valor` varchar(100) NOT NULL,
  PRIMARY KEY (`idconfig`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `config`
--

/*!40000 ALTER TABLE `config` DISABLE KEYS */;
INSERT INTO `config` (`idconfig`,`valor`) VALUES 
 ('cantidadCopiasSinVistaPrevia','1'),
 ('igv','18.0'),
 ('impresoraFacturaBoleta','Mostrar Ventana'),
 ('impresoraPredeterminada','18.0'),
 ('impresoraTicket','Microsoft Print to PDF'),
 ('maxFilasBoleta','11'),
 ('maxFilasFactura','11'),
 ('Porcentajevendedor','15'),
 ('tasaDeCambio','1.0'),
 ('vistaPreviaComprobantes','false');
/*!40000 ALTER TABLE `config` ENABLE KEYS */;


--
-- Definition of table `cotizacion`
--

DROP TABLE IF EXISTS `cotizacion`;
CREATE TABLE `cotizacion` (
  `idCotizacion` int(11) NOT NULL AUTO_INCREMENT,
  `fec_ctz` date DEFAULT NULL,
  `diasdur` int(11) DEFAULT NULL,
  `idCliente` int(11) NOT NULL,
  `Usuario_idusuario` int(11) NOT NULL,
  PRIMARY KEY (`idCotizacion`),
  KEY `fk_Cotizacion_Cliente1` (`idCliente`),
  KEY `fk_Cotizacion_Usuario1` (`Usuario_idusuario`),
  CONSTRAINT `fk_Cotizacion_Cliente1` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cotizacion_Usuario1` FOREIGN KEY (`Usuario_idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cotizacion`
--

/*!40000 ALTER TABLE `cotizacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `cotizacion` ENABLE KEYS */;


--
-- Definition of table `cuentaempresa`
--

DROP TABLE IF EXISTS `cuentaempresa`;
CREATE TABLE `cuentaempresa` (
  `idctaemprea` int(11) NOT NULL AUTO_INCREMENT,
  `numcta` varchar(20) NOT NULL,
  `tipcta` varchar(20) NOT NULL,
  `idbanco` int(11) NOT NULL,
  PRIMARY KEY (`idctaemprea`),
  KEY `idbanco` (`idbanco`),
  CONSTRAINT `cuentaempresa_ibfk_1` FOREIGN KEY (`idbanco`) REFERENCES `banco` (`idBanco`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cuentaempresa`
--

/*!40000 ALTER TABLE `cuentaempresa` DISABLE KEYS */;
/*!40000 ALTER TABLE `cuentaempresa` ENABLE KEYS */;


--
-- Definition of table `datosusuarios`
--

DROP TABLE IF EXISTS `datosusuarios`;
CREATE TABLE `datosusuarios` (
  `idDatosUsuarios` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) DEFAULT NULL,
  `ape` varchar(45) DEFAULT NULL,
  `dire` varchar(45) DEFAULT NULL,
  `tel` varchar(45) DEFAULT NULL,
  `dni` varchar(45) DEFAULT NULL,
  `Usuario_idusuario` int(11) NOT NULL,
  PRIMARY KEY (`idDatosUsuarios`),
  KEY `fk_DatosUsuarios_Usuario1` (`Usuario_idusuario`),
  CONSTRAINT `fk_DatosUsuarios_Usuario1` FOREIGN KEY (`Usuario_idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `datosusuarios`
--

/*!40000 ALTER TABLE `datosusuarios` DISABLE KEYS */;
INSERT INTO `datosusuarios` (`idDatosUsuarios`,`nom`,`ape`,`dire`,`tel`,`dni`,`Usuario_idusuario`) VALUES 
 (1,'ALDO OMAR','MORALES CARLOS','Av. aaa','938517731','72080489',1);
/*!40000 ALTER TABLE `datosusuarios` ENABLE KEYS */;


--
-- Definition of table `detallecotizacion`
--

DROP TABLE IF EXISTS `detallecotizacion`;
CREATE TABLE `detallecotizacion` (
  `idDetallecotizacion` int(11) NOT NULL AUTO_INCREMENT,
  `cnt` int(11) DEFAULT NULL,
  `prec` decimal(9,2) DEFAULT NULL,
  `idCotizacion` int(11) NOT NULL,
  `codctlg` int(11) NOT NULL,
  PRIMARY KEY (`idDetallecotizacion`),
  KEY `fk_Detallecotizacion_Cotizacion1` (`idCotizacion`),
  KEY `FK_detallecotizacion_2` (`codctlg`),
  CONSTRAINT `FK_detallecotizacion_2` FOREIGN KEY (`codctlg`) REFERENCES `catalogoproducto` (`codctlg`),
  CONSTRAINT `fk_Detallecotizacion_Cotizacion1` FOREIGN KEY (`idCotizacion`) REFERENCES `cotizacion` (`idCotizacion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `detallecotizacion`
--

/*!40000 ALTER TABLE `detallecotizacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `detallecotizacion` ENABLE KEYS */;


--
-- Definition of table `detalleproducto`
--

DROP TABLE IF EXISTS `detalleproducto`;
CREATE TABLE `detalleproducto` (
  `idDetalleProducto` int(11) NOT NULL AUTO_INCREMENT,
  `cant` int(11) DEFAULT NULL,
  `idcatalogo1` int(11) NOT NULL,
  `idcatalogo2` int(11) NOT NULL,
  PRIMARY KEY (`idDetalleProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `detalleproducto`
--

/*!40000 ALTER TABLE `detalleproducto` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalleproducto` ENABLE KEYS */;


--
-- Definition of table `detventafacturar`
--

DROP TABLE IF EXISTS `detventafacturar`;
CREATE TABLE `detventafacturar` (
  `idDetVentaFacturar` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `codctlg` int(11) NOT NULL,
  `cant` decimal(9,2) NOT NULL,
  `prec` decimal(9,2) NOT NULL,
  `idVenta` int(11) NOT NULL,
  `cantreal` decimal(9,2) NOT NULL,
  `mostrar` int(11) NOT NULL,
  `bandera` int(11) NOT NULL,
  PRIMARY KEY (`idDetVentaFacturar`),
  KEY `FK_DetVentaFacturar_1` (`codctlg`),
  KEY `FK_DetVentaFacturar_2` (`idVenta`),
  CONSTRAINT `FK_DetVentaFacturar_1` FOREIGN KEY (`codctlg`) REFERENCES `catalogoproducto` (`codctlg`),
  CONSTRAINT `FK_DetVentaFacturar_2` FOREIGN KEY (`idVenta`) REFERENCES `venta` (`idVenta`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `detventafacturar`
--

/*!40000 ALTER TABLE `detventafacturar` DISABLE KEYS */;
/*!40000 ALTER TABLE `detventafacturar` ENABLE KEYS */;


--
-- Definition of table `deuda`
--

DROP TABLE IF EXISTS `deuda`;
CREATE TABLE `deuda` (
  `idDeuda` int(11) NOT NULL AUTO_INCREMENT,
  `montdeu` decimal(9,2) DEFAULT NULL,
  `pgoinici` decimal(9,2) DEFAULT NULL,
  `fecdeud` date DEFAULT NULL,
  `idVenta` int(11) NOT NULL,
  PRIMARY KEY (`idDeuda`),
  KEY `fk_Deuda_Venta1` (`idVenta`),
  CONSTRAINT `fk_Deuda_Venta1` FOREIGN KEY (`idVenta`) REFERENCES `venta` (`idVenta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `deuda`
--

/*!40000 ALTER TABLE `deuda` DISABLE KEYS */;
/*!40000 ALTER TABLE `deuda` ENABLE KEYS */;


--
-- Definition of table `doc_compra`
--

DROP TABLE IF EXISTS `doc_compra`;
CREATE TABLE `doc_compra` (
  `idDoc_Compra` int(11) NOT NULL AUTO_INCREMENT,
  `serie` varchar(15) DEFAULT NULL,
  `nume` varchar(45) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `idCompra` int(11) NOT NULL,
  `idSede` int(10) unsigned NOT NULL,
  `situa` varchar(20) NOT NULL COMMENT '''pagado,deuda,parcial''',
  `monto` decimal(9,2) NOT NULL,
  `incluyeigv` int(11) NOT NULL,
  `cometario` text NOT NULL,
  `credcont` varchar(45) NOT NULL,
  `fechapago` date NOT NULL,
  PRIMARY KEY (`idDoc_Compra`),
  KEY `fk_Doc_Compra_Compra1` (`idCompra`),
  KEY `FK_doc_compra_2` (`idSede`),
  CONSTRAINT `FK_doc_compra_2` FOREIGN KEY (`idSede`) REFERENCES `sede` (`idSede`),
  CONSTRAINT `fk_Doc_Compra_Compra1` FOREIGN KEY (`idCompra`) REFERENCES `compra` (`idCompra`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `doc_compra`
--

/*!40000 ALTER TABLE `doc_compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `doc_compra` ENABLE KEYS */;


--
-- Definition of table `estado_sinicial`
--

DROP TABLE IF EXISTS `estado_sinicial`;
CREATE TABLE `estado_sinicial` (
  `idestsini` int(11) NOT NULL AUTO_INCREMENT,
  `estado` int(11) NOT NULL,
  PRIMARY KEY (`idestsini`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `estado_sinicial`
--

/*!40000 ALTER TABLE `estado_sinicial` DISABLE KEYS */;
/*!40000 ALTER TABLE `estado_sinicial` ENABLE KEYS */;


--
-- Definition of table `fecha_caducidad`
--

DROP TABLE IF EXISTS `fecha_caducidad`;
CREATE TABLE `fecha_caducidad` (
  `id_feccad` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_cad` date NOT NULL,
  `id_Producto` int(11) NOT NULL,
  PRIMARY KEY (`id_feccad`),
  KEY `FK_fecha_caducidad` (`id_Producto`),
  CONSTRAINT `FK_fecha_caducidad` FOREIGN KEY (`id_Producto`) REFERENCES `producto` (`idProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `fecha_caducidad`
--

/*!40000 ALTER TABLE `fecha_caducidad` DISABLE KEYS */;
/*!40000 ALTER TABLE `fecha_caducidad` ENABLE KEYS */;


--
-- Definition of table `garantiaxcompra`
--

DROP TABLE IF EXISTS `garantiaxcompra`;
CREATE TABLE `garantiaxcompra` (
  `idGarantiaxCompra` int(11) NOT NULL AUTO_INCREMENT,
  `fecinic` date DEFAULT NULL,
  `fecfin` date DEFAULT NULL,
  `idProducto` int(11) NOT NULL,
  `periodo` varchar(45) NOT NULL,
  `cantiperio` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idGarantiaxCompra`),
  KEY `fk_GarantiaxCompra_Producto1` (`idProducto`),
  CONSTRAINT `fk_GarantiaxCompra_Producto1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `garantiaxcompra`
--

/*!40000 ALTER TABLE `garantiaxcompra` DISABLE KEYS */;
/*!40000 ALTER TABLE `garantiaxcompra` ENABLE KEYS */;


--
-- Definition of table `guiaremision`
--

DROP TABLE IF EXISTS `guiaremision`;
CREATE TABLE `guiaremision` (
  `idGuiaRemision` int(11) NOT NULL AUTO_INCREMENT,
  `idComprobante` int(11) NOT NULL,
  `idVenta` int(11) NOT NULL,
  `fechaEmision` date DEFAULT NULL,
  `fechaInicioTraslado` date DEFAULT NULL,
  `puntoDePartida` varchar(350) DEFAULT NULL,
  `puntoDeLlegada` varchar(350) DEFAULT NULL,
  `marcaPlacaVehiculo` varchar(350) DEFAULT NULL,
  `numeroLicenciaConducir` varchar(150) DEFAULT NULL,
  `constanciaInscripcion` varchar(150) DEFAULT NULL,
  `nombreTransportista` varchar(150) DEFAULT NULL,
  `rucTransportista` varchar(11) DEFAULT NULL,
  `motivoDeTraslado` varchar(1024) DEFAULT NULL,
  `placavehi` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idGuiaRemision`),
  KEY `FK_GuiaRemision_1` (`idVenta`) USING BTREE,
  KEY `FK_guiaremision_2` (`idComprobante`),
  CONSTRAINT `FK_GuiaRemision_1` FOREIGN KEY (`idVenta`) REFERENCES `venta` (`idVenta`),
  CONSTRAINT `FK_guiaremision_2` FOREIGN KEY (`idComprobante`) REFERENCES `comprobantes` (`idComprobantes`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `guiaremision`
--

/*!40000 ALTER TABLE `guiaremision` DISABLE KEYS */;
/*!40000 ALTER TABLE `guiaremision` ENABLE KEYS */;


--
-- Definition of table `identificacion`
--

DROP TABLE IF EXISTS `identificacion`;
CREATE TABLE `identificacion` (
  `idIdentificacion` int(11) NOT NULL AUTO_INCREMENT,
  `desident` varchar(45) DEFAULT NULL,
  `numident` varchar(45) DEFAULT NULL,
  `idCliente` int(11) NOT NULL,
  PRIMARY KEY (`idIdentificacion`),
  KEY `fk_Identificacion_Cliente1` (`idCliente`),
  CONSTRAINT `fk_Identificacion_Cliente1` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `identificacion`
--

/*!40000 ALTER TABLE `identificacion` DISABLE KEYS */;
INSERT INTO `identificacion` (`idIdentificacion`,`desident`,`numident`,`idCliente`) VALUES 
 (1,'Dni','00000000',1);
/*!40000 ALTER TABLE `identificacion` ENABLE KEYS */;


--
-- Definition of table `igv`
--

DROP TABLE IF EXISTS `igv`;
CREATE TABLE `igv` (
  `idigv` int(11) NOT NULL AUTO_INCREMENT,
  `valor` decimal(9,2) DEFAULT NULL,
  `ver` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idigv`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `igv`
--

/*!40000 ALTER TABLE `igv` DISABLE KEYS */;
INSERT INTO `igv` (`idigv`,`valor`,`ver`) VALUES 
 (1,'0.18','18');
/*!40000 ALTER TABLE `igv` ENABLE KEYS */;


--
-- Definition of table `imprimirvarios`
--

DROP TABLE IF EXISTS `imprimirvarios`;
CREATE TABLE `imprimirvarios` (
  `idimp` int(11) NOT NULL AUTO_INCREMENT,
  `codctlg` int(11) DEFAULT NULL,
  PRIMARY KEY (`idimp`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `imprimirvarios`
--

/*!40000 ALTER TABLE `imprimirvarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `imprimirvarios` ENABLE KEYS */;


--
-- Definition of table `inventarioinicial`
--

DROP TABLE IF EXISTS `inventarioinicial`;
CREATE TABLE `inventarioinicial` (
  `idInventarioInicial` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `feching` date NOT NULL,
  `idSede` int(10) unsigned NOT NULL,
  `idProducto` int(11) NOT NULL,
  `idProveedor` int(11) NOT NULL,
  PRIMARY KEY (`idInventarioInicial`),
  KEY `FK_InventarioInicial_1` (`idSede`),
  KEY `FK_InventarioInicial_2` (`idProducto`),
  KEY `FK_InventarioInicial_3` (`idProveedor`),
  CONSTRAINT `FK_InventarioInicial_1` FOREIGN KEY (`idSede`) REFERENCES `sede` (`idSede`),
  CONSTRAINT `FK_InventarioInicial_2` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`),
  CONSTRAINT `FK_InventarioInicial_3` FOREIGN KEY (`idProveedor`) REFERENCES `proveedor` (`idProveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `inventarioinicial`
--

/*!40000 ALTER TABLE `inventarioinicial` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventarioinicial` ENABLE KEYS */;


--
-- Definition of table `marca`
--

DROP TABLE IF EXISTS `marca`;
CREATE TABLE `marca` (
  `idMarca` int(11) NOT NULL AUTO_INCREMENT,
  `nommrc` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`idMarca`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `marca`
--

/*!40000 ALTER TABLE `marca` DISABLE KEYS */;
/*!40000 ALTER TABLE `marca` ENABLE KEYS */;


--
-- Definition of table `modelo`
--

DROP TABLE IF EXISTS `modelo`;
CREATE TABLE `modelo` (
  `idModelos` int(11) NOT NULL AUTO_INCREMENT,
  `nommod` varchar(90) DEFAULT NULL,
  PRIMARY KEY (`idModelos`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `modelo`
--

/*!40000 ALTER TABLE `modelo` DISABLE KEYS */;
/*!40000 ALTER TABLE `modelo` ENABLE KEYS */;


--
-- Definition of table `modelocatalogo`
--

DROP TABLE IF EXISTS `modelocatalogo`;
CREATE TABLE `modelocatalogo` (
  `idModelo` int(11) NOT NULL AUTO_INCREMENT,
  `codctlg` int(11) NOT NULL,
  `idModelos` int(11) NOT NULL,
  PRIMARY KEY (`idModelo`),
  KEY `fk_Modelo_Catalogoproducto1` (`codctlg`),
  KEY `fk_ModeloCatalogo_Modelos1` (`idModelos`),
  CONSTRAINT `fk_ModeloCatalogo_Modelos1` FOREIGN KEY (`idModelos`) REFERENCES `modelo` (`idModelos`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Modelo_Catalogoproducto1` FOREIGN KEY (`codctlg`) REFERENCES `catalogoproducto` (`codctlg`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `modelocatalogo`
--

/*!40000 ALTER TABLE `modelocatalogo` DISABLE KEYS */;
/*!40000 ALTER TABLE `modelocatalogo` ENABLE KEYS */;


--
-- Definition of table `movimientocaja`
--

DROP TABLE IF EXISTS `movimientocaja`;
CREATE TABLE `movimientocaja` (
  `idMovimientoCaja` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `concepto` varchar(150) NOT NULL,
  `fch` date NOT NULL,
  `hra` time NOT NULL,
  `monto` decimal(9,2) NOT NULL,
  `tipo` varchar(25) NOT NULL,
  `comprobante` varchar(20) NOT NULL,
  `numcomprobante` varchar(15) DEFAULT NULL,
  `idUsuario` int(11) NOT NULL,
  `idSede` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idMovimientoCaja`),
  KEY `FK_MovimientoCaja_1` (`idUsuario`),
  KEY `FK_MovimientoCaja_2` (`idSede`),
  CONSTRAINT `FK_MovimientoCaja_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idusuario`),
  CONSTRAINT `FK_MovimientoCaja_2` FOREIGN KEY (`idSede`) REFERENCES `sede` (`idSede`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `movimientocaja`
--

/*!40000 ALTER TABLE `movimientocaja` DISABLE KEYS */;
/*!40000 ALTER TABLE `movimientocaja` ENABLE KEYS */;


--
-- Definition of table `pagodoccompra`
--

DROP TABLE IF EXISTS `pagodoccompra`;
CREATE TABLE `pagodoccompra` (
  `idPagoDocCompra` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fec` date NOT NULL,
  `hra` time NOT NULL,
  `monto` decimal(9,2) NOT NULL,
  `idDoc_Compra` int(11) NOT NULL,
  PRIMARY KEY (`idPagoDocCompra`),
  KEY `FK_PagoDocCompra_1` (`idDoc_Compra`),
  CONSTRAINT `FK_PagoDocCompra_1` FOREIGN KEY (`idDoc_Compra`) REFERENCES `doc_compra` (`idDoc_Compra`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pagodoccompra`
--

/*!40000 ALTER TABLE `pagodoccompra` DISABLE KEYS */;
/*!40000 ALTER TABLE `pagodoccompra` ENABLE KEYS */;


--
-- Definition of table `pagos`
--

DROP TABLE IF EXISTS `pagos`;
CREATE TABLE `pagos` (
  `idpagos` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `id` int(11) NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `usuario` varchar(45) NOT NULL,
  `monto` decimal(9,2) NOT NULL,
  `pagador` text NOT NULL,
  `comprobante` varchar(20) NOT NULL,
  `numerocomprobante` varchar(15) NOT NULL,
  `descuento` decimal(9,2) NOT NULL,
  PRIMARY KEY (`idpagos`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pagos`
--

/*!40000 ALTER TABLE `pagos` DISABLE KEYS */;
/*!40000 ALTER TABLE `pagos` ENABLE KEYS */;


--
-- Definition of table `pagoxdeuda`
--

DROP TABLE IF EXISTS `pagoxdeuda`;
CREATE TABLE `pagoxdeuda` (
  `idPagoxDeuda` int(11) NOT NULL AUTO_INCREMENT,
  `fecpgxdeu` date DEFAULT NULL,
  `montpag` decimal(9,2) DEFAULT NULL,
  `idDeuda` int(11) NOT NULL,
  PRIMARY KEY (`idPagoxDeuda`),
  KEY `fk_PagoxDeuda_Deuda1` (`idDeuda`),
  CONSTRAINT `fk_PagoxDeuda_Deuda1` FOREIGN KEY (`idDeuda`) REFERENCES `deuda` (`idDeuda`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pagoxdeuda`
--

/*!40000 ALTER TABLE `pagoxdeuda` DISABLE KEYS */;
/*!40000 ALTER TABLE `pagoxdeuda` ENABLE KEYS */;


--
-- Definition of table `permisos`
--

DROP TABLE IF EXISTS `permisos`;
CREATE TABLE `permisos` (
  `idPermisos` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(65) NOT NULL,
  PRIMARY KEY (`idPermisos`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `permisos`
--

/*!40000 ALTER TABLE `permisos` DISABLE KEYS */;
INSERT INTO `permisos` (`idPermisos`,`Descripcion`) VALUES 
 (1,'Generar comprobantes de venta'),
 (2,'Crear y actualizar usuarios'),
 (3,'Cambiar Password'),
 (4,'Backup de la Base de datos'),
 (5,'Restaurar la Base de datos'),
 (6,'Tipos de comprobante'),
 (7,'Agregar Permisos'),
 (8,'Asignar Permisos'),
 (9,'Clientes'),
 (10,'Cotizacion de productos'),
 (11,'Venta de productos'),
 (12,'Anular comprobante de venta'),
 (13,'Registrar pagos por deudas'),
 (14,'Generar Guia Remision'),
 (15,'Ver Garantia'),
 (16,'Proveedores'),
 (17,'Bancos'),
 (18,'Cuentas bancarias'),
 (19,'Depositos x Compras'),
 (20,'Ver Compras'),
 (21,'Catalogo de producto'),
 (22,'Ingreso de productos'),
 (23,'Kardex de productos'),
 (24,'Lineas de producto'),
 (25,'Marcas'),
 (26,'Modelos'),
 (27,'Productos'),
 (28,'Prestador'),
 (29,'Stock Productos en Almacen'),
 (30,'Unidad'),
 (31,'PrestarProductos'),
 (32,'Agregar Especificaciones'),
 (33,'Registar Devolucion'),
 (34,'Ingresar Productos Prestados'),
 (35,'Ingresar Productos sin Documento'),
 (36,'Devolver Prestamo'),
 (37,'Ventas'),
 (38,'Movimiento Productos'),
 (39,'Productos Prestamos'),
 (40,'Monto por Vendedor '),
 (41,'Productos Devueltos'),
 (42,'Deudores con Retraso'),
 (43,'Deudas Canceladas'),
 (44,'Guias de Remision'),
 (45,'Ver Comprobantes y Ventas'),
 (46,'IGV'),
 (47,'Facturar Servicio'),
 (48,'Ver Comprobantes y Servicio'),
 (49,'Particion de Producto'),
 (50,'Productos para Ensamblaje'),
 (51,'Facturar Productos Ensamblados'),
 (52,'Iniciar Comprobantes'),
 (53,'Stock Minimo'),
 (54,'Cambio de Sede de Producto'),
 (55,'Realizar Ventas Anteriores'),
 (56,'Cambio de Estado de Productos'),
 (57,'Ver Caducidad'),
 (58,'Orden de Compra'),
 (59,'Registro de E/S a Caja'),
 (60,'Ver Movimientos de Caja'),
 (61,'Canjear Comprobantes'),
 (62,'Compras por Pagar'),
 (63,'Ventas x Documento'),
 (64,'VentasxVendedor'),
 (65,'Pagos x Deudas'),
 (66,'Programar Depositos'),
 (67,'Registrar Depositos a la Empresa'),
 (68,'Retiros de la Cuenta de la Empresa'),
 (69,'Depositos a la Cuenta de la Empresa'),
 (70,'Liquidez en la cuenta bancaria'),
 (71,'Depositos'),
 (72,'Retiros'),
 (73,'Productos Defectuosos'),
 (74,'Registro de obras'),
 (75,'Retirar Prodcutos Defectuosos'),
 (76,'Balancear Stock'),
 (78,'Productos Vendidos');
/*!40000 ALTER TABLE `permisos` ENABLE KEYS */;


--
-- Definition of table `permisosusuario`
--

DROP TABLE IF EXISTS `permisosusuario`;
CREATE TABLE `permisosusuario` (
  `idPermisosUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `idusuario` int(11) NOT NULL,
  `idPermisos` int(11) NOT NULL,
  PRIMARY KEY (`idPermisosUsuario`),
  KEY `fk_PermisosUsuario_Usuario1` (`idusuario`),
  KEY `fk_PermisosUsuario_Permisos1` (`idPermisos`),
  CONSTRAINT `fk_PermisosUsuario_Permisos1` FOREIGN KEY (`idPermisos`) REFERENCES `permisos` (`idPermisos`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_PermisosUsuario_Usuario1` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `permisosusuario`
--

/*!40000 ALTER TABLE `permisosusuario` DISABLE KEYS */;
INSERT INTO `permisosusuario` (`idPermisosUsuario`,`idusuario`,`idPermisos`) VALUES 
 (1,1,1),
 (2,1,2),
 (3,1,3),
 (4,1,4),
 (5,1,5),
 (6,1,6),
 (7,1,7),
 (8,1,8),
 (9,1,9),
 (10,1,10),
 (11,1,11),
 (12,1,12),
 (13,1,13),
 (14,1,14),
 (15,1,15),
 (16,1,16),
 (17,1,17),
 (18,1,18),
 (19,1,19),
 (20,1,20),
 (21,1,21),
 (22,1,22),
 (23,1,23),
 (24,1,24),
 (25,1,25),
 (26,1,26),
 (27,1,27),
 (28,1,28),
 (29,1,29),
 (30,1,30),
 (31,1,31),
 (32,1,32),
 (33,1,33),
 (34,1,34),
 (35,1,35),
 (36,1,36),
 (37,1,37),
 (38,1,38),
 (39,1,39),
 (40,1,40),
 (41,1,41),
 (42,1,42),
 (43,1,43),
 (44,1,44),
 (45,1,45),
 (46,1,46),
 (47,1,47),
 (48,1,48),
 (49,1,49),
 (50,1,50),
 (51,1,51),
 (52,1,52),
 (53,1,53),
 (55,1,54),
 (56,1,55),
 (112,1,57),
 (113,1,56),
 (114,1,58),
 (115,1,59),
 (116,1,60),
 (117,1,61),
 (118,1,62),
 (119,1,63),
 (120,1,64),
 (121,1,65),
 (125,1,66),
 (126,1,67),
 (127,1,68),
 (128,1,69),
 (129,1,70),
 (130,1,71),
 (131,1,72),
 (132,1,73),
 (133,1,74),
 (134,1,75),
 (135,1,76),
 (137,1,78);
/*!40000 ALTER TABLE `permisosusuario` ENABLE KEYS */;


--
-- Definition of table `por_vender`
--

DROP TABLE IF EXISTS `por_vender`;
CREATE TABLE `por_vender` (
  `idproducto` int(11) NOT NULL,
  `idusuario` int(11) NOT NULL,
  `sede` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `por_vender`
--

/*!40000 ALTER TABLE `por_vender` DISABLE KEYS */;
/*!40000 ALTER TABLE `por_vender` ENABLE KEYS */;


--
-- Definition of table `proddefectuosos`
--

DROP TABLE IF EXISTS `proddefectuosos`;
CREATE TABLE `proddefectuosos` (
  `idproddefectuosos` int(11) NOT NULL AUTO_INCREMENT,
  `idproducto` int(11) NOT NULL,
  `idRetiroDefectuosos` int(11) NOT NULL,
  `costo` decimal(9,2) NOT NULL,
  `precio` decimal(9,2) NOT NULL,
  PRIMARY KEY (`idproddefectuosos`),
  KEY `idRetiroDefectuosos` (`idRetiroDefectuosos`),
  CONSTRAINT `proddefectuosos_ibfk_1` FOREIGN KEY (`idRetiroDefectuosos`) REFERENCES `retirodefectuosos` (`idRetiroDefectuosos`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `proddefectuosos`
--

/*!40000 ALTER TABLE `proddefectuosos` DISABLE KEYS */;
/*!40000 ALTER TABLE `proddefectuosos` ENABLE KEYS */;


--
-- Definition of table `producto`
--

DROP TABLE IF EXISTS `producto`;
CREATE TABLE `producto` (
  `idProducto` int(11) NOT NULL AUTO_INCREMENT,
  `fecingralm` date DEFAULT NULL,
  `Catalogoproducto_codctlg` int(11) NOT NULL,
  `codbrr` varchar(45) DEFAULT NULL,
  `idDoc_Compra` int(11) NOT NULL,
  `costo` decimal(9,2) NOT NULL,
  `estdo` varchar(45) NOT NULL,
  `precVenta` decimal(9,2) NOT NULL,
  `idSede` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idProducto`),
  KEY `fk_Producto_Catalogoproducto1` (`Catalogoproducto_codctlg`),
  KEY `fk_Producto_Doc_Compra1` (`idDoc_Compra`),
  KEY `FK_producto_3` (`idSede`),
  CONSTRAINT `fk_Producto_Catalogoproducto1` FOREIGN KEY (`Catalogoproducto_codctlg`) REFERENCES `catalogoproducto` (`codctlg`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Producto_Doc_Compra1` FOREIGN KEY (`idDoc_Compra`) REFERENCES `doc_compra` (`idDoc_Compra`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `producto`
--

/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;


--
-- Definition of table `productoaperturado`
--

DROP TABLE IF EXISTS `productoaperturado`;
CREATE TABLE `productoaperturado` (
  `idproductoaperturado` int(11) NOT NULL AUTO_INCREMENT,
  `idproducto` int(11) NOT NULL,
  `idaperturaproducto` int(11) NOT NULL,
  PRIMARY KEY (`idproductoaperturado`),
  KEY `idaperturaproducto` (`idaperturaproducto`),
  CONSTRAINT `productoaperturado_ibfk_1` FOREIGN KEY (`idaperturaproducto`) REFERENCES `aperturaproducto` (`idaperturaproducto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `productoaperturado`
--

/*!40000 ALTER TABLE `productoaperturado` DISABLE KEYS */;
/*!40000 ALTER TABLE `productoaperturado` ENABLE KEYS */;


--
-- Definition of table `productocantidad`
--

DROP TABLE IF EXISTS `productocantidad`;
CREATE TABLE `productocantidad` (
  `idcantidad` int(11) NOT NULL AUTO_INCREMENT,
  `idproducto` int(11) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  PRIMARY KEY (`idcantidad`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `productocantidad`
--

/*!40000 ALTER TABLE `productocantidad` DISABLE KEYS */;
/*!40000 ALTER TABLE `productocantidad` ENABLE KEYS */;


--
-- Definition of table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
CREATE TABLE `proveedor` (
  `idProveedor` int(11) NOT NULL AUTO_INCREMENT,
  `nompro` varchar(45) DEFAULT NULL,
  `dir` varchar(100) DEFAULT NULL,
  `fono` varchar(45) DEFAULT NULL,
  `ltrs` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idProveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `proveedor`
--

/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;


--
-- Definition of table `retirodefectuosos`
--

DROP TABLE IF EXISTS `retirodefectuosos`;
CREATE TABLE `retirodefectuosos` (
  `idRetiroDefectuosos` int(11) NOT NULL AUTO_INCREMENT,
  `fecre` date NOT NULL,
  `hra` time NOT NULL,
  `obser` varchar(100) NOT NULL,
  `idusuario` int(11) NOT NULL,
  PRIMARY KEY (`idRetiroDefectuosos`),
  KEY `idusuario` (`idusuario`),
  CONSTRAINT `retirodefectuosos_ibfk_1` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `retirodefectuosos`
--

/*!40000 ALTER TABLE `retirodefectuosos` DISABLE KEYS */;
/*!40000 ALTER TABLE `retirodefectuosos` ENABLE KEYS */;


--
-- Definition of table `ruc`
--

DROP TABLE IF EXISTS `ruc`;
CREATE TABLE `ruc` (
  `idRuc` int(11) NOT NULL AUTO_INCREMENT,
  `numruc` varchar(11) DEFAULT NULL,
  `idProveedor` int(11) NOT NULL,
  PRIMARY KEY (`idRuc`),
  KEY `fk_Ruc_Proveedor1` (`idProveedor`),
  CONSTRAINT `fk_Ruc_Proveedor1` FOREIGN KEY (`idProveedor`) REFERENCES `proveedor` (`idProveedor`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ruc`
--

/*!40000 ALTER TABLE `ruc` DISABLE KEYS */;
/*!40000 ALTER TABLE `ruc` ENABLE KEYS */;


--
-- Definition of table `sede`
--

DROP TABLE IF EXISTS `sede`;
CREATE TABLE `sede` (
  `idSede` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nomse` varchar(45) NOT NULL,
  PRIMARY KEY (`idSede`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sede`
--

/*!40000 ALTER TABLE `sede` DISABLE KEYS */;
INSERT INTO `sede` (`idSede`,`nomse`) VALUES 
 (1,'Principal');
/*!40000 ALTER TABLE `sede` ENABLE KEYS */;


--
-- Definition of table `serie`
--

DROP TABLE IF EXISTS `serie`;
CREATE TABLE `serie` (
  `idSerie` int(11) NOT NULL AUTO_INCREMENT,
  `seri` varchar(45) DEFAULT NULL,
  `Producto_idProducto` int(11) NOT NULL,
  PRIMARY KEY (`idSerie`),
  KEY `fk_Serie_Producto1` (`Producto_idProducto`),
  CONSTRAINT `fk_Serie_Producto1` FOREIGN KEY (`Producto_idProducto`) REFERENCES `producto` (`idProducto`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `serie`
--

/*!40000 ALTER TABLE `serie` DISABLE KEYS */;
/*!40000 ALTER TABLE `serie` ENABLE KEYS */;


--
-- Definition of table `tipocomprobante`
--

DROP TABLE IF EXISTS `tipocomprobante`;
CREATE TABLE `tipocomprobante` (
  `idTipoComprobante` int(11) NOT NULL AUTO_INCREMENT,
  `tipcompr` varchar(45) DEFAULT NULL,
  `candig` int(11) NOT NULL,
  `ser` varchar(45) NOT NULL,
  `idSede` int(10) unsigned NOT NULL,
  `ruc` varchar(11) NOT NULL,
  `facde` varchar(25) NOT NULL,
  PRIMARY KEY (`idTipoComprobante`),
  KEY `FK_tipocomprobante_1` (`idSede`),
  CONSTRAINT `FK_tipocomprobante_1` FOREIGN KEY (`idSede`) REFERENCES `sede` (`idSede`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tipocomprobante`
--

/*!40000 ALTER TABLE `tipocomprobante` DISABLE KEYS */;
INSERT INTO `tipocomprobante` (`idTipoComprobante`,`tipcompr`,`candig`,`ser`,`idSede`,`ruc`,`facde`) VALUES 
 (1,'Venta Libre',6,'001',1,'20204040678','Productos'),
 (2,'Guia de Remision',6,'001',1,'20204040678','Productos'),
 (3,'Ticket',6,'001',1,'20204040678','Productos'),
 (4,'Recibo',6,'001',1,'20204040678','Productos'),
 (5,'Factura',6,'001',1,'20204040678','Productos'),
 (6,'Ticket Factura',6,'001',1,'20204040678','Productos'),
 (7,'Nota de Venta',5,'001',1,'20204040678','Productos'),
 (8,'Ticket Boleta',6,'001',1,'20204040678','Productos');
/*!40000 ALTER TABLE `tipocomprobante` ENABLE KEYS */;


--
-- Definition of table `tipousuario`
--

DROP TABLE IF EXISTS `tipousuario`;
CREATE TABLE `tipousuario` (
  `idTipousuario` int(11) NOT NULL AUTO_INCREMENT,
  `nomtpus` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idTipousuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tipousuario`
--

/*!40000 ALTER TABLE `tipousuario` DISABLE KEYS */;
INSERT INTO `tipousuario` (`idTipousuario`,`nomtpus`) VALUES 
 (1,'Administrador'),
 (2,'Vendedor');
/*!40000 ALTER TABLE `tipousuario` ENABLE KEYS */;


--
-- Definition of table `unidad`
--

DROP TABLE IF EXISTS `unidad`;
CREATE TABLE `unidad` (
  `idUnidad` int(11) NOT NULL AUTO_INCREMENT,
  `nomuni` varchar(90) DEFAULT NULL,
  `abre` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`idUnidad`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `unidad`
--

/*!40000 ALTER TABLE `unidad` DISABLE KEYS */;
INSERT INTO `unidad` (`idUnidad`,`nomuni`,`abre`) VALUES 
 (1,'UND',''),
 (2,'SCHT',''),
 (3,'CJA',''),
 (4,'GLN',''),
 (5,'PCK',''),
 (6,'PQT',''),
 (7,'CONO',''),
 (8,'CJON',''),
 (9,'BDN',''),
 (10,'BLD',''),
 (11,'TIRA',''),
 (12,'BLSA',''),
 (13,'KG',''),
 (14,'SACO',''),
 (15,'DOC',''),
 (16,'PAR',''),
 (17,'SBRE',''),
 (18,'PACK',''),
 (19,'BOTELLA',''),
 (20,'PNCH',''),
 (21,'POTE',''),
 (22,'FSCO','');
/*!40000 ALTER TABLE `unidad` ENABLE KEYS */;


--
-- Definition of table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `idusuario` int(11) NOT NULL AUTO_INCREMENT,
  `nomusr` varchar(45) DEFAULT NULL,
  `psw` varchar(8) DEFAULT NULL,
  `Tipousuario_idTipousuario` int(11) NOT NULL,
  `idSede` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idusuario`),
  KEY `fk_Usuario_Tipousuario1` (`Tipousuario_idTipousuario`),
  KEY `FK_usuario_2` (`idSede`),
  CONSTRAINT `FK_usuario_2` FOREIGN KEY (`idSede`) REFERENCES `sede` (`idSede`),
  CONSTRAINT `fk_Usuario_Tipousuario1` FOREIGN KEY (`Tipousuario_idTipousuario`) REFERENCES `tipousuario` (`idTipousuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usuario`
--

/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`idusuario`,`nomusr`,`psw`,`Tipousuario_idTipousuario`,`idSede`) VALUES 
 (1,'admin','admin',1,1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;


--
-- Definition of table `venta`
--

DROP TABLE IF EXISTS `venta`;
CREATE TABLE `venta` (
  `idVenta` int(11) NOT NULL AUTO_INCREMENT,
  `fecvta` date DEFAULT NULL,
  `moda` varchar(45) DEFAULT NULL,
  `idCliente` int(11) NOT NULL,
  `Usuario_idusuario` int(11) NOT NULL,
  `montfactu` decimal(9,2) NOT NULL,
  `montreal` decimal(9,2) NOT NULL,
  `tipo` varchar(1) NOT NULL COMMENT 'N,A,F',
  `descuento` decimal(9,2) NOT NULL,
  `referencia` varchar(150) NOT NULL,
  `vuelto` decimal(9,2) NOT NULL,
  PRIMARY KEY (`idVenta`),
  KEY `fk_Venta_Cliente1` (`idCliente`),
  KEY `fk_Venta_Usuario1` (`Usuario_idusuario`),
  CONSTRAINT `fk_Venta_Cliente1` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Venta_Usuario1` FOREIGN KEY (`Usuario_idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `venta`
--

/*!40000 ALTER TABLE `venta` DISABLE KEYS */;
/*!40000 ALTER TABLE `venta` ENABLE KEYS */;


--
-- Definition of table `venta_producto`
--

DROP TABLE IF EXISTS `venta_producto`;
CREATE TABLE `venta_producto` (
  `idVenta_producto` int(11) NOT NULL AUTO_INCREMENT,
  `prc` decimal(9,2) DEFAULT NULL,
  `Venta_idVenta` int(11) NOT NULL,
  `Producto_idProducto` int(11) NOT NULL,
  `situ` varchar(1) NOT NULL,
  PRIMARY KEY (`idVenta_producto`),
  KEY `fk_Venta_producto_Venta1` (`Venta_idVenta`),
  KEY `fk_Venta_producto_Producto1` (`Producto_idProducto`),
  CONSTRAINT `fk_Venta_producto_Producto1` FOREIGN KEY (`Producto_idProducto`) REFERENCES `producto` (`idProducto`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Venta_producto_Venta1` FOREIGN KEY (`Venta_idVenta`) REFERENCES `venta` (`idVenta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `venta_producto`
--

/*!40000 ALTER TABLE `venta_producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `venta_producto` ENABLE KEYS */;


--
-- Definition of function `CadFac`
--

DROP FUNCTION IF EXISTS `CadFac`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `CadFac`(n int) RETURNS varchar(1000) CHARSET latin1
BEGIN
DECLARE crt int;
DECLARE i int default 1;
DECLARE rst VARCHAR(1000) DEFAULT '';
DECLARE num VARCHAR(100) DEFAULT '';
DECLARE fac VARCHAR(100) DEFAULT '';
set num=n;
if num<=15 and num>=2 then
while i<=num do
set fac=concat(factorial(i));
if length(rst)=0 then
set rst=concat(rst,fac);
else
set rst=concat(rst,' , ',fac);
end if;
set i=i+1;
end while;
return rst;
end if;
set rst=concat('El numero ',num,'no cumple la condicion');
return rst;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `CalcularMontoCaja`
--

DROP FUNCTION IF EXISTS `CalcularMontoCaja`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `CalcularMontoCaja`() RETURNS decimal(9,2)
BEGIN
 declare res decimal(9,2) default 0.0;
 declare ing decimal(9,2) default 0.0;
 declare egre decimal(9,2) default 0.0;

 if(select count(*) from movimientocaja)>0 then
  set ing=(select sum(monto) from movimientocaja where tipo='Ingreso'); 
  set egre=(select sum(monto) from movimientocaja where tipo='Egreso');
  set res=ing-egre;
 end if;
 return res;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `DevolverFechadeGarantia`
--

DROP FUNCTION IF EXISTS `DevolverFechadeGarantia`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `DevolverFechadeGarantia`(tpo int,cant int) RETURNS date
BEGIN
 declare fecres date;
 if tpo=1 then
  SELECT DATE_ADD(curdate(), INTERVAL cant month) into fecres;
 end if;
 if tpo=2 then
  SELECT DATE_ADD(curdate(), INTERVAL cant Year) into fecres;
 end if;
 return fecres;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `Factorial`
--

DROP FUNCTION IF EXISTS `Factorial`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `Factorial`(n int) RETURNS int(11)
begin
  Declare fa int default 1;
  Declare num int default 1;
  while num<=n do
   Set fa=fa*num;
   Set num=num+1;
  end while;
  return fa;
 end $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `Factorial2`
--

DROP FUNCTION IF EXISTS `Factorial2`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `Factorial2`(n int) RETURNS varchar(45) CHARSET latin1
begin
 declare num int default 1;
 declare i int default 1;
 declare resp varchar(45) default '';
 if n>2 and n<14 then
   while i<=n do
    set num=num*i;
    set i=i+1;
    if i=2 then
      set resp=(select concat(resp,num));
    else
      set resp=(select concat(resp,',',num));
    end if;
    end while;
    return resp;
  else
    set resp=(select concat('El numero: ',n,' no cumple con la condicion'));
    return resp;
  end if;
end $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `Factorial3`
--

DROP FUNCTION IF EXISTS `Factorial3`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `Factorial3`(n int) RETURNS varchar(45) CHARSET latin1
begin
 declare num,j int default 1;
 declare i int default 1;
 declare resp varchar(45) default '';
 if n>2 and n<14 then
   while i<=n do
      set j=Factorial(i);
      set resp=(concat(resp,',',j));
    end while;
  else
    set resp=(concat('El numero: ',n,' no cumple con la condicion'));
  end if;
  return resp;
end $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `FactorialCond`
--

DROP FUNCTION IF EXISTS `FactorialCond`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `FactorialCond`(n int) RETURNS varchar(100) CHARSET latin1
begin
declare fa int default 1;
declare num int default 1;
if (n>=1 and n<=10) then
while num<=n do
set fa=fa*num;
set num=num+1;
end while;
return concat('El factorial del numero ',n,' es: ', fa);
else
return concat('El numero ',n, ' no cumple con la condicion');
end if;
end $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `GeneraDigitos`
--

DROP FUNCTION IF EXISTS `GeneraDigitos`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `GeneraDigitos`(cdig int,num int) RETURNS varchar(30) CHARSET latin1
BEGIN
declare cares varchar(30) default '';
 declare ctd int default 1;
 set cdig=cdig-length(num);
 while ctd<=cdig do
  set cares=concat('0',cares);
  set ctd=ctd+1;
 end while;
 set cares=concat(cares,num);
 return cares;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `initcap`
--

DROP FUNCTION IF EXISTS `initcap`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `initcap`(cadena VARCHAR(100)) RETURNS varchar(100) CHARSET latin1
BEGIN 
DECLARE pos INT DEFAULT 0; 
DECLARE tmp VARCHAR(100) DEFAULT ''; 
DECLARE result VARCHAR(100) DEFAULT ''; 
REPEAT SET pos = LOCATE(' ', cadena); 
 IF pos = 0 THEN SET pos = CHAR_LENGTH(cadena); 
 END IF; 
 SET tmp = LEFT(cadena,pos);
 SET result = CONCAT(result, UPPER(LEFT(tmp,1)),SUBSTR(tmp,2));
 SET cadena = RIGHT(cadena,CHAR_LENGTH(cadena)-pos);
UNTIL CHAR_LENGTH(cadena) = 0 END REPEAT;
RETURN result;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `InsertaCaracteristica`
--

DROP FUNCTION IF EXISTS `InsertaCaracteristica`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `InsertaCaracteristica`( carac varchar(45)) RETURNS int(11)
BEGIN
declare idcompr int;
   insert into caracteriticas (descripcion) values (carac);
   set idcompr=(select idcaracteriticas from caracteriticas where descripcion=carac order by idcaracteriticas desc limit 1);
return idcompr;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `InsertaCompra`
--

DROP FUNCTION IF EXISTS `InsertaCompra`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `InsertaCompra`( fechadoc date, idprov int, serdoc varchar(45), numen varchar(45),
tip varchar(40),idsed int,mont decimal(9,2),igv int,comen text, credco varchar(30),fechpa date) RETURNS int(11)
BEGIN
declare idcompr int;
   insert into compra (fec, idproveedor) values (fechadoc,idprov);
   set idcompr=(select idCompra from compra order by idCompra desc limit 1);
insert into doc_compra(serie,nume,tipo,idCompra,idSede,situa,monto, incluyeigv, cometario, credcont, fechapago)
values(serdoc,numen,tip,idcompr,idsed,'parcial',mont,igv,comen,credco,fechpa);
set idcompr=(select idDoc_Compra from doc_compra order by idDoc_Compra desc limit 1);
return idcompr;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `InsertaComprobante`
--

DROP FUNCTION IF EXISTS `InsertaComprobante`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `InsertaComprobante`(tipocomp varchar(45), venta int) RETURNS int(11)
BEGIN
declare idComprVe int;
declare codcomp int;
declare codComprobante int;
set codComprobante=tipocomp;
insert into compventa (fecemi,idComprobantes,idVenta) values(curdate(),tipocomp, venta);
update comprobantes set esta='Emitido' where idComprobantes=tipocomp;
set idComprVe =(select idCompVenta from compventa order by idCompVenta desc limit 1);
return idComprVe;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `InsertaCotizacion`
--

DROP FUNCTION IF EXISTS `InsertaCotizacion`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `InsertaCotizacion`( cliente int, nusuario varchar(45), dias int) RETURNS int(11)
BEGIN
declare idcoti int;
declare codus int;
set codus=(select  idusuario from usuario where nomusr=nusuario);
   insert into cotizacion (fec_ctz, diasdur,idcliente, Usuario_idusuario) values (curdate(), dias, cliente, codus);
   set idcoti=(select idcotizacion from cotizacion order by idcotizacion desc limit 1);
return idcoti;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `InsertaGuia`
--

DROP FUNCTION IF EXISTS `InsertaGuia`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `InsertaGuia`() RETURNS int(11)
BEGIN
declare idven int;
declare idcomp int;
declare idtipoc int;
set idtipoc=(select idtipocomprobante from tipocomprobante where tipcompr='Guia de Remision');
set idcomp=(select idcomprobantes from comprobantes where idtipocomprobante=idtipoc and esta='Activo' order by idcomprobantes desc limit 1);
   insert into guia_remision (fechae, idcomprobantes) values (curdate(),idcomp);
   set idven=(select idguia_remision from guia_remision  order by idguia_remision desc limit 1);
return idven;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `InsertaServiciopre`
--

DROP FUNCTION IF EXISTS `InsertaServiciopre`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `InsertaServiciopre`(fec varchar(45), comp varchar(45), clie varchar(45),tot decimal(9,2) ) RETURNS int(11)
BEGIN
declare idtipoous int;
declare codus int;
declare valorIgv decimal(9,2 )default 0.18;
select valor into valorIgv from igv limit 1;
insert into comporbanteservicio(fecha, idcomprobantes,idcliente,igv,total)values(fec,comp,clie,valorIgv,tot);
set idtipoous=(select idcomporbanteservicio from comporbanteservicio order by idcomporbanteservicio desc limit 1);
return idtipoous;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `InsertaVendedor`
--

DROP FUNCTION IF EXISTS `InsertaVendedor`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `InsertaVendedor`(nomusu varchar(45), pas varchar(45), tipo varchar(45),idsed int) RETURNS int(11)
BEGIN
declare idtipoous int;
declare codus int;
set idtipoous=(select  idTipousuario from tipousuario where nomtpus=tipo);
   insert into usuario (nomusr, psw,Tipousuario_idTipousuario,idSede) values (nomusu, pas, idtipoous,idsed);
   set codus=(select idusuario from usuario order by idusuario desc limit 1);
return codus;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `InsertaVentas`
--

DROP FUNCTION IF EXISTS `InsertaVentas`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `InsertaVentas`(modalidad varchar(45),
cliente int, usuario varchar(45),montfactura decimal(9,2),mntreal decimal(9,2),
tpo varchar(1),descu decimal(9,2),refer varchar(150), vuel decimal(9,2)) RETURNS int(11)
BEGIN
 declare idven int; declare codus int;
 set codus=(select  idusuario from usuario where nomusr=usuario);

  insert into venta (fecvta, moda,idcliente, Usuario_idusuario,montfactu,montreal,tipo,descuento,referencia, vuelto)
 values (curdate(), modalidad, cliente, codus,montfactura,mntreal,tpo,descu,refer,vuel );

 set idven=(select idventa from venta  where fecvta=curdate() and idcliente=cliente
 and Usuario_idusuario=codus order by idventa desc limit 1);
 return idven;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `mes`
--

DROP FUNCTION IF EXISTS `mes`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `mes`(m int) RETURNS varchar(15) CHARSET latin1
BEGIN
 declare rst varchar(30) default '';
 if m=1 then
  set rst='Enero';
 end if;
 if m=2 then
  set rst='Febrero';
 end if;
 if m=3 then
  set rst='Marzo';
 end if;
 if m=4 then
  set rst='Abril';
 end if;
 if m=5 then
  set rst='Mayo';
 end if;
 if m=6 then
  set rst='Junio';
 end if;
 if m=7 then
  set rst='Julio';
 end if;
 if m=8 then
  set rst='Agosto';
 end if;
 if m=9 then
  set rst='Septiembre';
 end if;
 if m=10 then
  set rst='Octubre';
 end if;
 if m=11 then
  set rst='Noviembre';
 end if;
 if m=12 then
  set rst='Diciembre';
 end if;
 return rst;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `PrimLetras`
--

DROP FUNCTION IF EXISTS `PrimLetras`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `PrimLetras`(Cadena Varchar(80)) RETURNS varchar(20) CHARSET latin1
BEGIN
 declare rst varchar(20) default '';
 declare ct int default 0;
 while ct<=length(Cadena) do
  if mid(Cadena,ct,1)=space(1) then
   set rst=Concat(rst,mid(Cadena,ct+1,1));
  end if;
  set ct=ct+1;
 end while;
 return ucase(rst);
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `Ver_Guia`
--

DROP FUNCTION IF EXISTS `Ver_Guia`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `Ver_Guia`(idv int) RETURNS varchar(30) CHARSET latin1
BEGIN
declare gi int default 0;
declare cares varchar(30) default '';
set gi=(select count(*) from vta_numeroguiasegunventa where idventa=idv);
if gi=1 then
  set cares =   (select numeroGuia from vta_numeroguiasegunventa where idventa=idv);
else
  set cares='';
end if;
return cares;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `ActualizarEstadoProducto`
--

DROP PROCEDURE IF EXISTS `ActualizarEstadoProducto`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `ActualizarEstadoProducto`(in cdg varchar(60),in estd varchar(20))
BEGIN
 update producto set estdo=estd where codbrr=cdg;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `actualizarPRecio`
--

DROP PROCEDURE IF EXISTS `actualizarPRecio`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarPRecio`()
begin
declare pecs decimal(9,2);
declare idactalogo int;
declare dato int;
set dato=1;
while dato < 205 do
set idactalogo=(select catalogoproducto_codctlg from producto where idproducto=dato);
set pecs=(select precsg from catalogoproducto where codctlg=idactalogo);
update producto set precVenta= pecs where idproducto=dato;
set dato=dato+1;
end while;
end $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `agregardcevueltos`
--

DROP PROCEDURE IF EXISTS `agregardcevueltos`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregardcevueltos`(in nompr varchar(90),in codbarr varchar(45),
 in prven decimal(9,2), in peri int,in canti int,in codbarrante varchar(45), in ser varchar(20))
begin
declare codcatla int;
declare codPro int;
declare iddoccom int;
declare csotan varchar(20);
set csotan=(select costo from productos_prestados where codbrr=codbarrante);
set iddoccom=(select idDoc_Compra from productos_prestados where codbrr=codbarrante);
set codPro=(select idproducto from productos_prestados where codbrr=codbarrante);
set codcatla= (select codctlg from catalogoproducto where nomctlg=nompr);
update producto set estdo='Prestado Devuelto' where idproducto=codPro;
insert into producto (fecingralm, Catalogoproducto_codctlg, codbrr, idDoc_Compra, costo,estdo, precVenta)
values(curdate(),codcatla,codbarr,iddoccom, csotan,'Disponible',prven);
set codPro=(select idproducto from producto where codbrr=codbarr);
insert into serie (seri, Producto_idProducto) values(ser, codPro);
if peri=1 then
  insert into garantiaxcompra (fecinic, fecfin, idproducto, periodo, cantiperio)
  values(curdate(),adddate(curdate(),interval canti month),codPro,peri,canti);
else
  insert into garantiaxcompra (fecinic, fecfin, idproducto, periodo, cantiperio)
  values(curdate(),adddate(curdate(),interval canti year),codPro,peri,canti);
end if;
select codpro;
end $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `AnulandoComprobante`
--

DROP PROCEDURE IF EXISTS `AnulandoComprobante`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `AnulandoComprobante`(in comp int, in idnewtipo int,in evt int)
BEGIN
 declare num int;declare cndig int;
 declare seri varchar(20) default '';
 if evt=1 then
  if (SELECT count(*) FROM comprobantes where idTipoComprobante=idnewtipo and esta='Activo')=0 then
   set seri=(select ser from tipocomprobante where idTipoComprobante=idnewtipo limit 1);
   set num=(select nume from comprobantes where idTipoComprobante=idnewtipo order by nume desc limit 1);set num=num+1;
   set cndig=(select candig from tipocomprobante where idTipocomprobante=idnewtipo);
   insert into comprobantes(serie,nume,esta,idTipoComprobante) values(seri,GeneraDigitos(cndig,num),'Activo',idnewtipo);
   set num=(select idComprobantes from comprobantes where esta='Activo' and idTipoComprobante=idnewtipo order by idcomprobantes desc limit 1);
  else
   set num=(select idComprobantes from comprobantes where esta='Activo' and idTipoComprobante=idnewtipo limit 1);
  end if;
   update comprobantes set esta='Anulado' where idComprobantes=comp;
   update comprobantes set esta='Emitido' where idComprobantes=num;
   update compventa set idComprobantes=num,fecemi=curdate() where idComprobantes=comp;
 end if;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `Comprobantes`
--

DROP PROCEDURE IF EXISTS `Comprobantes`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Comprobantes`(in idtpo int,in numini int ,in evt int)
BEGIN
 declare ser varchar(20) default '';
 declare cdig int default 0;
 if evt=0 then
  if (select count(*) from comprobantes where idTipoComprobante=idtpo)=0 then
   set ser=(select ser from tipocomprobante where idTipoComprobante=idtpo);
   set cdig=(select candig from tipocomprobante where idTipoComprobante=idtpo);
    set cdig=cdig-length(numini);
   set cdig=GeneraDigitos(cdig,numini);
   insert into comprobantes(serie,nume,esta,idTipoComprobante) values(ser,cdig,'Activo',idtpo);
   select 'Se inicio correctamente';
  else
   select 'Este tipo de comprobante ya esta iniciado';
  end if;
 end if;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `ComprobantesComputec2`
--

DROP PROCEDURE IF EXISTS `ComprobantesComputec2`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `ComprobantesComputec2`(in idcmp int,in cdig int,
in idtipo int,in numini int,in evt int)
BEGIN
declare elnum varchar(40) default '';
if evt=0 then
 if (SELECT count(*) FROM comprobantes where idTipoComprobante=idtipo)=0 then
  set elnum=GeneraDigitos(cdig,numini);
  insert into comprobantes(nume,esta,idTipoComprobante) values(elnum,'Activo',idtipo);
  select 'Se inicio correctamente el tipo de comprobante';
 else
  select 'El Documento en cuestion ya fue inicializado';
 end if;
end if;
if evt=1 then
 set elnum=numini+1;
 set elnum=GeneraDigitos(cdig,numini);
 insert into comprobantes(nume,esta,idTipoComprobante) values(numini,'Activo',idtipo);
 select (select nume from comprobantes where esta='Activo' order by nume desc limit 1);
end if;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `CrearPrestador`
--

DROP PROCEDURE IF EXISTS `CrearPrestador`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `CrearPrestador`(in pres varchar(80),in res varchar(80),in fno varchar(20),in direc varchar(80))
BEGIN
 declare resp varchar(80) default '';
 select idprestador into res from prestador where nomprest=pres and respon=res;
 if isnull(resp) then
  set res="";
 end if;
 if length(resp)=0 then
  insert into prestador(nomprest,respon,fono,direc) values(pres,res,fno,direc);
 end if;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `CrearPrestamoExterno`
--

DROP PROCEDURE IF EXISTS `CrearPrestamoExterno`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `CrearPrestamoExterno`(in prestador int,in fdev date)
BEGIN
 declare cod varchar(40) default'';
 Declare pre,cant,i,idp int;
 DECLARE curtem CURSOR FOR SELECT * FROM pempoprestamo;
 insert into prestamo(fecini) values(curdate());
 set pre=(select idPrestamo from prestamo order by idPrestamo desc limit 1);
  insert into externo(fecdev,sit,idPrestamo,idPrestador) Values(fdev,'Activo',pre,prestador);
 set cant=(select count(*) from tempoprestamo);
 set i=1;
 OPEN curtem;
 While i<=cant Do
  FETCH curtem INTO cod;
  set idp=(select idProducto from producto where codbrr=cod);
  update producto set estdo='Prestado' where codbrr=cod;
  insert into prestamoproducto(idPrestamo,idProducto) values(pre,idp);
  set i=i+1;
 End While;
 CLOSE curtem;
 drop table tempoprestamo;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `CrearTipoComprobanteICECompured`
--

DROP PROCEDURE IF EXISTS `CrearTipoComprobanteICECompured`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `CrearTipoComprobanteICECompured`(in id int,in sde varchar(25),
in tipo varchar(30),in sr varchar(10),in candg int,in rc varchar(11),in fact varchar(20),in evt int)
BEGIN
 if evt=0 then
  set sde=(select idSede from sede where nomse=sde);
  insert into tipocomprobante(tipcompr,candig,ser,idSede,ruc,facde) values(tipo,candg,sr,sde,rc,fact);
  select 'Se creÃ³ el tipo correctamente';
 end if;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `DevolverMotoVentasporVendedor`
--

DROP PROCEDURE IF EXISTS `DevolverMotoVentasporVendedor`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `DevolverMotoVentasporVendedor`(in fchini date,in fchfnl date)
BEGIN
 select Vendedor,sum(monto) Total from montoporvendedor where fecha between fchini and fchfnl
 group by Vendedor;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `DevolverSedeUsuario`
--

DROP PROCEDURE IF EXISTS `DevolverSedeUsuario`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `DevolverSedeUsuario`(in usu varchar(20),in clv varchar(20),in tpo varchar(20))
BEGIN
 set usu=(select Sede from vta_usuarios where Usuario=usu and Clave=clv and Tipo=tpo);
 select usu Resultado;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `DistribuiralaSedes`
--

DROP PROCEDURE IF EXISTS `DistribuiralaSedes`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `DistribuiralaSedes`(in sede varchar(50),in idprod integer)
BEGIN
 set sede=(select idsede from sede where nomse=sede);
 update producto set idsede=sede where idproducto=idprod;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `EditarListaVendida`
--

DROP PROCEDURE IF EXISTS `EditarListaVendida`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `EditarListaVendida`(in codp int)
begin
declare idvpro int;
set idvpro=(select idventa_producto from venta_producto where Producto_idproducto=codp);
delete from garantiaxventa where idventa_producto=idvpro;
delete from venta_producto where Producto_idproducto=codp;
update producto set estdo='Disponible' where idproducto=codp;
end $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `ElComprobante`
--

DROP PROCEDURE IF EXISTS `ElComprobante`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `ElComprobante`(in idnuminicmp int,in sed varchar(30),
in tipo varchar(20),in para varchar(20),in numini int,in evt int)
BEGIN
 Declare cdig int default 0;
 Declare idtip int default 0;
 Declare sr varchar(15) default '';
 Declare idcomp int default 0;
 Declare seriecomprobante  varchar(45) default '';

 set sed=(select idsede from sede where nomse=sed);
set seriecomprobante = (select ser from tipocomprobante where tipcompr=tipo and idSede=sed and facde=para);
 if (select count(idTipoComprobante) from tipocomprobante where tipcompr=tipo and idSede=sed and facde=para)>0 then
 set idtip=(select idTipoComprobante from tipocomprobante where tipcompr=tipo and idSede=sed and facde=para);
 set cdig=(select candig from tipocomprobante where idTipoComprobante=idtip);
 set sr=(select ser from tipocomprobante where idTipoComprobante=idtip);
  if (SELECT count(*) FROM comprobantes where idTipoComprobante=idtip)=0 then
   if evt=0 then
    set numini=GeneraDigitos(cdig,numini);
    insert into comprobantes(serie,nume,esta,idTipoComprobante) values(seriecomprobante,GeneraDigitos(cdig,numini),'Activo',idtip);
    select 'Se inicio correctamente el tipo de comprobante';
   else
    select 'Se debe iniciar este tipo de comprobante';
   end if;
  else
   if evt=2 then
    update comprobantes set esta='Emitido' where idComprobantes=numini;
   end if;
   if evt=1 then
    if(select count(*) from comprobantes where esta='Activo' and idTipoComprobante=idtip)=0 then
     set numini=(select nume from comprobantes where   idTipoComprobante=idtip order by nume desc limit 1);
     if isnull(numini)=false then
     set numini=numini+1;
     insert into comprobantes(serie,nume,esta,idTipoComprobante) values(seriecomprobante,GeneraDigitos(cdig,numini),'Activo',idtip);
     set idcomp=(select idComprobantes from comprobantes where esta='Activo' and idTipoComprobante=idtip order by idComprobantes desc limit 1);
select GeneraDigitos(cdig,numini),sr,idcomp,idtip;
    else
        select 'Se debe iniciar este tipo de comprobante','x','x';
      end if;
    else
     set idcomp=(select idComprobantes from comprobantes where esta='Activo' and idTipoComprobante=idtip order by idComprobantes desc limit 1);
     set numini=(select nume from comprobantes where   idComprobantes=idcomp order by nume desc limit 1);
     select GeneraDigitos(cdig,numini),sr,idcomp;
    end if;
   end if;
  end if;
end if;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `eliminaerProducto`
--

DROP PROCEDURE IF EXISTS `eliminaerProducto`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminaerProducto`(in pro int)
begin
delete from serie where Producto_idProducto=pro;
delete from garantiaxcompra where idProducto=pro;
delete from producto where idProducto=pro;
end $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `EliminarPrestador`
--

DROP PROCEDURE IF EXISTS `EliminarPrestador`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `EliminarPrestador`(in idpres int)
BEGIN
 declare ct int default 0;
 set ct=(select count(*) from externo where idPrestador=idpres);
 if ct=0 then
  delete from prestador where idPrestador=idpres;
 end if;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `GeneraComprobante`
--

DROP PROCEDURE IF EXISTS `GeneraComprobante`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `GeneraComprobante`(in sed varchar(30),in tpo varchar(30),in pr varchar(20))
BEGIN
 declare num int default 0;
 declare ser varchar(15) default '';
 declare idtip varchar(20) default '';
 declare cdig int default 0;
  declare idsed int default 0;
 if(select count(*) from vta_comprobantes where sede=sed and tipo=tpo and para=pr)=0 then 
  select 'Falta iniciar comprobante';
 else
  set ser=(select serie from vta_comprobantes where sede=sed and tipo=tpo and para=pr limit 1);
  set idtip=(select id from vta_comprobantes where sede=sed and tipo=tpo and para=pr limit 1);
  if(select count(*) from vta_comprobantes where sede=sed and tipo=tpo and para=pr and Estado='Activo')>0 then
   select numero,ser,idtip from vta_comprobantes where sede=sed and tipo=tpo and para=pr and Estado='Activo';
  else
   set idsed=(select idSede from sede where nomse=sed);
   set cdig=(select candig from tipocomprobante where tipcompr=tpo and facde=pr and idSede=idsed);
   set idtip=(select idTipocomprobante from tipocomprobante where tipcompr=tpo and facde=pr and idSede=idsed);
   set num=(select numero from vta_comprobantes where sede=sed and tipo=tpo and para=pr order by numero desc limit 1);
   set num=num+1;
   select GeneraDigitos(cdig,num),ser,idtip;
  end if;
 end if;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `GenerarOtroComprobante`
--

DROP PROCEDURE IF EXISTS `GenerarOtroComprobante`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `GenerarOtroComprobante`(in idvta int, in idcom int, in tipoco int)
begin
declare codcomp int;
set codcomp=(select idcomprobantes from comprobantes where esta='Activo' and idTipoComprobante=tipoco order by idcomprobantes asc limit 1);
update comprobantes set esta='Anulado' where idcomprobantes=idcom;
update compventa set idcomprobantes=codcomp where idventa=idvta;
update comprobantes set esta='Emitido' where idcomprobantes=codcomp;
end $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `IngresarProductos`
--

DROP PROCEDURE IF EXISTS `IngresarProductos`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `IngresarProductos`(IN seripro VARCHAR(25),
IN idcat INT,IN fecdoc DATE,IN fecven DATE,IN cdbrrs VARCHAR(45),IN costo VARCHAR(45),IN perio INT,IN cantiper INT,
IN idprov INT,IN bdr INT, IN dcmp INT ,IN idcomprd INT,IN ubi VARCHAR(30),IN est varchar(20))
BEGIN
 DECLARE idcompr INT;
 DECLARE fecga VARCHAR(20) DEFAULT'';
 DECLARE precsug DECIMAL(9,2);
 DECLARE idsed INT;
 IF bdr=1 THEN
  SET idcompr=idcomprd;
  SET idsed=(SELECT idSede FROM sede WHERE nomse=ubi);
  SET precsug=(SELECT precsg  FROM catalogoproducto WHERE codctlg= idcat);
  INSERT INTO producto(fecingralm,Catalogoproducto_codctlg,codbrr,idDoc_Compra,costo,estdo,precVenta,idsede) VALUES(CURDATE(),
   idcat,cdbrrs,idcompr,costo,est,precsug,idsed);

   SET idcompr=(SELECT idProducto FROM producto ORDER BY idProducto DESC LIMIT 1);


 END IF;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `IngresoInventarioInicial`
--

DROP PROCEDURE IF EXISTS `IngresoInventarioInicial`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `IngresoInventarioInicial`(in sede int,in producto int,in proveedor int)
BEGIN
 insert into inventarioinicial(feching,idSede,idProducto,idProveedor) values(curdate(),sede,producto,proveedor);
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `InsertarCliente`
--

DROP PROCEDURE IF EXISTS `InsertarCliente`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertarCliente`(in nomClien varchar(90), in direc varchar(75),in  telfono varchar(45),
in tipocli varchar(45), in destipoiden varchar(45), in numiden varchar(45),in idcli int,in tipo int)
begin
declare cliente int;
if tipo =0 then
  insert into cliente(nomclie, dir, fono, tipo) values(nomClien,direc,telfono,tipocli);
  set cliente=(select idCliente from cliente order by idClieNte desc limit 1);
  insert into identificacion (desident,numident,idCliente) values(destipoiden,numiden,cliente);
else
  if tipo =1 then
    update cliente set nomclie=nomClien, dir=direc, fono=telfono,tipo=tipocli where idCliente=idcli;
    update identificacion set desident=destipoiden, numident=numiden where idCliente=idcli;
  else
    DELETE FROM identificacion WHERE idCliente=idcli;    
    delete from cliente where idCliente=idcli;
  end if;
end if;
end $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `insertarprestamosuno`
--

DROP PROCEDURE IF EXISTS `insertarprestamosuno`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertarprestamosuno`(in tipopr int, in fechd varchar(20),in codprestado int)
begin
declare codpres int;
insert into prestamo (fecini,tipopres) values (curdate(),tipopr);
set codpres=(select idprestamo from prestamo order by idPrestamo desc limit 1);
insert into externo(fecdev,sit,idprestamo,idprestador) values(fechd,'Activo',codpres,codprestado);
select codpres;
end $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `LimpiaPorVender`
--

DROP PROCEDURE IF EXISTS `LimpiaPorVender`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `LimpiaPorVender`(in usu varchar(20),in sed varchar(20),in nomb varchar(100))
BEGIN
 declare cnt int default 0; declare fila int default 1;
 declare idpr int default 0; declare idus int default 0;
 set cnt=(select count(*) from vta_porvender where Usuario=usu and Sede=sed and Catalogo=nomb);
 set idus=(select idusuario from usuario where nomusr=usu);
 while(fila<=cnt) do
  set idpr=(select id from vta_porvender where Usuario=usu and Sede=sed and Catalogo=nomb order by id limit 1);
  delete from por_vender where idusuario=idus and idproducto=idpr;
  -- update producto set estdo='Disponible' where idProducto=idpr;
  update producto set estdo='Disponible' where idProducto=idpr and estdo='Por vender';
  set fila=fila+1;
 end while;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `LimpiaTablas`
--

DROP PROCEDURE IF EXISTS `LimpiaTablas`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `LimpiaTablas`()
BEGIN
 delete FROM detallecotizacion;
 delete FROM cotizacion;
 delete FROM pagoxdeuda;
 delete FROM deuda;
 delete FROM compventa;
 delete FROM garantiaxventa;
 delete FROM venta_producto;
 delete FROM venta;
 delete FROM comporbanteservicio;
 delete FROM comprobantes;
 delete FROM serie;
 delete FROM garantiaxcompra;
 delete FROM carateristicasproducto;
 delete FROM prestamoproducto;
 delete FROM producto;
 delete FROM doc_compra;
 delete FROM compra;
 DELETE FROM servicio;
 DELETE FROM tempoprestamo ;
 DELETE FROM tecnico;
 DELETE FROM datosusuarios WHERE IdDatosUsuarios<>2;
 delete FROM permisosusuario WHERE IdUsuario>1;
 delete FROM usuario WHERE IdUsuario>1;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `MoficarPrestador`
--

DROP PROCEDURE IF EXISTS `MoficarPrestador`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `MoficarPrestador`(in idPre int,in nom varchar(80),in res varchar(80),in drc varchar(80),in fn varchar(80))
BEGIN
 declare rsp int default 0;
 set rsp=(select count(*) from prestador where nomprest=nom and idPrestador<>idPre);
 If isnull(rsp) then
  set rsp=0;
 End If;
 if rsp=0 then
   set rsp=(select count(*) from prestador where respon=res and idPrestador<>idPre);
 end if;
 If isnull(rsp) then
  set rsp=0;
 End If;
 If rsp=0 then
  update prestador set nomprest=nom,respon=res,fono=fn,direc=drc where idPrestador=idPre;
 End if;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `PagarAporte`
--

DROP PROCEDURE IF EXISTS `PagarAporte`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `PagarAporte`(id int,mtocn double(9,2))
BEGIN
 declare sdo double(9,2);
 declare cda int;
 declare crsaporte cursor for select saldo,codapor from aporte where idafiliado=id and saldo>0;
 open crsaporte;
 c1_loop: LOOP
  fetch crsaporte into sdo,cda;
  if sdo<=mtocn then
   update aporte set saldo=0 where codapor=cda;
   set mtocn=mtocn-sdo;
  else
   set sdo=sdo-mtocn;
   update aporte set saldo=sdo where codapor=cda;
   set mtocn=0;
  end if;
  IF (l_last_row=1 or mtocn<=0) THEN
   LEAVE c1_loop;
  END IF;
 END LOOP c1_loop;
 close crsaporte;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `PrimLtrsMayus`
--

DROP PROCEDURE IF EXISTS `PrimLtrsMayus`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `PrimLtrsMayus`(in cadena varchar(200))
BEGIN
DECLARE pos INT DEFAULT 0;
DECLARE tmp VARCHAR(100) DEFAULT '';
DECLARE result VARCHAR(100) DEFAULT '';
set cadena=lower(cadena);
REPEAT SET pos = LOCATE(' ', cadena);
 IF pos = 0 THEN SET pos = CHAR_LENGTH(cadena);
 END IF;
 SET tmp = LEFT(cadena,pos);
 SET result = CONCAT(result, UPPER(LEFT(tmp,1)),SUBSTR(tmp,2));
 SET cadena = RIGHT(cadena,CHAR_LENGTH(cadena)-pos);
UNTIL CHAR_LENGTH(cadena) = 0 END REPEAT;
select result;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `RegistraPagoAProveedores`
--

DROP PROCEDURE IF EXISTS `RegistraPagoAProveedores`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `RegistraPagoAProveedores`(idcmp int,mto decimal(9,2))
BEGIN
 declare id int default -1;
 declare bdr int default -1;
 declare mnt decimal(9,2) default 0.0;
 set @id=(select idDoc_Compra from Doc_Compra where idCompra=idcmp);
 if(isnul(@id)) then
  set @id=-1;
 end if;
 insert into pagodoccompra(fec,hra,monto,idDoc_Compra) values(curdate(),curtime(),mto,@id);
 set mnt=(select sum(monto) from pagodoccompra where idDoc_Compra=@id);
 set bdr=(select if((select sum(monto) from pagodoccompra where idDoc_Compra=@id)=mnt,1,0));
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `Servicios`
--

DROP PROCEDURE IF EXISTS `Servicios`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Servicios`(in id int,in serv varchar(60),in cst decimal(9,2),in evt int)
BEGIN
 if evt=0 then 
  if(select count(*) from servicio where DescriSer=serv)=0 then
   insert into servicio(DescriSer,cost) values(serv,cst);
   select '1';
  else
   select '2';
  end if;
 end if;
 if evt=1 then 
  if (select count(*) from servicio where DescriSer=serv and idServicio<>id)=0 then
   update servicio set DescriSer=serv,cost=cst where idServicio=id;
   select '1';
  else
   select '2';
  end if;
 end if;
 if evt=2 then 
  delete from servicio where idServicio=id;
 end if;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `Sp_OrdenCompra`
--

DROP PROCEDURE IF EXISTS `Sp_OrdenCompra`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Sp_OrdenCompra`(ordcmp varchar(15),prvd varchar(60),nrord int ,evt int)
BEGIN
 set prvd=(select idProveedor from proveedor where nompro=prvd);
 if evt=1 then
  insert into ordecompra(numord,fecord,idProveedor) values(ordcmp,curdate(),prvd);
  select idOrdeCompra from ordecompra order by idOrdeCompra desc limit 1;
 end if;
 if evt=2 then  
  update ordecompra set idProveedor=prvd;
 end if;
 if evt=3 then  
  update ordecompra set idProveedor=prvd;
 end if;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `Tecnicos`
--

DROP PROCEDURE IF EXISTS `Tecnicos`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Tecnicos`(in id int,in tecn varchar(60),in drc varchar(60),in dn varchar(8),
in tlf varchar(13),in evt int,in idse int)
BEGIN
 if evt=0 then 
  if(select count(*) from tecnico where dni=dn)=0 then
   insert into tecnico(nomtec,dni,dir,tel,idSede) values(tecn,dn,drc,tlf,idse);
   select 'Se registro correctamente';
  else
   select 'No se registro porque se repite el dni';
  end if;
 end if;
 if evt=1 then 
  if (select count(*) from tecnico where dni=dn and idTecnico<>id)=0 then
   update tecnico set nomtec=tecn,dni=dn,dir=drc,tel=tlf,idSede=idse where idTecnico=id;
   select 'ModificaciÃ³n Correcta';
  else
   select 'No se Modifico';
  end if;
 end if;
 if evt=2 then 
  delete from tecnico where idTecnico=id;
 end if;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `UpdateVendedor`
--

DROP PROCEDURE IF EXISTS `UpdateVendedor`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateVendedor`(in idus int, in nous varchar(45), in ps varchar(45), in nomb varchar(45), apez varchar(45), in di varchar(45),
in te varchar(20),in dn varchar(10))
begin
update usuario set nomusr=nous, psw=ps where idusuario=idus;
update datosusuarios set nom=nomb, ape=apez, dire=di, tel=te,dni=dn where Usuario_idusuario=idus;
end $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `Venta_Productos_Venta`
--

DROP PROCEDURE IF EXISTS `Venta_Productos_Venta`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Venta_Productos_Venta`(in precio varchar(20), in venta int,
in product int,in tipovta int)
begin
 declare idvntpro int; declare idtipocom int; declare idComprVe int;
 if tipovta=0 then
  insert into venta_producto (prc,Venta_idVenta,Producto_idProducto,situ) values (precio,venta,product,'N');
  update producto set estdo='Vendido' where idproducto=product;

  

 else
  update producto set estdo='Disponible' where idproducto=product and estdo='Por vender';
  insert into venta_producto (prc,Venta_idVenta,Producto_idProducto,situ) values (precio,venta,product,'F');
 end if; 



end $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of view `compra_proveedor`
--

DROP TABLE IF EXISTS `compra_proveedor`;
DROP VIEW IF EXISTS `compra_proveedor`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `compra_proveedor` AS select `p`.`idProveedor` AS `idproveedor`,`d`.`idDoc_Compra` AS `idDoc_Compra`,`c`.`idCompra` AS `idcompra`,`p`.`nompro` AS `nompro`,`c`.`fec` AS `fec`,`d`.`serie` AS `serie`,`d`.`nume` AS `nume`,`d`.`tipo` AS `tipo` from ((`proveedor` `p` join `compra` `c`) join `doc_compra` `d`) where ((`p`.`idProveedor` = `c`.`idProveedor`) and (`c`.`idCompra` = `d`.`idCompra`));

--
-- Definition of view `comprobantesvta`
--

DROP TABLE IF EXISTS `comprobantesvta`;
DROP VIEW IF EXISTS `comprobantesvta`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `comprobantesvta` AS select `c`.`idComprobantes` AS `idComprobantes`,`t`.`tipcompr` AS `tipcompr`,`c`.`nume` AS `nume`,`c`.`serie` AS `serie`,`c`.`esta` AS `esta` from (`tipocomprobante` `t` join `comprobantes` `c`) where (`c`.`idTipoComprobante` = `t`.`idTipoComprobante`);

--
-- Definition of view `credito`
--

DROP TABLE IF EXISTS `credito`;
DROP VIEW IF EXISTS `credito`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `credito` AS select `v`.`idVenta` AS `idventa`,`v`.`idCliente` AS `idcliente`,`d`.`idDeuda` AS `iddeuda`,`d`.`montdeu` AS `montdeu`,`d`.`pgoinici` AS `pgoinici`,`d`.`fecdeud` AS `fecdeud`,(`d`.`montdeu` + `d`.`pgoinici`) AS `total`,`c`.`nomctlg` AS `nomctlg`,`cl`.`nomclie` AS `nomclie`,`cl`.`fono` AS `fono`,`du`.`nom` AS `nom`,dayofmonth(`v`.`fecvta`) AS `dia`,month(`v`.`fecvta`) AS `mes`,year(`v`.`fecvta`) AS `an` from (((((((`venta` `v` join `deuda` `d`) join `venta_producto` `vp`) join `producto` `p`) join `catalogoproducto` `c`) join `cliente` `cl`) join `usuario` `u`) join `datosusuarios` `du`) where ((`v`.`idVenta` = `d`.`idVenta`) and (`v`.`idVenta` = `vp`.`Venta_idVenta`) and (`vp`.`Producto_idProducto` = `p`.`idProducto`) and (`p`.`Catalogoproducto_codctlg` = `c`.`codctlg`) and (`v`.`idCliente` = `cl`.`idCliente`) and (`v`.`Usuario_idusuario` = `u`.`idusuario`) and (`u`.`idusuario` = `du`.`Usuario_idusuario`));

--
-- Definition of view `ddddd`
--

DROP TABLE IF EXISTS `ddddd`;
DROP VIEW IF EXISTS `ddddd`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ddddd` AS select `impri_comprobante_venta`.`idVenta` AS `idventa`,`impri_comprobante_venta`.`nomclie` AS `nomclie`,`impri_comprobante_venta`.`nomctlg` AS `nomctlg` from `impri_comprobante_venta` group by `impri_comprobante_venta`.`idVenta`,`impri_comprobante_venta`.`nomctlg`;

--
-- Definition of view `deudores`
--

DROP TABLE IF EXISTS `deudores`;
DROP VIEW IF EXISTS `deudores`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `deudores` AS select `d`.`idDeuda` AS `idDeuda`,`v`.`idVenta` AS `idventa`,`c`.`idCliente` AS `idcliente`,`d`.`montdeu` AS `montdeu`,`d`.`pgoinici` AS `pgoinici`,`d`.`fecdeud` AS `fecdeud`,`v`.`fecvta` AS `fecvta`,`v`.`moda` AS `moda`,`c`.`nomclie` AS `nomclie`,`c`.`fono` AS `fono`,`c`.`dir` AS `dir`,`i`.`numident` AS `numident`,`t`.`tipcompr` AS `tipcompr`,`cb`.`nume` AS `nume` from ((((((`deuda` `d` join `venta` `v`) join `cliente` `c`) join `identificacion` `i`) join `compventa` `co`) join `comprobantes` `cb`) join `tipocomprobante` `t`) where ((`d`.`idVenta` = `v`.`idVenta`) and (`v`.`idCliente` = `c`.`idCliente`) and (`i`.`idCliente` = `c`.`idCliente`) and (`v`.`idVenta` = `co`.`idVenta`) and (`co`.`idComprobantes` = `cb`.`idComprobantes`) and (`cb`.`idTipoComprobante` = `t`.`idTipoComprobante`) and (`cb`.`esta` <> 'Anulado'));

--
-- Definition of view `dtscotizacion`
--

DROP TABLE IF EXISTS `dtscotizacion`;
DROP VIEW IF EXISTS `dtscotizacion`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `dtscotizacion` AS select `cz`.`idCotizacion` AS `ID`,`cz`.`fec_ctz` AS `Fecha`,date_format((`cz`.`fec_ctz` + interval `cz`.`diasdur` day),'%d/%m/%Y') AS `F_Caducidad`,`c`.`nomclie` AS `Cliente`,`c`.`dir` AS `Direccion`,concat(`du`.`nom`,' ',`du`.`ape`) AS `Usuario`,`cp`.`codctlg` AS `codctlg`,concat(`md`.`nommod`,' ',if((`mrc`.`nommrc` = 'SIN MARCA'),'',`mrc`.`nommrc`),' ',`cp`.`nomctlg`) AS `nomctlg`,`dc`.`cnt` AS `Cant`,round((`dc`.`prec` / `dc`.`cnt`),2) AS `Precio`,`dc`.`prec` AS `Importe` from ((((((((`cliente` `c` join `detallecotizacion` `dc`) join `cotizacion` `cz`) join `usuario` `u`) join `sede` `s`) join `datosusuarios` `du`) join `catalogoproducto` `cp`) join `modelo` `md`) join `marca` `mrc`) where ((`c`.`idCliente` = `cz`.`idCliente`) and (`u`.`idusuario` = `cz`.`Usuario_idusuario`) and (`cz`.`idCotizacion` = `dc`.`idCotizacion`) and (`s`.`idSede` = `u`.`idSede`) and (`du`.`Usuario_idusuario` = `u`.`idusuario`) and (`cp`.`codctlg` = `dc`.`codctlg`) and (`cp`.`idModelos` = `md`.`idModelos`) and (`cp`.`idMarca` = `mrc`.`idMarca`));

--
-- Definition of view `editar_compro`
--

DROP TABLE IF EXISTS `editar_compro`;
DROP VIEW IF EXISTS `editar_compro`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `editar_compro` AS select `v`.`idVenta` AS `idventa`,`v`.`fecvta` AS `fecvta`,`v`.`moda` AS `moda`,`c`.`idCliente` AS `idcliente`,`c`.`nomclie` AS `nomclie`,`c`.`dir` AS `dir`,`c`.`fono` AS `fono`,`c`.`tipo` AS `tipo`,`co`.`idCompVenta` AS `idCompVenta`,`co`.`fecemi` AS `fecemi`,`cp`.`idComprobantes` AS `idComprobantes`,`t`.`ser` AS `serie`,`cp`.`nume` AS `nume`,`cp`.`esta` AS `esta`,`t`.`tipcompr` AS `tipcompr` from ((((`venta` `v` join `cliente` `c`) join `compventa` `co`) join `comprobantes` `cp`) join `tipocomprobante` `t`) where ((`v`.`idCliente` = `c`.`idCliente`) and (`v`.`idVenta` = `co`.`idVenta`) and (`co`.`idComprobantes` = `cp`.`idComprobantes`) and (`t`.`idTipoComprobante` = `cp`.`idTipoComprobante`));

--
-- Definition of view `impri_comprobante_venta`
--

DROP TABLE IF EXISTS `impri_comprobante_venta`;
DROP VIEW IF EXISTS `impri_comprobante_venta`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `impri_comprobante_venta` AS select `v`.`idVenta` AS `idVenta`,`cl`.`nomclie` AS `nomclie`,`cl`.`dir` AS `dir`,`id`.`numident` AS `numident`,`ca`.`nomctlg` AS `nomctlg`,`pv`.`prc` AS `prc`,(`pv`.`prc` - (`pv`.`prc` * 0.19)) AS `Pretot`,dayofmonth(`v`.`fecvta`) AS `dia`,month(`v`.`fecvta`) AS `mes`,year(`v`.`fecvta`) AS `an`,`co`.`nume` AS `serie`,(`pv`.`prc` * 0.19) AS `igv`,`pv`.`prc` AS `tot` from (((((((((`venta` `v` join `venta_producto` `pv`) join `compventa` `c`) join `producto` `p`) join `catalogoproducto` `ca`) join `marca` `m`) join `usuario` `u`) join `cliente` `cl`) join `comprobantes` `co`) join `identificacion` `id`) where ((`v`.`idVenta` = `pv`.`Venta_idVenta`) and (`pv`.`Producto_idProducto` = `p`.`idProducto`) and (`c`.`idVenta` = `v`.`idVenta`) and (`p`.`Catalogoproducto_codctlg` = `ca`.`codctlg`) and (`ca`.`idMarca` = `m`.`idMarca`) and (`cl`.`idCliente` = `v`.`idCliente`) and (`v`.`Usuario_idusuario` = `u`.`idusuario`) and (`c`.`idComprobantes` = `co`.`idComprobantes`) and (`cl`.`idCliente` = `id`.`idCliente`));

--
-- Definition of view `impri_comprobante_venta1`
--

DROP TABLE IF EXISTS `impri_comprobante_venta1`;
DROP VIEW IF EXISTS `impri_comprobante_venta1`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `impri_comprobante_venta1` AS select `v`.`idVenta` AS `idVenta`,`cl`.`nomclie` AS `nomclie`,`cl`.`dir` AS `dir`,`id`.`numident` AS `numident`,`ca`.`nomctlg` AS `nomctlg`,`pv`.`prc` AS `prc`,`pv`.`prc` AS `Pretot`,dayofmonth(`v`.`fecvta`) AS `dia`,month(`v`.`fecvta`) AS `mes`,year(`v`.`fecvta`) AS `an`,`co`.`nume` AS `serie`,(`pv`.`prc` * `ig`.`valor`) AS `igv`,(`pv`.`prc` + (`pv`.`prc` * `ig`.`valor`)) AS `tot`,`un`.`abre` AS `abre`,`p`.`codbrr` AS `codbrr`,`se`.`seri` AS `seri`,`ig`.`ver` AS `ver` from ((((((((((((`venta` `v` join `venta_producto` `pv`) join `compventa` `c`) join `producto` `p`) join `catalogoproducto` `ca`) join `marca` `m`) join `usuario` `u`) join `cliente` `cl`) join `comprobantes` `co`) join `identificacion` `id`) join `unidad` `un`) join `serie` `se`) join `igv` `ig`) where ((`v`.`idVenta` = `pv`.`Venta_idVenta`) and (`pv`.`Producto_idProducto` = `p`.`idProducto`) and (`c`.`idVenta` = `v`.`idVenta`) and (`p`.`Catalogoproducto_codctlg` = `ca`.`codctlg`) and (`ca`.`idMarca` = `m`.`idMarca`) and (`cl`.`idCliente` = `v`.`idCliente`) and (`v`.`Usuario_idusuario` = `u`.`idusuario`) and (`c`.`idComprobantes` = `co`.`idComprobantes`) and (`cl`.`idCliente` = `id`.`idCliente`) and (`ca`.`idUnidad` = `un`.`idUnidad`) and (`p`.`idProducto` = `se`.`Producto_idProducto`));

--
-- Definition of view `lavista1`
--

DROP TABLE IF EXISTS `lavista1`;
DROP VIEW IF EXISTS `lavista1`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `lavista1` AS select `v`.`idVenta` AS `idVenta`,concat(`ti`.`tipcompr`,'-',`cpr`.`nume`) AS `Comprobante`,`v`.`fecvta` AS `Fecha`,`c`.`nomclie` AS `Cliente`,`v`.`moda` AS `Modalidad`,concat(`d`.`nom`,' ',`d`.`ape`) AS `Usuario`,if((`cpr`.`esta` = 'Anulado'),0.00,`v`.`montfactu`) AS `Monto`,if((`cpr`.`esta` = 'Anulado'),0.00,`v`.`descuento`) AS `Des`,if((`cpr`.`esta` = 'Anulado'),0.00,(`v`.`montfactu` - `v`.`descuento`)) AS `MontDescuento`,`cpr`.`esta` AS `Estado`,`ti`.`idTipoComprobante` AS `Tipo`,concat(`i`.`desident`,' ',`i`.`numident`) AS `ElNumero`,`cpr`.`esta` AS `esta` from ((((((((`venta` `v` join `cliente` `c`) join `identificacion` `i`) join `usuario` `u`) join `datosusuarios` `d`) join `venta_producto` `vp`) join `compventa` `co`) join `comprobantes` `cpr`) join `tipocomprobante` `ti`) where ((`v`.`idCliente` = `c`.`idCliente`) and (`v`.`Usuario_idusuario` = `u`.`idusuario`) and (`u`.`idusuario` = `d`.`Usuario_idusuario`) and (`v`.`idVenta` = `vp`.`Venta_idVenta`) and (`v`.`idVenta` = `co`.`idVenta`) and (`co`.`idComprobantes` = `cpr`.`idComprobantes`) and (`cpr`.`idTipoComprobante` = `ti`.`idTipoComprobante`) and (`c`.`idCliente` = `i`.`idCliente`) and (`c`.`nomclie` <> 'Consumo')) group by `v`.`idVenta` union all select `v`.`idVenta` AS `idVenta`,concat(`tc`.`tipcompr`,'-',`cb`.`nume`) AS `Comprobante`,`v`.`fecvta` AS `Fecha`,`cl`.`nomclie` AS `Cliente`,`v`.`moda` AS `Modalidad`,concat(`du`.`nom`,' ',`du`.`ape`) AS `Usuario`,`v`.`montfactu` AS `Monto`,`v`.`descuento` AS `Descuento`,(`v`.`montfactu` - `v`.`descuento`) AS `MontoDescuento`,`cb`.`esta` AS `Estado`,`tc`.`idTipoComprobante` AS `Tipo`,concat(`idf`.`desident`,' ',`idf`.`numident`) AS `ElNumero`,`cb`.`esta` AS `Esta` from (((((((`venta` `v` join `compventa` `cv` on((`cv`.`idVenta` = `v`.`idVenta`))) join `comprobantes` `cb` on((`cb`.`idComprobantes` = `cv`.`idComprobantes`))) join `tipocomprobante` `tc` on((`tc`.`idTipoComprobante` = `cb`.`idTipoComprobante`))) join `cliente` `cl` on((`cl`.`idCliente` = `v`.`idCliente`))) join `usuario` `us` on((`us`.`idusuario` = `v`.`Usuario_idusuario`))) join `datosusuarios` `du` on((`du`.`Usuario_idusuario` = `us`.`idusuario`))) join `identificacion` `idf` on((`idf`.`idCliente` = `cl`.`idCliente`))) where `v`.`idVenta` in (select `venta`.`idVenta` AS `idVenta` from `venta` where (not(`venta`.`idVenta` in (select `venta_producto`.`Venta_idVenta` AS `Venta_idVenta` from `venta_producto`)))) order by 1 desc;

--
-- Definition of view `lavista2`
--

DROP TABLE IF EXISTS `lavista2`;
DROP VIEW IF EXISTS `lavista2`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `lavista2` AS select `v`.`idVenta` AS `idVenta`,concat(`ti`.`tipcompr`,'-',`cpr`.`nume`) AS `Comprobante`,`v`.`fecvta` AS `Fecha`,`c`.`nomclie` AS `Cliente`,`v`.`moda` AS `Modalidad`,`d`.`nom` AS `Usuario`,if((`cpr`.`esta` = 'Anulado'),0.00,`v`.`montreal`) AS `MontoReal`,if((`cpr`.`esta` = 'Anulado'),0.00,`v`.`descuento`) AS `Des`,if((`cpr`.`esta` = 'Anulado'),0.00,(`v`.`montreal` - `v`.`descuento`)) AS `MontDescuento`,`cpr`.`esta` AS `Estado`,`ti`.`idTipoComprobante` AS `Tipo`,concat(`i`.`desident`,' ',`i`.`numident`) AS `ElNumero`,`cpr`.`esta` AS `esta` from ((((((((`venta` `v` join `cliente` `c`) join `identificacion` `i`) join `usuario` `u`) join `datosusuarios` `d`) join `venta_producto` `vp`) join `compventa` `co`) join `comprobantes` `cpr`) join `tipocomprobante` `ti`) where ((`v`.`idCliente` = `c`.`idCliente`) and (`v`.`Usuario_idusuario` = `u`.`idusuario`) and (`u`.`idusuario` = `d`.`Usuario_idusuario`) and (`v`.`idVenta` = `vp`.`Venta_idVenta`) and (`v`.`idVenta` = `co`.`idVenta`) and (`co`.`idComprobantes` = `cpr`.`idComprobantes`) and (`cpr`.`idTipoComprobante` = `ti`.`idTipoComprobante`) and (`c`.`idCliente` = `i`.`idCliente`)) group by `v`.`idVenta` order by `v`.`idVenta`;

--
-- Definition of view `lavistavta`
--

DROP TABLE IF EXISTS `lavistavta`;
DROP VIEW IF EXISTS `lavistavta`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `lavistavta` AS select `v`.`idVenta` AS `idVenta`,`ti`.`tipcompr` AS `tipcompr`,concat(`ti`.`ser`,'-',`cpr`.`nume`) AS `Comprobante`,`v`.`fecvta` AS `Fecha`,`c`.`nomclie` AS `Cliente`,`v`.`moda` AS `Modalidad`,`d`.`nom` AS `Usuario`,if((`cpr`.`esta` = 'Anulado'),0.00,`v`.`montreal`) AS `MontoReal`,if((`cpr`.`esta` = 'Anulado'),0.00,`v`.`descuento`) AS `Des`,if((`cpr`.`esta` = 'Anulado'),0.00,(`v`.`montreal` - `v`.`descuento`)) AS `MontDescuento`,`cpr`.`esta` AS `Estado`,`ti`.`idTipoComprobante` AS `Tipo`,concat(`i`.`desident`,' ',`i`.`numident`) AS `ElNumero`,`cpr`.`esta` AS `esta` from ((((((((`venta` `v` join `cliente` `c`) join `identificacion` `i`) join `usuario` `u`) join `datosusuarios` `d`) join `venta_producto` `vp`) join `compventa` `co`) join `comprobantes` `cpr`) join `tipocomprobante` `ti`) where ((`v`.`idCliente` = `c`.`idCliente`) and (`v`.`Usuario_idusuario` = `u`.`idusuario`) and (`u`.`idusuario` = `d`.`Usuario_idusuario`) and (`v`.`idVenta` = `vp`.`Venta_idVenta`) and (`v`.`idVenta` = `co`.`idVenta`) and (`co`.`idComprobantes` = `cpr`.`idComprobantes`) and (`cpr`.`idTipoComprobante` = `ti`.`idTipoComprobante`) and (`c`.`idCliente` = `i`.`idCliente`)) group by `v`.`idVenta` order by `v`.`idVenta`;

--
-- Definition of view `loscatalogos`
--

DROP TABLE IF EXISTS `loscatalogos`;
DROP VIEW IF EXISTS `loscatalogos`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `loscatalogos` AS select `cp`.`codctlg` AS `Codigo`,concat(`md`.`nommod`,if((`mr`.`nommrc` <> 'Generica'),concat(' ',`mr`.`nommrc`,' '),' '),`cp`.`nomctlg`) AS `Producto`,`cp`.`precsg` AS `Precio`,`ud`.`nomuni` AS `abre`,`cp`.`stockmin` AS `StockMinimo`,`cp`.`prexmenor` AS `prexmenor`,`cp`.`prexmayor` AS `prexmayor`,`cp`.`codbarra` AS `codbarra` from (((`catalogoproducto` `cp` join `marca` `mr` on((`mr`.`idMarca` = `cp`.`idMarca`))) join `modelo` `md` on((`md`.`idModelos` = `cp`.`idModelos`))) join `unidad` `ud` on((`ud`.`idUnidad` = `cp`.`idUnidad`)));

--
-- Definition of view `montoporvendedor`
--

DROP TABLE IF EXISTS `montoporvendedor`;
DROP VIEW IF EXISTS `montoporvendedor`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `montoporvendedor` AS select `d`.`nom` AS `Vendedor`,`vp`.`prc` AS `monto`,`v`.`fecvta` AS `fecha` from ((((`venta` `v` join `usuario` `u`) join `datosusuarios` `d`) join `tipousuario` `t`) join `venta_producto` `vp`) where ((`t`.`idTipousuario` = `u`.`Tipousuario_idTipousuario`) and (`u`.`idusuario` = `d`.`Usuario_idusuario`) and (`v`.`Usuario_idusuario` = `u`.`idusuario`) and (`v`.`idVenta` = `vp`.`Venta_idVenta`));

--
-- Definition of view `producto_venta`
--

DROP TABLE IF EXISTS `producto_venta`;
DROP VIEW IF EXISTS `producto_venta`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `producto_venta` AS select `p`.`idProducto` AS `idproducto`,`p`.`codbrr` AS `codbrr`,`p`.`estdo` AS `estdo`,`c`.`precsg` AS `precVenta`,concat(`mo`.`nommod`,' ',`c`.`nomctlg`) AS `nomctlg`,`s`.`seri` AS `seri`,`m`.`nommrc` AS `nommrc`,`se`.`nomse` AS `Sede`,`c`.`prexmenor` AS `prexmenor`,`c`.`prexmayor` AS `prexmayor`,`mo`.`nommod` AS `nommod`,`c`.`codctlg` AS `codctlg` from ((((((`producto` `p` join `catalogoproducto` `c`) join `serie` `s`) join `marca` `m`) join `sede` `se`) join `modelo` `mo`) join `unidad` `un`) where ((`p`.`Catalogoproducto_codctlg` = `c`.`codctlg`) and (`p`.`idProducto` = `s`.`Producto_idProducto`) and (`c`.`idUnidad` = `un`.`idUnidad`) and (`m`.`idMarca` = `c`.`idMarca`) and (`p`.`idSede` = `se`.`idSede`) and (`c`.`idModelos` = `mo`.`idModelos`));

--
-- Definition of view `registro_movimiento`
--

DROP TABLE IF EXISTS `registro_movimiento`;
DROP VIEW IF EXISTS `registro_movimiento`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `registro_movimiento` AS select `dc`.`idDoc_Compra` AS `idDoc_Compra`,`dc`.`serie` AS `serie`,`dc`.`tipo` AS `tipo`,`c`.`idCompra` AS `idCompra`,`c`.`fec` AS `fec`,`pr`.`idProveedor` AS `idProveedor`,`pr`.`nompro` AS `nompro`,`p`.`idProducto` AS `idproducto`,`p`.`fecingralm` AS `fecingralm`,`p`.`codbrr` AS `codbrr`,`p`.`costo` AS `costo`,`p`.`estdo` AS `estdo`,`v`.`idVenta` AS `idventa`,`v`.`fecvta` AS `fecvta`,`v`.`moda` AS `moda`,`vp`.`idVenta_producto` AS `idVenta_producto`,`vp`.`prc` AS `prc`,`ct`.`nomctlg` AS `nomctlg`,`co`.`nume` AS `serieventa`,`tip`.`tipcompr` AS `tipcompr`,`ser`.`seri` AS `seri` from (((((((((((`doc_compra` `dc` join `compra` `c`) join `proveedor` `pr`) join `producto` `p`) join `venta` `v`) join `venta_producto` `vp`) join `compventa` `cv`) join `comprobantes` `co`) join `cliente` `cl`) join `catalogoproducto` `ct`) join `tipocomprobante` `tip`) join `serie` `ser`) where ((`c`.`idCompra` = `dc`.`idCompra`) and (`c`.`idProveedor` = `pr`.`idProveedor`) and (`dc`.`idDoc_Compra` = `p`.`idDoc_Compra`) and (`p`.`idProducto` = `vp`.`Producto_idProducto`) and (`vp`.`Venta_idVenta` = `v`.`idVenta`) and (`v`.`idVenta` = `cv`.`idVenta`) and (`cv`.`idComprobantes` = `co`.`idComprobantes`) and (`v`.`idCliente` = `cl`.`idCliente`) and (`p`.`Catalogoproducto_codctlg` = `ct`.`codctlg`) and (`co`.`idTipoComprobante` = `tip`.`idTipoComprobante`) and (`p`.`idProducto` = `ser`.`Producto_idProducto`));

--
-- Definition of view `resporte_ventas`
--

DROP TABLE IF EXISTS `resporte_ventas`;
DROP VIEW IF EXISTS `resporte_ventas`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `resporte_ventas` AS select `i`.`idVenta` AS `idventa`,`i`.`nomclie` AS `nomclie`,`i`.`numident` AS `numident`,`i`.`nomctlg` AS `nomctlg`,`i`.`prc` AS `prc`,`v`.`fecvta` AS `fecvta`,`i`.`serie` AS `serie`,`v`.`moda` AS `moda`,`d`.`nom` AS `nom`,`u`.`nomusr` AS `nomusr` from (((`impri_comprobante_venta` `i` join `venta` `v`) join `usuario` `u`) join `datosusuarios` `d`) where ((`i`.`idVenta` = `v`.`idVenta`) and (`v`.`Usuario_idusuario` = `u`.`idusuario`) and (`u`.`idusuario` = `d`.`Usuario_idusuario`));

--
-- Definition of view `rpteventas`
--

DROP TABLE IF EXISTS `rpteventas`;
DROP VIEW IF EXISTS `rpteventas`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `rpteventas` AS select `v`.`idVenta` AS `idVenta`,concat(`ti`.`tipcompr`,': ',`ti`.`ser`,'_',`cpr`.`nume`) AS `Comprobante`,`v`.`fecvta` AS `Fecha`,`c`.`nomclie` AS `Cliente`,`v`.`moda` AS `Modalidad`,`d`.`nom` AS `Usuario`,if((`cpr`.`esta` = 'Anulado'),0.00,`v`.`montreal`) AS `MontoReal`,if((`cpr`.`esta` = 'Anulado'),0.00,round((`v`.`montreal` / 1.18),2)) AS `Baseimponible`,if((`cpr`.`esta` = 'Anulado'),0.00,(`v`.`montreal` - round((`v`.`montreal` / 1.18),2))) AS `IGV`,if((`cpr`.`esta` = 'Anulado'),0.00,(`v`.`montreal` - `v`.`descuento`)) AS `MontDescuento`,`cpr`.`esta` AS `Estado`,`ti`.`idTipoComprobante` AS `Tipo`,concat(`i`.`desident`,' ',`i`.`numident`) AS `ElNumero`,`cpr`.`esta` AS `esta` from ((((((((`venta` `v` join `cliente` `c`) join `identificacion` `i`) join `usuario` `u`) join `datosusuarios` `d`) join `venta_producto` `vp`) join `compventa` `co`) join `comprobantes` `cpr`) join `tipocomprobante` `ti`) where ((`v`.`idCliente` = `c`.`idCliente`) and (`v`.`Usuario_idusuario` = `u`.`idusuario`) and (`u`.`idusuario` = `d`.`Usuario_idusuario`) and (`v`.`idVenta` = `vp`.`Venta_idVenta`) and (`v`.`idVenta` = `co`.`idVenta`) and (`co`.`idComprobantes` = `cpr`.`idComprobantes`) and (`cpr`.`idTipoComprobante` = `ti`.`idTipoComprobante`) and (`c`.`idCliente` = `i`.`idCliente`)) group by `v`.`idVenta` order by `v`.`idVenta`;

--
-- Definition of view `rpteventasfinal`
--

DROP TABLE IF EXISTS `rpteventasfinal`;
DROP VIEW IF EXISTS `rpteventasfinal`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `rpteventasfinal` AS select `v`.`idVenta` AS `idVenta`,`ti`.`tipcompr` AS `tipcompr`,`ti`.`ser` AS `ser`,`cpr`.`nume` AS `Comprobante`,`v`.`fecvta` AS `Fecha`,`c`.`nomclie` AS `Cliente`,`v`.`moda` AS `Modalidad`,`d`.`nom` AS `Usuario`,if((`cpr`.`esta` = 'Anulado'),0.00,`v`.`montfactu`) AS `MontoReal`,if((`cpr`.`esta` = 'Anulado'),0.00,round((`v`.`montfactu` / 1.18),2)) AS `Baseimponible`,if((`cpr`.`esta` = 'Anulado'),0.00,(`v`.`montfactu` - round((`v`.`montfactu` / 1.18),2))) AS `IGV`,if((`cpr`.`esta` = 'Anulado'),0.00,`v`.`montfactu`) AS `MontDescuento`,`cpr`.`esta` AS `Estado`,`ti`.`idTipoComprobante` AS `Tipo`,concat(`i`.`desident`,' ',`i`.`numident`) AS `ElNumero`,`cpr`.`esta` AS `esta` from ((((((((`venta` `v` join `cliente` `c`) join `identificacion` `i`) join `usuario` `u`) join `datosusuarios` `d`) join `venta_producto` `vp`) join `compventa` `co`) join `comprobantes` `cpr`) join `tipocomprobante` `ti`) where ((`v`.`idCliente` = `c`.`idCliente`) and (`v`.`Usuario_idusuario` = `u`.`idusuario`) and (`u`.`idusuario` = `d`.`Usuario_idusuario`) and (`v`.`idVenta` = `vp`.`Venta_idVenta`) and (`v`.`idVenta` = `co`.`idVenta`) and (`co`.`idComprobantes` = `cpr`.`idComprobantes`) and (`cpr`.`idTipoComprobante` = `ti`.`idTipoComprobante`) and (`c`.`idCliente` = `i`.`idCliente`)) group by `v`.`idVenta` order by `v`.`idVenta`;

--
-- Definition of view `vendedores`
--

DROP TABLE IF EXISTS `vendedores`;
DROP VIEW IF EXISTS `vendedores`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vendedores` AS select `u`.`idusuario` AS `idusuario`,`t`.`idTipousuario` AS `idTipousuario`,`d`.`idDatosUsuarios` AS `idDatosUsuarios`,concat(`d`.`ape`,' ',`d`.`nom`) AS `nombre`,`u`.`psw` AS `psw`,`u`.`nomusr` AS `nomusr`,`d`.`dire` AS `dire`,`d`.`tel` AS `tel`,`d`.`dni` AS `dni`,`t`.`nomtpus` AS `nomtpus`,`u`.`idSede` AS `idSede`,`d`.`nom` AS `nom`,`d`.`ape` AS `ape` from ((`usuario` `u` join `datosusuarios` `d`) join `tipousuario` `t`) where ((`u`.`idusuario` = `d`.`Usuario_idusuario`) and (`u`.`Tipousuario_idTipousuario` = `t`.`idTipousuario`));

--
-- Definition of view `venta_detalle`
--

DROP TABLE IF EXISTS `venta_detalle`;
DROP VIEW IF EXISTS `venta_detalle`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `venta_detalle` AS select `i`.`idVenta` AS `idventa`,`i`.`nomclie` AS `nomclie`,`i`.`dir` AS `dir`,`i`.`numident` AS `numident`,`i`.`nomctlg` AS `nomctlg`,`i`.`prc` AS `prc`,`v`.`fecvta` AS `fec`,`i`.`serie` AS `serie`,`id`.`desident` AS `desident`,`tp`.`tipcompr` AS `tipcompr`,`i`.`codbrr` AS `codbrr`,`i`.`seri` AS `seri` from ((((((`impri_comprobante_venta1` `i` join `venta` `v`) join `cliente` `c`) join `identificacion` `id`) join `compventa` `co`) join `comprobantes` `cpr`) join `tipocomprobante` `tp`) where ((`i`.`idVenta` = `v`.`idVenta`) and (`v`.`idCliente` = `c`.`idCliente`) and (`c`.`idCliente` = `id`.`idCliente`) and (`v`.`idVenta` = `co`.`idVenta`) and (`co`.`idComprobantes` = `cpr`.`idComprobantes`) and (`cpr`.`idTipoComprobante` = `tp`.`idTipoComprobante`));

--
-- Definition of view `ventas_imprimir_todo`
--

DROP TABLE IF EXISTS `ventas_imprimir_todo`;
DROP VIEW IF EXISTS `ventas_imprimir_todo`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ventas_imprimir_todo` AS select `v`.`idVenta` AS `idventa`,`c`.`nomclie` AS `nomclie`,`v`.`fecvta` AS `fecvta`,`v`.`moda` AS `moda`,`d`.`nom` AS `nom`,sum(`vp`.`prc`) AS `total`,`u`.`nomusr` AS `nomusr`,`cpr`.`nume` AS `nume`,`ti`.`tipcompr` AS `tipcompr` from (((((((`venta` `v` join `cliente` `c`) join `usuario` `u`) join `datosusuarios` `d`) join `venta_producto` `vp`) join `compventa` `co`) join `comprobantes` `cpr`) join `tipocomprobante` `ti`) where ((`v`.`idCliente` = `c`.`idCliente`) and (`v`.`Usuario_idusuario` = `u`.`idusuario`) and (`u`.`idusuario` = `d`.`Usuario_idusuario`) and (`v`.`idVenta` = `vp`.`Venta_idVenta`) and (`v`.`idVenta` = `co`.`idVenta`) and (`co`.`idComprobantes` = `cpr`.`idComprobantes`) and (`cpr`.`idTipoComprobante` = `ti`.`idTipoComprobante`)) group by `v`.`idVenta`;

--
-- Definition of view `ver_compra_editar`
--

DROP TABLE IF EXISTS `ver_compra_editar`;
DROP VIEW IF EXISTS `ver_compra_editar`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ver_compra_editar` AS select `c`.`idCompra` AS `idcompra`,`c`.`fec` AS `fec`,`pr`.`idProveedor` AS `idproveedor`,`dc`.`idDoc_Compra` AS `iddoc_compra`,`dc`.`serie` AS `serie`,`dc`.`nume` AS `nume`,`dc`.`tipo` AS `tipo`,`p`.`idProducto` AS `idproducto`,`p`.`codbrr` AS `codbrr`,`p`.`costo` AS `costo`,`s`.`seri` AS `seri`,`g`.`periodo` AS `periodo`,`g`.`cantiperio` AS `cantiperio`,`pr`.`nompro` AS `nompro`,`ca`.`nomctlg` AS `nomctlg` from ((((((`compra` `c` join `doc_compra` `dc`) join `producto` `p`) join `serie` `s`) join `garantiaxcompra` `g`) join `proveedor` `pr`) join `catalogoproducto` `ca`) where ((`c`.`idCompra` = `dc`.`idCompra`) and (`c`.`idProveedor` = `pr`.`idProveedor`) and (`dc`.`idDoc_Compra` = `p`.`idDoc_Compra`) and (`p`.`idProducto` = `g`.`idProducto`) and (`p`.`idProducto` = `s`.`Producto_idProducto`) and (`p`.`Catalogoproducto_codctlg` = `ca`.`codctlg`));

--
-- Definition of view `ver_para_devolver`
--

DROP TABLE IF EXISTS `ver_para_devolver`;
DROP VIEW IF EXISTS `ver_para_devolver`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ver_para_devolver` AS select `p`.`idProducto` AS `idproducto`,`c`.`nomctlg` AS `nomctlg`,`p`.`codbrr` AS `codbrr`,`p`.`precVenta` AS `precVenta`,`p`.`estdo` AS `estdo`,`l`.`nommod` AS `nommod`,`s`.`seri` AS `seri` from (((`producto` `p` join `catalogoproducto` `c`) join `modelo` `l`) join `serie` `s`) where ((`p`.`Catalogoproducto_codctlg` = `c`.`codctlg`) and (`c`.`idModelos` = `l`.`idModelos`) and (`p`.`idProducto` = `s`.`Producto_idProducto`));

--
-- Definition of view `ver_vendido`
--

DROP TABLE IF EXISTS `ver_vendido`;
DROP VIEW IF EXISTS `ver_vendido`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ver_vendido` AS select `v`.`idVenta` AS `idventa`,`p`.`idProducto` AS `idproducto`,`p`.`codbrr` AS `codbrr`,`vp`.`prc` AS `prc`,`c`.`nomctlg` AS `nomctlg`,`m`.`nommrc` AS `nommrc`,`s`.`seri` AS `seri`,`v`.`fecvta` AS `fecvta`,`v`.`Usuario_idusuario` AS `Usuario_idusuario` from (((((`producto` `p` join `venta_producto` `vp`) join `catalogoproducto` `c`) join `marca` `m`) join `serie` `s`) join `venta` `v`) where ((`p`.`idProducto` = `vp`.`Producto_idProducto`) and (`p`.`Catalogoproducto_codctlg` = `c`.`codctlg`) and (`c`.`idMarca` = `m`.`idMarca`) and (`p`.`idProducto` = `s`.`Producto_idProducto`) and (`v`.`idVenta` = `vp`.`Venta_idVenta`));

--
-- Definition of view `ver_ventas_para_guia`
--

DROP TABLE IF EXISTS `ver_ventas_para_guia`;
DROP VIEW IF EXISTS `ver_ventas_para_guia`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ver_ventas_para_guia` AS select `v`.`idVenta` AS `idventa`,`c`.`nomclie` AS `nomclie`,`v`.`fecvta` AS `fecvta`,`v`.`moda` AS `moda`,`d`.`nom` AS `nom`,sum(`vp`.`prc`) AS `suma`,`u`.`nomusr` AS `nomusr`,`cpr`.`nume` AS `nume` from ((((((`venta` `v` join `cliente` `c`) join `usuario` `u`) join `datosusuarios` `d`) join `venta_producto` `vp`) join `compventa` `co`) join `comprobantes` `cpr`) where ((`v`.`idCliente` = `c`.`idCliente`) and (`v`.`Usuario_idusuario` = `u`.`idusuario`) and (`u`.`idusuario` = `d`.`Usuario_idusuario`) and (`v`.`idVenta` = `vp`.`Venta_idVenta`) and (`v`.`idVenta` = `co`.`idVenta`) and (`co`.`idComprobantes` = `cpr`.`idComprobantes`)) group by `v`.`idVenta`;

--
-- Definition of view `verproductospordocumentocompra`
--

DROP TABLE IF EXISTS `verproductospordocumentocompra`;
DROP VIEW IF EXISTS `verproductospordocumentocompra`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `verproductospordocumentocompra` AS select `c`.`idCompra` AS `idcompra`,`c`.`fec` AS `fec`,`c`.`idProveedor` AS `idproveedor`,`dc`.`idDoc_Compra` AS `idDoc_Compra`,`dc`.`serie` AS `serie`,`dc`.`nume` AS `nume`,`dc`.`tipo` AS `tipo`,`p`.`idProducto` AS `idproducto`,`p`.`fecingralm` AS `fecingralm`,`p`.`codbrr` AS `codbrr`,`p`.`costo` AS `costo`,`p`.`estdo` AS `estdo`,`p`.`precVenta` AS `precVenta`,concat(`mo`.`nommod`,' ',`ct`.`nomctlg`,' ',`un`.`nomuni`) AS `nomctlg`,`pr`.`nompro` AS `nompro` from ((((((`compra` `c` join `doc_compra` `dc`) join `producto` `p`) join `catalogoproducto` `ct`) join `proveedor` `pr`) join `modelo` `mo`) join `unidad` `un`) where ((`c`.`idCompra` = `dc`.`idCompra`) and (`dc`.`idDoc_Compra` = `p`.`idDoc_Compra`) and (`p`.`Catalogoproducto_codctlg` = `ct`.`codctlg`) and (`c`.`idProveedor` = `pr`.`idProveedor`) and (`ct`.`idModelos` = `mo`.`idModelos`) and (`ct`.`idUnidad` = `un`.`idUnidad`));

--
-- Definition of view `vta_catalogo`
--

DROP TABLE IF EXISTS `vta_catalogo`;
DROP VIEW IF EXISTS `vta_catalogo`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_catalogo` AS select `cp`.`codctlg` AS `Codigo`,concat(`mo`.`nommod`,' ',`m`.`nommrc`,' ',`cp`.`nomctlg`) AS `Producto`,`cp`.`precsg` AS `Precio`,`mo`.`nommod` AS `Modelo`,`m`.`nommrc` AS `Marca`,`u`.`nomuni` AS `abre`,`cp`.`stockmin` AS `StockMinimo`,`cp`.`prexmenor` AS `prexmenor`,`cp`.`prexmayor` AS `prexmayor`,`cp`.`codbarra` AS `Barra` from ((((`marca` `m` join `modelo` `mo`) join `modelocatalogo` `mc`) join `catalogoproducto` `cp`) join `unidad` `u`) where ((`cp`.`idMarca` = `m`.`idMarca`) and (`cp`.`idUnidad` = `u`.`idUnidad`) and (`mo`.`idModelos` = `mc`.`idModelos`) and (`cp`.`codctlg` = `mc`.`codctlg`));

--
-- Definition of view `vta_cliente`
--

DROP TABLE IF EXISTS `vta_cliente`;
DROP VIEW IF EXISTS `vta_cliente`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_cliente` AS select `c`.`idCliente` AS `idcliente`,`c`.`nomclie` AS `nomclie`,`c`.`dir` AS `dir`,`c`.`fono` AS `fono`,`c`.`tipo` AS `tipo`,`i`.`idIdentificacion` AS `idIdentificacion`,`i`.`desident` AS `desident`,`i`.`numident` AS `numident` from (`cliente` `c` join `identificacion` `i`) where (`c`.`idCliente` = `i`.`idCliente`);

--
-- Definition of view `vta_comprobantes`
--

DROP TABLE IF EXISTS `vta_comprobantes`;
DROP VIEW IF EXISTS `vta_comprobantes`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_comprobantes` AS select `c`.`idComprobantes` AS `ID`,`tc`.`ser` AS `Serie`,`c`.`nume` AS `Numero`,`tc`.`tipcompr` AS `Tipo`,`c`.`esta` AS `Estado`,`tc`.`facde` AS `Para`,`s`.`nomse` AS `Sede`,`tc`.`ruc` AS `ruc` from ((`comprobantes` `c` join `tipocomprobante` `tc`) join `sede` `s`) where ((`tc`.`idSede` = `s`.`idSede`) and (`c`.`idTipoComprobante` = `tc`.`idTipoComprobante`));

--
-- Definition of view `vta_datosimpresion`
--

DROP TABLE IF EXISTS `vta_datosimpresion`;
DROP VIEW IF EXISTS `vta_datosimpresion`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_datosimpresion` AS select `c`.`idCliente` AS `idCliente`,`c`.`nomclie` AS `nomclie`,`c`.`dir` AS `dir`,dayofmonth(`v`.`fecvta`) AS `dia`,`mes`(month(`v`.`fecvta`)) AS `mes`,year(`v`.`fecvta`) AS `anio`,`c`.`tipo` AS `Tipo`,`v`.`idVenta` AS `idVenta` from ((`cliente` `c` join `venta` `v`) join `identificacion` `i`) where ((`c`.`idCliente` = `v`.`idCliente`) and (`c`.`idCliente` = `i`.`idCliente`));

--
-- Definition of view `vta_datossede`
--

DROP TABLE IF EXISTS `vta_datossede`;
DROP VIEW IF EXISTS `vta_datossede`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_datossede` AS select distinct `v`.`idVenta` AS `idVenta`,`cv`.`idCompVenta` AS `idCompVenta`,`cb`.`idComprobantes` AS `idcomprobantes`,`tc`.`tipcompr` AS `tipcompr`,`cb`.`serie` AS `serie`,`cb`.`nume` AS `nume`,`cv`.`fecemi` AS `fecemi`,`v`.`fecvta` AS `fecvta`,`c`.`nomclie` AS `nomclie`,`i`.`desident` AS `desident`,`i`.`numident` AS `numident`,`cb`.`esta` AS `esta`,`s`.`nomse` AS `nomse`,`s`.`idSede` AS `idSede` from ((((((`cliente` `c` join `tipocomprobante` `tc`) join `comprobantes` `cb`) join `sede` `s`) join `identificacion` `i`) join `venta` `v`) join `compventa` `cv`) where ((`tc`.`idTipoComprobante` = `cb`.`idTipoComprobante`) and (`cb`.`idComprobantes` = `cv`.`idComprobantes`) and (`v`.`idVenta` = `cv`.`idVenta`) and (`v`.`idCliente` = `c`.`idCliente`) and (`i`.`idCliente` = `c`.`idCliente`) and (`s`.`idSede` = `tc`.`idSede`));

--
-- Definition of view `vta_deuda`
--

DROP TABLE IF EXISTS `vta_deuda`;
DROP VIEW IF EXISTS `vta_deuda`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_deuda` AS select `c`.`idCompra` AS `idCompra`,`p`.`nompro` AS `nompro`,`d`.`tipo` AS `tipo`,`d`.`nume` AS `nume`,`c`.`fec` AS `fec`,if(((select distinct sum(`pagodoccompra`.`monto`) AS `sum(``pagodoccompra``.``monto``)` from `pagodoccompra` where (`pagodoccompra`.`idDoc_Compra` = `d`.`idDoc_Compra`)) <> 0),(sum(`pr`.`costo`) - (select distinct sum(`pagodoccompra`.`monto`) AS `sum(``pagodoccompra``.``monto``)` from `pagodoccompra` where (`pagodoccompra`.`idDoc_Compra` = `d`.`idDoc_Compra`))),sum(`pr`.`costo`)) AS `Saldo`,`d`.`situa` AS `situa`,`d`.`idSede` AS `idSede` from (((`proveedor` `p` join `compra` `c`) join `doc_compra` `d`) join `producto` `pr`) where ((`p`.`idProveedor` = `c`.`idProveedor`) and (`c`.`idCompra` = `d`.`idCompra`) and (`d`.`idDoc_Compra` = `pr`.`idDoc_Compra`) and (`d`.`situa` <> 'Pagado')) group by `c`.`idCompra`;

--
-- Definition of view `vta_importes`
--

DROP TABLE IF EXISTS `vta_importes`;
DROP VIEW IF EXISTS `vta_importes`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_importes` AS select `v`.`idVenta` AS `idventa`,sum(`v`.`Importe`) AS `Total`,round((sum(`v`.`Importe`) / 1.18),2) AS `SubTotal`,(sum(`v`.`Importe`) - round((sum(`v`.`Importe`) / 1.18),2)) AS `Igv` from `vta_parafacturar` `v` group by `v`.`idVenta`;

--
-- Definition of view `vta_inventarioinicial`
--

DROP TABLE IF EXISTS `vta_inventarioinicial`;
DROP VIEW IF EXISTS `vta_inventarioinicial`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_inventarioinicial` AS select `p`.`codbrr` AS `Codigo`,`c`.`nomctlg` AS `Descripcion`,`m`.`nommrc` AS `Marca`,`p`.`precVenta` AS `Precio`,`p`.`fecingralm` AS `Ingreso`,`pv`.`nompro` AS `nompro`,`s`.`nomse` AS `Sede` from ((((((`producto` `p` join `catalogoproducto` `c`) join `proveedor` `pv`) join `sede` `s`) join `inventarioinicial` `iv`) join `marca` `m`) join `modelo` `lp`) where ((`p`.`Catalogoproducto_codctlg` = `c`.`codctlg`) and (`iv`.`idProducto` = `p`.`idProducto`) and (`iv`.`idSede` = `s`.`idSede`) and (`iv`.`idProveedor` = `pv`.`idProveedor`) and (`m`.`idMarca` = `c`.`idMarca`) and (`lp`.`idModelos` = `c`.`idModelos`) and (`pv`.`idProveedor` = 1));

--
-- Definition of view `vta_losproveedores`
--

DROP TABLE IF EXISTS `vta_losproveedores`;
DROP VIEW IF EXISTS `vta_losproveedores`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_losproveedores` AS select `p`.`idProveedor` AS `Codigo`,`r`.`numruc` AS `Ruc`,`p`.`nompro` AS `Proveedor`,`p`.`dir` AS `Direccion`,`p`.`fono` AS `Telefono`,`p`.`ltrs` AS `Letras` from (`proveedor` `p` join `ruc` `r`) where (`p`.`idProveedor` = `r`.`idProveedor`);

--
-- Definition of view `vta_maestra_producto`
--

DROP TABLE IF EXISTS `vta_maestra_producto`;
DROP VIEW IF EXISTS `vta_maestra_producto`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_maestra_producto` AS select `p`.`idProducto` AS `ID`,`p`.`codbrr` AS `Codigo`,`c`.`nomctlg` AS `Producto`,`m`.`nommrc` AS `Marca`,`p`.`precVenta` AS `P_Venta`,`s`.`seri` AS `Serie`,`p`.`estdo` AS `Estado`,`dc`.`tipo` AS `T_Documento`,`dc`.`nume` AS `N_Documento`,`se`.`nomse` AS `Sede`,`p`.`costo` AS `Costo` from (((((`producto` `p` join `catalogoproducto` `c`) join `doc_compra` `dc`) join `sede` `se`) join `marca` `m`) join `serie` `s`) where ((`p`.`Catalogoproducto_codctlg` = `c`.`codctlg`) and (`p`.`idDoc_Compra` = `dc`.`idDoc_Compra`) and (`s`.`Producto_idProducto` = `p`.`idProducto`) and (`p`.`idSede` = `se`.`idSede`) and (`c`.`idMarca` = `m`.`idMarca`));

--
-- Definition of view `vta_movcaja`
--

DROP TABLE IF EXISTS `vta_movcaja`;
DROP VIEW IF EXISTS `vta_movcaja`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_movcaja` AS select `movimientocaja`.`idMovimientoCaja` AS `idMovimientoCaja`,`movimientocaja`.`concepto` AS `concepto`,`movimientocaja`.`fch` AS `fch`,`movimientocaja`.`comprobante` AS `comprobante`,`movimientocaja`.`tipo` AS `tipo`,`movimientocaja`.`numcomprobante` AS `numcomprobante`,`movimientocaja`.`monto` AS `monto`,`movimientocaja`.`idSede` AS `idSede`,0.00 AS `descuento` from `movimientocaja` union select `v`.`idVenta` AS `idVenta`,concat('Venta a ',' ',`cl`.`nomclie`) AS `Concepto`,`v`.`fecvta` AS `fecvta`,`tc`.`tipcompr` AS `Tipo_Comprobante`,if((`cl`.`nomclie` = 'CONSUMO'),'Egreso','Ingreso') AS `if(cl.nomclie='CONSUMO','Egreso','Ingreso')`,concat(`tc`.`ser`,'_',`c`.`nume`) AS `Nro_Comprobante`,`v`.`montreal` AS `Monto`,`tc`.`idSede` AS `idSede`,`v`.`descuento` AS `descuento` from (((((`venta` `v` join `compventa` `cv`) join `comprobantes` `c`) join `tipocomprobante` `tc`) join `cliente` `cl`) join `detventafacturar` `dvf`) where ((`v`.`idCliente` = `cl`.`idCliente`) and (`cv`.`idVenta` = `v`.`idVenta`) and (`cv`.`idComprobantes` = `c`.`idComprobantes`) and (`c`.`idTipoComprobante` = `tc`.`idTipoComprobante`) and (`dvf`.`idVenta` = `v`.`idVenta`) and (`v`.`moda` = 'Contado') and (`v`.`tipo` <> 'F')) group by `v`.`idVenta` union select `p`.`idPagoxDeuda` AS `ID`,concat('Pago x Deuda del Cliente',' ',`cl`.`nomclie`) AS `Concepto`,`p`.`fecpgxdeu` AS `F_Pago`,`tc`.`tipcompr` AS `Comprobante`,'Ingreso' AS `Tipo`,concat(`tc`.`ser`,'_',`c`.`nume`) AS `Nro_Documento`,`p`.`montpag` AS `Monto_Pagado`,`tc`.`idSede` AS `idSede`,`v`.`descuento` AS `descuento` from ((((((`cliente` `cl` join `venta` `v`) join `tipocomprobante` `tc`) join `comprobantes` `c`) join `compventa` `cv`) join `deuda` `d`) join `pagoxdeuda` `p`) where ((`cl`.`idCliente` = `v`.`idCliente`) and (`tc`.`idTipoComprobante` = `c`.`idTipoComprobante`) and (`c`.`idComprobantes` = `cv`.`idComprobantes`) and (`v`.`idVenta` = `cv`.`idVenta`) and (`v`.`idVenta` = `d`.`idVenta`) and (`d`.`idDeuda` = `p`.`idDeuda`) and (`p`.`montpag` > 0));

--
-- Definition of view `vta_movcaja1`
--

DROP TABLE IF EXISTS `vta_movcaja1`;
DROP VIEW IF EXISTS `vta_movcaja1`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_movcaja1` AS select `m`.`concepto` AS `concepto`,`m`.`fch` AS `fch`,`m`.`comprobante` AS `comprobante`,`m`.`monto` AS `monto`,0.00 AS `descuento`,`m`.`monto` AS `importe`,`m`.`numcomprobante` AS `numcomprobante`,`m`.`tipo` AS `tipo` from `movimientocaja` `m` union all select concat('PAGO DE ',`p`.`tipo`,' ',`p`.`pagador`) AS `concat('PAGO DE ',p.``tipo``,' ',
p.``pagador``)`,`p`.`fecha` AS `fecha`,`p`.`comprobante` AS `comprobante`,`v`.`montreal` AS `monto`,`p`.`descuento` AS `descuento`,(`p`.`monto` - `p`.`descuento`) AS `importe`,`p`.`numerocomprobante` AS `numerocomprobante`,if((`p`.`tipo` = 'Venta'),if((`p`.`pagador` = 'Consumo'),'Egreso','Ingreso'),'Egreso') AS `Tipo` from (`pagos` `p` join `venta` `v`) where (`p`.`id` = `v`.`idVenta`) union all select concat('PAGO POR VENTA A CREDITO A ',`cl`.`nomclie`) AS `concat('PAGO POR VENTA A
CREDITO A ', cl.``nomclie``)`,`p`.`fecpgxdeu` AS `fecpgxdeu`,`t`.`tipcompr` AS `tipcompr`,`v`.`montreal` AS `monto`,0.00 AS `descuento`,`p`.`montpag` AS `importe`,concat(`cr`.`serie`,'-',`cr`.`nume`) AS `numcompro`,'Ingreso' AS `tipo` from ((((((`venta` `v` join `deuda` `d`) join `compventa` `c`) join `cliente` `cl`) join `pagoxdeuda` `p`) join `comprobantes` `cr`) join `tipocomprobante` `t`) where ((`v`.`idVenta` = `d`.`idVenta`) and (`v`.`idVenta` = `c`.`idVenta`) and (`v`.`idCliente` = `cl`.`idCliente`) and (`d`.`idDeuda` = `p`.`idDeuda`) and (`c`.`idComprobantes` = `cr`.`idComprobantes`) and (`cr`.`idTipoComprobante` = `t`.`idTipoComprobante`) and (`p`.`montpag` <> 0)) union all select concat('PAGO POR COMPRA A  ',`p`.`nompro`) AS `concat('PAGO POR COMPRA A  ',p.``nompro``)`,`pa`.`fec` AS `fec`,`d`.`tipo` AS `tipocom`,`pa`.`monto` AS `monto`,0.00 AS `descuneto`,`pa`.`monto` AS `importe`,`d`.`nume` AS `nume`,'Egreso' AS `tipo` from (((`compra` `c` join `doc_compra` `d`) join `proveedor` `p`) join `pagodoccompra` `pa`) where ((`c`.`idCompra` = `d`.`idDoc_Compra`) and (`c`.`idProveedor` = `p`.`idProveedor`) and (`d`.`idDoc_Compra` = `pa`.`idDoc_Compra`));

--
-- Definition of view `vta_numeroguiasegunventa`
--

DROP TABLE IF EXISTS `vta_numeroguiasegunventa`;
DROP VIEW IF EXISTS `vta_numeroguiasegunventa`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_numeroguiasegunventa` AS select `c`.`nume` AS `numeroGuia`,`v`.`idVenta` AS `idVenta` from ((`comprobantes` `c` join `guiaremision` `g`) join `venta` `v`) where ((`g`.`idVenta` = `v`.`idVenta`) and (`g`.`idComprobante` = `c`.`idComprobantes`));

--
-- Definition of view `vta_parafacturar`
--

DROP TABLE IF EXISTS `vta_parafacturar`;
DROP VIEW IF EXISTS `vta_parafacturar`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_parafacturar` AS select `cp`.`codctlg` AS `codctlg`,`cp`.`nomctlg` AS `nomctlg`,`df`.`cant` AS `cant`,`df`.`prec` AS `prec`,(`df`.`cant` * `df`.`prec`) AS `Importe`,`df`.`idVenta` AS `idVenta`,`v`.`fecvta` AS `fecvta`,`u`.`abre` AS `unidad` from (((`detventafacturar` `df` join `venta` `v`) join `catalogoproducto` `cp`) join `unidad` `u`) where ((`df`.`idVenta` = `v`.`idVenta`) and (`cp`.`codctlg` = `df`.`codctlg`) and (`cp`.`idUnidad` = `u`.`idUnidad`)) order by `cp`.`nomctlg`;

--
-- Definition of view `vta_porvender`
--

DROP TABLE IF EXISTS `vta_porvender`;
DROP VIEW IF EXISTS `vta_porvender`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_porvender` AS select `p`.`idProducto` AS `ID`,concat(`mo`.`nommod`,' ',`cp`.`nomctlg`) AS `Catalogo`,`p`.`estdo` AS `Estado`,`u`.`nomusr` AS `Usuario`,`pv`.`sede` AS `Sede` from ((((((`usuario` `u` join `catalogoproducto` `cp`) join `producto` `p`) join `por_vender` `pv`) join `sede` `s`) join `modelo` `mo`) join `unidad` `un`) where ((`pv`.`idusuario` = `u`.`idusuario`) and (`p`.`Catalogoproducto_codctlg` = `cp`.`codctlg`) and (`pv`.`idproducto` = `p`.`idProducto`) and (`s`.`idSede` = `p`.`idSede`) and (`cp`.`idModelos` = `mo`.`idModelos`) and (`cp`.`idUnidad` = `un`.`idUnidad`));

--
-- Definition of view `vta_producto1`
--

DROP TABLE IF EXISTS `vta_producto1`;
DROP VIEW IF EXISTS `vta_producto1`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_producto1` AS select `pr`.`idProducto` AS `Item`,`pr`.`codbrr` AS `Codigo`,`ctp`.`nomctlg` AS `Producto`,`ctp`.`precsg` AS `Precio`,`pr`.`costo` AS `Costo`,`se`.`seri` AS `Serie`,`pr`.`fecingralm` AS `F_Ingreso`,`pr`.`estdo` AS `Estado`,`p`.`nompro` AS `Proveedor` from (((((`proveedor` `p` join `compra` `cp`) join `doc_compra` `dcm`) join `producto` `pr`) join `catalogoproducto` `ctp`) join `serie` `se`) where ((`p`.`idProveedor` = `cp`.`idProveedor`) and (`cp`.`idCompra` = `dcm`.`idCompra`) and (`dcm`.`idDoc_Compra` = `pr`.`idDoc_Compra`) and (`ctp`.`codctlg` = `pr`.`Catalogoproducto_codctlg`) and (`pr`.`idProducto` = `se`.`Producto_idProducto`));

--
-- Definition of view `vta_productoprestar`
--

DROP TABLE IF EXISTS `vta_productoprestar`;
DROP VIEW IF EXISTS `vta_productoprestar`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_productoprestar` AS select `v`.`Codigo` AS `Codigo`,`v`.`Producto` AS `Producto`,`v`.`Serie` AS `Serie`,`v`.`Costo` AS `costo`,`v`.`Estado` AS `estado` from `vta_producto1` `v` where (`v`.`Estado` = 'disponible');

--
-- Definition of view `vta_productosstock`
--

DROP TABLE IF EXISTS `vta_productosstock`;
DROP VIEW IF EXISTS `vta_productosstock`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_productosstock` AS select count(`p`.`Catalogoproducto_codctlg`) AS `stock`,`p`.`idProducto` AS `idproducto`,`p`.`fecingralm` AS `fecingralm`,`p`.`Catalogoproducto_codctlg` AS `Catalogoproducto_codctlg`,`p`.`idDoc_Compra` AS `idDoc_Compra`,`c`.`nomctlg` AS `nomctlg`,`c`.`idMarca` AS `idmarca`,`c`.`idModelos` AS `idmodelos`,`c`.`idUnidad` AS `idUnidad`,`l`.`nommod` AS `nommod`,`m`.`nommrc` AS `nommrc`,`s`.`nomse` AS `Sede`,`c`.`precsg` AS `Precio` from ((((`producto` `p` join `catalogoproducto` `c`) join `modelo` `l`) join `marca` `m`) join `sede` `s`) where ((`p`.`Catalogoproducto_codctlg` = `c`.`codctlg`) and (`p`.`idSede` = `s`.`idSede`) and (`c`.`idModelos` = `l`.`idModelos`) and (`c`.`idMarca` = `m`.`idMarca`) and (`p`.`estdo` = 'Disponible') and (not(`p`.`idProducto` in (select `venta_producto`.`Producto_idProducto` AS `Producto_idproducto` from `venta_producto`)))) group by `c`.`codctlg`;

--
-- Definition of view `vta_tipocomprobante`
--

DROP TABLE IF EXISTS `vta_tipocomprobante`;
DROP VIEW IF EXISTS `vta_tipocomprobante`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_tipocomprobante` AS select `tc`.`idTipoComprobante` AS `ID`,`s`.`nomse` AS `Sede`,`tc`.`ruc` AS `Ruc`,`tc`.`tipcompr` AS `Tipo`,`tc`.`ser` AS `Serie`,`tc`.`candig` AS `Cant_Digitos`,`tc`.`facde` AS `Para` from (`tipocomprobante` `tc` join `sede` `s`) where (`tc`.`idSede` = `s`.`idSede`);

--
-- Definition of view `vta_usuarios`
--

DROP TABLE IF EXISTS `vta_usuarios`;
DROP VIEW IF EXISTS `vta_usuarios`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_usuarios` AS select `u`.`idusuario` AS `Codigo`,`u`.`nomusr` AS `Usuario`,`u`.`psw` AS `Clave`,`tu`.`nomtpus` AS `Tipo`,`s`.`nomse` AS `Sede` from ((`tipousuario` `tu` join `sede` `s`) join `usuario` `u`) where ((`u`.`idSede` = `s`.`idSede`) and (`tu`.`idTipousuario` = `u`.`Tipousuario_idTipousuario`));

--
-- Definition of view `vta_vistaejemplo`
--

DROP TABLE IF EXISTS `vta_vistaejemplo`;
DROP VIEW IF EXISTS `vta_vistaejemplo`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vta_vistaejemplo` AS select `cp`.`codbarra` AS `codbarra`,`cp`.`nomctlg` AS `Catalogo`,`m`.`nommrc` AS `Marca`,`u`.`nomuni` AS `Unidad` from ((`marca` `m` join `unidad` `u`) join `catalogoproducto` `cp`) where ((`cp`.`idUnidad` = `u`.`idUnidad`) and (`m`.`idMarca` = `cp`.`idMarca`));

--
-- Definition of view `vtaactvta`
--

DROP TABLE IF EXISTS `vtaactvta`;
DROP VIEW IF EXISTS `vtaactvta`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vtaactvta` AS select `venta_producto`.`Producto_idProducto` AS `Idproducto` from `venta_producto` where `venta_producto`.`Venta_idVenta` in (select `venta`.`idVenta` AS `idVenta` from `venta`);

--
-- Definition of view `vtaconsulta`
--

DROP TABLE IF EXISTS `vtaconsulta`;
DROP VIEW IF EXISTS `vtaconsulta`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vtaconsulta` AS select (select `p`.`idProducto` AS `idProducto` from `producto` `p` where ((`p`.`Catalogoproducto_codctlg` = `c`.`codctlg`) and (`p`.`estdo` = 'Disponible')) limit 1) AS `Name_exp_1`,(select `p`.`idSede` AS `idSede` from `producto` `p` where ((`p`.`Catalogoproducto_codctlg` = `c`.`codctlg`) and (`p`.`estdo` = 'Disponible')) limit 1) AS `Sede`,(select `p`.`codbrr` AS `codbrr` from `producto` `p` where ((`p`.`Catalogoproducto_codctlg` = `c`.`codctlg`) and (`p`.`estdo` = 'Disponible')) limit 1) AS `Name_exp_3`,concat(`mo`.`nommod`,' ',`c`.`nomctlg`) AS `nomctlg`,`m`.`nommrc` AS `nommrc`,`u`.`nomuni` AS `nomuni`,(select `p`.`costo` AS `costo` from `producto` `p` where ((`p`.`Catalogoproducto_codctlg` = `c`.`codctlg`) and (`p`.`estdo` = 'Disponible')) limit 1) AS `Name_exp_7`,`c`.`prexmayor` AS `prexmayor`,(select count(`p`.`Catalogoproducto_codctlg`) AS `count(``p``.``Catalogoproducto_codctlg``)` from `producto` `p` where ((`p`.`Catalogoproducto_codctlg` = `c`.`codctlg`) and (`p`.`estdo` = 'Disponible')) limit 1) AS `Name_exp_9` from (((`catalogoproducto` `c` join `marca` `m`) join `modelo` `mo`) join `unidad` `u`) where ((`c`.`idMarca` = `m`.`idMarca`) and (`c`.`idModelos` = `mo`.`idModelos`) and (`c`.`idUnidad` = `u`.`idUnidad`) and ((concat(`mo`.`nommod`,' ',`c`.`nomctlg`) like '%%') or ((select `p`.`codbrr` AS `codbrr` from `producto` `p` where ((`p`.`Catalogoproducto_codctlg` = `c`.`codctlg`) and (`p`.`estdo` = 'Disponible')) limit 1) like '%%') or (`m`.`nommrc` like '%%') or (`c`.`nomctlg` like '%%')) and `c`.`codctlg` in (select `p`.`Catalogoproducto_codctlg` AS `Catalogoproducto_codctlg` from `producto` `p` where (`p`.`estdo` = 'Disponible')));

--
-- Definition of view `vtapermisosusuarios`
--

DROP TABLE IF EXISTS `vtapermisosusuarios`;
DROP VIEW IF EXISTS `vtapermisosusuarios`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vtapermisosusuarios` AS select `ps`.`idPermisos` AS `idpermisos`,`ps`.`Descripcion` AS `descripcion`,`p`.`idPermisosUsuario` AS `idPermisosUsuario`,`p`.`idusuario` AS `idusuario` from (`permisosusuario` `p` join `permisos` `ps`) where (`p`.`idPermisos` = `ps`.`idPermisos`);

--
-- Definition of view `vtaprodcutostotal`
--

DROP TABLE IF EXISTS `vtaprodcutostotal`;
DROP VIEW IF EXISTS `vtaprodcutostotal`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vtaprodcutostotal` AS select `p`.`idProducto` AS `idProducto`,`p`.`fecingralm` AS `fecingralm`,`p`.`Catalogoproducto_codctlg` AS `Catalogoproducto_codctlg`,`p`.`codbrr` AS `codbrr`,`p`.`idDoc_Compra` AS `idDoc_Compra`,`p`.`costo` AS `costo`,`p`.`estdo` AS `estdo`,`p`.`precVenta` AS `precVenta`,`s`.`idSerie` AS `idSerie`,`s`.`seri` AS `seri`,`s`.`Producto_idProducto` AS `Producto_idProducto`,`c`.`codctlg` AS `codctlg`,concat(`mo`.`nommod`,' ',`c`.`nomctlg`,' ',`un`.`nomuni`) AS `nomctlg`,`c`.`precsg` AS `precsg`,`c`.`idMarca` AS `idMarca`,`c`.`idModelos` AS `idModelos`,`c`.`idUnidad` AS `idUnidad`,`se`.`nomse` AS `Sede` from (((((`producto` `p` join `serie` `s`) join `catalogoproducto` `c`) join `sede` `se`) join `modelo` `mo`) join `unidad` `un`) where ((`p`.`Catalogoproducto_codctlg` = `c`.`codctlg`) and (`s`.`Producto_idProducto` = `p`.`idProducto`) and (`se`.`idSede` = `p`.`idSede`) and (`c`.`idModelos` = `mo`.`idModelos`) and (`c`.`idUnidad` = `un`.`idUnidad`));



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
