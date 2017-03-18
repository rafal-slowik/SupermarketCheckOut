/**
 * 
 */
package market;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import market.item.Item;
import market.rule.IPriceRule;

/**
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public class CashRegisterService {

	private List<Item> basket = new ArrayList<>();
	private List<IPriceRule> specialOffers = new ArrayList<>();
	private BigDecimal total = BigDecimal.ZERO;
	private BigDecimal totalDiscount = BigDecimal.ZERO;


	private void addToTotal(BigDecimal toAdd) {
		total = total.add(toAdd);
	}
	
	private void addToTotalDiscount(BigDecimal addDiscount) {
		totalDiscount = totalDiscount.add(addDiscount);
	}
	
	/**
	 * Apply all price rules to the basket
	 * @return recipe
	 */
	public void appyPriceRules() {
		Optional.ofNullable(specialOffers).orElse(new ArrayList<>()).stream()
				.forEach(rule -> rule.applyPriceRule(basket));
	}

	/**
	 * Generate recipe from current basket.
	 * @return recipe
	 */
	public String printRecipe() {
		AtomicInteger line = new AtomicInteger(1);
		StringBuilder builder = new StringBuilder();
		Utils.printHeader(builder);
		Optional.ofNullable(basket).orElse(new ArrayList<>()).stream().sorted(Comparator.comparing(Item::getId))
				.forEach(item -> {
					Utils.printLine(item, builder, line.incrementAndGet());
					addToTotal(item.getRealPrice());
					addToTotalDiscount(item.getDiscount());
				});
		Utils.printFooter(builder, total, totalDiscount);
		return builder.toString();
	}

	/**
	 * Before print recipe all price rules will be applied.
	 * @return recipe
	 */
	public String appyPriceRulesAndPrintRecipe() {
		appyPriceRules();
		return printRecipe();
	}

	/**
	 * @return the basket with the items
	 */
	public List<Item> getBasket() {
		return basket;
	}

	/**
	 * @return the specialOffers
	 */
	public List<IPriceRule> getSpecialOffers() {
		return specialOffers;
	}

	/**
	 * Add new price rule
	 * @param rule
	 */
	public void addSpecialOffer(IPriceRule rule) {
		specialOffers.add(rule);
	}

	/**
	 * Add new {@link Item} to the basket.
	 * @param item - item to add
	 */
	public void addItemToBasket(Item item) {
		basket.add(item);
	}
}
