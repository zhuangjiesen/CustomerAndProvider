package com.java.core.redis.customer;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import java.lang.reflect.Method;
import java.net.URI;

/**
 * @param
 * @Author: zhuangjiesen
 * @Description:
 * @Date: Created in 2018/12/10
 */
public class AppJedisPool extends JedisPool {

    public AppJedisPool() {
        super();
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, String host) {
        super(poolConfig, host);
    }

    public AppJedisPool(String host, int port) {
        super(host, port);
    }

    public AppJedisPool(String host) {
        super(host);
    }

    public AppJedisPool(String host, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(host, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public AppJedisPool(URI uri) {
        super(uri);
    }

    public AppJedisPool(URI uri, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(uri, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public AppJedisPool(URI uri, int timeout) {
        super(uri, timeout);
    }

    public AppJedisPool(URI uri, int timeout, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(uri, timeout, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password) {
        super(poolConfig, host, port, timeout, password);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, boolean ssl) {
        super(poolConfig, host, port, timeout, password, ssl);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(poolConfig, host, port, timeout, password, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, String host, int port) {
        super(poolConfig, host, port);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, String host, int port, boolean ssl) {
        super(poolConfig, host, port, ssl);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, String host, int port, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(poolConfig, host, port, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout) {
        super(poolConfig, host, port, timeout);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, boolean ssl) {
        super(poolConfig, host, port, timeout, ssl);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(poolConfig, host, port, timeout, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, int database) {
        super(poolConfig, host, port, timeout, password, database);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, int database, boolean ssl) {
        super(poolConfig, host, port, timeout, password, database, ssl);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, int database, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(poolConfig, host, port, timeout, password, database, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, int database, String clientName) {
        super(poolConfig, host, port, timeout, password, database, clientName);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, int database, String clientName, boolean ssl) {
        super(poolConfig, host, port, timeout, password, database, clientName, ssl);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, int database, String clientName, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(poolConfig, host, port, timeout, password, database, clientName, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int connectionTimeout, int soTimeout, String password, int database, String clientName, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(poolConfig, host, port, connectionTimeout, soTimeout, password, database, clientName, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, URI uri) {
        super(poolConfig, uri);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, URI uri, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(poolConfig, uri, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, URI uri, int timeout) {
        super(poolConfig, uri, timeout);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, URI uri, int timeout, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(poolConfig, uri, timeout, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, URI uri, int connectionTimeout, int soTimeout) {
        super(poolConfig, uri, connectionTimeout, soTimeout);
    }

    public AppJedisPool(GenericObjectPoolConfig poolConfig, URI uri, int connectionTimeout, int soTimeout, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(poolConfig, uri, connectionTimeout, soTimeout, sslSocketFactory, sslParameters, hostnameVerifier);
    }


    @Override
    public Jedis getResource() {
        Jedis jedis = super.getResource();
        JedisInterceptor jedisInterceptor = new JedisInterceptor(jedis);
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(jedisInterceptor);
        enhancer.setSuperclass(Jedis.class);
        return (Jedis)enhancer.create();
    }


    private static class JedisInterceptor implements MethodInterceptor {

        private Object target;

        public JedisInterceptor(Object target) {
            this.target = target;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            if (method.getName().equals("set") || method.getName().equals("get") ) {
                return null;
            }

            return method.invoke(target, objects);
        }
    }
}
