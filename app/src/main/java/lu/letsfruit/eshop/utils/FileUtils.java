package lu.letsfruit.eshop.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.webkit.MimeTypeMap;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    public static String fromUriToBase64(Context context, Uri uri) throws IOException {

        String extension = getMimeType(context, uri);
        InputStream is = context.getContentResolver().openInputStream(uri);

        String prefix = "";

        if(extension.equals("image/jpeg")){
            prefix = "data:image/jpeg;base64,";
        } else if(extension.equals("image/png")){
            prefix = "data:image/png;base64,";
        } else if(extension.equals("image/jpg")) {
            prefix = "data:image/jpg;base64,";
        } else {
            throw new IOException("L'image doit être en png ou en jpg");
        }

        //encode le fichier au format base64 "URL safe",
        //car le serveur utilisera : Base64.getUrlDecoder().decode(base64Str);
        byte[] encoded = Base64.encode(
                IOUtils.toByteArray(is), Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);

        return prefix + new String(encoded);
    }

    private static String getMimeType(Context context, Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }
}
