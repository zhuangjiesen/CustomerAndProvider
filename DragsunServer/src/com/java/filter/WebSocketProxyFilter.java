package com.java.filter;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by zhuangjiesen on 2017/9/16.
 */
public class WebSocketProxyFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        System.out.println(" i am doing WebSocketProxyFilter......");

//        filterChain.doFilter(servletRequest , servletResponse);
        HttpServletRequest httpRequest =(HttpServletRequest) servletRequest;


        Enumeration<String> headNames = httpRequest.getHeaderNames();
        boolean isWebSocketRequest = false;
        String connection = null;
        String upgrade = null;
        if (headNames != null) {
            while (headNames.hasMoreElements()) {
                String headName = headNames.nextElement();
                if ("connection".equals(headName.toLowerCase())) {
                    Enumeration<String> connnections = httpRequest.getHeaders(headName);
                    if (connnections != null && connnections.hasMoreElements()) {
                        String connectionTmp = connnections.nextElement();
                        if (connectionTmp != null && "upgrade".equals(connectionTmp.toLowerCase())) {
                            connection = connectionTmp;
                        }
                    }
                } else if ("upgrade".equals(headName.toLowerCase())) {
                    Enumeration<String> upgrades = httpRequest.getHeaders(headName);
                    if (upgrades != null && upgrades.hasMoreElements()) {
                        String upgradeTmp = upgrades.nextElement();
                        if (upgradeTmp != null && "websocket".equals(upgradeTmp.toLowerCase())) {
                            upgrade = upgradeTmp;
                        }
                    }
                }
            }
        }
        if (connection != null && upgrade != null) {
            isWebSocketRequest = true;
        }

        if (!isWebSocketRequest) {
            filterChain.doFilter(servletRequest , servletResponse);
            return;
        }

        String uri = httpRequest.getRequestURI();
        String url = "http://100.70.5.6:38888" + uri;
//        String url = "http://172.16.236.163:8080/lua-file";

        HttpClient httpClient = new DefaultHttpClient();

        // get method
        HttpGet httpGet = new HttpGet(url);

        // set header

        headNames = httpRequest.getHeaderNames();
        if (headNames != null) {
            while (headNames.hasMoreElements()) {
                String headName = headNames.nextElement();
                Enumeration<String> headValue = httpRequest.getHeaders(headName);
                if (headValue != null && headValue.hasMoreElements()) {
                    httpGet.setHeader(headName, headValue.nextElement());
                }
            }
        }
        httpGet.setHeader("Host" , "100.70.5.6:38888");

        //response
        HttpResponse response = null;

        final HttpGet get = httpGet;
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpResponse response = null;
                    try{
                        response = httpClient.execute(get);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("xxx");


        //get response into String
        String temp="";
        try{
            HttpEntity entity = response.getEntity();
            temp = EntityUtils.toString(entity,"UTF-8");
            System.out.println(" res  : " + temp);
        }catch (Exception e) {
            e.printStackTrace();
        }

        HttpServletResponse httpResponse =(HttpServletResponse) servletResponse;
        httpResponse.addHeader("connection" , "upgrade");
        httpResponse.addHeader("upgrade" , "websocket");
        httpResponse.setStatus(101);



    }

    public void destroy() {

    }
}
