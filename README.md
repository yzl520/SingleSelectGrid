# 使用场景
1.当你觉得用GridView去实现单选功能有些麻烦。
2.当你的单选布局需要与其他可滑动控件（如ListView）嵌套使用，而你又不想花时间解决滑动兼容问题。
###效果预览
![preview](https://github.com/yzl520/SingleSelectGrid/raw/master/image/preview.png)
###XML布局
```
<com.android.yzl.lib.SingleSelectGrid
        android:id="@+id/ssg"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:columnNum="4"
        app:itemHeight="120dp" />
```
###设置适配器
```
SingleSelectGrid ssg = (SingleSelectGrid) findViewById(R.id.ssg);
        ssg.setAdapter(new SingleSelectGrid.SingleSelectGridAdapter() {
            @Override
            public int getCount() {
                return 12;
            }

            @Override
            public View getView(int pos, ViewGroup parent) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_ssg, parent, false);
                return view;
            }

            @Override
            public void onSelect(int pos, View v, View lastSelectItem) {
                Toast.makeText(MainActivity.this, "" + pos, Toast.LENGTH_SHORT).show();
            }
        });
```
