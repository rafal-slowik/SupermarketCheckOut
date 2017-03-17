/**
 * 
 */
package market.rule.impl;

import static market.property.ConfigProperties.getConfigInstance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

import market.item.Item;
import market.rule.AbstractPriceRule;

/**
 * Buy N items in set and the cheapest is for free.
 * 
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public class BuyNCheapestFree extends AbstractPriceRule {

	private long[] itemIds;

	/**
	 * 
	 * @param itemIds
	 *            - ids of the items in the set
	 */
	public BuyNCheapestFree(long[] itemIds) {
		checkPreconditions(itemIds);
		this.itemIds = itemIds;
	}

	private void checkPreconditions(long[] itemIds) {
		Preconditions.checkArgument(itemIds != null,
				getConfigInstance().formatProperty("message.variable_must_not_be_null", "items list"));
		Preconditions.checkArgument(itemIds.length > 0,
				getConfigInstance().findByKey("message.nbr_of_special_priced_items_must_positive"));
		Set<Long> set = new HashSet<>();
		for (Long key : itemIds) {
			Preconditions.checkArgument(!set.contains(key),
					getConfigInstance().findByKey("message.same_variable_in_set"));
			set.add(key);
		}
	}

	@Override
	public Predicate<Item> selectionFilterPredicate() {
		return item -> Arrays.stream(itemIds).boxed().collect(Collectors.toList()).contains(item.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see market.rule.AbstractPriceRule#priceRuleConsumer(java.util.List)
	 */
	@Override
	public void processFilteredItems(List<Item> filteredItems) {
		AtomicInteger counter = new AtomicInteger(0);
		List<Item> listToOperate = Optional.ofNullable(filteredItems).orElse(new ArrayList<>());
		Map<Long, List<Item>> map = listToOperate.stream().collect(Collectors.groupingBy(Item::getId));
		if (itemIds.length != map.size()) {
			return;
		}
		int min = map.values().stream().min(Comparator.comparing(List::size)).get().size();
		List<Item> listToUdate = new ArrayList<>();
		map.entrySet().forEach(entry -> listToUdate.addAll(entry.getValue().subList(0, min)));
		listToUdate.stream().sorted(Comparator.comparing(Item::getNormalPrice).thenComparing(Item::getId))
				.forEach(item -> {
					item.setUsedFlag(true);
					if (counter.getAndIncrement() < min) {
						item.setRealPrice(BigDecimal.ZERO);
					}
				});
	}
}
