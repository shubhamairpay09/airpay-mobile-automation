package com.airpay.money.data;

import java.util.List;

/** Module inventory for Money deep crawl (maps to Java *Screen classes). */
public final class MoneyModuleInventory {

    public record Module(String id, String label, String... entryLabels) {
    }

    private MoneyModuleInventory() {
    }

    public static List<Module> primaryTabs() {
        return List.of(
                new Module("tab-finsight", "FinSight", "FinSight"),
                new Module("tab-products", "Products", "Products"),
                new Module("tab-calculators", "Calculators", "Calculators")
        );
    }

    public static Module startFinancialHealthCheck() {
        return new Module("start-financial-health-check", "Start Financial Health Check",
                "Start Financial Health Check");
    }

    public static List<Module> financialHealthCheckItems() {
        return List.of(
                new Module("what-is-fhc", "What is FHC", "What is FHC", "What is Financial Health Check", "What is FHC?"),
                new Module("how-it-works", "How it works", "How it works", "How it Works", "How FHC works"),
                new Module("start-fhc", "Start FHC", "Start FHC", "Start Financial Health Check")
        );
    }

    public static List<Module> resourcesChildren() {
        return List.of(
                new Module("about-us", "About Us", "About Us", "About airpay money", "About"),
                new Module("contact-us", "Contact Us", "Contact Us", "Contact us", "Contact"),
                new Module("testimonials", "Testimonials", "Testimonials", "Testimonial")
        );
    }

    public static List<Module> menuSections() {
        return List.of(
                new Module("menu-about", "About Us", "About Us", "About airpay money", "About"),
                new Module("menu-contact", "Contact Us", "Contact Us", "Contact us", "Contact"),
                new Module("menu-settings", "Settings", "Settings", "App Settings"),
                new Module("menu-faqs", "FAQs", "FAQs", "FAQ", "Help"),
                new Module("menu-privacy", "Privacy Policy", "Privacy Policy", "Privacy"),
                new Module("menu-terms", "Terms", "Terms and Conditions", "Terms & Conditions", "T&C"),
                new Module("menu-grievance", "Grievance", "Grievance", "Grievance Redressal"),
                new Module("menu-notifications", "Notifications", "Notifications", "Alerts")
        );
    }

    public static List<Module> productTiles() {
        return List.of(
                new Module("product-mf", "Mutual Funds", "Mutual Fund", "Mutual Funds", "SIP"),
                new Module("product-gold", "Digital Gold", "Digital Gold", "Gold"),
                new Module("product-fd", "Fixed Deposits", "Fixed Deposit", "FD"),
                new Module("product-nps", "NPS", "NPS", "National Pension"),
                new Module("product-insurance", "Insurance", "Insurance", "Health Insurance", "Life Insurance"),
                new Module("product-tax", "Tax", "Tax", "Tax Planning"),
                new Module("product-health", "Financial Health", "Financial Health", "Health Check", "Net Worth"),
                new Module("product-us-stocks", "US Stocks", "US Stock", "US Stocks"),
                new Module("product-sip", "SIP", "SIP", "Start SIP"),
                new Module("product-credit-card", "Credit Card", "Credit Card", "Credit Cards"),
                new Module("product-business-loan", "Business Loan", "Business Loan", "Apply for Loan")
        );
    }
}
