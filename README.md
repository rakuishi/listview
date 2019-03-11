# ListView

ListView is an Android library that can handle a list more easily than `android.widget.ListView` and `RecyclerView`. This library provides the best way of handling a list, endless scrolling, fetching new items.

```Kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById(R.id.list_view)
        listView.add(SingleLineListItem("Text"))
    }
}

class SingleLineListItem(private var text: String) : ListItem(R.layout.list_item_single_line) {

    override fun render() {
        findViewById<TextView>(R.id.text_view).text = text
    }
}
```
