package dev.demos.fast_server.resources;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("usuarios")
public class Usuarios {
    
    @GET
    @Produces("application/json")
    public String listar_usuarios() {
        String sql = "SELECT * FROM usuarios";
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

}
