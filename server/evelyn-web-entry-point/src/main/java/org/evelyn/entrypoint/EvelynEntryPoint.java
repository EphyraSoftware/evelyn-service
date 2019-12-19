package org.evelyn.entrypoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import java.io.File;

@EnableZuulProxy
@SpringBootApplication
public class EvelynEntryPoint {
    public static void main(String[] args) {
        configureTrustStore();

        SpringApplication.run(EvelynEntryPoint.class, args);
    }

    private static void configureTrustStore() {
        var trustStore = "/etc/evelyn/truststore.p12";
        var trustStoreFile = new File(trustStore);
        if (trustStoreFile.isFile() && trustStoreFile.canRead()) {
            System.out.println("Configuring truststore.");
            System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
            System.setProperty("javax.net.ssl.trustStore", trustStore);
            System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        }
    }
}
