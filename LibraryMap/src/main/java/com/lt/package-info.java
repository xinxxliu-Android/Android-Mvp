/**
 * 该模块为 地图模块
 * 所有地图显示操作，
 * 目前包含：
 * 高德地图    {@link com.lt.amap}
 * 谷歌地图    {@link com.lt.admap}
 * 如需操作，使用{@link com.lt.IMap}
 * 对地图本身进行操作。
 * 如需切换，使用MapManager进行配置、切换等其他操作
 */
package com.lt;
/**
 * 地图控件 抽象的生命周期
 * 对应 activity的生命周期
 */

import com.lt.IMapLifecycle;
/**
 *  地图控件配置设置
 *  一般为地图的配置如：
 *      初始化配置
 */
import com.lt.IMapSettings;
/**
 * 操作地图控件命令
 * 如：
 *      移动视角到指定位置
 *      设置缩放
 */
import com.lt.IMapCommon;
/**
 *  地图控件
 *  ui配置
 *  如：
 *      添加点
 *      添加线
 *      添加自定义图形
 *      移除点
 *      移除线
 *      移除图形
 *      释放所有图形等
 */
import com.lt.IMapUi;