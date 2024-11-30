# sistemaBancario
Objetiva: Garantir que múltiplas transações sejam realizadas sem inconsistências no saldo


## Descrição
Aplicação básica para gerenciamento de contas bancárias, incluindo criação de contas, listagem e transferências.

## Requisitos
- Java 8
- Maven
- PostgreSQL

## Configuração
1. Configure o banco de dados PostgreSQL:
   ```sql
   CREATE DATABASE sistema_bancario;
2. Baixe o projeto
   git clone <url_do_repositorio>
   cd sistemaBancario

3. Configure o arquivo src/main/resources/application.properties com as credenciais corretas:
    spring.datasource.username=<seu_usuario>
    spring.datasource.password=<sua_senha>

4. Instale as dependências e compile o projeto
   mvn clean package

5. Execute o sistema_bancario
   mvn spring-boot:run

6. Acesse o sistema no navegador em
   http://localhost:8080

   http://localhost:8080/contas/criar
	http://localhost:8080/contas
   http://localhost:8080/contas/transferir

7. Teste
   mvn test
