package examenUD2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Examen2 {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Debe haber un argumento (1 o 2)");
			System.exit(0);
		}
		int valor = 0;
		// Cojo el valor y compruebo que es un numero y que es o 1 o 2.
		try {
			valor = Integer.parseInt(args[0]);
			if (!(valor == 1 || valor == 2))
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			System.err.println("El argumento ha de ser un numero (1 o 2)");
		}
		try {
			Connection conexion = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/examen? UseSSL=true & serverTimezone =UTC", "ExamenAAJ", "Examen-2");
			String query = "";
			if (valor == 1) {
				query = "INSERT INTO CLIENTES VALUES (5, 'PILAR MARTÍN', 'C/Félix Fernández 12', 'Cáceres', '927229988', '11223434L'),"
						+ "(6, 'FERNANDO GARCÍA', 'C/Serrano 25', 'Madrid', '916754554', '88004320E'),"
						+ "(7, 'PEDRO AGUILAR', 'Av Valdecañas 1', 'Navalmoral', '927908790', '4133219J');";

//			query = "INSERT INTO CLIENTES VALUES (7, 'PEDRO AGUILAR', 'Av Valdecañas 1', 'Navalmoral', '927908790', '4133219J');";
			} else {
				query = "INSERT INTO productos VALUES (1, 'Cubo de basura rojo 20 litros', 20, 2, 12.00),"
						+ "(2, '200 lapices Staedtler', 15, 2, 15.25),"
						+ "(3, 'Flamenco chill in 2 discos', 14, 1, 8.00);";
			}
			
			Statement sentencia = conexion.createStatement();
			int affectedRows = sentencia.executeUpdate(query);
			System.out.println(query);
			System.out.printf("Filas afectadas %d%n", affectedRows);
		} catch (SQLException e) {
			System.err.println("Error de SQL.");
			e.printStackTrace();
		}

	}

}
