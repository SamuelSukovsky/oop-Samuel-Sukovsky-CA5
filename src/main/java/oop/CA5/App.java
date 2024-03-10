package oop.CA5;



import oop.CA5.DAOs.MySqlVendorDao;
import oop.CA5.DAOs.VendorDaoInterface;
import oop.CA5.DTOs.Vendor;
import oop.CA5.Exceptions.DaoException;

import java.util.List;

public class App
{
    public static void main(String[] args)
    {
        VendorDaoInterface IVendorDao = new MySqlVendorDao();  //"IUserDao" -> "I" stands for for

//        // Notice that the userDao reference is an Interface type.
//        // This allows for the use of different concrete implementations.
//        // e.g. we could replace the MySqlUserDao with an OracleUserDao
//        // (accessing an Oracle Database)
//        // without changing anything in the Interface.
//        // If the Interface doesn't change, then none of the
//        // code in this file that uses the interface needs to change.
//        // The 'contract' defined by the interface will not be broken.
//        // This means that this code is 'independent' of the code
//        // used to access the database. (Reduced coupling).
//
//        // The Business Objects require that all User DAOs implement
//        // the interface called "UserDaoInterface", as the code uses
//        // only references of the interface type to access the DAO methods.

        try
        {
            System.out.println("\nCall findAllVendors()");
            List<Vendor> vendors = IVendorDao.getAllVendors();     // call a method in the DAO

            if( vendors.isEmpty() )
                System.out.println("There are no Vendors");
            else {
                for (Vendor vendor : vendors)
                    System.out.println("Vendor: " + vendor.toString());
            }

            // test dao - with username and password that we know are present in the database
            System.out.println("\nCall: findVendorByName()");
            int id = 1;
            Vendor vendor = IVendorDao.getVendorById(id);

            if( vendor != null ) // null returned if userid and password not valid
                System.out.println("User found: " + vendor);
            else
                System.out.println("Username with that password not found");

            // test dao - with an invalid username (i.e. not in database)
            id = 3;
            vendor = IVendorDao.getVendorById(id);
            if(vendor != null)
                System.out.println("Vendor id: " + id + " was found: " + vendor);
            else
                System.out.println("Vendor id: " + id + " is not valid.");
        }
        catch( DaoException e )
        {
            e.printStackTrace();
        }
    }
}
