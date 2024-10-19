package com.backfcdev.companiescrud.repositories;

import com.backfcdev.companiescrud.entities.Company;
import com.backfcdev.companiescrud.entities.WebSite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WebSiteRepository extends JpaRepository<WebSite, Long> {

    Optional<Company> findByName(String name);
}
