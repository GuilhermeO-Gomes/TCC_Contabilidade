package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
  private static String configurar(String propriedade, String ambiente, String padrao) {
    return System.getProperty(propriedade, System.getenv().getOrDefault(ambiente, padrao));
  }

  public static Connection conectar() throws SQLException {
    return DriverManager.getConnection(
        configurar(
            "erp.db.url",
            "ERP_DB_URL",
            "jdbc:mysql://localhost:3306/erp_contabil?connectTimeout=5000&socketTimeout=15000"),
        configurar("erp.db.usuario", "ERP_DB_USUARIO", "root"),
        configurar("erp.db.senha", "ERP_DB_SENHA", ""));
  }
}
