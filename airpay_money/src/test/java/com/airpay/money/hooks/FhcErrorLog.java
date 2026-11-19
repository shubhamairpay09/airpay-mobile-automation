package com.airpay.money.hooks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Stores every FHC error (system + assertion) so it can be raised later.
 */
public final class FhcErrorLog {

    private static final Logger LOG = LogManager.getLogger(FhcErrorLog.class);
    private static final Path FILE = Path.of("target", "money", "fhc-errors.log");
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static volatile String last = "";

    private FhcErrorLog() {
    }

    public static void record(String scenario, String kind, String detail) {
        String line = TIME.format(LocalDateTime.now())
                + " | " + safe(scenario)
                + " | " + safe(kind)
                + " | " + safe(detail).replace('\n', ' ');
        last = line;
        LOG.error("FHC error stored | {}", line);
        try {
            Files.createDirectories(FILE.getParent());
            Files.writeString(FILE, line + System.lineSeparator(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOG.warn("Could not write {}: {}", FILE, e.toString());
        }
    }

    public static String last() {
        return last;
    }

    public static Path file() {
        return FILE;
    }

    private static String safe(String value) {
        return value == null || value.isBlank() ? "-" : value.trim();
    }
}
