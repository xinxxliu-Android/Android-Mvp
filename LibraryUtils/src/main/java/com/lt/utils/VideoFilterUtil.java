package com.lt.utils;

/**
 *
 */
public final class VideoFilterUtil {
    public static void checkVideoDuration(long videoDuration) {
        if (videoDuration / 1000 > 60)
            throw new VideoFilterException("视频时长不能超过1分钟");
    }

    public static final class VideoFilterException extends RuntimeException {
        public VideoFilterException(String message) {
            super(message);
        }
    }
}
