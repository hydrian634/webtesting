package com.webtesting.demo.repository;

import com.webtesting.demo.model.Address;
import com.webtesting.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);
    Optional<Address> findByUserAndIsDefaultTrue(User user);
    boolean existsByUserAndId(User user, Long id);
}
