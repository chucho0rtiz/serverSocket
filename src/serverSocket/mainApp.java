package serverSocket;
import java.net.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.*;

public class mainApp {

	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
		System.out.println("******Maquina servidor******");
		boolean state = true;
		// se abre el puerto por el cual se va a conectar el cliente al server
		ServerSocket ss = new ServerSocket(4444);
		
		// Se realiza la conexion a bases de datos
		conectardb conectar = new conectardb();
		Connection conn = conectar.conectar_db("db_server_socket", "chucho", "123456");
		
		while(state) {
			// se acepta la peticion de acceso del cliente
			Socket s = ss.accept();
			System.out.println("cliente conectado");
			
			// se recibe una respuesta del cliente 
			InputStreamReader in = new InputStreamReader(s.getInputStream());
			BufferedReader bf = new BufferedReader(in);
			String str = bf.readLine();
			
			if(str.equals("false")) {
				ss.close();
				System.out.println("Server Close");
				
				state = false;
				break;
				
			} else {
				// se le envia el parametro requerido para traer informacion de la persona
				ArrayList<String> person = conectar.read_data(conn, "personas", Integer.parseInt(str));
				
				// se envia una respuesta al cliente
				PrintWriter pr = new PrintWriter(s.getOutputStream());
				if (person.isEmpty()) pr.println("Persona dueña de ese numero telefonico no existe");
				else pr.println("Lista de datos: "+ person);
				pr.flush();
			}
			
			
		}
		
	}

}
