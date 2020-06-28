package com.quantchi.tianji.service.search.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description 
 * @author leiel
 * @Date 2020/1/16 2:40 PM
 */

public class PDFUtils {

    /**
     * 将PDF按页数每页转换成一个jpg图片
     * @param filePath
     * @return
     */
    public static List<String> pdfToImagePath(String filePath){
        List<String> list = new ArrayList<>();
        // 获取去除后缀的文件路径
        String fileDirectory = filePath.substring(0,filePath.lastIndexOf("."));

        String imagePath;

        try {
            FileInputStream input = new FileInputStream(filePath);

            File f = new File(fileDirectory);
            if(!f.exists()){
                f.mkdir();
            }
            PDDocument doc = PDDocument.load(input);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for(int i=0; i<pageCount; i++){
                // 方式1,第二个参数是设置缩放比(即像素)
                // BufferedImage image = renderer.renderImageWithDPI(i, 296);
                // 方式2,第二个参数是设置缩放比(即像素)
                // 第二个参数越大生成图片分辨率越高，转换时间也就越长
                BufferedImage image = renderer.renderImage(i, 2.5f);
                imagePath = fileDirectory + "/"+i + ".jpg";
                ImageIO.write(image, "PNG", new File(imagePath));
                list.add(imagePath);
            }
            doc.close();              //关闭文件,不然该pdf文件会一直被占用。
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        String filePath = "/Users/leiel/Downloads/hi.txt";
        List<String> imageList = pdfToImagePath(filePath);

        File file = new File("/Users/leiel/Downloads/operateManual");

        try {

            FileInputStream in = new FileInputStream(filePath);
            int size = in.available();
            System.out.println("可读取的字节数 " + size);
            Byte [] text = new Byte[size];
            for (int i = 0; i < text.length; i++) {
                text[i] = (byte)(in.read());
                System.out.print(text[i]);
            }
            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

            if(file.isDirectory()){

                File[] files = file.listFiles();

                if(files.length > 0){
                    System.out.println(files.length);
                }
            }

            Iterator<String> iterator = imageList.iterator();
            while(iterator.hasNext()){

                System.out.println(iterator.next());
            }
        }



    }


}
