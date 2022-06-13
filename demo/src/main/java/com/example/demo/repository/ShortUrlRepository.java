package com.example.demo.repository;

import com.example.demo.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<Url, Integer> {

    
}
