/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.SaleDAO;
import Data.Pool;
import Models.Sale;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Matheus
 */
public class SaleService {
    
    private Connection connection;

    public SaleService() {
    }
    
    public void printReport(Date initialDate, Date endDate){
            
            SaleDAO saleDAO = getDao();         
            //Initializing the attributes that will be used as total of each store
            double totalStore1 = 0;
            double totalStore2 = 0;
            double totalStore3 = 0;
            //Initializing the list attributes
            List<Double> sales1 = new ArrayList<>(); 
            List<Double> sales2 = new ArrayList<>(); 
            List<Double> sales3 = new ArrayList<>(); 
            //Passing the DB result to a list type object
            List<Sale> sales = saleDAO.getSales(initialDate, endDate);
            //Running through the list
            for (Sale sale : sales) {
                if("loja1".equalsIgnoreCase(sale.getStore().getName())){
                    totalStore1 += sale.getValue();
                    sales1.add(sale.getValue());
                }else if("loja2".equalsIgnoreCase(sale.getStore().getName())){
                    totalStore2 += sale.getValue();
                    sales2.add(sale.getValue());
                }else{
                    totalStore3 += sale.getValue();;
                    sales3.add(sale.getValue());
                }
            }
            //Adding the total of each store in your respective list
            sales1.add(totalStore1);
            sales2.add(totalStore2);
            sales3.add(totalStore3);
            //Reporting
            System.out.println("-----------Report--------------");
            System.out.println("Store [Elo, Mastercard, Visa, Total]"); 
            System.out.println("Loja 1"+sales1);
            System.out.println("Loja 2"+sales2);
            System.out.println("Loja 3"+sales3);
    }
      
    public SaleDAO getDao() {
        connection = Pool.get();
        return new SaleDAO(connection);
    }

    public void releaseDao(SaleDAO dao) {
        if (dao != null) {
            if (connection != null) {
                Pool.release(connection);
            }
            dao = null;
        }
    }
}
