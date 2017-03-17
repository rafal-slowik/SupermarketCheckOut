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
import market.rule.impl.BuyNCheapestFree;
import market.rule.impl.BuyNForSpecial;
import market.rule.impl.BuyNItemsXGetMItemsY;
import market.rule.impl.BuyNPayM;

/**
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public class Basket {

	private List<Item> items = new ArrayList<>();
	private List<IPriceRule> specialOffers = new ArrayList<>();
	private BigDecimal total = BigDecimal.ZERO;

	public Basket() {
		// buy 3 (in a set of items) and the cheapest is free
		IPriceRule rule = new BuyNCheapestFree(new long[] { 1, 2, 3 });
		specialOffers.add(rule);
		// buy 3 (equals) items and pay for 2
		rule = new BuyNPayM(4, 3, 2);
		specialOffers.add(rule);
		// buy 2 (equals) items for a special price
		rule = new BuyNForSpecial(5, 2, new BigDecimal("1.33"));
		specialOffers.add(rule);
		// for each 3 (equals) items id 6, you get 2 items id 7 for free
		rule = new BuyNItemsXGetMItemsY(6, 7, 3, 2);
		specialOffers.add(rule);
	}

	public void appyPriceRules() {
		Optional.ofNullable(specialOffers).orElse(new ArrayList<>()).stream()
				.forEach(rule -> rule.applyPriceRule(items));
	}

	public String printRecipe() {
		AtomicInteger line = new AtomicInteger(1);
		StringBuilder builder = new StringBuilder();
		Utils.printHeader(builder);
		Optional.ofNullable(items).orElse(new ArrayList<>()).stream().sorted(Comparator.comparing(Item::getItemName))
				.forEach(item -> {
					Utils.printLine(item, builder, line.incrementAndGet());
					addToTotal(item.getRealPrice());
				});
		Utils.printFooter(builder, total);
		return builder.toString();
	}

	public void addToTotal(BigDecimal toAdd) {
		total = total.add(toAdd);
	}

	/**
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * @return the specialOffers
	 */
	public List<IPriceRule> getSpecialOffers() {
		return specialOffers;
	}

	public void addSpecialOffer(IPriceRule rule) {
		specialOffers.add(rule);
	}

	public void addItem(Item item) {
		items.add(item);
	}

	public static void main(String[] args) {
		Basket basket = new Basket();
		basket.addItem(new Item(1, "most expensive", new BigDecimal("3.22")));
		basket.addItem(new Item(2, "medium expensive", new BigDecimal("3.00")));
		basket.addItem(new Item(3, "cheapest", new BigDecimal("1.22")));

		basket.addItem(new Item(4, "item id 4", new BigDecimal("1.99")));
		basket.addItem(new Item(4, "item id 4", new BigDecimal("1.99")));
		basket.addItem(new Item(4, "item id 4", new BigDecimal("1.99")));
		basket.addItem(new Item(4, "item id 4", new BigDecimal("1.99")));

		basket.addItem(new Item(5, "item id 5", new BigDecimal("5.99")));
		basket.addItem(new Item(5, "item id 5", new BigDecimal("5.99")));
		basket.addItem(new Item(5, "item id 5", new BigDecimal("5.99")));

		basket.addItem(new Item(6, "item id 6", new BigDecimal("10.99")));
		basket.addItem(new Item(6, "item id 6", new BigDecimal("10.99")));
		basket.addItem(new Item(6, "item id 6", new BigDecimal("10.99")));

		basket.addItem(new Item(7, "item id 7", new BigDecimal("0.99")));
		basket.addItem(new Item(7, "item id 7", new BigDecimal("0.99")));
		basket.addItem(new Item(7, "item id 7", new BigDecimal("0.99")));

		basket.appyPriceRules();
		System.out.println(basket.printRecipe());
	}
}
