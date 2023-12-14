package examenUD2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Examen3 {

	public static void main(String[] args) {
		// Chequeo que haya un argumento
		if (args.length != 1) {
			System.err.println("Debe haber un argumento (ID de cliente)");
			System.exit(0);
		}
		try {
			// Aqui hago parse, si no es un numero salta una exception que se trata mas
			// abajo
			int idcliente = Integer.parseInt(args[0]);
			String nombreCliente = "";
			Connection conexion = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/examen? UseSSL=true & serverTimezone =UTC", "ExamenAAJ", "Examen-2");

			// Hago una conexion para ver si el cliente existe y aprovecho a sacar el nombre
			String query = "SELECT nombre FROM CLIENTES WHERE ID = ?";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, idcliente);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				System.err.printf("No existe cliente con ID: %d%n", idcliente); // Si no existe pasa esto y sale del
																				// programa
				System.exit(0);
			} else { // Si existe pues ya recogemos el nombre y hacemos otra query con ella.
				nombreCliente = rs.getString(1);
				System.out.printf("Ventas del cliente: %s%n", nombreCliente);
				query = "SELECT IDVENTA, FECHAVENTA, PRODUCTOS.DESCRIPCION, CANTIDAD, PRODUCTOS.PVP, CANTIDAD*PVP AS IMPORTE FROM VENTAS JOIN PRODUCTOS ON (VENTAS.IDPRODUCTO = productos.ID) WHERE IDCLIENTE = ?;";
				ps = conexion.prepareStatement(query);
				ps.setInt(1, idcliente);
				rs = ps.executeQuery();
				while (rs.next()) { // Recorro el resultset mostrando los datos.
					System.out.printf("Venta: %d ** %s%n", rs.getInt(1), rs.getString(2));
					System.out.printf("\tProducto: %s%n", rs.getString(3));
					System.out.printf("\tCantidad: %d ** PVP: %.02f%n", rs.getInt(4), rs.getFloat(5));
					System.out.printf("\tImporte: %.02f%n", rs.getFloat(6));
				}
			}

		} catch (SQLException e) {
			System.err.println("Error de SQL");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.err.println("El argumento ha de ser un numero"); // Trato la excepcion del argumento
		}

	}
}
