package org.springframework.core.io;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月17日 10:13:17
 */
public class DefaultResourceLoader implements ResourceLoader{

    private static final String CLASS_URL_PREFIX = "classpath:";

    @Override
    public Resource getResource(String location) {
        if(location.startsWith(CLASS_URL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASS_URL_PREFIX.length()));
        }else {
            try {
                //尝试当作URL来处理
                URL url = new URL(location);
                return new UrlResource(url);
            } catch(MalformedURLException e) {
                //当作文件系统下的资源处理
                return new FileSystemResource(location);
            }
        }
    }
}
