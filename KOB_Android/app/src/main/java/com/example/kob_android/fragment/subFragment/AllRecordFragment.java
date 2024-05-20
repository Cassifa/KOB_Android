package com.example.kob_android.fragment.subFragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.kob_android.R;
import com.example.kob_android.adapter.RecordItemAdapter;
import com.example.kob_android.net.ListApiService;
import com.example.kob_android.net.responseData.RecordItemsData;
import com.example.kob_android.pojo.RecordItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-24  04:18
 * @Description:
 */
@AndroidEntryPoint
public class AllRecordFragment extends Fragment {
    @Inject
    ListApiService listApiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment_all_record, container, false);
        ListView rankList = view.findViewById(R.id.all_record_list);

        new FetchRecordTask(rankList).execute();
        return view;
    }
    private class FetchRecordTask extends AsyncTask<Void, Void, List<RecordItem>> {
        private ListView rankList;

        FetchRecordTask(ListView rankList) {
            this.rankList = rankList;
        }

        @Override
        protected List<RecordItem> doInBackground(Void... voids) {
            List<RecordItem> recordItemList = new ArrayList<>();
            try {
                Call<RecordItemsData> call = listApiService.getRecordList(1);
                Response<RecordItemsData> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    RecordItemsData recordItemsData = response.body();
                    if (recordItemsData.getRecords() != null) {
                        recordItemList.addAll(recordItemsData.getRecords());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return recordItemList;
        }

        @Override
        protected void onPostExecute(List<RecordItem> recordItemList) {
            RecordItemAdapter adapter = new RecordItemAdapter(getContext(), recordItemList);
            rankList.setAdapter(adapter);
        }
    }
}
