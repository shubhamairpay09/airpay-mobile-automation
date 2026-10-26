package com.airpay.mobile.utilities.android;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class AdbCommands {

    private static final Logger LOG = LogManager.getLogger(AdbCommands.class);

    private AdbCommands() {
    }

    public static int shell(String udid, String... shellArgs) {
        List<String> cmd = prefix(udid);
        cmd.add("shell");
        cmd.addAll(List.of(shellArgs));
        return run(cmd, 20).exitCode();
    }

    public static String shellOut(String udid, int timeoutSec, String... shellArgs) {
        List<String> cmd = prefix(udid);
        cmd.add("shell");
        cmd.addAll(List.of(shellArgs));
        return run(cmd, timeoutSec).stdout();
    }

    public static void grant(String udid, String appPackage, String permission) {
        int code = shell(udid, "pm", "grant", appPackage, permission);
        if (code != 0) {
            LOG.debug("pm grant {} {} exit={}", appPackage, permission, code);
        }
    }

    public static void grantCommonRuntimePermissions(String udid, String appPackage) {
        String[] perms = {
                "android.permission.POST_NOTIFICATIONS",
                "android.permission.RECEIVE_SMS",
                "android.permission.READ_SMS",
                "android.permission.SEND_SMS",
                "android.permission.READ_PHONE_STATE",
                "android.permission.ACCESS_FINE_LOCATION",
                "android.permission.ACCESS_COARSE_LOCATION",
                "android.permission.CAMERA",
                "android.permission.RECORD_AUDIO",
                "android.permission.READ_CONTACTS",
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_MEDIA_IMAGES",
                "android.permission.READ_MEDIA_VIDEO"
        };
        for (String p : perms) {
            grant(udid, appPackage, p);
        }
    }

    public static String adbBinary() {
        for (String env : List.of("ANDROID_HOME", "ANDROID_SDK_ROOT")) {
            String home = System.getenv(env);
            if (home == null || home.isBlank()) {
                continue;
            }
            String exe = isWindows() ? "adb.exe" : "adb";
            Path candidate = Path.of(home, "platform-tools", exe);
            if (Files.isRegularFile(candidate)) {
                return candidate.toAbsolutePath().toString();
            }
        }
        return isWindows() ? "adb.exe" : "adb";
    }

    private static List<String> prefix(String udid) {
        List<String> cmd = new ArrayList<>();
        cmd.add(adbBinary());
        if (udid != null && !udid.isBlank()) {
            cmd.add("-s");
            cmd.add(udid.trim());
        }
        return cmd;
    }

    private static Result run(List<String> cmd, int timeoutSec) {
        try {
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            String out;
            try (InputStream in = process.getInputStream()) {
                out = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            }
            boolean finished = process.waitFor(timeoutSec, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                LOG.warn("adb timed out: {}", String.join(" ", cmd));
                return new Result(1, out);
            }
            return new Result(process.exitValue(), out);
        } catch (Exception e) {
            LOG.warn("adb failed: {} | {}", String.join(" ", cmd), e.toString());
            return new Result(1, "");
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase().contains("win");
    }

    private record Result(int exitCode, String stdout) {
    }
}
