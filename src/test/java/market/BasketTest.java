/**
 * 
 */
package market;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import market.item.Item;
import market.rule.IPriceRule;
import market.rule.impl.BuyNCheapestFree;

/**
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public class BasketTest {

	private Basket basket = null;

	@Before
	public void init() {
		basket = new Basket();
	}

	/**
	 * It is not true test method, it is more like demonstration how to use
	 * {@link Basket}
	 */
	@Test
	public void test1() {
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

	/**
	 * It is not true test method, it is more like demonstration how to use
	 * {@link Basket}
	 */
	@Test
	public void test2() {
		// add new rule
		// the cheaper item of id 9 and id 10 will be for free if they together in
		// the basket
		IPriceRule rule = new BuyNCheapestFree(new long[] { 8, 9 });

		basket.addSpecialOffer(rule);

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

		basket.addItem(new Item(8, "item id 8", new BigDecimal("2.88")));
		basket.addItem(new Item(8, "item id 8", new BigDecimal("2.88")));
		basket.addItem(new Item(8, "item id 8", new BigDecimal("2.88")));
		
		basket.addItem(new Item(9, "item id 9", new BigDecimal("2.89")));
		basket.addItem(new Item(9, "item id 9", new BigDecimal("2.89")));

		basket.appyPriceRules();
		System.out.println(basket.printRecipe());
	}

}
