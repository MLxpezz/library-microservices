package com.library.loans_microservice.repository;

import com.library.loans_microservice.entity.LoanHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanHistoryRepository extends JpaRepository<LoanHistoryEntity, Long> {

}
