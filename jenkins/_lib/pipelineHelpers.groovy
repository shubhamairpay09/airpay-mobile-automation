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
    if (suite == 'smoke') tags += ' and @smoke and not @a11y'
    if (suite == 'functional') tags += ' and @functional and not @a11y'
    if (suite == 'a11y') tags += ' and @a11y'
    if (suite == 'regression') tags += ' and @regression and not @a11y'
    if (extraTags?.trim()) tags += " and ${extraTags.trim()}"
    return tags
}

return this
