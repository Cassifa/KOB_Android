package com.example.kob_android.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.kob_android.R;
import com.example.kob_android.activity.ModifyMyBotActivity;
import com.example.kob_android.adapter.BotItemAdapter;
import com.example.kob_android.net.BotApiService;
import com.example.kob_android.net.responseData.pojo.Bot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-24  07:40
 * @Description:
 */
@AndroidEntryPoint
public class BotListFragment extends Fragment implements View.OnClickListener {
    @Inject
    BotApiService botApiService;
    Button addBotBtn;
    ListView botList;

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mybots, container, false);
        botList = view.findViewById(R.id.myBotsView);
        addBotBtn = view.findViewById(R.id.addBot);
        addBotBtn.setOnClickListener(this);
        new FetchRecordTask(botList).execute();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new FetchRecordTask(botList).execute(); // 刷新数据
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), ModifyMyBotActivity.class);
        intent.putExtra("isNew", true);
        requireContext().startActivity(intent);
    }

    private class FetchRecordTask extends AsyncTask<Void, Void, List<Bot>> {
        private final ListView botListView;

        FetchRecordTask(ListView botList) {
            this.botListView = botList;
        }

        @Override
        protected List<Bot> doInBackground(Void... voids) {
            List<Bot> botList = new ArrayList<>();
            try {
                Call<List<Bot>> call = botApiService.getlist();
                Response<List<Bot>> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    List<Bot> list = response.body();
                    botList.addAll(list);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return botList;
        }

        @Override
        protected void onPostExecute(List<Bot> botList) {
            BotItemAdapter adapter = new BotItemAdapter(getActivity(), botList, botApiService);
            botListView.setAdapter(adapter);
        }
    }
}
