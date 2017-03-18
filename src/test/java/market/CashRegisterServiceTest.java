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
import market.rule.impl.BuyNForSpecial;
import market.rule.impl.BuyNItemsXGetMItemsY;
import market.rule.impl.BuyNPayM;

/**
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public class CashRegisterServiceTest {

	private CashRegisterService basket = null;

	@Before
	public void init() {
		basket = new CashRegisterService();
		// buy 3 (in a set of items) and the cheapest is free
		IPriceRule rule = new BuyNCheapestFree(new long[] { 1, 2, 3 });
		basket.addSpecialOffer(rule);

		// buy 3 (equals) items and pay for 2
		rule = new BuyNPayM(4, 3, 2);
		basket.addSpecialOffer(rule);

		// buy 2 (equals) items for a special price
		rule = new BuyNForSpecial(5, 2, new BigDecimal("1.33"));
		basket.addSpecialOffer(rule);

		// for each 3 (equals) items id 6, you get 2 items id 7 for free
		rule = new BuyNItemsXGetMItemsY(6, 7, 3, 2);
		basket.addSpecialOffer(rule);
	}

	/**
	 * It is not true test method, it is more like demonstration how to use
	 * {@link CashRegisterService}
	 */
	@Test
	public void test1() {
		basket.addItemToBasket(new Item(1, "most expensive", new BigDecimal("3.22")));
		basket.addItemToBasket(new Item(2, "medium expensive", new BigDecimal("3.00")));
		basket.addItemToBasket(new Item(3, "cheapest", new BigDecimal("1.22")));

		basket.addItemToBasket(new Item(4, "item id 4", new BigDecimal("1.99")));
		basket.addItemToBasket(new Item(4, "item id 4", new BigDecimal("1.99")));
		basket.addItemToBasket(new Item(4, "item id 4", new BigDecimal("1.99")));
		basket.addItemToBasket(new Item(4, "item id 4", new BigDecimal("1.99")));

		basket.addItemToBasket(new Item(5, "item id 5", new BigDecimal("5.99")));
		basket.addItemToBasket(new Item(5, "item id 5", new BigDecimal("5.99")));
		basket.addItemToBasket(new Item(5, "item id 5", new BigDecimal("5.99")));

		basket.addItemToBasket(new Item(6, "item id 6", new BigDecimal("10.99")));
		basket.addItemToBasket(new Item(6, "item id 6", new BigDecimal("10.99")));
		basket.addItemToBasket(new Item(6, "item id 6", new BigDecimal("10.99")));

		basket.addItemToBasket(new Item(7, "item id 7", new BigDecimal("0.99")));
		basket.addItemToBasket(new Item(7, "item id 7", new BigDecimal("0.99")));
		basket.addItemToBasket(new Item(7, "item id 7", new BigDecimal("0.99")));

		basket.appyPriceRules();
		System.out.println(basket.printRecipe());
	}

	/**
	 * It is not true test method, it is more like demonstration how to use
	 * {@link CashRegisterService}
	 */
	@Test
	public void test2() {
		// add new rule
		// the cheaper item of id 9 and id 10 will be for free if they together
		// in
		// the basket
		IPriceRule rule = new BuyNCheapestFree(new long[] { 8, 9 });

		basket.addSpecialOffer(rule);

		basket.addItemToBasket(new Item(1, "most expensive", new BigDecimal("3.22")));
		basket.addItemToBasket(new Item(2, "medium expensive", new BigDecimal("3.00")));
		basket.addItemToBasket(new Item(3, "cheapest", new BigDecimal("1.22")));

		basket.addItemToBasket(new Item(4, "item id 4", new BigDecimal("1.99")));
		basket.addItemToBasket(new Item(4, "item id 4", new BigDecimal("1.99")));
		basket.addItemToBasket(new Item(4, "item id 4", new BigDecimal("1.99")));
		basket.addItemToBasket(new Item(4, "item id 4", new BigDecimal("1.99")));

		basket.addItemToBasket(new Item(5, "item id 5", new BigDecimal("5.99")));
		basket.addItemToBasket(new Item(5, "item id 5", new BigDecimal("5.99")));
		basket.addItemToBasket(new Item(5, "item id 5", new BigDecimal("5.99")));

		basket.addItemToBasket(new Item(6, "item id 6", new BigDecimal("10.99")));
		basket.addItemToBasket(new Item(6, "item id 6", new BigDecimal("10.99")));
		basket.addItemToBasket(new Item(6, "item id 6", new BigDecimal("10.99")));

		basket.addItemToBasket(new Item(7, "item id 7", new BigDecimal("0.99")));
		basket.addItemToBasket(new Item(7, "item id 7", new BigDecimal("0.99")));
		basket.addItemToBasket(new Item(7, "item id 7", new BigDecimal("0.99")));

		basket.addItemToBasket(new Item(8, "item id 8", new BigDecimal("2.88")));
		basket.addItemToBasket(new Item(8, "item id 8", new BigDecimal("2.88")));
		basket.addItemToBasket(new Item(8, "item id 8", new BigDecimal("2.88")));

		basket.addItemToBasket(new Item(9, "item id 9", new BigDecimal("2.89")));
		basket.addItemToBasket(new Item(9, "item id 9", new BigDecimal("2.89")));

		basket.appyPriceRules();
		System.out.println(basket.printRecipe());
	}
	
	@Test
	public void test3() {
		// add new rule
		// the cheaper item of id 9 and id 10 will be for free if they together
		// in
		// the basket
		IPriceRule rule = new BuyNCheapestFree(new long[] { 10, 11, 12 });

		basket.addSpecialOffer(rule);

		basket.addItemToBasket(new Item(10, "most expensive", new BigDecimal("3.22")));
		basket.addItemToBasket(new Item(11, "medium expensive", new BigDecimal("3.22")));
		basket.addItemToBasket(new Item(12, "cheapest", new BigDecimal("3.22")));

		basket.appyPriceRules();
		System.out.println(basket.printRecipe());
	}


}
