# HxListOfItems

Show a dialog with a list of items with search capabilities.

<img src="https://github.com/H4zh4n/HaxHanTools/assets/47919702/4f236019-2ae4-4a71-85ab-f47ec3123d8d" width="30%"/> <img src="https://github.com/H4zh4n/HaxHanTools/assets/47919702/e2a4002e-8ee0-4594-8a4b-f21341b9f4ab" width="30%"/> <img src="https://github.com/H4zh4n/HaxHanTools/assets/47919702/c5a7239c-019f-4438-8911-cc7040fcd48b" width="30%"/>

### Basic usage
```java
new HxListOfItems(this)
	.setItems(new String[]{"a", "b"})
	.show();
```


# Parameters

### `setItems(ArrayList<String> items)`
### `setItems(String[] items)`
Provide the items to be shown. As of now the list of items have to be provided in String, no custom classes allowed. the List can be Normal array `new String[]{"a", "b"}` or an ArrayList.

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
### `onSingleItemClickListener(OnHxItemClickListener action)`
What to do after an item is clicked. When you provide `new OnHxItemClickListener` you will be provided with these values : 

`itemText` : The text of the item clicked.
`itemPosition` : clicked item's position in the list .
`thisHxDialogObject` : Current instance of the dialog. you may close it manually or do whatever. useful when providing false for `closeDialogOnItemClick` when calling `onSingleItemClickListener(action, closeDialogOnItemClick)`.


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
			super.onItemLongClicked();  
		}
	
	
	})  
	.setItems(new String[]{"a", "b"})  
	.show();
```


As mentioned before, `onSingleItemClickListener` has three overloaded methods.

1. `onSingleItemClickListener(OnHxItemClickListener action)` : Provide Click action only.
2. `onSingleItemClickListener(OnHxItemClickListener action, boolean closeDialogOnItemClick)` : Provide Click action. and whether to close the dialog when item is clicked or not. when providing `false` for `closeDialogOnItemClick` make sure to handle close yourself, using `thisHxDialogObject`. default for `closeDialogOnItemClick` is `true`.
3. `onSingleItemClickListener(OnHxItemClickListener action, boolean closeDialogOnItemClick, boolean closeDialogOnItemLongClick)` : Provide Click action. and whether to close the dialog when item is clicked or not. AND whether to close the dialog when item is long pressed. default for `closeDialogOnItemLongClick` is `false`.

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
