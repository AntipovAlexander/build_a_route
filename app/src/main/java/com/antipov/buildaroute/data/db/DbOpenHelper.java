package com.antipov.buildaroute.data.db;

import android.content.Context;

import com.antipov.buildaroute.data.db.entities.DaoMaster;

import org.greenrobot.greendao.database.Database;

public class DbOpenHelper extends DaoMaster.OpenHelper {
    public DbOpenHelper(Context context, String name) {
        super(context, name);
    }

    /**
     * migrations goes here
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
