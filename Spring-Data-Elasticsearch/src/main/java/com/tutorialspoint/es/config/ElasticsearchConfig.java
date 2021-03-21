package com.tutorialspoint.es.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.tutorialspoint.es.repository")
@ComponentScan(basePackages = "com.tutorialspoint.es.service.impl")
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

	/*
	 * @Value(
	 * "${elasticsearch.home:D:\\PersonalData\\elasticsearch-7.6.2-windows-x86_64\\elasticsearch-7.6.2}")
	 * private String elasticsearchHome;
	 */

	@Value("${elasticsearch.cluster.name:my-elasticsearch-cluster}")
	private String clusterName;

	/*
	 * @Bean public Client client() throws UnknownHostException { TransportClient
	 * client = null; try { final Settings elasticsearchSettings =
	 * Settings.builder().put("cluster.name", clusterName)
	 * .put("client.transport.sniff", true).put("xpack.security.user",
	 * "elastic:JUTVRhWwaU8Ov8UWQBIy") .put("xpack.ssl.certificate",
	 * "D:\\Tutorials\\elasticsearch-kibana-setup\\elastic-certificates.p12")
	 * .put("xpack.security.transport.ssl.enabled",
	 * "true").put("xpack.ssl.certificate_authorities",
	 * "D:\\Tutorials\\elasticsearch-kibana-setup\\elastic-stack-ca.p12") .build();
	 * client = new PreBuiltTransportClient(elasticsearchSettings); TransportAddress
	 * addresses = new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300);
	 * client.addTransportAddress(addresses); } catch (UnknownHostException e) {
	 * e.printStackTrace(); } return client; }
	 */

	private static final String CERT_FILE = "D:\\Tutorials\\elasticsearch-kibana-setup\\elastic-certificates.p12";
	private static final String CERT_PASSWORD = "JUTVRhWwaU8Ov8UWQBIy";
	private static final String USER_NAME = "elastic";
	private static final String USER_PASS = "JUTVRhWwaU8Ov8UWQBIy";

	@Override
	public RestHighLevelClient elasticsearchClient() {

		final ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo("localhost:9200")
				.usingSsl(createSSLContext()) // use the SSLContext with the client cert
				.withBasicAuth(USER_NAME, USER_PASS) // use the headers for authentication
				.build();

		return RestClients.create(clientConfiguration).rest();
	}

	public static KeyStore getKeyStore() throws Exception {
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		String password = "";
		FileInputStream in = null;
		try {
			in = new FileInputStream(CERT_FILE);
			keyStore.load(in, password.toCharArray());
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return keyStore;
	}

	private SSLContext createSSLContext() {
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");

			KeyManager[] keyManagers = getKeyManagers();

			sslContext.init(keyManagers, geTrustManagers(), null);

			return sslContext;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private KeyManager[] getKeyManagers() throws KeyStoreException, NoSuchAlgorithmException, IOException,
			CertificateException, UnrecoverableKeyException {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CERT_FILE)) {
			KeyStore clientKeyStore = KeyStore.getInstance("PKCS12");
			clientKeyStore.load(inputStream, CERT_PASSWORD.toCharArray());

			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(clientKeyStore, CERT_PASSWORD.toCharArray());
			return kmf.getKeyManagers();
		}
	}

	private TrustManager[] geTrustManagers() throws KeyStoreException, NoSuchAlgorithmException, IOException,
			CertificateException, UnrecoverableKeyException {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CERT_FILE)) {
			KeyStore clientKeyStore = KeyStore.getInstance("PKCS12");
			clientKeyStore.load(inputStream, CERT_PASSWORD.toCharArray());

			TrustManagerFactory kmf = TrustManagerFactory.getInstance("SunX509");
			kmf.init(clientKeyStore);
			return kmf.getTrustManagers();
		}
	}

	@Bean
	@Primary
	public ElasticsearchOperations elasticsearchTemplate() {
		return elasticsearchOperations();
	}

}
