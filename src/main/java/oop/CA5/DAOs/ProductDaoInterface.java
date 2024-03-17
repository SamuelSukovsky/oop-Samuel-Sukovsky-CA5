package oop.CA5.DAOs;

/** OOP Feb 2022
 * UserDaoInterface
 *
 * Declares the methods that all UserDAO types must implement,
 * be they MySql User DAOs or Oracle User DAOs etc...
 *
 * Classes from the Business Layer (users of this DAO interface)
 * should use reference variables of this interface type to avoid
 * dependencies on the underlying concrete classes (e.g. MySqlUserDao).
 *
 * More sophisticated implementations will use a factory
 * method to instantiate the appropriate DAO concrete classes
 * by reading database configuration information from a
 * configuration file (that can be changed without altering source code)
 *
 * Interfaces are also useful when testing, as concrete classes
 * can be replaced by mock DAO objects.
 */

import oop.CA5.DTOs.Product;
import oop.CA5.DTOs.Vendor;
import oop.CA5.Exceptions.DaoException;

import java.util.List;

public interface ProductDaoInterface
{
    public List<Product> getAllProducts() throws DaoException;

    public Product getProductById(int productId) throws DaoException;

    public Product getProductByName(String productName) throws DaoException;

    public void deleteProduct(int productId) throws DaoException;

}

