package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager implements AutoCloseable {

    private static final String URL = "jdbc:mysql://localhost:3306/financas";
    private static final String USER = "root"; 
    private static final String PASSWORD = "root"; 

    private Connection connection;

    public DatabaseManager() throws SQLException {
        try {
            // Carrega o driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Estabelece a conexão com o banco de dados
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não encontrado.", e);
        }
    }

    public void inserirConta(double salarioBruto, double valeRefeicao, double valeTransporte,
                             double valeAlimentacao, double planoSaudeDependente, int numeroDependentes) throws SQLException {
        String sql = "INSERT INTO conta (salario_bruto, vale_refeicao, vale_transporte, vale_alimentacao, plano_saude_dependente, numero_dependentes) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, salarioBruto);
            stmt.setDouble(2, valeRefeicao);
            stmt.setDouble(3, valeTransporte);
            stmt.setDouble(4, valeAlimentacao);
            stmt.setDouble(5, planoSaudeDependente);
            stmt.setInt(6, numeroDependentes);
            stmt.executeUpdate();
        }
    }

    public ResultSet obterContas() throws SQLException {
        String sql = "SELECT * FROM conta";
        PreparedStatement stmt = connection.prepareStatement(sql);
        return stmt.executeQuery();
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
