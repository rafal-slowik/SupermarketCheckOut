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
import java.util.function.Predicate;

import com.google.common.base.Preconditions;

import market.item.Item;
import market.rule.BuyNEqualItems;

/**
 * For each N (equals) items X, you get K items Y for free, if they are in the
 * basket.
 * 
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public class BuyNItemsXGetMItemsY extends BuyNEqualItems {

	private long freeItemId;
	private int numberFreeItems;

	/**
	 * 
	 * @param itemId
	 *            - id of the item in offer
	 * @param freeItemsId
	 *            - free items id
	 * @param buy
	 *            - number of items in offer
	 * @param numberFreeItems
	 *            - number of free items
	 * 
	 * @throws IllegalArgumentException
	 *             in case {@code buy} or {@code numberFreeItems} is lower than
	 *             0.
	 */
	public BuyNItemsXGetMItemsY(long itemId, long freeItemId, int buy, int numberFreeItems) {
		super(itemId, buy);
		checkPreconditions(numberFreeItems);
		this.itemId = itemId;
		this.freeItemId = freeItemId;
		this.buy = buy;
		this.numberFreeItems = numberFreeItems;
	}

	private void checkPreconditions(long numberFreeItems) {
		Preconditions.checkArgument(numberFreeItems > 0,
				getConfigInstance().findByKey("message.negative_free_itms_nbr"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see market.rule.BuyNEqualItems#selectionFilterPredicate()
	 */
	@Override
	public Predicate<Item> selectionFilterPredicate() {
		return super.selectionFilterPredicate().or(item -> item.getId() == freeItemId);
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
		List<Item> listToOperate = Optional.ofNullable(filteredItems).orElse(new ArrayList<>());
		long purchasedItems = listToOperate.stream().filter(it -> it.getId() == itemId).count();
		if (purchasedItems < buy) {
			return;
		}

		// update available free
		AtomicInteger toUpdate = new AtomicInteger(((int) purchasedItems / buy) * numberFreeItems);
		listToOperate.stream().filter(it -> it.getId() == freeItemId).forEach(it -> {
			if (counter.getAndIncrement() < toUpdate.get()) {
				reduceCounter.getAndIncrement();
				it.setUsedFlag(true);
				it.setRealPrice(BigDecimal.ZERO);
			}
		});

		float tmp = reduceCounter.get() / (float) numberFreeItems;
		toUpdate.set((int) Math.ceil(tmp) * buy);
		counter.set(0);
		listToOperate.stream().filter(it -> it.getId() == itemId).forEach(it -> {
			if (counter.getAndIncrement() < toUpdate.get()) {
				it.setUsedFlag(true);
			}
		});
	}
}
