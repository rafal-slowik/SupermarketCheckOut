/**
 * 
 */
package market.rule.impl;

import static market.property.ConfigProperties.getConfigInstance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.base.Preconditions;

import market.item.Item;
import market.rule.BuyNEqualItems;

/**
 * Buy N same products and pay for M of them.
 * 
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public class BuyNPayM extends BuyNEqualItems {

	private int payFor;

	/**
	 * Buy {@code buy} number of {@link Item} with the same {@code itemId} and then
	 * pay for {@code payFor} items.
	 * 
	 * @param itemId
	 *            - it of item
	 * @param buy
	 *            - a number of items need to be buy
	 * @param payFor
	 *            - a number of items which will be paid
	 */
	public BuyNPayM(long itemId, int buy, int payFor) {
		super(itemId, buy);
		checkPreconditions(buy, payFor);
		this.itemId = itemId;
		this.buy = buy;
		this.payFor = payFor;
	}

	private void checkPreconditions(int buy, int payFor) {
		Preconditions.checkArgument(payFor > 0, getConfigInstance().findByKey("message.negative_nbr_of_items_to_pay"));
		Preconditions.checkArgument(buy > payFor, getConfigInstance().findByKey("message.pay.for.more.than.purchase"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see market.rule.AbstractPriceRule#priceRuleConsumer(java.util.List)
	 */
	@Override
	public void processFilteredItems(List<Item> filteredItems) {
		final AtomicInteger counter = new AtomicInteger(0);
		final AtomicInteger reduceCounter = new AtomicInteger(0);
		List<Item> listToOperate = Optional.of(filteredItems).orElse(new ArrayList<>());
		listToOperate.subList(0, (listToOperate.size() / buy) * buy).stream().forEach(item -> {
			item.setUsedFlag(true);
			if (counter.getAndIncrement() % buy == 0) {
				reduceCounter.set(1);
			}

			if (reduceCounter.getAndIncrement() <= (buy - payFor)) {
				item.setRealPrice(BigDecimal.ZERO);
			}
		});
	}
}
