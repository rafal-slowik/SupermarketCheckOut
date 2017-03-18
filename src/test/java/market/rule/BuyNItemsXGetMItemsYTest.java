/**
 * 
 */
package market.rule;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import market.item.Item;
import market.rule.impl.BuyNItemsXGetMItemsY;

/**
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public class BuyNItemsXGetMItemsYTest extends TestUtils {
	private IPriceRule buy5ItemsId1Get3ItemsId2 = null;

	@Before
	public void init() {
		buy5ItemsId1Get3ItemsId2 = new BuyNItemsXGetMItemsY(1, 2, 5, 3);
	}

	@Test
	public void processBuy5ItemsId1Get3ItemsId1Test() {
		int resultSetSize = 8;
		int alreadyUsedFlagOverall = 8;
		int freeId2Items = 3;
		int alreadyUsedFlagOverallCounter = 0;
		int freeId2ItemsCounter = 0;

		List<Item> result = prepareTestList(1, 5, 5);
		result.addAll(prepareTestList(2, 3, 3));
		buy5ItemsId1Get3ItemsId2.applyPriceRule(result);

		assertEquals(resultSetSize, result.size());
		for (Item item : result) {
			if (BigDecimal.ZERO.equals(item.getRealPrice())) {
				if (item.getId() == 2) {
					freeId2ItemsCounter++;
				}
			}

			if (item.isUsedFlag()) {
				alreadyUsedFlagOverallCounter++;
			}
		}
		assertEquals(alreadyUsedFlagOverall, alreadyUsedFlagOverallCounter);
		assertEquals(freeId2Items, freeId2ItemsCounter);
	}

	@Test
	public void processBuy5ItemsId1Get3ItemsId2Test() {
		int resultSetSize = 17;
		int alreadyUsedFlagOverall = 16;
		int freeId2Items = 6;
		int alreadyUsedFlagOverallCounter = 0;
		int freeId2ItemsCounter = 0;

		List<Item> result = prepareTestList(1, 11, 11);
		result.addAll(prepareTestList(2, 6, 6));
		buy5ItemsId1Get3ItemsId2.applyPriceRule(result);

		assertEquals(resultSetSize, result.size());
		for (Item item : result) {
			if (BigDecimal.ZERO.equals(item.getRealPrice())) {
				if (item.getId() == 2) {
					freeId2ItemsCounter++;
				}
			}

			if (item.isUsedFlag()) {
				alreadyUsedFlagOverallCounter++;
			}
		}
		assertEquals(alreadyUsedFlagOverall, alreadyUsedFlagOverallCounter);
		assertEquals(freeId2Items, freeId2ItemsCounter);
	}

	@Test
	public void processBuy5ItemsId1Get3ItemsId3Test() {
		int resultSetSize = 16;
		int alreadyUsedFlagOverall = 15;
		int freeId2Items = 5;
		int alreadyUsedFlagOverallCounter = 0;
		int freeId2ItemsCounter = 0;

		List<Item> result = prepareTestList(1, 11, 11);
		result.addAll(prepareTestList(2, 5, 5));
		buy5ItemsId1Get3ItemsId2.applyPriceRule(result);

		assertEquals(resultSetSize, result.size());
		for (Item item : result) {
			if (BigDecimal.ZERO.equals(item.getRealPrice())) {
				if (item.getId() == 2) {
					freeId2ItemsCounter++;
				}
			}

			if (item.isUsedFlag()) {
				alreadyUsedFlagOverallCounter++;
			}
		}
		assertEquals(alreadyUsedFlagOverall, alreadyUsedFlagOverallCounter);
		assertEquals(freeId2Items, freeId2ItemsCounter);
	}
}
