package com.driver.services.impl;

import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;

    @Override
    public Admin register(String username, String password)
    {
        Admin admin=new Admin(username,password);
        adminRepository1.save(admin);
        return admin;
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName)
    {
        Admin admin=adminRepository1.findById(adminId).get();
        if(admin==null)
        {
            return null;
        }
        ServiceProvider serviceProvider=new ServiceProvider(providerName);
        admin.getServiceProviders().add(serviceProvider);
        serviceProvider.setAdmin(admin);

        adminRepository1.save(admin);

        return admin;
    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception
    {
        ServiceProvider serviceProvider=serviceProviderRepository1.findById(serviceProviderId).orElse(null);

        if(serviceProvider==null)
        {
            throw new Exception("No Service Provider is Present at this id");
        }

       if(!CountryName.values().toString().contains(countryName))
       {
           throw new Exception("Country Not Found");
       }

        CountryName countryName1= new Country(CountryName.valueOf(countryName)).getCountryName();


        String code=countryName1.toCode();

        Country country=new Country(countryName1);
        country.setCode(code);

        serviceProvider.getCountryList().add(country);

        return serviceProvider;
    }
}
