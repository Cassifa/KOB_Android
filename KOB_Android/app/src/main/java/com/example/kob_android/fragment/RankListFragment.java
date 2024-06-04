package com.example.kob_android.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.kob_android.R;
import com.example.kob_android.adapter.RankItemAdapter;
import com.example.kob_android.net.ListApiService;
import com.example.kob_android.net.responseData.RankListData;
import com.example.kob_android.net.responseData.pojo.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-23  19:39
 * @Description:
 */
@AndroidEntryPoint
public class RankListFragment extends Fragment {
    @Inject
    ListApiService listApiService;

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranklist, container, false);
        ListView rankList = view.findViewById(R.id.rankListView);
        new FetchRecordTask(rankList).execute();
        return view;
    }

    private class FetchRecordTask extends AsyncTask<Void, Void, List<User>> {
        private final ListView rankList;

        FetchRecordTask(ListView rankList) {
            this.rankList = rankList;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            List<User> rankList = new ArrayList<>();
            try {
                Call<RankListData> call = listApiService.getRankList(-1);
                Response<RankListData> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    RankListData rankListData = response.body();
                    if (rankListData.getUsers() != null) {
                        rankList.addAll(rankListData.getUsers());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return rankList;
        }

        @Override
        protected void onPostExecute(List<User> userList) {
            RankItemAdapter adapter = new RankItemAdapter(getContext(), userList);
            rankList.setAdapter(adapter);
        }
    }

}
