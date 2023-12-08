package com.salty.canvas.model;

import lombok.Builder;
import lombok.Setter;

/**
 * @author wangcong
 * @description
 * @since 2023/12/07 16:03
 */
@Setter
@Builder
public class MonitorInfo {
    String diskInfo;
    long liveTime;
    @Override
    public String toString() {
        return diskInfo + "\nSaltyCloudCanvas has been has been running for " + liveTime / 1000 / 3600 + " minute(s)";
    }
}
