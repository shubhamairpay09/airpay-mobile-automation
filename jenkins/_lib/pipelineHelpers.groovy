/**
 * Minimal Jenkins helpers for mobile pipelines (Windows + Unix).
 * load: def helpers = load 'jenkins/_lib/pipelineHelpers.groovy'
 */

def runCmd(String cmd) {
    if (isUnix()) {
        sh cmd
    } else {
        bat cmd
    }
}

def runCmdStatus(String cmd) {
    if (isUnix()) {
        return sh(script: cmd, returnStatus: true)
    }
    return bat(script: cmd, returnStatus: true)
}

def cleanReportDirs(String module) {
    def product = env.PRODUCT ?: 'capital'
    def dirs = [
            "target/${product}",
            "target/cucumber-reports/${module}",
            "target/surefire-reports/${module}"
    ]
    dirs.each { d ->
        if (fileExists(d)) {
            echo "Cleaning ${d}"
            if (isUnix()) {
                sh "rm -rf \"${d}\""
            } else {
                bat "if exist \"${d.replace('/', '\\\\')}\" rmdir /s /q \"${d.replace('/', '\\\\')}\""
            }
        }
    }
}

def writeCiOverrides(Map cfg) {
    def lines = []
    if (cfg.envName) lines << "env=${cfg.envName}"
    if (cfg.product) lines << "airpay.product=${cfg.product}"
    if (cfg.androidUdid) lines << "android.udid=${cfg.androidUdid}"
    if (cfg.otp) {
        def product = (cfg.product ?: env.PRODUCT ?: 'capital').toString()
        lines << "${product}.test.otp=${cfg.otp}"
    }
    if (cfg.a11yEnabled) lines << "a11y.enabled=${cfg.a11yEnabled}"
    if (cfg.a11yDeepScan) lines << "a11y.deep.scan=${cfg.a11yDeepScan}"
    if (cfg.a11yReportDir) lines << "a11y.report.dir=${cfg.a11yReportDir}"
    writeFile file: 'config/ci-overrides.properties', text: lines.join('\n') + '\n'
    echo "Wrote config/ci-overrides.properties (${lines.size()} keys)"
}

def suiteTags(String productTag, String suite, String extraTags) {
    def tags = productTag ?: '@airpay-capital'
    if (suite == 'smoke') tags += ' and @smoke and not @a11y and not @lms'
    if (suite == 'functional') tags += ' and @functional and not @a11y and not @lms'
    if (suite == 'a11y') tags += ' and @a11y'
    if (suite == 'regression') tags += ' and @regression and not @a11y and not @lms'
    if (extraTags?.trim()) tags += " and ${extraTags.trim()}"
    return tags
}

/**
 * One Windows command: install any missing JDK, Maven, Android SDK, Appium, and
 * UiAutomator2 driver, start Appium when port 4723 is closed, then run the suite.
 * cfg keys: module, product, productTag, test, envName, suite, udid, otp,
 * cucumberTags, extraTags, extraMaven, cleanReports.
 */
def runMobile(Map cfg) {
    if (isUnix()) {
        error('This job uses the Windows setup scripts. Run it on a Windows agent with the phone attached.')
    }
    def product = (cfg.product ?: 'capital').toString()
    def suite = (cfg.suite ?: 'smoke').toString()
    def otp = (cfg.otp ?: '').toString().replaceAll('[^0-9A-Za-z]', '')
    def extra = '-DforkCount=0 -Dandroid.newCommandTimeout=120 -Dandroid.uiautomator2ServerReadTimeoutMs=30000'
    if (cfg.extraMaven) {
        extra = "${extra} ${cfg.extraMaven}"
    }
    if (otp) {
        extra = "${extra} -D${product}.test.otp=${otp}"
    }
    def clean = cfg.cleanReports ? 'Yes' : 'No'
    def udid = (cfg.udid ?: '').toString().replace('"', '')
    def cucumberTags = (cfg.cucumberTags ?: '').toString().replace('"', '')
    def extraTags = (cfg.extraTags ?: '').toString().replace('"', '')
    def script = """@echo off
set \"MODULE=${cfg.module}\"
set \"PRODUCT=${product}\"
set \"PRODUCT_TAG=${cfg.productTag}\"
set \"TEST=${cfg.test}\"
set \"ENV=${cfg.envName ?: 'live'}\"
set \"SUITE=${suite}\"
set \"OPEN_ALLURE=No\"
set \"CLEAN_PREVIOUS_REPORTS=${clean}\"
set \"PREFERRED_UDID=${udid}\"
set \"CUCUMBER_TAGS=${cucumberTags}\"
set \"EXTRA_TAGS=${extraTags}\"
set \"EXTRA_MAVEN=${extra}\"
call scripts\\_lib\\run-mobile-product.bat
"""
    return runCmdStatus(script)
}

def publishAllure(String product) {
    def code = runCmdStatus("call scripts\\reports\\open-allure.bat ${product}")
    if (code != 0) {
        echo "Allure HTML was not generated (exit ${code}). Results stay under target/${product}/allure-results."
    }
}

return this
