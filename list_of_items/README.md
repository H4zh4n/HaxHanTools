# HxListOfItems

Show a dialog with a list of items with search capabilities.

<img src="https://github.com/H4zh4n/HaxHanTools/assets/47919702/4f236019-2ae4-4a71-85ab-f47ec3123d8d" width="30%"/> <img src="https://github.com/H4zh4n/HaxHanTools/assets/47919702/e2a4002e-8ee0-4594-8a4b-f21341b9f4ab" width="30%"/> <img src="https://github.com/H4zh4n/HaxHanTools/assets/47919702/c5a7239c-019f-4438-8911-cc7040fcd48b" width="30%"/>

### Basic usage
```java
String array[] = {"Sulaymaniyah", "Hawler"};

new HxListOfItems(this)
	.setItemTextProvider(new ItemTextProvider() {
	    @Override
	    public CharSequence getText(int position) {
		return array[position];
	    }
	})
	.setItems(array)
	.show();
```


# Parameters

### `setItems(ArrayList<Object> items)`
### `setItems(Object[] items)`
Provide the items to be shown. The list can be of any type (String, Integer, Custom class... ).
The List can be Normal array `new String[]{"a", "b"}` or an ArrayList.

Normal array :
```java

String [] array = {"a", "b"};

new HxListOfItems(this)
	.setItems(array)
	.show();
```

ArrayList :

```java

ArrayList<String> arLst = new ArrayList<>();

arLst.add("a");
arLst.add("b");

new HxListOfItems(this)
	.setItems(arLst)
	.show();
```
____
### `setItemTextProvider (ItemTextProvider)`
MUST provide this method so that your items have the correct text.

```java
ArrayList<String> arLst = new ArrayList<>();

arLst.add("a");
arLst.add("b");

new HxListOfItems(this)
	.setItems(arLst)
	.setItemTextProvider(new ItemTextProvider() {
	    @Override
	    public CharSequence getText(int position) {
		// For each item in arLst, it will return it's own text and display it in the item text place.
		return arLst.get(position);
	    }
	})
	.show();
```

____
### Custom Classes
If your array has custom class types, it's best to do as following :

- Assuming we have this custom class :
```java
public class Item {
  int id;

  // Either have a field as public, or a getter method that is public. handle that yourself. 
  public String name; 

  public Item (int id, String name){
    this.id = id;
    this.name = name;
  }
}
```

- We can then do the following :
```java

// create the list
ArrayList<Item> itms = new ArrayList();
itms.add(new Item(1, "Sulaymani"));
itms.add(new Item(2, "Erbil"));
itms.add(new Item(3, "Karkuk"));

//                 v denote array type for setItems
new HxListOfItems<Item>(this)
      .setItems(itms)

      // setup each item's text in the popup.
      .setItemTextProvider(new ItemTextProvider() {
          @Override
          public CharSequence getText(int position) {
              // calling the public field of [name] from the arraylist [itms].
              return itms.get(position).name;
          }
      })
      //                                                 v denote the class type, used for [itemObject]
      .setOnItemClickListener(new OnHxItemClickListener<Item>() {
          @Override
          public void onItemClicked() {
            String text = itemText;
            Item clickedItem = itemObject;
          }
      })
      .show();
```

____

### `show()`
Display the list. this must be provided preferably at the end of all the parameters so the list can be shown.

```java
new HxListOfItems(this)
	.setItems(new String[]{"a", "b"})
	.show();
```

### `enableSearch(boolean enableSearch)`
Show Search header or not. default is true.

```java
new HxListOfItems(this)
	.enableSearch(false) // Hides the search section
	.setItems(new String[]{"a", "b"})
	.show();
```
____
### `setOnItemClickListener(OnHxItemClickListener action)`
What to do after an item is clicked. When you provide `new OnHxItemClickListener` you will be provided with these values : 

1. `itemObject` : The Object of the item clicked. When instantiating the `setOnItemClickListener`, be sure to provide a type just so this variable has same type.
```java
...
//                                                 v denote the class type, used for [itemObject]
.setOnItemClickListener(new OnHxItemClickListener<Item>() {
	@Override
	public void onItemClicked() {
		// the itemObject will have the type of Item, which might be what your provided in the array.
		Item clickedItem = itemObject;
	}
})
...
```



2. `itemText` : The text of the item clicked.

3. `itemPosition` : clicked item's position in the list .

4. `thisHxDialogObject` : Current instance of the dialog. you may close it manually or do whatever. useful when providing false for when you call `setCloseDialogOnItemClick`.

Example :
```java
new HxListOfItems(this)  
	.setOnItemClickListener(new OnHxItemClickListener<Item>() {  
			@Override  
			public void onItemClicked() {  
				// Click action here...
				Item clickedItem = itemObject;
				String tx = itemText;
				int pst = itemPosition;
				Dialog d = thisHxDialogObject;
			}  
		})  
	.setItems(new String[]{"a", "b"})  
	.show();
```

You can also add an action for long click by overriding `onItemLongClicked`.

```java
new HxListOfItems(this)  
	.onSingleItemClickListener(new OnHxItemClickListener() {  
		@Override  
		public void onItemClicked() {  
		  // Click action here...
		  String tx = itemText;
		  int pst = itemPosition;
		  Dialog d = thisHxDialogObject;
		}
	
		@Override  
		public void onItemLongClicked() {  
			// on item long clicked. will be provided with same variables as onItemClicked. (itemObject, itemText,itemPosition ... etc.)

		}
	
	
	})  
	.setItems(new String[]{"a", "b"})  
	.show();
```


____

### `onListCloseListener(CustomAction action)`
An action to perform after the list is closed. This will be performed whether an item is clicked or the list is closed from the close button.


```java
new HxListOfItems(this)
	.onListCloseListener(new CustomAction() {  
		@Override  
		public void positiveButtonPressed() {  
		  // code here
		}  
	})
	.setItems(new String[]{"a", "b"})
	.show();
```

____

... more method explanations to come ...
