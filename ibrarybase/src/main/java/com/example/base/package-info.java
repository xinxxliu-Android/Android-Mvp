/**
 * 该模块为：抽象界面模块
 *          AbstractBasexxx 为 根抽象，最上层
*                           其中集成了：toast统一展示，主线程handler(可用作当前模块 mvp通讯等)
 *          Basexxx         为 工具抽象，集成部分常用工具。如 路由工具，弹窗工具等并封装统一调度函数
 *                          其中集成了：router路由切换
 *          Simplexx        为 示例实现。当 当前页面无业务处理时，可使用{@link com.lt.base.SimpleActivity}或{@link com.lt.base.SimpleFragment} 作为纯数据展示
 */
package com.example.base;