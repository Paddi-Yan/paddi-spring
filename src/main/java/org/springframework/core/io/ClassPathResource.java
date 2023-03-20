package org.springframework.core.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * classpath下资源的实现类
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月17日 10:11:40
 */
public class ClassPathResource implements Resource{

    private final String path;

    public ClassPathResource(String path) {
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(this.path);
        if(stream == null) {
            throw new FileNotFoundException(this.path + " cannot be opened because it does not exist");
        }
        return stream;
    }
}
