package oop.CA5.DAOs;

import oop.CA5.DTOs.Vendor;
import oop.CA5.Exceptions.DaoException;

import java.util.List;

public interface VendorDaoInterface
{
    public List<Vendor> getAllVendors() throws DaoException;

    public Vendor getVendorById(int vendorId) throws DaoException;

    public Vendor getVendorByName(String vendorName) throws DaoException;

    public void deleteVendor(int vendorId) throws DaoException;

    public void updateVendorById(int id, Vendor updatedVendor) throws DaoException;
}

