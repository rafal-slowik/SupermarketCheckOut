/**
 * 
 */
package market.rule.impl;

import static market.property.ConfigProperties.getConfigInstance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.google.common.base.Preconditions;

import market.item.Item;
import market.rule.BuyNEqualItems;

/**
 * Buy N items of the same type for special price.
 * 
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public class BuyNForSpecial extends BuyNEqualItems {

	private BigDecimal specialPrice;

	/**
	 * 
	 * @param itemId
	 * @param buy
	 * @param specialPrice
	 */
	public BuyNForSpecial(long itemId, int buy, BigDecimal specialPrice) {
		super(itemId, buy);
		checkPreconditions(specialPrice);
		this.itemId = itemId;
		this.buy = buy;
		this.specialPrice = specialPrice;
	}

	private void checkPreconditions(BigDecimal specialPrice) {
		Preconditions.checkArgument(specialPrice != null,
				getConfigInstance().formatProperty("message.variable_must_not_be_null", "special price"));
		Preconditions.checkArgument(specialPrice.compareTo(BigDecimal.ZERO) >= 0,
				getConfigInstance().findByKey("message.special_price_must_be_positiv_number"));
	}

	@Override
	public Predicate<Item> selectionFilterPredicate() {
		return item -> item.getId() == itemId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see market.rule.AbstractPriceRule#priceRuleConsumer(java.util.List)
	 */
	@Override
	public void processFilteredItems(List<Item> filteredItems) {
		List<Item> listToOperate = Optional.ofNullable(filteredItems).orElse(new ArrayList<>());
		listToOperate.subList(0, (listToOperate.size() / buy) * buy).stream().forEach(item -> {
			item.setUsedFlag(true);
			item.setRealPrice(specialPrice);
		});
	}
}
