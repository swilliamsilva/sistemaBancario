package com.sistema.bancario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootTest
public class DatabaseConnectionTest {
	
	

    private static final Logger logger = LogManager.getLogger(DatabaseConnectionTest.class);

   
    
    @Autowired
    private DataSource dataSource;

    @Test
    public void testDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
        	logger.info("==================================");
            logger.info("Conexão estabelecida com sucesso!");
            logger.info("==================================");

            DatabaseMetaData metaData = connection.getMetaData();

            // Verifica se a tabela "conta" existe
            boolean tabelaContaExiste = false;
            try (ResultSet tables = metaData.getTables(null, null, "conta", null)) {
                tabelaContaExiste = tables.next();
            }

            if (!tabelaContaExiste) {
            	logger.info("================================================");
                logger.warn("Tabela 'conta' não encontrada. Criando tabela...");
                logger.info("================================================");

                // Cria a tabela "conta"
                try (Statement statement = connection.createStatement()) {
                	String sql = "CREATE TABLE conta ("
                	           + "id SERIAL PRIMARY KEY, "
                	           + "titular VARCHAR(255) NOT NULL, "
                	           + "saldo NUMERIC(10, 2) NOT NULL"
                	           + ");";
                	statement.executeUpdate(sql);
                	logger.info("==================================");
                    logger.info("Tabela 'conta' criada com sucesso!");
                    logger.info("==================================");
                }
            } else {
            	logger.info("==================================================");
                logger.info("Tabela 'conta' já existe. Nenhuma ação necessária.");
                logger.info("==================================================");
            }

            // Testa a conexão executando uma simples consulta
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT 1")) {
                if (resultSet.next()) {
                	logger.info("======================================");
                    logger.info("Conexão testada com sucesso! Retorno: " + resultSet.getInt(1));
                    logger.info("======================================");
                }
            }
        } catch (SQLException e) {
        	logger.info("===========================================");
            logger.error("Erro ao conectar ao banco de dados: ", e);
            logger.info("============================================");
        }
    }
}

