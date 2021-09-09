package cool.sodo.user.service;

import cool.sodo.common.base.domain.Address;

import java.util.List;

public interface AddressService {

    void insertAddress(Address address);

    void updateAddress(Address address);

    void removeAddress(String addressId);

    Address getAddress(String addressId);

    Address getDefaultAddressNullable(String userId);

    Address getAddressBase(String addressId);

    Address getAddressIdentity(String addressId);

    Address getAddressIdentityNullable(String addressId);

    List<Address> listAddressBase(String userId);

}
