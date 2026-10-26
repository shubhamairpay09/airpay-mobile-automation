package com.airpay.mobile.utilities.locators;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PageXpathStoreTest {

    @Test
    void extractUsesClassNameXmlTagsAndSkipsUnnamedContainers() {
        String xml = """
                <?xml version='1.0' encoding='UTF-8' standalone='yes' ?>
                <hierarchy rotation="0">
                  <android.widget.FrameLayout class="android.widget.FrameLayout" clickable="false" bounds="[0,0][1080,2340]">
                    <android.widget.Button content-desc="Login With Mobile Number" clickable="true" enabled="true" bounds="[100,1800][980,1920]" />
                    <android.widget.EditText hint="Enter 10-Digit Number" clickable="true" focusable="true" enabled="true" bounds="[100,400][980,500]" />
                    <android.widget.CheckBox content-desc="Accept consent" checkable="true" clickable="true" enabled="true" bounds="[40,700][120,780]" />
                    <node class="android.widget.Button" content-desc="Send OTP" clickable="true" enabled="true" />
                  </android.widget.FrameLayout>
                </hierarchy>
                """;
        Map<String, String> xpaths = PageXpathStore.extract(xml);
        assertThat(xpaths).isNotEmpty();
        assertThat(xpaths.keySet()).noneMatch(k -> k.startsWith("unnamed"));
        assertThat(xpaths.values().stream().anyMatch(v -> v.contains("Login With Mobile Number"))).isTrue();
        assertThat(xpaths.values().stream().anyMatch(v -> v.contains("Enter 10-Digit Number"))).isTrue();
        assertThat(xpaths.values().stream().anyMatch(v -> v.contains("Accept consent"))).isTrue();
        assertThat(xpaths.values().stream().anyMatch(v -> v.contains("Send OTP"))).isTrue();
    }
}
