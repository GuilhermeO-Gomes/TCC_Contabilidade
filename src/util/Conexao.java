package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	// A primeira parte da URL é padrão do jdbc, a segunda trata-se do endereço do BD, e a terceira parte é o nome do banco
	// A porta do localhost está como 3306 porque é padrão, mas caso por algum motivo o seu seja diferente, pode mudar para poder executar
private static final String URL = "jdbc:mysql://localhost:3306/cadastro_java";
private static final String USUARIO = "root";
private static final String SENHA = "";

public static Connection conectar() throws SQLException {
	return DriverManager.getConnection(URL, USUARIO, SENHA);
}
}
