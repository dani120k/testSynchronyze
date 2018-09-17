package syncronization.update;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import syncronization.model.HierarchyGroup;
import syncronization.model.OrgUnit;
import syncronization.model.UserInfo;

import javax.net.ssl.SSLContext;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;

public class StructureCreating {
    public static void createDomain(ProgrammConfig programmConfig, Domain domain)throws KeyStoreException, NoSuchAlgorithmException,
            KeyManagementException{
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
                .exchange("https://" + programmConfig.getServerHost() + ":" + programmConfig.getServerPort() +"/admin/create/domain", HttpMethod.POST, entity, String.class);


        String str  = response.getBody();
        System.out.println(str);
    }

    public static void createHierarchyGroup(ProgrammConfig programmConfig, HierarchyGroup hgroup) throws KeyStoreException, NoSuchAlgorithmException,
            KeyManagementException{
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
        HttpEntity<String> entity = new HttpEntity<>(hgroup.toString(), headers);


        System.out.println(entity.toString());
        ResponseEntity<String> response = restTemplate
                .exchange("https://" + programmConfig.getServerHost() + ":" + programmConfig.getServerPort() +"/admin/create/hgroup", HttpMethod.POST, entity, String.class);

        String str  = response.getBody();
        System.out.println(str);
    }


    public static void createUserInfo(ProgrammConfig programmConfig, UserInfo userInfo) throws KeyStoreException, NoSuchAlgorithmException,
            KeyManagementException{
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
        HttpEntity<String> entity = new HttpEntity<>(userInfo.toString(), headers);


        System.out.println(entity.toString());
        ResponseEntity<String> response = restTemplate
                .exchange("https://" + programmConfig.getServerHost() + ":" + programmConfig.getServerPort() +"/admin/add/userinfo", HttpMethod.POST, entity, String.class);

        String str  = response.getBody();
        System.out.println(str);
    }

    public static void createOrgUnit(ProgrammConfig programmConfig, OrgUnit orgUnit) throws KeyStoreException, NoSuchAlgorithmException,
            KeyManagementException{
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
        HttpEntity<String> entity = new HttpEntity<String>(orgUnit.toString(), headers);

        ResponseEntity<String> response = restTemplate
                .exchange("https://" + programmConfig.getServerHost() + ":" + programmConfig.getServerPort() +"/admin/create/orgunit", HttpMethod.POST, entity, String.class);

        String str  = response.getBody();
        System.out.println(str);
    }

    public static List<HierarchyGroup> getListOfHierarchyGroup(ProgrammConfig programmConfig)throws KeyStoreException, NoSuchAlgorithmException,
            KeyManagementException{
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
        HttpEntity<String> entity = new HttpEntity<String>("", headers);

        ResponseEntity<String> response = restTemplate
                .exchange("https://" + programmConfig.getServerHost() + ":" + programmConfig.getServerPort() +"/admin/get/hgroup", HttpMethod.GET, entity, String.class);

        System.out.println(response.getStatusCode());
        String str  = response.getBody();
        System.out.println(str);
        Gson gson = new Gson();
        String jsonOutput = str;
        Type listType = new TypeToken<List<HierarchyGroup>>(){}.getType();
        List<HierarchyGroup> groups = (List<HierarchyGroup>) gson.fromJson(jsonOutput, listType);
        for(int i = 0; i < groups.size(); i++)
            System.out.println(groups.get(i).toString());

        return groups;
    }

    public static List<Domain> getListOfDomain(ProgrammConfig programmConfig)throws KeyStoreException, NoSuchAlgorithmException,
            KeyManagementException{
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
        HttpEntity<String> entity = new HttpEntity<String>("", headers);

        ResponseEntity<String> response = restTemplate
                .exchange("https://" + programmConfig.getServerHost() + ":" + programmConfig.getServerPort() +"/admin/get/domain", HttpMethod.GET, entity, String.class);

        System.out.println(response.getStatusCode());
        String str  = response.getBody();
        System.out.println(str);
        Gson gson = new Gson();
        String jsonOutput = str;
        Type listType = new TypeToken<List<Domain>>(){}.getType();
        List<Domain> domains = (List<Domain>) gson.fromJson(jsonOutput, listType);
        for(int i = 0; i < domains.size(); i++)
            System.out.println(domains.get(i).toString());

        return domains;
    }

    public static List<UserInfo> getListOfUserInfo(ProgrammConfig programmConfig)throws KeyStoreException, NoSuchAlgorithmException,
            KeyManagementException{
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
        HttpEntity<String> entity = new HttpEntity<String>("", headers);

        ResponseEntity<String> response = restTemplate
                .exchange("https://" + programmConfig.getServerHost() + ":" + programmConfig.getServerPort() +"/admin/get/userinfo", HttpMethod.GET, entity, String.class);

        System.out.println(response.getStatusCode());
        String str  = response.getBody();
        System.out.println("output is " + str);
        Gson gson = new Gson();
        String jsonOutput = str;
        System.out.println(jsonOutput);
        Type listType = new TypeToken<UserInfo>(){}.getType();
        List<UserInfo> userInfos =  (List<UserInfo>)  gson.fromJson(jsonOutput, listType);
        for(int i = 0; i < userInfos.size(); i++)
            System.out.println(userInfos.get(i).toString());

        return userInfos;
    }

    public static List<OrgUnit> getListOfOrgUnits(ProgrammConfig programmConfig)throws KeyStoreException, NoSuchAlgorithmException,
            KeyManagementException{
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
        HttpEntity<String> entity = new HttpEntity<String>("", headers);

        ResponseEntity<String> response = restTemplate
                .exchange("https://" + programmConfig.getServerHost() + ":" + programmConfig.getServerPort() +"/admin/get/orgunits", HttpMethod.GET, entity, String.class);

        System.out.println(response.getStatusCode());
        String str  = response.getBody();
        System.out.println("output is " + str);
        Gson gson = new Gson();
        String jsonOutput = str;
        System.out.println(jsonOutput);
        Type listType = new TypeToken<OrgUnit>(){}.getType();
        List<OrgUnit> orgUnits =  (List<OrgUnit>)  gson.fromJson(jsonOutput, listType);
        for(int i = 0; i < orgUnits.size(); i++)
            System.out.println(orgUnits.get(i).toString());

        return orgUnits;
    }
}
