package com.oviesAries.recipe.domain.utill;

import java.sql.Blob;
import java.util.Base64;

public class BlobConverter {

    public static String blobToString(Blob blob) {
        try {
            byte[] bytes = blob.getBytes(1, (int) blob.length());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
