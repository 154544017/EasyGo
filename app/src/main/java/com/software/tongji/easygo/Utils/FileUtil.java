package com.software.tongji.easygo.Utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

//与文件操作有关的工具
public class FileUtil {
    //文件复制工具(从相册复制到newPath路径下)
    public static void copyFile(String oldPath, String newPath) {
        try {
            int byteRead;
            File oldFile = new File(oldPath);
            if (oldFile.exists()) {//文件存在时
                InputStream inStream = new FileInputStream(oldPath);//读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }
    //图片保存工具，将服务器中的图片保存到本地savePath下
    public static void saveImage(Context context, String imgUrl, String savePath){
        RequestOptions options = new RequestOptions()
                .override(Target.SIZE_ORIGINAL); //指定图片大小(原图)
        FutureTarget<File> target = Glide.with(context)
                .asFile()
                .load(imgUrl)
                .apply(options)
                .submit();
        try {
            final File imageFile = target.get();
            copyFile(imageFile.getPath(), savePath);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


    }

}
