package gui;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

public class Modelo {
    private String ip;
    private String user;
    private String password;
    private String adminPassword;

    public Modelo() {
        getPropValues();
    }

    public String getIp() { return ip; }
    public String getUser() { return user; }
    public String getPassword() { return password; }
    public String getAdminPassword() { return adminPassword; }

    private Connection conexion;

    void conectar() {
        try {
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://" + ip + ":3306/vinoteca", user, password);
        } catch (SQLException sqle) {
            try {
                conexion = DriverManager.getConnection(
                        "jdbc:mysql://" + ip + ":3306/", user, password);
                PreparedStatement statement = null;
                String code = leerFichero();
                String[] query = code.split("--");
                for (String aQuery : query) {
                    statement = conexion.prepareStatement(aQuery);
                    statement.executeUpdate();
                }
                assert statement != null;
                statement.close();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String leerFichero() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("vinoteca.sql"));
        String linea;
        StringBuilder stringBuilder = new StringBuilder();
        while ((linea = reader.readLine()) != null) {
            stringBuilder.append(linea);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    void desconectar() {
        try {
            conexion.close();
            conexion = null;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    //vinos
    void insertarVino(String nombre, String tipo, int añada, String origen,
                      float precio, int stock, int idbodega, LocalDate fechaIngreso) {
        String sql = "INSERT INTO vinos (nombre, tipo, añada, origen, precio, stock, idbodega, fechaingreso) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement s = conexion.prepareStatement(sql)) {
            s.setString(1, nombre);
            s.setString(2, tipo);
            s.setInt(3, añada);
            s.setString(4, origen);
            s.setFloat(5, precio);
            s.setInt(6, stock);
            s.setInt(7, idbodega);
            s.setDate(8, Date.valueOf(fechaIngreso));
            s.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    void modificarVino(String nombre, String tipo, int añada, String origen,
                       float precio, int stock, int idbodega, LocalDate fechaIngreso, int idvino) {
        String sql = "UPDATE vinos SET nombre=?, tipo=?, añada=?, origen=?, precio=?, stock=?, idbodega=?, fechaingreso=? WHERE idvino=?";
        try (PreparedStatement s = conexion.prepareStatement(sql)) {
            s.setString(1, nombre);
            s.setString(2, tipo);
            s.setInt(3, añada);
            s.setString(4, origen);
            s.setFloat(5, precio);
            s.setInt(6, stock);
            s.setInt(7, idbodega);
            s.setDate(8, Date.valueOf(fechaIngreso));
            s.setInt(9, idvino);
            s.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    void eliminarVino(int idvino) {
        String sql = "DELETE FROM vinos WHERE idvino=?";
        try (PreparedStatement s = conexion.prepareStatement(sql)) {
            s.setInt(1, idvino);
            s.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    ResultSet consultarVinos() throws SQLException {
        String sql = "SELECT v.idvino as 'ID', v.nombre as 'Nombre', v.tipo as 'Tipo', " +
                "v.añada as 'Añada', v.origen as 'Origen', v.precio as 'Precio', " +
                "v.stock as 'Stock', concat(b.idbodega,' - ',b.nombre) as 'Bodega', " +
                "v.fechaingreso as 'Fecha de ingreso' " +
                "FROM vinos v INNER JOIN bodegas b ON b.idbodega = v.idbodega";
        return conexion.prepareStatement(sql).executeQuery();
    }

    public ResultSet buscarVino(String nombre) throws SQLException {
        String sql = "SELECT * FROM vinos WHERE nombre = ?";
        PreparedStatement s = conexion.prepareStatement(sql);
        s.setString(1, nombre);
        return s.executeQuery();
    }

    public boolean vinoNombreYaExiste(String nombre) {
        try {
            PreparedStatement s = conexion.prepareStatement("SELECT COUNT(*) FROM vinos WHERE nombre=?");
            s.setString(1, nombre);
            ResultSet rs = s.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    //bodegas
    void insertarBodega(String nombre, String pais, String telefono,
                        String email, String direccion) {
        String sql = "INSERT INTO bodegas (nombre, pais, telefono, email, direccion) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement s = conexion.prepareStatement(sql)) {
            s.setString(1, nombre);
            s.setString(2, pais);
            s.setString(3, telefono);
            s.setString(4, email);
            s.setString(5, direccion);
            s.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    void modificarBodega(String nombre, String pais, String telefono,
                         String email, String direccion, int idbodega) {
        String sql = "UPDATE bodegas SET nombre=?, pais=?, telefono=?, email=?, direccion=? WHERE idbodega=?";
        try (PreparedStatement s = conexion.prepareStatement(sql)) {
            s.setString(1, nombre);
            s.setString(2, pais);
            s.setString(3, telefono);
            s.setString(4, email);
            s.setString(5, direccion);
            s.setInt(6, idbodega);
            s.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    void eliminarBodega(int idbodega) {
        String sql = "DELETE FROM bodegas WHERE idbodega=?";
        try (PreparedStatement s = conexion.prepareStatement(sql)) {
            s.setInt(1, idbodega);
            s.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    ResultSet consultarBodegas() throws SQLException {
        String sql = "SELECT idbodega as 'ID', nombre as 'Nombre', pais as 'País', " +
                "telefono as 'Teléfono', email as 'Email', direccion as 'Dirección' FROM bodegas";
        return conexion.prepareStatement(sql).executeQuery();
    }

    public ResultSet buscarBodega(String nombre) throws SQLException {
        String sql = "SELECT * FROM bodegas WHERE nombre = ?";
        PreparedStatement s = conexion.prepareStatement(sql);
        s.setString(1, nombre);
        return s.executeQuery();
    }

    public boolean bodegaNombreYaExiste(String nombre) {
        try {
            PreparedStatement s = conexion.prepareStatement("SELECT COUNT(*) FROM bodegas WHERE nombre=?");
            s.setString(1, nombre);
            ResultSet rs = s.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    //empleados
    void insertarEmpleado(String nombre, String apellidos, String dni, String cargo,
                          String telefono, float salario, LocalDate fechaContratacion) {
        String sql = "INSERT INTO empleados (nombre, apellidos, dni, cargo, telefono, salario, fechacontratacion) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement s = conexion.prepareStatement(sql)) {
            s.setString(1, nombre);
            s.setString(2, apellidos);
            s.setString(3, dni);
            s.setString(4, cargo);
            s.setString(5, telefono);
            s.setFloat(6, salario);
            s.setDate(7, Date.valueOf(fechaContratacion));
            s.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    void modificarEmpleado(String nombre, String apellidos, String dni, String cargo,
                           String telefono, float salario, LocalDate fechaContratacion, int idempleado) {
        String sql = "UPDATE empleados SET nombre=?, apellidos=?, dni=?, cargo=?, telefono=?, salario=?, fechacontratacion=? WHERE idempleado=?";
        try (PreparedStatement s = conexion.prepareStatement(sql)) {
            s.setString(1, nombre);
            s.setString(2, apellidos);
            s.setString(3, dni);
            s.setString(4, cargo);
            s.setString(5, telefono);
            s.setFloat(6, salario);
            s.setDate(7, Date.valueOf(fechaContratacion));
            s.setInt(8, idempleado);
            s.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    void eliminarEmpleado(int idempleado) {
        String sql = "DELETE FROM empleados WHERE idempleado=?";
        try (PreparedStatement s = conexion.prepareStatement(sql)) {
            s.setInt(1, idempleado);
            s.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    ResultSet consultarEmpleados() throws SQLException {
        String sql = "SELECT idempleado as 'ID', nombre as 'Nombre', apellidos as 'Apellidos', " +
                "dni as 'DNI', cargo as 'Cargo', telefono as 'Teléfono', " +
                "salario as 'Salario', fechacontratacion as 'Fecha de contratación' FROM empleados";
        return conexion.prepareStatement(sql).executeQuery();
    }

    public ResultSet buscarEmpleado(String dni) throws SQLException {
        String sql = "SELECT * FROM empleados WHERE dni = ?";
        PreparedStatement s = conexion.prepareStatement(sql);
        s.setString(1, dni);
        return s.executeQuery();
    }

    public boolean empleadoYaExiste(String nombre, String apellidos) {
        try {
            PreparedStatement s = conexion.prepareStatement("SELECT COUNT(*) FROM empleados WHERE nombre=? AND apellidos=?");
            s.setString(1, nombre);
            s.setString(2, apellidos);
            ResultSet rs = s.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    //ventas
    void insertarVenta(int idempleado, int idvino, String nombreCliente,
                       String estado, String metodoPago, LocalDate fechaVenta, float total) {
        String sql = "INSERT INTO ventas (idempleado, idvino, nombrecliente, estado, metodopago, fechaventa, total) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement s = conexion.prepareStatement(sql)) {
            s.setInt(1, idempleado);
            s.setInt(2, idvino);
            s.setString(3, nombreCliente);
            s.setString(4, estado);
            s.setString(5, metodoPago);
            s.setDate(6, Date.valueOf(fechaVenta));
            s.setFloat(7, total);
            s.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    void modificarVenta(int idempleado, int idvino, String nombreCliente,
                        String estado, String metodoPago, LocalDate fechaVenta, float total, int idventa) {
        String sql = "UPDATE ventas SET idempleado=?, idvino=?, nombrecliente=?, estado=?, metodopago=?, fechaventa=?, total=? WHERE idventa=?";
        try (PreparedStatement s = conexion.prepareStatement(sql)) {
            s.setInt(1, idempleado);
            s.setInt(2, idvino);
            s.setString(3, nombreCliente);
            s.setString(4, estado);
            s.setString(5, metodoPago);
            s.setDate(6, Date.valueOf(fechaVenta));
            s.setFloat(7, total);
            s.setInt(8, idventa);
            s.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    void eliminarVenta(int idventa) {
        String sql = "DELETE FROM ventas WHERE idventa=?";
        try (PreparedStatement s = conexion.prepareStatement(sql)) {
            s.setInt(1, idventa);
            s.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    ResultSet consultarVentas() throws SQLException {
        String sql = "SELECT ve.idventa as 'ID', " +
                "concat(e.idempleado,' - ',e.apellidos,', ',e.nombre) as 'Empleado', " +
                "concat(v.idvino,' - ',v.nombre) as 'Vino', " +
                "ve.nombrecliente as 'Cliente', ve.estado as 'Estado', " +
                "ve.metodopago as 'Método de pago', ve.fechaventa as 'Fecha de venta', " +
                "ve.total as 'Total' " +
                "FROM ventas ve " +
                "INNER JOIN empleados e ON e.idempleado = ve.idempleado " +
                "INNER JOIN vinos v ON v.idvino = ve.idvino";
        return conexion.prepareStatement(sql).executeQuery();
    }

    public ResultSet buscarVenta(String nombreCliente) throws SQLException {
        String sql = "SELECT * FROM ventas WHERE nombrecliente = ?";
        PreparedStatement s = conexion.prepareStatement(sql);
        s.setString(1, nombreCliente);
        return s.executeQuery();
    }

    public boolean ventaClienteYaExiste(String nombreCliente) {
        try {
            PreparedStatement s = conexion.prepareStatement("SELECT COUNT(*) FROM ventas WHERE nombrecliente=?");
            s.setString(1, nombreCliente);
            ResultSet rs = s.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    //config
    private void getPropValues() {
        try {
            Properties prop = new Properties();
            InputStream inputStream = new FileInputStream("config.properties");
            prop.load(inputStream);
            ip = prop.getProperty("ip");
            user = prop.getProperty("user");
            password = prop.getProperty("pass");
            adminPassword = prop.getProperty("admin");
            inputStream.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    void setPropValues(String ip, String user, String pass, String adminPass) {
        try {
            Properties prop = new Properties();
            prop.setProperty("ip", ip);
            prop.setProperty("user", user);
            prop.setProperty("pass", pass);
            prop.setProperty("admin", adminPass);
            OutputStream out = new FileOutputStream("config.properties");
            prop.store(out, null);
        } catch (IOException ex) { ex.printStackTrace(); }
        this.ip = ip;
        this.user = user;
        this.password = pass;
        this.adminPassword = adminPass;
    }
}