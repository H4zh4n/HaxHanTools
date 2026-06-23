# HxListOfItems

A searchable dialog that displays a list of items with rich customization — theming, selection, images, and click handling.

<img src="https://github.com/H4zh4n/HaxHanTools/assets/47919702/4f236019-2ae4-4a71-85ab-f47ec3123d8d" width="30%"/> <img src="https://github.com/H4zh4n/HaxHanTools/assets/47919702/e2a4002e-8ee0-4594-8a4b-f21341b9f4ab" width="30%"/> <img src="https://github.com/H4zh4n/HaxHanTools/assets/47919702/c5a7239c-019f-4438-8911-cc7040fcd48b" width="30%"/>

## Quick Start

```java
String[] cities = {"Sulaymaniyah", "Hawler", "Kirkuk"};

new HxListOfItems<String>(this)
    .setItems(cities)
    .setItemTextProvider(position -> cities[position])
    .setOnItemClickListener(new OnHxItemClickListener<>() {
        @Override
        public void onItemClicked() {
            HxToast.showToast("Selected: " + itemText);
        }
    })
    .show();
```

---

## Providing Items

Items can be an array or an `ArrayList` of any type — `String`, `Integer`, or your own custom classes.

### Using an Array

```java
String[] colors = {"Red", "Green", "Blue"};

new HxListOfItems<String>(this)
    .setItems(colors)
    .setItemTextProvider(position -> colors[position])
    .show();
```

### Using an ArrayList

```java
ArrayList<String> names = new ArrayList<>();
names.add("Ahmed");
names.add("Sara");
names.add("Dana");

new HxListOfItems<String>(this)
    .setItems(names)
    .setItemTextProvider(position -> names.get(position))
    .show();
```

### Using Custom Classes

The list can hold any object type. You control what text to display via `setItemTextProvider`:

```java
public class City {
    public int id;
    public String name;
    public City(int id, String name) { this.id = id; this.name = name; }
}

ArrayList<City> cities = new ArrayList<>();
cities.add(new City(1, "Sulaymaniyah"));
cities.add(new City(2, "Erbil"));
cities.add(new City(3, "Kirkuk"));

new HxListOfItems<City>(this)
    .setItems(cities)
    .setItemTextProvider(position -> cities.get(position).name)
    .setOnItemClickListener(new OnHxItemClickListener<City>() {
        @Override
        public void onItemClicked() {
            City selected = itemObject;   // typed as City
            String text = itemText;        // the displayed text
            int pos = itemPosition;        // 0-based index
        }
    })
    .show();
```

---

## Click & Long-Click

### Basic Click Handling

```java
new HxListOfItems<String>(this)
    .setItems(cities)
    .setItemTextProvider(position -> cities[position])
    .setOnItemClickListener(new OnHxItemClickListener<String>() {
        @Override
        public void onItemClicked() {
            HxToast.showToastSuccess("Clicked: " + itemText);
        }
    })
    .show();
```

### OnHxItemClickListener Callback Fields

| Field | Type | Description |
|---|---|---|
| `itemObject` | `T` | The actual object from your list |
| `itemText` | `String` | The text displayed for the item |
| `itemPosition` | `int` | 0-based index in the list |
| `thisHxDialogObject` | `HxListOfItems` | Current dialog instance (useful for manual dismiss) |

### Long-Click Support

Override `onItemLongClicked` in the same listener:

```java
.setOnItemClickListener(new OnHxItemClickListener<City>() {
    @Override
    public void onItemClicked() {
        // Single tap
    }

    @Override
    public void onItemLongClicked() {
        // Long press — same fields available
        HxToast.showToast("Long-pressed: " + itemText);
    }
})
```

### Controlling Dialog Close Behavior

```java
// Keep dialog open after click (useful for multi-select)
.setCloseDialogOnItemClick(false)

// Close dialog on long-click
.setCloseDialogOnItemLongClick(true)
```

---

## Search

Search is enabled by default with case-insensitive matching. Search matches are **highlighted** in the list.

```java
// Hide the search bar
.enableSearch(false)

// Case-sensitive search
.enableSearch(true, false)

// Customize the matched text highlight color
.setFoundPortionTextColor(Color.parseColor("#FF9800"))

// Change search input text color & hint
.setSearchTextColor(Color.WHITE)
.setSearchHint("Type to filter...")
```

---

## Pre-Selecting Items

```java
// Select by index
.setSelectedItemIndex(2)

// Select by exact text match
.setSelectedItem("Erbil")

// Select first item whose text contains the given string
.setSelectFirstItemThatContains("Kirk")

// Disable auto-scrolling to the selected item
.scrollToSelectedItemWhenDialogOpens(false)
```

---

## Theming & Appearance

```java
new HxListOfItems<String>(this)
    .setTitle("Choose a City")
    .setTitleBarBackgroundColor(Color.parseColor("#1A1A2E"))
    .setTitleBarTextColor(Color.WHITE)

    // Item colors (normal state)
    .setItemBackgroundColor(Color.parseColor("#2D2D44"))
    .setItemTextColor(Color.WHITE)

    // Item colors (selected state)
    .setSelectedItemBackgroundColor(Color.parseColor("#16213E"))
    .setSelectedItemTextColor(Color.parseColor("#E94560"))

    // Dialog background
    .setDialogBackgroundColor(Color.parseColor("#0F3460"))
    .setHintBackgroundColor(Color.parseColor("#1A1A2E"))

    // Close button
    .setCloseButtonColor(Color.WHITE)
    .showCloseButton(true)

    .setItems(cities)
    .setItemTextProvider(position -> cities[position])
    .show();
```

---

## Item Icons

Each item can have a leading icon. Different icons for normal and selected states:

```java
// Same icon for all items
.setItemImage(R.drawable.ic_city, Color.parseColor("#333333"))

// Different icon when selected
.setSelectedItemImage(R.drawable.ic_city_selected, Color.parseColor("#E94560"))
```

---

## Dialog Lifecycle

```java
new HxListOfItems<String>(this)
    // Action to run after the dialog is shown
    .doAfterDialogShown(() -> Logger.d("Dialog is now visible"))

    // Action when the dialog is dismissed (by any means)
    .onListCloseListener(() -> Logger.d("Dialog was closed"))

    .setItems(cities)
    .setItemTextProvider(position -> cities[position])
    .show();
```

---

## API Reference

| Method | Description |
|---|---|
| `setItems(T[] items)` | Provide items as an array |
| `setItems(ArrayList<T> items)` | Provide items as an ArrayList |
| `setItemTextProvider(ItemTextProvider)` | **Required.** Maps each item to its display text |
| `setOnItemClickListener(OnHxItemClickListener)` | Click & long-click handler |
| `show()` | Display the dialog |
| `setTitle(String title)` | Set the title bar text |
| `setTitleBarBackgroundColor(int)` | Title bar background color |
| `setTitleBarTextColor(int)` | Title bar text color |
| `setDialogBackgroundColor(int)` | Full dialog background color |
| `setHintBackgroundColor(int)` | Search bar background color |
| `setCloseButtonColor(int)` | Tint the close (✕) button |
| `showCloseButton(boolean)` | Show or hide the close button |
| `enableSearch(boolean)` | Show/hide the search bar (default: `true`) |
| `enableSearch(boolean, boolean)` | Second param controls case-insensitive matching |
| `setSearchTextColor(int)` | Search input text color |
| `setSearchHint(String)` | Search input placeholder text |
| `setFoundPortionTextColor(int)` | Highlight color for search matches |
| `setItemBackgroundColor(int)` | Normal item background |
| `setItemTextColor(int)` | Normal item text color |
| `setSelectedItemBackgroundColor(int)` | Selected item background |
| `setSelectedItemTextColor(int)` | Selected item text color |
| `setSelectedItemIndex(int)` | Pre-select an item by index |
| `setSelectedItem(String)` | Pre-select an item by exact text match |
| `setSelectFirstItemThatContains(String)` | Pre-select first item containing the given text |
| `scrollToSelectedItemWhenDialogOpens(boolean)` | Auto-scroll to selected item (default: `true`) |
| `setCloseDialogOnItemClick(boolean)` | Close on item click (default: `true`) |
| `setCloseDialogOnItemLongClick(boolean)` | Close on item long-click (default: `false`) |
| `setItemImage(Bitmap, int)` | Set a leading icon for items (Bitmap + background) |
| `setItemImage(int, int)` | Set a leading icon from a drawable resource |
| `setSelectedItemImage(Bitmap, int)` | Set the leading icon for the selected item |
| `setSelectedItemImage(int, int)` | Set selected icon from a drawable resource |
| `doAfterDialogShown(CustomAction)` | Action to run after the dialog appears |
| `onListCloseListener(CustomAction)` | Action to run when the dialog is dismissed |