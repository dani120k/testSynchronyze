package syncronization.update;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import syncronization.ProgrammConfig;
import syncronization.model.Domain;
import syncronization.model.OrgUnit;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class UpdateUserInfo {
    public static void updateOrgUnit(ProgrammConfig programmConfig, OrgUnit orgUnit)throws KeyStoreException, NoSuchAlgorithmException,
            KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> {
            System.out.println(chain[0].getIssuerX500Principal().getName());
            return true;
        };

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();


        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);

        String plainCreds = "admin:test";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.getEncoder().encodeToString(plainCredsBytes).getBytes();
        String base64Creds = new String(base64CredsBytes);


        RestTemplate restTemplate = new RestTemplate(requestFactory);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(orgUnit.toString(), headers);

        ResponseEntity<String> response = restTemplate
                .exchange("https://" + programmConfig.getServerHost() + ":" + programmConfig.getServerPort() +"/admin/update/orgunit", HttpMethod.POST, entity, String.class);


        String str  = response.getBody();
        System.out.println(str);
    }

    public static void updateDomain(ProgrammConfig programmConfig, Domain domain)throws KeyStoreException, NoSuchAlgorithmException,
            KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> {
            System.out.println(chain[0].getIssuerX500Principal().getName());
            return true;
        };

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();


        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);

        String plainCreds = "admin:test";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.getEncoder().encodeToString(plainCredsBytes).getBytes();
        String base64Creds = new String(base64CredsBytes);


        RestTemplate restTemplate = new RestTemplate(requestFactory);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(domain.toString(), headers);

        ResponseEntity<String> response = restTemplate
                .exchange("https://" + programmConfig.getServerHost() + ":" + programmConfig.getServerPort() +"/admin/update/domain", HttpMethod.POST, entity, String.class);


        String str  = response.getBody();
        System.out.println(str);
    }
}
