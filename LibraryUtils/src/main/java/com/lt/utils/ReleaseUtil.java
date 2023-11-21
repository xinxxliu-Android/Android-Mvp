package com.lt.utils;

import com.lt.func.IReleasable;

public final class ReleaseUtil {

    public static void release(IReleasable... releasable){
        if (releasable == null)
            return;
        for (IReleasable iReleasable : releasable) {
            if (iReleasable != null)
                iReleasable.release();
        }
    }

}
