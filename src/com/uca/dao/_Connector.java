
package com.uca.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class _Connector
{

    private static String   url       = "jdbc:h2:~/playPoke",
                            user      = "sa",

                            passwd    = "";
    private static Connection connect;

    public static Connection getInstance()
    {
        if(connect == null)
        {
            try
            {
                connect = DriverManager.getConnection(url, user, passwd);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return connect;
    }

}
