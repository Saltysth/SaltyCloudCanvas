package com.salty.canvas.service.impl;

import com.salty.canvas.constants.Constants;
import com.salty.canvas.model.MonitorInfo;
import com.salty.canvas.service.MonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author wangcong
 * @description
 * @since 2023/12/07 16:08
 */
@Service
@Slf4j
public class MonitorServiceImpl implements MonitorService {

    @Override
    public MonitorInfo getMonitorInfo() {
        StringBuilder diskInfo = new StringBuilder();
        try {
            String command = "df -hP"; // Replace "/" with the path you want to monitor

            Process process = new ProcessBuilder()
                .command("bash", "-c", command)
                .start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse and extract relevant information from the 'df' command output
                if (line.startsWith("/")) {
                    String[] parts = line.split("\\s+");
                    diskInfo.append("File System: " + parts[0] + '\n');
                    diskInfo.append("Used Space: " + parts[2] + '\n');
                    diskInfo.append("Capacity: " + parts[4] + '\n\n');
                }
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                log.info("获取磁盘信息成功");
            } else {
                log.info("获取磁盘信息失败, exitCode: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            log.error("获取服务器磁盘信息出错");
            e.printStackTrace();
        }
        return MonitorInfo.builder()
            .diskInfo(diskInfo.toString())
            .liveTime(System.currentTimeMillis() - Constants.startTime)
            .build();
    }
}
