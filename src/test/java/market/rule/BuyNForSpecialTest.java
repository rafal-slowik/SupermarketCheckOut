/**
 * 
 */
package market.rule;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import market.item.Item;
import market.rule.impl.BuyNForSpecial;

/**
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public class BuyNForSpecialTest extends TestUtils {

	private IPriceRule buy3For2_66EachRule = null;

	@Before
	public void init() {
		buy3For2_66EachRule = new BuyNForSpecial(1, 3, new BigDecimal("2.66"));
	}

	@Test
	public void processBuy3For2_66EachRule1Test() {
		List<Item> result = prepareTestList(1, 5, 10);
		List<Item> expected = prepareExpectedResultListSpecialPrice(1, 5, 10, 3, new BigDecimal("2.66"));
		buy3For2_66EachRule.applyPriceRule(result);
		check(result, expected);
	}

	@Test
	public void processBuy3For2_66EachRule2Test() {
		List<Item> result = prepareTestList(1, 6, 11);
		List<Item> expected = prepareExpectedResultListSpecialPrice(1, 6, 11, 3, new BigDecimal("2.66"));
		buy3For2_66EachRule.applyPriceRule(result);
		check(result, expected);
	}

	@Test
	public void negativePriceRule1Test() {
		try {
			new BuyNForSpecial(1, 3, new BigDecimal("-2.66"));
			Assert.fail("IllegalArgumentException should be thrown");
		} catch (Throwable t) {
			if (t instanceof IllegalArgumentException == false) {
				Assert.fail("IllegalArgumentException should be thrown");
			}
		}
	}

	@Test
	public void nullPriceRule1Test() {
		try {
			new BuyNForSpecial(1, 3, null);
			Assert.fail("IllegalArgumentException should be thrown");
		} catch (Throwable t) {
			if (t instanceof IllegalArgumentException == false) {
				Assert.fail("IllegalArgumentException should be thrown");
			}
		}
	}

	@Test
	public void negativeNbrOfItemsRule1Test() {
		try {
			new BuyNForSpecial(1, -1, new BigDecimal("2.66"));
			Assert.fail("IllegalArgumentException should be thrown");
		} catch (Throwable t) {
			if (t instanceof IllegalArgumentException == false) {
				Assert.fail("IllegalArgumentException should be thrown");
			}
		}
	}
}
