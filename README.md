# DialogDemo

csdn传送门： [Android对话框详解](http://blog.csdn.net/dacongge/article/details/78965005)

> 前言：我想对十年前的自己说：“不要懒惰。”我想在十年后对十年前的自己说：“谢谢你的坚持。”

Anroid中对话框的使用频率还是相当高的，今天就参照**官方文档**把这些系统提供的对话框尽量全的整理一下，并把重点明确的标注出来。这样以后再使用的时候不用再去翻阅别人的博客或者官方文档了。

首先我们先来看一个本文中所介绍的所有对话框种类：

- **普通对话框**
- **传统单选列表对话框**
- **永久性单选列表对话框**
- **永久性多选列表对话框**
- **自定义对话框**
- **未完待续**

**Dialog**是对话框的基类，但我们应该避免直接使用它，而是使用其子类**AlertDialog**，我们可以直接使用AlertDialog，但是我们应该尽量使用DialogFragment来作为对话框的容器，官方文档上的说明是：**DialogFragment 类提供您创建对话框和管理其外观所需的所有控件，而不是调用 Dialog 对象上的方法。** 同时我们还可以确保其正确的处理生命周期和不同窗口大小的不同显示方式，以及对屏幕旋转的处理等。

使用时建议使用**android.support.v4.app.DialogFragment**，这样您的Dialog可以运行在Android1.6或更高的版本上。**android.app.DialogFragment**则支持3.0（API 11）及以上。

首先讲一下通用的部分，在DialogFragment中，我们需要在**onCreateDialog**回调方法中创建AlertDialog。然后在该方法中通过**AlertDialog.Builder**来对Dialog的样式进行配置，最后调用**builder.create()**方法来创建Dialog作为onCreateDialog方法的返回值及可。

首先我们看一下Fragment的写法：
```java
public class DFragment extends DialogFragment {
...
 @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int kind = bundle.getInt(DIALOG_KING);
        return getDialog(kind);
    }

```
这里的getDialog(kind)现在只需要知道是一个返回Dialog对象的方法即可。在使用中，我们只需要在onCreateDialog回调方法中配置好我们的Dialog，然后将该Dialog当做返回参数返回就可以了。

接下来我们在简单介绍一下怎么在外部使用DialogFragment显示对话框。
在需要显示对话框的位置（比如Activity中）调用如下代码即可：
```java
DFragment dFragment = new DFragment();
dFragment.show(getSupportFragmentManager(),"normal");
```
这样我们就可以显示出对话框了。

下面我们开始详细讲解Dialog具体的配置方法，也可以说是**AlertDialog.Builder**的使用讲解。


### 普通对话框 
点击按钮或者对话框外部，对话框消失。

- setTitle 设置标题
- setMessage 对话框具体内容
- setPositiveButton 设置“积极”按钮提示内容及点击事件
- setNegativeButton 设置“消极”按钮提示内容及点击事件
- setNeutralButton 设置“中性”按钮提示内容及点击事件，实际中可以做为“稍后提醒我”等功能键
- ***setCancelable***  设置false时无法通过点击外部或返回键取消对话框
- create 创建Dialog对象


**有的方法有不同参数的重载方法，可以根据需要使用**
```java
	/**
     * 普通对话框
     */
    public Dialog getNormalDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("标题")
                .setMessage("我是具体内容")
                .setPositiveButton("确定按钮", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("取消按钮", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton("中性按钮", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false);
        return builder.create();
    }
```
显示效果如下：
![普通对话框 ](http://img.blog.csdn.net/20180104142628522?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZGFjb25nZ2U=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

### 传统单选列表对话框 
点击列表中的列表项后，列表消失。

- setItems 设置展示列表
```java
	/**
     * 传统单选列表对话框
     */
    public Dialog getItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("传统单选列表")
                .setItems(new String[]{"小狗", "小兔", "小猫"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                Toast.makeText(DFragment.this.getActivity(), "小狗", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(DFragment.this.getActivity(), "小兔", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(DFragment.this.getActivity(), "小猫", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
        return builder.create();
    }
```
![传统单选列表对话框](http://img.blog.csdn.net/20180104144553375?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZGFjb25nZ2U=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

### 永久性单选列表对话框 
点击列表项，列表不消失，用户可以反复点击列表。点击按钮后对话框消失。

- setSingleChoiceItems 设置单选列表和点击事件

我们先看一下这个方法各个参数都是什么，我们选其中一个重载方法来看一下。
setSingleChoiceItems(CharSequence[] items, int checkedItem, final OnClickListener listener)
看参数名就可以猜个大概，**items** - 需要展示的数组、**checkedItem** - 默认选中的item、**listener** - 点击item的监听。可是如果我们不希望展示时有默认选中的item怎么办。大家的第一反应可能是google一把，但是google前我们不妨先看一下源码。初学时很多同学都很排斥源码，认为自己看不懂，但是任何事情都是有一个过程的，而且很多问题都是浮在源码表面的，比如这一个。
```java
		/**
         * Set a list of items to be displayed in the dialog as the content, you will be notified of
         * the selected item via the supplied listener. The list will have a check mark displayed to
         * the right of the text for the checked item. Clicking on an item in the list will not
         * dismiss the dialog. Clicking on a button will dismiss the dialog.
         *
         * @param items the items to be displayed.
         * @param checkedItem specifies which item is checked. If -1 no items are checked.
         * @param listener notified when an item on the list is clicked. The dialog will not be
         *        dismissed when an item is clicked. It will only be dismissed if clicked on a
         *        button, if no buttons are supplied it's up to the user to dismiss the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setSingleChoiceItems(CharSequence[] items, int checkedItem, final OnClickListener listener) {
            P.mItems = items;
            P.mOnClickListener = listener;
            P.mCheckedItem = checkedItem;
            P.mIsSingleChoice = true;
            return this;
        }
```
我们重点看一下注释中对第二个参数的解释。
@param checkedItem specifies which item is checked. **If -1 no items are checked.**
答案一目了然，如果我们不希望设置默认选中的item，只需要第二个参数传**-1**即可，就像之前所说的，这个问题的答案甚至不需要去追踪源码，他就在注释中。所以对于刚接触Android的同学，我们不要怕源码，只有逐渐的习惯看源码，我们才能不惧怕源码，毕竟没有什么事情是一蹴而就的，都需要一个过程。下面我们看一下实现代码。

```java
	/**
     * 永久性单选列表对话框
     */
    public Dialog getSingleChoiceDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("单选列表")
                .setSingleChoiceItems(new String[]{"小狗", "小兔", "小猫"}, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();

    }
```
效果图：
![永久性单选列表](http://img.blog.csdn.net/20180104144710699?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZGFjb25nZ2U=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)



### 永久性多选列表对话框
- setMultiChoiceItems 设置多选列表的方法
方法源码：
```java
		/**
         * Set a list of items to be displayed in the dialog as the content,
         * you will be notified of the selected item via the supplied listener.
         * The list will have a check mark displayed to the right of the text
         * for each checked item. Clicking on an item in the list will not
         * dismiss the dialog. Clicking on a button will dismiss the dialog.
         *
         * @param items the text of the items to be displayed in the list.
         * @param checkedItems specifies which items are checked. It should be null in which case no
         *        items are checked. If non null it must be exactly the same length as the array of
         *        items.
         * @param listener notified when an item on the list is clicked. The dialog will not be
         *        dismissed when an item is clicked. It will only be dismissed if clicked on a
         *        button, if no buttons are supplied it's up to the user to dismiss the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems,
                final OnMultiChoiceClickListener listener) {
            P.mItems = items;
            P.mOnCheckboxClickListener = listener;
            P.mCheckedItems = checkedItems;
            P.mIsMultiChoice = true;
            return this;
        }
```
该方法和setSingleChoiceItems相似，第二个参数checkedItems可以配置哪些item需要默认选中，源码注释中也写到了，如果不希望有默认选中的item，该项传null即可。

```java
	/**
     * 永久性多选列表对话框
     */
    public Dialog getMultiChoiceDialog() {
        list = new ArrayList();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("多选列表")
                .setMultiChoiceItems(new String[]{"小狗", "小兔", "小猫"}, new boolean[]{true, false, true}, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            list.add(which);
                        } else if (list.contains(which)) {//取消确认时判断保存的列表中是否有该position
                            // Else, if the item is already in the array, remove it
//                            list.remove(which);
                            list.remove(Integer.valueOf(which));
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();

    }
```
有一点需要稍微注意下，**list.remove(Integer.valueOf(which));**，在从列表中删除数据时，我们是按Integer对象移除的，不是按下标移除的，按下标移除是不合理的，所以一定要按上方的写法写，不要写成~~list.remove(which);~~
效果图：
![多选列表对话框](http://img.blog.csdn.net/20180104205156620?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZGFjb25nZ2U=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

### 自定义对话框
- setView 设置自定义对话框的视图
```java
public Builder setView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            P.mViewSpacingSpecified = false;
            return this;
        }
```
我们需要准备好自己写好的xml文件或者自定义的View，然后调用该方法即可。使用自定义试图的对话框时，我们还是可以给该对话框设置标题和按钮的，当然也可以不设置，完全使用自定义的xml视图。
具体代码：
```java
	 /**
     * 自定义对话框
     */
    public Dialog getDefineDialog() {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_signin, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("自定义布局")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();

    }
```
具体对view的操作在获取到View对象后进行就可以了。

xml文件布局：
```java
<?xml version="1.0" encoding="utf-8"?>
<android.support.v7.widget.LinearLayoutCompat
    android:orientation="vertical"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content">

    <android.support.v7.widget.AppCompatEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"/>

    <android.support.v7.widget.AppCompatEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"/>

</android.support.v7.widget.LinearLayoutCompat>
```
后续还会继续补充进度条Dialog等。

