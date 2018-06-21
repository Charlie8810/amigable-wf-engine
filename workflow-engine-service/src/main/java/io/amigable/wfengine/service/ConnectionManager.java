package io.amigable.wfengine.service;


import io.amigable.wfengine.service.data.Parameter;
import io.amigable.wfengine.service.data.Parameters;
import io.amigable.wfengine.service.data.eParameterType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ConnectionManager {

    private Connection dbConnection = null;
    private Statement statement = null;
    private PreparedStatement pStatement = null;

    public ConnectionManager() throws SQLException{
        this.dbConnection = this.doConnect();
    }

    private Connection doConnect() throws  SQLException{
        return DriverManager.getConnection("jdbc:mysql://192.168.10.10/amigable_wf?" +
                "user=usr_amigable&password=amigable.01&useSSL=false");
    }

    public ResultSet executeQuery(String query) throws SQLException{
        this.statement = this.dbConnection.createStatement();
        return statement.executeQuery(query);
    }


    public ResultSet executeProcedure(String pname, List<Parameter> params) throws SQLException{

        String query = "CALL " + pname +"(";

        for (Parameter p : params) {
            switch (p.getType()){
                case DEFAULT:
                    query=query +"'"+p.getValue() + "',";
                    break;
                case BOOLEAN:
                    query=query + p.getValue();
                    break;
                case INTEGER:
                    query=query + p.getValue();
                    break;
                case FLOAT:
                    query=query + p.getValue();
                    break;
                case DECIMAL:
                    query=query + p.getValue();
                    break;
                case DOUBLE:
                    query=query + p.getValue();
                    break;
                case VARCHAR:
                    query=query +"'"+p.getValue() + "',";
                    break;
                case TEXT:
                    query=query +"'"+p.getValue() + "',";
                    break;
                case DATETIME:
                    query=query +"'"+p.getValue() + "',";
                    break;
                case DATE:
                    query=query +"'"+p.getValue() + "',";
                    break;
                case TIME:
                    query=query +"'"+p.getValue() + "',";
                    break;
            }
        }

        query = query.substring(0, query.lastIndexOf(",")) + ")";

        this.statement = this.dbConnection.createStatement();
        return statement.executeQuery(query);
    }

    public void doClose() throws SQLException{
        this.dbConnection.close();
    }
}

