/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storeapp;

import Services.SaleService;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Matheus
 */
public class StoreApp {

    public static void main(String[] args) {
        
        SaleService service = new SaleService();
        
        Date initialDate = new GregorianCalendar(2017, 00, 01).getTime();
        Date endDate = new GregorianCalendar(2017, 01, 01).getTime();
        
        service.printReport(initialDate, endDate);
    }
 
}
