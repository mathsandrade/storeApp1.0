/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.CreditCard;
import Models.Sale;
import Models.Store;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Matheus
 */
public class SaleDAO {
    
    private Connection connection;
        
    public SaleDAO(Connection conn){
         this.connection = conn;
    }
    
    public Sale construir(ResultSet result) throws SQLException{
        Sale sale = new Sale();
        
       sale.setValue(result.getDouble("total"));
       sale.setCreditCard(new CreditCardDAO(connection).select(new CreditCard(){{
           setId(result.getInt("credit_card_id"));
       }}));
       sale.setStore(new StoreDAO(connection).select(new Store(){{
           setId(result.getInt("store_id"));
       }}));
       
        return sale;
    }
    
    public List<Sale> getSales(Date initialDate, Date endDate){
        PreparedStatement command ;
        String sql = "select SUM(t1.value) AS total, t2.name, t3.name, t1.credit_card_id, t1.store_id FROM sale t1 INNER JOIN credit_card t2 ON t1.credit_card_id = t2.id INNER JOIN store t3 \n" +
        "on t1.store_id = t3.id WHERE t1.date BETWEEN ? AND ? GROUP BY t2.name, t3.name, t1.credit_card_id, t1.store_id ORDER BY t3.name";
        List<Sale> sales = new ArrayList<>();
        
        try{
            connection.setAutoCommit(false);
            command = connection.prepareStatement(sql);
            command.setTimestamp(1, new Timestamp(initialDate.getTime()));
            command.setTimestamp(2, new Timestamp(endDate.getTime()));
            ResultSet result = command.executeQuery();
            
            while (result.next()){
                Sale sale = construir(result); 
                sales.add(sale);
            }
            connection.commit();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return sales;
    }
    
}
