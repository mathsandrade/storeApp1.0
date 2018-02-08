/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.CreditCard;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Matheus
 */
public class CreditCardDAO {
    
    private Connection connection;
    
    public CreditCardDAO(Connection conn){
        this.connection = conn;
    }
    
    public CreditCard construir(ResultSet result) throws SQLException{
        CreditCard creditCard = new CreditCard();
        
        creditCard.setId(result.getInt("id"));
        creditCard.setName(result.getString("name"));
        
        return creditCard;
    }
    
    public CreditCard select(CreditCard creditCard){
        PreparedStatement command;
        String sql = "SELECT id, name FROM credit_card WHERE id = ?";
        CreditCard creditCardBD = null;
        
        try{
            
            connection.setAutoCommit(false);
            command = connection.prepareStatement(sql);
            command.setInt(1, creditCard.getId());
            ResultSet result = command.executeQuery();
            
            if(result.next()){
                creditCardBD = construir(result);
            }
            
            connection.commit();
        } catch(SQLException e){
            e.printStackTrace();
        }
        return creditCardBD;
    }
}
