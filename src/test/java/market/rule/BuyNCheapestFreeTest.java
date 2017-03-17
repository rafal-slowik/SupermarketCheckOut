/**
 * 
 */
package market.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import market.item.Item;
import market.rule.impl.BuyNCheapestFree;

/**
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public class BuyNCheapestFreeTest extends TestUtils {

	private BuyNCheapestFree buy3ForCheapestFree = null;

	@Before
	public void init() {
		buy3ForCheapestFree = new BuyNCheapestFree( new long[] { 1, 2, 3 });
	}

	@Test
	public void processBuy3ForCheapestFreeRule1Test() {
		int expectedSize = 12;
		int expectedReducedItemsId3 = 2;
		int expectedReducedItemsOverall = 2;
		int expectedAlreadyUsedTrueFlag = 6;

		int reducedItemsId3Counter = 0;
		int alreadyUsedTrueFlagCounter = 0;
		int reducedItemsOverall = 0;

		List<Item> result = generateProcessBuy3ForCheapestFreeRule1Data();

		buy3ForCheapestFree.applyPriceRule(result);
		assertEquals(expectedSize, result.size());

		for (Item item : result) {
			if (BigDecimal.ZERO.equals(item.getRealPrice())) {
				reducedItemsOverall++;
				if (item.getId() == 3) {
					reducedItemsId3Counter++;
				}
			}

			if (item.isUsedFlag()) {
				alreadyUsedTrueFlagCounter++;
			}
		}
		assertEquals(expectedReducedItemsId3, reducedItemsId3Counter);
		assertEquals(expectedAlreadyUsedTrueFlag, alreadyUsedTrueFlagCounter);
		assertEquals(expectedReducedItemsOverall, reducedItemsOverall);
	}

	@Test
	public void repeatetIdsRule1Test() {
		try {
			new BuyNCheapestFree(new long[] { 1, 2, 1 });
			fail("IllegalArgumentException should be thrown");
		} catch (Throwable t) {
			if (t instanceof IllegalArgumentException == false) {
				fail("IllegalArgumentException should be thrown");
			}
		}
	}

	@Test
	public void nullOfItemsRule1Test() {
		try {
			new BuyNCheapestFree(null);
			fail("IllegalArgumentException should be thrown");
		} catch (Throwable t) {
			if (t instanceof IllegalArgumentException == false) {
				fail("IllegalArgumentException should be thrown");
			}
		}
	}

	@Test
	public void negativeBuyItemsNbrRule1Test() {
		try {
			new BuyNCheapestFree(new long[] { });
			fail("IllegalArgumentException should be thrown");
		} catch (Throwable t) {
			if (t instanceof IllegalArgumentException == false) {
				fail("IllegalArgumentException should be thrown");
			}
		}
	}

	private List<Item> generateProcessBuy3ForCheapestFreeRule1Data() {
		List<Item> result = prepareTestList(1, 2, 2, new BigDecimal("1.22"));
		result.addAll(prepareTestList(2, 3, 3, new BigDecimal("1.12")));
		result.addAll(prepareTestList(3, 3, 3, new BigDecimal("1.11")));
		result.addAll(prepareTestList(4, 4, 4, new BigDecimal("1.55")));
		return result;
	}
}
