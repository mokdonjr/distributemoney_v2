package com.seungchan.distributemoney_v2.common.util;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

public class FileUtil {

    public static StringBuffer readFile(ClassPathResource classPathResource) throws IOException {
        URI uri = classPathResource.getURI();
        return readFile(uri.getPath());
    }

    public static StringBuffer readFile(String path) throws IOException {
        StringBuffer buffer = new StringBuffer();
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        while (true) {
            String s = br.readLine();
            if (s == null)
                break;
            buffer.append(s).append("\n");
        }
        br.close();
        return buffer;
    }
}
