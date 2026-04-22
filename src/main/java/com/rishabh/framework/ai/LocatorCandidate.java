package com.rishabh.framework.ai;

import org.openqa.selenium.By;

public record LocatorCandidate(String name, By by) {
    @Override
    public String toString() {
        return name + " -> " + by;
    }
}
