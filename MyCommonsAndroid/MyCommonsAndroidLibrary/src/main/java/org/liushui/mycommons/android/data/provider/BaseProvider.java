package org.liushui.mycommons.android.data.provider;

import java.util.Arrays;

import org.liushui.mycommons.android.log.McLog;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Title: BaseProvider.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2013-5-15<br>
 * Version:v1.0
 */
public abstract class BaseProvider extends ContentProvider {

    protected static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    protected Context mContext;

    public boolean onCreate() {
        this.mContext = getContext();
        McLog.m(this, "onCreate");
        SQLiteDatabase db = getReadableDatabase();
        boolean r = db != null;
        if (db != null) {
            db.close();
        }
        return r;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        McLog.m(this, "query");
        McLog.i("uri = " + uri);
        McLog.i("projection = " + Arrays.toString(projection));
        McLog.i("selection = " + selection);
        McLog.i("selectionArgs = " + Arrays.toString(selectionArgs));
        McLog.i("sortOrder = " + sortOrder);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(getTableName(uri), projection, selection, selectionArgs, null, null, sortOrder);
            McLog.i("query rows = " + cursor.getCount());
        } catch (Exception e) {
            McLog.e(e.toString());
        }
        if (db != null) {
            db.close();
        }
        return cursor;
    }

    public Uri insert(Uri uri, ContentValues values) {
        McLog.m(this, "insert");
        McLog.i("uri = " + uri);
        McLog.i("values = " + values);

        SQLiteDatabase db = getWritableDatabase();
        long rowId = db.insert(getTableName(uri), null, values);
        Uri rowUri = ContentUris.appendId(getUri(uri).buildUpon(), rowId).build();
        if (db != null) {
            db.close();
        }
        McLog.i("insert uri = " + rowUri);
        getContext().getContentResolver().notifyChange(rowUri, null);
        return rowUri;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        McLog.m(this, "delete");
        McLog.i("uri = " + uri);
        McLog.i("selection = " + selection);
        McLog.i("selectionArgs = " + Arrays.toString(selectionArgs));

        int r = 0;
        SQLiteDatabase db = getWritableDatabase();
        r = db.delete(getTableName(uri), selection, selectionArgs);
        McLog.i("delete rows = " + r);
        if (db != null) {
            db.close();
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return r;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        McLog.m(this, "update");
        McLog.i("uri = " + uri);
        McLog.i("values = " + values);
        McLog.i("selection = " + selection);
        McLog.i("selectionArgs = " + Arrays.toString(selectionArgs));

        int match = sURIMatcher.match(uri);
        McLog.i("match  = " + match);
        int r = 0;
        SQLiteDatabase db = getWritableDatabase();
        r = db.update(getTableName(uri), values, selection, selectionArgs);
        McLog.i("update rows = " + r);
        if (db != null) {
            db.close();
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return r;
    }

    /**
     * 根据Uri得到表名
     *
     * @param uri
     * @return
     */
    protected abstract String getTableName(Uri uri);

    /**
     * 得到跟uri
     *
     * @param uri
     * @return
     */
    protected abstract Uri getUri(Uri uri);

    /**
     * 可写db
     *
     * @return
     */
    protected abstract SQLiteDatabase getWritableDatabase();

    /**
     * 可读db
     *
     * @return
     */
    protected abstract SQLiteDatabase getReadableDatabase();
}
