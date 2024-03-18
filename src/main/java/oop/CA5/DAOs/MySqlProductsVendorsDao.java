package oop.CA5.DAOs;

import oop.CA5.DTOs.Product;
import oop.CA5.DTOs.Vendor;
import oop.CA5.Exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlProductsVendorsDao extends MySqlDao implements ProductsVendorsDaoInterface
{
    /**
     * Main author: Samuel Sukovský
     *
     */
    @Override
    public List<Product> getProductsSoldByVendorId(int vendorId) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> productList = new ArrayList<>();

        try
        {
            connection = this.getConnection();
            String query = "SELECT ProductID, ProductName FROM PRODUCTS JOIN PRODUCTSVENDORS USING(ProductID) WHERE VendorID = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1,vendorId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                int productId = resultSet.getInt("ProductID");
                String productName = resultSet.getString("ProductName");
                Product p = new Product(productId,productName);
                productList.add(p);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("getProductsSoldByVendorId() " + e.getMessage());
        }
        finally
        {
            try
            {
                if(resultSet != null)
                {
                    resultSet.close();
                }
                if(preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if(connection != null)
                {
                    freeConnection(connection);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("getProductsSoldByVendorId() " + e.getMessage());
            }
        }
        return productList;
    }

    /**
     * Main author: Samuel Sukovský
     *
     */

    @Override
    public List<Vendor> getVendorsSellingProductId(int productId) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Vendor> vendorList = new ArrayList<>();

        try
        {
            connection = this.getConnection();
            String query = "SELECT VendorID, VendorName FROM VENDORS LEFT JOIN PRODUCTSVENDORS USING(VendorID) WHERE ProductID = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, productId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                int vendorId = resultSet.getInt("VendorId");
                String vendorName = resultSet.getString("VendorName");
                Vendor v = new Vendor(vendorId, vendorName);
                vendorList.add(v);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("getVendorsSellingProductId() " + e.getMessage());
        }
        finally
        {
            try
            {
                if(resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("getVendorsSellingProductId() " + e.getMessage());
            }
        }
        return vendorList;
    }

    /**
     * Main author: Samuel Sukovský
     *
     */
    @Override
    public Product getCheapestProductSoldByVendor(int vendorId) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Product product = null;

        try
        {
            connection = this.getConnection();
            String query = "SELECT ProductID, ProductName FROM PRODUCTS JOIN PRODUCTSVENDORS USING(ProductID) WHERE VendorID = ? ORDER BY Price ASC";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, vendorId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                int productId = resultSet.getInt("ProductID");
                String productName = resultSet.getString("ProductName");

                product = new Product(productId, productName);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("getCheapestProductSoldByVendor() " + e.getMessage());
        }
        finally
        {
            try
            {
                if(resultSet != null)
                {
                    resultSet.close();
                }
                if(preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if(connection != null)
                {
                    freeConnection(connection);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("getCheapestProductSoldByVendor() " + e.getMessage());
            }
        }
        return product;
    }

    /**
     * Main author: Samuel Sukovský
     *
     */
    @Override
    public Vendor getVendorSellingProductForCheapest(int productId) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Vendor vendor = null;

        try
        {
            connection = this.getConnection();
            String query = "SELECT VendorID, VendorName FROM VENDORS JOIN PRODUCTSVENDORS USING(VendorID) WHERE ProductID = ? ORDER BY Price ASC";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, productId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                int vendorId = resultSet.getInt("VendorID");
                String vendorName = resultSet.getString("VendorName");

                vendor = new Vendor(vendorId, vendorName);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("getVendorSellingProductForCheapest() " + e.getMessage());
        }
        finally
        {
            try
            {
                if(resultSet != null)
                {
                    resultSet.close();
                }
                if(preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if(connection != null)
                {
                    freeConnection(connection);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("getVendorSellingProductForCheapest() " + e.getMessage());
            }
        }
        return vendor;
    }

    /**
     * Main author: Samuel Sukovský
     *
     */

    @Override
    public void deleteByProductID(int productId) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = this.getConnection();
            String query = "DELETE FROM PRODUCTSVENDORS WHERE ProductID = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DaoException("deleteByProductID() " + e.getMessage());
        }
        finally
        {
            try
            {
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("getVendorsSellingProductId() " + e.getMessage());
            }
        }
    }

    /**
     * Main author: Samuel Sukovský
     *
     */

    @Override
    public void deleteByVendorID(int vendorId) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = this.getConnection();
            String query = "DELETE FROM PRODUCTSVENDORS WHERE VendorID = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, vendorId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DaoException("deleteByVendorID() " + e.getMessage());
        }
        finally
        {
            try
            {
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("getVendorsSellingProductId() " + e.getMessage());
            }
        }
    }

    /**
     * Main author: Aleksandra Kail
     *
     */

    @Override
    public void updateProductsVendorsById(int pId, int vId, double price, int quantity) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.getConnection();

            String query = "UPDATE productsvendors SET price = ?, quantity = ? WHERE productID = ? AND vendorID = ? ";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setDouble(1, price);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setInt(3, pId);
            preparedStatement.setInt(4, vId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new DaoException("No matching record found for product ID " + pId + " and vendor ID: " + vId);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("updateProductsVendors() " + e.getMessage());
        }
        finally
        {
            try
            {
                if(preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if(connection != null)
                {
                    freeConnection(connection);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("updateProductsVendors() " + e.getMessage());
            }
        }
    }
}
