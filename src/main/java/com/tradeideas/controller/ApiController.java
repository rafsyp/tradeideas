package com.tradeideas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tradeideas.domain.Trade;
import com.tradeideas.domain.User;
import com.tradeideas.service.TradeService;

@Controller
public class ApiController extends BaseController {

	@Autowired
	private TradeService tradeservice;

	/**
	 * This endpoint is used by rest clients to upload trade ideas from an automated trade
	 * scanning bot with basic auth.
	 */

	@RequestMapping(value = "/api", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Trade> createTradeFromApi(@RequestBody Trade trade, @AuthenticationPrincipal User user) {
		logger.info("> added trade idea from api");

		Trade savedTrade = tradeservice.save(user, trade);

		logger.info("< added trade idea from api");
		return new ResponseEntity<Trade>(savedTrade, HttpStatus.CREATED);
	}
	
	/**
	 * This endpoint is used by dashboard.html to retrieve a list of trades and pass it to Datatables.
	 */
	
	@RequestMapping(value = "/apilist", method = RequestMethod.GET)
	@ResponseBody 
	public List<Trade> getTradeList(@AuthenticationPrincipal User user) {
		logger.info("> getting list of all trades from API");
		return tradeservice.getallTradesbyUser(user);
	}
	
	/**
	 * Old form for adding a trade with addtrade.html
	 */

	@GetMapping("/addtrade")
	public String getTrades(ModelMap model) {
		model.put("trade", new Trade());
		return "addtrade";
	}

	/**
	 * Old form for adding a trade with addtrade.html
	 */

	@PostMapping("/addtrade")
	public String createTrade(@AuthenticationPrincipal User user, Trade trade) {

		logger.info("> added trade idea from addtrade form");

		tradeservice.save(user, trade);

		logger.info("< added trade idea from addtrade form");
		return "redirect:/dashboard";
	}
	
}
