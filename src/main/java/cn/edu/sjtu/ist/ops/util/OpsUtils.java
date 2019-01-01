/*
 * Copyright 2018 SJTU IST Lab
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.edu.sjtu.ist.ops.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

public class OpsUtils {
    public static String ETCD_JOBS_PATH = "ops/jobs";
    public static String ETCD_NODES_PATH = "ops/nodes";
    public static String ETCD_MAPCOMPLETED_PATH = "ops/shuffle/mapCompleted";
    public static String ETCD_SHUFFLECOMPLETED_PATH = "ops/shuffle/shuffleCompleted";
    public static String ETCD_REDUCETASKS_PATH = "ops/tasks/reduceTasks";
    public static String ETCD_REDUCENUM_PATH = "ops/shuffle/reduceNum";

    public static String buildKeyJob(String jobId) {
        return OpsUtils.ETCD_JOBS_PATH + "/job-" + jobId;
    }

    public static String buildKeyMapCompleted(String nodeIp, String jobId, String mapId) {
        return OpsUtils.ETCD_MAPCOMPLETED_PATH + "/mapCompleted-" + nodeIp + "-" + jobId + "-" + mapId;
    }

    public static String buildKeyShuffleCompleted(String dstNodeIp, String jobId, String num, String mapId) {
        return OpsUtils.ETCD_SHUFFLECOMPLETED_PATH + "/shuffleCompleted-" + dstNodeIp + "-" + jobId + "-" + num + "-"
                + mapId;
    }

    public static String buildKeyReduceTask(String nodeIp, String jobId, String reduceId) {
        return OpsUtils.ETCD_REDUCETASKS_PATH + "/reduceTasks-" + nodeIp + "-" + jobId + "-" + reduceId;
    }

    public static String buildKeyReduceNum(String nodeIp, String jobId, String reduceId) {
        return OpsUtils.ETCD_REDUCENUM_PATH + "/reduceNum-" + nodeIp + "-" + jobId + "-" + reduceId;
    }

    public static void initLocalDir(String localDir) {
        try {
            File dir = new File(localDir);
            if (!dir.exists()) {
                FileUtils.forceMkdir(new File(localDir));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readFully(InputStream in, byte[] buf, int off, int len) throws IOException {
        int toRead = len;
        while (toRead > 0) {
            int ret = in.read(buf, off, toRead);
            if (ret < 0) {
                throw new IOException("Premature EOF from inputStream");
            }
            toRead -= ret;
            off += ret;
        }
    }

    public static String getMapOutputPath(String jobId, String taskId, Integer num) {
        return "shuffle/" + jobId + "/" + taskId + "/" + "map_" + num + ".out";
    }
}