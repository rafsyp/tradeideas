package com.tradeideas.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tradeideas.domain.Trade;
import com.tradeideas.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusinessServiceTest {

	@Autowired
	private UserService userService;
	@Autowired
	private TradeService tradeService;

	@BeforeAll
	public void initDb() {

		User newUser = new User();
		newUser.setName("test");
		newUser.setUsername("bob@gmail.com");
		newUser.setPassword("test");
		userService.save(newUser);

		Trade trade = new Trade();
		trade.setTicker("aapl");
		trade.setTradetype("long");
		tradeService.save(newUser, trade);
	}

	@Test
	public void testUser() {
		User user = userService.findbyUsername("bob@gmail.com");
		assertNotNull(user);
	}

	@Test
	public void testTrade() {
		User user = userService.findbyUsername("bob@gmail.com");
		List<Trade> trades = tradeService.getallTradesbyUser(user);
		assertNotNull(trades);

	}

}