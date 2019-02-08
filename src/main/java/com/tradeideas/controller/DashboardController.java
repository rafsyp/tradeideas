package com.tradeideas.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tradeideas.domain.Trade;
import com.tradeideas.domain.User;
import com.tradeideas.service.TradeService;

@Controller
public class DashboardController extends BaseController {

	@Autowired
	private TradeService tradeservice;
	
    /**
     * Web service endpoint to fetch all trades from the logged in user. This method populates a page object with a list of trades
     * fetched by the tradeservice autowired member variable.  The page is added to a model which is used by dashboard.html
     * to display the list of trades to the user. 
     * 
     * @param  model  the list of trade objects that will be built up and passed over to thymeleaf and displayed to the user
     * @param  user  the user currently logged in
     * @param  page  page object with list of trades created in dashboard.html found in resources/templates.
     * @param  size  the number of trades per page created in dashboard.html found in resources/templates.
     * @return returns the dashboard.html page found in resources/templates.
     */

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String listTrades(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, @AuthenticationPrincipal User user) {
		logger.info("> getlistTrades");
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);

		Page<Trade> bookPage = tradeservice.findPaginated(user, PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("bookPage", bookPage);

		int totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		logger.info("< getlistTrades");
		return "dashboard";
	}

	
    /**
     * Method that saves and updates trades from the edit and new button on dashboard.html found in resources/templates
     * 
     * @param  user  the user currently logged in
     * @param  trade the trade object being passed in from dashboard.html
     * @return returns the dashboard.html page found in resources/templates.
     */
	
	@PostMapping("/save")
	public String save(@AuthenticationPrincipal User user, Trade trade) {
		logger.info("> save");
		tradeservice.save(user, trade);
		logger.info("< save");
		return "redirect:/dashboard";
	}
	
    /**
     * Method that deletes trades from the delete button in dashboard.html found in resources/templates
     * 
     * @param  user  the user currently logged in
     * @param  id  the id of the trade to be deleted
     * @return returns the dashboard.html page found in resources/templates.
     */

	@GetMapping("/delete")
	public String delete(Long id) {
		logger.info("> deleted trade with id: " + id );
		tradeservice.deleteTradebyID(id);
		return "redirect:/dashboard";
	}
	
    /**
     * Method that finds a trade by id when called by the modal edit form found in dashboard.html found in resources/templates
     * 
     * @param  id  the id of the trade to be searched
     * @return returns the trade object that was selected in the edit form
     */

	@RequestMapping(value = "/findOne", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Trade find(long id) {
		logger.info("> find" + id);
		return tradeservice.findbyId(id);

	}

}
