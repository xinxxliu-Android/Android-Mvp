package com.lt.admap.cache;

import android.graphics.drawable.Drawable;
import android.util.Log;

import org.osmdroid.tileprovider.modules.IFilesystemCache;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.util.StreamUtils;
import org.osmdroid.util.MapTileIndex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImgTileWriter implements IFilesystemCache {
    private String dir;

    public ImgTileWriter(String cacheDir) {
        if (!cacheDir.endsWith(File.separator)) {
            cacheDir += File.separator;
        }
        dir = cacheDir;
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    public boolean saveFile(final ITileSource pTileSourceInfo, final long pMapTileIndex, final InputStream pStream, final Long pExpirationTime) {
        FileOutputStream bos = null;
        File file = null;
        try {
            int zoom = MapTileIndex.getZoom(pMapTileIndex);
            int x = MapTileIndex.getX(pMapTileIndex);
            int y = MapTileIndex.getY(pMapTileIndex);
            file = new File(dir + ""+x +""+y+".png");
            File parent = file.getParentFile();
            if (!parent.exists()) parent.mkdirs();
            if (file.exists()) {
                return true;
            }

            bos = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = pStream.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.close();
            pStream.close();
        } catch (Throwable ex) {
            if (file != null) file.delete();
            Log.e("niupi", "Unable to store cached tile from " + pTileSourceInfo.name() + " " + MapTileIndex.toString(pMapTileIndex), ex);
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    @Override
    public boolean exists(ITileSource pTileSource, final long pMapTileIndex) {
        int x = MapTileIndex.getX(pMapTileIndex);
        int y = MapTileIndex.getY(pMapTileIndex);
        // File file = new File(dir + DataTool.getMapCacheName(x, y, zoom));
        File file = new File(dir + ""+x +"/"+y+".png");
        return file.exists();
    }

    @Override
    public void onDetach() {

    }

    @Override
    public boolean remove(final ITileSource tileSource, final long pMapTileIndex) {
        //not supported
        return false;
    }

    @Override
    public Long getExpirationTimestamp(final ITileSource pTileSource, final long pMapTileIndex) {
        return null;
    }


    /**
     * @since 5.6.5
     */
    @Override
    public Drawable loadTile(final ITileSource pTileSource, final long pMapTileIndex) throws Exception {
        InputStream inputStream = null;
        try {
            int x = MapTileIndex.getX(pMapTileIndex);
            int y = MapTileIndex.getY(pMapTileIndex);
            // File file = new File(dir + DataTool.getMapCacheName(x, y, zoom));
            File file = new File(dir + ""+x +""+y+".png");
            inputStream = new FileInputStream(file);
            return pTileSource.getDrawable(inputStream);
        } finally {
            if (inputStream != null) {
                StreamUtils.closeStream(inputStream);
            }
        }
    }
}