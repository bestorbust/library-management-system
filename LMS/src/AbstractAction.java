/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import java.sql.*;
public abstract class AbstractAction implements Action{
    protected Connection connection;
    
    public AbstractAction(Connection connection){
        this.connection=connection;
    }

    

   
    
}
