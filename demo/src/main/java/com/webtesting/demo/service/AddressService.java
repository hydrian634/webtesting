package com.webtesting.demo.service;

import com.webtesting.demo.dto.AddressResponse;
import com.webtesting.demo.model.Address;
import com.webtesting.demo.model.User;
import com.webtesting.demo.repository.AddressRepository;
import com.webtesting.demo.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {
    
    private final AddressRepository addressRepository;
    
    @Transactional
    public AddressResponse createAddress(User user, Address address) {
        log.info("Creating address for user: {}", user.getId());
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);
        return convertToResponse(savedAddress);
    }
    
    public List<AddressResponse> getUserAddresses(User user) {
        log.info("Fetching addresses for user: {}", user.getId());
        return addressRepository.findByUser(user)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }
    
    public AddressResponse getAddressById(Long addressId) {
        log.info("Fetching address with id: {}", addressId);
        return addressRepository.findById(addressId)
            .map(this::convertToResponse)
            .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));
    }
    
    @Transactional
    public AddressResponse updateAddress(Long addressId, Address updatedAddress) {
        log.info("Updating address with id: {}", addressId);
        Address address = addressRepository.findById(addressId)
            .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));
        
        address.setAddress(updatedAddress.getAddress());
        address.setDetailAddress(updatedAddress.getDetailAddress());
        address.setCity(updatedAddress.getCity());
        address.setState(updatedAddress.getState());
        address.setPostalCode(updatedAddress.getPostalCode());
        address.setCountry(updatedAddress.getCountry());
        
        Address saved = addressRepository.save(address);
        return convertToResponse(saved);
    }
    
    @Transactional
    public void deleteAddress(Long addressId) {
        log.info("Deleting address with id: {}", addressId);
        addressRepository.deleteById(addressId);
    }
    
    private AddressResponse convertToResponse(Address address) {
        return AddressResponse.builder()
            .id(address.getId())
            .address(address.getAddress())
            .detailAddress(address.getDetailAddress())
            .city(address.getCity())
            .state(address.getState())
            .postalCode(address.getPostalCode())
            .country(address.getCountry())
            .isDefault(address.getIsDefault())
            .build();
    }
}
