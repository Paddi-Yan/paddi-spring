package com.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 对java.net.URL进行资源定位的实现类
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月17日 10:08:37
 */
public class UrlResource implements Resource{
    private final URL url;

    public UrlResource(URL url) {
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection urlConnection = this.url.openConnection();
        try {
            return urlConnection.getInputStream();
        } catch(IOException e) {
            throw e;
        }
    }
}
