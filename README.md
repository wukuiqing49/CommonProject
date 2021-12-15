## 自定义TextView 实现高亮显示手机号,邮箱,网址 以及实现可扩展收起的TextView

#### 引用

implementation 'com.github.wukuiqing49:CommonProject:v1.0.4'

#### 效果
<p>
<img src="imgs/defult.jpg" width="32%">
<img src="imgs/open.jpg" width="32%">
</p>

#### 使用

#####1.展示邮箱,手机号,网址
`< <com.wu.view.AutoLinkTextView
          android:layout_marginTop="20dp"
          android:layout_marginRight="20dp"
          android:layout_marginLeft="20dp"
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          app:useLongClick="true"
          android:text="296606241@qq.com 我的测试数据"
          tools:ignore="MissingConstraints" />>`  
