/**
 * 
 */
package market.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import market.item.Item;

/**
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public interface IPriceRule {

	void applyPriceRule(List<Item> itemsToCheck);

	Predicate<Item> selectionFilterPredicate();

	default List<Item> filterByCriteria(List<Item> itemsToCheck) {
		return Optional.ofNullable(itemsToCheck).orElse(new ArrayList<>()).stream()
				.filter(item -> !item.isUsedFlag() && (selectionFilterPredicate().test(item)))
				.collect(Collectors.toCollection(ArrayList::new));
	}
}
