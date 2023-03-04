package utils;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
/*
 * 利用HttpClient进行post请求的工具类
 */
public class HttpsClientUtil {
    public static String doPost(String url,String body,Map headers){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = new sslzlr();
            httpPost = new HttpPost(url);

            StringEntity entity = new StringEntity(body);
            if(headers!=null&&headers.size()>0) {
                for (Object x : headers.keySet()) {
                    httpPost.setHeader(x.toString(),headers.get(x).toString());
                }
            }
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity);
                }
                Header[] x = response.getAllHeaders();
            }
        }catch(Exception e){
            if (e.getCause().toString().indexOf("303 See Other but no location header")!=-1)
            {
                return "需要登录才能进行该操作";
            }
            throw new RuntimeException(e);
        }
        return result;
    }
    public static String doPostfile(String url,Map<String,String> body,Map headers) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipartEntityBuilder.setCharset(Charset.forName("UTF-8"));
        String resultString ="";
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("DataEncoding", "UTF-8");
            if(headers!=null&&headers.size()>0) {
                for (Object x : headers.keySet()) {
                    httpPost.setHeader(x.toString(), headers.get(x).toString());
                }
            }
            for(String x:body.keySet()) {
                String value=body.get(x);
                if(value.startsWith("(text"))
                    multipartEntityBuilder.addTextBody(x, value.substring(6));
//                else
//                {
//                    String filename=value.substring(6);
//                    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//                    File y = new File(filename);
//                    ossClient.getObject(new GetObjectRequest(bucketName, sceneattach+filename),y);
//                    multipartEntityBuilder.addBinaryBody(x, y);
//                }
            }
            HttpEntity httpEntity = multipartEntityBuilder.build();
            httpPost.setEntity(httpEntity);
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            return resultString;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return resultString;
        }
    }
    public static String doPutfile(String url, Map<String,String> body,Map headers) {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = new sslzlr();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpPut httpPut = new HttpPut(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        httpPut.setConfig(requestConfig);
        httpPut.setHeader("DataEncoding", "UTF-8");
        if(headers!=null&&headers.size()>0) {
            for (Object x : headers.keySet()) {
                httpPut.setHeader(x.toString(), headers.get(x).toString());
            }
        }
        CloseableHttpResponse httpResponse = null;
        try {
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            multipartEntityBuilder.setCharset(Charset.forName("UTF-8"));
            for(String x:body.keySet()) {
                String value=body.get(x);
                if(value.startsWith("(text"))
                    multipartEntityBuilder.addTextBody(x, value.substring(6));
//                else
//                {
//                    String filename=value.substring(6);
//                    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//                    File y = new File(filename);
//                    ossClient.getObject(new GetObjectRequest(bucketName, sceneattach+filename),y);
//                    multipartEntityBuilder.addBinaryBody("file", y);
//                }
            }
            HttpEntity httpEntity = multipartEntityBuilder.build();
            httpPut.setEntity(httpEntity);
            httpResponse = httpClient.execute(httpPut);
            HttpEntity entity = httpResponse.getEntity();
            String result = EntityUtils.toString(entity);
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public static String sendhttpsfile(String url, Map paramerMap, Map headerMap, String method) {
        switch (method.toUpperCase()) {
            case "POST":
                return doPostfile(url, paramerMap, headerMap);
            case "PUT":
                return doPutfile(url, paramerMap,headerMap);
            default:
                return null;
        }
    }
    public static String sendhttps(String url, String paramerString, Map headerMap, String method) {
        String paramerString2="";
        try {
            paramerString2=new String(paramerString.getBytes("UTF-8"),"ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        switch (method.toUpperCase()) {
            case "POST":
                return doPost(url, paramerString2, headerMap);
            case "GET":{
                if(paramerString2!=null&&paramerString2.length()>0)
                    return doGet(url+"?"+paramerString2, headerMap);
                else
                    return doGet(url, headerMap);
            }
            case "PUT":
                return doPut(url, headerMap,paramerString2);
            case "DELETE":
                return doDelete(url, headerMap,paramerString2);
            default:
                return null;
        }
    }
    public static String doDelete(String url, Map headers,String body) {
        sslzlr httpClient = null;
        try {
            httpClient = new sslzlr();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        httpDelete.setConfig(requestConfig);
        httpDelete.setHeader("DataEncoding", "UTF-8");
        if(headers!=null&&headers.size()>0) {
            for (Object x : headers.keySet())
                httpDelete.setHeader(x.toString(), headers.get(x).toString());
        }
        CloseableHttpResponse httpResponse = null;
        try {
            StringEntity entity2 = new StringEntity(body);
            httpDelete.setEntity(entity2);
            httpResponse = httpClient.execute(httpDelete);
            HttpEntity entity = httpResponse.getEntity();
            String result = EntityUtils.toString(entity);
            return result;
        } catch (ClientProtocolException e) {
            if (e.getCause().toString().indexOf("303 See Other but no location header")!=-1)
            {
                return "需要登录才能进行该操作";
            }
            e.printStackTrace();
        } catch (IOException e) {
            if (e.getCause().toString().indexOf("303 See Other but no location header")!=-1)
            {
                return "需要登录才能进行该操作";
            }
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public static String doPut(String url, Map headers, String jsonStr) {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = new sslzlr();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpPut httpPut = new HttpPut(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        httpPut.setConfig(requestConfig);
        httpPut.setHeader("DataEncoding", "UTF-8");
        if(headers!=null&&headers.size()>0) {
            for (Object x : headers.keySet())
                httpPut.setHeader(x.toString(), headers.get(x).toString());
        }
        CloseableHttpResponse httpResponse = null;
        try {
            httpPut.setEntity(new StringEntity(jsonStr));
            httpResponse = httpClient.execute(httpPut);
            HttpEntity entity = httpResponse.getEntity();
            String result = EntityUtils.toString(entity);
            return result;
        } catch (ClientProtocolException e) {
            if (e.getCause().toString().indexOf("303 See Other but no location header")!=-1)
            {
                return "需要登录才能进行该操作";
            }
            e.printStackTrace();
        } catch (IOException e) {
            if (e.getCause().toString().indexOf("303 See Other but no location header")!=-1)
            {
                return "需要登录才能进行该操作";
            }
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public static String doGet(String url,Map headers){
        String response = null;
        try{
            DefaultHttpClient client = new sslzlr();

            URIBuilder builder = new URIBuilder(url);
            HttpGet request = new HttpGet(builder.build());
            if(headers!=null&&headers.size()>0) {
                for (Object x : headers.keySet())
                    request.setHeader(x.toString(), headers.get(x).toString());
            }
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(6000)
                    .setConnectTimeout(6000)
                    .setConnectionRequestTimeout(6000).build();
            request.setConfig(requestConfig);

            HttpResponse res = client.execute(request);
//            HttpEntity entity = res.getEntity();
            response = EntityUtils.toString(res.getEntity());// 返回json格式：
        } catch (Exception e) {
            if (e.getCause().toString().indexOf("303 See Other but no location header")!=-1)
            {
                return "需要登录才能进行该操作";
            }
            throw new RuntimeException(e);
        }
        return response;
    }
}