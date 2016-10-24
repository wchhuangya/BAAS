package com.ch.wchhuangya.baas.activity.dbdatashow;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ch.wchhuangya.baas.BaseActivity;
import com.ch.wchhuangya.baas.R;
import com.ch.wchhuangya.baas.db.PhoneDB;

/**
 * 展示 Phone.db 数据库内容
 * Created by wchya on 16/10/19.
 */

public class PhoneDBShowActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private CustomAdapter mAdapter;
    private String search = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_db_show);

        init();
    }

    private void init() {
        /*findViewById(R.id.listview_ll).setVisibility(View.GONE);*/
        final EditText mSearchEt = (EditText) findViewById(R.id.listview_et);
        findViewById(R.id.listview_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = mSearchEt.getText().toString().trim();
                getLoaderManager().restartLoader(0, null, PhoneDBShowActivity.this);
            }
        });
        ListView mListView = (ListView) findViewById(R.id.listview_listview);
        TextView mNoDataTv = (TextView) findViewById(R.id.listview_nodata_tv);

        mAdapter = new CustomAdapter(mActivity, null, false);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(mNoDataTv);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CustomLoader(mActivity);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    static class CustomLoader extends AsyncTaskLoader<Cursor> {
        private Context mContext;

        CustomLoader(Context context) {
            super(context);
            mContext = context;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Override
        public Cursor loadInBackground() {
            return PhoneDB.getInstance(mContext).findAll();
        }
    }

    class CustomAdapter extends CursorAdapter {

        CustomAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return View.inflate(context, R.layout.phone_db_calls_record_item, null);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            Holder holder;
            if (view.getTag() == null) {
                holder = new Holder();
                holder.mNumberTv = (TextView) view.findViewById(R.id.calls_record_number_tv);
                holder.mSyncTv = (TextView) view.findViewById(R.id.calls_record_sync_tv);
                holder.mTimeTv = (TextView) view.findViewById(R.id.calls_record_time_tv);
                holder.mTypeTv = (TextView) view.findViewById(R.id.calls_record_type_tv);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            holder.mNumberTv.setText(cursor.getString(cursor.getColumnIndex(PhoneDB.CallsRecord.PHONE_NUMBER)));
            holder.mSyncTv.setText(PhoneDB.SyncFlag.getDescribe(cursor.getInt(cursor.getColumnIndex(PhoneDB.CallsRecord.SYNC_FLAG))));
            holder.mTimeTv.setText(cursor.getString(cursor.getColumnIndex(PhoneDB.CallsRecord.DATA_TIME)));
            holder.mTypeTv.setText(cursor.getString(cursor.getColumnIndex(PhoneDB.CallsRecord.CALL_TYPE)));
        }

        class Holder {
            private TextView mNumberTv;
            private TextView mSyncTv;
            private TextView mTimeTv;
            private TextView mTypeTv;
        }
    }
}
