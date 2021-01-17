package com.example.demo.repository;

import com.example.demo.domain.EventRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRequestRepository
    extends JpaRepository<EventRequest, String>, QuerydslPredicateExecutor<EventRequest> {
}
