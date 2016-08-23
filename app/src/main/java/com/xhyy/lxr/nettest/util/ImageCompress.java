package com.xhyy.lxr.nettest.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageCompress {
	public static  File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(imageFileName, /* prefix */
				".jpg", /* suffix */
				storageDir /* directory */
		);
		return image;
	}
	public static File scal(Uri fileUri){
		Log.e("ImageCompress","-----------------------------------");
		String path = fileUri.getPath();
		File outputFile = new File(path);
		long fileSize = outputFile.length();
		final long fileMaxSize = 200 * 1024;
        Log.e("ImageCompress","fileSize"+fileSize);
		 if (fileSize >= fileMaxSize) {
             Log.e("ImageCompress","00000000000000000000000");
	            BitmapFactory.Options options = new BitmapFactory.Options();
	            options.inJustDecodeBounds = true;
	            BitmapFactory.decodeFile(path, options);
	            int height = options.outHeight;
	            int width = options.outWidth;
                Log.e("ImageCompress","111111111111111111111");
	            double scale = Math.sqrt((float) fileSize / fileMaxSize);
	            options.outHeight = (int) (height / scale);
	            options.outWidth = (int) (width / scale);
	            options.inSampleSize = (int) (scale + 0.5);
	            options.inJustDecodeBounds = false;
				options.inPreferredConfig= Bitmap.Config.ARGB_4444;
	            Bitmap bitmap = BitmapFactory.decodeFile(path, options);

	            outputFile = new File(PhotoUtil.createImageFile().getPath());
	            FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(outputFile);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            Log.e("ImageCompress", ">>>>>>>>>>>" + outputFile.length());
	            if (!bitmap.isRecycled()) {
	                bitmap.recycle();
	            }else{
	            	File tempFile = outputFile;
	            	outputFile = new File(PhotoUtil.createImageFile().getPath());
	                PhotoUtil.copyFileUsingFileChannels(tempFile, outputFile);
	            }
	        }
		 return outputFile;
	}
    public static File serverScal(Uri fileUri){
        Log.e("ImageCompress","------------重度压缩--------------");
        String path = fileUri.getPath();
        File outputFile = new File(path);
        long fileSize = outputFile.length();
        final long fileMaxSize = 60 * 1024;
        Log.e("ImageCompress","fileSize"+fileSize);
        if (fileSize >= fileMaxSize) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int height = options.outHeight;
            int width = options.outWidth;
            double scale = Math.sqrt((float) fileSize / fileMaxSize);
            options.outHeight = (int) (height / scale);
            options.outWidth = (int) (width / scale);
            options.inSampleSize = (int) (scale + 0.5);
            options.inJustDecodeBounds = false;
            options.inPreferredConfig= Bitmap.Config.ARGB_4444;
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);

            outputFile = new File(PhotoUtil.createImageFile().getPath());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(outputFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.e("ImageCompress", ">>>>>重度压缩>>>>>>" + outputFile.length());
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }else{
                File tempFile = outputFile;
                outputFile = new File(PhotoUtil.createImageFile().getPath());
                PhotoUtil.copyFileUsingFileChannels(tempFile, outputFile);
            }
        }
        return outputFile;
    }
}
