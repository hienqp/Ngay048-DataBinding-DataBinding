# DATA BINDING VÀ MVVM

[DataBinding Google Document](https://developer.android.com/topic/libraries/data-binding)

- DataBinding Library là thư viện hỗ trợ, giúp liên kết giao diện UI trong Layout đến các nguồn dữ liệu trong app, bằng cách sử dụng hình thức khai báo trực tiếp trong Layout thay vì viết chương trình điều khiển
- DataBinding Library được Google giới thiệu nhằm hỗ trợ trực tiếp cho mô hình MVVM trong quá trình xây dựng ứng dụng
___

## CONFIGURE DATA BINDING

- để sử dụng DataBinding, trong file __build.gradle Module App__ ta thêm ``buildFeatures`` và thiết lập ``dataBinding true`` vào trong thẻ ``android``
- __build.gradle (module:App)__
```js
android {

    buildFeatures {
        dataBinding true
    }
}

```
- click __Sync Now__ để Gradle đồng bộ dữ liệu
- tất nhiên, ta chỉ mới enable Data Binding chứ chưa thực hiện thao tác sử dụng nó
- Data Binding thường sử dụng với ViewModel class trong kiến trúc MVVM, vì thế ta sẽ tiến hành khai báo class ViewModel và thực hiện các bước sử dụng Data Binding

___

## VIEWMODEL

- xây dựng 1 class ViewModel, ở đây ta sẽ dựng class __MainViewModel__ để chuyên xử lý logic cho __MainActivity__, có nghĩa là class __MainViewModel__ này sẽ hoàn toàn thay thế __MainActivity__ trong việc sử lý logic
- giả sử class __MainViewModel__ quản lý TextView, có nhiệm vụ khởi tạo 1 đối tượng có TextView, set và get dữ liệu cho TextView
- __MainViewModel.java__
```java
public class MainViewModel {
    private String name;

    public MainViewModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

___

## LAYOUT DÙNG ĐỂ BIND DATA VỚI VIEWMODEL

- ở đây ta sử dụng luôn layout __activity_main__ để bind data giữa layout với đối tượng MainViewModel
- ta chỉnh sửa lại như sau:
    - cấp view group cao nhất là __layout_, bên trong ta khai báo __data__ và __ViewGroup__
    - khai báo sử dụng data (chỉ đến ViewModel được dùng để bind data)
    - khai báo ViewGroup (LinearLayout, FrameLayout, RelativeLayout, ....) nếu cần quản lý nhiều View, hoặc chỉ 1 View
- khai báo thẻ ``<layout></layout>`` là cấp __ViewGroup__ cao nhất
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android" 
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <!--  -->
</layout>
```

- khai báo thẻ ``<data></data>`` bên trong, và chỉ định ``<variable />`` cho thẻ ``data``
- thẻ ``variable`` có 2 thuộc tính là:
    - ``name``: nên đặt trùng tên với __ViewModel__ đang bind data
    - ``type``: hay địa chỉ dẫn đến class __ViewModel__
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="MainViewModel"
            type="com.example.databinding.MainViewModel" />
    </data>

    <!--  -->
</layout>
```

- tiếp tục, trong ``<layout></layout>`` khai báo các ViewGroup hoặc View cần binding data
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="MainViewModel"
            type="com.example.databinding.MainViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:text="@{MainViewModel.name}"
            android:textSize="20sp" />
    </LinearLayout>
</layout>
```
- ở trên ta thấy TextView sẽ dùng để hiển thị thuộc tính ``name`` từ ``MainViewModel`` với cú pháp:
    - ``@{ViewModel.THUỘC_TÍNH}`` : câu lệnh ràng buộc dữ liệu 1 chiều
    - ``@={ViewModel.THUỘC_TÍNH}`` : câu lệnh ràng buộc dữ liệu 2 chiều
- với cú pháp trên, thì bất cứ khi nào __MainViewModel__ thay đổi thuộc tính __name__ thì ngay lập tức TextView trên __activity_main.xml__ sẽ thay đổi theo

___

## MAIN ACTIVITY CLASS

- để class __MainViewModel__ và layout __activity_main__ có thể liên lạc được với nhau, phải thông qua class data binding được sinh ra khi __enable dataBinding__
- trong __MainActivity.java__ trước khi gọi function ``setContentView()`` ta khai báo đối tượng binding tương ứng với layout __activity_main.xml__ thì class binding là __ActivityMainBinding.java__
- __MainActivity.java__
```java
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
```

- khởi tạo đối tượng class binding thông qua chính class binding gọi đến function ``inflate(getLayoutInflater())``
- hoặc thông qua class ``DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main )`` 
- __MainActivity.java__
```java
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // cách 1
//        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        // cách 2
        mActivityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main );

    }
}
```

- khởi tạo đối tượng ViewModel ở đây là __MainViewModel__
- __MainActivity.java__
```java
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // cách 1
//        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
//        MainViewModel mainViewModel = new MainViewModel("Data Binding Tutorial");

        // cách 2
        mActivityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main );
        MainViewModel mainViewModel = new MainViewModel("Data Binding Tutorial");

    }
}
```

- thiết lập set dữ liệu ViewModel cho Binding class
- __MainActivity.java__
```java
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // cách 1
//        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
//        MainViewModel mainViewModel = new MainViewModel("Data Binding Tutorial");
//        mActivityMainBinding.setMainViewModel(mainViewModel);

        // cách 2
        mActivityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main );
        MainViewModel mainViewModel = new MainViewModel("Data Binding Tutorial");
        mActivityMainBinding.setMainViewModel(mainViewModel);

    }
}
```

- gọi function ``setContentView()`` và truyền vào chính là đối tượng Binding thực hiện function ``getRoot()``
- __MainActivity.java__
```java
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // cách 1
//        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
//        MainViewModel mainViewModel = new MainViewModel("Data Binding Tutorial");
//        mActivityMainBinding.setMainViewModel(mainViewModel);

        // cách 2
        mActivityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main );
        MainViewModel mainViewModel = new MainViewModel("Data Binding Tutorial");
        mActivityMainBinding.setMainViewModel(mainViewModel);

        setContentView(mActivityMainBinding.getRoot());
    }
}
```