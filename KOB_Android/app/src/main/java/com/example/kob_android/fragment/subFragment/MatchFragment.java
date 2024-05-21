package com.example.kob_android.fragment.subFragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.kob_android.R;
import com.example.kob_android.adapter.BotItemAdapter;
import com.example.kob_android.fragment.BotListFragment;
import com.example.kob_android.fragment.PlayGroundFragment;
import com.example.kob_android.net.BotApiService;
import com.example.kob_android.net.responseData.pojo.Bot;
import com.example.kob_android.net.responseData.pojo.User;
import com.example.kob_android.utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Response;

@AndroidEntryPoint
public class MatchFragment extends Fragment {
    @Inject
    BotApiService botApiService;


    PlayGroundFragment father;
    Spinner botSpinner;
    ImageView myPhoto;
    ImageView opponentPhoto;
    TextView myName;
    TextView opponentName;
    View view;
    int checkedBot;

    List<Bot> bots;

    public MatchFragment(PlayGroundFragment father) {
        this.father = father;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_match, container, false);
        initView();
        refreshSpinner();
        return view;
    }

    private void initView() {
        botSpinner = view.findViewById(R.id.currentGameBotsSpinner);
        myName = view.findViewById(R.id.currentGameMyName);
        myPhoto = view.findViewById(R.id.currentGameMyPhoto);
        opponentName = view.findViewById(R.id.currentGameOpponentName);
        opponentPhoto = view.findViewById(R.id.currentGameOpponentPhoto);
        updateInfo(Constant.getMyInfo(),true);
    }

    //刷新数据列表
    private void refreshSpinner() {
        checkedBot = -1;
        //获取adapter和bots列表
        new MatchFragment.FetchRecordTask().execute();
    }

    public int getCheckedBotId() {
        String selectedText = botSpinner.getSelectedItem().toString();
        if (selectedText.equals("亲自出马")) return -1;
        for (Bot bot : bots)
            if (bot.getTitle().equals(selectedText))
                return bot.getId();
        return -1;
    }

    //更新用户信息
    public void updateInfo(User user, boolean isMe) {
        if (isMe) {
            Constant.setHttpImg(myPhoto,user.getPhoto(),getContext());
            myName.setText(user.getUsername());
        } else {
            Constant.setHttpImg(opponentPhoto,user.getPhoto(),getContext());
            opponentName.setText(user.getUsername());
        }
    }


    private class FetchRecordTask extends AsyncTask<Void, Void, List<Bot>> {
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
            bots = botList;
            String[] botNameArray = new String[bots.size() + 1];
            botNameArray[0] = "亲自出马";
            for (int i = 0; i < bots.size(); i++) {
                botNameArray[i + 1] = bots.get(i).getTitle();
            }
            ArrayAdapter<String> botsAdapter =
                    new ArrayAdapter<>(requireActivity(), R.layout.item_optional_bots, botNameArray);
            botSpinner.setAdapter(botsAdapter);
            botSpinner.setSelection(0);
        }
    }
}