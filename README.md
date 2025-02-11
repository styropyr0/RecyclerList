# RecyclerList

RecyclerList is an easy-to-use RecyclerView wrapper that eliminates the need to manually declare adapters and ViewHolders. It simplifies the implementation of lists in Android applications, providing a streamlined and efficient way to manage item views.

This library is built on top of RecyclerView and inherits from it, meaning all standard RecyclerView methods work seamlessly.

## Features
- No need to manually create adapters and ViewHolders
- Simple data binding with lambda functions
- Built-in support for layout management (orientation, reverse layout, stack from end)
- Easy item removal and dynamic updates
- Callbacks for view attachment, detachment, and recycling

## Installation

RecyclerList is hosted on JitPack. To include it in your project, follow these steps:

### Step 1: Add JitPack Repository

Add the JitPack repository to your root `build.gradle` (or `settings.gradle` for newer versions of Gradle):

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
```
or for `settings.gradle.kts`

```kotlin
repositories { 
    maven { url = uri("https://jitpack.io") }
}
```

### Step 2: Add Dependency

Add the following dependency to your module-level `build.gradle`:

```gradle
dependencies {
    implementation 'com.github.styropyr0:RecyclerList:v1.0.0'
}
```
or for `app:build.gradle.kts`

```kotlin
dependencies {
    implementation("com.github.styropyr0:RecyclerList:v1.0.0")
}
```

Replace `1.0.0` with the latest release version.

## Usage

### XML Declaration

To use RecyclerList in your layout, declare it in XML:

```xml
<com.matrix.recycler_list.RecyclerList
    android:id="@+id/recyclerList"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

### Setting Data in Kotlin

Bind a list of items to the RecyclerList dynamically:

```kotlin
val recyclerList = findViewById<RecyclerList<Data>>(R.id.recyclerList)
recyclerList.apply {
    setHasStableIds(true)
    reverseLayout(false)
    stackFromEnd(false)
    setItems(dataList, R.layout.cell_layout) { view, item, index ->
        view.findViewById<TextView>(R.id.title).text = item.title
        view.findViewById<TextView>(R.id.content).text = item.content
        view.setOnClickListener {
            removeItem(index)
        }
    }
}

// This will change the 6th element's title and content after 3s
GlobalScope.launch {
  delay(3000)
  withContext(Dispatchers.Main) {
    recyclerList.items[5] = Data("Title CHANGED", "This is the content of CHANGED")
    recyclerList.notifyItemChanged(5)
  }
}
```

### Customizing RecyclerList

RecyclerList provides multiple customization options:

- **Set Layout Orientation:**
  ```kotlin
  recyclerList.setOrientation(RecyclerView.VERTICAL)
  ```

- **Reverse Layout:**
  ```kotlin
  recyclerList.reverseLayout(true)
  ```

- **Stack From End:**
  ```kotlin
  recyclerList.stackFromEnd(true)
  ```

- **Get First/Last Visible Item Positions:**
  ```kotlin
  val firstVisible = recyclerList.findFirstVisibleItemPosition()
  val lastVisible = recyclerList.findLastVisibleItemPosition()
  ```

- **Remove an Item:**
  ```kotlin
  recyclerList.removeItem(position)
  ```

- **Get View at a Specific Position:**
  ```kotlin
  val view = recyclerList.getViewAt(position)
  ```

## Callbacks

RecyclerList provides callbacks for managing views dynamically:

- **On View Recycled:**
  ```kotlin
  recyclerList.onViewRecycled { viewHolder ->
      // Handle view recycling
  }
  ```

- **On View Attached to Window:**
  ```kotlin
  recyclerList.onViewAttachedToWindow { viewHolder ->
      // Handle view attached
  }
  ```

- **On View Detached from Window:**
  ```kotlin
  recyclerList.onViewDetachedFromWindow { viewHolder ->
      // Handle view detached
  }
  ```

- **On RecyclerView Attached:**
  ```kotlin
  recyclerList.onAttachedToRecyclerView { recyclerView ->
      // Handle RecyclerView attachment
  }
  ```

## License

RecyclerList is licensed under the MIT License. See [LICENSE](LICENSE) for details.

---

