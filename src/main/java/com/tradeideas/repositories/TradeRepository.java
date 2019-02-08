package com.tradeideas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tradeideas.domain.Trade;
import com.tradeideas.domain.User;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long>{ 

}
