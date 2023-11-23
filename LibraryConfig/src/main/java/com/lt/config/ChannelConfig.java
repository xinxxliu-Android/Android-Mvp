package com.lt.config;

import com.blankj.utilcode.util.StringUtils;

import java.util.Locale;

/**
 * 渠道声明配置类。
 * 同步文件： channel.gradle
 */
public interface ChannelConfig {

    String CHONG_QING = "重庆";

    String MENG_DONG = "蒙东";

    String BEI_JING = "北京测试-测试";
    String QINGHAI_XINING = "青海-西宁";
    String GUO_HUA = "新能源-国华";

    String ZHONG_GUANG_HE = "新能源-中广核";

    String TEST_NAN_JING = "江苏测试-测试";
    String HE_BEI = "河北-石家庄";
    String SHAN_XI = "山西-太原";
    String XIN_NENG_YUAN = "新能源";
    String TAI_ZHOU = "江苏-泰州";
    String QING_NENG_YUAN = "新能源-清能院";
    String XI_NING_2 = "青海-西宁-2";

    static boolean isNanJingTest() {
        return StringUtils.equalsIgnoreCase(BuildConfig.CHANNEL_KEY, TEST_NAN_JING);
    }

    static boolean isTaiZhou() {
        return StringUtils.equalsIgnoreCase(BuildConfig.CHANNEL_KEY, TAI_ZHOU);
    }


    static boolean isChongQing() {
        return StringUtils.equalsIgnoreCase(BuildConfig.CHANNEL_KEY, CHONG_QING);
    }

    /**
     * 蒙东
     */
    static boolean isMengDong() {
        String channel = BuildConfig.CHANNEL_KEY.toLowerCase(Locale.ROOT);
        return channel.contains(MENG_DONG);
    }

    /**
     * 北京测试
     */
    static boolean isBeiJingTest() {
        String channel = BuildConfig.CHANNEL_KEY.toLowerCase(Locale.ROOT);
        return channel.contains(BEI_JING);
    }

    static boolean isQingHaiXiNing() {
        String channel = BuildConfig.CHANNEL_KEY.toLowerCase(Locale.ROOT);
        return channel.contains(QINGHAI_XINING);
    }

    static boolean isGuoHua() {
        String channel = BuildConfig.CHANNEL_KEY.toLowerCase(Locale.ROOT);
        return channel.contains(GUO_HUA);
    }

    static boolean isZhongGuangHe() {
        String channel = BuildConfig.CHANNEL_KEY.toLowerCase(Locale.ROOT);
        return channel.contains(ZHONG_GUANG_HE);
    }

    static boolean isHeBei() {
        String channel = BuildConfig.CHANNEL_KEY.toLowerCase(Locale.ROOT);
        return channel.contains(HE_BEI);
    }

    static boolean isXinNengYuan() {
        String channel = BuildConfig.CHANNEL_KEY.toLowerCase(Locale.ROOT);
        return channel.contains(XIN_NENG_YUAN);
    }

    static boolean isQingNengYuan() {
        String channel = BuildConfig.CHANNEL_KEY.toLowerCase(Locale.ROOT);
        return channel.contains(QING_NENG_YUAN);
    }

    static boolean isXiNing2() {
        String channel = BuildConfig.CHANNEL_KEY.toLowerCase(Locale.ROOT);
        return channel.contains(XI_NING_2);
    }

    static boolean isShanXi() {
        String channel = BuildConfig.CHANNEL_KEY.toLowerCase(Locale.ROOT);
        return channel.contains(SHAN_XI);
    }

    /**
     * 是否开放焦距功能
     */
    static boolean openFocalLength() {
        return isHeBei() || isXinNengYuan() || isXiNing2() ;//|| isBeiJingTest();
    }
}
