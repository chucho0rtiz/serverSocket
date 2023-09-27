package serverSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class conectardb {
	
	public Connection conectar_db(String dbname, String usuario, String clave) {
		Connection conn = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			conn =  DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/"+dbname, usuario, clave);
			if(conn != null) {
				System.out.println("Se conecto la base de datos");
			} else {
				System.out.println("No Se conecto la base de datos");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
		return conn;
	}
	
	public ArrayList<String> read_data(Connection conn, String table_name, int telefono) {
		Statement statement;
		ResultSet rs = null;
		Statement statementc;
		ResultSet rsc = null;
		int idCiudad = 0;
		ArrayList<String> persona = new ArrayList<>();
		try {
			// llamar las personas
			if(telefono > 0) {
				String query = String.format("select * from %s where dir_tel=%d", table_name, telefono);
				statement = conn.createStatement();
				rs = statement.executeQuery(query);
				while(rs.next()) {
					persona.add(" Telefono: "+rs.getString("dir_tel"));
					persona.add("Nombre: "+rs.getString("dir_nombre"));
					persona.add("Direccion: "+rs.getString("dir_direccion"));
					idCiudad = Integer.parseInt(rs.getString("dir_ciud_id"));
				}
				
			}
			//llamar las ciudades
			if(idCiudad != 0) {
				String queryc = String.format("select * from %s where ciud_id=%d","ciudades", idCiudad);
				statementc = conn.createStatement();
				rsc = statementc.executeQuery(queryc);
				while(rsc.next()) {
					persona.add("Ciudad: : "+rsc.getString("ciud_nombre")+" ");
				}
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return persona;
	}
}
