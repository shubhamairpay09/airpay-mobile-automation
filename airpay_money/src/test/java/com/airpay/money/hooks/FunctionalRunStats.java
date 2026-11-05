package com.airpay.money.hooks;

import io.cucumber.java.AfterAll;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

/** Live pass / fail / skip counts for the functional Money run. */
public final class FunctionalRunStats {

    private static final Logger LOG = LogManager.getLogger(FunctionalRunStats.class);
    private static final AtomicInteger PASSED = new AtomicInteger();
    private static final AtomicInteger FAILED = new AtomicInteger();
    private static final AtomicInteger SKIPPED = new AtomicInteger();

    private FunctionalRunStats() {
    }

    static void record(Scenario scenario) {
        switch (scenario.getStatus()) {
            case PASSED -> PASSED.incrementAndGet();
            case FAILED -> FAILED.incrementAndGet();
            case SKIPPED, PENDING, UNDEFINED, AMBIGUOUS -> SKIPPED.incrementAndGet();
            default -> {
            }
        }
    }

    @AfterAll
    public static void writeSummary() {
        int passed = PASSED.get();
        int failed = FAILED.get();
        int skipped = SKIPPED.get();
        int total = passed + failed + skipped;
        String line = String.format(
                "Money functional | total=%d | passed=%d | failed=%d | skipped=%d%n",
                total, passed, failed, skipped);
        LOG.info(line.trim());
        Path out = Path.of("target", "money", "functional-run-summary.txt");
        try {
            Files.createDirectories(out.getParent());
            Files.writeString(out, line);
        } catch (IOException e) {
            LOG.warn("Could not write {}", out, e);
        }
    }
}
