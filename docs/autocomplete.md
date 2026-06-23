# FrogoListWithAutoComplete

A chip-style multi-value input component. Each value appears as an inline chip with a remove button, and new values are added via an AutoCompleteTextView with dropdown suggestions.

---

## Quick Start

```java
// 1. Prepare your suggestion list
List<String> suggestions = Arrays.asList("Action", "Drama", "Comedy", "Horror");

// 2. Create a ClassFrogoData container
ClassFrogoData data = new ClassFrogoData();
data.autoCompleteList = suggestions;      // Suggestions for the dropdown
data.dataIncluded = new ArrayList<>();     // Currently selected values
data.backgroundColor = Color.WHITE;        // Chip background color
data.isLinear = true;                      // true = full-width, false = wrap content

// 3. Setup the RecyclerView
FrogoListWithAutoComplete frogo = new FrogoListWithAutoComplete(context);
IFrogoViewAdapter adapter = frogo.initFrogo(data);

yourRecyclerView.injector()
    .addData(data.dataIncluded)
    .addCallback(adapter)
    .createLayoutLinearVertical(false)
    .addCustomView(R.layout.hx_item_auto_complete)
    .build();
```

---

## ClassFrogoData Properties

| Property | Type | Default | Description |
|---|---|---|---|
| `autoCompleteList` | `List<String>` | — | Suggestions shown in the dropdown |
| `dataIncluded` | `List<String>` | — | The list of currently selected values |
| `backgroundColor` | int | — | Background color of each chip |
| `isLinear` | boolean | — | `true` for full-width chips, `false` for wrap-content |
| `frg` | FrogoRecyclerView | — | Reference to the parent RecyclerView |

---

## Key Methods

### `initFrogo(ClassFrogoData data)`

Returns an `IFrogoViewAdapter` that manages the auto-complete chips. Each item renders as:
- **Non-empty**: displays the value with a close (✕) button to remove it
- **Empty**: displays an AutoCompleteTextView for adding new values

### `formatToSingleLine(List<String> ar)`

Converts a list of strings into a pipe-separated string:

```java
List<String> tags = Arrays.asList("Action", "Comedy");
String line = FrogoListWithAutoComplete.formatToSingleLine(tags);
// → "Action | Comedy"
```

### `formatInitFrogoArray(List<String> arList, String pipeSeparated)`

Parses a pipe-separated string back into a list:

```java
List<String> list = new ArrayList<>();
FrogoListWithAutoComplete.formatInitFrogoArray(list, "Action | Comedy");
// list → ["Action", "Comedy"]
```

### `setListForAutoComplete(AutoCompleteTextView et, List values)`

Attaches an `AutoSuggestAdapter` to an AutoCompleteTextView with the given suggestion values.

### `requestLastItemFocus(ClassFrogoData dt)`

Programmatically scrolls to and focuses the last (empty) item for input.

---

## User Interaction

- **Adding a value**: Type in the empty AutoCompleteTextView at the bottom and press Enter, or tap away to lose focus
- **Removing a value**: Tap the ✕ icon on any chip
- **Auto-suggest**: Matches against the provided `autoCompleteList` as the user types

---

## Persisting Data

Use `formatToSingleLine` / `formatInitFrogoArray` to convert between list and pipe-separated string for storage:

```java
// Save
String serialized = FrogoListWithAutoComplete.formatToSingleLine(data.dataIncluded);
Utils.put("selected_genres", serialized);

// Restore
List<String> restored = new ArrayList<>();
String saved = Utils.getStringPref("selected_genres", "");
FrogoListWithAutoComplete.formatInitFrogoArray(restored, saved);
data.dataIncluded = restored;
```

---

## Notes

- Requires `FrogoRecyclerView` from the Frogobox Recycler library
- The component must be used within a `FrogoRecyclerView`, not a standard RecyclerView
- Suggestion filtering happens via `AutoSuggestAdapter` (custom adapter in the library)