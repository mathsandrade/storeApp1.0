/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Store;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Matheus
 */
public class StoreDAO {
    
    private Connection connection;
    
    public StoreDAO(Connection conn){
        this.connection = conn;
    }
    
    public Store construir(ResultSet result) throws SQLException{
        Store store = new Store();
        
        store.setId(result.getInt("id"));
        store.setName(result.getString("name"));
        
        return store;
    }
    
    public Store select(Store store){
        PreparedStatement command;
        String sql = "SELECT id, name FROM store WHERE id = ?";
        Store storeBD = null;
        
        try {
            
            connection.setAutoCommit(false);
            command = connection.prepareStatement(sql);
            command.setInt(1, store.getId());
            ResultSet result = command.executeQuery();
            
            if (result.next()){
                storeBD = construir(result);
            }
            connection.commit();
     
        } catch(SQLException e){
            e.printStackTrace();
        }
        return storeBD;
    }
}
