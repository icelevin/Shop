package com.jyunmore.lib_ec.datebase;

import android.content.Context;
import android.service.autofill.Dataset;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

public class DataBaseManager {

    private DaoSession daoSession;
    private UserProfileDao dao;

    public static final class Holder {
        public static final DataBaseManager INSTANCE = new DataBaseManager();

    }

    public DataBaseManager init(Context context) {
        initDao(context);
        return this;
    }

    public static DataBaseManager getInstance() {
        return Holder.INSTANCE;
    }

    private void initDao(Context context) {
        final ReleaseOpenHolder openHolder = new ReleaseOpenHolder(context, "fast_ec.db");
        final Database database = openHolder.getWritableDb();
        daoSession = new DaoMaster(database).newSession();
        dao = daoSession.getUserProfileDao();
    }

    public final UserProfileDao getDao() {
        return dao;
    }
}
