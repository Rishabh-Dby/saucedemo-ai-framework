package com.rishabh.framework.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HealingStore {
    private static final ThreadLocal<List<HealingReport>> REPORTS = ThreadLocal.withInitial(ArrayList::new);

    private HealingStore() {
    }

    public static void add(HealingReport report) {
        REPORTS.get().add(report);
    }

    public static List<HealingReport> getReports() {
        return Collections.unmodifiableList(REPORTS.get());
    }

    public static void clear() {
        REPORTS.remove();
    }
}
