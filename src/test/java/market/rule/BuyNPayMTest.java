/**
 * 
 */
package market.rule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import market.item.Item;
import market.rule.impl.BuyNPayM;

/**
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public class BuyNPayMTest extends TestUtils {

	private BuyNPayM buy3Pay2Rule = null;
	private BuyNPayM buy4Pay2Rule = null;
	private BuyNPayM buy4Pay3Rule = null;
	private BuyNPayM buy7Pay3Rule = null;

	@Before
	public void init() {
		buy3Pay2Rule = new BuyNPayM(1, 3, 2);
		buy4Pay2Rule = new BuyNPayM(1, 4, 2);
		buy4Pay3Rule = new BuyNPayM(1, 4, 3);
		buy7Pay3Rule = new BuyNPayM(1, 7, 3);
	}

	@Test
	public void processBuy3Pay2Rule1Test() {
		List<Item> result = prepareTestList(1, 5, 10);
		List<Item> expected = prepareExpectedResultList(1, 5, 10, 3, 1);
		buy3Pay2Rule.applyPriceRule(result);
		check(result, expected);
	}

	@Test
	public void processBuy3Pay2Rule2Test() {
		List<Item> result = prepareTestList(1, 6, 10);
		List<Item> expected = prepareExpectedResultList(1, 6, 10, 3, 1);
		buy3Pay2Rule.applyPriceRule(result);
		check(result, expected);
	}

	@Test
	public void processBuy4Pay2Rule1Test() {
		List<Item> result = prepareTestList(1, 5, 10);
		List<Item> expected = prepareExpectedResultList(1, 5, 10, 4, 2);
		buy4Pay2Rule.applyPriceRule(result);
		check(result, expected);
	}

	@Test
	public void processBuy4Pay2Rule2Test() {
		List<Item> result = prepareTestList(1, 9, 20);
		List<Item> expected = prepareExpectedResultList(1, 9, 20, 4, 2);
		buy4Pay2Rule.applyPriceRule(result);
		check(result, expected);
	}

	@Test
	public void processBuy4Pay3Rule1Test() {
		List<Item> result = prepareTestList(1, 5, 10);
		List<Item> expected = prepareExpectedResultList(1, 5, 10, 4, 1);
		buy4Pay3Rule.applyPriceRule(result);
		check(result, expected);
	}

	@Test
	public void processBuy4Pay3Rule2Test() {
		List<Item> result = prepareTestList(1, 9, 20);
		List<Item> expected = prepareExpectedResultList(1, 9, 20, 4, 1);
		buy4Pay3Rule.applyPriceRule(result);
		check(result, expected);
	}

	@Test
	public void processBuy7Pay3RuleTest() {
		List<Item> result = prepareTestList(1, 7, 15);
		List<Item> expected = prepareExpectedResultList(1, 7, 15, 7, 4);
		buy7Pay3Rule.applyPriceRule(result);
		check(result, expected);
	}

	@Test
	public void processBuy7Pay3Rule2Test() {
		List<Item> result = prepareTestList(1, 15, 20);
		List<Item> expected = prepareExpectedResultList(1, 15, 20, 7, 4);
		buy7Pay3Rule.applyPriceRule(result);
		check(result, expected);
	}

	@Test
	public void nullInputBuy7Pay3Rule2Test() {
		List<Item> input = null;
		buy7Pay3Rule.applyPriceRule(input);
		Assert.assertNull(input);
	}

	@Test
	public void negativeNumberTest() {
		try {
			new BuyNPayM(1, 2, -1);
			Assert.fail("IllegalArgumentException should be thrown");
		} catch (Throwable t) {
			if (t instanceof IllegalArgumentException == false) {
				Assert.fail("IllegalArgumentException should be thrown");
			}
		}
	}

	@Test
	public void greaterNuberOfItemsToPayThanAvailableTest() {
		try {
			new BuyNPayM(1, 2, 3);
			Assert.fail("IllegalArgumentException should be thrown");
		} catch (Throwable t) {
			if (t instanceof IllegalArgumentException == false) {
				Assert.fail("IllegalArgumentException should be thrown");
			}
		}
	}
}
