<h1>Supermarket checkout system supporting special price rules.</h1>

<p><font size="3" color="red">Important: Each items in the basket can be taken as a part of special price rule ONLY once.</font></p>

To implementation used JDK 8.<BR><BR>
To check out the project use following command: <br />
<b>git clone https://github.com/rafal-slowik/SupermarketCheckOut.git</b>
<br /><br />
To build and run the tests you need to use Maven in version 3:<br />
<b>mvn clean install site</b><br/><br/>
The report can be found in following directory:<br />
<b>projectDirectory/target/site</b>

<h2>Recipe examples:</h2>

[Example 1 ](recipe_example/recipe1.txt "Example of recipe 1")<br>
[Example 2 ](recipe_example/recipe2.txt "Example of recipe 2")<br>
[Example 3 ](recipe_example/recipe3.txt "Example of recipe 3") 

<h2>Example of using the system can be found in the following java file</h2>

[How to use the system ](src/test/java/market/CashRegisterServiceTest.java "CashRegisterServiceTest") 

<h2>There are implemented price rules which are flexible to use:</h2><br>
<b>1) Buy N items and pay for M:</b><br>
Example - For each 3 items with id = 1 the customer will pay as for 2 items.<br>
Usage: <b><i>IPriceRule rule = new BuyNPayM(1, 3, 2);</i></b><br>
<b>Case 1:</b><br>
If there are 6 items with id = 1 in the basket, which are not applied to other price rule yet, then the rule will be applied twice because there are 6 (2 * 3) items, so the customer will pay for 4 items instead of 6.
<br><br>
<b>Case 2:</b><br>
If there are 6 items with id = 1 in the basket and 2 of them were applied to other price rule, then the rule will be applied once, because effectively there are 4 items which can be taken into consideration and only 3 of them meet the criteria of this rule, so finally the customer get 1 free item.
<br><br>

<b>2) Buy N (equals) items for a special price:</b><br>
Example - For each 3 items with id = 1 the customer will charged 2.33 for each of them.<br>
Usage: <b><i>IPriceRule rule = new BuyNForSpecial(1, 3, new BigDecimal("2.33"));</i></b><br>
<b>Case 1:</b><br>
If there are 6 items with id = 1 in the basket, which are not applied to other price rule yet, then the rule will be applied twice because there are 6 (2 * 3) items, so the customer will pay 2.33 for each of them (6 items in this case).
<br><br>
<b>Case 2:</b><br>
If there are 6 items with id = 1 in the basket and 2 of them were applied to other price rule, then the rule will be applied once, because effectively there are 4 items which can be taken into consideration and only 3 of them meet the criteria of this rule, so the rule will be applied to 3 items and the price will be changed for them.
<br><br>

<b>3) Buy N (in a set of items) and the cheapest is free:</b><br>
Example - For each set which consists of 3 items with id = 1, id = 2 and id = 3 the cheapest one will be free.<br>
Usage: <b><i>IPriceRule rule = new BuyNCheapestFree( new long[] { 1, 2, 3 });</i></b><br>
<b>Case 1:</b><br>
If there are 2 sets of items with id = 1 [price: 1.99], 2 [price: 1.89] and 3 [price: 1.88] in the basket, which are not applied to other price rule yet, then the rule will be applied twice because there are 2 sets of items which meet the criteria of this rule. The prices for 2 items with id = 3 will be reduced to 0.0, because there are the cheapest items within the sets.
<br><br>
<b>Case 2:</b><br>
If there are 2 sets of items with id = 1 [price: 1.99], 2 [price: 1.89] and 3 [price: 1.88] in the basket and 1 item with id = 2 was applied to other rule, so there is only one set of items which meet the criteria of current rule. Finally, only one item with id = 3 will be reduced to 0.0, because there is the cheapest item within the set.
<br><br>
<b>Case 3:</b><br>
If there is a set of items with id = 1 [price: 1.89], 2 [price: 1.89] and 3 [price: 1.89] and they were not applied to other price rule yet, so the set meets the criteria of current rule. Finally, the price of the item with id = 1 will be reduced to 0.0, because in the case when there are same prices for more than one item within the set, the item with the smallest id will be taken.
<br><br>

<b>4) For each N (equals) items X, the customer gets K items Y for free:</b><br>
Example - For each 4 items with id = 1, the customer gets 3 items with id = 2 for free. The prices for the free items will calculated if they are present in the basket and were not applied to other price role/<br>
Usage: <b><i>IPriceRule rule = new BuyNItemsXGetMItemsY(1, 2, 4, 3);</i></b><br>
<b>Case 1:</b><br>
If there are 4 items with id = 1 and 3 items with id = 2 in the basket, which are not applied to other price rule yet, then they meet the criteria of current rule.
The prices of the items (3 items) with the id = 2 will be reduced to 0.0.
<br><br>
<b>Case 2:</b><br>
If there are 4 items with id = 1 and 3 items with id = 2 in the basket, but one of the item with id = 2 was applied to other price rule. As there are 4 items with the id = 1, the criteria of the rule are met. There are only 2 items with id = 2 which can be taken into consideration, so the price of of those 2 items will be changed to 0.0.
<br><br>
<b>Case 3:</b><br>
If there are 10 items with id = 1 and 3 items with id = 2 in the basket, but one of the item with id = 1 was applied to other price rule. Effectively there are 9 items with id = 1 which can be applied to this rule, so the rule can be used twice in this case (8 items). However, there are only 3 items with id = 2 and it means that the rule will be applied only once and the price of those 3 items with id = 2 will be reduced to 0.0.
<br><br>
<b>Case 4:</b><br>
If there are 12 items with id = 1 and 4 items with id = 2 in the basket, but one of the item with id = 1 was applied to other price rule. Effectively there are 11 items with id = 1 which can be applied to this rule, so the rule can be used twice in this case (4*3 > available items = 11 >= 4*2). However, there are only 4 items with id = 2 and it means that the rule will be applied only twice and the price of those 4 items with id = 2 will be reduced to 0.0.
<br><br>


