package com.jrdlib;

import java.sql.*;
import java.util.Properties;

/**
 *
 * @author Neri N. otimizado por Rafael Daniel
 * @version 0.99
 *
 */
/**
 * Classe Conexao: utilitário de baixo nível para gerenciar conexões JDBC com diversos bancos (PostgreSQL, Firebird e Microsoft Access).
 * Fornece operações para abrir conexões, validar conectividade, desconectar e executar SQL (simples e preparados),
 * bem como recuperar ResultSet.
 * 
 * Principais características:
 * - Suporte a PostgreSQL (com e sem SSL), Firebird e Access via drivers JDBC específicos.
 * - Métodos para testar conectividade das bases.
 * - Armazena referências de conexão (static) e objetos de consulta (Statement, ResultSet, PreparedStatement).
 * - Sistema de fechamento de conexão com tratamento básico de erros.
 * 
 * Observações:
 * - A implementação usa campos estáticos e não é threadsafe.
 * - Ausência de pool de conexões; gerência manual de abertura/fechamento.
 * - Os métodos podem lançar exceções; alguns encapsulam exceções retornando false em checks de conectividade.
 */
public class Conexao {

    private SwingsAuxilios objSwingsAuxilios = new SwingsAuxilios();
    private static Connection objConection;
    private static Connection objConectionTest;
    public Statement statement;
    public ResultSet resultset;
    public PreparedStatement preparedstatement;

    
   
    
  
    /**
     * Checks whether the underlying connection is currently valid.
     *
     * This method uses the underlying connection's isValid method with a timeout of 1000 milliseconds
     * and returns true if the connection is valid within that timeout.
     *
     * @return true if the connection is valid within 1000 milliseconds; false otherwise
     * @throws Exception if an error occurs while validating the connection
     */
    public boolean checkConnection() throws Exception {

        if (objConection.isValid(1000)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Verifica se a conexão está fechada
     *
     * @return
     * @throws java.lang.Exception
     */
    public boolean isValidConnection() {

        try {
            if (objConection.isValid(1000)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Inicia conexao com o banco de dados PostgresSQl
     *
     * @param EndBd
     * @param Port
     * @param Bd
     * @param UserBd
     * @param SenhaBd
     * @throws Exception
     */
    public void conectaPostgresSQL(String EndBd, String Port, String Bd, String UserBd, String SenhaBd) throws Exception {
        /*Class.forName("org.postgresql.Driver");
        DriverManager.setLoginTimeout(5);
        objConection = DriverManager.getConnection("jdbc:postgresql://" + EndBd + ":5432/" + Bd + "", UserBd, SenhaBd);*/

        Class.forName("org.postgresql.Driver");
        DriverManager.setLoginTimeout(5);
        Properties props = new Properties();
        props.setProperty("user", UserBd);
        props.setProperty("password", SenhaBd);
        objConection = DriverManager.getConnection("jdbc:postgresql://" + EndBd + ":" + Port + "/" + Bd, props);
    }

    /**
     * Inicia conexao com o banco de dados PostgresSQl
     *
     * @param EndBd
     * @param Port
     * @param Bd
     * @param UserBd
     * @param SenhaBd
     * @throws Exception
     */
    public void conectaPostgresSQLWithSSL(String EndBd, String Port, String Bd, String UserBd, String SenhaBd) throws Exception {
        Class.forName("org.postgresql.Driver");
        Properties props = new Properties();
        props.setProperty("user", UserBd);
        props.setProperty("password", SenhaBd);
        props.setProperty("ssl", "true");
        objConection = DriverManager.getConnection("jdbc:postgresql://" + EndBd + ":" + Port + "/" + Bd, props);

    }

    /**
     * Inicia conexao com o banco de dados PostgresSQl
     *
     * @param EndBd
     * @param Port
     * @param Bd
     * @param UserBd
     * @param SenhaBd
     * @return
     */
    public boolean TestConectBdPostgresSQL(String EndBd, String Port, String Bd, String UserBd, String SenhaBd) {
        try {
            Class.forName("org.postgresql.Driver");
            DriverManager.setLoginTimeout(5);
            objConectionTest = DriverManager.getConnection("jdbc:postgresql://" + EndBd + ":" + Port + "/" + Bd + "", UserBd, SenhaBd);
            objConectionTest.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Inicia uma conexao com o banco
     *
     * @param CaminhoBd Caminho do Banco
     * @param UserBd Usuario do Banco
     * @param SenhaBd Senha do banco
     * @throws Exception
     */
    public void conectaFirebirdSQL(String CaminhoBd, String UserBd, String PassBd) throws Exception {
        Class.forName("org.firebirdsql.jdbc.FBDriver");
        DriverManager.setLoginTimeout(5);
        objConection = DriverManager.getConnection("jdbc:firebirdsql:" + CaminhoBd, UserBd, PassBd);
    }

    /**
     * Verifica se é possível a conexão com o banco
     *
     * @param CaminhoBd Caminho do Banco
     * @param UserBd Usuario do Banco
     * @param SenhaBd Senha do banco
     * @return
     */
    public boolean TestConectBdFirebirdSQL(String CaminhoBd, String UserBd, String SenhaBd) {
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            DriverManager.setLoginTimeout(5);
            objConectionTest = DriverManager.getConnection("jdbc:firebirdsql:" + CaminhoBd, UserBd, SenhaBd);
            objConectionTest.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Efetua Conexão com o Access
     *
     * @param CaminhoAccess caminho completo do banco
     */
    public void conectaBdAccess(String CaminhoAccess) throws Exception {
        //   Class.forName("com.hxtt.sql.access.AccessDriver");
        //  conexao = DriverManager.getConnection("jdbc:Access:/"+CaminhoAccess, "", "");
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        DriverManager.setLoginTimeout(5);
        objConection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + CaminhoAccess, "", "");
    }

    /**
     * Testa se é possível conectar ao arquivo do Access
     *
     * @param CaminhoAccess
     * @return boolean
     */
    public boolean TestConectBdAccess(String CaminhoAccess) {
        try {
            Class.forName("com.hxtt.sql.access.AccessDriver");
            DriverManager.setLoginTimeout(5);
            objConectionTest = DriverManager.getConnection("jdbc:Access:/" + CaminhoAccess, "", "");
            objConectionTest.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Efetua Desconexão com o Bando de Dados
     *
     * @return desconecta()
     */
    public void desconecta() {
        try {
            if (objConection != null) {
                objConection.close();
            }
        } catch (SQLException fechaBanco) {
            objSwingsAuxilios.ErrorMenssagemException("Ocoreu um erro ao tentar fechar a conexão com o banco de dados", fechaBanco);
            System.exit(0);
        }

    }

    /**
     * Executa uma Instrução SQL com retorno
     *
     * @return resultset
     */
    public void executeSQL(String SQL) throws SQLException {
        // System.out.println(SQL);
        statement = objConection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultset = statement.executeQuery(SQL);//AQUI O RESULTSET RECEBE A QUERY ULTILIZAR ESSA CLASSE PRA CONSULTAS
    }

    /**
     *
     * @param SQL
     * @throws SQLException
     */
    public void executeSQLSimples(String SQL) throws SQLException {
        statement = objConection.createStatement();
        statement.execute(SQL);//ULTILIZAR ESSA CLASSE PRA INSERT ETCS
    }

    /**
     * Executa um preparedstatement simples sem retorno de dados Apos chamar
     * esse método para executar chame preparedstatement.execute()
     *
     * @param SQL
     * @throws SQLException
     */
    public void executeSQLSimples_prepareStatement(String SQL) throws SQLException {
        preparedstatement = objConection.prepareStatement(SQL);
    }

    /**
     * Executa uma Query com retorno de dados em um preparedstatement Apos
     * chamar esse método chame executeSQL_prepareStatementExecuteQuery() para
     * executar a query e retornar os valores
     *
     * @param SQL
     * @throws SQLException
     */
    public void executeSQL_prepareStatement(String SQL) throws SQLException {
        preparedstatement = objConection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    /**
     * Para ser usado em conjunto com executeSQL_prepareStatement(SQL) Recebe um
     * preparedstatement chamdo anteriormente e retorna o resultset pronto pra
     * ser usado
     *
     * @param SQL
     * @throws SQLException
     */
    public void executeSQL_prepareStatementExecuteQuery() throws SQLException {
        resultset = preparedstatement.executeQuery();//AQUI O RESULTSET RECEBE A QUERY ULTILIZAR ESSA CLASSE PRA CONSULTAS
    }

}
