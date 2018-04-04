/*
Navicat MySQL Data Transfer

Source Server         : mysql5.7.20
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-04-04 17:26:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `article_id` int(11) NOT NULL AUTO_INCREMENT,
  `content` longtext COMMENT '文章内容',
  `title` varchar(255) DEFAULT '',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('23', '`LoadSir`是一个高效易用，低碳环保，扩展性良好的加载反馈页管理框架，在加载网络或其他数据时候，根据需求切换状态页面，可添加自定义状态页面，如加载中，加载失败，无数据，网络超时，占位图，登录失效等常用页面。可配合网络加载框架，结合返回状态码，错误码，数据进行状态页自动切换，封装使用效果更佳。\nLoadSir现在版本已经升级至1.3.6，相关内容请参考Github最新说明**[Github传送门](https://github.com/KingJA/LoadSir)**\n\n本文前面是使用流程，基于1.2.2完成，后面是原理解析，如果大家有兴趣，可耐心看完。\n\n效果预览\n---\n| **in Activity**|**in View**|**in Fragment**|\n|:---:|:----:|:----:|\n|![](http://upload-images.jianshu.io/upload_images/1411177-04c45b9a5b593767.gif?imageMogr2/auto-orient/strip)|![](http://upload-images.jianshu.io/upload_images/1411177-7f69d11c38e35022.gif?imageMogr2/auto-orient/strip)|![](http://upload-images.jianshu.io/upload_images/1411177-56c1bfc5c0afd594.gif?imageMogr2/auto-orient/strip)|\n\n| **Placeholder**|**Muitl-Fragment**|**ViewPage+Fragment**|\n|:---:|:----:|:----:|\n|![](http://upload-images.jianshu.io/upload_images/1411177-375aa91b97c77959.gif?imageMogr2/auto-orient/strip)|![](http://upload-images.jianshu.io/upload_images/1411177-2dfef21f5c99e851.gif?imageMogr2/auto-orient/strip)|![](http://upload-images.jianshu.io/upload_images/1411177-795e29c2c584656b.gif?imageMogr2/auto-orient/strip)|\n\n使用场景\n---\n下面为大家常见的加载反馈页面：\n\n| **loading**|**error**|**timeout**|\n|:---:|:----:|:----:|\n|![](http://upload-images.jianshu.io/upload_images/1411177-4ff5baaeac614661.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![](http://upload-images.jianshu.io/upload_images/1411177-be3ab1f823af479a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![](http://upload-images.jianshu.io/upload_images/1411177-2eba000af858ab29.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|\n\n| **empty**|**custom**|**placeholder**|\n|:---:|:----:|:----:|\n|![](http://upload-images.jianshu.io/upload_images/1411177-41c45decb8b31c97.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![](http://upload-images.jianshu.io/upload_images/1411177-a17c7015f0d00172.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![](http://upload-images.jianshu.io/upload_images/1411177-8a37cfb2ad80027f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|\n\n面对这么多状态页面，你是不是还在用include的方式，setVisibility(View.VISIBLE/GONE)，这种方式即不方便控制，也造成了视图层级冗余（你要把所有状态布局include进一个视图）。如果有一种工具，能把这些事都做了就好了。恰好，  `LoadSir` 把这些事做了，接下来我们就来了解一下它。\n\nLoadSir的功能及特点\n---\n* 支持Activity，Fragment，Fragment(v4)，View状态回调\n* 适配多个Fragment切换，及Fragment+ViewPager切换，不会状态叠加或者状态错乱\n* 利用泛型转换输入信号和输出状态，可根据网络返回体的状态码或者数据返回自动适配状态页，实现全局自动状态切换\n* 只加载唯一一个状态视图，不会预加载全部视图\n* 可保留标题栏(Toolbar,titile view等)\n* 可设置重新加载点击事件(OnReloadListener)\n* 可自定义状态页(继承Callback类)\n* 可在子线程直接切换状态\n* 可设置初始状态页(常用进度页作为初始状态)\n* 不需要设置枚举或者常量状态值，直接用状态页类类型(xxx.class)作为状态码\n* 可扩展状态页面，在配置中添加自定义状态页\n* 可对单个状态页单独设置点击事件，根据返回boolean值覆盖或者结合OnReloadListener使用，如网络错误可跳转设置页\n* 可全局单例配置，也可以单独配置\n* 无预设页面，低耦合，开发者随心配置\n\n开始使用LoadSir\n---\nLoadSir的使用只需要简单的三步，三步上篮的三步。\n###### 添加依赖\n\n```groovy\ncompile \'com.kingja.loadsir:loadsir:1.3.6\'\n```\n## 第一步： 配置\n\n###### 全局配置方式\n全局配置方式，使用的是单例模式，即获取的配置都是一样的。可在Application中配置，添加状态页，设置初始化状态页，建议使用这种配置方式。\n```java\npublic class App extends Application {\n    @Override\n    public void onCreate() {\n        super.onCreate();\n        LoadSir.beginBuilder()\n                .addCallback(new ErrorCallback())//\'添加各种状态页\n                .addCallback(new EmptyCallback())\n                .addCallback(new LoadingCallback())\n                .addCallback(new TimeoutCallback())\n                .addCallback(new CustomCallback())\n                .setDefaultCallback(LoadingCallback.class)//设置默认状态页\n                .commit();\n    }\n}\n```\n###### 单独配置方式\n如果你即想保留全局配置，又想在某个特殊页面加点不同的配置，可采用该方式。\n\n```java\nLoadSir loadSir = new LoadSir.Builder()\n                .addCallback(new LoadingCallback())\n                .addCallback(new EmptyCallback())\n                .addCallback(new ErrorCallback())\n                .build();\n        loadService = loadSir.register(this, new Callback.OnReloadListener() {\n            @Override\n            public void onReload(View v) {\n                // 重新加载逻辑\n            }\n        });\n```\n## 第二步： 注册\n\n###### 在Activity中使用\n\n```java\n@Override\nprotected void onCreate(@Nullable Bundle savedInstanceState) {\n    super.onCreate(savedInstanceState);\n    setContentView(R.layout.activity_content);\n    // Your can change the callback on sub thread directly.\n    LoadService loadService = LoadSir.getDefault().register(this, new Callback.OnReloadListener() {\n        @Override\n        public void onReload(View v) {\n            // 重新加载逻辑\n        }\n    });\n}}\n```\n\n###### 在View 中使用\n```java\nImageView imageView = (ImageView) findViewById(R.id.iv_img);\nLoadSir loadSir = new LoadSir.Builder()\n        .addCallback(new TimeoutCallback())\n        .setDefaultCallback(LoadingCallback.class)\n        .build();\nloadService = loadSir.register(imageView, new Callback.OnReloadListener() {\n    @Override\n    public void onReload(View v) {\n        loadService.showCallback(LoadingCallback.class);\n        // 重新加载逻辑\n    }\n});\n```\n###### 在Fragment 中使用\n由于Fragment添加到Activitiy方式多样，比较特别，所以在Fragment中注册方式不同于上面两种，大家先看模板代码：\n```java\n@Nullable\n@Override\npublic View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle\n        savedInstanceState) {\n    //第一步：获取布局View\n    rootView = View.inflate(getActivity(), R.layout.fragment_a_content, null);\n    //第二步：注册布局View\n    LoadService loadService = LoadSir.getDefault().register(rootView, new Callback.OnReloadListener() {\n        @Override\n        public void onReload(View v) {\n            // 重新加载逻辑\n        }\n    });\n    //第三步：返回LoadSir生成的LoadLayout\n    return loadService.getLoadLayout();\n}\n```\n\n## 第三步： 回调\n\n###### 直接回调\n```java\nprotected void loadNet() {\n        // 进行网络访问...\n        // 进行回调\n        loadService.showSuccess();//成功回调\n        loadService.showCallback(EmptyCallback.class);//其他回调\n    }\n```\n###### 转换器回调 (推荐使用)\n如果你不想再每次回调都要手动进行的话，可以选择注册的时候加入转换器，可根据返回的数据，适配对应的回调。\n\n```java\nLoadService loadService = LoadSir.getDefault().register(this, new Callback.OnReloadListener() {\n    @Override\n    public void onReload(View v) {\n            // 重新加载逻辑\n    }}, new Convertor<HttpResult>() {\n    @Override\n    public Class<? extends Callback> map(HttpResult httpResult) {\n        Class<? extends Callback> resultCode = SuccessCallback.class;\n        switch (httpResult.getResultCode()) {\n            case SUCCESS_CODE://成功回调\n                if (httpResult.getData().size() == 0) {\n                    resultCode = EmptyCallback.class;\n                }else{\n                    resultCode = SuccessCallback.class;\n                }\n                break;\n            case ERROR_CODE:\n                resultCode = ErrorCallback.class;\n                break;\n        }\n        return resultCode;\n    }\n});\n```\n回调的时候直接传入转换器指定的数据类型。\n```java\nloadService.showWithConvertor(httpResult);\n```\n### 自定义回调页\nLoadSir为了完全解耦，没有预设任何状态页，开发者根据需求自定义自己的回调页面，比如加载中，没数据，错误，超时等常用页面，\n设置布局及自定义点击逻辑\n\n```java\npublic class CustomCallback extends Callback {\n    @Override\n    protected int onCreateView() {\n        return R.layout.layout_custom;\n    }\n\n    @Override\n    protected boolean onRetry(final Context context, View view) {\n        //布局点击事件\n        Toast.makeText(context.getApplicationContext(), \"Hello mother fuck! :p\", Toast.LENGTH_SHORT).show();\n        //子控件事件\n        (view.findViewById(R.id.iv_gift)).setOnClickListener(new View.OnClickListener() {\n            @Override\n            public void onClick(View v) {\n                Toast.makeText(context.getApplicationContext(), \"It\'s your gift! :p\", Toast.LENGTH_SHORT).show();\n            }\n        });\n        return true;//返回true则覆盖了register时传入的重试点击事件，返回false则两个都执行\n    }\n\n    //是否在显示Callback视图的时候显示原始图(SuccessView)，返回true显示，false隐藏\n    @Override\n    public boolean getSuccessVisible() {\n        return super.getSuccessVisible();\n    }\n\n    //将Callback添加到当前视图时的回调，View为当前Callback的布局View\n    @Override\n    public void onAttach(Context context, View view) {\n        super.onAttach(context, view);\n    }\n\n    //将Callback从当前视图删除时的回调，View为当前Callback的布局View\n    @Override\n    public void onDetach() {\n        super.onDetach(context, view);\n    }\n}\n```\n### 动态修改Callback\n\n```java\nloadService = LoadSir.getDefault().register(...);\nloadService.setCallBack(EmptyCallback.class, new Transport() {\n   @Override\n   public void order(Context context, View view) {\n       TextView mTvEmpty = (TextView) view.findViewById(R.id.tv_empty);\n       mTvEmpty.setText(\"fine, no data. You must fill it!\");\n   }\n});\n```\n\n### 代码混淆\n\n```xml\n-dontwarn com.kingja.loadsir.**\n-keep class com.kingja.loadsir.** {*;}\n```\n\n### 占位图布局效果\nplaceholder效果状态页类似[ShimmerRecyclerView](https://github.com/sharish/ShimmerRecyclerView)的效果. LoadSir只用了一个自定义状态页PlaceHolderCallback就完成类似的效果，是不是很棒 :p\n\n\n看到这，想必各位使用LoadSir应该没问题了，如果还想再进一步了解它的内部结构，可以继续往下看。\n\n原理解析\n---\n### 流程图\n![](http://upload-images.jianshu.io/upload_images/1411177-a0828e1626e834f6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)\n\n### 关键类\n* LoadSir：提供单例模式获取全局唯一实例，内部保存配置信息，根据配置创建LoadService。\n* LoadService：具体操作服务类，提供showSuccess，showCallback，showWithCoverator等方法来进行状态页回调。\n* LoadLayout：最终显示在用户面前的视图View，替换了原布局，是LoadService直接操作对象，要显示的状态页的视图会被添加到LoadLayout上。\n* Callback：状态页抽象类，抽象自定义布局和自定义点击事件两个方法留给子类实现。\n* Coverator：转换接口，可将网络返回实体转换成对应的状态页，达到自动适配状态页的目的。\n\n我们直接观察在Activity中普通加载和使用LoadSir加载视图的区别\n###### >>>没使用LoadSir\n![](http://upload-images.jianshu.io/upload_images/1411177-099cf4327cb5f4f2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)\n\n###### >>>使用LoadSir\n![](http://upload-images.jianshu.io/upload_images/1411177-a7ce57635f7a753e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)\n\n大家可以看到，LoadSir用LoadLayout把原来的布局给替代掉了，原来的布局加在了LoadLayout上，其它自定义的状态页也同样会被加到这个LoadLayout上(显示的时候)，而且LoadLayout的子View只有一个，就是当前要显示的状态页布局，并没有把当前不显示的比如加载中布局，错误布局，无数据布局加载进来，这也是LoadSir的优点之一，按需加载，并且只加载一个状态布局。\n###### >>>替换逻辑\n```java\npublic static TargetContext getTargetContext(Object target) {\n        ViewGroup contentParent;\n        Context context;\n        if (target instanceof Activity) {\n            Activity activity = (Activity) target;\n            context = activity;\n            contentParent = (ViewGroup) activity.findViewById(android.R.id.content);\n        } else if (target instanceof View) {\n            View view = (View) target;\n            contentParent = (ViewGroup) (view.getParent());\n            context = view.getContext();\n        } else {\n            throw new IllegalArgumentException(\"The target must be within Activity, Fragment, View.\");\n        }\n       ...\n        if (contentParent != null) {\n            contentParent.removeView(oldContent);\n        }\n        return new TargetContext(context, contentParent, oldContent, childIndex);\n    }\n```\n大家可以看到，在Activity和View中的情况都比较简单，直接获取target的父控件，然后在父控件中替换掉该布局即可。在Fragment中，由于可能多个Fragment的布局View并存在一个父控件里，所以不能简单地使用父控件删除子View方式替换，也有可能父控件是ViewPager，不能通过addView()的方式添加LoadLayout。因此Fragment的注册方式是直接返回了LoadLayout到Activity上。这样也达到了一样的目的。\n\n\n下面是ViewPager+Fragment场景中使用LoadSir的视图，两个Fragment用各自的LoadLayout进行视图分离，避免了状态页叠加或错位。\n![](http://upload-images.jianshu.io/upload_images/1411177-8a03962401af83ca.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)\n看到这的童鞋应该也大概知道LoadSir是怎么回事了，如果想明白LoadSir的代码实现，请继续往下看。\n\n### 源码解析\n我们按上面三步上篮的步骤来稍微分析下源码\n###### >>>第一步：配置\n单例模式获取LoadSir，在LoadSir构造的时候创建默认配置\n```java\npublic static LoadSir getDefault() {\n        if (loadSir == null) {\n            synchronized (LoadSir.class) {\n                if (loadSir == null) {\n                    loadSir = new LoadSir();\n                }\n            }\n        }\n        return loadSir;\n    }\n\n    private LoadSir() {\n        this.builder = new Builder();\n    }\n```\nBuilder主要提供添加状态页，和设置默认状态页的方法\n```java\npublic static class Builder {\n        private List<Callback> callbacks = new ArrayList<>();\n        private Class<? extends Callback> defaultCallback;\n\n        public Builder addCallback(Callback callback) {\n            callbacks.add(callback);\n            return this;\n        }\n\n        public Builder setDefaultCallback(Class<? extends Callback> defaultCallback) {\n            this.defaultCallback = defaultCallback;\n            return this;\n        }\n      ...\n        public LoadSir build() {\n            return new LoadSir(this);\n        }\n\n    }\n```\nLoadSir提供beginBuilder()...commit()来设置全局配置。\n```java\npublic class LoadSir  {\n   ...\n    public static Builder beginBuilder() {\n        return new Builder();\n    }\n\n    public static class Builder {\n      \n        public void commit() {\n            getDefault().setBuilder(this);\n        }\n      ...\n    }\n}\n\n```\n\n###### >>>第二步：注册\nLoadSir注册后返回的是LoadService，一看名字大家就明白这是服务类，就是我们所说的Service层。\n```java\npublic LoadService register(Object target, Callback.OnReloadListener onReloadListener) {\n        return register(target, onReloadListener, null);\n    }\n\n    public <T> LoadService register(Object target, Callback.OnReloadListener onReloadListener, Convertor<T>\n            convertor) {\n        TargetContext targetContext = LoadSirUtil.getTargetContext(target);\n        return new LoadService<>(convertor, targetContext, onReloadListener, builder);\n    }\n```\n在LoadService的构造方法中根据target等信息创建Success视图，并且生成LoadLayout，相当于LoadSir每次注册都会创建一个LoadLayout。\n```java\nLoadService(Convertor<T> convertor, TargetContext targetContext, Callback\n            .OnReloadListener onReloadListener, LoadSir.Builder builder) {\n        this.convertor = convertor;\n        Context context = targetContext.getContext();\n        View oldContent = targetContext.getOldContent();\n        loadLayout = new LoadLayout(context, onReloadListener);\n        loadLayout.addCallback(new SuccessCallback(oldContent, context,\n                onReloadListener));\n        if (targetContext.getParentView() != null) {\n            targetContext.getParentView().addView(loadLayout, targetContext.getChildIndex(), oldContent\n                    .getLayoutParams());\n        }\n        initCallback(builder);\n    }\n```\n###### >>>第三步：回调\nLoadService的三个回调方法最终调用的都是loadLayout.showCallback(callback);\n```java\npublic void showSuccess() {\n        loadLayout.showCallback(SuccessCallback.class);\n    }\n\n    public void showCallback(Class<? extends Callback> callback) {\n        loadLayout.showCallback(callback);\n    }\n\n    public void showWithConvertor(T t) {\n        if (convertor == null) {\n            throw new IllegalArgumentException(\"You haven\'t set the Convertor.\");\n        }\n        loadLayout.showCallback(convertor.map(t));\n    }\n```\n我们直接看LoadLayout的showCallback方法，先做Callback是否配置判断，然后进行线程安全操作。重点还是showCallbackView(callback);\n```java\npublic void showCallback(final Class<? extends Callback> callback) {\n        if (!callbacks.containsKey(callback)) {\n            throw new IllegalArgumentException(String.format(\"The Callback (%s) is nonexistent.\", callback\n                    .getSimpleName()));\n        }\n        if (LoadSirUtil.isMainThread()) {\n            showCallbackView(callback);\n        } else {\n            postToMainThread(callback);\n        }\n    }\n```\n这个方法可以说是最后的执行者，就做两件事，删除LoadLayout所有子View(重置)，添加指定的布局页View(回调)。\n```java\nprivate void showCallbackView(Class<? extends Callback> status) {\n        if (getChildCount() > 0) {\n            removeAllViews();\n        }\n        for (Class key : callbacks.keySet()) {\n            if (key == status) {\n                addView(callbacks.get(key).getRootView());\n            }\n        }\n    }\n```\n自此，LoadSir一个完整的配置，注册，回调的过程完成了。不知道你们明白了没，反正我是有点口渴了。\n\n总结\n---\n建议在Application中全局配置，在BaseActivity，BaseFragment或者MVP中封装使用，能极大的减少代码量，让你的代码更加优雅，生活更加愉快。时间和个人能力有限，如果大家发现需要改进的地方，欢迎提交issue。\n如果这个库对你有用的话，也请点个star：p **[Github传送门](https://github.com/KingJA/LoadSir)**', '优雅地处理加载中(loading)，重试(retry)和无数据(empty)等', '2018-04-04 09:56:04', '2018-04-04 09:56:04');
INSERT INTO `article` VALUES ('28', '`首先感谢大家阅读这篇文章`\n日常开发中，经常会遇到Activity和Activity，Activity和Fragment之间的传值场景，通常，大家会这么处理：\n**Activity和Activity之间传值**\n首先在起始Activity进行存值操作\n```java\nIntent intent = new Intent(activity, TargetActivity.class);\n        intent.putExtra(\"age\", 35);\n        intent.putExtra(\"name\", Jordan);\n        intent.putExtra(\"person\", (Serializable) new Person());\n        activity.startActivity(intent)\n        activity.startActivityForResult(intent, 100)//如果需要返回值\n```\n然后在目标Activity取值操作\n```java\n        int age = getIntent().getIntExtra(\"age\", 0);\n        String name = getIntent().getStringExtra(\"name\", \"\");\n        Person person = (Person) getIntent().getSerializableExtra(\"person\");\n```\n\n**Activity和Fragment之间传值**\n存数据\n```java\npublic static TargetFragment newInstance(int age,String name,Person person) {\n        TargetFragment target = new TargetFragment();\n        Bundle bundle = new Bundle();\n        bundle.putInt(\"age\",age);\n        bundle.putString(\"String\",position);\n        bundle.putSerializable(\"person\",person);\n        target.setArguments(bundle);\n        return target;\n    }\n```\n取数据\n```java\nint age= getArguments().getInt(\"age\");\nString name = getArguments().getString(\"name \");\nPerson person= (Person )getArguments().getSerializable(\"person\");\n```\n是不是觉得很繁琐，要写这么多模板代码，生命苦短，青春短暂。有强迫症的同学甚至会为每个传值变量取个常量名字，如果有工具能自动生成这些模板代码，我们只用在目标Activity或者Fragment直接调用数据就好了。\n咳咳咳，那么就请大家来尝试下实现这个需求的：[ActivityBus](https://github.com/KingJA/ActivityBus)\nActivityBus基于编译时注解开发，自动生成Activity或者Fragment代理类，代理类中封装了以上的传值和取值的代码，用户只需在目标Activity或者Fragment中需要传值的变量上添加注解就可以完成一键传值。节省下来的时间至少可以让我们在这炎炎夏日抽空喝杯凉开。\n\n那么我们就直接开搞吧\n\n### 在Activity间传值\n###### 第一步：添加注解\n\n@RequestParam：在目标Activity的需要传值的成员变量上添加该注解。\n@ActivityBus：如果你需要调用startActivityForResult()，在目标Activity 上添加该注解，并设置requestCode。\n\n```java\n@ActivityBus(requestCode = 100)\npublic class TargetActivity extends AppCompatActivity {\n    @RequestParam\n    public int age;\n    @RequestParam\n    public String name;\n    @RequestParam\n    public Person person;\n    ...\n}\n```\n\n###### 第二步：传递数据\n编译以后，ActivityBus会自动生成代理类，如TargetActivityBus，名称格式为【目标Actiity】+Bus，就是说，把你的Activty变成了一辆公交车，可以带客了，乘客就是你要传输的数据。之后调用代理类的goActivity()传入所需的数据即可。\n\n```java\npublic class MainActivity extends AppCompatActivity {\n    ...\n    TargetActivityBus.goActivity(this,1,\"Hello\",new Person(\"Entity\"));\n    ...\n}\n```\n\n###### 第三步：在目标Activity中进行注册\n在目标Activity中调用register()，相当于告诉公交车，我要到这来。乘客到这站就可以下车了。接下来你就可以对乘客do anything了。\n```java\n@ActivityBus(requestCode = 100)\npublic class TargetActivity extends AppCompatActivity {\n    @RequestParam\n    public int age;\n    @RequestParam\n    public String name;\n    @RequestParam\n    public Person person;\n...\n@Override\n    protected void onCreate(@Nullable Bundle savedInstanceState) {\n        super.onCreate(savedInstanceState);\n        setContentView(R.layout.activity_second);\n        ...\n        TargetActivityBus.register(this);\n        tv_name.setText(\"My name is\"+name);\n    }\n...\n}\n\n```\n\n### Activty和Fragment间传值\n在Fragment中使用更简单，只需要两步。\n###### 第一步：添加注解\n\n```java\npublic class TargetFragment extends Fragment {\n    @RequestParam\n    public int aInt;\n    @RequestParam\n    public String aString;\n    ...\n}\n```\n\n###### 第二步：传递数据\n调用Fragment代理类的newInstance()方法进行传值。\n```java\npublic class MainActivity extends AppCompatActivity {\n    ...\n    TargetFragment targetFragment = TargetFragmentBus.newInstance(1,\"Hello\");\n    getSupportFragmentManager().beginTransaction().add(R.id.fl_fragment, targetFragment).commit();\n    ...\n}\n```\n支持传值的数据类型\n---\n支持传值的数据类型基本包括了日常开发的大部分数据类型，其中 `List`,`Set`,`Map`也是同样简单的一键传值。实体类型需要实现Serializable接口。\n* Base type : `boolean`,`byte`,`char`,`short`,`int`,`long`,`float`,`double`,`String`\n* Base array type : `boolean[]`,`byte[]`,`char[]`,`short[]`,`int[]`,`long[]`,`float[]`,`double[]`,`String[]`\n* Container : `List`,`Set`,`Map`\n* Serializable : 实现Serializable接口的所有实体\n\n结尾\n---\n**[ActivityBus](https://github.com/KingJA/ActivityBus)**只适用模块内或者依赖模块间的显示调用，并不具备模块化组件间调用的能力。不过够用就行。如果对你有帮助，可以点个star支持下。具体的使用，请点**[Github传送](https://github.com/KingJA/ActivityBus)**。', '【写框架】基于编译时注解打造ActivityBus，一键传值', '2018-04-04 15:05:57', '2018-04-04 15:05:57');

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES ('29');
SET FOREIGN_KEY_CHECKS=1;
