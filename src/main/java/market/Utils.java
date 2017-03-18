/**
 * 
 */
package market;

import java.math.BigDecimal;
import java.text.NumberFormat;

import market.item.Item;

/**
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public class Utils {

	private static NumberFormat format = NumberFormat.getCurrencyInstance();
	private static String spaces100 = "                                                                                                    ";
	private static String bottom100 = "---------------------------------------------------------------------------------------------------";
	private static String itemName = "Item Name";
	private static String normalPrice = "Normal Price";
	private static String discount = "Discount";
	private static String afterDiscount = "After Discount";

	public static void printLine(Item item, StringBuilder builder, int lineNumber) {
		int start = lineNumber * 100;
		builder.append(spaces100);

		String idAndName = "id=" + item.getId() + " " + item.getItemName();

		builder.replace(start, start + idAndName.length(), idAndName);
		start += 40;
		builder.replace(start, start + getFormatedLength(item.getNormalPrice()), getFormated(item.getNormalPrice()));
		start += 20;
		builder.replace(start, start + getFormatedLength(item.getDiscount()), getFormated(item.getDiscount()));
		start += 20;
		builder.replace(start, start + getFormatedLength(item.getRealPrice()), getFormated(item.getRealPrice()));
		start += 19;
		builder.replace(start, start + System.lineSeparator().length(), System.lineSeparator());
	}

	private static int getFormatedLength(BigDecimal decimal) {
		return format.format(decimal).length();
	}

	private static String getFormated(BigDecimal decimal) {
		return format.format(decimal);
	}

	public static void printHeader(StringBuilder builder) {
		int start = 0;
		builder.append(spaces100);
		builder.replace(start, start + itemName.length(), itemName);
		start += 40;
		builder.replace(start, start + normalPrice.length(), normalPrice);
		start += 20;
		builder.replace(start, start + discount.length(), discount);
		start += 20;
		builder.replace(start, start + afterDiscount.length(), afterDiscount);
		start += 19;
		builder.replace(start, start + System.lineSeparator().length(), System.lineSeparator());
		builder.append(bottom100).append(System.lineSeparator());

	}

	public static void printFooter(StringBuilder builder, BigDecimal total, BigDecimal totalDiscount) {
		builder.append(bottom100).append(System.lineSeparator());
		builder.append("Total discount:   ").append(format.format(totalDiscount)).append(System.lineSeparator());
		builder.append(bottom100).append(System.lineSeparator());
		builder.append("Total:   ").append(format.format(total)).append(System.lineSeparator());
	}
}
