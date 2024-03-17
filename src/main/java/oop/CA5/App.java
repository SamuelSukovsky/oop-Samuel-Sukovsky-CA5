package oop.CA5;



import oop.CA5.DAOs.*;
import oop.CA5.DTOs.Product;
import oop.CA5.DTOs.Vendor;
import oop.CA5.Exceptions.DaoException;

import java.util.List;

public class App
{
    public static void main(String[] args)
    {
        ProductDaoInterface IProductDao = new MySqlProductDao();
        VendorDaoInterface IVendorDao = new MySqlVendorDao();
        ProductsVendorsDaoInterface IProductsVendorsDao = new MySqlProductsVendorsDao();

        try
        {
            /*
            System.out.println("\nCall getAllVendors()");
            List<Vendor> vendors = IVendorDao.getAllVendors();

            if( vendors.isEmpty() )
                System.out.println("There are no Vendors");
            else {
                for (Vendor vendor : vendors)
                    System.out.println("Vendor: " + vendor.toString());
            }

            System.out.println("\nCall: getVendorById()");
            int id = 1;
            Vendor vendor = IVendorDao.getVendorById(id);

            if( vendor != null )
                System.out.println("Vendor id: " + id + " was found: " + vendor);
            else
                System.out.println("Vendor id: " + id + " is not valid.");

            id = 4;
            vendor = IVendorDao.getVendorById(id);
            if(vendor != null)
                System.out.println("Vendor id: " + id + " was found: " + vendor);
            else
                System.out.println("Vendor id: " + id + " is not valid.");
            */

            /*
            System.out.println("\nCall getAllProducts()");
            List<Product> products = IProductDao.getAllProducts();     // call a method in the DAO

            if( products.isEmpty() )
                System.out.println("There are no Products");
            else {
                for (Product product : products)
                    System.out.println("Product: " + product.toString());
            }

            System.out.println("\nCall: findVendorById()");
            int id = 1;
            Product product = IProductDao.getProductById(id);

            if( product != null )
                System.out.println("Product id: " + id + " was found: " + product);
            else
                System.out.println("Product id: " + id + " is not valid.");

            id = 11;
            product = IProductDao.getProductById(id);
            if(product != null)
                System.out.println("Product id: " + id + " was found: " + product);
            else
                System.out.println("Product id: " + id + " is not valid.");
             */

            // IProductDao.deleteProduct(1);
            // IVendorDao.deleteVendor(1);

            /*
            System.out.println("\nCall: getVendorsSellingProductId()");
            int id = 2;
            List<Vendor> vendors = IProductsVendorsDao.getVendorsSellingProductId(id);

            if( vendors.isEmpty() )
                System.out.println("There are no vendors selling product " + id);
            else {
                for (Vendor vendor : vendors)
                    System.out.println("Vendor: " + vendor.toString());
            }

            System.out.println("\nCall: getProductsSoldByVendorId()");
            id = 2;
            List<Product> products = IProductsVendorsDao.getProductsSoldByVendorId(id);

            if( products.isEmpty() )
                System.out.println("There are no products sold by vendor " + id);
            else {
                for (Product product : products)
                    System.out.println("Product: " + product.toString());
            }
            */

            System.out.println("\nCall: getVendorByName()");
            String name = "Kane's Jewellery";
            Vendor vendor = IVendorDao.getVendorByName(name);

            if( vendor != null )
                System.out.println("Vendor name: " + name + " was found: " + vendor);
            else
                System.out.println("Vendor name: " + name + " is not valid.");

            name = "Necklace";
            Product product = IProductDao.getProductByName(name);
            if(product != null)
                System.out.println("Product name: " + name + " was found: " + product);
            else
                System.out.println("Product name: " + name + " is not valid.");

        }
        catch( DaoException e )
        {
            e.printStackTrace();
        }
    }
}
