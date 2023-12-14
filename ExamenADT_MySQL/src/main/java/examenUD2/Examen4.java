package examenUD2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Examen4 {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Debe haber dos argumentos (stock a subir y stock minimo a buscar)");
			System.exit(0);
		}
		int parametro1 = 0;
		int parametro2 = 0;
		try {
			parametro1 = Integer.parseInt(args[0]);
			parametro2 = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.err.println("Los parametros han de ser numeros");
			System.exit(0);
		}
		Connection conexion;
		try {
			conexion = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/examen? UseSSL=true & serverTimezone =UTC", "ExamenAAJ", "Examen-2");
			String sql = "{ call subidastock (?, ?) } ";
			CallableStatement cs = conexion.prepareCall(sql);
			cs.setInt(1, parametro1);
			cs.setInt(2, parametro2);
			int rowsAffected = cs.executeUpdate();
			System.out.printf("Subida realizada.%nFilas afectadas: %d%n", rowsAffected);
			
		} catch (SQLException e) {
			System.err.println("Error de SQL.");
			e.printStackTrace();
		}


	}

}
