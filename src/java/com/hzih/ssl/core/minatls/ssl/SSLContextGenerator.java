package com.hzih.ssl.core.minatls.ssl;


import java.io.File;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import org.apache.mina.filter.ssl.KeyStoreFactory;
import org.apache.mina.filter.ssl.SslContextFactory;

/**
 * @author giftsam
 */
public class SSLContextGenerator
{
    private static String TRUST_STORE = "D:\\cert\\TrustKey.keystore";
    private static String TRUST_KEY_STORE_PASSWORD = "123qwe";

    private static String CLIENT_KEY_STORE = "D:\\cert\\fJuly1_666612345678945612.pfx";
    private static String CLIENT_KEY_STORE_PASSWORD = "123qwe";

    private static String SERVER_KEY_STORE = "D:\\cert\\vpnson_192.168.1.212.pfx";
    private static String SERVER_KEY_STORE_PASSWORD = "123qwe";
    
    
    public SSLContext getClientSslContext()
    {
        SSLContext sslContext = null;
        try
        {
            File keyStoreFile = new File(CLIENT_KEY_STORE);
            File trustStoreFile = new File(TRUST_STORE);

            if (keyStoreFile.exists() && trustStoreFile.exists())
            {
                final SslContextFactory sslContextFactory = new SslContextFactory();

                final KeyStoreFactory keyStoreFactory = new KeyStoreFactory();

                final KeyStoreFactory trustStoreFactory = new KeyStoreFactory();

                int trust_index = -1;
                if(TRUST_STORE.contains("."))
                    trust_index = TRUST_STORE.lastIndexOf(".") ;
                String trust_type = "jks";
                if(trust_index!=-1)
                    trust_type = TRUST_STORE.substring(trust_index+1,TRUST_STORE.length()) ;

                if(trust_type.equalsIgnoreCase("p12")||trust_type.equalsIgnoreCase("pfx")){
                    trustStoreFactory.setType("pkcs12");
                }
                if (trust_type.equalsIgnoreCase("jks")||trust_type.equalsIgnoreCase("keystore")){
                    trustStoreFactory.setType("JKS");
                }

                int index = -1;
                if(CLIENT_KEY_STORE.contains("."))
                    index = CLIENT_KEY_STORE.lastIndexOf(".") ;
                String type = "jks";
                if(index!=-1)
                    type = CLIENT_KEY_STORE.substring(index+1,CLIENT_KEY_STORE.length()) ;

                if(type.equalsIgnoreCase("p12")||type.equalsIgnoreCase("pfx")){
                    keyStoreFactory.setType("pkcs12");
                }
                if (type.equalsIgnoreCase("jks")||type.equalsIgnoreCase("keystore")){
                    keyStoreFactory.setType("JKS");
                }

                keyStoreFactory.setDataFile(keyStoreFile);
                keyStoreFactory.setPassword(CLIENT_KEY_STORE_PASSWORD);

                trustStoreFactory.setDataFile(trustStoreFile);
                trustStoreFactory.setPassword(TRUST_KEY_STORE_PASSWORD);


                final KeyStore keyStore = keyStoreFactory.newInstance();
                final KeyStore trustStore = trustStoreFactory.newInstance();

                sslContextFactory.setTrustManagerFactoryKeyStore(trustStore); //可信证书
                sslContextFactory.setKeyManagerFactoryKeyStore(keyStore);     //匹配证书
                sslContextFactory.setKeyManagerFactoryKeyStorePassword(CLIENT_KEY_STORE_PASSWORD);
                sslContext = sslContextFactory.newInstance();
                System.out.println("SSL provider is: " + sslContext.getProvider());
            }
            else
            {
                System.out.println("Keystore or Truststore file does not exist");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return sslContext;
    }



    public SSLContext getServerSslContext()
    {
        SSLContext sslContext = null;
        try
        {
            File keyStoreFile = new File(SERVER_KEY_STORE);
            File trustStoreFile = new File(TRUST_STORE);

            if (keyStoreFile.exists() && trustStoreFile.exists())
            {
                final SslContextFactory sslContextFactory = new SslContextFactory();
                final KeyStoreFactory keyStoreFactory = new KeyStoreFactory();
                final KeyStoreFactory trustStoreFactory = new KeyStoreFactory();

                int trust_index = -1;
                if(TRUST_STORE.contains("."))
                    trust_index = TRUST_STORE.lastIndexOf(".") ;
                String trust_type = "jks";
                if(trust_index!=-1)
                    trust_type = TRUST_STORE.substring(trust_index+1,TRUST_STORE.length()) ;

                if(trust_type.equalsIgnoreCase("p12")||trust_type.equalsIgnoreCase("pfx")){
                    trustStoreFactory.setType("pkcs12");
                }
                if (trust_type.equalsIgnoreCase("jks")||trust_type.equalsIgnoreCase("keystore")){
                    trustStoreFactory.setType("JKS");
                }

                int index = -1;
                if(SERVER_KEY_STORE.contains("."))
                    index = SERVER_KEY_STORE.lastIndexOf(".") ;
                String type = "jks";
                if(index!=-1)
                    type = SERVER_KEY_STORE.substring(index+1,SERVER_KEY_STORE.length()) ;

                if(type.equalsIgnoreCase("p12")||type.equalsIgnoreCase("pfx")){
                    keyStoreFactory.setType("pkcs12");
                }
                if (type.equalsIgnoreCase("jks")||type.equalsIgnoreCase("keystore")){
                    keyStoreFactory.setType("JKS");
                }
                keyStoreFactory.setDataFile(keyStoreFile);
                keyStoreFactory.setPassword(SERVER_KEY_STORE_PASSWORD);

                trustStoreFactory.setDataFile(trustStoreFile);
                trustStoreFactory.setPassword(TRUST_KEY_STORE_PASSWORD);

                final KeyStore keyStore = keyStoreFactory.newInstance();
                sslContextFactory.setKeyManagerFactoryKeyStore(keyStore);

                final KeyStore trustStore = trustStoreFactory.newInstance();
                sslContextFactory.setTrustManagerFactoryKeyStore(trustStore);
                sslContextFactory.setKeyManagerFactoryKeyStorePassword(SERVER_KEY_STORE_PASSWORD);
                sslContext = sslContextFactory.newInstance();
                System.out.println("SSL provider is: " + sslContext.getProvider());
            }
            else
            {
                System.out.println("Keystore or Truststore file does not exist");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return sslContext;
    }
}


