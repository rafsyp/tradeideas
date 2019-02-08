package com.tradeideas.service;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.tradeideas.domain.Trade;
import com.tradeideas.domain.User;
import com.tradeideas.repositories.TradeRepository;
import com.tradeideas.repositories.UserRepository;

/**
 * Business service to handle trade object crud operations.
 */

@Service
public class TradeService {

	@Autowired
	private TradeRepository tradeRepo;

	@Autowired
	private UserRepository userRepo;
		
	/**
	 * This method is used by the Dashboard controller to get the list of trades from the logged in user.
	 * The userrepo finds all trades from the logged in user and saves them in the "trades" variable.
	 * The "list" variable is then created with a sublist from the trades variable.
	 * The sublist is then returned as a page of trades.
	 * 
	 * @param bookpage  The completed page of trade objects sent to the dashboard controller to be displayed.
	 */

	public Page<Trade> findPaginated(User user, Pageable pageable) {
		List<Trade> trades = tradeRepo.findByUser(user);
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Trade> list;

		if (trades.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, trades.size());
			list = trades.subList(startItem, toIndex);
		}

		Page<Trade> bookPage = new PageImpl<Trade>(list, PageRequest.of(currentPage, pageSize), trades.size());

		return bookPage;
	}

	public Trade findbyId(long id) {
		return tradeRepo.findById(id).get();
	}
	
	public void deleteTradebyID(long id) {
		Trade trade = tradeRepo.getOne(id);
		trade.setUser(null);
		tradeRepo.save(trade);
		tradeRepo.deleteById(id);
	}
	
	public void deleteTrade(Trade trade) {
		tradeRepo.delete(trade);
	}

	public Trade save(User user, Trade trade) {
		trade.setUser(user);
		return tradeRepo.save(trade);
	}

	public Trade update(User user, Trade trade) {
		trade.setUser(user);
		return tradeRepo.save(trade);
	}

	public Trade saveApiTrade(Trade trade) {
		return tradeRepo.save(trade);
	}

	public List<Trade> getallTrades() {
		return tradeRepo.findAll();
	}

}
