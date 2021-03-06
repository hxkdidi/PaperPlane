package com.marktony.zhihudaily.refactor.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.marktony.zhihudaily.refactor.data.ZhihuDailyNewsQuestion;

import java.util.List;

/**
 * Created by lizhaotailang on 2017/6/15.
 */

@Dao
public interface ZhihuDailyNewsDao {

    @Query("SELECT * FROM zhihu_daily_news")
    List<ZhihuDailyNewsQuestion> loadAllZhihuDailyNews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ZhihuDailyNewsQuestion> items);

    @Query("SELECT * FROM zhihu_daily_news where id = :id")
    ZhihuDailyNewsQuestion loadZhihuDailyNewsItem(int id);

    @Update
    void updateZhihuDailyNews(ZhihuDailyNewsQuestion question);

}
