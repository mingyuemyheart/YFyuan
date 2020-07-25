package com.moft.xfapply.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.crash.CrashHandler;
import com.moft.xfapply.task.SendReportAsyncTask;

public class ReportErrorActivity extends BaseActivity {
    private TextView tv_action_progress = null;
    private TextView tv_action_status = null;
    private TextView tv_action_tin = null;
    private ProgressBar loading_process = null;
    private int totalCnt = 0;
    private int totalFailureCnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_error);

        loading_process = (ProgressBar) this.findViewById(R.id.loading_process);
        tv_action_status = (TextView) this.findViewById(R.id.tv_action_status);
        tv_action_progress = (TextView) this.findViewById(R.id.tv_action_progress);

        tv_action_tin = (TextView) this.findViewById(R.id.tv_action_tin);
        tv_action_tin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        CrashHandler.sendCrashReportsToServer(new SendReportAsyncTask.OnSendReportListener() {

            @Override
            public void onPreExecute() {
                loading_process.setVisibility(View.VISIBLE);
                tv_action_progress.setVisibility(View.VISIBLE);
                tv_action_progress.setText("");
                tv_action_status.setText("正在上传异常日志...");
                tv_action_tin.setVisibility(View.GONE);
            }

            @Override
            public void onProgress(Integer... progress) {
                totalCnt = progress[2];
                totalFailureCnt = progress[1];
                if (progress[1] > 0) {
                    tv_action_progress.setText((progress[0] + progress[1]) +
                            "/" + progress[2] + "失败(" + progress[1] + ")");
                } else {
                    tv_action_progress.setText((progress[0] + progress[1]) +
                            "/" + progress[2]);
                }
            }

            @Override
            public void onPostExecute() {
                loading_process.setVisibility(View.GONE);
                tv_action_progress.setVisibility(View.GONE);
                tv_action_tin.setVisibility(View.VISIBLE);

                if (totalFailureCnt == 0) {
                    tv_action_status.setText("异常日志上传成功，新版本会尽快修复。");
                } else if (totalFailureCnt >= totalCnt) {
                    tv_action_status.setText("异常日志上传失败，下次启动会自动上传。");
                } else {
                    tv_action_status.setText("已上传部分异常日志，新版本会尽快修复。其中失败的日志下次启动会自动上传。");
                }
            }
        });
    }
}
