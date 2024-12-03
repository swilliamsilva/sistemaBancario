package com.sistema.bancario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootTest
@ActiveProfiles("test")
public class DatabaseConnectionTest {

    private static final Logger logger = LogManager.getLogger(DatabaseConnectionTest.class);

    @Autowired
    private DataSource dataSource;

    @Test
    public void testDatabaseConnectionAndTableCreation() {
        try (Connection connection = dataSource.getConnection()) {
            logger.info("==================================");
            logger.info("Testando conexão com o banco de dados...");
            logger.info("==================================");

            if (connection.isValid(2)) {
                logger.info("Conexão estabelecida com sucesso!");
            } else {
                logger.error("Falha ao conectar ao banco de dados!");
                return;
            }

            DatabaseMetaData metaData = connection.getMetaData();

            // Verificar ou criar a tabela "cad_usuarios"
            checkAndCreateTable(connection, metaData, "cad_usuarios",
                    "CREATE TABLE cad_usuarios (" +
                    "id SERIAL PRIMARY KEY, " +
                    "conta_cliente VARCHAR(50) NOT NULL UNIQUE, " +
                    "nome_cliente VARCHAR(255) NOT NULL, " +
                    "senha VARCHAR(255) NOT NULL, " +
                    "usuario VARCHAR(50) NOT NULL UNIQUE" +
                    ")");

            // Verificar ou criar a tabela "conta"
            checkAndCreateTable(connection, metaData, "conta",
                    "CREATE TABLE conta (" +
                    "id SERIAL PRIMARY KEY, " +
                    "titular VARCHAR(255) NOT NULL, " +
                    "numero_conta VARCHAR(8) NOT NULL UNIQUE, " +
                    "saldo NUMERIC(10, 2) NOT NULL, " +
                    "saldo_especial NUMERIC(10, 2) DEFAULT 0.0, " +
                    "situacao_conta VARCHAR(50) DEFAULT 'Ativa', " +
                    "data_hora_abertura TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL" +
                    ")");

            // Verificar ou criar a tabela "transacao"
            checkAndCreateTable(connection, metaData, "transacao",
                    "CREATE TABLE transacao (" +
                    "id SERIAL PRIMARY KEY, " +
                    "conta_id BIGINT NOT NULL, " +
                    "data_hora_transacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "tipo_transacao VARCHAR(10) NOT NULL, " +
                    "valor NUMERIC(10, 2) NOT NULL, " +
                    "valor_usado_especial NUMERIC(10, 2) DEFAULT 0.0, " +
                    "FOREIGN KEY (conta_id) REFERENCES conta(id)" +
                    ")");

            // Teste de consulta simples
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT 1")) {
                if (resultSet.next()) {
                    logger.info("Conexão testada com sucesso! Retorno: {}", resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao conectar ou criar tabelas no banco de dados: ", e);
        }
    }

    private void checkAndCreateTable(Connection connection, DatabaseMetaData metaData, String tableName,
                                     String createTableSQL) {
        try {
            logger.info("Verificando existência da tabela: {}", tableName);
            boolean tableExists;
            try (ResultSet tables = metaData.getTables(null, null, tableName, null)) {
                tableExists = tables.next();
            }

            if (tableExists) {
                logger.info("Tabela '{}' já existe. Nenhuma ação necessária.", tableName);
            } else {
                logger.warn("Tabela '{}' não encontrada. Criando tabela...", tableName);
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(createTableSQL);
                    logger.info("Tabela '{}' criada com sucesso!", tableName);
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao verificar ou criar a tabela '{}': ", tableName, e);
        }
    }
}
