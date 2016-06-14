package daw;

import exception.MyException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.User;

/**
 *
 * @author usu21
 */
public class UserDAO {

    private Connection conexion;

    public boolean validateUser(String username, String password) throws MyException {
        conectar();
        try {
            String query = "select username, password from usuario where username=? and password=?;";
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            throw new MyException("Error al valida: " + ex.getLocalizedMessage());
        } finally {
            desconectar();
        }
        return false;

    }

    public void insertUser(User user) throws MyException {

        conectar();
        try {
            String insert = "insert into usuario values(?,?,?,?)";
            PreparedStatement ps = conexion.prepareStatement(insert);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getSurname());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            throw new MyException("Error al insertar: " + ex.getLocalizedMessage());
        } finally {
            desconectar();
        }

    }

    private void conectar() throws MyException {
        try {
            String url = "jdbc:mysql://localhost:3306/mensajeria";
            String user = "root";
            String pass = "jeveris";
            conexion = DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            throw new MyException("Error al conectar " + ex.getLocalizedMessage());
        }
    }

    private void desconectar() throws MyException {
        try {
            conexion.close();
        } catch (SQLException ex) {
            throw new MyException("Error al desconectar " + ex.getLocalizedMessage());
        }
    }

}
