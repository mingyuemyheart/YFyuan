# 更新日志

## [未发布]

## [1.18] -2019-03-07
- 【858552】【采集终端】我->关于我们，展示当前版本更新内容
- 【868568】【应用终端】追加强制更新功能
- 【886047】授权提示分为两类：未授权、授权过期。样式红色提醒，文言更友好话
- 【886048】数据详细界面，不提示是否更新最新版本数据，自动更新最新数据
- 【886049】终端与平台数据一致，实时同步
- 【886054】授权成功后，未取消提示，提示授权成功
- 【886055】首次自动登录有几率出现，登录失败，或登录我的信息中未显示中队名
- 【886063】【数据】选择大中队分类，将列表显示改成树形显示
- 【886069】数据】车辆、器材、灭火剂、输入类别和名称，顺序对调
- 【887040】【数据】过滤条件（类别和大中队），自动显示
- 【892636】追加默认设置我-自动登录功能
- 【894112】数据模块列表显示按照update降序排列，器材，灭火剂将显示的类型修改成名称
- 【900352】【物质器材灭火剂】追加车辆信息显示
- 【900357】【移动终端】消防器材/灭火剂列表中要显示车载器材
- 【900358】【移动应用端】也添加关于器材的统计，和前台一致
- 【900369】【移动终端】车辆的“所属机构”字段隐藏不在显示
- 【900379】【移动应用端】中大队 添加 “市本级/外区县”字段，其显示的内容与关联的大队一致
- 【886077】【移动应用端】地图自动显示周边数据
- 【886078】【移动应用端】在线车辆详细，文言显示问题（速度、油亮小数点后两位）
- 【857415】【移动终端】百度街景新版本Android 8.0.0 街景不正常显示
- 【857417】【移动端】【旧版预案】重点单位预案显示可以查看多个旧版预案
- 【859887】【移动应用端】统计页面的支队统计信息不正确
- 【903427】【应用终端-统计】重点单位高级查询数据重复

## [1.17] - 2019-01-18
### 变更
- 【837499】终端统计逻辑变更，半小时没有操作，不计入使用时长。对应人 @王泉
		- activity/bussiness/GeomEleBussiness.java
		- activity/MainActivity.java
		- utils/DbUtil.java
		
- 【837536】建构筑物分类对应，变更为建筑、装置、储罐、自定义分区（含总分区）等四类。对应人 @王泉
		- activity/MainActivity.java
		- activity/bussiness/CommonBussiness.java
		- activity/bussiness/DBUpdateBussiness.java
		- activity/bussiness/DeviceAuthBussiness.java
		- activity/bussiness/GeomEleBussiness.java
		- activity/logic/ZddwLogic.java
		- app/Constant.java
		- app/LvApplication.java
		- logic/network/OkHttpClientManager.java
		- model/entity/AppAccessDBInfo.java
		- model/entity/KeyUnitDBInfo.java
		- utils/DbUtil.java
## [1.16] - 2018-12-24
### 修复
- 【S08_0007】应用版地图选择支队图标，显示的选中图标不正确。对应人 @王泉
		- app/LvApplication.java
- 【B07_0008】离线库首次下载后，统计页面未更新。对应人 @王泉
		- activity/FragmentZqsl.java
- 选择地图上支队图标时，没有概要信息显示。对应人 @王泉
		- model/entity/StationDetachmentDBInfo.java
## [1.15] - 2018-12-13
### 新增	
- 对应全国账号，辽宁硬编码问题。对应人 @王泉
		- activity/MainActivity.java
		- activity/logic/CityListLogic.java
		- activity/logic/PropertyLogic.java
		- activity/logic/ZddwLogic.java
		- app/LvApplication.java
		- KeyDiagram/PlaneDiagram.java
### 修复
- 【S08_0005】上传关键图示上传，需要关联分区。对应人 @王泉
	 - activity/logic/KeyDiagramLogic.java

### 变更

### 优化

### 删除

### 安全