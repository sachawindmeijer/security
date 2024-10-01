package com.example.fullsecurity.repositories;

import com.example.fullsecurity.models.Authority;
import com.example.fullsecurity.models.CIModule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository   extends JpaRepository<Authority, String> {
    boolean existsAuthorityByAuthority(String Author);

}
