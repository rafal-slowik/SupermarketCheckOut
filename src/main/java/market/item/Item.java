package market.item;
/**
 * 
 */

import static market.property.ConfigProperties.getConfigInstance;

import java.math.BigDecimal;

import com.google.common.base.Preconditions;

/**
 * Superclass of all items in the supermarket.
 * 
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public class Item {

	// unique id
	private long id;
	private BigDecimal realPrice;
	private BigDecimal normalPrice;
	// item name
	private String itemName;
	// marker if the item was already taken in special price rule
	private boolean usedFlag;

	/**
	 * 
	 * @param id
	 *            - id of the item
	 * @param itemName
	 *            - the name of the item. If longer than 30 characters then will
	 *            be truncated to 30 characters.
	 * @param normalPrice
	 *            - normal price for the item
	 * 
	 * @throws IllegalArgumentException
	 *             when {@code normalPrice} or {@code itemName} is {@code null}
	 *             or {@code normalPrice} <= 0.
	 */
	public Item(long id, String itemName, BigDecimal normalPrice) {
		checkPreconditions(normalPrice, itemName);
		this.id = id;
		this.itemName = itemName.length() > 30 ? itemName.substring(0, 30) : itemName;
		this.normalPrice = normalPrice;
		this.realPrice = normalPrice;
	}

	private void checkPreconditions(BigDecimal specialPrice, String itemName) {
		Preconditions.checkArgument(itemName != null,
				getConfigInstance().formatProperty("message.variable_must_not_be_null", "item name"));
		Preconditions.checkArgument(specialPrice != null,
				getConfigInstance().formatProperty("message.variable_must_not_be_null", "special price"));
		Preconditions.checkArgument(specialPrice.compareTo(BigDecimal.ZERO) >= 0,
				getConfigInstance().findByKey("message.special_price_must_be_positiv_number"));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}

	public BigDecimal getNormalPrice() {
		return normalPrice;
	}

	public void setNormalPrice(BigDecimal normalPrice) {
		this.normalPrice = normalPrice;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public boolean isUsedFlag() {
		return usedFlag;
	}

	public void setUsedFlag(boolean usedFlag) {
		this.usedFlag = usedFlag;
	}

	/**
	 * 
	 * @return discount for the item
	 */
	public BigDecimal getDiscount() {
		if (usedFlag) {
			return realPrice.subtract(normalPrice);
		}
		return BigDecimal.ZERO;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result + ((normalPrice == null) ? 0 : normalPrice.hashCode());
		result = prime * result + ((realPrice == null) ? 0 : realPrice.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (id != other.id)
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (normalPrice == null) {
			if (other.normalPrice != null)
				return false;
		} else if (!normalPrice.equals(other.normalPrice))
			return false;
		if (realPrice == null) {
			if (other.realPrice != null)
				return false;
		} else if (!realPrice.equals(other.realPrice))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", realPrice=" + realPrice + ", normalPrice=" + normalPrice + ", itemName=" + itemName
				+ "]";
	}
}
