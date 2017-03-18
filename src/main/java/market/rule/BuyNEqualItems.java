/**
 * 
 */
package market.rule;

import static market.property.ConfigProperties.getConfigInstance;

import java.util.function.Predicate;

import com.google.common.base.Preconditions;

import market.item.Item;

/**
 * 
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public abstract class BuyNEqualItems extends AbstractPriceRule {

	protected long itemId;
	protected int buy;

	protected BuyNEqualItems(long itemId, int buy) {
		checkPreconditions(buy);
		this.itemId = itemId;
		this.buy = buy;
	}

	private void checkPreconditions(int buy) {
		Preconditions.checkArgument(buy > 0, getConfigInstance().findByKey("message.negative_nbr_of_items"));
	}

	@Override
	public Predicate<Item> selectionFilterPredicate() {
		return item -> item.getId() == itemId;
	}
}
