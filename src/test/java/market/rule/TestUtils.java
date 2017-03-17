/**
 * 
 */
package market.rule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;

import market.item.Item;

/**
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public class TestUtils {

	protected List<Item> prepareExpectedResultList(long id, int numberOfElementsWithSameId, int totalNumberOfElements,
			int buy, int numberOfFreeElements) {
		List<Item> items = new ArrayList<>();
		String productName = "productId";
		int idx = (numberOfElementsWithSameId / buy) * buy - 1;
		for (int i = 0; i < totalNumberOfElements; i++) {
			if (i < numberOfElementsWithSameId) {
				Item item = addItem(id, productName + id, BigDecimal.valueOf(3));
				items.add(item);
				if (i <= idx) {
					item.setUsedFlag(true);
					if (i % buy < numberOfFreeElements) {
						item.setRealPrice(BigDecimal.ZERO);
					}
				}
			} else {
				int newId = id == i ? i + 1 : i;
				items.add(addItem(newId, productName + newId, BigDecimal.valueOf(newId)));
			}
		}
		return items;
	}

	protected Item addItem(long id, String name, BigDecimal price) {
		return new Item(id, name, price);
	}
	
	protected List<Item> prepareTestList(long id, int numberOfSameElements, int numberOfElements) {
		List<Item> items = new ArrayList<>();
		String productName = "productId";
		for (int i = 0; i < numberOfElements; i++) {
			if (i < numberOfSameElements) {
				items.add(addItem(id, productName + id, BigDecimal.valueOf(3)));
			} else {
				int newId = id == i ? i + 1 : i;
				items.add(addItem(newId, productName + newId, BigDecimal.valueOf(newId)));
			}
		}
		return items;
	}

	protected List<Item> prepareExpectedResultListSpecialPrice(long id, int numberOfElementsWithSameId, int totalNumberOfElements, int buy,
			BigDecimal specialPrice) {
				List<Item> items = new ArrayList<>();
				String productName = "productId";
				int idx = (numberOfElementsWithSameId / buy) * buy - 1;
				for (int i = 0; i < totalNumberOfElements; i++) {
					if (i < numberOfElementsWithSameId) {
						Item item = addItem(id, productName + id, BigDecimal.valueOf(3));
						items.add(item);
						if (i <= idx) {
							item.setUsedFlag(true);
							item.setRealPrice(specialPrice);
						}
					} else {
						int newId = id == i ? i + 1 : i;
						items.add(addItem(newId, productName + newId, BigDecimal.valueOf(newId)));
					}
				}
				return items;
			}

	protected void check(List<Item> expected, List<Item> result) {
		Assert.assertEquals(expected.size(), result.size());
		Iterator<Item> isIt = expected.iterator();
		Iterator<Item> expectedIt = result.iterator();
		while (isIt.hasNext()) {
			Item expectedItem = expectedIt.next();
			Item isItem = isIt.next();
			Assert.assertEquals(expectedItem.getId(), isItem.getId());
			Assert.assertEquals(expectedItem.getRealPrice(), isItem.getRealPrice());
			Assert.assertEquals(expectedItem.isUsedFlag(), isItem.isUsedFlag());
		}
	}

	protected List<Item> prepareTestList(long id, int numberOfSameElements, int numberOfElements, BigDecimal price) {
		List<Item> items = new ArrayList<>();
		String productName = "productId";
		for (int i = 0; i < numberOfElements; i++) {
			if (i < numberOfSameElements) {
				items.add(addItem(id, productName + id, price));
			} else {
				int newId = id == i ? i + 1 : i;
				items.add(addItem(newId, productName + newId, BigDecimal.valueOf(newId)));
			}
		}
		return items;
	}
}
