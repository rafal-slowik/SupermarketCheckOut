/**
 * 
 */
package market.rule;

import java.util.List;

import market.item.Item;

/**
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public abstract class AbstractPriceRule implements IPriceRule {

	@Override
	public void applyPriceRule(List<Item> itemsToCheck) {
		List<Item> filteredItems = filterByCriteria(itemsToCheck);
		processFilteredItems(filteredItems);
	}

	public abstract void processFilteredItems(List<Item> filteredItems);
}
