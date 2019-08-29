package dev.demos.fast_server.resources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("usuario")
public class Usuario {

    private static String token = "token";

    @GET
    @Path("{cedula}")
    @Produces("application/json")
    public String buscar_usuario(@PathParam("cedula") int cedula) {
        String sql = "SELECT * FROM usuarios WHERE cedula = " + cedula;
        String json_test = "[";
        try {
            Connection conn = Database.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            Boolean first = false;
            while (rs.next()) {
                if (first) {
                    json_test += ',';
                }
                first = true;
                json_test += "{\"cedula\":" + rs.getInt("cedula") + ",\"nombre\":\"" + rs.getString("nombre") + "\",\"apellido\":\"" + rs.getString("apellido") + "\",\"email\":\"" + rs.getString("email") + "\",\"telefono\":\"" + rs.getString("telefono") + "\"}";
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "{\"status\":\"error\"}";
        }
        json_test += "]";
        return json_test;
    }

    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public String crear_usuario(
            @FormParam("cedula") int cedula,
            @FormParam("nombre") String nombre,
            @FormParam("apellido") String apellido,
            @FormParam("email") String email,
            @FormParam("telefono") String telefono) {
        String sql = "INSERT INTO usuarios (cedula,nombre,apellido,email,telefono) VALUES(?,?,?,?,?)";
        try {
            Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cedula);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apellido);
            pstmt.setString(4, email);
            pstmt.setString(5, telefono);
            pstmt.executeUpdate();
            return "{\"status\":\"ok\"}";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "{\"status\":\"error\"}";
        }
    }

    @DELETE
    @Path("{cedula}")
    @Produces("application/json")
    public String eliminar_usuario(@PathParam("cedula") int cedula) {
        String sql = "DELETE FROM usuarios WHERE cedula = ?";
        try {
            Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cedula);
            pstmt.executeUpdate();
            return "{\"status\":\"ok\"}";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "{\"status\":\"error\"}";
        }
    }

}
