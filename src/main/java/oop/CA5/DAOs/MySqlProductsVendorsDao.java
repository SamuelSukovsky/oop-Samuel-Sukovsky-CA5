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
            String query = "SELECT ProductID, ProductName FROM PRODUCTS JOIN PRODUCTSVENDORS USING ProductID WHERE VendorID = ?";
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
            String query = "SELECT VendorID, VendorName FROM VENDORS JOIN PRODUCTSVENDORS USING VendorID WHERE ProductID = ?";
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
}
